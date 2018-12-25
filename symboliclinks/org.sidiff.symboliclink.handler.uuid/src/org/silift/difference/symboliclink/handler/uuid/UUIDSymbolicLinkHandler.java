package org.silift.difference.symboliclink.handler.uuid;


import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.collections.CollectionUtil;
import org.sidiff.common.emf.EMFResourceUtil;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.access.EObjectLocation;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.handler.AbstractDifferenceSymbolicLinkHandler;
import org.silift.difference.uuidsymboliclink.UUIDSymbolicLinkObject;
import org.silift.difference.uuidsymboliclink.UuidsymboliclinkFactory;

/**
 * An UUID-based symbolic-link-handler.
 * 
 * @author cpietsch
 *
 */
public class UUIDSymbolicLinkHandler extends AbstractDifferenceSymbolicLinkHandler {

	@Override
	public SymbolicLinkObject generateInternalSymbolicLinkObject(EObject eObject) {
		
		UUIDSymbolicLinkObject link = null;
		String uuid = deriveUUID(eObject);
		
		if(uuid != null){
			link = UuidsymboliclinkFactory.eINSTANCE.createUUIDSymbolicLinkObject();
			
			try{
				link.setName(eObject.eGet(eObject.eClass().getEStructuralFeature("name")).toString());
			}catch (NullPointerException e){
				System.out.println("INFO: " + eObject + " has no name");
			}
			link.setUuid(uuid);
			
			link.setReliability(1.f);
		}
		
		return link;
		
	}

	@Override
	public EObject resolveSymbolicLinkObject(SymbolicLinkObject symbolicLink, Resource targetModel) {
		
		UUIDSymbolicLinkObject uuidSymbolicLink = (UUIDSymbolicLinkObject)symbolicLink;
		
		for (EObject eObject : CollectionUtil.asIterable(targetModel.getAllContents())) {
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
	private static String deriveUUID(EObject eObject){
		
		String uuid = EMFUtil.getXmiId(eObject);
		
		assert (!(eObject instanceof EGenericType) || uuid != null): eObject +"("+eObject.eClass().getName()+")" + "has no uuid";
		
		return uuid;
	}

	@Override
	public boolean canComputeReliability() {
		return false;
	}
}
