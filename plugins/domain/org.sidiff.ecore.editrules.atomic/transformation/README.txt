
The following transformations were moved from the "generated" to the "notUsedByEditor"
folder because the Ecore Diagram Editor does not support the actions done in the
listed transformations - allthough they are derived from the meta model.

CREATE_EObjectInEAnnotation_execute.henshin
DELETE_EObjectInEAnnotation_execute.henshin
MOVE_EObject_Ref_contents_To_EAnnotation_execute.henshin

*********************************************************************************
*********************************************************************************

The following transformations are deleted from the "generated" folder
since they are adjusted manually and therefore listed under "manual".

The manual adjustment is the setting of the eOpposite-Reference edge to the
eOpposite-Reference edge.

SET_EReference_Ref_eOpposite_tgt_EReference_execute.henshin:
1. Rückrichtung von eOpposite wird ebenfalls gesetzt
2. injective-matching = false (reflexive EReferences möglich)
3. PAC: Für zwei eOpposite-Referenzen r1 und r2 muss gelten: r1.tgt = r2.src UND r1.src = r2.tgt

UNSET_EReference_Ref_eOpposite_tgt_EReference_execute.henshin:
1. Rückrichtung von eOpposite wird ebenfalls gesetzt
2. injective-matching = false (reflexive EReferences möglich)
Bemerkungen: 
* Hier PAC nicht explizit spezifiziert (folgt aus eOpposites)

*********************************************************************************
*********************************************************************************
Constraints beachten: 
EReferences, EAttributes und EParameters haben immer einen eType.

Betroffene Regeln (EReference): 
SET_EReference_Ref_eType_tgt_EClassifier -> entfällt 
UNSET_EReference_Ref_eType_tgt_EClassifier -> entfällt 
CHANGE_EReference_Ref_eType_tgt_EClassifier -> neu						
CREATE_EReferenceInEClass -> anpassen (eType wird ebenfalls gesetzt)	
DELETE_EReferenceInEClass-> anpassen (eType wird ebenfalls gelöscht)	

Betroffene Regeln (EAttribute):
SET_EAttribute_Ref_eType_tgt_EClassifier -> entfällt 
UNSET_EAttribute_Ref_eType_tgt_EClassifier -> entfällt 
CHANGE_EAttribute_Ref_eType_tgt_EClassifier -> neu 							
CREATE_EAttributeInEClass -> anpassen (eType wird ebenfalls gesetzt) 	
DELETE_EAttributeInEClass-> anpassen (eType wird ebenfalls gelöscht)	

Betroffene Regeln (EParameter):
SET_EParameter_Ref_eType_tgt_EClassifier -> entfällt 
UNSET_EParameter_Ref_eType_tgt_EClassifier -> entfällt 
CHANGE_EParameter_Ref_eType_tgt_EClassifier -> neu 							
CREATE_EParameterInEClass -> anpassen (eType wird ebenfalls gesetzt) 	
DELETE_EParameterInEClass-> anpassen (eType wird ebenfalls gelöscht)	
*********************************************************************************
*********************************************************************************
Nicht-injektives Matching

CREATE_EReferenceInEClass:
 Selected:EClass und EReferenceType:EClass dürfen auf das gleiche Objekt gemateched
 werden (Eine EClass hat eine EReference auf sich selbst).
 Daher muss die Regel nicht-injektiv gematched werden
 
Ebenso:
DELETE_EReferenceInEClass
*********************************************************************************
*********************************************************************************
Aus eOpposite (upperBound=1 UND "gegenüberliegend") resultiert NAC, davon betroffene Regeln:
- SET_EReference_Ref_eOpposite_tgt_EReference
- MOVE_EReference_Ref_eStructuralFeatures_To_EClass
- CHANGE_EReferenceType
- DELETE_EReferenceInEClass
*********************************************************************************
*********************************************************************************
Komplett neu hinzugefügt:
- moveOppositeReference
- changeOppositeReferenceType
Beides sind atomare Regeln die benötigt werden, um transiente Effekte abzufangen!
*********************************************************************************
*********************************************************************************
The following transformations are deleted from the "generated" folder
since they are adjusted manually and therefore listed under "manual", this
has been done because of the EMF Constraints which are implemented directly into 
the corresponding editrules.

(See doc/constraints/Constraintslist.txt for more detailed information)

- SET_EClass_Abstract
- SET_EClass_Interface
- CREATE_EClassInEPackage
- SET_EAttribute_ID
- MOVE_EParameter_Ref_EParameters_To_EOperation
- SET_EEnumLiteral_Name
- MOVE_EEnumLiteral_Ref_eLiterals_to_EEnum
- SET_EParameter_Name
- CREATE_EENumLiteralInEENum
- SET_EEnumLiteral_Literal
- CREATE_EParameterInEOperation
- CREATE_EENumInEPackage
- MOVE_EPackage_Ref_eSubpackages_To_EPackage
- MOVE_EDataType_Ref_eClassifiers_To_EPackage
- CREATE_EDataTypeInEPackage
- MOVE_EENum_Ref_EClassifiers_To_EPackage
- CREATE_EPackageInEPackage
- SET_EPackage_Name
- MOVE_EClass_Ref_eClassifiers_To_EPackage

The following manual rules are replacing the / corresponding to the 
deleted ones above:

(new)
- SET_EClass_Abstract
- SET_EClass_AbstractNOT
- SET_EClass_Interface
- SET_EClass_InterfaceNOT
- CREATE_EClassInEPackage
- CREATE_ECLassAsInterfaceInEPackage
- CHANGE_EClassToInterfaceInEPackage
- SET_EAttribute_ID
- SET_EAttribute_ID_NOT
- CREATE_EAttributeAsIDInEClass
- CREATE_EENumLiteralInEENum
- SET_EEnumLiteral_Literal
- MOVE_EParameter_Ref_EParameters_To_EOperation
- SET_EEnumLiteral_Name
- MOVE_EEnumLiteral_Ref_eLiterals_to_EEnum
- SET_EParameter_Name
- CREATE_EPackageInEPackage
- MOVE_EClass_Ref_eClassifiers_To_EPackage
- SET_EPackage_Name
- MOVE_EDataType_Ref_eClassifiers_To_EPackage
- CREATE_EENumInEPackage
- MOVE_EPackage_Ref_eSubpackages_To_EPackage
- MOVE_EENum_Ref_EClassifiers_To_EPackage
- CREATE_EDataTypeInEPackage

(edited)
- CREATE_EAttributeInEClass
- CREATE_EParameterInEOperation
- CREATE_EClassInEPackage
*********************************************************************************
*********************************************************************************
