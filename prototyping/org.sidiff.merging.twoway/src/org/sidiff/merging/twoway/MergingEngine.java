package org.sidiff.merging.twoway;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.merging.twoway.facade.TwoWayMergingFacade;
import org.sidiff.merging.twoway.facade.TwoWayMergingSettings;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.batch.arguments.BatchMatcherBasedArgumentManager;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.patching.ui.arguments.InteractiveArgumentManager;
import org.sidiff.patching.ui.handler.DialogPatchInterruptHandler;

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
	 * The {@link Resource} of the merged model
	 */
	private Resource mergedModel;
	
	/**
	 * The {@link Difference} containing the {@link AsymmetricDifference} for creating the intersection of {@link #modelA} and {@link #modelB}
	 */
	private Difference asymDiff;
	
	/**
	 * The {@link PatchEngine}
	 */
	private PatchEngine patchEngine;
	
	/**
	 * The {@link TwoWayMergingSettings}
	 */
	private TwoWayMergingSettings mergingSettings;
	
	public MergingEngine(Resource modelA, Resource modelB, Resource mergedModel, TwoWayMergingSettings settings){
		this.modelA = modelA;
		this.modelB = modelB;
		this.mergedModel = mergedModel;
		this.mergingSettings = settings;
	}
	
	/**
	 * 
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException 
	 */
	public void init() throws InvalidModelException, NoCorrespondencesException{
		// intersection
		asymDiff = TwoWayMergingFacade.liftMeUp(modelA, modelB, mergingSettings);
	}
	
	/**
	 * 
	 */
	public PatchEngine merge(){
		// add all elements which are in A but not in B
		initPatchEngine(asymDiff.getAsymmetric());
		if(mergingSettings.getExecutionMode().equals(ExecutionMode.BATCH)){
			patchEngine.applyPatch(true);
		}
		return patchEngine;
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
		patchingSettings.setScope(mergingSettings.getScope());
		if(mergingSettings.getExecutionMode().equals(ExecutionMode.BATCH)){
			patchingSettings.setArgumentManager(new BatchMatcherBasedArgumentManager(mergingSettings.getMatcher()));
			patchingSettings.setInterruptHandler(new BatchInterruptHandler());
		}else if(mergingSettings.getExecutionMode().equals(ExecutionMode.INTERACTIVE)){
			patchingSettings.setArgumentManager(new InteractiveArgumentManager(mergingSettings.getMatcher()));
			patchingSettings.setInterruptHandler(new DialogPatchInterruptHandler());
		}
		patchingSettings.setTransformationEngine(TransformationEngineUtil.getFirstTransformationEngine(EMFModelAccess.getCharacteristicDocumentType(mergedModel)));
		patchingSettings.setValidationMode(ValidationMode.NO_VALIDATION);
		
		patchEngine = new PatchEngine(asymmetricDifference, mergedModel, patchingSettings);
	}
	
	// ---------- Getter-/Setter-Methods ----------
	
	/**
	 * 
	 * @return
	 */
	public Resource getMergedModel(){
		return mergedModel;
	}
}
