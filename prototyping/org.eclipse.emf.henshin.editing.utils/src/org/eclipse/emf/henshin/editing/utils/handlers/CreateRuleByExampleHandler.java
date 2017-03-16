package org.eclipse.emf.henshin.editing.utils.handlers;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.editing.utils.HenshinEditingUtils;
import org.eclipse.emf.henshin.editing.utils.util.EMFHandlerUtil;
import org.eclipse.emf.henshin.editing.utils.util.HenshinModelHelper;
import org.eclipse.emf.henshin.editing.utils.util.UIUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Constructs a Henshin rule from a pair of models, i.e. an example which
 * declaratively demonstrates the transformation in terms of an original model A
 * and a changed model B.<br/>
 * 
 * Basically, the corresponding elements in A and B will be treated as elements
 * to be preserved by the rule, elements contained in A only will be treated as
 * elements to be deleted, and elements contained in B only will be treated as
 * elements to be created.<br/>
 * 
 * Corresponding elements in A and B are currently determined by calculating a
 * matching using EMFCompare. This might be a variation point in the future, the
 * basic infrastructure is designed for exchangeability of the matcher used to
 * determine the corresponding elements in A and B.
 * 
 * 
 * @author Manuel Ohrndorf, Timo Kehrer
 */
public class CreateRuleByExampleHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		Resource resourceA = null;
		Resource resourceB = null;

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			if (structuredSelection.size() == 2) {
				for (@SuppressWarnings("rawtypes")
				Iterator iterator = structuredSelection.iterator(); iterator.hasNext();) {
					Object oA = iterator.next();
					Object oB = iterator.next();

					if ((oA instanceof IResource) && (oB instanceof IResource)) {
						ResourceSet rssA = new ResourceSetImpl();
						URI uriA = EMFHandlerUtil.getURI((IResource) oA);
						resourceA = rssA.getResource(uriA, true);

						ResourceSet rssB = new ResourceSetImpl();
						URI uriB = EMFHandlerUtil.getURI((IResource) oB);
						resourceB = rssB.getResource(uriB, true);
					}
				}
			}
		}

		if ((resourceA != null) && (resourceB != null)) {
			Module module = HenshinEditingUtils.createRuleByExample(resourceA, resourceB);

			if (module != null) {
				module.getImports().addAll(HenshinModelHelper.calculateImports(module));

				URI eoURI = resourceA.getURI().trimSegments(1).appendSegment(module.getName())
						.appendFileExtension("henshin");
				Resource eoRes = new ResourceSetImpl().createResource(eoURI);
				eoRes.getContents().add(module);

				try {
					eoRes.save(Collections.emptyMap());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return null;
			} else {
				UIUtil.showError("Could not transform this difference to an edit-rule.");
				return null;
			}
		} else {
			UIUtil.showError("The selected resource does not contain a model difference.");
			return null;
		}
	}
}
