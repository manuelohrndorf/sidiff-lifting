package org.sidiff.difference.symmetric.compareview.ide.structureviewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;


public class TestStructureViewerCreator implements IViewerCreator {
	
	@Override
	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		return new TestStructureViewer(parent, config);
	}
}
