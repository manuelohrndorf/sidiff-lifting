package org.sidiff.difference.rulebase.ui.editor.columns;

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
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.difference.rulebase.wrapper.RuleBaseItemWrapper;

public class ColumnVersion {

	public ColumnVersion(final RulebaseEditor editor) {
		final TableViewer ruleViewer = editor.getRuleViewer();
		final TableColumnLayout tableColumnLayout = editor.getTableColumnLayout();
		
		TableViewerColumn versionColumn = new TableViewerColumn(ruleViewer, SWT.NONE);
		tableColumnLayout.setColumnData(versionColumn.getColumn(), new ColumnPixelData(55));
		versionColumn.getColumn().setText("Version");
		versionColumn.getColumn().setResizable(false);
		versionColumn.getColumn().setAlignment(SWT.CENTER);
		versionColumn.getColumn().setToolTipText("Version of the rule base item.");

		// LabelProvider for versionColumn
		versionColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(RuleBaseItemWrapper.getVersion((RuleBaseItem) cell.getElement()));
			}
		});

		// Setup editing support for versionColumn
		versionColumn.setEditingSupport(new EditingSupport(ruleViewer) {

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
				// Get Henshin semantic change set priority attribute
				return "" + RuleBaseItemWrapper.getVersion((RuleBaseItem) element);
			}

			@Override
			protected void setValue(Object element, Object value) {
				// Get Henshin semantic change set priority attribute
				String version = RuleBaseItemWrapper.getVersion((RuleBaseItem) element);

				try {
					// Set semantic change set priority
					String newVersion = (String) value;

					if (!newVersion.equals(version)) {
						int[] versionSplit = RuleBaseItemWrapper.convertStringToVersion(newVersion);
						RuleBaseItemWrapper.setVersion((RuleBaseItem) element, versionSplit[0], versionSplit[1], versionSplit[2]);
						ruleViewer.update(element, null);
					}
				} catch (NumberFormatException e) {
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}

		});
	}
}
