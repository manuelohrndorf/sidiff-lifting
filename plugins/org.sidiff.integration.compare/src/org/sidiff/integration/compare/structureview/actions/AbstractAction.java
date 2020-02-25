package org.sidiff.integration.compare.structureview.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.sidiff.integration.compare.SiLiftCompareConfiguration;

/**
 * @author rmueller
 */
public abstract class AbstractAction extends Action implements ISelectionChangedListener {

	private ISelectionProvider selectionProvider;
	private SiLiftCompareConfiguration config;

	public AbstractAction(String text, ImageDescriptor image, ISelectionProvider selectionProvider, SiLiftCompareConfiguration config) {
		super(text, image);
		this.selectionProvider = selectionProvider;
		this.selectionProvider.addSelectionChangedListener(this);
		this.config = config;
	}

	public void dispose() {
		if(selectionProvider != null) {
			selectionProvider.removeSelectionChangedListener(this);
			selectionProvider = null;
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if(event.getSelection() instanceof IStructuredSelection) {
			handleSelectionChanged(((IStructuredSelection)event.getSelection()).getFirstElement());
		}
	}

	public abstract void handleSelectionChanged(Object object);
	public abstract boolean isVisible();

	public SiLiftCompareConfiguration getConfig() {
		return config;
	}
}
