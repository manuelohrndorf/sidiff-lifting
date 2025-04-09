/******************************************************************************
 * Copyright (c) 2002, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.directedit;

import org.eclipse.swt.widgets.Composite;

/**
 * @since 3.2
 * @deprecated This interface is left for backward compatibility only and will be removed soon.
 * @see org.eclipse.gmf.runtime.gef.ui.internal.parts.WrapTextCellEditor
 */
public class WrapTextCellEditor extends org.eclipse.gmf.runtime.gef.ui.internal.parts.WrapTextCellEditor {

	/**
	 * Creates a new text string cell editor with no control
	 * The cell editor value is the string itself, which is initially the empty
	 * string. Initially, the cell editor has no cell validator.
	 */
	public WrapTextCellEditor() {
		super();
	}

	/**
	 * Creates a new text string cell editor parented under the given control.
	 * The cell editor value is the string itself, which is initially the empty string. 
	 * Initially, the cell editor has no cell validator.
	 *
	 * @param parent the parent control
	 */
	public WrapTextCellEditor(Composite parent) {
		super(parent);
	}

	/**
	 * Creates a new text string cell editor parented under the given control.
	 * The cell editor value is the string itself, which is initially the empty string. 
	 * Initially, the cell editor has no cell validator.
	 *
	 * @param parent the parent control
	 * @param style the style bits
	 */
	public WrapTextCellEditor(Composite parent, int style) {
		super(parent, style);
	}

}
