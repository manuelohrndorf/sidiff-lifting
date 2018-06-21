package org.sidiff.vcmsintegration.structureview.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.vcmsintegration.Activator;
import org.sidiff.vcmsintegration.DisplayMode;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;
import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer;
import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewerContentProvider;
import org.sidiff.vcmsintegration.util.MessageDialogUtil;

/**
 * An action that is used to apply a selected operation invocation.
 * 
 * @author Adrian Bingener, Robert Müller
 *
 */
public class ApplyOnLeftAction extends Action implements ISelectionChangedListener, IPropertyChangeListener {

	private OperationInvocation selectedOperation;
	private SiLiftStructureMergeViewer viewer;
	private SiLiftStructureMergeViewerContentProvider contentProvider;
	private SiLiftCompareConfiguration compareConfiguration;

	/**
	 * Creates a new instance of the {@link ApplyOnLeftAction}.
	 * @param viewer 
	 */
	public ApplyOnLeftAction(SiLiftStructureMergeViewer viewer, SiLiftStructureMergeViewerContentProvider contentProvider,
			SiLiftCompareConfiguration compareConfiguration) {
		Assert.isNotNull(contentProvider);
		this.setText("Apply selected operation on left");
		this.setEnabled(false);
		this.setImageDescriptor(Activator.getImageDescriptor(Activator.IMAGE_MERGE_TO_LEFT));
		this.viewer = viewer;
		this.contentProvider = contentProvider;
		this.compareConfiguration = compareConfiguration;
		// TODO: the listener is not properly removed
		this.compareConfiguration.addPropertyChangeListener(this);
	}

	@Override
	public void run() {
		// apply OperationInvocation
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Applying Operation", IProgressMonitor.UNKNOWN);
				try {
					contentProvider.applyOperationInvocation(selectedOperation);
				} catch (Exception e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		MessageDialogUtil.showProgressDialog(runnable);

		// fire ResourceChangedEvent
		viewer.notifyResourceChangeListener(contentProvider.getPatchedResource());

		// refresh GUI
		viewer.refresh();
	}

	public void updateEnabledState() {
		setEnabled(compareConfiguration.getDisplayMode() == DisplayMode.ASYMMETRIC_DIFFERENCE
				&& selectedOperation != null && contentProvider.getLeft().isEditable());
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		selectedOperation = null;
		ISelection selection = event.getSelection();
		if(selection instanceof IStructuredSelection) {
			Object firstElement = ((IStructuredSelection)selection).getFirstElement();
			if(firstElement instanceof OperationInvocation) {
				selectedOperation = (OperationInvocation)firstElement;
			}
		}

		updateEnabledState();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getProperty() == SiLiftCompareConfiguration.DISPLAY_MODE) {
			updateEnabledState();
		}
	}
}
