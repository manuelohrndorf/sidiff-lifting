 /**
   * Validates the ConsistentTransient constraint of '<em>EAttribute</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
  */
public boolean validateEAttribute_ConsistentTransient(EAttribute eAttribute, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    EDataType eAttributeType = eAttribute.getEAttributeType();
    boolean result = 
      isEffectivelyTransient(eAttribute) ||
        eAttributeType == null || 
        eAttributeType.isSerializable() ||
        FeatureMapUtil.isFeatureMapEntry(eAttributeType);
    if (!result && diagnostics != null)
    {
      String attributeName = eAttribute.getName();
      if (eAttribute.getEContainingClass() != null)
      {
        attributeName = eAttribute.getEContainingClass().getName() + "." + attributeName;
      }
      diagnostics.add
        (createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           CONSISTENT_TRANSIENT,
           "_UI_EAttributeConsistentTransient_diagnostic", 
           new String[] {attributeName},
           new Object[] { eAttribute },
           context));
    }
    return result;
  }  