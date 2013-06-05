  /**
   * Validates the WellFormedSourceURI constraint of '<em>EAnnotation</em>'.
   * <!-- begin-user-doc -->
   * The source URI must either be either <code>null</code> or {@link #isWellFormedURI(String) well formed}.
   * <!-- end-user-doc -->
   * @generated NOT
   */
public boolean validateEAnnotation_WellFormedSourceURI(EAnnotation eAnnotation, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    String source = eAnnotation.getSource();
    boolean result = source == null || isWellFormedURI(source);
    if (!result && diagnostics != null)
    {
      diagnostics.add
        (createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           WELL_FORMED_SOURCE_URI,
           "_UI_EAnnotationSourceURINotWellFormed_diagnostic",
           new Object[] { source },
           new Object[] { eAnnotation },
           context));
    }
    return result;
  }
