package org.sidiff.editrule.tools.handlers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

public class HenshinPropertyTester extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if(receiver instanceof IResource) {
			IResource resource = (IResource) receiver;
			try {
				return !CleanUpDynamicEStructuralFeaturesHandler.findAllHenshinFiles(resource).isEmpty();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

}
