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
	
	public int getStats(){
		int stats = 0;
		for(AbstractTestCaseEntry entry : entries){
			stats += entry.getStats();
		}
		return stats;
	}
}
