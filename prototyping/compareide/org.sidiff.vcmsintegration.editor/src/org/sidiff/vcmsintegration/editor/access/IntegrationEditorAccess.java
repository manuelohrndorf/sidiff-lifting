package org.sidiff.vcmsintegration.editor.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.vcmsintegration.editor.extension.IEditorIntegration;

public class IntegrationEditorAccess {

	private static IntegrationEditorAccess INSTANCE = null;
	private final List<IEditorIntegration> integrationEditors;

	public static IntegrationEditorAccess getInstance() {
		if (INSTANCE == null)
			INSTANCE = new IntegrationEditorAccess();
		return INSTANCE;
	}

	private IntegrationEditorAccess() {
		integrationEditors = getAllAvailableEditorIntegrations();
	}

	protected static List<IEditorIntegration> getAllAvailableEditorIntegrations() {
		List<IEditorIntegration> integrationEditors = new ArrayList<IEditorIntegration>();
		for (IConfigurationElement e : Platform.getExtensionRegistry().getConfigurationElementsFor(IEditorIntegration.EXTENSION_POINT_ID)) {
			try {
				integrationEditors.add((IEditorIntegration)e.createExecutableExtension(IEditorIntegration.EXTENSION_POINT_ARGUMENT));
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}
		return integrationEditors;
	}

	public List<IEditorIntegration> getIntegrationEditors() {
		return Collections.unmodifiableList(integrationEditors);
	}

	/**
	 * Returns an editor
	 * 
	 * @param model
	 * @return
	 */
	public IEditorIntegration getIntegrationEditorForModel(Resource model) {
		// TODO: cleanup this method
		IEditorIntegration candiate = null;
		boolean diagramSupported = false;
		for (IEditorIntegration de : integrationEditors) {
			if (de.supportsDiagramming(model)) {
				diagramSupported = true;
				if (candiate == null) {
					candiate = de;
				} else if (de.isDefaultEditorPresent() && de.isDiagramEditorPresent()) {
					candiate = de;
				} else if (!candiate.isDefaultEditorPresent() && de.isDefaultEditorPresent()) {
					candiate = de;
				}
			} else if (de.supportsModel(model)) {
				if (candiate == null) {
					candiate = de;
				} else if (de.isDefaultEditorPresent() && !diagramSupported) {
					candiate = de;
				}
			}
		}
		if (candiate == null)
			candiate = DefaultEditorIntegration.getInstance(model.getURI());
		return candiate;
	}

	/**
	 * Selects a integration editor for a model type. Editors with a present
	 * DIagram Editor will be preferred
	 * 
	 * @param modelOrDiagramFile
	 * @return
	 */
	public IEditorIntegration getIntegrationEditorForModelOrDiagramFile(URI modelOrDiagramFile) {
		for (IEditorIntegration de : integrationEditors) {
			if (de.supportsModel(modelOrDiagramFile) || de.supportsDiagram(modelOrDiagramFile)) {
				return de;
			}
		}
		return DefaultEditorIntegration.getInstance(modelOrDiagramFile);
	}

	/**
	 * {@link IEditorIntegration#getHighlightableElement(EObject)}
	 * 
	 * @param element
	 * @return
	 */
	public EObject getHighlightableElement(EObject element) {
		EObject candidate = null;
		for (IEditorIntegration de : integrationEditors) {
			EObject he = de.getHighlightableElement(element);
			if (he != null) {
				if (he != element)
					return he;
				candidate = he;
			}
		}
		if (candidate != null)
			return candidate;
		return DefaultEditorIntegration.getInstance(element.eResource().getURI()).getHighlightableElement(element);
	}
}
