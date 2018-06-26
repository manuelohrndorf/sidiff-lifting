package org.sidiff.vcmsintegration.contentview;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.contentmergeviewer.ContentMergeViewer;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;
import org.sidiff.vcmsintegration.SiLiftCompareDifferencer;
import org.sidiff.vcmsintegration.remote.CompareResource;
import org.sidiff.vcmsintegration.remote.CompareResource.Side;
import org.sidiff.vcmsintegration.util.MessageDialogUtil;

/**
 * Used to show all ecore-files participating in a comparison as trees;
 * highlights items concerning a difference selected in the associated
 * {@link org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer}
 * 
 * @author Jonas Schmeck, Robert Müller
 *
 */
public class SiLiftContentMergeViewer extends ContentMergeViewer {

	private static final String BUNDLE_NAME = "org.sidiff.vcmsintegration.contentview.SiLiftContentMergeViewer";

	private ISelectionProvider selectionProvider;
	private Map<Side, TreeViewer> treeViewers;
	private Map<Side, CompareResource> resources;
	private Map<Side, SiLiftContentMergeViewerLabelProvider> labelProviders;
	private AdapterFactory adapterFactory;
	private SiLiftCompareDifferencer.IModelViewerAdapter modelViewerAdapter;
	private SiLiftCompareDifferencer differencer;
	private IPropertyChangeListener propertyChangeListener;
	private SiLiftCompareConfiguration config;

	/**
	 * Creates a new {@link SiLiftContentMergeViewer} with the given compare configuration.
	 *
	 * @param parent The parent composite that holds this view
	 * @param config Configuration used to obtain information about the comparison
	 * 
	 */
	public SiLiftContentMergeViewer(Composite parent, SiLiftCompareConfiguration config) {
		super(SWT.NONE, ResourceBundle.getBundle(BUNDLE_NAME), config);
		this.selectionProvider = config.getContainer().getWorkbenchPart().getSite().getSelectionProvider();
		this.adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.treeViewers = new EnumMap<>(Side.class);
		this.resources = new EnumMap<>(Side.class);
		this.labelProviders = new EnumMap<>(Side.class);
		this.differencer = SiLiftCompareDifferencer.getInstance();
		this.config = config;

		setContentProvider(new SiLiftContentMergeViewerContentProvider(config));
		buildControl(parent);
		getControl().setData(CompareUI.COMPARE_VIEWER_TITLE, "SiLiftContentViewer");

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
		differencer.addModelViewerAdapter(modelViewerAdapter);

		propertyChangeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if(event.getProperty() == SiLiftCompareConfiguration.MIRRORED) {
					refresh();

					// switch dirty states
					// TODO: check if this is required
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
			treeViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
			SiLiftContentMergeViewerLabelProvider labelProvider =
					new SiLiftContentMergeViewerLabelProvider(adapterFactory, treeViewer, selectionProvider);
			treeViewer.setLabelProvider(labelProvider);

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
	 * @param a The object to be displayed in the {@link ancestorTree}
	 * @param l The object to be displayed in the {@link leftTree}
	 * @param r The object to be displayed in the {@link rightTree}
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
		// NOTE: this must be overridden or else the superclass implementation clears our own content provider
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
		if(differencer != null && modelViewerAdapter != null) {
			differencer.removeModelViewerAdapter(modelViewerAdapter);
			differencer = null;
			modelViewerAdapter = null;
		}
		if(propertyChangeListener != null) {
			config.removePropertyChangeListener(propertyChangeListener);
			propertyChangeListener = null;
		}
		super.handleDispose(event);
	}
}
