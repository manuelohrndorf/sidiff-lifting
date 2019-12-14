package org.sidiff.editrule.rulebase.ui.editor.columns.impl;

import java.util.Objects;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
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

public class ColumnNameBasedClassification extends AbstractRuleBaseColumn {

	@Override
	public void createColumn(final RulebaseEditor editor, TableViewerColumn nameBasedClassificationColumn, TableColumnLayout layout) {
		layout.setColumnData(nameBasedClassificationColumn.getColumn(), new ColumnPixelData(150));
		
		nameBasedClassificationColumn.getColumn().setText("Classification");
		nameBasedClassificationColumn.getColumn().setToolTipText("Classification of the edit rule");
		
		final TableViewer ruleViewer = editor.getRuleViewer();
		
		// Sorting support:
		nameBasedClassificationColumn.getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				editor.invertSortedAscending();
				ruleViewer.getTable().setSortDirection(editor.isSortedAscending() ? SWT.UP : SWT.DOWN);
				ruleViewer.refresh();
			}
		});

		nameBasedClassificationColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return EditRuleItemUtil.getClassificationName((RuleBaseItem)element, "NameBasedClassificator");
			}
		});
		
		nameBasedClassificationColumn.setEditingSupport(new EditingSupport(ruleViewer) {
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
				return EditRuleItemUtil.getClassificationName((RuleBaseItem) element, "NameBasedClassificator");
			}

			@Override
			protected void setValue(Object element, Object value) {
				if(!Objects.equals(value, getValue(element))) {
					EditRuleItemUtil.setClassificationName((RuleBaseItem) element, (String) value, "NameBasedClassificator");
					ruleViewer.update(element, null);
					editor.setDirty();					
				}
			}
		});
	}
}
