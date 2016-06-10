package org.sidiff.editrule.rulebase.ui.editor.columns.impl;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.builder.EditRuleBaseWrapper;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.IRuleBaseColumn;

public class ColumnDependenyCount implements IRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn potDepColumn, TableColumnLayout layout) {
		layout.setColumnData(potDepColumn.getColumn(), new ColumnPixelData(100));

		potDepColumn.getColumn().setText("Dependencies");
		potDepColumn.getColumn().setResizable(false);
		potDepColumn.getColumn().setAlignment(SWT.CENTER);
		potDepColumn.getColumn().setToolTipText("Incoming source and target potential dependencies (related to this rulebase)");

		// LabelProvider for versionColumn
		final EditRuleBaseWrapper rbManager = editor.getRulebaseWrapper();
		
		potDepColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(rbManager.getIncomingDependencies(((RuleBaseItem) cell.getElement()).getEditRule()).size() + "");
			}
		});
	}
}
