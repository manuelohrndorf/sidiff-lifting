package org.sidiff.mutation.ui.wizards;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.sidiff.mutation.config.MutationConfig;
import org.sidiff.mutation.ui.widgets.MutationOperatorsWidget;
import org.sidiff.mutation.ui.widgets.MutationOptionsSelectionWidget;
import org.sidiff.mutation.ui.widgets.NameWidget;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.Scope;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetModification;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.common.util.ui.widgets.IWidgetValidation.ValidationMessage;
import org.silift.common.util.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;

public class MutationWizardPage extends WizardPage {


	private static final String DEFAULT_TITLE = "Mutate Model";
	private static final String DEFAULT_MESSAGE = "Mutation configuration";

	private Composite container;
	private NameWidget nameWidget;
	private MutationOperatorsWidget operatorsWidget;
	private MutationOptionsSelectionWidget optionsWidget;

	private Resource targetModel;

	private ValidationMessage message = null;
	private ValidationListener validationListener;

	private class ValidationListener implements SelectionListener,
			ModifyListener {

		@Override
		public void modifyText(ModifyEvent e) {
			validate();
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			validate();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}

	}

	public MutationWizardPage(Resource targetModel) {
		this(null, targetModel);
	}

	/**
	 * 
	 * @param title
	 * @param targetModel
	 *            Feature model or null for a generic resource
	 */
	public MutationWizardPage(String title, Resource targetModel) {
		super("MutationWizardPage");

		this.setTitle(title != null ? title : DEFAULT_TITLE);
		this.setImageDescriptor(org.sidiff.mutation.ui.Activator
				.getImageDescriptor("icon.png"));

		validationListener = new ValidationListener();
		this.targetModel = targetModel;
	}

	@Override
	public void createControl(final Composite parent) {

		// Add scrolling to this page
		container = new Composite(parent, SWT.NONE);
		{
			GridLayout layout = new GridLayout(1, false);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			container.setLayout(layout);
		}

		/*
		 * Name widget
		 */
		nameWidget = new NameWidget(false, null, null, null);
		addWidget(container, nameWidget, new GridData(GridData.FILL_HORIZONTAL));

		/*
		 * Operators widget
		 */
		Group group0 = new Group(container, SWT.SHADOW_ETCHED_IN);
		group0.setText("Operators");
		group0.setLayout(new GridLayout(1, false));
		group0.setLayoutData(new GridData(GridData.FILL_BOTH
				| GridData.BEGINNING));
		operatorsWidget = new MutationOperatorsWidget();
		addWidget(group0, operatorsWidget, new GridData(GridData.FILL_BOTH));

		/*
		 * Options widget
		 */
		Group group1 = new Group(container, SWT.SHADOW_ETCHED_IN);
		group1.setText("Options");
		group1.setLayout(new GridLayout(1, false));
		// Assuming parent is grid layout
		group1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		optionsWidget = new MutationOptionsSelectionWidget();
		addWidget(group1, optionsWidget, new GridData(GridData.FILL_BOTH));

		/*
		 * Set doctypes
		 */
		Set<String> doctypes;
		if (targetModel != null){
			doctypes=EMFModelAccessEx.getDocumentTypes(targetModel, Scope.RESOURCE_SET);
		} else {
			doctypes = new HashSet<>();
		}
		operatorsWidget.setDocumentTypes(doctypes);
		
		/*
		 * Finishing
		 */
		// Required to avoid an error in the system
		setControl(container);
		// Set message
		setMessage(DEFAULT_MESSAGE);
		// Initial validation
		validate();
		//
		parent.setSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public void updateConfiguration(MutationConfig config) {
		if (container == null)
			throw new RuntimeException("Controls not created");
		config.setName(nameWidget.getName());
		operatorsWidget.updateConfiguration(config);
		optionsWidget.updateConfiguration(config);
		config.setTargetModel(targetModel);
	}

	private void addWidget(Composite parent, IWidget widget, Object layoutData) {
		// Create controls:
		widget.createControl(parent);
		widget.setLayoutData(layoutData);

		// Add validation:
		if (widget instanceof IWidgetSelection) {
			((IWidgetSelection) widget)
					.addSelectionListener(validationListener);
		}
		if (widget instanceof IWidgetModification) {
			((IWidgetModification) widget)
					.addModifyListener(validationListener);
		}
	}

	private void validate() {
		updateWizardMessage(validateTargetModel()
				&& validateWidget(operatorsWidget)
				&& validateWidget(optionsWidget) && validateWidget(nameWidget));
	}

	private boolean validateTargetModel() {
		if (targetModel == null) {
			message = new ValidationMessage(ValidationType.ERROR,
					"Error loading  model");
			return false;
		}
		return true;
	}

	private void updateWizardMessage(boolean valid) {
		if (!valid) {
			String errorMessage = (message != null ? message.getMessage()
					: "Unkown error");
			if (getErrorMessage() == null
					|| !getErrorMessage().equals(errorMessage))
				;
			setErrorMessage(errorMessage);
			setPageComplete(false);
		} else {
			setErrorMessage(null);
			if (message != null && message.getMessage() != null
					&& !message.getMessage().isEmpty()) {
				int messageType = (message.getType() == ValidationType.WARNING ? IMessageProvider.WARNING
						: IMessageProvider.NONE);
				if (getMessage() == null
						|| !getMessage().equals(message.getMessage())
						|| getMessageType() != messageType)
					setMessage(message.getMessage(), messageType);
			} else {
				if (getMessage() == null
						|| !getMessage().equals(DEFAULT_MESSAGE)
						|| getMessageType() != IMessageProvider.NONE)
					setMessage(DEFAULT_MESSAGE, IMessageProvider.NONE);
			}
			setPageComplete(true);
		}
	}

	private boolean validateWidget(IWidgetValidation widget) {
		boolean valid = widget.validate();
		message = widget.getValidationMessage();
		return valid;
	}

}
