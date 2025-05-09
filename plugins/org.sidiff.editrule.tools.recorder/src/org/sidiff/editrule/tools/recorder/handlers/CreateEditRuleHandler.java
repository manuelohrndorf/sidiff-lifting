package org.sidiff.editrule.tools.recorder.handlers;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.ui.util.EMFHandlerUtil;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.editrule.tools.recorder.DifferenceToEditRule;
import org.sidiff.editrule.tools.recorder.TransformationSetup;
import org.sidiff.editrule.tools.util.EditRuleUtil;
import org.sidiff.editrule.tools.util.HenshinDiagramUtil;
import org.sidiff.matching.model.Correspondence;

/**
 * Transforms a symmetric difference into an edit-rule.
 * 
 * @author Manuel Ohrndorf
 */
public class CreateEditRuleHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SymmetricDifference difference = EMFHandlerUtil.getSelection(event, SymmetricDifference.class);
		
		if (difference != null) {
			String eoName = difference.eResource().getURI().segments()[difference.eResource().getURI().segmentCount() - 2];
			Module module = createEditRule(eoName, difference.getMatching().getCorrespondences(), difference.getChanges());

			if (module != null) {
				module.getImports().addAll(EditRuleUtil.getImports(module));
				
				URI eoURI = EcoreUtil.getURI(difference).trimSegments(1)
						.appendSegment(module.getName() + "_execute")
						.appendFileExtension("henshin");
				Resource eoRes = difference.eResource().getResourceSet().createResource(eoURI);
				eoRes.getContents().add(module);

				try {
					eoRes.save(Collections.emptyMap());
					Resource diagramResource = HenshinDiagramUtil.createDiagram(module);
					
					if (HenshinDiagramUtil.maxNodeCount(module, 100)) {
						HenshinDiagramUtil.openDiagram(diagramResource);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				UIUtil.showMessage("Edit-Rule saved:\n\n" + eoURI.toPlatformString(true));
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
	
	public static Module createEditRule(String eoName, 
			Collection<Correspondence> correspondences, Collection<Change> changes) {
		
		TransformationSetup trafoSetup = new TransformationSetup();
		trafoSetup.setChanges(changes);
		trafoSetup.setCorrespondences(correspondences);
		trafoSetup.setEditRuleName(eoName);
		
		DifferenceToEditRule editRuleRecorder = new DifferenceToEditRule(trafoSetup);
		return editRuleRecorder.getEditRule();
	}
}
