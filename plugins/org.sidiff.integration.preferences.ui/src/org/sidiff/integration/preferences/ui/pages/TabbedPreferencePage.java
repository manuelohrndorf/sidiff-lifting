package org.sidiff.integration.preferences.ui.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.sidiff.integration.preferences.util.PreferenceStoreUtil;

/**
 * A preference pages with tabs to select nested preferences pages.
 * @author rmueller
 */
public class TabbedPreferencePage extends PropertyAndPreferencePage {

	private TabFolder tabFolder;
	private List<Tab> tabs = new ArrayList<>();
	private String preferenceQualifier = PreferenceStoreUtil.PREFERENCE_QUALIFIER;

	public TabbedPreferencePage() {
		super();
	}

	public TabbedPreferencePage(String title) {
		super(title);
	}

	public TabbedPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control doCreateContents(Composite parent) {
		switch(tabs.size()) {
			case 0: return new Composite(parent, SWT.NONE);
			case 1: return tabs.get(0).page.createContents(parent);
			default: {
				tabFolder = new TabFolder(parent, SWT.TOP);
				for(Tab tab : tabs) {
					tab.createTabControl(tabFolder);
				}
				return tabFolder;
			}
		}
	}

	@Override
	protected void reloadPreferences() {
		for(Tab tab : tabs) {
			tab.page.reloadPreferences();
		}
	}

	@Override
	protected void defaultPreferences() {
		for(Tab tab : tabs) {
			tab.page.defaultPreferences();
		}
	}

	@Override
	protected void savePreferences() {
		for(Tab tab : tabs) {
			tab.page.savePreferences();
		}
	}

	@Override
	protected void validatePreferences() {
		for(Tab tab : tabs) {
			tab.page.validatePreferences();
		}
	}

	@Override
	public void dispose() {
		if(tabs != null) {
			for(Tab tab : tabs) {
				tab.page.dispose();
			}
			tabs = null;
		}
		super.dispose();
	}

	@Override
	public void setElement(IAdaptable element) {
		super.setElement(element);
		for(Tab tab : tabs) {
			tab.page.setElement(element);
		}
	}

	@Override
	protected String getHelpContextId() {
		int index = -1;
		if(tabFolder != null) {
			index = tabFolder.getSelectionIndex();
		} else if(tabs.size() > 0) {
			index = 0;
		}
		if(index == -1) {
			return null;
		}
		return tabs.get(index).page.getHelpContextId();
	}

	public void addTab(PropertyAndPreferencePage page, String title) {
		addTab(page, title, null);
	}

	public void addTab(PropertyAndPreferencePage page, String title, String tooltip) {
		if(tabFolder != null) {
			throw new IllegalStateException("Tabs must be added before the controls are created");
		}

		Tab tab = new Tab(Objects.requireNonNull(page), Objects.requireNonNull(title), tooltip);
		page.setParentPage(this);
		tabs.add(tab);
	}

	public void setPreferenceQualifier(String preferenceQualifier) {
		this.preferenceQualifier = Objects.requireNonNull(preferenceQualifier);
	}

	@Override
	public String getPreferenceQualifier() {
		return preferenceQualifier;
	}

	private static class Tab {
		final PropertyAndPreferencePage page;
		final String title;
		final String tooltip;

		Tab(PropertyAndPreferencePage page, String title, String tooltip) {
			this.page = page;
			this.title = title;
			this.tooltip = tooltip;
		}

		void createTabControl(TabFolder tabFolder) {
			TabItem item = new TabItem(tabFolder, SWT.NONE);
			item.setText(title);
			item.setToolTipText(tooltip);

			Composite content = new Composite(tabFolder, SWT.NONE);
			GridLayoutFactory.fillDefaults().margins(5, 5).spacing(0, 0).applyTo(content);

			Control pageControl = page.createContents(content);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(pageControl);

			item.setControl(content);
		}
	}
}
