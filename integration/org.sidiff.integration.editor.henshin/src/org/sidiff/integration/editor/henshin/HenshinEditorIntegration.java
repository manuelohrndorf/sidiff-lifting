package org.sidiff.integration.editor.henshin;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditor;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IEditorPart;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.integration.editor.extension.BasicEditorIntegration;

public class HenshinEditorIntegration extends BasicEditorIntegration {

	public HenshinEditorIntegration() {
		super("org.eclipse.emf.henshin.presentation.HenshinEditorID",
				"org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorID", HenshinPackage.eINSTANCE.getNsURI(),
				"henshin", "henshin_diagram");
	}

	@Override
	public boolean supportsGMFAnimation(URI diagramFile) {
		return diagramFileExt.equals(diagramFile.fileExtension().toLowerCase());
	}

	@Override
	public EditingDomain getEditingDomain(IEditorPart editorPart) {
		if (editorPart instanceof HenshinDiagramEditor) {
			HenshinDiagramEditor editor = (HenshinDiagramEditor) editorPart;
			return editor.getEditingDomain();
		} else {
			// TODO Exception?
			return null;
		}
	}

	@Override
	public Resource getResource(IEditorPart editorPart) {
		if (editorPart instanceof HenshinDiagramEditor) {
			HenshinDiagramEditor editor = (HenshinDiagramEditor) editorPart;
			return editor.getDiagram().getElement().eResource();
		} else {
			// TODO Exception?
			return null;
		}
	}

	/**
	 * Here, we override the standard behavior of the super class which just
	 * returns a set which contains only the given input element. In particular,
	 * we implement a special tratment for preserved nodes/attributes/edges.<br/>
	 * In Henshin, preserved elements in the view of the editor always have just
	 * one representative element in the data layer of the editor, namely the
	 * LHS element of the preserved node. However, also the RHS element is
	 * highlightable by the same preserved node element in the editor view.
	 * Thus, for a given Henshin node which is preserved, we return both the LHS
	 * and the RHS nodes as highlightable objects.
	 */
	@Override
	public Collection<EObject> getHighlightableElements(EObject element) {
		Collection<EObject> res = super.getHighlightableElements(element);

		if (element instanceof Node) {
			Node node = (Node) element;
			
			if ((node.getGraph() != null) && (node.getGraph().getRule() != null)) {
				Rule rule = (Rule) node.getGraph().getRule();
				Node remoteNode = HenshinRuleAnalysisUtilEx.getRemoteNode(rule.getMappings(), node);
				
				if (remoteNode != null) {
					res.add(remoteNode);
				}
			}
		}
		if (element instanceof Edge) {
			Edge edge = (Edge) element;
			
			if ((edge.getGraph() != null) && (edge.getGraph().getRule() != null)) {
				Rule rule = (Rule) edge.getGraph().getRule();
				Edge remoteEdge = HenshinRuleAnalysisUtilEx.getRemoteEdge(rule.getMappings(), edge);
				
				if (remoteEdge != null) {
					res.add(remoteEdge);
				}
			}
		}
		if (element instanceof Attribute) {
			Attribute attribute = (Attribute) element;
			
			if ((attribute.getNode() != null) && (attribute.getNode().getGraph() != null) && (attribute.getNode().getGraph().getRule() != null)) {
				Attribute remoteAttribute = HenshinRuleAnalysisUtilEx.getRemoteAttribute(attribute);
				
				if (remoteAttribute != null) {
					res.add(remoteAttribute);
				}
			}
		}

		return res;
	}
}
