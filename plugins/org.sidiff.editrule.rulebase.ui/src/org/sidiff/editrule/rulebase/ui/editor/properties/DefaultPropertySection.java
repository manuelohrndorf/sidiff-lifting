package org.sidiff.editrule.rulebase.ui.editor.properties;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;

/**
 * An {@link AdvancedPropertySection} that assumes the part is
 * a {@link RulebaseEditor} to obtains and use its adapter factory.
 * @author rmueller
 */
public class DefaultPropertySection extends AdvancedPropertySection {

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		page.setPropertySourceProvider(new AdapterFactoryContentProvider(((RulebaseEditor)part).getAdapterFactory()));
	}
}
