package org.sidiff.mutation.config;

import java.util.LinkedList;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.Match;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.mutation.selection.AbstractSelection;

/**
 * Base class for mutation configurations.
 * 
 * @author dreuling
 */
public class MutationConfig {
	
	private Resource targetModel;
	private LinkedList<EditRule> mutationOperators;
	private AbstractSelection<EditRule> aos;
	private AbstractSelection<Match> acs;	
	private int mutationOrder;	
	private boolean validateMutants;	
	
	public MutationConfig(){
		super();
	}
	

	public MutationConfig(Resource targetModel,LinkedList<EditRule> mutationOperators, AbstractSelection<EditRule> aos, AbstractSelection<Match> acs,
			int mutationOrder, boolean validateMutants) {
		super();
		this.targetModel = targetModel;
		this.mutationOperators = mutationOperators;
		this.aos = aos;
		this.acs = acs;
		this.mutationOrder = mutationOrder;
		this.validateMutants = validateMutants;
	}


	

	public Resource getTargetModel() {
		return targetModel;
	}




	public void setTargetModel(Resource targetModel) {
		this.targetModel = targetModel;
	}




	public LinkedList<EditRule> getMutationOperators() {
		return mutationOperators;
	}




	public void setMutationOperators(LinkedList<EditRule> mutationOperators) {
		this.mutationOperators = mutationOperators;
	}




	public AbstractSelection<EditRule> getAos() {
		return aos;
	}




	public void setAos(AbstractSelection<EditRule> aos) {
		this.aos = aos;
	}




	public AbstractSelection<Match> getAcs() {
		return acs;
	}




	public void setAcs(AbstractSelection<Match> acs) {
		this.acs = acs;
	}




	public int getMutationOrder() {
		return mutationOrder;
	}




	public void setMutationOrder(int mutationOrder) {
		this.mutationOrder = mutationOrder;
	}




	public boolean isValidateMutants() {
		return validateMutants;
	}




	public void setValidateMutants(boolean validateMutants) {
		this.validateMutants = validateMutants;
	}
	

	@Override
	public String toString() {
		String result = "-----------------------Mutation Config-------------------------------" + System.lineSeparator();
		result += "Target Model: " + getTargetModel() + System.lineSeparator();
		result += "Mutation Operators: " + getMutationOperators() + System.lineSeparator();
		result += "Operator Selection: " + getAos() + System.lineSeparator();
		result += "Context Selection: " + getAcs() + System.lineSeparator();
		result += "-------------------------------------------------------------------------" + System.lineSeparator();
		return result; 
	}
}
