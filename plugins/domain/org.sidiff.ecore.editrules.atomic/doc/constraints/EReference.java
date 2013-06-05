  /**
   * Validates the ConsistentOpposite constraint of '<em>EReference</em>'.
   * <!-- begin-user-doc -->
   * An {@link EReference#getEOpposite() opposite} is optional but if one exists,
   * it must be a feature of this references's {@link EReference#getEReferenceType() type},
   * it must have this reference as its opposite,
   * and, if this feature is {@link EStructuralFeature#isTransient() transient},
   * then the opposite must also be transient,
   * must not {@link EReference#isResolveProxies() resolve proxies}.
   * or must be a {@link EReference#isContainment() containment},
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEReference_ConsistentOpposite(EReference eReference, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    EReference eOpposite = eReference.getEOpposite();
    if (eOpposite != null)
    {
      if (eReference.getEContainingClass() != null)
      {
        EReference oppositeEOpposite = eOpposite.getEOpposite();
        if (oppositeEOpposite != eReference)
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
                 CONSISTENT_OPPOSITE_NOT_MATCHING,
                 "_UI_EReferenceOppositeOfOppositeInconsistent_diagnostic",
                 null,
                 new Object[] { eReference, eOpposite, oppositeEOpposite },
                 context));
          }
        }
        EClassifier eType = eReference.getEType();
        if (eType != null)
        {
          EClass oppositeEContainingClass = eOpposite.getEContainingClass();
          if (oppositeEContainingClass != null && oppositeEContainingClass != eType)
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
                   CONSISTENT_OPPOSITE_NOT_FROM_TYPE,
                   "_UI_EReferenceOppositeNotFeatureOfType_diagnostic",
                   null,
                   new Object[] { eReference, eOpposite, eType },
                   context));
            }
          }
        }
      }
      if (result)
      {
        result =
          !isEffectivelyTransient(eReference) ||
            isEffectivelyTransient(eOpposite) ||
            !eOpposite.isResolveProxies() ||
            eOpposite.isContainment();
        if (diagnostics != null && !result)
        {
          diagnostics.add
            (createDiagnostic
              (Diagnostic.ERROR,
               DIAGNOSTIC_SOURCE,
               CONSISTENT_OPPOSITE_BAD_TRANSIENT,
               "_UI_EReferenceTransientOppositeNotTransient_diagnostic",
               null,
               new Object[] { eReference, eOpposite },
               context));
        }
      }
      if (result)
      {
        result = !eReference.isContainment() || !eOpposite.isContainment();
        if (diagnostics != null && !result)
        {
          diagnostics.add
            (createDiagnostic
              (Diagnostic.ERROR,
               DIAGNOSTIC_SOURCE,
               CONSISTENT_OPPOSITE_BOTH_CONTAINMENT,
               "_UI_EReferenceOppositeBothContainment_diagnostic",
               null,
               new Object[] { eReference, eOpposite },
               context));
        }
      }
    }
    return result;
  }

  /**
   * Validates the SingleContainer constraint of '<em>EReference</em>'.
   * <!-- begin-user-doc -->
   * A {@link EReference#isContainer() container} reference must have a upper bound of 1.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEReference_SingleContainer(EReference eReference, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = !eReference.isContainer() || eReference.getUpperBound() == 1;
    if (diagnostics != null && !result)
    {
      diagnostics.add
        (createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           SINGLE_CONTAINER,
           "_UI_EReferenceSingleContainer_diagnostic",
           new Object[] { eReference.getUpperBound() },
           new Object[] { eReference },
           context));
    }
    return result;
  }


  /**
   * Validates the ConsistentKeys constraint of '<em>EReference</em>'.
   * <!-- begin-user-doc -->
   * The {@link EReference#getEKeys() keys} of a reference must be features of the reference's {@link ETypedElement#getEType()}.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEReference_ConsistentKeys(EReference eReference, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    EList<EAttribute> eKeys = eReference.getEKeys();
    if (!eKeys.isEmpty())
    {
      EClass eClass = eReference.getEReferenceType();
      if (eClass != null)
      {
        for (EAttribute eAttribute :eKeys)
        {
          if (eClass.getFeatureID(eAttribute) == -1)
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
                   CONSISTENT_KEYS,
                   "_UI_EReferenceConsistentKeys_diagnostic",
                   new Object[] { getObjectLabel(eAttribute, context) },
                   new Object[] { eReference, eAttribute },
                   context));
            }
          }
        }
      }
    }
    return result;
  }

  /**
   * Validates the ConsistentUnique constraint of '<em>EReference</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEReference_ConsistentUnique(EReference eReference, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    // Multi-valued references that are containment or bidirectional must be unique.
    //
    boolean result = true;
    if (eReference.isMany() && 
          (eReference.isContainment() || eReference.getEOpposite() != null) &&
          !eReference.isUnique())
    {
      result = false;
      if (diagnostics != null)
      {
        diagnostics.add
          (createDiagnostic
            (Diagnostic.ERROR,
             DIAGNOSTIC_SOURCE,
             CONSISTENT_UNIQUE,
             "_UI_EReferenceConsistentUnique_diagnostic",
             null,
             new Object[] { eReference },
             context));
      }
    }
    return result;
  }

  /**
   * Validates the ConsistentContainer constraint of '<em>EReference</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEReference_ConsistentContainer(EReference eReference, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    if (eReference.isContainment() && eReference.getEContainingClass() != null)
    {
      EClass eClass = eReference.getEReferenceType();
      if (eClass != null)
      {
        for (EReference otherEReference : eClass.getEAllReferences())
        {
          if (otherEReference.isRequired() && otherEReference.isContainer() && otherEReference.getEOpposite() != eReference)
          {
            if (diagnostics != null)
            {
              diagnostics.add
                (createDiagnostic
                  (Diagnostic.ERROR,
                   DIAGNOSTIC_SOURCE,
                   CONSISTENT_CONTAINER,
                   "_UI_EReferenceConsistentContainer_diagnostic",
                   new Object[] { getObjectLabel(otherEReference, context) },
                   new Object[] { eReference, otherEReference },
                   context));
            }
            return false;
          }
        }
      }
    }
    return true; 
  }