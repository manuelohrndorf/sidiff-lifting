package org.sidiff.difference.ui.widgets.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sidiff.configuration.IConfigurable;

public class ConfigurableTableEntryProvider extends AbstractTableEntryProvider {

	private final IConfigurable configurable;
	private final List<ITableEntry> entries = new ArrayList<ITableEntry>();
	
	public ConfigurableTableEntryProvider(String name, String value, IConfigurable configurable) {
		super(name, value);
		this.configurable = configurable;
		for (Map.Entry<String, Object> e: configurable.getConfigurationOptions().entrySet()){
			ConfigureOptionTableEntry entry=new ConfigureOptionTableEntry(this, configurable, e.getKey());
			entries.add(entry);
		}
		if (this.configurable != null && this.entries.isEmpty()){
			LabelTableEntry entry=new LabelTableEntry(this, "(no options for this element)");
			entries.add(entry);
			fireEntryAdded(entry);
		}
	}

	public IConfigurable getConfigurable() {
		return configurable;
	}
		
	@Override
	public List<ITableEntry> getEntries() {
		return new ArrayList<ITableEntry>(entries);
	}

}
