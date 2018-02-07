package org.sidiff.slicer.structural.configuration.presentation;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;

/**
 * Interface of the configuration editor. Used by the slicing logic to access function and data of the configuration editor.
 * @author rmueller
 *
 */
public interface IConfigurationEditor
{
	/**
	 * Returns the slicing configuration which is the root element of the input file
	 * @return the slicing configuration
	 */
	SlicingConfiguration getConfig();
	
	/**
	 * Adds the command to the command stack and executes it.
	 * This also enables the "save"-option.
	 * @param cmd the command
	 */
	void executeCommand(Command cmd);
	
	/**
	 * Returns the editing domain of the slicing configuration.
	 * @return the editing domain
	 */
	EditingDomain getEditingDomain();

	/**
	 * Shows the Alternative Elements-View (if it is not already open) and gives it focus.
	 * The given object is selected in the alternative elements view and in the current viewer of the editor.
	 * @param object the object to show the alternative elements for
	 */
	void showAlternativeElements(EObject object);
}
