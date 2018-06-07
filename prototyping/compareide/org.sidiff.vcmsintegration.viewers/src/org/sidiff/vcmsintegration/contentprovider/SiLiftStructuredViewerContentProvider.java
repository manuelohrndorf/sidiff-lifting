package org.sidiff.vcmsintegration.contentprovider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IResourceProvider;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.ui.internal.revision.ResourceEditableRevision;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.emf.access.EMFModelAccess;
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
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.vcmsintegration.preferences.exceptions.InvalidSettingsException;
import org.sidiff.vcmsintegration.preferences.exceptions.UnsupportedFeatureLevelException;
import org.sidiff.vcmsintegration.preferences.util.PreferenceUtil;
import org.sidiff.vcmsintegration.preferences.util.SettingsFactory;
import org.sidiff.vcmsintegration.util.MessageDialogUtil;

/**
 * This is a special content provider that is used for the {@link TreeViewer} in
 * the SiLift structure view. This structure view shows the difference between
 * the two models. Therefore this provider will receive the two models as input,
 * but must display the difference that is being lifted by the
 * {@link LiftingFacade}. That is what this content provider does. It receives
 * the two models as input, calculates the {@link AsymmetricDifference} and
 * provides methods for the {@link TreeViewer} to show it.
 * 
 * @author Adrian Bingener
 *
 */
public class SiLiftStructuredViewerContentProvider implements ITreeContentProvider {

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
	 * Stores the types of directions that the asymmetric difference can have.
	 * When calculating the asymmetric difference it may be have the direction
	 * from left to right, or light to left. The default value of this variable
	 * is {@link DifferenceDirection#LEFT_TO_RIGHT}.
	 */
	private DifferenceDirection differenceDirection = DifferenceDirection.LEFT_TO_RIGHT;
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
	public SiLiftStructuredViewerContentProvider(DisplayMode displayMode) {
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
	public SiLiftStructuredViewerContentProvider(DisplayMode displayMode, CompareConfiguration compareConfiguration) {
		this.displayMode = displayMode;
	}

	@Override
	public void dispose() {
		LogUtil.log(LogEvent.NOTICE, "Disposing content provider");
		// Release resources if acquired
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		LogUtil.log(LogEvent.WARNING, "The input has changed. Input change cannot be handled yet.");
		// TODO: Handle input change. Maybe reload the data...
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
		// This should be an ICompareInput due to testing
		if (inputElement instanceof ICompareInput) {
			ICompareInput compareInput = (ICompareInput) inputElement;

			try {
				// the two resources to compare
				left = new CompareResource(compareInput.getLeft(), ResourceType.LEFT);
				right = new CompareResource(compareInput.getRight(), ResourceType.RIGHT);
				// check if ancestor is available
				if (compareInput.getAncestor() != null) {
					ancestor = new CompareResource(compareInput.getAncestor(), ResourceType.ANCESTOR);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (CoreException e1) {
				e1.printStackTrace();
			}

			try {
				switch (displayMode) {
				case SYMMETRIC_DIFFERENCE:
					refresh(displayMode);
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
					if (asymmetricDifference == null) {
						refresh(displayMode);
					}
					// get a list of all OperationInvocations, which have not
					// been applied
					List<OperationInvocation> tempElements = patchEngine.getOperationManager().getOperationInvocations(OperationInvocationStatus.INIT);
					// remove OperationInvocations, which are dependencies
					tempElements = filterOperationInvocationsWithDependencies(tempElements);

					return tempElements.toArray();
				}
			} catch (InvalidModelException e) {
				MessageDialogUtil.showInvalidModelDialog();
				LogUtil.log(LogEvent.ERROR, "One of the input models seems to be invalid");
			} catch (NoCorrespondencesException e) {
				MessageDialogUtil.showNoCorrespondencesDialog();
				LogUtil.log(LogEvent.ERROR, "No correspondences could be found between the models");
			} catch (InvalidSettingsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedFeatureLevelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			MessageDialog.open(MessageDialog.ERROR, null, "Invalid input", "The given input cannot be used to derive a difference that can be displayed in the tree viewer", SWT.NONE);
			LogUtil.log(LogEvent.ERROR, "Received invalid input. The given input cannot be used to derive a difference that can be displayed in the tree viewer");
		}

		// If non of the above cases matched, or an exception occurred, an empty
		// list of objects is returned, since there is no data to show
		return new Object[] {};
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

	@Override
	public Object getParent(Object element) {
		if (element instanceof EObject) {
			return ((EObject) element).eContainer();
		} else {
			LogUtil.log(LogEvent.WARNING, String.format("Cannot get parent of non EObject %s", element));
			return null;
		}
	}

	/**
	 * Checks if the given object (as {@link EObject} has children. If it has,
	 * this method will return <code>true</code>, otherwise <code>false</code>.
	 * 
	 * @param element The element to be checked for children
	 * @return <code>true</code> if the given object has children or
	 *         <code>false</code> in case it hasn't, or it is not an
	 *         {@link EObject}
	 */
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof EObject) {
			return !((EObject) element).eContents().isEmpty();
		}
		LogUtil.log(LogEvent.WARNING, String.format("Cannot display a non EObject %s in tree view", element));
		return false;
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
	public void refresh(DisplayMode displayMode) throws InvalidModelException, NoCorrespondencesException, InvalidSettingsException, UnsupportedFeatureLevelException {
		LogUtil.log(LogEvent.NOTICE, String.format("Refreshing contents for display mode %s", displayMode));

		// get documenttype of left model
		// left and right should have the same document type
		String documentType = EMFModelAccess.getCharacteristicDocumentType(left.getResource());

		LiftingSettings settings = null;

		// if we can get the current project, use project settings
		// else use global settings
		if (left.getTypedElement() instanceof IResourceProvider) {
			IProject project = ((IResourceProvider) left.getTypedElement()).getResource().getProject();

			// get settings from the SettingsFactory
			settings = (LiftingSettings) SettingsFactory.getInstance().getLiftingSettingsFactory(documentType, PreferenceUtil.getInstance().getPluginPreferenceStore(), project).getSettings();
		} else {
			// get settings from the SettingsFactory
			settings = (LiftingSettings) SettingsFactory.getInstance().getLiftingSettingsFactory(documentType, PreferenceUtil.getInstance().getPluginPreferenceStore()).getSettings();
		}

		switch (displayMode) {
		case SYMMETRIC_DIFFERENCE:
			symmetricDifference = LiftingFacade.liftTechnicalDifference(left.getResource(), right.getResource(), settings);
			break;
		case ASYMMETRIC_DIFFERENCE:
			if(ancestor != null && ancestor.getResource() != null) {
				asymmetricDifference = getAsymmetricDifference(left.getResource(), right.getResource(), ancestor.getResource(), settings);
			} else {
				asymmetricDifference = getAsymmetricDifference(left.getResource(), right.getResource(), settings);	
			}
			
			// create new PatchEngine
			createPatchEngine();
			break;
		default:
			LogUtil.log(LogEvent.WARNING, "Cannot refresh content. Unsupported display mode " + displayMode);
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
			public void run(IProgressMonitor monitor) {
				try {
					// Actual refresh method that blocks
					monitor.beginTask("Calculating model difference", IProgressMonitor.UNKNOWN);
					refresh(displayMode);
					monitor.done();
				} catch (InvalidModelException e) {
					MessageDialogUtil.showInvalidModelDialog();
					LogUtil.log(LogEvent.ERROR, "One of the input models seems to be invalid");
				} catch (NoCorrespondencesException e) {
					MessageDialogUtil.showNoCorrespondencesDialog();
					LogUtil.log(LogEvent.ERROR, "No correspondences could be found between the models");
				} catch (InvalidSettingsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedFeatureLevelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			// TODO: Handle this exception properly
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO: Handle this exception properly
			e.printStackTrace();
		}
	}

	/**
	 * Returns the {@link AsymmetricDifference} based on the current
	 * {@link DifferenceDirection}. If the direction is
	 * {@link DifferenceDirection#LEFT_TO_RIGHT}, the difference will be
	 * calculated assuming that the left resource is the origin and the right
	 * resource the modified model. If the direction is
	 * {@link DifferenceDirection#RIGHT_TO_LEFT}, the left resource is the
	 * modified and the right resource the origin model.
	 * 
	 * @param left The actual left resource as it was provided to
	 *            {@link #getElements(Object)}
	 * @param right The actual right resource as it was provided to
	 *            {@link #getElements(Object)}
	 * @param settings The settings that configure the lifting process
	 * @return The asymmetric difference whose direction depends on the current
	 *         value of the {@link DifferenceDirection}
	 * @throws InvalidModelException If one of the given models is invalid
	 * @throws NoCorrespondencesException If no correspondences could be found
	 *             between the models
	 */
	private AsymmetricDifference getAsymmetricDifference(Resource left, Resource right, LiftingSettings settings) throws InvalidModelException, NoCorrespondencesException {
		switch (differenceDirection) {
		case LEFT_TO_RIGHT:
			return AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(left, right, settings).getAsymmetric();
		case RIGHT_TO_LEFT:
			return AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(right, left, settings).getAsymmetric();
		default:
			LogUtil.log(LogEvent.WARNING, String.format("Unsupported difference direction %s. Falling back to %s", differenceDirection.toString(), DifferenceDirection.LEFT_TO_RIGHT));
			return AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(left, right, settings).getAsymmetric();
		}
	}
	
	/**
	 * Returns the {@link AsymmetricDifference} based on the current
	 * {@link DifferenceDirection}. If the direction is
	 * {@link DifferenceDirection#LEFT_TO_RIGHT}, the difference will be
	 * calculated assuming that the left resource is the origin and the right
	 * resource the modified model. If the direction is
	 * {@link DifferenceDirection#RIGHT_TO_LEFT}, the left resource is the
	 * modified and the right resource the origin model.
	 * 
	 * @param left The actual left resource as it was provided to
	 *            {@link #getElements(Object)}
	 * @param right The actual right resource as it was provided to
	 *            {@link #getElements(Object)}
	 * @param ancestor The actual ancestor resource as it was provided to
	 *            {@link #getElements(Object)}
	 * @param settings The settings that configure the lifting process
	 * @return The asymmetric difference whose direction depends on the current
	 *         value of the {@link DifferenceDirection}
	 * @throws InvalidModelException If one of the given models is invalid
	 * @throws NoCorrespondencesException If no correspondences could be found
	 *             between the models
	 */
	private AsymmetricDifference getAsymmetricDifference(Resource left, Resource right, Resource ancestor, LiftingSettings settings) throws InvalidModelException, NoCorrespondencesException {
		switch (differenceDirection) {
		case LEFT_TO_RIGHT:
			return AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(ancestor, right, settings).getAsymmetric();
		case RIGHT_TO_LEFT:
			return AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(ancestor, left, settings).getAsymmetric();
		default:
			LogUtil.log(LogEvent.WARNING, String.format("Unsupported difference direction %s. Falling back to %s", differenceDirection.toString(), DifferenceDirection.LEFT_TO_RIGHT));
			return AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(ancestor, right, settings).getAsymmetric();
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
	 * Set DifferenceDirection. Defines direction of the generated asymmetric
	 * difference.
	 * 
	 * @param differenceDirection New DifferenceDirection.
	 */
	public void setDifferenceDirection(DifferenceDirection differenceDirection) {
		this.differenceDirection = differenceDirection;
	}

	/**
	 * Get DifferenceDirection. Defines direction of the generated asymmetric
	 * difference.
	 * 
	 * @return Current DifferenceDirection
	 */
	public DifferenceDirection getDifferenceDirection() {
		return differenceDirection;
	}

	/**
	 * Toggle DifferenceDirection. Switches between LEFT_TO_RIGHT and
	 * RIGHT_TO_LEFT
	 * @throws UnsupportedFeatureLevelException 
	 * @throws InvalidSettingsException 
	 * @throws NoCorrespondencesException 
	 * @throws InvalidModelException 
	 */
	public void switchDifferenceDirection() throws InvalidModelException, NoCorrespondencesException, InvalidSettingsException, UnsupportedFeatureLevelException {
		if (differenceDirection.equals(DifferenceDirection.LEFT_TO_RIGHT)) {
			differenceDirection = DifferenceDirection.RIGHT_TO_LEFT;
			refresh(displayMode);
		} else {
			differenceDirection = DifferenceDirection.LEFT_TO_RIGHT;
			refresh(displayMode);
		}
	}

	/**
	 * Check if DifferenceDIrection is LEFT_TO_RIGHT
	 */
	public boolean isLeftToRight() {
		return differenceDirection.equals(DifferenceDirection.LEFT_TO_RIGHT);
	}

	/**
	 * Applies the specified OperationInvocation after all its predecessors have
	 * been executed.
	 * 
	 * @param operationInvocation OperationInvocation to apply.
	 */
	public void applyOperationInvocation(OperationInvocation operationInvocation) {
		// check if OparationInvocation has any predecessors
		if (operationInvocation.getPredecessors().size() > 0) {
			// check if the predecessors have been executed
			for (OperationInvocation predecessor : operationInvocation.getPredecessors()) {
				// should ignore itself
				// and apply the predecessor if the status is not passed
				if (operationInvocation != predecessor && patchEngine.getOperationManager().getStatusWrapper(predecessor).getStatus() != OperationInvocationStatus.PASSED) {
					// call applyOperationInvocation recursive
					this.applyOperationInvocation(predecessor);
					// check if apply was successful
					if (patchEngine.getOperationManager().getStatusWrapper(predecessor).getStatus() != OperationInvocationStatus.PASSED) {
						Exception ex = patchEngine.getOperationManager().getStatusWrapper(predecessor).getExecutionError();
						System.out.println(ex);
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
	 * 
	 * @throws UnsupportedFeatureLevelException If the requested feature level
	 *             is not supported
	 * @throws InvalidSettingsException If the settings contain invalid values
	 */
	@SuppressWarnings("restriction")
	private void createPatchEngine() throws InvalidSettingsException, UnsupportedFeatureLevelException {
		// decide which ressource to patch
		CompareResource resource = null;
		if (this.isLeftToRight()) {
			resource = this.getLeft();
		} else {
			resource = this.getRight();
		}
		// get document type
		String documentType = EMFModelAccess.getCharacteristicDocumentType(resource.getResource());
		// get settings
		PatchingSettings patchingSettings = null;
		// if we can get the current project, use project settings
		// else use global settings
		if (this.getLeft().getTypedElement() instanceof IResourceProvider) {
			IProject project = ((IResourceProvider) this.getLeft().getTypedElement()).getResource().getProject();
			patchingSettings = (PatchingSettings) SettingsFactory.getInstance().getPatchingSettingsFactory(documentType, PreferenceUtil.getInstance().getPluginPreferenceStore(), project).getSettings();
		} else {
			patchingSettings = (PatchingSettings) SettingsFactory.getInstance().getPatchingSettingsFactory(documentType, PreferenceUtil.getInstance().getPluginPreferenceStore()).getSettings();
		}
		// create PatchEngine
		AsymmetricDifference asymmetricDifference = this.getAsymmetricDifference();
		PatchEngine patchEngine = new PatchEngine(asymmetricDifference, resource.getResource(), patchingSettings);
		
		//init modified detector
		IModifiedDetector modifiedDetector = patchingSettings.getModifiedDetector();
		patchingSettings.setModifiedDetector(modifiedDetector);
		
		if (modifiedDetector != null) {
			try {
				Resource modelB = differenceDirection == DifferenceDirection.LEFT_TO_RIGHT ? 
						right.getResource() : left.getResource();
				modifiedDetector.init(asymmetricDifference.getOriginModel(), modelB,
						patchingSettings.getMatcher(), patchingSettings.getScope());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		// set global variables
		this.patchingSettings = patchingSettings;
		this.patchEngine = patchEngine;
		this.patchedResource = resource;
		if (this.patchedResource.getTypedElement() instanceof ResourceEditableRevision) {
			String uri = ((ResourceEditableRevision) this.patchedResource.getTypedElement()).getFile().getLocationURI().getPath();
			this.patchedResource.getResource().setURI(URI.createFileURI(uri));
		}
	}

}
