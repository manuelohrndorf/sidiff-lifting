package org.sidiff.serge.core;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;
import org.sidiff.serge.generators.actions.CreateGenerator;

public class GenerationActionDelegator {

	private static GenerationActionDelegator GAD = null;
	private static Configuration 			 c	 = Configuration.getInstance();
	private static EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
	private static ElementFilter			 FILTER = ElementFilter.getInstance();
	
	/**
	 * Singleton
	 * @return {@link GenerationActionDelegator}
	 */
	public static GenerationActionDelegator getInstance() {
		if(GAD==null) {
			GAD = new GenerationActionDelegator();
		}
		return GAD;
	}
	
	/**
	 * General CREATE-generation method, that finds all relevant
	 * contexts and references that represent different CREATE-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link CreateGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate create modules for the given classifier.
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_CREATE(EClassifier eClassifier) throws OperationTypeNotImplementedException {
	
		if(FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.CREATE)) {
		
			EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);
			
			/** In case of no Stereotype, create CREATE normally ******************************************************************************/
			if(!c.PROFILEAPPLICATIONINUSE || (c.PROFILEAPPLICATIONINUSE && !eInf.isStereotype())) {
				
				/** Create Modules for every parent in which the EClass may exist. ************************************************************/
				HashMap<EReference, List<EClassifier>> optionalParents = ECM.getAllOptionalParentContext(eClassifier, c.REDUCETOSUPERTYPE_CREATEDELETE);
				for(Entry<EReference,List<EClassifier>> pcEntry: optionalParents.entrySet()) {			
					List<EClassifier> contexts = pcEntry.getValue();
					EReference eRef = pcEntry.getKey();
	
					for(EClassifier context: contexts) {
						
						if(FILTER.isAllowedAsDangling(context, OperationType.CREATE)) {
						
						
						CreateGenerator generator = new CreateGenerator(eRef, context);
						generator.generate();
						
						//...
						
						
						}
						
						
					}
					
				}
			}
			/** In case of Stereotype, there are no contexts! Just create Rule with <<create>> Node for Stereotype ****************************/
			else{
			
			
				//..
			
			}
			
		
		}
		return null;
	}
	
	/**
	 * General DELETE-generation method that delegates each given CREATE-module of the input set
	 * to the inverse creation method {@link DeleteGenerator}.
	 * 
	 * @param set of create modules
	 */
	public void generate_DELETE(Set<Module> variantModules) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * General MOVE-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link MoveGenerator}
	 * 
	 * @param eClassifier
	 */
	public void generate_MOVE(EClassifier eClassifier) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * General MOVE-UP-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-UP-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link MoveUpGenerator}
	 * 
	 * @param eClassifier
	 */
	public void generate_MOVE_UP(EClassifier eClassifier) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * General MOVE-DOWN-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-DOWN-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link MoveDownGenerator}
	 * 
	 * @param eClassifier
	 */
	public void generate_MOVE_DOWN(EClassifier eClassifier) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * General MOVE-Combination-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-Combination-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link MoveCombinationGenerator}
	 * 
	 * @param eClassifier
	 */
	public void generate_MOVE_COMBINATION(EClassifier eClassifier) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * General ADD-generation method, that finds all relevant
	 * contexts and references that represent different ADD-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link AddGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate add modules for the given eclass.
	 */
	public Set<Module> generate_ADD(EClassifier eClassifier) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * General REMOVE-generation method that delegates each given REMOVE-module of the input set
	 * to the inverse creation method {@link RemoveGenerator}.
	 * 
	 * @param set of remove modules
	 */
	public void generate_REMOVE(Set<Module> addModules) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * General SET-ATTRIBUTE-generation method, that finds all relevant
	 * contexts and references that represent different SET-ATTRIBUTE for this eClassifier.
	 * For each setup the generation process will be delegated to {@link SetAttributeGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate set attribute modules for the given eClassifier.
	 */
	public Set<Module> generate_SET_ATTRIBUTE(EClassifier eClassifier) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * General UNSET-ATTRIBUTE-generation method that delegates each given UNSET-ATTRIBUTE-module of the input set
	 * to the inverse creation method {@link UnsetAttributeGenerator}.
	 * <br/> Whereby the inverse can be considered as the resetting to default values if any.
	 * 
	 * @param set of set attribute modules
	 */
	public void generate_UNSET_ATTRIBUTE(Set<Module> set_attribute_Modules) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * General SET-REFERENCE-generation method, that finds all relevant
	 * contexts and references that represent different SET-REFERENCE for this eClassifier.
	 * For each setup the generation process will be delegated to {@link SetReferenceGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate set reference modules for the given eClassifier.
	 */
	public Set<Module> generate_SET_REFERENCE(EClassifier eClassifier) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * General UNSET-REFERENCE-generation method that delegates each given UNSET-REFERENCE-module of the input set
	 * to the inverse creation method {@link UnsetReferenceGenerator}.
	 * <br/> Whereby the inverse can be considered as the resetting to default values if any.
	 * 
	 * @param set of set reference modules
	 */
	public void generate_UNSET_REFERENCE(Set<Module> set_reference_Modules) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * General CHANGe-generation method, that finds all relevant
	 * contexts and references that represent different CHANGe-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link ChangeGenerator}
	 * 
	 * @param eClassifier
	 */
	public void generate_CHANGE(EClassifier eClassifier) {
		// TODO Auto-generated method stub
	}

	/**
	 * General variant-generation method, that finds all variants of each given
	 * module. A variant either replaces the given module or it is added as an additional
	 * module to the given set.</br></br>
	 * Variants will be created if < < create > > nodes are abstract and need a concrete replacement.
	 * Each possible replacement results in a new module variant. If more than one < < create > > node
	 * is contained in the original rule or in any of the new variants (that required adding further mandatory <<create>> nodes),
	 * the cross product of all variants will be created...</br></br>
	 * Furthermore, variants for the sub types will also be created if super type replacements of contained node types is wished.
	 * This can also result in cross products of different sub type combinations. There can also be further replaces if
	 * one of the sub type is an abstract EClass again, and so on.
	 * </br></br>
	 * Variants are necessary for the completeness and correctness of module generation.
	 * For each setup the generation process will be delegated to {@link VariantPostprocessor}
	 * 
	 * @param eClassifier
	 * @return Set of disparate create modules for the given input modules.
	 */
	public Set<Module> VariantPostprocessor(EClassifier eClassifier) {
		// TODO Auto-generated method stub
		return null;
	}

}
