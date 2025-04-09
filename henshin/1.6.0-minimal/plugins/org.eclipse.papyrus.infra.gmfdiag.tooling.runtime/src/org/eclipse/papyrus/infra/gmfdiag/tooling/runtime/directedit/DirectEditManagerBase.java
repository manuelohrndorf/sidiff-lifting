/******************************************************************************
 * Copyright (c) 2002, 2010, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 *    Dmitry Stadnik (Borland) - contribution for bugzilla 135694
 ****************************************************************************/

package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.directedit;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;

/**
 * @since 3.2
 * @deprecated This class is left for backward compatibility only and will be removed soon.
 * @see org.eclipse.gmf.runtime.diagram.ui.tools.DirectEditManagerBase
 */
@Deprecated
public abstract class DirectEditManagerBase extends org.eclipse.gmf.runtime.diagram.ui.tools.DirectEditManagerBase {

	public DirectEditManagerBase(ITextAwareEditPart source) {
		super(source);
	}

	public DirectEditManagerBase(GraphicalEditPart source, Class<?> editorType, CellEditorLocator locator) {
		super(source, editorType, locator);
	}
}
