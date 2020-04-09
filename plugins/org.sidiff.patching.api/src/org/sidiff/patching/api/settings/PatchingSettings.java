package org.sidiff.patching.api.settings;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.IModifiedDetectorSettings;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.matcher.IMatcher;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.IPatchEngineSettings;
import org.sidiff.patching.PatchMode;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.validation.ValidationMode;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

/**
 * @see PatchingSettingsItem
 */
public class PatchingSettings extends LiftingSettings implements IPatchEngineSettings, IModifiedDetectorSettings {

	private static final String PLUGIN_ID = "org.sidiff.patching.api";

	private IArgumentManager argumentManager;
	private IPatchInterruptHandler interruptHandler;
	private ITransformationEngine transformationEngine;
	private IModifiedDetector modifiedDetector;
	private ExecutionMode executionMode;
	private PatchMode patchMode;
	private int minReliability = -1;
	private ValidationMode validationMode = ValidationMode.NO_VALIDATION; // must not be null

	/**
	 * The Symbolic Link Handler for calculating symbolic links.
	 */
	private ISymbolicLinkHandler symbolicLinkHandler;

	/**
	 * Default constructor to create mostly empty settings.
	 * Call initDefaults last, after calling the necessary setters.
	 */
	public PatchingSettings() {
		super();
	}

	/**
	 * @deprecated Use the empty constructor and initDefaults instead.
	 */
	public PatchingSettings(Scope scope, boolean validate, IMatcher matcher, 
			ICandidates candidatesService, ICorrespondences correspondenceService, 
			ITechnicalDifferenceBuilder techBuilder, ISymbolicLinkHandler symbolicLinkHandler,
			IArgumentManager argumentManager, IPatchInterruptHandler interruptHandler,
			ITransformationEngine transformationEngine, IModifiedDetector modifiedDetector, 
			ExecutionMode executionMode, PatchMode patchMode, int minReliability, ValidationMode validationMode) {

		super(scope, validate, matcher, candidatesService, correspondenceService, techBuilder);
		this.argumentManager = argumentManager;
		this.interruptHandler = interruptHandler;
		this.transformationEngine = transformationEngine;
		this.modifiedDetector = modifiedDetector;
		this.executionMode = executionMode;
		this.patchMode = patchMode;
		this.minReliability = minReliability;
		this.validationMode = validationMode == null ? ValidationMode.NO_VALIDATION : validationMode;
	}

	/**
	 * @deprecated Use the empty constructor and initDefaults instead.
	 */
	public PatchingSettings(Scope scope, boolean validate, IMatcher matcher, 
			ICandidates candidatesService, ICorrespondences correspondenceService, 
			ITechnicalDifferenceBuilder techBuilder, ISymbolicLinkHandler symbolicLinkHandler) {

		super(scope, validate, matcher, candidatesService, correspondenceService, techBuilder);
		this.symbolicLinkHandler = symbolicLinkHandler;
	}

	@Override
	public void validate(MultiStatus multiStatus) {
		super.validate(multiStatus);

		if(argumentManager == null) {
			multiStatus.add(new Status(IStatus.ERROR, PLUGIN_ID, 0, "Argument Manager is not set.", null));
		}

		if(interruptHandler == null) {
			multiStatus.add(new Status(IStatus.ERROR, PLUGIN_ID, 0, "Interrupt Handler is not set.", null));
		}

		if(transformationEngine == null) {
			multiStatus.add(new Status(IStatus.ERROR, PLUGIN_ID, 0, "TransformationEngine is not set.", null));
		}

		if(executionMode == null) {
			multiStatus.add(new Status(IStatus.ERROR, PLUGIN_ID, 0, "Execution Mode is not set.", null));
		}

		if(patchMode == null) {
			multiStatus.add(new Status(IStatus.ERROR, PLUGIN_ID, 0, "Patch Mode is not set.", null));
		}
	}

	@Override
	public void initDefaults(Set<String> documentTypes) {
		super.initDefaults(documentTypes);

		if(transformationEngine == null) {
			setTransformationEngine(getDefaultTransformationEngine(documentTypes));
		}
	}

	protected ITransformationEngine getDefaultTransformationEngine(Set<String> documentTypes) {
		return ITransformationEngine.MANAGER.getDefaultExtension(documentTypes)
			.orElseThrow(() -> new IllegalStateException(
				"No Transformation Engine was found for the target document types: " + documentTypes));
	}

	@Override
	public String toString() {
		return super.toString() + "\n"
			+ "PatchingSettings["
			+ "Argument Manager: " + toString(getArgumentManager()) + ", "
			+ "Interrupt Handler: " + toString(getInterruptHandler()) + ", "
			+ "Transformation Engine: " + toString(getTransformationEngine()) + ", "
			+ "Modified Detector: " + toString(getModifiedDetector()) + ", "
			+ "Validation Mode: " + getValidationMode() + ", "
			+ "Execution Mode: " + getExecutionMode() + ", "
			+ "Patch Mode: " + getPatchMode() + ", "
			+ "Minimum Reliability: " + (getMinReliability() > -1 ? getMinReliability() : "N/A") + ", "
			+ "Symbolic Link Handler: " + toString(getSymbolicLinkHandler()) + "]";
	}

	// ---------- Getter and Setter Methods----------

	/**
	 * @return
	 * @see PatchingSettingsItem#ARG_MANAGER
	 */
	@Override
	public IArgumentManager getArgumentManager() {
		return argumentManager;
	}

	/**
	 * 
	 * @param argumentManager
	 * @see PatchingSettingsItem#ARG_MANAGER
	 */
	public void setArgumentManager(IArgumentManager argumentManager) {
		if (!Objects.equals(this.argumentManager, argumentManager)) {
			this.argumentManager = argumentManager;
			notifyListeners(PatchingSettingsItem.ARG_MANAGER);
		}
	}

	/**
	 * @return
	 * @see PatchingSettingsItem#INTERRUPT_HANDLER
	 */
	@Override
	public IPatchInterruptHandler getInterruptHandler() {
		return interruptHandler;
	}

	/**
	 * @param interruptHandler
	 * @see PatchingSettingsItem#INTERRUPT_HANDLER
	 */
	public void setInterruptHandler(IPatchInterruptHandler interruptHandler) {
		if (!Objects.equals(this.interruptHandler, interruptHandler)) {
			this.interruptHandler = interruptHandler;
			notifyListeners(PatchingSettingsItem.INTERRUPT_HANDLER);
		}
	}

	/**
	 * @return
	 * @see PatchingSettingsItem#TRANSFORMATION_ENGINE
	 */
	@Override
	public ITransformationEngine getTransformationEngine() {
		return transformationEngine;
	}

	/**
	 * @param transformationEngine
	 * @see PatchingSettingsItem#TRANSFORMATION_ENGINE
	 */
	public void setTransformationEngine(ITransformationEngine transformationEngine) {
		if (!Objects.equals(this.transformationEngine, transformationEngine)) {
			this.transformationEngine = transformationEngine;
			notifyListeners(PatchingSettingsItem.TRANSFORMATION_ENGINE);
		}
	}

	/**
	 * @return
	 * @see PatchingSettingsItem#MODIFIED_DETECTOR
	 */
	@Override
	public IModifiedDetector getModifiedDetector() {
		return modifiedDetector;
	}

	/**
	 * @param modifiedDetector
	 * @see PatchingSettingsItem#MODIFIED_DETECTOR
	 */
	public void setModifiedDetector(IModifiedDetector modifiedDetector) {
		if (!Objects.equals(this.modifiedDetector, modifiedDetector)) {
			this.modifiedDetector = modifiedDetector;
			notifyListeners(PatchingSettingsItem.MODIFIED_DETECTOR);
		}
	}

	/**
	 * @return
	 * @see PatchingSettingsItem#EXEC_MODE
	 */
	@Override
	public ExecutionMode getExecutionMode() {
		return executionMode;
	}

	/**
	 * @param executionMode
	 * @see PatchingSettingsItem#EXEC_MODE
	 */
	public void setExecutionMode(ExecutionMode executionMode) {
		if (this.executionMode != executionMode) {
			this.executionMode = executionMode;
			notifyListeners(PatchingSettingsItem.EXEC_MODE);
		}
	}

	/**
	 * @return
	 * @see PatchingSettingsItem#PATCH_MODE
	 */
	@Override
	public PatchMode getPatchMode() {
		return patchMode;
	}

	/**
	 * @param patchMode
	 * @see PatchingSettingsItem#PATCH_MODE
	 */
	public void setPatchMode(PatchMode patchMode) {
		if (this.patchMode != patchMode) {
			this.patchMode = patchMode;
			notifyListeners(PatchingSettingsItem.PATCH_MODE);
		}
	}

	/**
	 * @return
	 * @see PatchingSettingsItem#RELIABILITY
	 */
	@Override
	public int getMinReliability() {
		return minReliability;
	}

	/**
	 * @param minReliability
	 * @see PatchingSettingsItem#RELIABILITY
	 */
	public void setMinReliability(int minReliability) {
		if (this.minReliability != minReliability) {
			this.minReliability = minReliability;
			notifyListeners(PatchingSettingsItem.RELIABILITY);
		}
	}

	/**
	 * @return
	 * @see PatchingSettingsItem#VALIDATION_MODE
	 */
	@Override
	public ValidationMode getValidationMode() {
		return validationMode;
	}

	/**
	 * @param validationMode
	 * @see PatchingSettingsItem#VALIDATION_MODE
	 */
	public void setValidationMode(ValidationMode validationMode) {
		if (this.validationMode != validationMode) {
			this.validationMode = Objects.requireNonNull(validationMode, "validationMode must not be null");
			notifyListeners(PatchingSettingsItem.VALIDATION_MODE);
		}
	}

	/**
	 * @return The Symbolic Link Handler for symbolic link generation.
	 * @see PatchingSettingsItem#SYMBOLIC_LINK_HANDLER
	 */
	@Override
	public ISymbolicLinkHandler getSymbolicLinkHandler() {
		return symbolicLinkHandler;
	}

	/**
	 * @param symbolicLinkHandler The Symbolic Link Handler for symbolic link generation.
	 * @see PatchingSettingsItem#SYMBOLIC_LINK_HANDLER
	 */
	public void setSymbolicLinkHandler(ISymbolicLinkHandler symbolicLinkHandler) {
		if (!Objects.equals(this.symbolicLinkHandler, symbolicLinkHandler)) {
			this.symbolicLinkHandler = symbolicLinkHandler;
			notifyListeners(PatchingSettingsItem.SYMBOLIC_LINK_HANDLER);
		}
	}

	/**
	 * @return <code>true</code>, if the {@link #symbolicLinkHandler} is set, <code>false</code> otherwise.
	 * @see PatchingSettingsItem#SYMBOLIC_LINK_HANDLER
	 */
	public boolean useSymbolicLinks() {
		return symbolicLinkHandler != null;
	}


	public static PatchingSettings defaultSettings() {
		return defaultSettings(Collections.emptySet());
	}

	public static PatchingSettings defaultSettings(Set<String> documentTypes) {
		PatchingSettings settings = new PatchingSettings();
		settings.initDefaults(documentTypes);
		return settings;
	}
}
