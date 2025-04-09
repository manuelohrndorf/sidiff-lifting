/*
 * Copyright (c) 2006 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Michael Golubev (Borland) - initial API and implementation (for Eclipse MDT/UML2Tools)
 */
package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.draw2d;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

public class CenterLayout extends StackLayout {

	private static final Rectangle RECTANGLE = new Rectangle();

	private boolean myLayoutAgainstGrandParent;

	public void layout(IFigure parent) {
		Rectangle r = findFigureToCenter(parent).getClientArea();
		final int centerX = r.x + r.width / 2;
		final int centerY = r.y + r.height / 2;
		List<?> children = parent.getChildren();
		IFigure child;
		for (int i = 0; i < children.size(); i++) {
			child = (IFigure) children.get(i);
			Dimension prefSize = child.getPreferredSize(r.width, r.height);
			RECTANGLE.x = centerX - prefSize.width / 2;
			RECTANGLE.y = centerY - prefSize.height / 2;
			RECTANGLE.width = prefSize.width;
			RECTANGLE.height = prefSize.height;
			child.setBounds(RECTANGLE);
		}
	}

	protected IFigure findFigureToCenter(IFigure parent) {
		return myLayoutAgainstGrandParent && parent.getParent() != null ? parent.getParent() : parent;
	}

	public void setLayoutAgainstGrandParent(boolean layoutAgainstGrandParent) {
		myLayoutAgainstGrandParent = true;
	}
}
