package org.sidiff.integration.preferences.ui.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;

/**
 * A preference page that contains {@link IPreferenceField preference fields}.
 * @author Felix Breitweiser, Robert Müller
 *
 */
public class PreferenceFieldPage extends PropertyAndPreferencePage {

	private final IPropertyChangeListener propertyChangeListener = (event) -> validatePreferences();

	private String helpContextId;
	private List<IPreferenceField> preferenceFields = new ArrayList<IPreferenceField>();

	private Composite container;

	public PreferenceFieldPage() {
		super();
	}

	public PreferenceFieldPage(String title) {
		super(title);
	}

	public PreferenceFieldPage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control doCreateContents(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, true));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		updatePreferenceFields();

		return container;
	}

	@Override
	protected void savePreferences() {
		for(IPreferenceField field : preferenceFields) {
			field.save(getPreferenceStore());
		}
	}

	@Override
	protected void reloadPreferences() {
		for(IPreferenceField field : preferenceFields) {
			field.load(getPreferenceStore());
		}
	}

	@Override
	protected void defaultPreferences() {
		for(IPreferenceField field : preferenceFields) {
			field.loadDefault(getPreferenceStore());
		}
	}

	@Override
	protected void validatePreferences() {
		for(IPreferenceField field : preferenceFields) {
			if(!field.isValid()) {
				setValid(false);
				return;
			}
		}
		setValid(true);
	}

	@Override
	public String getHelpContextId() {
		return helpContextId;
	}

	public void setHelpContextId(String helpContextId) {
		this.helpContextId = helpContextId;
	}

	public void addField(IPreferenceField field) {
		addFields(Collections.singleton(field));
	}

	public void addFields(Collection<IPreferenceField> fields) {
		preferenceFields.addAll(fields);
		for(IPreferenceField field : fields) {
			field.addPropertyChangeListener(propertyChangeListener);
		}
	}

	public void clearFields() {
		for(IPreferenceField field : preferenceFields) {
			if(field.getControl() != null) {
				field.getControl().dispose();
			}
			field.removePropertyChangeListener(propertyChangeListener);
		}
		preferenceFields.clear();
		validatePreferences();
	}

	public void updatePreferenceFields() {
		if(container != null && !container.isDisposed()) {
			for(IPreferenceField field : preferenceFields) {
				if(field.getControl() != null) {
					field.getControl().dispose();
				}
				field.createControls(container);
			}
			validatePreferences();
		}
	}
}
