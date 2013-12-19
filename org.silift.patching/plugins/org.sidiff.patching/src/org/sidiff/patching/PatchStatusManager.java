package org.sidiff.patching;

import java.util.ArrayList;
import java.util.List;

import org.sidiff.difference.asymmetric.OperationInvocation;

public class PatchStatusManager {

	private List<StatusWrapper> statusWrappers;
	
	public PatchStatusManager(List<OperationInvocation> operationInvocations){
		statusWrappers = new ArrayList<>();
		for(OperationInvocation op : operationInvocations){
			statusWrappers.add(new StatusWrapper(op));
		}
	}
	
	
	public void createPatchReport(){
		
	}
	
	public void update(){
		
	}
	
	public boolean modelChanged(){
		return false;
	}
	
	public StatusWrapper getStatusWrapper(OperationInvocation op){
		return null;
	}
}
