package org.sidiff.difference.rulebase.ui.editor.columns.impl;

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
import org.sidiff.difference.rulebase.util.RecognitionRuleItemUtil;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.IRuleBaseColumn;

public class ColumnPriority implements IRuleBaseColumn {

	@Override
	public void createColumn(final RulebaseEditor editor, TableViewerColumn priorityColumn, TableColumnLayout layout) {
		layout.setColumnData(priorityColumn.getColumn(), new ColumnPixelData(100));
		
		priorityColumn.getColumn().setText("Priority");
		priorityColumn.getColumn().setResizable(false);
		priorityColumn.getColumn().setAlignment(SWT.CENTER);
		priorityColumn.getColumn().setToolTipText("Recognition rule post processsing priority");

		// LabelProvider for priorityColumn
		priorityColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				// Get Henshin semantic change set priority attribute
				cell.setText("" + RecognitionRuleItemUtil.getPriority((RuleBaseItem) cell.getElement()));
			}
		});

		// Setup editing support for priorityColumn
		final TableViewer ruleViewer = editor.getRuleViewer();
		
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
				return "" + RecognitionRuleItemUtil.getPriority((RuleBaseItem) element);
			}

			@Override
			protected void setValue(Object element, Object value) {
				// Get Henshin semantic change set priority attribute
				int priority = RecognitionRuleItemUtil.getPriority((RuleBaseItem) element);

				try {
					// Set semantic change set priority
					int newPriority = Integer.valueOf((String) value);

					if (newPriority != priority) {
						RecognitionRuleItemUtil.setPriority((RuleBaseItem) element, newPriority);
						ruleViewer.update(element, null);
						editor.setDirty(((RuleBaseItem) element).getEditRule());
					}
				} catch (NumberFormatException e) {
				}
			}

		});
	}
}
