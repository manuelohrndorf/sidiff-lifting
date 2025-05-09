package org.sidiff.editrule.rulebase.ui.editor.columns.impl;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.editor.columns.AbstractRuleBaseColumn;

public class ColumnDerivedRefrences extends AbstractRuleBaseColumn {

	@Override
	public void createColumn(RulebaseEditor editor, TableViewerColumn derivedRefColumn, TableColumnLayout layout) {
		layout.setColumnData(derivedRefColumn.getColumn(), new ColumnPixelData(100));
		
		derivedRefColumn.getColumn().setText("Derived Features");
		derivedRefColumn.getColumn().setResizable(false);
		derivedRefColumn.getColumn().setAlignment(SWT.CENTER);
		derivedRefColumn.getColumn().setToolTipText("Uses derived features?");

		derivedRefColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return Boolean.toString(((RuleBaseItem)element).getEditRule().isUseDerivedFeatures());
			}
		});
	}
}
