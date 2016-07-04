package org.sidiff.editrule.rulebase.ui.editor.columns.impl;

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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.AbstractRuleBaseColumn;
import org.sidiff.editrule.rulebase.util.EditRuleItemUtil;

public class ColumnNameBasedClassification extends AbstractRuleBaseColumn {

	@Override
	public void createColumn(final RulebaseEditor editor, TableViewerColumn nameBasedClassificationColumn, TableColumnLayout layout) {
		layout.setColumnData(nameBasedClassificationColumn.getColumn(), new ColumnPixelData(150));
		
		nameBasedClassificationColumn.getColumn().setText("Classification");
		nameBasedClassificationColumn.getColumn().setToolTipText("Classification of the edit rule");
		
		// Sorting support:
		final TableViewer ruleViewer = editor.getRuleViewer();
		
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
				cell.setText(EditRuleItemUtil.getClassificationName((RuleBaseItem) cell.getElement(), 0));
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
				return EditRuleItemUtil.getClassificationName((RuleBaseItem) element, 0);
			}

			@Override
			protected void setValue(Object element, Object value) {
				EditRuleItemUtil.setClassificationName((RuleBaseItem) element, (String) value, 0);
				ruleViewer.update(element, null);
				editor.setDirty(((RuleBaseItem) element).getEditRule());
			}

		});
	}
	
}
