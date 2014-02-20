package org.sidiff.patching.ui.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPersistableEditor;
import org.eclipse.ui.IPerspectiveFactory;
import org.sidiff.patching.ui.view.OperationExplorerView;
import org.sidiff.patching.ui.view.ReportView;

public class SiLiftPerspective implements IPerspectiveFactory,
IPersistableEditor {
	
	public static final String ID = "org.sidiff.patching.ui.perspective.SiLiftPerspective";
	
	@Override
	public void saveState(IMemento memento) {
		// NO support for state saving now
	}

	@Override
	public void restoreState(IMemento memento) {
		// NO support for state restoring now
	}

	@Override
	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}

	public void defineActions(IPageLayout layout) {

		// Add "show views".
		layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
		layout.addShowViewShortcut(OperationExplorerView.ID);
		layout.addShowViewShortcut(ReportView.ID);
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
	}


	public void defineLayout(IPageLayout layout) {
		
		// Place editor
		String editorArea = layout.getEditorArea();

		// Place project and operation explorer
		IFolderLayout left_top = layout.createFolder("left_top", IPageLayout.LEFT, 0.25f, editorArea);
		left_top.addView(IPageLayout.ID_PROJECT_EXPLORER);
		left_top.addView(OperationExplorerView.ID);
		
		// Place report 		
		IFolderLayout left_bottom = layout.createFolder("left_bottom", IPageLayout.BOTTOM, 0.5f, 
				OperationExplorerView.ID);
		left_bottom.addView(ReportView.ID);
		
		// Place properties
		IFolderLayout right_top = layout.createFolder("right_bottom", IPageLayout.RIGHT, 0.25f, editorArea);
		right_top.addView(IPageLayout.ID_PROP_SHEET);
		
		// Place outline
		IFolderLayout right_bottom = layout.createFolder("right_bottom", IPageLayout.BOTTOM, 0.5f, 
				IPageLayout.ID_PROP_SHEET);
		right_bottom.addView(IPageLayout.ID_OUTLINE);
		
	}
}
