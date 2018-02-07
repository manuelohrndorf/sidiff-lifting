package org.sidiff.slicer.structural;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.util.StringUtil;
import org.sidiff.slicer.ISlicer;
import org.sidiff.slicer.ISlicingConfiguration;
import org.sidiff.slicer.exception.WrongConfigurationException;
import org.sidiff.slicer.model.ModelSlice;
import org.sidiff.slicer.model.SlicedElement;
import org.sidiff.slicer.model.SlicedReference;
import org.sidiff.slicer.structural.configuration.Constraint;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.SlicedEReference;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.SlicingMode;
import org.sidiff.slicing.util.visualization.GraphUtil;

/**
 * 
 * @author cpietsch
 *
 */
public class StructureBasedSlicer implements ISlicer {

	/**
	 * The {@link SlicingConfiguration} determining the type and further
	 * constraints for the elements to be sliced
	 */
	private SlicingConfiguration slicingConfiguration;
	
	/**
	 * The {@link ModelSlice} containing all sliced elements
	 * and their sliced references
	 */
	private ModelSlice modelSlice;
	
	// ############### inner accessed fields ###############
	
	/**
	 * A {@link Map} for an efficient access to a given {@link SlicedEClass}
	 * referring to the respective {@link EClass}
	 */
	private Map<EClass, SlicedEClass> slicedEClasses;
	
	/**
	 * A {@link Map} for an efficient access to a given ordered list of
	 * {@link SlicedEReference}s referring to the respective {@link EReference}
	 */
	private Map<EReference, ArrayList<SlicedEReference>> slicedEReferences;
	
	/**
	 * elements which do not meet any slicing condition but are needed to fulfill
	 * a multiplicity constraint of the respective modeling language
	 */
	private Set<EObject> bounderyElements;
	
	/**
	 * An {@link ECrossReferenceAdapter} for inversive navigation of
	 * {@link EReference}
	 */
	private ECrossReferenceAdapter adapter;
	
	/**
	 * control variable determining the depth of a recursion
	 */
	private int recursionDepth;

	// ############### ISlicer ###############
	@Override
	public String getKey() {
		return this.getClass().getName();
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public Set<String> getDocumentTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canHandleDocTypes(Set<String> documentTypes) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canHandleModels(Collection<Resource> models) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void init(ISlicingConfiguration config) throws WrongConfigurationException {
		if(config instanceof SlicingConfiguration){
			
			this.slicingConfiguration = (SlicingConfiguration) config;
			this.modelSlice = new ModelSlice();
			
			// (re-)initialize inner accessed fields
			this.slicedEClasses = new HashMap<EClass, SlicedEClass>();
			this.slicedEReferences = new HashMap<EReference, ArrayList<SlicedEReference>>();
			this.bounderyElements = new HashSet<EObject>();
			
			for(SlicedEClass slicedEClass : this.slicingConfiguration.getSlicedEClasses()){
				this.slicedEClasses.put(slicedEClass.getType(), slicedEClass);
				for(SlicedEReference slicedEReference : slicedEClass.getSlicedEReferences()){
					if(this.slicedEReferences.get(slicedEReference.getType()) == null){
						ArrayList<SlicedEReference> slicedEReferencesValue =  new ArrayList<SlicedEReference>();
						slicedEReferencesValue.add(slicedEReference);
						this.slicedEReferences.put(slicedEReference.getType(),slicedEReferencesValue);
					}else{
						ArrayList<SlicedEReference> slicedEReferencesValue = this.slicedEReferences.get(slicedEReference.getType());
						if(slicedEReference.getOverwrite() != null && slicedEReferencesValue.contains(slicedEReference.getOverwrite())){
							int index = slicedEReferencesValue.indexOf(slicedEReference.getOverwrite());
							slicedEReferencesValue.add(index, slicedEReference);
						}else{
							this.slicedEReferences.get(slicedEReference.getType()).add(slicedEReference);
						}
					}
				}
			}
			this.adapter = new ECrossReferenceAdapter();
			
			this.recursionDepth = 0;
			
			GraphUtil.init();
		}else{
			throw new WrongConfigurationException();
		}
	}

	@Override
	public void slice(Collection<EObject> slicingCriteria) {
		if(this.recursionDepth == 0){
			LogUtil.log(LogEvent.MESSAGE, "############### Slicer STARTED ################");
		}
		
		this.recursionDepth++;
		
		for(EObject slicingCriterion : slicingCriteria){
			if(!this.modelSlice.contains(slicingCriterion)){
				
				SlicedElement slicedElement = new SlicedElement(slicingCriterion, bounderyElements.contains(slicingCriterion));
				this.modelSlice.addSlicedElement(slicedElement);
				
				GraphUtil.addNode(slicedElement.getOrigin());
				LogUtil.log(LogEvent.MESSAGE, String.format("%0" + recursionDepth + "d", 0).replace("0","*") + " " + "Sliced EClass: " + StringUtil.resolve(slicingCriterion));
				
				Set<EObject> nextSlicingCriteria = new HashSet<EObject>();
					
				if(this.slicingConfiguration.getSlicingMode().equals(SlicingMode.FORWARD)
						|| this.slicingConfiguration.getSlicingMode().equals(SlicingMode.BIDIRECTIONAL)){
					nextSlicingCriteria.addAll(getForwardElements(slicedElement));
				}
				
				if(this.slicingConfiguration.getSlicingMode().equals(SlicingMode.BACKWARD)
						|| this.slicingConfiguration.getSlicingMode().equals(SlicingMode.BIDIRECTIONAL)){
					nextSlicingCriteria.addAll(getBackwardElements(slicedElement));
				}
				
				if(this.slicingConfiguration.isCheckMultiplicity()){
					nextSlicingCriteria.addAll(getMandatoryNeighbours(slicedElement));
				}				
				
				slice(nextSlicingCriteria);
			}	
		}
			
		if(--recursionDepth == 0){
			LogUtil.log(LogEvent.MESSAGE, "############### Slicer FINISHED ###############");
		}
	}
	
	// ############### getter/setter methods ###############
	
	public void setModelSlice(ModelSlice modelSlice){
		this.modelSlice = modelSlice;
	}
	
	@Override
	public ModelSlice getModelSlice() {
		return this.modelSlice;
	}
	
	// ############### inner accessed methods ###############
	
	/**
	 * 
	 * @param slicedElement
	 * @return
	 */
	private Set<EObject> getForwardElements(SlicedElement slicedElement){
		Set<EObject> elements = new HashSet<EObject>();
		for(EReference eReference : slicedElement.getType().getEAllReferences()){
			if(!eReference.isDerived()){
				Collection<EObject> tgts = checkSlicingCondition(slicedElement.getOrigin(), eReference);
				for(EObject tgt : tgts){
					if(checkSlicingCondition(tgt, tgt.eClass())){
						
						slicedElement.addSlicedReference(new SlicedReference(eReference, tgt, false, false));
						elements.add(tgt);
						
						GraphUtil.addEdge(eReference, slicedElement.getOrigin(), tgt);
						LogUtil.log(LogEvent.MESSAGE, String.format("%0" + recursionDepth + "d", 0).replace("0", "*")
								+ " " + "Sliced EReferences: " + eReference.getName() + ": "
								+ StringUtil.resolve(slicedElement.getOrigin()) + "--->" + StringUtil.resolve(tgt));
					}
				}
			}
		}
		return elements;
	}

	/**
	 * 
	 * @param slicedElement
	 * @return
	 */
	private Set<EObject> getBackwardElements(SlicedElement slicedElement){
		Set<EObject> elements = new HashSet<EObject>();
		for(EObject src : getIncomingNeighbours(slicedElement.getOrigin())){
			if(checkSlicingCondition(src, src.eClass())){
				for(EReference eReference : src.eClass().getEAllReferences()){
					if(!eReference.isDerived()){
						Collection<EObject> tgts = checkSlicingCondition(src, eReference);
						if(tgts.contains(slicedElement.getOrigin())){
							slicedElement.addSlicedReference(new SlicedReference(eReference, src, true, false));
							elements.add(src);
							
							GraphUtil.addEdge(eReference, src, slicedElement.getOrigin());
							LogUtil.log(LogEvent.MESSAGE, String.format("%0" + recursionDepth + "d", 0).replace("0", "*")
									+ " " + "Sliced (inversed) EReferences: " + eReference.getName() + ": "
									+ StringUtil.resolve(src) + "--->" + StringUtil.resolve(slicedElement.getOrigin()));
						}
					}
				}
			}
		}
		return elements;
	}

	/**
	 * 
	 * @param slicedElement
	 * @return
	 */
	private Set<EObject> getMandatoryNeighbours(SlicedElement slicedElement) {
		Set<EObject> elements = new HashSet<EObject>();
		for(EReference eReference : slicedElement.getType().getEAllReferences()){
			if(!eReference.isDerived() && eReference.getLowerBound() > 0){
				int size = slicedElement.getSlicedReference(eReference).size();
				if (size < eReference.getLowerBound()) {
					Collection<EObject> tgts = getReferenceTargets(slicedElement.getOrigin(), eReference);
					Iterator<EObject> iterator = tgts.iterator();
					while (size < eReference.getLowerBound() && iterator.hasNext()) {
						EObject tgt = iterator.next();
						if (!slicedElement.contains(eReference, tgt)) {
							SlicedReference boundaryReference = new SlicedReference(eReference, tgt, false, true);
							slicedElement.addSlicedReference(boundaryReference);
							this.bounderyElements.add(tgt);
							elements.add(tgt);
							size++;
							
							GraphUtil.addEdge(eReference, slicedElement.getOrigin(), tgt);
							LogUtil.log(LogEvent.MESSAGE, String.format("%0" + recursionDepth + "d", 0).replace("0", "*")
									+ " " + "Sliced boundary EReferences: " + eReference.getName() + ": "
									+ StringUtil.resolve(slicedElement.getOrigin()) + "--->" + StringUtil.resolve(tgt));
						}
					}
				}
			}			
		}
		// TODO handle containment feature as outgoing reference with lower bound 1
//		EObject container = slicedElement.getOrigin().eContainer();
//		if(container != null){
//			EReference eReference = slicedElement.getOrigin().eContainmentFeature();
//			if(!modelSlice.contains(container)){
//				this.bounderyElements.add(container);
//			}
//		}
		return elements;
	}
	
	/**
	 * 
	 * @param eObject
	 * @return
	 */
	private Set<EObject> getIncomingNeighbours(EObject eObject){
		Set<EObject> incomingNeighbours = new HashSet<EObject>();
		if(!eObject.eResource().getResourceSet().eAdapters().contains(this.adapter)){
			eObject.eResource().getResourceSet().eAdapters().add(this.adapter);
		}
		Collection<Setting> settings = this.adapter.getInverseReferences(eObject, true);
		for(Setting setting : settings){
			incomingNeighbours.add(setting.getEObject());
		}
		return incomingNeighbours;
	}
	
	/**
	 * 
	 * @param eObject
	 * @param eReference
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Collection<EObject> getReferenceTargets(EObject eObject, EReference eReference){
		Collection<EObject> elements = new ArrayList<EObject>();
		if(eObject.eGet(eReference)!=null){
			Collection<EObject> tgts = new ArrayList<EObject>();
			if(eReference.isMany()){
				tgts.addAll((Collection<? extends EObject>) eObject.eGet(eReference));
			}else{
				tgts.add((EObject) eObject.eGet(eReference));
			}
			elements.addAll(tgts);
		}
		return elements;
	}
	
	/**
	 * checks the slicing condition for an element
	 * @param element
	 * @param type
	 * @return
	 */
	private boolean checkSlicingCondition(EObject element, EClass eClass) {
		
		boolean isValid = false;

		
		if (this.slicedEClasses.containsKey(eClass)) {
			Constraint constraint = this.slicedEClasses.get(eClass).getConstraint();
			isValid = constraint == null
					|| this.slicingConfiguration.getConstraintInterpreter().evaluate(constraint, element).getBoolean();

		} else {
			for (EClass superEClass : eClass.getESuperTypes()) {
				isValid = checkSlicingCondition(element, superEClass);
				break;
			}
		}
		
		return isValid;
	}
	
	/**
	 * 
	 * @param element
	 * @param eReference
	 * @return
	 */
	private Collection<EObject> checkSlicingCondition(EObject element, EReference eReference){
		
		Collection<EObject> validReferences = new ArrayList<EObject>();
		
		if (this.slicedEReferences.containsKey(eReference)) {
			
			List<EClass> superTypes = new LinkedList<EClass>();
			superTypes.add(element.eClass());
			superTypes.addAll(element.eClass().getEAllSuperTypes());
			
			for (SlicedEReference slicedEReference : this.slicedEReferences.get(eReference)) {
				if (superTypes.contains(slicedEReference.getSlicedEClass().getType())) {
					Constraint constraint = slicedEReference.getConstraint();
					if (constraint != null) {
						validReferences.addAll(slicingConfiguration.getConstraintInterpreter().evaluate(constraint, element).getEObjects());
					}else{
						validReferences.addAll(getReferenceTargets(element, eReference));
					}
				}
			}
		}
		return validReferences;
	}
}
