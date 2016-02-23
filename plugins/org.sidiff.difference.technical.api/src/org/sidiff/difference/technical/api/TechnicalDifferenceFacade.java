package org.sidiff.difference.technical.api;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ui.console.MessageConsoleStream;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.EMFStorage;
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

public class TechnicalDifferenceFacade extends MatchingFacade {

	public static String SYMMETRIC_DIFF_EXT = "symmetric";
	
	protected static MergeImports importMerger = null;
	
	private static int addedObjects = 0;
	private static int addedReferences = 0;
	private static int removedObjects = 0;
	private static int removedReferences = 0;
	private static int attributeValueChanges = 0;
	
	public static SymmetricDifference deriveDifference(Matching matching, DifferenceSettings settings){
		
		MessageConsoleStream mcStream = console.newMessageStream();
		mcStream.println("########## Difference Derivator ##########");
		
		SymmetricDifference symmetricDiff = SymmetricFactory.eINSTANCE.createSymmetricDifference();

		symmetricDiff.setMatching(matching);
		
		importMerger = new MergeImports(symmetricDiff, settings.getScope(), true);
		
		
		if(settings.isEnabled_MergeImports()){
			// Merge Imports
			mcStream.println("------------------------------");
			mcStream.print("Merge imports");
			mcStream.println("------------------------------");
			status(true);
			importMerger.merge();
			status(false);
			mcStream.println("[finished]");
			mcStream.println("------------------------------");			
		}
		mcStream.println("------------------------------");
		mcStream.print("Derive technical difference");
		mcStream.println("------------------------------");
		status(true);
		// Derive technical difference
		ITechnicalDifferenceBuilder tdBuilder = settings.getTechBuilder();
		tdBuilder.deriveTechDiff(symmetricDiff, settings.getScope());
		status(false);
		mcStream.println("[finished]");
		mcStream.println("------------------------------");	
		
		if(settings.isEnabled_MergeImports()){
			// Unmerge Imports
			mcStream.println("------------------------------");
			mcStream.print("Unmerge imports");
			mcStream.println("------------------------------");
			status(true);
			importMerger.unmerge();
			status(false);
			mcStream.println("[finished]");
			mcStream.println("------------------------------");	
		}
		
		countChanges(symmetricDiff);
		
		mcStream.println("Add Object: " + addedObjects);
		mcStream.println("Add Reference: " + addedReferences);
		mcStream.println("Remove Object: " + removedObjects);
		mcStream.println("Remove Reference: " + removedReferences);
		mcStream.println("Attribute Value Change: " + attributeValueChanges);
		
		return symmetricDiff;
	}
	
	public static SymmetricDifference deriveDifference(Resource modelA, Resource modelB, DifferenceSettings settings) throws InvalidModelException{
		
		try {
			return deriveDifference(match(Arrays.asList(modelA, modelB), settings), settings);
		} catch (NoCorrespondencesException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}
	
	public static SymmetricDifference deriveDifference(InputModels models, DifferenceSettings settings) throws InvalidModelException{
		ArrayList<Resource> resources = new ArrayList<Resource>(models.getResources());
		return deriveDifference(resources.get(0), resources.get(1), settings);
	}
	
	/**
	 * Serializes a difference.
	 * 
	 * @param symDiff
	 *            The difference to serialize.
	 * @param path
	 *            The serialization path.
	 * @param fileName
	 *            The file name of the difference.
	 */
	public static void serializeDifference(SymmetricDifference symDiff, String path, String fileName) {
		if (!(path.endsWith("/") || path.endsWith("\\"))) {
			path = path + "/";
		}

		if (!(fileName.endsWith("." + SYMMETRIC_DIFF_EXT))) {
			fileName = fileName + "." + SYMMETRIC_DIFF_EXT;
		}

		EMFStorage.eSaveAs(EMFStorage.pathToUri(path + fileName), symDiff);
	}
	
	/**
	 * Load symmetric difference.
	 * 
	 * @param path
	 *            The path to the symmetric difference.
	 * @return The loaded symmetric difference.
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
