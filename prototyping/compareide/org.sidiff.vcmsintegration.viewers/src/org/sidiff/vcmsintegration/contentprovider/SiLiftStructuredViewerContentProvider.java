package org.sidiff.vcmsintegration.contentprovider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.emf.doctype.util.EMFDocumentTypeUtil;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.integration.preferences.settingsadapter.SettingsAdapterUtil;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.util.PatchingUtils;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.sidiff.vcmsintegration.remote.CompareResource;
import org.sidiff.vcmsintegration.util.MessageDialogUtil;

// TODO: the operation invocation is always applied on the left side even though there are two distinct actions
// FIXME: applying operations does not work
/**
 * This is a special content provider that is used for the {@link TreeViewer} in
 * the SiLift structure view. This structure view shows the difference between
 * the two models. Therefore this provider will receive the two models as input,
 * but must display the difference that is being lifted by the
 * {@link LiftingFacade}. That is what this content provider does. It receives
 * the two models as input, calculates the {@link AsymmetricDifference} and
 * provides methods for the {@link TreeViewer} to show it.
 * 
 * @author Adrian Bingener, Robert Müller
 *
 */
public class SiLiftStructuredViewerContentProvider extends AdapterFactoryContentProvider {

	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	/**
	 * The resource of the left model that is used as input for this content
	 * provider.
	 */
	private CompareResource left;
	/**
	 * The resource of the right model that is used as input for this content
	 * provider
	 */
	private CompareResource right;
	/**
	 * The common ancestor. May be null.
	 */
	private CompareResource ancestor;
	/**
	 * The PatchEngine is required to apply OperationInvocations. May be null.
	 */
	private PatchEngine patchEngine;
	/**
	 * PatchingSettings used to create the PatchEngine. May be null.
	 */
	private PatchingSettings patchingSettings;
	/**
	 * The Model, which will be or has been patched by the current PatchEngine.
	 */
	private CompareResource patchedResource;
	/**
	 * Defines what content the provider will return, based on the given input
	 * models. For now it can only show the two types of differences, the
	 * symmetric and asymmetric difference.
	 */
	private DisplayMode displayMode;
	/**
	 * If present, the symmetric difference is stored in this variable. Once the
	 * user choose the asymmetric difference to show, it keeps stored in this
	 * variable. So the difference must not be recomputed each time the display
	 * mode changes.
	 */
	private SymmetricDifference symmetricDifference;
	/**
	 * If present, the symmetric difference is stored in this variable. Once the
	 * user choose the symmetric difference to show, it keeps stored in this
	 * variable. So the difference must not be recomputed each time the display
	 * mode changes.
	 */
	private AsymmetricDifference asymmetricDifference;

	/**
	 * Creates a new instance of the
	 * {@link SiLiftStructuredViewerContentProvider}. The given display mode
	 * defines which type of difference is displayed when the viewer, that this
	 * content provider provides data for, is initially created.
	 * 
	 * @param displayMode The type of difference that is displayed
	 */
	public SiLiftStructuredViewerContentProvider(AdapterFactory adapterFactory, DisplayMode displayMode) {
		super(adapterFactory);
		this.displayMode = displayMode;
	}

	/**
	 * Creates a new instance of the
	 * {@link SiLiftStructuredViewerContentProvider}. The given display mode
	 * defines which type of difference is displayed when the viewer, that this
	 * content provider provides data for, is initially created.
	 * 
	 * @param displayMode The type of difference that is displayed
	 */
	public SiLiftStructuredViewerContentProvider(AdapterFactory adapterFactory, DisplayMode displayMode,
			CompareConfiguration compareConfiguration) {
		super(adapterFactory);
		this.displayMode = displayMode;
	}

	/**
	 * This method is called when the view is created. The given input element
	 * represents the two compared models, when using e.g.
	 * "Compare with &gt; Each other". This method assumes that the given input
	 * is an {@link ICompareInput}, which provides methods to access the left
	 * and right model. These models are then used to derive the difference
	 * which is then used as input for the {@link TreeViewer}.
	 * 
	 * @param inputElement The input element which should be an
	 *            {@link ICompareInput}
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if(!(inputElement instanceof ICompareInput)) {
			MessageDialogUtil.showErrorDialog("Invalid input", "The given input cannot be used to derive a "
					+ "difference that can be displayed in the tree viewer");
			return EMPTY_OBJECT_ARRAY;
		}

		ICompareInput compareInput = (ICompareInput) inputElement;

		// load the resources
		try {
			left = CompareResource.load(compareInput.getLeft(), CompareResource.Side.LEFT);
			right = CompareResource.load(compareInput.getRight(), CompareResource.Side.RIGHT);
			ancestor = CompareResource.load(compareInput.getAncestor(), CompareResource.Side.ANCESTOR);
		} catch (IOException | CoreException e) {
			MessageDialogUtil.showExceptionDialog(e);
			return EMPTY_OBJECT_ARRAY;
		}

		// initialize differences
		try {
			refresh(displayMode);
		} catch (InvalidModelException | NoCorrespondencesException e) {
			MessageDialogUtil.showExceptionDialog(e);
			return EMPTY_OBJECT_ARRAY;
		}

		switch (displayMode) {
			case SYMMETRIC_DIFFERENCE:
				List<Object> elements = new LinkedList<Object>();
				// add ChangeSets
				for(SemanticChangeSet changeSet : symmetricDifference.getChangeSets()) {
					elements.add(changeSet);
				}
				// add all changes, which are not in any ChangeSet
				for(Change change : symmetricDifference.getChanges()) {
					boolean found = false;
					for(SemanticChangeSet changeSet : symmetricDifference.getChangeSets()) {
						if(changeSet.getChanges().contains(change)) {
							found = true;
							break;
						}
					}
					// add change if found
					if(!found) {
						elements.add(change);
					}
				}
				// add matching
				elements.add(symmetricDifference.getMatching());
				return elements.toArray();

			case ASYMMETRIC_DIFFERENCE:
				// get a list of all OperationInvocations, which have not
				// been applied
				List<OperationInvocation> operations = patchEngine.getOperationManager()
						.getOperationInvocations(OperationInvocationStatus.INIT);
				// remove OperationInvocations, which are dependencies
				operations = filterOperationInvocationsWithDependencies(operations);
				return operations.toArray();
		}

		return EMPTY_OBJECT_ARRAY;
	}

	/**
	 * Filters the given list, by removing all {@link OperationInvocation}s
	 * which have dependencies. This method returns a new list that contains
	 * only those {@link OperationInvocation}s that don't have any dependencies.
	 * 
	 * @param input A list of {@link OperationInvocation}s that is filtered
	 * @return A new list that only contains {@link OperationInvocation}s that
	 *         don't have any dependencies.
	 * @param input List of all OperationInvocation
	 * @return Filtered list of OperationInvocations
	 */
	private List<OperationInvocation> filterOperationInvocationsWithDependencies(Collection<OperationInvocation> input) {
		LinkedList<OperationInvocation> result = new LinkedList<OperationInvocation>();
		for (OperationInvocation operationInvocation : input) {
			// check if OperationInvocation has any predecessors
			// every OperationInvocation has itself as a predecessor
			if (operationInvocation.getAllSuccessors().size() == 1) {
				result.add(operationInvocation);
			}
		}
		return result;
	}

	/**
	 * Returns all children of the given object (as {@link EObject}, or an empty
	 * list if it has none or the given element is not an {@link EObject}.
	 * 
	 * @param parentElement The element that is searched for children
	 * @return All children of the given element
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		LinkedList<Object> result = new LinkedList<Object>();
		if (parentElement instanceof EObject) {

			// This if-else cases, cause certain subtrees in the differences to
			// show special content, not just the eContents
			if (displayMode.equals(DisplayMode.ASYMMETRIC_DIFFERENCE)) {

				// The child elements of the OperationInvocation should be the
				// changes
				if (parentElement instanceof OperationInvocation) {
					result = new LinkedList<Object>(Arrays.asList(((OperationInvocation) parentElement).getChangeSet()));
					// Add predecessors of OperationInvocations as children in
					// tree view
					for (OperationInvocation predecessors : ((OperationInvocation) parentElement).getAllPredecessors()) {
						// every OperationInvocation has itself as predecessors
						// add only if OperationInvocation has not been applied
						// yet
						if (predecessors != parentElement && patchEngine.getOperationManager().getStatusWrapper(predecessors).getStatus() != OperationInvocationStatus.PASSED) {
							result.addFirst(predecessors);
						}
					}
					// show changes in SemanticChangeSet
				} else if (parentElement instanceof SemanticChangeSet) {
					result = new LinkedList<Object>(Arrays.asList(((SemanticChangeSet) parentElement).getChanges().toArray()));
				}

			} else if (displayMode.equals(DisplayMode.SYMMETRIC_DIFFERENCE)) {
				// The child elements of the SemanticChangeSet should be the
				// changes
				if (parentElement instanceof SemanticChangeSet) {
					result = new LinkedList<Object>(Arrays.asList(((SemanticChangeSet) parentElement).getChanges().toArray()));
				}
			}

			// If non of the above cases matches, there must not be special
			// handling for the current object
			if (result.isEmpty()) {
				result = new LinkedList<Object>(Arrays.asList(((EObject) parentElement).eContents().toArray()));
			}
		}
		LogUtil.log(LogEvent.WARNING, "Trying to display a non EObject in tree view");
		return result.toArray();
	}

	/**
	 * Recalculates the model difference. The difference that is calculated
	 * depends on the given display mode.
	 * 
	 * @param displayMode The display mode that is used to refresh
	 * @throws InvalidModelException If one of the given models is invalid
	 * @throws NoCorrespondencesException If no correspondences could be found
	 *             between the models
	 * @throws UnsupportedFeatureLevelException
	 * @throws InvalidSettingsException
	 */
	public void refresh(DisplayMode displayMode) throws InvalidModelException, NoCorrespondencesException {
		Assert.isNotNull(displayMode);
		LogUtil.log(LogEvent.NOTICE, String.format("Refreshing contents for display mode %s", displayMode));
		
		// TODO: make sure that getResource() is not null here

		InputModels inputModels = new InputModels(left.getResource(), right.getResource());
		if(!inputModels.haveSameDocumentType()) {
			throw new InvalidModelException("Document types of input models are not matching");
		}

		// if we can get the current project, use project settings
		// else use global settings
		IFile leftFile = Adapters.adapt(left.getTypedElement(), IFile.class);

		LiftingSettings settings = new LiftingSettings();
		if (leftFile != null) {
			SettingsAdapterUtil.adaptSettingsProject(settings, inputModels.getProject(),
					inputModels.getDocumentTypes(), Collections.<Enum<?>>emptySet());
		} else {
			SettingsAdapterUtil.adaptSettingsGlobal(settings, inputModels.getDocumentTypes(), 
					Collections.<Enum<?>>emptySet());
		}

		switch (displayMode) {
			case SYMMETRIC_DIFFERENCE:
				symmetricDifference = LiftingFacade.liftTechnicalDifference(left.getResource(), right.getResource(), settings);
				break;

			case ASYMMETRIC_DIFFERENCE:
				if(ancestor != null && ancestor.getResource() != null) {
					asymmetricDifference = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(
							ancestor.getResource(), right.getResource(), settings).getAsymmetric();
				} else {
					asymmetricDifference = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(
							left.getResource(), right.getResource(), settings).getAsymmetric();
				}
				
				// create new PatchEngine
				createPatchEngine();
				break;
		}
	}

	/**
	 * The PatchEngine is required to apply OperationInvocations. May be null.
	 * 
	 * @return The PatchEngine currently in use.
	 */
	public PatchEngine getPatchEngine() {
		return patchEngine;
	}

	/**
	 * @return the patchingSettings The PatchingSettings used to create the
	 *         current PatchEngine.
	 */
	public PatchingSettings getPatchingSettings() {
		return patchingSettings;
	}

	/**
	 * @return The Model, which will be or has been patched by the current
	 *         PatchEngine.
	 */
	public CompareResource getPatchedResource() {
		return patchedResource;
	}

	/**
	 * Uses the {@link #refresh(DisplayMode)} method to calculate the model
	 * difference. The difference that is calculated depends on the given
	 * display mode. Unlike {@link #refresh(DisplayMode)} this method, does not
	 * block, but will return immediately. It will start a background process
	 * that will show a waiting dialog until the calculation is finished.
	 * 
	 * @param displayMode The type of difference that is calculated
	 */
	public void refreshAsync(final DisplayMode displayMode) {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					// Actual refresh method that blocks
					monitor.beginTask("Calculating model difference", IProgressMonitor.UNKNOWN);
					refresh(displayMode);
					monitor.done();
				} catch (InvalidModelException | NoCorrespondencesException e) {
					throw new InvocationTargetException(e);
				}
			}
		};

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		Shell shell = win != null ? win.getShell() : null;
		try {
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
			dialog.run(true, false, runnable);
		} catch (InvocationTargetException e) {
			MessageDialogUtil.showExceptionDialog(e.getTargetException());
		} catch (InterruptedException e) {
			MessageDialogUtil.showExceptionDialog(e);
		}
	}

	/**
	 * 
	 * @return Left CompareResource
	 */
	public CompareResource getLeft() {
		return left;
	}

	/**
	 * 
	 * @param left New left CompareReosurce
	 */
	public void setLeft(CompareResource left) {
		this.left = left;
	}

	/**
	 * 
	 * @return Right CompareResource
	 */
	public CompareResource getRight() {
		return right;
	}

	/**
	 * 
	 * @param right New right CompareResource
	 */
	public void setRight(CompareResource right) {
		this.right = right;
	}

	/**
	 * 
	 * @return The ancestor CompareResource. Might be null
	 */
	public CompareResource getAncestor() {
		return ancestor;
	}

	/**
	 * 
	 * @param ancestor New ancestor CompareResource
	 */
	public void setAncestor(CompareResource ancestor) {
		this.ancestor = ancestor;
	}

	/**
	 * Set the DisplayMode. Defines which difference is shown. Either asymmetric
	 * or symmetric.
	 * 
	 * @param displayMode New DisplayMode
	 */
	public void setDisplayMode(DisplayMode displayMode) {
		this.displayMode = displayMode;
	}

	/**
	 * 
	 * @return Current DisplayMode
	 */
	public DisplayMode getDisplayMode() {
		return displayMode;
	}

	/**
	 * 
	 * @return Current asymmetric difference
	 */
	public AsymmetricDifference getAsymmetricDifference() {
		return asymmetricDifference;
	}

	/**
	 * Toggle DifferenceDirection. Switches between LEFT_TO_RIGHT and
	 * RIGHT_TO_LEFT
	 * @throws UnsupportedFeatureLevelException 
	 * @throws InvalidSettingsException 
	 * @throws NoCorrespondencesException 
	 * @throws InvalidModelException 
	 */
	public void switchDifferenceDirection() throws InvalidModelException, NoCorrespondencesException {
		// FIXME: this does not work
		CompareResource tmp = left;
		left = right;
		right = tmp;
		refresh(displayMode);
	}

	/**
	 * Applies the specified OperationInvocation after all its predecessors have
	 * been executed.
	 * 
	 * @param operationInvocation OperationInvocation to apply.
	 * @throws Exception if the execution of the operation failed
	 */
	public void applyOperationInvocation(OperationInvocation operationInvocation) throws Exception {
		// check if OperationInvocation has any predecessors
		if (operationInvocation.getPredecessors().size() > 0) {
			// check if the predecessors have been executed
			for (OperationInvocation predecessor : operationInvocation.getPredecessors()) {
				// should ignore itself
				// and apply the predecessor if the status is not passed
				if (operationInvocation != predecessor
						&& patchEngine.getOperationManager().getStatusWrapper(predecessor).getStatus() != OperationInvocationStatus.PASSED) {
					// call applyOperationInvocation recursive
					this.applyOperationInvocation(predecessor);
					// check if apply was successful
					OperationInvocationWrapper wrapper = patchEngine.getOperationManager().getStatusWrapper(predecessor);
					if (wrapper.getStatus() != OperationInvocationStatus.PASSED) {
						Exception e = wrapper.getExecutionError();
						if(e == null)
							throw new RuntimeException("Operation could not be executed: " + wrapper.getStatus());
						throw e;
					}
				}
			}
		}

		// apply OperationInvocation
		patchEngine.apply(operationInvocation, patchingSettings.getExecutionMode().equals(ExecutionMode.INTERACTIVE));
	}

	/**
	 * Creates a new PatchEngine and stores result in the global variable. The
	 * used PatchingSettings are stored in its global variable, too. Decides
	 * which resource will be patched based on the direction of the asymmetric
	 * difference.
	 * 
	 * Should be called when: -a new asymmetric difference has been calculated
	 */
	private void createPatchEngine() {
		// decide which ressource to patch
		final CompareResource resourceA = getLeft();
		final CompareResource resourceB = getRight();

		// get document type
		Set<String> documentTypes = new HashSet<>(EMFDocumentTypeUtil.resolve(resourceA.getResource()));

		// if we can get the current project, use project settings
		// else use global settings
		IResource platformResource = resourceA.getPlatformResource();
		PatchingSettings patchingSettings = new PatchingSettings();
		if (platformResource != null) {
			SettingsAdapterUtil.adaptSettingsProject(patchingSettings, platformResource.getProject(),
					documentTypes, Collections.<Enum<?>>emptySet());
		} else {
			SettingsAdapterUtil.adaptSettingsGlobal(patchingSettings, documentTypes, 
					Collections.<Enum<?>>emptySet());
		}

		// Use interactive argument manager
		IArgumentManager argumentManager = PatchingUtils.getArgumentManager(asymmetricDifference,
				resourceA.getResource(), patchingSettings, patchingSettings.getExecutionMode());
		if(argumentManager == null) {
			throw new RuntimeException("No argument manager found");
		}
		patchingSettings.setArgumentManager(argumentManager);

		// Dialog Patch interrupt handler
		patchingSettings.setInterruptHandler(new DialogPatchInterruptHandler());

		// create PatchEngine
		AsymmetricDifference asymmetricDifference = this.getAsymmetricDifference();
		PatchEngine patchEngine = new PatchEngine(asymmetricDifference, resourceA.getResource(), patchingSettings);

		// init modified detector
		IModifiedDetector modifiedDetector = patchingSettings.getModifiedDetector();
		if (modifiedDetector != null) {
			try {
				modifiedDetector.init(asymmetricDifference.getOriginModel(), resourceB.getResource(),
						patchingSettings.getMatcher(), patchingSettings.getScope());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		// set global variables
		this.patchingSettings = patchingSettings;
		this.patchEngine = patchEngine;
		this.patchedResource = resourceA;

		// TODO: is this still required?
		if(platformResource != null) {
			this.patchedResource.getResource().setURI(URI.createFileURI(platformResource.getLocationURI().getPath()));
		}
	}
}
