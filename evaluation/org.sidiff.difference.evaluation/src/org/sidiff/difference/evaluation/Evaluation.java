package org.sidiff.difference.evaluation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author cpietsch
 *
 */
public class Evaluation {

	private static final String CSV_FIELD_DELIMITER = ";";
	
	private String name;

	private List<TestCase> cases;
	
	public Evaluation() {
		this.cases = new ArrayList<TestCase>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TestCase> getCases() {
		return cases;
	}
	
	public String toCSV(){
		String csv = "Test Case";
		List<CompositeTestCaseEntry> compositeTestCaseEntries = new ArrayList<CompositeTestCaseEntry>();
		//create header
		for(TestCase testCase : getCases()){
			for(AbstractTestCaseEntry entry : testCase.getEntries()){
				if(entry instanceof CompositeTestCaseEntry){
					compositeTestCaseEntries.add((CompositeTestCaseEntry)entry);
					csv += CSV_FIELD_DELIMITER + entry.getHeader();
				}
			}
			csv += "\n";
			for(CompositeTestCaseEntry outerEntry : compositeTestCaseEntries){
				Set<String> testCaseHeaders = new HashSet<String>();
				for(AbstractTestCaseEntry innerEntry : outerEntry.getEntries()){
					if(innerEntry instanceof ConcreteTestCaseEntry){
						ConcreteTestCaseEntry entry = (ConcreteTestCaseEntry)innerEntry;
						testCaseHeaders.add(entry.getHeader());
					}
				}
				for(String header : testCaseHeaders){
					csv += CSV_FIELD_DELIMITER + header;
				}
			}
			csv +="\n";
		}
		return csv;
	}
}
