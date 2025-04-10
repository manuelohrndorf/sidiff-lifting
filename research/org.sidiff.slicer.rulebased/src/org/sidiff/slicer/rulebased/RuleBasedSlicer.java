package org.sidiff.slicer.rulebased;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.api.util.Difference;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil;
import org.sidiff.difference.symmetric.util.SlicingChangeSetPriorityComparator;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.slicer.ISlicer;
import org.sidiff.slicer.ISlicingConfiguration;
import org.sidiff.slicer.rulebased.configuration.RuleBasedSlicingConfiguration;
import org.sidiff.slicer.rulebased.exceptions.EmptySlicingCriteriaException;
import org.sidiff.slicer.rulebased.exceptions.EmtpyModelSliceException;
import org.sidiff.slicer.rulebased.exceptions.ExtendedSlicingCriteriaIntersectionException;
import org.sidiff.slicer.rulebased.exceptions.NotInitializedException;
import org.sidiff.slicer.rulebased.exceptions.UncoveredChangesException;
import org.sidiff.slicer.rulebased.slice.ExecutableModelSlice;
import org.sidiff.slicer.rulebased.util.ExecutableModelSliceCreator;
import org.sidiff.slicer.slice.ModelSlice;

/**
 * 
 * @author cpietsch
 *
 */
public class RuleBasedSlicer implements ISlicer{

	/**
	 * The {@link RuleBasedSlicingConfiguration}
	 */
	private RuleBasedSlicingConfiguration slicingConfiguration;
	
	// ############### inner accessed fields ###############
	
	/**
	 * Mapping between correspondence elements in {@link #completeResource} and {@link #emptyResource}
	 */
	private Map<EObject,EObject> complete2emptyResource;
	
	/**
	 * The edit script for creating the complete model
	 */
	private AsymmetricDifference editScript_create;
	
	/**
	 * Mapping of created {@link EObject}s to the creating {@link OperationInvocation} in {@link #editScript_create}
	 */
	private Map<EObject, OperationInvocation> opInvsCreate;
	
	/**
	 * The {@link AsymmetricDifference} for deleting the complete model
	 */
	private AsymmetricDifference editScript_delete;
	
	/**
	 * Mapping of deleted {@link EObject}s to the deleting {@link OperationInvocation} in {@link #editScript_delete}
	 */
	private Map<EObject, OperationInvocation> opInvsDelete;
	
	/**
	 * The previous slicing criteria;
	 */
	private Set<EObject> slicingCriteria_old;
	
	/**
	 * The current slicing Criteria;
	 */
	private Set<EObject> slicingCriteria_new;
	
	/**
	 * {@link #addSlicingCriteria} = {@link #slicingCriteria_new} - {@link #slicingCriteria_old}
	 */
	private Set<EObject> addSlicingCriteria;
	
	/**
	 * {@link #extend_addSlicingCriteria} = extend({@link #slicingCriteria_new} - extend({@link #slicingCriteria_old})
	 */
	private Set<EObject> extend_addSlicingCriteria;
	
	/**
	 * {@link #remSlicingCriteria} = {@link #slicingCriteria_old} - {@link #slicingCriteria_new}
	 */
	private Set<EObject> remSlicingCriteria;
	
	/**
	 * {@link #extend_remSlicingCriteria} = extend({@link #slicingCriteria_old} - extend({@link #slicingCriteria_new})
	 */
	private Set<EObject> extend_remSlicingCriteria;
	
	/**
	 * flag that indicates if the slicer is initialized
	 */
	private boolean initialized = false;

	@Override
	public boolean canHandleConfiguration(ISlicingConfiguration config) {
		return config instanceof RuleBasedSlicingConfiguration;
	}

	/**
	 * initializes the slicer
	 * @param config
	 * 			the {@link RuleBasedSlicingConfiguration}
	 * @param completeResource
	 * 			the {@link #completeResource}
	 * @param slicedResource
	 * 			the {@link #slicedResource}
	 * @throws NoCorrespondencesException 
	 * @throws InvalidModelException 
	 * @throws UncoveredChangesException 
	 */
	@Override
	public void init(ISlicingConfiguration config) throws UncoveredChangesException, InvalidModelException, NoCorrespondencesException{
		
		this.slicingConfiguration = (RuleBasedSlicingConfiguration) config;
		
		UUIDResource emptyResource = this.slicingConfiguration.getEmtpyResource();
		UUIDResource completeResource = this.slicingConfiguration.getCompleteResource();
		
		Set<EObject> postProcessorElements = new HashSet<EObject>();
		Set<EditRule> inverseEditRules = new HashSet<EditRule>();
		
		this.complete2emptyResource = new HashMap<EObject, EObject>();
		
		for (Iterator<EObject> iterator_empty = emptyResource.getAllContents(); iterator_empty.hasNext();) {
			EObject eObject_empty = iterator_empty.next();
			for (Iterator<EObject> iterator_complete = completeResource.getAllContents(); iterator_complete.hasNext();) {
				EObject eObject_complete  =  iterator_complete.next();
				if(emptyResource.getID(eObject_empty).equals(completeResource.getID(eObject_complete))){
					this.complete2emptyResource.put(eObject_complete, eObject_empty);
					break;
				}
			}
		}
		
		
		this.slicingCriteria_old = this.slicingConfiguration.getOldSlicingCriteria();
		this.slicingCriteria_new = new HashSet<EObject>(this.complete2emptyResource.keySet());
		
		this.editScript_create = generateEditScript(emptyResource, completeResource, EditScriptDirection.CREATION);
		
		this.opInvsCreate = new HashMap<EObject, OperationInvocation>();
		
		for (OperationInvocation opInv : this.editScript_create.getOperationInvocations()) {

			Set<EObject> addedElements = new HashSet<EObject>();
			for(Change change : opInv.getChangeSet().getChanges()){
				if(change instanceof AddObject){
					EObject obj = ((AddObject)change).getObj();
					addedElements.add(obj);
					this.opInvsCreate.put(obj, opInv);
				}
			}
			
			if(addedElements.size()>1){
				postProcessorElements.addAll(addedElements);
			}
			
			EditRule editRule = opInv.resolveEditRule();
			EditRule inverseEditRule = editRule.getInverse();
			if(inverseEditRule == null && editRule.getClassification().stream().anyMatch(classification -> classification.getName().equals("VALUE CHANGE"))) {
				inverseEditRule = editRule;
			}
//			assert inverseEditRule != null: "no inversive Edit Rule found for " + opInv.getEditRuleName();
			if (inverseEditRules != null && inverseEditRule != null) {
				inverseEditRules.add(inverseEditRule);
			} else {
				inverseEditRules = null;
			}
			
		}
		
		this.slicingConfiguration.getLiftingSettings().setComparator(new SlicingChangeSetPriorityComparator(postProcessorElements));
		
		updateRuleBase(inverseEditRules);
		
		editScript_delete = generateEditScript(completeResource, emptyResource, EditScriptDirection.DELETION);
		
		opInvsDelete = new HashMap<EObject, OperationInvocation>();
		for (OperationInvocation opInv : editScript_delete.getOperationInvocations()) {
			for(Change change : opInv.getChangeSet().getChanges()){
				if(change instanceof RemoveObject){
					EObject obj = ((RemoveObject)change).getObj();
					opInvsDelete.put(obj, opInv);
				}
			}
		}

		updateRuleBase(null);
		
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
	 * @throws EmptySlicingCriteriaException 
	 * @throws EmtpyModelSliceException 
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public ModelSlice slice(Collection<EObject> input) throws NotInitializedException, ExtendedSlicingCriteriaIntersectionException, EmptySlicingCriteriaException, EmtpyModelSliceException{

		if(initialized){
			LogUtil.log(LogEvent.MESSAGE, "############### Slicer Started ###############");
			
			this.slicingCriteria_new.addAll(input);
			
			addSlicingCriteria = new HashSet<EObject>(slicingCriteria_new);
			addSlicingCriteria.removeAll(this.slicingCriteria_old);
			
			remSlicingCriteria = new HashSet<EObject>(slicingCriteria_old);
			remSlicingCriteria.removeAll(slicingCriteria_new);
			
			if(!(addSlicingCriteria.isEmpty() && remSlicingCriteria.isEmpty())) {
			Set<EObject> extend_slicingCriteria_old = new HashSet<EObject>(slicingCriteria_old);
			
			for(EObject in : slicingCriteria_old){
				if(complete2emptyResource.get(in) == null){
					OperationInvocation opInv = opInvsCreate.get(in);
					assert opInv != null: "no creating operation invocation found for " + in;
					for(OperationInvocation preOpInv : opInv.getAllPredecessors()){
						for(EObject eObject : opInvsCreate.keySet()){
							if(preOpInv.equals(opInvsCreate.get(eObject))){
								extend_slicingCriteria_old.add(eObject);
							}
						}
					}
				}
			}
			

			Set<EObject> extend_slicingCriteria_new = new HashSet<EObject>(slicingCriteria_new);

			for (EObject in : slicingCriteria_new) {
				if(complete2emptyResource.get(in) == null){
					OperationInvocation opInv = opInvsCreate.get(in);
					assert opInv != null : "no creating operation invocation found for " + in;
					for (OperationInvocation preOpInv : opInv.getAllPredecessors()) {
						for (EObject eObject : opInvsCreate.keySet()) {
							if (preOpInv.equals(opInvsCreate.get(eObject))) {
								extend_slicingCriteria_new.add(eObject);
							}
						}
					}
				}
			}
			
			extend_addSlicingCriteria = new HashSet<EObject>(extend_slicingCriteria_new);
			extend_addSlicingCriteria.removeAll(extend_slicingCriteria_old);
			
			extend_remSlicingCriteria = new HashSet<EObject>(extend_slicingCriteria_old);
			extend_remSlicingCriteria.removeAll(extend_slicingCriteria_new);
			
			Set<OperationInvocation> ignoredOpInvs = new HashSet<OperationInvocation>();
			ignoredOpInvs.addAll(editScript_create.getOperationInvocations());
			ignoredOpInvs.addAll(editScript_delete.getOperationInvocations());
			
			Set<OperationInvocation> extendOpInvsCreate = new HashSet<OperationInvocation>();
			for(EObject addElement : extend_addSlicingCriteria){
				OperationInvocation opInv = opInvsCreate.get(addElement);
				assert opInv != null : "no creating operation invocation found for " + addElement;
				extendOpInvsCreate.add(opInv);
			}
			
			// collect all operation invocations creating all connecting edges according the
			// transitive closure of the required relation, which is implied by dependencies
			// between operation invocations
			for (OperationInvocation opInv : editScript_create.getOperationInvocations()){
				if(!opInvsCreate.values().contains(opInv) && extendOpInvsCreate.contains(opInv.getPredecessors())){
					extendOpInvsCreate.add(opInv);
				}
			}
			
			Set<OperationInvocation> extendOpInvsDelete = new HashSet<OperationInvocation>();
			for(EObject remElement : extend_remSlicingCriteria){
				OperationInvocation opInv = opInvsDelete.get(remElement);
				assert opInv != null : "no deleting operation invocation found for " + remElement;
				extendOpInvsDelete.add(opInv);
			}
			
			
			// collect all operation invocations deleting all connecting edges according the
			// transitive closure of the required relation, which is implied by dependencies
			// between operation invocations
			for (OperationInvocation opInv : editScript_delete.getOperationInvocations()){
				if(!opInvsDelete.values().contains(opInv) && extendOpInvsDelete.contains(opInv.getPredecessors())){
					extendOpInvsDelete.add(opInv);
				}
			}
			
			// check intersection of extendOpInvsCreate and extendOpInvsDelete
			Set<EObject> intersect_extendSlicingCriteria = new HashSet<EObject>();
			for(EObject remEObject : extend_remSlicingCriteria){
				if(extend_addSlicingCriteria.contains(remEObject)){
					intersect_extendSlicingCriteria.add(remEObject);
				}
			}
			
			this.slicingCriteria_new.addAll(this.extend_addSlicingCriteria);
			
			if(!intersect_extendSlicingCriteria.isEmpty())
				throw new ExtendedSlicingCriteriaIntersectionException(intersect_extendSlicingCriteria);
			
			ignoredOpInvs.removeAll(extendOpInvsCreate);
			ignoredOpInvs.removeAll(extendOpInvsDelete);
			
			ExecutableModelSlice executableModelSlice = new ExecutableModelSliceCreator().createExecutableModelSlice(extendOpInvsCreate, extendOpInvsDelete, ignoredOpInvs);
			
			LogUtil.log(LogEvent.MESSAGE, "############### Slicer FINISHED ###############");
			if(executableModelSlice.getAsymmetricDifference().getOperationInvocations().isEmpty()) {
				throw new EmtpyModelSliceException();
			}
			return  executableModelSlice;
			}else {
				throw new EmptySlicingCriteriaException();
			}
			
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
	 * @param direction
	 * @return an {@link AsymmetricDifference}
	 * @throws UncoveredChangesException 
	 * @throws NoCorrespondencesException 
	 * @throws InvalidModelException 
	 */
	private AsymmetricDifference generateEditScript(Resource originModel, Resource changedModel, EditScriptDirection direction) throws UncoveredChangesException, InvalidModelException, NoCorrespondencesException{
		URI path = originModel.getURI().trimSegments(1);
		String fileName = "editScript_" + direction;
		String asymmetricDifferencePath = path + fileName + "." + AsymmetricDiffFacade.ASYMMETRIC_DIFF_EXT;
		
		// Try to load an existing patch:
		if (new File(asymmetricDifferencePath).exists()) {
			try {
				URI asymmetricDifferenceURI = EMFStorage.pathToUri(asymmetricDifferencePath);
				changedModel.getResourceSet().getResources().add(originModel);
				Resource asymmetricDifferenceResource = changedModel.getResourceSet().getResource(asymmetricDifferenceURI, true);
				AsymmetricDifference asymmetricDifference = (AsymmetricDifference) asymmetricDifferenceResource.getContents().get(0);
				
				return asymmetricDifference;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Create new patch:
		Difference diff = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(originModel, changedModel, this.slicingConfiguration.getLiftingSettings());
		AsymmetricDifference asymDiff = diff.getAsymmetric(); 
		asymDiff.setUriOriginModel(originModel.getURI().toString());
		asymDiff.setUriChangedModel(changedModel.getURI().toString());
		
		if (LogUtil.getLogEvents().contains(LogEvent.DEBUG.name())){
			int uncoveredChanges = DifferenceAnalysisUtil.getRemainingChanges(asymDiff.getSymmetricDifference()).size();
			LogUtil.log(LogEvent.DEBUG, "uncovered changes: " + uncoveredChanges);
			if(uncoveredChanges > 0) {
				LiftingFacade.serializeLiftedDifference(asymDiff.getSymmetricDifference(), path, fileName + "." + AsymmetricDiffFacade.SYMMETRIC_DIFF_EXT);
				throw new UncoveredChangesException();
			}
		}
		
		diff.serialize(SiDiffResourceSet.create(), path, fileName);

		return asymDiff;
	}
	
	/**
	 * enables all edit rules contained in activeEditRules
	 * @param activeEditRules
	 * 			{@link EditRule}s to be activated
	 * 			if the parameter is null, all edit rules will be enabled
	 */
	private void updateRuleBase(Set<EditRule> activeEditRules) {
		for(ILiftingRuleBase ruleBase : this.slicingConfiguration.getLiftingSettings().getRuleBases()) {
			if(activeEditRules == null) {
				ruleBase.activateAllEditRules();
			}else {
				for(EditRule editRule: ruleBase.getActiveEditRules()) {
					if(!activeEditRules.contains(editRule)) {
						editRule.getRuleBaseItem().setActive(false);
					}
				}
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
	
	public Set<EObject> getSlicingCriteria_old() {
		return slicingCriteria_old;
	}

	public Set<EObject> getSlicingCriteria_new() {
		return slicingCriteria_new;
	}

	public Set<EObject> getAddSlicingCriteria() {
		return addSlicingCriteria;
	}

	public Set<EObject> getRemSlicingCriteria() {
		return remSlicingCriteria;
	}

	public Set<EObject> getExtendedAddSlicingCriteria(){
		Set<EObject> extendSlicingCriteria = new HashSet<EObject>(extend_addSlicingCriteria);
		extendSlicingCriteria.removeAll(addSlicingCriteria);
		return extendSlicingCriteria;
	}
	
	public Set<EObject> getExtendedRemSlicingCriteria(){
		Set<EObject> extendSlicingCriteria = new HashSet<EObject>(remSlicingCriteria);
		extendSlicingCriteria.removeAll(extend_remSlicingCriteria);
		return extendSlicingCriteria;
	}
	
	private enum EditScriptDirection{
		CREATION,
		DELETION
	}

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

	public boolean canHandleDocTypes(Set<String> documentTypes) {
		return !PipelineUtils.getAvailableRulebases(documentTypes).isEmpty();
	}

	@Override
	public boolean canHandleModels(Collection<Resource> models) {
		Set<String> documentTypes = EMFModelAccess.getDocumentTypes(new ArrayList<Resource>(models));
		return canHandleDocTypes(documentTypes);
	}
}
