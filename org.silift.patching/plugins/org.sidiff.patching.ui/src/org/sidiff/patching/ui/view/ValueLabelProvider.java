package org.sidiff.patching.ui.view;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.patching.IPatchCorrespondence;
import org.sidiff.patching.report.PatchReport;
import org.sidiff.patching.report.PatchReport.Status;
import org.sidiff.patching.report.PatchReport.Type;
import org.sidiff.patching.report.ReportEntry;
import org.sidiff.patching.ui.Activator;

public class ValueLabelProvider extends ColumnLabelProvider {

	private IPatchCorrespondence correspondence;
	private boolean showReliabilities = false;
	private PatchReport report;
	private Collection<OperationInvocation> operationInvocations;
	
	private Image error = Activator.getImageDescriptor("fatalerror_obj_16x16.gif").createImage();
	private Image warning = Activator.getImageDescriptor("warning_16x16.gif").createImage();

	@Override
	public String getText(Object element) {
		if (element instanceof ObjectParameterBinding) {
			ObjectParameterBinding substitution = (ObjectParameterBinding) element;
			EObject actualA = substitution.getActualA();
			if (actualA == null) {
				return Util.getName(substitution.getActualB());
			}
			EObject eObject = correspondence.getCorrespondence(actualA);
			if (eObject != null) {
				float reliability = correspondence.getReliability(actualA, eObject);
				//return Util.getName(eObject) + " (" + reliability + ")";
				return Util.getName(eObject) + (showReliabilities? " (" + reliability + ")":"");
			}
			String caution = "(Missing object: ";
			caution += Util.getName(actualA);
			return caution + ")";
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
		if(element instanceof ObjectParameterBinding && 
				((ObjectParameterBinding)element).getFormalParameter().getDirection() == ParameterDirection.IN){
			EObject actualA = ((ObjectParameterBinding) element).getActualA();
			if (actualA == null) return null;
			EObject eObject = correspondence.getCorrespondence(actualA);
			if(eObject == null)
				return error;
			if(correspondence.isModified(eObject))
				return warning;
		}
		return null;
	}
	
	@Override
	public String getToolTipText(Object element) {
		if(element instanceof ObjectParameterBinding && 
				((ObjectParameterBinding)element).getFormalParameter().getDirection() == ParameterDirection.IN){
			EObject actualA = ((ObjectParameterBinding) element).getActualA();
			if (actualA == null) return null;
			EObject eObject = correspondence.getCorrespondence(actualA);
			if(eObject == null)
				return "Error: Missing object";
			if(correspondence.isModified(eObject))
				return "Warning: The object has been modified";
		}
		return null;
	}
	
	@Override
	public Color getForeground(Object element) {
		if (element instanceof ObjectParameterBinding) {
			ObjectParameterBinding objectParameterBinding = (ObjectParameterBinding) element;
			boolean isIn = objectParameterBinding.getFormalParameter().getDirection() == ParameterDirection.IN;
			EObject actualA = objectParameterBinding.getActualA();
			if (actualA != null) {
			EObject eObject = correspondence.getCorrespondence(actualA);
				if (isIn && eObject == null) {
					Display display = Activator.getDefault().getWorkbench().getDisplay();
					return new Color(display, 200, 0, 0);
				}
			}
			if (operationInvocations != null) {
				for (OperationInvocation invocation : operationInvocations) {
					for (ReportEntry re : report.getEntries(invocation,
							Type.VALIDATION, Status.FAILED)) {
						for (ParameterBinding binding : invocation
								.getParameterBindings()) {
							if (binding == objectParameterBinding) {
								EObject obj = objectParameterBinding.getActualB();
								EStructuralFeature feature = obj.eClass().getEStructuralFeature("name");
								if(re.getDescription().contains("'"+obj.eGet(feature)+"'")){
									Display display = Activator.getDefault().getWorkbench().getDisplay();
									return new Color(display, 200, 0, 0);
								}
							}
						}
					}
				}
				
			}
		}
		return super.getForeground(element);
	}

	public void setShowReliablities(boolean b){
		showReliabilities = b;
	}
	
	public void setCorrespondence(IPatchCorrespondence correspondence) {
		this.correspondence = correspondence;
	}
	
	public void setReport(PatchReport report) {
		this.report = report;
	}
	
	public void setOperationInvocations(Collection<OperationInvocation> operationInvocation){
		this.operationInvocations = operationInvocation;
	}
}
