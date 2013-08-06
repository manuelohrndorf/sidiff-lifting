package org.sidiff.patching.ui.view.filter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.sidiff.patching.report.PatchReport.Status;
import org.sidiff.patching.report.PatchReport.Type;
import org.sidiff.patching.report.ReportEntry;

public class ReportViewFilter extends ViewerFilter {

	private Type type;

	public ReportViewFilter(Type type) {
		this.type = type;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof ReportEntry) {
			ReportEntry entry = (ReportEntry) element;
			return entry.getType() != type || entry.getStatus() != Status.PASSED;
		}
		return true;
	}

}
