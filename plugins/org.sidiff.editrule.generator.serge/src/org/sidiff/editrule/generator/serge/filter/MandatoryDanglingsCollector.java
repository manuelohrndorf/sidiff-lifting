package org.sidiff.editrule.generator.serge.filter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
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
	
	public static Set<EClassifier> collectConfiguredAndRequiredDanglingClassifiers() {
		
		ClassifierInclusionConfiguration CIC = ClassifierInclusionConfiguration.getInstance();		
		Set<EClassifier> result = new HashSet<EClassifier>();	
		
		// Collect all ALWAYS included dangling types
		for (EClassifier eC : CIC.getDanglingInclusionTypes().keySet()) {
			if (InclusionType.ALWAYS.equals(CIC.getFocusInclusuionType(eC)))
				result.add(eC);
		}
		// Collect all ALWAYS included focus types
		for (EClassifier eC : CIC.getFocusInclusionTypes().keySet()) {
			if (InclusionType.ALWAYS.equals(CIC.getFocusInclusuionType(eC)))
				result.add(eC);
		}
		
		// now find all recursive mandatory children and only the
		// direct mandatory neighbors and parent contexts including consideration of their subtype hierarchy
		// Note: of the latter we only need direct relations since more recursive relations of danglings
		// are redundant for the rule creation/execution
		result.addAll(recursivlyCollectMandatoryChildren(result));
		result.addAll(collectAllDirectMandatoryNeighbors(result));
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
					InclusionType own_ict = CIC.getDanglingInclusuionType(child);
					InclusionType default_ict = CIC.getDefaultDanglingInclusionType();
					if( (default_ict.equals(InclusionType.ALWAYS)
							|| default_ict.equals(InclusionType.IF_REQUIRED))
							&& !own_ict.equals(InclusionType.NEVER)) {
							
							if(!resultSet.contains(child)) {
								resultSet.add(child);
								addedNewTypes = true;
							}
						}	
										
					// find out if sub type of child is configured by user to be used
					Set<EClassifier> childsSubTypes = ECM.getAllConcreteSubTypes(child);
					for(EClassifier subTypeOfChild: childsSubTypes) {
						InclusionType own_subtype_ict = CIC.getDanglingInclusuionType(subTypeOfChild);
						if( (default_ict.equals(InclusionType.ALWAYS)
							|| default_ict.equals(InclusionType.IF_REQUIRED))
							&& !own_subtype_ict.equals(InclusionType.NEVER)) {
							
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
					InclusionType own_ict = CIC.getDanglingInclusuionType(neighbor);
					InclusionType default_ict = CIC.getDefaultDanglingInclusionType();
					if( (default_ict.equals(InclusionType.ALWAYS)
							|| default_ict.equals(InclusionType.IF_REQUIRED))
							&& !own_ict.equals(InclusionType.NEVER)) {
							
							if(!resultSet.contains(neighbor)) {
								resultSet.add(neighbor);
								addedNewTypes = true;
							}
						}	
										
					// find out if sub type of neighbor is configured by user to be used
					Set<EClassifier> neighborsSubTypes = ECM.getAllSubTypes(neighbor);
					for(EClassifier subtypeOfNeighbor: neighborsSubTypes) {
						InclusionType own_subtype_ict = CIC.getDanglingInclusuionType(subtypeOfNeighbor);
						if( (default_ict.equals(InclusionType.ALWAYS)
							|| default_ict.equals(InclusionType.IF_REQUIRED))
							&& !own_subtype_ict.equals(InclusionType.NEVER)) {
							
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

		for(EClassifier eClassifier: alreadyfound) {

			EClassifierInfo eInfo = ECM.getEClassifierInfo(eClassifier);

			//get mandatory parent contexts
			for(List<EClassifier> parentContextsList: ECM.getAllParentContexts(eClassifier,false).values()) {
				for(EClassifier parent: parentContextsList) {
					EClassifierInfo parentContextInfo = ECM.getEClassifierInfo(parent);

					// add parent to resultSet if it is configured by user to be used
					InclusionType own_ict = CIC.getDanglingInclusuionType(parent);
					InclusionType default_ict = CIC.getDefaultDanglingInclusionType();
					if( (default_ict.equals(InclusionType.ALWAYS)
							|| default_ict.equals(InclusionType.IF_REQUIRED))
							&& !own_ict.equals(InclusionType.NEVER)) {

						if(!resultSet.contains(parent)) {
							resultSet.add(parent);						
							addedNewTypes = true;
						}

					}	

					// find out if sub type of parent is configured by user to be used
					Set<EClassifier> parentsSubtypes = ECM.getAllSubTypes(parent);
					for(EClassifier subtypeOfParent: parentsSubtypes) {
						InclusionType own_subtype_ict = CIC.getDanglingInclusuionType(subtypeOfParent);
						if( (default_ict.equals(InclusionType.ALWAYS)
								|| default_ict.equals(InclusionType.IF_REQUIRED))
								&& !own_subtype_ict.equals(InclusionType.NEVER)) {

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
