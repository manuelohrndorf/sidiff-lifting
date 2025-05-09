package org.sidiff.patching.ui.view.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.sidiff.difference.asymmetric.ParameterBinding;

public class UnchangedArgumentsFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		ParameterBinding binding = (ParameterBinding) element;		
		return !binding.isDefaultValue();
	}
}
