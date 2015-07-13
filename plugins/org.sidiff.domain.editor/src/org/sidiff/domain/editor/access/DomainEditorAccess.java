package org.sidiff.domain.editor.access;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.domain.editor.extension.IDomainEditor;

public class DomainEditorAccess {
	private static final String EXTENSIONPOINT_ID = "org.sidiff.domain.editor";
	private static DomainEditorAccess INSTANCE = null;
	private final List<IDomainEditor> domainEditors;

	public static DomainEditorAccess getInstance() {
		if (INSTANCE == null)
			INSTANCE = new DomainEditorAccess();
		return INSTANCE;
	}

	private DomainEditorAccess() {
		super();
		this.domainEditors = new ArrayList<IDomainEditor>();
		findDomainEditors();
	}

	public void findDomainEditors() {
		domainEditors.clear();
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSIONPOINT_ID);
		for (IConfigurationElement e : config) {
			IDomainEditor domainEditor = null;
			try {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof IDomainEditor){
					domainEditor = (IDomainEditor) o;
				} else {
					continue;
				}
				domainEditors.add(domainEditor);
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<IDomainEditor> getDomainEditors() {
		return new ArrayList<IDomainEditor>(domainEditors);
	}

	/**
	 * Returns an editor
	 * 
	 * @param model
	 * @return
	 */
	public IDomainEditor getDomainEditorForModel(Resource model) {
		IDomainEditor candiate = null;
		boolean diagramSupported=false;
		for (IDomainEditor de : domainEditors) {
			if (de.supportsDiagramming(model)) {
				if (candiate == null) {
					diagramSupported=true;
					candiate = de;
				} else if (de.isTreeEditorPresent()
						&& de.isDiagramEditorPresent()) {
					candiate = de;
					diagramSupported=true;
					break;
				} else if (!candiate.isTreeEditorPresent()
						&& de.isTreeEditorPresent()) {
					candiate = de;
					diagramSupported=true;
				}
			} else if (de.supportsModel(model)){
				if (candiate == null){
					candiate = de;
				} else if (de.isTreeEditorPresent() && !diagramSupported){
					candiate = de;
				}
			}
		}
		if (candiate == null) candiate = DefaultDomainEditor.getInstance();
		return candiate;
	}

	/**
	 * Selects a domain editor for a model type Only editors where a Tree Editor
	 * is present will be selected. Editors with a present DIagram Editor will
	 * be preferred
	 * 
	 * @param modelOrDiagramFile
	 * @return
	 */
	public IDomainEditor getDomainEditorForModelOrDiagramFile(URI modelOrDiagramFile) {
		IDomainEditor candiate = null;
		for (IDomainEditor de : domainEditors) {
			if ((de.supportsModel(modelOrDiagramFile) || de.supportsDiagram(modelOrDiagramFile))
					&& de.isTreeEditorPresent()) {
				if (candiate == null) {
					candiate = de;
				} else if (de.isDiagramEditorPresent()) {
					candiate = de;
					break;
				}
			}
		}
		if (candiate == null) return DefaultDomainEditor.getInstance();
		return candiate;
	}

	/**
	 * {@link IDomainEditor#getHighlightableElement(EObject)}
	 * @param element
	 * @return 
	 */
	public EObject getHighlightableElement(EObject element){
		EObject candidate=null;
		for (IDomainEditor de : domainEditors) {
			EObject he=de.getHighlightableElement(element);
			if (he != null){
				if (he != element) return he;
				candidate=he;
			}
		}
		if (candidate != null) return candidate;
		return DefaultDomainEditor.getInstance().getHighlightableElement(element);
	}
}
