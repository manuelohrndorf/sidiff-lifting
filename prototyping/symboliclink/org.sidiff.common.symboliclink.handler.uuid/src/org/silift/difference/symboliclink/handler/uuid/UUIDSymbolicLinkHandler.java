package org.silift.difference.symboliclink.handler.uuid;


import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.EMFUtil;
import org.silift.common.util.emf.EMFResourceUtil;
import org.silift.common.util.emf.EObjectLocation;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.handler.AbstractSymbolicLinkHandler;
import org.silift.difference.uuidsymboliclink.UUIDSymbolicLinkObject;
import org.silift.difference.uuidsymboliclink.UuidsymboliclinkFactory;

/**
 * An UUID-based symbolic-link-handler.
 * 
 * @author cpietsch
 *
 */
public class UUIDSymbolicLinkHandler extends AbstractSymbolicLinkHandler {

	private static final String NAME = "UUID Symbolic Link Handler";
	private static final String KEY = "UUIDSymbolicLinkHandler";

	@Override
	public SymbolicLinkObject generateSymbolicLinkObject(Map<EObject, SymbolicLinkObject> obj2symbl, EObject eObject) {
		if(obj2symbl.containsKey(eObject)){
			return obj2symbl.get(eObject);
		}else{
			String uuid = deriveUUID(eObject);		
			UUIDSymbolicLinkObject link = UuidsymboliclinkFactory.eINSTANCE.createUUIDSymbolicLinkObject();
			link.setName(eObject.eGet(eObject.eClass().getEStructuralFeature("name")).toString());
			link.setUuid(uuid);
			link.setReliability(1.f);
			obj2symbl.put(eObject, link);
			return link;
		}
	}

	@Override
	public EObject resolveSymbolicLink(SymbolicLinkObject symbolicLink, Resource targetModel) {
		UUIDSymbolicLinkObject uuidSymbolicLink = (UUIDSymbolicLinkObject)symbolicLink;
		for (Iterator<EObject> iterator = targetModel.getAllContents(); iterator.hasNext();) {
			EObject eObject = (EObject) iterator.next();
			String uuid = deriveUUID(eObject);
			EObjectLocation location = EMFResourceUtil.locate(targetModel, eObject);
			if(location.equals(EObjectLocation.RESOURCE_INTERNAL) && uuidSymbolicLink.getUuid().equals(uuid)){
				return eObject;
			}
		}
		return null;
	}
	
	/**
	 * Help method that derives the UUID of a model element.
	 * 
	 * @param eObject
	 * 			a model element
	 *
	 * @return a String representation of the UUID
	 */
	private String deriveUUID(EObject eObject){
		String uuid = EMFUtil.getXmiId(eObject);
		assert (eObject instanceof EGenericType || uuid != null): eObject + "has no uuid";
		return uuid;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}
	
	@Override
	public String getName(){
		return NAME;
	}
	
	@Override
	public String getKey(){
		return KEY;
	}
}
