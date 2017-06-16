package org.sidiff.slicer.rulebased;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
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
import org.sidiff.slicer.rulebased.configuration.SlicingConfiguration.SlicingMode;
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
	 * The {@link Resource} of the origin model
	 */
	private Resource originResource;
	
	/**
	 * The {@link Resource} of the target/sliced model
	 */
	private Resource targetResource;
	
	/**
	 * The {@link EditingDomain} holding the {@link #targetResource}
	 */
	private EditingDomain editingDomain;
	
	/**
	 * Set containing the initial sub-model, i.e. the root objects and their mandatory references
	 */
	private Set<EObject> submodel;
	
	/**
	 * A map holding the correspondences between the origin and (incremental modified) target resource
	 */
	private Map<EObject, EObject> correspondences;
	
	/**
	 * The {@link AsymmetricDifference} for building the slice
	 */
	private AsymmetricDifference asymDiff;
	
	/**
	 * The {@link PatchEngine} for applying the {@link #asymDiff}
	 */
	private PatchEngine patchEngine;
	
	/**
	 * flag that indicates if the slicer is initialized
	 */
	private boolean initialized = false;
	
	/**
	 * initializes the slicer
	 * @param config
	 * 			the {@link SlicingConfiguration}
	 * @param originResource
	 * 			the {@link #originResource}
	 * @param targetResource
	 * 			the {@link #targetResource}
	 */
	public void init(SlicingConfiguration config, Resource originResource, Resource targetResource){
		this.slicingConfiguration = config;
		this.originResource = originResource;
		this.targetResource = targetResource;
		this.generateIDs(originResource);
		this.correspondences = EMFUtil.copySubModel(new HashSet<EObject>(originResource.getContents()));
		this.submodel = new HashSet<EObject>(correspondences.keySet());
		this.targetResource.getContents().addAll(correspondences.values());
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
	public void slice(Set<EObject> slicingCriteria) throws UncoveredChangesException, NotInitializedException{

		if(initialized){
			LogUtil.log(LogEvent.MESSAGE, "############### Slicer Started ###############");
			
			this.enrichSlicingCriteria(slicingCriteria);
			this.slicingConfiguration.getLiftingSettings().setComparator(new SlicingChangeSetPriorityComparator(slicingCriteria));
			
			cleanCorrespondences(slicingCriteria);
			Map<EObject, OperationInvocation> opInvs = new HashMap<EObject, OperationInvocation>();
			
			// (1)
			AsymmetricDifference asymmetricDifference = this.generateEditScript();
			for (OperationInvocation opInv : asymmetricDifference.getOperationInvocations()) {
				for (ParameterBinding pb : opInv.getOutParameterBindings()) {
					if (pb instanceof ObjectParameterBinding) {
						ObjectParameterBinding opb = (ObjectParameterBinding) pb;
						EObject out = opb.getActualB();
						opInvs.put(out, opInv);
					}
				}
			}
			
			// (2) - (3)
			Set<OperationInvocation> opInvO = new HashSet<OperationInvocation>();
			for(EObject in : slicingCriteria){
				OperationInvocation opInv = opInvs.get(in);
				if(opInv != null){
					opInvO.add(opInv);
					opInvO.addAll(opInv.getAllPredecessors());
				}
			}
			
			// 4
			for(OperationInvocation opInv : asymmetricDifference.getOperationInvocations()){
				if((!opInv.getOutParameterBindings().isEmpty() || slicingConfiguration.isChangePreserving()) && !opInvO.contains(opInv)){
					opInv.setApply(false);
				}
			}
				
			// 5
			this.initPatchEngine();
			if(slicingConfiguration.getSlicingMode().equals(SlicingMode.BATCH)){
				this.applyEditScript();
				LogUtil.log(LogEvent.MESSAGE, "############### Slicer FINISHED ###############");
			}
		}else{
			throw new NotInitializedException();
		}

	}
	
	// ############### inner accessed methods ###############
	
	/**
	 * calculates an edit script with {@link #targetResource} as origin model
	 * and {@link #originResource} as changed model using the correspondences
	 * in {@link #correspondences}
	 * 
	 * @return an {@link AsymmetricDifference}
	 * @throws UncoveredChangesException 
	 */
	private AsymmetricDifference generateEditScript() throws UncoveredChangesException{
		
		Matching matching = MatchingModelFactory.eINSTANCE.createMatching();
		matching.setEResourceA(targetResource);
		matching.setEResourceB(originResource);
		matching.setUriA(targetResource.getURI().toFileString());
		matching.setUriB(originResource.getURI().toFileString());
		
		for(EObject eObject : correspondences.keySet()){
			Correspondence correspondence = MatchingModelFactory.eINSTANCE.createCorrespondence();
			correspondence.setMatchedA(correspondences.get(eObject));
			correspondence.setMatchedB(eObject);
			matching.getCorrespondences().add(correspondence);
		}
		
		for (Iterator<EObject> iterator = originResource.getAllContents(); iterator.hasNext();) {
			EObject eObject =  iterator.next();
			if(!correspondences.containsKey(eObject)){
				matching.getUnmatchedB().add(eObject);
			}
		}
		
		for (Iterator<EObject> iterator = targetResource.getAllContents(); iterator.hasNext();) {
			EObject eObject =  iterator.next();
			if(!correspondences.containsValue(eObject)){
				matching.getUnmatchedA().add(eObject);
			}
		}
		
		SymmetricDifference technicalDifference = AsymmetricDiffFacade.deriveTechnicalDifference(matching, this.slicingConfiguration.getLiftingSettings());
		asymDiff = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(technicalDifference, this.slicingConfiguration.getLiftingSettings()).getAsymmetric();
		if(DifferenceAnalysisUtil.getRemainingChanges(asymDiff.getSymmetricDifference()).size() > 0){
			throw new UncoveredChangesException();
		}
		return asymDiff;
	}
	
	private void initPatchEngine(){
		IArgumentManager argumentManager = new SlicingArgumentManager(correspondences);
		PatchingSettings settings = new PatchingSettings(slicingConfiguration.getLiftingSettings().getScope(), false,
				slicingConfiguration.getLiftingSettings().getMatcher(),
				slicingConfiguration.getLiftingSettings().getCandidatesService(),
				slicingConfiguration.getLiftingSettings().getCorrespondencesService(),
				slicingConfiguration.getLiftingSettings().getTechBuilder(), null,
				argumentManager,
				new BatchInterruptHandler(),
				TransformationEngineUtil.getFirstTransformationEngine(ITransformationEngine.DEFAULT_DOCUMENT_TYPE),
				null, slicingConfiguration.getSlicingMode().equals(SlicingMode.BATCH) ? ExecutionMode.BATCH
						: ExecutionMode.INTERACTIVE,
				PatchMode.PATCHING, 100, ValidationMode.NO_VALIDATION);
		
		patchEngine = new PatchEngine(asymDiff, targetResource, settings);
		if(editingDomain != null){
			patchEngine.setPatchedEditingDomain(editingDomain);
		}
	}
	
	/**
	 * applies an edit script onto a target model and adds new created model
	 * elements to {@link #correspondences}
	 */
	private void applyEditScript(){
		patchEngine.applyPatch(true);
		updateCorrespondences();
	}
	
	/**
	 * 
	 * @param slicingCriteria
	 */
	private void cleanCorrespondences(Set<EObject> slicingCriteria){
		Set<EObject> removedSlicingCriteria = new HashSet<EObject>();
		for(EObject eObject : correspondences.keySet()){
			if(!slicingCriteria.contains(eObject) && !submodel.contains(eObject)){
				removedSlicingCriteria.add(eObject);
			}
		}
		for(EObject eObject : removedSlicingCriteria){
			correspondences.remove(eObject);
		}
	}
	
	/**
	 * 
	 */
	private void updateCorrespondences(){
		for(OperationInvocation opInv : asymDiff.getOperationInvocations()){
			if(opInv.isApply()){
				for(ParameterBinding pb : opInv.getOutParameterBindings()){
					if(pb instanceof ObjectParameterBinding){
						ObjectParameterBinding opb = (ObjectParameterBinding) pb;
						ObjectArgumentWrapper argumentWrapper = (ObjectArgumentWrapper) patchEngine.getArgumentManager().getArgument(opb);
						correspondences.put(argumentWrapper.getObjectBinding().getActualB(), argumentWrapper.getTargetObject());
					}
				}
			}
		}
	}
	
	/**
	 * enriches the slicing criteria, i.e. adds all container elements
	 * @param slicingCriteria
	 */
	private void enrichSlicingCriteria(Set<EObject> slicingCriteria){
		Set<EObject> containers = new HashSet<EObject>();
		for(EObject eObject : slicingCriteria){
			EObject container = eObject.eContainer();
			while(container != null){
				containers.add(container);
				container = container.eContainer();
			}
		}
		slicingCriteria.addAll(containers);
	}
	
	/**
	 * generates UUIDs for elements without one
	 * @param model
	 */
	private void generateIDs(Resource model){
		for (Iterator<EObject> iterator = model.getAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			String id = EMFUtil.getXmiId(eObject);
			if(id == null){
				EMFUtil.setXmiId(eObject, java.util.UUID.randomUUID().toString());
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * sets the {@link #editingDomain}
	 * @param editingDomain
	 */
	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	/**
	 * 
	 * @return
	 */
	public PatchEngine getPatchEngine() {
		return patchEngine;
	}
	
	/**
	 * 
	 * @param slicingMode
	 */
	public void switchSlicingMode(SlicingMode slicingMode){
		slicingConfiguration.setSlicingMode(slicingMode);
	}
}
