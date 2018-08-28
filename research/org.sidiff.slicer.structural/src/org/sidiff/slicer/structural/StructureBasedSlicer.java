package org.sidiff.slicer.structural;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.util.StringUtil;
import org.sidiff.entities.util.ImportFailedException;
import org.sidiff.slicer.ISlicer;
import org.sidiff.slicer.ISlicingConfiguration;
import org.sidiff.slicer.exception.WrongConfigurationException;
import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.slice.SliceFactory;
import org.sidiff.slicer.slice.SlicedElement;
import org.sidiff.slicer.slice.util.SliceImporter;
import org.sidiff.slicer.structural.configuration.Constraint;
import org.sidiff.slicer.structural.configuration.IConstraintResult;
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
	private Map<EReference, List<SlicedEReference>> slicedEReferences;

	/**
	 * An {@link ECrossReferenceAdapter} for inversive navigation of
	 * {@link EReference}
	 */
	private ECrossReferenceAdapter adapter;

	/**
	 * Importer used to import model slices, sliced elements, references and attributes.
	 */
	private SliceImporter importer;

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
		if(slicingConfiguration == null) {
			throw new IllegalStateException("slicer is not initialized; call init first");
		}
		return new HashSet<>(slicingConfiguration.getDocumentTypes());
	}

	@Override
	public boolean canHandleDocTypes(Set<String> documentTypes) {
		return getDocumentTypes().containsAll(documentTypes);
	}

	@Override
	public boolean canHandleModels(Collection<Resource> models) {
		Set<String> docTypes = getDocumentTypes();
		for(Resource model : models) {
			String docType = EMFModelAccess.getCharacteristicDocumentType(model);
			if(docType == null || !docTypes.contains(docType)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void init(ISlicingConfiguration config) throws WrongConfigurationException {
		if(!(config instanceof SlicingConfiguration)) {
			throw new WrongConfigurationException();
		}

		this.slicingConfiguration = (SlicingConfiguration)config;
		this.importer = new SliceImporter();
		this.importer.init(SliceFactory.eINSTANCE.createModelSlice());

		// (re-)initialize inner accessed fields
		this.slicedEClasses = new HashMap<>();
		this.slicedEReferences = new HashMap<>();

		for(SlicedEClass slicedEClass : this.slicingConfiguration.getSlicedEClasses()) {
			this.slicedEClasses.put(slicedEClass.getType(), slicedEClass);
			for(SlicedEReference slicedEReference : slicedEClass.getSlicedEReferences()) {
				if(this.slicedEReferences.get(slicedEReference.getType()) == null) {
					ArrayList<SlicedEReference> slicedEReferencesValue =  new ArrayList<SlicedEReference>();
					slicedEReferencesValue.add(slicedEReference);
					this.slicedEReferences.put(slicedEReference.getType(), slicedEReferencesValue);
				} else {
					List<SlicedEReference> slicedEReferencesValue = this.slicedEReferences.get(slicedEReference.getType());
					if(slicedEReference.getOverwrite() != null && slicedEReferencesValue.contains(slicedEReference.getOverwrite())) {
						int index = slicedEReferencesValue.indexOf(slicedEReference.getOverwrite());
						slicedEReferencesValue.add(index, slicedEReference);
					} else {
						slicedEReferencesValue.add(slicedEReference);
					}
				}
			}
		}
		this.adapter = new ECrossReferenceAdapter();
	}

	@Override
	public ModelSlice slice(Collection<EObject> initialCriteria) throws ImportFailedException {
		LogUtil.log(LogEvent.MESSAGE, "############### Slicer STARTED ################");

		// iterate over all the slicing criteria
		// new criteria will be added add the end of the list
		// the called methods must ensure that no existing element is added
		List<EObject> criteria = new LinkedList<>(initialCriteria);
		for(int i = 0; i < criteria.size(); i++) {
			EObject slicingCriterion = criteria.get(i);
			SlicedElement slicedElement = importer.importEObject(slicingCriterion);

			GraphUtil.get(this).addNode(slicedElement.getObject());
			LogUtil.log(LogEvent.MESSAGE, "Sliced EClass: " + StringUtil.resolve(slicingCriterion));

			SlicingMode slicingMode = slicingConfiguration.getSlicingMode();
			if(slicingMode == SlicingMode.FORWARD || slicingMode == SlicingMode.BIDIRECTIONAL) {
				addForwardElements(criteria, slicedElement);
			}
			if(slicingMode == SlicingMode.BACKWARD || slicingMode == SlicingMode.BIDIRECTIONAL) {
				addBackwardElements(criteria, slicedElement);
			}
			if(slicingConfiguration.isCheckMultiplicity()) {
				addMandatoryNeighbours(criteria, slicedElement);
			}
		}

		LogUtil.log(LogEvent.MESSAGE, "############### Slicer FINISHED ###############");
		
		return this.importer.getModelSlice();
	}

	// ############### inner accessed methods ###############

	/**
	 * 
	 * @param list
	 * @param slicedElement
	 * @throws ImportFailedException 
	 */
	private void addForwardElements(List<EObject> list, SlicedElement slicedElement) throws ImportFailedException {
		for(EReference eReference : slicedElement.getType().getEAllReferences()) {
			if(!eReference.isDerived()) {
				EObject src = slicedElement.getObject();
				Collection<EObject> tgts = computeSlicedReferenceTargets(src, eReference);
				for(EObject tgt : tgts) {
					if(checkSlicingCondition(tgt)) {
						importer.importEReference(eReference, src, tgt);
						if(!list.contains(tgt)) {
							list.add(tgt);
						}

						GraphUtil.get(this).addEdge(eReference, src, tgt);
						LogUtil.log(LogEvent.MESSAGE, "Sliced EReference: " + StringUtil.resolve(src)
							+ " ---[" + eReference.getName() + "]---> " + StringUtil.resolve(tgt));
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param list
	 * @param slicedElement
	 * @throws ImportFailedException 
	 */
	private void addBackwardElements(List<EObject> list, SlicedElement slicedElement) throws ImportFailedException {
		EObject tgt = slicedElement.getObject();
		for(EObject src : getIncomingNeighbours(tgt)) {
			if(checkSlicingCondition(src)) {
				for(EReference eReference : src.eClass().getEAllReferences()) {
					if(!eReference.isDerived()) {
						Collection<EObject> targets = computeSlicedReferenceTargets(src, eReference);
						if(targets.contains(tgt))
						{
							importer.importEReference(eReference, src, tgt);
							if(!list.contains(src)) {
								list.add(src);
							}

							GraphUtil.get(this).addEdge(eReference, src, tgt);
							LogUtil.log(LogEvent.MESSAGE, "Sliced inversed EReference: " + StringUtil.resolve(tgt)
								+ " <---[" + eReference.getName() + "]--- " + StringUtil.resolve(src));
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param list
	 * @param slicedElement
	 * @throws ImportFailedException 
	 */
	private void addMandatoryNeighbours(List<EObject> list, SlicedElement slicedElement) throws ImportFailedException {
		EObject src = slicedElement.getObject();
		for(EReference eReference : slicedElement.getType().getEAllReferences()) {
			if(!eReference.isDerived() && eReference.getLowerBound() > 0) {
				int size = slicedElement.getReferences(eReference).size();
				if(size < eReference.getLowerBound()) {
					Collection<EObject> tgts = getReferenceTargets(src, eReference);
					Iterator<EObject> iterator = tgts.iterator();
					while(size < eReference.getLowerBound() && iterator.hasNext()) {
						EObject tgt = iterator.next();
						importer.importEReference(eReference, src, tgt);

						if(!list.contains(tgt)) {
							list.add(tgt);
						}
						size++;

						GraphUtil.get(this).addEdge(eReference, src, tgt);
						LogUtil.log(LogEvent.MESSAGE, "Sliced boundary EReference: " + StringUtil.resolve(src)
							+ " ---[" + eReference.getName() + "]---> " + StringUtil.resolve(tgt));
					}
				}
			}
		}

		// handle containment feature as outgoing reference with lower bound 1
		EObject container = src.eContainer();
		if(container != null) {
			EReference eReference = src.eContainmentFeature();
			importer.importEReference(eReference, container, src);

			if(!list.contains(container)) {
				list.add(container);
			}

			GraphUtil.get(this).addEdge(eReference, container, src);
			LogUtil.log(LogEvent.MESSAGE, "Sliced containment EReference: " + StringUtil.resolve(container)
				+ " ---[" + eReference.getName() + "]---> " + StringUtil.resolve(src));
		}
	}

	/**
	 * 
	 * @param eObject
	 * @return
	 */
	private Set<EObject> getIncomingNeighbours(EObject eObject) {
		if(eObject.eResource() != null && eObject.eResource().getResourceSet() != null
				&& !eObject.eResource().getResourceSet().eAdapters().contains(adapter)) {
			eObject.eResource().getResourceSet().eAdapters().add(adapter);
		}
		Set<EObject> incomingNeighbours = new HashSet<EObject>();
		for(Setting setting : adapter.getInverseReferences(eObject, true)) {
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
	private Collection<EObject> getReferenceTargets(EObject eObject, EReference eReference) {
		Object target = eObject.eGet(eReference);
		if(target == null) {
			return Collections.emptySet();
		} else if(eReference.isMany()) {
			return (Collection<EObject>)target;
		} else {
			return Collections.singleton((EObject)target);
		}
	}

	private boolean checkSlicingCondition(EObject element)
	{
		return checkSlicingCondition(element, element.eClass());
	}

	/**
	 * checks the slicing condition for an element
	 * @param element
	 * @param type
	 * @return
	 */
	private boolean checkSlicingCondition(EObject element, EClass eClass) {
		if(slicedEClasses.containsKey(eClass)) {
			Constraint constraint = slicedEClasses.get(eClass).getConstraint();
			return constraint == null || evaluateConstraint(constraint, element).getBoolean();
		}

		// check the slicing condition for the super types recursively
		for(EClass superEClass : eClass.getESuperTypes()) {
			if(checkSlicingCondition(element, superEClass)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param element
	 * @param eReference
	 * @return
	 */
	private Collection<EObject> computeSlicedReferenceTargets(EObject element, EReference eReference) {
		List<SlicedEReference> references = slicedEReferences.get(eReference);
		if(references == null) {
			return Collections.emptyList();
		}

		List<EClass> superTypes = new LinkedList<EClass>();
		superTypes.add(element.eClass());
		superTypes.addAll(element.eClass().getEAllSuperTypes());

		Collection<EObject> validReferences = new ArrayList<EObject>();
		for(SlicedEReference slicedEReference : references) {
			if(superTypes.contains(slicedEReference.getSlicedEClass().getType())) {
				Constraint constraint = slicedEReference.getConstraint();
				if(constraint != null) {
					validReferences.addAll(evaluateConstraint(constraint, element).getEObjects());
				} else {
					validReferences.addAll(getReferenceTargets(element, eReference));
				}
			}
		}
		return validReferences;
	}

	/**
	 * Evaluates the constraint for the element using the constraint interpreter of the slicing configuration.
	 * @param constraint the constraint
	 * @param element the context element
	 * @return result of the constraint evaluation (boolean and object)
	 */
	private IConstraintResult evaluateConstraint(Constraint constraint, EObject element)
	{
		return slicingConfiguration.getConstraintInterpreter().evaluate(constraint, element);
	}
}
