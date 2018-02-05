package org.sidiff.patching.settings;

import java.util.Set;

import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.matcher.IMatcher;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

public class PatchingSettings extends LiftingSettings {

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
	private int minReliability;
	
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
	
	public PatchingSettings(Set<String> documentTypes) {
		super(documentTypes);
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
		this.symbolicLinkHandler = symbolicLinkHandler;
	}

	public PatchingSettings(Scope scope, boolean validate, IMatcher matcher, 
			ICandidates candidatesService, ICorrespondences correspondenceService, 
			ITechnicalDifferenceBuilder techBuilder, ISymbolicLinkHandler symbolicLinkHandler) {
		
		super(scope, validate, matcher, candidatesService, correspondenceService, techBuilder);
		this.symbolicLinkHandler = symbolicLinkHandler;
	}

	@Override
	public boolean validateSettings() {
		// TODO CPietsch (2016-02-08)
		return super.validateSettings();
	}

	public String toString() {
		String result = super.toString();
		result += "Validation Mode: " + validationMode + "\n";
		result += "Execution Mode: " + executionMode + "\n";
		result += "Patch Mode: " + patchMode + "\n";
		result += "minimum Reliability: " + (minReliability > -1 ? minReliability : "N/A") + "\n";
		return result;
	}
	
	// ---------- Getter and Setter Methods----------
	
	public IArgumentManager getArgumentManager() {
		return argumentManager;
	}

	public void setArgumentManager(IArgumentManager argumentManager) {
		if (this.argumentManager == null || !this.argumentManager.equals(argumentManager)) {
			this.argumentManager = argumentManager;
			notifyListeners(PatchingSettingsItem.ARG_MANAGER);
		}
	}

	public IPatchInterruptHandler getInterruptHandler() {
		return interruptHandler;
	}

	public void setInterruptHandler(IPatchInterruptHandler interruptHandler) {
		if (this.interruptHandler == null || !this.interruptHandler.equals(interruptHandler)) {
			this.interruptHandler = interruptHandler;
			notifyListeners(PatchingSettingsItem.INTERRUPT_HANDLER);
		}
	}

	public ITransformationEngine getTransformationEngine() {
		return transformationEngine;
	}

	public void setTransformationEngine(ITransformationEngine transformationEngine) {
		if (this.transformationEngine == null || !this.transformationEngine.equals(transformationEngine)) {
			this.transformationEngine = transformationEngine;
			notifyListeners(PatchingSettingsItem.TRANSFORMATION_ENGINE);
		}
	}

	public IModifiedDetector getModifiedDetector() {
		return modifiedDetector;
	}

	public void setModifiedDetector(IModifiedDetector modifiedDetector) {
		if (this.modifiedDetector == null || !this.modifiedDetector.equals(modifiedDetector)) {
			this.modifiedDetector = modifiedDetector;
			notifyListeners(PatchingSettingsItem.MODIFIED_DETECTOR);
		}
	}

	public ExecutionMode getExecutionMode() {
		return executionMode;
	}

	public void setExecutionMode(ExecutionMode executionMode) {
		if (this.executionMode == null || !this.executionMode.equals(executionMode)) {
			this.executionMode = executionMode;
			notifyListeners(PatchingSettingsItem.EXEC_MODE);
		}
	}
	
	public PatchMode getPatchMode() {
		return patchMode;
	}

	public void setPatchMode(PatchMode patchMode) {
		if (this.patchMode == null || !this.patchMode.equals(patchMode)) {
			this.patchMode = patchMode;
			notifyListeners(PatchingSettingsItem.PATCH_MODE);
		}
	}
	
	public int getMinReliability() {
		return minReliability;
	}

	public void setMinReliability(int minReliability) {
		if (this.minReliability != minReliability) {
			this.minReliability = minReliability;
			notifyListeners(PatchingSettingsItem.RELIABILITY);
		}
	}
	
	public ValidationMode getValidationMode() {
		return validationMode;
	}

	public void setValidationMode(ValidationMode validationMode) {
		if (this.validationMode == null || !this.validationMode.equals(validationMode)) {
			this.validationMode = validationMode;
			notifyListeners(PatchingSettingsItem.VALIDATION_MODE);
		}
	}
	
	/**
	 * 
	 * @return The Symbolic Link Handler for symbolic link generation.
	 */
	public ISymbolicLinkHandler getSymbolicLinkHandler() {
		return symbolicLinkHandler;
	}

	/**
	 * 
	 * @param symbolicLinkHandler
	 *            The Symbolic Link Handler for symbolic link generation.
	 */
	public void setSymbolicLinkHandler(ISymbolicLinkHandler symbolicLinkHandler) {
		if (symbolicLinkHandler == null && this.symbolicLinkHandler != null) {
			this.symbolicLinkHandler = null;
			this.notifyListeners(LiftingSettingsItem.SYMBOLIC_LINK_HANDLER);
		} else if (this.symbolicLinkHandler == null
				|| !this.symbolicLinkHandler.getName().equals(symbolicLinkHandler.getName())) {
			this.symbolicLinkHandler = symbolicLinkHandler;
			this.notifyListeners(LiftingSettingsItem.SYMBOLIC_LINK_HANDLER);
		}
	}
	
	/**
	 * 
	 * @return <code>true</code>, if the {@link #symbolicLinkHandler} is set
	 *         otherwise <code>false</code>.
	 */
	public boolean useSymbolicLinks() {
		if (symbolicLinkHandler != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public enum ValidationMode {
		NO_VALIDATION, MODEL_VALIDATION, ITERATIVE_VALIDATION
	}
}
