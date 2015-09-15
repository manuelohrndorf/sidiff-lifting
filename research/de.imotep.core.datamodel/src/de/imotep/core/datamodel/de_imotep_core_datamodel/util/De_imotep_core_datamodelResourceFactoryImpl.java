/**
 */
package de.imotep.core.datamodel.de_imotep_core_datamodel.util;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see de.imotep.core.datamodel.de_imotep_core_datamodel.util.De_imotep_core_datamodelResourceImpl
 * @generated
 */
public class De_imotep_core_datamodelResourceFactoryImpl extends ResourceFactoryImpl {
	/**
	 * Creates an instance of the resource factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public De_imotep_core_datamodelResourceFactoryImpl() {
		super();
	}

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Resource createResource(URI uri) {
		Resource result = new De_imotep_core_datamodelResourceImpl(uri);
		return result;
	}

} //De_imotep_core_datamodelResourceFactoryImpl
