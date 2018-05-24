package org.sidiff.integration.preferences.fieldeditors.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;

/**
 * Composite preference field that can be expanded/collapsed.
 * @author Robert Müller
 *
 */
public class ExpandableCompositeField extends AbstractCompositeField {

	public ExpandableCompositeField(String title) {
		super(title);
	}

	public ExpandableCompositeField(String preferencePrefix, String title) {
		super(preferencePrefix, title);
	}

	@Override
	protected Control doCreateControls(Composite parent, String title) {
		ExpandableComposite expandableParent = new ExpandableComposite(parent, SWT.BORDER);
		expandableParent.setLayout(new GridLayout(1, true));
		expandableParent.setText(title);
		Control nestedControl = createNestedPreferenceControl(expandableParent);
		expandableParent.setClient(nestedControl);
		return expandableParent;
	}
}
