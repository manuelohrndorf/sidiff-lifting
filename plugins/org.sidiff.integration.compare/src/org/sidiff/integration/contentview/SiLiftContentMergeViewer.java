package org.sidiff.integration.contentview;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.contentmergeviewer.ContentMergeViewer;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.integration.SiLiftCompareConfiguration;
import org.sidiff.integration.SiLiftCompareDifferencer;
import org.sidiff.integration.remote.CompareResource;
import org.sidiff.integration.remote.CompareResource.Side;
import org.sidiff.integration.selection.SiLiftCompareSelectionController;
import org.sidiff.integration.util.MessageDialogUtil;

/**
 * Used to show all ecore-files participating in a comparison as trees;
 * highlights items concerning a difference selected in the associated
 * {@link org.sidiff.integration.structureview.SiLiftStructureMergeViewer}
 * 
 * @author Jonas Schmeck, Robert Müller
 *
 */
public class SiLiftContentMergeViewer extends ContentMergeViewer {

	private static final String BUNDLE_NAME = "org.sidiff.integration.contentview.SiLiftContentMergeViewer";

	private SiLiftCompareConfiguration config;

	private Map<Side, TreeViewer> treeViewers;
	private Map<Side, CompareResource> resources;

	private AdapterFactoryContentProvider contentProvider;
	private Map<Side, SiLiftContentMergeViewerLabelProvider> labelProviders;

	private SiLiftCompareDifferencer.IModelViewerAdapter modelViewerAdapter;
	private IPropertyChangeListener propertyChangeListener;
	private ISelectionChangedListener selectionListener;

	/**
	 * Creates a new {@link SiLiftContentMergeViewer} with the given compare configuration.
	 *
	 * @param parent The parent composite that holds this view
	 * @param config Configuration used to obtain information about the comparison
	 * 
	 */
	public SiLiftContentMergeViewer(Composite parent, SiLiftCompareConfiguration config) {
		super(SWT.NONE, ResourceBundle.getBundle(BUNDLE_NAME), config);
		this.config = config;
		this.contentProvider = new AdapterFactoryContentProvider(config.getAdapterFactory());
		this.treeViewers = new EnumMap<>(Side.class);
		this.resources = new EnumMap<>(Side.class);
		this.labelProviders = new EnumMap<>(Side.class);

		setContentProvider(new SiLiftContentMergeViewerContentProvider(config));
		buildControl(parent);
		getControl().setData(CompareUI.COMPARE_VIEWER_TITLE, "SiLift ContentViewer");

		// manage the toolbar
		customizeToolbar(getToolBarManager(parent));

		modelViewerAdapter = new SiLiftCompareDifferencer.IModelViewerAdapter() {
			@Override
			public void setDirty(Side side, boolean dirty) {
				if(side == Side.LEFT)
					setLeftDirty(dirty);
				else if(side == Side.RIGHT)
					setRightDirty(dirty);
			}

			@Override
			public void onChange(Side side, CompareResource compRes) {
				updateCompareResource(side, compRes);
			}

			@Override
			public void onRefresh(Side side) {
				TreeViewer treeViewer = treeViewers.get(side);
				if(treeViewer != null) {
					treeViewer.refresh();
				}
			}
		};
		config.getDifferencer().addModelViewerAdapter(modelViewerAdapter);

		propertyChangeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if(event.getProperty() == SiLiftCompareConfiguration.MIRRORED) {
					refresh();

					// switch dirty states
					boolean leftDirty = isLeftDirty();
					setLeftDirty(isRightDirty());
					setRightDirty(leftDirty);
				}
			}
		};
		config.addPropertyChangeListener(propertyChangeListener);
	}

	/**
	 * Remove unused items from the toolbar.
	 * @param manager The ToolBarManager that holds the items
	 */
	private void customizeToolbar(IToolBarManager manager) {
		IContributionItem[] items = manager.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i] instanceof ActionContributionItem) {
				ActionContributionItem item = (ActionContributionItem) items[i];
				switch(item.getAction().getText()) {
					case "action.CopyLeftToRight.label":
					case "action.CopyRightToLeft.label":
						manager.remove(item);
						break;
				}
			}
		}
	}

	/**
	 * Create the {@link TreeViewer}s and set their content/label providers.
	 * 
	 * @param composite The parent composite for the {@link TreeViewer}s
	 *  
	 */
	@Override
	protected void createControls(Composite composite) {
		for(Side side : Side.values()) {
			TreeViewer treeViewer = new TreeViewer(composite);
			treeViewer.setContentProvider(contentProvider);
			SiLiftContentMergeViewerLabelProvider labelProvider =
					new SiLiftContentMergeViewerLabelProvider(config.getAdapterFactory(),
							treeViewer, SiLiftCompareSelectionController.getInstance());
			treeViewer.setLabelProvider(labelProvider);
			treeViewer.addSelectionChangedListener(SiLiftCompareSelectionController.getInstance());

			treeViewers.put(side, treeViewer);
			labelProviders.put(side, labelProvider);
		}
	}

	/**
	 * Handles adjustment of the {@link ancestorTree}.
	 */
	@Override
	protected void handleResizeAncestor(int x, int y, int width, int height) {
		TreeViewer ancestorTree = treeViewers.get(Side.ANCESTOR);
		if(ancestorTree == null)
			return;

		if(width > 0 && height > 0) {
			ancestorTree.getTree().setVisible(true);
			ancestorTree.getTree().setBounds(x, y, width, height);
		} else {
			ancestorTree.getTree().setVisible(false);
		}
	}

	/**
	 * Handles adjustment of the {@link leftTree} / {@link rightTree}. 
	 */
	@Override
	protected void handleResizeLeftRight(int x, int y, int leftWidth, int centerWidth, int rightWidth, int height) {
		TreeViewer leftTree = treeViewers.get(Side.LEFT);
		if(leftTree != null) {
			leftTree.getTree().setBounds(x, y, leftWidth, height);
		}

		TreeViewer rightTree = treeViewers.get(Side.RIGHT);
		if(rightTree != null) {
			rightTree.getTree().setBounds(x+leftWidth+centerWidth, y, rightWidth, height);
		}
	}

	/**
	 * Initializes the content displayed in this viewer.
	 * 
	 * @param ancestor the ancestor object
	 * @param left the left object
	 * @param right the right object
	 * 
	 */
	@Override
	protected void updateContent(Object ancestor, Object left, Object right) {
		updateCompareResource(Side.ANCESTOR, (CompareResource)ancestor);
		updateCompareResource(Side.LEFT, (CompareResource)left);
		updateCompareResource(Side.RIGHT, (CompareResource)right);
	}

	@Override
	public void setContentProvider(IContentProvider contentProvider) {
		// NOTE: this must be overridden or else the superclass
		//       implementation clears our own content provider
		//       when the view is mirrored
		if(contentProvider instanceof SiLiftContentMergeViewerContentProvider) {
			super.setContentProvider(contentProvider);
		}
	}

	/**
	 * unused
	 */
	@Override
	protected void copy(boolean leftToRight) {
		// is implemented by the structure-viewer
	}

	@Override
	protected byte[] getContents(boolean left) {
		try {
			CompareResource compRes = resources.get(left ? Side.LEFT : Side.RIGHT);
			if(compRes != null && compRes.getResource() != null) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				compRes.getResource().save(out, null);
				return out.toByteArray();
			}
		} catch (IOException e) {
			MessageDialogUtil.showExceptionDialog(e);
		}
		return null;
	}

	protected void updateCompareResource(Side side, CompareResource changed) {
		resources.put(side, changed);

		TreeViewer treeViewer = treeViewers.get(side);
		if(treeViewer != null) {
			treeViewer.setInput(changed == null ? null : changed.getResource());
			treeViewer.expandToLevel(2);
			treeViewer.refresh();
		}
	}

	@Override
	protected void handleDispose(DisposeEvent event) {
		if(config.getDifferencer() != null && modelViewerAdapter != null) {
			config.getDifferencer().removeModelViewerAdapter(modelViewerAdapter);
			modelViewerAdapter = null;
		}
		if(propertyChangeListener != null) {
			config.removePropertyChangeListener(propertyChangeListener);
			propertyChangeListener = null;
		}
		if(selectionListener != null) {
			for(TreeViewer treeViewer : treeViewers.values()) {
				treeViewer.removeSelectionChangedListener(selectionListener);
			}
			selectionListener = null;
		}
		super.handleDispose(event);
	}
}
