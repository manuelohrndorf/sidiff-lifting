package org.sidiff.patching.arguments;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.ExternalReferenceCalculator;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.ExternalReferenceContainer;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.SymboliclinkPackage;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

public abstract class AbstractSymblBasedArgumentManager extends BaseArgumentManager {

	/**
	 * The symbolic link handler to be used to resolve the symbolic links of the originModel.
	 */
	private ISymbolicLinkHandler symbolicLinkHandler;

	/**
	 * Mapping of symbolic links and objects of the target model
	 */
	private Map<SymbolicLinkObject, EObject> linkResolvingA;
	
	private Map<SymbolicLinkObject, EObject> linkResolvingB;

	@Override
	public void init(AsymmetricDifference patch, Resource targetModel, IArgumentManagerSettings settings) {
		this.symbolicLinkHandler = settings.getSymbolicLinkHandler();
		super.init(patch, targetModel, settings);
		this.linkResolvingA = null;
		this.linkResolvingB = null;
	}
	
	@Override
	public float getReliability(ObjectParameterBinding binding, EObject targetObject) {
		EObject originObject = binding.getActualA();
		if(originObject == targetObject){
			//if the origin object is not a symbolic link, it can only be 
			//taken from a model of the package registry.
			if(binding.getActualA() instanceof SymbolicLinkObject){
				return ((SymbolicLinkObject)binding.getActualA()).getReliability();
			}else{
				return 1.f;
			}
		}
		return 0.0f;
	}
	
	@Override
	protected EObject resolveOriginObject(EObject originObject) {
		if(linkResolvingA == null){
			linkResolvingA = symbolicLinkHandler.resolveSymbolicLinkObjects(
					(SymbolicLinks) getOriginModel().getContents().get(0),
					getTargetModel(), true);
		}
		if(linkResolvingB == null){
			linkResolvingB = symbolicLinkHandler.resolveSymbolicLinkObjects(
					(SymbolicLinks) getChangedModel().getContents().get(0),
					getTargetModel(), true);
		}
		if(originObject instanceof SymbolicLinkObject){
			EObject inA = linkResolvingA.get(originObject);
			if(inA != null){
				return inA;
			}
			return linkResolvingB.get(originObject);
		}
		//if the origin object is not a symbolic link, it can only be 
		//taken from a model of the package registry
		return originObject;
	}
	
	@Override
	protected void collectReferencedRegistryAndResourceSetResources(){
		ExternalReferenceCalculator refCalculator = new ExternalReferenceCalculator();
		ExternalReferenceContainer extContainer = refCalculator.calculate(getTargetModel(), getScope());
		setPackageRegistryResources(extContainer.getReferencedRegistryModels());
		setResourceSetResources(extContainer.getReferencedResourceSetModels());
	}

	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected Map<SymbolicLinkObject, EObject> getLinkResolvingA() {
		return linkResolvingA;
	}
	
	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected Map<SymbolicLinkObject, EObject> getLinkResolvingB() {
		return linkResolvingB;
	}

	@Override
	public boolean canResolveArguments(AsymmetricDifference asymmetricDifference, Resource targetModel, IArgumentManagerSettings settings) {
		if(settings.getSymbolicLinkHandler() == null) {
			return false;
		}

		Resource originModel = asymmetricDifference.getOriginModel();
		if(EMFModelAccess.getCharacteristicDocumentType(originModel).equals(SymboliclinkPackage.eNS_URI)){
			SymbolicLinks symbolicLinks = (SymbolicLinks) originModel.getContents().get(0);
			return EMFModelAccess.getCharacteristicDocumentType(targetModel).equals(symbolicLinks.getDocType());
		}
		return false;
	}
}
