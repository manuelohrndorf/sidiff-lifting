/**
 */
package org.sidiff.difference.symmetricprofiled.util;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

import org.sidiff.difference.symmetricprofiled.SymmetricProfiledPackage;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SymmetricProfiledXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymmetricProfiledXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		SymmetricProfiledPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the SymmetricProfiledResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new SymmetricProfiledResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new SymmetricProfiledResourceFactoryImpl());
		}
		return registrations;
	}

} //SymmetricProfiledXMLProcessor
