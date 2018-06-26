package org.sidiff.vcmsintegration.contentview;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;


public class SiLiftContentMergeViewerCreator implements IViewerCreator {

	@Override
	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		SiLiftContentMergeViewer viewer = new SiLiftContentMergeViewer(parent, new SiLiftCompareConfiguration(config));
		return viewer;
	}
}
