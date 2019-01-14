package org.sidiff.integration.editor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.extension.ExtensionManager;
import org.sidiff.common.extension.IExtension.Description;
import org.sidiff.integration.editor.internal.DefaultEditorIntegration;

public class EditorIntegrationManager extends ExtensionManager<IEditorIntegration>{

	public EditorIntegrationManager(final Description<? extends IEditorIntegration> description) {
		super(description);
	}
	
	/**
	 * Selects a integration editor for a model type.
	 * Editors with a present Diagram Editor will
	 * be preferred.
	 * 
	 * @param model
	 * @return
	 */
	public IEditorIntegration getIntegrationEditorForModel(Resource model) {
		IEditorIntegration candidate = null;
		for (IEditorIntegration de : IEditorIntegration.MANAGER.getExtensions()) {
			if (de.supportsDiagramming(model) && de.isDiagramEditorPresent()) {
				candidate = de;
				break;
			} else if (de.supportsModel(model) && de.isDefaultEditorPresent()){
				candidate = de;
			}
		}
		if (candidate == null) {
			return new DefaultEditorIntegration(model.getURI());
		}
		return candidate;
	}

	/**
	 * Selects a integration editor for a model type.
	 * Editors with a present Diagram Editor will
	 * be preferred.
	 * 
	 * @param modelOrDiagramFile
	 * @return
	 */
	public IEditorIntegration getIntegrationEditorForModelOrDiagramFile(URI modelOrDiagramFile) {
		IEditorIntegration candidate = null;
		for (IEditorIntegration de : IEditorIntegration.MANAGER.getExtensions()) {
			if(de.supportsDiagram(modelOrDiagramFile) && de.isDiagramEditorPresent()) {
				return de;
			} else if(de.supportsModel(modelOrDiagramFile) && de.isDefaultEditorPresent()) {
				candidate = de;
			}
		}
		if(candidate == null) {
			return new DefaultEditorIntegration(modelOrDiagramFile);
		}
		return candidate;
	}

	/**
	 * {@link IEditorIntegration#getHighlightableElement(EObject)}
	 * @param element
	 * @return 
	 */
	public Collection<EObject> getHighlightableElements(EObject element){
		Set<EObject> candidates = new HashSet<EObject>();
		for (IEditorIntegration de : IEditorIntegration.MANAGER.getExtensions()) {
			candidates.addAll(de.getHighlightableElements(element));
		}
		if (!candidates.isEmpty()){
			return candidates;
		}
		return new DefaultEditorIntegration(element.eResource().getURI()).getHighlightableElements(element);
	}
}
