package org.sidiff.difference.rulebase.ui.editor.columns.impl;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.sidiff.difference.rulebase.util.RecognitionRuleItemUtil;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.IRuleBaseColumn;

public class ColumnRecognitionType implements IRuleBaseColumn {

	@Override
	public void createColumn(final RulebaseEditor editor, TableViewerColumn unitTypeColumn, TableColumnLayout layout) {
		layout.setColumnData(unitTypeColumn.getColumn(), new ColumnPixelData(100));
		
		unitTypeColumn.getColumn().setText("RR-Type");
		unitTypeColumn.getColumn().setResizable(false);
		unitTypeColumn.getColumn().setToolTipText("Recognition rule main unit type");

		// LabelProvider for unitTypeColumn
		unitTypeColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				RuleBaseItem item = (RuleBaseItem) cell.getElement();
				cell.setText(RecognitionRuleItemUtil.getDisplayRRType(item));
			}
		});
	}
}
