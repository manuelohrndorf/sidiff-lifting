package org.sidiff.editrule.tools;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.difference.lifting.facade.util.PipelineUtils;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.extension.IRuleBase;

public class EditRuleNameMapper implements IApplication{

	@Override
	public Object start(IApplicationContext context) throws Exception {
System.out.println("--------------- START ---------------");
		
//		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
//		
//		String resourceURI = args[0];
		
		HashMap<String, String> replacments = new HashMap<String, String>();
		
		replacments.put("CREATE_", "create");
		replacments.put("DELETE", "delete");
		replacments.put("UNSET", "unset");
		replacments.put("SET", "set");
		replacments.put("ADD", "add");
		replacments.put("REMOVE", "remove");
		replacments.put("MOVE", "move");

		replacments.put("\\(\\w*\\)", "");
		
		HashMap<String, String> replacments2 = new HashMap<String, String>();
		replacments2.put("eattribute", "EAttribute");
		replacments2.put("eclass", "EClass");
		replacments2.put("eoperation", "EOperation");
		replacments2.put("eenum", "EEnum");
		
		
//		EObject eObject = EMFStorage.eLoad(EMFStorage.pathToFileUri(resourceURI));
		
		Set<IRuleBase> rulebases = PipelineUtils.getAvailableRulebases(EcorePackage.eNS_URI);
		
		for(IRuleBase rb : rulebases){
			for(EditRule er : rb.getActiveEditRules()){
				String revisedName = er.getExecuteModule().getName();
				revisedName = revisedName.replaceAll("\\(\\w*\\)", "");
				for(String s : revisedName.split("_")){
					String replacement = s;
					if(s.length() > 1){
						if(s.equals(s.toUpperCase())){
							replacement = s.replace(s.subSequence(1, s.length()).toString(), s.subSequence(1, s.length()).toString().toLowerCase());
						}
						revisedName = revisedName.replace(s, replacement);
						
						revisedName = revisedName.replaceAll("_", "");
						// Create a resource set with a base directory:
						HenshinResourceSet resourceSet = new HenshinResourceSet();

						// Load the module:
						//FIXME
						Module module = resourceSet.getModule("path/to/rulebases"+er.getExecuteModule().eResource().getURI().toPlatformString(true));
						module.setName(revisedName);
						er.getExecuteModule().setName(revisedName);
						resourceSet.saveEObject(module, "path/to/rulebases"+er.getExecuteModule().eResource().getURI().toPlatformString(true));
					}
					
				}
				
				System.out.println("mappings.add(new NameMapping(\"" + er.getExecuteModule().getName()+"\",\"" + revisedName+ "\"));");
			}
		}
		
		return null;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
