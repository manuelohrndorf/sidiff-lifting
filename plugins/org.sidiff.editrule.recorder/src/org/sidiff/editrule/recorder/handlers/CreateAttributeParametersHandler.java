package org.sidiff.editrule.recorder.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.editrule.recorder.handlers.util.EMFHandlerUtil;
import org.sidiff.editrule.recorder.handlers.util.EditRuleUtil;
import org.sidiff.editrule.recorder.handlers.util.UIUtil;

/**
 * Creates a parameter for each attribute variable.
 * 
 * @author Manuel Ohrndorf
 */
public class CreateAttributeParametersHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Module editRule = EMFHandlerUtil.getSelection(event, Module.class);
		
		if (editRule != null) {
			List<Parameter> attributeParameters = new ArrayList<>();
			
			editRule.eAllContents().forEachRemaining(element -> {
				if (element instanceof Attribute) {
					attributeParameters.add(HenshinFactory.eINSTANCE.createParameter(
							((Attribute) element).getValue()));
				}
			});
			
			Rule mainRule = EditRuleUtil.getMainRule(editRule);
			
			for (Parameter attributeParameter : attributeParameters) {
				if (mainRule.getParameter(attributeParameter.getName()) == null) {
					mainRule.getParameters().add(attributeParameter);
				}
			}
			
			// Save edit-rule:
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
