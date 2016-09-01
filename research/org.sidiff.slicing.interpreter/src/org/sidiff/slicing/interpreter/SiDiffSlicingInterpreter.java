package org.sidiff.slicing.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.slicing.configuration.SlicedEClass;
import org.sidiff.slicing.configuration.SlicingConfiguration;
import org.sidiff.slicing.configuration.SlicingMode;
import org.sidiff.slicing.slicingmodel.Slicing;
import org.sidiff.slicing.slicingmodel.SlicingmodelFactory;

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
	
	// ############### inner accessed fields ###############
	
	/**
	 * A {@link Map} containing the copies of all sliced elements
	 */
	private Map<EObject,EObject> slicedElements;
	
	/**
	 * A {@link Map} containing the copies of all sliced context elements
	 */
	private Map<EObject,EObject> slicedContextElements;
	
	/**
	 * A {@link Map} containing the copies of all sliced boundary elements
	 */
	private Map<EObject,EObject> slicedBoundaryElements;
	
	/**
	 * A {@link Map} for an efficient access to a given {@link SlicedEClass} referring to the respective {@link EClass}
	 */
	private Map<EClass, SlicedEClass> slicedEClasses;
	
	/**
	 * An {@link ECrossReferenceAdapter} for inversive navigation of {@link EReference}
	 */
	private ECrossReferenceAdapter adapter;
	
	/**
	 * control variable determining the depth of a recursion
	 */
	private int recursionDepth;
	/**
	 * Initializes the {@link SiDiffSlicingInterpreter}
	 * @param slicingConfiguration
	 * @param input
	 */
	public void init(SlicingConfiguration slicingConfiguration){
			
		this.slicingConfiguration = slicingConfiguration;
			
		// (re-)initialize inner accessed fields
		
		this.slicedElements = new HashMap<EObject, EObject>();
		this.slicedContextElements = new HashMap<EObject,EObject>();
		this.slicedBoundaryElements = new HashMap<EObject,EObject>();
		
		this.slicedEClasses = new HashMap<EClass, SlicedEClass>();
		
		for(SlicedEClass slicedEClass : this.slicingConfiguration.getSlicedEClasses()){
			this.slicedEClasses.put(slicedEClass.getType(), slicedEClass);
		}
		this.adapter = new ECrossReferenceAdapter();
		
		this.recursionDepth = 0;
	}
	
	/**
	 * 
	 * @param contexts
	 */
	public void slice(Set<EObject> input) {
		
		recursionDepth++;
		
		for(EObject in : input){
			
			Set<EObject> nextInput = new HashSet<EObject>();
			
			EObject clonedIn = cloneEObject(in);
			
			if(this.slicedEClasses.containsKey(in.eClass())){
				this.slicedContextElements.put(in,clonedIn);
				nextInput.addAll(getOutgoingNeighbours(in));	
				if(slicingConfiguration.getSlicingMode().equals(SlicingMode.PESSIMISTIC)){
					nextInput.addAll(getIncomingNeighbours(in));
				}
			}else{
				this.slicedBoundaryElements.put(in,clonedIn);
				nextInput.addAll(getMandatoryNeighbours(in));
			}
			
			this.slicedElements.put(in, clonedIn);
			
			nextInput.removeAll(this.slicedElements.keySet());
			
			slice(nextInput);
			
			
		}

		if(--recursionDepth == 0){
			for(EObject eObject : slicedElements.keySet()){
				relinkEReferences(eObject);
			}
		}
	}

	public Slicing getSlicedModel() {
		Slicing slicing = SlicingmodelFactory.eINSTANCE.createSlicing();
		for(EObject eObject : slicedContextElements.values()){
			slicing.getSlicedContextElements().add(eObject);
		}
		for(EObject eObject : slicedBoundaryElements.values()){
			slicing.getSlicedBoundaryElements().add(eObject);
		}
		
		return slicing;
	}	
	
	private EObject cloneEObject(EObject eObject){
		return EMFUtil.copyWithoutReferences(eObject);
	}
	
	private List<EObject> getMandatoryNeighbours(EObject eObject){
		return EMFModelAccess.getMandatoryNodeNeighbors(eObject);
	}
	
	private List<EObject> getIncomingNeighbours(EObject eObject){
		List<EObject> incomingNeighbours = new ArrayList<EObject>();
		if(!eObject.eResource().getResourceSet().eAdapters().contains(adapter)){
			eObject.eResource().getResourceSet().eAdapters().add(adapter);
		}
		Collection<Setting> settings = adapter.getInverseReferences(eObject, true);
		for(Setting setting : settings){
			incomingNeighbours.add(setting.getEObject());
		}
		return incomingNeighbours;
	}
	
	private List<EObject> getOutgoingNeighbours(EObject eObject){
		return EMFModelAccess.getNodeNeighbors(eObject);
	}
	
	private void relinkEReferences(EObject src){
		for (EReference eReference : src.eClass().getEAllReferences()) {
			if (!eReference.isDerived()) {
				if (eReference.isMany()) {
					List<EObject> targets = new ArrayList<EObject>();
					for (EObject target : (List<EObject>) src.eGet(eReference)) {
						if (slicedElements.containsKey(target)) {
							targets.add(slicedElements.get(target));
						}
					}
					slicedElements.get(src).eSet(eReference, targets);
				} else {
					EObject target = (EObject) src.eGet(eReference);
					if (!slicedElements.containsKey(target)) {
						if (eReference.isUnsettable()) {
							slicedElements.get(src).eUnset(eReference);
						} else {
							slicedElements.get(src).eSet(eReference, null);
						}
					} else {
						slicedElements.get(src).eSet(eReference, slicedElements.get(target));
					}
				}
			}
		}
	}
}
