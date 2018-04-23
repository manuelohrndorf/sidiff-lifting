package org.sidiff.integration.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.sidiff.integration.preferences.util.PreferenceUtil;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.sidiff.integration.preferences"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		setDefaultPreferenceValues();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	/**
	 * Sets the default settings for the preference pages
	 * Includes matching, difference, lifting and patching settings (also for the domains Ecore and UML)
	 */
	private void setDefaultPreferenceValues() {
		IPreferenceStore store = PreferenceUtil.getInstance().getPluginPreferenceStore();
		
		// TODO: use preference initializer to initialize the preferences
		// TODO: split up these definitions over the respective plugins that contribute the setting
		// TODO: create classes with constants for the preference names / functions for retrieving them
		// TODO: some of these default values are probably no longer correct with the other changes
		store.setDefault("matchingOrder", "org.sidiff.matcher.signature.name.NamedElementMatcher");
		store.setDefault("candidatesServices", "InterModelTypeCandidates");
		store.setDefault("correspondencesServices", "MatchingModelCorrespondences");
		store.setDefault("similaritiesServices", "DefaultSimilaritiesService");
		store.setDefault("similaritiesCalculationServices", "DefaultSimilaritiesCalculationService");
		store.setDefault("recognitionEngineModes", "LIFTING");
		store.setDefault("calculateEditRuleMatch", true);
		store.setDefault("serializeEditRuleMatch", true);
		store.setDefault("executionMode", "INTERACTIVE");
		store.setDefault("patchMode", "PATCHING");
		store.setDefault("minReliability", 0);
		store.setDefault("scope", "RESOURCE");
		store.setDefault("validateModels", true);
		store.setDefault("validationMode", "MODEL_VALIDATION");
		
		//Ecore Settings defaults
		store.setDefault("http://www.eclipse.org/emf/2002/Ecore:Ecore Atomic", true);
		store.setDefault("http://www.eclipse.org/emf/2002/EcoremodifiedDetector", "org.sidiff.ecore.modifieddetector.EcoreModifiedDetector");
		store.setDefault("http://www.eclipse.org/emf/2002/EcorerecognitionRuleSorter", "EcoreRRSorter");
		store.setDefault("http://www.eclipse.org/emf/2002/EcoretechnicalDifferenceBuilderOrder", "org.sidiff.ecore.difference.technical.TechnicalDifferenceBuilderEcore");
		store.setDefault("http://www.eclipse.org/emf/2002/EcoretransformationEngine", "org.sidiff.patching.transformator.henshin.HenshinTransformationEngineImpl");
		
		//UML Settings defaults
		store.setDefault("http://www.eclipse.org/uml2/5.0.0/UML:UML_2v4_Atomic", true);
		store.setDefault("http://www.eclipse.org/uml2/5.0.0/UMLrecognitionRuleSorter", "GenericRRSorter");
		store.setDefault("http://www.eclipse.org/uml2/5.0.0/UMLtechnicalDifferenceBuilderOrder", "org.sidiff.uml2v4.difference.technical.TechnicalDifferenceBuilderUML");
		store.setDefault("http://www.eclipse.org/uml2/5.0.0/UMLtransformationEngine", "org.sidiff.patching.transformator.henshin.HenshinTransformationEngineImpl");
		
		//advanced settings defaults
		store.setDefault("mergeImports", true);
		store.setDefault("useThreadPool", true);
		store.setDefault("numberOfThreads", 32);
		store.setDefault("rulesPerThread", 10);
		store.setDefault("sortRecognitionRuleNodes", true);
		store.setDefault("rulesetReduction", true);
		store.setDefault("buildGraphPerRule", true);
		store.setDefault("detectSplitJoins", false);
	}

}
