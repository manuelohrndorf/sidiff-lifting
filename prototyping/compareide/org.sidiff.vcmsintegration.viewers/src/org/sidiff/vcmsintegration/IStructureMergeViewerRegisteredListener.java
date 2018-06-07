/**
 * 
 */
package org.sidiff.vcmsintegration;

import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer;

/**
 * Interface to that is used to notify user about a changed StructureMergeViewer
 * 
 * @author louis
 *
 */
public interface IStructureMergeViewerRegisteredListener {

	/**
	 * Called when the StructureMergeViewer has been changed.
	 * 
	 * @param structureMergeViewer The current instance of the
	 *            {@link SiLiftStructureMergeViewer}
	 */
	void onStructureMergeViewerRegisteredListener(SiLiftStructureMergeViewer structureMergeViewer);

}