package org.sidiff.vcmsintegration.contentview;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.ContentMergeViewer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.vcmsintegration.Activator;
import org.sidiff.vcmsintegration.IStructureMergeViewerRegisteredListener;
import org.sidiff.vcmsintegration.ResourceChangeListener;
import org.sidiff.vcmsintegration.ViewerRegistry;
import org.sidiff.vcmsintegration.contentprovider.SiLiftMergeViewerContentProvider;
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

	private final Color COLOR_ATTRIBUTE_VALUE_CHANGED = new Color(Display.getCurrent(), 255, 0, 127);
	private final Color COLOR_REMOVE_OBJECT = new Color(Display.getCurrent(), 190, 10, 10);
	private final Color COLOR_ADD_OBJECT = new Color(Display.getCurrent(), 10, 190, 10);
	private final Color COLOR_ANCESTOR = new Color(Display.getCurrent(), 10, 10, 190);

	/**
	 * separate {@link TreeViewer}s to view ecore-files
	 */
	private Map<Side, TreeViewer> treeViewers;

	private Map<Side, CompareResource> resources;

	private Map<Side, LabelProvider> labelProviders;

	private AdapterFactory adapterFactory;
	
	
	/**
	 * Creates a new {@link SiLiftContentMergeViewer} with the given compare configuration.
	 *
	 * @param parent The parent composite that holds this view
	 * @param config Configuration used to obtain information about the comparison
	 * 
	 */
	public SiLiftContentMergeViewer(Composite parent, CompareConfiguration config) {
		super(SWT.NONE, ResourceBundle.getBundle("org.sidiff.vcmsintegration.contentview.res"), config);
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		treeViewers = new EnumMap<>(Side.class);
		resources = new EnumMap<>(Side.class);
		labelProviders = new EnumMap<>(Side.class);

		setContentProvider(new SiLiftMergeViewerContentProvider(config));
		buildControl(parent);
		getControl().setData(CompareUI.COMPARE_VIEWER_TITLE, "SiLiftContentViewer");

		// manage the toolbar
		customizeToolbar(getToolBarManager(parent));

		// register listener to receive notifications about the selection made in the structure-viewer
		config.getContainer().getWorkbenchPart().getSite().getSelectionProvider().addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				handleSelectionChanged(event);
			}
		});

		// check if StructureMergeViewer is already registered
		if(ViewerRegistry.getInstance().getStructureMergeViewer() != null) {
			ViewerRegistry.getInstance().getStructureMergeViewer().addResourceChangeListener(this);
		} else {
			// add listener and register the ResourceChangedListener as soon as the StructureMergeViewer is registered
			final SiLiftContentMergeViewer self = this;
			ViewerRegistry.getInstance().addStructureMergeViewerRegisteredListener(new IStructureMergeViewerRegisteredListener() {
				
				@Override
				public void onStructureMergeViewerRegisteredListener(SiLiftStructureMergeViewer structureMergeViewer) {
					// register ResourceChangedListener
					structureMergeViewer.addResourceChangeListener(self);
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
			LabelProvider labelProvider = new LabelProvider(adapterFactory, treeViewer);
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

	@Override
	protected void handleDispose(DisposeEvent event) {
		COLOR_ATTRIBUTE_VALUE_CHANGED.dispose();
		COLOR_REMOVE_OBJECT.dispose();
		COLOR_ADD_OBJECT.dispose();
		COLOR_ANCESTOR.dispose();
		super.handleDispose(event);
	}

	/**
	 * Is triggered whenever a new selection is made in the associated
	 * {@link org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer};
	 * Calls methods responsible for highlighting items concerning the
	 * selected difference.
	 * 
	 * @param event The triggered event containing the selection
	 * 
	 */
	protected void handleSelectionChanged(SelectionChangedEvent event) {
		if(!(event.getSelection() instanceof IStructuredSelection)) return;
		Object element = ((IStructuredSelection)event.getSelection()).getFirstElement();

		if (element instanceof Change) {
			clearAllHighlights();
			highlightChange((Change)element);
			refreshTrees();
		}
		else if (element instanceof SemanticChangeSet) {
			clearAllHighlights();
			highlightChangeSet((SemanticChangeSet)element);
			refreshTrees();
		}
		else if (element instanceof OperationInvocation) {
			clearAllHighlights();
			highlightOperationInvocation(element);
			refreshTrees();
		}
	}

	protected void highlightOperationInvocation(Object element) {
		OperationInvocation inv = (OperationInvocation)element;
		highlightChangeSet(inv.getChangeSet());
		List<OperationInvocation> pre = inv.getAllPredecessors();
		if (pre != null) {
			for (OperationInvocation opInv : pre) {
				highlightChangeSet(opInv.getChangeSet());
			}
		}
	}

	/**
	 * This method highlights all changes in the given ChangeSet
	 * @param cs The ChangeSet
	 */
	private void highlightChangeSet(SemanticChangeSet cs) {
		for (Change c : cs.getChanges()) {
			highlightChange(c);
		}
	}

	private void highlightChange(Change change) {
		if (change instanceof AttributeValueChange) {
			applyHighlight(((AttributeValueChange)change).getObjA(), COLOR_ATTRIBUTE_VALUE_CHANGED);
			applyHighlight(((AttributeValueChange)change).getObjB(), COLOR_ATTRIBUTE_VALUE_CHANGED);
		}
		else if (change instanceof RemoveObject) {
			applyHighlight(((RemoveObject)change).getObj(), COLOR_REMOVE_OBJECT);
		}
		else if (change instanceof AddObject) {
			applyHighlight(((AddObject)change).getObj(), COLOR_ADD_OBJECT);
		}
		// else
		// either instanceof 'RemoveReference' or 'AddReference'
		// we don't want to highlight these
	}

	private void applyHighlight(EObject eObject, Color color) {
		for(Side side : Side.values()) {
			TreeViewer treeViewer = treeViewers.get(side);
			if(treeViewer == null)
				continue;
			Resource input = (Resource)treeViewer.getInput();
			if(input == null) 
				continue;
			EObject resolvedObject = input.getEObject(EcoreUtil.getURI(eObject).fragment());
			if(resolvedObject == null)
				continue;

			labelProviders.get(side).setObjectColor(resolvedObject, color);
			treeViewer.expandToLevel(resolvedObject, TreeViewer.ALL_LEVELS);
			treeViewer.update(resolvedObject, null);
		}
	}

	private void clearAllHighlights() {
		for(LabelProvider labelProvider : labelProviders.values()) {
			labelProvider.clearAllColors();
		}
	}

	/**
	 * Refresh all trees.
	 */
	private void refreshTrees() {
		for(TreeViewer treeViewer : treeViewers.values()) {
			treeViewer.refresh();
		}
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

	private static class LabelProvider extends AdapterFactoryLabelProvider.ColorProvider {

		private Map<Object, Color> objectColors;

		public LabelProvider(AdapterFactory adapterFactory, Viewer viewer) {
			super(adapterFactory, viewer);
			objectColors = new HashMap<>();
		}

		@Override
		public Color getForeground(Object object) {
			Color color = objectColors.get(object);
			if(color != null) {
				return color;
			}

			return super.getForeground(object);
		}

		public void clearAllColors() {
			objectColors.clear();
		}

		public void setObjectColor(Object object, Color color) {
			objectColors.put(object, color);
		}
	}
}
