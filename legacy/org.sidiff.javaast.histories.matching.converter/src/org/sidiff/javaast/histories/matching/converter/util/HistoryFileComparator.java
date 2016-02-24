package org.sidiff.javaast.histories.matching.converter.util;

import java.io.File;
import java.util.Comparator;

public class HistoryFileComparator implements Comparator<File> {

	@Override
	public int compare(File arg0, File arg1) {
		String fileName0 = arg0.getName();
		String fileName1 = arg1.getName();
		if(fileName0.equals(fileName1))
			return 0;
		else{
			
			String revisionPattern = "(\\D*)(\\d+)*";
		
			String revision0 = fileName0.replaceAll(revisionPattern, "$2");
			String revision1 = fileName1.replaceAll(revisionPattern, "$2");
			if(Integer.parseInt(revision0)<Integer.parseInt(revision1))
				return -1;
			return 1;
		}			
		
	}

}
