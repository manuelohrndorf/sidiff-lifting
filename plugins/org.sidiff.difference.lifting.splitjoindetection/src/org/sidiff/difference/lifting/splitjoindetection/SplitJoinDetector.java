package org.sidiff.difference.lifting.splitjoindetection;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Join;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Split;
import org.sidiff.difference.lifting.recognitionengine.matching.UriBasedEditRuleMatch;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.symmetric.FragmentJoin;
import org.sidiff.difference.symmetric.FragmentSplit;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;

/**
 * With this class one can detect splits and/or joins in a
 * symmetric difference
 * 
 * @author mrindt
 *
 */
public class SplitJoinDetector {

	/**
	 * The Symmetric Difference
	 */
	private SymmetricDifference diff;
	
	/**
	 * Constructor
	 * 
	 * @param diff (SymmetricDifference to be processed)
	 */
	public SplitJoinDetector(SymmetricDifference diff) {
		
		this.diff = diff;
		
	}
	
	/**
	 * Detects Splits and/or Joins within SemanticChangeSets and
	 * arranges matched EObjects into respective Reference lists (e.g., splitInto)
	 * 
	 * @returns The processed SymmetricDifference with Split/Joins, if any
	 */
	public SymmetricDifference detect() {
		
		for(SemanticChangeSet scs: diff.getChangeSets()) {
			
			// use URIbasedEditruleMatch for easy access to EditRuleMatches from Nodes to EObjects
			UriBasedEditRuleMatch uriBasedERMatch = new UriBasedEditRuleMatch(scs);
			
			EditRule er = scs.resolveEditRule();
			Rule r = (Rule) er.getExecuteMainUnit().getSubUnits(false).get(0);
						
			// only continue if the rule contains Split/Join definitions
			if(!isASplitJoinRefactoring(er.getExecuteModule())) {
				return diff;
			}

			// process Rule Splits
			for(Split erSplit: r.getSplits()) {

				// create FragmentSplit for SymmetricDiff
				FragmentSplit fSplit = SymmetricFactory.eINSTANCE.createFragmentSplit();

				//arrange sources for FragmentSplit
				for(Node erFromNode: erSplit.getSplitFrom()) {

					Set<EObject> targetElements = uriBasedERMatch.getOccurenceA(erFromNode);
					assert(targetElements.size()==1) : "Splits do not support Multi-Nodes, yet.";
					EObject target = targetElements.iterator().next();
					fSplit.getSplitFrom().add(target);	
					
				}

				//arrange targets of FragmentSplit
				for(Node erIntoNode: erSplit.getSplitInto()) {

					Set<EObject> targetElements = uriBasedERMatch.getOccurenceB(erIntoNode);
					assert(targetElements.size()==1) : "Splits do not support Multi-Nodes, yet.";
					EObject targetElem = targetElements.iterator().next();
					fSplit.getSplitInto().add(targetElem);

				}

				// add FragmentSplit to SCS
				scs.getSplits().add(fSplit);

			}

			// process Rule Joins
			for(Join erJoin: r.getJoins()) {

				// create FragmentJoin for SymmetricDiff
				FragmentJoin fJoin = SymmetricFactory.eINSTANCE.createFragmentJoin();

				//arrange sources for FragmentJoin
				for(Node erFromNode: erJoin.getJoinFrom()) {

					Set<EObject> targetElements = uriBasedERMatch.getOccurenceA(erFromNode);
					assert(targetElements.size()==1) : "Joins do not support Multi-Nodes, yet.";
					EObject target = targetElements.iterator().next();
					fJoin.getJoinFrom().add(target);	
					
				}

				//arrange targets of FragmentJoin
				for(Node erIntoNode: erJoin.getJoinInto()) {

					Set<EObject> targetElements = uriBasedERMatch.getOccurenceB(erIntoNode);
					assert(targetElements.size()==1) : "Joins do not support Multi-Nodes, yet.";
					EObject targetElem = targetElements.iterator().next();
					fJoin.getJoinInto().add(targetElem);

				}

				// add FragmentJoins to SCS
				scs.getJoins().add(fJoin);
			}
		}
		return diff;
	}
	
	/**
	 * Checks, if rule has an Annotation [RuleType = "Refactoring"] embedded
	 * 
	 * @param r the Rule
	 * @return <code>true</code> if annotation is contained,
	 * 			<code>false</code> otherwise.
	 */
	private boolean isASplitJoinRefactoring(Module m) {
		
		for(Annotation anno: m.getAnnotations()) {
			if( anno.getKey().equals("RuleType") && anno.getValue().equals("Refactoring")) {
				return true;
			}
		}

		return false;
	}
}
