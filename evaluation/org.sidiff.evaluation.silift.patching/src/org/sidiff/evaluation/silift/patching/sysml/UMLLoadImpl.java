package org.sidiff.evaluation.silift.patching.sysml;

import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMILoadImpl;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Note: copied from UMLHandler (6.9.13)
 * 
 * @author kehrer
 *
 */
public class UMLLoadImpl extends XMILoadImpl {

	public UMLLoadImpl(XMLHelper helper) {
		super(helper);
	}

	@Override
	protected DefaultHandler makeDefaultHandler() {
		return new UMLHandler(resource, helper, options);
	}
}