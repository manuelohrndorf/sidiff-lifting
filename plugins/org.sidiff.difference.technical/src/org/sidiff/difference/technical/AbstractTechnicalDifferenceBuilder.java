package org.sidiff.difference.technical;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.emf.EMFResourceUtil;
import org.sidiff.common.emf.EMFUtil;
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
 * Note: We do not consider dynamic nodes, edges and attributes
 * @see {@link EMFUtil#isDynamic(EObject)}
 * @see {@link EMFUtil#isDynamic(EObject, EReference)}
 * @see {@link EMFUtil#isDynamic(EObject, EAttribute)}
 * 
 * @author kehrer, cpietsch
 */
public abstract class AbstractTechnicalDifferenceBuilder implements ITechnicalDifferenceBuilder {

	private SymmetricDifference diff;
	private Scope scope;

	private static final SymmetricFactory factory = SymmetricFactory.eINSTANCE;

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

		this.diff = difference;
		this.scope = scope;

		processUnmatchedA();
		processUnmatchedB();
		processCorrespondences();

		return diff;
	}

	private void processCorrespondences() {
		for (Iterator<Correspondence> iterator = diff.getMatching().getCorrespondences().iterator(); iterator.hasNext();) {
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

			if(EMFUtil.isDynamic(nodeA)) {
				assert EMFUtil.isDynamic(nodeB): "Corresponence between an dynamic and non-dynamic element..!?";
				LogUtil.log(LogEvent.DEBUG, "Skip correspondence (dynamic): " + nodeA + " <-> " + nodeB);
				// skip and also delete correspondence
				if(c.getContainerCorrespondence() != null)
					c.getContainerCorrespondence().getContainmentCorrespondences().remove(c);
				iterator.remove();
				continue;
			}
			
			if (unconsideredNodeTypes.contains(nodeType)) {
				LogUtil.log(LogEvent.DEBUG, "Skip correspondence (unconsideredNodeType): " + nodeA + " <-> " + nodeB);
				// skip and also delete correspondence
				if(c.getContainerCorrespondence() != null)
					c.getContainerCorrespondence().getContainmentCorrespondences().remove(c);
				iterator.remove();
				continue;
			}

			EObjectLocation objectALocation = EMFResourceUtil.locate(diff.getModelA(), nodeA);
			EObjectLocation objectBLocation = EMFResourceUtil.locate(diff.getModelB(), nodeB);
			assert (objectALocation == objectBLocation) : "different object locations not yet supported!";

			if (objectALocation == EObjectLocation.PACKAGE_REGISTRY) {
				LogUtil.log(LogEvent.DEBUG, "Skip correspondence (PACKAGE_REGISTRY): " + nodeA + " <-> " + nodeB);
				// skip, but do not delete correspondence
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
				Set<EObject> nodesetA = new HashSet<>();
				addReferencedObjects(edgeType, nodeA, nodesetA);
				Set<EObject> nodesetB = new HashSet<>();
				addReferencedObjects(edgeType, nodeB, nodesetB);

				// Diff A
				Set<EObject> diffA = differenceA(nodesetA, nodesetB);
				deriveDiffA(edgeType, nodeA, diffA);

				// Diff B
				Set<EObject> diffB = differenceB(nodesetA, nodesetB);
				deriveDiffB(edgeType, nodeB, diffB);
			}

			// EAttributes
			for (EAttribute attributeType : nodeType.getEAllAttributes()) {
				deriveAttributeValueChange(nodeA, nodeB, attributeType);
			}
		}
	}

	private void processUnmatchedA() {
		for(Iterator<EObject> iterator = diff.getMatching().getUnmatchedA().iterator(); iterator.hasNext();) {
			EObject elementA = iterator.next();

			if (!doProcess(elementA)) {
				LogUtil.log(LogEvent.DEBUG, "Skip node (does not match docType): " + elementA);
				continue;
			}
			
			if(EMFUtil.isDynamic(elementA)) {
				LogUtil.log(LogEvent.DEBUG, "Skip dynamic node: " + elementA);
				iterator.remove();
				continue;
			}
			
			if (unconsideredNodeTypes.contains(elementA.eClass())) {
				LogUtil.log(LogEvent.DEBUG, "Skip node (unconsideredNodeType): " + elementA);
				iterator.remove();
				continue;
			}

			// Special in A
			LogUtil.log(LogEvent.DEBUG, "removeNode: " + getObjectName(elementA));
			removeNode(elementA);

			// Outgoing edges
			EClass nodeType = elementA.eClass();
			for (EReference edgeType : nodeType.getEAllReferences()) {
				Set<EObject> diffA = new HashSet<EObject>();
				addReferencedObjects(edgeType, elementA, diffA);
				deriveDiffA(edgeType, elementA, diffA);
			}
		}


	}

	private void processUnmatchedB() {

		for(Iterator<EObject> iterator = diff.getMatching().getUnmatchedB().iterator(); iterator.hasNext();) {
			EObject elementB = iterator.next();
			
			if (!doProcess(elementB)) {
				LogUtil.log(LogEvent.DEBUG, "Skip node (does not match docType): " + elementB);
				continue;
			}
			
			if(EMFUtil.isDynamic(elementB)) {
				LogUtil.log(LogEvent.DEBUG, "Skip dynamic node: " + elementB);
				iterator.remove();
				continue;
			}
			
			if (unconsideredNodeTypes.contains(elementB.eClass())) {
				LogUtil.log(LogEvent.DEBUG, "Skip node (unconsideredNodeType): " + elementB);
				iterator.remove();
				continue;
			}

			// Special in B
			LogUtil.log(LogEvent.DEBUG, "addNode: " + getObjectName(elementB));
			addNode(elementB);

			// Outgoing edges
			EClass nodeType = elementB.eClass();
			for (EReference edgeType : nodeType.getEAllReferences()) {
				Set<EObject> diffB = new HashSet<EObject>();
				addReferencedObjects(edgeType, elementB, diffB);
				deriveDiffB(edgeType, elementB, diffB);
			}
		}


	}

	private void deriveDiffA(EReference edgeType, EObject nodeA, Set<EObject> diffA) {
//		if (EMFUtil.isDynamic(nodeA, edgeType)) {
//			LogUtil.log(LogEvent.DEBUG, "Skip dynamic edge type: " + edgeType);
//			return;
//		}
//		if (unconsideredEdgeTypes.contains(edgeType)) {
//			return;
//		}

		for (EObject tgt : diffA) {
			LogUtil.log(LogEvent.DEBUG, "delEdge: src: " + getObjectName(nodeA) + " target: " + getObjectName(tgt)
			+ " type: " + edgeType.getName());
			removeEdge(nodeA, tgt, edgeType);
		}
	}

	private void deriveDiffB(EReference edgeType, EObject nodeB, Set<EObject> diffB) {
//		if (EMFUtil.isDynamic(nodeB, edgeType)) {
//			LogUtil.log(LogEvent.DEBUG, "Skip dynamic edge type: " + edgeType);
//			return;
//		}
//		if (unconsideredEdgeTypes.contains(edgeType)) {
//			LogUtil.log(LogEvent.DEBUG, "Skip unconsidered edge type: " + edgeType);
//			return;
//		}

		for (EObject tgt : diffB) {
			LogUtil.log(LogEvent.DEBUG, "addEdge: src: " + getObjectName(nodeB) + " target: " + getObjectName(tgt)
			+ " type: " + edgeType.getName());
			addEdge(nodeB, tgt, edgeType);
		}
	}

	private Set<EObject> differenceA(Set<EObject> a, Set<EObject> b) {
		Set<EObject> res = new HashSet<>(a);
		for (EObject objA : a) {
			if (b.contains(diff.getCorrespondingObjectInB(objA))) {
				res.remove(objA);
			}
		}
		return res;
	}

	private Set<EObject> differenceB(Set<EObject> a, Set<EObject> b) {
		Set<EObject> res = new HashSet<>(b);
		for (EObject objB : b) {
			if (a.contains(diff.getCorrespondingObjectInA(objB))) {
				res.remove(objB);
			}
		}
		return res;
	}

	private void deriveAttributeValueChange(EObject nodeA, EObject nodeB, EAttribute attributeType) {
		if (EMFMetaAccess.isUnconsideredStructualFeature(attributeType)
				|| unconsideredAttributeTypes.contains(attributeType)) {
			LogUtil.log(LogEvent.DEBUG, "Skip unconsidered attribute type: " + attributeType);
			return;
		}

		List<String> valuesA = EMFUtil.getAttributeValues(nodeA, attributeType).stream()
				.map(value -> EcoreUtil.convertToString(attributeType.getEAttributeType(), value))
				.collect(Collectors.toList());
		List<String> valuesB = EMFUtil.getAttributeValues(nodeB, attributeType).stream()
				.map(value -> EcoreUtil.convertToString(attributeType.getEAttributeType(), value))
				.collect(Collectors.toList());

		if (equalAttributeValues(attributeType, valuesA, valuesB)) {
			// no value change
			return;
		}

		AttributeValueChange change = factory.createAttributeValueChange();
		change.setObjA(nodeA);
		change.setObjB(nodeB);
		change.setType(attributeType);
		diff.getChanges().add(change);
	}

	private static boolean equalAttributeValues(EAttribute attributeType, List<String> valuesA, List<String> valuesB) {
		int size = valuesA.size();
		if(size != valuesB.size()) {
			// Number of values must always match
			return false;
		}
		if(size == 0) {
			// Attribute is unset
			return true;
		}
		if(size == 1) {
			// Single-valued attribute
			return valuesA.get(0).equals(valuesB.get(0));
		}
		// Multi-valued attribute
		if(attributeType.isOrdered()) {
			// The same values must appear in the same order
			return IntStream.range(0, size)
						.allMatch(i -> valuesA.get(i).equals(valuesB.get(i)));
		}
		// Sort list of values to achieve unordered comparison
		List<String> unorderedValuesA = new ArrayList<>(valuesA);
		Collections.sort(unorderedValuesA);
		List<String> unorderedValuesB = new ArrayList<>(valuesB);
		Collections.sort(unorderedValuesB);
		return IntStream.range(0, size)
				.allMatch(i -> unorderedValuesA.get(i).equals(unorderedValuesB.get(i)));
	}

	private void removeNode(EObject obj) {
		RemoveObject r = factory.createRemoveObject();
		r.setObj(obj);
		diff.getChanges().add(r);
	}

	private void addNode(EObject obj) {
		AddObject a = factory.createAddObject();
		a.setObj(obj);
		diff.getChanges().add(a);
	}

	private void removeEdge(EObject src, EObject tgt, EReference type) {
		RemoveReference c = factory.createRemoveReference();
		c.setSrc(src);
		c.setTgt(tgt);
		c.setType(type);
		diff.getChanges().add(c);
	}

	private void addEdge(EObject src, EObject tgt, EReference type) {
		AddReference c = factory.createAddReference();
		c.setSrc(src);
		c.setTgt(tgt);
		c.setType(type);
		diff.getChanges().add(c);
	}

	private void addReferencedObjects(EReference refType, EObject src, Collection<EObject> referencedObjects) {
		if(refType.isDerived()) {
			LogUtil.log(LogEvent.DEBUG, "Skip derived reference: " + src + "." + refType);
			return;
		}
		
		if (unconsideredEdgeTypes.contains(refType)) {
			LogUtil.log(LogEvent.DEBUG, "Skip unconsidered edge type: " + src + "." + refType);
			return;
		}
		
		for(EObject tgt : EMFUtil.getReferenceTargets(src, refType)) {
			if(EMFUtil.isDynamic(tgt)) {
				LogUtil.log(LogEvent.DEBUG, "Skip reference with dynamic target: " + src + "." + refType + "." + tgt);
			} else {
				referencedObjects.add(tgt);
			}
		}
	}

	@Override
	public String getKey() {
		return getClass().getName();
	}

	private boolean doProcess(EObject object) {
		// generic td builders can process every eObject
		return getDocumentTypes().contains(GENERIC_TYPE)
				|| getDocumentTypes().contains(EMFModelAccess.getDocumentType(object));
	}

	@Override
	public boolean canHandle(Set<String> documentTypes) {
		// generic td builders can handle every model
		Set<String> intersection = new HashSet<String>(documentTypes);
		intersection.retainAll(getDocumentTypes());
		return getDocumentTypes().contains(GENERIC_TYPE) || !intersection.isEmpty();
	}
	
	@Override
	public boolean canHandleModels(Resource modelA, Resource modelB) {
		Set<String> docTypes = EMFModelAccess.getDocumentTypes(modelA, Scope.RESOURCE_SET);
		docTypes.addAll(EMFModelAccess.getDocumentTypes(modelB, Scope.RESOURCE_SET));
		return canHandle(docTypes);
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
