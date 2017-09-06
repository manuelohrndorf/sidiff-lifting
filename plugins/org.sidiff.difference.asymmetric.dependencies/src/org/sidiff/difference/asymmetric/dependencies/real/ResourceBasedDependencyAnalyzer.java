package org.sidiff.difference.asymmetric.dependencies.real;

import java.util.HashSet;

import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.lifting.recognitionengine.IEditRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.matching.UriBasedEditRuleMatch;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.project.runtime.util.RuleBaseProjectUtil;

public class ResourceBasedDependencyAnalyzer extends DependencyAnalyzer {

	public ResourceBasedDependencyAnalyzer(AsymmetricDifference asymmetricDiff) {
		super(asymmetricDiff);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		for(OperationInvocation opInv : asymmetricDiff.getOperationInvocations()){
			EditRule editRule = opInv.resolveEditRule();
			ruleBases.add(RuleBaseProjectUtil.resolveIEditRuleBase(editRule, ILiftingRuleBase.TYPE));
			SemanticChangeSet scs = opInv.getChangeSet();
			if(!editRule2SCS.containsKey(editRule)){
				editRule2SCS.put(editRule, new HashSet<SemanticChangeSet>());
			}
			editRule2SCS.get(editRule).add(scs);
		}
	}

	@Override
	protected IEditRuleMatch getEditRuleMatch(SemanticChangeSet scs) {
		IEditRuleMatch iEditRuleMatch = new UriBasedEditRuleMatch(scs);
		return iEditRuleMatch;
	}

}
