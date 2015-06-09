package org.sidiff.difference.rulebase.wrapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.cpa.CPAOptions;
import org.eclipse.emf.henshin.cpa.CpaByAGG;
import org.eclipse.emf.henshin.cpa.ICriticalPairAnalysis;
import org.eclipse.emf.henshin.cpa.UnsupportedRuleException;
import org.eclipse.emf.henshin.cpa.result.CPAResult;
import org.eclipse.emf.henshin.cpa.result.CriticalElement;
import org.eclipse.emf.henshin.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinUnitAnalysis;
import org.sidiff.difference.rulebase.PotentialAttributeDependency;
import org.sidiff.difference.rulebase.PotentialDependency;
import org.sidiff.difference.rulebase.PotentialDependencyKind;
import org.sidiff.difference.rulebase.PotentialEdgeDependency;
import org.sidiff.difference.rulebase.PotentialNodeDependency;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.RulebaseFactory;

/**
 * provides the functionality to analyze PotentialDependencies for a given RuleBaseItem together with an existing RuleBase by using Henshins Critical Pair Analysis (CPA) 
 *  
 * @author Kristopher Born
 *
 */
public class CpaClient {
	
	/**
	 * The rulebase to process.
	 */
	private RuleBase ruleBase;
	
	/**
	 * The translator to get the different names of dependency kinds within SiLift from the conflict and dependency kinds of Henshins CPA.  
	 */
	private HenshinToSiLiftCriticalKindTranslator kindTranslator = new HenshinToSiLiftCriticalKindTranslator(); 

	public CpaClient(RuleBase rulebase) {
		this.ruleBase = rulebase;
	}

	/**
	 * Initial method to analyze all potential dependencies of the new RuleBaseItem with the existing RuleBaseItems in the ruleBase
	 * 
	 * @param item The new RuleBaseItem
	 * @return A set of new calculated PotentialDependenies of which each contains the EditRule of the new RuleBaseItems as at least one of the two involved  EditRules 
	 */
	public Set<PotentialDependency> analyseNewItem(RuleBaseItem item) {
		
		// this mapping is required to allocate the rules of the results of the CPA with the original RuleBaseItems. 
		Map<Rule,List<RuleBaseItem>> mappingOfHenshinRulesWithinExistingRuleBase = generateMappingOfHenshinRulesAndRuleBaseItems(ruleBase);
		
		List<Rule> henshinRulesOfNewRuleBaseItem = HenshinUnitAnalysis.getRules(item.getEditRule().getExecuteMainUnit());

		// extend the mapping of rules and RuleBaseItems by the RuleBaseItem which is within this method analysed.
		for(Rule henshinRule : henshinRulesOfNewRuleBaseItem){
			if(mappingOfHenshinRulesWithinExistingRuleBase.containsKey(henshinRulesOfNewRuleBaseItem)){
				mappingOfHenshinRulesWithinExistingRuleBase.get(henshinRule).add(item);
			}
			else{
				LinkedList<RuleBaseItem> listOfRuleBaseItems = new LinkedList<RuleBaseItem>();
				listOfRuleBaseItems.add(item);
				mappingOfHenshinRulesWithinExistingRuleBase.put(henshinRule, listOfRuleBaseItems);
			}
		}		
		
		LinkedList<Rule> listOfHenshinRulesWithinRuleBaseAndNewItem = new LinkedList<Rule>(mappingOfHenshinRulesWithinExistingRuleBase.keySet());

		//returns an empty set and prints an error message in case of non injective rules within the new RuleBaseItem item.
		if(!allRulesOfItemAreInjectiveMatching(item)){
			System.err.println("There are non injectiv rules within the RuleBaseItem: >"+item.getEditRule().getExecuteMainUnit().getName()+"< . Only injective rules are supported!");
			System.err.println("No dependencies had been analyzed for the rules of this RuleBaseItems.");
			return new HashSet<PotentialDependency>();
		}
		
		// extracts the injective rules and prints an error in case of non injective rules in the existing RuleBase
		List<Rule> listOfHenshinRulesWithinRuleBaseAndNewItemFilteredByInjectivity = extractRulesWithInjectiveMatching(listOfHenshinRulesWithinRuleBaseAndNewItem);
		printErrorforNonInjectiveRules(listOfHenshinRulesWithinRuleBaseAndNewItem);			
		
		ICriticalPairAnalysis criticalPairAnalyser = new CpaByAGG();
		/**Since we are interested on conflicts and dependencies of pairings with the RuleBaseItem as the first rule 
		 * as well as those with the RuleBaseItem as second rule there are overall four analysis runs required. 
		 * Two runs for dependencies and two for conflicts.
		 */
		List<CriticalPair> criticalPairs = new LinkedList<CriticalPair>(); 
		try {
			criticalPairAnalyser.init(henshinRulesOfNewRuleBaseItem,listOfHenshinRulesWithinRuleBaseAndNewItemFilteredByInjectivity/*allreadyProcessedRules*/, new CPAOptions());
			CPAResult conflicts = criticalPairAnalyser.runConflictAnalysis();
			CPAResult dependencies = criticalPairAnalyser.runDependencyAnalysis();
			//collect the single lists of results 
			criticalPairs.addAll(conflicts.getCriticalPairs());
			criticalPairs.addAll(dependencies.getCriticalPairs());
		} catch (UnsupportedRuleException e) {
			e.printStackTrace();
		}
		
		try {
			criticalPairAnalyser.init(listOfHenshinRulesWithinRuleBaseAndNewItemFilteredByInjectivity/*allreadyProcessedRules*/, henshinRulesOfNewRuleBaseItem, new CPAOptions());
			CPAResult conflictsInverseOrdered = criticalPairAnalyser.runConflictAnalysis();
			CPAResult dependenciesInverseOrdered = criticalPairAnalyser.runDependencyAnalysis();
			//collect the single lists of results 
			criticalPairs.addAll(conflictsInverseOrdered.getCriticalPairs());
			criticalPairs.addAll(dependenciesInverseOrdered.getCriticalPairs());
		} catch (UnsupportedRuleException e) {
			e.printStackTrace();
		}			
		
		//  processes the results to match the data structure of PotentialDependencies
		return createPotentialDependencysForCpaResults(
				criticalPairs, mappingOfHenshinRulesWithinExistingRuleBase,
				henshinRulesOfNewRuleBaseItem);
	}

	// prints the errors on the console for rules which are not configured to match injective.
	private void printErrorforNonInjectiveRules(
			LinkedList<Rule> listOfHenshinRulesWithinRuleBaseAndNewItem) {
		List<Rule> nonInjectiveHenshinRulesWithinRuleBaseAndNewItem = extractRulesWithNoninjectiveMatching(listOfHenshinRulesWithinRuleBaseAndNewItem);
		if(nonInjectiveHenshinRulesWithinRuleBaseAndNewItem.size() > 0){
			System.err.println("The following "+nonInjectiveHenshinRulesWithinRuleBaseAndNewItem.size()+" rules within the RuleBaseItem have a non injective setting regarding their matching.");
			System.err.println("Only njective matching is supported. The following rules are not analysed regarding potential dependencies.");
			for(Rule nonInjectiveRule : nonInjectiveHenshinRulesWithinRuleBaseAndNewItem){
				System.err.println(nonInjectiveRule.getName());
			}			
		}
	}

	// creates all the PotentialDependencies for the critical pairs, which origin from Henshins CPA
	private Set<PotentialDependency> createPotentialDependencysForCpaResults(
			List<CriticalPair> criticalPairs,
			Map<Rule, List<RuleBaseItem>> henshinRulesWithinRuleBaseItemsMapping,
			List<Rule> henshinRulesOfNewRuleBaseItem) {
		Set<PotentialDependency> newCreatedDependencies = new HashSet<PotentialDependency>();
		for(CriticalPair criticalPair : criticalPairs){
			
			Rule firstRuleOfCriticalPair = criticalPair.getFirstRule();
			List<RuleBaseItem> firstRule_RuleBaseItems_OfCriticalPair = henshinRulesWithinRuleBaseItemsMapping.get(firstRuleOfCriticalPair);

			if(firstRule_RuleBaseItems_OfCriticalPair == null){
				firstRule_RuleBaseItems_OfCriticalPair = henshinRulesWithinRuleBaseItemsMapping.get(henshinRulesOfNewRuleBaseItem.get(0));
			}
			
			
			Rule secondRuleOfCriticalPair = criticalPair.getSecondRule();
			List<RuleBaseItem> secondRule_RuleBaseItems_OfCriticalPair = henshinRulesWithinRuleBaseItemsMapping.get(secondRuleOfCriticalPair); //
			if(secondRule_RuleBaseItems_OfCriticalPair == null){
				secondRule_RuleBaseItems_OfCriticalPair = henshinRulesWithinRuleBaseItemsMapping.get(henshinRulesOfNewRuleBaseItem.get(0));
			}
			
			for(RuleBaseItem firstRule_RuleBaseItem : firstRule_RuleBaseItems_OfCriticalPair){ //TODO: NPE here if(firstRule_RuleBaseItems_OfCriticalPair != null && secondRule_RuleBaseItems_OfCriticalPair != null)
				for(RuleBaseItem secondRule_RuleBaseItem : secondRule_RuleBaseItems_OfCriticalPair){
					
					for(CriticalElement criticalElement : criticalPair.getCriticalElements()){
						
						PotentialDependencyKind potentialDependencyKind = kindTranslator.translate(criticalPair);

						
						PotentialDependency potentialDependency = createPotentialDependency(firstRule_RuleBaseItem, secondRule_RuleBaseItem, criticalElement, potentialDependencyKind);

						if(potentialDependency instanceof PotentialNodeDependency)
							ruleBase.getPotentialNodeDependencies().add((PotentialNodeDependency) potentialDependency);
						if(potentialDependency instanceof PotentialEdgeDependency)
							ruleBase.getPotentialEdgeDependencies().add((PotentialEdgeDependency) potentialDependency);
						if(potentialDependency instanceof PotentialAttributeDependency)
							ruleBase.getPotentialAttributeDependencies().add((PotentialAttributeDependency) potentialDependency);
						newCreatedDependencies.add(potentialDependency);
					}
				}
			}
		}
		return newCreatedDependencies;
	}


	/**
	 * Creates a PotentialDependency based on the two related RuleBaseItems, a CriticalElement by the Henshin CPA and a PotentialDependencyKind
	 * @param firstRule_RuleBaseItem The RuleBaseItem providing the first involved rule.
	 * @param secondRule_RuleBaseItem The RuleBaseItem providing the second involved rule.
	 * @param criticalElement The CriticalElement provided by the Henshin CPA on which the newly created PontetialDependency is based on.
	 * @param potentialDependencyKind The kind of PotentialDependency.
	 * @return The created PotentialDependency.
	 */
	private PotentialDependency createPotentialDependency(RuleBaseItem firstRule_RuleBaseItem,
			RuleBaseItem secondRule_RuleBaseItem,
			CriticalElement criticalElement,
			PotentialDependencyKind potentialDependencyKind) {
		
		PotentialDependency potentialDependency = null;
		
		if(criticalElement.elementInFirstRule instanceof Node && criticalElement.elementInSecondRule instanceof Node){
			potentialDependency = createPotentialNodeDependency(criticalElement);
		}
		
		if(criticalElement.elementInFirstRule instanceof Edge && criticalElement.elementInSecondRule instanceof Edge){
			potentialDependency = createPotentialEdgeDependency(criticalElement);
		}
		
		if(criticalElement.elementInFirstRule instanceof Attribute && criticalElement.elementInSecondRule instanceof Attribute){
			potentialDependency = createPotentialAttributeDependency(criticalElement);
		}
		
		if(potentialDependency == null){
			System.err.println("The processed CriticalElement is inconsistent");
			System.err.println("first rule: "+firstRule_RuleBaseItem.getEditRule().getExecuteModule().getName()+"   ");
			System.err.println("second rule: "+secondRule_RuleBaseItem.getEditRule().getExecuteModule().getName());
			return null;
		}
		
		potentialDependency.setKind(potentialDependencyKind);
		potentialDependency.setSourceRule(firstRule_RuleBaseItem.getEditRule());
		potentialDependency.setTargetRule(secondRule_RuleBaseItem.getEditRule()); 
		
		return potentialDependency;
	}
	
	// Creates a PotentialNodeDependency based on a Critical Element.
	private PotentialNodeDependency createPotentialNodeDependency(CriticalElement criticalElement) {
		PotentialNodeDependency potentialNodeDependency = RulebaseFactory.eINSTANCE.createPotentialNodeDependency();
		potentialNodeDependency.setSourceNode((Node)criticalElement.elementInFirstRule);  
		potentialNodeDependency.setTargetNode((Node)criticalElement.elementInSecondRule);	
		potentialNodeDependency.setRuleBase(ruleBase);
		return potentialNodeDependency;
	}
	
	// Creates a PotentialEdgeDependency based on a Critical Element.
	private PotentialEdgeDependency createPotentialEdgeDependency(CriticalElement criticalElement) {
		PotentialEdgeDependency potentialEdgeDependency = RulebaseFactory.eINSTANCE.createPotentialEdgeDependency();
		potentialEdgeDependency.setSourceEdge((Edge) criticalElement.elementInFirstRule);
		potentialEdgeDependency.setTargetEdge((Edge) criticalElement.elementInSecondRule);
		potentialEdgeDependency.setRuleBase(ruleBase);
		return potentialEdgeDependency;
	}
	
	// Creates a PotentialAttributeDependency based on a Critical Element.
	private PotentialAttributeDependency createPotentialAttributeDependency(CriticalElement criticalElement) {
		PotentialAttributeDependency potentialAttributeDependency = RulebaseFactory.eINSTANCE.createPotentialAttributeDependency();
		potentialAttributeDependency.setSourceAttribute((Attribute) criticalElement.elementInFirstRule);
		potentialAttributeDependency.setTargetAttribute((Attribute) criticalElement.elementInSecondRule);
		potentialAttributeDependency.setRuleBase(ruleBase);
		return potentialAttributeDependency;
	}
	
	// Generates a mapping of all the rules within the RuleBaseItems by which the containing RuleBaseItem of a rule can be accessed. 
	private Map<Rule, List<RuleBaseItem>> generateMappingOfHenshinRulesAndRuleBaseItems(RuleBase rulebase) {
		Map<Rule, List<RuleBaseItem>> resultMap = new HashMap<Rule, List<RuleBaseItem>>();
		
		for(RuleBaseItem ruleBaseItem : rulebase.getItems()){
			List<Rule> henshinRulesOfRuleBaseItem = HenshinUnitAnalysis.getRules(ruleBaseItem.getEditRule().getExecuteMainUnit());
			
			for(Rule heshinRule : henshinRulesOfRuleBaseItem){
				if(resultMap.containsKey(henshinRulesOfRuleBaseItem)){
					resultMap.get(heshinRule).add(ruleBaseItem);
				}
				else{
					LinkedList<RuleBaseItem> listOfRuleBaseItems = new LinkedList<RuleBaseItem>();
					listOfRuleBaseItems.add(ruleBaseItem);
					resultMap.put(heshinRule, listOfRuleBaseItems);
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * Checks whether all the rules within in the RuleBaseItem are configured for injective matching
	 * @param item The RuleBaseItem to analyze
	 * @return true if all the rules within in the RuleBaseItem are configured for injective matching
	 */
	private boolean allRulesOfItemAreInjectiveMatching(RuleBaseItem item){
		List<Rule> rulesOfItem = HenshinUnitAnalysis.getRules(item.getEditRule().getExecuteMainUnit());
		for(Rule rule : rulesOfItem){
			if(rule.isInjectiveMatching() == false)
				return false;
		}
		return true;
	}


	// Extracts all rules from the given list, which have injective matching activated
	private List<Rule> extractRulesWithInjectiveMatching(List<Rule> listOfHenshinRules) {
		LinkedList<Rule> rulesWithInjectiveMatching = new LinkedList<Rule>();
		for(Rule rule : listOfHenshinRules){
			if(rule.isInjectiveMatching())
				rulesWithInjectiveMatching.add(rule);
		}
		return rulesWithInjectiveMatching;
	}

	// Extracts all the rules with disabled injective matching out of a given list of Henshin rules.
	private List<Rule> extractRulesWithNoninjectiveMatching(List<Rule> listOfHenshinRules) {
		LinkedList<Rule> rulesWithNoninjectiveMatching = new LinkedList<Rule>();
		for(Rule rule : listOfHenshinRules){
			if(!rule.isInjectiveMatching())
				rulesWithNoninjectiveMatching.add(rule);
		}
		return rulesWithNoninjectiveMatching;
	}
}