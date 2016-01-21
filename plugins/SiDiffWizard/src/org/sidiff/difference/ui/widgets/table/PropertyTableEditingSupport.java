package org.sidiff.difference.ui.widgets.table;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;

/**
 * Class for editing support in the {@link PropertyTableWidget}
 * 
 * @author Lukas
 *
 */
class PropertyTableEditingSupport extends EditingSupport {

	/**
	 * TableViewer of parent {@link PropertyTableWidget}
	 */
	private final TableViewer viewer;

	/**
	 * Editing support in the {@link PropertyTableWidget}
	 * 
	 * @param viewer
	 *            TableViewer of parent {@link PropertyTableWidget}
	 */
	public PropertyTableEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		if (element instanceof ITableEntry) {
			ITableEntry entry = ((ITableEntry) element);
			CellEditor editor = entry.getCellEditor();
			if (editor == null)
				editor = entry.createCellEditor(viewer.getTable());
			return editor;
		} else {
			return null;
		}
	}

	@Override
	protected boolean canEdit(Object element) {
		if (element instanceof ITableEntry) {
			return ((ITableEntry) element).isEditable();
		} else {
			return false;
		}
	}

	@Override
	protected Object getValue(Object element) {
		if (element instanceof ITableEntry) {
			return ((ITableEntry) element).getCellEditorValue();
		} else {
			return null;
		}
	}

	@Override
	protected void setValue(Object element, Object value) {
		if (element instanceof ITableEntry) {
			((ITableEntry) element).setCellEditorValue(value);
		}
		viewer.update(element, null);
	}

}
