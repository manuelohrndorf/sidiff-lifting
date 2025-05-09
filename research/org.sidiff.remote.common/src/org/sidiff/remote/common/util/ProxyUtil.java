package org.sidiff.remote.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.ProxyProperty;

public class ProxyUtil {

	public static ProxyObject convertEObject(EObject eObject, boolean infinite) {
		String label = getLabel(eObject);
		String id = ((UUIDResource) eObject.eResource()).getID(eObject);
		String type = eObject.eClass().getEPackage().getNsURI() + "#//" + eObject.eClass().getName();
		
		Set<EObject> children = new HashSet<EObject>();
		
		List<ProxyProperty> properties = new ArrayList<ProxyProperty>();
		for(EAttribute eAttribute : eObject.eClass().getEAllAttributes()) {
			ProxyProperty porperty = convertEStructuralFeature(eObject, eAttribute);
			properties.add(porperty);
		}
		
		for(EReference eReference : eObject.eClass().getEAllReferences()) {
			if(!eReference.isContainment()) {
				ProxyProperty porperty = convertEStructuralFeature(eObject, eReference);
				properties.add(porperty);
			}else if(infinite) {
				children.addAll(EMFModelAccess.getChildren(eObject, eReference));					
			}
		}
		boolean container = !eObject.eContents().isEmpty();
		
		
		ProxyObject proxyObject = new ProxyObject(label, id, type, properties, container);
		
		for(EObject child : children) {
			ProxyObject childProxyObject = convertEObject(child, infinite);
			childProxyObject.setParent(proxyObject);
		}
		
		return proxyObject;
	}
	
	public static ProxyProperty convertEStructuralFeature(EObject eObject, EStructuralFeature eStructuralFeature){

		String name = eStructuralFeature.getName();
		String value = "";
		if(eStructuralFeature.isMany()) {
			Object multiValue = eObject.eGet(eStructuralFeature);
			for(Object object : (List<?>)multiValue) {
				value += getLabel(object) + ", ";
			}
		
			if(!value.isEmpty()) {
				value = value.substring(0, value.length()-2);
			}
		}else {
			Object object = eObject.eGet(eStructuralFeature);
			if(object != null) {
				value = getLabel(object);
			}
		}
		
		return new ProxyProperty(name, value);
	}
	
	public static ProxyObject convertFile(File file, File rootFolder) {
		String label = file.getName();
		String id = file.getPath().toString().replace(rootFolder.getPath().toString() + File.separator, "");
		String type = file.isDirectory()? "Folder" : "File";
		boolean container = file.isDirectory();
		List<ProxyProperty> properties = new ArrayList<ProxyProperty>();
		// TODO fill properties
		
		ProxyObject proxyObject = new ProxyObject(label, id, type, properties, container);
		
		return proxyObject;
	}
	
	public static void addProperty(ProxyObject proxyObject, String name, String value) {
		ProxyProperty property = new ProxyProperty(name, value);
		proxyObject.getProperties().add(property);
	}
	
	private static String getLabel(Object object) {
		String label = "";
		
		if(object instanceof EObject) {
			EObject eObject = (EObject) object;
	
		
			boolean hasName = false;
			EStructuralFeature nameFeature = eObject.eClass().getEStructuralFeature("name");
			if (nameFeature != null) {
				Object nameValue = eObject.eGet(nameFeature);
				
				if (nameValue != null && nameValue instanceof String && !((String)nameValue).isEmpty()) {
					label += (String) nameValue;
					hasName = true;
				}
			}
			
			if(!hasName){
				// Remove Object ID if present:
				label += object.toString().replaceFirst("@.*?\\s", "").replaceAll("\\(.*\\)", "").replaceAll("\\s", "");
			}
		}else {
			label = object.toString();
		}
				
		return label;
	}
	
	public static List<ProxyObject> convertEMFResource(UUIDResource subResource, UUIDResource completeResource) {
		Map<EObject, ProxyObject> proxyObjects = new HashMap<EObject, ProxyObject>();

		for (String uuid : subResource.getEObjectToIDMap().values()) {

			EObject eObject = completeResource.getIDToEObjectMap().get(uuid);
			// for (Iterator<EObject> iterator = completeResource.getAllContents();
			// iterator.hasNext();) {
			// EObject eObject = iterator.next();
			if (!proxyObjects.containsKey(eObject)) {
				ProxyObject proxyObject = convertEObject(eObject, false);
				proxyObject.setSelected(true);

				proxyObjects.put(eObject, proxyObject);

				EObject parentEObject = eObject.eContainer();

				while (parentEObject != null) {
					ProxyObject parentProxyObject = proxyObjects.get(parentEObject);
					
					if (parentProxyObject == null) {
						parentProxyObject = convertEObject(parentEObject, false);
						proxyObjects.put(parentEObject, parentProxyObject);
					}
					
					proxyObject.setParent(parentProxyObject);
					parentProxyObject.setSelected(true);
					
					for (EObject childEObject : parentEObject.eContents()) {
						ProxyObject childProxyObject = proxyObjects.get(childEObject);
						
						if (childProxyObject == null) {
							childProxyObject = convertEObject(childEObject, false);
							childProxyObject.setSelected(subResource.getEObjectToIDMap()
									.containsValue(completeResource.getID(childEObject)));
							childProxyObject.setParent(parentProxyObject);

							proxyObjects.put(childEObject, childProxyObject);
						}
					}
					
					proxyObject = parentProxyObject;
					parentEObject = parentEObject.eContainer();
				}
			}
		}

		List<ProxyObject> contents = new ArrayList<ProxyObject>();
		for (EObject content : completeResource.getContents()) {
			contents.add(proxyObjects.get(content));
		}

		return contents;
	}
	
	public static List<ProxyObject> convertEMFResource(UUIDResource resource) {
		Map<EObject, ProxyObject> proxyObjects = new HashMap<EObject, ProxyObject>();
		
		for (Iterator<EObject> iterator = resource.getAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			ProxyObject proxyObject = convertEObject(eObject, false);
			if(eObject.eContainer() != null) {
				proxyObject.setParent(proxyObjects.get(eObject.eContainer()));
			}
			proxyObjects.put(eObject, proxyObject);
		}
		
		List<ProxyObject> contents = new ArrayList<ProxyObject>();
		for(EObject content : resource.getContents()) {
			contents.add(proxyObjects.get(content));
		}
		
		return contents;
	}
	
	public static List<ProxyObject> convertFileTree(File file, File rootFolder, Collection<File> blacklist) {
		List<File> files = new ArrayList<File>();
		File currentFile = file;
		while(!currentFile.equals(rootFolder)) {
			currentFile = currentFile.getParentFile();
			List<File> children = new ArrayList<File>(Arrays.asList(currentFile.listFiles()));
			children.removeAll(blacklist);
			files.addAll(0, children);
		}
		
		Map<File, ProxyObject> proxyObjects = new HashMap<File, ProxyObject>();
		for(File f : files) {
			ProxyObject proxyObject = convertFile(f, rootFolder);
			proxyObject.setSelected(f.equals(file));
			proxyObjects.put(f, proxyObject);
			if(!f.getParentFile().equals(rootFolder)) {
				proxyObject.setParent(proxyObjects.get(f.getParentFile()));
			}
		}
		
		List<ProxyObject> rootProxyObjects = new ArrayList<ProxyObject>();
		for(ProxyObject proxyObject : proxyObjects.values()) {
			if(proxyObject.getParent() == null) {
				rootProxyObjects.add(proxyObject);
			}
		}
		
		return rootProxyObjects;
	}
	
	public static List<ProxyObject> sort(ProxyObject proxyObject){
		List<ProxyObject> proxyObjects = new ArrayList<ProxyObject>();
		proxyObjects.add(proxyObject);
		for(ProxyObject child : proxyObject.getChildren()) {
			proxyObjects.addAll(sort(child));
		}
		return proxyObjects;
	}
}
