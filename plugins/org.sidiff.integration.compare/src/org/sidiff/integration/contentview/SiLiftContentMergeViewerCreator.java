package org.sidiff.integration.contentview;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.integration.SiLiftCompareConfiguration;


public class SiLiftContentMergeViewerCreator implements IViewerCreator {

	@Override
	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		SiLiftContentMergeViewer viewer = new SiLiftContentMergeViewer(parent, SiLiftCompareConfiguration.wrap(config));
		return viewer;
	}
}
