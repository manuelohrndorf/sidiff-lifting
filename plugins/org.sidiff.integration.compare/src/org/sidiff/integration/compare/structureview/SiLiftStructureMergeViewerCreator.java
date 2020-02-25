package org.sidiff.integration.compare.structureview;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.integration.compare.SiLiftCompareConfiguration;

/**
 * The class that is being registered in eclipse. It creates a new instance of
 * the {@link SiLiftStructureMergeViewer} each time the eclipse environment
 * request one.
 * 
 * @author Adrian Bingener
 *
 */
public class SiLiftStructureMergeViewerCreator implements IViewerCreator {

	@Override
	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		SiLiftCompareConfiguration siConfig = SiLiftCompareConfiguration.wrap(config);
		SiLiftStructureMergeViewer viewer = new SiLiftStructureMergeViewer(parent, siConfig);
		return viewer;
	}
}