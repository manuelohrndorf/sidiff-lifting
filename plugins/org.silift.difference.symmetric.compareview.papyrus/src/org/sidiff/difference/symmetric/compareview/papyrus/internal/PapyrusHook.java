package org.sidiff.difference.symmetric.compareview.papyrus.internal;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.editor.PapyrusMultiDiagramEditor;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.symmetric.compareview.DecorationHook;

public class PapyrusHook implements DecorationHook {

	@Override
	public void onViewWillBeDecorated(View view) {
		for(IEditorReference editorReference : PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()){
			IEditorPart part = editorReference.getEditor(false);
			
			if(part instanceof PapyrusMultiDiagramEditor){
				PapyrusMultiDiagramEditor papyrusEditor = (PapyrusMultiDiagramEditor) part;
				
				ISashWindowsContentProvider sashWindowsContentProvider = (ISashWindowsContentProvider) papyrusEditor.getAdapter(ISashWindowsContentProvider.class);
				
				ISashWindowsContainer sashWindowsContainer = papyrusEditor.getISashWindowsContainer();
				IPage page = sashWindowsContainer.getActiveSashWindowsPage();
				
				CTabFolder cTabFolder = (CTabFolder) page.getControl().getParent();
				
				TabFolder tabFolder = (TabFolder) sashWindowsContentProvider.getRootModel();
				for(PageRef pageRef : tabFolder.getChildren()){
					Diagram diagram = (Diagram) pageRef.getEmfPageIdentifier();
					Iterator<EObject> it = diagram.eAllContents();
					while(it.hasNext()){
						EObject eObject = it.next();
						if(eObject == view){
							cTabFolder.setSelection(tabFolder.getChildren().indexOf(pageRef));
							break;
						}
					}
				}
			}
		}
	}

}
