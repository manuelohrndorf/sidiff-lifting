package org.sidiff.difference.mutation.config;

import java.util.LinkedList;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.Match;
import org.sidiff.difference.mutation.selection.AbstractSelection;
import org.sidiff.difference.rulebase.EditRule;

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
	
	private String name;

	public MutationConfig(String name, Resource targetModel,LinkedList<EditRule> mutationOperators, AbstractSelection<EditRule> aos, AbstractSelection<Match> acs,
			int mutationOrder, boolean validateMutants) {
		super();

		this.name = name;
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

	public LinkedList<EditRule> getMutationOperators() {
		return mutationOperators;
	}

	public AbstractSelection<EditRule> getAos() {
		return aos;
	}

	public AbstractSelection<Match> getAcs() {
		return acs;
	}

	public int getMutationOrder() {
		return mutationOrder;
	}

	public String getName() {
		return name;
	}
	
	public boolean isValidateMutants() {
		return validateMutants;
	}
	

	@Override
	public String toString() {
		String result = "-----------------------Mutation Config: " + getName() + "-------------------------------" + System.lineSeparator();
		result += "Target Model: " + getTargetModel() + System.lineSeparator();
		result += "Mutation Operators: " + getMutationOperators() + System.lineSeparator();
		result += "Operator Selection: " + getAos() + System.lineSeparator();
		result += "Context Selection: " + getAcs() + System.lineSeparator();
		result += "-------------------------------------------------------------------------" + System.lineSeparator();
		return result; 
	}
}
