package org.sidiff.integration.compare.properties;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * @author rmueller
 */
public class CompareEditorPropertySheetPageAdapterFactory implements IAdapterFactory {

	public static final String CONTRIBUTOR_ID = "org.sidiff.integration.compare.editor";

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if(adapterType == IPropertySheetPage.class) {
			return adapterType.cast(new TabbedPropertySheetPage(new ITabbedPropertySheetPageContributor() {
				@Override
				public String getContributorId() {
					return CONTRIBUTOR_ID;
				}
			}));
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { IPropertySheetPage.class };
	}
}
