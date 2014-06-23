package org.sidiff.difference.lifting.settings;

import java.util.Set;

import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.silift.common.util.emf.Scope;

public class LiftingSettings extends Settings {
	
	/**
	 * Validation of the input models. (Default: False)
	 */
	private boolean validate = false;

	/**
	 * Triggering of the Recognition-Engine pipeline.
	 * 
	 * @see LiftingSettings.RecognitionEngineMode (Default: Post processed operation detection)
	 */
	private RecognitionEngineMode recognitionEngineMode = RecognitionEngineMode.LIFTING_AND_POST_PROCESSING;
	
	/**
	 * <p>
	 * Triggering of the Recognition-Engine pipeline.
	 * </p>
	 * <ul>
	 * <li><code>NO_LIFTING</code>: Disable the operation detection (Lifting).</li>
	 * <li><code>LIFTING</code>: Enable the operation detection (Lifting).</li>
	 * <li><code>LIFTING_AND_POST_PROCESSING:</code> Post processed (remove
	 * overlapping Semantic-Change-Sets) operation detection (Lifting).</li>
	 * </ul>
	 */
	public enum RecognitionEngineMode {
		
		/**
		 * Disable the operation detection (Lifting).
		 */
		NO_LIFTING, 
		
		/**
		 * Enable the operation detection (Lifting).
		 */
		LIFTING,
		
		/**
		 * Post processed (remove overlapping Semantic-Change-Sets) operation detection (Lifting).
		 */
		LIFTING_AND_POST_PROCESSING
	}
	
	/**
	 * Setup the lifting settings.
	 * 
	 * @param documentType
	 *            The document type of the models.
	 */
	public LiftingSettings(String documentType) {
		super(documentType);
	}

	/**
	 * Setup the lifting settings.
	 * 
	 * @param scope
	 *            {@link Settings#setScope(Scope)}
	 * @param matcher
	 *            {@link Settings#setMatcher(IMatcher)}
	 * @param techBuilder
	 *            {@link Settings#setTechBuilder(ITechnicalDifferenceBuilder)}
	 * @param ruleBases
	 *            {@link Settings#setRuleBases(Set)}
	 * @param validate
	 *            {@link LiftingSettings#setValidate(boolean)}
	 * @param recognitionEngine
	 *            {@link LiftingSettings#setRecognitionEngineMode(RecognitionEngineMode)}
	 */
	public LiftingSettings(Scope scope, IMatcher matcher,
			ITechnicalDifferenceBuilder techBuilder, Set<IRuleBase> ruleBases,
			boolean validate, RecognitionEngineMode recognitionEngine) {
		
		super(scope, matcher, techBuilder, ruleBases);
		this.validate = validate;
		this.recognitionEngineMode = recognitionEngine;
	}

	/**
	 * Get the validation of the input models. (Default: False)
	 * 
	 * @return <code>true</code> if the input models will be validated;
	 *         <code>false</code> otherwise.
	 */
	public boolean isValidate() {
		return validate;
	}

	/**
	 * Set the validation of the input models. (Default: False)
	 * 
	 * @param validate
	 *            <code>true</code> if the input models should be validated;
	 *            <code>false</code> otherwise.
	 */
	public void setValidate(boolean validate) {
		if (this.validate != validate) {
			this.validate = validate;
			this.notifyListeners(LiftingSettingsItem.VALTIDATE);
		}
	}

	/**
	 * Triggering of the Recognition-Engine pipeline.
	 * 
	 * @see LiftingSettings.RecognitionEngineMode (Default: Post processed operation detection)
	 */
	public RecognitionEngineMode getRecognitionEngineMode() {
		return recognitionEngineMode;
	}

	/**
	 * Triggering of the Recognition-Engine pipeline.
	 * 
	 * @param recognitionEngineMode The mode {@link LiftingSettings.RecognitionEngineMode}
	 */
	public void setRecognitionEngineMode(
			RecognitionEngineMode recognitionEngineMode) {
		
		if ((this.recognitionEngineMode == null)
				|| !(this.recognitionEngineMode.equals(recognitionEngineMode))) {
			
			this.recognitionEngineMode = recognitionEngineMode;
			this.notifyListeners(LiftingSettingsItem.RECOGNITION_ENGINE_MODE);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.sidiff.difference.lifting.settings.Settings#toString()
	 */
	public String toString() {
		StringBuffer result = new StringBuffer(super.toString());
		
		result.append("Validate: " + validate + "\n");
		result.append("Recognition Engine Mode: " + recognitionEngineMode + "\n");
		
		return result.toString();
	}
}
