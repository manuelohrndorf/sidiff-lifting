package org.sidiff.difference.lifting.api.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.difference.lifting.recognitionengine.RecognitionEngineSetup;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;
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
import org.sidiff.editrule.rulebase.project.runtime.library.IRuleBaseProject;
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
	 * Returns all registered recognition rule sorter matching the given document
	 * type.
	 * 
	 * @param documentTypes
	 *            The document types, i.e. the package namespace URI of the models.
	 * @return all registered {@link IRecognitionRuleSorter} matching the given document
	 *         type
	 */
	public static Set<IRecognitionRuleSorter> getAvailableRecognitionRuleSorters(Set<String> documentTypes) {
		return new HashSet<>(IRecognitionRuleSorter.MANAGER.getExtensions(documentTypes, true));
	}
	
	/**
	 * Returns all registered recognition rule sorter
	 * 
	 * @return all registered {@link IRecognitionRuleSorter}
	 */
	public static Set<IRecognitionRuleSorter> getAllAvailableRecognitionRuleSorters() {
		return new HashSet<>(IRecognitionRuleSorter.MANAGER.getExtensions());
	}
	
	/**
	 * Returns a generic recognition rule sorter.
	 * 
	 * @return a generic {@link IRecognitionRuleSorter}
	 */
	public static IRecognitionRuleSorter getGenericRecognitionRuleSorter(){
		return IRecognitionRuleSorter.MANAGER.getDefaultExtension().orElseThrow();
	}
	
	/**
	 * Returns the default recognition rule sorter for the given document types.
	 * 
	 * @param documentTypes
	 *            The document types, i.e. the package namespace URI of the models.
	 * @return the default {@link IRecognitionRuleSorter} for the given document
	 *         types
	 */
	public static IRecognitionRuleSorter getDefaultRecognitionRuleSorter(Set<String> documentTypes) {
		return IRecognitionRuleSorter.MANAGER.getDefaultExtension(documentTypes).orElseThrow();
	}

	/**
	 * Returns the recognition rule sorter identified by the key.
	 * 
	 * @param key
	 *            The key of the recognition rule sorter
	 * @return the {@link IRecognitionRuleSorter} identified by the key
	 */
	public static IRecognitionRuleSorter getRecognitionRuleSorter(String key) {
		return IRecognitionRuleSorter.MANAGER.getExtension(key).orElse(null);
	}

	/**
	 * Returns all registered lifting rulebases.
	 * 
	 * @return all registered {@link ILiftingRuleBase}s
	 */
	public static Set<ILiftingRuleBase> getAllAvailableRulebases() {
		return IRuleBaseProject.MANAGER.getRuleBases(ILiftingRuleBase.TYPE);
	}

	/**
	 * Returns all registered lifting rulebases matching the given document types.
	 * 
	 * @param documentTypes
	 *            The document types, i.e. the package namespace URIs used by a
	 *            model.
	 * @return all registered {@link ILiftingRuleBase}s matching the given document types
	 */
	public static Set<ILiftingRuleBase> getAvailableRulebases(Set<String> documentTypes) {
		return IRuleBaseProject.MANAGER.getRuleBases(documentTypes, ILiftingRuleBase.TYPE);
	}
	
	/**
	 * Returns the lifting rulebase identified by the key.
	 * 
	 * @param key
	 *            The key of the rulebase
	 * @return the {@link ILiftingRuleBase} identified by the key
	 */
	public static ILiftingRuleBase getRulebase(String key) {
		return IRuleBaseProject.MANAGER.getRuleBase(key, ILiftingRuleBase.class).orElse(null);
	}

	/**
	 * Generates a filename for a new difference between model A and model B.
	 * 
	 * @param modelA
	 *            The {@link Resource} of the earlier version of the model.
	 * @param modelB
	 *            The {@link Resource} of the later version of the model.
	 * @param settings
	 *            Specifies the {@link LiftingSettings} of the semantic lifting algorithm.
	 * @return a filename MODELAxMODELB_MATCHINGENGINE_LIFTING_POSTPROCESSING
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
	 * Generates a filename for a new lifted difference.
	 * 
	 * @param model
	 *            The technical {@link SymmetricDifference} to lift.
	 * @param settings
	 *            Specifies the {@link LiftingSettings} of the semantic lifting algorithm.
	 * @return a filename {old filename}_LIFTING_POSTPROCESSING
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
	
	/**
	 * Converts the lifting settings to the recognition engine setup.
	 * 
	 * @param settings
	 *            The {@link LiftingSettings} of the semantic lifting algorithm.
	 * @return the {@link RecognitionEngineSetup}
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
