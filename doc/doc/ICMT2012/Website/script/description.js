// Units

var mainUnit = 'The Main-Unit schedules the 5 transformation phases in a sequential order.';

var EnrichmentPhase = 'The Enrichment-Phase adds some informations to the edit-rule. See rules for detail.';

var MatchingPhase = 'The Matching-Phase gathers all necessary informations from the edit-rule. See rules for detail.';

var CreationPhase = 'The Creation-Phase builds all generation patterns for the LHS of the recognition-rule. There is one rule for each generation pattern';

var MirrorPhase = 'The Mirror-Phase mirrors all nodes, attributes and edges from the LHS to RHS graph.';

var GroupingPhase = 'The Grouping-Phase adds the semantic change set to the recognition-rule and connects it with all low-level changes.';

// Rules
	
var EmptyRule = 'The Empty-Rule is used as a kernel rule (marked with "k" in the unit tree). So all multi rules (marked with "m" in the unit tree) in the amalgamation unit are applied as often as possibly.';

// MatchingPhase

var CreateImplicitEdge = 'The Create-Implicit-Edge rule puts all (not yet given) opposite edges into the edit-rule. As the source and target of an edge (in the edit-rule) can '
 + 'be the same node, the rule is matched non-injective [Injective Matching = false].';

var MapPreserveEdge = 'Map-Preserve-Edge creates a mapping for each edge that intersects between the LHS and RHS of the edit-rule. (Like the Henshin node mappings.) '
 + 'As the source and target of an edge (in the edit-rule) can be the same node, the rule is matched non-injective [Injective Matching = false].';

// MatchingPhase 

var MatchCorrespondencePattern = 'This rule creates a Correspondence-Pattern object (in the helper structure) for each node '
 + 'which intersects between the LHS and RHS of the edit-rule. It creates the nodes which represent the model A and model B '
 + 'objects and their traces to the original edit-rule nodes.';

var MatchAddObjectPattern = 'This rule creates an Add-Object-Pattern object (in the helper structure) for each node which is only ' 
 + 'represented on the RHS of the edit-rule. It creates the node which represent the model B object and the trace to the original edit-rule node.';

var MatchRemoveObjectPattern = 'This rule creates an Remove-Object-Pattern object (in the helper structure) for each node which is only ' 
 + 'represented on the LHS of the edit-rule. It creates the node which represent the model A object and the trace to the original edit-rule node.';

var MatchPreservedReferencePattern = 'This rule creates two Preserved-Reference-Pattern objects (betwenn model A and model B nodes) (in the helper structure) '
 + 'for each edge which intersects between the LHS and RHS of the edit-rule. A <<preserve>> edge from the edit-rule is matched through the EdgeMappings '
 + '(of the helper structure). As the source and target of an edge (in the edit-rule) can be the same node, the rule is matched non-injective [Injective Matching = false].';

var MatchAddReferencePattern = 'This rule creates an Add-Reference-Pattern object (in the helper structure) for each edge which is only ' 
 + 'represented on the RHS of the edit-rule. A <<create>> edge from the edit-rule is matched through a Negative Application Condition (NAC) of the EdgeMappings (of the helper structure). '
 + 'It also creates a node which represents the (meta) type of the added reference. As the source and target of an edge (in the edit-rule) can be the same node, the rule is matched non-injective [Injective Matching = false].';

var MatchRemoveReferencePattern = 'This rule creates a Remove-Reference-Pattern object (in the helper structure) for each edge which is only ' 
 + 'represented on the LHS of the edit-rule. A <<delete>> edge from the edit-rule is matched through a Negative Application Condition (NAC) of the EdgeMappings (of the helper structure). '
 + 'It also creates a node which represents the (meta) type of the removed reference. As the source and target of an edge (in the edit-rule) can be the same node, the rule is matched non-injective [Injective Matching = false].';

var MatchAttributeValueChangePattern = 'This rule creates a Attribute-Value-Change-Pattern object (in the helper structure) for each attribute which is only ' 
 + 'represented on the RHS of the edit-rule. It also creates a node which represents the (meta) type of the changed attribute.';

var RemoveDuplicatedEReferenceType = 'A node wich represents a type of the meta model had to be unique. Those nodes are used by the Remove/Add-Reference-Pattern. '
 + 'The rule Remove-Duplicated-EReference-Type deletes those duplicated type nodes, which were created (parallel) in the matching rules before.'
 + ' The containing counted unit repeats until all duplications are removed [Count = -1].';

var RemoveDuplicatedEAttributeType = 'A node wich represents a type of the meta model had to be unique. Those nodes are used by the Attribute-Value-Change-Pattern. '
 + 'The rule Remove-Duplicated-EAttribute-Type deletes those duplicated type nodes, which were created (parallel) in the matching rules before.'
 + ' The containing counted unit repeats until all duplications are removed [Count = -1].';

// CreationPhase

var CreateCorrespondencePattern = 'The Create-Correspondence-Pattern rule adds the Correspondence node (which were omited in the Matchin-Phase) '
 + 'and assign the pattern to the LHS of the recognition-rule.';

var CreateAddObjectPattern = 'The Create-Add-Object-Pattern rule adds the low-level change node (which were omited in the Matchin-Phase) and '
 + 'assigns the pattern to the LHS of the recognition-rule.';

var CreateRemoveObjectPattern = 'The Create-Remove-Object-Pattern rule adds the low-level change node (which were omited in the Matchin-Phase) '
 + 'and assigns the pattern to the LHS of the recognition-rule.';

var CreatePreservedReferencePattern = 'The Create-Preserved-Reference-Pattern assigns the pattern to the LHS of the recognition-rule.';

var CreateAddReferencePattern = 'The Create-Add-Reference-Pattern rule adds the low-level change node (which were omited in the Matchin-Phase) '
 + 'and assigns the pattern to the LHS of the recognition-rule. As the source and target of an edge (in the edit-rule) can be the same node, the rule is matched non-injective [Injective Matching = false].';

var CreateRemoveReferencePattern = 'The Create-Remove-Reference-Pattern rule adds the low-level '
 + 'change node (which were omited in the Matchin-Phase)  and assigns the pattern to the LHS of the recognition-rule. As the source and target of an edge (in the edit-rule) can be the same node, the rule '
 + 'is matched non-injective [Injective Matching = false].';

var CreateAttributeValueChangePattern = 'The Create-Attribute-Value-Change-Pattern rule adds the low-level change node (which were omited in the Matchin-Phase) '
 + 'and assigns the pattern to the LHS of the recognition-rule.';

// MirrorPhase

var MirrorNodesLHStoRHS = 'Mirror-Nodes-LHS-to-RHS rules copies all nodes from the LHS graph to RHS graph of the recognition-rule.';

var MirrorAttributeLHStoRHS = 'Mirror-Attribute-LHS-to-RHS rule copies all attributes from the LHS graph to RHS graph of the recognition-rule.';

var MirrorEdgesLHStoRHS = 'Mirror-Edges-LHS-to-RHS rule copies all edges from the LHS graph to RHS graph of the recognition-rule. As the source '
 + 'and target of an edge (in the edit-rule) can be the same node, the rule is matched non-injective [Injective Matching = false].';

// GroupingPhase

var CreateChangeSet = 'Create-Change-Set rule creates the difference and the semantic change set node into the recognition-rule.';
var LinkChangesToChangeSet = 'Link-Changes-To-ChangeSet rule creates the edges which connects the semantic change set with the low-level change nodes.';