package org.sidiff.difference.symmetric.compareview.internal.handler;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.ui.IEditorPart;
import org.sidiff.difference.symmetric.SymmetricDifference;

public class DifferencePropertyTester extends PropertyTester {

	public DifferencePropertyTester() {

	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		assert(receiver instanceof IEditorPart);

		IEditorPart editor = (IEditorPart) receiver;
		if(editor instanceof IEditingDomainProvider){
			EditingDomain editingDomain = ((IEditingDomainProvider) editor).getEditingDomain();

			for(Resource resource : editingDomain.getResourceSet().getResources()){
				if(resource.getContents().get(0) instanceof SymmetricDifference){
					return true;
				}
			}
		}
		
		return false;
	}

}
