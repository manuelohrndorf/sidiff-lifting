package org.sidiff.vcmsintegration.contentview;

import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.contentmergeviewer.ContentMergeViewer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.impl.OperationInvocationImpl;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.impl.ChangeImpl;
import org.sidiff.difference.symmetric.impl.SemanticChangeSetImpl;
import org.sidiff.vcmsintegration.IStructureMergeViewerRegisteredListener;
import org.sidiff.vcmsintegration.ResourceChangeListener;
import org.sidiff.vcmsintegration.ViewerRegistry;
import org.sidiff.vcmsintegration.contentprovider.CompareResource;
import org.sidiff.vcmsintegration.contentprovider.SiLiftMergeViewerContentProvider;
import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer;
import org.sidiff.vcmsintegration.util.Resources;
import org.sidiff.vcmsintegration.util.Util;

/**
 * Used to show all ecore-files participating in a comparison as trees;
 * highlights items concerning a difference selected in the associated
 * {@link org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer}
 * 
 * @author Jonas Schmeck
 *
 */
public class SiLiftContentMergeViewer extends ContentMergeViewer implements ResourceChangeListener {
	
	/**
	 * The plug-in ID
	 */
	private static final String PLUGIN_ID = "org.sidiff.vcmsintegration.viewers"; //$NON-NLS-1$
	
	/**
	 * content objects
	 */
	private Object ancestor, left, right;
	
	/**
	 * separate {@link TreeViewer}s to view ecore-files
	 */
	private TreeViewer ancestorTree, leftTree, rightTree;
	
	
	/**
	 * Creates a new {@link SiLiftContentMergeViewer} with the given compare configuration.
	 *
	 * @param parent The parent composite that holds this view
	 * @param config Configuration used to obtain information about the comparison
	 * 
	 */
	public SiLiftContentMergeViewer(Composite parent, CompareConfiguration config) {
		super(SWT.NULL, ResourceBundle.getBundle("org.sidiff.vcmsintegration.contentview.res"), config);
		
		// register ContentMergeViewer
		ViewerRegistry.getInstance().setContentMergeViewer(this);;
		
		setContentProvider(new SiLiftMergeViewerContentProvider(config));
		buildControl(parent);
		
		// manage the toolbar
		customizeToolbar(getToolBarManager(parent));
		
		getControl().setData(CompareUI.COMPARE_VIEWER_TITLE, "SiLiftContentViewer");
		
		// register listener to receive notifications about the selection made in the structure-viewer
		config.getContainer().getWorkbenchPart().getSite().getSelectionProvider().addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				SiLiftContentMergeViewer.this.selectionChanged(event);
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
				if (item.getAction().getText().equals("Toggle Ancestor"))
					item.getAction().setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, Resources.ICON_TOGGLE_ANCESTOR));
				if (item.getAction().getText().equals("action.CopyLeftToRight.label"))
					manager.remove(item);
				if (item.getAction().getText().equals("action.CopyRightToLeft.label"))
					manager.remove(item);
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
	    ancestorTree = new TreeViewer(composite);
	    ancestorTree.setContentProvider(Util.getAdapterFactoryContentProvider());
	    ancestorTree.setLabelProvider(Util.getAdapterFactoryLabelProvider());
	    
	    leftTree = new TreeViewer(composite);
	    leftTree.setContentProvider(Util.getAdapterFactoryContentProvider());
	    leftTree.setLabelProvider(Util.getAdapterFactoryLabelProvider());
	    
	    rightTree = new TreeViewer(composite);
	    rightTree.setContentProvider(Util.getAdapterFactoryContentProvider());
	    rightTree.setLabelProvider(Util.getAdapterFactoryLabelProvider());
	}

	/**
	 * Handles adjustment of the {@link ancestorTree}.
	 */
	@Override
	protected void handleResizeAncestor(int x, int y, int width, int height) {
		if(width > 0) {
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
		leftTree.getTree().setBounds(x, y, leftWidth, height);
		rightTree.getTree().setBounds(x+leftWidth+centerWidth, y, rightWidth, height);
	}
	
	/**
	 * Updates the content displayed in this viewer.
	 * 
	 * @param a The object to be displayed in the {@link ancestorTree}
	 * @param l The object to be displayed in the {@link leftTree}
	 * @param r The object to be displayed in the {@link rightTree}
	 * 
	 */
	@Override
	protected void updateContent(Object a, Object l, Object r) {
		ancestor = a;
		left = l;
		right = r;
		try {
			if(ancestor != null && ancestor instanceof IStreamContentAccessor) {
				ancestorTree.setInput(Util.loadEObjectFromStream((IStreamContentAccessor) ancestor));
				ancestorTree.refresh();
			}
			if(left != null && left instanceof IStreamContentAccessor) {
				leftTree.setInput(Util.loadEObjectFromStream((IStreamContentAccessor) left));
				leftTree.refresh();
			}
			if(right != null && right instanceof IStreamContentAccessor) {
				rightTree.setInput(Util.loadEObjectFromStream((IStreamContentAccessor) right));
				rightTree.refresh();
			}
		} catch(Exception e) {
			e.printStackTrace();
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
		// not needed
		return new byte[0];
	}
	
	/**
	 * Returns the content provider of this viewer
	 * 
	 * @return {@link SiLiftMergeViewerContentProvider}
	 * 
	 */
	public SiLiftMergeViewerContentProvider getEcoreMergeContentProvider() {
		return (SiLiftMergeViewerContentProvider) getContentProvider();
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
	public void selectionChanged(SelectionChangedEvent event) {
		if(!(event.getSelection() instanceof TreeSelection)) return;
		TreeSelection selection = (TreeSelection) event.getSelection();
		
		if (selection.getFirstElement() instanceof ChangeImpl) {
			ChangeImpl change = (ChangeImpl) selection.getFirstElement();
			
			resetTrees();
			
			for(EReference ref : change.eClass().getEAllReferences()) {
				if (change instanceof AttributeValueChange)
					findItem((EObject) change.eGet(ref), new Color(Display.getCurrent(), 255, 0, 127));
				else if (change instanceof RemoveObject)
					findItem((EObject) change.eGet(ref), new Color(Display.getCurrent(), 190, 10, 10));
				else if (change instanceof AddObject)
					findItem((EObject) change.eGet(ref), new Color(Display.getCurrent(), 10, 190, 10));
				// else
				// either instanceof 'RemoveReference' or 'AddReference'
				// we don't want to highlight these
			}
			
			refreshTrees();
		}
		else if (selection.getFirstElement() instanceof SemanticChangeSetImpl) {
			SemanticChangeSet changeSet = (SemanticChangeSet) selection.getFirstElement();
			
			resetTrees();
			
			highlightChangeSet(changeSet);
			
			refreshTrees();
		}
		else if (selection.getFirstElement() instanceof OperationInvocationImpl) {
			OperationInvocationImpl inv = (OperationInvocationImpl) selection.getFirstElement();
			
			resetTrees();
			
			highlightChangeSet(inv.getChangeSet());
			List<OperationInvocation> pre = inv.getAllPredecessors();
			if (pre != null) {
				for (OperationInvocation opInv : pre) {
					highlightChangeSet(opInv.getChangeSet());
				}
			}
			
			refreshTrees();
		}
	}
	
	/**
	 * This method highlights all changes in the given ChangeSet
	 * @param cs The ChangeSet
	 */
	private void highlightChangeSet(SemanticChangeSet cs) {
		for (Change c : cs.getChanges()) {
			ChangeImpl change = (ChangeImpl) c;
			for(EReference ref : change.eClass().getEAllReferences()) {
				if (change instanceof AttributeValueChange)
					findItem((EObject) change.eGet(ref), new Color(Display.getCurrent(), 255, 0, 127));
				else if (change instanceof RemoveObject)
					findItem((EObject) change.eGet(ref), new Color(Display.getCurrent(), 190, 10, 10));
				else if (change instanceof AddObject)
					findItem((EObject) change.eGet(ref), new Color(Display.getCurrent(), 10, 190, 10));
				// else
				// either instanceof 'RemoveReference' or 'AddReference'
				// we don't want to highlight these
			}
		}
	}
	
	/**
	 * Find the given object and expand the item containing it so it
	 * becomes visible; also call the method to highlight it.
	 * @param needle The object to be searched for
	 * @param c The color for highlighting
	 */
	private void findItem(EObject needle, Color c) {
		EObject lsel = ((EObject)leftTree.getInput()).eResource().getEObject(EcoreUtil.getURI(needle).fragment());
		EObject rsel = ((EObject)rightTree.getInput()).eResource().getEObject(EcoreUtil.getURI(needle).fragment());
		EObject asel = null;
		try {
			asel = ((EObject)ancestorTree.getInput()).eResource().getEObject(EcoreUtil.getURI(needle).fragment());
		} catch (Exception e) {
			asel = null;
		}
		
		if (lsel != null) {
			leftTree.setSelection(new StructuredSelection(lsel), true);
			TreeItem[] items = leftTree.getTree().getItems();
			recursiveFindItem(lsel, items, c);
		}
		
		if (rsel != null) {
			rightTree.setSelection(new StructuredSelection(rsel), true);
			TreeItem[] items = rightTree.getTree().getItems();
			recursiveFindItem(rsel, items, c);
		}
		
		if (asel != null) {
			ancestorTree.setSelection(new StructuredSelection(asel), true);
			TreeItem[] items = ancestorTree.getTree().getItems();
			c.dispose();
			recursiveFindItem(asel, items, new Color(Display.getCurrent(), 10, 10, 190));
		}
	}
	
	/**
	 * Refresh all trees and remove any selection made.
	 */
	private void refreshTrees() {
		leftTree.setSelection(null);
		leftTree.refresh();
		rightTree.setSelection(null);
		rightTree.refresh();
		try {
			ancestorTree.setSelection(null);
			ancestorTree.refresh();
		} catch (Exception e) {}
	}
	
	/**
	 * Reset all trees to initial state.
	 */
	private void resetTrees() {
		setForegroundColor(leftTree.getTree().getItems(), new Color(Display.getCurrent(), 0, 0, 0));
		leftTree.collapseAll();
		setForegroundColor(rightTree.getTree().getItems(), new Color(Display.getCurrent(), 0, 0, 0));
		rightTree.collapseAll();
		try {
			setForegroundColor(ancestorTree.getTree().getItems(), new Color(Display.getCurrent(), 0, 0, 0));
			ancestorTree.collapseAll();
		} catch (Exception e) {}
	}
	
	/**
	 * Search the EObject in the given items, if found set its foreground color
	 * @param needle The EObject to search for
	 * @param haystack The TreeItems to search
	 * @param c The new foreground color
	 */
	private void recursiveFindItem(EObject needle, TreeItem[] haystack, Color c) {
		if (haystack == null)
			return;
		for (int i = 0; i < haystack.length; i++) {
			TreeItem item = haystack[i];
			if (item.getData().equals(needle)) {
				item.setForeground(c);
				item.setExpanded(true);
				return;
			}
			else {
				try {
					recursiveFindItem(needle, item.getItems(), c);
				} catch (Exception e) {}
			}
		}
		return;
	}
	
	/**
	 * Set the foreground color of the given items
	 * @param items The items to change the color for
	 * @param c The new foreground color
	 */
	private void setForegroundColor(TreeItem[] items, Color c) {
		for (int i = 0; i < items.length; i++) {
			TreeItem item = items[i];
			item.setForeground(c);
			try {
				setForegroundColor(item.getItems(), c);
			} catch (Exception e) {}
		}
	}

	/**
	 * Called by the {@link org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer} when
	 * one of the files that participate in the comparison change, this updates the {@link TreeViewer}s
	 * so they show the new version
	 * @param compareResource The {@link CompareResource} that holds the changed resource and some metadata
	 */
	@Override
	public void onResourceChanged(CompareResource compareResource) {
		String type = compareResource.getResourceType().toString();
		if (type.equals("LEFT")) {
			left = compareResource.getResource();
			leftTree.setInput(compareResource.getResource().getContents().get(0));
			leftTree.refresh();
		}
		else if (type.equals("RIGHT")) {
			right = compareResource.getResource();
			rightTree.setInput(compareResource.getResource().getContents().get(0));
			rightTree.refresh();
		}
		else if (type.equals("ANCESTOR")) {
			ancestor = compareResource.getResource();
			ancestorTree.setInput(compareResource.getResource().getContents().get(0));
			ancestorTree.refresh();
		}
	}
	
}
