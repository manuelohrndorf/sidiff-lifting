package org.sidiff.profileapplicator.impl;

import org.sidiff.common.io.IOUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.profileapplicator.ProfileApplicatorService;
import org.sidiff.profileapplicator.services.ProfileApplicator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ProfileApplicatorServiceImpl implements ProfileApplicatorService{

	private static ProfileApplicator applicator  = null;
		
	public ProfileApplicatorServiceImpl() {
		
	}
	
	public void init(Class<?> service, String pathToConfig, String pathToInputFolder, String pathToOutputFolder) {
	
		if(service == ProfileApplicator.class){			
			applicator = new ProfileApplicator();
		}//TODO else exception
		
		applicator.setConfigPath(pathToConfig);
		applicator.setInputFolderPath(pathToInputFolder);
		applicator.setOutputFolderPath(pathToOutputFolder);
		
			
		LogUtil.log(LogEvent.NOTICE, "Interpreting Configuration File...");
		
		Document doc = XMLParser.parseStream(IOUtil.getInputStream(applicator.getConfigPath()));		
		Element docElem = doc.getDocumentElement();
		org.w3c.dom.Node currentNode = null;
		NodeList currentChildNodes = null;
		
		// retrieve and set 	
			
		currentNode = doc.getElementsByTagName("baseTypeOnly").item(0);
		applicator.setBaseTypeOnly((Boolean.valueOf(Common.getAttributeValue("allow", currentNode))));
		
	
		
	
		

	}	
	
	
	@Override
	public void applyProfile(Class<?> service) {

		assert(service!=null) : "Service not found";				
		//TODO
		
	}	
	
}
