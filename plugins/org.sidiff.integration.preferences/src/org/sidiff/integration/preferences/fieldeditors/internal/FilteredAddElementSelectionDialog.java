package org.sidiff.integration.preferences.fieldeditors.internal;

import java.util.Collection;
import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.sidiff.integration.preferences.PreferencesPlugin;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * @author rmueller
 */
public class FilteredAddElementSelectionDialog<T> extends FilteredItemsSelectionDialog {

	private static final String SETTINGS = FilteredAddElementSelectionDialog.class.getCanonicalName();

	private Class<T> elementType;
	private Collection<T> selectableValues;
	private IPreferenceValueConverter<? super T> valueConverter;

	public FilteredAddElementSelectionDialog(Shell shell, Class<T> elementType, Collection<T> selectableValues,
			IPreferenceValueConverter<? super T> valueConverter) {
		super(shell);
		this.elementType = elementType;
		this.selectableValues = selectableValues;
		this.valueConverter = valueConverter;
		setListLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if(elementType.isInstance(element)) {
					return valueConverter.getLabel(elementType.cast(element));					
				}
				return "null";
			}
		});
		setInitialPattern("**");
	}

	@Override
	protected Control createExtendedContentArea(Composite parent) {
		return null;
	}

	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = PreferencesPlugin.getDefault().getDialogSettings().getSection(SETTINGS);
		if (settings == null) {
			settings = PreferencesPlugin.getDefault().getDialogSettings().addNewSection(SETTINGS);
		}
		return settings;
	}

	@Override
	protected IStatus validateItem(Object item) {
		return Status.OK_STATUS;
	}

	@Override
	protected ItemsFilter createFilter() {
		return new ItemsFilter() {
			@Override
			public boolean matchItem(Object item) {
				return matches(getElementName(item));
			}
			@Override
			public boolean isConsistentItem(Object item) {
				return true;
			}
		};
	}

	@Override
	protected Comparator<T> getItemsComparator() {
		return Comparator.comparing(valueConverter::getLabel);
	}

	@Override
	protected void fillContentProvider(AbstractContentProvider contentProvider, ItemsFilter itemsFilter,
			IProgressMonitor progressMonitor) throws CoreException {

		for(T value : selectableValues) {
			contentProvider.add(value, itemsFilter);
		}
	}

	@Override
	public String getElementName(Object item) {
		return valueConverter.getLabel(elementType.cast(item));
	}
}
