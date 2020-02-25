package org.sidiff.integration.compare.structureview;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.sidiff.integration.compare.internal.CompareIntegrationPlugin;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;

/**
 * @author rmueller
 */
public class SiLiftStructureMergeViewerLabelProvider extends AdapterFactoryLabelProvider.StyledLabelProvider {

	private Image imgApplied;
	private Image imgApplicable;
	private Image imgWarning;
	private Image imgConflicting;
	private Image imgIgnored;

	private Color colorApplied;
	private Color colorIgnored;

	public SiLiftStructureMergeViewerLabelProvider(AdapterFactory adapterFactory, Viewer viewer) {
		super(adapterFactory, viewer);
	}

	@Override
	public String getText(Object object) {
		if(object instanceof OperationInvocationWrapper) {
			OperationInvocationWrapper wrapper = (OperationInvocationWrapper)object;
			return wrapper.getText() + wrapper.getChangedArguments();
		}
		return super.getText(object);
	}

	@Override
	public Image getImage(Object object) {
		if(object instanceof OperationInvocationWrapper) {
			OperationInvocationWrapper wrapper = (OperationInvocationWrapper)object;
			if (wrapper.getStatus() == OperationInvocationStatus.PASSED) {
				if(imgApplied == null) {
					imgApplied = CompareIntegrationPlugin.getImageDescriptor(CompareIntegrationPlugin.IMAGE_APPLIED).createImage();
				}
				return imgApplied;
			} else if(wrapper.getStatus() == OperationInvocationStatus.IGNORED){
				if(imgIgnored == null) {
					imgIgnored = CompareIntegrationPlugin.getImageDescriptor(CompareIntegrationPlugin.IMAGE_IGNORED).createImage();
				}
				return imgIgnored;
			} else if (wrapper.getStatus() == OperationInvocationStatus.FAILED || wrapper.hasUnresolvedInArguments()) {
				if(imgConflicting == null) {
					imgConflicting = CompareIntegrationPlugin.getImageDescriptor(CompareIntegrationPlugin.IMAGE_CONFLICT).createImage();
				}
				return imgConflicting;
			} else if (wrapper.hasModifiedInArguments()) {
				if(imgWarning == null) {
					imgWarning = CompareIntegrationPlugin.getImageDescriptor(CompareIntegrationPlugin.IMAGE_WARNING).createImage();
				}
				return imgWarning;
			} else {
				if(imgApplicable == null) {
					imgApplicable = CompareIntegrationPlugin.getImageDescriptor(CompareIntegrationPlugin.IMAGE_APPLICABLE).createImage();
				}
				return imgApplicable;
			}
		}
		return super.getImage(object);
	}

	@Override
	public Color getForeground(Object object) {
		if(object instanceof OperationInvocationWrapper) {
			final OperationInvocationWrapper wrapper = (OperationInvocationWrapper)object;
			if(wrapper.getStatus() == OperationInvocationStatus.PASSED) {
				if(colorApplied == null) {
					colorApplied = new Color(Display.getDefault(), 0, 200, 0);
				}
				return colorApplied;
			} else if(wrapper.getStatus() == OperationInvocationStatus.IGNORED) {
				if(colorIgnored == null) {
					colorIgnored = new Color(Display.getDefault(), 128, 128, 128);
				}
				return colorIgnored;
			}
		}
		return super.getForeground(object);
	}

	@Override
	public void dispose() {
		if(imgApplied != null) {
			imgApplied.dispose();
			imgApplied = null;
		}
		if(imgApplicable != null) {
			imgApplicable.dispose();
			imgApplicable = null;
		}
		if(imgWarning != null) {
			imgWarning.dispose();
			imgWarning = null;
		}
		if(imgConflicting != null) {
			imgConflicting.dispose();
			imgConflicting = null;
		}
		if(imgIgnored != null) {
			imgIgnored.dispose();
			imgIgnored = null;
		}
		if(colorApplied != null) {
			colorApplied.dispose();
			colorApplied = null;
		}
		if(colorIgnored != null) {
			colorIgnored.dispose();
			colorIgnored = null;
		}
		super.dispose();
	}
}
