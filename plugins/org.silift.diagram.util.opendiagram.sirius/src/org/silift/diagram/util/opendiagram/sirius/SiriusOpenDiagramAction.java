package org.silift.diagram.util.opendiagram.sirius;

import java.util.Collection;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.ui.business.api.dialect.DialectUIManager;
import org.eclipse.sirius.viewpoint.DAnalysis;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DView;
import org.eclipse.ui.IEditorPart;
import org.silift.diagram.util.actions.OpenDiagramAction;

public class SiriusOpenDiagramAction implements OpenDiagramAction {

	@Override
	public IEditorPart open(URI uri, String editorId) {
		// Start Sirius Session
		Session session = SessionManager.INSTANCE.getSession(uri, new NullProgressMonitor());

		if (!session.isOpen()) {
			session.open(new NullProgressMonitor());
		}

		for (Session otherSession : SessionManager.INSTANCE.getSessions()) {
			otherSession.getOwnedViews();
		}
		// Get representation
		Collection<DView> views = session.getOwnedViews();

		EList<DRepresentation> representations = new BasicEList<DRepresentation>();
		
		for(DView view : views){
			representations.addAll(view.getOwnedRepresentations());
		}

		DRepresentation representation = null;

		//FIXME: Generic way of choosing the right representation
		representation = representations.get(0);
		
		if(representation!= null){
			return DialectUIManager.INSTANCE.openEditor(session, representation,
					new NullProgressMonitor());
		}
		else
			return null;
		
	}

}
