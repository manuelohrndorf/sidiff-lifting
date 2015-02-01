package org.sidiff.difference.mutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.Match;
import org.sidiff.difference.rulebase.EditRule;

public class MutationManagement {

	private Map<Integer,List<Mutation>> orderToMutations;
	
	private Map<Integer,List<Resource>> inputModels;
		
	public MutationManagement(){
		this.orderToMutations  = new HashMap<Integer, List<Mutation>>();
		this.inputModels  = new HashMap<Integer, List<Resource>>();
	}

	public List<Mutation> getMutations(int mutationOrder){
		return orderToMutations.get(mutationOrder);
	}
	
	public List<Resource> getInputModels(int mutationOrder) {
		return inputModels.get(mutationOrder);
	}
	
	public List<Resource> getMutants(int mutationOrder) {
		ArrayList<Resource> mutants = new ArrayList<Resource>();
		for(Mutation mutation : orderToMutations.get(mutationOrder)){
			mutants.add(mutation.getMutant());
		}
		return mutants;
	}

	public void setInputModels(int mutationOrder, List<Resource> inputModels) {
		this.inputModels.put(mutationOrder,inputModels);
	}
	
	public void initializeOrder(int mutationOrder){
		setInputModels(mutationOrder, getMutants(mutationOrder-1));		
	}	
	
	public List<Mutation> getAllMutations() {
		ArrayList<Mutation> mutations = new ArrayList<Mutation>();
		for(int order : orderToMutations.keySet()){
			for(Mutation mutation : orderToMutations.get(order)){
				mutations.add(mutation);
			}
		}
		return Collections.unmodifiableList(mutations);
	}
	
	public void addMutation(int mutationOrder, Mutation mutation){
		List<Mutation> mutations = this.orderToMutations.get(mutationOrder);
		if(mutations == null){
			mutations = new ArrayList<Mutation>();
		}
		mutations.add(mutation);
		this.orderToMutations.put(mutationOrder, mutations);
	}

	public boolean mutationHasBeenUsedBefore(EditRule operator, Match context){
		for(Mutation mutation : getAllMutations()){
			boolean opEqu = operator.equals(mutation.getUsedOperator());
			System.out.println(operator + "<->" + mutation.getUsedOperator() + ": " + opEqu);
			boolean coEqu = context.equals(mutation.getUsedContext());
			System.out.println(context + "<->" + mutation.getUsedContext() + ": " + coEqu);
		if(opEqu && coEqu)
				return true;
		}
		
		return false;
	}
	
	
}
