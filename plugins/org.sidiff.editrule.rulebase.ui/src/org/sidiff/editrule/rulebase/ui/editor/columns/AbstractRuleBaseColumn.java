package org.sidiff.editrule.rulebase.ui.editor.columns;

import org.eclipse.swt.SWT;

public abstract class AbstractRuleBaseColumn implements IRuleBaseColumn {

	@Override
	public int getStyle() {
		return SWT.None;
	}
}
