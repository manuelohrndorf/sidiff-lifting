package org.sidiff.patching.ui.view;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.patching.report.OperationExecutionEntry;
import org.sidiff.patching.report.ReportEntry;

public class ReportEntryPropertySource implements IPropertySource {

	private final ReportEntry reportEntry;
	
	public ReportEntryPropertySource(ReportEntry reportEntry){
		this.reportEntry = reportEntry;
	}
	@Override
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// TODO Auto-generated method stub
		if(reportEntry instanceof OperationExecutionEntry){
			return new IPropertyDescriptor[]{
				new TextPropertyDescriptor("description", "Description"),
				new TextPropertyDescriptor("kind", "Kind"),
				new TextPropertyDescriptor("inArgs", "Input Arguments"),
				new TextPropertyDescriptor("outArgs", "Output Arguments"),
				new TextPropertyDescriptor("error", "Error")
			};
		}else{
			return new IPropertyDescriptor[]{
				new TextPropertyDescriptor("description", "Description") };
		}
	}

	@Override
	public Object getPropertyValue(Object id) {
		String output = "";
		if (id.equals("description")) {
			return reportEntry.getDescription();
		}
		if(reportEntry instanceof OperationExecutionEntry){
			if(id.equals("kind")){
				return ((OperationExecutionEntry)reportEntry).getKind();
			}
			if(id.equals("inArgs")){
				output = mapToString(((OperationExecutionEntry)reportEntry).getInArgs());
	
			}
			if(id.equals("outArgs")){
				output = mapToString(((OperationExecutionEntry)reportEntry).getOutArgs());
			}
			if(id.equals("error")){
				//output = ((OperationExecutionEntry)reportEntry).getError().toString();
			}
		}
		return output;
	}

	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub

	}
	
	private String mapToString(Map<ParameterBinding, Object> map){
		String string = "";
		int it = 0;
		for(ParameterBinding binding : map.keySet()){
			string += binding.getFormalName() +": ";
			if(binding instanceof ObjectParameterBinding){
				EObject obj = (EObject)map.get(binding);
				EStructuralFeature feature = obj.eClass().getEStructuralFeature("name");
				if(feature != null) {
					string += obj.eGet(feature);
				}									
			}else{
				string += map.get(binding);
			}
			if(!(it == map.size()-1)){
				string += ", ";
			}
			it++;
		}
		return string;
	}

}
