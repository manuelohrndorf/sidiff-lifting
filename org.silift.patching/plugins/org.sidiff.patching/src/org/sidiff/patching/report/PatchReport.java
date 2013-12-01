package org.sidiff.patching.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sidiff.difference.asymmetric.OperationInvocation;

public class PatchReport {

	public enum Status {
		PASSED, SKIPPED, FAILED, WARNING;
	}
	
	public enum Type {
		PARAMETER("Parameter"), EXECUTION("Execution"), VALIDATION("Validation"), MODIFICATION("Modification");

		private String type;

		Type(String type) {
			this.type = type;
		}

		public String toString() {
			return type;
		}
	}

	private Map<OperationInvocation, Map<Type, List<ReportEntry>>> operationMap;
	
	private HashMap<OperationInvocation, Collection<ReportEntry>> parameterEntries;
	private HashMap<OperationInvocation, ReportEntry> executionEntries;
	private HashMap<OperationInvocation, Collection<ReportEntry>> validationEntries;

	/**
	 * Handles a list of Reports
	 */
	public PatchReport() {
		operationMap = new HashMap<OperationInvocation, Map<Type, List<ReportEntry>>>();
		parameterEntries = new HashMap<OperationInvocation, Collection<ReportEntry>>();
		executionEntries = new HashMap<OperationInvocation, ReportEntry>();
		validationEntries = new HashMap<OperationInvocation, Collection<ReportEntry>>();
	}

	public HashMap<OperationInvocation, Collection<ReportEntry>> getParameterEntries() {
		return parameterEntries;
	}

	public HashMap<OperationInvocation, ReportEntry> getExecutionEntries() {
		return executionEntries;
	}
	
	public HashMap<OperationInvocation, Collection<ReportEntry>> getValidationEntries() {
		return validationEntries;
	}

	/**
	 * Adds a new Report to list
	 * 
	 * @param status
	 * @param type
	 * @param description
	 */
//	public void add(ReportEntry reportEntry) {
//		this.entries.add(reportEntry);
//	}
//	
//	//TEST
//	public void remove(ReportEntry reportEntry){
//		this.entries.remove(reportEntry);
//	}
//
//	public void add(Collection<ReportEntry> reportEntries) {
//		for (ReportEntry reportEntry : reportEntries) {
//			add(reportEntry);
//		}
//	}
//
//	public void add(OperationInvocation operationInvocation, Collection<ReportEntry> reportEntries) {
//		for (ReportEntry reportEntry : reportEntries) {
//			add(operationInvocation, reportEntry);
//		}
//	}
//
//	/**
//	 * Adds a Report to list
//	 * 
//	 * @param operationInvocation
//	 * @param reportEntry
//	 */
//	public void add(OperationInvocation operationInvocation,
//			ReportEntry reportEntry) {
//		add(reportEntry);
//		putIntoOperationMap(operationInvocation, reportEntry);
//	}

//	private void putIntoOperationMap(OperationInvocation operationInvocation, ReportEntry reportEntry) {
//		Map<Type, List<ReportEntry>> operation = operationMap.get(operationInvocation);
//		if (operation == null) {
//			operation = new HashMap<Type, List<ReportEntry>>();
//			operationMap.put(operationInvocation, operation);
//		}
//		List<ReportEntry> list = operation.get(reportEntry.getType());
//		if (list == null) {
//			list = new ArrayList<ReportEntry>();
//			operation.put(reportEntry.getType(), list);
//		}
//		list.add(reportEntry);
//	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		for (ReportEntry report : getEntries()) {
			stringBuffer.append(report + "\n");
		}
		return stringBuffer.toString();
	}

	public List<ReportEntry> getEntries(Type type, Status status){
		List<ReportEntry> list = new ArrayList<ReportEntry>();
		if(getEntries()!=null){
			for(ReportEntry entry : getEntries()){
				if(entry.getType() == type && entry.getStatus() == status){
					list.add(entry);
				}
			}
		}
		return list;
	}
	
	public List<ReportEntry> getEntries() {
		ArrayList <ReportEntry> entries = new ArrayList<ReportEntry>();
		for(OperationInvocation op: parameterEntries.keySet()){
			for(ReportEntry re: parameterEntries.get(op)){
				entries.add(re);
			}
		}
		for(OperationInvocation op: executionEntries.keySet()){
			entries.add(executionEntries.get(op));
		}
		for(OperationInvocation op: validationEntries.keySet()){
			for(ReportEntry re: validationEntries.get(op)){
				entries.add(re);
			}
		}
		return entries;
//		if (entries != null) {
//			return entries;
//		} else {
//			return Collections.emptyList();
//		}
	}

	public List<ReportEntry> getEntries(OperationInvocation operationInvocation, Type type) {
		ArrayList <ReportEntry> entries = new ArrayList<ReportEntry>();
		for(OperationInvocation op: parameterEntries.keySet()){
			if(op.equals(operationInvocation)){
				for(ReportEntry re: parameterEntries.get(op)){
					if(re.getType().equals(type))
						entries.add(re);
				}
			}
		}
		for(OperationInvocation op: executionEntries.keySet()){
			if(op.equals(operationInvocation) && executionEntries.get(op).getType().equals(type))
				entries.add(executionEntries.get(op));
		}
		for(OperationInvocation op: validationEntries.keySet()){
			if(op.equals(operationInvocation)){
				for(ReportEntry re: validationEntries.get(op)){
					if(re.getType().equals(type))
						entries.add(re);
				}
			}
		}
		return entries;
	}
	
	
	public List<ReportEntry> getEntries(OperationInvocation operationInvocation, Type type, Status status) {
		ArrayList <ReportEntry> entries = new ArrayList<ReportEntry>();
		for(OperationInvocation op: parameterEntries.keySet()){
			if(op.equals(operationInvocation)){
				for(ReportEntry re: parameterEntries.get(op)){
					if(re.getType().equals(type) && re.getStatus().equals(status))
						entries.add(re);
				}
			}
		}
		for(OperationInvocation op: executionEntries.keySet()){
			if(op.equals(operationInvocation) && executionEntries.get(op).getType().equals(type) && executionEntries.get(op).getStatus().equals(status))
				entries.add(executionEntries.get(op));
		}
		for(OperationInvocation op: validationEntries.keySet()){
			if(op.equals(operationInvocation)){
				for(ReportEntry re: validationEntries.get(op)){
					if(re.getType().equals(type) && re.getStatus().equals(status))
						entries.add(re);
				}
			}
		}
		return entries;
	}
}
