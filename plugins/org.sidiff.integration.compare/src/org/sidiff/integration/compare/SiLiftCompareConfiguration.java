package org.sidiff.integration.compare;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ICompareContainer;
import org.eclipse.compare.ICompareInputLabelProvider;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Image;
import org.sidiff.integration.compare.internal.CompareIntegrationPlugin;

/**
 * @author rmueller
 */
public class SiLiftCompareConfiguration extends CompareConfiguration {

	public static final String DISPLAY_MODE = CompareIntegrationPlugin.ID + ".DISPLAY_MODE";
	public static final String ADAPTER_FACTORY = CompareIntegrationPlugin.ID + ".ADAPTER_FACTORY";
	public static final String DIFFERENCER = CompareIntegrationPlugin.ID + ".DIFFERENCER";

	private static Map<CompareConfiguration, SiLiftCompareConfiguration> instances = new HashMap<>();

	public static SiLiftCompareConfiguration wrap(CompareConfiguration config) {
		SiLiftCompareConfiguration wrapper = instances.get(config);
		if(wrapper == null) {
			wrapper = new SiLiftCompareConfiguration(config);
			instances.put(config, wrapper);
		}
		return wrapper;
	}

	private final CompareConfiguration delegate;
	private final ListenerList<IPropertyChangeListener> listeners;
	private final IPropertyChangeListener propertyChangeListener;

	private SiLiftCompareConfiguration(CompareConfiguration delegate) {
		this.listeners = new ListenerList<>();
		this.delegate = delegate;
		this.propertyChangeListener = new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				fireChange(event.getProperty(), event.getOldValue(), event.getNewValue());
			}
		};
		this.delegate.addPropertyChangeListener(propertyChangeListener);
		initializeDefaultValues();
	}

	private void initializeDefaultValues() {
		setProperty(DISPLAY_MODE, DisplayMode.getDefault());
		setProperty(ADAPTER_FACTORY, new SiLiftCompareAdapterFactory());
		setProperty(DIFFERENCER, new SiLiftCompareDifferencer(this));
	}


	public DisplayMode getDisplayMode() {
		return (DisplayMode)getProperty(DISPLAY_MODE);
	}

	public void setDisplayMode(DisplayMode displayMode) {
		setProperty(DISPLAY_MODE, displayMode);
	}

	public AdapterFactory getAdapterFactory() {
		return (AdapterFactory)getProperty(ADAPTER_FACTORY);
	}

	public void setAdapterFactory(AdapterFactory adapterFactory) {
		setProperty(ADAPTER_FACTORY, adapterFactory);
	}

	public SiLiftCompareDifferencer getDifferencer() {
		return (SiLiftCompareDifferencer)getProperty(DIFFERENCER);
	}

	public void setDifferencer(SiLiftCompareDifferencer differencer) {
		setProperty(DIFFERENCER, differencer);
	}


	@Override
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		listeners.remove(listener);
	}

	protected void fireChange(String propertyName, Object oldValue, Object newValue) {
		PropertyChangeEvent event = null;
		Object[] listenerObjects = listeners.getListeners();
		if (listenerObjects != null) {
			for(Object object : listenerObjects) {
				IPropertyChangeListener l = (IPropertyChangeListener)object;
				if (event == null) {
					event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
				}
				l.propertyChange(event);
			}
		}
	}

	@Override
	public void dispose() {
		delegate.removePropertyChangeListener(propertyChangeListener);
		delegate.dispose();
		// remove the saved instance
		instances.remove(delegate);
	}


	//
	// delegate methods for CompareConfiguration

	@Override
	public IPreferenceStore getPreferenceStore() {
		return delegate.getPreferenceStore();
	}

	@Override
	public Image getImage(int kind) {
		return delegate.getImage(kind);
	}

	@Override
	public Image getImage(Image base, int kind) {
		return delegate.getImage(base, kind);
	}

	@Override
	public boolean isMirrored() {
		return delegate.isMirrored();
	}

	@Override
	public void setProperty(String key, Object newValue) {
		if(delegate == null)
			return;
		delegate.setProperty(key, newValue);
	}

	@Override
	public Object getProperty(String key) {
		return delegate.getProperty(key);
	}

	@Override
	public void setAncestorLabel(String label) {
		delegate.setAncestorLabel(label);
	}

	@Override
	public String getAncestorLabel(Object element) {
		return delegate.getAncestorLabel(element);
	}

	@Override
	public void setAncestorImage(Image image) {
		delegate.setAncestorImage(image);
	}

	@Override
	public Image getAncestorImage(Object element) {
		return delegate.getAncestorImage(element);
	}

	@Override
	public void setLeftEditable(boolean editable) {
		delegate.setLeftEditable(editable);
	}

	@Override
	public boolean isLeftEditable() {
		return delegate.isLeftEditable();
	}

	@Override
	public void setLeftLabel(String label) {
		delegate.setLeftLabel(label);
	}

	@Override
	public String getLeftLabel(Object element) {
		return delegate.getLeftLabel(element);
	}

	@Override
	public void setLeftImage(Image image) {
		delegate.setLeftImage(image);
	}

	@Override
	public Image getLeftImage(Object element) {
		return delegate.getLeftImage(element);
	}

	@Override
	public void setRightEditable(boolean editable) {
		delegate.setRightEditable(editable);
	}

	@Override
	public boolean isRightEditable() {
		return delegate.isRightEditable();
	}

	@Override
	public void setRightLabel(String label) {
		delegate.setRightLabel(label);
	}

	@Override
	public String getRightLabel(Object element) {
		return delegate.getRightLabel(element);
	}

	@Override
	public void setRightImage(Image image) {
		delegate.setRightImage(image);
	}

	@Override
	public Image getRightImage(Object element) {
		return delegate.getRightImage(element);
	}

	@Override
	public ICompareContainer getContainer() {
		return delegate.getContainer();
	}

	@Override
	public void setContainer(ICompareContainer container) {
		delegate.setContainer(container);
	}

	@Override
	public ICompareInputLabelProvider getLabelProvider() {
		return delegate.getLabelProvider();
	}

	@Override
	public void setLabelProvider(ICompareInput input, ICompareInputLabelProvider labelProvider) {
		delegate.setLabelProvider(input, labelProvider);
	}

	@Override
	public void setDefaultLabelProvider(ICompareInputLabelProvider labelProvider) {
		delegate.setDefaultLabelProvider(labelProvider);
	}

	@Override
	public void setChangeIgnored(int kind, boolean ignored) {
		delegate.setChangeIgnored(kind, ignored);
	}

	@Override
	public boolean isChangeIgnored(int kind) {
		return delegate.isChangeIgnored(kind);
	}
}
