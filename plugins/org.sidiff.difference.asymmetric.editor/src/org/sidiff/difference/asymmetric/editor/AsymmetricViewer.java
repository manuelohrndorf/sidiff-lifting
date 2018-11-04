package org.sidiff.difference.asymmetric.editor;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.sidiff.difference.symmetric.provider.AdapterToolTipLabelProvider;

public class AsymmetricViewer extends ContentViewer {

	/**
	 * The main control.
	 */
	Composite container;

	/**
	 * The tree viewer which displays the difference.
	 */
	private TreeViewer treeViewer;

	/**
	 * This is the one adapter factory used for providing views of the model.
	 */
	protected ComposedAdapterFactory adapterFactory;

	public AsymmetricViewer(ComposedAdapterFactory adapterFactory) {
		super();
		this.adapterFactory = adapterFactory;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public Composite createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));

		// Set item and label provider, and enable tooltips:
		treeViewer = new TreeViewer(container, SWT.MULTI);
		{
			treeViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
			treeViewer.setLabelProvider(new AdapterToolTipLabelProvider(adapterFactory, treeViewer));
			ColumnViewerToolTipSupport.enableFor(treeViewer, ToolTip.RECREATE);
		}

		Tree tree = treeViewer.getTree();
		{
			tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			treeViewer.addDoubleClickListener(new IDoubleClickListener() {
				@Override
				public void doubleClick(DoubleClickEvent event) {
					TreeViewer viewer = (TreeViewer) event.getViewer();
					IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
					Object selectedNode = thisSelection.getFirstElement();
					viewer.setExpandedState(selectedNode, !viewer.getExpandedState(selectedNode));
				}
			});
		}

		return container;
	}

	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	public void setDefaultSelection() {
		// Set selection:
		TreeItem item = treeViewer.getTree().getItem(0);

		if (item != null) {
			treeViewer.getTree().setSelection(item);
		}

		// Fake selection event:
		treeViewer.setSelection(treeViewer.getSelection(), true);
	}

	@Override
	public Control getControl() {
		return container;
	}

	@Override
	public Object getInput() {
		return treeViewer.getInput();
	}

	@Override
	public ISelection getSelection() {
		return treeViewer.getSelection();
	}

	@Override
	public void refresh() {
		treeViewer.refresh();
	}

	@Override
	public void setInput(Object input) {
		treeViewer.setInput(input);
	}

	@Override
	public void setSelection(ISelection selection, boolean reveal) {
		treeViewer.setSelection(selection, reveal);
	}

    @Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
    	treeViewer.addSelectionChangedListener(listener);
    }

    @Override
    public void removeSelectionChangedListener(ISelectionChangedListener listener) {
    	treeViewer.removeSelectionChangedListener(listener);
    }
}
