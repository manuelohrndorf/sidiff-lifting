package org.sidiff.difference.technical;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.sidiff.common.emf.EMFResourceUtil;
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.EObjectLocation;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.matching.model.Correspondence;

/**
 * Abstract base class for deriving a low-level difference (which is called
 * technical difference here for some historical reasons) from a given matching.
 * 
 * Subclasses can add unconsideredNodeTypes, unconsideredEdgeTypes and
 * unconsideredAttributeTypes in order to filter the low-level difference.
 * 
 * @author kehrer
 */
public abstract class AbstractTechnicalDifferenceBuilder implements ITechnicalDifferenceBuilder {

	protected SymmetricDifference difference;
	protected Scope scope;

	private final SymmetricFactory factory = SymmetricFactory.eINSTANCE;

	// information provided by subclasses
	private Set<EClass> unconsideredNodeTypes;
	private Set<EReference> unconsideredEdgeTypes;
	private Set<EAttribute> unconsideredAttributeTypes;

	protected AbstractTechnicalDifferenceBuilder() {
		unconsideredNodeTypes = getUnconsideredNodeTypes();
		unconsideredEdgeTypes = getUnconsideredEdgeTypes();
		unconsideredAttributeTypes = getUnconsideredAttributeTypes();
	}

	@Override
	public SymmetricDifference deriveTechDiff(SymmetricDifference difference, Scope scope) {
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "----------------- DERIVE TECHNICAL DIFFERENCE --------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "Difference builder: " + getClass().getSimpleName());
		LogUtil.log(LogEvent.NOTICE, "Scope: " + scope);

		this.difference = difference;
		this.scope = scope;

		processUnmatchedA();
		processUnmatchedB();
		processCorrespondences();

		return difference;
	}

	private void processCorrespondences() {
		for (Iterator<Correspondence> iterator = difference.getMatching().getCorrespondences().iterator(); iterator.hasNext();) {
			Correspondence c = iterator.next();
			EObject nodeA = c.getMatchedA();
			EObject nodeB = c.getMatchedB();
			EClass nodeType = nodeA.eClass();
			assert (nodeB.eClass() == nodeType) : "Corresponding objects have different types..!?";

			// filter

			if (!doProcess(nodeA)) {
				LogUtil.log(LogEvent.DEBUG, "Skip correspondence (does not match docType): " + nodeA + " <-> " + nodeB);
				continue;
			}

			if (unconsideredNodeTypes.contains(nodeType)) {
				LogUtil.log(LogEvent.DEBUG, "Skip correspondence (unconsideredNodeType): " + nodeA + " <-> " + nodeB);
				// skip and also delete correspondence
				iterator.remove();
				continue;
			}

			EObjectLocation objectALocation = EMFResourceUtil.locate(difference.getModelA(), nodeA);
			EObjectLocation objectBLocation = EMFResourceUtil.locate(difference.getModelB(), nodeB);
			assert (objectALocation == objectBLocation) : "different object locations not yet supported!";

			if (objectALocation == EObjectLocation.PACKAGE_REGISTRY) {
				LogUtil.log(LogEvent.DEBUG, "Skip correspondence (PACKAGE_REGISTRY): " + nodeA + " <-> " + nodeB);
				// skip, but do no delete correspondence
				continue;
			}

			if (scope == Scope.RESOURCE && objectALocation == EObjectLocation.RESOURCE_SET_INTERNAL) {
				LogUtil.log(LogEvent.DEBUG, "Skip correspondence (RESOURCE_SET): " + nodeA + " <-> " + nodeB);
				// skip, but do not delete correspondence
				continue;
			}

			// end filter

			// EReferences
			for (EReference edgeType : nodeType.getEAllReferences()) {
				Set<EObject> nodesetA = getReferencedObjects(edgeType, nodeA);
				Set<EObject> nodesetB = getReferencedObjects(edgeType, nodeB);
				
				// Diff A
				Set<EObject> diffA = differenceA(nodesetA, nodesetB);
				deriveDiffA(edgeType, nodeA, diffA);

				// Diff B
				Set<EObject> diffB = differenceB(nodesetA, nodesetB);
				deriveDiffB(edgeType, nodeB, diffB);
			}

			// EAttributes
			for (EAttribute attributeType : nodeType.getEAllAttributes()) {
				if (!attributeType.isDerived()) {
					deriveAttributeValueChange(nodeA, nodeB, attributeType);
				}
			}
		}
	}

	private void processUnmatchedA() {

		for(EObject elementA : difference.getMatching().getUnmatchedA()){
			if (!doProcess(elementA)) {
				LogUtil.log(LogEvent.DEBUG, "Skip node (does not match docType): " + elementA);
				continue;
			}
			if (unconsideredNodeTypes.contains(elementA.eClass())) {
				LogUtil.log(LogEvent.DEBUG, "Skip node (unconsideredNodeType): " + elementA);
				continue;
			}

			// Special in A
			LogUtil.log(LogEvent.DEBUG, "removeNode: " + getObjectName(elementA));
			removeNode(elementA);

			// Outgoing edges
			EClass nodeType = elementA.eClass();
			for (EReference edgeType : nodeType.getEAllReferences()) {
				Set<EObject> diffA = getReferencedObjects(edgeType, elementA);
				deriveDiffA(edgeType, elementA, diffA);
			}
		}
	}

	private void processUnmatchedB() {

		for(EObject elementB : difference.getMatching().getUnmatchedB()){
			if (!doProcess(elementB)) {
				LogUtil.log(LogEvent.DEBUG, "Skip node (does not match docType): " + elementB);
				continue;
			}
			if (unconsideredNodeTypes.contains(elementB.eClass())) {
				LogUtil.log(LogEvent.DEBUG, "Skip node (unconsideredNodeType): " + elementB);
				continue;
			}

			// Special in B
			LogUtil.log(LogEvent.DEBUG, "addNode: " + getObjectName(elementB));
			addNode(elementB);

			// Outgoing edges
			EClass nodeType = elementB.eClass();
			for (EReference edgeType : nodeType.getEAllReferences()) {
				Set<EObject> diffB = getReferencedObjects(edgeType, elementB);
				deriveDiffB(edgeType, elementB, diffB);
			}
		}
	}

	private void deriveDiffA(EReference edgeType, EObject nodeA, Set<EObject> diffA) {
		if (edgeType.isDerived()) {
			return;
		}
		if (unconsideredEdgeTypes.contains(edgeType)) {
			return;
		}

		for (EObject tgt : diffA) {
			LogUtil.log(LogEvent.DEBUG, "delEdge: src: " + getObjectName(nodeA) + " target: " + getObjectName(tgt)
			+ " type: " + edgeType.getName());
			removeEdge(nodeA, tgt, edgeType);
		}
	}

	private void deriveDiffB(EReference edgeType, EObject nodeB, Set<EObject> diffB) {
		if (edgeType.isDerived()) {
			return;
		}
		if (unconsideredEdgeTypes.contains(edgeType)) {
			return;
		}

		for (EObject tgt : diffB) {
			LogUtil.log(LogEvent.DEBUG, "addEdge: src: " + getObjectName(nodeB) + " target: " + getObjectName(tgt)
			+ " type: " + edgeType.getName());
			addEdge(nodeB, tgt, edgeType);
		}
	}

	private Set<EObject> differenceA(Set<EObject> objectsInA, Set<EObject> objectsInB) {
		Set<EObject> difference = new HashSet<EObject>();
		
		for (EObject objectInA : objectsInA) {
			if (!hasCorrespondingObjectInB(objectInA, objectsInB)) {
				difference.add(objectInA);
			}
		}

		return difference;
	}
	
	/**
	 * Checks if the given object has at least one corresponding object in the
	 * set of given objects.
	 * 
	 * @param objectInA
	 *            The object to test.
	 * @param objectsInB
	 *            The set of objects to test against.
	 * @return <code>true</code> if the object has corresponding object in the
	 *         set; <code>false</code> otherwise.
	 */
	protected boolean hasCorrespondingObjectInB(EObject objectInA, Set<EObject> objectsInB) {
		EObject objectInB = difference.getCorrespondingObjectInB(objectInA);
		
		// 1. Check if models A and B point to the same object
		//    => interpret this as correspondence
		// 2. Check if model B has a correspondence.
		// 3. Check if the corresponding object in model A is contained in the given set.
		return objectsInB.contains(objectInA) || ((objectInB != null) && objectsInB.contains(objectInB));
	}

	private Set<EObject> differenceB(Set<EObject> objectsInA, Set<EObject> objectsInB) {
		Set<EObject> difference = new HashSet<EObject>();
		
		for (EObject objectInB : objectsInB) {
			if (!hasCorrespondingObjectInA(objectInB, objectsInA)) {
				difference.add(objectInB);
			}
		}

		return difference;
	}
	
	/**
	 * Checks if the given object has at least one corresponding object in the
	 * set of given objects.
	 * 
	 * @param objectInB
	 *            The object to test.
	 * @param objectsInA
	 *            The set of objects to test against.
	 * @return <code>true</code> if the object has corresponding object in the
	 *         set; <code>false</code> otherwise.
	 */
	protected boolean hasCorrespondingObjectInA(EObject objectInB, Set<EObject> objectsInA) {
		EObject objectInA = difference.getCorrespondingObjectInA(objectInB);
		
		// 1. Check if models A and B point to the same object
		//    => interpret this as correspondence
		// 2. Check if model B has a correspondence.
		// 3. Check if the corresponding object in model A is contained in the given set.
		return objectsInA.contains(objectInB) || ((objectInA != null) && objectsInA.contains(objectInA));
	}

	private void deriveAttributeValueChange(EObject nodeA, EObject nodeB, EAttribute attributeType) {
		if (unconsideredAttributeTypes.contains(attributeType)) {
			return;
		}

		Object valueA = nodeA.eGet(attributeType);
		Object valueB = nodeB.eGet(attributeType);

		// Normalize null values of String attributes: We consider null to be
		// conceptually equal to ""
		if (attributeType.getEAttributeType().getName().equals("EString")) {
			if (valueA == null) {
				valueA = "";
			}
			if (valueB == null) {
				valueB = "";
			}
		}

		// TODO: This section needs to be revised if we want to support
		// multi-valued attributes!

		// Check for multi value attribute
		// Change comparison style if so
		if (valueA instanceof EDataTypeEList) {
			EDataTypeEList<?> valueAList = (EDataTypeEList<?>) valueA;
			assert (valueAList.size() <= 1) : "'Real' multi-valued attributes not yet supported: " + valueAList;

			String compareString = "";
			if (valueAList.size() > 0) {
				compareString = valueAList.toString();
				compareString = compareString.replace("[", "");
				compareString = compareString.replace("]", "");
			}

			valueA = compareString;
		}

		if (valueB instanceof EDataTypeEList) {
			EDataTypeEList<?> valueBList = (EDataTypeEList<?>) valueB;
			assert (valueBList.size() <= 1) : "'Real' multi-valued attributes not yet supported: " + valueBList;

			String compareString = "";
			if (valueBList.size() > 0) {
				compareString = valueBList.toString();
				compareString = compareString.replace("[", "");
				compareString = compareString.replace("]", "");
			}

			valueB = compareString;
		}

		if (EMFMetaAccess.isUnconsideredStructualFeature(attributeType)
				|| (valueA != null && valueB != null && valueA.equals(valueB)) || (valueA == null && valueB == null)) {

			// no value change
			return;
		}

		AttributeValueChange change = factory.createAttributeValueChange();
		change.setObjA(nodeA);
		change.setObjB(nodeB);
		change.setType(attributeType);
		difference.getChanges().add(change);

	}

	private void removeNode(EObject obj) {
		RemoveObject r = factory.createRemoveObject();
		r.setObj(obj);
		difference.getChanges().add(r);
	}

	private void addNode(EObject obj) {
		AddObject a = factory.createAddObject();
		a.setObj(obj);
		difference.getChanges().add(a);
	}

	private void removeEdge(EObject src, EObject tgt, EReference type) {
		RemoveReference c = factory.createRemoveReference();
		c.setSrc(src);
		c.setTgt(tgt);
		c.setType(type);
		difference.getChanges().add(c);
	}

	private void addEdge(EObject src, EObject tgt, EReference type) {
		AddReference c = factory.createAddReference();
		c.setSrc(src);
		c.setTgt(tgt);
		c.setType(type);
		difference.getChanges().add(c);
	}

	@SuppressWarnings("unchecked")
	private Set<EObject> getReferencedObjects(EReference refType, EObject src) {
		if (refType.isMany()) {
			return new HashSet<EObject>((Collection<EObject>) src.eGet(refType));
		} else {
			EObject obj = (EObject) src.eGet(refType);
			if (obj != null) {
				return Collections.singleton(obj);
			}
		}
		return Collections.emptySet();
	}

	@Override
	public String getKey() {
		return getClass().getName();
	}

	private boolean doProcess(EObject object) {
		// generic td builders can process every eObject
		if (getDocumentType().equals(EMFModelAccess.GENERIC_DOCUMENT_TYPE)){
			return true;
		}

		return EMFModelAccess.getDocumentType(object).equals(getDocumentType());
	}

	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		// generic td builders can handle every model
		if (getDocumentType().equals(EMFModelAccess.GENERIC_DOCUMENT_TYPE)){
			return true;
		}

		Set<String> docTypesA = EMFModelAccess.getDocumentTypes(modelA, Scope.RESOURCE_SET);
		Set<String> docTypesB = EMFModelAccess.getDocumentTypes(modelB, Scope.RESOURCE_SET);

		return docTypesA.contains(getDocumentType()) && docTypesB.contains(getDocumentType());
	}

	@Override
	public boolean canHandle(String docType){
		if(getDocumentType().equals(EMFModelAccess.GENERIC_DOCUMENT_TYPE) || docType.equals(getDocumentType()))
			return true;
		else
			return false;
	}

	protected abstract String getObjectName(EObject obj);

	/**
	 * Subclasses have to decide which nodes (i.e. EObjects) shall be ignored by
	 * the difference builder. This is done by specifying the types of the nodes
	 * that are to be ignored.
	 * 
	 * @return
	 */
	protected abstract Set<EClass> getUnconsideredNodeTypes();

	/**
	 * Subclasses have to decide which edges (i.e. Links) shall be ignored by
	 * the difference builder. This is done by specifying the types of the edges
	 * that are to be ignored.
	 * 
	 * @return
	 */
	protected abstract Set<EReference> getUnconsideredEdgeTypes();

	/**
	 * Subclasses have to decide which attributes shall be ignored by the
	 * difference builder. This is done by specifying the types of the
	 * attributes that are to be ignored.
	 * 
	 * @return
	 */
	protected abstract Set<EAttribute> getUnconsideredAttributeTypes();

}
