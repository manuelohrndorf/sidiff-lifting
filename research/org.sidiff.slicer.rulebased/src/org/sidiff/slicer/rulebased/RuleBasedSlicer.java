package org.sidiff.slicer.rulebased;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil;
import org.sidiff.difference.symmetric.util.SlicingChangeSetPriorityComparator;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.matching.model.Matching;
import org.sidiff.matching.model.MatchingModelFactory;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.arguments.ObjectArgumentWrapper;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.slicer.rulebased.configuration.SlicingConfiguration;
import org.sidiff.slicer.rulebased.exceptions.NotInitializedException;
import org.sidiff.slicer.rulebased.exceptions.UncoveredChangesException;

/**
 * 
 * @author cpietsch
 *
 */
public class RuleBasedSlicer{

	/**
	 * The {@link SlicingConfiguration}
	 */
	private SlicingConfiguration slicingConfiguration;
	
	// ############### inner accessed fields ###############
	
	/**
	 * The {@link Resource} of the complete model
	 */
	private Resource completeResource;
	
	/**
	 * The {@link Resource} of the 'empty' model containing only the root elements
	 */
	private Resource emptyResource;
	
	/**
	 * The {@link Resource} of the sliced model
	 */
	private Resource slicedResource;
	
	/**
	 * A map holding the correspondences between the complete and (incremental modified) sliced model
	 */
	private Map<EObject, EObject> c_complete_slice;
	
	/**
	 * A map holding the correspondences between the sliced and 'empty' model
	 */
	private Map<EObject, EObject> c_slice_empty;
	
	/**
	 * The {@link PatchEngine} for applying the {@link #asymDiff}
	 */
	private PatchEngine patchEngine;
	
	/**
	 * flag that indicates if the slicer is initialized
	 */
	private boolean initialized = false;
	
	/**
	 * elements to be added
	 */
	private Set<EObject> addSlicingCriteria;
	
	/**
	 * elements to be removed
	 */
	private Set<EObject> remSlicingCriteria;
	
	/**
	 * initializes the slicer
	 * @param config
	 * 			the {@link SlicingConfiguration}
	 * @param completeResource
	 * 			the {@link #completeResource}
	 * @param slicedResource
	 * 			the {@link #slicedResource}
	 */
	public void init(SlicingConfiguration config, Resource completeResource, Resource emtpyResource, Resource slicedResource){
		
		this.slicingConfiguration = config;
		
		this.completeResource = completeResource;
		this.emptyResource = emtpyResource;
		this.slicedResource = slicedResource;
		
//		this.generateIDs(completeResource);
		
		this.c_complete_slice = EMFUtil.copySubModel(new HashSet<EObject>(completeResource.getContents()));
		
		this.slicedResource.getContents().addAll(c_complete_slice.values());
		
		for(EObject c_complete : c_complete_slice.keySet()){
			String id = EMFUtil.getXmiId(c_complete);
			EMFUtil.setXmiId(c_complete_slice.get(c_complete), id);
		}
		
		this.c_slice_empty = EMFUtil.copySubModel(new HashSet<EObject>(slicedResource.getContents()));
		
		this.emptyResource.getContents().addAll(c_slice_empty.values());
		
		for(EObject c_slice : c_slice_empty.keySet()){
			String id = EMFUtil.getXmiId(c_slice);
			EMFUtil.setXmiId(c_slice_empty.get(c_slice), id);
		}
		
//		EcoreUtil.resolveAll(originResource);
//		for(Resource resource : originResource.getResourceSet().getResources()){
//			if(resource != originResource){
//				Resource r = EMFStorage.eLoad(resource.getURI()).eResource();
//				this.targetResource.getResourceSet().getResources().add(r);
//			}
//		}
		this.initialized = true;
	}

	/**
	 *  the incremental rule-based slicing algorithm
	 *  1. Delta_G = generateEditScript(e, G)
	 *  2. ES_S = {es in ES_G | cre(es) in S != null}
	 *  3. ES_O = ES_S cup {es in (ES_G \ ES_S) | exists es' in ES_G : es < es'}
	 *  4. Delta_O = (ES_O. DEP_O) with DEP_O = {(es_1, es_2) in DEP_G | es_1 in ES_O and es_2 in ES_O}
	 *  5. O = applyEditScript(Delta_O, e)
	 * @throws UncoveredChangesException 
	 * @throws NotInitializedException 
	 */
	public AsymmetricDifference slice(Set<EObject> addSlicingCriteria, Set<EObject> remSlicingCriteria) throws UncoveredChangesException, NotInitializedException{

		if(initialized){
			LogUtil.log(LogEvent.MESSAGE, "############### Slicer Started ###############");
			
			this.addSlicingCriteria = addSlicingCriteria;
			this.remSlicingCriteria = remSlicingCriteria;
			
			
			this.slicingConfiguration.getLiftingSettings().setComparator(new SlicingChangeSetPriorityComparator(this.addSlicingCriteria));
			
			// calculate the asymmetric difference between the sliced and complete model
			AsymmetricDifference asymDiff_slice_complete = this.generateEditScript(this.slicedResource, this.completeResource, c_complete_slice);
			
			// collect all operation invocations in asymDiff_slice_complete creating the slicing criteria
			Map<EObject, OperationInvocation> opInvsAdd = new HashMap<EObject, OperationInvocation>();
			for (OperationInvocation opInv : asymDiff_slice_complete.getOperationInvocations()) {
				for(Change change : opInv.getChangeSet().getChanges()){
					if(change instanceof AddObject){
						EObject obj = ((AddObject)change).getObj();
						opInvsAdd.put(obj, opInv);
					}
				}
			}
			
			Set<OperationInvocation> opInvOAdd = new HashSet<OperationInvocation>();
			for(EObject in : this.addSlicingCriteria){
				OperationInvocation opInv = opInvsAdd.get(in);
				if(opInv != null){
					opInvOAdd.add(opInv);
					opInvOAdd.addAll(opInv.getAllPredecessors());
				}
			}
			
			// disable all operation invocations in asymDiff_slice_complete not creating slicing criteria or not setting an attribute or references 
			for(OperationInvocation opInv : asymDiff_slice_complete.getOperationInvocations()){
				if(!opInvOAdd.contains(opInv) || !opInvsAdd.containsValue(opInv) && !opInvOAdd.containsAll(opInv.getPredecessors())){
					opInv.setApply(false);
				}
			}
			
			// calculate the asymmetric difference between the sliced and 'empty' model
			AsymmetricDifference asymDiff_slice_empty = this.generateEditScript(this.slicedResource, this.emptyResource, c_slice_empty);
			
			// collect all operation invocations in asymDiff_slice_empty deleting a element not in the slicing criteria
			Map<EObject, OperationInvocation> opInvsRem = new HashMap<EObject, OperationInvocation>();
			for (OperationInvocation opInv : asymDiff_slice_empty.getOperationInvocations()) {
				for(Change change : opInv.getChangeSet().getChanges()){
					if(change instanceof RemoveObject){
						EObject obj = ((RemoveObject)change).getObj();
						for(EObject c_complete : c_complete_slice.keySet()){
							if(c_complete_slice.get(c_complete).equals(obj)){
								opInvsRem.put(c_complete, opInv);
								
							}
						}
						
					}
				}
			}
			
			Set<OperationInvocation> opInvORem = new HashSet<OperationInvocation>();
			for(EObject in : this.remSlicingCriteria){
				OperationInvocation opInv = opInvsRem.get(in);
				if(opInv != null){
					opInvORem.add(opInv);
					opInvORem.addAll(opInv.getAllPredecessors());
				}
			}
			
			// disable all operation invocations in asymDiff_slice_empty removing the slicing criteria 
			for(OperationInvocation opInv : asymDiff_slice_empty.getOperationInvocations()){
				if(!opInvORem.contains(opInv)){
					opInv.setApply(false);
				}
			}
			
			asymDiff_slice_complete.getOperationInvocations().addAll(asymDiff_slice_empty.getOperationInvocations());
			asymDiff_slice_complete.getParameterMappings().addAll(asymDiff_slice_empty.getParameterMappings());
//			AsymmetricDifference asymDiff_merged = AsymmetricFactory.eINSTANCE.createAsymmetricDifference();
//			asymDiff_merged.setUriOriginModel(asymDiff_slice_complete.getUriOriginModel());
//			asymDiff_merged.setUriChangedModel(asymDiff_slice_complete.getUriChangedModel());
//			asymDiff_merged.getOperationInvocations().addAll(asymDiff_slice_empty.getOperationInvocations());
//			asymDiff_merged.getParameterMappings().addAll(asymDiff_slice_empty.getParameterMappings());
//			asymDiff_merged.getOperationInvocations().addAll(asymDiff_slice_complete.getOperationInvocations());
//			asymDiff_merged.getParameterMappings().addAll(asymDiff_slice_complete.getParameterMappings());
			
			// 5
			this.initPatchEngine(asymDiff_slice_complete, c_complete_slice);
			this.applyEditScript(asymDiff_slice_complete);
			LogUtil.log(LogEvent.MESSAGE, "############### Slicer FINISHED ###############");
			
			return  asymDiff_slice_complete;
			
		}else{
			throw new NotInitializedException();
		}

	}
	
	// ############### inner accessed methods ###############
	
	/**
	 * calculates an edit script with {@link #slicedResource} as origin model
	 * and {@link #completeResource} as changed model using the correspondences
	 * in {@link #correspondences}
	 * @param originModel
	 * @param changedModel
	 * @param correspondences
	 * @return an {@link AsymmetricDifference}
	 * @throws UncoveredChangesException 
	 */
	private AsymmetricDifference generateEditScript(Resource originModel, Resource changedModel, Map<EObject, EObject> correspondences) throws UncoveredChangesException{
		
		Matching matching = MatchingModelFactory.eINSTANCE.createMatching();
		matching.setEResourceA(originModel);
		matching.setEResourceB(changedModel);
		matching.setUriA(originModel.getURI().toFileString());
		matching.setUriB(changedModel.getURI().toFileString());
		
		for(EObject eObject : correspondences.keySet()){
			Correspondence correspondence = MatchingModelFactory.eINSTANCE.createCorrespondence();
			if(eObject.eResource().equals(originModel)){
				correspondence.setMatchedA(eObject);
				correspondence.setMatchedB(correspondences.get(eObject));
			}else{
				correspondence.setMatchedA(correspondences.get(eObject));
				correspondence.setMatchedB(eObject);
			}
			matching.getCorrespondences().add(correspondence);
		}
		
		for (Iterator<EObject> iterator = changedModel.getAllContents(); iterator.hasNext();) {
			EObject eObject =  iterator.next();
			if(!correspondences.containsKey(eObject) && !correspondences.containsValue(eObject)){
				matching.getUnmatchedB().add(eObject);
			}
		}
		
		for (Iterator<EObject> iterator = originModel.getAllContents(); iterator.hasNext();) {
			EObject eObject =  iterator.next();
			if(!correspondences.containsValue(eObject) && !correspondences.containsKey(eObject)){
				matching.getUnmatchedA().add(eObject);
			}
		}
		
		SymmetricDifference technicalDifference = AsymmetricDiffFacade.deriveTechnicalDifference(matching, this.slicingConfiguration.getLiftingSettings());
		technicalDifference.setUriModelB(changedModel.getURI().toFileString());
		AsymmetricDifference asymDiff = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(technicalDifference, this.slicingConfiguration.getLiftingSettings()).getAsymmetric();
		asymDiff.setUriOriginModel(originModel.getURI().toString());
		asymDiff.setUriChangedModel(changedModel.getURI().toString());
		if(DifferenceAnalysisUtil.getRemainingChanges(asymDiff.getSymmetricDifference()).size() > 0){
			LiftingFacade.serializeLiftedDifference(asymDiff.getSymmetricDifference(), EMFStorage.uriToPath(slicedResource.getURI()).replace(slicedResource.getURI().lastSegment(),  ""), "diff");
			throw new UncoveredChangesException();
		}
		return asymDiff;
	}
	
	private void initPatchEngine(AsymmetricDifference asymDiff, Map<EObject,EObject> correspondences){
		IArgumentManager argumentManager = new SlicingArgumentManager(correspondences);
		PatchingSettings settings = new PatchingSettings(slicingConfiguration.getLiftingSettings().getScope(), false,
				slicingConfiguration.getLiftingSettings().getMatcher(),
				slicingConfiguration.getLiftingSettings().getCandidatesService(),
				slicingConfiguration.getLiftingSettings().getCorrespondencesService(),
				slicingConfiguration.getLiftingSettings().getTechBuilder(), null,
				argumentManager,
				new BatchInterruptHandler(),
				TransformationEngineUtil.getFirstTransformationEngine(ITransformationEngine.DEFAULT_DOCUMENT_TYPE),
				null, ExecutionMode.BATCH,
				PatchMode.PATCHING, 100, ValidationMode.NO_VALIDATION);
		
		patchEngine = new PatchEngine(asymDiff, slicedResource, settings);
	}
	
	/**
	 * applies an edit script onto a target model and adds new created model
	 * elements to {@link #correspondences}
	 */
	private void applyEditScript(AsymmetricDifference asymDiff){
		patchEngine.applyPatch(true);
		updateCorrespondences(asymDiff);
	}
	
//	/**
//	 * 
//	 * @param slicingCriteria
//	 */
//	private void cleanCorrespondences(Set<EObject> slicingCriteria){
//		Set<EObject> removedSlicingCriteria = new HashSet<EObject>();
//		for(EObject eObject : correspondences.keySet()){
//			if(!slicingCriteria.contains(eObject) && !submodel.contains(eObject)){
//				removedSlicingCriteria.add(eObject);
//			}
//		}
//		for(EObject eObject : removedSlicingCriteria){
//			correspondences.remove(eObject);
//		}
//	}
	
	/**
	 * 
	 */
	private void updateCorrespondences(AsymmetricDifference asymDiff){
		for(OperationInvocation opInv : asymDiff.getOperationInvocations()){
			if(opInv.isApply()){
				for(ParameterBinding pb : opInv.getOutParameterBindings()){
					if(pb instanceof ObjectParameterBinding){
						ObjectParameterBinding opb = (ObjectParameterBinding) pb;
						ObjectArgumentWrapper argumentWrapper = (ObjectArgumentWrapper) patchEngine.getArgumentManager().getArgument(opb);
						c_complete_slice.put(argumentWrapper.getObjectBinding().getActualB(), argumentWrapper.getTargetObject());
					}
				}
				for(Change change : opInv.getChangeSet().getChanges()){
					if(change instanceof RemoveObject){
						RemoveObject removeObject = (RemoveObject)change;
						Set<EObject> removedObjects = new HashSet<EObject>();
						for(EObject c_complete : c_complete_slice.keySet()){
							EObject c_slice = c_complete_slice.get(c_complete);
							if(removeObject.getObj().equals(c_slice)){
								removedObjects.add(c_complete);
							}
						}
						for(EObject removedObject : removedObjects){
							c_complete_slice.remove(removedObject);
						}
					}
				}
			}
		}
	}
	
//	/**
//	 * calculates the elements to be added to the slice
//	 * @param slicingCriteria
//	 * @return
//	 */
//	private Set<EObject> addedSlicingCriteria(Set<EObject> slicingCriteria){
//		Set<EObject> addedElements = new HashSet<EObject>();
//		for(EObject eObject : slicingCriteria){
//			addedElements.add(eObject);
//			EObject container = eObject.eContainer();
//			while(container != null){
//				addedElements.add(container);
//				container = container.eContainer();
//			}
//		}
//		return addedElements;
//	}
//	
//	/**
//	 * calculates the elements to be removed from the slice
//	 * @param slicingCriteria
//	 * @return
//	 */
//	private Set<EObject> removedSlicingCriteria(Set<EObject> slicingCriteria){
//		Set<EObject> removedElements = new HashSet<EObject>();
//		for (Iterator<EObject> iterator = slicedResource.getAllContents(); iterator.hasNext();) {
//			EObject eObject = iterator.next();
//			for(EObject c_complete : c_complete_slice.keySet()){
//				if(c_complete_slice.get(c_complete).equals(eObject) && !slicingCriteria.contains(c_complete_slice.get(c_complete))){
//					removedElements.add(c_complete);
//				}
//			}
//		}
//		return removedElements;
//	}
	
//	/**
//	 * generates UUIDs for elements without one
//	 * @param model
//	 */
//	private void generateIDs(Resource model){
//		for (Iterator<EObject> iterator = model.getAllContents(); iterator.hasNext();) {
//			EObject eObject = iterator.next();
//			String id = EMFUtil.getXmiId(eObject);
//			if(id == null){
//				EMFUtil.setXmiId(eObject, EcoreUtil.generateUUID());
//			}
//		}
//	}

	/**
	 * 
	 * @return
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * 
	 * @return
	 */
	public PatchEngine getPatchEngine() {
		return patchEngine;
	}
}
