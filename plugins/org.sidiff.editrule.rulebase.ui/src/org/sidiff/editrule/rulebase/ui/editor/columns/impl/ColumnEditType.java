package org.sidiff.editrule.rulebase.ui.editor.columns.impl;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.IRuleBaseColumn;
import org.sidiff.editrule.rulebase.util.EditRuleItemUtil;

public class ColumnEditType implements IRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn editUnitTypeColumn, TableColumnLayout layout) {
		layout.setColumnData(editUnitTypeColumn.getColumn(), new ColumnPixelData(100));
		
		editUnitTypeColumn.getColumn().setText("ER-Type");
		editUnitTypeColumn.getColumn().setResizable(false);
		editUnitTypeColumn.getColumn().setToolTipText("Edit rule main unit type");

		// LabelProvider for unitTypeColumn
		editUnitTypeColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				RuleBaseItem item = (RuleBaseItem) cell.getElement();
				cell.setText(EditRuleItemUtil.getDisplayERType(item));
			}
		});
	}
}
