package org.sidiff.difference.rulebase.ui.editor.columns.impl;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.sidiff.difference.rulebase.util.RecognitionRuleItemUtil;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.AbstractRuleBaseColumn;

public class ColumnRefinementLevel extends AbstractRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn refinementLevelColumn, TableColumnLayout layout) {
		layout.setColumnData(refinementLevelColumn.getColumn(), new ColumnPixelData(100));

		refinementLevelColumn.getColumn().setText("Refinement");
		refinementLevelColumn.getColumn().setResizable(false);
		refinementLevelColumn.getColumn().setAlignment(SWT.CENTER);
		refinementLevelColumn.getColumn().setToolTipText("Refinement level of recognition rule");

		refinementLevelColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return Integer.toString(RecognitionRuleItemUtil.getRefinementLevel((RuleBaseItem)element));
			}
		});
	}
}
