package org.sidiff.editrule.generator.serge.ui.handlers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;

public class SergeFilePropertyTester extends PropertyTester {

	private static final String SERGEFILE = "sergeFile";
	private static final String SERGEENDING = ".xml";


	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {

		if(property.equals(SERGEFILE)){

			if(receiver instanceof IFile){
				IFile file = (IFile) receiver;
				String filePath = file.getLocation().toOSString();
				if(filePath.endsWith(SERGEENDING)){
					return true;
				}
			}

		}
		return false;

	}
}
