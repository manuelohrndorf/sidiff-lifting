package org.sidiff.editrule.rulebase.ui.editor.columns;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;

public class AbstractRuleBaseColumn implements IRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn column, TableColumnLayout layout) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getStyle() {
		return SWT.None;
	}

}
