package org.sidiff.slicer.structural.configuration.presentation;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.ui.ViewerPane;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.ui.celleditor.AdapterFactoryTreeEditor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DecoratingStyledCellLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.sidiff.slicer.structural.configuration.preferences.ColorPreferencePage;
import org.sidiff.slicer.structural.configuration.preferences.PreferenceConstants;

/**
 * A ViewerPane split with a SashForm to show an imported model with a CheckboxTreeViewer
 * and a view containing explanations for the colors used by the model viewer.
 * @author rmueller
 *
 */
class ImportedModelViewerPane extends ViewerPane implements IPropertyChangeListener
{
	private List<Color> disposeableColors;

	protected ConfigurationEditor editor;
	protected AdapterFactory adapterFactory;
	protected EPackage modelPackage;
	protected SlicingLogicDelegate slicingLogicDelegate;
	
	protected Control helpControl;
	protected SashForm sashForm;

	protected Label defaultLabel;
	protected Label referencedElementLabel;
	protected Label referencedElementMandatoryLabel;
	protected Label danglingReferenceLabel;
	
	public ImportedModelViewerPane(IWorkbenchPage page, ConfigurationEditor editor, AdapterFactory adapterFactory, EPackage modelPackage)
	{
		super(page, editor);
		this.editor = editor;
		this.adapterFactory = adapterFactory;
		this.modelPackage = modelPackage;
		this.disposeableColors = new LinkedList<Color>();
	}

	@Override
	public Viewer createViewer(Composite composite)
	{
		CheckboxTreeViewer viewer = new CheckboxTreeViewer(composite);
		viewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));

		// create a delegate that implements a font and color provider, a check state provider,
		// a label decorator, and a check state listener for the model viewer and delegates
		// the functions to the slicing logic
		slicingLogicDelegate = new SlicingLogicDelegate(editor, adapterFactory, viewer, modelPackage.eResource());
		viewer.setLabelProvider(new DecoratingStyledCellLabelProvider(slicingLogicDelegate, slicingLogicDelegate, null));
		viewer.setCheckStateProvider(slicingLogicDelegate);
		viewer.addCheckStateListener(slicingLogicDelegate);
		ColumnViewerToolTipSupport.enableFor(viewer, ToolTip.RECREATE);

		// set the model
		viewer.setInput(modelPackage.eResource());
		viewer.setSelection(new StructuredSelection(modelPackage.eResource()));

		new AdapterFactoryTreeEditor(viewer.getTree(), adapterFactory);

		// expand the EPackage and all checked elements
		viewer.expandToLevel(2);
		viewer.setExpandedElements(viewer.getCheckedElements());

		setTitle(modelPackage.getName() + " - " + modelPackage.getNsURI(), //$NON-NLS-1$
				slicingLogicDelegate.getImage(modelPackage));

		viewer.setComparator(new EcoreLexicalViewerComparator());

		return viewer;
	}

	/**
	 * Creates the help controls.
	 * @param parent the parent
	 * @return controls containing the explanations
	 */
	public Control createHelpControl(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NONE);
		{
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.verticalSpacing = 12;
			composite.setLayout(layout);

			GridData data = new GridData();
			data.verticalAlignment = GridData.FILL;
			data.grabExcessVerticalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			composite.setLayoutData(data);
		}

		Label titleLabel = new Label(composite, SWT.LEFT);
		titleLabel.setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ViewerPane_label_Colors"));//$NON-NLS-1$
		{
			FontDescriptor fontDescriptor = FontDescriptor.createFrom(titleLabel.getFont()).setStyle(SWT.BOLD).setHeight(16);
			Font boldFont = fontDescriptor.createFont(titleLabel.getDisplay());
			titleLabel.setFont(boldFont);
		}

		defaultLabel = new Label(composite, SWT.LEFT);
		defaultLabel.setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ViewerPane_label_Default"));//$NON-NLS-1$

		referencedElementLabel = new Label(composite, SWT.LEFT);
		referencedElementLabel.setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ViewerPane_label_ReferencedElement"));//$NON-NLS-1$

		referencedElementMandatoryLabel = new Label(composite, SWT.LEFT);
		referencedElementMandatoryLabel.setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ViewerPane_label_ReferencedElementMandatory")); //$NON-NLS-1$

		CLabel multiplicityLabel = new CLabel(composite, SWT.LEFT);
		multiplicityLabel.setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ViewerPane_label_ReferenceMandatoryTarget")); //$NON-NLS-1$
		multiplicityLabel.setImage(ConfigurationEditorPlugin.getImageDescriptor(ConfigurationEditorPlugin.IMAGE_MULTIPLICITY).createImage());

		danglingReferenceLabel = new Label(composite, SWT.LEFT);
		danglingReferenceLabel.setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ViewerPane_label_DanglingReference")); //$NON-NLS-1$

		CLabel containsDanglingLabel = new CLabel(composite, SWT.LEFT);
		containsDanglingLabel.setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ViewerPane_label_ContainsDangling")); //$NON-NLS-1$
		containsDanglingLabel.setImage(ConfigurationEditorPlugin.getImageDescriptor(ConfigurationEditorPlugin.IMAGE_DANGLING).createImage());

		CLabel constraintLabel = new CLabel(composite, SWT.LEFT);
		constraintLabel.setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ViewerPane_label_ElementConstraint")); //$NON-NLS-1$
		constraintLabel.setImage(ConfigurationEditorPlugin.getImageDescriptor(ConfigurationEditorPlugin.IMAGE_CONSTRAINT).createImage());

		Button preferenceButton = new Button(composite, SWT.PUSH);
		preferenceButton.setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_ViewerPane_label_ChangePreferences")); //$NON-NLS-1$
		preferenceButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				PreferencesUtil.createPreferenceDialogOn(null, ColorPreferencePage.ID, null, null).open();
			}
		});

		refreshHelpColors();

		return composite;
	}

	/**
	 * Updates the colors and styles of the labels according to the preferences.
	 */
	public void refreshHelpColors()
	{
		defaultLabel.setForeground(getPreferenceColor(PreferenceConstants.TEXT_COLOR_DEFAULT));
		setLabelTextStyle(defaultLabel, PreferenceConstants.TEXT_STYLE_DEFAULT);

		referencedElementLabel.setForeground(getPreferenceColor(PreferenceConstants.TEXT_COLOR_REFERENCED));
		setLabelTextStyle(referencedElementLabel, PreferenceConstants.TEXT_STYLE_REFERENCED);
		
		referencedElementMandatoryLabel.setForeground(getPreferenceColor(PreferenceConstants.TEXT_COLOR_REFERENCED));
		setLabelTextStyle(referencedElementMandatoryLabel, PreferenceConstants.TEXT_STYLE_REFERENCED_MULTI);

		danglingReferenceLabel.setBackground(getPreferenceColor(PreferenceConstants.BACKGROUND_COLOR_DANGLING_REFERENCE));
	}

	@Override
	public void createControl(Composite parent)
	{
		if(getControl() == null)
		{
			container = parent;

			// Create view form.    
			//control = new ViewForm(parent, getStyle());
			control = new ViewForm(parent, SWT.NONE);
			control.addDisposeListener(new DisposeListener()
			{
				public void widgetDisposed(DisposeEvent event)
				{
					dispose();
				}
			});
			control.marginWidth = 0;
			control.marginHeight = 0;

			// Create a title bar.
			createTitleBar();

			sashForm = new SashForm(control, SWT.HORIZONTAL);
			viewer = createViewer(sashForm);
			helpControl = createHelpControl(sashForm);
			sashForm.setWeights(new int[] {3, 1});

			control.setContent(sashForm);
			control.setTabList(new Control[] { sashForm });

			// When the pane or any child gains focus, notify the workbench.
			control.addListener(SWT.Activate, this);
			hookFocus(control);
			hookFocus(viewer.getControl());

			ConfigurationEditorPlugin.getPlugin().getPreferenceStore().addPropertyChangeListener(this);
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();
		ConfigurationEditorPlugin.getPlugin().getPreferenceStore().removePropertyChangeListener(this);

		if(disposeableColors != null)
		{
			for(Color c : disposeableColors)
			{
				c.dispose();
			}
			disposeableColors = null;
		}
	}

	public Control getHelpControl()
	{
		return helpControl;
	}

	public SashForm getSashForm()
	{
		return sashForm;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event)
	{
		if(event.getSource() == ConfigurationEditorPlugin.getPlugin().getPreferenceStore())
		{
			refreshHelpColors();
		}
	}

	private void setLabelTextStyle(Label label, String preferenceKey)
	{
		int style = ConfigurationEditorPlugin.getPlugin().getPreferenceStore().getInt(preferenceKey);
		FontDescriptor fontDescriptor = FontDescriptor.createFrom(label.getFont()).setStyle(style);
		Font boldFont = fontDescriptor.createFont(label.getDisplay());
		label.setFont(boldFont);
	}

	private Color getPreferenceColor(String preferenceKey)
	{
		String colorString = ConfigurationEditorPlugin.getPlugin().getPreferenceStore().getString(preferenceKey);

		RGB rgb = StringConverter.asRGB(colorString, null);
		if(rgb == null)
			return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);

		for(Color c : disposeableColors)
		{
			if(c.getRGB().equals(rgb))
			{
				return c;
			}
		}

		Color c = new Color(Display.getCurrent(), rgb);
		disposeableColors.add(c);
		return c;
	}

	@Override
	protected void requestActivation()
	{
		super.requestActivation();
		editor.setCurrentViewerPane(this);
	}

	public SlicingLogicDelegate getSlicingLogicDelegate()
	{
		return slicingLogicDelegate;
	}
}
