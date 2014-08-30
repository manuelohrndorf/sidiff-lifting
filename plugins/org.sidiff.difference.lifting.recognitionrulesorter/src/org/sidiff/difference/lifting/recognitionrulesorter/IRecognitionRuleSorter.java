package org.sidiff.difference.lifting.recognitionrulesorter;

import java.util.Comparator;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;

public interface IRecognitionRuleSorter extends Comparator<Node>{
	
	/**
	 * The shared extension point id.
	 */
	public static final String extensionPointID = "org.sidiff.difference.lifting.recognitionrulesorter.recognition_rule_sorter_extension";

	/**
	 * Returns the name of the recognition rule sorter.
	 * 
	 * @return the sorter name
	 */
	public String getName();
	
	/**
	 * Returns the short name (used as a key) of the recognition rule sorter.
	 * 
	 * @return the sorter short name (used as key)
	 */
	public String getKey();
	
	/**
	 * @return 	the document type the recognition rule sorter
	 * 			was implemented for.
	 */
	public String getDocumentType();
	
	/**
	 * @param analysis
	 * 			the corresponding difference analysis knowing the count of
	 *          changes in the difference.
	 */
	public void setDifferenceAnalysis(DifferenceAnalysis analysis);
	
	/**
	 * @return the corresponding difference analysis knowing the count of
	 *         changes in the difference.
	 */
	public DifferenceAnalysis getDifferenceAnalysis();
}
