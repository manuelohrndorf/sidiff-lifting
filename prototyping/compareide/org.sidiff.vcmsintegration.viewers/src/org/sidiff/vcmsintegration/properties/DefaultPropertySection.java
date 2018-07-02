package org.sidiff.vcmsintegration.properties;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * An {@link AdvancedPropertySection} that sets the property source
 * provider of the page to a {@link CompareEditorPropertySourceProvider}.
 * @author Robert Müller
 *
 */
public class DefaultPropertySection extends AdvancedPropertySection {

	public DefaultPropertySection() {
		super();
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		page.setPropertySourceProvider(new CompareEditorPropertySourceProvider());
	}
}
