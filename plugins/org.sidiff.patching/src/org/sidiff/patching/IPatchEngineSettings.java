package org.sidiff.patching;

import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.arguments.IArgumentManagerSettings;
import org.sidiff.patching.interrupt.IPatchInterruptHandler;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.validation.ValidationMode;

public interface IPatchEngineSettings extends IArgumentManagerSettings {

	IArgumentManager getArgumentManager();
	ITransformationEngine getTransformationEngine();
	ExecutionMode getExecutionMode();
	ValidationMode getValidationMode();
	IPatchInterruptHandler getInterruptHandler();
}
