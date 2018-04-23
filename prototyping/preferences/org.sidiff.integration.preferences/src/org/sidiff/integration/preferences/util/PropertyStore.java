package org.sidiff.integration.preferences.util;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.util.SafeRunnable;

/**
 * 
 * Handles correct delegation of loading and setting properties 
 * on a resource or preferenceStore, depending on user settings
 * @author Felix Breitweiser
 */
public class PropertyStore extends EventManager implements IPreferenceStore {
	
	private static final String USE_RESOURCE_SETTINGS = "USE_RESOURCE_SETTINGS";
	
	/**
	 * true, if the resource specific settings should be used
	 */
	private boolean useResourceSettings;
	
	/**
	 * the preference store for the global settings
	 */
	private IPreferenceStore workbenchStore;
	
	/**
	 * the qualifier to identify resouce specific settings in the Resource
	 */
	private String qualifier; 
	
	/**
	 * the resource from which resource specific settings will be loaded (and saved) 
	 */
	private IResource resource;
	
	public PropertyStore(IResource resource, IPreferenceStore workbenchStore, String pageId) {
		this.workbenchStore = workbenchStore;
		this.qualifier = pageId;
		this.resource = resource;
		try {
			this.useResourceSettings = 
					Boolean.valueOf(resource.getPersistentProperty(key(USE_RESOURCE_SETTINGS)));
		} catch(CoreException e) {
			this.useResourceSettings = false;
		}
	}
	
	/**
	 * @param name the name of the key
	 * convienience Method: creates a qualifiedName from the pageId and a name
	 */
	private QualifiedName key(String name) {
		return new QualifiedName(qualifier, name);
	}
	
	/**
	 * @param use whether or not to use resource specific settings
	 * used to enable/disable usage of resource specific settings
	 * @return Returns whether or not to use resource specific settings
	 */
	public boolean useResourceSettings(boolean use) {
		try {
			resource.setPersistentProperty(key(USE_RESOURCE_SETTINGS), Boolean.toString(use));
			useResourceSettings = use;
		} catch (CoreException e) {}
		return useResourceSettings;
	}
	
	/**
	 * @return true if resrouce specific settings will be used
	 */
	public boolean useResourceSettings() {
		return useResourceSettings;
	}
	
	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultString(java.lang.String)
	 */
	@Override
	public String getDefaultString(String name) {
	  return workbenchStore.getDefaultString(name);
	}	

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultBoolean(java.lang.String)
	 */
	@Override
	public boolean getDefaultBoolean(String name) {
		return workbenchStore.getDefaultBoolean(name);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultDouble(java.lang.String)
	 */
	@Override
	public double getDefaultDouble(String name) {
		return workbenchStore.getDefaultDouble(name);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultFloat(java.lang.String)
	 */
	@Override
	public float getDefaultFloat(String name) {
		return workbenchStore.getDefaultFloat(name);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultInt(java.lang.String)
	 */
	@Override
	public int getDefaultInt(String name) {
		return workbenchStore.getDefaultInt(name);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getDefaultLong(java.lang.String)
	 */
	@Override
	public long getDefaultLong(String name) {
		return workbenchStore.getDefaultLong(name);
	}
	
	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getString(java.lang.String)
	 * loads a string from the resource if resource specific settings are being used
	 * falls back to the workbenchStore otherwise, or if the setting is not yet present in the resource
	 */
	@Override
	public String getString(String name) {
		try {
			boolean has = resource.getPersistentProperties().containsKey(key(name));
			if(useResourceSettings && has) {
				return resource.getPersistentProperty(key(name));
			}
		} catch (CoreException e) {}
		return workbenchStore.getString(name);
		
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getBoolean(java.lang.String)
	 * loads a boolean from the resource if resource specific settings are being used
	 * falls back to the workbenchStore otherwise, or if the setting is not yet present in the resource
	 */
	@Override
	public boolean getBoolean(String name) {
		try {
			boolean has = resource.getPersistentProperties().containsKey(key(name));
			if(useResourceSettings && has) {
				return Boolean.valueOf(resource.getPersistentProperty(key(name)));
			}
		} catch (CoreException e) {}
		return workbenchStore.getBoolean(name);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getDouble(java.lang.String)
	 * loads a double from the resource if resource specific settings are being used
	 * falls back to the workbenchStore otherwise, or if the setting is not yet present in the resource
	 */
	@Override
	public double getDouble(String name) {
		try {
			boolean has = resource.getPersistentProperties().containsKey(key(name));
			if(useResourceSettings && has) {
				return Double.valueOf(resource.getPersistentProperty(key(name)));
			}
		} catch (CoreException e) {}
		return workbenchStore.getDouble(name);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getFloat(java.lang.String)
	 * loads a float from the resource if resource specific settings are being used
	 * falls back to the workbenchStore otherwise, or if the setting is not yet present in the resource
	 */
	@Override
	public float getFloat(String name) {
		try {
			boolean has = resource.getPersistentProperties().containsKey(key(name));
			if(useResourceSettings && has) {
				return Float.valueOf(resource.getPersistentProperty(key(name)));
			}
		} catch (CoreException e) {}
		return workbenchStore.getFloat(name);
	}
	
	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getInt(java.lang.String)
	 * loads a integer from the resource if resource specific settings are being used
	 * falls back to the workbenchStore otherwise, or if the setting is not yet present in the resource
	 */
	@Override
	public int getInt(String name) {
		try {
			boolean has = resource.getPersistentProperties().containsKey(key(name));
			if(useResourceSettings && has) {
				return Integer.valueOf(resource.getPersistentProperty(key(name)));
			}
		} catch (CoreException e) {}
		return workbenchStore.getInt(name);
	}
	
	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#getLong(java.lang.String)
	 * loads a long from the resource if resource specific settings are being used
	 * falls back to the workbenchStore otherwise, or if the setting is not yet present in the resource
	 */
	@Override
	public long getLong(String name) {
		try {
			boolean has = resource.getPersistentProperties().containsKey(key(name));
			if(useResourceSettings && has) {
				return Long.valueOf(resource.getPersistentProperty(key(name)));
			}
		} catch (CoreException e) {}
		return workbenchStore.getLong(name);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#isDefault(java.lang.String)
	 */
	@Override
	public boolean isDefault(String name) {
		try {
			return !resource.getPersistentProperties().containsKey(key(name)) && workbenchStore.isDefault(name);
		} catch (CoreException e) {}
		return workbenchStore.isDefault(name);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#needsSaving()
	 */
	@Override
	public boolean needsSaving() {
		return false;
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#putValue(java.lang.String, java.lang.String)
	 * saves a setting in the resource store
	 * falls back to the workbench store if resource settings should not be used
	 */
	@Override
	public void putValue(String name, String value) {
		try {
			if(useResourceSettings) {
				resource.setPersistentProperty(key(name), value);
				return;
			}
		}catch(CoreException e) {}
		workbenchStore.putValue(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, double)
	 */
	@Override
	public void setDefault(String name, double value) {
		workbenchStore.setDefault(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, float)
	 */
	@Override
	public void setDefault(String name, float value) {
		workbenchStore.setDefault(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, int)
	 */
	@Override
	public void setDefault(String name, int value) {
		workbenchStore.setDefault(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, long)
	 */
	@Override
	public void setDefault(String name, long value) {
		workbenchStore.setDefault(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, java.lang.String)
	 */
	@Override
	public void setDefault(String name, String value) {
		workbenchStore.setDefault(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setDefault(java.lang.String, boolean)
	 */
	@Override
	public void setDefault(String name, boolean value) {
		workbenchStore.setDefault(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setToDefault(java.lang.String)
	 */
	@Override
	public void setToDefault(String name) {
		try {
			if(useResourceSettings) {
				resource.setPersistentProperty(key(name), workbenchStore.getDefaultString(name));
				return;
			}
		} catch(CoreException e) {}
		workbenchStore.setToDefault(name);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, double)
	 */
	@Override
	public void setValue(String name, double value) {
		try {
			if(useResourceSettings) {
				resource.setPersistentProperty(key(name), Double.toString(value));
				return;
			}
		} catch(CoreException e) {}
		workbenchStore.setValue(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, float)
	 */
	@Override
	public void setValue(String name, float value) {
		try {
			if(useResourceSettings) {
				resource.setPersistentProperty(key(name), Float.toString(value));
				return;
			}
		} catch(CoreException e) {}
		workbenchStore.setValue(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, int)
	 */
	@Override
	public void setValue(String name, int value) {
		try {
			if(useResourceSettings) {
				resource.setPersistentProperty(key(name), Integer.toString(value));
				return;
			}
		} catch(CoreException e) {}
		workbenchStore.setValue(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, long)
	 */
	@Override
	public void setValue(String name, long value) {
		try {
			if(useResourceSettings) {
				resource.setPersistentProperty(key(name), Long.toString(value));
				return;
			}
		} catch(CoreException e) {}
		workbenchStore.setValue(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, java.lang.String)
	 */
	@Override
	public void setValue(String name, String value) {
		try {
			if(useResourceSettings) {
				resource.setPersistentProperty(key(name), value);
				return;
			}
		} catch(CoreException e) {}
		workbenchStore.setValue(name, value);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#setValue(java.lang.String, boolean)
	 */
	@Override
	public void setValue(String name, boolean value) {
		try {
			if(useResourceSettings) {
				resource.setPersistentProperty(key(name), Boolean.toString(value));
				return;
			}
		} catch(CoreException e) {}
		workbenchStore.setValue(name, value);
	}
	
	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#addPropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
	 */
	@Override
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		super.addListenerObject(listener);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String name) {
		try {
			return resource.getPersistentProperties().containsKey(key(name)) || workbenchStore.contains(name);
		} catch (CoreException e) {}
		return workbenchStore.contains(name);
	}

	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#firePropertyChangeEvent(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) {
		final Object[] finalListeners = getListeners();
		// Do we need to fire an event?
		if (finalListeners.length > 0
				&& (oldValue == null || !oldValue.equals(newValue))) {
			final PropertyChangeEvent pe = new PropertyChangeEvent(this, name,
					oldValue, newValue);
			for (int i = 0; i < finalListeners.length; ++i) {
				final IPropertyChangeListener l = (IPropertyChangeListener) finalListeners[i];
				SafeRunnable.run(new SafeRunnable(JFaceResources
						.getString("PreferenceStore.changeError")) { //$NON-NLS-1$
							@Override
							public void run() {
								l.propertyChange(pe);
							}
						});
			}
		}
	}
	
	/**
	 * @see org.eclipse.jface.preference.IPreferenceStore#removePropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
	 */
	@Override
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		super.removeListenerObject(listener);
	}
}