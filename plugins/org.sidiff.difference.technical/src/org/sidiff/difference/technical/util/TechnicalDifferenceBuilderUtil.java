package org.sidiff.difference.technical.util;

import java.util.HashSet;
import java.util.Set;

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
	public static Set<ITechnicalDifferenceBuilder> getAvailableTechnicalDifferenceBuilder(String documentType){
		Set<ITechnicalDifferenceBuilder> tdbSet = new HashSet<ITechnicalDifferenceBuilder>();
		
		ITechnicalDifferenceBuilder genericTechnicalDifferenceBuilder = new GenericTechnicalDifferenceBuilder();
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(ITechnicalDifferenceBuilder.extensionPointID)) {
			try {
				ITechnicalDifferenceBuilder tdbExtension = (ITechnicalDifferenceBuilder) configurationElement.createExecutableExtension("difference_builder");
				if (documentType.equals(tdbExtension.getDocumentType())) {
					tdbSet.add(tdbExtension);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(tdbSet.size()==0)
			tdbSet.add(genericTechnicalDifferenceBuilder);
		
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
		Set<ITechnicalDifferenceBuilder> tdBuilders = TechnicalDifferenceBuilderUtil.getAvailableTechnicalDifferenceBuilder(documentType);
		assert (!tdBuilders.isEmpty()) : "No technical difference builder found for documentType " + documentType;
		
		ITechnicalDifferenceBuilder tdBuilder = null;
		if (documentType.equals(EcorePackage.eINSTANCE.getNsURI())){
			for (ITechnicalDifferenceBuilder iTechnicalDifferenceBuilder : tdBuilders) {
				if (!iTechnicalDifferenceBuilder.getClass().getName().contains("Generics")){
					tdBuilder = iTechnicalDifferenceBuilder;
					break;
				}
			}
		} else {
			tdBuilder = tdBuilders.iterator().next();
		}
		
		return tdBuilder;
	}

}
