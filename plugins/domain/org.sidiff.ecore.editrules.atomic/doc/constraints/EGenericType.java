  /**
   * Validates the ConsistentType constraint of '<em>EGeneric Type</em>'.
   * <!-- begin-user-doc -->
   * A generic type must not reference both a {@link EGenericType#getEClassifier() classifier}
   * and a {@link EGenericType#getETypeParameter() type parameter}.
   * The referenced type parameter must be in scope, i.e.,
   * its {@link EObject#eContainer()} must be an {@link EcoreUtil#isAncestor(EObject, EObject)} of this generic type.
   * A generic type used as a {@link EClass#getEGenericSuperTypes() generic super type}
   * must have a classifier that refers to a {@link EClass class}.
   * A generic type used as a {@link EGenericType#getETypeArguments() type argument} of a generic type used as a generic super type
   * must specify either a classifier or a type parameter, i.e., it can't be a wildcard.
   * A generic type may omit both the classifier and the type argument to act as a wildcard
   * only when used as a type argument of some generic type,
   * with the above exception.
   * If present, the classifier of generic type used as the {@link ETypedElement#getEType() type} of an {@link EAttribute attribute}
   * must be a {@link EDataType data type}.
   * If present, the classifier of generic type used as the type of a {@link EReference reference}
   * must be a class.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEGenericType_ConsistentType(EGenericType eGenericType, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;

    ETypeParameter eTypeParameter = eGenericType.getETypeParameter();
    EClassifier eClassifier = eGenericType.getEClassifier();
    if (eTypeParameter != null)
    {
      if (eClassifier != null)
      {
        // Can't have both a classifier and a type parameter.
        //
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
                CONSISTENT_TYPE_NO_TYPE_PARAMETER_AND_CLASSIFIER,
                "_UI_EGenericTypeNoTypeParameterAndClassifier_diagnostic",
                null,
                new Object[] { eGenericType },
                context));
        }
      }
      
      // The referencing generic type must be contained to be in scope
      //
      EObject scope = eTypeParameter.eContainer();
      boolean  inScope = EcoreUtil.isAncestor(scope, eGenericType);
      if (inScope)
      {
        // And even if it is contained, it must not be a forward reference.
        // eTypeParameterIndex == index is allowed when the type parameter is 
        // the type argument of the bound, though,
        // i.e., when the type argument is not nested directly as a child of the type parameter.
        //
        List<?> typeParameters = (List<?>)scope.eGet(eTypeParameter.eContainmentFeature());
        EObject usage = eGenericType; 
        for (EObject container = usage.eContainer(); container != scope; container = container.eContainer())
        {
          usage = container;
        }
        int index = typeParameters.indexOf(usage);
        int eTypeParameterIndex = typeParameters.indexOf(eTypeParameter);
        inScope = index == -1 || 
          index > eTypeParameterIndex ||
          (index == eTypeParameterIndex && eGenericType.eContainingFeature() != EcorePackage.Literals.ETYPE_PARAMETER__EBOUNDS);
      }

      if (!inScope)
      {
        // The type parameter must be in scope and must not be a forward reference.
        //
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
                CONSISTENT_TYPE_TYPE_PARAMETER_NOT_IN_SCOPE,
                "_UI_EGenericTypeOutOfScopeTypeParameter_diagnostic",
                null,
                new Object[] { eGenericType },
                context));
        }
      }
    }

    EReference eContainmentFeature = eGenericType.eContainmentFeature();
    if (eContainmentFeature == EcorePackage.Literals.ECLASS__EGENERIC_SUPER_TYPES)
    {
      // When used as a generic super type, there must be a classifier that refers to a class.
      //
      if (!(eGenericType.getEClassifier() instanceof EClass))
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
               CONSISTENT_TYPE_CLASS_REQUIRED,
               "_UI_EGenericTypeNoClass_diagnostic",
               null,
               new Object[] { eGenericType },
               context));
        }
      }
    }
    else if (eContainmentFeature == EcorePackage.Literals.EGENERIC_TYPE__ETYPE_ARGUMENTS)
    {
      if (eGenericType.eContainer().eContainmentFeature() == EcorePackage.Literals.ECLASS__EGENERIC_SUPER_TYPES)
      {
        // The type arguments of a generic super type must not be a wildcard.
        //
        if (eClassifier == null && eTypeParameter == null)
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
                 CONSISTENT_TYPE_WILDCARD_NOT_PERMITTED,
                 "_UI_EGenericTypeNoTypeParameterOrClassifier_diagnostic",
                 null,
                 new Object[] { eGenericType },
                 context));
          }
        }
      }
    }
    else if (eContainmentFeature != null)
    {
      // Wildcards are only allowed in type arguments.
      //
      if (eClassifier == null && eTypeParameter == null)
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
               CONSISTENT_TYPE_WILDCARD_NOT_PERMITTED,
               "_UI_EGenericTypeNoTypeParameterOrClassifier_diagnostic",
               null,
               new Object[] { eGenericType },
               context));
        }
      }
      else if (eClassifier != null)
      {
        EObject eContainer = eGenericType.eContainer();
        if (eContainer instanceof EStructuralFeature)
        {
          if (eClassifier instanceof EClass)
          {
            if (eContainer instanceof EAttribute)
            {
              // The classifier of an attribute's generic type must be a data type.
              //
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
                     CONSISTENT_TYPE_CLASS_NOT_PERMITTED,
                     "_UI_EAttributeNoDataType_diagnostic",
                     null,
                     new Object[] { eGenericType },
                     context));
              }
            }
          }
          else if (eClassifier instanceof EDataType)
          {
            if (eContainer instanceof EReference)
            {
              // The classifier of an references's generic type must be a class.
              //
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
                     CONSISTENT_TYPE_DATA_TYPE_NOT_PERMITTED,
                     "_UI_EReferenceNoClass_diagnostic",
                     null,
                     new Object[] { eGenericType },
                     context));
              }
            }
          }
        }
      }
    }
    
    if (eClassifier != null && eContainmentFeature != null && eContainmentFeature != EcorePackage.Literals.ETYPED_ELEMENT__EGENERIC_TYPE)
    {
      // A primitive type can only be used as the generic type of a typed element.
      //
      String instanceClassName = eClassifier.getInstanceClassName();
      if (instanceClassName == "boolean" ||
            instanceClassName == "byte" ||
            instanceClassName == "char" ||
            instanceClassName == "double" ||
            instanceClassName == "float" ||
            instanceClassName == "int" ||
            instanceClassName == "long" ||
            instanceClassName == "short")
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
               CONSISTENT_TYPE_PRIMITIVE_TYPE_NOT_PERMITTED,
               "_UI_EGenericTypeInvalidPrimitiveType_diagnostic",
               new Object[] { instanceClassName },
               new Object[] { eGenericType },
               context));
        }
      }
    }
    return result;
  }

  /**
   * Validates the ConsistentBounds constraint of '<em>EGeneric Type</em>'.
   * <!-- begin-user-doc -->
   * A generic type may have bounds only when used as a {@link EGenericType#getETypeArguments() type argument}.
   * A generic type may not have both a {@link EGenericType#getELowerBound() lower} and an {@link EGenericType#getEUpperBound() upper bound}.
   * A generic type may not have bounds
   * as well as a {@link EGenericType#getEClassifier() classifier} or a {@link EGenericType#getETypeParameter() type parameter}.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEGenericType_ConsistentBounds(EGenericType eGenericType, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;

    EGenericType eLowerBound = eGenericType.getELowerBound();
    EGenericType eUpperBound = eGenericType.getEUpperBound();
    if (eLowerBound != null || eUpperBound != null)
    {
      EStructuralFeature eContainmentFeature = eGenericType.eContainmentFeature();
      if (eContainmentFeature == EcorePackage.Literals.EGENERIC_TYPE__ETYPE_ARGUMENTS)
      {
        // Can't have both an upper and lower bound.
        //
        if (eLowerBound != null && eUpperBound != null)
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
                 CONSISTENT_BOUNDS_NO_LOWER_AND_UPPER,
                 "_UI_EGenericTypeNoUpperAndLowerBound_diagnostic",
                 null,
                 new Object[] { eGenericType },
                 context));
          }
        }

        // Can't have a classifier or a type parameter as well as bounds.
        //
        if (eGenericType.getEClassifier() != null || eGenericType.getETypeParameter() != null)
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
                 CONSISTENT_BOUNDS_NO_BOUNDS_WITH_TYPE_PARAMETER_OR_CLASSIFIER,
                 "_UI_EGenericTypeNoTypeParameterOrClassifierAndBound_diagnostic",
                 null,
                 new Object[] { eGenericType },
                 context));
          }
        }
      }
      else
      {
        // Can only have bounds when used as a type argument.
        //
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
               CONSISTENT_BOUNDS_NOT_ALLOWED,
               "_UI_EGenericTypeBoundsOnlyForTypeArgument_diagnostic",
               null,
               new Object[] { eGenericType },
               context));
        }
      }
    }
    return result;
  }

  /**
   * Validates the ConsistentArguments constraint of '<em>EGeneric Type</em>'.
   * <!-- begin-user-doc -->
   * A generic type can have {@link EGenericType#getETypeArguments() type arguments}
   * only if it has a {@link EGenericType#getEClassifier() classifier} that specifies {@link EClassifier#getETypeParameters()};
   * the number of type arguments must match the number of type parameters.
   * It is only a warning for there to be no arguments when there are parameters, but any other mismatch is an error.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEGenericType_ConsistentArguments(EGenericType eGenericType, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    EClassifier eClassifier = eGenericType.getEClassifier();
    EList<EGenericType> eTypeArguments = eGenericType.getETypeArguments();
    int eTypeArgumentSize = eTypeArguments.size();
    if (eClassifier == null)
    {
      if (eTypeArgumentSize != 0)
      {
        // Can't have type arguments unless there is a classifier
        //
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
               CONSISTENT_ARGUMENTS_NONE_ALLOWED,
               "_UI_EGenericTypeNoArguments_diagnostic",
               null,
               new Object[] { eGenericType },
               context));
        }
      }
    }
    else
    {
      EList<ETypeParameter> eTypeParameters = eClassifier.getETypeParameters();
      int eTypeParameterSize = eTypeParameters.size();
      if (eTypeArgumentSize == 0)
      {
        if (eTypeParameterSize > 0)
        {
          // Have no arguments when they are allowed is only a warning.
          //
          if (diagnostics == null)
          {
            return false;
          }
          else
          {
            result = false;
            diagnostics.add
              (createDiagnostic
                (Diagnostic.WARNING,
                 DIAGNOSTIC_SOURCE,
                 CONSISTENT_ARGUMENTS_NONE,
                 "_UI_EGenericTypeArgumentsNeeded_diagnostic",
                 new Object [] { eClassifier.getName(), eTypeParameterSize },
                 new Object[] { eGenericType },
                 context));

          }
        }
      }
      else if (eTypeArgumentSize != eTypeParameters.size())
      {
        // Incorrect number of type arguments.
        //
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
               CONSISTENT_ARGUMENTS_INCORRECT_NUMBER,
               "_UI_EGenericTypeIncorrectArguments_diagnostic", 
               new Object [] { eClassifier.getName(), eTypeArgumentSize, eTypeParameterSize },
               new Object[] { eGenericType },
               context));

        }
      }
      else
      {
        Map<ETypeParameter, EGenericType> substitutions = new HashMap<ETypeParameter, EGenericType>();
        for (int i = 0; i < eTypeParameterSize; ++i)
        {
          ETypeParameter eTypeParameter = eTypeParameters.get(i);
          EGenericType eTypeArgument = eTypeArguments.get(i);
          substitutions.put(eTypeParameter, eTypeArgument);
          if (!isValidSubstitution(eTypeArgument, eTypeParameter, substitutions))
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
                   CONSISTENT_ARGUMENTS_INVALID_SUBSTITUTION,
                   "_UI_EGenericTypeArgumentInvalidSubstitution_diagnostic", 
                    new Object [] 
                    { 
                      getObjectLabel(eTypeArgument, context), 
                      getObjectLabel(eTypeParameter, context) 
                    },
                   new Object[] { eGenericType, eTypeArgument, eTypeParameter },
                   context));
    
            }
          }
        }
      }
    }
    return result;
  }
