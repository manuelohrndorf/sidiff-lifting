package org.sidiff.editrule.tools;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.emf.refactor.examples.simpleWebModelingLanguage.SimpleWebModelingLanguagePackage;
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
		replacments.put("DELETE_", "delete");
		replacments.put("UNSET_", "unset");
		replacments.put("SET_", "set");
		replacments.put("ADD_", "add");
		replacments.put("REMOVE_", "remove");
		replacments.put("MOVE_", "move");
		replacments.put("_", "");
		replacments.put("\\(\\w*\\)", "");
		
		
//		EObject eObject = EMFStorage.eLoad(EMFStorage.pathToFileUri(resourceURI));
		
		Set<IRuleBase> rulebases = PipelineUtils.getAvailableRulebases(SimpleWebModelingLanguagePackage.eNS_URI);
		
		for(IRuleBase rb : rulebases){
			for(EditRule er : rb.getActiveEditRules()){
				String revisedName = er.getExecuteModule().getName();
				for(String key : replacments.keySet()){
					revisedName = revisedName.replaceAll(key, replacments.get(key));
				}
				for(String s : revisedName.split("[a-z]+")){
					if(s.length()>2)
						revisedName = revisedName.replace(s.subSequence(1, s.length()-1), s.subSequence(1, s.length()-1).toString().toLowerCase());
				}
				System.out.println("mappings.add(new NameMapping(\"" + er.getExecuteModule().getName()+"\",\"" + revisedName + "\"));");
			}
		}
		
		return null;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
