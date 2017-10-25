package org.sidiff.vcmsintegration.preferences.lifting;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.sidiff.vcmsintegration.preferences.TabbedPreferenceFieldPage;
import org.sidiff.vcmsintegration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.NumberPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;

/**
 * Class to hold the controls for the advanced settings
 * @author Daniel Roedder
 *
 */
public class AdvancedPage extends TabbedPreferenceFieldPage implements IWorkbenchPreferencePage{

	/**
	 * Integer for the tab number
	 */
	private int tabNumber;
	
	/**
	 * {@link PreferenceField} for the use thread pool setting
	 */
	private PreferenceField useThreadPool;
	
	/**
	 * The {@link PreferenceField} for the number of threads to be used
	 */
	private PreferenceField numberOfThreads;
	
	/**
	 * The {@link PreferenceField} for the rules per thread setting
	 */
	private PreferenceField rulesPerThread;
	
	/**
	 * The {@link PreferenceField} for the sort recognition rule nodes setting
	 */
	private PreferenceField sortRecognitionRuleNodes;
	
	/**
	 * The {@link PreferenceField} for the ruleset reduction settings
	 */
	private PreferenceField rulesetReduction;
	
	/**
	 * The {@link PreferenceField} for the build graph per rule setting
	 */
	private PreferenceField buildGraphPerRule;
	
	/**
	 * the {@link PreferenceField} for the detect split joins setting
	 */
	private PreferenceField detectSplitJoins;
	
	/**
	 * 
	 * Superclass method, not needed here
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.TabbedPreferenceFieldPage#createPreferenceFields()
	 */
	@Override
	protected void createPreferenceFields() {
		tabNumber = this.addTab("Settings");
		
		useThreadPool = new CheckBoxPreferenceField("useThreadPool", "Use Thread Pool");
		numberOfThreads = new NumberPreferenceField("numberOfThreads", "Number of Threads");
		rulesPerThread = new NumberPreferenceField("rulesPerThread", "Rules per Thread");
		sortRecognitionRuleNodes = new CheckBoxPreferenceField("sortRecognitionRuleNodes", "Sort Recognition Rule Nodes");
		rulesetReduction = new CheckBoxPreferenceField("rulesetReduction", "Ruleset Reduction");
		buildGraphPerRule = new CheckBoxPreferenceField("buildGraphPerRule", "Build Graph per Rule");
		detectSplitJoins = new CheckBoxPreferenceField("detectSplitJoins", "Detect Split Joins");
		
		
		this.addField(useThreadPool, tabNumber);
		this.addField(numberOfThreads, tabNumber);
		this.addField(rulesPerThread, tabNumber);
		this.addField(sortRecognitionRuleNodes, tabNumber);
		this.addField(rulesetReduction, tabNumber);
		this.addField(buildGraphPerRule, tabNumber);
		this.addField(detectSplitJoins, tabNumber);
	}

}
