package org.sidiff.difference.evaluation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author cpietsch
 *
 */
public class Evaluation {

	private static final String CSV_FIELD_DELIMITER = ";";
	
	private String name;

	private List<TestCase> cases;
	
	private List<Header> headers;
	
	public Evaluation() {
		this.cases = new ArrayList<TestCase>();
		this.headers = new ArrayList<Header>();
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
	
	public List<Header> getHeaders() {
		return headers;
	}
	
	public Header getHeader(String name){
		for(Header header : headers){
			if(header.getName().equals(name)){
				return header;
			}
		}
		return null;
	}

	public String toCSV(){
		String csv = "Test Case";
		//create Header
		for(Header header : headers){
			csv += CSV_FIELD_DELIMITER + header.getName();
			if(!header.getSubHeaders().isEmpty()){
				for(int i = 0 ; i < header.getSubHeaders().size()-1; i++){
					csv += CSV_FIELD_DELIMITER;
				}
			}
		}
		csv += "\n";
		
		for(Header header : headers){
			for(Header subHeader : header.getSubHeaders()){
				csv += CSV_FIELD_DELIMITER + subHeader.getName();
			}
		}
		csv += "\n";
		
		//Test-Cases
		for(TestCase testCase : cases){
			csv += testCase.getName();
			// Entries
			for(AbstractTestCaseEntry entry : testCase.getEntries()){
				csv += CSV_FIELD_DELIMITER + entry.getStats();
			}
			csv += "\n";
		}
		return csv;
	}
}
