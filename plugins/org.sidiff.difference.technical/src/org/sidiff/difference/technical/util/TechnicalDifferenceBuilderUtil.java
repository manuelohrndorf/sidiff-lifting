package org.sidiff.difference.technical.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EcorePackage;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.technical.GenericTechnicalDifferenceBuilder;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;

public class TechnicalDifferenceBuilderUtil {

	/**
	 * Returns the available technical difference builders for the given documentType.
	 * If no convenient builder is found, a generic technical difference builder will be returned.
	 * 
	 * @param documentTypes
	 * @return
	 */
	public static Set<ITechnicalDifferenceBuilder> getAvailableTechnicalDifferenceBuilders(Set<String> documentTypes){
		Set<ITechnicalDifferenceBuilder> tdbSet = new HashSet<ITechnicalDifferenceBuilder>();
		
		ITechnicalDifferenceBuilder genericTechnicalDifferenceBuilder = new GenericTechnicalDifferenceBuilder();
		for(ITechnicalDifferenceBuilder techBuilder : getAllAvailableTechnicalDifferenceBuilders()){
			if (techBuilder.canHandleDocTypes(documentTypes)) {
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
	 * documentTypes: <br/>
	 * In case of Ecore: take first non-generics diff builder. <br/>
	 * Otherwise: take first technical difference builder.
	 * 
	 * @param documentTypes
	 * @return
	 */
	public static ITechnicalDifferenceBuilder getDefaultTechnicalDifferenceBuilder(Set<String> documentTypes){
		Set<ITechnicalDifferenceBuilder> tdBuilders = getAvailableTechnicalDifferenceBuilders(documentTypes);
		assert (!tdBuilders.isEmpty()) : "No technical difference builder found for document type " + documentTypes;
		
		ITechnicalDifferenceBuilder tdBuilder = null;
		if (documentTypes.equals(EcorePackage.eINSTANCE.getNsURI())){
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
		return new GenericTechnicalDifferenceBuilder();
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

	/**
	 * Just sorts the low-level changes for better readability in the difference
	 * viewer. Of course, the order of the low-level changes has no semantics.
	 * 
	 * @param difference
	 *            The difference which contains the low-level changes.
	 */
	public static void sortLowLevelChanges(SymmetricDifference difference) {
		
		// Note: In order to prevent the IllegalArgumentException "The 'no
		// duplicates' constraint is violated" on the changes EList, we
		// do sort on a copy of diff.getChanges().
		List<Change> changes = new ArrayList<Change>(difference.getChanges());

		Collections.sort(changes, new Comparator<Change>() {
			@Override
			public boolean equals(Object obj) {
				return super.equals(obj);
			}

			public int compare(Change c1, Change c2) {
				return changeToString(c1).compareTo(changeToString(c2));
			}

			private String changeToString(Change c) {
				String res = c.eClass().getName();
				if (c instanceof AddReference) {
					res += ((AddReference) c).getType().getName();
				}
				if (c instanceof RemoveReference) {
					res += ((RemoveReference) c).getType().getName();
				}
				if (c instanceof AddObject) {
					res += ((AddObject) c).getObj().eClass().getName();
				}
				if (c instanceof RemoveObject) {
					res += ((RemoveObject) c).getObj().eClass().getName();
				}
				if (c instanceof AttributeValueChange) {
					res += ((AttributeValueChange) c).getType().getName();
				}
				return res;
			}
		});

		difference.getChanges().clear();
		difference.getChanges().addAll(changes);
	}
}
