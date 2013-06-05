package org.sidiff.serge.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.common.emf.ecore.EClassVisitor;
import org.sidiff.common.emf.ecore.ECoreTraversal;
import org.sidiff.common.emf.modelstorage.ModelStorage;
import org.sidiff.serge.EditRulesGeneratingService;
import org.sidiff.serge.services.HenshinTransformationGenerator;
import org.sidiff.serge.util.Common;

public class EditRulesGeneratingServiceImpl implements EditRulesGeneratingService{

	@Override
	public void generate(Class<?> service, String outputFolderPath, Resource metaModel, String config, String workspace_loc) {

		ArrayList<EPackage> ePackages = new ArrayList<EPackage>();
		Iterator<EObject> it = metaModel.getAllContents();
		while(it.hasNext()) {
			EObject elem = it.next();
			if(elem instanceof EPackage) {
				// only add EPackages which not only contain further EPackages
				// and not only contain abstract model elements
				for(EObject e: ((EPackage)elem).eContents()) {
					EStructuralFeature es = e.eClass().getEStructuralFeature("abstract");				
					if(es!=null) {
						if(e.eGet(es).equals(false) && !ePackages.contains((EPackage)elem)) {
							ePackages.add((EPackage)elem);
						}
					}					
				}			
			}else if(elem instanceof EClass){
				// if profileApplications recognizable via "base_"-features
				// also add ePackages of the targeted meta classes of these references.
				// It might be required to resolve eProxyURIs and load the referenced
				// ecore file into the EPackage Registry.
				List<EStructuralFeature> extensions = EMFMetaAccess.getEStructuralFeaturesByRegEx((EClass)elem, "^(base)_\\w+", true);
				for(EStructuralFeature extension: extensions) {
					if(extension.getEType().eIsProxy()) {
						
						// resolve eProxyURI of referenced meta model
						assert(workspace_loc!=null): "Add workspace_log variable into your run configuration at the bottom";
						
						String completePath = Common.convertEProxyURIToFilePath(extension.getEType().toString(), workspace_loc);
						
						File ecoreFile = new File(completePath);
						
						assert(ecoreFile!=null): "The Metamodel uses other ecore files that are not inside your workspace and its correct plugin";
						
						// load the referenced ecore file into the EPackage Registry.
						String modelKey = ModelStorage.getInstance().loadEMF(ecoreFile.getPath());					
						Resource referencedEcoreRes = ModelStorage.getInstance().getModel(modelKey);	
						
						EObject eObject = referencedEcoreRes.getContents().get(0);
						if (eObject instanceof EPackage) {
						    EPackage p = (EPackage)eObject;
						    if(!EPackage.Registry.INSTANCE.containsKey(p.getNsURI())) {
						    	EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
						    	if(!ePackages.contains(p)) {
						    		ePackages.add(p);
						    	}
						    }
						}
					}
					else{
						// meta model resolvable. Add relevant packages for visitor pattern
				    	if(!ePackages.contains(extension.getEType().getEPackage())) {
				    		ePackages.add(extension.getEType().getEPackage());
				    	}
					}
				}
			}
		}
		
		
		
		assert(service!=null) : "Service not found";
		assert(!ePackages.isEmpty()) : "No EPackages found under the given meta model";
		
		if(service == HenshinTransformationGenerator.class && !ePackages.isEmpty()){
			
			EClassVisitor henshinTrafoVisitor = new HenshinTransformationGenerator(outputFolderPath, ePackages, config);
			ECoreTraversal.traverse(henshinTrafoVisitor, ePackages.toArray(new EPackage[ePackages.size()]));
				
		}
		
	}	
	
}
