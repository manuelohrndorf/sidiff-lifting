package org.sidiff.difference.technical.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EcorePackage;
import org.sidiff.difference.technical.GenericTechnicalDifferenceBuilder;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;

public class TechnicalDifferenceBuilderUtil {

	/**
	 * Returns the available technical difference builders for the given documentType.
	 * If no convenient builder is found, a generic technical difference builder will be returned.
	 * 
	 * @param documentType
	 * @return
	 */
	public static Set<ITechnicalDifferenceBuilder> getAvailableTechnicalDifferenceBuilders(String documentType){
		Set<ITechnicalDifferenceBuilder> tdbSet = new HashSet<ITechnicalDifferenceBuilder>();
		
		ITechnicalDifferenceBuilder genericTechnicalDifferenceBuilder = new GenericTechnicalDifferenceBuilder();
		for(ITechnicalDifferenceBuilder techBuilder : getAllAvailableTechnicalDifferenceBuilders()){
			if (techBuilder.canHandle(documentType)) {
				tdbSet.add(techBuilder);
			}
		}
		
		if(tdbSet.size()==0){
			tdbSet.add(genericTechnicalDifferenceBuilder);
		}
		
		return tdbSet;
	}
	
	/**
	 * 
	 * Returns the default technical difference builder for the given
	 * documentType: <br/>
	 * In case of Ecore: take first non-generics diff builder. <br/>
	 * Otherwise: take first technical difference builder.
	 * 
	 * @param documentType
	 * @return
	 */
	public static ITechnicalDifferenceBuilder getDefaultTechnicalDifferenceBuilder(String documentType){
		Set<ITechnicalDifferenceBuilder> tdBuilders = getAvailableTechnicalDifferenceBuilders(documentType);
		assert (!tdBuilders.isEmpty()) : "No technical difference builder found for document type " + documentType;
		
		ITechnicalDifferenceBuilder tdBuilder = null;
		if (documentType.equals(EcorePackage.eINSTANCE.getNsURI())){
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
	
	public static ITechnicalDifferenceBuilder getGenericTechnicalDifferenceBuilder(){
		return new GenericTechnicalDifferenceBuilder();
	}
	
	/**
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
	
	private static Set<ITechnicalDifferenceBuilder> getAllAvailableTechnicalDifferenceBuilders(){
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
