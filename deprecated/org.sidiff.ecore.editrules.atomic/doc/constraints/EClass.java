import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreValidator.EOperationSignatureValidator;

/**
 * Validates the InterfaceIsAbstract constraint of '<em>EClass</em>'.
 * <!-- begin-user-doc -->
 * A class that is an interface must be abstract.
 * <!-- end-user-doc -->
 * @generated NOT
 */
public boolean validateEClass_InterfaceIsAbstract(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = !eClass.isInterface() || eClass.isAbstract();
    if (!result && diagnostics != null)
    {
      diagnostics.add
        (createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           INTERFACE_IS_ABSTRACT,
           "_UI_EClassInterfaceNotAbstract_diagnostic",
           null,
           new Object[] { eClass },
           context));
    }
    return result;
  }

/**
 * Validates the AtMostOneID constraint of '<em>EClass</em>'.
 * <!-- begin-user-doc -->
 * There can be at most one attribute that is an ID.
 * <!-- end-user-doc -->
 * @generated NOT
 */
public boolean validateEClass_AtMostOneID(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context)
{
  boolean result = true;
  EAttribute eIDAttribute = eClass.getEIDAttribute();

  // A document root can have multiple ID attributes because there can be multiple global element or attribute declarations of type ID 
  // and these will be the ID in the complex types that reference them,
  // i.e., they aren't really the ID of the document root.
  //
  if (eIDAttribute != null && !ExtendedMetaData.INSTANCE.isDocumentRoot(eClass))
  {
    LOOP:
    for (EAttribute eAttribute : eClass.getEAllAttributes())
    {
      if (eAttribute.isID() && eIDAttribute != eAttribute)
      {
        result = false;
        if (diagnostics == null)
        {
          break;
        }
        else
        {
          // We do not want to diagnose any errors that have already been diagnosed by a super type.
          // Although we ignore all the first super's features, there may be mixin classes that still would result in duplicates.
          //
          for (EClass eSuperType : eClass.getESuperTypes())
          {
            EList<EStructuralFeature> eAllStructuralFeatures = eSuperType.getEAllStructuralFeatures();
            if (eAllStructuralFeatures.contains(eIDAttribute) && eAllStructuralFeatures.contains(eAttribute))
            {
              continue LOOP;
            }
          }
          diagnostics.add
            (createDiagnostic
              (Diagnostic.ERROR,
               DIAGNOSTIC_SOURCE,
               AT_MOST_ONE_ID,
               "_UI_EClassAtMostOneID_diagnostic",
               new Object[] { getFeatureLabel(eIDAttribute, context), getFeatureLabel(eAttribute, context) },
               new Object[] { eClass, eAttribute, eIDAttribute },
               context));
        }
      }
    }
  }
  return result;
}

/**
 * Validates the UniqueFeatureNames constraint of '<em>EClass</em>'.
 * <!-- begin-user-doc -->
 * No two features may have matching names.
 * Feature names are matched ignoring their case and their underscore separators.
 * It is an error to have two features with names that are equal but only a warning to have two features with names that match.
 * <!-- end-user-doc -->
 * @generated NOT
 */
public boolean validateEClass_UniqueFeatureNames(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context)
{
  boolean result = true;
  int size = eClass.getFeatureCount();
  if (size > 0)
  {
    // For performance, skip matching all the features of the first super type.
    //
    int start = 0;
    EList<EClass> eSuperTypes = eClass.getESuperTypes();
    if (!eSuperTypes.isEmpty())
    {
      start = eSuperTypes.get(0).getEAllStructuralFeatures().size();
    }

    // Build a list of the keys
    //
    ArrayList<String> keys = new ArrayList<String>();
    LOOP:
    for (int i = 0; i < size; ++i)
    {
      EStructuralFeature eStructuralFeature = eClass.getEStructuralFeature(i);
      String name = eStructuralFeature.getName();
      if (name == null)
      {
        // Don't bother complaining about things with no name,
        // since there are constraints for that problem.
        //
        keys.add(null);
      }
      else
      {
        // Drop the _ separators and normalize the case.
        //
        String key = name.replace("_", "").toLowerCase();

        // Ignore features from the first super type.
        //
        if (i >= start)
        {
          int index = keys.indexOf(key);
          if (index != -1)
          {
            if (diagnostics == null)
            {
              return false;
            }
            else
            {
              result = false;

              EStructuralFeature otherEStructuralFeature = eClass.getEStructuralFeature(index);

              // We do not want to diagnose any errors that have already been diagnosed by a super type.
              // Although we ignore all the first super's features, there may be mixin classes that still would result in duplicates.
              //
              for (EClass eSuperType : eSuperTypes)
              {
                EList<EStructuralFeature> eAllStructuralFeatures = eSuperType.getEAllStructuralFeatures();
                if (eAllStructuralFeatures.contains(eStructuralFeature) && eAllStructuralFeatures.contains(otherEStructuralFeature))
                {
                  continue LOOP;
                }
              }

              // Produce different levels of diagnostic depending whether the names are exactly the same or only matching.
              //
              String otherName = otherEStructuralFeature.getName();
              diagnostics.add
                (createDiagnostic
                  (name.equals(otherName) ? Diagnostic.ERROR : Diagnostic.WARNING,
                   DIAGNOSTIC_SOURCE,
                   UNIQUE_FEATURE_NAMES,
                   name.equals(otherName) ? "_UI_EClassUniqueEStructuralFeatureName_diagnostic" : "_UI_EClassDissimilarEStructuralFeatureName_diagnostic",
                   name.equals(otherName) ?  new Object[] { name } :  new Object[] { name, otherName },
                   new Object[] { eClass, eStructuralFeature, otherEStructuralFeature },
                   context));
            }
          }
        }
        keys.add(key);
      }
    }
  }
  return result;
}
/**
 * Validates the UniqueOperationSignatures constraint of '<em>EClass</em>'.
 * <!-- begin-user-doc -->
 * No two operations defined in the same class may have matching signatures.
 * The signature is determined by the name of the operation and the types of its parameters.
 * If the name is the same and the types match, the signatures match.
 * Types match if they are the same classifier, or both classifiers have instance class names that are the same.
 * <!-- end-user-doc -->
 * @generated NOT
 */
public boolean validateEClass_UniqueOperationSignatures(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context)
{
  return uniqueOperationSignaturesValidator.validateEOperationSignatures(eClass, eClass.getEOperations(), eClass.getEOperations(), diagnostics, context);
}

/**
 * Validates the NoCircularSuperTypes constraint of '<em>EClass</em>'.
 * <!-- begin-user-doc -->
 * A super type must not appear in its own list of all super types.
 * <!-- end-user-doc -->
 * @generated NOT
 */
public boolean validateEClass_NoCircularSuperTypes(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context)
{
  EList<EClass> eAllSuperTypes = eClass.getEAllSuperTypes();
  boolean result = !eAllSuperTypes.contains(eClass);
  if (result)
  {
    for (EClass otherEClass : eAllSuperTypes)
    {
      if (otherEClass.getEAllSuperTypes().contains(eClass))
      {
        result = false;
        break;
      }
    }
  }
  if (!result && diagnostics != null)
  {
    diagnostics.add
      (createDiagnostic
        (Diagnostic.ERROR,
         DIAGNOSTIC_SOURCE,
         NO_CIRCULAR_SUPER_TYPES,
         "_UI_EClassNoCircularSuperTypes_diagnostic",
         null,
         new Object[] { eClass },
         context));
  }
  return result;
}

/**
 * Validates the WellFormedMapEntryClass constraint of '<em>EClass</em>'.
 * <!-- begin-user-doc -->
 * A map entry class must have features named 'key' and 'value'.
 * <!-- end-user-doc -->
 * @generated NOT
 */
public boolean validateEClass_WellFormedMapEntryClass(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context)
{
  boolean result = true;
  if (eClass.getInstanceClassName() == "java.util.Map$Entry")
  {
    EStructuralFeature keyFeature = eClass.getEStructuralFeature("key");
    if (keyFeature == null)
    {
      if (diagnostics == null)
      {
        return false;
      }
      else
      {
        result = false;
        diagnostics.add
          (createDiagnostic
            (Diagnostic.ERROR,
             DIAGNOSTIC_SOURCE,
             WELL_FORMED_MAP_ENTRY_CLASS,
             "_UI_EClassNotWellFormedMapEntry_diagnostic",
             new Object[] { "key" },
             new Object[] { eClass },
             context));
      }
    }
    EStructuralFeature valueFeature = eClass.getEStructuralFeature("value");
    if (valueFeature == null)
    {
      if (diagnostics == null)
      {
        return false;
      }
      else
      {
        result = false;
        diagnostics.add
          (createDiagnostic
            (Diagnostic.ERROR,
             DIAGNOSTIC_SOURCE,
             WELL_FORMED_MAP_ENTRY_CLASS,
             "_UI_EClassNotWellFormedMapEntry_diagnostic", 
             new Object[] { "value" },
             new Object[] { eClass },
             context));
      }
    }
  }
  else
  {
    for (EClass eSuperType : eClass.getEAllSuperTypes())
    {
      if (eSuperType.getInstanceClassName() == "java.util.Map$Entry")
      {
        if (diagnostics == null)
        {
          return false;
        }
        else
        {
          result = false;
          diagnostics.add
            (createDiagnostic
              (Diagnostic.ERROR,
               DIAGNOSTIC_SOURCE,
               WELL_FORMED_MAP_ENTRY_NO_INSTANCE_CLASS_NAME,
               "_UI_EClassNotWellFormedMapEntryNoInstanceClassName_diagnostic",
               null,
               new Object[] { eClass },
               context));
        }
      }
    }
  }
  return result;
}

/**
 * Validates the ConsistentSuperTypes constraint of '<em>EClass</em>'.
 * <!-- begin-user-doc -->
 * The same class must not occur more than once among the generic super types
 * nor among all the generic super types
 * where occurrences in the latter represent conflicting instantiations of the same classifier.
 * <!-- end-user-doc -->
 * @generated NOT
 */
public boolean validateEClass_ConsistentSuperTypes(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context)
{
  boolean result = true;

  // Maintain a list of classifiers for looking up conflicts.
  //
  ArrayList<EClassifier> superTypes = new ArrayList<EClassifier>();

  // Look for duplicates among the generic super types.
  //
  EList<EGenericType> eGenericSuperTypes = eClass.getEGenericSuperTypes();
  for (EGenericType eGenericSuperType : eGenericSuperTypes)
  {
    // Ignore it if it isn't a class. Not being a class is diagnosed for the generic type itself.
    //
    EClassifier eClassifier = eGenericSuperType.getEClassifier();
    if (eClassifier instanceof EClass)
    {
      int index = superTypes.indexOf(eClassifier);
      if (index != -1)
      {
        if (diagnostics == null)
        {
          return false;
        }
        else
        {
          result = false;
          diagnostics.add
            (createDiagnostic
              (Diagnostic.ERROR,
               DIAGNOSTIC_SOURCE,
               CONSISTENT_SUPER_TYPES_DUPLICATE,
               "_UI_EClassNoDuplicateSuperTypes_diagnostic",
               new Object [] { eGenericSuperTypes.indexOf(eGenericSuperType), index },
               new Object[] { eClass, eGenericSuperType,  eGenericSuperTypes.get(index) },
               context));
        }
      }
    }
    superTypes.add(eClassifier);
  }

  if (result)
  {
    superTypes.clear();
    EList<EGenericType> eAllGenericSuperTypes = eClass.getEAllGenericSuperTypes();
    for (EGenericType eGenericSuperType : eAllGenericSuperTypes)
    {
      EClassifier eClassifier = eGenericSuperType.getEClassifier();
      if (eClassifier instanceof EClass)
      {
        int index = superTypes.indexOf(eClassifier);
        if (index != -1)
        {
          if (diagnostics == null)
          {
            return false;
          }
          else
          {
            result = false;
            diagnostics.add
              (createDiagnostic
                (Diagnostic.ERROR,
                 DIAGNOSTIC_SOURCE,
                 CONSISTENT_SUPER_TYPES_CONFLICT,
                 "_UI_EClassConsistentSuperTypes_diagnostic",
                 new Object [] { getObjectLabel(eClassifier, context) },
                 new Object[] { eClass, eGenericSuperType,  eAllGenericSuperTypes.get(index) },
                 context));
          }
        }
      }
      superTypes.add(eClassifier);
    }
  }
  return result;
}

/**
 * Validates the DisjointFeatureAndOperationSignatures constraint of '<em>EClass</em>'.
 * <!-- begin-user-doc -->
 * Each feature defined in the class is 
 * interpreted as implicitly defining the operations 
 * with the signatures corresponding to the generated accessors for that feature
 * hence the same type of constraint as {@link #validateEClass_UniqueOperationSignatures(EClass, DiagnosticChain, Map)} applies.
 * <!-- end-user-doc -->
 * @generated NOT
 */
public boolean validateEClass_DisjointFeatureAndOperationSignatures(EClass eClass, DiagnosticChain diagnostics, Map<Object, Object> context)
{
  boolean result = true;
  EList<EOperation> eOperations = eClass.getEOperations();
  final Map<EOperation, EStructuralFeature> implicitEOperationToEStructuralFeatureMap = new LinkedHashMap<EOperation, EStructuralFeature>();
  if (!eOperations.isEmpty())
  {
    for (EStructuralFeature eStructuralFeature : eClass.getEStructuralFeatures())
    {
      String featureName = eStructuralFeature.getName();
      EClassifier eType = eStructuralFeature.getEType();
      if (featureName != null && featureName.length() != 0 && eType != null)
      {
        featureName = featureName.substring(0,1).toUpperCase() + featureName.substring(1);
        if (!EcoreUtil.isSuppressedVisibility(eStructuralFeature, EcoreUtil.GET))
        {
          String getAccessor = (eStructuralFeature.isMany() || !"boolean".equals(eType.getInstanceClassName()) ? "get" : "is") + featureName;
          if ("getClass".equals(getAccessor))
          {
            getAccessor = "getClass_";
          }
          EOperation eOperation = EcoreFactory.eINSTANCE.createEOperation();
          eOperation.setName(getAccessor);
          eOperation.setUpperBound(eStructuralFeature.getUpperBound());
          eOperation.setOrdered(eStructuralFeature.isOrdered());
          eOperation.setUnique(eStructuralFeature.isUnique());
          eOperation.setEType(eType);
          implicitEOperationToEStructuralFeatureMap.put(eOperation, eStructuralFeature);
        }
        if (!eStructuralFeature.isMany() && eStructuralFeature.isChangeable() && !EcoreUtil.isSuppressedVisibility(eStructuralFeature, EcoreUtil.SET))
        {
          String setAccessor = "set" + featureName;
          EOperation eOperation = EcoreFactory.eINSTANCE.createEOperation();
          eOperation.setName(setAccessor);
          EParameter eParameter = EcoreFactory.eINSTANCE.createEParameter();
          eParameter.setName(featureName);
          eParameter.setEType(eType);
          eOperation.getEParameters().add(eParameter);
          implicitEOperationToEStructuralFeatureMap.put(eOperation, eStructuralFeature);
        }
        if (eStructuralFeature.isUnsettable())
        {
          if (!EcoreUtil.isSuppressedVisibility(eStructuralFeature, EcoreUtil.IS_SET))
          {
            String isSetAccessor =  "isSet" + featureName;
            EOperation eOperation = EcoreFactory.eINSTANCE.createEOperation();
            eOperation.setName(isSetAccessor);
            eOperation.setEType(EcorePackage.Literals.EBOOLEAN);
            implicitEOperationToEStructuralFeatureMap.put(eOperation, eStructuralFeature);
          }
          if (!EcoreUtil.isSuppressedVisibility(eStructuralFeature, EcoreUtil.UNSET))
          {
            String unsetAccessor =  "unset" + featureName;
            EOperation eOperation = EcoreFactory.eINSTANCE.createEOperation();
            eOperation.setName(unsetAccessor);
            implicitEOperationToEStructuralFeatureMap.put(eOperation, eStructuralFeature);
          }
        }
      }
    }

    result =
      new EOperationSignatureValidator("_UI_EClassDisjointFeatureAndOperationSignatures_diagnostic", DISJOINT_FEATURE_AND_OPERATION_SIGNATURES, true)
      {
        @Override
        protected EModelElement getTarget(EOperation otherEOperation)
        {
          return implicitEOperationToEStructuralFeatureMap.get(otherEOperation);
        }
      }.validateEOperationSignatures(eClass, eOperations, implicitEOperationToEStructuralFeatureMap.keySet(), diagnostics, context);
  }
  return result;
}