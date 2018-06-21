package org.sidiff.slicer.structural.configuration.wizard.pages;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.sidiff.slicer.structural.configuration.IConstraintInterpreter;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.SlicingMode;
import org.sidiff.slicer.structural.configuration.util.ConfigurationUtil;
import org.sidiff.slicer.structural.configuration.wizard.ConfigurationWizardPlugin;

public class ConfigurationModelWizardPropertiesPage extends WizardPage
{
	protected static final List<String> ENCODINGS =
			Arrays.asList("UTF-8", "ASCII", "UTF-16", "UTF-16BE", "UTF-16LE", "ISO-8859-1"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

	protected Combo encodingField;

	protected Combo slicingModeField;

	protected Button multiplicityField;

	protected Combo constraintIDField;

	protected Text nameField;

	protected Text descriptionField;
	
	protected Button advancedButton;
	
	protected boolean advancedSection = false;

	protected final ModifyListener modifyListener = new ModifyListener()
	{
		@Override
		public void modifyText(ModifyEvent e)
		{
			setPageComplete(validatePage());
		}
	};

	public ConfigurationModelWizardPropertiesPage()
	{
		super(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_page_properties_title")); //$NON-NLS-1$
		setTitle(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_page_properties_title")); //$NON-NLS-1$
		setDescription(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_page_properties")); //$NON-NLS-1$
	}

	public boolean getAdvancedSection()
	{
		return advancedSection;
	}

	public void setAdvancedSection(boolean b)
	{
		advancedSection = b;
	}

	@Override
	public void createControl(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NONE);
		{
			GridLayout layout = new GridLayout();
			layout.numColumns = 2;
			layout.verticalSpacing = 12;
			composite.setLayout(layout);

			GridData data = new GridData();
			data.verticalAlignment = GridData.FILL;
			data.grabExcessVerticalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			composite.setLayoutData(data);
		}

		// encoding
		Label encodingLabel = new Label(composite, SWT.LEFT);
		encodingLabel.setText(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_property_encoding")); //$NON-NLS-1$
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			encodingLabel.setLayoutData(data);
		}

		encodingField = new Combo(composite, SWT.READ_ONLY);
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			encodingField.setLayoutData(data);
		}
		for(String encoding : ENCODINGS)
		{
			encodingField.add(encoding);
		}
		encodingField.select(0);
		encodingField.addModifyListener(modifyListener);

		// name
		Label nameLabel = new Label(composite, SWT.LEFT);
		nameLabel.setText(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_property_name")); //$NON-NLS-1$
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			nameLabel.setLayoutData(data);
		}

		nameField = new Text(composite, SWT.BORDER);
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			nameField.setLayoutData(data);
		}
		nameField.addModifyListener(modifyListener);

		//Advanced Button
		advancedButton = new Button(composite, SWT.BORDER);
		advancedButton.setText(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_property_advanced")); //$NON-NLS-1$
		
		
		Label placeholder = new Label(composite, SWT.RIGHT);
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			placeholder.setLayoutData(data);
		}
		placeholder.setVisible(false);

		// description
		Label descriptionLabel = new Label(composite, SWT.LEFT);
		descriptionLabel.setText(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_property_desc")); //$NON-NLS-1$
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			descriptionLabel.setLayoutData(data);
		}
		descriptionLabel.setVisible(getAdvancedSection());

		descriptionField = new Text(composite, SWT.BORDER);
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			descriptionLabel.setLayoutData(data);
			data.grabExcessHorizontalSpace = true;
			descriptionField.setLayoutData(data);
		}
		descriptionField.addModifyListener(modifyListener);
		descriptionField.setVisible(getAdvancedSection());

		// slicing mode
		Label modeLabel = new Label(composite, SWT.LEFT);
		modeLabel.setText(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_property_mode")); //$NON-NLS-1$
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			modeLabel.setLayoutData(data);
		}
		modeLabel.setVisible(getAdvancedSection());

		slicingModeField = new Combo(composite, SWT.READ_ONLY);
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			slicingModeField.setLayoutData(data);
		}
		for(SlicingMode mode : SlicingMode.VALUES)
		{
			slicingModeField.add(mode.toString());
		}
		slicingModeField.select(0);
		slicingModeField.addModifyListener(modifyListener);
		slicingModeField.setVisible(getAdvancedSection());

		// check multiplicity
		Label multiplicityLabel = new Label(composite, SWT.LEFT);
		multiplicityLabel.setText(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_property_multiplicity")); //$NON-NLS-1$
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			multiplicityLabel.setLayoutData(data);
		}
		multiplicityLabel.setVisible(getAdvancedSection());

		multiplicityField = new Button(composite, SWT.CHECK);
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			multiplicityField.setLayoutData(data);
		}
		multiplicityField.setVisible(getAdvancedSection());

		// constraint interpreter id
		Label constraintIDLabel = new Label(composite, SWT.LEFT);
		constraintIDLabel.setText(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_property_constraintinterpreter")); //$NON-NLS-1$
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			constraintIDLabel.setLayoutData(data);
		}
		constraintIDLabel.setVisible(getAdvancedSection());

		constraintIDField = new Combo(composite, SWT.READ_ONLY);
		{
			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			constraintIDField.setLayoutData(data);
		}
		for(IConstraintInterpreter constraint : ConfigurationUtil.getAllAvailableConstraintInterpreters())
		{
			constraintIDField.add(constraint.getID());
		}
		constraintIDField.select(0);
		constraintIDField.addModifyListener(modifyListener);
		constraintIDField.setVisible(getAdvancedSection());

		advancedButton.addListener(SWT.Selection, new Listener()
		{
			@Override
			public void handleEvent(Event event)
			{
				switch(event.type)
				{
					case SWT.Selection:
						if(advancedSection == false)
						{
							setAdvancedSection(true);
						}
						else if(advancedSection == true)
						{
							setAdvancedSection(false);
						}
						descriptionLabel.setVisible(getAdvancedSection());
						descriptionField.setVisible(getAdvancedSection());
						modeLabel.setVisible(getAdvancedSection());
						slicingModeField.setVisible(getAdvancedSection());
						multiplicityLabel.setVisible(getAdvancedSection());
						multiplicityField.setVisible(getAdvancedSection());
						constraintIDLabel.setVisible(getAdvancedSection());
						constraintIDField.setVisible(getAdvancedSection());
				}

			}
		});

		setPageComplete(validatePage());
		setControl(composite);
	}

	public boolean validatePage()
	{
		return !nameField.getText().isEmpty();
	}

	public String getEncoding()
	{
		return encodingField.getText();
	}

	public String getConstraintInterpreterID()
	{
		return constraintIDField.getText();
	}

	public SlicingMode getSlicingMode()
	{
		return SlicingMode.get(slicingModeField.getText());
	}

	public String getConfigurationName()
	{
		return nameField.getText();
	}

	public String getConfigurationDescription()
	{
		return descriptionField.getText();
	}

	public boolean isCheckMultiplicity()
	{
		return multiplicityField.getSelection();
	}

	public void updateSlicingConfiguration(SlicingConfiguration slicingConfiguration)
	{
		if(!getConfigurationDescription().isEmpty())
		{
			slicingConfiguration.setDescription(getConfigurationDescription());
		}

		slicingConfiguration.setSlicingMode(getSlicingMode());

		if(!getConfigurationName().isEmpty())
		{
			slicingConfiguration.setName(getConfigurationName());
		}

		slicingConfiguration.setCheckMultiplicity(isCheckMultiplicity());

		if(!getConstraintInterpreterID().isEmpty())
		{
			slicingConfiguration.setConstraintInterpreterID(getConstraintInterpreterID());
		}
	}
}
