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

public class ColumnRefinementLevel implements IRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn refinementLevelColumn, TableColumnLayout layout) {
		layout.setColumnData(refinementLevelColumn.getColumn(), new ColumnPixelData(100));
		
		refinementLevelColumn.getColumn().setText("Refinement");
		refinementLevelColumn.getColumn().setResizable(false);
		refinementLevelColumn.getColumn().setAlignment(SWT.CENTER);
		refinementLevelColumn.getColumn().setToolTipText("Refinement level of recognition rule");

		// LabelProvider for refinementColumn
		refinementLevelColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				// Get Henshin semantic change set refinement attribute
				cell.setText("" + RecognitionRuleItemUtil.getRefinementLevel((RuleBaseItem) cell.getElement()));
			}
		});
	}
}
