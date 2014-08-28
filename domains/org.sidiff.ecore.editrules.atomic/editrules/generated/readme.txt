Removed the following Rules (because we cannot adjust this in SERGe):
(please note the wildcard symbol in the rule file names)
- SET_ATTRIBUTE_EClassifier_*_execute.henshin
- SET_ATTRIBUTE_ENamedElement_*_execute.henshin
- SET_ATTRIBUTE_EStructuralFeature_*.henshin
- SET_ATTRIBUTE_ETypedElement_*_execute.henshin
- SET_REFERENCE_EStructuralFeature_*_TGT_EClassifier_execute.henshin
- SET_REFERENCE_ETypedElement_*_TGT_EClassifier_execute.henshin
- UNSET_REFERENCE_EStructuralFeature_(eType)_TGT_EClassifier_execute.henshin
- UNSET_REFERENCE_ETypedElement_(eType)_TGT_EClassifier_execute.henshin

Removed the following rules
(because we have not en effective meta-model available
in an effective meta-model, we could declare EReferece ->(eType)-> EClass with multiplicity 1..1
- CREATE_EReference_IN_EClass_(eStructuralFeatures)_execute.henshin
- DELETE_EReference_IN_EClass_(eStructuralFeatures)_execute.henshin
- MOVE_EReference_FROM_EClass_(eStructuralFeatures)_TO_EClass_(eStructuralFeatures)_execute.henshin
- SET_REFERENCE_EReference_(eType)_TGT_EClassifier_execute.henshin
- UNSET_REFERENCE_EReference_(eType)_TGT_EClassifier_execute.henshin



=> After SERGe generation, go to your rule directory and execute this:

cd ./SET
rm SET_ATTRIBUTE_EClassifier_*_execute.henshin
rm SET_ATTRIBUTE_ENamedElement_*_execute.henshin
rm SET_ATTRIBUTE_EStructuralFeature_*.henshin
rm SET_ATTRIBUTE_ETypedElement_*_execute.henshin
rm SET_REFERENCE_EStructuralFeature_*_TGT_EClassifier_execute.henshin
rm SET_REFERENCE_ETypedElement_*_TGT_EClassifier_execute.henshin
cd ../UNSET
rm UNSET_REFERENCE_EStructuralFeature_*_TGT_EClassifier_execute.henshin
rm UNSET_REFERENCE_ETypedElement_*_TGT_EClassifier_execute.henshin
cd ../CREATE 
rm 'CREATE_EReference_IN_EClass_(eStructuralFeatures)_execute.henshin'
cd ../DELETE 
rm 'DELETE_EReference_IN_EClass_(eStructuralFeatures)_execute.henshin'
cd ../MOVE
rm 'MOVE_EReference_FROM_EClass_(eStructuralFeatures)_TO_EClass_(eStructuralFeatures)_execute.henshin'
cd ../SET
rm 'SET_REFERENCE_EReference_(eType)_TGT_EClassifier_execute.henshin'
cd ../UNSET
rm 'UNSET_REFERENCE_EReference_(eType)_TGT_EClassifier_execute.henshin'
cd ..