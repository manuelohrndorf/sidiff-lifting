package org.sidiff.vcmsintegration.structureview.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.vcmsintegration.Activator;
import org.sidiff.vcmsintegration.DisplayMode;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;
import org.sidiff.vcmsintegration.util.MessageDialogUtil;

/**
 * 
 * @author Robert Müller
 *
 */
public class ApplyPatchOnLeftAction extends Action implements IPropertyChangeListener {

	private SiLiftCompareConfiguration config;

	/**
	 * Creates a new instance of the {@link ApplyPatchOnLeftAction}.
	 * @param viewer 
	 */
	public ApplyPatchOnLeftAction(SiLiftCompareConfiguration config) {
		super("Apply all non conflicting and unignored changes", Activator.getImageDescriptor(Activator.IMAGE_APPLY_PATCH));
		this.setToolTipText("Apply all non conflicting and unignored changes");
		this.config = config;
		// TODO: the listener is not properly removed
		this.config.addPropertyChangeListener(this);
		updateEnabledState();
	}

	@Override
	public void run() {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("Applying Patch", IProgressMonitor.UNKNOWN);
				try {
					config.getDifferencer().applyPatch();
				} catch (Exception e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		MessageDialogUtil.showProgressDialog(runnable);
	}

	public void updateEnabledState() {
		setEnabled(config.getDisplayMode() == DisplayMode.ASYMMETRIC_DIFFERENCE
				&& config.getDifferencer().getPatchEngine() != null);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getProperty() == SiLiftCompareConfiguration.DISPLAY_MODE
				|| event.getProperty() == SiLiftCompareConfiguration.MIRRORED) {
			updateEnabledState();
		}
	}
}
