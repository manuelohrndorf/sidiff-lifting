package org.sidiff.vcmsintegration;

import org.sidiff.vcmsintegration.contentprovider.CompareResource;

/**
 * An interface that is used to notify observers about changes that occur in the
 * content of the structure viewer.
 * 
 * @author Adrian Bingener
 *
 */
public interface ResourceChangeListener {

	/**
	 * Called when the resource was changed.
	 * 
	 * @param compareResource The resource that was changed.
	 */
	void onResourceChanged(CompareResource compareResource);
}
