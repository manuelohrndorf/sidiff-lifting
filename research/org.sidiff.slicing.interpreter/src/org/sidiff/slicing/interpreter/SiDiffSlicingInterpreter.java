package org.sidiff.slicing.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.slicing.configuration.SlicedBoundaryEReference;
import org.sidiff.slicing.configuration.SlicedEClass;
import org.sidiff.slicing.configuration.SlicingConfiguration;
import org.sidiff.slicing.configuration.SlicingMode;
import org.sidiff.slicing.util.EMFModelAccess;

/**
 * 
 * @author cpietsch
 *
 */
public class SiDiffSlicingInterpreter {

	/**
	 * The {@link SlicingConfiguration}
	 */
	private SlicingConfiguration slicingConfiguration;
	
	/**
	 * The input {@link ResourceSet} containing all related {@link Resource}s.
	 */
	private ResourceSet input;
	
	/**
	 * The output {@link ResourceSet} containing a copy of all sliced elements of the related {@link Resource}s;
	 */
	private ResourceSet output;
	
	// ############### inner accessed fields ###############
	
	/**
	 * A {@link Map} initiated by the constructor for an efficient access to a given {@link SlicedEClass} referring to the respective {@link EClass}
	 */
	private Map<EClass, SlicedEClass> slicedEClasses;
	
	/**
	 * A {@link Map} initiated by the constructor for ...
	 */
	private Map<Resource,Resource> input2output;
	
	/**
	 * A {@link Map} initiated by the constructor for an efficient access to all {@link EObject}s typed by {@link EClass}
	 */
	private Map<EClass, Set<EObject>> eClass2EObjects;
	
	/**
	 * A {@link Set} containing all sliced context elements
	 */
	private Set<EObject> slicedContextElements;
	
	/**
	 * A {@link Set} containing all sliced elements related by boundary context elements
	 */
	private Set<EObject> slicedRelatedBoundaryElements;
	
	public SiDiffSlicingInterpreter(SlicingConfiguration slicingConfiguration, ResourceSet input) {
		
		this.slicingConfiguration = slicingConfiguration;
		this.input = input;
		
		init();
	}
	
	/**
	 * 
	 * @param contexts
	 */
	public void slice(List<EObject> contexts) {
		
		for(EObject context : contexts){
			if(this.slicedEClasses.containsKey(context.eClass())){
				
				if(!this.slicedContextElements.contains(context)){
					this.slicedContextElements.add(context);
				
					SlicedEClass slicedEClass = this.slicedEClasses.get(context);
				
					List<EObject> nextContextes = new ArrayList<EObject>();
					if(slicedEClass.isBoundary()){
						nextContextes.addAll(getBoundaryNeighbours(context, slicedEClass));					
					}else{
						nextContextes.addAll(getAllNeighbours(context));
					}
					this.slice(nextContextes);
				}
			}else{
				slicedRelatedBoundaryElements.add(context);
				slicedRelatedBoundaryElements.addAll(getMandatoryNeighboursOnly(context));
			}
		}
		copySlicedElements();
	}
	
	public SlicingConfiguration getSlicingConfiguration() {
		return slicingConfiguration;
	}

	public ResourceSet getInput() {
		return input;
	}

	public ResourceSet getOutput() {
		return output;
	}

	public Map<Resource, Resource> getInput2output() {
		return input2output;
	}
	
	private void init(){
		this.output = new ResourceSetImpl();
		this.input2output = new HashMap<Resource, Resource>();
		this.slicedContextElements = new HashSet<EObject>();
		this.eClass2EObjects = new HashMap<EClass, Set<EObject>>();
		
		for(SlicedEClass slicedEClass : slicingConfiguration.getSlicedEClasses()){
			slicedEClasses.put(slicedEClass.getType(), slicedEClass);
		}
		for(Resource i : input.getResources()){
			Resource o = new ResourceImpl();
			this.output.getResources().add(o);
			this.input2output.put(i, o);
			for (Iterator<EObject> iterator = i.getAllContents(); iterator.hasNext();) {
				EObject eObject = iterator.next();
				EClass eClass = eObject.eClass();
				
				if(!eClass2EObjects.containsKey(eClass)){
					Set<EObject> eObjects = new HashSet<EObject>();
					eClass2EObjects.put(eClass, eObjects);
				}
				
				eClass2EObjects.get(eClass).add(eObject);
			}
		}
	}
	
	private List<EObject> getBoundaryNeighbours(EObject context, SlicedEClass slicedEClass){
		List<EObject> boundaryNeighbours = new ArrayList<EObject>();
		for(SlicedBoundaryEReference outgoing : slicedEClass.getOutgoings()){
			for(EObject boundaryNeighbour : EMFModelAccess.getEReferenceTargets(context, outgoing.getType(), false)){
				if(!this.slicedContextElements.contains(boundaryNeighbour)){
					boundaryNeighbours.add(boundaryNeighbour);
				}
			}
		}
		if(this.slicingConfiguration.getSlicingMode().equals(SlicingMode.PESSIMISTIC)){
			for(SlicedBoundaryEReference incomings : slicedEClass.getOutgoings()){
				EReference eReference = incomings.getType();
			
			
			
				EObject nextContext = (EObject) context.eGet(eReference);
				boundaryNeighbours.add(nextContext);
			}
		}
		return boundaryNeighbours;
	}
	
	private List<EObject> getAllNeighbours(EObject context){
		List<EObject> neighbours = new ArrayList<EObject>();
		for(EReference eReference : context.eClass().getEAllReferences()){
			for(EObject nextContext : EMFModelAccess.getEReferenceTargets(context, eReference, false)){
				if(!this.slicedContextElements.contains(nextContext)){
					neighbours.add(nextContext);
				}
			}
		}
		return neighbours;
	}
	
	private List<EObject> getMandatoryNeighboursOnly(EObject context){
		List<EObject> mandatoryNeighbours = new ArrayList<EObject>();
		for(EReference eReference : context.eClass().getEAllReferences()){
			for(EObject mandatroyNeighbour : EMFModelAccess.getEReferenceTargets(context, eReference, true)){
				if(!(this.slicedContextElements.contains(mandatroyNeighbour) || this.slicedRelatedBoundaryElements.contains(mandatroyNeighbour))){
					mandatoryNeighbours.add(mandatroyNeighbour);
					mandatoryNeighbours.addAll(getMandatoryNeighboursOnly(mandatroyNeighbour));
				}
			}
		}
		return mandatoryNeighbours;
	}
	
	@SuppressWarnings("unchecked")
	private void copySlicedElements(){
		Copier copier = new Copier();
		copier.copyAll(slicedContextElements);
		copier.copyAll(slicedRelatedBoundaryElements);
		
		for(EObject origin : copier.keySet()){
			for(EReference eReference : origin.eClass().getEAllReferences()){
				if(!eReference.isDerived()){
					if(eReference.isMany()){
						List<EObject> copies = new ArrayList<EObject>();
						for(EObject eObject : (EList<EObject>) origin.eGet(eReference)){
							copies.add(copier.get(eObject));
						}
						copier.get(origin).eSet(eReference, copies);
					}else{
						EObject copy = copier.get(origin);
						EObject relatedCopy = (EObject) copy.eGet(eReference);
						if(relatedCopy == null){
							if(eReference.isUnsettable()){
								copy.eUnset(eReference);
							}else{
								copy.eSet(eReference, null);
							}
						}else{
							copy.eSet(eReference, relatedCopy);
						}
					}
				}
			}
		}
		
		//TODO change resources
	}

}
