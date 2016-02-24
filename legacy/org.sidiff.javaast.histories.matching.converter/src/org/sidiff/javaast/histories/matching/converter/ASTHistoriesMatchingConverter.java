package org.sidiff.javaast.histories.matching.converter;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.javaast.model.JavaModelPackage;
import org.sidiff.matcher.BaseMatcher;

import differencemodel.Correspondence;
import differencemodel.Difference;
import differencemodel.DifferencemodelPackage;

/**
 * This matcher converts correspondences of the old @see{Difference} model into the new @see{MatchingModel} 
 *
 * It does not match or compute a new matching on its own, a already existing difference file is needed.
 * 
 * Therefore priorly computed matchings can be reused without recomputing them.
 * 
 * @author dreuling
 *
 */
public class ASTHistoriesMatchingConverter extends BaseMatcher{

	@Override
	public String getDocumentType() {
		return JavaModelPackage.eNS_URI;
	}

	@Override
	public String getName() {
		return "AST Matcher";
	}

	@Override
	public String getDescription() {
		return "AST Matcher";
	}

	@Override
	protected void match() {
		
		//Get old matching/difference model
		//@see DifferenceModel in lifting/legacy
		
		// Be sure DifferenceModel is initialized
		DifferencemodelPackage.eINSTANCE.eClass();

		// Register the XMI resource factory for the .xmi extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());
		
		Iterator<Resource> it = getModels().iterator();
		Resource modelA = it.next();
		Resource modelB = it.next();		
		
		//Get old diff fileName based on modelA and modelB filenames
		String fileUriA = modelA.getURI().toString();
		String fileNameA = fileUriA.substring(0, fileUriA.indexOf("."));
		//TODO ...
		
		String fileName = "xxx";	
		Resource rdiff = EMFStorage.eLoad(URI.createURI(fileName)).eResource();
		assert rdiff!=null: "Difference not found at: " + fileName + ", aborting matching!";
		
		//Put all together for proxy resolution		
		ResourceSet rs = new ResourceSetImpl();
		rs.getResources().add(modelA);
		rs.getResources().add(modelB);
		rs.getResources().add(rdiff);
		EcoreUtil.resolveAll(rs);
		
		//Copy/Convert all old correspondences
		Difference diff = (Difference) rdiff.getContents().get(0);
		for(Correspondence c : diff.getCorrespondences()){
			this.getCorrespondencesService().addCorrespondence(c.getObjA(),c.getObjB());
		}		 
	}

}
