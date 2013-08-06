package org.sidiff.patching.ui.view;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.patching.util.PatchUtil;

public class PatchContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

	@Override
	public Object[] getElements(Object object) {
		return getChildren(object);
	}

	@Override
	public Object[] getChildren(Object object) {
		if (object instanceof AsymmetricDifference) {
			AsymmetricDifference difference = (AsymmetricDifference) object;
			return PatchUtil.getOrderdOperationInvocations(difference.getOperationInvocations()).toArray();
		} else if (object instanceof OperationInvocation) {
			OperationInvocation operationInvocation = (OperationInvocation) object;
			return operationInvocation.getParameterBindings().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof EObject) {
			EObject eObject = (EObject) element;
			return eObject.eContainer();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof AsymmetricDifference) {
			AsymmetricDifference difference = (AsymmetricDifference) object;
			return !difference.getOperationInvocations().isEmpty();
		} else if (object instanceof OperationInvocation) {
			OperationInvocation operationInvocation = (OperationInvocation) object;
			return !operationInvocation.getParameterBindings().isEmpty();
		}
		return false;
	}


}
