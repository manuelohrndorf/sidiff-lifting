package org.sidiff.difference.technical.api.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.provider.AddObjectItemProvider;
import org.sidiff.difference.symmetric.provider.AddReferenceItemProvider;
import org.sidiff.difference.symmetric.provider.AttributeValueChangeItemProvider;
import org.sidiff.difference.symmetric.provider.RemoveObjectItemProvider;
import org.sidiff.difference.symmetric.provider.RemoveReferenceItemProvider;
import org.sidiff.difference.symmetric.provider.SymmetricItemProviderAdapterFactory;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.matching.api.util.MatchingUtils;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.matching.model.provider.CorrespondenceItemProvider;

/**
 * Utility functions which are made publicly available to any clients using the
 * SiLift framework. UI components of the SiLift IDE should, if required, also
 * use these utility functions, and not the internal functions of the respective
 * engines.
 * 
 * @author kehrer, mohrndorf, cpietsch
 */
public class TechnicalDifferenceUtils extends MatchingUtils{
	
	/**
	 * Returns all registered technical difference builders matching the given
	 * document types.
	 * 
	 * @param documentTypes
	 *            The document types, i.e. the package namespace URI of a model. There can be more than one.
	 * @return all registered {@link ITechnicalDifferenceBuilder}s matching the given
	 *         document types.
	 * @see #getAvailableTechnicalDifferenceBuilders(String)
	 */
	public static List<ITechnicalDifferenceBuilder> getAvailableTechnicalDifferenceBuilders(Set<String> documentTypes) {
		return new ArrayList<>(ITechnicalDifferenceBuilder.MANAGER.getExtensions(documentTypes, false));
	}
	
	/**
	 * Returns all registered technical difference builders matching the document types of the given models.
	 * 
	 * @param documentTypes
	 *            The document types, i.e. the package namespace URI of a model. There can be more than one.
	 * @return all registered {@link ITechnicalDifferenceBuilder}s matching the given
	 *         document types.
	 * @see #getAvailableTechnicalDifferenceBuilders(String)
	 */
	public static List<ITechnicalDifferenceBuilder> getAvailableTechnicalDifferenceBuilders(Resource modelA, Resource modelB) {
		return ITechnicalDifferenceBuilder.MANAGER.getTechnicalDifferenceBuilders(modelA, modelB);
	}
	
	/**
	 * Returns all registered technical difference builders.
	 * 
	 * @return all registered {@link ITechnicalDifferenceBuilder}s.
	 */
	public static List<ITechnicalDifferenceBuilder> getAllAvailableTechnicalDifferenceBuilders() {
		return new ArrayList<>(ITechnicalDifferenceBuilder.MANAGER.getExtensions());
	}
	
	/**
	 * Returns a generic technical difference builder.
	 * 
	 * @return a generic {@link ITechnicalDifferenceBuilder}
	 */
	public static ITechnicalDifferenceBuilder getGenericTechnicalDifferenceBuilder() {
		return ITechnicalDifferenceBuilder.MANAGER.getDefaultExtension().get();
	}

	/**
	 * 
	 * Returns the default technical difference builder for the given documentTypes:
	 * <br/>
	 * In case of Ecore: take first non-generic difference builder. <br/>
	 * Otherwise: take first technical difference builder
	 * 
	 * @param documentTypes
	 *            The document types, i.e. the package namespace URI of a model.
	 *            There can be more than one.
	 * @return the default {@link ITechnicalDifferenceBuilder} for the given
	 *         documentTypes
	 */
	public static ITechnicalDifferenceBuilder getDefaultTechnicalDifferenceBuilder(Set<String> documentTypes) {
		return ITechnicalDifferenceBuilder.MANAGER.getDefaultExtension(documentTypes).get();
	}

	/**
	 * Returns the technical difference builder with the given key.
	 * 
	 * @param key
	 *            The key of the technical difference builder
	 * @return the {@link ITechnicalDifferenceBuilder} identified by the key
	 */
	public static ITechnicalDifferenceBuilder getTechnicalDifferenceBuilder(String key){
		return ITechnicalDifferenceBuilder.MANAGER.getExtension(key).orElse(null);
	}
	
	public static String extractCommonPath(String... paths) {
		String result = null;
		for (String path : paths) {
			File file = new File(path);
			assert (file.isFile()) : "Not a File!" + path;

			if (result == null) {
				result = file.getParent();
			}

			assert (result.equals(file.getParent())) : "Different Paths! " + result + " vs. " + file.getParent();
		}
		return result + "/";
	}

	/**
	 * Generates a filename for a new difference between model A and model B.
	 * 
	 * @param modelA
	 *            The {@link Resource} of the earlier version of the model.
	 * @param modelB
	 *            The {@link Resource} of the later version of the model.
	 * @param settings
	 *            Specifies the {@link DifferenceSettings} of the differencing algorithm.
	 * @return a filename MODELAxMODELB_MATCHINGENGINE_technical
	 */
	public static String generateDifferenceFileName(Resource modelA, Resource modelB, DifferenceSettings settings) {
		String fileName = modelA.getURI().trimFileExtension().lastSegment() + "_x_"
				+ modelB.getURI().trimFileExtension().lastSegment();

		if (settings.getMatcher() != null) {
			fileName += "_" + settings.getMatcher().getKey();
		}

		fileName += "_technical";

		return fileName;
	}

	/**
	 * Sorts the difference correspondences and changes by their label provider
	 * text.
	 * 
	 * @param difference
	 *            The {@link SymmetricDifference} to sort.
	 */
	public static void sortDifference(final SymmetricDifference difference) {

		// Sort Correspondences:
		Comparator<Correspondence> correspondenceSorter = new Comparator<Correspondence>() {
			SymmetricItemProviderAdapterFactory adapterFactory = new SymmetricItemProviderAdapterFactory();
		
			CorrespondenceItemProvider correspondenceItemProvider = new CorrespondenceItemProvider(adapterFactory);

			Map<Correspondence, String> texts = new HashMap<Correspondence, String>();
			{
				for (Correspondence c : difference.getMatching().getCorrespondences()) {
					texts.put(c, correspondenceItemProvider.getText(c));
				}
			}
			
			@Override
			public int compare(Correspondence o1, Correspondence o2) {
				return texts.get(o1).compareToIgnoreCase(texts.get(o2));
			}
		};

		ECollections.sort(difference.getMatching().getCorrespondences(), correspondenceSorter);

		// Sort changes:
		Comparator<Change> changeSorter = new Comparator<Change>() {

			SymmetricItemProviderAdapterFactory adapterFactory = new SymmetricItemProviderAdapterFactory();

			AddObjectItemProvider addObjectItemProvider = new AddObjectItemProvider(adapterFactory);
			RemoveObjectItemProvider removeObjectItemProvider = new RemoveObjectItemProvider(adapterFactory);

			AddReferenceItemProvider addReferenceItemProvider = new AddReferenceItemProvider(adapterFactory);;
			RemoveReferenceItemProvider removeReferenceItemProvider = new RemoveReferenceItemProvider(adapterFactory);

			AttributeValueChangeItemProvider attributeValueChangeItemProvider = new AttributeValueChangeItemProvider(
					adapterFactory);

			@Override
			public int compare(Change o1, Change o2) {
				String s1 = "";
				String s2 = "";

				if (o1 instanceof AddObject) {
					s1 = addObjectItemProvider.getText(o1);
				} else if (o1 instanceof RemoveObject) {
					s1 = removeObjectItemProvider.getText(o1);
				} else if (o1 instanceof AddReference) {
					s1 = addReferenceItemProvider.getText(o1);
				} else if (o1 instanceof RemoveReference) {
					s1 = removeReferenceItemProvider.getText(o1);
				} else if (o1 instanceof AttributeValueChange) {
					s1 = attributeValueChangeItemProvider.getText(o1);
				}

				if (o2 instanceof AddObject) {
					s2 = addObjectItemProvider.getText(o2);
				} else if (o2 instanceof RemoveObject) {
					s2 = removeObjectItemProvider.getText(o2);
				} else if (o2 instanceof AddReference) {
					s2 = addReferenceItemProvider.getText(o2);
				} else if (o2 instanceof RemoveReference) {
					s2 = removeReferenceItemProvider.getText(o2);
				} else if (o2 instanceof AttributeValueChange) {
					s2 = attributeValueChangeItemProvider.getText(o2);
				}

				return s1.compareToIgnoreCase(s2);
			}
		};

		for (SemanticChangeSet changeSet : difference.getChangeSets()) {
			ECollections.sort(changeSet.getChanges(), changeSorter);
		}

		for (SemanticChangeSet changeSet : difference.getUnusedChangeSets()) {
			ECollections.sort(changeSet.getChanges(), changeSorter);
		}
	}
}
