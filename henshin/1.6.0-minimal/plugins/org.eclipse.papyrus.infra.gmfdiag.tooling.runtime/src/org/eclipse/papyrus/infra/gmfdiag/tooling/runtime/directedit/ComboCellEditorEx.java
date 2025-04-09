/******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Svyatoslav Kovalsky (Montages) - initial API and implementation 
 ****************************************************************************/

package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.directedit;

import java.util.Arrays;

import org.eclipse.gmf.runtime.gef.ui.internal.parts.CellEditorExDelegate;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/* public */class ComboCellEditorEx extends ComboBoxCellEditor implements org.eclipse.gmf.runtime.gef.ui.internal.parts.CellEditorEx {

	private static final int ourStyle = SWT.READ_ONLY;

	private org.eclipse.gmf.runtime.gef.ui.internal.parts.CellEditorExDelegate myExDelegate;

	public ComboCellEditorEx() {
		setStyle(ourStyle);
	}

	/**
	 * @param parent
	 *            the parent control
	 * @param items
	 *            the list of strings for the combo box
	 */
	public ComboCellEditorEx(Composite parent, String[] items) {
		super(parent, items, ourStyle);
	}

	private CellEditorExDelegate getExDelegate() {
		if (myExDelegate == null) {
			myExDelegate = new CellEditorExDelegate(this);
		}
		return myExDelegate;
	}

	/**
	 * This will be used when an edit has occurred by a ModifyEvent has been
	 * been send. Will call #setValue(Object) but will also call
	 * editOccured(null) to make sure that the dirty flag is set probably and
	 * that any listeners are informed about the changed.
	 * 
	 * @param value
	 *            Value to set the cell editor to.
	 * 
	 *            Note: This happens address defect RATLC00522324. For our
	 *            topgraphical edit parts we delagate the direct edit request to
	 *            a primary edit part and set focus on that. The issue is that
	 *            if the user has typed in an initial character when setting
	 *            focus to the edit part, which typically is a
	 *            TextCompartmentEditPart then setting that intial value does
	 *            not fire the necessary change events that need to occur in
	 *            order for that value to be recongnized. If you don't use this
	 *            method then the result is that if you just type in the initial
	 *            character and that is it then the text compartment loses focus
	 *            then the value will not be saved. This is because setting the
	 *            value of the cell doesn't think its value has changed since
	 *            the first character is not recongized as a change.
	 */
	public void setValueAndProcessEditOccured(Object value) {
		setValue(value);
		//  assume all values are valid for now
		valueChanged(true, true);
	}

	@Override
	protected void doSetValue(Object value) {
		getExDelegate().setOriginalValue(value);
		super.doSetValue(Arrays.asList(getItems()).indexOf(value));
	}

	@Override
	protected Object doGetValue() {
		Integer itemIndex = (Integer) super.doGetValue();
		return itemIndex == -1 ? null : getItems()[itemIndex];
	}

	/**
	 * @return boolean value specifying whether or not the value has been
	 *         changed
	 */
	public boolean hasValueChanged() {
		return getExDelegate().hasValueChanged();
	}

	/*
	 * Runs super deactivate unless it has been locked and otherwise unlocks
	 * deactivation
	 * 
	 * @see org.eclipse.jface.viewers.CellEditor#deactivate()
	 */
	public void deactivate() {
		if (!getExDelegate().unlockDeactivation()) {
			super.deactivate();
		}
	}

	/**
	 * Returns true if deactivation has been locked
	 * 
	 * @return
	 */
	public boolean isDeactivationLocked() {
		return getExDelegate().isDeactivationLocked();
	}

	/**
	 * Sets deactivation lock so that the cell editor does not perform
	 * deactivate
	 * 
	 * @param deactivationLock
	 */
	public void setDeactivationLock(boolean deactivationLock) {
		getExDelegate().setDeactivationLock(deactivationLock);
	}
}
