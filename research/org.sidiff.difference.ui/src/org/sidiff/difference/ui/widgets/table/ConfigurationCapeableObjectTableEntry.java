package org.sidiff.difference.ui.widgets.table;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.configuration.ConfigurationLoader;
import org.sidiff.configuration.IConfigurationCapable;
import org.w3c.dom.Document;

public class ConfigurationCapeableObjectTableEntry implements ITableEntry, IWidgetValidation {
	/**
	 * Used for events
	 */
	public static final String PROPERTY_VALUE = "__CONFIGURATION";
	private final AbstractTableEntryProvider provider;
	private boolean configurationLoadError = false;
	private URL selectedConfigURL;
	private final String docType;
	private final IConfigurationCapable configObject;
	private final Map<String, URL> availableConfigurations = new HashMap<String, URL>();
	private ComboBoxCellEditor editor = null;
	/**
	 * Result of the last validation. IMPORTANT: This may not be up-to-date
	 */
	private ValidationMessage validationMessage;
	/**
	 * List of listeners, that kindly asked to get notified when the value was
	 * changed.
	 */
	private final ListenerList propertyChangeListeners = new ListenerList();

	public void fireValueChangedEvent(final Object oldValue, final Object newValue) {
		Object[] array = propertyChangeListeners.getListeners();
		for (int i = 0; i < array.length; i++) {
			final IPropertyChangeListener l = (IPropertyChangeListener) array[i];
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.propertyChange(new PropertyChangeEvent(ConfigurationCapeableObjectTableEntry.this, PROPERTY_VALUE,
							oldValue, newValue));
				}
			});
		}
	}

	public ConfigurationCapeableObjectTableEntry(AbstractTableEntryProvider provider,
			IConfigurationCapable configObject, String docType) {
		super();
		this.provider = provider;
		this.configObject = configObject;
		this.docType = docType;
		try {
			Set<URL> configs = ConfigurationLoader.getAvailableConfigs(docType, configObject.getSubfolderName());
			String[] cNames = new String[configs.size()];
			{
				int i = 0;
				for (URL url : configs) {
					cNames[i] = (URI.createURI(url.toString())).lastSegment();
					availableConfigurations.put(cNames[i], url);
					i++;
				}
			}
			// Set first element as selected
			if (!availableConfigurations.isEmpty()) {
				setConfigurationByName(availableConfigurations.keySet().iterator().next());
			} else {
				setConfigurationByName(null);
			}
		} catch (Exception e) {
			this.configurationLoadError = true;
			configObject.setConfiguration(null);
		}
	}

	@Override
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		propertyChangeListeners.add(listener);
	}

	@Override
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		propertyChangeListeners.remove(listener);
	}

	@Override
	public ValidationMessage getValidationMessage() {
		return validationMessage;
	}

	public void setConfigurationByName(String name) throws NoSuchElementException {
		String oldConfigStr = (selectedConfigURL != null ? selectedConfigURL.toString() : null);
		if (name != null) {
			selectedConfigURL = availableConfigurations.get(name);
			if (selectedConfigURL != null) {
				Document config;
				try {
					// TODO Loading of config
					config = ConfigurationLoader.loadConfig(selectedConfigURL.openStream());
					configObject.setConfiguration(config);
					assert(configObject.getConfiguration().equals(config)) : "Config was not set";
					configurationLoadError = (config == null || configObject.getConfiguration() != config);
				} catch (IOException e) {
					configurationLoadError = true;
				}
				fireValueChangedEvent(oldConfigStr, selectedConfigURL.toString());
			} else {
				configurationLoadError = true;
				fireValueChangedEvent(oldConfigStr, null);
				throw new NoSuchElementException();
			}
		} else {
			selectedConfigURL = null;
			configurationLoadError = false;
			fireValueChangedEvent(oldConfigStr, null);
		}
	}

	public String[] getConfigurationNames() {
		String[] names = new String[availableConfigurations.size()];
		int i = 0;
		for (String name : availableConfigurations.keySet()) {
			names[i] = name;
			i++;
		}
		return names;
	}

	public String getDocType() {
		return docType;
	}

	@Override
	public boolean validate() {
		if (configurationLoadError) {
			validationMessage = new ValidationMessage(ValidationType.ERROR, "Error loading the configuration");
			return false;
		} else if (availableConfigurations.isEmpty()) {
			validationMessage = new ValidationMessage(ValidationType.ERROR, "No configurations found");
			return false;
		} else if (selectedConfigURL == null || configObject.getConfiguration() == null) {
			validationMessage = new ValidationMessage(ValidationType.ERROR, "Select a configuration");
			return false;
		} else {
			validationMessage = new ValidationMessage(ValidationType.OK, "");
			return true;
		}
	}

	public IConfigurationCapable getConfigObject() {
		return configObject;
	}

	public String getSelectedConfigurationName() {
		if (selectedConfigURL == null)
			return null;
		for (Map.Entry<String, URL> entry : availableConfigurations.entrySet()) {
			if (selectedConfigURL.equals(entry.getValue()))
				return entry.getKey();
		}
		return null;
	}

	public URL getSelectedConfigurationURL() {
		return selectedConfigURL;
	}

	@Override
	public AbstractTableEntryProvider getProvider() {
		return provider;
	}

	@Override
	public String getDisplayLabel() {
		return "Configuration";
	}

	@Override
	public String getDisplayValue() {
		String value = getSelectedConfigurationName();
		return (value != null ? value : "");
	}

	@Override
	public String getToolipText() {
		return "[Configuration]";
	}

	@Override
	public Image getIcon() {
		return PropertyTableWidget.IMAGE_CONFIG;
	}

	@Override
	public boolean isEditable() {
		return true;
	}

	@Override
	public CellEditor createCellEditor(Table table) {
		this.editor = new ComboBoxCellEditor(table, getConfigurationNames(), SWT.READ_ONLY);
		return editor;
	}

	@Override
	public CellEditor getCellEditor() {
		return this.editor;
	}

	@Override
	public Object getCellEditorValue() {
		String selected = getSelectedConfigurationName();
		if (selected == null || this.editor == null)
			return -1;
		String items[] = this.editor.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(selected))
				return i;
		}
		return -1;
	}

	@Override
	public void setCellEditorValue(Object value) {
		Integer pos = (Integer) value;
		String name = null;
		if (this.editor != null && pos != null && pos >= 0) {
			name = this.editor.getItems()[pos];
		}
		setConfigurationByName(name);
	}

	@Override
	public void dispose() {
		//Do nothing
	}
}
