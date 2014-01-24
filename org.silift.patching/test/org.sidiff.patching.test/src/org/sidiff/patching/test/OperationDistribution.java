package org.sidiff.patching.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.facade.util.Difference;

public class OperationDistribution {

	private static final String CSV_FIELD_DELIMITER = ";";
	
	private Map<String, Map<String, Integer>> distribution;
	private List<String> allOperationNames;
	
	public OperationDistribution() {
		distribution = new HashMap<String, Map<String,Integer>>();		
		allOperationNames = new LinkedList<String>();
	}


	public void append(String evolScenario, Difference difference){
		distribution.put(evolScenario, new HashMap<String, Integer>());
		
		for (OperationInvocation op : difference.getAsymmetric().getOperationInvocations()) {
			addOperation(evolScenario, op);
		}
	}
	
	public void toCSV(String path) throws IOException{
		StringBuffer buffer = new StringBuffer();
		Collections.sort(allOperationNames);
		
		// Header
		buffer.append("");
		for (String opName : allOperationNames) {
			buffer.append(CSV_FIELD_DELIMITER + opName);
		}
		buffer.append("\n");
		
		// Data
		for (String evolScen : distribution.keySet()) {
			// Start row
			buffer.append(evolScen);
			for (String opName : allOperationNames) {
				if (distribution.get(evolScen).containsKey(opName)){
					buffer.append(CSV_FIELD_DELIMITER + distribution.get(evolScen).get(opName));
				} else {
					buffer.append(CSV_FIELD_DELIMITER + "0");
				}
			}
			buffer.append("\n");
			// End row
		}
		
		// Write
		FileWriter writer = new FileWriter(path);
		writer.write(buffer.toString());
		writer.close();
	}
	
	private void addOperation(String evolScenario, OperationInvocation op){
		String opName = getOperationName(op);
		if (!allOperationNames.contains(opName)){
			allOperationNames.add(opName);
		}
		
		if (!distribution.get(evolScenario).containsKey(opName)){
			distribution.get(evolScenario).put(opName, 0);
		}
		
		int sum = distribution.get(evolScenario).get(opName);
		distribution.get(evolScenario).put(opName, ++sum);
	}
	
	private String getOperationName(OperationInvocation op){
		return op.getEditRule().getExecuteMainUnit().getModule().getName();
	}
}
