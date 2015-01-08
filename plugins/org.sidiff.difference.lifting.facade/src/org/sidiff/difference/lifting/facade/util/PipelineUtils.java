package org.sidiff.difference.lifting.facade.util;

import java.io.File;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.lifting.settings.LiftingSettings.RecognitionEngineMode;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.matcher.util.MatcherUtil;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.rulebase.util.RuleBaseUtil;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.provider.AddObjectItemProvider;
import org.sidiff.difference.symmetric.provider.AddReferenceItemProvider;
import org.sidiff.difference.symmetric.provider.AttributeValueChangeItemProvider;
import org.sidiff.difference.symmetric.provider.CorrespondenceItemProvider;
import org.sidiff.difference.symmetric.provider.RemoveObjectItemProvider;
import org.sidiff.difference.symmetric.provider.RemoveReferenceItemProvider;
import org.sidiff.difference.symmetric.provider.SymmetricItemProviderAdapterFactory;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.util.TechnicalDifferenceBuilderUtil;
import org.silift.common.util.emf.EMFStorage;

/**
 * Utility functions which are made publicly available to any clients using the
 * SiLift framework. UI components of the SiLift IDE should, if required, also
 * use these utility functions, and not the internal functions of the respective
 * engines.
 * 
 * @author kehrer, mohrndorf
 */
public class PipelineUtils {

	/**
	 * Load EMF resource.
	 * 
	 * @param path
	 *            The EMF-file path.
	 * @return The loaded EMF-object.
	 */
	public static Resource loadModel(String path) {
		return EMFStorage.eLoad(EMFStorage.pathToUri(path)).eResource();
	}

	/**
	 * Find all available technical difference builders matching the given
	 * document type.
	 * 
	 * @param documentType
	 *            The document type, i.e. the package namespace URI of a model.
	 * @return All available technical difference builders matching the given
	 *         document type.
	 * @see LiftingFacade#getDocumentType(Resource)
	 */
	public static Set<ITechnicalDifferenceBuilder> getAvailableTechnicalDifferenceBuilders(String documentType) {
		return TechnicalDifferenceBuilderUtil.getAvailableTechnicalDifferenceBuilder(documentType);
	}
	
	/**
	 * Find all available technical difference builders matching the given
	 * document types.
	 * 
	 * @param documentTypes
	 *            The document types, i.e. the package namespace URI of a model. There can be more than one.
	 * @return All available technical difference builders matching the given
	 *         document types.
	 * @see #getAvailableTechnicalDifferenceBuilders(String)
	 */
	public static Set<ITechnicalDifferenceBuilder> getAvailableTechnicalDifferenceBuilders(Set<String> documentTypes) {
		Set<ITechnicalDifferenceBuilder> builders = new HashSet<ITechnicalDifferenceBuilder>();
		for(String documentType : documentTypes){
			builders.addAll(getAvailableTechnicalDifferenceBuilders(documentType));
		}
		return builders;
	}

	/**
	 * 
	 * Returns the default technical difference builder for the given
	 * documentType: <br/>
	 * In case of Ecore: take first non-generics diff builder. <br/>
	 * Otherwise: take first technical difference builder
	 * 
	 * @param documentType
	 * @return
	 */
	public static ITechnicalDifferenceBuilder getDefaultTechnicalDifferenceBuilder(String documentType) {
		return TechnicalDifferenceBuilderUtil.getDefaultTechnicalDifferenceBuilder(documentType);
	}

	/**
	 * Find all available recognition rule sorter matching the given document
	 * type.
	 * 
	 * @param documentType
	 *            The document type, i.e. the package namespace URI of a model.
	 * @return All available recognition rule sorter matching the given document
	 *         type.
	 * @see LiftingFacade#getDocumentType(Resource)
	 */
	public static Set<IRecognitionRuleSorter> getAvailableRecognitionRuleSorters(String documentType) {
		return RecognitionRuleSorterUtil.getAvailableRecognitionRuleSorters(documentType);
	}

	/**
	 * 
	 * Returns the default recognition rule sorter for the given documentType: <br/>
	 * 
	 * @param documentType
	 * @return
	 */
	public static IRecognitionRuleSorter getDefaultRecognitionRuleSorter(String documentType) {
		return RecognitionRuleSorterUtil.getDefaultRecognitionRuleSorter(documentType);
	}

	/**
	 * Find all available rulebases matching the given document type.
	 * 
	 * @param documentType
	 *            The document type, i.e. the package namespace URI of a model.
	 * @return All available rulebases matching the given document type.
	 */
	public static Set<IRuleBase> getAvailableRulebases(String documentType) {
		return RuleBaseUtil.getAvailableRulebases(documentType);
	}

	/**
	 * Find all available rulebases matching the given document types.
	 * 
	 * @param documentTypes
	 *            The document types, i.e. the package namespace URIs used by a
	 *            model.
	 * @return All available rulebases matching the given document types.
	 */
	public static Set<IRuleBase> getAvailableRulebases(Set<String> documentTypes) {
		return RuleBaseUtil.getAvailableRulebases(documentTypes);
	}

	/**
	 * Find all available matchers matching the given document type.
	 * 
	 * @param modelA
	 *            Model A of the comparison.
	 * @param modelB
	 *            Model B of the comparison.
	 * @return All available rulebases matching the given document type.
	 * @see LiftingFacade#getDocumentType(Resource)
	 */
	public static Set<IMatcher> getAvailableMatchers(Resource modelA, Resource modelB) {
		return MatcherUtil.getAvailableMatchers(modelA, modelB);
	}

	/**
	 * Get matcher by its key name.
	 * 
	 * @param key
	 *            The key name of the matcher.
	 * @param modelA
	 *            Model A of the comparison.
	 * @param modelB
	 *            Model B of the comparison.
	 * @return The matcher with the key name; null otherwise.
	 * @see IMatcher#getKey()
	 */
	public static IMatcher getMatcherByKey(String key, Resource modelA, Resource modelB) {
		return MatcherUtil.getMatcherByKey(key, modelA, modelB);
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
	 * Generates a file name for a new difference between model A and model B.
	 * 
	 * @param modelA
	 *            The earlier version of the model.
	 * @param modelB
	 *            The later version of the model.
	 * @param settings
	 *            Specifies the settings of the semantic lifting algorithm.
	 * @return A file name MODELAxMODELB_MATCHINGENGINE_LIFTING_POSTPROCESSING.
	 */
	public static String generateDifferenceFileName(Resource modelA, Resource modelB, LiftingSettings settings) {
		String fileName = extractModelName(EMFStorage.uriToPath(modelA.getURI())) + "_x_"
				+ extractModelName(EMFStorage.uriToPath(modelB.getURI()));

		if (settings.getMatcher() != null) {
			fileName += "_" + settings.getMatcher().getKey();
		}

		if (settings.getRecognitionEngineMode() != RecognitionEngineMode.NO_LIFTING) {
			fileName += "_lifted";
		} else {
			fileName += "_technical";
		}

		if (settings.getRecognitionEngineMode() == RecognitionEngineMode.LIFTING_AND_POST_PROCESSING) {
			fileName += "_post-processed";
		}

		return fileName;
	}

	/**
	 * Generates a file name for a new lifted difference.
	 * 
	 * @param model
	 *            The technical difference to lift.
	 * @param settings
	 *            Specifies the settings of the semantic lifting algorithm.
	 * @return A file name {old filename}_LIFTING_POSTPROCESSING.
	 */
	public static String generateDifferenceFileName(Resource model, LiftingSettings settings) {
		String fileName = extractModelName(EMFStorage.uriToPath(model.getURI()));

		fileName = fileName.replace("_technical", "_lifted");

		if (settings.getRecognitionEngineMode() == RecognitionEngineMode.LIFTING_AND_POST_PROCESSING) {
			fileName += "_post-processed";
		}

		return fileName;
	}

	/**
	 * Cut of the file extension.
	 * 
	 * @param filename
	 *            The file name with extension.
	 * @return The file name without extension.
	 */
	private static String extractModelName(String filename) {
		String fName = new File(filename).getName();
		return fName.substring(0, fName.lastIndexOf('.'));
	}

	/**
	 * Cleanup, i.e. remove adapters from all the eObjects involved in
	 * differencing in order to prevent memory leaks.
	 * 
	 * @param difference
	 * @param imports
	 */
	protected static void cleanup(SymmetricDifference difference, Set<EObject> imports) {
		// Release adapters for difference
		for (Iterator<EObject> iterator = difference.eAllContents(); iterator.hasNext();) {
			EObject object = iterator.next();
			releaseAdapters(object);
			if (object instanceof AddReference) {
				releaseAdapters(((AddReference) object).getType());
			}
			if (object instanceof RemoveReference) {
				releaseAdapters(((RemoveReference) object).getType());
			}
			if (object instanceof AttributeValueChange) {
				releaseAdapters(((AttributeValueChange) object).getType());
			}
		}

		// Release adapters for model A
		for (Resource r : difference.getModelA().getResourceSet().getResources()) {
			for (Iterator<EObject> iterator = r.getAllContents(); iterator.hasNext();) {
				releaseAdapters(iterator.next());
			}
		}

		// Release adapters for model B
		for (Resource r : difference.getModelB().getResourceSet().getResources()) {
			for (Iterator<EObject> iterator = r.getAllContents(); iterator.hasNext();) {
				releaseAdapters(iterator.next());
			}
		}

		// Release adapters for imported eObjects
		if (imports != null) {
			for (EObject eObject : imports) {
				releaseAdapters(eObject);
			}
		}
	}

	/**
	 * Sorts the difference correspondences and changes by their label provider
	 * text.
	 * 
	 * @param difference
	 *            The difference to sort.
	 */
	public static void sortDifference(SymmetricDifference difference) {

		// Sort Correspondences:
		Comparator<Correspondence> correspondenceSorter = new Comparator<Correspondence>() {
			SymmetricItemProviderAdapterFactory adapterFactory = new SymmetricItemProviderAdapterFactory();

			CorrespondenceItemProvider correspondenceItemProvider = new CorrespondenceItemProvider(adapterFactory);

			@Override
			public int compare(Correspondence o1, Correspondence o2) {
				String s1 = correspondenceItemProvider.getText(o1);
				String s2 = correspondenceItemProvider.getText(o2);

				return s1.compareToIgnoreCase(s2);
			}
		};

		ECollections.sort(difference.getCorrespondences(), correspondenceSorter);

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

	/**
	 * private helper method for cleanup.
	 * 
	 * @param eObject
	 */
	private static void releaseAdapters(EObject eObject) {
		if (eObject != null) {
			EList<Adapter> adapters = eObject.eAdapters();

			if ((adapters != null) && (!adapters.isEmpty())) {
				adapters.clear();
			}
		}
	}
}
