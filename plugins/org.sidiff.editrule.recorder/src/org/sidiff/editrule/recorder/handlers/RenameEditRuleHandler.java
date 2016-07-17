package org.sidiff.editrule.recorder.handlers;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Display;
import org.sidiff.editrule.recorder.handlers.util.EMFHandlerUtil;
import org.sidiff.editrule.recorder.handlers.util.EditRuleNaming;
import org.sidiff.editrule.recorder.handlers.util.EditRuleUtil;
import org.sidiff.editrule.recorder.handlers.util.UIUtil;
import org.sidiff.editrule.recorder.handlers.util.UIUtil.NotEmptyValidator;

/**
 * Renames an edit-rule: file, module, rule, diagram
 * 
 * @author Manuel Ohrndorf
 */
public class RenameEditRuleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Module editRule = EMFHandlerUtil.getSelection(event, Module.class);
		
		if (editRule != null) {
			InputDialog setNameDialog = new InputDialog(Display.getDefault().getActiveShell(), 
					"Set Rule Name", 
					"Enter a new rule name:\n\n(format: words and whitespaces - e.g.: Create new EClass)", 
					EditRuleNaming.removeCamelCase(editRule.getName()), 
					new NotEmptyValidator());
			
			setNameDialog.open();
			
			InputDialog setDescriptionDialog = new InputDialog(Display.getDefault().getActiveShell(), 
					"Set Rule Description", 
					"Enter a new rule description:", editRule.getDescription(), 
					null);
			
			setDescriptionDialog.open();
			
			// Rename:
			if (setNameDialog.getValue() != null) {
				String name = setNameDialog.getValue();

				// Module and rule:
				editRule.setName(EditRuleNaming.formatModuleName(name));
				EditRuleUtil.getMainRule(editRule).setName(EditRuleNaming.formatRuleName(name));
				
				// Description:
				if (setDescriptionDialog.getValue() != null) {
					String description = setDescriptionDialog.getValue();
					
					if (description != null) {
						editRule.setDescription(description);
					}
				}
				
				// Files:
				URI modelURI = editRule.eResource().getURI();
				URI diagramURI = modelURI.trimFileExtension().appendFileExtension("henshin_diagram");
				
				String newFileName = EditRuleNaming.formatFileName(name);
				
				// Model:
				URI newModelURI = modelURI.trimSegments(1).appendSegment(
						newFileName + "_execute").appendFileExtension("henshin");
				editRule.eResource().setURI(newModelURI);
				
				// Diagram:
				Resource diagram = editRule.eResource().getResourceSet().getResource(diagramURI, true);
				URI newDiagramURI = modelURI.trimSegments(1).appendSegment(
						newFileName + "_execute").appendFileExtension("henshin_diagram");
				diagram.setURI(newDiagramURI);
				
				// Save changes:
				try {
					editRule.eResource().save(Collections.emptyMap());
					diagram.save(Collections.emptyMap());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				UIUtil.showMessage("Edit-Rule saved:\n\n" + EcoreUtil.getURI(editRule).toPlatformString(true));
			}
		}
		
		return null;
	}

}
