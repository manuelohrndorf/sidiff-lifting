package org.sidiff.vcmsintegration.properties;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;

/**
 * A utility class that contains functions for retrieving the property sheet page and changing its selection.
 * @author Robert Müller
 *
 */
public class PropertySheetPageHelper {

	protected static IPropertySheetPage getPropertySheetPage() {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if(activePage != null) {
			IViewPart view = activePage.findView(IPageLayout.ID_PROP_SHEET);
			if(view != null) {
				if(view instanceof PropertySheet) {
					PropertySheet propertySheet = (PropertySheet)view;
					IPage page = propertySheet.getCurrentPage();
					if(page instanceof IPropertySheetPage) {
						return (IPropertySheetPage)page;
					}
				}
			}
		}
		return null;
	}

	public static void notifiySelectionChanged(IWorkbenchPart part, ISelection selection) {
		IPropertySheetPage page = getPropertySheetPage();
		if(page != null) {
			page.selectionChanged(part, selection);
		}
	}
}
