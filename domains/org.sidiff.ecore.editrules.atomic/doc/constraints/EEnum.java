  /**
   * Validates the UniqueEnumeratorNames constraint of '<em>EEnum</em>'.
   * <!-- begin-user-doc -->
   * No two enum literals may have matching names.
   * Literal names are matched ignoring their case and their underscore separators.
   * It is an error to have two enum literals with names that are equal but only a warning to have two enum literals with names that match.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEEnum_UniqueEnumeratorNames(EEnum eEnum, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    List<String> names = new ArrayList<String>();
    EList<EEnumLiteral> eLiterals = eEnum.getELiterals();
    for (EEnumLiteral eEnumLiteral : eLiterals)
    {
      String name = eEnumLiteral.getName();
      if (name == null)
      {
        names.add(null);
      }
      else
      {
        String key = name.replace("_", "").toUpperCase();
        int index = names.indexOf(key);
        if (index != -1)
        {
          if (diagnostics == null)
          {
           return false;
          }
          else
          {
            result = false;
            EEnumLiteral otherEEnumLiteral = eLiterals.get(index);
            String otherName = otherEEnumLiteral.getName();
            diagnostics.add
              (createDiagnostic
                (name.equals(otherName) ? Diagnostic.ERROR : Diagnostic.WARNING,
                 DIAGNOSTIC_SOURCE,
                 UNIQUE_ENUMERATOR_NAMES,
                 name.equals(otherName) ? "_UI_EEnumUniqueEnumeratorNames_diagnostic" : "_UI_EEnumDissimilarEnumeratorNames_diagnostic",
                 name.equals(otherName) ? new Object[] { name } : new Object[] { name, otherName },
                 new Object[] { eEnum, eEnumLiteral, otherEEnumLiteral },
                 context));
          }
        }
        names.add(key);
      }
    }
    return result;
  }

  /**
   * Validates the UniqueEnumeratorLiterals constraint of '<em>EEnum</em>'.
   * <!-- begin-user-doc -->
   * No two enum literals may have the same literal value.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEEnum_UniqueEnumeratorLiterals(EEnum eEnum, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    List<String> literals = new ArrayList<String>();
    EList<EEnumLiteral> eLiterals = eEnum.getELiterals();
    for (EEnumLiteral eEnumLiteral : eLiterals)
    {
      String literal = eEnumLiteral.getLiteral();
      if (literal != null)
      {
        int index = literals.indexOf(literal);
        if (index != -1)
        {
          if (diagnostics == null)
          {
            return false;
          }
          else
          {
            result = false;
            EEnumLiteral otherEEnumLiteral = eLiterals.get(index);
            // Don't complain about the literals if they are the same as the names and the names collide.
            //
            String name = eEnumLiteral.getName();
            if (name == null || !name.equals(literal) || !name.equals(otherEEnumLiteral.getName()))
            {
              diagnostics.add
                (createDiagnostic
                  (Diagnostic.ERROR,
                   DIAGNOSTIC_SOURCE,
                   UNIQUE_ENUMERATOR_LITERALS,
                   "_UI_EEnumUniqueEnumeratorLiterals_diagnostic", 
                   new Object[] { literal },
                   new Object[] { eEnum, eEnumLiteral, otherEEnumLiteral },
                   context));
            }
          }
        }
      }
      literals.add(literal);
    }
    return result;
  }