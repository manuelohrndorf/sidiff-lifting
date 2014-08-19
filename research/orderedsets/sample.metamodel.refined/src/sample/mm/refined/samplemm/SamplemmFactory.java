/**
 */
package sample.mm.refined.samplemm;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see sample.mm.refined.samplemm.SamplemmPackage
 * @generated
 */
public interface SamplemmFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SamplemmFactory eINSTANCE = sample.mm.refined.samplemm.impl.SamplemmFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>NC List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>NC List</em>'.
	 * @generated
	 */
	NCList createNCList();

	/**
	 * Returns a new object of class '<em>Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Item</em>'.
	 * @generated
	 */
	Item createItem();

	/**
	 * Returns a new object of class '<em>Sample Metamodel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sample Metamodel</em>'.
	 * @generated
	 */
	SampleMetamodel createSampleMetamodel();

	/**
	 * Returns a new object of class '<em>CList</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>CList</em>'.
	 * @generated
	 */
	CList createCList();

	/**
	 * Returns a new object of class '<em>Item Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Item Link</em>'.
	 * @generated
	 */
	Item_Link createItem_Link();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SamplemmPackage getSamplemmPackage();

} //SamplemmFactory
