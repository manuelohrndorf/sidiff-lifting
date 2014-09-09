package org.sidiff.difference.rulebase.wrapper;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.difference.lifting.edit2recognition.EditModule2RecognitionModule;
import org.sidiff.difference.lifting.edit2recognition.exceptions.NoMainUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.NoRecognizableChangesInEditRule;
import org.sidiff.difference.lifting.edit2recognition.exceptions.NoUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.UnsupportedApplicationConditionException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.UnsupportedTransformationSytemException;
import org.sidiff.difference.lifting.edit2recognition.traces.ACObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.AddObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.CorrespondencePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.RemoveObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;
import org.sidiff.difference.lifting.edit2recognition.util.Edit2RecognitionUtil;
import org.sidiff.difference.lifting.edit2recognition.util.RuleChecker;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.RulebaseFactory;
import org.sidiff.difference.rulebase.Trace;
import org.silift.common.util.emf.EMFStorage;

/**
 * Transforms an edit rule rulebase wrapper into an recognition rule rulebase wrapper.
 * 
 * @author Manuel Ohrndorf
 */
public class EditWrapper2RecognitionWrapper {
	
	/**
	 * The rulebase item.
	 */
	private RuleBaseItem item;
	
	/**
	 * Module transformation engine.
	 */
	private EditModule2RecognitionModule transformation;
	
	/**
	 * Transforms an edit rule transformation system into an recognition transformation system.
	 * <p>supports:</p>
	 * <ul>
	 * <li>Sequential unit with single rule</li>
	 * <li>Priority unit with single rule</li>
	 * <li>Amalgamation unit</li>
	 * </ul>
	 * 
	 * @param editRuleURI
	 *            path of the edit rule transformation system.
	 * @throws NoUnitFoundException 
	 * @throws NoMainUnitFoundException 
	 */
	public EditWrapper2RecognitionWrapper(URI editRuleURI) 
			throws NoMainUnitFoundException, NoUnitFoundException {
		
		// Load edit-rule:
		Module editModule = (Module) EMFStorage.eLoad(editRuleURI);
		
		// Initialize transformation:
		init(editModule);
	}
	
	/**
	 * Transforms an edit rule transformation system into an recognition transformation system.
	 * <p>supports:</p>
	 * <ul>
	 * <li>Sequential unit with single rule</li>
	 * <li>Priority unit with single rule</li>
	 * <li>Amalgamation unit</li>
	 * </ul>
	 * 
	 * @param editRule
	 *            the edit rule transformation system.
	 * @throws NoUnitFoundException 
	 * @throws NoMainUnitFoundException 
	 */
	public EditWrapper2RecognitionWrapper(EditRule editRule) 
			throws NoMainUnitFoundException, NoUnitFoundException {
		
		// Initialize transformation:
		init(editRule.getExecuteModule());
	}
	
	/**
	 * Initialize transformation.
	 * 
	 * @param editRule
	 *            The edit rule module to transform.
	 * @throws NoMainUnitFoundException
	 * @throws NoUnitFoundException
	 */
	private void init(Module editRule) throws NoMainUnitFoundException, NoUnitFoundException {
		
		// Initialize transformation:
		transformation = new EditModule2RecognitionModule(editRule);
		
		// Initialize rulebase item:
		item = createRuleBaseItem(editRule);
	}
	
	/**
	 * Initializes a rule base edit rule wrapper.
	 * 
	 * @return an edit rule wrapper.
	 */
	private EditRule createEditRule(Module editModule) 
			throws NoMainUnitFoundException, NoUnitFoundException {

		// Create edit rule
		EditRule editRule = RulebaseFactory.eINSTANCE.createEditRule();
		editRule.setExecuteMainUnit(Edit2RecognitionUtil.findExecuteMainUnit(editModule));
		editRule.setUseDerivedFeatures(RuleChecker.checkDerivedReferences(editModule));
		
		return editRule;
	}
	
	/**
	 * Initializes a rule base recognition rule wrapper.
	 * 
	 * @return an recognition rule wrapper.
	 */
	private RecognitionRule createRecognitionRule() {
		return RulebaseFactory.eINSTANCE.createRecognitionRule();
	}
	
	/**
	 * Creates a new rule base item which includes an edit rule and the 
	 * transformed recognition rule.
	 * @param editRule 
	 * @param editRule 
	 * 
	 * @return A new rule base item.
	 * @throws UnsupportedTransformationSytemException
	 * @throws NoUnitFoundException
	 * @throws NoMainUnitFoundException
	 * @throws UnsupportedApplicationConditionException
	 */
	private RuleBaseItem createRuleBaseItem(Module editRule) 
			throws NoMainUnitFoundException, NoUnitFoundException  {

		// Create rule base element
		RuleBaseItem item = RulebaseFactory.eINSTANCE.createRuleBaseItem();
		
		item.setRecognitionRule(createRecognitionRule());
		item.setEditRule(createEditRule(editRule));
		
		return item;
	}

	/**
	 * Initializes a rule base recognition rule wrapper.
	 * 
	 * @return A rule base recognition rule wrapper.
	 * @throws NoRecognizableChangesInEditRule 
	 */
	public RuleBaseItem transform() 
			
			throws UnsupportedTransformationSytemException, 
			NoUnitFoundException, NoMainUnitFoundException, 
			UnsupportedApplicationConditionException, 
			NoRecognizableChangesInEditRule {
		
		// Create recognition rule wrapper:
		RecognitionRule recognitionRule = RulebaseFactory.eINSTANCE.createRecognitionRule();

		// Start transformation: edit-rule -> recognition-rule:
		transformation.transform();

		// Save traces from edit rule to recognition rule:
		for (TransformationPatterns patterns : transformation.getPatterns()) {
			transformPatternsToTraces(patterns);
		}
		
		// Finally remove implicit edges which were added during transformation:
		transformation.deleteImplicitEdges();
		
		// Fill recognition rule wrapper:
		Unit recognitionMainUnit = transformation.getRecognitionMainUnit();
		recognitionRule.setRecognitionMainUnit((Rule) recognitionMainUnit);
		item.setRecognitionRule(recognitionRule);
		
		return item;
	}
	
	/**
	 * Convert the generation patters to traces.
	 */
	private void transformPatternsToTraces(TransformationPatterns patterns) {
		
		Collection<Trace> tracesA = item.getTracesA();
		Collection<Trace> tracesB = item.getTracesB();
		
		// Create traces for correspondence patterns
		for (CorrespondencePattern pattern : patterns.getCorrespondencePatterns()) {

			if (pattern.nodeA != null) {
				// Create trace: LHS <<preserve>> edit rule node
				// -> LHS <<preserve>> model A recognition rule node
				Trace lhs_traceA = RulebaseFactory.eINSTANCE.createTrace();
				lhs_traceA.setEditRuleTrace(pattern.trace.getLhsNode());
				lhs_traceA.setRecognitionRuleTrace(pattern.nodeA.getLhsNode());
				
				// Add model A traces
				tracesA.add(lhs_traceA);
			}

			if (pattern.nodeB != null) {
				// Create trace: LHS <<preserve>> edit rule node
				// -> LHS <<preserve>> model B recognition rule node
				Trace rhs_traceB = RulebaseFactory.eINSTANCE.createTrace();
				rhs_traceB.setEditRuleTrace(pattern.trace.getLhsNode());
				rhs_traceB.setRecognitionRuleTrace(pattern.nodeB.getLhsNode());

				// Add model B traces
				tracesB.add(rhs_traceB);
			}
		}

		// Create traces for add object patterns
		for (AddObjectPattern pattern : patterns.getAddObjectPatterns()) {

			// Create trace: RHS <<create>> edit rule node
			// -> LHS <<preserve>> model B recognition rule node
			Trace rhs_traceB = RulebaseFactory.eINSTANCE.createTrace();
			rhs_traceB.setEditRuleTrace(pattern.trace);
			rhs_traceB.setRecognitionRuleTrace(pattern.nodeB.getLhsNode());

			// Add model B traces
			tracesB.add(rhs_traceB);
		}

		// Create traces for remove object patterns
		for (RemoveObjectPattern pattern : patterns.getRemoveObjectPatterns()) {

			// Create trace: LHS <<delete>> edit rule node
			// -> <<preserve>> model A recognition rule node
			Trace rhs_traceA = RulebaseFactory.eINSTANCE.createTrace();
			rhs_traceA.setEditRuleTrace(pattern.trace);
			rhs_traceA.setRecognitionRuleTrace(pattern.nodeA.getLhsNode());

			// Add model A traces
			tracesA.add(rhs_traceA);
		}
		
		// Create traces for application condition (AC) patterns
		for (ACObjectPattern pattern : patterns.getACObjectPatterns()) {

			// Create trace: Nested condition <<forbid/require>> edit rule node
			// -> Nested condition <<forbid/require>> (model B) recognition rule node
			Trace rhs_traceB = RulebaseFactory.eINSTANCE.createTrace();
			rhs_traceB.setEditRuleTrace(pattern.acTrace);
			rhs_traceB.setRecognitionRuleTrace(pattern.acNode);

			// Add model B traces
			tracesB.add(rhs_traceB);
		}
	}
}
