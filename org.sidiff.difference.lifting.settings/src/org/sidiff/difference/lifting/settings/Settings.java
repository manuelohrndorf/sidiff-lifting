package org.sidiff.difference.lifting.settings;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.rulebase.util.RuleBaseUtil;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.util.TechnicalDifferenceBuilderUtil;
import org.silift.common.util.emf.Scope;

public abstract class Settings {
	
	/**
	 * The {@link Scope} of the comparison.
	 */
	private Scope scope;

	/**
	 * The Matcher for model comparison.
	 */
	private IMatcher matcher;
	
	/**
	 * The Technical Difference Builder to use. ({@link ITechnicalDifferenceBuilder}) 
	 */
	private ITechnicalDifferenceBuilder techBuilder;
	
	/**
	 * List of active Rulebases.
	 */
	private Set<IRuleBase> ruleBases;
	
	/**
	 * Creates a thread pool that reuses a fixed number of threads for the
	 * operation detection (Lifting). (Default: <code>true</code>)
	 */
	private boolean useThreadPool = true;
	
	/**
	 * Number of recognizer threads in the tread pool. (Default: 16)
	 */
	private int numberOfThreads = 16;
	
	/**
	 * Recognition-Rules per recognizer thread. (Default: 10)
	 */
	private int rulesPerThread = 10;
	
	/**
	 * Optimizes the (matching) order of the nodes in the Recognition-Rules.
	 * (Default (strongly recommended): <code>true</code>)
	 */
	private boolean sortRecognitionRuleNodes = true;
	
	/**
	 * Optimization: Filtering of unmatchable Recognition-Rules. (Default: <code>true</code>)
	 */
	private boolean ruleSetReduction = true;
	
	/**
	 * <p>Optimization: (Default: <code>true</code>)</p>
	 * <ul> 
	 * <li><code>true</code>: Builds a minimal working graph for each Recognition-Rule.
	 * <li><code>false</code>: Build a single working graph for all Recognition-Rule.
	 * </ul>
	 */
	private boolean buildGraphPerRule = true;

	/**
	 * All listeners of this Setting-Object.
	 */
	private ArrayList<ISettingsChangedListener> listeners = new ArrayList<ISettingsChangedListener>();

	/**
	 * Setup the comparison settings.
	 */
	public Settings() {
	}
	
	/**
	 * Setup the comparison settings.
	 * 
	 * @param documentType
	 *            The document type of the models.
	 */
	public Settings(String documentType) {
		
		// Default: Use single resource
		this.scope = Scope.RESOURCE;
		
		// Default: Use the default TechnicalDifference builder
		this.techBuilder = TechnicalDifferenceBuilderUtil.getDefaultTechnicalDifferenceBuilder(documentType);
		
		// Default: Use named element matcher
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				IMatcher.extensionPointID)) {
			try {
				IMatcher matcherExtension = (IMatcher) configurationElement.createExecutableExtension("name");
				
				if(matcherExtension.getName().equals("org.sidiff.difference.matcher.namedelement.NamedElementMatcher")){
					this.matcher = matcherExtension;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Default: Use all available rulebases:
		this.ruleBases = RuleBaseUtil.getAvailableRulebases(documentType);
	}
	
	/**
	 * Setup the comparison settings.
	 * 
	 * @param scope
	 *            {@link Settings#setScope(Scope)}
	 * @param matcher
	 *            {@link Settings#setMatcher(IMatcher)}
	 * @param techBuilder
	 *            {@link Settings#setTechBuilder(ITechnicalDifferenceBuilder)}
	 * @param ruleBases
	 *            {@link Settings#setRuleBases(Set)}
	 */
	public Settings(Scope scope, IMatcher matcher,
			ITechnicalDifferenceBuilder techBuilder, Set<IRuleBase> ruleBases) {
		super();
		this.scope = scope;
		this.matcher = matcher;
		this.techBuilder = techBuilder;
		this.ruleBases = ruleBases;
	}

	/**
	 * @return The {@link Scope} of the comparison.
	 */
	public Scope getScope() {
		return scope;
	}

	/**
	 * Setup the new {@link Scope} of the comparison.
	 * 
	 * @param scope
	 *            The new {@link Scope}.
	 */
	public void setScope(Scope scope) {
		if(this.scope == null || !this.scope.equals(scope)){
			this.scope = scope;
			this.notifyListeners(SettingsItem.SCOPE);
		}
	}


	/**
	 * @return The Matcher for model comparison.
	 */
	public IMatcher getMatcher() {
		return matcher;
	}

	/**
	 * @param matcher
	 *            The Matcher for model comparison.
	 */
	public void setMatcher(IMatcher matcher) {
		if(this.matcher == null || !this.matcher.getName().equals(matcher.getName())){
			this.matcher = matcher;
			this.notifyListeners(SettingsItem.MATCHER);
		}
	}

	/**
	 * @return The Technical Difference Builder. ({@link ITechnicalDifferenceBuilder}) 
	 */
	public ITechnicalDifferenceBuilder getTechBuilder() {
		return techBuilder;
	}

	/**
	 * @param techBuilder
	 *            The Technical Difference Builder. (
	 *            {@link ITechnicalDifferenceBuilder})
	 */
	public void setTechBuilder(ITechnicalDifferenceBuilder techBuilder) {
		if(this.techBuilder == null || !this.techBuilder.getName().equals(techBuilder.getName())){
			this.techBuilder = techBuilder;
			this.notifyListeners(SettingsItem.TECH_BUILDER);
		}
	}
	
	/**
	 * List of active Rulebases.
	 * 
	 * @return The list of all active Rulebases.
	 */
	public Set<IRuleBase> getRuleBases() {
		return ruleBases;
	}

	/**
	 * Sets the list of active Rulebases.
	 * 
	 * @param ruleBases
	 *            The list of all active Rulebases.
	 */
	public void setRuleBases(Set<IRuleBase> ruleBases) {
		if(this.ruleBases == null || this.ruleBases.size() != ruleBases.size()){
			this.ruleBases = ruleBases;
			this.notifyListeners(SettingsItem.RULEBASES);
		}
	}
	
	/**
	 * Creates a thread pool that reuses a fixed number of threads for the
	 * operation detection (Lifting). (Default: <code>true</code>)
	 * 
	 * @return <code>true</code> if the optimization is active;
	 *         <code>false</code> otherwise.
	 */
	public boolean isUseThreadPool() {
		return useThreadPool;
	}

	/**
	 * Creates a thread pool that reuses a fixed number of threads for the
	 * operation detection (Lifting). (Default: <code>true</code>)
	 * 
	 * @param useThreadPool
	 *            <code>true</code> to activate the optimization;
	 *            <code>false</code> otherwise.
	 */
	public void setUseThreadPool(boolean useThreadPool) {
		if (useThreadPool != this.useThreadPool) {
			this.useThreadPool = useThreadPool;
			this.notifyListeners(SettingsItem.USE_THREAD_POOL);
		}
	}

	/**
	 * Thread pool configuration.
	 * 
	 * @return The number of recognizer threads in the tread pool. (Default: 16)
	 */
	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	/**
	 * Thread pool configuration.
	 * 
	 * @param numberOfThreads
	 *            The number of recognizer threads in the tread pool. (Default: 16)
	 */
	public void setNumberOfThreads(int numberOfThreads) {
		if (numberOfThreads != this.numberOfThreads) {
			this.numberOfThreads = numberOfThreads;
			this.notifyListeners(SettingsItem.NUMBER_OF_THREADS);
		}
	}

	/**
	 * Thread pool configuration.
	 * 
	 * @return The number of Recognition-Rules per recognizer thread. (Default: 10)
	 */
	public int getRulesPerThread() {
		return rulesPerThread;
	}

	/**
	 * Thread pool configuration.
	 * 
	 * @param rulesPerThread
	 *            The number of Recognition-Rules per recognizer thread.
	 *            (Default: 10)
	 */
	public void setRulesPerThread(int rulesPerThread) {
		if (rulesPerThread != this.rulesPerThread) {
			this.rulesPerThread = rulesPerThread;
			this.notifyListeners(SettingsItem.RULES_PER_THREAD);
		}
	}

	/**
	 * Optimizes the (matching) order of the nodes in the Recognition-Rules.
	 * (Default (strongly recommended): <code>true</code>)
	 * 
	 * @return <code>true</code> if the optimization is active;
	 *         <code>false</code> otherwise.
	 */
	public boolean isSortRecognitionRuleNodes() {
		return sortRecognitionRuleNodes;
	}

	/**
	 * Optimizes the (matching) order of the nodes in the Recognition-Rules.
	 * (Default (strongly recommended): <code>true</code>)
	 * 
	 * @param sortRecognitionRuleNodes
	 *            <code>true</code> to activate the optimization;
	 *            <code>false</code> otherwise.
	 */
	public void setSortRecognitionRuleNodes(boolean sortRecognitionRuleNodes) {
		if (sortRecognitionRuleNodes != this.sortRecognitionRuleNodes) {
			this.sortRecognitionRuleNodes = sortRecognitionRuleNodes;
			this.notifyListeners(SettingsItem.SORT_RECOGNITION_RULE_NODES);
		}
	}

	/**
	 * Optimization: Filtering of unmatchable Recognition-Rules. (Default: <code>true</code>)
	 * 
	 * @return <code>true</code> if the optimization is active;
	 *         <code>false</code> otherwise.
	 */
	public boolean isRuleSetReduction() {
		return ruleSetReduction;
	}

	/**
	 * Optimization: Filtering of unmatchable Recognition-Rules. (Default: <code>true</code>)
	 * 
	 * @param ruleSetReduction
	 *            <code>true</code> to activate the optimization;
	 *            <code>false</code> otherwise.
	 */
	public void setRuleSetReduction(boolean ruleSetReduction) {
		if (ruleSetReduction != this.ruleSetReduction) {
			this.ruleSetReduction = ruleSetReduction;
			this.notifyListeners(SettingsItem.RULE_SET_REDUCTION);
		}
	}

	/**
	 * <p>Optimization: (Default: <code>true</code>)</p>
	 * <ul> 
	 * <li><code>true</code>: Builds a minimal working graph for each Recognition-Rule.
	 * <li><code>false</code>: Build a single working graph for all Recognition-Rule.
	 * </ul>
	 * 
	 * @return <code>true</code> if the optimization is active;
	 *         <code>false</code> otherwise.
	 */
	public boolean isBuildGraphPerRule() {
		return buildGraphPerRule;
	}

	/**
	 * <p>Optimization: (Default: <code>true</code>)</p>
	 * <ul> 
	 * <li><code>true</code>: Builds a minimal working graph for each Recognition-Rule.
	 * <li><code>false</code>: Build a single working graph for all Recognition-Rule.
	 * </ul>
	 * 
	 * @param buildGraphPerRule
	 *            <code>true</code> to activate the optimization;
	 *            <code>false</code> otherwise.
	 */
	public void setBuildGraphPerRule(boolean buildGraphPerRule) {
		if (buildGraphPerRule != this.buildGraphPerRule) {
			this.buildGraphPerRule = buildGraphPerRule;
			this.notifyListeners(SettingsItem.BUILD_GRAPH_PER_RULE);
		}
	}
	
	/**
	 * Adds a new listener to this Settings-Object. 
	 * 
	 * @param listener The listener.
	 */
	public void addSettingsChangedListener(ISettingsChangedListener listener){
		if(!this.listeners.contains(listener)){
			this.listeners.add(listener);
		}
	}
	
	/**
	 * Call this function every time when a setting was changed!
	 * 
	 * @param item
	 *            The Enumeration associated with the changed setting.
	 */
	protected void notifyListeners(Enum<?> item){
		if(listeners!=null){
			for(ISettingsChangedListener listener: listeners){
				listener.settingsChanged(item);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuffer result = new StringBuffer(super.toString());
		result.append(scope!=null?"Scope: " + scope + "\n":"");
		result.append(matcher!=null?"Matcher: " + matcher.getName() + "\n":"");
		result.append(techBuilder!=null?"Technical Difference Builder: " + techBuilder.getName() + "\n":"");
		
		if(ruleBases != null){
			result.append("Rulesbases: ");
			
			for(IRuleBase rb : ruleBases){
				result.append(rb.getName() + ", ");
			}
			result.deleteCharAt(result.toString().lastIndexOf(','));
			result.append("\n");
		}
		
		result.append("Use Thread Pool: " + useThreadPool + "\n");
		result.append("Number of Threads in the Pool: " + numberOfThreads + "\n");
		result.append("Recognition-Rules per thread: " + rulesPerThread + "\n");
		result.append("Sort of Recognition-Rule nodes: " + sortRecognitionRuleNodes + "\n");
		result.append("Rule set reduction: " + ruleSetReduction + "\n");
		result.append("Build minimal working graph pre rule: " + buildGraphPerRule + "\n");
		
		return result.toString();
	}
}
