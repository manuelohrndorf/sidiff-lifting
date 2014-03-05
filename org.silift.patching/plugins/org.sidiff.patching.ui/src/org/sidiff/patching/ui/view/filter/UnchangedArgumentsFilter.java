package org.sidiff.patching.ui.view.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.patching.arguments.ArgumentWrapper;

public class UnchangedArgumentsFilter extends ViewerFilter {
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof ParameterBinding) {
			ParameterBinding binding = (ParameterBinding) element;
			//return !argument.isDefaultValue();
		}
		return true;
	}

}
