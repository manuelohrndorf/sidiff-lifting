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

public class ColumnACCount implements IRuleBaseColumn {

	@Override
	public void createColumn(final RulebaseEditor editor, TableViewerColumn numberOfACsColumn, TableColumnLayout layout) {
		layout.setColumnData(numberOfACsColumn.getColumn(), new ColumnPixelData(80));
		
		numberOfACsColumn.getColumn().setText("Number of ACs");
		numberOfACsColumn.getColumn().setResizable(false);
		numberOfACsColumn.getColumn().setAlignment(SWT.CENTER);
		numberOfACsColumn.getColumn().setToolTipText("Number of application conditions in recognition rule");

		// LabelProvider for numberOfACsColumn
		numberOfACsColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				// Get Henshin semantic change set number of acs attribute
				cell.setText("" + RecognitionRuleItemUtil.getNumberOfACs((RuleBaseItem) cell.getElement()));
			}
		});
	}
}
