package org.sidiff.editrule.rulebase.project.ide.wizard;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.editrule.generator.settings.EditRuleGenerationSettings;
import org.sidiff.editrule.generator.ui.widgets.EditRuleGeneratorChooserWidget;
import org.sidiff.editrule.generator.ui.widgets.EditRuleGeneratorSettingsWidget;
import org.sidiff.editrule.generator.ui.widgets.EditRuleGeneratorWidget;
import org.sidiff.editrule.rulebase.project.ide.Activator;

public class RuleBaseProjectPage01 extends WizardPage implements IPageChangedListener{

	private String DEFAULT_MESSAGE = "Advanced RuleBase Project Settings";
	
	private Composite container;

	private EditRuleGeneratorWidget generatorWidget;
	private EditRuleGeneratorSettingsWidget generatorSettingsWidget;
	private EditRuleGeneratorChooserWidget chooserWidget;
	
	private EditRuleGenerationSettings settings;

	private SelectionAdapter validationListener;

	
	public RuleBaseProjectPage01(EditRuleGenerationSettings settings) {
		super("RuleBaseProjectPage1");

		this.setTitle("Advanced RuleBase Project Settings");
		this.setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.getPluginId(), "16x16_rulebase.gif"));

		this.settings = settings;
		// Listen for validation failures:
		validationListener =
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						validate();
					}
				};
	}

	@Override
	public void createControl(final Composite parent) {

		// Add scrolling to this page
		final Composite wrapper = new Composite(parent, SWT.NONE);
		{
			GridLayout layout = new GridLayout(1, false);
			layout.marginWidth = 0;
			layout.marginHeight = 0;
			wrapper.setLayout(layout);
		}

		final ScrolledComposite sc = new ScrolledComposite(wrapper, SWT.V_SCROLL);
		GridData sc_data = new GridData(SWT.FILL, SWT.FILL, true, true);
		{
			sc.setLayoutData(sc_data);

			sc.setExpandHorizontal(true);
			sc.setExpandVertical(true);
		}

		container = new Composite(sc, SWT.NULL);
		{
			GridLayout layout = new GridLayout(1, false);
			layout.marginWidth = 10;
			layout.marginHeight = 10;
			container.setLayout(layout);
		}

		sc.setContent(container);

		// Create widgets for this page:
		createWidgets();

		// Compute height:
		sc.setMinSize(container.computeSize(SWT.DEFAULT, SWT.DEFAULT, true));
		Point containerSize = container.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		sc_data.heightHint = containerSize.y;

		// Required to avoid an error in the system:
		setControl(wrapper);

		// Set dialog message:
		/* Note: Needed to force correct layout for scrollbar!? *
		 *       Set at least to setMessage(" ")!               */
		setMessage(DEFAULT_MESSAGE);
		
		// Initial validation:
		validate();
		
		chooserWidget.getrBtnGenerate().addSelectionListener(validationListener);
		chooserWidget.getrBtnManually().addSelectionListener(validationListener);
		generatorSettingsWidget.addSelectionListener(validationListener);
		generatorWidget.getList_generators().addSelectionListener(validationListener);
		generatorSettingsWidget.getTxtDefaultConfig().addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				validate();
				
			}
		});		
		generatorSettingsWidget.getTxtRefinedConfig().addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				validate();
				
			}
		});
	}

	private void createWidgets() {	

		// Chooser Widget
		chooserWidget = new EditRuleGeneratorChooserWidget();
		addWidget(container, chooserWidget);
		
		// EditRule Generator
		generatorWidget = new EditRuleGeneratorWidget();
		generatorWidget.setSettings(this.settings);
		addWidget(container, generatorWidget);
		
		// EditRule Generator Settings:
		generatorSettingsWidget = new EditRuleGeneratorSettingsWidget(this.settings);
		addWidget(container, generatorSettingsWidget);
		
		
		
	}

	private void addWidget(Composite parent, IWidget widget) {
		// Create controls:
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		widget.createControl(parent);
		widget.setLayoutData(data);

		// Add validation:
		if (widget instanceof IWidgetSelection) {
			((IWidgetSelection) widget).addSelectionListener(validationListener);
		}
	}

	private void validate() {
		setErrorMessage(null);
		setPageComplete(true);
		if (validateWidget(chooserWidget) && validateWidget(generatorWidget) &&	validateWidget(generatorSettingsWidget)) {
			setErrorMessage(null);
			setPageComplete(true);
		}
		generatorSettingsWidget.setEnabled(chooserWidget.getrBtnGenerate().getSelection());
		generatorWidget.setEnabled(chooserWidget.getrBtnGenerate().getSelection());
		if (chooserWidget.getrBtnManually().getSelection()) {
			setPageComplete(true);
			setErrorMessage(null);
			setMessage("Press Finish for defining Edit Rules manually");

		}
	}

	private Boolean validateWidget(IWidgetValidation widget) {
		if (!widget.validate()) {
			if(widget.getValidationMessage().getType().equals(ValidationType.ERROR)){
				setErrorMessage(widget.getValidationMessage().getMessage());
				setPageComplete(false);
			}else{
				setMessage(widget.getValidationMessage().getMessage(), IMessageProvider.WARNING);
			}
		}
		return widget.validate();
	}

	public EditRuleGeneratorSettingsWidget getGeneratorSettingsWidget() {
		return generatorSettingsWidget;
	}	
	
	public EditRuleGeneratorWidget getGeneratorWidget() {
		return generatorWidget;
	}	

	@Override
	public void pageChanged(PageChangedEvent event) {
		validate();
	}
}
