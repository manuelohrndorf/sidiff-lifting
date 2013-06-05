  /**
   * Validates the ValidDefaultValueLiteral constraint of '<em>EStructural Feature</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEStructuralFeature_ValidDefaultValueLiteral(EStructuralFeature eStructuralFeature, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    String defaultValueLiteral = eStructuralFeature.getDefaultValueLiteral();
    Object defaultValue = null;
    EDataType eDataType = null;
    boolean result = true;
    if (defaultValueLiteral != null)
    {
      EClassifier eType = eStructuralFeature.getEType();
      if (eType instanceof EDataType)
      {
        eDataType = (EDataType)eType;
        defaultValue = eStructuralFeature.getDefaultValue();
        if (defaultValue == null)
        {
          // We need to be conservative and diagnose a problem only if we are quite sure that type is built-in 
          // and hence that the lack of a default value really represents a problem with being unable to convert the literal to a value.
          // 
          result = !isBuiltinEDataType(eDataType);
        }
        else
        {
          result = getRootEValidator(context).validate(eDataType, defaultValue, null, context);
        }
      }
      else
      {
        result = false;
      }
    }
    if (diagnostics != null && !result)
    {
      BasicDiagnostic diagnostic =
        createDiagnostic
         (Diagnostic.ERROR,
          DIAGNOSTIC_SOURCE,
          VALID_LOWER_BOUND,
          "_UI_EStructuralFeatureValidDefaultValueLiteral_diagnostic",
          new Object[] { defaultValueLiteral },
          new Object[] { eStructuralFeature },
          context);
      if (defaultValue != null)
      {
        getRootEValidator(context).validate(eDataType, defaultValue, diagnostic, context);
      }
      diagnostics.add(diagnostic);
    }
    return result;
  }