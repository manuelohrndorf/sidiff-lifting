package org.eclipse.emf.henshin.editing.utils.handlers;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.editing.utils.HenshinEditingUtils;
import org.eclipse.emf.henshin.editing.utils.util.EMFHandlerUtil;
import org.eclipse.emf.henshin.model.Module;

/**
 * Removes all non-context (preserve) nodes from an edit rule.
 * 
 * @author Manuel Ohrndorf, Timo Kehrer
 */
public class ReduceToMinimalRuleHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Module module = EMFHandlerUtil.getSelection(event, Module.class);

		if (module != null) {
			HenshinEditingUtils.reduceToMinimalRule(module);

			try {
				String fileName = module.eResource().getURI().lastSegment().replace(".henshin", "") + "_reduced";
				URI uri = module.eResource().getURI().trimSegments(1).appendSegment(fileName)
						.appendFileExtension("henshin");
				Resource res = new ResourceSetImpl().createResource(uri);
				res.getContents().add(module);
				res.save(Collections.EMPTY_MAP);

			} catch (IOException e) {
				e.printStackTrace();
			}			
		}

		return null;
	}
}
