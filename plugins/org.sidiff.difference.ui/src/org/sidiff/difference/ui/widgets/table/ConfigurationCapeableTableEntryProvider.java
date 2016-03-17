package org.sidiff.difference.ui.widgets.table;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.sidiff.configuration.IConfigurationCapable;

/**
 * A ITableEntryProvider for IConfigurationCapables
 * @author lukas
 *
 */
public class ConfigurationCapeableTableEntryProvider extends AbstractTableEntryProvider {

	/**
	 * The entry hosted by this provider
	 */
	private final ConfigurationCapeableObjectTableEntry entry;

	public ConfigurationCapeableTableEntryProvider(String name, String value, IConfigurationCapable configurable,
			String docType) {
		super(name, value);
		entry = new ConfigurationCapeableObjectTableEntry(this, configurable, docType);
	}

	/**
	 * Get the object this provider provides an entry for
	 * @return
	 */
	public IConfigurationCapable getConfigurationCapable() {
		return entry.getConfigObject();
	}

	/**
	 * Get the used doc type
	 * @return
	 */
	public String getDocType() {
		return entry.getDocType();
	}
	
	/**
	 * Get the selected configuration URL
	 * @return Configuration URL or null
	 */
	public URL getSelectedConfigurationURL(){
		return entry.getSelectedConfigurationURL();
	}
	
	/**
	 * Get the name of the selected configuration
	 * @return Name or null
	 */
	public String getSelectedConfigurationName(){
		return entry.getSelectedConfigurationName();
	}
		
	@Override
	public List<ITableEntry> getEntries() {
		ArrayList<ITableEntry> list = new ArrayList<ITableEntry>();
		list.add(entry);
		return list;
	}

}
