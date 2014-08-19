package org.sidiff.difference.asymmetric.util;

import java.util.HashMap;
import java.util.Map;

import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;

public class AsymmetricDifferenceUtil {

	// TODO: WORKAROUND: SCS -> Operation Invocation Mapping
	private static Map<AsymmetricDifference, Map<SemanticChangeSet, OperationInvocation>> scs2operation = new HashMap<AsymmetricDifference, Map<SemanticChangeSet,OperationInvocation>>();
	
	/**
	 * Convert semantic change sets (SCS) of the symmetric difference into
	 * operation invocations for the asymmetric difference.
	 */
	public static void deriveAsymmetricDifference(SymmetricDifference symmetricDiff,
			AsymmetricDifference asymmetricDiff) {

		Map<SemanticChangeSet, OperationInvocation> mappingSCS2operation = new HashMap<SemanticChangeSet, OperationInvocation>();
		
		for (SemanticChangeSet scs : symmetricDiff.getChangeSets()) {
			OperationInvocation opInv = AsymmetricFactory.eINSTANCE.createOperationInvocation();			
			opInv.setChangeSet(scs);
			asymmetricDiff.getOperationInvocations().add(opInv);
			
			mappingSCS2operation.put(scs, opInv);
		}
		
		scs2operation.put(asymmetricDiff, mappingSCS2operation);
	}

	/**
	 * Searches for the corresponding operation invocation of the semantic
	 * change set.
	 * 
	 * @param diff
	 *            The asymmetric difference containing the operation invocation.
	 * @param scs
	 *            The semantic change set to search for.
	 * @return The corresponding operation invocation.
	 */
	public static OperationInvocation getOperationInvocationOfSCS(AsymmetricDifference diff,
			SemanticChangeSet scs) {

		return scs2operation.get(diff).get(scs);
	}
}
