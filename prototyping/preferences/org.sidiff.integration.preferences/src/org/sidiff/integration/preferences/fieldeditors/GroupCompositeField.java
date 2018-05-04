package org.sidiff.integration.preferences.fieldeditors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 * Composite preference field showing the child preferences in a {@link Group}.
 * @author Robert Müller
 *
 */
public class GroupCompositeField extends AbstractCompositeField {

	public GroupCompositeField(String title) {
		super(title);
	}

	public GroupCompositeField(String preferencePrefix, String title) {
		super(preferencePrefix, title);
	}

	@Override
	protected Control doCreateControls(Composite parent, String title) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(title);
		group.setLayout(new GridLayout(1, true));
		createNestedPreferenceControl(group);
		return group;
	}
}
