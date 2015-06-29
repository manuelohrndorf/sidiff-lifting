package org.sidiff.merging.twoway;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.merging.twoway.facade.TwoWayMergingFacade;
import org.sidiff.merging.twoway.facade.TwoWayMergingSettings;
import org.sidiff.merging.twoway.facade.TwoWayMergingSettings.ChangeKind;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.batch.arguments.BatchMatcherBasedArgumentManager;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.patching.ui.arguments.InteractiveArgumentManager;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;
import org.silift.patching.settings.ExecutionMode;
import org.silift.patching.settings.PatchMode;
import org.silift.patching.settings.PatchingSettings;
import org.silift.patching.settings.PatchingSettings.ValidationMode;

/**
 * 
 * @author cpietsch
 *
 */
public class MergingEngine {

	/**
	 * The {@link Resource} of model A
	 */
	private Resource modelA;
	
	/**
	 * The {@link Resource} of model B
	 */
	private Resource modelB;
	
	/**
	 * The document type of the argument models
	 */
	private String documentType;
	
	/**
	 * The {@link Difference} containing the {@link AsymmetricDifference} for creating the intersection of {@link #modelA} and {@link #modelB}
	 */
	private Difference asymDiff;
	
	/**
	 * The {@link Difference} containing the {@link AsymmetricDifference} for applying the changes minus({@link #modelA},{@link #modelB})
	 */
	private Difference asymDiffAminusB;
	
	/**
	 * The {@link Difference} containing the {@link AsymmetricDifference} for applying the changes minus({@link #modelB},{@link #modelA})
	 */
	private Difference asymDiffBminusA;
	
	/**
	 * The {@link Resource} of the merged model
	 */
	private Resource mergedModel;
	
	/**
	 * The {@link PatchEngine}
	 */
	private PatchEngine patchEngine;
	
	/**
	 * The {@link TwoWayMergingSettings}
	 */
	private TwoWayMergingSettings mergingSettings;
	
	public MergingEngine(Resource modelA, Resource modelB, TwoWayMergingSettings settings){
		this.modelA = modelA;
		this.modelB = modelB;
		this.documentType = EMFModelAccessEx.getCharacteristicDocumentType(modelA);
		this.mergingSettings = settings;
	}
	
	/**
	 * 
	 * @throws InvalidModelException
	 */
	public void init() throws InvalidModelException{
		calculateAsymDiffs();
		calculateIntersectionModel();
	}
	
	/**
	 * 
	 */
	public void merge(){
		initPatchEngine(asymDiffAminusB.getAsymmetric());
		patchEngine.applyPatch(true);
		
		initPatchEngine(asymDiffBminusA.getAsymmetric());
		patchEngine.applyPatch(true);
	}
	
	/**
	 * 
	 * @throws InvalidModelException
	 */
	private void calculateAsymDiffs() throws InvalidModelException{
		// intersection
		mergingSettings.setChangeKind(ChangeKind.ADD);
		asymDiff = TwoWayMergingFacade.liftMeUp(modelA, modelB, mergingSettings);
		
		mergingSettings.setChangeKind(ChangeKind.REMOVE);
		// minus(modelA, modelB)
		asymDiffAminusB = TwoWayMergingFacade.liftMeUp(modelA, modelB, mergingSettings);
		// minus(modelB, modelA)
		asymDiffBminusA = TwoWayMergingFacade.liftMeUp(modelB, modelA, mergingSettings);
	}
	
	/**
	 * 
	 * @param asymmetricDifference
	 */
	private void initPatchEngine(AsymmetricDifference asymmetricDifference){
		PatchingSettings patchingSettings = new PatchingSettings();
		patchingSettings.setExecutionMode(mergingSettings.getExecutionMode());
		patchingSettings.setMatcher(mergingSettings.getMatcher());
		patchingSettings.setPatchMode(PatchMode.MERGING);
		patchingSettings.setRuleBases(mergingSettings.getRuleBases());
		patchingSettings.setScope(mergingSettings.getScope());
		if(mergingSettings.getExecutionMode().equals(ExecutionMode.BATCH)){
			patchingSettings.setArgumentManager(new BatchMatcherBasedArgumentManager(mergingSettings.getMatcher()));
			patchingSettings.setInterruptHandler(new BatchInterruptHandler());
		}else if(mergingSettings.getExecutionMode().equals(ExecutionMode.INTERACTIVE)){
			patchingSettings.setArgumentManager(new InteractiveArgumentManager(mergingSettings.getMatcher()));
			patchingSettings.setInterruptHandler(new DialogPatchInterruptHandler());
		}
		patchingSettings.setTransformationEngine(TransformationEngineUtil.getFirstTransformationEngine(documentType));
		patchingSettings.setValidationMode(ValidationMode.NO_VALIDATION);
		
		patchEngine = new PatchEngine(asymmetricDifference, mergedModel, patchingSettings);
	}
	
	/**
	 * 
	 */
	private void calculateIntersectionModel(){
		mergedModel = modelA;
		initPatchEngine(asymDiff.getAsymmetric());
		patchEngine.applyPatch(true);
	}

	/**
	 * 
	 */
	public void serializeMergedModel(String path){	
		URI uri = URI.createFileURI(path);
		EMFStorage.eSaveAs(uri, mergedModel.getContents().get(0));
		mergedModel = EMFStorage.eLoad(uri).eResource();
	}
	
	// ---------- Getter-/Setter-Methods ----------
	
	/**
	 * 
	 * @return
	 */
	public PatchEngine getPatchEngine() {
		return patchEngine;
	}
}
