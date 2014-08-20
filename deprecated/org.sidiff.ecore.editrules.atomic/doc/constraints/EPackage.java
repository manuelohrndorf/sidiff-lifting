 /**
   * Validates the WellFormedNsURI constraint of '<em>EPackage</em>'.
   * <!-- begin-user-doc -->
   * The namespace URI must be {@link #isWellFormedURI(String) well formed} and may not be <code>null</code>.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEPackage_WellFormedNsURI(EPackage ePackage, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    String nsURI = ePackage.getNsURI();
    boolean result = isWellFormedURI(nsURI);
    if (!result && diagnostics != null)
    {
      diagnostics.add
        (createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           WELL_FORMED_NS_URI,
           "_UI_EPackageNsURINotWellFormed_diagnostic",
           new Object[] { nsURI },
           new Object[] { ePackage },
           context));
    }
    return result;
  }

  /**
   * Validates the WellFormedNsPrefix constraint of '<em>EPackage</em>'.
   * <!-- begin-user-doc -->
   * The namespace prefix must be either the empty string
   * or a {@link XMLTypeValidator#validateNCName(String, DiagnosticChain, Map) valid NCName}
   * that does not start with any case combination of the three letters
   * <a href="http://www.w3.org/TR/REC-xml-names/#xmlReserved">"xml"</a>.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEPackage_WellFormedNsPrefix(EPackage ePackage, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    String nsPrefix = ePackage.getNsPrefix();
    boolean
      result = "".equals(nsPrefix) ||
        nsPrefix != null &&
          XMLTypeValidator.INSTANCE.validateNCName(nsPrefix, null, context) &&
          (!nsPrefix.toLowerCase().startsWith("xml") || XMLNamespacePackage.eNS_URI.equals(ePackage.getNsURI()));
    if (!result && diagnostics != null)
    {
      diagnostics.add
        (createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           WELL_FORMED_NS_PREFIX,
           "_UI_EPackageNsPrefixNotWellFormed_diagnostic",
           new Object[] { nsPrefix },
           new Object[] { ePackage },
           context));
    }
    return result;
  }

  /**
   * Validates the UniqueSubpackageNames constraint of '<em>EPackage</em>'.
   * <!-- begin-user-doc -->
   * No two packages my have the same name.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEPackage_UniqueSubpackageNames(EPackage ePackage, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    List<String> names = new ArrayList<String>();
    EList<EPackage> eSubpackages = ePackage.getESubpackages();
    for (EPackage eSubpackage : eSubpackages)
    {
      String name = eSubpackage.getName();
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
            EPackage otherESubpackage = eSubpackages.get(index);
            diagnostics.add
              (createDiagnostic
                (Diagnostic.ERROR,
                 DIAGNOSTIC_SOURCE,
                 UNIQUE_SUBPACKAGE_NAMES,
                 "_UI_EPackageUniqueSubpackageNames_diagnostic", 
                 new Object[] { name },
                 new Object[] { ePackage, eSubpackage, otherESubpackage },
                 context));
          }
        }
      }
      names.add(name);
    }
    return result;
  }

  /**
   * Validates the UniqueClassifierNames constraint of '<em>EPackage</em>'.
   * <!-- begin-user-doc -->
   * No two classifiers may have matching names.
   * Classifier names are matched ignoring their case and their underscore separators.
   * It is an error to have two classifier with names that are equal but only a warning to have two classifiers with names that match.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEPackage_UniqueClassifierNames(EPackage ePackage, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    List<String> names = new ArrayList<String>();
    EList<EClassifier> eClassifiers = ePackage.getEClassifiers();
    for (EClassifier eClassifier : eClassifiers)
    {
      String name = eClassifier.getName();
      if (name != null)
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
            EClassifier otherEClassifier = eClassifiers.get(index);
            String otherName = otherEClassifier.getName();
            diagnostics.add
              (createDiagnostic
                (name.equals(otherName) ? Diagnostic.ERROR : Diagnostic.WARNING,
                 DIAGNOSTIC_SOURCE,
                 UNIQUE_CLASSIFIER_NAMES,
                 name.equals(otherName) ? "_UI_EPackageUniqueClassifierNames_diagnostic" : "_UI_EPackageDissimilarClassifierNames_diagnostic",
                 name.equals(otherName) ? new Object[] { name } : new Object[] { name, otherName },
                 new Object[] { ePackage, eClassifier, otherEClassifier },
                 context));
          }
        }
        names.add(key);
      }
      else
      {
        names.add(null);
      }
    }
    return result;
  }

  /**
   * Validates the UniqueNsURIs constraint of '<em>EPackage</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateEPackage_UniqueNsURIs(EPackage ePackage, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    String nsURI = ePackage.getNsURI();
    if (nsURI != null)
    {
      EPackage rootEPackage = ePackage;
      for (EPackage eSuperPackage = ePackage.getESuperPackage(); eSuperPackage != null; eSuperPackage = eSuperPackage.getESuperPackage())
      {
        rootEPackage = eSuperPackage;
      }
      
      UniqueEList<EPackage> ePackages = new UniqueEList.FastCompare<EPackage>();
      ePackages.add(rootEPackage);
      for (int i = 0; i < ePackages.size(); ++i)
      {
        ePackages.addAll(ePackages.get(i).getESubpackages());
      }
      ePackages.remove(ePackage);
      
      for (EPackage otherEPackage : ePackages)
      {
        if (nsURI.equals(otherEPackage.getNsURI()))
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
                 UNIQUE_NS_URIS,
                 "_UI_EPackageUniqueNsURIs_diagnostic", 
                 new Object[] { nsURI },
                 new Object[] { ePackage, otherEPackage },
                 context));
          }
        }
      }
    }
    return result;
  }