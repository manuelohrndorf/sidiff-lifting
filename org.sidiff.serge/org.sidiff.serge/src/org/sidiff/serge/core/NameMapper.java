package org.sidiff.serge.core;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Module;

//TODO: Maybe this should be a SERGe config option
public class NameMapper {
	
	private List<NameMapping> mappings;
	private Map<String , Module> moduleIndex;
	
	public NameMapper(EPackage metaModel, Set<Module> allModules) {
		// init mappings
		mappings = new ArrayList<NameMapper.NameMapping>(allModules.size());
		if (metaModel.getNsURI().equals(EcorePackage.eINSTANCE.getNsURI())){
			initEcoreNameMappings();
		}
	
		// init module index
		moduleIndex = new HashMap<String, Module>();
		for (Module module : allModules) {
			moduleIndex.put(module.getName(), module);
		}
	}
	
	public void replaceNames(){
		for (NameMapping mapping : mappings) {
			Module module = moduleIndex.get(mapping.generatedName);
			if (module != null){
				module.setName(mapping.manualName);
			}
		}
	}
	
	private void initEcoreNameMappings(){
		mappings.add(new NameMapping("<gen-name>", "<understandable-name>"));
		// ....
	}
	
	class NameMapping {
		String generatedName;
		String manualName;
		
		NameMapping(String generatedName, String manualName) {
			super();
			this.generatedName = generatedName;
			this.manualName = manualName;
		}
	}
}
