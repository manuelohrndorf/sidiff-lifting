Modifications to generated edit rules:

Deleted:
- MOVE_ExcludeConstraint_FROM_FeatureModel_(constraints)_TO_FeatureModel_(constraints)_execute.henshin
- MOVE_Group_FROM_FeatureModel_(groups)_TO_FeatureModel_(groups)_execute.henshin
- MOVE_RequireConstraint_FROM_FeatureModel_(constraints)_TO_FeatureModel_(constraints)_execute.henshin
(We do not move elements across different feature models)

Deleted:
- SET_ATTRIBUTE_Constraint_Name_execute.henshin
- SET_ATTRIBUTE_Feature_Name_execute.henshin
- SET_ATTRIBUTE_Group_Name_execute.henshin
- SET_ATTRIBUTE_NamedElement_Name_execute.henshin
(These set operations will never occur because names serve as ID for elements of these types)

