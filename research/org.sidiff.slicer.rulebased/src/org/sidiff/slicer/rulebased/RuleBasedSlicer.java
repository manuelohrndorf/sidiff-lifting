package org.sidiff.slicer.rulebased;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.api.AsymmetricDiffFacade;
import org.sidiff.slicer.ISlicer;
import org.sidiff.slicer.ISlicingConfiguration;
import org.sidiff.slicer.ModelSlice;
import org.sidiff.slicer.SlicedElement;
import org.sidiff.slicer.rulebased.configuration.SlicingConfiguration;

public class RuleBasedSlicer implements ISlicer {

	/**
	 * The {@link SlicingConfiguration} specifying the the ...
	 */
	private SlicingConfiguration slicingConfiguration;
	
	
	// ############### inner accessed fields ###############
	
	/**
	 * 
	 */
	private AsymmetricDifference asymmetricDifference;
	
	/**
	 * 
	 */
	private ModelSlice modelSlice;
	
	/**
	 *
	 */
	private Map<EObject, OperationInvocation> opInvs;
	
	private Map<OperationInvocation, Set<EObject>> eObjects;
	
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
	public void init(ISlicingConfiguration config) {
		if(config instanceof SlicingConfiguration){
			this.slicingConfiguration = (SlicingConfiguration) config;
			Resource originModel = ((SlicingConfiguration) config).getOriginModel();
			// (re-)initialize inner accessed fields
			
			this.modelSlice = new ModelSlice();
			Resource emptyModel = new ResourceSetImpl().createResource(URI.createURI("tmp"));
			originModel.getContents().addAll(EMFUtil.copySubModel(new HashSet<EObject>(originModel.getContents())));
			
			try {
				// 1. Delta_G = generateEditScript(e, G)
				asymmetricDifference = AsymmetricDiffFacade.deriveLiftedAsymmetricDifference(emptyModel, originModel, this.slicingConfiguration.getLiftingSettings()).getAsymmetric();
				
				for(OperationInvocation opInv : asymmetricDifference.getOperationInvocations()){
					Set<EObject> outs = new HashSet<EObject>();
					for(ParameterBinding pb : opInv.getOutParameterBindings()){
						if(pb instanceof ObjectParameterBinding){
							ObjectParameterBinding opb = (ObjectParameterBinding) pb;
							EObject out = opb.getActualB();
							opInvs.put(out, opInv);
							outs.add(out);
						}
					}
					eObjects.put(opInv, outs);
				}
				
			} catch (InvalidModelException | NoCorrespondencesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	@Override
	public void slice(Collection<EObject> input) {
		for(EObject in : input){
			if(!this.modelSlice.contains(in)){
				Set<EObject> nextInput = new HashSet<EObject>();
				
				modelSlice.addSlicedElement(new SlicedElement(in, false));
				// 2. ES_S = {es in ES_G | cre(es) in S != null}
				OperationInvocation es_s = opInvs.get(in);
				// 3. ES_O= ES_S cup {es in (ES_G \ ES_S) | exists es' in ES_G : es < es'}
				for(OperationInvocation opInv : es_s.getPredecessors()){
					nextInput.addAll(eObjects.get(opInv));
				}
				
				slice(nextInput);
			}
		}
		relinkEReferences();
	}

	
	@Override
	public Collection<EObject> getModelSlice() {
		return modelSlice.export();
	}
	
	@SuppressWarnings("unchecked")
	private void relinkEReferences(){
		for(SlicedElement slicedElement : modelSlice.getSlicedElements()){
			EObject src = slicedElement.getOrigin();
			for (EReference eReference : src.eClass().getEAllReferences()) {
				if (eReference.isChangeable() && !eReference.isDerived()) {
					if (eReference.isMany()) {
						for (EObject target : (List<EObject>) src.eGet(eReference)) {
							if (modelSlice.contains(target)) {
								((List<EObject>)modelSlice.getSlicedElement(src).getCopy().eGet(eReference)).add(modelSlice.getSlicedElement(target).getCopy());
							}
						}
					} else {
						EObject target = (EObject) src.eGet(eReference);
						if (target != null){
							if(!modelSlice.contains(target)) {
								if (eReference.isUnsettable()) {
									modelSlice.getSlicedElement(src).getCopy().eUnset(eReference);
								} else {
									modelSlice.getSlicedElement(src).getCopy().eSet(eReference, null);
								}
							} else {
								modelSlice.getSlicedElement(src).getCopy().eSet(eReference, modelSlice.getSlicedElement(target).getCopy());
							}
						}
					}
				}
			}
		}
	}

}
