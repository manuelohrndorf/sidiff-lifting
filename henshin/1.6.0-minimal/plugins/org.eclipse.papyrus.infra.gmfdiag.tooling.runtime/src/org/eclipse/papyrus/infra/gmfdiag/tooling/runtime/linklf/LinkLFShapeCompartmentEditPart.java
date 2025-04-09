/*****************************************************************************
 * Copyright (c) 2014-2015, 2021, 2024 CEA LIST, Montages AG, ARTAL and others
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Anatoly Tishenko (tishenko@montages.com) - Initial API and implementation
 *  Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : Pull up refreshVisuals/setRatio for shape compartments
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 583091
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutAnimator;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RangeModel;
import org.eclipse.draw2d.ScrollBar;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemsAwareFreeFormLayer;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.figures.ShapeCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.layout.FreeFormLayoutEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.AnimatableScrollPane;
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.OverlayScrollPaneLayout;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @since 3.3
 */
public class LinkLFShapeCompartmentEditPart extends ShapeCompartmentEditPart {

	private PropertyChangeListener myGridListener = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			String propertyName = evt.getPropertyName();
			if (SnapToGrid.PROPERTY_GRID_ORIGIN.equals(propertyName) || //
					SnapToGrid.PROPERTY_GRID_ENABLED.equals(propertyName) || //
					SnapToGrid.PROPERTY_GRID_SPACING.equals(propertyName)) {

				updateGridBehavior();
			}
		}
	};
	
	public LinkLFShapeCompartmentEditPart(View view) {
		super(view);
	}

	@Override
	public IFigure createFigure() {
		ShapeCompartmentFigure result = new ShapeCompartmentFigureEx(
				getCompartmentName(), getMapMode());
		result.getContentPane().setLayoutManager(getLayoutManager());
		result.getContentPane().addLayoutListener(LayoutAnimator.getDefault());
		return result;
	}

	@Override
	public void addNotify() {
		super.addNotify();
		getViewer().addPropertyChangeListener(myGridListener);
		updateGridBehavior();
	}

	@Override
	public void removeNotify() {
		getViewer().removePropertyChangeListener(myGridListener);
		unsetScrollGridBehaviour(getCompartmentFigure());
		super.removeNotify();
	}

	protected void updateGridBehavior() {
		Rectangle gridSpec = getGridSpec(getViewer());
		if (gridSpec != null) {
			setScrollGridBehaviour(getCompartmentFigure());
		}
	}

	protected void setScrollGridBehaviour(
			ResizableCompartmentFigure compartmentFigure) {
		ScrollPane sp = compartmentFigure.getScrollPane();
		decorateHorizontalRange(sp);
		decorateVerticalRange(sp);
		updateStepIncrements(sp);
	}

	private void unsetScrollGridBehaviour(
			ResizableCompartmentFigure compartmentFigure) {
		ScrollPane sp = compartmentFigure.getScrollPane();
		undecorateHorizontalRange(sp);
		undecorateVerticalRange(sp);
		updateStepIncrements(sp);
	}

	/**
	 * Undecorate vertical range of the given pane 
	 * 
	 * @param pane
	 */
	private void undecorateVerticalRange(ScrollPane pane) {
		pane.setViewport(null); 
		pane.setVerticalScrollBar(null);
	}

	/**
	 * Undecorate vertical range of the given pane 
	 * 
	 * @param pane
	 */
	private void undecorateHorizontalRange(ScrollPane pane) {
		pane.setViewport(null);
		pane.setHorizontalScrollBar(null);
	}

	private void updateStepIncrements(ScrollPane pane) {
		Rectangle gridSpec = getGridSpec(getViewer());
		if (gridSpec == null) {
			return;
		}
		ScrollBar hScroll = pane.getHorizontalScrollBar();
		hScroll.setStepIncrement(gridSpec.height);
		ScrollBar wScroll = pane.getVerticalScrollBar();
		wScroll.setStepIncrement(gridSpec.width);
	}

	private void decorateVerticalRange(ScrollPane pane) {
		ScrollBar scroll = pane.getVerticalScrollBar();
		RangeModel model = scroll.getRangeModel();
		if (false == model instanceof SnapToGridRangeModel) {
			SnapToGridRangeModel range = new SnapToGridRangeModel(model);
			scroll.setRangeModel(range);
			pane.getViewport().setVerticalRangeModel(range);
		}
	}

	private void decorateHorizontalRange(ScrollPane pane) {
		ScrollBar scroll = pane.getHorizontalScrollBar();
		RangeModel model = scroll.getRangeModel();
		if (false == model instanceof SnapToGridRangeModel) {
			SnapToGridRangeModel range = new SnapToGridRangeModel(model);
			scroll.setRangeModel(range);
			pane.getViewport().setHorizontalRangeModel(range);
		}
	}

	protected static Rectangle getGridSpec(EditPartViewer viewer) {
		Boolean enabled = (Boolean) viewer
				.getProperty(SnapToGrid.PROPERTY_GRID_ENABLED);
		if (enabled == null || !enabled) {
			return null;
		}
		int gridX = 0;
		int gridY = 0;
		Point origin;
		Dimension spacing = (Dimension) viewer
				.getProperty(SnapToGrid.PROPERTY_GRID_SPACING);
		if (spacing != null) {
			gridX = spacing.width;
			gridY = spacing.height;
		}
		if (gridX == 0) {
			gridX = SnapToGrid.DEFAULT_GRID_SIZE;
		}
		if (gridY == 0) {
			gridY = SnapToGrid.DEFAULT_GRID_SIZE;
		}
		Point loc = (Point) viewer.getProperty(SnapToGrid.PROPERTY_GRID_ORIGIN);
		if (loc != null) {
			origin = loc;
		} else {
			origin = new Point();
		}

		return new Rectangle(origin.x, origin.y, gridX, gridY);

	}

	private class SnapToGridRangeModel implements RangeModel {

		private RangeModel myBaseRangeModel;

		public SnapToGridRangeModel(RangeModel rangeModel) {
			myBaseRangeModel = rangeModel;
		}

		@Override
		public void setValue(int value) {
			Rectangle gridSpec = getGridSpec(getViewer());
			if (gridSpec != null) {
				value = Math.max(value, 0);
				value = gridSpec.height * (value / gridSpec.height);
			}
			if (value + getMinimum() <= getMaximum()) {
				myBaseRangeModel.setValue(value);
			}
		}

		@Override
		public void setMinimum(int min) {
			myBaseRangeModel.setMinimum(min);
		}

		@Override
		public void setMaximum(int max) {
			myBaseRangeModel.setMaximum(max);
		}

		@Override
		public void setExtent(int extent) {
			myBaseRangeModel.setExtent(extent);
		}

		@Override
		public void setAll(int min, int extent, int max) {
			myBaseRangeModel.setAll(min, extent, max);
		}

		@Override
		public void removePropertyChangeListener(PropertyChangeListener listener) {
			myBaseRangeModel.removePropertyChangeListener(listener);
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public int getValue() {
			return myBaseRangeModel.getValue();
		}

		@Override
		public int getMinimum() {
			return myBaseRangeModel.getMinimum();
		}

		@Override
		public int getMaximum() {
			return myBaseRangeModel.getMaximum();
		}

		@Override
		public int getExtent() {
			return myBaseRangeModel.getExtent();
		}

		@Override
		public void addPropertyChangeListener(PropertyChangeListener listener) {
			myBaseRangeModel.addPropertyChangeListener(listener);
		}
	}

	public static class ShapeCompartmentFigureEx extends ShapeCompartmentFigure {

		public ShapeCompartmentFigureEx(String title, IMapMode mm) {
			super(title, mm);
		}

		@Override
		protected void configureFigure(IMapMode mm) {
			ScrollPane scrollpane = getScrollPane();
			if (scrollpane == null) {
				scrollpane = scrollPane = new AnimatableScrollPaneWithMM(mm);
			}
			scrollpane.setViewport(new FreeformViewport());
			scrollPane.setScrollBarVisibility(ScrollPane.AUTOMATIC);
			scrollpane.setLayoutManager(new OverlayScrollPaneLayout());

			IFigure contents = new BorderItemsAwareFreeFormLayer();
			contents.setLayoutManager(new FreeFormLayoutEx());
			scrollpane.setContents(contents);

			int MB = mm.DPtoLP(5);
			scrollpane.setBorder(new MarginBorder(MB, MB, MB, MB));
			int SZ = mm.DPtoLP(10);
			scrollpane.setMinimumSize(new Dimension(SZ, SZ));

			this.setFont(FONT_TITLE);
		}

	}

	private static class AnimatableScrollPaneWithMM extends
			AnimatableScrollPane implements IMapMode {

		private IMapMode myMapMode;

		public AnimatableScrollPaneWithMM(IMapMode mm) {
			myMapMode = mm;
		}

		@Override
		public int LPtoDP(int logicalUnit) {
			return myMapMode.LPtoDP(logicalUnit);
		}

		@Override
		public int DPtoLP(int deviceUnit) {
			return myMapMode.DPtoLP(deviceUnit);
		}

		@Override
		public Translatable LPtoDP(Translatable t) {
			return myMapMode.LPtoDP(t);
		}

		@Override
		public Translatable DPtoLP(Translatable t) {
			return myMapMode.DPtoLP(t);
		}
	}


	@Override
	protected void setRatio(Double ratio) {
		if (getFigure().getParent().getLayoutManager() instanceof ConstrainedToolbarLayout) {
			super.setRatio(ratio);
		}
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		return super.getTargetEditPart(request);
	}

	@Override
	protected void handleNotificationEvent(Notification notification) {
		Object feature = notification.getFeature();
		if (NotationPackage.eINSTANCE.getSize_Width().equals(feature)
				|| NotationPackage.eINSTANCE.getSize_Height().equals(feature)
				|| NotationPackage.eINSTANCE.getLocation_X().equals(feature)
				|| NotationPackage.eINSTANCE.getLocation_Y().equals(feature)) {
			refreshBounds();
		}
		super.handleNotificationEvent(notification);
	}

	/**
	 * Refresh bounds.
	 *
	 * @since 4.1
	 */
	protected void refreshBounds() {
		int width = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Width())).intValue();
		int height = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getSize_Height())).intValue();
		Dimension size = new Dimension(width, height);
		int x = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_X())).intValue();
		int y = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE.getLocation_Y())).intValue();
		Point loc = new Point(x, y);
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, getFigure(), new Rectangle(loc, size));
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshBounds();
	}

}
