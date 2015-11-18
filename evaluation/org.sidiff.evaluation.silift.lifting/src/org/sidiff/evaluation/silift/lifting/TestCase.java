package org.sidiff.evaluation.silift.lifting;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.symmetric.SymmetricDifference;

/**
 * 
 * @author cpietsch
 *
 */
public class TestCase {

	private static final String CSV_FIELD_DELIMITER = ";";
	
	private List<Difference> difference;
	private List<AbstractTestCaseEntry> entries;
	
	public TestCase(List<Difference> difference){
		this.difference = difference;
		this.entries = new ArrayList<AbstractTestCaseEntry>();
	}
	
	public String getName() {
		
		SymmetricDifference symmetricDifference = difference.get(0).getSymmetric();
		
		Resource modelA = symmetricDifference.getModelA();
		Resource modelB = symmetricDifference.getModelB();
		
		return modelA.getURI().lastSegment() + "->" + modelB.getURI().lastSegment();
		
	}

	public List<AbstractTestCaseEntry> getEntries() {
		return entries;
	}
	
	public String toCSV(){
		String csv = "";
		csv += getName() + CSV_FIELD_DELIMITER;
		return null;
	}
	
}
