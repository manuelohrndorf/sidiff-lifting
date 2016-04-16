package org.sidiff.difference.ui.widgets.table;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;

public class LabelTableEntry implements ITableEntry {
	private final AbstractTableEntryProvider provider;
	private final String label;
	
	public LabelTableEntry(AbstractTableEntryProvider provider, String label) {
		super();
		this.provider = provider;
		this.label=label;
	}

	@Override
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		//Not supported (covered by ITableEntryProviderListener)
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
		return label;
	}

	@Override
	public String getDisplayValue() {
		return "";
	}

	@Override
	public Image getIcon() {
		return null;
	}
	
	@Override
	public String getToolipText() {
		return "No options for this element available";
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
