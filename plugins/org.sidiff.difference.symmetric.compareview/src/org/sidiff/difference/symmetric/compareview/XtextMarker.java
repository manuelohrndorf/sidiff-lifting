package org.sidiff.difference.symmetric.compareview;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorPart;

public interface XtextMarker {
	
	public boolean isXtextObject(EObject eObject);

	public void mark(EObject textEObject, IEditorPart editor);
	
	public void clear();

	public Set<EObject> findXtextObjectInEditor(EObject search, IEditorPart editor, final URI resourceURI);
}
