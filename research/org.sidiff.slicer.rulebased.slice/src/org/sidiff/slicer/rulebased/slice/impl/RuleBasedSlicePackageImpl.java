/**
 */
package org.sidiff.slicer.rulebased.slice.impl;

import java.io.IOException;

import java.net.URL;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import org.sidiff.difference.asymmetric.AsymmetricPackage;

import org.sidiff.difference.symmetric.SymmetricPackage;

import org.sidiff.entities.EntitiesPackage;

import org.sidiff.formula.FormulaPackage;

import org.sidiff.matching.model.MatchingModelPackage;

import org.sidiff.slicer.rulebased.slice.RuleBasedSliceFactory;
import org.sidiff.slicer.rulebased.slice.RuleBasedSlicePackage;

import org.sidiff.slicer.slice.SlicePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RuleBasedSlicePackageImpl extends EPackageImpl implements RuleBasedSlicePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String copyright = "(c), Christopher Pietsch, Software Engineering Group, University of Siegen 2017 all rights reserved";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected String packageFilename = "slice.ecore";

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass executableModelSliceEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.sidiff.slicer.rulebased.slice.RuleBasedSlicePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RuleBasedSlicePackageImpl() {
		super(eNS_URI, RuleBasedSliceFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link RuleBasedSlicePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @generated
	 */
	public static RuleBasedSlicePackage init() {
		if (isInited) return (RuleBasedSlicePackage)EPackage.Registry.INSTANCE.getEPackage(RuleBasedSlicePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredRuleBasedSlicePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		RuleBasedSlicePackageImpl theRuleBasedSlicePackage = registeredRuleBasedSlicePackage instanceof RuleBasedSlicePackageImpl ? (RuleBasedSlicePackageImpl)registeredRuleBasedSlicePackage : new RuleBasedSlicePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		AsymmetricPackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		EntitiesPackage.eINSTANCE.eClass();
		FormulaPackage.eINSTANCE.eClass();
		MatchingModelPackage.eINSTANCE.eClass();
		SlicePackage.eINSTANCE.eClass();
		SymmetricPackage.eINSTANCE.eClass();

		// Load packages
		theRuleBasedSlicePackage.loadPackage();

		// Fix loaded packages
		theRuleBasedSlicePackage.fixPackageContents();

		// Mark meta-data to indicate it can't be changed
		theRuleBasedSlicePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RuleBasedSlicePackage.eNS_URI, theRuleBasedSlicePackage);
		return theRuleBasedSlicePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExecutableModelSlice() {
		if (executableModelSliceEClass == null) {
			executableModelSliceEClass = (EClass)EPackage.Registry.INSTANCE.getEPackage(RuleBasedSlicePackage.eNS_URI).getEClassifiers().get(0);
		}
		return executableModelSliceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExecutableModelSlice_AsymmetricDifference() {
        return (EReference)getExecutableModelSlice().getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getExecutableModelSlice__Serialize__String_boolean() {
        return getExecutableModelSlice().getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleBasedSliceFactory getRuleBasedSliceFactory() {
		return (RuleBasedSliceFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isLoaded = false;

	/**
	 * Laods the package and any sub-packages from their serialized form.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void loadPackage() {
		if (isLoaded) return;
		isLoaded = true;

		URL url = getClass().getResource(packageFilename);
		if (url == null) {
			throw new RuntimeException("Missing serialized package: " + packageFilename);
		}
		URI uri = URI.createURI(url.toString());
		Resource resource = new EcoreResourceFactoryImpl().createResource(uri);
		try {
			resource.load(null);
		}
		catch (IOException exception) {
			throw new WrappedException(exception);
		}
		initializeFromLoadedEPackage(this, (EPackage)resource.getContents().get(0));
		createResource(eNS_URI);
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isFixed = false;

	/**
	 * Fixes up the loaded package, to make it appear as if it had been programmatically built.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void fixPackageContents() {
		if (isFixed) return;
		isFixed = true;
		fixEClassifiers();
	}

	/**
	 * Sets the instance class on the given classifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void fixInstanceClass(EClassifier eClassifier) {
		if (eClassifier.getInstanceClassName() == null) {
			eClassifier.setInstanceClassName("org.sidiff.slicer.rulebased.slice." + eClassifier.getName());
			setGeneratedClassName(eClassifier);
		}
	}

} //RuleBasedSlicePackageImpl
