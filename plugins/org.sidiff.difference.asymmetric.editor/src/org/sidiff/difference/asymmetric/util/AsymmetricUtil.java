package org.sidiff.difference.asymmetric.util;

import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.OperationInvocation;

public class AsymmetricUtil {
	
	public static void enable(OperationInvocation operation){
		operation.setApply(true);
		for(DependencyContainer dependency : operation.getIncoming()){
			enable(dependency.getSource());
		}
	}
	
	public static void disable(OperationInvocation operation){
		operation.setApply(false);
		for(DependencyContainer dependency : operation.getIncoming()){
			disable(dependency.getSource());
		}
	}
}
