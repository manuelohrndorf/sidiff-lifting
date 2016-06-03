package org.sidiff.difference.rulebase.wrapper;

import java.util.Collection;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.Edit2RecognitionTransformation;
import org.sidiff.difference.lifting.edit2recognition.exceptions.EditToRecognitionException;
import org.sidiff.difference.lifting.edit2recognition.traces.ACObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.AddObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.CorrespondencePattern;
import org.sidiff.difference.lifting.edit2recognition.traces.RemoveObjectPattern;
import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;
import org.sidiff.difference.lifting.edit2recognition.util.ImplicitEdgeCompletion;
import org.sidiff.difference.rulebase.LiftingRulebaseFactory;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.Trace;
import org.sidiff.editrule.rulebase.RuleBaseItem;

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
	private Edit2RecognitionTransformation transformation;

	/**
	 * Creates a recognition rule for the given rulebase item.
	 * 
	 * @param item
	 *            The rulebase item which contains the corresponding edit-rule.
	 * @throws EditToRecognitionException
	 * @throws NoMainUnitFoundException 
	 */
	public void transform(RuleBaseItem item) throws EditToRecognitionException, NoMainUnitFoundException {
		
		// Create recognition rule wrapper:
		RecognitionRule recognitionRule = createRecognitionRule();
		
		// Initialize rulebase item:
		this.item = item;
		this.item.getEditRuleAttachments().add(recognitionRule);
		
		// Initialize transformation:
		transformation = new Edit2RecognitionTransformation(item.getEditRule().getExecuteModule());
				
		// Auto fixing the edit-rule:
		ImplicitEdgeCompletion implicitEdgeCompletion = new ImplicitEdgeCompletion();
		implicitEdgeCompletion.createImplicitEdges(item.getEditRule().getExecuteModule());

		// Start transformation: edit-rule -> recognition-rule:
		transformation.transform();

		// Save traces from edit rule to recognition rule:
		for (TransformationPatterns patterns : transformation.getPatterns()) {
			transformPatternsToTraces(patterns);
		}
		
		// Undo edit-rule auto-fixes:
		implicitEdgeCompletion.deleteImplicitEdges();
		
		// Fill recognition rule wrapper:
		Unit recognitionMainUnit = transformation.getRecognitionMainUnit();
		recognitionRule.setRecognitionMainUnit((Rule) recognitionMainUnit);
		item.getEditRuleAttachments().add(recognitionRule);
	}
	
	/**
	 * Initializes a rule base recognition rule wrapper.
	 * 
	 * @return an recognition rule wrapper.
	 */
	private RecognitionRule createRecognitionRule() {
		return LiftingRulebaseFactory.eINSTANCE.createRecognitionRule();
	}
	
	/**
	 * Convert the generation patters to traces.
	 */
	private void transformPatternsToTraces(TransformationPatterns patterns) {
		
		Collection<Trace> tracesA = item.getEditRuleAttachment(RecognitionRule.class).getTracesA();
		Collection<Trace> tracesB = item.getEditRuleAttachment(RecognitionRule.class).getTracesB();
		
		// Create traces for correspondence patterns
		for (CorrespondencePattern pattern : patterns.getCorrespondencePatterns()) {

			if (pattern.getNodeA() != null) {
				// Create trace: LHS <<preserve>> edit rule node
				// -> LHS <<preserve>> model A recognition rule node
				Trace lhs_traceA = LiftingRulebaseFactory.eINSTANCE.createTrace();
				lhs_traceA.setEditRuleTrace(pattern.getTrace().getLhsNode());
				lhs_traceA.setRecognitionRuleTrace(pattern.getNodeA().getLhsNode());
				
				// Add model A traces
				tracesA.add(lhs_traceA);
			}

			if (pattern.getNodeB() != null) {
				// Create trace: LHS <<preserve>> edit rule node
				// -> LHS <<preserve>> model B recognition rule node
				Trace rhs_traceB = LiftingRulebaseFactory.eINSTANCE.createTrace();
				rhs_traceB.setEditRuleTrace(pattern.getTrace().getLhsNode());
				rhs_traceB.setRecognitionRuleTrace(pattern.getNodeB().getLhsNode());

				// Add model B traces
				tracesB.add(rhs_traceB);
			}
		}

		// Create traces for add object patterns
		for (AddObjectPattern pattern : patterns.getAddObjectPatterns()) {

			// Create trace: RHS <<create>> edit rule node
			// -> LHS <<preserve>> model B recognition rule node
			Trace rhs_traceB = LiftingRulebaseFactory.eINSTANCE.createTrace();
			rhs_traceB.setEditRuleTrace(pattern.getTrace());
			rhs_traceB.setRecognitionRuleTrace(pattern.getNodeB().getLhsNode());

			// Add model B traces
			tracesB.add(rhs_traceB);
		}

		// Create traces for remove object patterns
		for (RemoveObjectPattern pattern : patterns.getRemoveObjectPatterns()) {

			// Create trace: LHS <<delete>> edit rule node
			// -> <<preserve>> model A recognition rule node
			Trace rhs_traceA = LiftingRulebaseFactory.eINSTANCE.createTrace();
			rhs_traceA.setEditRuleTrace(pattern.getTrace());
			rhs_traceA.setRecognitionRuleTrace(pattern.getNodeA().getLhsNode());

			// Add model A traces
			tracesA.add(rhs_traceA);
		}
		
		// Create traces for application condition (AC) patterns
		for (ACObjectPattern pattern : patterns.getACObjectPatterns()) {

			// Create trace: Nested condition <<forbid/require>> edit rule node
			// -> Nested condition <<forbid/require>> (model B) recognition rule node
			Trace rhs_traceB = LiftingRulebaseFactory.eINSTANCE.createTrace();
			rhs_traceB.setEditRuleTrace(pattern.getACTrace());
			rhs_traceB.setRecognitionRuleTrace(pattern.getACNode());

			// Add model B traces
			tracesB.add(rhs_traceB);
		}
	}
}
