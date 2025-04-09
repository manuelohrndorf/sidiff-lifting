/*****************************************************************************
 * Copyright (c) 2014-15 CEA LIST, Montages AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Golubev (Montages) - Initial API and implementation
 *   
 *****************************************************************************/
package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.SnapToGrid;

/**
 * Utility class to compute active grid specification for given edit part
 * viewer.
 * <p/>
 * Clients may call static methods to compute grid spec once, or may setup
 * listeners that will automatically update the active spec when it changed
 * 
 * @since 3.3
 */
public class DiagramGridSpec {

	private final EditPartViewer myViewer;

	private PrecisionRectangle myRelativeGridSpec;

	private PrecisionRectangle myAbsoluteGridSpec;

	private final GridSpecListener myGridListener;

	public DiagramGridSpec(EditPartViewer viewer) {
		myViewer = viewer;
		myGridListener = new GridSpecListener() {

			@Override
			public void gridSpecChanged() {
				myRelativeGridSpec = null;
				myAbsoluteGridSpec = null;
			}
		};
		myViewer.addPropertyChangeListener(myGridListener);
	}

	public void dispose() {
		myViewer.removePropertyChangeListener(myGridListener);
		myRelativeGridSpec = null;
		myAbsoluteGridSpec = null;
	}

	/**
	 * Always returns the same instance to avoid endless creation
	 * 
	 * @return active grid specification in absolute coordinates or
	 *         <code>null</code> if not enabled
	 */
	public PrecisionRectangle getAbsoluteGridSpec() {
		PrecisionRectangle result = getRelativeGridSpec();
		if (result == null) {
			return null;
		}

		if (myAbsoluteGridSpec == null) {
			myAbsoluteGridSpec = new PrecisionRectangle();
		}
		myAbsoluteGridSpec.setPreciseBounds(result.preciseX(),
				result.preciseY(), result.preciseWidth(),
				result.preciseHeight());
		GraphicalEditPart diagramEP = (GraphicalEditPart) myViewer
				.getContents();
		diagramEP.getContentPane().translateToAbsolute(myAbsoluteGridSpec);

		return myAbsoluteGridSpec;
	}

	private PrecisionRectangle getRelativeGridSpec() {
		if (myRelativeGridSpec == null) {
			myRelativeGridSpec = getRelativeGridSpec(myViewer);
		}
		return myRelativeGridSpec;
	}

	public EditPartViewer getViewer() {
		return myViewer;
	}

	/**
	 * Computes actual grid specification (origin + single cell width and
	 * height) in the absolute coordinate system. Note, in contrast to
	 * {@link #getRelativeGridSpec(EditPartViewer)} this specification depends
	 * on the active zoom or scroll and can't be cached by clients.
	 * 
	 * @param viewer
	 * @return absolute grid specification, or <code>null</code> if grid is not
	 *         enabled
	 */
	public static PrecisionRectangle getAbsoluteGridSpec(EditPartViewer viewer) {
		PrecisionRectangle spec = getRelativeGridSpec(viewer);
		if (spec != null) {
			GraphicalEditPart diagramEP = (GraphicalEditPart) viewer
					.getContents();
			diagramEP.getContentPane().translateToAbsolute(spec);
		}
		return spec;
	}

	/**
	 * Computes actual grid specification (origin + single cell width and
	 * height) in the coordinates relative to the diagram content pane.
	 * <p/>
	 * This specification depends only on the grid-relative properties stored in
	 * the {@link EditPartViewer}, so client may cache it and rely on
	 * {@link EditPartViewer#addPropertyChangeListener(PropertyChangeListener)}
	 * 
	 * @param viewer
	 * @return grid specification in the coordinate system relative to diagram
	 *         content pane, or <code>null</code> if grid is not enabled
	 */
	private static PrecisionRectangle getRelativeGridSpec(EditPartViewer viewer) {
		Boolean enabled = (Boolean) viewer
				.getProperty(SnapToGrid.PROPERTY_GRID_ENABLED);
		if (enabled == null || !enabled) {
			return null;
		}
		double gridX = 0;
		double gridY = 0;
		Dimension spacing = (Dimension) viewer
				.getProperty(SnapToGrid.PROPERTY_GRID_SPACING);
		if (spacing != null) {
			gridX = spacing.preciseWidth();
			gridY = spacing.preciseHeight();
		}
		if (gridX <= 0) {
			gridX = SnapToGrid.DEFAULT_GRID_SIZE;
		}
		if (gridY <= 0) {
			gridY = SnapToGrid.DEFAULT_GRID_SIZE;
		}
		Point origin = (Point) viewer
				.getProperty(SnapToGrid.PROPERTY_GRID_ORIGIN);
		PrecisionRectangle result = new PrecisionRectangle(//
				origin == null ? 0 : origin.preciseX(), origin == null ? 0
						: origin.preciseY(), gridX, gridY);

		return result;
	}

	public static abstract class GridSpecListener implements
			PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			String propertyName = evt.getPropertyName();
			if (SnapToGrid.PROPERTY_GRID_ORIGIN.equals(propertyName) || //
					SnapToGrid.PROPERTY_GRID_ENABLED.equals(propertyName) || //
					SnapToGrid.PROPERTY_GRID_SPACING.equals(propertyName)) {

				gridSpecChanged();
			}
		}

		public abstract void gridSpecChanged();
	};

}
