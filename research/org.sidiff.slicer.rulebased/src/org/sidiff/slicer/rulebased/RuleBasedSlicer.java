package org.sidiff.slicer.rulebased;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil;
import org.sidiff.patching.PatchEngine;
import org.sidiff.slicer.rulebased.configuration.SlicingConfiguration;
import org.sidiff.slicer.rulebased.exceptions.ExtendedSlicingCriteriaIntersectionException;
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
	 * The {@link AsymmetricDifference} for creating the complete model
	 */
	private AsymmetricDifference editScript_create;
	
	private Map<EObject, OperationInvocation> opInvsCreate;
	
	/**
	 * The {@link AsymmetricDifference} for deleting the complete model
	 */
	private AsymmetricDifference editScript_delete;
	
	private Map<EObject, OperationInvocation> opInvsDelete;
	
	/**
	 * The {@link PatchEngine} for applying the {@link #asymDiff}
	 */
	private PatchEngine patchEngine;
	
	/**
	 * flag that indicates if the slicer is initialized
	 */
	private boolean initialized = false;

	/**
	 * The previous slicing criteria;
	 */
	private Set<EObject> slicingCriteria_old;
	
	/**
	 * The current slicing Criteria;
	 */
	private Set<EObject> slicingCriteria_new;
	
	private Set<EObject> addSlicingCriteria;
	
	private Set<EObject> extend_addSlicingCriteria;
	
	private Set<EObject> remSlicingCriteria;
	
	private Set<EObject> extend_remSlicingCriteria;
	
	/**
	 * initializes the slicer
	 * @param config
	 * 			the {@link SlicingConfiguration}
	 * @param completeResource
	 * 			the {@link #completeResource}
	 * @param slicedResource
	 * 			the {@link #slicedResource}
	 * @throws NoCorrespondencesException 
	 * @throws InvalidModelException 
	 * @throws UncoveredChangesException 
	 */
	public void init(SlicingConfiguration config, Resource completeResource, Resource emtpyResource) throws UncoveredChangesException, InvalidModelException, NoCorrespondencesException{
		
		this.slicingConfiguration = config;
		
		this.completeResource = completeResource;
		this.emptyResource = emtpyResource;
		
		Map<EObject, EObject> copies = EMFUtil.copySubModel(new HashSet<EObject>(completeResource.getContents()));
		
		this.emptyResource.getContents().addAll(copies.values());
		
		for(EObject origin : copies.keySet()){
			String id = EMFUtil.getXmiId(origin);
			EMFUtil.setXmiId(copies.get(origin), id);
		}
		
		this.slicingCriteria_old = new HashSet<EObject>();
		this.slicingCriteria_new = new HashSet<EObject>(copies.keySet());
		
		editScript_create = generateEditScript(emtpyResource, completeResource);
		
		opInvsCreate = new HashMap<EObject, OperationInvocation>();
		for (OperationInvocation opInv : editScript_create.getOperationInvocations()) {
			for(Change change : opInv.getChangeSet().getChanges()){
				if(change instanceof AddObject){
					EObject obj = ((AddObject)change).getObj();
					opInvsCreate.put(obj, opInv);
				}
			}
		}
		
		editScript_delete = generateEditScript(completeResource, emtpyResource);
		
		opInvsDelete = new HashMap<EObject, OperationInvocation>();
		for (OperationInvocation opInv : editScript_delete.getOperationInvocations()) {
			for(Change change : opInv.getChangeSet().getChanges()){
				if(change instanceof RemoveObject){
					EObject obj = ((RemoveObject)change).getObj();
					opInvsDelete.put(obj, opInv);
				}
			}
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
	 * @throws ExtendedSlicingCriteriaIntersectionException 
	 */
	public AsymmetricDifference slice(Set<EObject> slicingCriteria) throws NotInitializedException, ExtendedSlicingCriteriaIntersectionException{

		if(initialized){
			LogUtil.log(LogEvent.MESSAGE, "############### Slicer Started ###############");
			
			for(OperationInvocation opInv : editScript_create.getOperationInvocations()){
				opInv.setApply(false);
			}
			
			for(OperationInvocation opInv : editScript_delete.getOperationInvocations()){
				opInv.setApply(false);
			}
			
			this.slicingCriteria_old = new HashSet<EObject>(this.slicingCriteria_new);
			this.slicingCriteria_new = new HashSet<EObject>(slicingCriteria);
			
			addSlicingCriteria = new HashSet<EObject>(slicingCriteria_new);
			addSlicingCriteria.removeAll(this.slicingCriteria_old);
			
			remSlicingCriteria = new HashSet<EObject>(slicingCriteria_old);
			remSlicingCriteria.removeAll(slicingCriteria_new);
			
			// collect all operation invocations creating the slicing criteria
			extend_addSlicingCriteria = new HashSet<EObject>(addSlicingCriteria);
			Set<OperationInvocation> extendOpInvsCreate = new HashSet<OperationInvocation>();
			for(EObject in : addSlicingCriteria){
				OperationInvocation opInv = opInvsCreate.get(in);
				if(opInv != null){
					opInv.setApply(true);
					extendOpInvsCreate.add(opInv);
					extendOpInvsCreate.addAll(opInv.getAllPredecessors());
					for(OperationInvocation preOpInv : opInv.getAllPredecessors()){
						for(EObject eObject : opInvsCreate.keySet()){
							if(!slicingCriteria_old.contains(eObject) && preOpInv.equals(opInvsCreate.get(eObject))){
								preOpInv.setApply(true);
								extend_addSlicingCriteria.add(eObject);
								slicingCriteria_new.add(eObject);
							}
						}
					}
				}
			}
			
			slicingCriteria_new.addAll(extend_addSlicingCriteria);
			
			for (OperationInvocation opInv : editScript_create.getOperationInvocations()){
				if(!opInvsCreate.values().contains(opInv) && extendOpInvsCreate.contains(opInv.getPredecessors())){
					opInv.setApply(true);
				}
			}
			
			// collect all operation invocations deleting the removed slicing criteria
			extend_remSlicingCriteria = new HashSet<EObject>(remSlicingCriteria);
			Set<OperationInvocation> extendOpInvsDelete = new HashSet<OperationInvocation>();
			for(EObject in : remSlicingCriteria){
				OperationInvocation opInv = opInvsDelete.get(in);
				if(opInv != null){
					opInv.setApply(true);
					extendOpInvsDelete.add(opInv);
					extendOpInvsDelete.addAll(opInv.getAllPredecessors());
					for(OperationInvocation preOpInv : opInv.getAllPredecessors()){
						for(EObject eObject : opInvsDelete.keySet()){
							if(slicingCriteria_old.contains(eObject) && preOpInv.equals(opInvsDelete.get(eObject))){
								preOpInv.setApply(true);
								extend_remSlicingCriteria.add(eObject);
								slicingCriteria_new.remove(eObject);
							}
						}
					}
				}
			}
			
			
			for (OperationInvocation opInv : editScript_delete.getOperationInvocations()){
				if(!opInvsDelete.values().contains(opInv) && extendOpInvsDelete.contains(opInv.getPredecessors())){
					opInv.setApply(true);
				}
			}
			// check intersection of extendOpInvsCreate and extendOpInvsDelete
			
			Set<EObject> intersect_extendSlicingCriteria = new HashSet<EObject>();
			for(EObject remEObject : extend_remSlicingCriteria){
				if(extend_addSlicingCriteria.contains(remEObject)){
					intersect_extendSlicingCriteria.add(remEObject);
				}
			}
			
			if(!intersect_extendSlicingCriteria.isEmpty())
				throw new ExtendedSlicingCriteriaIntersectionException(intersect_extendSlicingCriteria);
		
			
			AsymmetricDifference editScript_merged = EcoreUtil.copy(editScript_create);
			editScript_merged.getOperationInvocations().addAll(EcoreUtil.copyAll(editScript_delete.getOperationInvocations()));

			
			LogUtil.log(LogEvent.MESSAGE, "############### Slicer FINISHED ###############");
			
			return  editScript_merged;
			
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
	 * @throws NoCorrespondencesException 
	 * @throws InvalidModelException 
	 */
	private AsymmetricDifference generateEditScript(Resource originModel, Resource changedModel) throws UncoveredChangesException, InvalidModelException, NoCorrespondencesException{
				
		AsymmetricDifference asymDiff = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(originModel, changedModel, this.slicingConfiguration.getLiftingSettings()).getAsymmetric();
		asymDiff.setUriOriginModel(originModel.getURI().toString());
		asymDiff.setUriChangedModel(changedModel.getURI().toString());
		if(DifferenceAnalysisUtil.getRemainingChanges(asymDiff.getSymmetricDifference()).size() > 0){
			LiftingFacade.serializeLiftedDifference(asymDiff.getSymmetricDifference(), EMFStorage.uriToPath(completeResource.getURI()).replace(completeResource.getURI().lastSegment(),  ""), "diff");
			throw new UncoveredChangesException();
		}
		return asymDiff;
	}
	
//	private void initPatchEngine(AsymmetricDifference asymDiff, Map<EObject,EObject> correspondences){
//		IArgumentManager argumentManager = new SlicingArgumentManager(correspondences);
//		PatchingSettings settings = new PatchingSettings(slicingConfiguration.getLiftingSettings().getScope(), false,
//				slicingConfiguration.getLiftingSettings().getMatcher(),
//				slicingConfiguration.getLiftingSettings().getCandidatesService(),
//				slicingConfiguration.getLiftingSettings().getCorrespondencesService(),
//				slicingConfiguration.getLiftingSettings().getTechBuilder(), null,
//				argumentManager,
//				new BatchInterruptHandler(),
//				TransformationEngineUtil.getFirstTransformationEngine(ITransformationEngine.DEFAULT_DOCUMENT_TYPE),
//				null, ExecutionMode.BATCH,
//				PatchMode.PATCHING, 100, ValidationMode.NO_VALIDATION);
//		
//		patchEngine = new PatchEngine(asymDiff, slicedResource, settings);
//	}
	
//	/**
//	 * applies an edit script onto a target model and adds new created model
//	 * elements to {@link #correspondences}
//	 */
//	private void applyEditScript(AsymmetricDifference asymDiff){
//		patchEngine.applyPatch(true);
//		updateCorrespondences(asymDiff);
//	}
	
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
//	private void updateCorrespondences(AsymmetricDifference asymDiff){
//		for(OperationInvocation opInv : asymDiff.getOperationInvocations()){
//			if(opInv.isApply()){
//				for(ParameterBinding pb : opInv.getOutParameterBindings()){
//					if(pb instanceof ObjectParameterBinding){
//						ObjectParameterBinding opb = (ObjectParameterBinding) pb;
//						ObjectArgumentWrapper argumentWrapper = (ObjectArgumentWrapper) patchEngine.getArgumentManager().getArgument(opb);
//						c_complete_slice.put(argumentWrapper.getObjectBinding().getActualB(), argumentWrapper.getTargetObject());
//					}
//				}
//				for(Change change : opInv.getChangeSet().getChanges()){
//					if(change instanceof RemoveObject){
//						RemoveObject removeObject = (RemoveObject)change;
//						Set<EObject> removedObjects = new HashSet<EObject>();
//						for(EObject c_complete : c_complete_slice.keySet()){
//							EObject c_slice = c_complete_slice.get(c_complete);
//							if(removeObject.getObj().equals(c_slice)){
//								removedObjects.add(c_complete);
//							}
//						}
//						for(EObject removedObject : removedObjects){
//							c_complete_slice.remove(removedObject);
//						}
//					}
//				}
//			}
//		}
//	}
	
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
	
	public Set<EObject> getExtendedAddSlicingCriteria(){
		Set<EObject> extendSlicingCriteria = new HashSet<EObject>(extend_addSlicingCriteria);
		extendSlicingCriteria.removeAll(addSlicingCriteria);
		return extendSlicingCriteria;
	}
	
	public Set<EObject> getExtendedRemSlicingCriteria(){
		Set<EObject> extendSlicingCriteria = new HashSet<EObject>(extend_remSlicingCriteria);
		extendSlicingCriteria.removeAll(remSlicingCriteria);
		return extendSlicingCriteria;
	}
}
