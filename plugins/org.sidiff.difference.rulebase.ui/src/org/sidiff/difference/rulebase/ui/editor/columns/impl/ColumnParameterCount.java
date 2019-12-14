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

public class ColumnParameterCount extends AbstractRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn numberOfParamsColumn, TableColumnLayout layout) {
		layout.setColumnData(numberOfParamsColumn.getColumn(), new ColumnPixelData(100));

		numberOfParamsColumn.getColumn().setText("Parameters");
		numberOfParamsColumn.getColumn().setResizable(false);
		numberOfParamsColumn.getColumn().setAlignment(SWT.CENTER);
		numberOfParamsColumn.getColumn().setToolTipText("Number of parameters of recognition rule");

		numberOfParamsColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return Integer.toString(RecognitionRuleItemUtil.getNumberOfParams((RuleBaseItem)element));
			}
		});
	}
}
