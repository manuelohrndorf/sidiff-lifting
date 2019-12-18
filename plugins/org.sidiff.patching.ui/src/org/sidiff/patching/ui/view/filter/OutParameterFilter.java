package org.sidiff.patching.ui.view.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.editrule.rulebase.ParameterDirection;

public class OutParameterFilter extends ViewerFilter{

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return !(element instanceof ObjectParameterBinding)
				|| !((ObjectParameterBinding)element).getFormalParameter().getDirection().equals(ParameterDirection.OUT);
	}
}
