package org.sidiff.editrule.rulebase.ui.editor.columns;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;

public class RuleBaseColumnLibrary {

	private static final String ATTR_ID = "id";
	private static final String ATTR_INSERT_AFTER = "insertAfter";

	/**
	 * The registration ID for a rulebase type (e.g. edit-rule, recognition-rules, ...).
	 */
	public static final String EXTENSION_POINT_ID_RULEBASE_COLUMN = "org.sidiff.editrule.rulebase.ui.column";
	
	/**
	 * Add all registered rulebase columns.
	 * 
	 * @param editor The target editor.
	 */
	public static void createAllColumns(final RulebaseEditor editor) {
		// ID -> Column
		Map<String, IConfigurationElement> columns = new LinkedHashMap<>();

		// Add all registered rulebase columns:
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID_RULEBASE_COLUMN)) {

			columns.put(configurationElement.getAttribute(ATTR_ID), configurationElement);
		}

		// Get insert order:
		if (columns.isEmpty()) {
			return;
		}

		List<IConfigurationElement> orderedColumns = new ArrayList<>(columns.size());
		orderColumns(columns, orderedColumns);
		
		for (IConfigurationElement columnExtension : orderedColumns) {
			try {
				Object rulebaseColumn = columnExtension.createExecutableExtension("class");
				
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

	private static void orderColumns(
			Map<String, IConfigurationElement> columns, 
			List<IConfigurationElement> orderedColumns) {

		for (IConfigurationElement columnExtension : columns.values()) {
			addColumnAndPrevious(columnExtension, columns, orderedColumns);
		}
	}

	private static int addColumnAndPrevious(
			IConfigurationElement column,
			Map<String, IConfigurationElement> columns, 
			List<IConfigurationElement> orderedColumns) {

		String actualColumn = column.getAttribute(ATTR_ID);
		if(actualColumn == null) {
			throw new IllegalArgumentException(ATTR_ID + " attribute of column " + column + " is not set");
		}

		if (actualColumn.equalsIgnoreCase("HEAD")) {
			return -1;
		} else if (actualColumn.equalsIgnoreCase("APPEND")) {
			return orderedColumns.size() - 1;
		}

		int columnIndex = orderedColumns.indexOf(column);
		if(columnIndex != -1) {
			return columnIndex;
		}

		String previousColumn = column.getAttribute(ATTR_INSERT_AFTER);
		if(previousColumn == null) {
			throw new IllegalArgumentException(ATTR_INSERT_AFTER + " attribute of column " + column + " is not set");
		}
		if (!previousColumn.equals(actualColumn) && columns.containsKey(previousColumn)) {
			IConfigurationElement previousColumnElement = columns.get(previousColumn);
			int previousColumnIndex = orderedColumns.indexOf(previousColumnElement);

			// Add previous columns:
			if (previousColumnIndex == -1) {
				previousColumnIndex = addColumnAndPrevious(previousColumnElement, columns, orderedColumns);
			}

			// Add actual column after previous column:
			columnIndex = previousColumnIndex + 1;
			orderedColumns.add(columnIndex, column);
		} else if (previousColumn.equalsIgnoreCase("HEAD")) {
			// Head:
			columnIndex = 0;
			orderedColumns.add(columnIndex, column);
		} else {
			// Append:
			orderedColumns.add(column);
			columnIndex = orderedColumns.size() - 1;
		}
		return columnIndex;
	}
}
