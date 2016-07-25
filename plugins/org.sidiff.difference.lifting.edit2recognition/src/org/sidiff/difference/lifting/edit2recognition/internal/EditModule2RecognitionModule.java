package org.sidiff.difference.lifting.edit2recognition.internal;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.difference.lifting.edit2recognition.EditPattern2RecognitionPattern;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;
import org.sidiff.difference.lifting.edit2recognition.util.TransformationConstants;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.matching.model.MatchingModelPackage;

/**
 * Transforms an edit-rule module into an recognition-rule module.
 * 
 * @author Manuel Ohrndorf
 */
public class EditModule2RecognitionModule implements EditPattern2RecognitionPattern {

	/**
	 * The edit-rule module.
	 */
	private Module editModule;
	
	/**
	 * The edit-rule main unit.
	 */
	private Unit editMainUnit;
	
	/**
	 * The recognition-rule module.
	 */
	private Module recognitionModule;
	
	/**
	 * The recognition-rule main unit.
	 */
	private Unit recognitionMainUnit;
	
	/**
	 * Unit transformation engine
	 */
	private EditPattern2RecognitionPattern transformation;
	
	/** 
	 * "atomic" or "complex" edit operation
	 */
	private boolean atomic;
	
	/**
	 * Transforms an edit-rule module into an recognition-rule module.
	 * 
	 * @param editModule
	 *            the edit rule transformation system.
	 * @param atomic
	 *            "atomic" (<code>true</code>) or "complex" (<code>false</code>) edit operation.
	 * @throws EditToRecognitionException
	 */
	public EditModule2RecognitionModule(Module editModule, Unit editMainUnit, boolean atomic) 
			throws EditToRecognitionException {
		
		// Initialize transformation:
		this.editModule = editModule;
		this.editMainUnit = editMainUnit;
		this.atomic = atomic;
	}
	
	/**
	 * Transforms an edit-rule module into an recognition-rule module.
	 * 
	 * @return The transformed recognition-rule module.
	 * @throws EditToRecognitionException
	 */
	public Module transform() throws EditToRecognitionException {

		/*
		 * Start unit transformation
		 */
		
		// Transform the edit-rule main-unit into a recognition-unit:
		transformation = new EditUnit2RecognitionUnit(editMainUnit, atomic);
		recognitionMainUnit = ((EditUnit2RecognitionUnit) transformation).transform();
		
		// Initialize recognition rule:
		recognitionModule = createRecognitionModule();
		recognitionModule.getUnits().add(recognitionMainUnit);
		
		return recognitionModule;
	}
	
	/**
	 * Initializes a new recognition rule module.
	 * 
	 * @return A new recognition rule module.
	 */
	private Module createRecognitionModule() {

		// Initialize recognition transformation system
		Module recognitionModule = HenshinFactory.eINSTANCE.createModule();
		recognitionModule.setName(TransformationConstants.RECOGNITION_MODULE_PREFIX + editModule.getName());

		// Organize recognition rule imports
		recognitionModule.getImports().add(SymmetricPackage.eINSTANCE);
		recognitionModule.getImports().add(MatchingModelPackage.eINSTANCE);
	
		// Copy description
		if (editModule.getDescription() != null) {
			recognitionModule.setDescription(TransformationConstants.RECOGNITION_MODULE_DESCRIPTION_PREFIX + editModule.getDescription());
		}

		for (EPackage eImport : editModule.getImports()) {
			recognitionModule.getImports().add(eImport);
		}
		
		return recognitionModule;
	}

	/**
	 * @return The edit-rule module.
	 */
	public Module getEditModule() {
		return editModule;
	}

	/**
	 * @return The edit-rule main-unit.
	 */
	public Unit getEditMainUnit() {
		return editMainUnit;
	}

	/**
	 * @return The recognition-rule module.
	 */
	public Module getRecognitionModule() {
		return recognitionModule;
	}

	/**
	 * @return The recognition-rule main-unit.
	 */
	public Unit getRecognitionMainUnit() {
		return recognitionMainUnit;
	}

	@Override
	public Map<Unit, TransformationPatterns> getPatterns() {
		return transformation.getPatterns();
	}
}
