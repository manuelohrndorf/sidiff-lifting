package org.sidiff.difference.ui.wizard;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.ui.widgets.table.AbstractTableEntryProvider;
import org.sidiff.difference.ui.widgets.table.ITableEntry;
import org.sidiff.difference.ui.widgets.table.PropertyTableWidget;
import org.sidiff.service.IService;

public abstract class ServiceTableEntry<T extends IService, S extends AbstractSettings>
		implements ITableEntry, IWidgetValidation {
	/**
	 * Used for events
	 */
	public static final String PROPERTY_VALUE = "VALUE";
	private final AbstractTableEntryProvider provider;
	protected final S settings;
	protected final Enum<?> settingsItem;
	private boolean selectable;
	/**
	 * Stores the last service set by this ServiceTableEntry. It is used for
	 * masking SettingsChanged events fired by this itself and prevent infinite
	 * event loops.
	 */
	private T lastSetService;
	private Set<T> availableServices = null;
	private final String name;
	private ComboBoxCellEditor editor = null;
	/**
	 * Result of the last validation. IMPORTANT: This may not be up-to-date
	 */
	private ValidationMessage validationMessage;
	private ISettingsChangedListener settingsListener = new ISettingsChangedListener() {

		@Override
		public void settingsChanged(Enum<?> item) {
			if (ServiceTableEntry.this.settingsItem.equals(item)) {
				T n = getSetting();
				// Prevent infinite event loops
				if ((lastSetService == null && n != null) || (lastSetService != null && !lastSetService.equals(n))) {
					setSelectedService(n);
				}
			}
		}
	};
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
					l.propertyChange(
							new PropertyChangeEvent(ServiceTableEntry.this, PROPERTY_VALUE, oldValue, newValue));
				}
			});
		}
	}

	public ServiceTableEntry(AbstractTableEntryProvider provider, String name, S settings, Enum<?> settingsItem,
			boolean selectable) {
		super();
		this.provider = provider;
		this.name = name;
		this.settings = settings;
		this.settingsItem = settingsItem;
		this.selectable = selectable;
		this.availableServices = new HashSet<T>(findAvailableServices());
		if (selectable && getSetting() == null && !this.availableServices.isEmpty()) {
			setSelectedService(this.availableServices.iterator().next());
		} else {
			this.lastSetService = getSetting();
		}
	}

	protected abstract Set<T> findAvailableServices();

	public String getName() {
		return name;
	}

	public Set<T> getAvailableServices() {
		return new HashSet<T>(availableServices);
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		IService oldValue = getSelectedService();
		this.selectable = selectable;
		fireValueChangedEvent(oldValue, getSelectedService());
	}

	@Override
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		propertyChangeListeners.add(listener);
	}

	@Override
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		propertyChangeListeners.remove(listener);
	}

	protected abstract void setSetting(T service);

	protected abstract T getSetting();

	@SuppressWarnings("unchecked")
	public void setValue(Object value) {
		setSelectedService((T) value);
	}

	@Override
	public ValidationMessage getValidationMessage() {
		return validationMessage;
	}

	@Override
	public boolean validate() {
		T selectedService = getSetting();
		if (selectable && selectedService == null) {
			validationMessage = new ValidationMessage(ValidationType.ERROR, "No service selected");
			return false;
		} else if (selectable && !availableServices.contains(selectedService)) {
			validationMessage = new ValidationMessage(ValidationType.ERROR, "Selected service not available");
			return false;
		} else {
			validationMessage = new ValidationMessage(ValidationType.OK, "");
			return true;
		}
	}

	public void setSelectedService(T selected) {
		T oldValue = getSelectedService();
		T oldSelected = lastSetService;
		setSetting(selected);
		lastSetService = selected;
		if (oldValue != selected | oldSelected != selected) {
			fireValueChangedEvent((oldValue != selected ? oldValue : oldSelected), selected);
		}
	}

	public T getSelectedService() {
		return (isSelectable() ? getSetting() : null);
	}

	public void setServiceByID(String id) {
		if (id != null && !id.isEmpty()) {
			T selected = null;
			for (T service : availableServices) {
				if (service.getServiceID().equals(id)) {
					selected = service;
					break;
				}
			}
			if (selected == null) {
				// TODO Debug out
			}
			setSelectedService(selected);
		} else {
			setSelectedService(null);
		}
	}

	public String[] getServiceIDs() {
		String[] ids = new String[availableServices.size()];
		Iterator<T> services = availableServices.iterator();
		for (int i = 0; i < ids.length; i++) {
			ids[i] = services.next().getServiceID();
		}
		return ids;
	}

	@Override
	public AbstractTableEntryProvider getProvider() {
		return provider;
	}

	@Override
	public String getDisplayLabel() {
		return name;
	}

	@Override
	public String getDisplayValue() {
		IService service = getSelectedService();
		return (service != null ? service.getServiceID() : "(not applicable)");
	}

	@Override
	public String getToolipText() {
		return "[" + name + "]";
	}

	@Override
	public Image getIcon() {
		return PropertyTableWidget.IMAGE_SERVICE;
	}

	@Override
	public boolean isEditable() {
		return selectable;
	}

	@Override
	public CellEditor createCellEditor(Table table) {
		this.editor = new ComboBoxCellEditor(table, getServiceIDs(), SWT.READ_ONLY);
		return this.editor;
	}

	@Override
	public CellEditor getCellEditor() {
		return this.editor;
	}

	@Override
	public Object getCellEditorValue() {
		T selectedService = getSelectedService();
		if (selectedService == null)
			return -1;
		String selected = selectedService.getServiceID();
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
		setServiceByID(name);
	}

	public void dispose() {
		settings.removeSettingsChangedListener(settingsListener);
	}
}
