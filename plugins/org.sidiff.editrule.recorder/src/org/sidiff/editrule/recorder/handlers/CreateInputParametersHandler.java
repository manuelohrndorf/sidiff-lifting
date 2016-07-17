package org.sidiff.editrule.recorder.handlers;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getPreservedNodes;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.getRules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.sidiff.common.henshin.view.NodePair;
import org.sidiff.editrule.recorder.handlers.util.EMFHandlerUtil;
import org.sidiff.editrule.recorder.handlers.util.EditRuleUtil;
import org.sidiff.editrule.recorder.handlers.util.UIUtil;

/**
 * Creates an edit-rule input parameter.
 * 
 * @author Manuel Ohrndorf
 */
public class CreateInputParametersHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Module editRule = EMFHandlerUtil.getSelection(event, Module.class);
		
		if (editRule != null) {
			
			// Create dialog:
			ElementListSelectionDialog selectDialog = new ElementListSelectionDialog(
					Display.getDefault().getActiveShell(), new LabelProvider() {
						@Override
						public String getText(Object element) {

							if (element instanceof NodePair) {
								return ((NodePair) element).getLhsNode().getName() + " : "
										+ ((NodePair) element).getLhsNode().getType().getName();
							}

							return super.getText(element);
						}
					});
			selectDialog.setTitle("Create Input-Parameters");
			selectDialog.setHelpAvailable(false);
			selectDialog.setMultipleSelection(true);
			
			// Collect preserve nodes:
			List<NodePair> preserveNodes = new ArrayList<>();
			
			getRules(editRule).forEach(rule -> {
				preserveNodes.addAll(getPreservedNodes(rule));
			});
			
			selectDialog.setElements(preserveNodes.toArray());
			
			// Open dialog:
			selectDialog.open();
			
			// Create Input-Parameter:
			if (selectDialog.getResult() != null) {
				for(Object selection : selectDialog.getResult()) {
					if (selection instanceof NodePair) {
						EditRuleUtil.createInputParameter(editRule, ((NodePair) selection).getLhsNode().getName());
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
		}
		
		return null;
	}
}
