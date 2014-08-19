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

public class ColumnACCount {

	public ColumnACCount(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn numberOfACsColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(numberOfACsColumn.getColumn(), new ColumnPixelData(80));
		numberOfACsColumn.getColumn().setText("Number of ACs");
		numberOfACsColumn.getColumn().setResizable(false);
		numberOfACsColumn.getColumn().setAlignment(SWT.CENTER);
		numberOfACsColumn.getColumn().setToolTipText("Number of application conditions in recognition rule");

		// LabelProvider for numberOfACsColumn
		numberOfACsColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				// Get Henshin semantic change set number of acs attribute
				cell.setText("" + RuleBaseItemWrapper.getNumberOfACs((RuleBaseItem) cell.getElement()));
			}
		});
	}
}
