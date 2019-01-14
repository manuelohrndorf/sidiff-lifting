package org.sidiff.integration.editor.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.PlatformUI;
import org.sidiff.integration.editor.BasicEditorIntegration;

public class DefaultEditorIntegration extends BasicEditorIntegration {

	public DefaultEditorIntegration(URI modelFile) {
		super(getDefaultId(modelFile), null, null, modelFile.fileExtension(), null);
	}

	private static String getDefaultId(URI modelFile) {
		IEditorDescriptor editor = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(modelFile.lastSegment());
		return editor == null ? null : editor.getId();
	}
}
