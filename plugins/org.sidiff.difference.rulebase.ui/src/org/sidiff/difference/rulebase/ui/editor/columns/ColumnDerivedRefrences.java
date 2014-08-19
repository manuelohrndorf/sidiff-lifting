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

public class ColumnDerivedRefrences {

	public ColumnDerivedRefrences(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn derivedRefColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(derivedRefColumn.getColumn(), new ColumnPixelData(55));
		derivedRefColumn.getColumn().setText("uses D.R.");
		derivedRefColumn.getColumn().setResizable(false);
		derivedRefColumn.getColumn().setAlignment(SWT.CENTER);
		derivedRefColumn.getColumn().setToolTipText("Only for testing purposes");

		// LabelProvider for versionColumn
		derivedRefColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((RuleBaseItem) cell.getElement()).getEditRule().isUseDerivedFeatures()+"");
			}
		});
	}
}
