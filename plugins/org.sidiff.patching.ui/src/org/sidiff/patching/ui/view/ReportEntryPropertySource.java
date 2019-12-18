package org.sidiff.patching.ui.view;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.patching.report.OperationExecutionEntry;
import org.sidiff.patching.report.ReportEntry;
import org.sidiff.patching.report.ValidationEntry;
import org.sidiff.patching.validation.IValidationError;

public class ReportEntryPropertySource implements IPropertySource {

	private final ReportEntry reportEntry;

	public ReportEntryPropertySource(ReportEntry reportEntry) {
		this.reportEntry = reportEntry;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		if (reportEntry instanceof OperationExecutionEntry) {
			return new IPropertyDescriptor[] {
				new PropertyDescriptor("description", "Description"),
				new PropertyDescriptor("kind", "Kind"),
				new PropertyDescriptor("inArgs", "Input Arguments"),
				new PropertyDescriptor("outArgs", "Output Arguments"),
				new PropertyDescriptor("error", "Error")
			};
		} else if (reportEntry instanceof ValidationEntry) {
			ValidationEntry validationEntry = (ValidationEntry)reportEntry;
			Object currentValidationErrors[] = validationEntry.getCurrentValidationErrors().toArray();
			PropertyDescriptor[] descriptors = new PropertyDescriptor[currentValidationErrors.length];
			for (int i = 0; i < currentValidationErrors.length; i++) {
				IValidationError error = (IValidationError)currentValidationErrors[i];
				descriptors[i] = new PropertyDescriptor("err" + i, error.getSource());
				if (validationEntry.getNewValidationErrors().contains(error)) {
					descriptors[i].setCategory("New Validation Errors");
				} else if (validationEntry.getRemovedValidationErrors().contains(error)) {
					descriptors[i].setCategory("Removed Validation Errors");
				} else {
					descriptors[i].setCategory("Remaining Validation Errors");
				}
			}
			return descriptors;
		} else {
			return new IPropertyDescriptor[] {
				new PropertyDescriptor("description", "Description")
			};
		}
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("description")) {
			return reportEntry.getDescription();
		} else if (reportEntry instanceof OperationExecutionEntry) {
			if (id.equals("kind")) {
				return ((OperationExecutionEntry)reportEntry).getKind().name();
			} else if (id.equals("inArgs")) {
				return mapToString(((OperationExecutionEntry)reportEntry).getInArgs());
			} else if (id.equals("outArgs")) {
				return mapToString(((OperationExecutionEntry)reportEntry).getOutArgs());
			} else if (id.equals("error")) {
				if (((OperationExecutionEntry)reportEntry).getError() != null) {
					return ((OperationExecutionEntry)reportEntry).getError().getMessage();					
				}
			}
		} else if (reportEntry instanceof ValidationEntry) {
			Object errors[] = ((ValidationEntry)reportEntry).getCurrentValidationErrors().toArray();
			for (int i = 0; i < errors.length; i++) {
				if (id.equals("err" + i)) {
					return ((IValidationError)errors[i]).getMessage();					
				}
			}
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
	}

	private static String mapToString(Map<ParameterBinding, Object> map) {
		String string = "";
		int it = 0;
		if (map != null && !map.isEmpty()) {
			for (ParameterBinding binding : map.keySet()) {
				string += binding.getFormalName() + ": ";
				if (binding instanceof ObjectParameterBinding) {
					EObject obj = (EObject)map.get(binding);
					EStructuralFeature feature = obj.eClass().getEStructuralFeature("name");
					if (feature != null) {
						string += obj.eGet(feature);
					}
				} else {
					string += map.get(binding);
				}
				if (!(it == map.size() - 1)) {
					string += ", ";
				}
				it++;
			}
		}
		return string;
	}

}
