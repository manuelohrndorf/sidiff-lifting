package org.sidiff.difference.technical.api;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.MergeImports;
import org.sidiff.difference.technical.api.settings.DifferenceSettings;
import org.sidiff.matching.api.MatchingFacade;
import org.sidiff.matching.input.InputModels;
import org.sidiff.matching.model.Matching;

/**
 * Convenient access to differencing functions.
 */
public class TechnicalDifferenceFacade extends MatchingFacade {

	public static String SYMMETRIC_DIFF_EXT = "symmetric";
	
	protected static MergeImports importMerger = null;
	
	private static int addedObjects = 0;
	private static int addedReferences = 0;
	private static int removedObjects = 0;
	private static int removedReferences = 0;
	private static int attributeValueChanges = 0;
	
	/**
	 * Derives a technical {@link SymmetricDifference} based on a given
	 * {@link Matching} between two models.
	 * 
	 * @param matching
	 *            The matching between the input models.
	 * @param settings
	 *            Specifies the settings of the difference algorithm.
	 * @return A technical {@link SymmetricDifference} between the input models.
	 * 
	 * @see MatchingFacade#match(java.util.Collection, org.sidiff.matching.api.settings.MatchingSettings)
	 */
	public static SymmetricDifference deriveTechnicalDifference(Matching matching, DifferenceSettings settings){
		
		if(!settings.getClass().equals(DifferenceSettings.class)){
			LogUtil.log(LogEvent.NOTICE, "Settings:\n" + settings);
		}
		
		SymmetricDifference symmetricDiff = SymmetricFactory.eINSTANCE.createSymmetricDifference();

		symmetricDiff.setMatching(matching);
		
		importMerger = new MergeImports(symmetricDiff, settings.getScope(), true);
		
		
		if(settings.isEnabled_MergeImports()){
			// Merge Imports
			LogUtil.log(LogEvent.NOTICE, "Merge imports ...");
			importMerger.merge();		
		}

		LogUtil.log(LogEvent.NOTICE, "Derive technical difference ...");

		// Derive technical difference
		ITechnicalDifferenceBuilder tdBuilder = settings.getTechBuilder();
		tdBuilder.deriveTechDiff(symmetricDiff, settings.getScope());
		
		if(settings.isEnabled_MergeImports()){
			// Unmerge Imports
			LogUtil.log(LogEvent.NOTICE, "Unmerge imports ...");
			importMerger.unmerge();
		}
		
		countChanges(symmetricDiff);
		
		LogUtil.log(LogEvent.NOTICE, "Add Object: " + addedObjects);
		LogUtil.log(LogEvent.NOTICE, "Add Object: " + addedObjects);
		LogUtil.log(LogEvent.NOTICE, "Add Reference: " + addedReferences);
		LogUtil.log(LogEvent.NOTICE, "Remove Object: " + removedObjects);
		LogUtil.log(LogEvent.NOTICE, "Remove Reference: " + removedReferences);
		LogUtil.log(LogEvent.NOTICE, "Attribute Value Change: " + attributeValueChanges);
		
		return symmetricDiff;
	}
	
	/**
	 * Derives a technical {@link SymmetricDifference} between two models.
	 * Although, a symmetric difference is usually undirected the underlying
	 * difference model of SiLift implies a direction.
	 * 
	 * @param modelA
	 *            The origin model.
	 * @param modelB
	 *            The modified model.
	 * @param settings
	 *            Specifies the settings of the difference algorithm.
	 * @return A technical {@link SymmetricDifference} between the input models.
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException 
	 */
	public static SymmetricDifference deriveTechnicalDifference(Resource modelA, Resource modelB, DifferenceSettings settings) throws InvalidModelException, NoCorrespondencesException{

		return deriveTechnicalDifference(match(Arrays.asList(modelA, modelB), settings), settings);
	}
	
	/**
	 * Derives a technical {@link SymmetricDifference} between two models.
	 * Although, a symmetric difference is usually undirected the underlying
	 * difference model of SiLift implies a direction.
	 * 
	 * @param models
	 * 			The origin and modified model.
	 * @param settings
	 *            Specifies the settings of the difference algorithm.
	 * @return A technical {@link SymmetricDifference} between the input models.
	 * @throws InvalidModelException
	 * @throws NoCorrespondencesException 
	 */
	public static SymmetricDifference deriveTechnicalDifference(InputModels models, DifferenceSettings settings) throws InvalidModelException, NoCorrespondencesException{
		ArrayList<Resource> resources = new ArrayList<Resource>(models.getResources());
		return deriveTechnicalDifference(resources.get(0), resources.get(1), settings);
	}
	
	/**
	 * Serializes a technical {@link SymmetricDifference}.
	 * 
	 * @param symDiff
	 *            The difference to be serialized.
	 * @param path
	 *            The serialization path.
	 * @param fileName
	 *            The file name of the difference.
	 */
	public static void serializeTechnicalDifference(SymmetricDifference symDiff, String path, String fileName) {
		if (!(path.endsWith("/") || path.endsWith("\\"))) {
			path = path + "/";
		}

		if (!(fileName.endsWith("." + SYMMETRIC_DIFF_EXT))) {
			fileName = fileName + "." + SYMMETRIC_DIFF_EXT;
		}

		EMFStorage.eSaveAs(EMFStorage.pathToUri(path + fileName), symDiff);
	}
	
	/**
	 * Load a technical {@link SymmetricDifference}.
	 * 
	 * @param path
	 *            The path to the symmetric difference.
	 * @return The loaded technical {@link SymmetricDifference}.
	 */
	public static SymmetricDifference loadTechnicalDifference(String path) {
		return (SymmetricDifference) EMFStorage.eLoad(EMFStorage.pathToUri(path));
	}
	
	private static void countChanges(SymmetricDifference diff){
		for(Change c : diff.getChanges()){
			if(c instanceof AddObject){
				addedObjects++;
			}else if(c instanceof AddReference){
				addedReferences++;
			}else if(c instanceof RemoveObject){
				removedObjects++;
			}else if(c instanceof RemoveReference){
				removedReferences++;
			}else if(c instanceof AttributeValueChange){
				attributeValueChanges++;
			}
		}
	}
}
