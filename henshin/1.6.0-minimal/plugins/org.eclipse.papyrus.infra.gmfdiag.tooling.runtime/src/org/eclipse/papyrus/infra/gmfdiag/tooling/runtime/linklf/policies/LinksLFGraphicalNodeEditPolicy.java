package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.policies;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionAnchorsCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionEndsCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.INodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.internal.commands.SetConnectionBendpointsCommand;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.IAnchorableFigure;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.LinkLFShapeNodeAnchorDelegate;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.linklf.SlidableSnapToGridAnchor;

/**
 * @since 3.3
 */
public class LinksLFGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	private static final String PARAM_SOURCE_ANCHOR = LinksLFGraphicalNodeEditPolicy.class
			.getName() + ":SourceAnchor";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		INodeEditPart node = getConnectableEditPart();
		if (node == null)
			return null;

		TransactionalEditingDomain editingDomain = getEditingDomain();

		GetAnchorAndBendpoints anchorAndBendpoints = new GetTargetAnchorAndBendpoints();
		ConnectionAnchor targetAnchor = anchorAndBendpoints
				.getAnchorAndBendpoints(request);

		INodeEditPart targetEP = getConnectionCompleteEditPart(request);
		if (targetEP == null) {
			return null;
		}

		SetConnectionEndsCommand sceCommand = new SetConnectionEndsCommand(
				editingDomain, StringStatics.BLANK);
		sceCommand.setEdgeAdaptor(new EObjectAdapter((EObject) request
				.getConnectionEditPart().getModel()));
		sceCommand.setNewTargetAdaptor(targetEP);
		SetConnectionAnchorsCommand scaCommand = new SetConnectionAnchorsCommand(
				editingDomain, StringStatics.BLANK);
		scaCommand.setEdgeAdaptor(new EObjectAdapter((EObject) request
				.getConnectionEditPart().getModel()));
		scaCommand.setNewTargetTerminal(targetEP
				.mapConnectionAnchorToTerminal(targetAnchor));
		CompositeCommand cc = new CompositeCommand(
				DiagramUIMessages.Commands_SetConnectionEndsCommand_Target);
		cc.compose(sceCommand);
		cc.compose(scaCommand);
		if (anchorAndBendpoints.getBendpointsHint() != null) {
			SetAbsoluteBendpointsCommand scbCommand = new SetAbsoluteBendpointsCommand(
					editingDomain);
			scbCommand.setEdgeAdapter(new EObjectAdapter((EObject) request
					.getConnectionEditPart().getModel()));
			scbCommand.setNewPointList(anchorAndBendpoints.getBendpointsHint()
					.getCopy());
			cc.compose(scbCommand);
		}
		Command cmd = new ICommandProxy(cc);
		EditPart cep = request.getConnectionEditPart();
		RoutingStyle style = (RoutingStyle) ((View) cep.getModel())
				.getStyle(NotationPackage.eINSTANCE.getRoutingStyle());
		Routing currentRouter = Routing.MANUAL_LITERAL;
		if (style != null) {
			currentRouter = style.getRouting();
		}
		Command cmdRouter = getRoutingAdjustment(
				request.getConnectionEditPart(), getSemanticHint(request),
				currentRouter, request.getTarget());
		if (cmdRouter != null) {
			cmd = cmd == null ? cmdRouter : cmd.chain(cmdRouter);
			// reset the bendpoints
			ConnectionAnchor sourceAnchor = node
					.getSourceConnectionAnchor(request);
			PointList pointList = new PointList();
			pointList.addPoint(sourceAnchor.getLocation(targetAnchor
					.getReferencePoint()));
			pointList.addPoint(targetAnchor.getLocation(sourceAnchor
					.getReferencePoint()));

			SetConnectionBendpointsCommand sbbCommand = new SetAbsoluteBendpointsCommand(
					editingDomain);
			sbbCommand.setEdgeAdapter(request.getConnectionEditPart());
			sbbCommand.setNewPointList(pointList,
					sourceAnchor.getReferencePoint(),
					targetAnchor.getReferencePoint());
			Command cmdBP = new ICommandProxy(sbbCommand);
			if (cmdBP != null) {
				cmd = cmd == null ? cmdBP : cmd.chain(cmdBP);
			}
		}
		return cmd;
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		INodeEditPart node = getConnectableEditPart();
		if (node == null) {
			return null;
		}

		GetAnchorAndBendpoints anchorAndBendpoints = new GetSourceAnchorAndBendpoints();
		anchorAndBendpoints.getAnchorAndBendpoints(request);

		Command result = super.getReconnectSourceCommand(request);
		if (anchorAndBendpoints.getBendpointsHint() != null && result != null
				&& result.canExecute()) {
			ICommand iCommand = ((ICommandProxy) result).getICommand();
			CompositeCommand cc = (CompositeCommand) iCommand;
			SetAbsoluteBendpointsCommand sbcCommand = new SetAbsoluteBendpointsCommand(
					getEditingDomain());
			sbcCommand.setEdge((ConnectionEditPart) request
					.getConnectionEditPart());
			sbcCommand.setNewPointList(anchorAndBendpoints.getBendpointsHint());
			cc.compose(sbcCommand);
		}
		return result;
	}

	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		Command result = super.getConnectionCompleteCommand(request);
		if (result == null) {
			return result;
		}

		ICommandProxy proxy = (ICommandProxy) request.getStartCommand();
		if (proxy == null) {
			return null;
		}
		CompositeCommand cc = (CompositeCommand) proxy.getICommand();
		Iterator<?> commandItr = cc.iterator();
		commandItr.next(); // 0 - CreateCommand
		commandItr.next(); // 1 - SetConnectionEndsCommand
		SetConnectionAnchorsCommand scaCommand = (SetConnectionAnchorsCommand) commandItr
				.next(); // 2
		SetConnectionBendpointsCommand sbpCommand = (SetConnectionBendpointsCommand) commandItr
				.next(); // 3

		ConnectionAnchor targetAnchor = getConnectionCompleteEditPart(request)
				.getTargetConnectionAnchor(request);
		ConnectionAnchor sourceAnchor = (ConnectionAnchor) request
				.getExtendedData().get(PARAM_SOURCE_ANCHOR);

		PointList points = sbpCommand.getNewPointList();
		if (points != null) {
			points = points.getCopy();
		}

		if (targetAnchor instanceof SlidableSnapToGridAnchor
				&& sourceAnchor instanceof SlidableSnapToGridAnchor) {
			SlidableSnapToGridAnchor sourceAnchorImpl = (SlidableSnapToGridAnchor) sourceAnchor;
			SlidableSnapToGridAnchor targetAnchorImpl = (SlidableSnapToGridAnchor) targetAnchor;

			Point updatedSourceLoc = sourceAnchorImpl
					.getLocation(targetAnchorImpl.getReferencePoint());
			Point updatedTargetLoc = targetAnchorImpl
					.getLocation(updatedSourceLoc);

			INodeEditPart targetEP = getConnectionCompleteEditPart(request);

			INodeEditPart sourceEP = (INodeEditPart) request
					.getSourceEditPart();
			IAnchorableFigure sourceFigure = (IAnchorableFigure) sourceEP.getFigure();
			IAnchorableFigure targetFigure = (IAnchorableFigure) targetEP.getFigure();
			
			ConnectionAnchor updatedSourceAnchor = sourceFigure
					.getSourceConnectionAnchorAt(updatedSourceLoc);
			ConnectionAnchor updatedTargetAnchor = targetFigure
					.getTargetConnectionAnchorAt(updatedTargetLoc);

			scaCommand.setNewSourceTerminal(sourceEP
					.mapConnectionAnchorToTerminal(updatedSourceAnchor));
			scaCommand.setNewTargetTerminal(targetEP
					.mapConnectionAnchorToTerminal(updatedTargetAnchor));

			points = new PointList();
			points.addPoint(updatedSourceAnchor.getReferencePoint());
			points.addPoint(updatedTargetAnchor.getReferencePoint());

		}

		if (points != null) {
			GraphicalEditPart diagramEP = (GraphicalEditPart) getHost()
					.getViewer().getContents();
			diagramEP.getContentPane().translateToRelative(points);
			sbpCommand.setNewPointList(points, sourceAnchor, targetAnchor);
		}

		return result;
	}

	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		if (!(request instanceof CreateConnectionViewRequest))
			return null;
		CreateConnectionViewRequest req = (CreateConnectionViewRequest) request;
		CompositeCommand cc = new CompositeCommand(
				DiagramUIMessages.Commands_CreateCommand_Connection_Label);
		TransactionalEditingDomain editingDomain = getEditingDomain();

		CreateCommand createCommand = createCreateConnectionViewCommand(req);
		setViewAdapter((IAdaptable) createCommand.getCommandResult()
				.getReturnValue());

		SetConnectionEndsCommand sceCommand = new SetConnectionEndsCommand(
				editingDomain, StringStatics.BLANK);
		sceCommand.setEdgeAdaptor(getViewAdapter());
		sceCommand.setNewSourceAdaptor(new EObjectAdapter(getView()));
		ConnectionAnchor sourceAnchor = getConnectableEditPart()
				.getSourceConnectionAnchor(request);
		SetConnectionAnchorsCommand scaCommand = new SetConnectionAnchorsCommand(
				editingDomain, StringStatics.BLANK);
		scaCommand.setEdgeAdaptor(getViewAdapter());
		scaCommand.setNewSourceTerminal(getConnectableEditPart()
				.mapConnectionAnchorToTerminal(sourceAnchor));
		SetConnectionBendpointsCommand sbbCommand = new SetAbsoluteBendpointsCommand(
				editingDomain);
		sbbCommand.setEdgeAdapter(getViewAdapter());
		cc.compose(createCommand);
		cc.compose(sceCommand);
		cc.compose(scaCommand);
		cc.compose(sbbCommand);
		Command c = new ICommandProxy(cc);
		request.setStartCommand(c);
		// additionally storing source anchor to allow recomputation
		request.getExtendedData().put(PARAM_SOURCE_ANCHOR, sourceAnchor);
		return c;
	}

	protected TransactionalEditingDomain getEditingDomain() {
		return ((IGraphicalEditPart) getHost()).getEditingDomain();
	}

	protected CreateCommand createCreateConnectionViewCommand(
			CreateConnectionViewRequest req) {
		Diagram diagramView = ((View) getHost().getModel()).getDiagram();
		CreateCommand createCommand = new CreateCommand(getEditingDomain(),
				req.getConnectionViewDescriptor(), diagramView.getDiagram());
		return createCommand;
	}

	private abstract static class GetAnchorAndBendpoints {
		private PointList myBendpointsHint;

		protected abstract ConnectionAnchor doGetConnectionAnchor(
				Request request);

		public ConnectionAnchor getAnchorAndBendpoints(Request request) {
			PointList oldHint = (PointList) getExtendedData(request).get(
					LinkLFShapeNodeAnchorDelegate.KEY_ROUTED_LINK_POINTS);
			ConnectionAnchor result;
			try {
				result = doGetConnectionAnchor(request);
				myBendpointsHint = (PointList) getExtendedData(request).get(
						LinkLFShapeNodeAnchorDelegate.KEY_ROUTED_LINK_POINTS);
			} finally {
				getExtendedData(request).put(
						LinkLFShapeNodeAnchorDelegate.KEY_ROUTED_LINK_POINTS,
						oldHint);
			}
			return result;
		}

		public PointList getBendpointsHint() {
			return myBendpointsHint;
		}

		@SuppressWarnings("unchecked")
		private static Map<String, Object> getExtendedData(Request req) {
			return req.getExtendedData();
		}
	}

	private class GetTargetAnchorAndBendpoints extends GetAnchorAndBendpoints {
		@Override
		protected ConnectionAnchor doGetConnectionAnchor(Request request) {
			return getConnectionTargetAnchor(request);
		}
	}

	private class GetSourceAnchorAndBendpoints extends GetAnchorAndBendpoints {
		@Override
		protected ConnectionAnchor doGetConnectionAnchor(Request request) {
			return getConnectableEditPart().getSourceConnectionAnchor(request);
		}
	}
}
