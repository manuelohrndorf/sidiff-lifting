package org.sidiff.patching.report;

/**
 * Callback interface for patch report listeners that need to be updated when
 * the patch report changed. <br/>
 * Note that this is currently a very simple interface that can be extended
 * whenever more information about the changes in the patch report shall be
 * reported to listeners.
 * 
 * @author kehrer
 */
public interface IPatchReportListener {

	public void reportChanged();
}
