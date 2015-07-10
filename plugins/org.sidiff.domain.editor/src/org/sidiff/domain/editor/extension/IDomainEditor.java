package org.sidiff.domain.editor.extension;

import java.io.FileNotFoundException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.ui.IEditorPart;

public interface IDomainEditor {

	/**
	 * Checks if a model file can be opened by the domain editor
	 * @param modelFile
	 * @return
	 */
	public boolean supportsModel(URI modelFile);
	/**
	 * Checks if a model file can be opened by the domain editor
	 * @param model
	 * @return
	 */
	public boolean supportsModel(Resource model);
	/**
	 * Checks if a diagram file can be opened by the domain editor.
	 * This does NOT mean that an editor is present or even specified
	 * @param diagramFile
	 * @return
	 */
	public boolean supportsDiagram(URI diagramFile);
	/**
	 * Checks if a diagram for the model can be created, saved, etc. by this editor
	 * @param modelFile
	 * @return
	 */
	public boolean supportsDiagramming(Resource model);
	/**
	 * Copies all diagram files for a model to a save path.
	 * Existing files will be replaced
	 * @param modelURI
	 * @param savePath
	 * @throws FileNotFoundException The diagram files could not be found
	 * @return File The main diagram file which can be opened by the editor
	 */
	public URI copyDiagram(URI modelURI, String savePath) throws FileNotFoundException;
	/**
	 * Returns the ID for the tree editor or null
	 * @return
	 */
	
	public String getTreeEditorID();
	/**
	 * Returns the ID for the diagram editor or null
	 * @return
	 */
	public String getDiagramEditorID();
	/**
	 * Checks if the tree editor is present in the current workspace
	 * @return true if present, false if not and null if the status cant't be determined
	 */
	public Boolean isTreeEditorPresent();
	/**
	 * Checks if the diagram editor is present in the current workspace
	 * @return
	 */
	public boolean isDiagramEditorPresent();
	/**
	 * Opens a model in the tree editor
	 * NOTE: If GMFAnimation is supported the editor must be opened with a org.eclipse.ui.IFileEditorInput
	 * @param modelURI
	 * @return Opened editor or null
	 */
	public IEditorPart openModelInTreeEditor(URI modelURI);
	/**
	 * Opens a diagram
	 * NOTE: If GMFAnimation is supported the editor must be opened with a org.eclipse.ui.IFileEditorInput
	 * @param diagramFile
	 * @return Opened editor or null
	 */
	public IEditorPart openDiagram(URI diagramFile);
	/**
	 * Opens a diagram
	 * NOTE: If GMFAnimation is supported the editor must be opened with a org.eclipse.ui.IFileEditorInput
	 * @param modelFile
	 * @return Opened editor or null
	 */
	public IEditorPart openDiagramForModel(URI modelFile);
	/**
	 * 
	 * @param diagramFile
	 * @return
	 */
	public boolean supportsGMFAnimation(URI diagramFile);
	/**
	 * Returns the EditingDomain of an EditorPart opened by this editor
	 * @param editorPart
	 * @return
	 */
	public EditingDomain getEditingDomain(IEditorPart editorPart);
	/**
	 * Returns the current Resource of an EditorPart opened by this editor
	 * @param editorPart
	 * @return
	 */
	public Resource getResource(IEditorPart editorPart);
}
