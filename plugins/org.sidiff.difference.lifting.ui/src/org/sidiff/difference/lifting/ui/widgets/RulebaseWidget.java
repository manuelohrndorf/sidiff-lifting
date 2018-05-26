package org.sidiff.difference.lifting.ui.widgets;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.lifting.api.settings.LiftingSettings;
import org.sidiff.difference.lifting.api.settings.LiftingSettingsItem;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;
import org.sidiff.difference.lifting.api.util.PipelineUtils;
import org.sidiff.difference.lifting.ui.Activator;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.matching.input.InputModels;

public class RulebaseWidget extends AbstractWidget implements IWidgetSelection, IWidgetValidation, ISettingsChangedListener {

	private LiftingSettings settings;
	private InputModels inputModels;

	private Composite container;
	private Table ruleBaseTable;
	private TableViewer rulebaseTableViewer;

	private List<RuleBaseEntry> rulebases;

	public RulebaseWidget(RecognitionEngineWidget recognitionWidget, InputModels inputModels) {
		this.inputModels = inputModels;
		getRulebasesEntries();
	}

	public RulebaseWidget(InputModels inputModels) {
		this.inputModels = inputModels;
		getRulebasesEntries();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}

		/*
		 * Rulebase table
		 */

		// Table viewer composite:
		Composite rulebaseComposite = new Composite(container, SWT.NONE);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 100;
			rulebaseComposite.setLayoutData(data);
		}
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		rulebaseComposite.setLayout(tableColumnLayout);

		// Rulebase viewer:
		int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER;
		rulebaseTableViewer = new TableViewer(rulebaseComposite, style);

		// SWT table:
		ruleBaseTable = rulebaseTableViewer.getTable();
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			ruleBaseTable.setLayoutData(data);
		}
		ruleBaseTable.setHeaderVisible(true);
		ruleBaseTable.setLinesVisible(true);

		// ArrayContentProvider -> Table input is a java collection:
		rulebaseTableViewer.setContentProvider(ArrayContentProvider.getInstance());

		/*
		 * Active column
		 */
		TableViewerColumn activeColumn = new TableViewerColumn(rulebaseTableViewer, SWT.NONE);
		tableColumnLayout.setColumnData(activeColumn.getColumn(), new ColumnPixelData(25));
		activeColumn.getColumn().setText("");
		activeColumn.getColumn().setAlignment(SWT.CENTER);
		activeColumn.getColumn().setResizable(false);
		activeColumn.getColumn().setToolTipText("Activate/Deactivate rulebase for recognition engine");

		// LabelProvider for activeColumn:
		activeColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((String) cell.getElement()).toString());
			}
		});

		// Table header action - invert selection:
		activeColumn.getColumn().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (RuleBaseEntry ruleBase : rulebases) {
					ruleBase.activated = !ruleBase.activated;
				}
				rulebaseTableViewer.refresh();
				settings.setRuleBases(getSelection());
			}
		});

		// Setup check box for activeColumn:
		activeColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return "";
			}

			@Override
			public Image getImage(Object element) {
				if (((RuleBaseEntry) element).activated) {
					return Activator.getImageDescriptor("checked.png").createImage();
				} else {
					return Activator.getImageDescriptor("unchecked.png").createImage();
				}
			}
		});

		// Setup editing support for activeColumn:
		activeColumn.setEditingSupport(new EditingSupport(rulebaseTableViewer) {

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return new CheckboxCellEditor(ruleBaseTable);
			}

			@Override
			protected Object getValue(Object element) {
				return ((RuleBaseEntry) element).activated;
			}

			@Override
			protected void setValue(Object element, Object value) {
				RuleBaseEntry ruleBase = ((RuleBaseEntry) element);
				ruleBase.activated = !ruleBase.activated;

				rulebaseTableViewer.refresh(element);
				settings.setRuleBases(getSelection());
			}

		});

		/*
		 * Rulebase name column
		 */
		TableViewerColumn ruleBaseColumn = new TableViewerColumn(rulebaseTableViewer, SWT.NONE);
		tableColumnLayout.setColumnData(ruleBaseColumn.getColumn(), new ColumnWeightData(100));
		ruleBaseColumn.getColumn().setText("Rule Bases");
		ruleBaseColumn.getColumn().setToolTipText("Rulebase description name");

		// LabelProvider for activeColumn:
		ruleBaseColumn.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(((RuleBaseEntry) cell.getElement()).rulebase.getName());
			}
		});

		/*
		 * Set table input
		 */

		if(!rulebases.isEmpty()){
			updateRulebasesSelection();
			rulebaseTableViewer.setInput(rulebases);
		}else{
			setEnabled(false);
		}
		rulebaseTableViewer.refresh();

		if(this.settings.getRuleBases() == null) {
			this.settings.setRuleBases(this.getSelection());
		}
		
		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	private void getRulebasesEntries() {
		// Search registered rulebase extension points
		Set<ILiftingRuleBase> rulebaseInstances = PipelineUtils.getAvailableRulebases(
				inputModels.getDocumentTypes());

		// Create rulebase list for table viewer
		rulebases = new LinkedList<RuleBaseEntry>();

		for (ILiftingRuleBase rulebase : rulebaseInstances) {
			rulebases.add(new RuleBaseEntry(rulebase, true));
		}
		

		Collections.sort(rulebases);
	}
	
	private void updateRulebasesSelection() {
		if(settings.getRuleBases() != null) {
			for(RuleBaseEntry entry : rulebases) {
				entry.activated = false;
				for(ILiftingRuleBase rulebase : settings.getRuleBases()) {
					if(rulebase.getName().equals(entry.rulebase.getName())) {
						entry.activated = true;
						break;
					}
				}
			}
			if(rulebaseTableViewer != null) {
				rulebaseTableViewer.refresh();
			}
		}
	}

	public class RuleBaseEntry implements Comparable<RuleBaseEntry>{
		public ILiftingRuleBase rulebase;
		public Boolean activated;

		public RuleBaseEntry(ILiftingRuleBase rulebase, Boolean activated) {
			super();
			this.rulebase = rulebase;
			this.activated = activated;
		}

		@Override
		public int compareTo(RuleBaseEntry o) {
			return this.rulebase.getName().compareTo(o.rulebase.getName());
		}
	}

	public Set<ILiftingRuleBase> getSelection() {
		Set<ILiftingRuleBase> selectedRuleBases = new HashSet<ILiftingRuleBase>();
		
		for (RuleBaseEntry entry : rulebases) {
			if (entry.activated) {
				selectedRuleBases.add(entry.rulebase);
			}
		}
		return selectedRuleBases;
	}

	public List<RuleBaseEntry> getRulebases() {
		return rulebases;
	}

	@Override
	public boolean validate() {
		return !isLiftingEnabled() || !getSelection().isEmpty();
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			return ValidationMessage.OK;
		} else {
			return new ValidationMessage(ValidationType.ERROR, "Please select at least one rulebase!");
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if (ruleBaseTable == null) {
			throw new RuntimeException("Create controls first!");
		}
		ruleBaseTable.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if (ruleBaseTable != null) {
			ruleBaseTable.removeSelectionListener(listener);
		}
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if(item.equals(LiftingSettingsItem.RECOGNITION_ENGINE_MODE)){
			setEnabled(isLiftingEnabled());
		} else if(item.equals(LiftingSettingsItem.RULEBASES)) {
			updateRulebasesSelection();
			getWidgetCallback().requestValidation();
		}
	}

	private boolean isLiftingEnabled() {
		return settings != null && settings.getRecognitionEngineMode() != RecognitionEngineMode.NO_LIFTING;
	}

	public LiftingSettings getSettings() {
		return settings;
	}

	public void setSettings(LiftingSettings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		updateRulebasesSelection();
	}
}