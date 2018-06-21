package org.sidiff.slicer.structural.configuration.commands;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.slicer.structural.configuration.ConfigurationFactory;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.SlicedEReference;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;
import org.sidiff.slicer.structural.configuration.util.ConfigurationUtil;

/**
 * Default handler for the "Add to slicing configuration" command.<br>
 * <br>
 * The current selection must be a {@link TreeSelection} that is not empty.
 * All elements in the selection that are {@link EObject}s will be handled.<br>
 * <br>
 * Dialogs will be shown for the user to select:
 * <ul>
 * <li>file to save the slicing configuration</li>
 * <li>which model elements to include</li>
 * </ul>
 * @author rmueller
 *
 */
public class AddSliceActionHandler extends AbstractHandler
{
	/**
	 * Flag that specifies whether to include all super types of {@link EClass}es in the slicing configuration.
	 */
	protected boolean includeSuperTypes = true;
	
	/**
	 * Flag that specifies whether to include {@link EReference}s for containment references.
	 */
	protected boolean includeContainments = true;
	
	/**
	 * Flag that specifies whether to include {@link EReference}s for non-containment references.
	 */
	protected boolean includeReferences = false;
	
	/**
	 * Flag that specifies whether to include {@link EClass}es that are the type of an included {@link EReference}.<br>
	 * This flag is only significant if {@link #includeReferences} is <code>true</code>.
	 */
	protected boolean includeReferencedTypes = false;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		// get selection and check if it is a TreeSelection
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if(!(selection instanceof TreeSelection))
			return null;

		// check that TreeSelection is not empty
		TreeSelection treeSelection = (TreeSelection)selection;
		if(treeSelection.isEmpty())
			return null;

		// show output file selection dialog
		String fileName = showFileSelectionDialog();
		if(fileName == null)
			return null;
		
		// show options dialog
		if(!showOptionsDialog())
			return null;

		// calculate which elements to include
		Map<EClass, Set<EReference>> classesAndReferences = calculateIncludedElements(treeSelection);

		// generate slicing configuration
		SlicingConfiguration cfg = generateSlicingConfiguration(classesAndReferences);

		// save the configuration
		saveSlicingConfiguration(fileName, cfg);
		return null;
	}

	/**
	 * Calculates which elements should be included in the slicing configuration based on the selection
	 * and the flags ({@link #includeSuperTypes}, {@link #includeContainments}, {@link #includeReferences},
	 * {@link #includeReferencedTypes})
	 * @param treeSelection the TreeSelection
	 * @return map of EClasses to sets of EReferences describing the elements to include in the configuration
	 */
	protected Map<EClass, Set<EReference>> calculateIncludedElements(TreeSelection treeSelection)
	{
		// map for classes and references
		Map<EClass, Set<EReference>> classesAndReferences = new HashMap<EClass, Set<EReference>>();

		// go over all paths in the tree selection
		for(TreePath path : treeSelection.getPaths())
		{
			Set<EReference> lastClassReferences = null;
			for(int i = 0; i < path.getSegmentCount(); i++)
			{
				// ignore all objects, that are not EObjects
				Object seg = path.getSegment(i);
				if(!(seg instanceof EObject))
					continue;

				EObject obj = (EObject)seg;
				EClass objClass = obj.eClass();

				// check if the class is already in the configuration
				if(!classesAndReferences.containsKey(objClass))
				{
					// add it to the configuration
					classesAndReferences.put(objClass, new HashSet<EReference>());
				}

				// add containment reference
				if(includeContainments && lastClassReferences != null && obj.eContainmentFeature() != null)
				{
					// check if reference if already in the configuration
					if(!lastClassReferences.contains(obj.eContainmentFeature()))
					{
						// add it to the configuration
						lastClassReferences.add(obj.eContainmentFeature());
					}
				}

				// add references
				if(includeReferences)
				{
					// set of references of the current EClass
					final Set<EReference> references = classesAndReferences.get(objClass);

					for(EReference r : objClass.getEReferences())
					{
						// containments are handled above
						if(r.isContainment() || !ConfigurationUtil.isSliceable(r))
							continue;

						// add reference
						if(!references.contains(r))
						{
							references.add(r);
						}

						// add references type
						if(includeReferencedTypes)
						{
							if(!classesAndReferences.containsKey(r.getEReferenceType()))
							{
								classesAndReferences.put(r.getEReferenceType(), new HashSet<EReference>());
							}
						}
					}
				}

				lastClassReferences = classesAndReferences.get(objClass);
			}
		}

		// add super types
		if(includeSuperTypes)
		{
			// super types are collected first to prevent concurrent modification of the set
			Set<EClass> superTypes = new HashSet<EClass>();
			
			// collect all super types in temporary set
			for(EClass eClass : classesAndReferences.keySet())
			{
				superTypes.addAll(eClass.getEAllSuperTypes());
			}
			
			// add super types to main set
			for(EClass s : superTypes)
			{
				if(!classesAndReferences.containsKey(s))
				{
					classesAndReferences.put(s, new HashSet<EReference>());
				}
			}
		}

		return classesAndReferences;
	}

	/**
	 * Generates a slicing configuration from the map of EClasses to sets of EReferences.<br>
	 * <br>
	 * All {@link EClass}es in the key set are added as {@link SlicedEClass}es.<br>
	 * All {@link EReference}s in a set are added as {@link SlicedEReference}s to the corresponding {@link SlicedEClass}.<br>
	 * The {@link EPackage} of every {@link EClass} is added to the list of imported packages.
	 * @param classesAndReferences map describing the elements to include in the configuration
	 * @return slicing configuration
	 */
	protected SlicingConfiguration generateSlicingConfiguration(Map<EClass, Set<EReference>> classesAndReferences)
	{
		// create a new configuration
		SlicingConfiguration cfg = ConfigurationFactory.eINSTANCE.createSlicingConfiguration();

		// set of imports (to prevent duplicates)
		Set<EPackage> imports = new HashSet<EPackage>();

		// add classes and references to configuration
		for(EClass eClass : classesAndReferences.keySet())
		{
			// import all packages of classes
			imports.add(eClass.getEPackage());

			// create SlicedEClass
			SlicedEClass slicedEClass = ConfigurationFactory.eINSTANCE.createSlicedEClass();
			slicedEClass.setType(eClass);

			// add the SlicedEReferences
			for(EReference eReference : classesAndReferences.get(eClass))
			{
				// create SlicedEReference
				SlicedEReference slicedEReference = ConfigurationFactory.eINSTANCE.createSlicedEReference();
				slicedEReference.setType(eReference);
				slicedEClass.getSlicedEReferences().add(slicedEReference);
			}

			// add SlicedEClass to configuration
			cfg.getSlicedEClasses().add(slicedEClass);
		}

		// add imports to configuration
		cfg.getImports().addAll(imports);

		return cfg;
	}

	/**
	 * Save the slicing configuration to the file specified by its name.
	 * Shows a status dialog informing about the success or failure of the operation.
	 * @param fileName file to save the slicing configuration to
	 * @param cfg the slicing configuration to save
	 */
	protected void saveSlicingConfiguration(String fileName, SlicingConfiguration cfg)
	{
		// create resource
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI.createFileURI(fileName));
		
		// add slicing configuration to resource
		resource.getContents().add(cfg);
		
		// save the resource
		try
		{
			resource.save(Collections.EMPTY_MAP);

			// show status dialog
			IStatus status = new Status(IStatus.INFO, ConfigurationEditorPlugin.ID,
					IStatus.OK, ConfigurationEditorPlugin.getSubstitutedString("_UI_AddSlice_ConfigSuccess", fileName), null); //$NON-NLS-1$
			ErrorDialog.openError(Display.getCurrent().getActiveShell(), ConfigurationEditorPlugin.INSTANCE.getString("_UI_AddSlice_ConfigCreated"), null, status); //$NON-NLS-1$
		}
		catch(IOException e)
		{
			// show error dialog
			IStatus status = new Status(IStatus.ERROR, ConfigurationEditorPlugin.ID,
					1, ConfigurationEditorPlugin.getSubstitutedString("_UI_AddSlice_CouldNotSafe", e.toString()), e); //$NON-NLS-1$
			ConfigurationEditorPlugin.INSTANCE.log(status);
			ErrorDialog.openError(Display.getCurrent().getActiveShell(), null, null, status);
		}
	}

	/**
	 * Shows a dialog to select a location to save the slicing configuration to.
	 * @return location to save the slicing configuration to
	 */
	protected String showFileSelectionDialog()
	{
		FileDialog fileDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
		fileDialog.setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_AddSlice_SelectLocation")); //$NON-NLS-1$
		fileDialog.setFilterExtensions(new String[] { "*.scfg" }); //$NON-NLS-1$
		fileDialog.setFilterNames(new String[] {ConfigurationEditorPlugin.INSTANCE.getString("_UI_AddSlice_FilterNames") }); //$NON-NLS-1$
		fileDialog.setOverwrite(true); // confirm if file already exists
		return fileDialog.open();
	}

	/**
	 * Shows an {@link OptionsDialog} asking which elements to include in the slicing configuration.
	 * When the dialog is closed, the checkbox-selection is saved to the flags.
	 * @return <code>true</code> if OK was pressed, <code>false</code> otherwise
	 */
	protected boolean showOptionsDialog()
	{
		OptionsDialog optionsDialog = new OptionsDialog(Display.getCurrent().getActiveShell());
		optionsDialog.setBlockOnOpen(true);
		optionsDialog.setHelpAvailable(false);
		optionsDialog.open(); // the returned status is always CANCEL for some unknown reason
		return optionsDialog.isConfirmed(); // that why we have our own confirmed-state
	}

	/**
	 * Dialog that contains checkboxes to select which elements to include in the slicing configuration.
	 * @author rmueller
	 *
	 */
	protected class OptionsDialog extends TitleAreaDialog
	{
		private Button checkboxSuperTypes;
		private Button checkboxContainments;
		private Button checkboxReferences;
		private Button checkboxReferencedTypes;

		/**
		 * Our own confirmed-state, because the result of {@link #open()} is always CANCEL
		 * for some unknown reason.
		 */
		private boolean confirmed;

		public OptionsDialog(Shell parentShell)
		{
			super(parentShell);
			this.confirmed = false;
		}

		@Override
		public void create()
		{
			super.create();
			setTitle(ConfigurationEditorPlugin.INSTANCE.getString("_UI_AddSlice_Options")); //$NON-NLS-1$
			setMessage(ConfigurationEditorPlugin.INSTANCE.getString("_UI_AddSlice_ChooseModel"), IMessageProvider.NONE); //$NON-NLS-1$
		}

		@Override
		protected Control createDialogArea(Composite parent)
		{
			Composite area = (Composite)super.createDialogArea(parent);
			Composite container = new Composite(area, SWT.NONE);
			container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			GridLayout layout = new GridLayout(2, false);
			container.setLayout(layout);

			createClassesAndAttributes(container);
			createSuperType(container);
			createContainments(container);
			createReferences(container);
			createReferencendTypes(container);

			return area;
		}

		private void createClassesAndAttributes(Composite container)
		{
			new Label(container, SWT.NONE).setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_AddSlice_Include_ClassesAttributes")); //$NON-NLS-1$

			Button checkboxClassesAndAttributes = new Button(container, SWT.CHECK);
			checkboxClassesAndAttributes.setLayoutData(new GridData(SWT.END, SWT.CENTER, true, false));
			checkboxClassesAndAttributes.setSelection(true);
			checkboxClassesAndAttributes.setEnabled(false);
		}

		private void createSuperType(Composite container)
		{
			new Label(container, SWT.NONE).setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_AddSlice_Include_SuperTypes")); //$NON-NLS-1$

			checkboxSuperTypes = new Button(container, SWT.CHECK);
			checkboxSuperTypes.setLayoutData(new GridData(SWT.END, SWT.CENTER, true, false));
			checkboxSuperTypes.setSelection(includeSuperTypes);
		}

		private void createContainments(Composite container)
		{
			new Label(container, SWT.NONE).setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_AddSlice_Include_Containments")); //$NON-NLS-1$

			checkboxContainments = new Button(container, SWT.CHECK);
			checkboxContainments.setLayoutData(new GridData(SWT.END, SWT.CENTER, true, false));
			checkboxContainments.setSelection(includeContainments);
		}

		private void createReferences(Composite container)
		{
			new Label(container, SWT.NONE).setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_AddSlice_Include_References")); //$NON-NLS-1$

			checkboxReferences = new Button(container, SWT.CHECK);
			checkboxReferences.setLayoutData(new GridData(SWT.END, SWT.CENTER, true, false));
			checkboxReferences.setSelection(includeReferences);
			checkboxReferences.addSelectionListener(new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{
					checkboxReferencedTypes.setEnabled(checkboxReferences.getSelection());
					if(!checkboxReferences.getSelection())
						checkboxReferencedTypes.setSelection(false);
				}
			});
		}

		private void createReferencendTypes(Composite container)
		{
			new Label(container, SWT.NONE).setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_AddSlice_Include_ReferencedTypes")); //$NON-NLS-1$

			checkboxReferencedTypes = new Button(container, SWT.CHECK);
			checkboxReferencedTypes.setLayoutData(new GridData(SWT.END, SWT.CENTER, true, false));
			checkboxReferencedTypes.setEnabled(includeReferences);
			checkboxReferencedTypes.setSelection(includeReferences && includeReferencedTypes);
		}

		@Override
		protected boolean isResizable()
		{
			return false;
		}

		private void saveInput()
		{
			includeSuperTypes = checkboxSuperTypes.getSelection();
			includeContainments = checkboxContainments.getSelection();
			includeReferences = checkboxReferences.getSelection();
			includeReferencedTypes = checkboxReferencedTypes.getSelection();
		}

		@Override
		protected Point getInitialSize()
		{
			return new Point(320, 260);
		}

		@Override
		protected void okPressed()
		{
			saveInput();
			super.okPressed();
			confirmed = true;
		}
		
		public boolean isConfirmed()
		{
			return confirmed;
		}
	}
}
