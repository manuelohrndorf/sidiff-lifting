package org.sidiff.editrule.validation;

public enum ValidationType {
	ruleOrganization,
	mainUnit,
	mainUnitType,
	mainUnitComposition,
	noUnusedParameters,
	uniqueParameterNames,
	mappedAllRuleObjectInParameters,
	mappedAllCreateNodes,
	mappedAllCreateNodeAttributes,
	correctParameterTyping,
	consistentEOpposite, 
	derivedEdges,
	atLeastOneAction,
	acComposition,
	acBoundaries,
	lhsBoundaries,
	noAcBoundaryAttributes, 
	multiRuleNodeEmbedding,
	multiRuleEdgeEmbedding,
	multiRuleAttributeEmbedding,
	multiRuleParameterEmbedding,
	uniqueMultiMappings
}
