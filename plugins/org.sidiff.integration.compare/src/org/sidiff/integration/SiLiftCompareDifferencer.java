package org.sidiff.integration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.sidiff.common.emf.doctype.util.EMFDocumentTypeUtil;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.ui.util.MessageDialogUtil;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.api.util.Difference;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.integration.preferences.settingsadapter.SettingsAdapterUtil;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;
import org.sidiff.integration.remote.CompareResource;
import org.sidiff.integration.remote.CompareResource.Side;
import org.sidiff.matching.input.InputModels;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.PatchMode;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.sidiff.patching.ui.view.ReportView;

/**
 * 
 * @author Robert Müller
 *
 */
public class SiLiftCompareDifferencer {

	private CompareResource left;
	private CompareResource right;
	private CompareResource ancestor;
	private CompareResource modifiedLeft;
	private CompareResource modifiedRight;

	private SiLiftCompareConfiguration config;
	private IPropertyChangeListener propertyChangeListener;

	private PatchEngine patchEngine;
	private Difference difference;

	private List<IModelViewerAdapter> modelViewerAdapters;
	private List<IDifferenceViewerAdapter> differenceViewerAdapters;

	public SiLiftCompareDifferencer(SiLiftCompareConfiguration config) {
		this.modelViewerAdapters = new LinkedList<>();
		this.differenceViewerAdapters = new LinkedList<>();
		this.propertyChangeListener = createPropertyChangeListener();
		this.setConfig(config);
	}

	protected IPropertyChangeListener createPropertyChangeListener() {
		return new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if(event.getProperty() == SiLiftCompareConfiguration.MIRRORED) {
					MessageDialogUtil.showProgressDialog(new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
							monitor.beginTask("Calculating model difference", IProgressMonitor.UNKNOWN);
							try {
								recalculateDifferences();
								notifyChangeResource(Side.LEFT, getLeft());
								notifyChangeResource(Side.RIGHT, getRight());
							} catch (InvalidModelException | NoCorrespondencesException | CoreException e) {
								throw new InvocationTargetException(e);
							} finally {
								monitor.done();
							}
						}
					});
				}
			}
		};
	}

	/**
	 * Recalculates the model differences.
	 * @throws InvalidModelException If one of the given models is invalid
	 * @throws NoCorrespondencesException If no correspondences could be found between the models
	 * @throws CoreException
	 */
	public void recalculateDifferences() throws InvalidModelException, NoCorrespondencesException, CoreException {
		CompareResource left = getLeft();
		CompareResource right = getRight();
		if(left.getResource() == null || right.getResource() == null) {
			throw new InvalidModelException("Resource could not be loaded for one or more inputs.");
		}

		InputModels inputModels = new InputModels(left.getResource(), right.getResource());
		if(!inputModels.haveSameDocumentType()) {
			throw new InvalidModelException("Document types of input models are not matching.");
		}

		// if we can get the current project, use project settings
		// else use global settings
		IResource platformResource = left.getPlatformResource();
		LiftingSettings settings = new LiftingSettings();
		if (platformResource != null && PreferenceStoreUtil.useSpecificSettings(platformResource.getProject())) {
			SettingsAdapterUtil.adaptSettingsProject(settings, platformResource.getProject(),
					inputModels.getDocumentTypes(), Collections.<Enum<?>>emptySet());
		} else {
			SettingsAdapterUtil.adaptSettingsGlobal(settings, inputModels.getDocumentTypes(), 
					Collections.<Enum<?>>emptySet());
		}

		difference = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(
				left.getResource(), right.getResource(), settings);

		createPatchEngine();
		initPatchReportView(patchEngine);

		notifyRefreshDifference();
	}

	/**
	 * Creates a new PatchEngine and stores result in the global variable. The
	 * used PatchingSettings are stored in its global variable, too. Decides
	 * which resource will be patched based on the direction of the asymmetric
	 * difference.
	 * 
	 * Should be called when: -a new asymmetric difference has been calculated
	 * @throws CoreException 
	 */
	private void createPatchEngine() throws CoreException {
		PatchingSettings patchingSettings = createPatchingSettings();

		// init modified detector
		if(getAncestor().getResource() != null) {
			IModifiedDetector modifiedDetector = patchingSettings.getModifiedDetector();
			if (modifiedDetector != null) {
				try {
					modifiedDetector.init(getAncestor().getResource(), getModifiedLeft().getResource(),
							patchingSettings.getMatcher(), patchingSettings.getScope());
				} catch (IOException e) {
					Activator.logError("SiLiftCompareDifferencer could not initialize modified detector", e);
				}
			}
		} else {
			patchingSettings.setModifiedDetector(null);
		}
		
		patchEngine = new PatchEngine(getAsymmetricDifference(), getModifiedLeft().getResource(), patchingSettings);
	}

	private PatchingSettings createPatchingSettings() throws CoreException {
		// get document type
		CompareResource left = getLeft();
		Set<String> documentTypes = new HashSet<>(EMFDocumentTypeUtil.resolve(left.getResource()));

		// if we can get the current project, use project settings
		// else use global settings
		IResource platformResource = left.getPlatformResource();
		if(platformResource == null) {
			platformResource = getRight().getPlatformResource();
		}
		PatchingSettings patchingSettings = new PatchingSettings();
		if (platformResource != null && PreferenceStoreUtil.useSpecificSettings(platformResource.getProject())) {
			SettingsAdapterUtil.adaptSettingsProject(patchingSettings, platformResource.getProject(),
					documentTypes, Collections.<Enum<?>>emptySet());
		} else {
			SettingsAdapterUtil.adaptSettingsGlobal(patchingSettings, documentTypes, 
					Collections.<Enum<?>>emptySet());
		}
		patchingSettings.setExecutionMode(ExecutionMode.INTERACTIVE);
		patchingSettings.setPatchMode(ancestor.getResource() == null ? PatchMode.PATCHING : PatchMode.MERGING);

		// Use interactive argument manager
		IArgumentManager argumentManager = IArgumentManager.MANAGER.getArgumentManager(getAsymmetricDifference(),
				left.getResource(), patchingSettings, patchingSettings.getExecutionMode());
		if(argumentManager == null) {
			throw new RuntimeException("No suitable argument manager found");
		}
		patchingSettings.setArgumentManager(argumentManager);

		// Dialog Patch interrupt handler
		patchingSettings.setInterruptHandler(new DialogPatchInterruptHandler());

		return patchingSettings;
	}

	private static void initPatchReportView(final PatchEngine patchEngine) {
		Display.getCurrent().asyncExec(() -> {
			try {
				ReportView reportView = (ReportView)PlatformUI
						.getWorkbench()
						.getActiveWorkbenchWindow()
						.getActivePage()
						.showView(ReportView.ID);
				reportView.setPatchReportManager(patchEngine.getPatchReportManager());
			} catch (PartInitException e) {
				Activator.logWarning("Could not initialize ReportView", e);
			}
		});
	}

	public void applyPatch() {
		if(patchEngine == null) {
			throw new IllegalStateException("Patch engine is not initialized");
		}
		patchEngine.applyPatch(false);
		notifyRefreshDifference();
		notifyDirtyResource(Side.LEFT, true);
		notifyRefreshResource(Side.LEFT);
	}

	/**
	 * Applies the specified OperationInvocation after all its predecessors have
	 * been executed.
	 * 
	 * @param operationInvocation OperationInvocation to apply.
	 * @throws Exception if the execution of the operation failed
	 */
	public void applyOperation(OperationInvocation operationInvocation) throws Exception {
		if(patchEngine == null) {
			throw new IllegalStateException("Patch engine is not initialized");
		}
		try {
			applyOperationImpl(operationInvocation);
		} finally {
			notifyRefreshDifference();
			notifyDirtyResource(Side.LEFT, true);
			notifyRefreshResource(Side.LEFT);
		}
	}

	protected void applyOperationImpl(OperationInvocation operationInvocation) throws Exception {
		// check if OperationInvocation has any predecessors
		if (operationInvocation.getPredecessors().size() > 0) {
			// check if the predecessors have been executed
			for (OperationInvocation predecessor : operationInvocation.getPredecessors()) {
				// should ignore itself
				// and apply the predecessor if the status is not passed
				if (operationInvocation != predecessor
						&& patchEngine.getOperationManager().getStatusWrapper(predecessor).getStatus() != OperationInvocationStatus.PASSED) {
					applyOperationImpl(predecessor);
				}
			}
		}

		// apply OperationInvocation
		patchEngine.apply(operationInvocation, true);

		// check if apply was successful
		OperationInvocationWrapper wrapper = patchEngine.getOperationManager().getStatusWrapper(operationInvocation);
		if (wrapper.getStatus() != OperationInvocationStatus.PASSED) {
			Exception e = wrapper.getExecutionError();
			if(e == null)
				throw new RuntimeException("Operation could not be executed: " + wrapper.getStatus());
			throw e;
		}
	}

	public void ignoreOperation(OperationInvocation operationInvocation) {
		if(patchEngine == null) {
			throw new IllegalStateException("Patch engine is not initialized");
		}
		patchEngine.ignore(operationInvocation);
		notifyRefreshDifference();
	}

	public void unignoreOperation(OperationInvocation operationInvocation) {
		if(patchEngine == null) {
			throw new IllegalStateException("Patch engine is not initialized");
		}
		patchEngine.unignore(operationInvocation);
		notifyRefreshDifference();
	}

	public void revertOperation(OperationInvocation operationInvocation) throws Exception {
		if(patchEngine == null) {
			throw new IllegalStateException("Patch engine is not initialized");
		}
		try {
			patchEngine.revert(operationInvocation);
			OperationInvocationWrapper wrapper = patchEngine.getOperationManager().getStatusWrapper(operationInvocation);
			if(wrapper.getStatus() != OperationInvocationStatus.REVERTED) {
				Exception e = wrapper.getExecutionError();
				if(e == null)
					throw new RuntimeException("Operation could not be reverted: " + wrapper.getStatus());
				throw e;
			}
		} finally {
			notifyRefreshDifference();
			notifyDirtyResource(Side.LEFT, true);
			notifyRefreshResource(Side.LEFT);
		}
	}

	public SymmetricDifference getSymmetricDifference() {
		return difference == null ? null : difference.getSymmetric();
	}

	public AsymmetricDifference getAsymmetricDifference() {
		return difference == null ? null : difference.getAsymmetric();
	}

	/**
	 * The PatchEngine is required to apply OperationInvocations. May be null.
	 * 
	 * @return The PatchEngine currently in use.
	 */
	public PatchEngine getPatchEngine() {
		return patchEngine;
	}

	public SiLiftCompareConfiguration getConfig() {
		return config;
	}

	public void setConfig(SiLiftCompareConfiguration config) {
		if(this.config != null) {
			this.config.removePropertyChangeListener(propertyChangeListener);
		}
		this.config = config;
		this.config.addPropertyChangeListener(propertyChangeListener);
	}

	public void loadCompareInput(ICompareInput compareInput) throws IOException, CoreException {
		this.left = CompareResource.load(compareInput.getLeft());
		this.modifiedLeft = left.mutateEcoreResource();

		this.right = CompareResource.load(compareInput.getRight());
		this.modifiedRight = right.mutateEcoreResource();

		this.ancestor = CompareResource.load(compareInput.getAncestor());

		notifyChangeResource(Side.LEFT, getModifiedLeft());
		notifyChangeResource(Side.RIGHT, getModifiedRight());
	}

	public CompareResource getLeft() {
		if(config.isMirrored())
			return right;
		return left;
	}

	public CompareResource getRight() {
		if(config.isMirrored())
			return left;
		return right;
	}

	public CompareResource getAncestor() {
		return ancestor;
	}

	public CompareResource getModifiedLeft() {
		if(config.isMirrored())
			return modifiedRight;
		return modifiedLeft;
	}

	public CompareResource getModifiedRight() {
		if(config.isMirrored())
			return modifiedLeft;
		return modifiedRight;
	}


	protected void notifyRefreshDifference() {
		for(IDifferenceViewerAdapter l : differenceViewerAdapters) {
			l.onRefresh();
		}
	}

	public void addDifferenceViewerAdapter(IDifferenceViewerAdapter listener) {
		if(!differenceViewerAdapters.contains(listener)) {
			differenceViewerAdapters.add(listener);
		}
	}

	public void removeDifferenceViewerAdapter(IDifferenceViewerAdapter listener) {
		differenceViewerAdapters.remove(listener);
	}
	
	public interface IDifferenceViewerAdapter {

		void onRefresh();

	}


	protected void notifyChangeResource(Side side, CompareResource compRes) {
		for(IModelViewerAdapter l : modelViewerAdapters) {
			l.onChange(side, compRes);
		}
	}

	protected void notifyRefreshResource(Side side) {
		for(IModelViewerAdapter l : modelViewerAdapters) {
			l.onRefresh(side);
		}
	}

	protected void notifyDirtyResource(Side side, boolean dirty) {
		for(IModelViewerAdapter l : modelViewerAdapters) {
			l.setDirty(side, dirty);
		}
	}

	public void addModelViewerAdapter(IModelViewerAdapter listener) {
		if(!modelViewerAdapters.contains(listener)) {
			modelViewerAdapters.add(listener);
		}
	}

	public void removeModelViewerAdapter(IModelViewerAdapter listener) {
		modelViewerAdapters.remove(listener);
	}

	public interface IModelViewerAdapter {

		void onChange(Side side, CompareResource compRes);
		void onRefresh(Side side);
		void setDirty(Side side, boolean dirty);

	}
}
