package org.sidiff.editrule.generator.serge.filter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.filter.ClassifierInclusionConfiguration.InclusionType;


/**
 * This class is responsible for collecting all recursive mandatory children
 * and direct mandatory neighbors and direct mandatory parent contexts
 * for other classifiers. Subtype hierarchies are also considered.
 * 
 * @author mrindt
 *
 */
public abstract class MandatoryDanglingsCollector {
	
	/**
	 * Collect only the mandatory children and neigbors of those classifiers recursively
	 * which the user has defined to generate modules for or as danglings.
	 * @return
	 */
	public static Set<EClassifier> collectConfiguredAndRequiredDanglingClassifiers() {
		
		ClassifierInclusionConfiguration CIC = ClassifierInclusionConfiguration.getInstance();		
		Set<EClassifier> result = new HashSet<EClassifier>();	
		
		// Collect all ALWAYS included dangling types
		for (EClassifier eC : CIC.getDanglingClassifierInclusionTypes().keySet()) {
			if (InclusionType.ALWAYS.equals(CIC.getFocusInclusionType(eC)))
				result.add(eC);
		}
		// Collect all ALWAYS included focus types
		if(!CIC.getFocusClassifierInclusionTypes().isEmpty()) {
			for (EClassifier eC : CIC.getFocusClassifierInclusionTypes().keySet()) {
				if (InclusionType.ALWAYS.equals(CIC.getFocusInclusionType(eC)))
					result.add(eC);
			}
		}else {
			// Collect all Classifiers if only the default inclusion option is set to ALWAYS
			if(CIC.getDefaultFocusInclusionType().equals(InclusionType.ALWAYS)) {
				result.addAll(Configuration.getInstance().METAMODEL.getEClassifiers());
			}
		}
		
		// now find all recursive mandatory children and only the
		// direct mandatory neighbors including consideration of their subtype hierarchy
		// Note: of the latter we only need direct relations since more recursive relations of danglings
		// are redundant for the rule creation/execution
		result.addAll(recursivlyCollectMandatoryChildren(result));
		result.addAll(collectAllDirectMandatoryNeighbors(result));
		
		return result;
	}

	public static Set<EClassifier> collectConfiguredAndRequiredDanglingParentContextClassifiers() {
		
		Set<EClassifier> result = new HashSet<EClassifier>();
				
		result.addAll(collectAllDirectParentContexts(result));
		
		return result;
	}
	
	private static Set<EClassifier> recursivlyCollectMandatoryChildren(Set<EClassifier> alreadyfound) {		
		
		EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
		ClassifierInclusionConfiguration CIC = ClassifierInclusionConfiguration.getInstance();
		
		Set<EClassifier> resultSet = new HashSet<EClassifier>(alreadyfound);
		
		for(EClassifier eClassifier: alreadyfound) {
			
			EClassifierInfo eInfo = ECM.getEClassifierInfo(eClassifier);
			Boolean addedNewTypes = false;
			
			//get recursively mandatory children
			for(List<EClassifier> childList: eInfo.getMandatoryChildren().values()) {
							
				for(EClassifier child: childList) {
					EClassifierInfo childInfo = ECM.getEClassifierInfo(child);
					
					// add child to resultSet if it is configured by user to be used
					InclusionType own_ict = CIC.getDanglingInclusionType(child);
					InclusionType default_ict = CIC.getDefaultDanglingInclusionType();
					if( (default_ict.equals(InclusionType.ALWAYS)
							|| default_ict.equals(InclusionType.IF_REQUIRED))
							&& (own_ict!=null && !own_ict.equals(InclusionType.NEVER))) {
							
							if(!resultSet.contains(child)) {
								resultSet.add(child);
								addedNewTypes = true;
							}
						}	
										
					// find out if sub type of child is configured by user to be used
					Set<EClassifier> childsSubTypes = ECM.getAllConcreteSubTypes(child);
					for(EClassifier subTypeOfChild: childsSubTypes) {
						InclusionType own_subtype_ict = CIC.getDanglingInclusionType(subTypeOfChild);
						if( (default_ict.equals(InclusionType.ALWAYS)
							|| default_ict.equals(InclusionType.IF_REQUIRED))
							&& (own_ict!=null && !own_subtype_ict.equals(InclusionType.NEVER))) {
							
							if(!resultSet.contains(subTypeOfChild)) {
								resultSet.add(subTypeOfChild);
								addedNewTypes = true;
							}
						}					
					}
					if(addedNewTypes) {
						resultSet.addAll(recursivlyCollectMandatoryChildren(resultSet));
					}
				}
			}	
		}
			
		return resultSet;
		
	}
	
	private static Set<EClassifier> collectAllDirectMandatoryNeighbors(Set<EClassifier> alreadyfound) {		

		EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
		ClassifierInclusionConfiguration CIC = ClassifierInclusionConfiguration.getInstance();

		Set<EClassifier> resultSet = new HashSet<EClassifier>(alreadyfound);
		Boolean addedNewTypes = false;

		for(EClassifier eClassifier: alreadyfound) {
			
			EClassifierInfo eInfo = ECM.getEClassifierInfo(eClassifier);
			
			//get recursively mandatory neighbors
			for(List<EClassifier> neighborList: ECM.getAllMandatoryNeighbours(eClassifier).values()) {
				for(EClassifier neighbor: neighborList) {
					EClassifierInfo neighborInfo = ECM.getEClassifierInfo(neighbor);
					
					// add neighbor to resultSet if it is configured by user to be used
					InclusionType own_ict = CIC.getDanglingInclusionType(neighbor);
					InclusionType default_ict = CIC.getDefaultDanglingInclusionType();
					if( (default_ict.equals(InclusionType.ALWAYS)
							|| default_ict.equals(InclusionType.IF_REQUIRED))
							&& (own_ict!=null && !own_ict.equals(InclusionType.NEVER))) {
							
							if(!resultSet.contains(neighbor)) {
								resultSet.add(neighbor);
								addedNewTypes = true;
							}
						}	
										
					// find out if sub type of neighbor is configured by user to be used
					Set<EClassifier> neighborsSubTypes = ECM.getAllSubTypes(neighbor);
					for(EClassifier subtypeOfNeighbor: neighborsSubTypes) {
						InclusionType own_subtype_ict = CIC.getDanglingInclusionType(subtypeOfNeighbor);
						if( (default_ict.equals(InclusionType.ALWAYS)
							|| default_ict.equals(InclusionType.IF_REQUIRED))
							&& (own_ict!=null && !own_subtype_ict.equals(InclusionType.NEVER))) {
							
							if(!resultSet.contains(subtypeOfNeighbor)) {
								resultSet.add(subtypeOfNeighbor);
								addedNewTypes = true;
							}
							
						}					
					}
				}
			}			
		}
			
		return resultSet;
		
	}
	
	private static Set<EClassifier> collectAllDirectParentContexts(Set<EClassifier> alreadyfound) {		

		EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
		ClassifierInclusionConfiguration CIC = ClassifierInclusionConfiguration.getInstance();

		Set<EClassifier> resultSet = new HashSet<EClassifier>(alreadyfound);
		Boolean addedNewTypes = false;

		// if alreadyfound is empty add:
		// add all focal elements as parents (in case of ALWAYS)
		// and all (always) dangling elements (in case of ALWAYS)
		if(alreadyfound.isEmpty()) {			
			alreadyfound.addAll(CIC.getFocusClassifiersByInclusionType(InclusionType.ALWAYS));
			alreadyfound.addAll(CIC.getDanglingClassifiersByInclusionType(InclusionType.ALWAYS));
		}
				
		// handle sub types of parents...
		for(EClassifier eClassifier: alreadyfound) {

			//get mandatory parent contexts
			for(List<EClassifier> parentContextsList: ECM.getAllParentContexts(eClassifier,false).values()) {
				for(EClassifier parent: parentContextsList) {

					// add parent to resultSet if it is configured by user to be used
					InclusionType own_ict = CIC.getDanglingInclusionType(parent);
					InclusionType default_ict = CIC.getDefaultDanglingInclusionType();
					if( (default_ict.equals(InclusionType.ALWAYS)
							|| default_ict.equals(InclusionType.IF_REQUIRED))
							&& (own_ict!=null && !own_ict.equals(InclusionType.NEVER))) {

						if(!resultSet.contains(parent)) {
							resultSet.add(parent);						
							addedNewTypes = true;
						}

					}	

					// find out if sub type of parent is configured by user to be used
					Set<EClassifier> parentsSubtypes = ECM.getAllSubTypes(parent);
					for(EClassifier subtypeOfParent: parentsSubtypes) {
						InclusionType own_subtype_ict = CIC.getDanglingInclusionType(subtypeOfParent);
						if( (default_ict.equals(InclusionType.ALWAYS))
								&& 
								(own_subtype_ict==null || !own_subtype_ict.equals(InclusionType.NEVER))) {

							if(!resultSet.contains(subtypeOfParent)) {
								resultSet.add(subtypeOfParent);
								addedNewTypes = true;
							}

						}					
					}
				}			
			}
		}

		return resultSet;

	}

}
