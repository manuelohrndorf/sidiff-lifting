package org.sidiff.integration.compare.contentview;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.integration.compare.SiLiftCompareConfiguration;

public class SiLiftContentMergeViewerCreator implements IViewerCreator {

	@Override
	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		return new SiLiftContentMergeViewer(parent, SiLiftCompareConfiguration.wrap(config));
	}
}
