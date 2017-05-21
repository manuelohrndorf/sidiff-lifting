package org.sidiff.editrule.recorder.handlers;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.editrule.recorder.handlers.util.EMFHandlerUtil;
import org.sidiff.editrule.recorder.handlers.util.EditRuleUtil;
import org.sidiff.editrule.recorder.handlers.util.UIUtil;

public class CleanUpImportsHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Module editRule = EMFHandlerUtil.getSelection(event, Module.class);
		
		if (editRule != null) {
			cleanUpImports(editRule);
			
			try {
				editRule.eResource().save(Collections.emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			UIUtil.showMessage("Edit-Rule saved:\n\n" + EcoreUtil.getURI(editRule).toPlatformString(true));
		}
		
		return null;
	}
	
	public static void cleanUpImports(Module editRule) {
		editRule.getImports().clear();
		editRule.getImports().addAll(EditRuleUtil.getImports(editRule));
	}
}
