package org.sidiff.serge.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.emf.extensions.impl.EcoreHelper;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.exceptions.ModuleForInverseCreationRequiredException;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;
import org.sidiff.serge.generators.actions.AddGenerator;
import org.sidiff.serge.generators.actions.CreateGenerator;
import org.sidiff.serge.generators.actions.DeleteGenerator;
import org.sidiff.serge.generators.actions.RemoveGenerator;

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
		
		Set<Module> modules = new HashSet<Module>();	
	
		if(c.CREATE_CREATES) {
		
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
							
							if(FILTER.isAllowedAsDangling(context, OperationType.CREATE, c.REDUCETOSUPERTYPE_CREATEDELETE)) {
												
								CreateGenerator generator = new CreateGenerator(eRef, context, eInf);
								Module resultModule = generator.generate();
								
								modules.add(resultModule);
							
							}
							
							
						}
						
					}
				}
				/** In case of Stereotype, there are no contexts! Just create Rule with <<create>> Node for Stereotype ****************************/
				else{
						
				
					//TODO ProfileModelIntegration
				
				}
				
			
			}
		}
		return modules;
	}
	
	/**
	 * General DELETE-generation method that delegates each given CREATE-module of the input set
	 * to the inverse creation method {@link DeleteGenerator}.
	 * 
	 * @param set of create modules
	 * @return 
	 * @throws ModuleForInverseCreationRequiredException 
	 */
	public Set<Module> generate_DELETE(Set<Module> createModulesSet) throws ModuleForInverseCreationRequiredException {

		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_DELETES) {
			
			if(!c.CREATE_CREATES) throw new ModuleForInverseCreationRequiredException(OperationType.DELETE);
			
			for(Module createModule: createModulesSet) {
				
				DeleteGenerator generator = new DeleteGenerator(createModule);
				Module resultModule = generator.generate();
				
				modules.add(resultModule);
				
			}
		}
		
		return modules;
		
	}
	
	/**
	 * General MOVE-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link MoveGenerator}
	 * 
	 * @param eClassifier
	 * @return 
	 */
	public Set<Module> generate_MOVE(EClassifier eClassifier) {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_MOVES) {
			
			// TODO ...
			
		}
		
		return modules;
		
	}
	
	/**
	 * General MOVE-UP-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-UP-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link MoveUpGenerator}
	 * 
	 * @param eClassifier
	 * @return 
	 */
	public Set<Module> generate_MOVE_UP(EClassifier eClassifier) {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_MOVE_UPS) {
			
			// TODO ...
			
		}
		
		return modules;
		
	}
	
	/**
	 * General MOVE-DOWN-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-DOWN-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link MoveDownGenerator}
	 * 
	 * @param eClassifier
	 * @return 
	 */
	public Set<Module> generate_MOVE_DOWN(EClassifier eClassifier) {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_MOVE_DOWNS) {
			
			// TODO ...
			
		}
		
		return modules;
		
	}
	
	/**
	 * General MOVE-Combination-generation method, that finds all relevant
	 * contexts and references that represent different MOVE-Combination-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link MoveCombinationGenerator}
	 * 
	 * @param eClassifier
	 * @return 
	 */
	public Set<Module> generate_MOVE_COMBINATION(EClassifier eClassifier) {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_MOVE_COMBINATIONS) {
		
			// TODO ...
			
		}
		
		return modules;
		
	}
	
	/**
	 * General ADD-generation method, that finds all relevant
	 * contexts and references that represent different ADD-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link AddGenerator}
	 * 
	 * @param eClassifier
	 * @return Set of disparate add modules for the given eclass.
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generate_ADD(EClassifier eClassifier) throws OperationTypeNotImplementedException {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_ADDS) {
			
			EClassifierInfo eClassInfo = ECM.getEClassifierInfo(eClassifier);
			
			if (!FILTER.isAllowedAsModuleBasis(eClassifier, OperationType.ADD)) return null;
	
			//TODO implicit requirements
			// if (!isImplicitlyRequiredForFeatureInheritance(eClassifier)))  return;
			if (c.PROFILEAPPLICATIONINUSE && eClassInfo.isExtendedMetaClass() && !c.isRoot(eClassifier)) return null;
			
			EClass eClass = (EClass) eClassifier;
							
			// EReferences and their EOpposites, if any		
			for(EReference eRef: eClass.getEAllReferences()) {
	
				// don't consider derived, not changeable, unsettable and transient references
				if(!eRef.isDerived() && eRef.isChangeable() && !eRef.isTransient()) {
	
					// eRef == no containment reference  *************************************************************/
					if(!eRef.isContainment()) {
						EReference eOpposite = eRef.getEOpposite();
	
						// and skip eRefs where it's EOpposite is a containment
						if((eOpposite!=null && !eOpposite.isContainment()) || eOpposite==null) {
	
							EClass contextType = (EClass)eRef.getEType();
	
							if (!FILTER.isAllowedAsDangling(contextType,OperationType.ADD,c.REDUCETOSUPERTYPE_ADDREMOVE))  continue;
	
							int lower = eRef.getLowerBound();
							int upper = eRef.getUpperBound();
							
							if(!eRef.isContainment() 
								&& (upper==-1 || upper-lower>0)
								&& (
									(EcoreHelper.isInheritedReference(eRef, eClassifier) && !c.REDUCETOSUPERTYPE_ADDREMOVE)
									|| !EcoreHelper.isInheritedReference(eRef, eClassifier)
								   )
							   ) {
	
								AddGenerator generator = new AddGenerator(eRef, contextType);
								Module resultModule = generator.generate(eClassifier);
								
								modules.add(resultModule);
	
							}
						}
					}
				}
			}
		}
		return modules;
	}

	/**
	 * General REMOVE-generation method that delegates each given REMOVE-module of the input set
	 * to the inverse creation method {@link RemoveGenerator}.
	 * 
	 * @param set of remove modules
	 * @return 
	 * @throws ModuleForInverseCreationRequiredException 
	 */
	public Set<Module> generate_REMOVE(Set<Module> addModules) throws ModuleForInverseCreationRequiredException {
		
		Set<Module> modules	= new HashSet<Module>();
		
		if(c.CREATE_REMOVES) {
		
			if(!c.CREATE_ADDS) throw new ModuleForInverseCreationRequiredException(OperationType.REMOVE);
						
			for(Module addModule: addModules) {
				
				RemoveGenerator generator = new RemoveGenerator(addModule);
				Module resultModule = generator.generate();
				
				modules.add(resultModule);
				
			}
		}
		return modules;
		
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
	 * @return 
	 */
	public Set<Module> generate_UNSET_ATTRIBUTE(Set<Module> set_attribute_Modules) {
		
		Set<Module> modules	= new HashSet<Module>();
		// TODO ...
		return modules;
		
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
	 * @return 
	 */
	public Set<Module> generate_UNSET_REFERENCE(Set<Module> set_reference_Modules) {
		
		Set<Module> modules	= new HashSet<Module>();
		// TODO ...
		return modules;
		
	}
	
	/**
	 * General CHANGe-generation method, that finds all relevant
	 * contexts and references that represent different CHANGe-Modules for this eClassifier.
	 * For each setup the generation process will be delegated to {@link ChangeGenerator}
	 * 
	 * @param eClassifier
	 * @return 
	 */
	public Set<Module> generate_CHANGE(EClassifier eClassifier) {
		
		Set<Module> modules	= new HashSet<Module>();
		// TODO ...
		return modules;
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
