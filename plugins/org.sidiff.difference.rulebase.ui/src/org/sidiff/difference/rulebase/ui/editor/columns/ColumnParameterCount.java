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

public class ColumnParameterCount {

	public ColumnParameterCount(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn numberOfParamsColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(numberOfParamsColumn.getColumn(), new ColumnPixelData(80));
		numberOfParamsColumn.getColumn().setText("Number of Params");
		numberOfParamsColumn.getColumn().setResizable(false);
		numberOfParamsColumn.getColumn().setAlignment(SWT.CENTER);
		numberOfParamsColumn.getColumn().setToolTipText("Number of parameters recognition rule");

		// LabelProvider for numberOfParamsColumn
		numberOfParamsColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				// Get Henshin semantic change set number of acs attribute
				cell.setText("" + RuleBaseItemWrapper.getNumberOfParams((RuleBaseItem) cell.getElement()));
			}
		});
	}
}
