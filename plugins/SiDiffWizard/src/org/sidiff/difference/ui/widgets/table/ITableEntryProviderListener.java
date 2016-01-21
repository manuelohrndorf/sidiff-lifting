package org.sidiff.difference.ui.widgets.table;

public interface ITableEntryProviderListener {
	public void onTableEntryAdded(AbstractTableEntryProvider sender, ITableEntry entry);
	public void onTableEntryRemoved(AbstractTableEntryProvider sender, ITableEntry entry);
	public void onPropertiesChanged(AbstractTableEntryProvider sender);
}
