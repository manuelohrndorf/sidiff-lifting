/**
   * Validates the WellFormedName constraint of '<em>ENamed Element</em>'.
   * <!-- begin-user-doc -->
   * The name must be a valid Java identifier.
   * I.e., it must start with a {@link Character#isJavaIdentifierStart(int) Java identifier start character},
   * that is followed by zero or more {@link Character#isJavaIdentifierPart(int) Java identifier part characters}.
   * This constraint is only enforced in a {@link #STRICT_NAMED_ELEMENT_NAMES} context.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateENamedElement_WellFormedName(ENamedElement eNamedElement, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    if (context != null && Boolean.FALSE.equals(context.get(STRICT_NAMED_ELEMENT_NAMES)))
    {
      return true;
    }

    boolean result = false;
    String name = eNamedElement.getName();
    if (name != null)
    {
      int length = name.length();
      if (length > 0 && Character.isJavaIdentifierStart(name.codePointAt(0)))
      {
        result = true;
        for (int i = Character.offsetByCodePoints(name, 0, 1); i < length; i = Character.offsetByCodePoints(name, i, 1))
        {
          if (!Character.isJavaIdentifierPart(name.codePointAt(i)))
          {
            result = false;
            break;
          }
        }
      }
    }

    if (!result && diagnostics != null)
    {
      diagnostics.add
        (createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           WELL_FORMED_NAME,
           "_UI_ENamedElementNameNotWellFormed_diagnostic",
           new Object[] { getValueLabel(EcorePackage.Literals.ESTRING, name, context) },
           new Object[] { eNamedElement },
           context));
    }
    return result;
  }