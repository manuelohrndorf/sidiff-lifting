package org.eclipse.emf.henshin.editing.utils.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.editing.utils.util.EMFHandlerUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;

/**
 * Cleans up unused Henshin rule parameters.
 * 
 * @author Manuel Ohrndorf, Timo Kehrer
 */
public class CleanUpParametersHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Module module = EMFHandlerUtil.getSelection(event, Module.class);
		
		if (module != null) {
			cleanUpParamters(module);
			
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
	
	private void cleanUpParamters(Module editRule) {
		Set<String> names = new HashSet<>();
		
		// Collect parameter names:
		editRule.eAllContents().forEachRemaining(element -> {
			if (element instanceof Node) {
				names.add(((Node) element).getName());
			}
			
			else if (element instanceof Attribute) {
				names.add(((Attribute) element).getValue());
			}
		});
		
		// Remove unknown parameters:
		List<EObject> unknownParamerters = new ArrayList<>();
		
		editRule.eAllContents().forEachRemaining(element -> {
			if (element instanceof Parameter) {
				if (!names.contains(((Parameter) element).getName())) {
					unknownParamerters.add(element);
				}
			}
		});
		
		for (EObject parameter : unknownParamerters) {
			EcoreUtil.remove(parameter);
		}
		
		// Remove deprecated mappings:
		List<EObject> deprecatedMappings = new ArrayList<>();
		
		editRule.eAllContents().forEachRemaining(element -> {
			if (element instanceof ParameterMapping) {
				ParameterMapping mapping = (ParameterMapping) element;
						
				if ((mapping.getSource() == null) || (mapping.getTarget() == null)) {
					EcoreUtil.remove(mapping);
				}
				
				else if ((mapping.getSource().eContainer() == null) || (mapping.getTarget().eContainer() == null)) {
					deprecatedMappings.add(mapping);
				}
			}
		});
		
		for (EObject mappings : deprecatedMappings) {
			EcoreUtil.remove(mappings);
		}
	}
}
