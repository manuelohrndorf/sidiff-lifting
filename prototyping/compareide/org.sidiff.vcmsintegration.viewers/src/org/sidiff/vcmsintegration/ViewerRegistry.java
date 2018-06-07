/**
 * 
 */
package org.sidiff.vcmsintegration;

import java.util.LinkedList;
import java.util.List;

import org.sidiff.vcmsintegration.contentview.SiLiftContentMergeViewer;
import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer;

/**
 * StructureMergeViewer and ContentMergeViewer can be registered here.
 * This is an easy and fast way to access them from each other.
 * @author louis
 *
 */
public class ViewerRegistry {
	
	static ViewerRegistry instance;
	
	/**
	 * the viewers
	 */
	SiLiftContentMergeViewer contentMergeViewer;
	SiLiftStructureMergeViewer structureMergeViewer;
	
	/** 
	 * registered listeners
	 */
	List<IContentMergeViewerRegisteredListener> contentMergeViewerRegisteredListeners;
	List<IStructureMergeViewerRegisteredListener> structureMergeViewerRegisteredListeners;

	/**
	 * create a new registry
	 */
	private ViewerRegistry() {
		// set viewers to null
		contentMergeViewer = null;
		structureMergeViewer = null;
		// init lists
		contentMergeViewerRegisteredListeners = new LinkedList<IContentMergeViewerRegisteredListener>();
		structureMergeViewerRegisteredListeners = new LinkedList<IStructureMergeViewerRegisteredListener>();
	}
	
	/**
	 * get global instance of ViewerRegistry
	 */
	public static ViewerRegistry getInstance() {
		if(instance == null) {
			instance = new ViewerRegistry();
		}
		return instance;
	}
	
	/**
	 * @return The contentMergeViewer. May be null if not set.
	 */
	public SiLiftContentMergeViewer getContentMergeViewer() {
		return contentMergeViewer;
	}

	/**
	 * @param contentMergeViewer the contentMergeViewer to set
	 */
	public void setContentMergeViewer(SiLiftContentMergeViewer contentMergeViewer) {
		this.contentMergeViewer = contentMergeViewer;
		fireContentMergeViewerRegisteredListener();
	}

	/**
	 * @return the structureMergeViewer. May be null if not set.
	 */
	public SiLiftStructureMergeViewer getStructureMergeViewer() {
		return structureMergeViewer;
	}

	/**
	 * @param structureMergeViewer the structureMergeViewer to set
	 */
	public void setStructureMergeViewer(SiLiftStructureMergeViewer structureMergeViewer) {
		this.structureMergeViewer = structureMergeViewer;
		fireStructureMergeViewerRegisteredListener();
	}
	
	
	// handle events for ContentMergeViewer 
	
	/**
	 * Add a new listener, which gets fired after a new ContentMergeViewer has been set.
	 */
	public void addContentMergeViewerRegisteredListener(IContentMergeViewerRegisteredListener contentMergeViewerRegisteredListener) {
		if(contentMergeViewerRegisteredListener != null) {
			contentMergeViewerRegisteredListeners.add(contentMergeViewerRegisteredListener);
		}
	}
	
	/**
	 * Remove listener for ContentMergeViewerRegistered event.
	 */
	public void removeContentMergeViewerRegisteredListener(IContentMergeViewerRegisteredListener contentMergeViewerRegisteredListener) {
		if(contentMergeViewerRegisteredListener != null && contentMergeViewerRegisteredListeners.contains(contentMergeViewerRegisteredListener)) {
			contentMergeViewerRegisteredListeners.remove(contentMergeViewerRegisteredListener);
		}
	}
	
	/**
	 * Notify all listeners of ContentMergeViewerRegistered event
	 */
	private void fireContentMergeViewerRegisteredListener() {
		for(IContentMergeViewerRegisteredListener contentMergeViewerRegisteredListener : contentMergeViewerRegisteredListeners) {
			if(contentMergeViewerRegisteredListener != null) {
				contentMergeViewerRegisteredListener.onContentMergeViewerRegisteredListener(this.contentMergeViewer);
			}
		}
	}
	
	//handle events for StructureMergeViewer
	
	/**
	 * Add a new listener, which gets fired after a new StructureMergeViewer has been set.
	 */
	public void addStructureMergeViewerRegisteredListener(IStructureMergeViewerRegisteredListener structureMergeViewerRegisteredListener) {
		if(structureMergeViewerRegisteredListener != null) {
			structureMergeViewerRegisteredListeners.add(structureMergeViewerRegisteredListener);
		}
	}
	
	/**
	 * Remove listener for StructureMergeViewerRegistered event.
	 */
	public void removeStructureMergeViewerRegisteredListener(IStructureMergeViewerRegisteredListener structureMergeViewerRegisteredListener) {
		if(structureMergeViewerRegisteredListener != null && structureMergeViewerRegisteredListeners.contains(structureMergeViewerRegisteredListener)) {
			structureMergeViewerRegisteredListeners.remove(structureMergeViewerRegisteredListener);
		}
	}
	
	/**
	 * Notify all listeners of StructureMergeViewerRegistered event
	 */
	private void fireStructureMergeViewerRegisteredListener() {
		for(IStructureMergeViewerRegisteredListener structureMergeViewerRegisteredListener : structureMergeViewerRegisteredListeners) {
			if(structureMergeViewerRegisteredListener != null) {
				structureMergeViewerRegisteredListener.onStructureMergeViewerRegisteredListener(this.structureMergeViewer);
			}
		}
	} 
	
}
