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

	private List<ReportEntry> entries;
	private Map<OperationInvocation, Map<Type, List<ReportEntry>>> operationMap;

	/**
	 * Handles a list of Reports
	 */
	public PatchReport() {
		entries = new ArrayList<ReportEntry>();
		operationMap = new HashMap<OperationInvocation, Map<Type, List<ReportEntry>>>();
	}

	/**
	 * Adds a new Report to list
	 * 
	 * @param status
	 * @param type
	 * @param description
	 */
	public void add(ReportEntry reportEntry) {
		this.entries.add(reportEntry);
	}
	
	//TEST
	public void remove(ReportEntry reportEntry){
		this.entries.remove(reportEntry);
	}

	public void add(Collection<ReportEntry> reportEntries) {
		for (ReportEntry reportEntry : reportEntries) {
			add(reportEntry);
		}
	}

	public void add(OperationInvocation operationInvocation, Collection<ReportEntry> reportEntries) {
		for (ReportEntry reportEntry : reportEntries) {
			add(operationInvocation, reportEntry);
		}
	}

	/**
	 * Adds a Report to list
	 * 
	 * @param operationInvocation
	 * @param reportEntry
	 */
	public void add(OperationInvocation operationInvocation, ReportEntry reportEntry) {
		add(reportEntry);
		putIntoOperationMap(operationInvocation, reportEntry);
	}

	private void putIntoOperationMap(OperationInvocation operationInvocation, ReportEntry reportEntry) {
		Map<Type, List<ReportEntry>> operation = operationMap.get(operationInvocation);
		if (operation == null) {
			operation = new HashMap<Type, List<ReportEntry>>();
			operationMap.put(operationInvocation, operation);
		}
		List<ReportEntry> list = operation.get(reportEntry.getType());
		if (list == null) {
			list = new ArrayList<ReportEntry>();
			operation.put(reportEntry.getType(), list);
		}
		list.add(reportEntry);
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		for (ReportEntry report : entries) {
			stringBuffer.append(report + "\n");
		}
		return stringBuffer.toString();
	}

	public List<ReportEntry> getEntries(Type type, Status status){
		List<ReportEntry> list = new ArrayList<ReportEntry>();
		if(entries!=null){
			for(ReportEntry entry : entries){
				if(entry.getType() == type && entry.getStatus() == status){
					list.add(entry);
				}
			}
		}
		return list;
	}
	
	public List<ReportEntry> getEntries() {
		if (entries != null) {
			return entries;
		} else {
			return Collections.emptyList();
		}
	}

	public List<ReportEntry> getEntries(OperationInvocation operationInvocation, Type type) {
		List<ReportEntry> list = null;
	
		try{
			list = operationMap.get(operationInvocation).get(type);
		}catch(NullPointerException e){
			e.printStackTrace();
		}	
		if (list != null) {
			return list;
		} else {
			return Collections.emptyList();
		}
	}
	
	public List<ReportEntry> getEntries(OperationInvocation operationInvocation, Type type, Status status) {
		List<ReportEntry> list = new ArrayList<ReportEntry>();
		if(!getEntries(operationInvocation, type).isEmpty()){
			for(ReportEntry re : getEntries(operationInvocation, type)){
				if(re.getStatus() == status)
					list.add(re);
			}
			return list;
		}else{
			return Collections.emptyList();
		}
	}
}
