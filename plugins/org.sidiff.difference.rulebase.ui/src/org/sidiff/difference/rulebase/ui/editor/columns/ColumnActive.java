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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.difference.rulebase.wrapper.RuleBaseWrapper;

public class ColumnActive {
	
	public ColumnActive(final RulebaseEditor editor) {
		final RuleBaseWrapper rbManager = editor.getRulebaseWrapper();
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn activeColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(activeColumn.getColumn(), new ColumnPixelData(25));
		activeColumn.getColumn().setText("");
		activeColumn.getColumn().setAlignment(SWT.CENTER);
		activeColumn.getColumn().setResizable(false);
		activeColumn.getColumn().setToolTipText("Activate/Deactivate rule for recognition engine");

		// Setup editing support for active column header
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
					((RuleBaseItem) element).setActive(false);
				} else {
					((RuleBaseItem) element).setActive(true);
				}

				ruleViewer.update(element, null);
			}

		});
	}
}
