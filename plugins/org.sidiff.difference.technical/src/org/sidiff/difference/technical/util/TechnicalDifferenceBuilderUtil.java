package org.sidiff.difference.technical.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.technical.GenericTechnicalDifferenceBuilder;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;

public class TechnicalDifferenceBuilderUtil {
	
	private final static ITechnicalDifferenceBuilder GENERIC_TECHNICAL_DIFFERENCE_BUILDER = new GenericTechnicalDifferenceBuilder();

	/**
	 * Returns the available technical difference builders for the given documentTypes.
	 * If no convenient builder is found, a generic technical difference builder will be returned.
	 * 
	 * @param documentTypes
	 * @return
	 */
	public static Set<ITechnicalDifferenceBuilder> getAvailableTechnicalDifferenceBuilders(Set<String> documentTypes){
		Set<ITechnicalDifferenceBuilder> tdbSet = new HashSet<ITechnicalDifferenceBuilder>();
		
		for(ITechnicalDifferenceBuilder techBuilder : getAllAvailableTechnicalDifferenceBuilders()){
			if (techBuilder.canHandleDocTypes(documentTypes)) {
				tdbSet.add(techBuilder);
			}
		}
		
		if(tdbSet.size()==0){
			tdbSet.add(GENERIC_TECHNICAL_DIFFERENCE_BUILDER);
		}
		
		return tdbSet;
	}
	
	/**
	 * Returns the available technical difference builders for the documentTypes of the given models.
	 * If no convenient builder is found, a generic technical difference builder will be returned.
	 * 
	 * @param documentTypes
	 * @return
	 */
	public static Set<ITechnicalDifferenceBuilder> getAvailableTechnicalDifferenceBuilders(Resource modelA, Resource modelB){
		Set<ITechnicalDifferenceBuilder> tdbSet = new HashSet<ITechnicalDifferenceBuilder>();
		
		for(ITechnicalDifferenceBuilder techBuilder : getAllAvailableTechnicalDifferenceBuilders()){
			if (techBuilder.canHandleModels(modelA, modelB)) {
				tdbSet.add(techBuilder);
			}
		}
		
		if(tdbSet.size()==0){
			tdbSet.add(GENERIC_TECHNICAL_DIFFERENCE_BUILDER);
		}
		
		return tdbSet;
	}
	
	/**
	 * 
	 * Returns the default technical difference builder for the given
	 * documentTypes: <br/>
	 * In case of Ecore: take first non-generics diff builder. <br/>
	 * Otherwise: take first technical difference builder.
	 * 
	 * @param documentTypes
	 * @return
	 */
	public static ITechnicalDifferenceBuilder getDefaultTechnicalDifferenceBuilder(Set<String> documentTypes){
		Set<ITechnicalDifferenceBuilder> tdBuilders = getAvailableTechnicalDifferenceBuilders(documentTypes);

		ITechnicalDifferenceBuilder tdBuilder = null;
		if (documentTypes.contains(EcorePackage.eINSTANCE.getNsURI())){
			for (ITechnicalDifferenceBuilder iTechnicalDifferenceBuilder : tdBuilders) {
				if (!iTechnicalDifferenceBuilder.getClass().getName().contains("Generics")){
					tdBuilder = iTechnicalDifferenceBuilder;
					break;
				}
			}
		} else {
			tdBuilder = getGenericTechnicalDifferenceBuilder();
		}
		
		return tdBuilder;
	}
	
	/**
	 * Returns a generic technical difference builder.
	 * 
	 * @return
	 */
	public static ITechnicalDifferenceBuilder getGenericTechnicalDifferenceBuilder(){
		return GENERIC_TECHNICAL_DIFFERENCE_BUILDER;
	}
	
	/**
	 * Returns the technical difference builder with the given key.
	 * 
	 * @param name
	 * @return
	 */
	public static ITechnicalDifferenceBuilder getTechnicalDifferenceBuilder(String key){
		ITechnicalDifferenceBuilder tbExtension = null;
		for(ITechnicalDifferenceBuilder techBuilder : getAllAvailableTechnicalDifferenceBuilders()){
			if (techBuilder.getKey().equals(key)) {
				tbExtension = techBuilder;
				break;
			}
		}
		return tbExtension;
	}
	
	/**
	 * Get all technical difference builders from the extension registry.
	 * 
	 * @return
	 */
	public static Set<ITechnicalDifferenceBuilder> getAllAvailableTechnicalDifferenceBuilders(){
		Set<ITechnicalDifferenceBuilder> availableTechBuilders = new HashSet<ITechnicalDifferenceBuilder>();
		
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(ITechnicalDifferenceBuilder.extensionPointID)) {
			try {
				availableTechBuilders.add((ITechnicalDifferenceBuilder) configurationElement.createExecutableExtension("difference_builder"));
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return availableTechBuilders;
	}

}
