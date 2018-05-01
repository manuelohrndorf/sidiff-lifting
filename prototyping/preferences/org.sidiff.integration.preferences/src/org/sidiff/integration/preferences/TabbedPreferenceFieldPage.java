package org.sidiff.integration.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;

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
	 * Creates all preference tabs and their corresponding fields using {@link #addTab(String, List)}.
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
			for(PreferenceField field : tab.fields) {
				field.save(getPreferenceStore());
			}
		}
	}

	@Override
	protected void reloadPreferences() {
		for(Tab tab : tabs) {
			for(PreferenceField field : tab.fields) {
				field.load(getPreferenceStore());
			}
		}
	}

	@Override
	protected void defaultPreferences() {
		for(Tab tab : tabs) {
			for(PreferenceField field : tab.fields) {
				field.loadDefault(getPreferenceStore());
			}
		}
	}

	@Override
	protected void validatePreferences() {
		for(Tab tab : tabs) {
			for(PreferenceField field : tab.fields) {
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
			for(PreferenceField field : tab.fields) {
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

	protected void addTab(String title, List<PreferenceField> fields) {
		tabs.add(new Tab(title, fields));
	}

	protected TabItem createTab(Tab tab) {
		TabItem item = new TabItem(tabFolder, SWT.NONE);
		item.setText(tab.title);

		Composite content = new Composite(tabFolder, SWT.NONE);
		content.setLayout(new GridLayout(1, true));

		item.setControl(content);
        return item;
	}

	private class Tab {
		private final String title;
		private final List<PreferenceField> fields;

		private Tab(String title, List<PreferenceField> fields) {
			this.title = title;
			this.fields = fields;
		}
	}
}
