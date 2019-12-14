package org.sidiff.editrule.rulebase.ui.editor.columns.impl;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.builder.EditRuleBaseWrapper;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.AbstractRuleBaseColumn;

public class ColumnConflictCount extends AbstractRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn potConColumn, TableColumnLayout layout) {
		layout.setColumnData(potConColumn.getColumn(), new ColumnPixelData(100));

		potConColumn.getColumn().setText("Conflicts");
		potConColumn.getColumn().setResizable(false);
		potConColumn.getColumn().setAlignment(SWT.CENTER);
		potConColumn.getColumn().setToolTipText("Potential conflicts (related to this rulebase)");

		final EditRuleBaseWrapper rbManager = editor.getRulebaseWrapper();
		potConColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return Integer.toString(rbManager.getConflicts(((RuleBaseItem)element).getEditRule()).size());
			}
		});
	}
}
