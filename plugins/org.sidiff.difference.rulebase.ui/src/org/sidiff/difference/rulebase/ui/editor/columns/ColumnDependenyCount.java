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
import org.sidiff.difference.rulebase.wrapper.RuleBaseWrapper;

public class ColumnDependenyCount {

	public ColumnDependenyCount(final RulebaseEditor editor) {
		final RuleBaseWrapper rbManager = editor.getRulebaseWrapper();
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn potDepColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(potDepColumn.getColumn(), new ColumnPixelData(55));
		potDepColumn.getColumn().setText("P.D.");
		potDepColumn.getColumn().setResizable(false);
		potDepColumn.getColumn().setAlignment(SWT.CENTER);
		potDepColumn.getColumn().setToolTipText("Incoming source and target potential dependencies (related to this rulebase).");

		// LabelProvider for versionColumn
		potDepColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(rbManager.getIncomingDependencies(((RuleBaseItem) cell.getElement()).getEditRule()).size() + "");
			}
		});
	}
}
