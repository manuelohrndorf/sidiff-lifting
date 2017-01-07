package org.sidiff.difference.lifting.recognitionrulesorter.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.difference.lifting.recognitionrulesorter.GenericRecognitionRuleSorter;
import org.sidiff.difference.lifting.recognitionrulesorter.IRecognitionRuleSorter;

public class RecognitionRuleSorterLibrary {

	/**
	 * 
	 * @param documentTypes
	 * @return
	 */
	public static Set<IRecognitionRuleSorter> getAvailableRecognitionRuleSorters(Set<String> documentTypes){
		Set<IRecognitionRuleSorter> rrSorters = new HashSet<IRecognitionRuleSorter>();
		
		if(documentTypes.size()==1) {
			for(IRecognitionRuleSorter rrsExtension: getAllAvailableRecognitionRuleSorters()){
				if (documentTypes.iterator().next().equals(rrsExtension.getDocumentType())
						|| rrsExtension instanceof GenericRecognitionRuleSorter) {
					rrSorters.add(rrsExtension);
				}
			}
		}
		
		return rrSorters;
	}
	
	/**
	 * 
	 * @param documentTypes
	 * @return
	 */
	public static IRecognitionRuleSorter getDefaultRecognitionRuleSorter(Set<String> documentTypes){
		Set<IRecognitionRuleSorter> rrSorters = RecognitionRuleSorterLibrary.getAvailableRecognitionRuleSorters(documentTypes);
		assert (!rrSorters.isEmpty()) : "No recognition rule sorter found for documentTypes " + documentTypes;
		
		IRecognitionRuleSorter rrSorter = null;
		if(rrSorters.size() > 1){
			for (IRecognitionRuleSorter iRecognitionRuleSorter : rrSorters) {
				if(!(iRecognitionRuleSorter instanceof GenericRecognitionRuleSorter)){
					rrSorter = iRecognitionRuleSorter;
					break;
				}
			}
		}else{
			rrSorter = rrSorters.iterator().next();
		}
		return rrSorter;
	}
	
	public static IRecognitionRuleSorter getRecognitionRuleSorter(String key){
		IRecognitionRuleSorter recognitionRuleSorter = null;
		for(IRecognitionRuleSorter rrSorter : getAllAvailableRecognitionRuleSorters()){
			if(rrSorter.getKey().equals(key)){
				recognitionRuleSorter = rrSorter;
				break;
			}
		}
		return recognitionRuleSorter;
	}
	
	public static Set<IRecognitionRuleSorter> getAllAvailableRecognitionRuleSorters(){
		Set<IRecognitionRuleSorter> rrSorters = new HashSet<IRecognitionRuleSorter>();
		 
		rrSorters.add(new GenericRecognitionRuleSorter());
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(IRecognitionRuleSorter.extensionPointID)) {
			try {
				IRecognitionRuleSorter rrsExtension = (IRecognitionRuleSorter) configurationElement
						.createExecutableExtension("recognition_rule_sorter");
				rrSorters.add(rrsExtension);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return rrSorters;
	}
}
