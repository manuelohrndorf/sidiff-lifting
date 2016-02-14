package org.sidiff.difference.ui.widgets.table;

import java.util.List;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.SafeRunnable;

/**
 * A provider for {@link ITableEntry}.</br>
 * In a {@link PropertyTableWidget} there is a section with a header for each provider.
 * @author lukas
 *
 */
public abstract class AbstractTableEntryProvider {
	/**
	 * List of all {@link ITableEntryProviderListener}s
	 */
	private final ListenerList tableEntryProviderListeners = new ListenerList();
	private String name, value;

	public AbstractTableEntryProvider(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	/**
	 * Fires
	 * {@link ITableEntryProviderListener#onPropertiesChanged(AbstractTableEntryProvider)}
	 */
	protected final void firePropertiesChanged() {
		Object[] array = tableEntryProviderListeners.getListeners();
		for (int i = 0; i < array.length; i++) {
			final ITableEntryProviderListener l = (ITableEntryProviderListener) array[i];
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.onPropertiesChanged(AbstractTableEntryProvider.this);
				}
			});
		}
	}

	/**
	 * Fires
	 * {@link ITableEntryProviderListener#onTableEntryAdded(AbstractTableEntryProvider, ITableEntry)}
	 */
	protected final void fireEntryAdded(final ITableEntry entry) {
		Object[] array = tableEntryProviderListeners.getListeners();
		for (int i = 0; i < array.length; i++) {
			final ITableEntryProviderListener l = (ITableEntryProviderListener) array[i];
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.onTableEntryAdded(AbstractTableEntryProvider.this, entry);
				}
			});
		}
	}

	/**
	 * Fires
	 * {@link ITableEntryProviderListener#onTableEntryRemoved(AbstractTableEntryProvider, ITableEntry)}
	 */
	protected final void fireEntryRemoved(final ITableEntry entry) {
		Object[] array = tableEntryProviderListeners.getListeners();
		for (int i = 0; i < array.length; i++) {
			final ITableEntryProviderListener l = (ITableEntryProviderListener) array[i];
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.onTableEntryRemoved(AbstractTableEntryProvider.this, entry);
				}
			});
		}
	}

	/**
	 * Adds a {@link ITableEntryProviderListener}
	 * 
	 * @param listener
	 */
	public void addTableEntryProviderListener(ITableEntryProviderListener listener) {
		tableEntryProviderListeners.add(listener);
	}

	/**
	 * Remove a {@link ITableEntryProviderListener}
	 * 
	 * @param listener
	 */
	public void removeTableEntryProviderListener(ITableEntryProviderListener listener) {
		tableEntryProviderListeners.remove(listener);
	}
	
	/**
	 * Removes all unmanaged resources from the entries and the provider itself.
	 * Also stops listening to everything the entries and the provider is listening to.
	 */
	public void dispose(){
		for (ITableEntry e : getEntries()){
			e.dispose();
		}
	}

	/**
	 * Returns the name of the provider. This name will be shown in the
	 * Property/Key column of the table
	 * 
	 * @return not null
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the provider. This name will be shown in the
	 * Property/Key column of the table
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
		firePropertiesChanged();
	}

	/**
	 * Returns the value of the provider. This will be shown in the value column
	 * of the table
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value of the provider. This will be shown in the value column of
	 * the table
	 * 
	 * @param value
	 *            not null
	 */
	public void setValue(String value) {
		this.value = value;
		firePropertiesChanged();
	}

	/**
	 * Returns the list of entries.</br>
	 * This list is in a constant order.</br>
	 * All changes (add/insert/remove) are announced to all
	 * registered {@link ITableEntryProviderListener}s
	 * 
	 * @return
	 */
	public abstract List<ITableEntry> getEntries();
}
