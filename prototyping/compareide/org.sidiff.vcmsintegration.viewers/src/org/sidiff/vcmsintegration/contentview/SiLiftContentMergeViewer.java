package org.sidiff.vcmsintegration.contentview;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.ContentMergeViewer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.vcmsintegration.Activator;
import org.sidiff.vcmsintegration.IStructureMergeViewerRegisteredListener;
import org.sidiff.vcmsintegration.ResourceChangeListener;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;
import org.sidiff.vcmsintegration.ViewerRegistry;
import org.sidiff.vcmsintegration.remote.CompareResource;
import org.sidiff.vcmsintegration.remote.CompareResource.Side;
import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer;
import org.sidiff.vcmsintegration.util.MessageDialogUtil;

/**
 * Used to show all ecore-files participating in a comparison as trees;
 * highlights items concerning a difference selected in the associated
 * {@link org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer}
 * 
 * @author Jonas Schmeck, Robert Müller
 *
 */
public class SiLiftContentMergeViewer extends ContentMergeViewer implements ResourceChangeListener {

	private ISelectionProvider selectionProvider;
	private Map<Side, TreeViewer> treeViewers;
	private Map<Side, CompareResource> resources;
	private Map<Side, SiLiftContentMergeViewerLabelProvider> labelProviders;
	private AdapterFactory adapterFactory;

	/**
	 * Creates a new {@link SiLiftContentMergeViewer} with the given compare configuration.
	 *
	 * @param parent The parent composite that holds this view
	 * @param config Configuration used to obtain information about the comparison
	 * 
	 */
	public SiLiftContentMergeViewer(Composite parent, SiLiftCompareConfiguration config) {
		super(SWT.NONE, ResourceBundle.getBundle("org.sidiff.vcmsintegration.contentview.res"), config);
		this.selectionProvider = config.getContainer().getWorkbenchPart().getSite().getSelectionProvider();
		this.adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.treeViewers = new EnumMap<>(Side.class);
		this.resources = new EnumMap<>(Side.class);
		this.labelProviders = new EnumMap<>(Side.class);

		setContentProvider(new SiLiftContentMergeViewerContentProvider(config));
		buildControl(parent);
		getControl().setData(CompareUI.COMPARE_VIEWER_TITLE, "SiLiftContentViewer");

		// manage the toolbar
		customizeToolbar(getToolBarManager(parent));

		// check if StructureMergeViewer is already registered
		if(ViewerRegistry.getInstance().getStructureMergeViewer() != null) {
			ViewerRegistry.getInstance().getStructureMergeViewer().addResourceChangeListener(this);
		} else {
			// add listener and register the ResourceChangedListener as soon as the StructureMergeViewer is registered
			ViewerRegistry.getInstance().addStructureMergeViewerRegisteredListener(new IStructureMergeViewerRegisteredListener() {
				@Override
				public void onStructureMergeViewerRegisteredListener(SiLiftStructureMergeViewer structureMergeViewer) {
					// register ResourceChangedListener
					structureMergeViewer.addResourceChangeListener(SiLiftContentMergeViewer.this);
					// remove own listener
					ViewerRegistry.getInstance().removeStructureMergeViewerRegisteredListener(this);
				}
			});
		}
	}

	/**
	 * Remove unused items from the toolbar and set icons for actions
	 * @param manager The ToolBarManager that holds the items
	 */
	private void customizeToolbar(IToolBarManager manager) {
		IContributionItem[] items = manager.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i] instanceof ActionContributionItem) {
				ActionContributionItem item = (ActionContributionItem) items[i];
				switch(item.getAction().getText()) {
					case "Toggle Ancestor":
						item.getAction().setImageDescriptor(Activator.getImageDescriptor(Activator.IMAGE_TOGGLE_ANCESTOR));
						break;

					case "Swap Left and Right View":
						item.getAction().setImageDescriptor(Activator.getImageDescriptor(Activator.IMAGE_SWAP_LEFT_RIGHT));
						break;

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
			SiLiftContentMergeViewerLabelProvider labelProvider = new SiLiftContentMergeViewerLabelProvider(adapterFactory, treeViewer, selectionProvider);
			treeViewer.setLabelProvider(labelProvider);
			treeViewer.setInput(resources.get(side));

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
		try {
			onResourceChanged(CompareResource.load((ITypedElement)ancestor, Side.ANCESTOR));
			onResourceChanged(CompareResource.load((ITypedElement)left, Side.LEFT));
			onResourceChanged(CompareResource.load((ITypedElement)right, Side.RIGHT));
		} catch(IOException | CoreException e) {
			MessageDialogUtil.showExceptionDialog(e);
		}
	}

	/**
	 * unused
	 */
	@Override
	protected void copy(boolean leftToRight) {
		// is implemented by the structure-viewer
	}

	/**
	 * unused
	 */
	@Override
	protected byte[] getContents(boolean left) {
		// not supported
		return null;
	}

	/**
	 * Called by the {@link org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer} when
	 * one of the files that participate in the comparison change, this updates the {@link TreeViewer}s
	 * so they show the new version
	 * @param compareResource The {@link CompareResource} that holds the changed resource and some metadata
	 */
	@Override
	public void onResourceChanged(CompareResource changed) {
		resources.put(changed.getSide(), changed);

		TreeViewer treeViewer = treeViewers.get(changed.getSide());
		if(treeViewer != null) {
			treeViewer.setInput(changed.getResource());
			treeViewer.refresh();
		}
	}

	@Override
	protected void handleDispose(DisposeEvent event) {
		if(ViewerRegistry.getInstance().getStructureMergeViewer() != null) {
			ViewerRegistry.getInstance().getStructureMergeViewer().addResourceChangeListener(this);
		}

		super.handleDispose(event);
	}
}
