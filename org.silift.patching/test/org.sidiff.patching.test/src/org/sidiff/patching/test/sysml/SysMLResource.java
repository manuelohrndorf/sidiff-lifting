package org.sidiff.patching.test.sysml;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.uml2.uml.resource.UMLResource;

/**
 * Note (kehrer, 6.9.13): 
 * - copied from UMLResourceImpl
 * - useUUIDs() overridden to return false
 * 
 * 
 * 
 * @generated
 */
public class SysMLResource extends XMIResourceImpl implements UMLResource {

	/**
	 * Creates an instance of the resource. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param uri
	 *            the URI of the new resource.
	 * @generated
	 */
	public SysMLResource(URI uri) {
		super(uri);
	}

	@Override
	protected XMLLoad createXMLLoad() {
		return new UMLLoadImpl(createXMLHelper());
	}

	@Override
	protected XMLSave createXMLSave() {
		return new UMLSaveImpl(createXMLHelper());
	}

	@Override
	protected boolean useIDAttributes() {
		return false;
	}

	@Override
	protected boolean useUUIDs() {
		return false;
	}

} // UMLResourceImpl
