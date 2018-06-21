/**
 * 
 */
package org.sidiff.vcmsintegration.structureview.actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.sidiff.vcmsintegration.Activator;
import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewerContentProvider;
import org.sidiff.vcmsintegration.util.MessageDialogUtil;

/**
 * Save model to file
 * @author Louis Christ, Robert Müller
 *
 */
public class SaveAction extends Action {

	private SiLiftStructureMergeViewerContentProvider contentProvider;

	/**
	 * Creates a new instance of the {@link SaveAction}.
	 */
	public SaveAction(SiLiftStructureMergeViewerContentProvider contentProvider) {
		Assert.isNotNull(contentProvider);
		this.setText("Save model");
		this.setEnabled(false);
		this.setImageDescriptor(Activator.getImageDescriptor(Activator.IMAGE_SAVE));
		this.contentProvider = contentProvider;
	}

	@Override
	public void run() {
		MessageDialogUtil.showProgressDialog(new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					monitor.beginTask("Saving model", IProgressMonitor.UNKNOWN);
					Resource res = contentProvider.getPatchEngine().getPatchedResource();
					res.save(null);
					MessageDialogUtil.showMessageDialog("Saved successfully", "The model has been saved successfully: " + res.getURI());
				} catch (IOException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		});
	}

	public void updateEnabledState() {
		// TODO: change this condition
		setEnabled(contentProvider.getPatchEngine() != null
				&& contentProvider.getLeft().isEditable());
	}
}
