package org.sidiff.patching.ui.view.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.sidiff.patching.report.OperationExecutionEntry;

public class ReportViewFilter extends ViewerFilter {

	//private OperationExecutionKind kind;
	
	public ReportViewFilter() {
		
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof OperationExecutionEntry) {
			OperationExecutionEntry entry = (OperationExecutionEntry) element;
			//return entry.getKind() != kind;
			return true;
		}
		return true;
	}

}
