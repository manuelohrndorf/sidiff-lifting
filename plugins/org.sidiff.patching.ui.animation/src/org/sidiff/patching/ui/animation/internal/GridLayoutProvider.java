package org.sidiff.patching.ui.animation.internal;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.ILayoutNodeProvider;

public class GridLayoutProvider extends AbstractProvider implements ILayoutNodeProvider {

	@Override
	public boolean provides(IOperation operation) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Runnable layoutLayoutNodes(List layoutNodes, boolean offsetFromBoundingBox, IAdaptable layoutHint) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canLayoutNodes(List layoutNodes, boolean shouldOffsetFromBoundingBox, IAdaptable layoutHint) {
		// TODO Auto-generated method stub
		return false;
	}

}
