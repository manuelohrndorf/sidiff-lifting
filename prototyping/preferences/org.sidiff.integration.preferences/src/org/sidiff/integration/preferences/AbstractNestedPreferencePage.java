package org.sidiff.integration.preferences;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchPropertyPage;

/**
 * Abstract superclass for preference pages that contain tabs containing other preference pages.
 * @author Robert Müller
 *
 */
public abstract class AbstractNestedPreferencePage extends PreferencePage implements IWorkbenchPreferencePage, IWorkbenchPropertyPage {

	private TabFolder tabFolder;
	private List<PropertyAndPreferencePage> settingsPages;
	private IAdaptable element;

	@Override
	protected Control createContents(Composite parent) {
		tabFolder = new TabFolder(parent, SWT.TOP);
		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = tabFolder.getSelectionIndex();
				if(index < 0 || index >= settingsPages.size())
					return;
				onPageChanged(index);
			}
		});

		for(PropertyAndPreferencePage page : getSettingsPages()) {
			createTab(page);
		}
		return tabFolder;
	}

	protected List<PropertyAndPreferencePage> getSettingsPages() {
		if(settingsPages == null) {
			settingsPages = createSubPages();
		}
		return settingsPages;
	}

	private void createTab(PropertyAndPreferencePage page) {
		TabItem item = new TabItem(tabFolder, SWT.NONE);
		item.setText(page.getTitle());

		Composite content = new Composite(tabFolder, SWT.NONE);
		content.setLayout(new FillLayout(SWT.HORIZONTAL));
		page.createControl(content);

		item.setControl(content);
	}

	@Override
	public void init(IWorkbench workbench) {
		for(PropertyAndPreferencePage page : getSettingsPages()) {
			page.init(workbench);
		}
	}

	@Override
	public void dispose() {
		for(PropertyAndPreferencePage page : getSettingsPages()) {
			page.dispose();
		}
		super.dispose();
	}

	@Override
	public boolean performOk() {
		boolean result = true;
		for(PropertyAndPreferencePage page : getSettingsPages()) {
			result &= page.performOk();
		}
		return result;
	}

	@Override
	protected void performDefaults() {
		for(PropertyAndPreferencePage page : getSettingsPages()) {
			page.performDefaults();
		}
	}

	@Override
	public IAdaptable getElement() {
		return element;
	}

	@Override
	public void setElement(IAdaptable element) {
		this.element = element;

		for(PropertyAndPreferencePage page : getSettingsPages()) {
			page.setElement(element);
		}
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		int index = tabFolder.getSelectionIndex();
		if(index < 0 || index >= settingsPages.size())
			return null;
		return settingsPages.get(index).getPreferenceStore();
	}

	protected abstract List<PropertyAndPreferencePage> createSubPages();
	protected abstract void onPageChanged(int newIndex);
}
