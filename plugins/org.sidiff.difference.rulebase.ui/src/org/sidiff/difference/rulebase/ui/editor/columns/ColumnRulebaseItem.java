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

public class ColumnRulebaseItem {

	public ColumnRulebaseItem(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn ruleColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(ruleColumn.getColumn(), new ColumnWeightData(50));
		ruleColumn.getColumn().setText("Rule");
		ruleColumn.getColumn().setToolTipText("Rules of the rule base");
		ruleColumn.getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.invertSortedAscending();
				ruleViewer.getTable().setSortDirection(editor.isSortedAscending() ? SWT.UP : SWT.DOWN);
				ruleViewer.refresh();
			}
		});

		ruleViewer.getTable().setSortColumn(ruleColumn.getColumn());

		// LabelProvider for Rule base item column
		ruleColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(RuleBaseItemInfo.getName((RuleBaseItem) cell.getElement()));
			}
		});

		// Setup editing support for Rule base item column
		ruleColumn.setEditingSupport(new EditingSupport(ruleViewer) {

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new TextCellEditor(ruleViewer.getTable());
			}

			@Override
			protected Object getValue(Object element) {
				return RuleBaseItemInfo.getName((RuleBaseItem) element);
			}

			@Override
			protected void setValue(Object element, Object value) {
				RuleBaseItemInfo.setName((RuleBaseItem) element, (String) value);
				ruleViewer.update(element, null);
			}

		});
	}
}
