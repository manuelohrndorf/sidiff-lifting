package org.sidiff.difference.symmetric.compareview.ide.contentviewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;


public class TestContentViewerCreator implements IViewerCreator {
	
	@Override
	public Viewer createViewer(final Composite parent, CompareConfiguration config) {
		return new TestContentViewer(parent, 0, null, config);
	}
	
}
