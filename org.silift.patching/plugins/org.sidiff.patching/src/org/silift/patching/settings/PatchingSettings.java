package org.silift.patching.settings;

import java.util.Set;

import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.silift.common.util.emf.Scope;
import org.silift.difference.lifting.settings.Settings;
import org.silift.difference.lifting.settings.SettingsItem;
import org.silift.modifieddetector.IModifiedDetector;

public class PatchingSettings extends Settings {

	private ValidationMode validationMode;
	private int minReliability;
	private ExecutionMode executionMode;
	private PatchMode patchMode;
	private IArgumentManager argumentManager;
	private IPatchInterruptHandler interruptHandler;
	private ITransformationEngine transformationEngine;
	private IModifiedDetector modifiedDetector;
	
	
	public PatchingSettings(ValidationMode validationMode, int minReliability,
			ExecutionMode executionMode, PatchMode patchMode,
			IArgumentManager argumentManager,
			IPatchInterruptHandler interruptHandler,
			ITransformationEngine transformationEngine,
			IModifiedDetector modifiedDetector) {
		super();
		this.validationMode = validationMode;
		this.minReliability = minReliability;
		this.executionMode = executionMode;
		this.patchMode = patchMode;
		this.argumentManager = argumentManager;
		this.interruptHandler = interruptHandler;
		this.transformationEngine = transformationEngine;
		this.modifiedDetector = modifiedDetector;
	}



	public PatchingSettings(Scope scope, Set<IRuleBase> ruleBases,
			IMatcher matcher, ITechnicalDifferenceBuilder techBuilder,
			ValidationMode validationMode, int minReliability, ExecutionMode executionMode,
			PatchMode patchMode) {
		super(scope, ruleBases, matcher, techBuilder);
		this.validationMode = validationMode;
		this.minReliability = minReliability;
		this.executionMode = executionMode;
		this.patchMode = patchMode;
	}



	public PatchingSettings() {
	}



	public ValidationMode getValidationMode() {
		return validationMode;
	}



	public void setValidationMode(ValidationMode validationMode) {
		if(this.validationMode == null || !this.validationMode.equals(validationMode)){
			this.validationMode = validationMode;
			notifyListeners(SettingsItem.VALIDATION_MODE);
		}
	}



	public int getMinReliability() {
		return minReliability;
	}



	public void setMinReliability(int minReliability) {
		if(this.minReliability != minReliability){
			this.minReliability = minReliability;
			notifyListeners(SettingsItem.RELIABILITY);
		}
	}

	public enum ValidationMode {
		NO_VALIDATION, MODEL_VALIDATION, ITERATIVE_VALIDATION
	}

	public ExecutionMode getExecutionMode() {
		return executionMode;
	}


	public void setExecutionMode(ExecutionMode executionMode) {
		if(this.executionMode == null || !this.executionMode.equals(executionMode)){
			this.executionMode = executionMode;
			notifyListeners(SettingsItem.EXEC_MODE);
		}
	}


	public PatchMode getPatchMode() {
		return patchMode;
	}


	public void setPatchMode(PatchMode patchMode) {
		if(this.patchMode == null || !this.patchMode.equals(patchMode)){
			this.patchMode = patchMode;
			notifyListeners(SettingsItem.PATCH_MODE);
		}
	}
	
	public IArgumentManager getArgumentManager() {
		return argumentManager;
	}



	public void setArgumentManager(IArgumentManager argumentManager) {
		if(this.argumentManager == null || !this.argumentManager.equals(argumentManager)){
			this.argumentManager = argumentManager;
			notifyListeners(SettingsItem.ARG_MANAGER);
		}
	}



	public IPatchInterruptHandler getInterruptHandler() {
		return interruptHandler;
	}



	public void setInterruptHandler(IPatchInterruptHandler interruptHandler) {
		if(this.interruptHandler == null || !this.interruptHandler.equals(interruptHandler)){
			this.interruptHandler = interruptHandler;
			notifyListeners(SettingsItem.INTERRUPT_HANDLER);
		}
	}



	public ITransformationEngine getTransformationEngine() {
		return transformationEngine;
	}



	public void setTransformationEngine(ITransformationEngine transformationEngine) {
		if(this.transformationEngine == null || !this.transformationEngine.equals(transformationEngine)){
			this.transformationEngine = transformationEngine;
			notifyListeners(SettingsItem.TRANSFORMATION_ENGINE);
		}
	}



	public IModifiedDetector getModifiedDetector() {
		return modifiedDetector;
	}



	public void setModifiedDetector(IModifiedDetector modifiedDetector) {
		if(this.modifiedDetector == null || !this.modifiedDetector.equals(modifiedDetector)){
			this.modifiedDetector = modifiedDetector;
			notifyListeners(SettingsItem.MODIFIED_DETECTOR);
		}
	}



	public String toString(){
		String result = super.toString();
		result += "Validation Mode: " + validationMode + "\n";
		result += "Execution Mode: " + executionMode + "\n";
		result += "Patch Mode: " + patchMode + "\n";
		result += "minimum Reliability: " + (minReliability>-1? minReliability:"N/A") + "\n";
		return result;
	}
	
}
