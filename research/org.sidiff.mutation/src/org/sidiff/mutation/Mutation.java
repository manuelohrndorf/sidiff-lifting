package org.sidiff.mutation;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.Match;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.mutation.util.MutationUtil;

public class Mutation {
	
	private Resource inputModel;
	
	private Resource mutant;
	
	private Copier copier;
	
	private EditRule usedOperator;
	
	private Match usedContext;	

	public Mutation(Resource inputModel, Resource mutant, Copier copier,
			EditRule usedOperator, Match usedContext) {
		super();
		this.inputModel = inputModel;
		this.mutant = mutant;
		this.copier = copier;
		this.usedOperator = usedOperator;
		this.usedContext = usedContext;
	}

	public Resource getInputModel() {
		return inputModel;
	}

	public Resource getMutant() {
		return mutant;
	}

	public Copier getCopier() {
		return copier;
	}

	public EditRule getUsedOperator() {
		return usedOperator;
	}

	public Match getUsedContext() {
		return usedContext;
	}
	
	@Override
	public String toString() {
		String result = "Input Model: " + MutationUtil.getResourceName(getInputModel()) + System.lineSeparator();
		result += "Mutation Operator: " + getUsedOperator() + System.lineSeparator();
		result += "Mutation Context: " + getUsedContext();
		result += "Resulting Mutant: " + MutationUtil.getResourceName(getMutant());
		return result; 
	}

	@Override
	public boolean equals(Object obj) {
		
		//Both mutations have to use the same operator
		boolean sameOperator = this.getUsedOperator().equals(obj);
		
		//Both mutations have to apply the mutation to the same context		
		boolean sameContext = this.getUsedContext().equals(((Mutation)obj).getUsedContext());
		
		return sameOperator && sameContext;		
	}

	@Override
	public int hashCode() {
		
		int hash = 1;
		hash = 37 * this.getUsedOperator().hashCode();
		hash = 37 * hash +this.getUsedContext().hashCode();	
		
		return hash;
	}
	
	
	
	
	
}
