package org.sidiff.patching.api.settings;

import java.util.Objects;
import java.util.Set;

import org.eclipse.core.runtime.MultiStatus;
import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
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
public class PatchingSettings extends LiftingSettings implements IPatchEngineSettings {

	/**
	 * 
	 */
	private IArgumentManager argumentManager;

	/**
	 * 
	 */
	private IPatchInterruptHandler interruptHandler;

	/**
	 * 
	 */
	private ITransformationEngine transformationEngine;

	/**
	 * 
	 */
	private IModifiedDetector modifiedDetector;

	/**
	 * 
	 */
	private ExecutionMode executionMode;

	/**
	 * 
	 */
	private PatchMode patchMode;

	/**
	 * 
	 */
	private int minReliability = -1;

	/**
	 * 
	 */
	private ValidationMode validationMode;

	/**
	 * The Symbolic Link Handler for calculating symbolic links.
	 */
	private ISymbolicLinkHandler symbolicLinkHandler;

	public PatchingSettings() {
		super();
	}

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
		this.validationMode = validationMode;
	}

	public PatchingSettings(Scope scope, boolean validate, IMatcher matcher, 
			ICandidates candidatesService, ICorrespondences correspondenceService, 
			ITechnicalDifferenceBuilder techBuilder, ISymbolicLinkHandler symbolicLinkHandler) {

		super(scope, validate, matcher, candidatesService, correspondenceService, techBuilder);
		this.symbolicLinkHandler = symbolicLinkHandler;
	}

	@Override
	public void validate(MultiStatus multiStatus) {
		super.validate(multiStatus);
		// TODO CPietsch (2016-02-08)
	}

	@Override
	public void initDefaults(Set<String> documentTypes) {
		super.initDefaults(documentTypes);
		if(transformationEngine == null) {
			transformationEngine = getDefaultTransformationEngine(documentTypes);
		}
	}

	protected ITransformationEngine getDefaultTransformationEngine(Set<String> documentTypes) {
		return ITransformationEngine.MANAGER.getDefaultExtension(documentTypes)
			.orElseThrow(() -> new IllegalStateException(
				"No Transformation Engine was found for the target document types: " + documentTypes));
	}

	@Override
	public String toString() {
		return new StringBuilder(super.toString()).append("\n")
			.append("PatchingSettings[")
			.append("Argument Manager: ").append(getArgumentManager() != null ? getArgumentManager().getName() : "none").append(", ")
			.append("Interrupt Handler: ").append(getInterruptHandler()).append(", ")
			.append("Transformation Engine: ").append(getTransformationEngine() != null ? getTransformationEngine().getName() : "none").append(", ")
			.append("Modified Detector: ").append(getModifiedDetector() != null ? getModifiedDetector().getName() : "none").append(", ")
			.append("Validation Mode: ").append(getValidationMode()).append(", ")
			.append("Execution Mode: ").append(getExecutionMode()).append(", ")
			.append("Patch Mode: ").append(getPatchMode()).append(", ")
			.append("Minimum Reliability: ").append(getMinReliability() > -1 ? getMinReliability() : "N/A").append(", ")
			.append("Symbolic Link Handler: ").append(getSymbolicLinkHandler() != null ? getSymbolicLinkHandler().getName() : "none")
			.append("]")
			.toString();
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
			this.validationMode = validationMode;
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
		PatchingSettings settings = new PatchingSettings();
		settings.initDefaults();
		return settings;
	}
	
	public static PatchingSettings defaultSettings(Set<String> documentTypes) {
		PatchingSettings settings = new PatchingSettings();
		settings.initDefaults(documentTypes);
		return settings;
	}
}
