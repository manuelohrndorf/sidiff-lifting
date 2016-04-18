package org.sidiff.difference.ui.widgets.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.ui.Activator;

/**
 * @author Lukas
 *
 */
public class PropertyTableWidget implements IWidget, IWidgetValidation, IPageChangedListener {

	/**
	 * A provider was added or removed
	 */
	public static final String PROPERTY_PROVIDER_ADD_REMOVE = "PROVIDER_ADD_REMOVE";
	/**
	 * An entry was added or removed
	 */
	public static final String PROPERTY_ENTRY_ADD_REMOVE = "ENTRY_ADD_REMOVE";
	/**
	 * An entry was changed
	 * oldValue: Object[]{Source table entry, oldValue} newValue:
	 * Object[]{Source table entry, newValue}
	 */
	public static final String PROPERTY_ENTRY_PROPERTY_CHANGE = "ENTRY_PROPERTY_CHANGE";

	/*
	 * Image constants
	 */
	public static Image IMAGE_CONFIG = null;
	public static Image IMAGE_SERVICE = null;
	private static Image IMAGE_EMPTY = null;
	private static Image IMAGE_WARN = null;
	private static Image IMAGE_ERROR = null;
	private static Image IMAGE_HEADER = null;
	private static Color COLOR_WARN = null;
	private static Color COLOR_ERROR = null;

	private final boolean showEmptyProviders;

	private final List<AbstractTableEntryProvider> providers = new ArrayList<AbstractTableEntryProvider>();
	private final Map<AbstractTableEntryProvider, HeaderTableEntry> headers = new HashMap<AbstractTableEntryProvider, HeaderTableEntry>();
	/**
	 * List entries that contain the data, e.g.
	 * {@link ConfigureOptionTableEntry}
	 */
	private final List<ITableEntry> entries = new ArrayList<ITableEntry>();
	/**
	 * Container
	 */
	private Composite container;
	/**
	 * The main table
	 */
	private Table table;
	/**
	 * TableVierwer of the main table
	 */
	private TableViewer tableViewer;
	/**
	 * Lock for asynchronous adding/removing of providers and entries
	 */
	private final ReentrantLock entryLock = new ReentrantLock();
	/**
	 * Externals waiting for changes
	 */
	private final ListenerList propertyChangeListeners = new ListenerList();
	/**
	 * Listener
	 */
	
	private final IPropertyChangeListener propertyChangeListener = new IPropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getSource() instanceof ITableEntry) {
				tableViewer.update((ITableEntry) event.getSource(), null);
				firePropertyChange(PROPERTY_ENTRY_PROPERTY_CHANGE,
						new Object[] { event.getSource(), event.getOldValue() },
						new Object[] { event.getSource(), event.getNewValue() });
			}
		}
	};

	private final ITableEntryProviderListener providerListener = new ITableEntryProviderListener() {

		@Override
		public void onTableEntryRemoved(AbstractTableEntryProvider sender, ITableEntry entry) {
			entryLock.lock();
			try {
				if (!providers.contains(sender) || !entries.contains(entry))
					return;
				entry.removePropertyChangeListener(propertyChangeListener);
				entries.remove(entry);
				if (sender.getEntries().isEmpty() && !showEmptyProviders) {
					HeaderTableEntry header = headers.get(sender);
					if (header != null) {
						entries.remove(header);
						headers.remove(sender);
					}
				}
			} finally {
				entryLock.unlock();
			}
			refreshViewer();
			firePropertyChange(PROPERTY_ENTRY_ADD_REMOVE, null, entry);
		}

		@Override
		public void onTableEntryAdded(AbstractTableEntryProvider sender, ITableEntry entry) {
			entryLock.lock();
			try {
				if (!providers.contains(sender) || entries.contains(entry))
					return;
				// TODO [LM] Das erneute Erstellen des Headers ist fehlerhaft:
				// Position der im Nachhinein erstellten Entries stimmt nicht
				// Get header
				HeaderTableEntry header = headers.get(sender);
				// If no header present create one
				if (header == null) {
					header = new HeaderTableEntry(sender);
					// Get header of next entry in provider list
					int providerPos = providers.indexOf(sender);
					int headerPos = -1;
					for (int i = providerPos + 1; i < providers.size(); i++) {
						HeaderTableEntry nextHeader = headers.get(providers.get(i));
						if (nextHeader != null) {
							headerPos = entries.indexOf(nextHeader) + 1;
							break;
						}
					}
					headers.put(sender, header);
					if (headerPos >= 0 && headerPos < entries.size()) {
						entries.add(headerPos, header);
					} else {
						entries.add(header);
					}
				}
				// Insert element in table at the position the provider
				// wants it to have
				int index = sender.getEntries().indexOf(entry);
				// Safeguard against race conditions
				if (index < 0)
					return;
				// Find right position and add
				int newElementsPos = entries.indexOf(header) + index + 1;
				if (newElementsPos >= 0 && newElementsPos < entries.size()) {
					entries.add(newElementsPos, entry);
				} else {
					entries.add(entry);
				}
				entry.addPropertyChangeListener(propertyChangeListener);
			} finally {
				entryLock.unlock();
			}
			refreshViewer();
			firePropertyChange(PROPERTY_ENTRY_ADD_REMOVE, null, entry);
		}

		@Override
		public void onPropertiesChanged(AbstractTableEntryProvider sender) {
			HeaderTableEntry header;
			entryLock.lock();
			try {
				header = headers.get(sender);
			} finally {
				entryLock.unlock();
			}
			if (header != null)
				tableViewer.update(header, null);
		}
	};

	/**
	 * See {@link PropertyTableWidget}
	 */
	public PropertyTableWidget(boolean showEmptyProviders) {
		if (!showEmptyProviders)
			throw new UnsupportedOperationException("!showEmptyProviders is faulty");
		this.showEmptyProviders = showEmptyProviders;
		/*
		 * Load images
		 */
		if (IMAGE_EMPTY == null)
			IMAGE_EMPTY = loadImage("empty");
		if (IMAGE_WARN == null)
			IMAGE_WARN = loadImage("warn");
		if (IMAGE_ERROR == null)
			IMAGE_ERROR = loadImage("error");
		if (IMAGE_CONFIG == null)
			IMAGE_CONFIG = loadImage("config");
		if (IMAGE_SERVICE == null)
			IMAGE_SERVICE = loadImage("service");
		if (IMAGE_HEADER == null)
			IMAGE_HEADER = loadImage("header");
	}

	private void firePropertyChange(final String property, final Object oldValue, final Object newValue) {
		Object[] array = propertyChangeListeners.getListeners();
		for (int i = 0; i < array.length; i++) {
			final IPropertyChangeListener l = (IPropertyChangeListener) array[i];
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.propertyChange(new PropertyChangeEvent(PropertyTableWidget.this, property, oldValue, newValue));
				}
			});
		}
	}

	/**
	 * Helper function for loading a PNG image
	 * 
	 * @param name
	 *            Name without extension
	 * @return Image or null on error
	 */
	private Image loadImage(String name) {
		try {
			return Activator.getImageDescriptor(name + ".png").createImage();
		} catch (Exception e) {
			return null;
		}
	}

	public void addPropertyChangeListener(IPropertyChangeListener listener) {
		propertyChangeListeners.add(listener);
	}

	public void removePropertyChangeListener(IPropertyChangeListener listener) {
		propertyChangeListeners.remove(listener);
	}

	public void addProvider(AbstractTableEntryProvider provider) {
		if (providers.contains(provider))
			return;
		List<ITableEntry> newEntries = provider.getEntries();
		entryLock.lock();
		try {
			providers.add(provider);
			if (!newEntries.isEmpty() || this.showEmptyProviders) {
				HeaderTableEntry header = new HeaderTableEntry(provider);
				headers.put(provider, header);
				entries.add(header);
			}
			entries.addAll(newEntries);
			provider.addTableEntryProviderListener(this.providerListener);
			for (ITableEntry entry : newEntries){
				entry.addPropertyChangeListener(propertyChangeListener);
			}
		} finally {
			entryLock.unlock();
		}
		refreshViewer();
		firePropertyChange(PROPERTY_PROVIDER_ADD_REMOVE, null, provider);
		for (ITableEntry newEntry : newEntries) {
			firePropertyChange(PROPERTY_ENTRY_ADD_REMOVE, null, newEntry);
		}
	}

	public void removeProvider(AbstractTableEntryProvider provider) {
		List<ITableEntry> toDelete = new ArrayList<ITableEntry>();
		HeaderTableEntry header = null;
		entryLock.lock();
		try {
			for (ITableEntry entry : entries) {
				if (entry.getProvider().equals(provider)) {
					entry.removePropertyChangeListener(propertyChangeListener);
					toDelete.add(entry);
				}
			}
			entries.removeAll(toDelete);
			header = headers.get(provider);
			headers.remove(provider);
			provider.removeTableEntryProviderListener(this.providerListener);
			providers.remove(provider);
		} finally {
			entryLock.unlock();
		}
		refreshViewer();
		
		for (ITableEntry delete : toDelete) {
			// No event for the header
			if (!delete.equals(header)) {
				firePropertyChange(PROPERTY_ENTRY_ADD_REMOVE, delete, null);
			}
		}
		firePropertyChange(PROPERTY_PROVIDER_ADD_REMOVE, provider, null);
	}

	/**
	 * @wbp.parser.entryPoint Creates the control
	 */
	@Override
	public Composite createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}
		/*
		 * Table creation
		 */
		Composite rulebaseComposite = new Composite(container, SWT.NONE);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 100;
			rulebaseComposite.setLayoutData(data);
		}
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		rulebaseComposite.setLayout(tableColumnLayout);
		tableViewer = new TableViewer(rulebaseComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 200;
			table.setLayoutData(data);
		}
		/*
		 * Content provider
		 */
		tableViewer.setContentProvider(new ListContentProvider());
		{
			/*
			 * Key column
			 */
			TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.NONE, 0);
			tableColumnLayout.setColumnData(column.getColumn(), new ColumnWeightData(100));
			column.getColumn().setText("Option");
			column.getColumn().setToolTipText("Name of the option");
			column.setLabelProvider(new CellLabelProvider() {
				@Override
				public void update(ViewerCell cell) {
					Object element = cell.getElement();
					if (element instanceof ITableEntry) {
						ITableEntry entry = (ITableEntry) element;
						cell.setText(entry.getDisplayLabel());
						Image icon = entry.getIcon();
						if (icon == null) {
							if (entry instanceof HeaderTableEntry) {
								icon = IMAGE_HEADER;
							} else {
								icon = IMAGE_EMPTY;
							}
						}
						cell.setImage(icon);
					}
					if (element instanceof HeaderTableEntry) {
						cell.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
					} else {
						cell.setFont(JFaceResources.getFontRegistry().get(JFaceResources.DEFAULT_FONT));
					}
				}

				@Override
				public String getToolTipText(Object element) {
					if (element instanceof ITableEntry) {
						return ((ITableEntry) element).getToolipText();
					} else {
						return "";
					}
				}

				@Override
				public Image getToolTipImage(Object object) {
					return null;
				}

			});
		}
		/*
		 * Value column
		 */
		{
			TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.NONE, 1);
			tableColumnLayout.setColumnData(column.getColumn(), new ColumnWeightData(100));
			column.getColumn().setText("Value");
			column.getColumn().setToolTipText("Value to set for this option");
			column.setLabelProvider(new CellLabelProvider() {
				@Override
				public void update(ViewerCell cell) {
					Object element = cell.getElement();
					Color background = null;
					Image image = null;
					String text = "";
					if (element instanceof IWidgetValidation) {
						// Make sure we have the newest validation message
						((IWidgetValidation) element).validate();
						// ...
						ValidationMessage validationMessage = ((IWidgetValidation) element).getValidationMessage();
						if (validationMessage != null && ValidationType.WARNING.equals(validationMessage.getType())) {
							if (IMAGE_WARN != null) {
								image = IMAGE_WARN;
							} else {
								background = COLOR_WARN;
							}
						} else if (validationMessage != null
								&& ValidationType.ERROR.equals(validationMessage.getType())) {
							if (IMAGE_ERROR != null) {
								image = IMAGE_ERROR;
							} else {
								background = COLOR_ERROR;
							}
						}

					}
					if (element instanceof ITableEntry) {
						ITableEntry entry = (ITableEntry) element;
						text = entry.getDisplayValue();
					}
					if (element instanceof HeaderTableEntry) {
						cell.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
					} else {
						cell.setFont(JFaceResources.getFontRegistry().get(JFaceResources.DEFAULT_FONT));
					}
					cell.setText(text);
					cell.setImage(image);
					cell.setBackground(background);
				}

				@Override
				public String getToolTipText(Object element) {
					String tooltip = "";
					if (element instanceof ITableEntry) {
						tooltip = ((ITableEntry) element).getToolipText();
					}
					if (element instanceof IWidgetValidation) {
						ValidationMessage validationMessage = ((IWidgetValidation) element).getValidationMessage();
						String message = (validationMessage != null ? validationMessage.getMessage() : "");
						tooltip = (message != null && !message.isEmpty() ? message : tooltip);
					}
					return tooltip;
				}

				@Override
				public Image getToolTipImage(Object object) {
					return null;
				}

			});
			column.setEditingSupport(new PropertyTableEditingSupport(tableViewer));
		}
		/*
		 * Activate Tooltips
		 */
		ColumnViewerToolTipSupport.enableFor(tableViewer);
		/*
		 * Fill the table
		 */
		tableViewer.setInput(entries);
		tableViewer.refresh();
		/*
		 * Done
		 */
		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		container.setLayoutData(layoutData);
	}

	@Override
	public boolean validate() {
		for (Object obj : entries) {
			if (obj instanceof IWidgetValidation) {
				if (!((IWidgetValidation) obj).validate())
					return false;
			}
		}
		return true;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		boolean warning = false, error = false;
		for (Object obj : entries) {
			if (obj instanceof IWidgetValidation) {
				ValidationMessage validationMessage = ((IWidgetValidation) obj).getValidationMessage();
				if (validationMessage != null) {
					if (ValidationType.ERROR.equals(validationMessage.getType()))
						error = true;
					if (ValidationType.WARNING.equals(validationMessage.getType()))
						warning = true;
				}
			}
		}
		if (error) {
			return new ValidationMessage(ValidationType.ERROR, "The properties contain one or more errors");
		} else if (warning) {
			return new ValidationMessage(ValidationType.WARNING, "The properties contain one or more warnings");
		} else {
			return new ValidationMessage(ValidationType.OK, "");
		}
	}

	/**
	 * WORKAROUND: This method forces the table to reflect structural changes and repaint all elements
	 */
	private void refreshViewer(){
		if (tableViewer != null){
			tableViewer.setInput(new LinkedList<ITableEntry>());
			tableViewer.setInput(entries);
		}
	}
	
	@Override
	public void pageChanged(PageChangedEvent event) {
		//Do nothing yet
	}
}