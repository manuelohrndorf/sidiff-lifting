package org.silift.diagram.util.actions;

import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IEditorPart;

public interface OpenDiagramAction {

	public IEditorPart open(URI uri, String editorId);
}
