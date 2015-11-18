package org.sidiff.evaluation.silift.lifting;

/**
 * 
 * @author cpietsch
 *
 */
public class ConcreteTestCaseEntry extends AbstractTestCaseEntry {

	private Number stats;
	
	public ConcreteTestCaseEntry(Header header, Number stats){
		super(header);
		this.stats = stats;
	}
	
	public Number getStats() {
		return stats;
	}

	public void setStats(int stats) {
		this.stats = stats;
	}
}
