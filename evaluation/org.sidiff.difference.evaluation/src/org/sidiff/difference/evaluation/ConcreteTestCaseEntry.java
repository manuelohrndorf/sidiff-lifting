package org.sidiff.difference.evaluation;

/**
 * 
 * @author cpietsch
 *
 */
public class ConcreteTestCaseEntry extends AbstractTestCaseEntry {

	private int stats;
	
	public ConcreteTestCaseEntry(Header header, int stats){
		super(header);
		this.stats = stats;
	}
	
	public int getStats() {
		return stats;
	}

	public void setStats(int stats) {
		this.stats = stats;
	}
}
