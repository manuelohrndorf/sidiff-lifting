/**
 * 
 */
package org.sidiff.vcmsintegration;

import org.sidiff.vcmsintegration.contentview.SiLiftContentMergeViewer;

/**
 * Interface to that is used to notify user about a changed ContentMergeViewer
 * @author louis
 *
 */
public interface IContentMergeViewerRegisteredListener {
	
	/**
	 * Called when the ContentMergeViewer has been changed.
	 * @param contentMergeViewer
	 */
	void onContentMergeViewerRegisteredListener(SiLiftContentMergeViewer contentMergeViewer);
	
}
