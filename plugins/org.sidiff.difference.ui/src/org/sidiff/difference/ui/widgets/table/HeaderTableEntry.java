package org.sidiff.difference.ui.widgets.table;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;

class HeaderTableEntry implements ITableEntry {
	private final AbstractTableEntryProvider provider;
	
	public HeaderTableEntry(AbstractTableEntryProvider provider) {
		super();
		this.provider = provider;
	}

	@Override
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		//Not supported (covered by (ITableEntryProviderListener)
	}

	@Override
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		//Not supported (covered by ITableEntryProviderListener)
	}
	
	@Override
	public AbstractTableEntryProvider getProvider() {
		return provider;
	}

	@Override
	public String getDisplayLabel() {
		return provider.getName();
	}

	@Override
	public String getDisplayValue() {
		return provider.getValue();
	}

	@Override
	public Image getIcon() {
		return null;
	}
	
	@Override
	public String getToolipText() {
		return "Options for "+getDisplayLabel();
	}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	public CellEditor getCellEditor() {
		throw new UnsupportedOperationException("No editing supported");
	}

	@Override
	public Object getCellEditorValue() {
		throw new UnsupportedOperationException("No editing supported");
	}

	@Override
	public void setCellEditorValue(Object value) {
		throw new UnsupportedOperationException("No editing supported");
	}

	@Override
	public CellEditor createCellEditor(Table table) {
		throw new UnsupportedOperationException("No editing supported");
	}

	@Override
	public void dispose() {
		//Do nothing
	}


}
