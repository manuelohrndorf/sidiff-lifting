package org.sidiff.editrule.rulebase.ui.editor.columns.impl;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.AbstractRuleBaseColumn;
import org.sidiff.editrule.rulebase.util.EditRuleItemUtil;

public class ColumnEditType extends AbstractRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn editUnitTypeColumn, TableColumnLayout layout) {
		layout.setColumnData(editUnitTypeColumn.getColumn(), new ColumnPixelData(100));
		
		editUnitTypeColumn.getColumn().setText("Rule Type");
		editUnitTypeColumn.getColumn().setResizable(false);
		editUnitTypeColumn.getColumn().setToolTipText("Edit rule main unit type");

		editUnitTypeColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return EditRuleItemUtil.getDisplayERType((RuleBaseItem)element);
			}
		});
	}
}
