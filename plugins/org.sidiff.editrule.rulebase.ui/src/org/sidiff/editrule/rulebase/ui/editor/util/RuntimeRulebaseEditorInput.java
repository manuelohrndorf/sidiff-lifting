package org.sidiff.editrule.rulebase.ui.editor.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.common.ui.util.UIUtil;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.builder.EditRuleBaseWrapper;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;

/**
 * Opens a rulebase registered by a runtime plug-in (with limited functionality).
 * 
 * Usage example to open a rulebase editor by the key of the rulebase:
 <code>	
	for (Iterator<? extends IBasicRuleBase> iterator = IRuleBaseProject.MANAGER.getRuleBases().iterator(); iterator.hasNext();) {
		IBasicRuleBase rulebaseView = iterator.next();
			
		if (rulebaseView.getKey().equals("org.sidiff.ecore.editrules.complex")) {
			RuntimeRulebaseEditorInput runtimeRulebaseEditorInput = new RuntimeRulebaseEditorInput(rulebaseView.getRuleBase());
			RuntimeRulebaseEditorInput.openRulebaseEditor(runtimeRulebaseEditorInput);
		}
	}
</code>
 *
 * @author Manuel Ohrndorf
 */
public class RuntimeRulebaseEditorInput extends URIEditorInput {

	private RuleBase rulebase;
	
	public RuntimeRulebaseEditorInput(RuleBase rulebase) {
		super(rulebase.eResource().getURI(), rulebase.getName());
		this.rulebase = rulebase;
	}
	
	public EditRuleBaseWrapper getEditRuleBaseWrapper() {
		return new RuntimeEditRuleBaseWrapper(rulebase);
	}
	
	public static void openRulebaseEditor(RuntimeRulebaseEditorInput input) throws PartInitException {
		IWorkbenchPage page = UIUtil.getActivePage();
		
		if(page == null) {
			throw new IllegalStateException("The active workbench window has no active page");
		}

		IDE.openEditor(page, input, RulebaseEditor.ID);
	}
	
	private static class RuntimeEditRuleBaseWrapper extends EditRuleBaseWrapper {

		public RuntimeEditRuleBaseWrapper(RuleBase rulebase) {
			this.rulebase = rulebase;
			this.rulebaseURI = rulebase.eResource().getURI();
			
			if (rulebase.eResource().getResourceSet() instanceof SiDiffResourceSet) {
				this.resourceSet = (SiDiffResourceSet) rulebase.eResource().getResourceSet();
			}
		}
		
		@Override
		public void saveRuleBase() {
			// ignore save operation...
			// TODO: Saving changes in a runtime rulebase (to a plug-in jar) is currently not supported.
			// super.saveRuleBase();
		}
		
		public IProject getProject() {
			if (this.project == null) {
				throw new UnsupportedOperationException("Operation is not supported on a runtime rulebase.");
			}
			return this.project;
		}
		
		public SiDiffResourceSet getResourceSet() {
			if (this.resourceSet == null) {
				throw new UnsupportedOperationException("Operation is not supported on a runtime rulebase.");
			}
			return this.resourceSet;
		}
		
	}
}
