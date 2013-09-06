package org.sidiff.patching.test.sysml;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.SAXXMIHandler;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.uml2.uml.Profile;

/**
 * Note: copied from UMLHandler (6.9.13)
 * 
 * @author kehrer
 *
 */
public class UMLHandler extends SAXXMIHandler {

	protected final Map<URI, Map<String, EObject>> urisToProxies = new HashMap<URI, Map<String, EObject>>();

	public UMLHandler(XMLResource xmiResource, XMLHelper helper, Map<?, ?> options) {
		super(xmiResource, helper, options);
	}

	@Override
	public void endElement(String uri, String localName, String name) {

		if (objects.peek() instanceof Profile) {
			handleForwardReferences();
		}

		super.endElement(uri, localName, name);
	}

	@Override
	public void endDocument() {
		super.endDocument();

		if (extendedMetaData != null) {

			for (EPackage demandedPackage : extendedMetaData.demandedPackages()) {
				String nsURI = demandedPackage.getNsURI();

				if (nsURI != null) {

					if (urisToLocations != null) {
						URI locationURI = urisToLocations.get(nsURI);

						if (locationURI != null) {
							Resource resource = new ResourceImpl();
							resource.setURI(locationURI);
							resource.getContents().add(demandedPackage);
						}
					}

					for (Map.Entry<String, String> entry : helper.getPrefixToNamespaceMap()) {

						if (nsURI.equals(entry.getValue())) {
							demandedPackage.setNsPrefix(entry.getKey());
						}
					}
				}
			}
		}
	}

	@Override
	protected EObject validateCreateObjectFromFactory(EFactory factory, String typeName, EObject newObject,
			EStructuralFeature feature) {

		if (!(objects.peek() instanceof AnyType) && newObject != null && newObject.eIsProxy()
				&& !sameDocumentProxies.contains(newObject)) {

			URI proxyURI = ((InternalEObject) newObject).eProxyURI();
			Map<String, EObject> typeNamesToProxies = urisToProxies.get(proxyURI);

			if (typeNamesToProxies == null) {
				urisToProxies.put(proxyURI, typeNamesToProxies = new HashMap<String, EObject>());
			}

			EObject proxy = typeNamesToProxies.get(typeName);

			if (proxy == null) {
				typeNamesToProxies.put(typeName, proxy = newObject);
			}

			// canonicalize proxies
			newObject = proxy;
		}

		return super.validateCreateObjectFromFactory(factory, typeName, newObject, feature);
	}

}
