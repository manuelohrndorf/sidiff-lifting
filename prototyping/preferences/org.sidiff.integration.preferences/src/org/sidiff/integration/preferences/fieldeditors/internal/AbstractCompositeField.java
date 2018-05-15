package org.sidiff.integration.preferences.fieldeditors.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.sidiff.integration.preferences.fieldeditors.ICompositePreferenceField;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;

/**
 * Abstract superclass for preference fields containing other preference fields.
 * {@link #doCreateControls(Composite, String)} must be implemented to create the composite parent
 * and add the child preference fields created by {@link #createNestedPreferenceControl(Composite)} to it.
 * Calls to all other methods are delegated to the contained fields.
 * @author Robert Müller
 *
 */
public abstract class AbstractCompositeField extends PreferenceField implements ICompositePreferenceField {

	private List<IPreferenceField> fields;

	/**
	 * Creates a new composite field with the given title and no preference prefix.
	 * @param title the title
	 */
	public AbstractCompositeField(String title) {
		this(null, title);
	}

	/**
	 * Creates a new composite field with the given preference prefix and title.
	 * @param preferencePrefix the prefix for contained preferences
	 * @param title the title
	 */
	public AbstractCompositeField(String preferencePrefix, String title) {
		super(preferencePrefix, title);
		this.fields = new ArrayList<IPreferenceField>();
	}

	@Override
	public void load(IPreferenceStore store) {
		for(IPreferenceField field : fields) {
			field.load(store);
		}
	}

	@Override
	public void loadDefault(IPreferenceStore store) {
		for(IPreferenceField field : fields) {
			field.loadDefault(store);
		}
	}

	@Override
	public void save(IPreferenceStore store) {
		for(IPreferenceField field : fields) {
			field.save(store);
		}
	}

	protected Control createNestedPreferenceControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, true));

		for(IPreferenceField field : fields) {
			field.createControls(container);
		}

		if(fields.isEmpty()) {
			Label label = new Label(container, SWT.NONE);
			label.setText("None available");
		}
		
		return container;
	}

	@Override
	public void setEnabled(boolean enabled) {
		for(IPreferenceField field : fields) {
			field.setEnabled(enabled);
		}
	}

	@Override
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		for(IPreferenceField field : fields) {
			field.addPropertyChangeListener(listener);
		}
	}

	@Override
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		for(IPreferenceField field : fields) {
			field.removePropertyChangeListener(listener);
		}
	}

	public void addField(IPreferenceField field) {
		String prefix = getPreferenceName();
		if(prefix != null) {
			field.setPrefix(prefix);
		}
		fields.add(field);
	}
}
