package org.sidiff.editrule.rulebase.ui.editor.columns;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;

public interface IRuleBaseColumn {

	/**
	 * Initializes a new (given) column.
	 * 
	 * @param editor The editor of the column.
	 * @param column The column to initialize.
	 * @param layout The layout to initialize.
	 */
	void createColumn(RulebaseEditor editor, TableViewerColumn column, TableColumnLayout layout);

	/**
	 * @return {@link SWT#NONE}, {@link SWT#LEFT}, {@link SWT#CENTER} , {@link SWT#RIGHT}
	 */
	int getStyle();
}
