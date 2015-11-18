package org.sidiff.patching.test.sysml;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLSave.XMLTypeInfo;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.uml2.uml.resource.UMLResource;

/**
* Note (kehrer, 6.9.13): 
 * - copied from UMLResourceImpl
 * - createResourceGen(URI uri) overridden to use our SysMLResource
 *
 * @generated
 */
public class SysMLResourceFactory
		extends ResourceFactoryImpl
		implements UMLResource.Factory {

	/**
	 * Creates an instance of the resource factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SysMLResourceFactory() {
		super();
	}

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource createResourceGen(URI uri) {
		UMLResource result = new SysMLResource(uri);
		result.setEncoding(UMLResource.DEFAULT_ENCODING);
		return result;
	}

	@Override
	public Resource createResource(URI uri) {
		UMLResource resource = (UMLResource) createResourceGen(uri);

		resource.setXMIVersion("20110701"); //$NON-NLS-1$

		Map<Object, Object> defaultLoadOptions = resource.getDefaultLoadOptions();

		defaultLoadOptions.put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
		defaultLoadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		defaultLoadOptions.put(XMLResource.OPTION_LAX_FEATURE_PROCESSING, Boolean.TRUE);
		defaultLoadOptions.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);

		Map<Object, Object> defaultSaveOptions = resource.getDefaultSaveOptions();

		defaultSaveOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
		defaultSaveOptions.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		defaultSaveOptions.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.TRUE);

		defaultSaveOptions.put(XMLResource.OPTION_SAVE_TYPE_INFORMATION,
			new XMLTypeInfo() {

				public boolean shouldSaveType(EClass objectType,
						EClassifier featureType, EStructuralFeature feature) {
					return objectType != featureType
						&& objectType != XMLTypePackage.Literals.ANY_TYPE;
				}

				public boolean shouldSaveType(EClass objectType,
						EClass featureType, EStructuralFeature feature) {
					return objectType != featureType;
				}
			});
		
		// Avoid relative paths from workspace resources to plugin resources.
		defaultSaveOptions.put(XMLResource.OPTION_URI_HANDLER,
			new URIHandlerImpl.PlatformSchemeAware());
		
		return resource;
	}

} // UMLResourceFactoryImpl

