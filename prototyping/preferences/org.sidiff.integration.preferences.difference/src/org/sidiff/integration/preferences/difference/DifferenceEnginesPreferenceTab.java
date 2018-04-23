package org.sidiff.integration.preferences.difference;

import org.sidiff.integration.preferences.AbstractEnginePreferenceTab;
import org.sidiff.integration.preferences.fieldeditors.PreferenceField;
import org.sidiff.integration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

/**
 * 
 * Class to create the tab for the difference settings.
 * @author Daniel Roedder, Robert Müller
 */
public class DifferenceEnginesPreferenceTab extends AbstractEnginePreferenceTab {

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

	@Override
	protected void createPreferenceFields() {
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
		addField(symbolicLinkHandlers);
	}

	/**
	 * @see org.sidiff.integration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 1;
	}
}
