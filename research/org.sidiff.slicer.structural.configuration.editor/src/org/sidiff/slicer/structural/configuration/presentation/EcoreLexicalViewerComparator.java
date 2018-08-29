package org.sidiff.slicer.structural.configuration.presentation;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

/**
 * A {@link ViewerComparator} for tree viewers showing ecore objects.
 * Elements are grouped by their respective container.
 * Elements implementing {@link ENamedElement} are sorted according
 * their names. Elements not implementing ENamedElement are sorted
 * using the default {@link ViewerComparator} implementation.
 * @author rmueller
 *
 */
public class EcoreLexicalViewerComparator extends ViewerComparator
{
	@Override
	public int category(Object element)
	{
		if(element instanceof EObject)
		{
			EObject container = ((EObject)element).eContainer();
			if(container != null)
			{
				return container.hashCode();
			}
		}
		return super.category(element);
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if(e1 instanceof ENamedElement && e2 instanceof ENamedElement)
		{
			return ((ENamedElement)e1).getName().compareTo(((ENamedElement)e2).getName());
		}
		return super.compare(viewer, e1, e2);
	}
}
