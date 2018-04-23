package org.sidiff.integration.preferences.difference;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

/**
 * 
 * Class to create the tab for the difference settings.
 * @author Daniel Roedder, Robert Müller
 */
public class DifferenceEnginesPreferenceTab implements ISiDiffEnginesPreferenceTab {
	
	/**
	 * List to hold all {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField}
	 */
	private List<PreferenceField> fieldList;
	
	/**
	 * The {@link IPreferenceStore} to be used
	 */
	private IPreferenceStore store;
	
	/**
	 * The {@link org.sidiff.integration.preferences.fieldeditors.PreferenceField} for the symbolic link handlers
	 */
	private PreferenceField symbolicLinkHandlers;

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Difference";
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {

	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();

		symbolicLinkHandlers = RadioBoxPreferenceField.create(
				"symbolicLinkHandlers",
				"Symbolic Link Handlers",
				SymbolicLinkHandlerUtil.getAvailableSymbolicLinkHandlers(),
				new IPreferenceValueConverter<ISymbolicLinkHandler>() {
					@Override
					public String getValue(ISymbolicLinkHandler value) {
						return value.getKey();
					}
					@Override
					public String getLabel(ISymbolicLinkHandler value) {
						return value.getName();
					}
				});
		
		fieldList.add(symbolicLinkHandlers);
		
		for (PreferenceField field : fieldList) {
			field.setPreferenceStore(store);
		}
		
		return fieldList;
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffEnginesPreferenceTab#setPreferenceStore(org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void setPreferenceStore(IPreferenceStore store) {
		this.store = store;

	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 1;
	}
}
