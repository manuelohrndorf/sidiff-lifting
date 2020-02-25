package org.sidiff.integration.preferences.fieldeditors.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.sidiff.integration.preferences.fieldeditors.ICompositePreferenceField;
import org.sidiff.integration.preferences.fieldeditors.IPreferenceField;

/**
 * Abstract superclass for preference fields containing other preference fields.
 * {@link #doCreateControls(Composite, String)} must be implemented to create the composite parent
 * and add the child preference fields created by {@link #createNestedPreferenceControl(Composite)} to it.
 * Calls to all other methods are delegated to the contained fields.
 * @author rmueller
 */
public class CompositeField<T extends IPreferenceField>
			extends PreferenceField implements ICompositePreferenceField<T> {

	private final WrapperSupplier wrapperSupplier;
	private List<T> fields;

	private Composite container;
	private Label emptyLabel;

	/**
	 * Creates a new composite field with the given title and no preference prefix.
	 * @param title the title
	 */
	public CompositeField(WrapperSupplier wrapperSupplier, String title) {
		this(wrapperSupplier, null, title);
	}

	/**
	 * Creates a new composite field with the given preference prefix and title.
	 * @param preferenceName name of the preference in the store 
	 * @param title the title
	 */
	public CompositeField(WrapperSupplier wrapperSupplier, String preferenceName, String title) {
		super(preferenceName, title);
		this.fields = new ArrayList<>();
		this.wrapperSupplier = wrapperSupplier;
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

	@Override
	protected Control doCreateControls(Composite parent) {
		return wrapperSupplier.createWrapper(parent, getTitle(), this::createNestedPreferenceControl);
	}

	protected Control createNestedPreferenceControl(Composite parent) {
		if(container != null) {
			container.dispose();
		}
		container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(container);
		container.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		for(IPreferenceField field : fields) {
			field.createControls(container);
		}

		if(fields.isEmpty()) {
			emptyLabel = new Label(container, SWT.NONE);
			emptyLabel.setText("None available");
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

	@Override
	public void addField(T field) {
		fields.add(field);
		if(container != null && !container.isDisposed()) {
			field.createControls(container);
		}
		if(emptyLabel != null && !emptyLabel.isDisposed()) {
			emptyLabel.setVisible(false);
			emptyLabel.moveBelow(null); // move to bottom
		}
	}

	@Override
	public void clearFields() {
		for(T field : fields) {
			Control control = field.getControl();
			if(control != null) {
				control.dispose();
			}
		}
		fields.clear();
		if(emptyLabel != null && !emptyLabel.isDisposed()) {
			emptyLabel.setVisible(true);
		}
	}
	
	@Override
	public boolean isEmpty() {
		return fields.isEmpty();
	}

	@FunctionalInterface
	public interface WrapperSupplier {
		Control createWrapper(Composite parent, String title, Function<Composite,Control> childrenSupplier);

		public static final WrapperSupplier COMPOSITE = (parent, title, childrenSupplier) -> {
			Composite composite = new Composite(parent, SWT.NONE);
			GridLayoutFactory.fillDefaults().applyTo(composite);
			childrenSupplier.apply(composite);
			return composite;
		};
		public static final WrapperSupplier GROUP = (parent, title, childrenSupplier) -> {
			Group group = new Group(parent, SWT.NONE);
			group.setText(title);
			GridLayoutFactory.fillDefaults().applyTo(group);
			childrenSupplier.apply(group);
			return group;
		};
		public static final WrapperSupplier EXPANDABLE = (parent, title, childrenSupplier) -> {
			ExpandableComposite expandableParent = new ExpandableComposite(parent, SWT.BORDER);
			GridLayoutFactory.fillDefaults().applyTo(expandableParent);
			expandableParent.setText(title);
			Control nestedControl = childrenSupplier.apply(expandableParent);
			expandableParent.setClient(nestedControl);
			expandableParent.addExpansionListener(new IExpansionListener() {
				@Override
				public void expansionStateChanging(ExpansionEvent e) {
				}
				@Override
				public void expansionStateChanged(ExpansionEvent e) {
					nestedControl.pack();
					expandableParent.pack();
					nestedControl.requestLayout();
				}
			});
			return expandableParent;
		};
	}
}
