package org.sidiff.difference.asymmetric.paramretrieval;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.ParameterInfo;
import org.sidiff.common.henshin.ParameterInfo.ParameterDirection;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.lifting.recognitionengine.IEditRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.IRecognitionEngine;
import org.sidiff.difference.lifting.recognitionengine.IRecognitionRuleMatch;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.editrule.rulebase.EditRule;

public class ParameterRetriever {

	/**
	 * RecognitionEngine that has been used to lift difference
	 */
	private IRecognitionEngine recognitionEngine;

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
	public ParameterRetriever(IRecognitionEngine engine, AsymmetricDifference asymmetricDiff) {
		this.recognitionEngine = engine;
		this.asymmetricDiff = asymmetricDiff;
		this.difference = engine.getSetup().getDifference();
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
		IEditRuleMatch erMatch = recognitionEngine.getEditRuleMatch(operationInvocation.getChangeSet());
		EditRule editRule = erMatch.getEditRule();
		Unit erMainUnit = editRule.getExecuteMainUnit();

		IRecognitionRuleMatch rrMatch = recognitionEngine.getRecognitionRuleMatch(operationInvocation.getChangeSet());

		for (Parameter formal : erMainUnit.getParameters()) {
			switch(ParameterInfo.getParameterKind(formal)) {
				case OBJECT:
					retrieveObjectParameter(operationInvocation, erMatch, formal);
					break;
				case VALUE:
					retrieveValueParameter(operationInvocation, rrMatch, formal);
					break;
			}
		}
	}

	private void retrieveObjectParameter(
			OperationInvocation operationInvocation,
			IEditRuleMatch erMatch,
			Parameter formal) {

		Node erNode = getEditRuleNode(formal);

		// Get occurrence(s)
		Collection<OccurrenceAB> occurrences = new LinkedHashSet<>();
		for (EObject obj : erMatch.getOccurenceA(erNode)) {
			occurrences.add(new OccurrenceAB(obj, null));
		}
		for (EObject obj : erMatch.getOccurenceB(erNode)) {
			occurrences.add(new OccurrenceAB(null, obj));
		}

		// Assertion
		Parameter ruleParam = ParameterInfo.getInnermostParameter(formal);
		if (((Rule) ruleParam.eContainer()).getKernelRule() != null) {
			// it's a multi-rule param => actually, there may be no
			// occurrence because the multi-rule did not match
		} else {
			assert (!occurrences.isEmpty()) : "No occurrence found for ER node " + erNode;
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
	}

	private void retrieveValueParameter(
			OperationInvocation operationInvocation,
			IRecognitionRuleMatch rrMatch,
			Parameter formal) {

		Parameter tail_editR = ParameterInfo.getInnermostParameter(formal);
		Object recognParam_value = rrMatch.getParameterValue(tail_editR.getName());
		
		//TODO revise this section, must be fixed in ValueParameterBinding itself probably
		String paramActual = null;
		if (recognParam_value != null) {
			paramActual = recognParam_value.toString();				
			
			//Test for multi attributes
			if (recognParam_value instanceof EDataTypeEList) {
				paramActual = paramActual.replace("[", "");
				paramActual = paramActual.replace("]", "");
			}			

			LogUtil.log(LogEvent.DEBUG, "Value-ParameterBinding: " + formal.getName() + " -> " + paramActual);
		}

		// Create ParameterSubstitution and add to OperationInvocation
		ValueParameterBinding paramSubstitution = AsymmetricFactory.eINSTANCE.createValueParameterBinding();
		paramSubstitution.setFormalName(formal.getName());
		if (paramActual != null) {					
			paramSubstitution.setActual(paramActual);
		}

		operationInvocation.getParameterBindings().add(paramSubstitution);
	}

	private static Node getEditRuleNode(Parameter formalParameter) {
		// Determine erNode
		ParameterDirection direction = ParameterInfo.getParameterDirection(formalParameter);
		Node erNode = null;
		if (direction == ParameterDirection.IN) {
			// ER node must be in LHS
			erNode = ParameterInfo.getInnermostIdentifiedNode(formalParameter, true);
		} else if (direction == ParameterDirection.OUT) {
			// ER node must be in RHS
			erNode = ParameterInfo.getInnermostIdentifiedNode(formalParameter, false);
		}

		assert (erNode != null) : "No identified node found for formal parameter " + formalParameter;
		return erNode;
	}
	

	private static ObjectParameterBinding createObjectParameterBinding(OccurrenceAB o, Parameter formal) {
		ObjectParameterBinding paramSubstitution = AsymmetricFactory.eINSTANCE.createObjectParameterBinding();
		paramSubstitution.setFormalName(formal.getName());
		paramSubstitution.setActualA(o.getObjectA());
		paramSubstitution.setActualB(o.getObjectB());
		return paramSubstitution;
	}

	/**
	 * An edit rule match node occurrences.
	 */
	private class OccurrenceAB {

		private EObject objA;
		private EObject objB;

		OccurrenceAB(EObject objA, EObject objB) {
			if(objA == null && objB == null) {
				throw new IllegalArgumentException("Either object must be set");
			} else if(objA == null) {
				this.objA = difference.getCorrespondingObjectInA(objB);
				this.objB = objB;
			} else if(objB == null) {
				this.objA = objA;
				this.objB = difference.getCorrespondingObjectInB(objA);
			} else {
				throw new IllegalArgumentException("Only one object should be set");
			}
		}

		public EObject getObjectA() {
			return objA;
		}

		public EObject getObjectB() {
			return objB;
		}

		@Override
		public boolean equals(Object o) {
			if(o == this) {
				return true;
			}
			if(!(o instanceof OccurrenceAB)) {
				return false;
			}
			OccurrenceAB anotherOccurrence = (OccurrenceAB) o;
			return getObjectA() == anotherOccurrence.getObjectA()
					&& getObjectB() == anotherOccurrence.getObjectB();
		}

		@Override
		public int hashCode() {
			return Objects.hash(getObjectA(), getObjectB());
		}

		@Override
		public String toString() {
			return "[A: " + getObjectA() + " | B: " + getObjectB() + "]";
		}
	}
}
