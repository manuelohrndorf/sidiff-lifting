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
	
	private Scope scope;
	
	private Set<ILiftingRuleBase> usedRulebases;
	
	private boolean ruleSetReduction;
	
	private boolean buildGraphPerRule;
	
	private IRecognitionRuleSorter ruleSorter;
	
	private boolean useThreadPool;
	
	private int numberOfThreads;
	
	private int rulesPerThread;
	
	private boolean calculateEditRuleMatch;
	
	private boolean serializeEditRuleMatch;

	public RecognitionEngineSetup(
			SymmetricDifference difference, ModelImports imports, Scope scope, 
			Set<ILiftingRuleBase> usedRulebases, boolean ruleSetReduction, 
			boolean buildGraphPerRule, IRecognitionRuleSorter ruleSorter, 
			boolean useThreadPool, int numberOfThreads, int rulesPerThread,
			boolean calculateEditRuleMatch, boolean serializeEditRuleMatch) {
		
		super();
		this.difference = difference;
		this.imports = imports;
		this.scope = scope;
		this.usedRulebases = usedRulebases;
		this.ruleSetReduction = ruleSetReduction;
		this.buildGraphPerRule = buildGraphPerRule;
		this.ruleSorter = ruleSorter;
		this.useThreadPool = useThreadPool;
		this.numberOfThreads = numberOfThreads;
		this.rulesPerThread = rulesPerThread;
		this.calculateEditRuleMatch = calculateEditRuleMatch;
		this.serializeEditRuleMatch = serializeEditRuleMatch;
	}
	
	public RecognitionEngineSetup(Scope scope, 
			Set<ILiftingRuleBase> usedRulebases, boolean ruleSetReduction, 
			boolean buildGraphPerRule, IRecognitionRuleSorter ruleSorter, 
			boolean useThreadPool, int numberOfThreads, int rulesPerThread,
			boolean calculateEditRuleMatch, boolean serializeEditRuleMatch) {
		
		super();
		this.scope = scope;
		this.usedRulebases = usedRulebases;
		this.ruleSetReduction = ruleSetReduction;
		this.buildGraphPerRule = buildGraphPerRule;
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
		return usedRulebases;
	}

	public void setUsedRulebases(Set<ILiftingRuleBase> usedRulebases) {
		this.usedRulebases = usedRulebases;
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
