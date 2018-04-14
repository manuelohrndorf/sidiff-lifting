package org.sidiff.slicer.rulebased.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.difference.asymmetric.AttributeDependency;
import org.sidiff.difference.asymmetric.Dependency;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.EdgeDependency;
import org.sidiff.difference.asymmetric.NodeDependency;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ParameterMapping;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.entities.Reference;
import org.sidiff.entities.util.ImportFailedException;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.slice.SlicedElement;
import org.sidiff.slicer.slice.util.SliceImporter;

public class ModelSliceOperationCopier {
		
	/**
	 * The {@link SliceImporter} relinking referenced model elements onto {@link SlicedElement}s
	 */
	private SliceImporter sliceImporter;
	
	/**
	 * Holds the copy for each {@link OperationInvocation}
	 */
	protected Map<OperationInvocation, OperationInvocation> opInvCopies;
	
	/**
	 * Holds the copy for each {@link DependencyContainer}
	 */
	protected Map<DependencyContainer, DependencyContainer> depConCopies;
	
	/**
	 * Holds the copy for each {@link ParameterMapping}
	 */
	protected Map<ParameterMapping, ParameterMapping> pmCopies;
	
	/**
	 * Holds the copy for each {@link SemanticChangeSet}
	 */
	protected Map<SemanticChangeSet, SemanticChangeSet> scsCopies;
	
	/**
	 * Holds the copy for each {@link Change}
	 */
	protected Map<Change, Change> changeCopies;
	
	/**
	 * Holds the copy for each {@link Correspondence}
	 */
	protected Map<Correspondence, Correspondence> correspondenceCopies;
	
	/**
	 * Holds the copy for each {@link ObjectParameterBinding}
	 */
	protected Map<ObjectParameterBinding, ObjectParameterBinding> opbCopies;
	
	/**
	 * Holds the {@link SlicedElement} for each element
	 */
	protected Map<EObject, SlicedElement> objCopies;
	
	
	/**
	 * A list of {@link OperationInvocation} which are ignored when re-linking the respective {@link ParameterMapping}s
	 * @see #relinkObjectParameterBindings(ObjectParameterBinding)
	 */
	protected Set<OperationInvocation> ignoredOpInvs;
	
	
	public ModelSliceOperationCopier(){
		
		this.sliceImporter = new SliceImporter();
		this.opInvCopies = new HashMap<OperationInvocation, OperationInvocation>();
		this.depConCopies = new HashMap<DependencyContainer, DependencyContainer>();
		this.pmCopies = new HashMap<ParameterMapping, ParameterMapping>();
		this.scsCopies = new HashMap<SemanticChangeSet, SemanticChangeSet>();
		this.changeCopies = new HashMap<Change,Change>();
		this.correspondenceCopies = new HashMap<Correspondence, Correspondence>();
		
		this.opbCopies = new HashMap<ObjectParameterBinding, ObjectParameterBinding>();
		
		this.objCopies = new HashMap<EObject, SlicedElement>();
		
		this.ignoredOpInvs = new HashSet<OperationInvocation>();
		
//		this.ermElements = new LinkedList<EObject>();
	}
	
	/**
	 * copies an {@link OperationInvocation} and its {@link SemanticChangeSet} and re-links its {@link ParameterBinding}s
	 * @param opInv
	 * @return
	 */
	public OperationInvocation copyOperationInvocation(OperationInvocation opInv){
		if(!opInvCopies.containsKey(opInv) && !ignoredOpInvs.contains(opInv)){
			
			// copy the current OperationInvocation
			opInvCopies.put(opInv, EcoreUtil.copy(opInv));
			
			// hold a map of all ObjectParameterBindings
			for(ParameterBinding pb : opInv.getParameterBindings()){
				if(pb instanceof ObjectParameterBinding){
					for(ParameterBinding pbCopy : opInvCopies.get(opInv).getParameterBindings()){
						if(pbCopy instanceof ObjectParameterBinding){
							if(pb.getFormalName().equals(pbCopy.getFormalName())){
								opbCopies.put((ObjectParameterBinding)pb, (ObjectParameterBinding)pbCopy);
							}
						}
					}
				}
			}
			
			// re-link the copied ParameterBindings
			for(ParameterBinding binding : opInv.getParameterBindings()){
				if(binding instanceof ObjectParameterBinding){
					ObjectParameterBinding opb = (ObjectParameterBinding)binding;
					
					// copy the incoming ParameterMapping
					if(opb.getIncoming() != null){
						OperationInvocation opInvTgt = (OperationInvocation) opb.getIncoming().getSource().eContainer();
						if(!ignoredOpInvs.contains(opInvTgt)){
							opbCopies.get(opb).setIncoming(copyParameterMapping(opb.getIncoming()));
						}else{
							// if the target of a parameter mapping is contained by an operation invocation contained in the ignore list, then
							// the actualA object has to be set manually.
							opbCopies.get(opb).setActualA(opb.getActualB());
							// Hold the object in order to create respective edit rule matches later
//							ermElements.add(opb.getActualB());
						}
					}
					relinkObjectParameterBinding(opbCopies.get(opb));
				}
			}
			
			// copy all outgoing DependencyContainer
			for(DependencyContainer depContainer: opInv.getOutgoing()){
				copyDependencyContainer(depContainer);
			}
			
			//copy and set the SemanticChangeSet
			opInvCopies.get(opInv).setChangeSet(copySemanticChangeSet(opInv.getChangeSet()));
		}		
		
		return opInvCopies.get(opInv);
	}
	
	/**
	 * copies the {@link ParameterMapping}
	 * @param opInvSrc
	 * @param opInvTgt
	 * @return
	 */
	protected ParameterMapping copyParameterMapping(ParameterMapping pm){
		pmCopies.put(pm, EcoreUtil.copy(pm));
		// copy the respective OperationInvocation if it doesn't already exist
		if(!opbCopies.containsKey(pm.getSource())){
			copyOperationInvocation((OperationInvocation) pm.getSource().eContainer());
		}
		
		pmCopies.get(pm).setSource(opbCopies.get(pm.getSource()));
		
		return pmCopies.get(pm);
	}
	
	/**
	 * copies the {@link DependencyContainer} with opInvSrc as {@link DependencyContainer#getSource()} 
	 * and opInvTgt as {@link DependencyContainer#getTarget()}.
	 * @param opInvSrc
	 * @param opInvTgt
	 * @return
	 */
	protected DependencyContainer copyDependencyContainer(DependencyContainer depContainer){
		OperationInvocation src = opInvCopies.get(depContainer.getSource());
		OperationInvocation tgt = opInvCopies.get(depContainer.getTarget());
		if(tgt == null){
			tgt = copyOperationInvocation(depContainer.getTarget());
		}
		depConCopies.put(depContainer, EcoreUtil.copy(depContainer));
		depConCopies.get(depContainer).setSource(src);
		depConCopies.get(depContainer).setTarget(tgt);
		for (Dependency dependency : depConCopies.get(depContainer).getDependencies()) {
			dependency.setSource(opInvCopies.get(depContainer.getSource()));
			dependency.setTarget(opInvCopies.get(depContainer.getTarget()));
			relinkDependency(dependency);
		}
		return depConCopies.get(depContainer);
	}
	
	private void relinkDependency(Dependency dependency) {
		if(dependency instanceof NodeDependency) {
			NodeDependency nodeDependency = (NodeDependency) dependency;
			try {
				SlicedElement element = sliceImporter.importEObject(nodeDependency.getObject());
				objCopies.put(nodeDependency.getObject(), element);
				nodeDependency.setObject(element);
			} catch (ImportFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(dependency instanceof EdgeDependency) {
			EdgeDependency edgeDependency = (EdgeDependency) dependency;
			try {
				SlicedElement src_element = sliceImporter.importEObject(edgeDependency.getSrcObject());
				SlicedElement tgt_element = sliceImporter.importEObject(edgeDependency.getTgtObject());
				objCopies.put(edgeDependency.getSrcObject(), src_element);
				objCopies.put(edgeDependency.getTgtObject(), tgt_element);
				edgeDependency.setSrcObject(src_element);
				edgeDependency.setTgtObject(tgt_element);
			} catch (ImportFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}else if(dependency instanceof AttributeDependency) {
			AttributeDependency attributeDependency = (AttributeDependency) dependency;
			
			try {
				SlicedElement element = sliceImporter.importEObject(attributeDependency.getObject());
				objCopies.put(attributeDependency.getObject(), element);
				attributeDependency.setObject(element);
			} catch (ImportFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * copies the {@link SemanticChangeSet} of an {@link OperationInvocation}
	 * @param scs
	 * @return
	 */
	protected SemanticChangeSet copySemanticChangeSet(SemanticChangeSet scs){
		if(!scsCopies.containsKey(scs)){
			scsCopies.put(scs, EcoreUtil.copy(scs));
			scsCopies.get(scs).getChanges().clear();
			for(Change change : scs.getChanges()){
				scsCopies.get(scs).getChanges().add(copyChange(change));
			}
			scsCopies.get(scs).setEditRuleMatch(null);
//			relinkEditRuleMatch(scsCopies.get(scs).getEditRuleMatch());
			relinkPartiallyOverlappings(scsCopies.get(scs));
			relinkOverlayings(scsCopies.get(scs));
			relinkSubsets(scsCopies.get(scs));
		}
		return scsCopies.get(scs);
	}
	
	/**
	 * copies the {@link Change}s of a {@link SemanticChangeSet}
	 * @param change
	 * @return
	 */
	protected Change copyChange(Change change){
		if(!changeCopies.containsKey(change)){
			changeCopies.put(change, EcoreUtil.copy(change));
			if(change instanceof AddObject){
				AddObject addObject = (AddObject)changeCopies.get(change);
				relinkAddObjectChange(addObject);
			}else if(change instanceof RemoveObject){
				RemoveObject removeObject = (RemoveObject)changeCopies.get(change);
				relinkRemoveObjectChange(removeObject);
			}else if (change instanceof AddReference){
				AddReference addReference = (AddReference)changeCopies.get(change);
				relinkAddReferenceChange(addReference);
			}else if(change instanceof RemoveReference){
				RemoveReference removeReference = (RemoveReference)changeCopies.get(change);
				relinkRemoveReferenceChange(removeReference);
			}else if(change instanceof AttributeValueChange){
				AttributeValueChange attrValueChange = (AttributeValueChange)changeCopies.get(change);
				relinkAttributeValueChange(attrValueChange);
			}
		}
		return changeCopies.get(change);
	}
	
	/**
	 * re-link the partially overlapping {@link SemanticChangeSet}s 
	 * @param semanticChangeSet
	 */
	protected void relinkPartiallyOverlappings(SemanticChangeSet semanticChangeSet){
		List<SemanticChangeSet> partiallyOverlappings_copy = new LinkedList<SemanticChangeSet>();
		for(SemanticChangeSet scs : semanticChangeSet.getPartiallyOverlappings()){
			SemanticChangeSet scs_copy;
			if(!scsCopies.containsKey(scs)){
				scs_copy = copySemanticChangeSet(scs);
			}else{
				scs_copy = scsCopies.get(scs);
			}
			partiallyOverlappings_copy.add(scs_copy);
		}
		semanticChangeSet.getPartiallyOverlappings().clear();
		semanticChangeSet.getPartiallyOverlappings().addAll(partiallyOverlappings_copy);
	}
	
	/**
	 * re-link the overlaying {@link SemanticChangeSet}s
	 * @param semanticChangeSet
	 */
	protected void relinkOverlayings(SemanticChangeSet semanticChangeSet){
		List<SemanticChangeSet> overlaying_copy = new LinkedList<SemanticChangeSet>();
		for(SemanticChangeSet scs : semanticChangeSet.getOverlayings()){
			SemanticChangeSet scs_copy;
			if(!scsCopies.containsKey(scs)){
				scs_copy = copySemanticChangeSet(scs);
			}else{
				scs_copy = scsCopies.get(scs);
			}
			overlaying_copy.add(scs_copy);
		}
		semanticChangeSet.getOverlayings().clear();
		semanticChangeSet.getOverlayings().addAll(overlaying_copy);
	}
	
	/**
	 * re-link the subset {@link SemanticChangeSet}s
	 * @param semanticChangeSet
	 */
	protected void relinkSubsets(SemanticChangeSet semanticChangeSet){
		List<SemanticChangeSet> subsets_copy = new LinkedList<SemanticChangeSet>();
		for(SemanticChangeSet scs : semanticChangeSet.getSubsets()){
			SemanticChangeSet scs_copy;
			if(!scsCopies.containsKey(scs)){
				scs_copy = copySemanticChangeSet(scs);
			}else{
				scs_copy = scsCopies.get(scs);
			}
			subsets_copy.add(scs_copy);
		}
		semanticChangeSet.getSubsets().clear();
		semanticChangeSet.getSubsets().addAll(subsets_copy);
	}
	

	public void calculateCorrespondences() {
		SymmetricDifference symDiff = (SymmetricDifference) scsCopies.keySet().iterator().next().eContainer();
		for(Correspondence c : symDiff.getMatching().getCorrespondences()){
			if(objCopies.containsKey(c.getMatchedA()) && objCopies.containsKey(c.getMatchedB())){
				correspondenceCopies.put(c, EcoreUtil.copy(c));
				correspondenceCopies.get(c).setMatchedA(objCopies.get(c.getMatchedA()));
				correspondenceCopies.get(c).setMatchedB(objCopies.get(c.getMatchedB()));
			}
		}
	}

	// ---------- Getters & Setters ----------
	
	public Map<OperationInvocation, OperationInvocation> getOpInvCopies() {
		return opInvCopies;
	}

	public Map<DependencyContainer, DependencyContainer> getDepConCopies() {
		return depConCopies;
	}

	public Map<ParameterMapping, ParameterMapping> getPmCopies() {
		return pmCopies;
	}

	public Map<SemanticChangeSet, SemanticChangeSet> getScsCopies() {
		return scsCopies;
	}

	public Map<Change, Change> getChangeCopies() {
		return changeCopies;
	}

	public Map<Correspondence, Correspondence> getCorrespondenceCopies() {
		return correspondenceCopies;
	}

	public Set<OperationInvocation> getIgnoredOpInvs() {
		return ignoredOpInvs;
	}

	public Map<EObject, SlicedElement> getObjCopies() {
		return objCopies;
	}


	
	public void setIgnoredOpInvs(Set<OperationInvocation> ignoredOpInvs) {
		this.ignoredOpInvs = ignoredOpInvs;
	}
	
	protected void relinkObjectParameterBinding(ObjectParameterBinding objectParameterBinding) {
		if(objectParameterBinding.getActualA() != null) {
			try {
				SlicedElement element = this.sliceImporter.importEObject(objectParameterBinding.getActualA());
				objCopies.put(objectParameterBinding.getActualA(), element);
				objectParameterBinding.setActualA(element);
			} catch (ImportFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(objectParameterBinding.getActualB() != null) {
			try {
				SlicedElement element = this.sliceImporter.importEObject(objectParameterBinding.getActualB());
				objCopies.put(objectParameterBinding.getActualB(), element);
				objectParameterBinding.setActualB(element);
			} catch (ImportFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void relinkAddObjectChange(AddObject addObjectChange) {
		try {
			SlicedElement element = this.sliceImporter.importEObject(addObjectChange.getObj());
			objCopies.put(addObjectChange.getObj(), element);
			addObjectChange.setObj(element);
		} catch (ImportFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void relinkRemoveObjectChange(RemoveObject removeObjectChange) {
		try {
			SlicedElement element = this.sliceImporter.importEObject(removeObjectChange.getObj());
			objCopies.put(removeObjectChange.getObj(), element);
			removeObjectChange.setObj(element);
		} catch (ImportFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void relinkAddReferenceChange(AddReference addReferenceChange) {
		try {
			Reference reference = this.sliceImporter.importEReference(addReferenceChange.getType(), addReferenceChange.getSrc(), addReferenceChange.getTgt());
			objCopies.put(addReferenceChange.getSrc(), (SlicedElement)reference.getSource());
			objCopies.put(addReferenceChange.getTgt(), (SlicedElement)reference.getTarget());
			addReferenceChange.setSrc(reference.getSource());
			addReferenceChange.setTgt(reference.getTarget());
		} catch (ImportFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void relinkRemoveReferenceChange(RemoveReference removeReferenceChange) {
		try {
			Reference reference = this.sliceImporter.importEReference(removeReferenceChange.getType(), removeReferenceChange.getSrc(), removeReferenceChange.getTgt());
			objCopies.put(removeReferenceChange.getSrc(), (SlicedElement)reference.getSource());
			objCopies.put(removeReferenceChange.getTgt(), (SlicedElement)reference.getTarget());
			removeReferenceChange.setSrc(reference.getSource());
			removeReferenceChange.setTgt(reference.getTarget());
		} catch (ImportFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void relinkAttributeValueChange(AttributeValueChange attributeValueChange) {
		try {
			SlicedElement elementA = this.sliceImporter.importEObject(attributeValueChange.getObjA());
			SlicedElement elementB = this.sliceImporter.importEObject(attributeValueChange.getObjB());
			objCopies.put(attributeValueChange.getObjA(), elementA);
			objCopies.put(attributeValueChange.getObjB(), elementB);
			this.sliceImporter.importEAttribute(attributeValueChange.getType(), attributeValueChange.getObjA());
			this.sliceImporter.importEAttribute(attributeValueChange.getType(), attributeValueChange.getObjB());
					
			attributeValueChange.setObjA(elementA);
			attributeValueChange.setObjB(elementB);
		} catch (ImportFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public ModelSlice getModelSlice() {
		return sliceImporter.getModelSlice();
	}


}
