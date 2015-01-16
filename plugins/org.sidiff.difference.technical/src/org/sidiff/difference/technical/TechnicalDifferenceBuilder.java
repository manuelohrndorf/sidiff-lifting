package org.sidiff.difference.technical;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.silift.common.util.access.EMFMetaAccessEx;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFResourceUtil;
import org.silift.common.util.emf.EObjectLocation;
import org.silift.common.util.emf.Scope;

/**
 * Abstract base class for deriving a low-level difference (which is called
 * technical difference here for some historical reasons) from a given matching.
 * 
 * Subclasses can add unconsideredNodeTypes, unconsideredEdgeTypes and
 * unconsideredAttributeTypes in order to filter the low-level difference.
 * 
 * @author kehrer
 */
public abstract class TechnicalDifferenceBuilder implements ITechnicalDifferenceBuilder {

	private Resource modelA;
	private Resource modelB;
	private SymmetricDifference diff;
	private Scope scope;

	private final SymmetricFactory factory = SymmetricFactory.eINSTANCE;

	// information provided by subclasses
	private Set<EClass> unconsideredNodeTypes;
	private Set<EReference> unconsideredEdgeTypes;
	private Set<EAttribute> unconsideredAttributeTypes;

	protected TechnicalDifferenceBuilder() {
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

		this.modelA = difference.getModelA();
		this.modelB = difference.getModelB();
		this.diff = difference;
		this.scope = scope;

		processUnmatchedA();
		processUnmatchedB();
		processCorrespondences();

		sortLowLevelChanges();

		return diff;
	}

	private void processCorrespondences() {
		for (Iterator<Correspondence> iterator = diff.getCorrespondences().iterator(); iterator.hasNext();) {
			Correspondence c = iterator.next();
			EObject nodeA = c.getObjA();
			EObject nodeB = c.getObjB();
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

			EObjectLocation objectALocation = EMFResourceUtil.locate(diff.getModelA(), nodeA);
			EObjectLocation objectBLocation = EMFResourceUtil.locate(diff.getModelB(), nodeB);
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
				Set<EObject> nodesetA = new HashSet<EObject>();
				addReferencedObjects(edgeType, nodeA, nodesetA);
				Set<EObject> nodesetB = new HashSet<EObject>();
				addReferencedObjects(edgeType, nodeB, nodesetB);

				// Diff A
				@SuppressWarnings("unchecked")
				Set<EObject> diffA = differenceA(nodesetA, nodesetB);
				deriveDiffA(edgeType, nodeA, diffA);

				// Diff B
				@SuppressWarnings("unchecked")
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
		TreeIterator<?> iterA = null;
		if (scope == Scope.RESOURCE) {
			iterA = modelA.getAllContents();
		}
		if (scope == Scope.RESOURCE_SET) {
			iterA = modelA.getResourceSet().getAllContents();
		}

		while (iterA.hasNext()) {
			Object next = iterA.next();
			if (next instanceof EObject) {
				EObject elementA = (EObject) next;
				if (!isCorresponding(elementA)) {
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
						Set<EObject> diffA = new HashSet<EObject>();
						addReferencedObjects(edgeType, elementA, diffA);
						deriveDiffA(edgeType, elementA, diffA);
					}
				}
			}
		}
	}

	private void processUnmatchedB() {
		TreeIterator<?> iterB = null;
		if (scope == Scope.RESOURCE) {
			iterB = modelB.getAllContents();
		}
		if (scope == Scope.RESOURCE_SET) {
			iterB = modelB.getResourceSet().getAllContents();
		}

		while (iterB.hasNext()) {
			Object next = iterB.next();
			if (next instanceof EObject) {
				EObject elementB = (EObject) next;
				if (!isCorresponding(elementB)) {
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
						Set<EObject> diffB = new HashSet<EObject>();
						addReferencedObjects(edgeType, elementB, diffB);
						deriveDiffB(edgeType, elementB, diffB);
					}
				}
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

	@SuppressWarnings("rawtypes")
	private Set differenceA(Set a, Set b) {
		@SuppressWarnings("unchecked")
		HashSet res = new HashSet(a);
		for (Iterator iterator = a.iterator(); iterator.hasNext();) {
			EObject ea = (EObject) iterator.next();

			if (b.contains(diff.getCorrespondingObjectInB(ea))) {
				res.remove(ea);
			}
		}

		return res;
	}

	@SuppressWarnings("rawtypes")
	private Set differenceB(Set a, Set b) {
		@SuppressWarnings("unchecked")
		HashSet res = new HashSet(b);
		for (Iterator iterator = b.iterator(); iterator.hasNext();) {
			EObject eb = (EObject) iterator.next();

			if (a.contains(diff.getCorrespondingObjectInA(eb))) {
				res.remove(eb);
			}
		}

		return res;
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

		if (EMFMetaAccessEx.isUnconsideredStructualFeature(attributeType)
				|| (valueA != null && valueB != null && valueA.equals(valueB)) || (valueA == null && valueB == null)) {

			// no value change
			return;
		}

		AttributeValueChange change = factory.createAttributeValueChange();
		change.setObjA(nodeA);
		change.setObjB(nodeB);
		change.setType(attributeType);
		diff.getChanges().add(change);

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

	@SuppressWarnings("unchecked")
	private void addReferencedObjects(EReference refType, EObject src, Collection<EObject> referencedObjects) {
		if (refType.isMany()) {
			referencedObjects.addAll((Collection<EObject>) src.eGet(refType));
		} else {
			EObject obj = (EObject) src.eGet(refType);
			if (obj != null) {
				referencedObjects.add(obj);
			}
		}
	}

	/**
	 * just sorts the low-level changes for better readability in the difference
	 * viewer. Of course, the order of the low-level changes has no semantics. <br/>
	 * Note: In order to prevent the IllegalArgumentException
	 * "The 'no duplicates' constraint is violated" on the changes EList, we do
	 * sort on a copy of diff.getChanges().
	 */
	private void sortLowLevelChanges() {
		List<Change> changes = new ArrayList<Change>(diff.getChanges());

		Collections.sort(changes, new Comparator<Change>() {
			@Override
			public boolean equals(Object obj) {
				return super.equals(obj);
			}

			public int compare(Change c1, Change c2) {
				return changeToString(c1).compareTo(changeToString(c2));
			}

			private String changeToString(Change c) {
				String res = c.eClass().getName();
				if (c instanceof AddReference) {
					res += ((AddReference) c).getType().getName();
				}
				if (c instanceof RemoveReference) {
					res += ((RemoveReference) c).getType().getName();
				}
				if (c instanceof AddObject) {
					res += ((AddObject) c).getObj().eClass().getName();
				}
				if (c instanceof RemoveObject) {
					res += ((RemoveObject) c).getObj().eClass().getName();
				}
				if (c instanceof AttributeValueChange) {
					res += ((AttributeValueChange) c).getType().getName();
				}
				return res;
			}
		});

		diff.getChanges().clear();
		diff.getChanges().addAll(changes);
	}

	@Override
	public String getName() {
		return getClass().getName();
	}

	private boolean isCorresponding(EObject object) {
		return diff.getCorrespondingObjectInA(object) != null || diff.getCorrespondingObjectInB(object) != null;
	}

	private boolean doProcess(EObject object) {
		// generic td builders can process every eObject
		if (getDocumentType().equals(EMFModelAccessEx.GENERIC_DOCUMENT_TYPE)){
			return true;
		}
		
		return EMFModelAccessEx.getDocumentType(object).equals(getDocumentType());
	}

	@Override
	public boolean canHandle(Resource modelA, Resource modelB) {
		// generic td builders can handle every model
		if (getDocumentType().equals(EMFModelAccessEx.GENERIC_DOCUMENT_TYPE)){
			return true;
		}
		
		Set<String> docTypesA = EMFModelAccessEx.getDocumentTypes(modelA, Scope.RESOURCE_SET);
		Set<String> docTypesB = EMFModelAccessEx.getDocumentTypes(modelB, Scope.RESOURCE_SET);

		return docTypesA.contains(getDocumentType()) && docTypesB.contains(getDocumentType());
	}
	
	@Override
	public boolean canHandle(String docType){
		if(docType.equals(getDocumentType()))
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
