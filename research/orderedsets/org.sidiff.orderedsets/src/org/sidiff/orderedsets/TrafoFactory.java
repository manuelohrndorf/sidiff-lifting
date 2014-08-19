package org.sidiff.orderedsets;

import org.sidiff.orderedsets.impl.MMOrig2MMRefinedJava;
import org.sidiff.orderedsets.impl.MOrig2MRefinedJava;
import org.sidiff.orderedsets.impl.MRefined2MOrigJava;

public class TrafoFactory {
	
	public static MMOrig2MMRefined createMMOrig2MMRefined(){
		return new MMOrig2MMRefinedJava();
	}
	
	public static MOrig2MRefined createMOrig2MRefined(){
		return new MOrig2MRefinedJava();
	}
	
	public static MRefined2MOrig createMRefined2MOrig(){
		return new MRefined2MOrigJava();
	}
}
