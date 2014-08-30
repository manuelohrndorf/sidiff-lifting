package org.sidiff.difference.lifting.recognitionrulesorter.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.difference.lifting.recognitionrulesorter.GeneralRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;

public class RecognitionRuleSorterUtil {

	/**
	 * 
	 * @param documentType
	 * @return
	 */
	public static Set<IRecognitionRuleSorter> getAvailableRecognitionRuleSorters(String documentType){
		Set<IRecognitionRuleSorter> rrSorters = new HashSet<IRecognitionRuleSorter>();
		
		IRecognitionRuleSorter generalRecognitionRuleSorter = new GeneralRecognitionRuleSorter();
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(IRecognitionRuleSorter.extensionPointID)) {
			try {
				IRecognitionRuleSorter rrsExtension = (IRecognitionRuleSorter) configurationElement.createExecutableExtension("recognition_rule_sorter");
				if (documentType.equals(rrsExtension.getDocumentType())) {
					rrSorters.add(rrsExtension);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(rrSorters.isEmpty()){
			rrSorters.add(generalRecognitionRuleSorter);
		}
		
		return rrSorters;
	}
	
	/**
	 * 
	 * @param documentType
	 * @return
	 */
	public static IRecognitionRuleSorter getDefaultRecognitionRuleSorter(String documentType){
		Set<IRecognitionRuleSorter> rrSorters = RecognitionRuleSorterUtil.getAvailableRecognitionRuleSorters(documentType);
		assert (!rrSorters.isEmpty()) : "No recognition rule sorter found for documentType " + documentType;
		
		IRecognitionRuleSorter rrSorter = null;
		if(rrSorters.size() > 1){
			for (IRecognitionRuleSorter iRecognitionRuleSorter : rrSorters) {
				if(!iRecognitionRuleSorter.getDocumentType().equals("general")){
					rrSorter = iRecognitionRuleSorter;
					break;
				}
			}
		}else{
			rrSorter = rrSorters.iterator().next();
		}
		return rrSorter;
	}
}
