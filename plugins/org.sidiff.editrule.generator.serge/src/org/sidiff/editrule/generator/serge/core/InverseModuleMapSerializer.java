package org.sidiff.editrule.generator.serge.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;

public class InverseModuleMapSerializer extends LogSerializer{

	
	public InverseModuleMapSerializer(SergeSettings settings) {
		
		super(settings);

	}
	
	public void serialize(InverseModuleMapper inverseModuleMapper) {
		
		// establish content of file
		String content = "";
		for(Entry<Map<Module,Module>,Set<Map<Parameter,Parameter>>> pair: inverseModuleMapper.getAllInverseModulePairs()) {
			Map<Module,Module> modulePair = pair.getKey();
			Set<Map<Parameter,Parameter>> correspondingParameterPairs = pair.getValue();
			
			Module m1 = modulePair.entrySet().iterator().next().getKey();
			Module m2 = modulePair.get(m1);
			
			content += "\n"+m1.getName() + " <--> " + m2.getName();
			for(Map<Parameter, Parameter> parameterPair : correspondingParameterPairs) {
				Parameter p1 = parameterPair.entrySet().iterator().next().getKey();
				Parameter p2 = parameterPair.get(p1);
				
				content += "| " + p1.getName() + " <--> " + p2.getName();
				
			}
		}
		
		// write it into a file
		
		String outputFilePath = settings.getOutputFolderPath()
				+ System.getProperty("file.separator")
				+ "_InverseModuleMap.log";
				
		File file = new File(outputFilePath);
		FileOutputStream fop = null;
		try {
			fop = new FileOutputStream(file);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	 


	}
}
