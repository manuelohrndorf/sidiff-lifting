package org.sidiff.difference.lifting.api.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.difference.lifting.recognitionengine.RecognitionEngineSetup;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterLibrary;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
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
import org.sidiff.difference.technical.api.util.TechnicalDifferenceUtils;
import org.sidiff.editrule.rulebase.project.runtime.library.RuleBaseProjectLibrary;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.matching.model.provider.CorrespondenceItemProvider;

/**
 * Utility functions which are made publicly available to any clients using the
 * SiLift framework. UI components of the SiLift IDE should, if required, also
 * use these utility functions, and not the internal functions of the respective
 * engines.
 * 
 * @author kehrer, mohrndorf
 */
public class PipelineUtils extends TechnicalDifferenceUtils {


	/**
	 * Find all available recognition rule sorter matching the given document
	 * type.
	 * 
	 * @param documentTypes
	 *            The document types, i.e. the package namespace URIs of a model.
	 * @return All available recognition rule sorter matching the given document
	 *         type.
	 * @see LiftingFacade#getDocumentType(Resource)
	 */
	public static Set<IRecognitionRuleSorter> getAvailableRecognitionRuleSorters(Set<String> documentTypes) {
		return RecognitionRuleSorterLibrary.getAvailableRecognitionRuleSorters(documentTypes);
	}
	
	/**
	 * Find all available recognition rule sorter
	 * 
	 * @return All available recognition rule sorter
	 * @see LiftingFacade#getDocumentType()
	 */
	public static Set<IRecognitionRuleSorter> getAllAvailableRecognitionRuleSorters() {
		return RecognitionRuleSorterLibrary.getAllAvailableRecognitionRuleSorters();
	}
	
	/**
	 * 
	 * @return
	 */
	public static IRecognitionRuleSorter getGenericRecognitionRuleSorter(){
		return RecognitionRuleSorterLibrary.getGenericRecognitionRuleSorter();
	}
	/**
	 * 
	 * Returns the default recognition rule sorter for the given documentType: <br/>
	 * 
	 * @param documentTypes
	 * @return
	 */
	public static IRecognitionRuleSorter getDefaultRecognitionRuleSorter(Set<String> documentTypes) {
		return RecognitionRuleSorterLibrary.getDefaultRecognitionRuleSorter(documentTypes);
	}

	public static IRecognitionRuleSorter getRecognitionRuleSorter(String key) {
		return RecognitionRuleSorterLibrary.getRecognitionRuleSorter(key);
	}

	/**
	 * Find all available rulebases matching the given document type.
	 * 
	 * @param documentType
	 *            The document type, i.e. the package namespace URI of a model.
	 * @return All available rulebases matching the given document type.
	 */
	public static Set<ILiftingRuleBase> getAvailableRulebases(String documentType) {
		return RuleBaseProjectLibrary.getRuleBases(Collections.singleton(documentType), ILiftingRuleBase.TYPE);
	}

	/**
	 * Find all available rulebases.
	 * 
	 * @return All available rulebases.
	 */
	public static Set<ILiftingRuleBase> getAllAvailableRulebases() {
		return RuleBaseProjectLibrary.getRuleBases(ILiftingRuleBase.TYPE);
	}

	/**
	 * Find all available rulebases matching the given document types.
	 * 
	 * @param documentTypes
	 *            The document types, i.e. the package namespace URIs used by a
	 *            model.
	 * @return All available rulebases matching the given document types.
	 */
	public static Set<ILiftingRuleBase> getAvailableRulebases(Set<String> documentTypes) {
		return RuleBaseProjectLibrary.getRuleBases(documentTypes, ILiftingRuleBase.TYPE);
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
		String fileName = TechnicalDifferenceUtils.generateDifferenceFileName(modelA, modelB, settings);

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
	 * Sorts the difference correspondences and changes by their label provider
	 * text.
	 * 
	 * @param difference
	 *            The difference to sort.
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
	
	/**
	 * Converts the lifting settings to the recognition engine setup.
	 * 
	 * @param settings
	 *            The lifting settings.
	 * @return The recognition engine setup
	 */
	public static RecognitionEngineSetup createRecognitionEngineSetup(LiftingSettings settings) {
		return new RecognitionEngineSetup(
				settings.getScope(), settings.getRuleBases(), 
				settings.isRuleSetReduction(), settings.isBuildGraphPerRule(), true,
				settings.getRrSorter(), settings.isUseThreadPool(),
				settings.getNumberOfThreads(), settings.getRulesPerThread(), 
				settings.isCalculateEditRuleMatch(), settings.isSerializeEditRuleMatch());
	}
}
