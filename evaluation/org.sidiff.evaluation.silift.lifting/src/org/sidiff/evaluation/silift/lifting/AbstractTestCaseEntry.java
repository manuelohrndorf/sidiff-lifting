package org.sidiff.evaluation.silift.lifting;

/**
 * 
 * @author cpietsch
 *
 */
public abstract class AbstractTestCaseEntry {

	private Header header;
	
	public AbstractTestCaseEntry(Header header) {
		this.header = header;
	}
	
	public Header getHeader(){
		return header;
	}
	
	public abstract Number getStats();
	
}
