package org.sidiff.difference.rulebase.ui.editor.columns;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.ui.Activator;
import org.sidiff.difference.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.difference.rulebase.wrapper.RuleBaseItemWrapper;
import org.silift.common.util.ui.UIUtil;

public class ColumnConsistent {

	public ColumnConsistent(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn activeColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(activeColumn.getColumn(), new ColumnPixelData(18));
		activeColumn.getColumn().setText("");
		activeColumn.getColumn().setAlignment(SWT.CENTER);
		activeColumn.getColumn().setResizable(false);
		activeColumn.getColumn().setToolTipText("Consistency of the Edit-Rule and Recognition-Rule");

		// Setup check box for activeColumn
		activeColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return "";
			}

			@Override
			public Image getImage(Object element) {
				if (RuleBaseItemWrapper.checkEditRuleMD5Hash(((RuleBaseItem) element))) {
					return null;
				} else {
					Image error = Activator.getImageDescriptor("refresh.png").createImage();
					return error;
				}
			}
		});
		
		// Setup editing support for activeColumn
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
				return false;
			}

			@Override
			protected void setValue(Object element, Object value) {
				RuleBaseItem item = (RuleBaseItem) element;

				if (!RuleBaseItemWrapper.checkEditRuleMD5Hash(item)) {
					UIUtil.callCommand("org.sidiff.difference.rulebase.ui.refreshRRules", null);
				}
			}
		});
	}
}
