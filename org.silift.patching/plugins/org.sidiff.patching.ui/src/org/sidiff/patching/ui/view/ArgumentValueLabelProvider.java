package org.sidiff.patching.ui.view;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.patching.arguments.ArgumentWrapper;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.operation.OperationManager;
import org.sidiff.patching.ui.Activator;

public class ArgumentValueLabelProvider extends ColumnLabelProvider {

	private IArgumentManager argumentManager;
	private OperationManager statusManager;
	private boolean showReliabilities = false;

	private final Image ERROR = Activator.getImageDescriptor("fatalerror_obj_16x16.gif").createImage();
	private final Image WARNING = Activator.getImageDescriptor("warning_16x16.gif").createImage();

	public void init(IArgumentManager argumentManager, OperationManager statusManager) {
		this.argumentManager = argumentManager;
		this.statusManager = statusManager;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ObjectParameterBinding) {
			ObjectParameterBinding binding = (ObjectParameterBinding) element;
			OperationInvocation op = (OperationInvocation) binding.eContainer();
			OperationInvocationWrapper opWrapper = statusManager.getStatusWrapper(op);
			ArgumentWrapper argument = argumentManager.getArgument(binding);
			
			if (opWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				// Already executed: Show execution parameters
				if (argument.isResolved()) {
					if (isInParameter(binding)){
						float reliability = argumentManager.getReliability(binding, argument.getTargetObject());
						return Util.getName(argument.getTargetObject())
								+ (showReliabilities ? " (" + reliability + ")" : "");
					}else{
						return Util.getName(argument.getTargetObject());
					} 
				} else {
					if (isInParameter(binding)){
						return "(" + Util.getName((EObject) opWrapper.getInvocationArgument(binding)) + ")";
					} else {
						return "(" + Util.getName(argument.getProxyObject()) + ")";
					}	
				}
			} else {
				// Not executed: Show current state of the argument selection	
				if (argument.isResolved()) {
					if (isInParameter(binding)){
						float reliability = argumentManager.getReliability(binding, argument.getTargetObject());
						return Util.getName(argument.getTargetObject())
								+ (showReliabilities ? " (" + reliability + ")" : "");
					}else{
						return Util.getName(argument.getTargetObject());
					}
				} else {
					if (isInParameter(binding)) {
						return "(Missing object: " + Util.getName(argument.getProxyObject()) + ")";
					} else {
						return "(Not yet created: " + Util.getName(argument.getProxyObject()) + ")";
					}
				}
			}

		} else if (element instanceof ValueParameterBinding) {
			ValueParameterBinding binding = (ValueParameterBinding) element;
			OperationInvocation op = (OperationInvocation) binding.eContainer();
			OperationInvocationWrapper opWrapper = statusManager.getStatusWrapper(op);
			
			if (opWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				Object actual = opWrapper.getInvocationArgument(binding);
				if (actual != null) {
					return actual.toString();
				} else {
					return "(Unknown Value)";
				}
			} else{
				String actual = binding.getActual();
				if (actual != null) {
					return actual;
				} else {
					return "(Unknown Value)";
				}
			}	
		}

		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof ObjectParameterBinding
				&& ((ObjectParameterBinding) element).getFormalParameter().getDirection() == ParameterDirection.IN) {

			ObjectParameterBinding binding = (ObjectParameterBinding) element;
			OperationInvocation op = (OperationInvocation) binding.eContainer();
			OperationInvocationWrapper opWrapper = statusManager.getStatusWrapper(op);

			if (opWrapper.getStatus() != OperationInvocationStatus.PASSED) {
				ArgumentWrapper argument = argumentManager.getArgument(binding);
				if (isInParameter(binding)) {
					if (!argument.isResolved()) {
						return ERROR;
					}
					if (argument.isResolved() && argumentManager.isModified(argument.getTargetObject())) {
						return WARNING;
					}
				}
			}
		}

		return null;
	}

	@Override
	public String getToolTipText(Object element) {
		if (element instanceof ObjectParameterBinding
				&& ((ObjectParameterBinding) element).getFormalParameter().getDirection() == ParameterDirection.IN) {

			ObjectParameterBinding binding = (ObjectParameterBinding) element;
			OperationInvocation op = (OperationInvocation) binding.eContainer();
			OperationInvocationWrapper opWrapper = statusManager.getStatusWrapper(op);

			if (opWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				return "TODO: getTooltipText param of executed operation";
			} else {
				ArgumentWrapper argument = argumentManager.getArgument(binding);
				if (argument.isResolved()) {
					if (argumentManager.isModified(argument.getTargetObject())) {
						return "Warning: The object has been modified";
					}
				} else {
					if (isInParameter(binding)) {
						return "Error: Missing object";
					} else {
						return "Object has not yet been created";
					}
				}
			}
		}
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof ObjectParameterBinding) {
			Display display = Activator.getDefault().getWorkbench().getDisplay();
			ObjectParameterBinding binding = (ObjectParameterBinding) element;
			OperationInvocation op = (OperationInvocation) binding.eContainer();
			OperationInvocationWrapper opWrapper = statusManager.getStatusWrapper(op);

			if (opWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				return new Color(display, 150, 150, 150);
			} else {
				ArgumentWrapper argument = argumentManager.getArgument(binding);
				if (isInParameter(binding) && !argument.isResolved()) {
					return new Color(display, 200, 0, 0);
				}
			}
		}

		return super.getForeground(element);
	}

	public void setShowReliablities(boolean b) {
		showReliabilities = b;
	}

	private boolean isInParameter(ObjectParameterBinding binding) {
		return binding.getFormalParameter().getDirection() == ParameterDirection.IN;
	}
}
