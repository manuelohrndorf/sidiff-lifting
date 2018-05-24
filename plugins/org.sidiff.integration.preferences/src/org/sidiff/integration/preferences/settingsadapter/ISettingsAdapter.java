package org.sidiff.integration.preferences.settingsadapter;

import java.util.Set;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.jface.preference.IPreferenceStore;
import org.sidiff.common.settings.AbstractSettings;

/**	
 * A settings adapter loads preferences from a {@link IPreferenceStore preference store}
 * and sets the field of {@link AbstractSettings settings} according to the preferences.
 * @author Robert Müller
 *
 */
public interface ISettingsAdapter {

	String EXTENSION_POINT_ID = "org.sidiff.integration.preferences.settingsAdapter";
	String EXTENSION_POINT_ATTRIBUTE_PIPELINE_STEP = "pipelineStep";
	String EXTENSION_POINT_ATTRIBUTE_CLASS = "class";

	/**
	 * Returns whether the given {@link AbstractSettings settings} can be adapted by this settings adapter.
	 * No further methods will be called if this method returns <code>false</code>.
	 * @param settings the settings
	 * @return <code>true</code>, if the settings can be adapted by this adapter, <code>false</code> otherwise
	 */
	boolean canAdapt(AbstractSettings settings);

	/**
	 * Adapts the given {@link AbstractSettings settings} with the previously {@link #load(IPreferenceStore) loaded} preferences.
	 * @param settings the settings
	 */
	void adapt(AbstractSettings settings);

	/**
	 * Loads preference values from the given preference store. Will be called before {@link #adapt(AbstractSettings) adapt}.
	 * @param store the preference store
	 */
	void load(IPreferenceStore store);

	/**
	 * Sets default values for all preferences managed by this adapter using the given preference store.
	 * Called independently of all other methods.
	 * @param store the preference store
	 */
	void initializeDefaults(IPreferenceStore store);

	/**
	 * Called before {@link ISettingsAdapter#load(IPreferenceStore)} with the document types for the current operation.
	 * @param documentTypes the document types' nsURI
	 */
	void setDocumentTypes(Set<String> documentTypes);

	/**
	 * Sets the diagnostic chain to add diagnostics to.
	 * @param diagnosticChain the diagnostic chain
	 */
	void setDiagnosticChain(DiagnosticChain diagnosticChain);
}
