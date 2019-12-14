package org.sidiff.editrule.rulebase.ui.editor.columns.impl;

import java.util.Objects;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.AbstractRuleBaseColumn;
import org.sidiff.editrule.rulebase.util.EditRuleItemUtil;

public class ColumnRulebaseItem extends AbstractRuleBaseColumn {

	@Override
	public void createColumn(final RulebaseEditor editor, TableViewerColumn ruleColumn, TableColumnLayout layout) {
		layout.setColumnData(ruleColumn.getColumn(), new ColumnWeightData(50, 200));
		
		ruleColumn.getColumn().setText("Rule");
		ruleColumn.getColumn().setToolTipText("Name of the edit rule");
		
		// Sorting support:
		final TableViewer ruleViewer = editor.getRuleViewer();
		
		ruleColumn.getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.invertSortedAscending();
				ruleViewer.getTable().setSortDirection(editor.isSortedAscending() ? SWT.UP : SWT.DOWN);
				ruleViewer.refresh();
			}
		});

		ruleViewer.getTable().setSortColumn(ruleColumn.getColumn());

		ruleColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return EditRuleItemUtil.getName((RuleBaseItem)element);
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
				return EditRuleItemUtil.getName((RuleBaseItem) element);
			}

			@Override
			protected void setValue(Object element, Object value) {
				if(!Objects.equals(value, getValue(element))) {
					EditRuleItemUtil.setName((RuleBaseItem) element, (String) value);
					ruleViewer.update(element, null);
					editor.setDirty();					
				}
			}
		});
	}
}
