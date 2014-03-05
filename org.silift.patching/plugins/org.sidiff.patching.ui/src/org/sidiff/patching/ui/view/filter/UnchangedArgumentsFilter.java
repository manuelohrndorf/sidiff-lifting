package org.sidiff.patching.ui.view.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.patching.arguments.ArgumentWrapper;

public class UnchangedArgumentsFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof ValueParameterBinding) {
			ValueParameterBinding binding = (ValueParameterBinding) element;
		
			//Check if there is a default value at all
			if(binding.getFormalParameter().getType().getDefaultValue() == null){
				//Check if there is a value at all
				if(binding.getActual() == null){
					return false;
				}
				else{
					return true;
				}
			}

			//Check whether the current value equals the default value
			else{
				return !binding.getFormalParameter().getType().getDefaultValue().toString().equals(binding.getActual().toString());
			}
		}
		return true;
	}

}
