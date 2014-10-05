package org.sidiff.difference.evaluation;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author cpietsch
 *
 */
public class CompositeTestCaseEntry extends AbstractTestCaseEntry {

	private static final String CSV_FIELD_DELIMITER = ";";
	
	private List<AbstractTestCaseEntry> entries;

	public CompositeTestCaseEntry(String header) {
		super(header);
		this.entries = new ArrayList<AbstractTestCaseEntry>();
	}
	
	public List<AbstractTestCaseEntry> getEntries() {
		return entries;
	}
}
