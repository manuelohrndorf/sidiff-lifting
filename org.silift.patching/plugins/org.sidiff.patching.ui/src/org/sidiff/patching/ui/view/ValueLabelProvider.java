package org.sidiff.patching.ui.view;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.patching.IPatchCorrespondence;
import org.sidiff.patching.ui.Activator;

public class ValueLabelProvider extends ColumnLabelProvider {

	private IPatchCorrespondence correspondence;

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
				return Util.getName(eObject) + " (" + reliability + ")";
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
		}
		return super.getForeground(element);
	}

	public void setCorrespondence(IPatchCorrespondence correspondence) {
		this.correspondence = correspondence;
	}
	
}
