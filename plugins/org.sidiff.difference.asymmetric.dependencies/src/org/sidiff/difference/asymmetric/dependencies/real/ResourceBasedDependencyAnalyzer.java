package org.sidiff.difference.asymmetric.dependencies.real;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.lifting.recognitionengine.IEditRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.matching.UriBasedEditRuleMatch;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.project.runtime.library.IRuleBaseProject;

public class ResourceBasedDependencyAnalyzer extends DependencyAnalyzer {

	private Function<SemanticChangeSet,IEditRuleMatch> matchFactory = UriBasedEditRuleMatch::new;

	public ResourceBasedDependencyAnalyzer(AsymmetricDifference asymmetricDiff) {
		super(asymmetricDiff);
	}

	public void setEditRuleMatchFactory(Function<SemanticChangeSet,IEditRuleMatch> matchFactory) {
		this.matchFactory = Objects.requireNonNull(matchFactory);
	}

	@Override
	protected Set<ILiftingRuleBase> initializeRuleBases() {
		return getAsymmetricDiff().getOperationInvocations().stream()
				.map(opInv -> IRuleBaseProject.MANAGER.resolveIEditRuleBase(opInv.resolveEditRule(), ILiftingRuleBase.TYPE))
				.collect(Collectors.toSet());
	}

	@Override
	protected Map<EditRule, Set<SemanticChangeSet>> initializeEditRule2SCS() {
		return getAsymmetricDiff().getOperationInvocations().stream()
				.collect(Collectors.groupingBy(OperationInvocation::resolveEditRule,
						Collectors.mapping(OperationInvocation::getChangeSet, Collectors.toSet())));
	}

	@Override
	protected IEditRuleMatch getEditRuleMatch(SemanticChangeSet scs) {
		return matchFactory.apply(scs);
	}
}
