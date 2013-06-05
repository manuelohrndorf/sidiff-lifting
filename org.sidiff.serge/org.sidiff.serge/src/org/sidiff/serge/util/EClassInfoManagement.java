package org.sidiff.serge.util;

import java.util.*;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.*;
import org.sidiff.common.emf.access.EMFMetaAccess;

public class EClassInfoManagement {

	private HashMap<EClass,EClassInfo> eClassInfoMap = new HashMap<EClass, EClassInfo>();
	private HashMap<EClass,ArrayList<EClass>> abstractToConcreteEClassMap = new HashMap<EClass,ArrayList<EClass>>();
	private boolean profileApplicationInUse = false;
	
	public EClassInfoManagement(Boolean profileApplicationInUse) {
		this.profileApplicationInUse = profileApplicationInUse;
	}

	public void mapConcreteEClassesToAbstractSuperTypes(List<EPackage> ePackages) {
		for (EPackage ePackage : ePackages) {
			for (EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					EClass eClass = (EClass) eClassifier;
					if (eClass.isAbstract()) {				
						ArrayList<EClass> concreteEClasses = new ArrayList<EClass>();
						abstractToConcreteEClassMap.put(eClass, concreteEClasses);
					}
				}
			}
		}


		// check if it has an abstract ESuperType
		// and add it to abstractToConcreteEClassMap	
		for (EPackage ePackage : ePackages) {
			for (EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					EClass eClass = (EClass) eClassifier;			
					if (!eClass.isAbstract()) {
						for(EClass abstractEClass: abstractToConcreteEClassMap.keySet()) {
							if(abstractEClass.isSuperTypeOf(eClass)) {
								abstractToConcreteEClassMap.get(abstractEClass).add(eClass);
							}
						}
					}
				}
			}
		}
	}
	
	public void gatherAllEClassInfos(List<EPackage> ePackages) {
				
		for (EPackage ePackage : ePackages) {
			for (EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					EClass eClass = (EClass) eClassifier;					


					// get or create EClassInfo
					EClassInfo eClassInfo = eClassInfoMap.get(eClass);
					if(eClassInfo==null) {
						eClassInfo = new EClassInfo(eClass);
					}

					// if using some profiled meta model: map stereotype / meta classes
					if (profileApplicationInUse) findAndMapStereotypes(eClassInfo);

					for (EReference eRef : eClass.getEAllReferences()) {

						if(!eRef.isDerived()) {

							// get mandatory and optional children
							List<EClass> mC = findMandatoryChild(eRef);
							List<EClass> oC = findOptionalChild(eRef);

							// get neighbour
							List<EClass> mN = findMandatoryNeighbour(eRef);
							List<EClass> oN = findOptionalNeighbour(eRef);

							// get parent context
							List<EClass> mPC = findMandatoryParentContext(eRef);
							List<EClass> oPC = findOptionalParentContext(eRef);

							// get neighbour context
							List<EClass> mNC = findMandatoryNeighbourContext(eRef);
							List<EClass> oNC = findOptionalNeighbourContext(eRef);

							if(mC!=null) {
								// add mandatory child
								for(EClass child: mC) {
									if(eClassInfo.getMandatoryChildren().get(eRef)==null) {
										ArrayList<EClass> list = new ArrayList<EClass>();
										list.add(child);
										eClassInfo.getMandatoryChildren().put(eRef,list);
									}else{
										if(!eClassInfo.getMandatoryChildren().get(eRef).contains(child)) {
											eClassInfo.getMandatoryChildren().get(eRef).add(child);
										}
									}
								}
								add_MPC_toEClass((EClass) eRef.getEType(), eClass, eRef);
							}
							if(oC!=null) {
								// add optional child
								for(EClass child: oC) {
									if(eClassInfo.getOptionalChildren().get(eRef)==null) {
										ArrayList<EClass> list = new ArrayList<EClass>();
										list.add(child);	
										eClassInfo.getOptionalChildren().put(eRef,list);
									}else{
										if(!eClassInfo.getOptionalChildren().get(eRef).contains(child)) {
											eClassInfo.getOptionalChildren().get(eRef).add(child);
										}
									}
								}
								add_OPC_toEClass((EClass) eRef.getEType(), eClass, eRef);
							}

							if(mN!=null) {
								// add mandatory neighbour
								for(EClass neighbour: mN) {
									if(eClassInfo.getMandatoryNeighbours().get(eRef)==null) {
										ArrayList<EClass> list = new ArrayList<EClass>();
										list.add(neighbour);
										eClassInfo.getMandatoryNeighbours().put(eRef, list);
									}else{
										if(!eClassInfo.getMandatoryNeighbours().get(eRef).contains(neighbour)) {
											eClassInfo.getMandatoryNeighbours().get(eRef).add(neighbour);
										}
									}
								}
								// add mandatory neighbour context to neighbour
								add_MNC_toEClass((EClass) eRef.getEType(), eClass, eRef);	
							}
							if(oN!=null) {
								// add optional neighbour
								for(EClass neighbour: oN) {
									if(eClassInfo.getOptionalNeighbours().get(eRef)==null) {
										ArrayList<EClass> list = new ArrayList<EClass>();
										list.add(neighbour);
										eClassInfo.getOptionalNeighbours().put(eRef, list);
									}else{
										if(!eClassInfo.getOptionalNeighbours().get(eRef).contains(neighbour)) {
											eClassInfo.getOptionalNeighbours().get(eRef).add(neighbour);
										}
									}
								}
								// add optional neighbour context to neighbour
								add_ONC_toEClass((EClass) eRef.getEType(), eClass, eRef);
							}

							if(mPC!=null) {
								// add mandatory parent contexts
								for(EClass p: mPC) {
									if(eClassInfo.getMandatoryParentContext().get(eRef.getEOpposite())==null) {
										ArrayList<EClass> list = new ArrayList<EClass>();
										list.add(p);
										eClassInfo.getMandatoryParentContext().put(eRef.getEOpposite(), list);
									}else{
										if(!eClassInfo.getMandatoryParentContext().get(eRef.getEOpposite()).contains(p)) {
											eClassInfo.getMandatoryParentContext().get(eRef.getEOpposite()).add(p);
										}
									}
								}
							}
							if(oPC!=null) {
								// add optional parent contexts
								for(EClass p: oPC) {			
									if(eClassInfo.getOptionalParentContext().get(eRef.getEOpposite())==null) {
										ArrayList<EClass> list = new ArrayList<EClass>();
										list.add(p);
										eClassInfo.getOptionalParentContext().put(eRef.getEOpposite(), list);
									}else{
										if(!eClassInfo.getOptionalParentContext().get(eRef.getEOpposite()).contains(p)) {
											eClassInfo.getOptionalParentContext().get(eRef.getEOpposite()).add(p);
										}
									}
								}
							}

							if(mNC!=null) {
								// add mandatory neighbour contexts
								for(EClass n: mNC) {	
									if(eClassInfo.getMandatoryNeighbourContext().get(eRef.getEOpposite())==null) {
										ArrayList<EClass> list = new ArrayList<EClass>();
										list.add(n);
										eClassInfo.getMandatoryNeighbourContext().put(eRef.getEOpposite(), list);
									}else{
										if(!eClassInfo.getMandatoryNeighbourContext().get(eRef.getEOpposite()).contains(n)) {
											eClassInfo.getMandatoryNeighbourContext().get(eRef.getEOpposite()).add(n);
										}
									}								
								}
							}
							if(oNC!=null) {
								// add optional neighbour contexts
								for(EClass n: oNC) {
									if(eClassInfo.getOptionalNeighbourContext().get(eRef.getEOpposite())==null) {
										ArrayList<EClass> list = new ArrayList<EClass>();
										list.add(n);
										eClassInfo.getOptionalNeighbourContext().put(eRef.getEOpposite(), list);
									}else{
										if(!eClassInfo.getOptionalNeighbourContext().get(eRef.getEOpposite()).contains(n)) {
											eClassInfo.getOptionalNeighbourContext().get(eRef.getEOpposite()).add(n);
										}
									}	
								}
							}
						}
					}
					eClassInfoMap.put(eClass,eClassInfo);
				}
			}
		}
	}

	public EClassInfo getEClassInfo(EClass eClass) {
		return eClassInfoMap.get(eClass);
	}
	
	public ArrayList<EClass> getAllConcreteEClassesForAbstract(EClass eClass) {
		ArrayList<EClass> concreteEClasses = new ArrayList<EClass>();
		for(EClass abstractEClass: abstractToConcreteEClassMap.keySet()) {
			if(abstractEClass.equals(eClass)) {
				for(EClass replacement :abstractToConcreteEClassMap.get(abstractEClass)) {
					if(replacement.isAbstract()) {
						concreteEClasses = getAllConcreteEClassesForAbstract(replacement);
					}else{
						concreteEClasses.add(replacement);
					}					
				}				
			}
		}
		return concreteEClasses;
	}
	
	public HashMap<EReference,List<EClass>> getAllOptionalParentContext(EClass eClass) {
		
		HashMap<EReference,List<EClass>> map = new HashMap<EReference, List<EClass>>();
		
		// add direct optional parent contexts
		map.putAll(getEClassInfo(eClass).getOptionalParentContext());

		// add indirect optional parent contexts (= superType's parents)
		for(EClass superType: eClass.getEAllSuperTypes()) {
			EClassInfo infoOfSuperType = getEClassInfo(superType);
			
			if(infoOfSuperType!=null) {
				map.putAll(infoOfSuperType.getOptionalParentContext());
			}
			
		}				
		return map;
	}
	
	
	public boolean hasMultipleOccurences(EClass context, HashMap<EReference,List<EClass>> map) {
		int count = 0;
		for(Entry<EReference,List<EClass>>  entry: map.entrySet()) {
			List<EClass> contexts = entry.getValue();
			for(EClass currentContext: contexts) {
				if(currentContext.equals(context)) {
					count++;
				}
			}
		}
		if(count>1) {
			return true;
		}else{
			return false;
		}
	}
	
	public void linkSubTypesToSuperTypes(List<EPackage> ePackages) {
		
		for (EPackage ePackage : ePackages) {
			for (EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					EClass eClass = (EClass) eClassifier;
					
					// get superTypes and link eClass as subType to them
					for(EClass superType : eClass.getEAllSuperTypes()) {
						EClassInfo infoOfSuperType = getEClassInfo(superType);
						if(infoOfSuperType!=null) {
							infoOfSuperType.getSubTypes().add(eClass);
						}
					}
				}
			}
		}
	}
	

	
	public HashMap<EReference,List<EClass>> getAllOptionalNeighbourContext(EClass eClass) {
		
		HashMap<EReference,List<EClass>> map = new HashMap<EReference, List<EClass>>();
		// add direct optional neighbour contexts
		map.putAll(getEClassInfo(eClass).getOptionalNeighbourContext());
		
		// add indirect optional neighbour contexts
		for(EClass superType: eClass.getESuperTypes()) {
			EClassInfo infoOfSuperType = getEClassInfo(superType);
			
			if(infoOfSuperType!=null) {
				map.putAll(infoOfSuperType.getOptionalNeighbourContext());
			}
			
		}
				
		return map;
	}
	
	public HashMap<EReference,List<EClass>> getAllNeighbourContexts(EClass eClass) {
		
		HashMap<EReference,List<EClass>> map = new HashMap<EReference, List<EClass>>();
		EClassInfo eClassInfo = getEClassInfo(eClass);
		// add direct neighbour contexts
		map.putAll(eClassInfo.getOptionalNeighbourContext());
		map.putAll(eClassInfo.getMandatoryNeighbourContext());
		
		// add indirect neighbour contexts
		for(EClass superType: eClass.getESuperTypes()) {
			EClassInfo infoOfSuperType = getEClassInfo(superType);
			
			if(infoOfSuperType!=null) {
				map.putAll(infoOfSuperType.getOptionalNeighbourContext());
				map.putAll(infoOfSuperType.getMandatoryNeighbourContext());
			}			
		}
				
		return map;
		
	}
	
	public HashMap<EReference,List<EClass>> getAllParentContexts(EClass eClass) {
		
		HashMap<EReference,List<EClass>> map = new HashMap<EReference, List<EClass>>();
		EClassInfo eClassInfo = getEClassInfo(eClass);
		// add direct parent contexts
		map.putAll(eClassInfo.getOptionalParentContext());
		map.putAll(eClassInfo.getMandatoryParentContext());
		
		// add indirect parent contexts
		for(EClass superType: eClass.getESuperTypes()) {
			EClassInfo infoOfSuperType = getEClassInfo(superType);
			
			if(infoOfSuperType!=null) {
				map.putAll(infoOfSuperType.getOptionalParentContext());
				map.putAll(infoOfSuperType.getMandatoryParentContext());
			}			
		}
				
		return map;
		
	}
	
	public HashMap<EReference,List<EClass>> getAllChildren(EClass eClass) {
		
		HashMap<EReference,List<EClass>> map = new HashMap<EReference, List<EClass>>();
		EClassInfo eClassInfo = getEClassInfo(eClass);
		// add direct children
		map.putAll(eClassInfo.getOptionalChildren());
		map.putAll(eClassInfo.getMandatoryChildren());
		
		// add indirect children
		for(EClass superType: eClass.getESuperTypes()) {
			EClassInfo infoOfSuperType = getEClassInfo(superType);
			
			if(infoOfSuperType!=null) {
				map.putAll(infoOfSuperType.getOptionalChildren());
				map.putAll(infoOfSuperType.getMandatoryChildren());
			}			
		}
				
		return map;
		
	}
	
	public HashMap<EReference,List<EClass>> getAllNeighbours(EClass eClass) {
		
		HashMap<EReference,List<EClass>> map = new HashMap<EReference, List<EClass>>();
		EClassInfo eClassInfo = getEClassInfo(eClass);
		// add direct neighbours
		map.putAll(eClassInfo.getOptionalNeighbours());
		map.putAll(eClassInfo.getMandatoryNeighbours());
		
		// add indirect neighbours
		for(EClass superType: eClass.getESuperTypes()) {
			EClassInfo infoOfSuperType = getEClassInfo(superType);
			
			if(infoOfSuperType!=null) {
				map.putAll(infoOfSuperType.getOptionalNeighbours());
				map.putAll(infoOfSuperType.getMandatoryNeighbours());
			}			
		}
				
		return map;
		
	}

	public boolean hasAbstractMandatoryChildren(EClassInfo eClassInfo) {
		for(EClass child: eClassInfo.getMandatoryChildren().values().iterator().next()) {
			if(abstractToConcreteEClassMap.containsKey(child)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasAbstractMandatoryNeighbours(EClassInfo eClassInfo) {
		for(EClass neighbour: eClassInfo.getMandatoryNeighbours().values().iterator().next()) {
			if(abstractToConcreteEClassMap.containsKey(neighbour)) {
				return true;
			}
		}
		return false;
	}
	
	
	public EClass getEClassByName(String name) {
		for(EClass eClass:eClassInfoMap.keySet()) {
			if(eClass.getName().equals(name)) {
				return eClass;
			}
		}
		return null;
	}
		
	
	/**** private methods *********************************************************************************/

	private List<EClass> findMandatoryChild(EReference eRef) {
		if (eRef.isContainment() && eRef.getLowerBound() > 0) {
			EClass mC = (EClass) eRef.getEType();
			return Collections.singletonList(mC);
		}
		return null;
	}

	private List<EClass> findOptionalChild(EReference eRef) {
		if (eRef.isContainment() && (eRef.getUpperBound() - eRef.getLowerBound() > 0 || eRef.getUpperBound() == -1)) {																								
			EClass oC = (EClass) eRef.getEType();
			return Collections.singletonList(oC);
		}
		return null;
	}

	private List<EClass> findMandatoryNeighbour(EReference eRef) {
		EReference eOpposite = eRef.getEOpposite();
		EClass oC = null;
		if (!eRef.isContainment()&& eOpposite != null && eRef.getLowerBound() > 0) {
			oC = (EClass) eRef.getEType();
			return Collections.singletonList(oC);
			
		} else if (!eRef.isContainment() && eOpposite == null && eRef.getLowerBound() > 0) {
			oC = (EClass) eRef.getEType();
			return Collections.singletonList(oC);
		}
		return null;
	}

	private List<EClass> findOptionalNeighbour(EReference eRef) {

		EReference eOpposite = eRef.getEOpposite();
		EClass oN = null;
		if (!eRef.isContainment() && eOpposite != null) {
			if (!eOpposite.isContainment()
					&& (eRef.getUpperBound() - eRef.getLowerBound() > 0 || eRef.getUpperBound() == -1)) {
				oN = (EClass) eRef.getEType();
				if(oN.isAbstract()) {
					return abstractToConcreteEClassMap.get(oN);
				}
				return Collections.singletonList(oN);
			}
		} else if (!eRef.isContainment()
				&& eOpposite == null
				&& (eRef.getUpperBound() - eRef.getLowerBound() > 0 || eRef.getUpperBound() == -1)) {
			oN = (EClass) eRef.getEType();
			if(oN.isAbstract()) {
				return abstractToConcreteEClassMap.get(oN);
			}
			return Collections.singletonList(oN);
		}
		return null;
	}

	private List<EClass> findMandatoryParentContext(EReference eRef) {
		EReference eOpposite = eRef.getEOpposite();
		EClass mPC = null;
		if (eOpposite != null
				&& eOpposite.isContainment()
				&& eOpposite.getLowerBound() > 0) {
			mPC = (EClass) eRef.getEType();
			if(mPC.isAbstract()) {
				return abstractToConcreteEClassMap.get(mPC);
			}
			return Collections.singletonList(mPC);
		}
		return null;
	}

	private List<EClass> findOptionalParentContext(EReference eRef) {
		EReference eOpposite = eRef.getEOpposite();
		EClass oPC = null;
		if (eOpposite != null
				&& eOpposite.isContainment()
				&& ((eOpposite.getUpperBound() - eOpposite.getLowerBound() > 0 || eOpposite.getUpperBound() == -1))) {
			oPC = (EClass) eRef.getEType();
			if(oPC.isAbstract()) {
				return abstractToConcreteEClassMap.get(oPC);
			}
			return Collections.singletonList(oPC);
		}
		return null;
	}

	private List<EClass> findOptionalNeighbourContext(EReference eRef) {
		EReference eOpposite = eRef.getEOpposite();
		EClass oNC = null;
		if(eOpposite !=null && (eOpposite.isContainment() || eRef.isContainment())) {
			return null;
		}
		else if (eOpposite != null
				&& !eOpposite.isContainment()
				&& (eOpposite.getUpperBound() - eOpposite.getLowerBound() > 0 || eOpposite.getUpperBound() == -1)) {
			oNC = (EClass) eRef.getEType();
			if (oNC.isAbstract()) {
				return abstractToConcreteEClassMap.get(oNC);
			}
			return Collections.singletonList(oNC);
		}	
		return null;
	}

	private List<EClass> findMandatoryNeighbourContext(EReference eRef) {
		EReference eOpposite = eRef.getEOpposite();
		EClass mNC = null;
		if (eOpposite != null && !eOpposite.isContainment() && eOpposite.getLowerBound()>0) {
			mNC = (EClass) eRef.getEType();
			if(mNC.isAbstract()) {
				return abstractToConcreteEClassMap.get(mNC);
			}
			return Collections.singletonList(mNC);
		}
		return null;
	}
	
	private void add_MPC_toEClass(EClass eClass, EClass parent, EReference parentRef) {
		
		EClassInfo eClassInfo = eClassInfoMap.get(eClass);
		
		// create new EClassInfo if none existing yet
		if(eClassInfo == null) {
			eClassInfo = new EClassInfo(eClass);
			eClassInfoMap.put(eClass,eClassInfo);
		}
		if(eClassInfoMap.get(eClass).getMandatoryParentContext().get(parentRef)==null) {
			ArrayList<EClass> list = new ArrayList<EClass>();
			list.add(parent);
			eClassInfoMap.get(eClass).getMandatoryParentContext().put(parentRef, list);
		}else{
			if(!eClassInfoMap.get(eClass).getMandatoryParentContext().get(parentRef).contains(parent)) {
				useSuperType(parent, eClassInfoMap.get(eClass).getMandatoryParentContext().get(parentRef));				
			}
		}

	}
	
	private void add_OPC_toEClass(EClass eClass, EClass parent, EReference parentRef) {
		EClassInfo eClassInfo = eClassInfoMap.get(eClass);
		
		// create new EClassInfo if none existing yet
		if(eClassInfo == null) {
			eClassInfo = new EClassInfo(eClass);
			eClassInfoMap.put(eClass,eClassInfo);
		}
		if(eClassInfo.getOptionalParentContext().get(parentRef)==null) {
			ArrayList<EClass> list = new ArrayList<EClass>();
			list.add(parent);
			eClassInfo.getOptionalParentContext().put(parentRef,list);
		}else{
			if(!eClassInfoMap.get(eClass).getOptionalParentContext().get(parentRef).contains(parent)) {
				useSuperType(parent, eClassInfo.getOptionalParentContext().get(parentRef));
			}
		}
	}
	
	private void add_MNC_toEClass(EClass toNeighbour, EClass fromNeighbour, EReference directedRef) {
		EClassInfo eClassInfo = eClassInfoMap.get(toNeighbour);
		
		// create new EClassInfo if none existing yet
		if(eClassInfo == null) {
			eClassInfo = new EClassInfo(toNeighbour);
			eClassInfoMap.put(toNeighbour,eClassInfo);
		}
		
		if(eClassInfo.getMandatoryNeighbourContext().get(directedRef)==null) {
			ArrayList<EClass> list = new ArrayList<EClass>();
			list.add(fromNeighbour);
			eClassInfo.getMandatoryNeighbourContext().put(directedRef,list);	
		}else{
			if(!eClassInfo.getMandatoryNeighbourContext().get(directedRef).contains(fromNeighbour)) {
				eClassInfo.getMandatoryNeighbourContext().get(directedRef).add(fromNeighbour);
			}
		}
	}
	
	private void add_ONC_toEClass(EClass toNeighbour, EClass fromNeighbour, EReference directedRef) {
		EClassInfo eClassInfo = eClassInfoMap.get(toNeighbour);
		
		// create new EClassInfo if none existing yet
		if(eClassInfo == null) {
			eClassInfo = new EClassInfo(toNeighbour);
			eClassInfoMap.put(toNeighbour,eClassInfo);
		}
		if(eClassInfo.getOptionalNeighbourContext().get(directedRef)==null) {
			ArrayList<EClass> list = new ArrayList<EClass>();
			list.add(fromNeighbour);
			eClassInfo.getOptionalNeighbourContext().put(directedRef,list);	
		}else{
			if(!eClassInfo.getOptionalNeighbourContext().get(directedRef).contains(fromNeighbour)) {
				eClassInfo.getOptionalNeighbourContext().get(directedRef).add(fromNeighbour);
			}
		}
	}
	
	/**
	 * Method that adds a eClass to the given list, but only if it is not a subType
	 * of another list entry. If it is a super type of a list entry it will remove the
	 * according sub type and replace it with the super type. Furthermore it will
	 * remove any resulting superType-hierarchy inside the list.
	 * @param eClass
	 * @param list
	 * @return
	 */
	private List<EClass> useSuperType(EClass eClass, List<EClass> list) {
		ArrayList<EClass> removedEClasses = new ArrayList<EClass>();
		ArrayList<EClass> addedEClasses = new ArrayList<EClass>();
		
		// check, if eClass is superType for any in list
		for(EClass listEntry : list) {
			
			// if eClass is supertype (and not equal listEntry) -> swap
			if(!eClass.equals(listEntry) && eClass.isSuperTypeOf(listEntry)) {
				if(!removedEClasses.contains(listEntry)) {
					removedEClasses.add(listEntry);
				}
				if(!addedEClasses.contains(eClass)) {
					addedEClasses.add(eClass);
				}
			}
			// not equal and neither is supertype of the other, just add eClass to list
			else if(!eClass.equals(listEntry) && !listEntry.isSuperTypeOf(eClass) && !eClass.isSuperTypeOf(listEntry)){
				if(!addedEClasses.contains(eClass)) {
					addedEClasses.add(eClass);
				}
			}
			
		}
		// remove all listEntries, that will be replaced by their superType
		list.removeAll(removedEClasses);
		removedEClasses.clear();
		
		// for all addedEclasses check in list, if the newly added
		// eClass is or has supertype in it
		for(EClass added: addedEClasses) {
			for(EClass existing: list) {
				if(added.isSuperTypeOf(existing)) {
					if(!removedEClasses.contains(existing)) {
						removedEClasses.add(existing);
					}
				}
				if(existing.isSuperTypeOf(added)) {
					if(!removedEClasses.contains(added)) {
						removedEClasses.add(added);
					}
				}
			}
		}
		// remove all addedEClasses, that have already a supertype in list
		addedEClasses.removeAll(removedEClasses);
		// now only add the addedEClasses, which don't have a superType in the list
		list.addAll(addedEClasses);
		return list;
	}
	

	/**
	 * This method checks the current EClass in the EClassInfo for containing
	 * EStructuralFeatures that are typically used in profiled meta models.
	 * Their names normally begin with "base_". If there are such so called "extensions"
	 * the involved EClassInfos are filled with a mapping from meta class to stereotype
	 * and vice versa.
	 * @param eClassInfo
	 */
	private void findAndMapStereotypes(EClassInfo eClassInfo) {
		
		// check for profile mechanism / extension of meta classes
		List<EStructuralFeature> featureList = EMFMetaAccess.getEStructuralFeaturesByRegEx(eClassInfo.getTheEClass(), "^(base)_\\w+", true);
		for(EStructuralFeature extension: featureList) {
			
			// set extended meta class
			EClass metaClass = (EClass) extension.getEType();
			eClassInfo.addExtendedMetaClass(metaClass);
			
			// set self as stereotype for the extended meta class
			EClassInfo eClassInfoOfMetaClass = getEClassInfo(metaClass);
			if(eClassInfoOfMetaClass == null) {
				eClassInfoOfMetaClass = new EClassInfo(metaClass);
				eClassInfoMap.put(metaClass,eClassInfoOfMetaClass);
			}
			eClassInfoOfMetaClass.addStereotype(eClassInfo.getTheEClass());
		}
		
	}
}
