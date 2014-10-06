package org.sidiff.difference.evaluation.engine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.evaluation.AbstractTestCaseEntry;
import org.sidiff.difference.evaluation.Evaluation;
import org.sidiff.difference.evaluation.Header;
import org.sidiff.difference.evaluation.TestCase;

/**
 * 
 * @author cpietsch
 *
 */
public abstract class AbstractEvaluationEngine {

	public static final String extensionPointID = "org.sidiff.difference.evaluation.difference_evaluation";
	
	private Evaluation evaluation;
	
	private List<List<Difference>> differences;
	
	public AbstractEvaluationEngine(){
		evaluation = new Evaluation();
	}
	
	public void evaluate(){
		
		evaluation.getHeaders().addAll(createHeaders());
		for(List<Difference> testCaseDifferences : differences){
			TestCase testCase = new TestCase(testCaseDifferences);
			testCase.getEntries().addAll(createTestCaseEntry(testCaseDifferences));
			evaluation.getCases().add(testCase);
		}
	}
	
	public abstract List<Header> createHeaders();
	
	public abstract List<AbstractTestCaseEntry> createTestCaseEntry(List<Difference> differences);
	
	public abstract String getKey();
	
	public void createCSV(String path) throws IOException{
		StringBuffer buffer = new StringBuffer();

		// Header
		buffer.append("");
		buffer.append(getEvaluation().toCSV());

		// Write
		FileWriter writer = new FileWriter(path);
		writer.write(buffer.toString());
		writer.close();
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public List<List<Difference>> getDifferences() {
		return differences;
	}

	public void setDifferences(List<List<Difference>> differences) {
		this.differences = differences;
	}
}
