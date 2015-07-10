package org.sidiff.domain.editor.extension;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractDomainEditor implements IDomainEditor {

	protected final String treeEditorId, diagramEditorId;
	private Boolean treeEditorPresent = null, diagramEditorPresent = null;

	public AbstractDomainEditor(String treeEditorId, String diagramEditorId) {
		super();
		this.treeEditorId = treeEditorId;
		this.diagramEditorId = diagramEditorId;
	}

	@Override
	public final String getTreeEditorID() {
		return treeEditorId;
	}

	@Override
	public final String getDiagramEditorID() {
		return diagramEditorId;
	}

	@Override
	public final Boolean isTreeEditorPresent() {
		if (treeEditorPresent == null)
			treeEditorPresent = isEditorPresent(treeEditorId);
		return treeEditorPresent;
	}

	@Override
	public final boolean isDiagramEditorPresent() {
		if (diagramEditorPresent == null)
			diagramEditorPresent = isEditorPresent(diagramEditorId);
		return diagramEditorPresent;
	}

	private static boolean isEditorPresent(String editorId) {
		if (editorId == null || "".equals(editorId))
			return false;
		IEditorDescriptor descriptor = (IEditorDescriptor) PlatformUI
				.getWorkbench().getEditorRegistry().findEditor(editorId);
		return (descriptor != null);
	}

	@Override
	public EditingDomain getEditingDomain(IEditorPart editorPart) {
		if (editorPart instanceof IEditingDomainProvider) {
			IEditingDomainProvider editor = (IEditingDomainProvider) editorPart;
			return editor.getEditingDomain();
		} else {
			// TODO Exception?
			return null;
		}
	}

	@Override
	public Resource getResource(IEditorPart editorPart) {
		if (editorPart instanceof IEditingDomainProvider) {
			IEditingDomainProvider editor = (IEditingDomainProvider) editorPart;
			return editor.getEditingDomain().getResourceSet().getResources()
					.get(0);
		} else {
			// TODO Exception?
			return null;
		}
	}
}
