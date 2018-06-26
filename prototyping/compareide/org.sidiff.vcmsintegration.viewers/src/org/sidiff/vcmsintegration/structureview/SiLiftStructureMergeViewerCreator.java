package org.sidiff.vcmsintegration.structureview;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;
import org.sidiff.vcmsintegration.SiLiftCompareDifferencer;

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
		SiLiftCompareConfiguration siConfig = new SiLiftCompareConfiguration(config);
		SiLiftCompareDifferencer.getInstance().setConfig(siConfig);
		SiLiftStructureMergeViewer viewer = new SiLiftStructureMergeViewer(parent, siConfig);
		return viewer;
	}
}