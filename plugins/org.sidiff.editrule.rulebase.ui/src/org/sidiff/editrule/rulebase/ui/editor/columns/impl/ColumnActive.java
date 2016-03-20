package org.sidiff.editrule.rulebase.ui.editor.columns.impl;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.builder.EditRuleBaseWrapper;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.IRuleBaseColumn;
import org.sidiff.editrule.rulebase.util.EditRuleItemUtil;

public class ColumnActive implements IRuleBaseColumn {

	@Override
	public void createColumn(final RulebaseEditor editor, TableViewerColumn activeColumn, TableColumnLayout layout) {
		layout.setColumnData(activeColumn.getColumn(), new ColumnPixelData(25));
		
		activeColumn.getColumn().setText("");
		activeColumn.getColumn().setAlignment(SWT.CENTER);
		activeColumn.getColumn().setResizable(false);
		activeColumn.getColumn().setToolTipText("Activate/Deactivate rule for recognition engine");

		// Setup editing support for active column header
		final EditRuleBaseWrapper rbManager = editor.getRulebaseWrapper();
		
		activeColumn.getColumn().addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// Invert all
				rbManager.invertAllItemsActivity();

				// Refresh the GUI
				editor.update();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Invert all
				rbManager.invertAllItemsActivity();

				// Refresh the GUI
				editor.update();
			}
		});

		// Setup check box for activeColumn
		activeColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return "";
			}

			@Override
			public Image getImage(Object element) {
				if (((RuleBaseItem) element).isActive()) {
					return editor.getImageDescriptor("checked.png").createImage();
				} else {
					return editor.getImageDescriptor("unchecked.png").createImage();
				}
			}
		});

		// Setup editing support for activeColumn
		final TableViewer ruleViewer = editor.getRuleViewer();
		
		activeColumn.setEditingSupport(new EditingSupport(ruleViewer) {

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new CheckboxCellEditor(ruleViewer.getTable());
			}

			@Override
			protected Object getValue(Object element) {
				return ((RuleBaseItem) element).isActive();
			}

			@Override
			protected void setValue(Object element, Object value) {

				if (((RuleBaseItem) element).isActive()) {
					EditRuleItemUtil.setActive((RuleBaseItem) element, false);
				} else {
					EditRuleItemUtil.setActive((RuleBaseItem) element, true);
				}

				ruleViewer.update(element, null);
			}

		});
	}
}
