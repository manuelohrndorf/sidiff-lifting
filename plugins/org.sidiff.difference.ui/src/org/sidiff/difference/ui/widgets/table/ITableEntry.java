package org.sidiff.difference.ui.widgets.table;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;

public interface ITableEntry {

	/**
	 * The provider for this item
	 * @return
	 */
	public AbstractTableEntryProvider getProvider();
	
	/**
	 * The label to display
	 * @return
	 */
	public String getDisplayLabel();
	/**
	 * Human-readable version of the current value
	 * @return
	 */
	public String getDisplayValue();
	/**
	 * Tooltip
	 * @return
	 */
	public String getToolipText();
	/**
	 * Icon
	 * @return
	 */
	public Image getIcon();
	
	/**
	 * Editable?
	 * @return
	 */
	public boolean isEditable();
	/**
	 * Creates the cell editor
	 * @param table
	 * @return
	 */
	public CellEditor createCellEditor(Table table);
	/**
	 * Last created cell editor
	 * @return
	 */
	public CellEditor getCellEditor();

	/**
	 * 
	 * @return
	 */
	public Object getCellEditorValue();
	/**
	 * Calles when the user changes the editor value
	 * @param value 
	 */
	public void setCellEditorValue(Object value);

	public void addPropertyChangeListener(IPropertyChangeListener propertyChangeListener);
	public void removePropertyChangeListener(IPropertyChangeListener propertyChangeListener);

	/**
	 * Removes all unmanaged resources and stops listening.
	 */
	public void dispose();
}
