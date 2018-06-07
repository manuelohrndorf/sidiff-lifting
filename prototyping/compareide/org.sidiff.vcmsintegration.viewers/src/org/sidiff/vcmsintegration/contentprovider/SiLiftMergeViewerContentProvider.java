package org.sidiff.vcmsintegration.contentprovider;

import java.io.IOException;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.sidiff.vcmsintegration.util.Util;

/**
 * 
 * @author Felix Breitweiser
 *
 */
public class SiLiftMergeViewerContentProvider implements IMergeViewerContentProvider {
	
	/**
	 * The compare configuration that is provided by the eclipse environment
	 */
	private CompareConfiguration config;
	
	
	/**
	 * Creates a new instance of the
	 * {@link SiLiftMergeViewerContentProvider}.
	 * 
	 * @param config The compare configuration that is provided by the eclipse evironment
	 */
	public SiLiftMergeViewerContentProvider(CompareConfiguration config) {
		this.config = config;
	}
	
	/**
	 * @see {@link org.eclipse.jface.viewers.IContentProvider}
	 */
	@Override
	public void dispose() {}

	/**
	 * Unused.
	 * This usually notifies this content provider that the associated viewer's input
	 * changed. We don't want to switch the input via this event.
	 * @see {@link org.eclipse.jface.viewers.IContentProvider}
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		//we are not interested, since we have no state
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public String getAncestorLabel(Object input) {
		return config.getAncestorLabel(input);
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public Image getAncestorImage(Object input) {
		return config.getAncestorImage(input);
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public ITypedElement getAncestorContent(Object input) {	
		if (input instanceof ICompareInput)
			return ((ICompareInput) input).getAncestor();
		return null;
	}
	
	/**
	 * Trys to return the ancestor as an EObject. If this fails the result is null.
	 * @param input The input object of the ContentMergeViewer
	 * @return The ancestor object as an EObject, null if this fails
	 */
	public EObject getAncestorEObject(Object input) {
		Object ancestor = getAncestorContent(input);
		if(ancestor instanceof IStreamContentAccessor) {
			try {
				return Util.loadEObjectFromStream((IStreamContentAccessor) ancestor);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Trys to return the ancestor as a Resource. If this fails the result is null.
	 * @param input The input object of the ContentMergeViewer
	 * @return The ancestor object as a Resource, null if this fails
	 */
	public Resource getAncestorResource(Object input) {
		return getAncestorEObject(input).eResource();
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public boolean showAncestor(Object input) {
		return input instanceof ICompareInput;
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public String getLeftLabel(Object input) {
		return config.getLeftLabel(input);
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public Image getLeftImage(Object input) {
		return config.getLeftImage(input);
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public Object getLeftContent(Object input) {	
		if (input instanceof ICompareInput)
			return ((ICompareInput) input).getLeft();
		return null;
	}
	
	/**
	 * Trys to return the left object as an EObject. If this fails the result is null.
	 * @param input The input object of the ContentMergeViewer
	 * @return The left object as an EObject, null if this fails
	 */
	public EObject getLeftEObject(Object input) {
		Object left = getLeftContent(input);
		if(left instanceof IStreamContentAccessor) {
			try {
				return Util.loadEObjectFromStream((IStreamContentAccessor) left);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Trys to return the left object as a Resource. If this fails the result is null.
	 * @param input The input object of the ContentMergeViewer
	 * @return The left object as a Resource, null if this fails
	 */
	public Resource getLeftResource(Object input) {
		return getLeftEObject(input).eResource();
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public boolean isLeftEditable(Object input) {
		return config.isLeftEditable();
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public void saveLeftContent(Object input, byte[] bytes) {
		if(input instanceof IEditableContent) {
			((IEditableContent) input).setContent(bytes);
		}
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public String getRightLabel(Object input) {
		return config.getRightLabel(input);
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public Image getRightImage(Object input) {
		return config.getRightImage(input);
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public Object getRightContent(Object input) {	
		if (input instanceof ICompareInput)
			return ((ICompareInput) input).getRight();
		return null;
	}
	
	/**
	 * Trys to return the right object as an EObject. If this fails the result is null.
	 * @param input The input object of the ContentMergeViewer
	 * @return The right object as an EObject, null if this fails
	 */
	public EObject getRightEObject(Object input) {
		Object right = getRightContent(input);
		if(right instanceof IStreamContentAccessor) {
			try {
				return Util.loadEObjectFromStream((IStreamContentAccessor) right);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Trys to return the right object as a Resource. If this fails the result is null.
	 * @param input The input object of the ContentMergeViewer
	 * @return The right object as a Resource, null if this fails
	 */
	public Resource getRightResource(Object input) {
		return getRightEObject(input).eResource();
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public boolean isRightEditable(Object input) {
		return config.isRightEditable();
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public void saveRightContent(Object input, byte[] bytes) {
		if(input instanceof IEditableContent) {
			((IEditableContent) input).setContent(bytes);
		}
	}

}
