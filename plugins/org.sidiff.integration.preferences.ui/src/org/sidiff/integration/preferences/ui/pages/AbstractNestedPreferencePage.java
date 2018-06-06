package org.sidiff.integration.preferences.ui.pages;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * Abstract superclass for preference pages that contain tabs containing other preference pages.
 * @author Robert Müller
 *
 */
public abstract class AbstractNestedPreferencePage extends PropertyAndPreferencePage {

	private TabFolder tabFolder;
	private List<PropertyAndPreferencePage> settingsPages;

	@Override
	protected Control doCreateContents(Composite parent) {
		tabFolder = new TabFolder(parent, SWT.TOP);
		for(PropertyAndPreferencePage page : getSettingsPages()) {
			createTab(page);
		}
		return tabFolder;
	}

	@Override
	protected void reloadPreferences() {
		for(PropertyAndPreferencePage page : getSettingsPages()) {
			page.reloadPreferences();
		}
	}

	@Override
	protected void defaultPreferences() {
		for(PropertyAndPreferencePage page : getSettingsPages()) {
			page.defaultPreferences();;
		}
	}

	@Override
	protected void savePreferences() {
		for(PropertyAndPreferencePage page : getSettingsPages()) {
			page.savePreferences();
		}
	}

	@Override
	protected void validatePreferences() {
		for(PropertyAndPreferencePage page : getSettingsPages()) {
			page.validatePreferences();
		}
	}

	protected List<PropertyAndPreferencePage> getSettingsPages() {
		if(settingsPages == null) {
			settingsPages = createSubPages();
			for(PropertyAndPreferencePage page : settingsPages) {
				page.setParentPage(this);
			}
		}
		return settingsPages;
	}

	private void createTab(PropertyAndPreferencePage page) {
		TabItem item = new TabItem(tabFolder, SWT.NONE);
		item.setText(page.getTitle());

		Composite content = new Composite(tabFolder, SWT.NONE);
		content.setLayout(new FillLayout(SWT.HORIZONTAL));
		page.createContents(content);

		item.setControl(content);
	}

	@Override
	public void dispose() {
		if(settingsPages != null) {
			for(PropertyAndPreferencePage page : settingsPages) {
				page.dispose();
			}
			settingsPages = null;
		}
		super.dispose();
	}

	@Override
	public void setElement(IAdaptable element) {
		super.setElement(element);
		for(PropertyAndPreferencePage page : getSettingsPages()) {
			page.setElement(element);
		}
	}

	/**
	 * Creates and returns the pages that are nested within this one.
	 * A tab is created for each page, using the title of the page as the tab's title.
	 * @return list of pages that are nested within this one
	 */
	protected abstract List<PropertyAndPreferencePage> createSubPages();
}
