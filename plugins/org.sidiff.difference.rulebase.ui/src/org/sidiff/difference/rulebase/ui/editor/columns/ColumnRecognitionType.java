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
import org.sidiff.difference.rulebase.wrapper.RuleBaseItemWrapper;

public class ColumnRecognitionType {

	public ColumnRecognitionType(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn unitTypeColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(unitTypeColumn.getColumn(), new ColumnPixelData(100));
		unitTypeColumn.getColumn().setText("RR-Type");
		unitTypeColumn.getColumn().setResizable(false);
		unitTypeColumn.getColumn().setToolTipText("Recognition rule main unit type");

		// LabelProvider for unitTypeColumn
		unitTypeColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				RuleBaseItem item = (RuleBaseItem) cell.getElement();
				cell.setText(RuleBaseItemWrapper.getDisplayRRType(item));
			}
		});
	}
}
