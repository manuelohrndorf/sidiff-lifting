package org.sidiff.editrule.rulebase.ui.editor.columns.impl;

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
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.AbstractRuleBaseColumn;
import org.sidiff.editrule.rulebase.util.EditRuleItemUtil;

public class ColumnDescription extends AbstractRuleBaseColumn {

	@Override
	public void createColumn(final RulebaseEditor editor, TableViewerColumn descriptionColumn, TableColumnLayout layout) {
		layout.setColumnData(descriptionColumn.getColumn(), new ColumnWeightData(50, 200));
		
		descriptionColumn.getColumn().setText("Description");
		descriptionColumn.getColumn().setToolTipText("Description of the edit rule");
		
		// Sorting support:
		final TableViewer ruleViewer = editor.getRuleViewer();
		
		descriptionColumn.getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.invertSortedAscending();
				ruleViewer.getTable().setSortDirection(editor.isSortedAscending() ? SWT.UP : SWT.DOWN);
				ruleViewer.refresh();
			}
		});

		descriptionColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(EditRuleItemUtil.getDescription((RuleBaseItem) cell.getElement()));
			}
		});
		
		// Setup editing support for Rule base description column
		descriptionColumn.setEditingSupport(new EditingSupport(ruleViewer) {

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
				return EditRuleItemUtil.getDescription((RuleBaseItem) element);
			}

			@Override
			protected void setValue(Object element, Object value) {
				EditRuleItemUtil.setDescription((RuleBaseItem) element, (String) value);
				ruleViewer.update(element, null);
				editor.setDirty(((RuleBaseItem) element).getEditRule());
			}

		});
	}
}
