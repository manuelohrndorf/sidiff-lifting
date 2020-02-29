package org.sidiff.patching.ui.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPersistableEditor;
import org.eclipse.ui.IPerspectiveFactory;
import org.sidiff.patching.ui.view.OperationExplorerView;
import org.sidiff.patching.ui.view.ReportView;

public class SiLiftPatchingPerspective implements IPerspectiveFactory, IPersistableEditor {

	public static final String ID = "org.sidiff.patching.ui.perspective.SiLiftPatchingPerspective";

	@Override
	public void saveState(IMemento memento) {
	}

	@Override
	public void restoreState(IMemento memento) {
	}

	@Override
	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}

	protected void defineActions(IPageLayout layout) {
		// Add "show views".
		layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
		layout.addShowViewShortcut(OperationExplorerView.ID);
		layout.addShowViewShortcut(ReportView.ID);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
	}

	protected void defineLayout(IPageLayout layout) {
		// Place editor
		String editorArea = layout.getEditorArea();

		// Place project and operation explorer
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.25f, editorArea);
		left.addView(IPageLayout.ID_PROJECT_EXPLORER);
		left.addView(OperationExplorerView.ID);

		// Place properties
		IFolderLayout bottom_left = layout.createFolder("bottom_left", IPageLayout.BOTTOM, 0.7f, OperationExplorerView.ID);
		bottom_left.addView(ReportView.ID);

		// Place report 		
		IFolderLayout bottom = 	layout.createFolder("bottom", IPageLayout.BOTTOM, 0.7f, editorArea);
		bottom.addView(IPageLayout.ID_PROP_SHEET);
	}
}
