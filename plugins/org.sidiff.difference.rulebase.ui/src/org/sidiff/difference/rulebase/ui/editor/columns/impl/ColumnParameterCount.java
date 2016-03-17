package org.sidiff.difference.rulebase.ui.editor.columns.impl;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.sidiff.difference.rulebase.util.RecognitionRuleItemUtil;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.IRuleBaseColumn;

public class ColumnParameterCount implements IRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn numberOfParamsColumn, TableColumnLayout layout) {
		layout.setColumnData(numberOfParamsColumn.getColumn(), new ColumnPixelData(80));
		
		numberOfParamsColumn.getColumn().setText("Number of Params");
		numberOfParamsColumn.getColumn().setResizable(false);
		numberOfParamsColumn.getColumn().setAlignment(SWT.CENTER);
		numberOfParamsColumn.getColumn().setToolTipText("Number of parameters recognition rule");

		// LabelProvider for numberOfParamsColumn
		numberOfParamsColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				// Get Henshin semantic change set number of acs attribute
				cell.setText("" + RecognitionRuleItemUtil.getNumberOfParams((RuleBaseItem) cell.getElement()));
			}
		});
	}
}
