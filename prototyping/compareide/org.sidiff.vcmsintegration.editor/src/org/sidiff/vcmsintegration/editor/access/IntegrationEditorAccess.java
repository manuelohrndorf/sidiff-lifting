package org.sidiff.vcmsintegration.editor.access;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.vcmsintegration.editor.extension.IEditorIntegration;

public class IntegrationEditorAccess {
	// TODO: change id when editors are migrated back
	private static final String EXTENSIONPOINT_ID = "org.sidiff.vcmsintegration.editor";
	private static IntegrationEditorAccess INSTANCE = null;
	private final List<IEditorIntegration> integrationEditors;

	public static IntegrationEditorAccess getInstance() {
		if (INSTANCE == null)
			INSTANCE = new IntegrationEditorAccess();
		return INSTANCE;
	}

	private IntegrationEditorAccess() {
		super();
		this.integrationEditors = new ArrayList<IEditorIntegration>();
		findIntegrationEditors();
	}

	public void findIntegrationEditors() {
		integrationEditors.clear();
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSIONPOINT_ID);
		for (IConfigurationElement e : config) {
			IEditorIntegration integrationEditor = null;
			try {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IEditorIntegration) {
					integrationEditor = (IEditorIntegration) o;
				} else {
					continue;
				}
				integrationEditors.add(integrationEditor);
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<IEditorIntegration> getIntegrationEditors() {
		return new ArrayList<IEditorIntegration>(integrationEditors);
	}

	/**
	 * Returns an editor
	 * 
	 * @param model
	 * @return
	 */
	public IEditorIntegration getIntegrationEditorForModel(Resource model) {
		IEditorIntegration candiate = null;
		boolean diagramSupported = false;
		for (IEditorIntegration de : integrationEditors) {
			if (de.supportsDiagramming(model)) {
				if (candiate == null) {
					diagramSupported = true;
					candiate = de;
				} else if (de.isDefaultEditorPresent() && de.isDiagramEditorPresent()) {
					candiate = de;
					diagramSupported = true;
					break;
				} else if (!candiate.isDefaultEditorPresent() && de.isDefaultEditorPresent()) {
					candiate = de;
					diagramSupported = true;
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
		IEditorIntegration candidate = null;
		for (IEditorIntegration de : integrationEditors) {
			if ((de.supportsModel(modelOrDiagramFile) || de.supportsDiagram(modelOrDiagramFile))) {
				candidate = de;
				break;
			}
		}
		if (candidate == null)
			return DefaultEditorIntegration.getInstance(modelOrDiagramFile);
		return candidate;
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
