package org.sidiff.difference.mutation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.Match;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.mutation.util.MutationUtil;
import org.sidiff.difference.rulebase.EditRule;

public class MutationManagement {

	private Map<Integer,Set<Mutation>> orderToMutations;
	
	private Map<Integer,Set<Resource>> orderToInputModels;
	
	private Map<Resource,LinkedList<Mutation>> mutantToMutationSequence;	
		
	public MutationManagement(Resource targetModel){
		this.orderToMutations  = new HashMap<Integer, Set<Mutation>>();
		this.orderToInputModels  = new HashMap<Integer, Set<Resource>>();
		this.mutantToMutationSequence = new HashMap<Resource, LinkedList<Mutation>>();
	}

	public Set<Mutation> getMutations(int mutationOrder){
		return orderToMutations.get(mutationOrder);
	}
	
	public Set<Resource> getInputModels(int mutationOrder) {
		return orderToInputModels.get(mutationOrder);
	}
	
	public Set<Resource> getMutants(int mutationOrder) {
		if(orderToMutations.get(mutationOrder)!=null){
		HashSet<Resource> mutants = new HashSet<Resource>();
		for(Mutation mutation : orderToMutations.get(mutationOrder)){
			mutants.add(mutation.getMutant());
		}
		return mutants;
		}
		else{
			Set<Resource> emptySet = Collections.emptySet();
			return emptySet;
		}
	}
	
	public Set<Resource> getAllMutants(){
		HashSet<Resource> mutants = new HashSet<Resource>();
		for(int order : orderToMutations.keySet()){
			for(Mutation mutation : orderToMutations.get(order)){
				mutants.add(mutation.getMutant());
			}
		}
		return Collections.unmodifiableSet(mutants);
	}

	public void setInputModels(int mutationOrder, Set<Resource> inputModels) {
		this.orderToInputModels.put(mutationOrder,inputModels);
	}
	
	public void initializeOrder(int mutationOrder){
		if(getMutants(mutationOrder-1) != null){
		setInputModels(mutationOrder, getMutants(mutationOrder-1));
		}
		else{
			Set<Resource> emptySet = Collections.emptySet();
			setInputModels(mutationOrder, emptySet);
		}
	}	
	
	public Set<Mutation> getAllMutations() {
		HashSet<Mutation> mutations = new HashSet<Mutation>();
		for(int order : orderToMutations.keySet()){
			for(Mutation mutation : orderToMutations.get(order)){
				mutations.add(mutation);
			}
		}
		return Collections.unmodifiableSet(mutations);
	}
	
	public void addMutation(int mutationOrder, Mutation mutation){
		
		// Add mutation to current mutationOrder
		Set<Mutation> mutations = this.orderToMutations.get(mutationOrder);
		if(mutations == null){
			mutations = new HashSet<Mutation>();
		}
		mutations.add(mutation);
		this.orderToMutations.put(mutationOrder, mutations);
		
		// Copy mutation sequence of input model		
		LinkedList<Mutation> mutationSequence = (LinkedList<Mutation>) getMutationSequence(mutation.getInputModel()).clone();
		// Add current mutation
		mutationSequence.add(mutation);		
		// Add to map
		this.mutantToMutationSequence.put(mutation.getMutant(), mutationSequence);		
			
	}
	
	public LinkedList<Mutation> getMutationSequence(Resource mutant){
		
		LinkedList<Mutation> mutationSequence = new LinkedList<Mutation>();

		//Already included
		if(this.mutantToMutationSequence.containsKey(mutant)){
			mutationSequence = this.mutantToMutationSequence.get(mutant);
		}
		
		//Newly computed
		else{
			for(Mutation mutation : getAllMutations()){
				if(mutation.getMutant().equals(mutant)){
					getMutationSequence(mutation.getInputModel());
					mutationSequence.add(mutation);
				}
			}	
		}
		return mutationSequence;
	}
	

	public String printMutationSequence(Resource mutant){
		LinkedList<Mutation> mutationSequence = getMutationSequence(mutant);
		String result = "";
		for(Mutation mutation : mutationSequence){
			String inputFileName = MutationUtil.getResourceName(mutation.getInputModel());
			String mutantFileName = MutationUtil.getResourceName(mutation.getMutant());
			String opName = mutation.getUsedOperator().getExecuteModule().getName();
			
			result += " " + inputFileName + "(" + opName + ") -> "  + mutantFileName + " ";
			
		}
		return result;
		
	}

	/**
	 * Checks whether the current "to happen" mutation will
	 * result in a mutant already created/generated before.
	 * @param inputModel
	 * @param operator
	 * @param context
	 * @param currentOrder
	 * @return
	 */
	public boolean mutantHasBeenCreatedBefore(Resource inputModel, EditRule operator, Match context, int currentOrder){
		
		// This mutation shall be applied and therefore tested
		Mutation currentMutation = new Mutation(inputModel, null, null, operator, context);		
		
		//The duplicate mutant candidate could only be created in the current order		
		for(Resource candidate : getMutants(currentOrder)){			
				if(mutantOriginatesFromMutation(candidate, currentMutation)){
					LogUtil.log(LogEvent.NOTICE, "- Mutant already created, skipping - ");
					LogUtil.log(LogEvent.DEBUG, "Input Model: " + MutationUtil.getResourceName(currentMutation.getInputModel()));
					LogUtil.log(LogEvent.DEBUG, "Mutation Operator: " + currentMutation.getUsedOperator());
					LogUtil.log(LogEvent.DEBUG, "Mutation Context: " + currentMutation.getUsedContext());			
					return true;			
				}
		}
		
	return false;
	}
	
	/**
	 * Checks 
	 * @param candidate
	 * @param currentMutation
	 * @return
	 */
	private boolean mutantOriginatesFromMutation(Resource candidate, Mutation currentMutation){		

		/**
		 * TODO
		 
		LinkedList<Mutation> candidateSequence = getMutationSequence(candidate);
		//Get mutation sequences of the resulting mutants.
		//As the order of the sequence is irrelevant, we use a set
		Set<Mutation> candidateMutations = new HashSet<Mutation>();
		candidateMutations.addAll(candidateSequence);

		LinkedList<Mutation> resultingSequence = getMutationSequence(currentMutation.getInputModel());
		resultingSequence.add(currentMutation);

		// Create a temporary set of mutations
		Set<Mutation> resultingMutations = new HashSet<Mutation>();
		resultingMutations.addAll(resultingSequence);

		// If the candidate represents the current mutation
		if(resultingMutations.equals(candidateMutations))
			return true;	
		
		else{
			for(int i = 0; i < resultingSequence.size(); i++){
				adaptToPreviousMutationContext(currentMutation, resultingSequence);		
				if(resultingMutations.equals(candidateMutations))
					return true;
			}
			
		}
		*/

		return false;
	}
	
	
}
