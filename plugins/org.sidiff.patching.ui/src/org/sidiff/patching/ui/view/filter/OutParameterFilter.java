package org.sidiff.patching.ui.view.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.rulebase.ParameterDirection;

public class OutParameterFilter extends ViewerFilter{

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof ObjectParameterBinding && ((ObjectParameterBinding)element).getFormalParameter().getDirection().equals(ParameterDirection.OUT))
			return false;
		return true;
	}

}
