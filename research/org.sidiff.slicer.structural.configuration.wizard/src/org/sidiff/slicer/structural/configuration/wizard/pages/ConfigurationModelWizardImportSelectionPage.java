package org.sidiff.slicer.structural.configuration.wizard.pages;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PatternFilter;
import org.sidiff.slicer.structural.configuration.ConfigurationPackage;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.provider.ConfigurationItemProviderAdapterFactory;
import org.sidiff.slicer.structural.configuration.wizard.ConfigurationWizardPlugin;

public class ConfigurationModelWizardImportSelectionPage extends WizardPage
{
	private static final int TABLE_WIDTH = 300;
	private static final int TABLE_HEIGHT = 250;

	protected ComposedAdapterFactory adapterFactory;
	protected ILabelProvider labelProvider;
	protected IContentProvider contentProvider;
	protected SlicingConfiguration slicingConfiguration;
	protected ItemProvider values;
	protected ItemProvider choiceOfValues;

	public ConfigurationModelWizardImportSelectionPage(SlicingConfiguration slicingConfiguration)
	{
		super(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_page_imports_title")); //$NON-NLS-1$
		setTitle(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_page_imports_title")); //$NON-NLS-1$
		setDescription(ConfigurationWizardPlugin.INSTANCE.getString("_UI_ConfigurationModelWizard_page_imports")); //$NON-NLS-1$
		this.slicingConfiguration = slicingConfiguration;
		this.initializeAdapterFactory();
		final IItemLabelProvider itemLabelProvider = new AdapterFactoryItemDelegator(adapterFactory)
				.getPropertyDescriptor(slicingConfiguration, ConfigurationPackage.Literals.SLICING_CONFIGURATION__IMPORTS)
				.getLabelProvider(slicingConfiguration);
		this.labelProvider = new LabelProvider()
		{
			@Override
			public String getText(Object object)
			{
				return itemLabelProvider.getText(object);
			}

			@Override
			public Image getImage(Object object)
			{
				return ExtendedImageRegistry.getInstance().getImage(itemLabelProvider.getImage(object));
			}
		};
		this.choiceOfValues = new ItemProvider(adapterFactory, getEPackages());
		this.values = new ItemProvider(adapterFactory, slicingConfiguration.getImports());
		this.contentProvider = new AdapterFactoryContentProvider(adapterFactory);
	}

	/**
	 * Initializes the adapter factory
	 */
	protected void initializeAdapterFactory()
	{
		adapterFactory = new ComposedAdapterFactory();
		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ConfigurationItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
	}

	private Set<EPackage> getEPackages()
	{
		Set<EPackage> packages = new HashSet<EPackage>();
		// key set is copied because getEPackage modifies the map, resulting in a ConcurrentModificationException
		Set<String> nsURIs = new HashSet<String>(EPackage.Registry.INSTANCE.keySet());
		for(String key : nsURIs)
		{
			packages.add(EPackage.Registry.INSTANCE.getEPackage(key));
		}
		return packages;
	}

	@Override
	public void createControl(Composite parent)
	{
		Composite contents = new Composite(parent, SWT.NONE);
		{
			contents.setLayout(new GridLayout());
			contents.setLayoutData(new GridData(GridData.FILL_BOTH));
		}

		GridLayout contentsGridLayout = (GridLayout)contents.getLayout();
		contentsGridLayout.numColumns = 3;

		GridData contentsGridData = (GridData)contents.getLayoutData();
		contentsGridData.horizontalAlignment = SWT.FILL;
		contentsGridData.verticalAlignment = SWT.FILL;

		Group filterGroupComposite = new Group(contents, SWT.NONE);
		filterGroupComposite.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Choices_pattern_group")); //$NON-NLS-1$
		filterGroupComposite.setLayout(new GridLayout(2, false));
		filterGroupComposite.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false, 3, 1));

		Label label = new Label(filterGroupComposite, SWT.NONE);
		label.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Choices_pattern_label")); //$NON-NLS-1$

		Text patternText = new Text(filterGroupComposite, SWT.BORDER);
		patternText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite choiceComposite = new Composite(contents, SWT.NONE);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalAlignment = SWT.END;
			choiceComposite.setLayoutData(data);

			GridLayout layout = new GridLayout();
			data.horizontalAlignment = SWT.FILL;
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.numColumns = 1;
			choiceComposite.setLayout(layout);
		}

		Label choiceLabel = new Label(choiceComposite, SWT.NONE);
		choiceLabel.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Choices_label")); //$NON-NLS-1$
		GridData choiceLabelGridData = new GridData();
		choiceLabelGridData.verticalAlignment = SWT.FILL;
		choiceLabelGridData.horizontalAlignment = SWT.FILL;
		choiceLabel.setLayoutData(choiceLabelGridData);

		final Table choiceTable = new Table(choiceComposite, SWT.MULTI | SWT.BORDER);
		{
			GridData choiceTableGridData = new GridData();
			choiceTableGridData.widthHint = TABLE_WIDTH;
			choiceTableGridData.heightHint = TABLE_HEIGHT;
			choiceTableGridData.verticalAlignment = SWT.FILL;
			choiceTableGridData.horizontalAlignment = SWT.FILL;
			choiceTableGridData.grabExcessHorizontalSpace = true;
			choiceTableGridData.grabExcessVerticalSpace = true;
			choiceTable.setLayoutData(choiceTableGridData);
		}

		final TableViewer choiceTableViewer = new TableViewer(choiceTable);
		{
			choiceTableViewer.setContentProvider(new AdapterFactoryContentProvider(new AdapterFactoryImpl()));
			choiceTableViewer.setLabelProvider(labelProvider);
			final PatternFilter filter = new PatternFilter()
			{
				@Override
				protected boolean isParentMatch(Viewer viewer, Object element)
				{
					return viewer instanceof AbstractTreeViewer && super.isParentMatch(viewer, element);
				}
			};
			choiceTableViewer.addFilter(filter);
			if(patternText != null)
			{
				patternText.addModifyListener(new ModifyListener()
				{
					@Override
					public void modifyText(ModifyEvent e)
					{
						filter.setPattern(((Text)e.widget).getText());
						choiceTableViewer.refresh();
					}
				});
			}
			choiceTableViewer.setInput(choiceOfValues);
		}

		Composite controlButtons = new Composite(contents, SWT.NONE);
		GridData controlButtonsGridData = new GridData();
		controlButtonsGridData.verticalAlignment = SWT.FILL;
		controlButtonsGridData.horizontalAlignment = SWT.FILL;
		controlButtons.setLayoutData(controlButtonsGridData);

		GridLayout controlsButtonGridLayout = new GridLayout();
		controlButtons.setLayout(controlsButtonGridLayout);

		new Label(controlButtons, SWT.NONE);

		final Button addButton = new Button(controlButtons, SWT.PUSH);
		addButton.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Add_label")); //$NON-NLS-1$
		GridData addButtonGridData = new GridData();
		addButtonGridData.verticalAlignment = SWT.FILL;
		addButtonGridData.horizontalAlignment = SWT.FILL;
		addButton.setLayoutData(addButtonGridData);

		final Button removeButton = new Button(controlButtons, SWT.PUSH);
		removeButton.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Remove_label")); //$NON-NLS-1$
		GridData removeButtonGridData = new GridData();
		removeButtonGridData.verticalAlignment = SWT.FILL;
		removeButtonGridData.horizontalAlignment = SWT.FILL;
		removeButton.setLayoutData(removeButtonGridData);

		Label spaceLabel = new Label(controlButtons, SWT.NONE);
		GridData spaceLabelGridData = new GridData();
		spaceLabelGridData.verticalSpan = 2;
		spaceLabel.setLayoutData(spaceLabelGridData);

		final Button upButton = new Button(controlButtons, SWT.PUSH);
		upButton.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Up_label")); //$NON-NLS-1$
		GridData upButtonGridData = new GridData();
		upButtonGridData.verticalAlignment = SWT.FILL;
		upButtonGridData.horizontalAlignment = SWT.FILL;
		upButton.setLayoutData(upButtonGridData);

		final Button downButton = new Button(controlButtons, SWT.PUSH);
		downButton.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Down_label")); //$NON-NLS-1$
		GridData downButtonGridData = new GridData();
		downButtonGridData.verticalAlignment = SWT.FILL;
		downButtonGridData.horizontalAlignment = SWT.FILL;
		downButton.setLayoutData(downButtonGridData);

		Composite featureComposite = new Composite(contents, SWT.NONE);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.horizontalAlignment = SWT.END;
			featureComposite.setLayoutData(data);

			GridLayout layout = new GridLayout();
			data.horizontalAlignment = SWT.FILL;
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			layout.numColumns = 1;
			featureComposite.setLayout(layout);
		}

		Label featureLabel = new Label(featureComposite, SWT.NONE);
		featureLabel.setText(EMFEditUIPlugin.INSTANCE.getString("_UI_Feature_label")); //$NON-NLS-1$
		GridData featureLabelGridData = new GridData();
		featureLabelGridData.horizontalSpan = 2;
		featureLabelGridData.horizontalAlignment = SWT.FILL;
		featureLabelGridData.verticalAlignment = SWT.FILL;
		featureLabel.setLayoutData(featureLabelGridData);

		final Table featureTable = new Table(featureComposite, SWT.MULTI | SWT.BORDER);
		GridData featureTableGridData = new GridData();
		featureTableGridData.widthHint = TABLE_WIDTH;
		featureTableGridData.heightHint = TABLE_HEIGHT;
		featureTableGridData.verticalAlignment = SWT.FILL;
		featureTableGridData.horizontalAlignment = SWT.FILL;
		featureTableGridData.grabExcessHorizontalSpace = true;
		featureTableGridData.grabExcessVerticalSpace = true;
		featureTable.setLayoutData(featureTableGridData);

		final TableViewer featureTableViewer = new TableViewer(featureTable);
		featureTableViewer.setContentProvider(contentProvider);
		featureTableViewer.setLabelProvider(labelProvider);
		featureTableViewer.setInput(values);
		final EList<Object> children = values.getChildren();
		if(!children.isEmpty())
		{
			featureTableViewer.setSelection(new StructuredSelection(children.get(0)));
		}
		choiceTableViewer.addDoubleClickListener(event -> {
				if(addButton.isEnabled())
				{
					addButton.notifyListeners(SWT.Selection, null);
				}
			});
		featureTableViewer.addDoubleClickListener(event -> {
				if(removeButton.isEnabled())
				{
					removeButton.notifyListeners(SWT.Selection, null);
				}
			});
		choiceTableViewer.addFilter(new ViewerFilter()
		{
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element)
			{
				return !values.getChildren().contains(element);
			}
		});

		upButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				IStructuredSelection selection = (IStructuredSelection)featureTableViewer.getSelection();
				int minIndex = 0;
				for(Iterator<?> i = selection.iterator(); i.hasNext();)
				{
					Object value = i.next();
					int index = children.indexOf(value);
					children.move(Math.max(index - 1, minIndex++), value);
				}

				updateSlicingConfiguration();
			}
		});

		downButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				IStructuredSelection selection = (IStructuredSelection)featureTableViewer.getSelection();
				int maxIndex = children.size() - 1;
				List<?> objects = selection.toList();
				for(ListIterator<?> i = objects.listIterator(objects.size()); i.hasPrevious();)
				{
					Object value = i.previous();
					int index = children.indexOf(value);
					children.move(Math.min(index + 1, maxIndex--), value);
				}

				updateSlicingConfiguration();
			}
		});

		addButton.addSelectionListener(new SelectionAdapter()
		{
			// event is null when choiceTableViewer is double clicked
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				if(choiceTableViewer != null)
				{
					IStructuredSelection selection = (IStructuredSelection)choiceTableViewer.getSelection();
					for(Iterator<?> i = selection.iterator(); i.hasNext();)
					{
						Object value = i.next();
						if(!children.contains(value))
						{
							children.add(value);
						}
					}
					featureTableViewer.refresh();
					featureTableViewer.setSelection(selection);
					choiceTableViewer.refresh();
				}

				updateSlicingConfiguration();
			}
		});

		removeButton.addSelectionListener(new SelectionAdapter()
		{
			// event is null when featureTableViewer is double clicked 
			@Override
			public void widgetSelected(SelectionEvent event)
			{
				IStructuredSelection selection = (IStructuredSelection)featureTableViewer.getSelection();
				Object firstValue = null;
				for(Iterator<?> i = selection.iterator(); i.hasNext();)
				{
					Object value = i.next();
					if(firstValue == null)
					{
						firstValue = value;
					}
					children.remove(value);
				}

				if(!children.isEmpty())
				{
					featureTableViewer.setSelection(new StructuredSelection(children.get(0)));
				}

				if(choiceTableViewer != null)
				{
					choiceTableViewer.refresh();
					choiceTableViewer.setSelection(selection);
				}

				updateSlicingConfiguration();
			}
		});

		setPageComplete(validatePage());
		setControl(contents);
	}

	protected void updateSlicingConfiguration()
	{
		slicingConfiguration.getImports().clear();
		for(Object object : values.getChildren())
		{
			if(object instanceof EPackage)
			{
				slicingConfiguration.getImports().add((EPackage)object);
			}
		}

		setPageComplete(validatePage());
	}

	protected boolean validatePage()
	{
		return !values.getChildren().isEmpty();
	}

	@Override
	public void dispose()
	{
		super.dispose();

		if(adapterFactory != null)
		{
			adapterFactory.dispose();
		}
	}
}
