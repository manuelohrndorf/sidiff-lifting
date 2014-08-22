package org.sidiff.editrule.generator.generators.actions;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.editrule.generator.configuration.Configuration.OperationType;
import org.sidiff.editrule.generator.core.TypeReplacer;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;

public class VariantGenerator {

	/**
	 * The original input module in which we want to replace supertypes with
	 * subtypes and abstract types with concrete types.
	 */
	private Module originalModule;
	
	/**
	 * The operation type of the original input model.
	 */
	private OperationType opType;
	
	/**
	 * Value that states if reduction to super type is wished.
	 */
	private boolean reduceToSuperType;
	

	/**
	 * Constructor
	 * @param originalModule
	 */
	public VariantGenerator(Module originalModule, OperationType opType, boolean reduceToSuperType) {
		this.originalModule = originalModule;
		this.opType = opType;
		this.reduceToSuperType = reduceToSuperType;
	}

	public Set<Module> generate() throws OperationTypeNotImplementedException{
		
		Set<Module> modules = new HashSet<Module>();
			
		TypeReplacer typeReplacer = new TypeReplacer(originalModule, opType, reduceToSuperType);
		modules.addAll(typeReplacer.replace());
		
		return modules;
	}
	
	
}
