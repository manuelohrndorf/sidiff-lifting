package org.sidiff.difference.lifting.recognitionengine;

import java.util.Set;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.technical.ModelImports;

public class RecognitionEngineSetup {

	private SymmetricDifference difference;
	
	private ModelImports imports;
	
	private Scope scope = Scope.RESOURCE;;
	
	private Set<ILiftingRuleBase> rulebases;
	
	private boolean ruleSetReduction = true;
	
	private boolean buildGraphPerRule = true;
	
	private boolean optimizeMatchingEngine = true;
	
	private IRecognitionRuleSorter ruleSorter;
	
	private boolean useThreadPool = true;
	
	private int numberOfThreads = 10;
	
	private int rulesPerThread = 10;
	
	private boolean calculateEditRuleMatch = false;
	
	private boolean serializeEditRuleMatch = false;
	
	public RecognitionEngineSetup(
			SymmetricDifference difference, ModelImports imports, 
			Set<ILiftingRuleBase> rulebases) {
		
		super();
		this.difference = difference;
		this.imports = imports;
		this.rulebases = rulebases;
	}

	public RecognitionEngineSetup(
			SymmetricDifference difference, ModelImports imports, Scope scope, 
			Set<ILiftingRuleBase> rulebases, boolean ruleSetReduction, 
			boolean buildGraphPerRule, boolean optimizeMatchingEngine,
			IRecognitionRuleSorter ruleSorter, 
			boolean useThreadPool, int numberOfThreads, int rulesPerThread,
			boolean calculateEditRuleMatch, boolean serializeEditRuleMatch) {
		
		super();
		this.difference = difference;
		this.imports = imports;
		this.scope = scope;
		this.rulebases = rulebases;
		this.ruleSetReduction = ruleSetReduction;
		this.buildGraphPerRule = buildGraphPerRule;
		this.optimizeMatchingEngine = optimizeMatchingEngine;
		this.ruleSorter = ruleSorter;
		this.useThreadPool = useThreadPool;
		this.numberOfThreads = numberOfThreads;
		this.rulesPerThread = rulesPerThread;
		this.calculateEditRuleMatch = calculateEditRuleMatch;
		this.serializeEditRuleMatch = serializeEditRuleMatch;
	}
	
	public RecognitionEngineSetup(Scope scope, 
			Set<ILiftingRuleBase> rulebases, boolean ruleSetReduction, 
			boolean buildGraphPerRule, boolean optimizeMatchingEngine,
			IRecognitionRuleSorter ruleSorter, 
			boolean useThreadPool, int numberOfThreads, int rulesPerThread,
			boolean calculateEditRuleMatch, boolean serializeEditRuleMatch) {
		
		super();
		this.scope = scope;
		this.rulebases = rulebases;
		this.ruleSetReduction = ruleSetReduction;
		this.buildGraphPerRule = buildGraphPerRule;
		this.optimizeMatchingEngine = optimizeMatchingEngine;
		this.ruleSorter = ruleSorter;
		this.useThreadPool = useThreadPool;
		this.numberOfThreads = numberOfThreads;
		this.rulesPerThread = rulesPerThread;
		this.calculateEditRuleMatch = calculateEditRuleMatch;
		this.serializeEditRuleMatch = serializeEditRuleMatch;
	}

	public SymmetricDifference getDifference() {
		return difference;
	}

	public void setDifference(SymmetricDifference difference) {
		this.difference = difference;
	}

	public ModelImports getImports() {
		return imports;
	}

	public void setImports(ModelImports imports) {
		this.imports = imports;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Set<ILiftingRuleBase> getRulebases() {
		return rulebases;
	}

	public void setRulebases(Set<ILiftingRuleBase> usedRulebases) {
		this.rulebases = usedRulebases;
	}

	public boolean isRuleSetReduction() {
		return ruleSetReduction;
	}

	public void setRuleSetReduction(boolean ruleSetReduction) {
		this.ruleSetReduction = ruleSetReduction;
	}

	public boolean isBuildGraphPerRule() {
		return buildGraphPerRule;
	}

	public void setBuildGraphPerRule(boolean buildGraphPerRule) {
		this.buildGraphPerRule = buildGraphPerRule;
	}

	public boolean isOptimizeMatchingEngine() {
		return optimizeMatchingEngine;
	}

	public void setOptimizeMatchingEngine(boolean optimizeMatchingEngine) {
		this.optimizeMatchingEngine = optimizeMatchingEngine;
	}

	public IRecognitionRuleSorter getRuleSorter() {
		return ruleSorter;
	}

	public void setRuleSorter(IRecognitionRuleSorter ruleSorter) {
		this.ruleSorter = ruleSorter;
	}

	public boolean isUseThreadPool() {
		return useThreadPool;
	}

	public void setUseThreadPool(boolean useThreadPool) {
		this.useThreadPool = useThreadPool;
	}

	public int getNumberOfThreads() {
		return numberOfThreads;
	}

	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}

	public int getRulesPerThread() {
		return rulesPerThread;
	}

	public void setRulesPerThread(int rulesPerThread) {
		this.rulesPerThread = rulesPerThread;
	}

	public boolean isCalculateEditRuleMatch() {
		return calculateEditRuleMatch;
	}

	public void setCalculateEditRuleMatch(boolean calculateEditRuleMatch) {
		this.calculateEditRuleMatch = calculateEditRuleMatch;
	}

	public boolean isSerializeEditRuleMatch() {
		return serializeEditRuleMatch;
	}

	public void setSerializeEditRuleMatch(boolean serializeEditRuleMatch) {
		this.serializeEditRuleMatch = serializeEditRuleMatch;
	}
}
