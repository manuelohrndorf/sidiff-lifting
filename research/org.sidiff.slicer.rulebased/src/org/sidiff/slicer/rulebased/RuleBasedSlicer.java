package org.sidiff.slicer.rulebased;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.patching.PatchEngine;
import org.sidiff.patching.batch.arguments.BatchMatcherBasedArgumentManager;
import org.sidiff.patching.batch.handler.BatchInterruptHandler;
import org.sidiff.patching.settings.ExecutionMode;
import org.sidiff.patching.settings.PatchMode;
import org.sidiff.patching.settings.PatchingSettings;
import org.sidiff.patching.settings.PatchingSettings.ValidationMode;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.slicer.ISlicer;
import org.sidiff.slicer.ISlicingConfiguration;
import org.sidiff.slicer.exception.WrongConfigurationException;
import org.sidiff.slicer.rulebased.configuration.SlicingConfiguration;

/**
 * 
 * @author cpietsch
 *
 */
public class RuleBasedSlicer implements ISlicer {

	/**
	 * The {@link SlicingConfiguration} specifying the the ...
	 */
	private SlicingConfiguration slicingConfiguration;
	
	
	// ############### inner accessed fields ###############
	
	/**
	 * 
	 */
	private Resource targetModel;

	
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

	@Override
	public boolean canHandleDocTypes(Set<String> documentTypes) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canHandleModels(Collection<Resource> models) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init(ISlicingConfiguration config) throws Exception{
		if(config instanceof SlicingConfiguration){
			this.slicingConfiguration = (SlicingConfiguration) config;
		}else{
			throw new WrongConfigurationException();
		}

	}

	/**
	 *  1. Delta_G = generateEditScript(e, G)
	 *  2. ES_S = {es in ES_G | cre(es) in S != null}
	 *  3. ES_O = ES_S cup {es in (ES_G \ ES_S) | exists es' in ES_G : es < es'}
	 *  4. Delta_O = (ES_O. DEP_O) with DEP_O = {(es_1, es_2) in DEP_G | es_1 in ES_O and es_2 in ES_O}
	 *  5. O = applyEditScript(Delta_O, e)
	 */
	@Override
	public void slice(Collection<EObject> input) {
		
		Map<EObject, OperationInvocation> opInvs = new HashMap<EObject, OperationInvocation>();
		
		Resource originModel = input.iterator().next().eResource();
		Resource emptyModel = new ResourceSetImpl().createResource(URI.createURI("emptyModel/"+originModel.getURI().lastSegment()));
		emptyModel.getContents().addAll(EMFUtil.copySubModel(new HashSet<EObject>(originModel.getContents())));
		
		// (1)
		AsymmetricDifference asymmetricDifference = this.generateEditScript(emptyModel, originModel);
		for (OperationInvocation opInv : asymmetricDifference.getOperationInvocations()) {
			Set<EObject> outs = new HashSet<EObject>();
			for (ParameterBinding pb : opInv.getOutParameterBindings()) {
				if (pb instanceof ObjectParameterBinding) {
					ObjectParameterBinding opb = (ObjectParameterBinding) pb;
					EObject out = opb.getActualB();
					opInvs.put(out, opInv);
					outs.add(out);
				}
			}
		}
		
		// (2) - (3)
		Set<OperationInvocation> opInvO = new HashSet<OperationInvocation>();
		for(EObject in : input){
			OperationInvocation opInv = opInvs.get(in);
			opInvO.add(opInv);
			opInvO.addAll(opInv.getAllPredecessors());
		}
		
		// 4
		for(OperationInvocation opInv : asymmetricDifference.getOperationInvocations()){
			if(!opInvO.contains(opInv)){
				opInv.setApply(false);
			}
		}
			
		this.targetModel =  new ResourceSetImpl().createResource(URI.createURI("targetModel/"+originModel.getURI().lastSegment()));
		this.targetModel.getContents().addAll(EMFUtil.copySubModel(new HashSet<EObject>(asymmetricDifference.getOriginModel().getContents())));
		
		// 5
		this.applyEditScript(asymmetricDifference, targetModel);
		LogUtil.log(LogEvent.MESSAGE, "############### Slicer FINISHED ###############");

	}


	
	private AsymmetricDifference generateEditScript(Resource emptyModel, Resource originModel){
		AsymmetricDifference asymDiff = null;
		
		try {
			asymDiff = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(emptyModel, originModel,
					this.slicingConfiguration.getLiftingSettings()).getAsymmetric();
		} catch (InvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoCorrespondencesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return asymDiff;
	}
	
	private void applyEditScript(AsymmetricDifference asymmetricDifference, Resource targetModel){

		PatchingSettings settings = new PatchingSettings(slicingConfiguration.getLiftingSettings().getScope(), false,
				slicingConfiguration.getLiftingSettings().getMatcher(),
				slicingConfiguration.getLiftingSettings().getCandidatesService(),
				slicingConfiguration.getLiftingSettings().getCorrespondencesService(),
				slicingConfiguration.getLiftingSettings().getTechBuilder(), null,
				new BatchMatcherBasedArgumentManager(slicingConfiguration.getLiftingSettings().getMatcher()),
				new BatchInterruptHandler(),
				TransformationEngineUtil.getFirstTransformationEngine(ITransformationEngine.DEFAULT_DOCUMENT_TYPE),
				null, ExecutionMode.BATCH, PatchMode.PATCHING, 100, ValidationMode.NO_VALIDATION);
		
		PatchEngine patchEngine = new PatchEngine(asymmetricDifference, targetModel, settings);
		patchEngine.applyPatch(true);
	}
	
	@Override
	public Collection<EObject> getModelSlice() {
		return targetModel.getContents();
	}
}
