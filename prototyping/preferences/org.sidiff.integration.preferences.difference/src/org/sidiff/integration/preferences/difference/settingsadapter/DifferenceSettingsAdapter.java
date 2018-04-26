package org.sidiff.integration.preferences.difference.settingsadapter;

import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.integration.preferences.interfaces.ISettingsAdapter;

/**
 * 
 * @author Robert Müller
 *
 */
public class DifferenceSettingsAdapter implements ISettingsAdapter {

	public static final String KEY_MERGE_IMPORTS = "mergeImports";
	public static final String KEY_UNMERGE_IMPORTS = "unmergeImports";

	private boolean mergeImports;
	private boolean unmergeImports;

	@Override
	public boolean canAdapt(AbstractSettings settings) {
		return settings instanceof DifferenceSettings;
	}

	@Override
	public void adapt(AbstractSettings settings) {
		DifferenceSettings diffSettings = (DifferenceSettings)settings;
		diffSettings.setMergeImports(mergeImports);
		diffSettings.setUnmergeImports(unmergeImports);
	}

	@Override
	public void load(IPreferenceStore store) {
		mergeImports = store.getBoolean(KEY_MERGE_IMPORTS);
		unmergeImports = store.getBoolean(KEY_UNMERGE_IMPORTS);
	}

	@Override
	public void initializeDefaults(IPreferenceStore store) {
		store.setDefault(KEY_MERGE_IMPORTS, true);
		store.setDefault(KEY_UNMERGE_IMPORTS, true);
	}

	@Override
	public int getPosition() {
		return 20;
	}
}
