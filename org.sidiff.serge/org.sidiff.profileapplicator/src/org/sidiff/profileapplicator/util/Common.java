package org.sidiff.profileapplicator.util;

import org.w3c.dom.NamedNodeMap;

public class Common {	

	public static String getAttributeValue(String attribName, org.w3c.dom.Node node) {
			
		NamedNodeMap attribs = node.getAttributes(); 
		for(int i=0; i<attribs.getLength(); i++) {
			if(attribs.item(i).getNodeName().equals(attribName)) {
				return attribs.item(i).getNodeValue();
			}
		}

		return null;
	}	
	
}
