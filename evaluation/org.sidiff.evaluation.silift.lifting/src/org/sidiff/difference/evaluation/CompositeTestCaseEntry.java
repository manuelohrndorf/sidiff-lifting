package org.sidiff.difference.evaluation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author cpietsch
 *
 */
public class CompositeTestCaseEntry extends AbstractTestCaseEntry {

	private List<AbstractTestCaseEntry> entries;

	public CompositeTestCaseEntry(Header header) {
		super(header);
		this.entries = new ArrayList<AbstractTestCaseEntry>();
	}
	
	public List<AbstractTestCaseEntry> getEntries() {
		return entries;
	}
	
	public Number getStats(){
		Number stats = 0;
		for(AbstractTestCaseEntry entry : entries){
			if(entry.getStats() instanceof Integer){
				stats = stats.intValue() + entry.getStats().intValue();
			}else{
				stats = stats.doubleValue() + entry.getStats().doubleValue();
			}
		}
		return stats;
	}
}
