package org.sidiff.remote.application.extraction;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.emf.doctype.util.EMFDocumentTypeUtil;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.slicer.rulebased.RuleBasedSlicer;
import org.sidiff.slicer.rulebased.configuration.RuleBasedSlicingConfiguration;
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
	
	private LiftingSettings liftingSettings;
	
	private PatchingSettings patchingSettings;
	
	private PatchEngine patchEngine;
	
	public File extract(Set<String> uuids, UUIDResource completeModel, UUIDResource emptyModel, UUIDResource slicedModel) throws UncoveredChangesException, InvalidModelException, NoCorrespondencesException, NotInitializedException, ExtendedSlicingCriteriaIntersectionException, IOException {
		
		RuleBasedSlicingConfiguration config = new RuleBasedSlicingConfiguration();
		config.setCompleteResource(completeModel);
		config.setEmtpyResource(emptyModel);
		Set<EObject> eObjects = new HashSet<EObject>();
		
//		Set<EObject> old_slicing_criteria = new HashSet<EObject>();
//		for(String uuid : slicedModel.getIDToEObjectMap().keySet()) {
//			old_slicing_criteria.add(completeModel.getIDToEObjectMap().get(uuid));
//		}
//		
//		config.setOldSlicingCriteria(old_slicing_criteria);
		
		initSettings(completeModel);
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
	
	public File update(Set<String> uuids, UUIDResource completeModel, UUIDResource emptyModel, UUIDResource slicedModel) throws UncoveredChangesException, InvalidModelException, NoCorrespondencesException, NotInitializedException, ExtendedSlicingCriteriaIntersectionException {
		RuleBasedSlicingConfiguration config = new RuleBasedSlicingConfiguration();
		config.setCompleteResource(completeModel);
		config.setEmtpyResource(emptyModel);
		Set<EObject> oldEObjects = new HashSet<EObject>();
		for(String id : slicedModel.getIDToEObjectMap().keySet()) {
			oldEObjects.add(completeModel.getIDToEObjectMap().get(id));
		}
		config.setOldSlicingCriteria(oldEObjects);
		Set<EObject> eObjects = new HashSet<EObject>();
		
		initSettings(completeModel);
		config.setLiftingSettings(liftingSettings);
		
		for(String uuid : uuids) {
			eObjects.add(completeModel.getIDToEObjectMap().get(uuid));	
		}
		
		RuleBasedSlicer slicer = new RuleBasedSlicer();
		slicer.init(config);
		ExecutableModelSlice modelSlice = (ExecutableModelSlice) slicer.slice(eObjects);
		String path = EMFStorage.uriToPath(completeModel.getURI()).replace(completeModel.getURI().lastSegment(), "");
		String file_path = modelSlice.serialize(path, true);
		
		return new File(file_path);
	}
	
	private void initSettings(UUIDResource completeModel) {
		this.liftingSettings = new LiftingSettings();
		
		this.liftingSettings.setMatcher(PipelineUtils.getMatcherByKey("org.sidiff.matcher.id.xmiid.XMIIDMatcher"));
		this.liftingSettings.setTechBuilder(PipelineUtils.getTechnicalDifferenceBuilder("org.sidiff.uml2v4.difference.technical.TechnicalDifferenceBuilderUMLProfileApplication"));
		this.liftingSettings.setRuleBases(PipelineUtils.getAvailableRulebases(new HashSet<String>(EMFDocumentTypeUtil.resolve(completeModel))));
		this.liftingSettings.setRrSorter(PipelineUtils.getDefaultRecognitionRuleSorter(new HashSet<String>(EMFDocumentTypeUtil.resolve(completeModel))));
		
		
		this.patchingSettings = new PatchingSettings(this.liftingSettings.getScope(), false, this.liftingSettings.getMatcher(), this.liftingSettings.getCandidatesService(), this.liftingSettings.getCorrespondencesService(), this.liftingSettings.getTechBuilder(), null, new ModelSliceBasedArgumentManager(), new BatchInterruptHandler(), TransformationEngineUtil.getFirstTransformationEngine(ITransformationEngine.DEFAULT_DOCUMENT_TYPE), ModifiedDetectorUtil.getGenericModifiedDetector(), org.sidiff.patching.ExecutionMode.INTERACTIVE, org.sidiff.patching.PatchMode.MERGING, 100, org.sidiff.patching.validation.ValidationMode.NO_VALIDATION);

	}
}
