package org.sidiff.mutation.ui.widgets;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.rulebase.util.RuleBaseUtil;
import org.sidiff.mutation.config.MutationConfig;

public class MutationOperatorsWidget implements IWidget, IWidgetSelection,
		IWidgetValidation {

	private Composite container;
	private CheckboxTreeViewer operatorsTree;
	private Composite selectButtonContainer;
	private Button selectAllButton, deselectAllButton;

	private final Set<String> docTypes=new HashSet<>();
	private final Set<IRuleBase> rulebases = new HashSet<>();

	private ValidationMessage message;

	public MutationOperatorsWidget() {
	}


	/**
	 * IMPORTANT
	 * Wizards must validate their components after calling this method
	 * 
	 * @param targetModel
	 *            Target model or null for a generic resource
	 */
	public void setDocumentTypes(Set<String> docTypes) {
		if (container == null)
			throw new RuntimeException("Contols not created");
		this.docTypes.clear();
		this.rulebases.clear();
		if (docTypes != null){
			this.docTypes.addAll(docTypes);
			rulebases.addAll(RuleBaseUtil.getAvailableRulebases(this.docTypes));
		}
		operatorsTree.setInput(null);
		operatorsTree.setInput(rulebases);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(2, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
			container.setLayoutData(new GridData(GridData.FILL_BOTH));
		}

		/*
		 * Mutation Operators Tree
		 */
		operatorsTree = new CheckboxTreeViewer(container);
		operatorsTree.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		operatorsTree.setContentProvider(new ITreeContentProvider() {

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean hasChildren(Object element) {
				if (element instanceof Set || element instanceof IRuleBase) {
					return true;
				}
				return false;
			}

			@Override
			public Object getParent(Object element) {
				if (element instanceof EditRule) {
					EditRule editRule = (EditRule) element;
					return editRule.getRuleBaseItem().getRuleBase();
				}
				return null;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				return getChildren(inputElement);
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof Set) {
					return ((Set) parentElement).toArray();
				}
				if (parentElement instanceof IRuleBase) {
					return ((IRuleBase) parentElement).getActiveEditRules()
							.toArray();
				}
				return null;
			}
		});
		operatorsTree.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IRuleBase) {
					IRuleBase rulebase = (IRuleBase) element;
					return rulebase.getName();
				} else if (element instanceof EditRule) {
					EditRule editRule = (EditRule) element;
					return editRule.getExecuteModule().getName();
				}
				return null;
			}
		});

		operatorsTree.addCheckStateListener(new ICheckStateListener() {

			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getChecked()) {
					operatorsTree.setSubtreeChecked(event.getElement(), true);
				} else {
					operatorsTree.setSubtreeChecked(event.getElement(), false);
				}
			}
		});

		operatorsTree.setInput(rulebases);

		operatorsTree.setComparator(new ViewerComparator() {

			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				return super.compare(viewer, e1, e2);
			}

		});

		/*
		 * SelectAll/DeselectAll
		 */
		selectButtonContainer = new Composite(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			selectButtonContainer.setLayout(grid);
			selectButtonContainer.setLayoutData(new GridData(
					GridData.FILL_VERTICAL));
		}
		selectAllButton = new Button(selectButtonContainer, SWT.PUSH);
		selectAllButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		selectAllButton.setText("Select all");
		selectAllButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				operatorsTree.expandAll(); //HACK: For selecting all elements
				for (TreeItem item : operatorsTree.getTree().getItems()) {
					for (TreeItem child : item.getItems()) {
						child.setChecked(true);
					}
					item.setChecked(true);
				}
				operatorsTree.collapseAll();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		deselectAllButton = new Button(selectButtonContainer, SWT.PUSH);
		deselectAllButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		deselectAllButton.setText("Deselect all");
		deselectAllButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				operatorsTree.expandAll(); //HACK: For selecting all elements
				for (TreeItem item : operatorsTree.getTree().getItems()) {
					for (TreeItem child : item.getItems()) {
						child.setChecked(false);
					}
					item.setChecked(false);
					operatorsTree.collapseAll();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		/*
		 * ...
		 */
		Composite composite = new Composite(container, SWT.NONE);
		{
			GridLayout grid = new GridLayout(2, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			composite.setLayout(grid);
			composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		}
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		setDocumentTypes(docTypes);
		validate();
		return container;

	}

	public void updateConfiguration(MutationConfig config) {
		config.setMutationOperators(getEditRuleSelection());
	}

	private LinkedList<EditRule> getEditRuleSelection() {
		LinkedList<EditRule> editrules = new LinkedList<EditRule>();
		for (Object element : operatorsTree.getCheckedElements()) {
			if (element instanceof EditRule) {
				editrules.add((EditRule) element);
			}
		}
		return editrules;
	}

	@Override
	public boolean validate() {
		if (getEditRuleSelection().size() == 0) {
			 message = new ValidationMessage(ValidationType.ERROR,
			 "At least one operator has to be selected");
			 return false;
		}
		message = new ValidationMessage(ValidationType.OK, "");
		return true;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		return message;
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if (operatorsTree.getTree() == null) {
		}
		operatorsTree.getTree().addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		operatorsTree.getTree().removeSelectionListener(listener);
	}

	@Override
	public Composite getWidget() {
		return null;
	}

	@Override
	public void setLayoutData(Object layoutData) {

	}

}