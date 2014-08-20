 /**
   * Validates the UniqueParameterNames constraint of '<em>EOperation</em>'.
   * <!-- begin-user-doc -->
   * No two parameters may have the same name.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEOperation_UniqueParameterNames(EOperation eOperation, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    List<String> names = new ArrayList<String>();
    EList<EParameter> eParameters = eOperation.getEParameters();
    for (EParameter eParameter : eParameters)
    {
      String name = eParameter.getName();
      if (name != null)
      {
        int index = names.indexOf(name);
        if (index != -1)
        {
          if (diagnostics == null)
          {
            return false;
          }
          else
          {
            result = false;
            EParameter otherEParameter = eParameters.get(index);
            diagnostics.add
              (createDiagnostic
                (Diagnostic.ERROR,
                 DIAGNOSTIC_SOURCE,
                 UNIQUE_PARAMETER_NAMES,
                 "_UI_EOperationUniqueParameterNames_diagnostic", 
                 new Object[] { name },
                 new Object[] { eOperation, eParameter, otherEParameter },
                 context));
          }
        }
      }
      names.add(name);
    }
    return result;
  }

  /**
   * Validates the UniqueTypeParameterNames constraint of '<em>EOperation</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEOperation_UniqueTypeParameterNames(EOperation eOperation, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    List<String> names = new ArrayList<String>();
    EList<ETypeParameter> eTypeParameters = eOperation.getETypeParameters();
    for (ETypeParameter eTypeParameter : eTypeParameters)
    {
      String name = eTypeParameter.getName();
      if (name != null)
      {
        int index = names.indexOf(name);
        if (index != -1)
        {
          if (diagnostics == null)
          {
            return false;
          }
          else
          {
            result = false;
            ETypeParameter otherETypeParameter = eTypeParameters.get(index);
            diagnostics.add
              (createDiagnostic
                (Diagnostic.ERROR,
                 DIAGNOSTIC_SOURCE,
                 UNIQUE_TYPE_PARAMETER_NAMES,
                 "_UI_UniqueTypeParameterNames_diagnostic", 
                 new Object[] { name },
                 new Object[] { eOperation, eTypeParameter, otherETypeParameter },
                 context));
          }
        }
      }
      names.add(name);
    }
    return result;
  }

  /**
   * Validates the NoRepeatingVoid constraint of '<em>EOperation</em>'.
   * <!-- begin-user-doc -->
   * An operation without a type, which represents void, must have an upper bound of 1.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEOperation_NoRepeatingVoid(EOperation eOperation, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    int upperBound = eOperation.getUpperBound();
    boolean result = upperBound == 1 || eOperation.getEType() != null;
    if (!result && diagnostics != null)
    {
      diagnostics.add
        (createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           NO_REPEATING_VOID,
           "_UI_EOperationNoRepeatingVoid_diagnostic", 
           new Object [] { upperBound },
           new Object[] { eOperation },
           context));
    }
    return result;
  }