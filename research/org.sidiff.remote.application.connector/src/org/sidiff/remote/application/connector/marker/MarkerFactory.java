package org.sidiff.remote.application.connector.marker;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * 
 * @author cpietsch
 *
 */
public class MarkerFactory {

	public static final String MARKER_DIRTY_RESOURCE_ID = "org.sidiff.remote.application.connector.marker.dirty";
	public static final String MARKER_DIRTY_RESOURCE_MESSAGE = "uncommited changes";
	
	public static void createMarker(IResource resource, String type, String message, int severity) throws CoreException {
		if(resource.findMarkers(MARKER_DIRTY_RESOURCE_ID, true, IResource.DEPTH_INFINITE).length == 0) {
			IMarker marker = resource.createMarker(type);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.SEVERITY, severity);
		}
	}
	
	public static void deleteMarkers(IResource resource, String type) throws CoreException {
		resource.deleteMarkers(type, true, IResource.DEPTH_INFINITE);
	}
	
	public static IMarker[] findMarkers(IResource resource, String type) throws CoreException {
	      IMarker[] markers = resource.findMarkers(type, true, IResource.DEPTH_INFINITE);
	      return markers;
	}
}
