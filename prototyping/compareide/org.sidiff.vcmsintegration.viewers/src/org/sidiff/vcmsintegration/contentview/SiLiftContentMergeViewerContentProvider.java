package org.sidiff.vcmsintegration.contentview;

import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;
import org.sidiff.vcmsintegration.SiLiftCompareDifferencer;
import org.sidiff.vcmsintegration.remote.CompareResource;

/**
 * 
 * @author Felix Breitweiser, Robert Müller
 *
 */
public class SiLiftContentMergeViewerContentProvider implements IMergeViewerContentProvider {

	/**
	 * The compare configuration that is provided by the eclipse environment
	 */
	private SiLiftCompareConfiguration config;

	private SiLiftCompareDifferencer differencer;

	/**
	 * Creates a new instance of the
	 * {@link SiLiftContentMergeViewerContentProvider}.
	 * 
	 * @param config The compare configuration that is provided by the eclipse evironment
	 */
	public SiLiftContentMergeViewerContentProvider(SiLiftCompareConfiguration config) {
		this.config = config;
		this.differencer = SiLiftCompareDifferencer.getInstance();
	}

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
	public CompareResource getAncestorContent(Object input) {
		return differencer.getAncestor();
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public boolean showAncestor(Object input) {
		return differencer.getAncestor() != null && differencer.getAncestor().getResource() != null;
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public String getLeftLabel(Object input) {
		if(config.isMirrored())
			return config.getRightLabel(input);
		return config.getLeftLabel(input);
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public Image getLeftImage(Object input) {
		if(config.isMirrored())
			return config.getRightImage(input);
		return config.getLeftImage(input);
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public CompareResource getLeftContent(Object input) {
		// NOTE: getModifiedLeft() already handles the mirrored state
		return differencer.getModifiedLeft();
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public boolean isLeftEditable(Object input) {
		if(config.isMirrored())
			return config.isRightEditable();
		return config.isLeftEditable();
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public void saveLeftContent(Object input, byte[] bytes) {
		if(input instanceof ICompareInput) {
			ITypedElement element = config.isMirrored()
					? ((ICompareInput)input).getRight()
					: ((ICompareInput)input).getLeft();
			if(element instanceof IEditableContent) {
				((IEditableContent)element).setContent(bytes);
			}
		}
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public String getRightLabel(Object input) {
		if(config.isMirrored())
			return config.getLeftLabel(input);
		return config.getRightLabel(input);
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public Image getRightImage(Object input) {
		if(config.isMirrored())
			return config.getLeftImage(input);
		return config.getRightImage(input);
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public CompareResource getRightContent(Object input) {
		// NOTE: getModifiedRight() already handles the mirrored state
		return differencer.getModifiedRight();
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public boolean isRightEditable(Object input) {
		if(config.isMirrored())
			return config.isLeftEditable();
		return config.isRightEditable();
	}

	/**
	 * @see {@link org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider}
	 */
	@Override
	public void saveRightContent(Object input, byte[] bytes) {
		if(input instanceof ICompareInput) {
			ITypedElement element = config.isMirrored()
					? ((ICompareInput)input).getLeft()
					: ((ICompareInput)input).getRight();
			if(element instanceof IEditableContent) {
				((IEditableContent)element).setContent(bytes);
			}
		}
	}

}
