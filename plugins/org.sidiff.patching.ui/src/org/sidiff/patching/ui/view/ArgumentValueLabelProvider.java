package org.sidiff.patching.ui.view;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.sidiff.common.emf.ecore.NameUtil;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.editrule.rulebase.ParameterDirection;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.arguments.ObjectArgumentWrapper;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.ui.internal.PatchingUiPlugin;

public class ArgumentValueLabelProvider extends ColumnLabelProvider {

	private OperationInvocationWrapper operationInvocationWrapper;

	private boolean showReliabilities = false;
	private boolean showQualifiedArgumentNames = false;

	private IArgumentManager argumentManager;

	private final Image ERROR = PatchingUiPlugin.getImageDescriptor("fatalerror_obj_16x16.gif").createImage();
	private final Image WARNING = PatchingUiPlugin.getImageDescriptor("warning_16x16.gif").createImage();

	public void init(OperationInvocationWrapper operationInvocationWrapper) {
		this.operationInvocationWrapper = operationInvocationWrapper;
		this.argumentManager = operationInvocationWrapper.getArgumentManager();
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ObjectParameterBinding) {
			ObjectParameterBinding binding = (ObjectParameterBinding) element;
			ObjectArgumentWrapper argument = (ObjectArgumentWrapper) operationInvocationWrapper.getActualArgument(binding);

			if (operationInvocationWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				// Already executed: Show execution args
				EObject object = (EObject)operationInvocationWrapper.getExecutionArgument(binding);
				String res = "[" + (showQualifiedArgumentNames ? NameUtil.getQualifiedArgumentName(object) : NameUtil.getName(object)) + "]";
				if (showReliabilities && isInParameter(binding)){
					float reliability = argumentManager.getReliability(binding, object);
					res += " (" + reliability + ")";
				}
				return res;
			}
			// Not executed: Show current state of the argument selection
			if (argument.isResolved()) {
				EObject object = argument.getTargetObject();
				if (isInParameter(binding)){
					float reliability = argumentManager.getReliability(binding, object);
					return (showQualifiedArgumentNames ? NameUtil.getQualifiedArgumentName(object) : NameUtil.getName(object))
							+ (showReliabilities ? " (" + reliability + ")" : "");
				}
				return showQualifiedArgumentNames ? NameUtil.getQualifiedArgumentName(object) : NameUtil.getName(object);
			}
			EObject object = argument.getProxyObject();
			if (isInParameter(binding)) {
				return "(Missing object: " + (showQualifiedArgumentNames ? NameUtil.getQualifiedArgumentName(object) : NameUtil.getName(object)) + ")";
			}
			return "(Not yet created: " + (showQualifiedArgumentNames ? NameUtil.getQualifiedArgumentName(object) : NameUtil.getName(object)) + ")";

		} else if (element instanceof ValueParameterBinding) {
			ValueParameterBinding binding = (ValueParameterBinding) element;
			if (operationInvocationWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				Object actual = operationInvocationWrapper.getExecutionArgument(binding);
				if (actual != null) {
					return actual.toString();
				}
				return "(Unknown Value)";
			}
			String actual = binding.getActual();
			if (actual != null) {
				return actual;
			}
			return "(Unknown Value)";
		}

		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof ObjectParameterBinding
				&& ((ObjectParameterBinding) element).getFormalParameter().getDirection() == ParameterDirection.IN) {

			ObjectParameterBinding binding = (ObjectParameterBinding) element;

			if (operationInvocationWrapper.getStatus() != OperationInvocationStatus.PASSED) {
				ObjectArgumentWrapper argument = (ObjectArgumentWrapper) argumentManager.getArgument(binding);
				if (isInParameter(binding)) {
					if (!argument.isResolved()) {
						return ERROR;
					}
					if (argument.isModified()) {
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

			if (operationInvocationWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				return "TODO: getTooltipText param of executed operation";
			}
			ObjectArgumentWrapper argument = (ObjectArgumentWrapper) argumentManager.getArgument(binding);
			if (argument.isResolved()) {
				if (argumentManager.isModified(argument.getTargetObject())) {
					return "Warning: The object has been modified";
				}
			} else {
				if (isInParameter(binding)) {
					return "Error: Missing object";
				}
				return "Object has not yet been created";
			}
		}
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof ParameterBinding) {
			if (operationInvocationWrapper.getStatus() == OperationInvocationStatus.PASSED) {
				return new Color(Display.getDefault(), 150, 150, 150);
			}
			if(element instanceof ObjectParameterBinding){
				ObjectParameterBinding objBinding = (ObjectParameterBinding) element;
				ObjectArgumentWrapper argument = (ObjectArgumentWrapper) argumentManager.getArgument(objBinding);
				if (isInParameter(objBinding) && !argument.isResolved()) {
					return new Color(Display.getDefault(), 200, 0, 0);
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

	public boolean isShowQualifiedArgumentName() {
		return showQualifiedArgumentNames;
	}

	public void setShowQualifiedArgumentName(boolean showQualifiedArgumentName) {
		this.showQualifiedArgumentNames = showQualifiedArgumentName;
	}

	@Override
	public void dispose() {
		ERROR.dispose();
		WARNING.dispose();

		super.dispose();
	}
}
