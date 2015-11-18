package org.sidiff.difference.lifting.edit2recognition.internal;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.edit2recognition.EditPattern2RecognitionPattern;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.UnsupportedUnitException;
import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;

/**
 * Transforms an edit-rule unit into an recognition-rule unit.
 * 
 * @author Manuel Ohrndorf
 */
public class EditUnit2RecognitionUnit implements EditPattern2RecognitionPattern {
	
	/**
	 * Unit transformation engine
	 */
	private EditPattern2RecognitionPattern transformation;
	
	/**
	 * The edit rule main unit.
	 */
	private Unit editUnit;
	
	/**
	 * The recognition rule main unit.
	 */
	private Unit recognitionUnit;
	
	/** 
	 * "atomic" or "complex" edit operation
	 */
	private boolean atomic;
	
	/**
	 * Transforms an edit-rule unit into an recognition-rule unit.
	 * <p>
	 * supports:
	 * <ul>
	 * <li>Sequential unit containing exactly one (multi-)rule</li>
	 * <li>Priority unit containing exactly one (multi-)rule</li>
	 * </ul>
	 * </p>
	 * 
	 * @param editUnit
	 *            the edit-rule unit.
	 * @param atomic
	 *            "atomic" (<code>true</code>) or "complex" (<code>false</code>) edit operation.
	 * @throws EditToRecognitionException
	 */
	public EditUnit2RecognitionUnit(Unit editUnit, boolean atomic) {
		this.editUnit = editUnit;
		this.atomic = atomic;
	}
	
	/**
	 * Transforms an edit rule transformation system into an recognition transformation system.
	 * <p>
	 * supports:
	 * <ul>
	 * <li>Sequential unit containing exactly one (multi-)rule</li>
	 * <li>Priority unit containing exactly one (multi-)rule</li>
	 * </ul>
	 * </p>
	 * 
	 * @return The transformed recognition-rule unit.
	 * @throws EditToRecognitionException
	 */
	public Unit transform() throws EditToRecognitionException {

		/*
		 * Start unit transformation
		 */
		
		// Supported: AmalgamationUnit := Unit -> Kernel Rule -> Multi-Rules -> ... 
		if (isAmalgamationUnit(editUnit)) {
			recognitionUnit = transformAmalgamationUnit();
		}
		
		// Supported: SequentialUnit with single Rule
		else if (editUnit instanceof SequentialUnit) {
			recognitionUnit = transformSequentialUnit();
		}

		// Supported: PriorityUnit with single Rule
		else if (editUnit instanceof PriorityUnit) {
			recognitionUnit = transformPriorityUnit();
		}
		
		else {
			throw new UnsupportedUnitException(editUnit);
		}

		// TODO: More Transformation Units
		
		return recognitionUnit;
	}
	
	/**
	 * Handles the transformation of the edit rule amalgamation unit to a recognition rule unit.
	 * 
	 * @return The transformed recognition-rule unit.
	 * @throws EditToRecognitionException
	 */
	private Unit transformAmalgamationUnit() throws EditToRecognitionException {

		List<Unit> subUnits = editUnit.getSubUnits(false);
		
		if ((subUnits.size() == 1) && (subUnits.get(0) instanceof Rule)) {
			Rule kernelRule = (Rule) subUnits.get(0);
			
			// Print report
			LogUtil.log(LogEvent.NOTICE, "Amalgamation Unit (" + editUnit.getModule().getName() + "): ");
			LogUtil.log(LogEvent.NOTICE, "  Kernel rule: " + kernelRule.getName());
			LogUtil.log(LogEvent.NOTICE, "  Multi rules: ");

			for (Rule multiRule : kernelRule.getMultiRules()) {
				LogUtil.log(LogEvent.NOTICE, "    -> " + multiRule.getName());
			}
			
			// Start rule transformation
			transformation = new EditMulti2RecognitionMulti(editUnit, atomic);
			Unit recognitionMainUnit = ((EditMulti2RecognitionMulti) transformation).transform();
			
			return recognitionMainUnit;
		}  else {
			throw new UnsupportedUnitException(editUnit);
		}
	}
	
	/**
	 * Handles the transformation of the edit rule sequential unit to a recognition rule unit.
	 * 
	 * @return The transformed recognition-rule unit.
	 * @throws EditToRecognitionException
	 */
	private Unit transformSequentialUnit() throws EditToRecognitionException {
		
		List<Unit> subUnits = ((SequentialUnit) editUnit).getSubUnits();

		if ((subUnits.size() == 1) && (subUnits.get(0) instanceof Rule)) {
			Rule editRule = (Rule) subUnits.get(0);

			// Print report
			LogUtil.log(LogEvent.NOTICE, "Sequential Unit: " + editUnit.getModule().getName());
			
			// Start rule transformation
			transformation = new EditRule2RecognitionRule(editRule, editUnit, atomic);
			Unit recognitionMainUnit = ((EditRule2RecognitionRule) transformation).transform();
			
			return recognitionMainUnit;
		} else {
			throw new UnsupportedUnitException(editUnit);
		}
	}

	/**
	 * Handles the transformation of the edit rule priority unit to a recognition rule unit.
	 * 
	 * @return The transformed recognition-rule unit.
	 * @throws EditToRecognitionException
	 */
	private Unit transformPriorityUnit() throws EditToRecognitionException {
		
		List<Unit> subUnits = ((PriorityUnit) editUnit).getSubUnits();

		if ((subUnits.size() == 1) && (subUnits.get(0) instanceof Rule)) {
			Rule editRule = (Rule) subUnits.get(0);

			// Print report
			LogUtil.log(LogEvent.NOTICE, "Priority Unit: " + editUnit.getModule().getName());
			
			// Start rule transformation
			transformation =  new EditRule2RecognitionRule(editRule, editUnit, atomic);
			Unit recognitionMainUnit = ((EditRule2RecognitionRule) transformation).transform();
			
			return recognitionMainUnit;
		} else {
			throw new UnsupportedUnitException(editUnit);
		}
	}

	@Override
	public Collection<TransformationPatterns> getPatterns() {
		return transformation.getPatterns();
	}
}
