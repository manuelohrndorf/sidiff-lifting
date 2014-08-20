 /**
   * Validates the WellFormedInstanceTypeName constraint of '<em>EClassifier</em>'.
   * <!-- begin-user-doc -->
   * The instance type name may be null only for a class or an enum
   * and must be {@link EGenericTypeBuilder#parseInstanceTypeName(String) well formed} when not null.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEClassifier_WellFormedInstanceTypeName(EClassifier eClassifier, DiagnosticChain diagnostics, final Map<Object, Object> context)
  {
    String instanceTypeName = eClassifier.getInstanceTypeName();
    Diagnostic typeBuilderDiagnostic = 
      instanceTypeName == null ? 
        null : 
        new EGenericTypeBuilder()
        {
          @Override
          protected BasicDiagnostic createDiagnostic(int severity, String source, int code, String messageKey, Object[] messageSubstitutions, Object[] data)
          {
            return EcoreValidator.this.createDiagnostic(severity, source, code, messageKey, messageSubstitutions, data, context);
          }

          @Override
          protected ResourceLocator getResourceLocator()
          {
            return EcoreValidator.this.getResourceLocator();
          }

          @Override
          protected String getString(String key, Object[] substitutions)
          {
            return EcoreValidator.this.getString(key, substitutions);
          }

          @Override
          protected void report(DiagnosticChain diagnostics, String key, Object[] substitutions, int index)
          {
            EcoreValidator.this.report(diagnostics, key, substitutions, index, context);
          }
        }.parseInstanceTypeName(instanceTypeName);
    String formattedName = null;
    boolean result =
      instanceTypeName != null ?
        typeBuilderDiagnostic.getSeverity() == Diagnostic.OK  && 
          instanceTypeName.equals(formattedName = EcoreUtil.toJavaInstanceTypeName((EGenericType)typeBuilderDiagnostic.getData().get(0))) :
        eClassifier instanceof EClass || eClassifier instanceof EEnum;
    if (!result && diagnostics != null)
    {
      BasicDiagnostic diagnosic =
        createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           WELL_FORMED_INSTANCE_TYPE_NAME,
           "_UI_EClassifierInstanceTypeNameNotWellFormed_diagnostic",
           new Object[] { getValueLabel(EcorePackage.Literals.ESTRING, instanceTypeName, context) },
           new Object[] { eClassifier },
           context);
      if (typeBuilderDiagnostic != null)
      {
        if (!typeBuilderDiagnostic.getChildren().isEmpty())
        {
          diagnosic.addAll(typeBuilderDiagnostic);
        }
        else if (instanceTypeName != null && formattedName != null)
        {
          // The string must contain inappropriate whitespace, so find the index for the first difference.
          //
          int i = 0;
          for (int length = Math.min(instanceTypeName.length(), formattedName.length()); 
               i < length; 
               i = Character.offsetByCodePoints(instanceTypeName, i, 1))
          {
            if (instanceTypeName.codePointAt(i) != formattedName.codePointAt(i))
            {
              break;
            }
          }
          
          diagnosic.add
           (createDiagnostic
             (Diagnostic.ERROR,
              DIAGNOSTIC_SOURCE,
              WELL_FORMED_INSTANCE_TYPE_NAME,
              instanceTypeName.codePointAt(i) == ' ' ? "_UI_EClassifierInstanceTypeNameUnexpectedSpace_diagnostic" : "_UI_EClassifierInstanceTypeNameExpectedSpace_diagnostic",
              new Object[] { i },
              new Object[] { i },
              context));
        }
      }
      diagnostics.add(diagnosic);
    }
    return result;
  }

  /**
   * Validates the UniqueTypeParameterNames constraint of '<em>EClassifier</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEClassifier_UniqueTypeParameterNames(EClassifier eClassifier, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    List<String> names = new ArrayList<String>();
    EList<ETypeParameter> eTypeParameters = eClassifier.getETypeParameters();
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
                 new Object[] { eClassifier, eTypeParameter, otherETypeParameter },
                 context));
          }
        }
      }
      names.add(name);
    }
    return result;
  }