/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package differencemodel.impl;

import differencemodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DifferencemodelFactoryImpl extends EFactoryImpl implements DifferencemodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DifferencemodelFactory init() {
		try {
			DifferencemodelFactory theDifferencemodelFactory = (DifferencemodelFactory)EPackage.Registry.INSTANCE.getEFactory("http://differencemodel/1.0"); 
			if (theDifferencemodelFactory != null) {
				return theDifferencemodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DifferencemodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifferencemodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DifferencemodelPackage.DIFFERENCE: return createDifference();
			case DifferencemodelPackage.ADD_OBJECT: return createAddObject();
			case DifferencemodelPackage.REMOVE_OBJECT: return createRemoveObject();
			case DifferencemodelPackage.ADD_REFERENCE: return createAddReference();
			case DifferencemodelPackage.REMOVE_REFERENCE: return createRemoveReference();
			case DifferencemodelPackage.SEMANTIC_CHANGE_SET: return createSemanticChangeSet();
			case DifferencemodelPackage.CORRESPONDENCE: return createCorrespondence();
			case DifferencemodelPackage.ATTRIBUTE_VALUE_CHANGE: return createAttributeValueChange();
			case DifferencemodelPackage.OBJECT_PARAMETER_SUBSTITUTION: return createObjectParameterSubstitution();
			case DifferencemodelPackage.VALUE_PARAMETER_SUBSTITUTION: return createValueParameterSubstitution();
			case DifferencemodelPackage.DEPENDENCY: return createDependency();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case DifferencemodelPackage.DEPENDENCY_KIND:
				return createDependencyKindFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case DifferencemodelPackage.DEPENDENCY_KIND:
				return convertDependencyKindToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Difference createDifference() {
		DifferenceImpl difference = new DifferenceImpl();
		return difference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddObject createAddObject() {
		AddObjectImpl addObject = new AddObjectImpl();
		return addObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoveObject createRemoveObject() {
		RemoveObjectImpl removeObject = new RemoveObjectImpl();
		return removeObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddReference createAddReference() {
		AddReferenceImpl addReference = new AddReferenceImpl();
		return addReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoveReference createRemoveReference() {
		RemoveReferenceImpl removeReference = new RemoveReferenceImpl();
		return removeReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SemanticChangeSet createSemanticChangeSet() {
		SemanticChangeSetImpl semanticChangeSet = new SemanticChangeSetImpl();
		return semanticChangeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Correspondence createCorrespondence() {
		CorrespondenceImpl correspondence = new CorrespondenceImpl();
		return correspondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeValueChange createAttributeValueChange() {
		AttributeValueChangeImpl attributeValueChange = new AttributeValueChangeImpl();
		return attributeValueChange;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObjectParameterSubstitution createObjectParameterSubstitution() {
		ObjectParameterSubstitutionImpl objectParameterSubstitution = new ObjectParameterSubstitutionImpl();
		return objectParameterSubstitution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueParameterSubstitution createValueParameterSubstitution() {
		ValueParameterSubstitutionImpl valueParameterSubstitution = new ValueParameterSubstitutionImpl();
		return valueParameterSubstitution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dependency createDependency() {
		DependencyImpl dependency = new DependencyImpl();
		return dependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DependencyKind createDependencyKindFromString(EDataType eDataType, String initialValue) {
		DependencyKind result = DependencyKind.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDependencyKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DifferencemodelPackage getDifferencemodelPackage() {
		return (DifferencemodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DifferencemodelPackage getPackage() {
		return DifferencemodelPackage.eINSTANCE;
	}

} //DifferencemodelFactoryImpl
