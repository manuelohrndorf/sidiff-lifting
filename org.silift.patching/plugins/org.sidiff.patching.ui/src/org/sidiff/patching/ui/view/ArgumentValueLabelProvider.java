package org.sidiff.patching.ui.view;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.patching.ArgumentWrapper;
import org.sidiff.patching.IArgumentManager;
import org.sidiff.patching.report.PatchReport;
import org.sidiff.patching.report.PatchReport.Status;
import org.sidiff.patching.report.PatchReport.Type;
import org.sidiff.patching.report.ReportEntry;
import org.sidiff.patching.ui.Activator;

public class ArgumentValueLabelProvider extends ColumnLabelProvider {

	private IArgumentManager argumentManager;
	private boolean showReliabilities = false;
	private PatchReport report;

	private final Image ERROR = Activator.getImageDescriptor("fatalerror_obj_16x16.gif").createImage();
	private final Image WARNING = Activator.getImageDescriptor("warning_16x16.gif").createImage();

	public void init(IArgumentManager argumentManager, PatchReport report) {
		this.argumentManager = argumentManager;
		this.report = report;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ObjectParameterBinding) {
			ObjectParameterBinding substitution = (ObjectParameterBinding) element;
			ArgumentWrapper argument = argumentManager.getArgument(substitution);
			if (argument.isResolved()) {
				float reliability = argumentManager.getReliability(substitution, argument.getTargetObject());
				return Util.getName(argument.getTargetObject()) + (showReliabilities ? " (" + reliability + ")" : "");
			} else {
				if (isInParameter(substitution)) {
					return "(Missing object: " + Util.getName(argument.getProxyObject()) + ")";
				} else {
					return "(Not yet created: " + Util.getName(argument.getProxyObject()) + ")";
				}
			}
			
		} else if (element instanceof ValueParameterBinding) {
			ValueParameterBinding substitution = (ValueParameterBinding) element;
			String actual = substitution.getActual();
			if (actual != null) {
				return actual;
			} else {
				return "(Unknown Value)";
			}
		}

		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof ObjectParameterBinding
				&& ((ObjectParameterBinding) element).getFormalParameter().getDirection() == ParameterDirection.IN) {

			ObjectParameterBinding binding = (ObjectParameterBinding) element;
			ArgumentWrapper argument = argumentManager.getArgument(binding);
			if (isInParameter(binding)) {
				if (!argument.isResolved()){
					return ERROR;
				}
				if (argument.isResolved() && argumentManager.isModified(argument.getTargetObject())) {
					return WARNING;
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
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof ObjectParameterBinding) {
			ObjectParameterBinding binding = (ObjectParameterBinding) element;
			ArgumentWrapper argument = argumentManager.getArgument(binding);
			if (isInParameter(binding) && !argument.isResolved()) {
				Display display = Activator.getDefault().getWorkbench().getDisplay();
				return new Color(display, 200, 0, 0);
			}

			// TODO:
			// Sometimes, we can trace validation errors to specific arguments
			// But does this make sense?
			OperationInvocation invocation = (OperationInvocation) binding.eContainer();
			for (ReportEntry re : report.getEntries(invocation, Type.VALIDATION, Status.FAILED)) {
				EObject obj = binding.getActualB();
				EStructuralFeature feature = obj.eClass().getEStructuralFeature("name");
				if (re.getDescription().contains("'" + obj.eGet(feature) + "'")) {
					Display display = Activator.getDefault().getWorkbench().getDisplay();
					return new Color(display, 200, 200, 0);
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
