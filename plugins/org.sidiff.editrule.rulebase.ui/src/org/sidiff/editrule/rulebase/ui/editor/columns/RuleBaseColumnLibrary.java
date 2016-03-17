package org.sidiff.editrule.rulebase.ui.editor.columns;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;

public class RuleBaseColumnLibrary {

	/**
	 * The registration ID for a rulebase type (e.g. edit-rule, recognition-rules, ...).
	 */
	public static final String EXTENSION_POINT_ID_RULEBASE_COLUMN = "org.sidiff.editrule.rulebase.ui.column";
	
	/**
	 * Add all registered rulebase columns.
	 * 
	 * @param editor
	 *            The target editor.
	 */
	public static void createAllColumns(final RulebaseEditor editor) {
		
		// Add all registered rulebase columns:
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID_RULEBASE_COLUMN)) {
			
			// TODO: Insert order!
			try {
				Object rulebaseColumn = configurationElement.createExecutableExtension("class");
				
				if (rulebaseColumn instanceof IRuleBaseColumn) {
					TableViewer ruleViewer = editor.getRuleViewer();
					TableColumnLayout layout = editor.getTableColumnLayout();
					
					int style = ((IRuleBaseColumn) rulebaseColumn).getStyle();
					TableViewerColumn column = new TableViewerColumn(ruleViewer, style);
					((IRuleBaseColumn) rulebaseColumn).createColumn(editor, column, layout);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
