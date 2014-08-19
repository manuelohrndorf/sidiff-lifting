package org.sidiff.difference.rulebase.ui.editor.columns;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.difference.rulebase.wrapper.RuleBaseItemWrapper;

public class ColumnPriority {

	public ColumnPriority(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn priorityColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(priorityColumn.getColumn(), new ColumnPixelData(55));
		priorityColumn.getColumn().setText("Priority");
		priorityColumn.getColumn().setResizable(false);
		priorityColumn.getColumn().setAlignment(SWT.CENTER);
		priorityColumn.getColumn().setToolTipText("Recognition rule post processsing priority");

		// LabelProvider for priorityColumn
		priorityColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				// Get Henshin semantic change set priority attribute
				cell.setText("" + RuleBaseItemWrapper.getPriority((RuleBaseItem) cell.getElement()));
			}
		});

		// Setup editing support for priorityColumn
		priorityColumn.setEditingSupport(new EditingSupport(ruleViewer) {

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
				// Get Henshin semantic change set priority attribute
				return "" + RuleBaseItemWrapper.getPriority((RuleBaseItem) element);
			}

			@Override
			protected void setValue(Object element, Object value) {
				// Get Henshin semantic change set priority attribute
				int priority = RuleBaseItemWrapper.getPriority((RuleBaseItem) element);

				try {
					// Set semantic change set priority
					int newPriority = Integer.valueOf((String) value);

					if (newPriority != priority) {
						RuleBaseItemWrapper.setPriority((RuleBaseItem) element, newPriority);
						ruleViewer.update(element, null);
					}
				} catch (NumberFormatException e) {
				}
			}

		});
	}
}
