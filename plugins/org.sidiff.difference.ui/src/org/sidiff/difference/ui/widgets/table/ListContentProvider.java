package org.sidiff.difference.ui.widgets.table;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

class ListContentProvider implements IStructuredContentProvider {
	
	
	public ListContentProvider() {
		super();
	}
	
	@Override
	public void dispose() {
		// Do nothing
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Do nothing
	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection<?>){
			Collection<?> entries = (Collection<?>)inputElement;
			Object[] objs = new Object[entries.size()];
			objs = entries.toArray(objs);
			return objs;
		} else {
			return new Object[0];
		}
	}

}
