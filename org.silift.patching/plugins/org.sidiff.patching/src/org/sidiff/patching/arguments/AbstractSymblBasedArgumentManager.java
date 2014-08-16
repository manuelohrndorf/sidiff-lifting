package org.sidiff.patching.arguments;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.silift.common.util.emf.ExternalReferenceCalculator;
import org.silift.common.util.emf.ExternalReferenceContainer;
import org.silift.difference.symboliclink.SymbolicLink;
import org.silift.difference.symboliclink.SymbolicLinks;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

public abstract class AbstractSymblBasedArgumentManager extends BaseArgumentManager {

	/**
	 * The symbolic link handler to be used to resolve the symbolic links of the originModel.
	 */
	private ISymbolicLinkHandler symbolicLinkHandler;

	/**
	 * Mapping of symbolic links and objects of the target model
	 */
	private Map<SymbolicLink, EObject> linkResolving;

	public AbstractSymblBasedArgumentManager(ISymbolicLinkHandler symbolicLinkHandler) {
		this.symbolicLinkHandler = symbolicLinkHandler;
	}

	@Override
	public float getReliability(ObjectParameterBinding binding, EObject targetObject) {
		EObject originObject = binding.getActualA();
		if(originObject == targetObject){
			//if the origin object is not a symbolic link, it can only be 
			//taken from a model of the package registry.
			if(binding.getActualA() instanceof SymbolicLink){
				return ((SymbolicLink)binding.getActualA()).getReliability();
			}else{
				return 1.f;
			}
		}
		return 0.0f;
	}
	
	@Override
	protected EObject resolveOriginObject(EObject originObject) {
		if(linkResolving == null){
			linkResolving = symbolicLinkHandler.resolveSymbolicLinks(
					(SymbolicLinks) getOriginModel().getContents().get(0),
					getTargetModel(), true);
		}
		if(originObject instanceof SymbolicLink){
			return linkResolving.get(originObject);
		}else{
			//if the origin object is not a symbolic link, it can only be 
			//taken from a model of the package registry
			return originObject;
		}
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
	protected Map<SymbolicLink, EObject> getLinkResolving() {
		return linkResolving;
	}
}
