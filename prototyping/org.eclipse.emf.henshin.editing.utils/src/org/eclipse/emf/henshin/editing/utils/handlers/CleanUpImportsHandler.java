package org.eclipse.emf.henshin.editing.utils.handlers;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.editing.utils.util.EMFHandlerUtil;
import org.eclipse.emf.henshin.editing.utils.util.HenshinModelHelper;
import org.eclipse.emf.henshin.editing.utils.util.UIUtil;
import org.eclipse.emf.henshin.model.Module;

/**
 * Cleans up unused meta-model imports.
 * 
 * @author Manuel Ohrndorf, Timo Kehrer
 */
public class CleanUpImportsHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Module editRule = EMFHandlerUtil.getSelection(event, Module.class);
		
		if (editRule != null) {
			editRule.getImports().clear();
			editRule.getImports().addAll(HenshinModelHelper.calculateImports(editRule));
			
			try {
				editRule.eResource().save(Collections.emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			UIUtil.showMessage("Edit-Rule saved:\n\n" + EcoreUtil.getURI(editRule).toPlatformString(true));
		}
		
		return null;
	}

}
