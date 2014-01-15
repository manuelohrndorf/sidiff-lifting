package org.sidiff.patching.ui.view;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;
import org.sidiff.patching.report.ReportEntry;

public class ReportEntryAdapterFactory implements IAdapterFactory {

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType== IPropertySource.class && adaptableObject instanceof ReportEntry){
		      return new ReportEntryPropertySource((ReportEntry) adaptableObject);
		    }
		    return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] { IPropertySource.class };
	}

}
