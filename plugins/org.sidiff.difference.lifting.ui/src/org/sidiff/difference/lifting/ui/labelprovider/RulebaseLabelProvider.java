package org.sidiff.difference.lifting.ui.labelprovider;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.sidiff.editrule.rulebase.view.basic.IBasicRuleBase;

public class RulebaseLabelProvider extends ColumnLabelProvider {

	@Override
	public String getText(Object element) {
		if(element instanceof IBasicRuleBase) {
			return ((IBasicRuleBase)element).getName();
		}
		return super.getText(element);
	}
}
