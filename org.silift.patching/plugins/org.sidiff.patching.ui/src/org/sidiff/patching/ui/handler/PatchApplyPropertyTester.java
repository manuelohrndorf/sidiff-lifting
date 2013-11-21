package org.sidiff.patching.ui.handler;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IFile;
public class PatchApplyPropertyTester extends PropertyTester {

	public PatchApplyPropertyTester() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		IFile file = (IFile) receiver;
		String filePath = file.getLocation().toOSString();
		if(filePath.endsWith("slp")) return true;
		
		return false;
	}

}
