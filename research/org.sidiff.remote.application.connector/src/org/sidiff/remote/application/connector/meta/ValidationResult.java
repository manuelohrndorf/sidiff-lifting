package org.sidiff.remote.application.connector.meta;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

	private List<ValidationEntry> entries;
	
	public ValidationResult() {
		entries = new ArrayList<ValidationEntry>();
	}
	
	public List<ValidationEntry> getEntries() {
		return entries;
	};
	
	public boolean isValide() {
		return entries.size() == 0;
	}
}
