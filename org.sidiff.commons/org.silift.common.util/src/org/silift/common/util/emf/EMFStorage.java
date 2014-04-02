package org.silift.common.util.emf;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * The resource manager is used to save and load EMF and java resources.
 */
public class EMFStorage {

	/**
	 * Do nothing while saving the URI.
	 */
	@SuppressWarnings("unused")
	private static class DoNotDeresolve extends URIHandlerImpl {
		@Override
		public URI deresolve(URI uri) {
			return uri;
		}
	}	
	
	/**
	 * URI will be replaced by the last segment
	 */
	private static class ResolveLastSegment extends URIHandlerImpl {
		@Override
		public URI deresolve(URI uri) {
			String rel= uri.toString();
			String segment = rel.substring(rel.indexOf(uri.lastSegment()));
			return URI.createURI(segment);
		}
	}
	
	
	/**
	 * URI will be replaced by the shortest relative URI.
	 */
	private static class DeresolveRelative extends URIHandlerImpl{
		@Override
		public URI deresolve(URI uri){
			return !uri.isPlatform() || (uri.segmentCount() > 0 && baseURI.segmentCount() > 0 && uri.segment(0).equals(baseURI.segment(0))) ? super.deresolve(uri) : uri;
		}
	}
	
	/**
	 * Deresolve as platform resource URI.
	 */
	private static class PlatformResourceDeresolve extends URIHandlerImpl {
		@Override
		public URI deresolve(URI uri) {
			if (!uri.isPlatformResource() && uri.isFile()) {
				return uriToPlatformUri(uri).appendFragment(uri.fragment());
			}
			return uri;
		}
	}	

	/**
	 * Save EMF resource which is already contained in a resource. References
	 * will be saved as platform resource URIs.
	 * 
	 * @param root
	 *            the root object that will be saved.
	 */
	public static void eSave(EObject root) {

		Resource resource = root.eResource();

		Map<String, Object> options = new HashMap<String, Object>();
		options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		options.put(XMIResource.OPTION_URI_HANDLER, new PlatformResourceDeresolve());

		try {
			resource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save EMF resource to given URI path. References will be saved as platform resource URIs.
	 * 
	 * @param path
	 *            the save path.
	 * @param root
	 *            the root object that will be saved.
	 */
	public static void eSaveAs(URI uri, EObject root) {

		Resource resource = new XMIIDResourceImpl(uri);
		resource.getContents().add(root);

		Map<String, Object> options = new HashMap<String, Object>();
		options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		options.put(XMIResource.OPTION_URI_HANDLER, new PlatformResourceDeresolve());

		try {
			resource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save EMF resource to given URI path.
	 * 
	 * @param path
	 *            the save path.
	 * @param root
	 *            the root object that will be saved.
	 * @param relative
	 * 			  true: URIs will be replaced by the shortest relative path.
	 *            false: Do nothing while saving the URI.
	 */
	public static void eSaveAs(URI uri, EObject root, boolean relative) {

		Resource resource = null;
		
		if(root.eResource() != null){
			resource = root.eResource();
			resource.setURI(uri);
		}else{
			resource = new XMIResourceImpl(uri);
			resource.getContents().add(root);
		}
		
		Map<String, Object> options = new HashMap<String, Object>();
		options.put(XMIResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		if(relative) {
			options.put(XMIResource.OPTION_URI_HANDLER, new DeresolveRelative());
		} else {
			options.put(XMIResource.OPTION_URI_HANDLER, new ResolveLastSegment());
		}

		try {
			resource.save(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Overwrite EMF resource.
	 * 
	 * @param oldObj
	 *            The old object to overwrite.
	 * @param newObj
	 *            The new object to put into the old resource.
	 */
	public static void eOverwrite(EObject oldObj, EObject newObj) {

		Resource resource = oldObj.eResource();
		resource.getContents().clear();
		resource.getContents().add(newObj);
	}

	/**
	 * Load EMF resource.
	 * 
	 * @param path
	 *            the EMF-file path.
	 * @return the loaded EMF-object.
	 */
	public static EObject eLoad(URI eObjectUri) {
		Resource eObjectResource = new ResourceSetImpl().getResource(eObjectUri, true);
		EObject eObject = eObjectResource.getContents().get(0);

		return eObject;
	}

	/**
	 * Reload EMF-object.
	 * 
	 * @param root
	 *            the root object (The same that was loaded).
	 * @return the reloaded EMF-object.
	 */
	public static EObject eReload(EObject root) {

		try {
			Resource resource = root.eResource();
			resource.unload();
			resource.load(null);
			EObject reloaded = resource.getContents().get(0);

			return reloaded;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Returns the URI of the given root object.
	 * 
	 * @param rootEObject
	 *            the root object
	 * @return the object URI.
	 */
	public static URI getURI(EObject rootEObject) {
		if (rootEObject.eIsProxy()) {
			return ((InternalEObject) rootEObject).eProxyURI().trimFragment();
		} else {
			return rootEObject.eResource().getURI();
		}
	}
	
	/**
	 * Converts a (e.g. platform) URI to a file URI.
	 * 
	 * @param uri The URI to convert.
	 * @return The given URI as file URI.
	 */
	public static URI uriToFileUri(URI uri) {
		URI fileURI = pathToFileUri(uriToPath(uri));
		return fileURI;
	}
	
	/**
	 * Converts a (e.g. file) URI to a platform resource URI.
	 * Returns an file URI if the URI is not workspace relative.
	 * 
	 * @param uri The URI to convert.
	 * @return The given URI as platform resource URI.
	 */
	public static URI uriToPlatformUri(URI uri) {
		URI fileURI = pathToUri(uriToPath(uri));
		return fileURI;
	}

	/**
	 * Converts a file path to an platform resource URI.
	 * 
	 * @param path
	 *            The path to convert.
	 * @return The given path as platform resource URI.
	 */
	public static URI pathToUri(String path) {
		File file = pathToFile(path);
		return fileToUri(file);
	}
	
	/**
	 * Converts a file path to an platform resource URI.
	 * 
	 * @param path
	 *            The path to convert.
	 * @return The given path as platform resource URI.
	 */
	public static URI pathToFileUri(String path) {
		File file = pathToFile(path);
		return fileToFileUri(file);
	}
	
	public static java.net.URI pathToRelativeUri(String base, String path) {
		return new File(base).toURI().relativize(new File(path).toURI());
	}

	/**
	 * Converts a file path to a <code>File</code>.
	 * 
	 * @param path
	 *            The path to convert.
	 * @return The given path as <code>File</code>
	 */
	public static File pathToFile(String path) {
		return new File(path);
	}

	/**
	 * Converts a URI to an absolute file path.
	 * 
	 * @param uri
	 *            The URI to convert.
	 * @return The given URI as absolute file path
	 */
	public static String uriToPath(URI uri) {
		if (uri.isPlatform()) {
			return pathToFile(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + uri.toPlatformString(true)).getAbsolutePath();
		}

		return pathToFile(uri.devicePath()).getAbsolutePath();
	}

	/**
	 * Convers a URI to a <code>File</code>.
	 * 
	 * @param uri
	 *            The URI to convert.
	 * @return The given URI as <code>File</code>.
	 */
	public static File uriToFile(URI uri) {
		return new File(uriToPath(uri));
	}

	/**
	 * Converts a <code>File</code> inside the workspace to an absolute file path.
	 * 
	 * @param file The <code>File</code> to convert.
	 * @return The given <code>File</code> as absolute file path
	 */
	public static String fileToPath(File file) {
		if (file.isAbsolute()) {
			return file.getAbsolutePath();
		} else {
			return new File(
					ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() 
					+ file.getPath()).getAbsolutePath();	
		}
	}

	/**
	 * Converts a <code>File</code> to a platform resource URI.
	 * Returns an file URI if the URI is not workspace relative.
	 * 
	 * @param file The <code>File</code> to convert.
	 * @return The given file as platform resource URI.
	 */
	public static URI fileToUri(File file) {
		IFile[] iFiles = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(file.toURI());
		
		if (iFiles.length > 0) {
			return URI.createPlatformResourceURI(iFiles[0].getFullPath().toString(), true);
		} else {
			return URI.createFileURI(file.getAbsolutePath());
		}
	}
	
	/**
	 * Converts a <code>File</code> to a file  URI.
	 * 
	 * @param file The <code>File</code> to convert.
	 * @return The given file as file URI.
	 */
	public static URI fileToFileUri(File file) {
		return URI.createFileURI(fileToPath(file));
	}

	/**
	 * Returns the folder path.
	 * 
	 * @param filePath
	 *            the file or folder path.
	 * @return the folder path.
	 */
	public static File getDirectory(String filePath) {
		File path = new File(filePath);

		if (path.isFile()) {
			return new File(path.getParent());
		} else {
			return path;
		}
	}
}
