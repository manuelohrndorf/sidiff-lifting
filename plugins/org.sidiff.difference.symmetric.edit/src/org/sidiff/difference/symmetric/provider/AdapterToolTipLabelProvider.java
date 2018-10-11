
package org.sidiff.difference.symmetric.provider;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.sidiff.common.stringresolver.util.LabelPrinter;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.impl.ChangeImpl;

public class AdapterToolTipLabelProvider extends DelegatingStyledCellLabelProvider {

	public AdapterToolTipLabelProvider(AdapterFactory adapterFactory, Viewer viewer) {
		super(new AdapterFactoryLabelProvider.StyledLabelProvider(adapterFactory, viewer));
	}

	@Override
	public String getToolTipText(Object element) {
		String text = null;
		
		if (element instanceof SemanticChangeSet) {
			text = ((SemanticChangeSet) element).getDescription();
		} else if (element instanceof ChangeImpl) {
			LabelPrinter labelPrinter = ((ChangeImpl) element).getLabelPrinter();
			
			if (element instanceof AddReference) {
				text = String.format("%s (%s -> %s)", labelPrinter.getLabel(((AddReference) element).getType()),
						labelPrinter.getToolTipLabel(((AddReference) element).getSrc()),
						labelPrinter.getToolTipLabel(((AddReference) element).getTgt()));
			} else if (element instanceof RemoveReference) {
				text = String.format("%s (%s -> %s)", labelPrinter.getLabel(((RemoveReference) element).getType()),
						labelPrinter.getToolTipLabel(((RemoveReference) element).getSrc()),
						labelPrinter.getToolTipLabel(((RemoveReference) element).getTgt()));
			}
		}
			
		return text;
	}
}
