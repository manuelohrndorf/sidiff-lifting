package org.sidiff.difference.ui.widgets.table;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.configuration.IConfigurable;
import org.sidiff.difference.ui.widgets.table.ConfigureOptionTableEntry.ObjectType;

/**
 * Object holding an Entry of a {@link MatcherConfigurationOptionsWidge}
 * 
 * @author Lukas
 *
 */
public class ConfigureOptionTableEntry implements ITableEntry, IWidgetValidation {
	public static enum ObjectType {
		STRING, INTEGER, FLOAT, DOUBLE, BOOLEAN, ENUM;
	}

	private final AbstractTableEntryProvider provider;
	/**
	 * Used for events
	 */
	public static final String PROPERTY_VALUE = "VALUE";
	/**
	 * Object which this key-value pair belongs to
	 */
	private final IConfigurable configObject;
	/**
	 * Key (name) of the option
	 */
	private final String key;
	/**
	 * Type of the property
	 */
	private final ObjectType type;
	/**
	 * For ENUMS: Type of the Enum
	 */
	private final Class<Enum<?>> enumType;
	/**
	 * Result of the last validation. IMPORTANT: This may not be up-to-date
	 */
	private ValidationMessage validationMessage;
	/**
	 * List of listeners, that kindly asked to get notified when the value was
	 * changed.
	 */
	private final ListenerList propertyChangeListeners = new ListenerList();
	/**
	 * Cell editor, created lazy. Keeps the same over the lifetime of this
	 * object
	 */
	private CellEditor cellEditor = null;

	public void fireValueChangedEvent(final Object oldValue, final Object newValue) {
		Object[] array = propertyChangeListeners.getListeners();
		for (int i = 0; i < array.length; i++) {
			final IPropertyChangeListener l = (IPropertyChangeListener) array[i];
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.propertyChange(new PropertyChangeEvent(ConfigureOptionTableEntry.this, PROPERTY_VALUE, oldValue,
							newValue));
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	public ConfigureOptionTableEntry(AbstractTableEntryProvider provider, IConfigurable configObject, String key) {
		super();
		this.provider = provider;
		this.configObject = configObject;
		this.key = key;
		this.validationMessage = null;
		/*
		 * Determine type
		 */
		Object value = configObject.getConfigurationOptions().get(key);
		if (value.getClass().isEnum()) {
			this.type = ObjectType.ENUM;
			this.enumType = (Class<Enum<?>>) value.getClass();
		} else {
			this.enumType = null;
			if (value instanceof Double) {
				this.type = ObjectType.DOUBLE;
			} else if (value instanceof Float) {
				this.type = ObjectType.FLOAT;
			} else if (value instanceof Integer) {
				this.type = ObjectType.INTEGER;
			} else if (value instanceof Boolean) {
				this.type = ObjectType.BOOLEAN;
			} else if (value instanceof String) {
				this.type = ObjectType.STRING;
			} else {
				throw new IllegalArgumentException("Unsupported value class \"" + value.getClass().getName() + "\"");
			}
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

	public IConfigurable getConfigObject() {
		return configObject;
	}

	public String getKey() {
		return key;
	}

	public ObjectType getType() {
		return type;
	}

	public Object getValue() {
		return configObject.getConfigurationOptions().get(key);
	}

	public void setValue(Object value) {
		Object oldValue = getValue();
		configObject.getConfigurationOptions().put(key, value);
		Object newValue=getValue();
		assert(newValue == value) : "The value was not set";
		fireValueChangedEvent(oldValue, newValue);
	}

	public Class<Enum<?>> getEnumType() {
		return enumType;
	}

	@Override
	public boolean validate() {
		Object value=getValue();
		if (ObjectType.INTEGER.equals(type) || ObjectType.FLOAT.equals(type) || ObjectType.DOUBLE.equals(type)) {
			String message = null;
			if (ObjectType.INTEGER.equals(type)) {
				if (!(value instanceof Integer)) {
					if (value instanceof String) {
						try {
							Integer.parseInt((String) value);
						} catch (NumberFormatException e) {
							message = "Enter an integer";
						}
					} else {
						message = "Enter an integer";
					}
				}
			} else if (ObjectType.FLOAT.equals(type)) {
				if (!(value instanceof Float)) {
					if (value instanceof String) {
						try {
							Float.parseFloat((String) value);
						} catch (NumberFormatException e) {
							message = "Enter a floatr";
						}
					} else {
						message = "Enter a float";
					}
				}
			} else if (ObjectType.DOUBLE.equals(type)) {
				if (!(value instanceof Double)) {
					if (value instanceof String) {
						try {
							Double.parseDouble((String) value);
						} catch (NumberFormatException e) {
							message = "Enter a double";
						}
					} else {
						message = "Enter a double";
					}
				}
			}
			this.validationMessage = new ValidationMessage((message == null ? ValidationType.OK : ValidationType.ERROR),
					(message != null ? message : ""));
			return (message == null);
		} else if (ObjectType.STRING.equals(type)) {
			if (value == null || !(value instanceof String)) {
				this.validationMessage = new ValidationMessage(ValidationType.ERROR, "Enter a String");
				return false;
			} else if (((String) value).isEmpty()) {
				this.validationMessage = new ValidationMessage(ValidationType.WARNING, "Empty String");
				return false;
			} else {
				this.validationMessage = new ValidationMessage(ValidationType.OK, "");
				return true;
			}
		} else if (ObjectType.ENUM.equals(type)) {
			if (value == null) {
				this.validationMessage = new ValidationMessage(ValidationType.ERROR, "No item selected");
				return false;
			} else {
				this.validationMessage = new ValidationMessage(ValidationType.OK, "");
				return true;
			}
		} else {
			this.validationMessage = new ValidationMessage(ValidationType.OK, "");
			return true;
		}
	}

	public String getDatatype() {
		switch (type) {
		case INTEGER:
			return "int";
		case FLOAT:
			return "float";
		case DOUBLE:
			return "double";
		case STRING:
			return "String";
		case BOOLEAN:
			return "boolean";
		case ENUM:
			return enumType.getSimpleName();
		default:
			return null;
		}
	}

	@Override
	public AbstractTableEntryProvider getProvider() {
		return provider;
	}

	@Override
	public String getDisplayLabel() {
		return key;
	}

	@Override
	public String getDisplayValue() {
		Object value=getValue();
		return (value != null ? value.toString() : "[null]");
	}

	@Override
	public String getToolipText() {
		return "[" + getDatatype() + "]";
	}

	@Override
	public Image getIcon() {
		return null;
	}

	@Override
	public boolean isEditable() {
		return true;
	}

	public CellEditor createCellEditor(Table table) {
		if (ObjectType.ENUM.equals(this.type)) {
			Enum<?>[] enumConstants = this.enumType.getEnumConstants();
			String[] items = new String[enumConstants.length];
			for (int i = 0; i < items.length; i++) {
				items[i] = enumConstants[i].toString();
			}
			this.cellEditor = new ComboBoxCellEditor(table, items, SWT.READ_ONLY);
		} else if (ObjectType.BOOLEAN.equals(this.type)) {
			this.cellEditor = new CheckboxCellEditor(table, SWT.CHECK);
		} else {
			this.cellEditor = new TextCellEditor(table);
		}
		return this.cellEditor;
	}

	@Override
	public CellEditor getCellEditor() {
		return this.cellEditor;
	}

	@Override
	public Object getCellEditorValue() {
		Object value=getValue();
		switch (this.type) {
		case INTEGER:
			return value.toString();
		case FLOAT:
			return value.toString();
		case DOUBLE:
			return value.toString();
		case BOOLEAN:
			return value;
		case STRING:
			return value;
		case ENUM:
			return posOfEnum(value);
		default:
			return null;
		}
	}

	private int posOfEnum(Object value) {
		CellEditor ce = getCellEditor();
		if (ce == null)
			return -1;
		String[] names = ((ComboBoxCellEditor) ce).getItems();
		for (int i = 0; i < names.length; i++) {
			if (names[i].equals(value.toString()))
				return i;
		}
		return -1;
	}

	@Override
	public void setCellEditorValue(Object value) {
		if (ObjectType.ENUM.equals(this.type)) {
			Integer pos = (Integer) value;
			Enum<?> v = null;
			CellEditor cellEditor=getCellEditor();
			if (cellEditor != null && pos != null && pos >= 0) {
				String name = ((ComboBoxCellEditor) cellEditor).getItems()[pos];
				Enum<?>[] enumConstants = this.enumType.getEnumConstants();
				for (int i = 0; i < enumConstants.length; i++) {
					if (enumConstants[i].toString().equals(name)) {
						v = enumConstants[i];
						break;
					}
				}
			}
			setValue(v);
		} else {
			setValue(value);
		}
	}

	@Override
	public void dispose() {
		//Do nothing
	}

}
