package org.sidiff.difference.mutation;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.Match;
import org.sidiff.difference.mutation.util.MutationUtil;
import org.sidiff.difference.rulebase.EditRule;

public class MutationManagement {

	private Map<Integer,Set<Mutation>> orderToMutations;
	
	private Map<Integer,Set<Resource>> orderToInputModels;
	
	private Map<Resource,LinkedList<Mutation>> mutantToMutationSequence;
		
	public MutationManagement(){
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
		HashSet<Resource> mutants = new HashSet<Resource>();
		for(Mutation mutation : orderToMutations.get(mutationOrder)){
			mutants.add(mutation.getMutant());
		}
		return mutants;
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
		setInputModels(mutationOrder, getMutants(mutationOrder-1));		
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

	public boolean mutationHasBeenUsedBefore(EditRule operator, Match context){
		for(Mutation mutation : getAllMutations()){
			//Check if both operators represent the same change
			boolean opEqu = operator.getExecuteModule().getName().equals(
					mutation.getUsedOperator().getExecuteModule().getName());
		//	System.out.println(operator + "<->" + mutation.getUsedOperator() + ": " + opEqu);
			boolean coEqu = context.equals(mutation.getUsedContext());
		//	System.out.println(context + "<->" + mutation.getUsedContext() + ": " + coEqu);
		if(opEqu && coEqu)
				return true;
		}
		
		return false;
	}
	
	
}
