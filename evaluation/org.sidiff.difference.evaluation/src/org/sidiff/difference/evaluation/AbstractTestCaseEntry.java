package org.sidiff.difference.evaluation;

/**
 * 
 * @author cpietsch
 *
 */
public abstract class AbstractTestCaseEntry {

	private String header;
	
	public AbstractTestCaseEntry(String header) {
		this.header = header;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}	
}
