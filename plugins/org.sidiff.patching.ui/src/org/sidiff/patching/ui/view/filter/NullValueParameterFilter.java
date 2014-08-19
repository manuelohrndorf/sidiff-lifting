package org.sidiff.patching.ui.view.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.sidiff.difference.asymmetric.ValueParameterBinding;

public class NullValueParameterFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof ValueParameterBinding) {
			ValueParameterBinding parameterBinding = (ValueParameterBinding) element;
			return parameterBinding.getActual() != null;

		}
		return true;
	}

}
