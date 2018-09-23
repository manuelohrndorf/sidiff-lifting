package org.sidiff.remote.application.extraction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.emf.doctype.util.EMFDocumentTypeUtil;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.IncrementalTechnicalDifferenceBuilder;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.remote.common.settings.MultiSelectionRemoteApplicationProperty;
import org.sidiff.remote.common.settings.RemotePreferences;
import org.sidiff.remote.common.settings.SingleSelectionRemoteApplicationProperty;
import org.sidiff.slicer.rulebased.RuleBasedSlicer;
import org.sidiff.slicer.rulebased.configuration.RuleBasedSlicingConfiguration;
import org.sidiff.slicer.rulebased.exceptions.EmptySlicingCriteriaException;
import org.sidiff.slicer.rulebased.exceptions.ExtendedSlicingCriteriaIntersectionException;
import org.sidiff.slicer.rulebased.exceptions.NotInitializedException;
import org.sidiff.slicer.rulebased.exceptions.UncoveredChangesException;
import org.sidiff.slicer.rulebased.slice.ExecutableModelSlice;
import org.sidiff.slicer.rulebased.slice.arguments.ModelSliceBasedArgumentManager;

/**
 * 
 * @author cpietsch
 *
 */
public class ExtractionEngine {
	
	private final static String XMIIDMatcher = "org.sidiff.matcher.id.xmiid.XMIIDMatcher";
	
	private LiftingSettings liftingSettings;
	
	private PatchingSettings patchingSettings;
	
	private PatchEngine patchEngine;
	
	public File extract(Set<String> uuids, UUIDResource completeModel, UUIDResource emptyModel, UUIDResource slicedModel, RemotePreferences preferences) throws UncoveredChangesException, InvalidModelException, NoCorrespondencesException, NotInitializedException, ExtendedSlicingCriteriaIntersectionException, IOException, EmptySlicingCriteriaException {
		
		RuleBasedSlicingConfiguration config = new RuleBasedSlicingConfiguration();
		config.setCompleteResource(completeModel);
		config.setEmtpyResource(emptyModel);
		Set<EObject> eObjects = new HashSet<EObject>();
		
		Set<EObject> old_slicing_criteria = new HashSet<EObject>();
		for(String uuid : emptyModel.getIDToEObjectMap().keySet()) {
			old_slicing_criteria.add(completeModel.getIDToEObjectMap().get(uuid));
		}
		
		config.setOldSlicingCriteria(old_slicing_criteria);
		
		initSettings(completeModel, preferences);
		config.setLiftingSettings(liftingSettings);
		
		for(String uuid : uuids) {
			eObjects.add(completeModel.getIDToEObjectMap().get(uuid));	
		}
		
		RuleBasedSlicer slicer = new RuleBasedSlicer();
		slicer.init(config);
		ExecutableModelSlice modelSlice = (ExecutableModelSlice) slicer.slice(eObjects);
		String path = EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "");
		modelSlice.serialize(path, true);
		
		this.patchEngine = new PatchEngine(modelSlice.getAsymmetricDifference(), slicedModel, patchingSettings);
		
		this.patchEngine.applyPatch(false);
		
		slicedModel.save(slicedModel.getDefaultSaveOptions());
		return new File(EMFStorage.uriToPath(slicedModel.getURI()));
	}
	
	public File update(Set<String> uuids, UUIDResource completeModel, UUIDResource emptyModel, UUIDResource slicedModel, RemotePreferences preferences) throws UncoveredChangesException, InvalidModelException, NoCorrespondencesException, NotInitializedException, ExtendedSlicingCriteriaIntersectionException, IOException, EmptySlicingCriteriaException {
		RuleBasedSlicingConfiguration config = new RuleBasedSlicingConfiguration();
		config.setCompleteResource(completeModel);
		config.setEmtpyResource(emptyModel);
		Set<EObject> oldEObjects = new HashSet<EObject>();
		for(String id : slicedModel.getIDToEObjectMap().keySet()) {
			oldEObjects.add(completeModel.getIDToEObjectMap().get(id));
		}
		config.setOldSlicingCriteria(oldEObjects);
		Set<EObject> eObjects = new HashSet<EObject>();
		
		initSettings(completeModel, preferences);
		config.setLiftingSettings(liftingSettings);
		
		for(String uuid : uuids) {
			eObjects.add(completeModel.getIDToEObjectMap().get(uuid));	
		}
		
		RuleBasedSlicer slicer = new RuleBasedSlicer();
		slicer.init(config);
		ExecutableModelSlice modelSlice = (ExecutableModelSlice) slicer.slice(eObjects);
		String path = EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "");
		String file_path = modelSlice.serialize(path, true);
		
		this.patchEngine = new PatchEngine(modelSlice.getAsymmetricDifference(), slicedModel, patchingSettings);
		
		this.patchEngine.applyPatch(false);
		
		slicedModel.save(slicedModel.getDefaultSaveOptions());
		
		return new File(file_path);
	}
	
	private void initSettings(UUIDResource completeModel, RemotePreferences preferences) {
		Set<String> documentTypes = new HashSet<String>(EMFDocumentTypeUtil.resolve(completeModel));
		Scope scope = Scope.valueOf(preferences.getGeneralProperties().getScope().getValue());
		
		List<ITechnicalDifferenceBuilder> technicalDifferenceBuilders = new ArrayList<ITechnicalDifferenceBuilder>();
		
		for(MultiSelectionRemoteApplicationProperty<String> property : preferences.getExtractionProperties().getTechnicalDifferenceBuilderProperties()) {
			if(documentTypes.contains(property.getDocumentType())) {
				for(String key : property.getValues()) {
					ITechnicalDifferenceBuilder builder = PipelineUtils.getTechnicalDifferenceBuilder(key);
					technicalDifferenceBuilders.add(builder);
				}
			}
		}
		
		ITechnicalDifferenceBuilder technicalDifferenceBuilder = null;
		if(technicalDifferenceBuilders.size()>1) {
			technicalDifferenceBuilder = new IncrementalTechnicalDifferenceBuilder(technicalDifferenceBuilders);
		}else {
			technicalDifferenceBuilder = technicalDifferenceBuilders.iterator().next();
		}
		
		boolean mergeImports = preferences.getExtractionProperties().getMergeImports().getValue();
		
		boolean unmergeImports = preferences.getExtractionProperties().getUnmergeImports().getValue();
		
		Set<ILiftingRuleBase> ruleBases = new HashSet<ILiftingRuleBase>();
		for(MultiSelectionRemoteApplicationProperty<String> property : preferences.getExtractionProperties().getRuleBaseProperties()) {
			if(documentTypes.contains(property.getDocumentType())) {
				for(String name : property.getValues()) {
					ILiftingRuleBase ruleBase = PipelineUtils.getRulebase(name);
					ruleBases.add(ruleBase);
				}
			}
		}
		
		IRecognitionRuleSorter recognitionRuleSorter = null;
		for(SingleSelectionRemoteApplicationProperty<String> property : preferences.getExtractionProperties().getRecognitionRuleSorterProperties()){
			if(documentTypes.contains(property.getDocumentType())){
				recognitionRuleSorter = PipelineUtils.getRecognitionRuleSorter(property.getValue());
			}
		}
		
		boolean validate = preferences.getValidationProperties().getValidateModels().getValue();
		
		
		this.liftingSettings = new LiftingSettings();
		this.liftingSettings.setScope(scope);
		this.liftingSettings.setMatcher(PipelineUtils.getMatcherByKey(XMIIDMatcher));
		this.liftingSettings.setTechBuilder(technicalDifferenceBuilder);
		this.liftingSettings.setMergeImports(mergeImports);
		this.liftingSettings.setUnmergeImports(unmergeImports);
		this.liftingSettings.setRuleBases(ruleBases);
		this.liftingSettings.setRrSorter(recognitionRuleSorter);
		this.liftingSettings.setValidate(validate);

		this.patchingSettings = new PatchingSettings(this.liftingSettings.getScope(), validate,
				this.liftingSettings.getMatcher(), this.liftingSettings.getCandidatesService(),
				this.liftingSettings.getCorrespondencesService(), this.liftingSettings.getTechBuilder(), null,
				new ModelSliceBasedArgumentManager(), new BatchInterruptHandler(),
				TransformationEngineUtil.getFirstTransformationEngine(ITransformationEngine.DEFAULT_DOCUMENT_TYPE),
				null, org.sidiff.patching.ExecutionMode.BATCH, org.sidiff.patching.PatchMode.PATCHING, 100,
				org.sidiff.patching.validation.ValidationMode.NO_VALIDATION);
	}
	
}
