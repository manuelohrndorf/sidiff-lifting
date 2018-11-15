package org.sidiff.editrule.generator.serge.generators.actions;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.Configuration.OperationTypeGroup;
import org.sidiff.editrule.generator.serge.core.TypeReplacer;
import org.sidiff.editrule.generator.types.OperationType;

public class SolitaryVariantGenerator {

	/**
	 * The original input module in which we want to replace supertypes with
	 * subtypes and abstract types with concrete types.
	 */
	private Module originalModule;
	
	/**
	 * The operation type of the original input model.
	 */
	private OperationType opType;
	private OperationTypeGroup opTypeGroup;
	
	

	/**
	 * Constructor
	 * @param originalModule
	 */
	public SolitaryVariantGenerator(Module originalModule, OperationType opType, OperationTypeGroup opTypeGroup) {
		this.originalModule = originalModule;
		this.opType = opType;
		this.opTypeGroup=opTypeGroup;
	}

	public Set<Module> generate() throws OperationTypeNotImplementedException{
		
		Set<Module> modules = new HashSet<Module>();
			
		TypeReplacer typeReplacer = new TypeReplacer(originalModule, opType, opTypeGroup);
		modules.addAll(typeReplacer.replace());
		
		return modules;
	}
	
	
}
