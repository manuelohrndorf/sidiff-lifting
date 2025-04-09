package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.part;

import java.util.Collections;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;
import org.eclipse.ui.services.IEvaluationService;

/**
 * @since 3.2
 */
public class LastClickPositionProvider extends AbstractSourceProvider {

	public static String SOURCE_LAST_CLICK = LastClickPositionProvider.class.getName() + ".lastClickPosition";

	private MouseListener myMouseListener;

	private IFigure myDiagramFigure;

	private DiagramEditor myDiagramEditor;

	private IEvaluationService myEvaluationService;

	private Point myLastClickedAt = new Point(0, 0);

	public LastClickPositionProvider(DiagramEditor diagramEditor) {
		myDiagramEditor = diagramEditor;
		EditPart diagramEditPart = myDiagramEditor.getDiagramGraphicalViewer().getContents();
		if (diagramEditPart instanceof DiagramEditPart) {
			myDiagramFigure = ((DiagramEditPart) diagramEditPart).getFigure();
			myMouseListener = new DiagramMouseListner();
			myDiagramFigure.addMouseListener(myMouseListener);
		}
	}

	public void attachToService() {
		if (myEvaluationService == null) {
			myEvaluationService = (IEvaluationService) myDiagramEditor.getSite().getService(IEvaluationService.class);
			if (myEvaluationService != null) {
				myEvaluationService.addSourceProvider(this);
			}
		}
	}

	public void detachFromService() {
		if (myEvaluationService != null) {
			myEvaluationService.removeSourceProvider(this);
			myEvaluationService = null;
		}
	}

	public void fireLastClickedAt(int x, int y) {
		myLastClickedAt = new Point(x, y);
		fireSourceChanged(ISources.WORKBENCH, SOURCE_LAST_CLICK, myLastClickedAt);
	}

	public void dispose() {
		if (myMouseListener != null) {
			myDiagramFigure.removeMouseListener(myMouseListener);
			myDiagramFigure = null;
		}

		if (myMouseListener != null) {
			myMouseListener = null;
		}
	}

	public Map<?, ?> getCurrentState() {
		return Collections.singletonMap(SOURCE_LAST_CLICK, myLastClickedAt);
	}

	public String[] getProvidedSourceNames() {
		return new String[] { SOURCE_LAST_CLICK };
	}

	private class DiagramMouseListner implements MouseListener {

		public void mouseReleased(MouseEvent me) {
		}

		public void mousePressed(MouseEvent me) {
			if (me.button == 3) {
				myLastClickedAt.setX(me.x);
				myLastClickedAt.setY(me.y);
			}
		}

		public void mouseDoubleClicked(MouseEvent me) {
		}
	}
}