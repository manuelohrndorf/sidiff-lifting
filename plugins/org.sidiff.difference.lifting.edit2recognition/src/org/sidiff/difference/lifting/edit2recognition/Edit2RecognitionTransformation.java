package org.sidiff.difference.lifting.edit2recognition;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.HenshinUnitAnalysis;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.lifting.edit2recognition.internal.EditModule2RecognitionModule;
import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;
import org.sidiff.difference.lifting.edit2recognition.util.ImplicitEdgeCompletion;
import org.sidiff.difference.lifting.edit2recognition.util.TransformationConstants;

/**
 * This class serves as the main entry point of the edit- to recognition-rule transformation.
 *
 * @author Manuel Ohrndorf
 */
public class Edit2RecognitionTransformation implements EditPattern2RecognitionPattern {

	/**
	 * The edit-rule module.
	 */
	private Module editModule;
	
	/**
	 * The edit-rule main unit.
	 */
	private Unit editMainUnit;
	
	/** 
	 * "atomic" or "complex" edit operation
	 */
	private boolean atomic;
	
	/**
	 * Unit transformation engine
	 */
	private EditModule2RecognitionModule transformation;
	
	/**
	 * Initializes a new edit- to recognition-rule transformation.
	 * 
	 * @param editModule
	 *            The edit-rule module.
	 * @throws NoMainUnitFoundException
	 * 
	 * @see {@link ImplicitEdgeCompletion}
	 */
	public Edit2RecognitionTransformation(Module editModule) throws NoMainUnitFoundException {
		
		this.editModule = editModule;
		this.editMainUnit = HenshinUnitAnalysis.findExecuteMainUnit(editModule);
		
		// Complex or atomic edit operation	
		URI editRuleURI = EcoreUtil.getURI(editModule);
		
		if (editRuleURI.toString().contains(TransformationConstants.PATH_SEGMENT_ATOMIC_EDITRULES)) {
			atomic = true;
		} else {
			atomic = false;
		}
	}
	
	/**
	 * Initializes a new edit- to recognition-rule transformation.
	 * 
	 * @param editModule
	 *            The edit-rule module.
	 * @throws NoMainUnitFoundException
	 * 
	 * @see {@link ImplicitEdgeCompletion}
	 */
	public Edit2RecognitionTransformation(Module editModule, boolean atomic) 
			throws NoMainUnitFoundException {
		
		this.editModule = editModule;
		this.editMainUnit = HenshinUnitAnalysis.findExecuteMainUnit(editModule);
		this.atomic = atomic;
	}
	
	/**
	 * Starts the edit- to recognition-rule transformation.
	 * 
	 * @throws EditToRecognitionException
	 */
	public void transform() throws EditToRecognitionException {
		transformation = new EditModule2RecognitionModule(editModule, editMainUnit, atomic);
		transformation.transform();
	}

	/**
	 * Returns the recognition-rule module and starts the transformation if necessary.
	 * 
	 * @return The recognition-rule module.
	 * @throws EditToRecognitionException
	 * 
	 * @see {@link EditToRecognitionTransformation#getRecognitionMainUnit())}
	 * @see {@link EditToRecognitionTransformation#transform())}
	 */
	public Module getRecognitionModule() throws EditToRecognitionException {
		
		if (transformation == null) {
			transform();
		}
		
		return transformation.getRecognitionModule();
	}

	/**
	 * Returns the recognition-rule main-unit and starts the transformation if necessary.
	 * 
	 * @return The recognition-rule main-unit (contained in a module).
	 * @throws EditToRecognitionException 
	 * 
	 * @see {@link Edit2RecognitionTransformation#getEditModule()}
	 * @see {@link EditToRecognitionTransformation#transform())}
	 */
	public Unit getRecognitionMainUnit() throws EditToRecognitionException {
		
		if (transformation == null) {
			transform();
		}
		
		return transformation.getRecognitionMainUnit();
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
	 * @return <code>true</code> if the edit-rule is an "atomic" rule; <code>false</code> if it is a
	 *         "complex" rule.
	 */
	public boolean isAtomic() {
		return atomic;
	}

	@Override
	public Collection<TransformationPatterns> getPatterns() {
		return transformation.getPatterns();
	}
}
