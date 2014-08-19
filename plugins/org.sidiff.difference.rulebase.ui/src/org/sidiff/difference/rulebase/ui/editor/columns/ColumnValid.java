package org.sidiff.difference.rulebase.ui.editor.columns;

import org.eclipse.emf.common.util.Diagnostic;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.difference.rulebase.wrapper.RuleBaseItemWrapper;

public class ColumnValid {

	public ColumnValid(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn activeColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(activeColumn.getColumn(), new ColumnPixelData(18));
		activeColumn.getColumn().setText("");
		activeColumn.getColumn().setAlignment(SWT.CENTER);
		activeColumn.getColumn().setResizable(false);
		activeColumn.getColumn().setToolTipText("Validation of the Edit-Rule");

		// Setup check box for activeColumn
		activeColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return "";
			}

			@Override
			public Image getImage(Object element) {
				if (((RuleBaseItem) element).isValid()) {
					return null;
				} else {
					Image error = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
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
				return ((RuleBaseItem) element).isValid();
			}

			@Override
			protected void setValue(Object element, Object value) {
				RuleBaseItem item = (RuleBaseItem) element;

				if (!item.isValid()) {
					// TODO[MO@13.02.14]: Reload the Edit-Rule before validation!
					Diagnostic diagnostic = RuleBaseItemWrapper.validate_EditRule(item);
					
					String message = "";
					
					if (!diagnostic.getChildren().isEmpty()) {
						for (Diagnostic childDiagnostic : diagnostic.getChildren()) {
							message += childDiagnostic.getMessage() + "\n\n";
						}
					} else {
						message += "No constraints are violated. Please refresh/regenerate this item!";
					}
					
					Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					MessageBox dialog =  new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
							dialog.setText("Diagnosis of " + RuleBaseItemWrapper.getName(item));
							dialog.setMessage(message);

					dialog.open(); 
				}
			}

		});
	}
}
