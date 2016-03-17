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
import org.sidiff.editrule.rulebase.ui.editor.columns.IRuleBaseColumn;
import org.sidiff.editrule.rulebase.util.EditRuleItemUtil;

public class ColumnInverseRule implements IRuleBaseColumn {

	@Override
	public void createColumn(final RulebaseEditor editor, TableViewerColumn inverseRuleColumn, TableColumnLayout layout) {
		layout.setColumnData(inverseRuleColumn.getColumn(), new ColumnWeightData(50));
		
		inverseRuleColumn.getColumn().setText("InverseRule");
		inverseRuleColumn.getColumn().setToolTipText("Inverse Rule");
		
		// Sorting support:
		final TableViewer ruleViewer = editor.getRuleViewer();
		
		inverseRuleColumn.getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.invertSortedAscending();
				ruleViewer.getTable().setSortDirection(editor.isSortedAscending() ? SWT.UP : SWT.DOWN);
				ruleViewer.refresh();
			}
		});

		ruleViewer.getTable().setSortColumn(inverseRuleColumn.getColumn());

		// LabelProvider for Rule base item column
		inverseRuleColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(EditRuleItemUtil.getInverseName((RuleBaseItem) cell.getElement()));
			}
		});

		// Setup editing support for Rule base item column
		inverseRuleColumn.setEditingSupport(new EditingSupport(ruleViewer) {

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
				return EditRuleItemUtil.getInverseName((RuleBaseItem) element);
			}

			@Override
			protected void setValue(Object element, Object value) {
				EditRuleItemUtil.setInverseName((RuleBaseItem) element, (String) value);
				ruleViewer.update(element, null);
			}

		});
	}
}
