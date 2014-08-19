package org.sidiff.difference.asymmetric.paramretrieval;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.ParameterInfo;
import org.sidiff.common.henshin.ParameterInfo.ParameterDirection;
import org.sidiff.common.henshin.ParameterInfo.ParameterKind;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.lifting.recognitionengine.matching.EngineBasedEditRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.matching.RecognitionRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngine;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.symmetric.SymmetricDifference;

public class ParameterRetriever {

	/**
	 * RecognitionEngine that has been used to lift difference
	 */
	private RecognitionEngine recognitionEngine;

	/**
	 * The asymmetric difference
	 */
	private AsymmetricDifference asymmetricDiff;

	/**
	 * Convenient access to lifted difference
	 */
	private SymmetricDifference difference;

	/**
	 * Creates new ParameterRetriever
	 * 
	 * @param engine
	 *            RecognitionEngine that has been used to lift difference
	 */
	public ParameterRetriever(RecognitionEngine engine, AsymmetricDifference asymmetricDiff) {
		this.recognitionEngine = engine;
		this.asymmetricDiff = asymmetricDiff;
		this.difference = engine.getDifference();
	}

	/**
	 * start parameter retrieval
	 */
	public void retrieveParameters() {
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "-------------------- RETRIEVE PARAMETERS -------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		for (OperationInvocation op : asymmetricDiff.getOperationInvocations()) {
			retrieveParameters(op);
		}
	}

	/**
	 * retrieve params for given SemanticChangeSet
	 * 
	 * @param operationInvocation
	 */
	private void retrieveParameters(OperationInvocation operationInvocation) {
		EngineBasedEditRuleMatch erMatch = recognitionEngine.getEditRuleMatch(operationInvocation.getChangeSet());
		EditRule editRule = erMatch.getEditRule();
		Unit erMainUnit = editRule.getExecuteMainUnit();

		RecognitionRuleMatch rrMatch = recognitionEngine.getRecognitionRuleMatch(operationInvocation.getChangeSet());

		for (Parameter formal : erMainUnit.getParameters()) {
			if (ParameterInfo.getParameterKind(formal).equals(ParameterKind.OBJECT)) {
				// Determine erNode
				ParameterDirection direction = ParameterInfo.getParameterDirection(formal);
				Node erNode = null;
				if (direction.equals(ParameterDirection.IN)) {
					// ER node must be in LHS
					erNode = ParameterInfo.getInnermostIdentifiedNode(formal, true);
				} else if (direction.equals(ParameterDirection.OUT)) {
					// ER node must be in RHS
					erNode = ParameterInfo.getInnermostIdentifiedNode(formal, false);
				}

				assert (erNode != null) : "No identified node found for formal parameter " + formal;

				// Get occurrence(s)
				Collection<OccurrenceAB> occurrences = new LinkedList<OccurrenceAB>();
				for (EObject obj : erMatch.getOccurenceA(erNode)) {
					OccurrenceAB o = new OccurrenceAB();
					o.setObjA(obj);
					if (!occurrences.contains(o)) {
						occurrences.add(o);
					}
				}
				for (EObject obj : erMatch.getOccurenceB(erNode)) {
					OccurrenceAB o = new OccurrenceAB();
					o.setObjB(obj);
					if (!occurrences.contains(o)) {
						occurrences.add(o);
					}
				}

				// Assertion
				Parameter ruleParam = ParameterInfo.getInnermostParameter(formal);
				if (((Rule) ruleParam.eContainer()).getKernelRule() != null) {
					// it's a multi-rule param => actually, there may be no
					// occurrence because the multi-rule did not match
				} else {
					assert (!occurrences.isEmpty()) : "No occurence found for ER node " + erNode;
				}
				// End Assertion

				if (occurrences.size() == 1) {
					// single mapping
					OccurrenceAB occurrenceAB = occurrences.iterator().next();
					ObjectParameterBinding binding = createObjectParameterBinding(occurrenceAB, formal);
					operationInvocation.getParameterBindings().add(binding);
					LogUtil.log(LogEvent.DEBUG, "Object-ParameterBinding: " + formal.getName() + " -> " + occurrenceAB);
				} else {
					// multi mapping
					MultiParameterBinding multiBinding = AsymmetricFactory.eINSTANCE.createMultiParameterBinding();
					multiBinding.setFormalName(formal.getName());
					operationInvocation.getParameterBindings().add(multiBinding);
					LogUtil.log(LogEvent.DEBUG, "Object-ParameterBinding (multi) for " + formal.getName() + ":");
					for (OccurrenceAB occurrenceAB : occurrences) {
						ObjectParameterBinding binding = createObjectParameterBinding(occurrenceAB, formal);
						multiBinding.getParameterBindings().add(binding);
						LogUtil.log(LogEvent.DEBUG, "\t" + occurrenceAB);
					}
				}

			} else {
				// VALUE Parameter

				Parameter tail_editR = ParameterInfo.getInnermostParameter(formal);
				Object recognParam_value = rrMatch.getParameterValue(tail_editR.getName());
				
				//TODO revise this section, must be fixed in ValueParameterBinding itself probably
				String paramActual = null;
				if (recognParam_value != null){
				paramActual = recognParam_value.toString();				
				
				//Test for multi attributes
					if (recognParam_value instanceof EDataTypeEList) {
						paramActual = paramActual.replace("[", "");
						paramActual = paramActual.replace("]", "");
					}			
				
					LogUtil.log(LogEvent.DEBUG, "Value-ParameterBinding: "
							+ formal.getName() + " -> " + paramActual);
				}

				// Create ParameterSubstitution and add to OperationInvocation
				ValueParameterBinding paramSubstitution = AsymmetricFactory.eINSTANCE.createValueParameterBinding();
				paramSubstitution.setFormalName(formal.getName());
				if (recognParam_value != null) {					
						paramSubstitution.setActual(paramActual);
				}
								
				operationInvocation.getParameterBindings().add(paramSubstitution);
				}
			}
		}
	

	private ObjectParameterBinding createObjectParameterBinding(OccurrenceAB o, Parameter formal) {
		ObjectParameterBinding paramSubstitution = AsymmetricFactory.eINSTANCE.createObjectParameterBinding();
		paramSubstitution.setFormalName(formal.getName());
		paramSubstitution.setActualA(o.getObjectA());
		paramSubstitution.setActualB(o.getObjectB());

		return paramSubstitution;
	}

	/**
	 * Very special utility class that helps to manage er node occurrences.
	 * 
	 */
	private class OccurrenceAB {

		private EObject objA;
		private EObject objB;

		void setObjA(EObject obj) {
			objA = obj;
		}

		void setObjB(EObject obj) {
			objB = obj;
		}

		EObject getObjectA() {
			assert (objA != null || objB != null);

			if (objA != null) {
				return objA;
			} else {
				return difference.getCorrespondingObjectInA(objB);
			}
		}

		EObject getObjectB() {
			assert (objA != null || objB != null);

			if (objB != null) {
				return objB;
			} else {
				return difference.getCorrespondingObjectInB(objA);
			}
		}

		@Override
		public boolean equals(Object o) {
			OccurrenceAB anotherOccurrence = (OccurrenceAB) o;
			return getObjectA() == anotherOccurrence.getObjectA() && getObjectB() == anotherOccurrence.getObjectB();
		}

		@Override
		public String toString() {
			return "[A: " + getObjectA() + " | B: " + getObjectB() + "]";
		}
	}
}
