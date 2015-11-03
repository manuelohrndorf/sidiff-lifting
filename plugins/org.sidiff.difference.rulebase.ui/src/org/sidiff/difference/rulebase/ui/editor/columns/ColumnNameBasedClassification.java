package org.sidiff.difference.rulebase.ui.editor.columns;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.difference.rulebase.wrapper.RuleBaseItemInfo;

public class ColumnNameBasedClassification {

	public ColumnNameBasedClassification(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn nameBasedClassificationColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(nameBasedClassificationColumn.getColumn(), new ColumnWeightData(50));
		nameBasedClassificationColumn.getColumn().setText("NameBasedClassification");
		nameBasedClassificationColumn.getColumn().setToolTipText("Name based classification of the Edit Rule");
		nameBasedClassificationColumn.getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.invertSortedAscending();
				ruleViewer.getTable().setSortDirection(editor.isSortedAscending() ? SWT.UP : SWT.DOWN);
				ruleViewer.refresh();
			}
		});

		nameBasedClassificationColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(RuleBaseItemInfo.getClassificationName((RuleBaseItem) cell.getElement(), 0));
			}
		});
		
		// Setup editing support for Rule base description column
		nameBasedClassificationColumn.setEditingSupport(new EditingSupport(ruleViewer) {

			@Override
			protected boolean canEdit(Object element) {
				return false;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(ruleViewer.getTable());
			}

			@Override
			protected Object getValue(Object element) {
				return RuleBaseItemInfo.getClassificationName((RuleBaseItem) element, 0);
			}

			@Override
			protected void setValue(Object element, Object value) {
				RuleBaseItemInfo.setClassificationName((RuleBaseItem) element, (String) value, 0);
				ruleViewer.update(element, null);
			}

		});
	}
	
}
