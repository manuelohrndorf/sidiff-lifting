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
 * 
 * @author Felix Breitweiser, Robert Müller
 *
 */
public abstract class TabbedPreferenceFieldPage extends PreferenceFieldPage implements IPropertyChangeListener {

	private TabFolder tabFolder;
	private List<List<PreferenceField>> tabs;
	private List<String> tabTitles;

	public TabbedPreferenceFieldPage() {
		tabs = new ArrayList<List<PreferenceField>>();
		tabTitles = new ArrayList<String>();
	}

	public TabbedPreferenceFieldPage(String title) {
		super(title);
		tabs = new ArrayList<List<PreferenceField>>();
		tabTitles = new ArrayList<String>();
	}

	public TabbedPreferenceFieldPage(String title, ImageDescriptor image) {
		super(title, image);
		tabs = new ArrayList<List<PreferenceField>>();
		tabTitles = new ArrayList<String>();
	}

	@Override
	protected Control doCreateContents(Composite parent) {
		tabFolder = new TabFolder(parent, SWT.TOP);

		createPreferenceFields();
		
		initialize();
		checkState();
		
		return tabFolder;
	}

	private void initialize() {
		for(int tab = 0; tab < tabs.size(); tab++) {
			List<PreferenceField> fields = tabs.get(tab);
			TabItem tabItem = createTab(tab);
			for(PreferenceField pf : fields) {
				pf.createControls((Composite)tabItem.getControl());
				pf.addPropertyChangeListener(this);
				pf.addPropertyChangeListener(getPropertyChangeListener());
				pf.setPreferenceStore(getPreferenceStore());
				pf.load();
			}
		}
	}

	private IPropertyChangeListener getPropertyChangeListener() {
		return new IPropertyChangeListener() {			
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				checkState();				
			}
		};
	}

	@Override
	protected void performDefaults() {
		for(List<PreferenceField> tab : tabs) {
			for(PreferenceField pf : tab) {
				pf.loadDefault();
			}
		}
		super.performDefaults();
	}
	
	@Override
	public boolean performOk() {
		for(List<PreferenceField> tab : tabs) {
			for(PreferenceField pf : tab) {
				pf.save();
			}
		}
        return true;
    }
	
	@Override
	public void dispose() {
        super.dispose();
        for(List<PreferenceField> tab : tabs) {
			for(PreferenceField pf : tab) {
				pf.removePropertyChangeListener(this);
			}
		}
    }
	
	public int addTab(String title) {
		tabs.add(new ArrayList<PreferenceField>());
		tabTitles.add(title);
        return tabs.size() - 1;
	}
	
	public void addField(PreferenceField editor, int tab) {
		if(tabs.size() > tab) {
			tabs.get(tab).add(editor);
		}
	}
	
	public TabItem createTab(int tab) {
		TabItem item = new TabItem(tabFolder, SWT.NONE);
		item.setText(tabTitles.get(tab));

		Composite content = new Composite(tabFolder, SWT.NONE);
		content.setLayout(new GridLayout(1, true));

		item.setControl(content);
        return item;
	}

	protected abstract void createPreferenceFields();

	@Override
	protected Iterable<PreferenceField> getAllFields() {
		List<PreferenceField> all = new ArrayList<PreferenceField>();
		for(List<PreferenceField> tab : tabs) {
			all.addAll(tab);
		}
		return all;
	}

	/**
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 * Default implementation does nothing
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
	}
}
