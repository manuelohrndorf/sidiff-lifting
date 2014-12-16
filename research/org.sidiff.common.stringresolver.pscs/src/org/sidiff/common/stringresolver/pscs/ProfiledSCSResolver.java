package org.sidiff.common.stringresolver.pscs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.stringresolver.GenericStringResolver;
import org.sidiff.common.stringresolver.IStringResolver;
import org.sidiff.common.stringresolver.util.StringResolverUtil;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricPackage;

public class ProfiledSCSResolver implements IStringResolver {

	@Override
	public boolean canHandleDocType(String docType) {
		return docType.equals(SymmetricPackage.eNS_URI);
	}

	@Override
	public String getDocType() {
		return SymmetricPackage.eNS_URI;
	}

	@Override
	public String getKey() {
		return "PSCSResolver";
	}

	@Override
	public String getName() {
		return "Profiled SCS Resolver";
	}

	@Override
	public String resolve(EObject eObject) {		
		String result = null;
		
		//Check whether this is a SCS
		if(eObject instanceof SemanticChangeSet){
			SemanticChangeSet scs = (SemanticChangeSet) eObject;
			
			// List of all stereotypes added to relevant objects
			List<EObject> stereoTypes = new ArrayList<>();
			
			// Iterate through all changes
			for(Change change : scs.getChanges()){
			
				//Which type of change has occurred
				if(change instanceof AddObject){
					AddObject ao = (AddObject) change;
					stereoTypes = EMFModelAccess.getStereoTypes(ao.getObj());					
				}
				else if(change instanceof RemoveObject){
					RemoveObject ro = (RemoveObject) change;
					stereoTypes = EMFModelAccess.getStereoTypes(ro.getObj());					

				}
				else if(change instanceof AddReference){
					AddReference ar = (AddReference) change;
					Set<EObject> stereoTypeSet = new HashSet<>();
					stereoTypeSet.addAll(EMFModelAccess.getStereoTypes(ar.getSrc()));					
					stereoTypeSet.addAll(EMFModelAccess.getStereoTypes(ar.getTgt()));
					stereoTypes = new ArrayList<>(stereoTypeSet);
				}
				else if(change instanceof RemoveReference){
					RemoveReference rr = (RemoveReference) change;
					Set<EObject> stereoTypeSet = new HashSet<>();
					stereoTypeSet.addAll(EMFModelAccess.getStereoTypes(rr.getSrc()));					
					stereoTypeSet.addAll(EMFModelAccess.getStereoTypes(rr.getTgt()));
					stereoTypes = new ArrayList<>(stereoTypeSet);
				}
				
				// AttributeValueChange
				else{
					AttributeValueChange avc = (AttributeValueChange) change;
					Set<EObject> stereoTypeSet = new HashSet<>();
					stereoTypeSet.addAll(EMFModelAccess.getStereoTypes(avc.getObjA()));					
					stereoTypeSet.addAll(EMFModelAccess.getStereoTypes(avc.getObjB()));
					stereoTypes = new ArrayList<>(stereoTypeSet);
				}
			}		
			
			// Use generic resolver 
			GenericStringResolver gr = new GenericStringResolver();
			result = gr.resolve(scs);
			if(stereoTypes.size() > 0){
				result += "<<";
				for(EObject stereoType : stereoTypes){
					result += StringResolverUtil.
							getAvailableStringResolver(
									EMFModelAccess.getDocumentType(stereoType)).resolve(stereoType);
					result += ",";
				}
				result = result.substring(0, result.length()-1);
				result += ">>";
			}
		}
		return result;
	}

	@Override
	public String resolveQualified(EObject eObject) {
		//The same as non qualified
		return resolve(eObject);
	}
	
}
