package org.sidiff.vcmsintegration.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.sidiff.difference.symmetric.provider.AdapterToolTipLabelProvider;

/**
 * This class provides static utility method that are used in the project.
 * 
 * @author All Developers
 *
 */
public class Util {

	/**
	 * Uses the {@link InputStream} from the given
	 * {@link IStreamContentAccessor} to create a temporarily copy of the file
	 * as an {@link EObject}.
	 * 
	 * @param input The {@link IStreamContentAccessor} which provides an
	 *            {@link InputStream} to the file that is loaded
	 * @return The {@link EObject} that is provided by the stream
	 * @throws IOException If an IO error occurrs
	 * @throws CoreException If the contents of this object could not be
	 *             accessed
	 */
	public static EObject loadEObjectFromStream(IStreamContentAccessor input) throws IOException, CoreException {
		ITypedElement typedElement = (ITypedElement) input;
		InputStream in = input.getContents();
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		Resource resource = resourceSet.createResource(URI.createURI(typedElement.getName()));
		resource.load(new URIConverter.ReadableInputStream(new InputStreamReader(in)), null);
		return resource.getContents().get(0);
	}

	/**
	 * Uses the {@link InputStream} from the given
	 * {@link IStreamContentAccessor} to create a temporarily copy of the file
	 * as an {@link EObject}.
	 * 
	 * @param input The {@link IStreamContentAccessor} which provides an
	 *            {@link InputStream} to the file that is loaded
	 * @param path Path to the resource. Used as URI of the resource.
	 * @return The {@link EObject} that is provided by the stream
	 * @throws IOException If an IO error occurrs
	 * @throws CoreException If the contents of this object could not be
	 *             accessed
	 */
	public static EObject loadEObjectFromStream(IStreamContentAccessor input, String path) throws IOException, CoreException {
		InputStream in = input.getContents();
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		Resource resource = resourceSet.createResource(URI.createURI(path));
		resource.load(in, null);
		return resource.getContents().get(0);
	}

	/**
	 * Tries to convert the given element into an {@link IStreamContentAccessor}
	 * and therefore into a {@link Resource} object.
	 * 
	 * @param typedElement The element which is being converted into an
	 *            {@link Resource}
	 * @return The resource of the model that is represented by the given
	 *         element
	 * @throws IOException If an IO error occurs
	 * @throws CoreException If the contents of this object could not be
	 *             accessed
	 */
	public static Resource getResourceByTypedElement(ITypedElement typedElement) throws IOException, CoreException {
		return getResourceByTypedElement(typedElement, typedElement.getName());
	}

	/**
	 * Tries to convert the given element into an {@link IStreamContentAccessor}
	 * and therefore into a {@link Resource} object.
	 * 
	 * @param typedElement The element which is being converted into an
	 *            {@link Resource}
	 * @param path Path to the resource
	 * @return The resource of the model that is represented by the given
	 *         element
	 * @throws IOException If an IO error occurs
	 * @throws CoreException If the contents of this object could not be
	 *             accessed
	 */
	public static Resource getResourceByTypedElement(ITypedElement typedElement, String path) throws IOException, CoreException {
		if (typedElement instanceof IStreamContentAccessor) {
			return Util.loadEObjectFromStream((IStreamContentAccessor) typedElement, path).eResource();
		} else {
			return null;
		}
	}

	/**
	 * Returns a factory that provides support for composing several factories
	 * for different models into a single factory serving the union of the model
	 * objects.
	 * 
	 * @return The {@link ComposedAdapterFactory}
	 */
	public static ComposedAdapterFactory getAdapterFactory() {
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		return adapterFactory;
	}

	/**
	 * Description copied from {@link AdapterFactoryContentProvider}<br>
	 * Returns a content provider that wraps an AdapterFactory and delegates its
	 * JFace provider interfaces to corresponding adapter-implemented item
	 * provider interfaces.
	 * 
	 * @return The {@link AdapterFactoryContentProvider}
	 */
	public static AdapterFactoryContentProvider getAdapterFactoryContentProvider() {
		return new AdapterFactoryContentProvider(getAdapterFactory());
	}

	/**
	 * Description copied from {@link AdapterFactoryLabelProvider}<br>
	 * Returns a label provider that wraps an AdapterFactory and delegates its
	 * JFace provider interfaces to corresponding adapter-implemented item
	 * provider interfaces.
	 * 
	 * @return The {@link AdapterFactoryLabelProvider}
	 */
	public static AdapterFactoryLabelProvider getAdapterFactoryLabelProvider() {
		return new AdapterFactoryLabelProvider(getAdapterFactory());
	}

	/**
	 * Description copied from {@link AdapterToolTipLabelProvider}<br>
	 * Returns an an extended version of the adapter factory label provider that
	 * also provides for fonts and colors.
	 * 
	 * @return The {@link AdapterToolTipLabelProvider}
	 */
	public static AdapterToolTipLabelProvider getAdapterToolTipLabelProvider() {
		return new AdapterToolTipLabelProvider(getAdapterFactory());
	}

	/**
	 * Returns an Array containing only objects with the specified classes.
	 * 
	 * @param array Array to filter
	 * @return Array containing only object with the specified classes
	 */
	public static LinkedList<Object> filterArray(Object[] array, Class<?>... classes) {
		LinkedList<Object> tempList = new LinkedList<Object>();
		// loop through input array
		for (Object inputElement : array) {
			// check for each class if object is an instance
			for (Class<?> inputClass : classes) {
				if (inputClass.isInstance(inputElement)) {
					// if the object is an instance add it to the list and skip
					// to next input Element
					tempList.add(inputElement);
					break;
				}
			}
		}
		return tempList;
	}
}
