package org.sidiff.difference.ui.widgets.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sidiff.configuration.IConfigurable;
import org.sidiff.configuration.IConfigurationCapable;

public class DynamicTableEntryProviders extends AbstractTableEntryProvider {

	private Object configurationObject = null;
	private final List<ITableEntry> entries = new ArrayList<ITableEntry>();
	
	
	public DynamicTableEntryProviders(String name, String value) {
		super(name, value);
	}

	public void setConfigurationObject(Object configurationObject, String docType){
		//Remove old objects
		while (!entries.isEmpty()){
			ITableEntry entry=entries.remove(0);
			fireEntryRemoved(entry);
			entry.dispose();
		}
		this.configurationObject = configurationObject;
		if (this.configurationObject instanceof IConfigurationCapable){
			ConfigurationCapeableObjectTableEntry entry = new ConfigurationCapeableObjectTableEntry(this, (IConfigurationCapable)this.configurationObject, docType);
			entries.add(entry);
			fireEntryAdded(entry);
		}
		if (this.configurationObject instanceof IConfigurable){
			IConfigurable configurable = (IConfigurable)this.configurationObject;
			for (Map.Entry<String, Object> e: configurable.getConfigurationOptions().entrySet()){
				ConfigureOptionTableEntry entry=new ConfigureOptionTableEntry(this, configurable, e.getKey());
				entries.add(entry);
				fireEntryAdded(entry);
			}
		}
		if (this.entries.isEmpty()){
			String label;
			if (this.configurationObject != null){
				label="(no options for this element)";
			} else {
				label="(no element selected)";
			}
			LabelTableEntry entry=new LabelTableEntry(this, label);
			entries.add(entry);
			fireEntryAdded(entry);
		}
	}
	
	@Override
	public List<ITableEntry> getEntries() {
		return new ArrayList<ITableEntry>(entries);
	}

	public Object getConfigurationObject() {
		return configurationObject;
	}

}
