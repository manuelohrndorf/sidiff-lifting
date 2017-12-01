package org.sidiff.editrule.tools.handlers;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isCreationNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.sidiff.common.emf.modelstorage.EMFHandlerUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.editrule.consistency.fixing.EditRuleFixer;

/**
 * Cleans up unused Henshin rule parameters.
 * 
 * @author Manuel Ohrndorf
 */
public class CleanUpParametersHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Module editRule = EMFHandlerUtil.getSelection(event, Module.class);
		
		if (editRule != null) {
			cleanUpParamters(editRule);
			
			try {
				editRule.eResource().save(Collections.emptyMap());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			UIUtil.showMessage("Edit-Rule saved:\n\n" + EcoreUtil.getURI(editRule).toPlatformString(true));
		}
		
		return null;
	}
	
	public static void cleanUpParamters(Module editRule) {
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
		
		// Create output-parameters:
		editRule.eAllContents().forEachRemaining(element -> {
			if (element instanceof Node) {
				if (isCreationNode((Node) element)) {
					EditRuleFixer.fix_mappedAllCreateNodes(editRule, (Node) element);
				}
			}
		});
	}
}
