package org.sidiff.vcmsintegration.structureview.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.vcmsintegration.Activator;
import org.sidiff.vcmsintegration.DisplayMode;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;
import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewer;
import org.sidiff.vcmsintegration.structureview.SiLiftStructureMergeViewerContentProvider;
import org.sidiff.vcmsintegration.util.MessageDialogUtil;

/**
 * An action that is used to toggle the direction of the difference that is
 * shown, since applying operation invocations depends on this direaction.
 * 
 * @author Adrian Bingener, Robert Müller
 *
 */
public class ToggleDirectionAction extends Action implements IPropertyChangeListener {

	private SiLiftStructureMergeViewer mergeViewer;
	private SiLiftStructureMergeViewerContentProvider contentProvider;
	private SiLiftCompareConfiguration compareConfiguration;

	/**
	 * Creates a new instance of the {@link ToggleDirectionAction}.
	 */
	public ToggleDirectionAction(SiLiftStructureMergeViewer mergeViewer, SiLiftStructureMergeViewerContentProvider contentProvider,
			SiLiftCompareConfiguration compareConfiguration) {
		Assert.isNotNull(contentProvider);
		this.setText("Switch Difference Direction");
		this.setImageDescriptor(Activator.getImageDescriptor(Activator.IMAGE_SWAP_LEFT_RIGHT));
		this.mergeViewer = mergeViewer;
		this.contentProvider = contentProvider;
		this.compareConfiguration = compareConfiguration;
		this.compareConfiguration.addPropertyChangeListener(this);
	}

	@Override
	public void run() {
		MessageDialogUtil.showProgressDialog(new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Calculating model difference", IProgressMonitor.UNKNOWN);
				try {
					contentProvider.switchDifferenceDirection();
				} catch (InvalidModelException | NoCorrespondencesException | CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		});

		mergeViewer.refresh();
	}

	public void updateEnabledState() {
		setEnabled(compareConfiguration.getDisplayMode() == DisplayMode.ASYMMETRIC_DIFFERENCE);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getProperty() == SiLiftCompareConfiguration.DISPLAY_MODE) {
			updateEnabledState();
		}
	}
}
