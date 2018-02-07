/**
 */
package org.sidiff.slicer.structural.configuration.util;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.slicer.structural.configuration.ConfigurationPackage;
import org.sidiff.slicer.structural.configuration.ConfigurationPlugin;
import org.sidiff.slicer.structural.configuration.Constraint;
import org.sidiff.slicer.structural.configuration.IConstraintInterpreter;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.SlicedEReference;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.SlicingMode;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.slicer.structural.configuration.ConfigurationPackage
 * @generated
 */
public class ConfigurationValidator extends EObjectValidator
{
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ConfigurationValidator INSTANCE = new ConfigurationValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.sidiff.slicer.structural.configuration";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationValidator()
	{
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage()
	{
	  return ConfigurationPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		switch (classifierID)
		{
			case ConfigurationPackage.SLICING_CONFIGURATION:
				return validateSlicingConfiguration((SlicingConfiguration)value, diagnostics, context);
			case ConfigurationPackage.SLICED_ECLASS:
				return validateSlicedEClass((SlicedEClass)value, diagnostics, context);
			case ConfigurationPackage.CONSTRAINT:
				return validateConstraint((Constraint)value, diagnostics, context);
			case ConfigurationPackage.SLICED_EREFERENCE:
				return validateSlicedEReference((SlicedEReference)value, diagnostics, context);
			case ConfigurationPackage.SLICING_MODE:
				return validateSlicingMode((SlicingMode)value, diagnostics, context);
			case ConfigurationPackage.ICONSTRAINT_INTERPRETER:
				return validateIConstraintInterpreter((IConstraintInterpreter)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSlicingConfiguration(SlicingConfiguration slicingConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(slicingConfiguration, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSlicedEClass(SlicedEClass slicedEClass, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(slicedEClass, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConstraint(Constraint constraint, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return validate_EveryDefaultConstraint(constraint, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSlicedEReference(SlicedEReference slicedEReference, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		if (!validate_NoCircularContainment(slicedEReference, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(slicedEReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(slicedEReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(slicedEReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(slicedEReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(slicedEReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(slicedEReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(slicedEReference, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(slicedEReference, diagnostics, context);
		if (result || diagnostics != null) result &= validateSlicedEReference_ReferenceNotDangling(slicedEReference, diagnostics, context);
		return result;
	}

	/**
	 * Validates the ReferenceNotDangling constraint of '<em>Sliced EReference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean validateSlicedEReference_ReferenceNotDangling(SlicedEReference slicedEReference, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		if(slicedEReference.getSlicedEClass() == null || slicedEReference.getSlicedEClass().getSlicingConfiguration() == null)
			return false;

		boolean refTargetFound = false;
		for(SlicedEClass slicedEClass : slicedEReference.getSlicedEClass().getSlicingConfiguration().getSlicedEClasses())
		{
			if(!slicedEReference.getType().eIsProxy() &&
					slicedEReference.getType().getEReferenceType().isSuperTypeOf(slicedEClass.getType()))
			{
				refTargetFound = true;
				break;
			}
		}

		if(!refTargetFound)
		{
			if(diagnostics != null)
			{
				String className;
				String referenceName;
				if(!slicedEReference.getSlicedEClass().getType().eIsProxy())
				{
					className = slicedEReference.getSlicedEClass().getType().getName();
					referenceName = slicedEReference.getType().getName();
				}
				else
				{
					className = EMFUtil.getEObjectID(slicedEReference.getSlicedEClass().getType());
					referenceName = EMFUtil.getEObjectID(slicedEReference.getType());
				}

				String referenceTargetName = "<unknown>"; //$NON-NLS-1$
				if(!slicedEReference.getType().eIsProxy())
				{
					referenceTargetName = slicedEReference.getType().getEReferenceType().getName();
				}

				diagnostics.add
					(createDiagnostic
						(Diagnostic.ERROR,
						 DIAGNOSTIC_SOURCE,
						 0,
						 "_UI_Constraint_ReferenceNotDangling_diagnostic", //$NON-NLS-1$
						 new Object[] { className + "." + referenceName, referenceTargetName }, //$NON-NLS-1$
						 new Object[] { slicedEReference },
						 context));
			}
			return false;
		}
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSlicingMode(SlicingMode slicingMode, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIConstraintInterpreter(IConstraintInterpreter iConstraintInterpreter, DiagnosticChain diagnostics, Map<Object, Object> context)
	{
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public ResourceLocator getResourceLocator()
	{
		return ConfigurationPlugin.INSTANCE.getPluginResourceLocator();
	}

} //ConfigurationValidator
