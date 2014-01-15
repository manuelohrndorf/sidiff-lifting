package org.sidiff.patching.ui.view;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.sidiff.patching.report.ReportEntry;

public class ReportEntryPropertySource implements IPropertySource {

	private final ReportEntry reportEntry;
	
	public ReportEntryPropertySource(ReportEntry reportEntry){
		this.reportEntry = reportEntry;
	}
	@Override
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		return new IPropertyDescriptor[]{
			new TextPropertyDescriptor("description", "Description") };
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("description")) {
			return reportEntry.getDescription();
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub

	}

}
