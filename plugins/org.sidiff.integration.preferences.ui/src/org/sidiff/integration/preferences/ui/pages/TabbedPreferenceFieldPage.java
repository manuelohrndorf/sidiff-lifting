package org.sidiff.integration.preferences.ui.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;

/**
 * Abstract superclass for preference/property pages that contain tabs containing preference fields.
 * @author Felix Breitweiser, Robert Müller
 *
 */
public abstract class TabbedPreferenceFieldPage extends PropertyAndPreferencePage {

	private TabFolder tabFolder;
	private List<Tab> tabs;
	private IPropertyChangeListener propertyChangeListener;

	public TabbedPreferenceFieldPage() {
		super();
		tabs = new ArrayList<Tab>();
	}

	public TabbedPreferenceFieldPage(String title) {
		super(title);
		tabs = new ArrayList<Tab>();
	}

	public TabbedPreferenceFieldPage(String title, ImageDescriptor image) {
		super(title, image);
		tabs = new ArrayList<Tab>();
	}

	/**
	 * Creates all preference tabs and their corresponding fields using {@link #addTab(String, String, List)}.
	 */
	protected abstract void createPreferenceFields();

	@Override
	protected Control doCreateContents(Composite parent) {
		tabFolder = new TabFolder(parent, SWT.TOP);

		createPreferenceFields();
		createPreferenceFieldControls();
		validatePreferences();

		return tabFolder;
	}

	@Override
	protected void savePreferences() {
		for(Tab tab : tabs) {
			for(IPreferenceField field : tab.fields) {
				field.save(getPreferenceStore());
			}
		}
	}

	@Override
	protected void reloadPreferences() {
		for(Tab tab : tabs) {
			for(IPreferenceField field : tab.fields) {
				field.load(getPreferenceStore());
			}
		}
	}

	@Override
	protected void defaultPreferences() {
		for(Tab tab : tabs) {
			for(IPreferenceField field : tab.fields) {
				field.loadDefault(getPreferenceStore());
			}
		}
	}

	@Override
	protected void validatePreferences() {
		for(Tab tab : tabs) {
			for(IPreferenceField field : tab.fields) {
				if(!field.isValid()) {
					setValid(false);
					return;
				}
			}
		}
		setValid(true);
	}

	private void createPreferenceFieldControls() {
		for(Tab tab : tabs) {
			TabItem tabItem = createTab(tab);
			for(IPreferenceField field : tab.fields) {
				field.createControls((Composite)tabItem.getControl());
				field.addPropertyChangeListener(getPropertyChangeListener());
			}
		}
	}

	private IPropertyChangeListener getPropertyChangeListener() {
		if(propertyChangeListener == null) {
			propertyChangeListener = new IPropertyChangeListener() {			
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					validatePreferences();			
				}
			};
		}
		return propertyChangeListener;
	}

	/**
	 * Adds a tab to this page with the given title, tooltip and preference fields.
	 * @param title the title of the tab
	 * @param tooltip the tooltip of the tab (or <code>null</code>)
	 * @param fields the preferences fields of the tab
	 */
	protected void addTab(String title, String tooltip, List<IPreferenceField> fields) {
		tabs.add(new Tab(title, tooltip, fields));
	}

	private TabItem createTab(Tab tab) {
		TabItem item = new TabItem(tabFolder, SWT.NONE);
		item.setText(tab.title);
		item.setToolTipText(tab.tooltip);

		Composite content = new Composite(tabFolder, SWT.NONE);
		content.setLayout(new GridLayout(1, true));
		content.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		item.setControl(content);
        return item;
	}

	private class Tab {
		private final String title;
		private final String tooltip;
		private final List<IPreferenceField> fields;

		private Tab(String title, String tooltip, List<IPreferenceField> fields) {
			this.title = title;
			this.tooltip = tooltip;
			this.fields = fields;
		}
	}
}
