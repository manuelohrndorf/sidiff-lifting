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

public class ColumnRefinementLevel {

	public ColumnRefinementLevel(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn refinementLevelColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(refinementLevelColumn.getColumn(), new ColumnPixelData(80));
		refinementLevelColumn.getColumn().setText("Refinement");
		refinementLevelColumn.getColumn().setResizable(false);
		refinementLevelColumn.getColumn().setAlignment(SWT.CENTER);
		refinementLevelColumn.getColumn().setToolTipText("Refinement level of recognition rule");

		// LabelProvider for refinementColumn
		refinementLevelColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				// Get Henshin semantic change set refinement attribute
				cell.setText("" + RuleBaseItemWrapper.getRefinementLevel((RuleBaseItem) cell.getElement()));
			}
		});
	}
}
