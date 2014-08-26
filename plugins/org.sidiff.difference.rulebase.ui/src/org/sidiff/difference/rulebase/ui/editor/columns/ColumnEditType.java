package org.sidiff.difference.rulebase.ui.editor.columns;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.difference.rulebase.wrapper.RuleBaseItemInfo;

public class ColumnEditType {

	public ColumnEditType(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn editUnitTypeColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(editUnitTypeColumn.getColumn(), new ColumnPixelData(100));
		editUnitTypeColumn.getColumn().setText("ER-Type");
		editUnitTypeColumn.getColumn().setResizable(false);
		editUnitTypeColumn.getColumn().setToolTipText("Edit rule main unit type");

		// LabelProvider for unitTypeColumn
		editUnitTypeColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				RuleBaseItem item = (RuleBaseItem) cell.getElement();
				cell.setText(RuleBaseItemInfo.getDisplayERType(item));
			}
		});
	}
}
