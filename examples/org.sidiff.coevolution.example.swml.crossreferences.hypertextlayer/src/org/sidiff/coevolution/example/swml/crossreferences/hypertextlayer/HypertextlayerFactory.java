/**
 */
package org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.HypertextlayerPackage
 * @generated
 */
public interface HypertextlayerFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HypertextlayerFactory eINSTANCE = org.sidiff.coevolution.example.swml.crossreferences.hypertextlayer.impl.HypertextlayerFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Hypertext Layer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hypertext Layer</em>'.
	 * @generated
	 */
	HypertextLayer createHypertextLayer();

	/**
	 * Returns a new object of class '<em>Static Page</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Static Page</em>'.
	 * @generated
	 */
	StaticPage createStaticPage();

	/**
	 * Returns a new object of class '<em>Index Page</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Index Page</em>'.
	 * @generated
	 */
	IndexPage createIndexPage();

	/**
	 * Returns a new object of class '<em>Data Page</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Data Page</em>'.
	 * @generated
	 */
	DataPage createDataPage();

	/**
	 * Returns a new object of class '<em>Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Link</em>'.
	 * @generated
	 */
	Link createLink();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	HypertextlayerPackage getHypertextlayerPackage();

} //HypertextlayerFactory
