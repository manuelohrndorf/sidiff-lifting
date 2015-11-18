package org.sidiff.editrule.consistency.fixing.ide;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.editrule.consistency.fixing.ERFixingEngine;

/**
 * This class represents a fix for a occurred validation error
 * of an @link{EditRule}.
 * 
 * It just executes the fixit() method of the @link{ERFixingEngine} class.
 * This shall be more adaptable, as this class is just a generic
 * way of handling EditRule errors, no specific label, description or icon
 * is possible because this way of implementation.
 * 
 * @author dreuling
 *
 */
public class EditRuleFix extends WorkbenchMarkerResolution {

	private final String validationType;
	private final IMarker originalMarker;

	EditRuleFix(String validationType, IMarker originalMarker) {
		this.validationType = validationType;	
		this.originalMarker = originalMarker;
	}

	@Override
	public void run(IMarker marker) {

		//Load all corresponding elements
		String uriAttribute = marker.getAttribute(EValidator.URI_ATTRIBUTE,	null);

		Module module = (Module) EMFStorage.eLoad(EMFStorage.pathToUri(marker.getResource().getLocation().toOSString()));
		EObject eObject = null;
		if (uriAttribute != null) {
			URI uri = URI.createURI(uriAttribute);
			eObject = module.eResource().getResourceSet().getEObject(uri, true);
		}		

		List<EObject> eObjects = new ArrayList<EObject>();
		if(marker.getAttribute(	EValidator.RELATED_URIS_ATTRIBUTE, null) != null){
			String[] relatedURIs = marker.getAttribute(	EValidator.RELATED_URIS_ATTRIBUTE, null).split(" ");
			for (String attribute : relatedURIs) {
				URI uri = URI.createURI(attribute);
				EObject eObj = module.eResource().getResourceSet().getEObject(uri, true);
				if (eObj != null) {
					eObjects.add(eObj);
				}
			}
		}

		// Fix the ValidationError
		try {
			ERFixingEngine fixEngine = new ERFixingEngine();
			fixEngine.fixit(validationType, module, eObject, eObjects);	
			module.eResource().save(null);

			// If successful, delete marker afterwards
			marker.delete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Override
	public String getDescription() {
		return getLabel();
	}


	@Override
	public Image getImage() {			
		return Activator.getImageDescriptor("fixEngine.gif").createImage();
	}

	public String getLabel() {
		return "Execute Edit-Rule Fixing Engine";
	}


	public String getValidationType() {
		return validationType;
	}

	@Override
	public IMarker[] findOtherMarkers(IMarker[] markers) {
		List<IMarker> others = new ArrayList<IMarker>();
		// Iterate through all markers and return
		// only those which are suitable				
		for (IMarker marker : markers) {
			if (isValidOther(marker)) {
				others.add(marker);
			}
		}
		return others.toArray(new IMarker[0]);
	}

	/**
	 * Checks whether another marker is valid
	 * to use the same @link{EditRuleFix}.
	 * @param marker
	 * @return
	 */
	public boolean isValidOther(final IMarker marker) {

		// Is it the originalMarker, we don't want duplicates!
		if(marker.equals(originalMarker)) {
			return false;
		}

		// Is it of the right MarkerType ?
		try {
			if(!marker.getType().equals(EValidator.MARKER)){
				return false;
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Has the marker the same @link{ValidationType} ?
		if(!(marker.getAttribute("rule", null).equals(validationType))){
			return false;
		}

		// This marker is a valid other
		return true;

	}

}
