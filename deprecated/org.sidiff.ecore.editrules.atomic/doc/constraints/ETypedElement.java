import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.ETypedElement;

  /**
   * Validates the ValidLowerBound constraint of '<em>ETyped Element</em>'.
   * <!-- begin-user-doc -->
   * The {@link ETypedElement#getLowerBound() lower bound} must be greater or equal to 0
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateETypedElement_ValidLowerBound(ETypedElement eTypedElement, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    int lowerBound = eTypedElement.getLowerBound();
    boolean result = lowerBound >= 0;
    if (diagnostics != null && !result)
    {
      diagnostics.add
        (createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           VALID_LOWER_BOUND,
           "_UI_ETypedElementValidLowerBound_diagnostic",
           new Object[] { lowerBound },
           new Object[] { eTypedElement },
           context));
    }
    return result;
  }

  /**
   * Validates the ValidUpperBound constraint of '<em>ETyped Element</em>'.
   * <!-- begin-user-doc -->
   * The {@link ETypedElement#getUpperBound() upper bound} must be either
   * {@link ETypedElement#UNBOUNDED_MULTIPLICITY},
   * {@link ETypedElement#UNSPECIFIED_MULTIPLICITY},
   * or greater than 0.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateETypedElement_ValidUpperBound(ETypedElement eTypedElement, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    int upperBound = eTypedElement.getUpperBound();
    boolean result =
      upperBound > 0 ||
        upperBound == ETypedElement.UNSPECIFIED_MULTIPLICITY ||
        upperBound == ETypedElement.UNBOUNDED_MULTIPLICITY;
    if (diagnostics != null && !result)
    {
      diagnostics.add
        (createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           VALID_UPPER_BOUND,
           "_UI_ETypedElementValidUpperBound_diagnostic",
           new Object[] { upperBound },
           new Object[] { eTypedElement },
           context));
    }
    return result;
  }

  /**
   * Validates the ConsistentBounds constraint of '<em>ETyped Element</em>'.
   * <!-- begin-user-doc -->
   * The {@link ETypedElement#getLowerBound() lower bound} must be less than or equal to the {@link ETypedElement#getUpperBound() upper bound},
   * unless the upper bound is one of the two special values
   * {@link ETypedElement#UNBOUNDED_MULTIPLICITY} or {@link ETypedElement#UNSPECIFIED_MULTIPLICITY}.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateETypedElement_ConsistentBounds(ETypedElement eTypedElement, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    int lowerBound = eTypedElement.getLowerBound();
    int upperBound = eTypedElement.getUpperBound();
    boolean result = upperBound < 0 || lowerBound <= upperBound;
    if (diagnostics != null && !result)
    {
      diagnostics.add
        (createDiagnostic
          (Diagnostic.ERROR,
           DIAGNOSTIC_SOURCE,
           CONSISTENT_BOUNDS,
           "_UI_ETypedElementConsistentBounds_diagnostic", 
           new Object[] { lowerBound, upperBound },
           new Object[] { eTypedElement },
           context));
    }
    return result;
  }

  /**
   * Validates the ValidType constraint of '<em>ETyped Element</em>'.
   * <!-- begin-user-doc -->
   * The {@link ETypedElement#getEGenericType() type} may be <code>null</code> only if this in an {@link EOperation operation}.
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public boolean validateETypedElement_ValidType(ETypedElement eTypedElement, DiagnosticChain diagnostics, Map<Object, Object> context)
  {
    boolean result = true;
    EGenericType eGenericType = eTypedElement.getEGenericType();
    if (eGenericType == null && !(eTypedElement instanceof EOperation))
    {
      result = false;
      if (diagnostics != null)
      {
        diagnostics.add
          (createDiagnostic
            (Diagnostic.ERROR,
             DIAGNOSTIC_SOURCE,
             VALID_TYPE,
             "_UI_ETypedElementNoType_diagnostic",
             null,
             new Object[] { eTypedElement },
             context));
      }
    }
    return result;
  }