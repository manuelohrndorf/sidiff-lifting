package org.sidiff.difference.rulebase.ui.editor.columns.impl;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor.EDataTypeCellEditor;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.sidiff.difference.rulebase.util.RecognitionRuleItemUtil;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.AbstractRuleBaseColumn;

public class ColumnPriority extends AbstractRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn priorityColumn, TableColumnLayout layout) {
		layout.setColumnData(priorityColumn.getColumn(), new ColumnPixelData(100));

		priorityColumn.getColumn().setText("Priority");
		priorityColumn.getColumn().setResizable(false);
		priorityColumn.getColumn().setAlignment(SWT.CENTER);
		priorityColumn.getColumn().setToolTipText("Recognition rule post processsing priority");

		priorityColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return Integer.toString(RecognitionRuleItemUtil.getPriority((RuleBaseItem)element));
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
				return new EDataTypeCellEditor(EcorePackage.eINSTANCE.getEInt(), ruleViewer.getTable());
			}

			@Override
			protected Object getValue(Object element) {
				return RecognitionRuleItemUtil.getPriority((RuleBaseItem)element);
			}

			@Override
			protected void setValue(Object element, Object value) {
				int oldPriority = (int)getValue(element);
				int newPriority = (int)value;

				if (newPriority != oldPriority) {
					RecognitionRuleItemUtil.setPriority((RuleBaseItem) element, newPriority);
					ruleViewer.update(element, null);
					editor.setDirty();
				}
			}
		});
	}
}
