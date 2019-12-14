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

public class ColumnACCount extends AbstractRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn numberOfACsColumn, TableColumnLayout layout) {
		layout.setColumnData(numberOfACsColumn.getColumn(), new ColumnPixelData(100));

		numberOfACsColumn.getColumn().setText("ACs");
		numberOfACsColumn.getColumn().setResizable(false);
		numberOfACsColumn.getColumn().setAlignment(SWT.CENTER);
		numberOfACsColumn.getColumn().setToolTipText("Number of application conditions in recognition rule");

		numberOfACsColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return Integer.toString(RecognitionRuleItemUtil.getNumberOfACs((RuleBaseItem)element));
			}
		});
	}
}
