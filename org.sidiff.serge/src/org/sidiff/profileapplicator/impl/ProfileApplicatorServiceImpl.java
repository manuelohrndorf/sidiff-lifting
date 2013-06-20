package org.sidiff.profileapplicator.impl;

import org.sidiff.profileapplicator.ProfileApplicatorService;
import org.sidiff.profileapplicator.services.ProfileApplicator;

public class ProfileApplicatorServiceImpl implements ProfileApplicatorService{

	private static ProfileApplicator applicator  = null;
		
	public ProfileApplicatorServiceImpl() {
		
	}
	
	@Override
	public void init(Class<?> service, String pathToConfig, String pathToInputFolder, String pathToOutputFolder) {
	
		if(service == ProfileApplicator.class){			
			applicator = new ProfileApplicator();
		}//TODO else exception
		
		applicator.setOutputFolderPath(pathToOutputFolder);
		
		/*
				
		LogUtil.log(LogEvent.NOTICE, "Interpreting Configuration File...");
		
		Document doc = XMLParser.parseStream(IOUtil.getInputStream(pathToConfig));
		
		Element docElem = doc.getDocumentElement();
		org.w3c.dom.Node currentNode = null;
		NodeList currentChildNodes = null;
		
		// retrieve and set general settings	
			
		currentNode = doc.getElementsByTagName("preventInconsistency").item(0);
		generator.setPreventInconsistencyThroughSkipping(Boolean.valueOf(Common.getAttributeValue("value", currentNode)));
		currentNode = doc.getElementsByTagName("initialChecks").item(0);		
		generator.setCreateINITIALS(Boolean.valueOf(Common.getAttributeValue("allow", currentNode)));
		currentNode = doc.getElementsByTagName("disableVariants").item(0);		
		generator.setDisableVariants(Boolean.valueOf(Common.getAttributeValue("value", currentNode)));
		currentNode = doc.getElementsByTagName("modelUsesProfileMechanism").item(0);		
		enableStereotypeMapping = Boolean.valueOf(Common.getAttributeValue("value", currentNode));
		generator.setProfileApplicationInUse(enableStereotypeMapping);
		currentNode = doc.getElementsByTagName("reduceToSuperType").item(0);
		generator.setReduceToSuperType_SETUNSET(Boolean.valueOf(Common.getAttributeValue("SET_UNSET", currentNode)));
		generator.setReduceToSuperType_ADDREMOVE(Boolean.valueOf(Common.getAttributeValue("ADD_REMOVE", currentNode)));
		generator.setReduceToSuperType_CHANGE(Boolean.valueOf(Common.getAttributeValue("CHANGE", currentNode)));
		
		*/
		

	}	
	
	
	@Override
	public void applyProfile(Class<?> service) {

		assert(service!=null) : "Service not found";				
		//TODO
		
	}	
	
}
