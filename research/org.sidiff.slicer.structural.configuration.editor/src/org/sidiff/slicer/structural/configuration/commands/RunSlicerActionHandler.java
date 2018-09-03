package org.sidiff.slicer.structural.configuration.commands;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.dialogs.ResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.slicer.ISlicer;
import org.sidiff.slicer.slice.ModelSlice;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditor;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;
import org.sidiff.slicer.structural.configuration.provider.ConfigurationItemProviderAdapterFactory;
import org.sidiff.slicer.util.SlicerUtil;
import org.sidiff.slicing.util.visualization.GraphUtil;

/**
 * Default handler for the "Run slicer" command.<br>
 * <br>
 * If the current selection is a file ({@link IFile}), it will be used as the slicing configuration.
 * Else, if the active editor is the {@link ConfigurationEditor}, the editor's input will be used.<br>
 * <br>
 * Dialogs will be shown for the user to input:
 * <ul>
 * <li>the model to slice</li>
 * <li>context identifiers (objects from the selected model)</li>
 * <li>slicer (if more than one slicer is installed)</li>
 * </ul>
 * The output is written to a folder inside the model's folder.
 * @author rmueller
 *
 */
public class RunSlicerActionHandler extends AbstractHandler
{
	/**
	 * Adapter factory used for providing views of the displayed elements
	 */
	private ComposedAdapterFactory adapterFactory;

	/**
	 * Initializes the ComposedAdapterFactory
	 */
	private void initializeAdapterFactory()
	{
		if(adapterFactory == null)
		{
			adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new ConfigurationItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
			adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		}
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

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		// get selection
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if(!(selection instanceof IStructuredSelection))
			return null;
		Object selectedObject = ((IStructuredSelection)selection).getFirstElement();
		
		// get active part
		IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
		
		// load slicing configuration (from selected element or active editor)
		SlicingConfiguration cfg;
		if(selectedObject instanceof IFile) // selected element is a file in a viewer
		{
			// load selected resource
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
					new EcoreResourceFactoryImpl());
			ResourceSet resourceSet = new ResourceSetImpl();
			URI uri = URI.createFileURI(((IFile)selectedObject).getLocationURI().normalize().getPath());
			Resource resource;
			try
			{
				resource = resourceSet.getResource(uri, true);
			}
			catch(WrappedException e)
			{
				showErrorDialog(ConfigurationEditorPlugin.getSubstitutedString("_UI_RunSlicer_LoadingResourceFailed", e.toString()), e); //$NON-NLS-1$
				return null;
			}

			// check resource
			if(resource == null)
			{
				showErrorDialog(ConfigurationEditorPlugin.INSTANCE.getString("_UI_RunSlicer_ResourceNotLoaded"), null); //$NON-NLS-1$
				return null;
			}
			else if(resource.getContents().isEmpty())
			{
				showErrorDialog(ConfigurationEditorPlugin.INSTANCE.getString("_UI_RunSlicer_ResourceEmpty"), null); //$NON-NLS-1$
				return null;
			}

			EObject root = resource.getContents().get(0);
			
			// check if element is slicing configuration
			if(!(root instanceof SlicingConfiguration))
			{
				showErrorDialog(ConfigurationEditorPlugin.getSubstitutedString("_UI_RunSlicer_SelectionNotConfig", root), null); //$NON-NLS-1$
				return null;
			}
			
			cfg = (SlicingConfiguration)root;
		}
		else if(activePart instanceof ConfigurationEditor) // configuration editor is active, use its input
		{
			cfg = ((ConfigurationEditor)activePart).getConfig();
		}
		else // selected element unusable
		{
			showErrorDialog(ConfigurationEditorPlugin.getSubstitutedString("_UI_RunSlicer_ActionCannotPerformed", selectedObject), null); //$NON-NLS-1$
			return null;
		}

		// get model uri
		URI modelURI = showModelSelectionDialog();
		if(modelURI == null)
			return null;

		// load model
		EObject model;
		try
		{
			model = EMFStorage.eLoad(modelURI);
		}
		catch(Exception e)
		{
			showErrorDialog(ConfigurationEditorPlugin.getSubstitutedString("_UI_RunSlicer_LoadingModelFailed", modelURI.toString()), e); //$NON-NLS-1$
			return null;
		}

		// get context identifiers
		List<EObject> contextIdentifiers = showContextIdentifierSelectionDialog(model.eResource());
		if(contextIdentifiers == null || contextIdentifiers.isEmpty())
			return null;

		// find all slicers
		Set<ISlicer> slicers = SlicerUtil.getAllAvailableSlicers();

		// choose slicer, show dialog if more than 1 available
		ISlicer slicer = null;
		if(slicers.isEmpty())
		{
			showErrorDialog(ConfigurationEditorPlugin.INSTANCE.getString("_UI_RunSlicer_NoSlicerFound"), null); //$NON-NLS-1$
		}
		else if(slicers.size() == 1)
		{
			slicer = slicers.iterator().next();
		}
		else
		{
			slicer = showSlicerSelectionDialog(slicers.toArray(new ISlicer[0]));
		}
		if(slicer == null)
			return null;

		// get output uri
		URI saveURI = showOutputSelectionDialog(modelURI);
		if(saveURI == null)
			return null;
		if(!modelURI.fileExtension().equalsIgnoreCase(saveURI.fileExtension()))
			saveURI = saveURI.appendFileExtension(modelURI.fileExtension());

		// slicing
		try
		{
			// run slicer
			slicer.init((SlicingConfiguration)cfg);
			ModelSlice slice = slicer.slice(contextIdentifiers);
			SlicerUtil.serializeModelSlice(saveURI, slice.export(object -> model.eResource().equals(object.eResource())));

			URI graphURI = saveURI.appendFileExtension(ConfigurationEditorPlugin.getSubstitutedString("_UI_RunSlicer_GraphFileExt")); //$NON-NLS-1$
			OutputStream graphOutput = model.eResource().getResourceSet().getURIConverter().createOutputStream(graphURI);
			graphOutput.write(GraphUtil.get(slicer).getOutput().getBytes());
			graphOutput.close();

			// show status message
			IStatus status = new Status(IStatus.INFO, ConfigurationEditorPlugin.ID,
					IStatus.OK, ConfigurationEditorPlugin.getSubstitutedString("_UI_RunSlicer_OutputWritten", saveURI.toString()), null); //$NON-NLS-1$
			ErrorDialog.openError(Display.getCurrent().getActiveShell(), ConfigurationEditorPlugin.INSTANCE.getString("_UI_RunSlicer_SlicingFinished"), null, status); //$NON-NLS-1$
		}
		catch(Exception e)
		{
			showErrorDialog(ConfigurationEditorPlugin.getSubstitutedString("_UI_RunSlicer_SlicingFailed", e.toString()), e); //$NON-NLS-1$
		}

		return null;
	}

	/**
	 * Shows an error dialog with the specified message and exception.
	 * If the exception is not <code>null</code>, the error is added to the error log.
	 * @param statusMessage error message
	 * @param exception exception that caused the error, may be <code>null</code>
	 */
	protected void showErrorDialog(String statusMessage, Throwable exception)
	{
		IStatus status = new Status(IStatus.ERROR, ConfigurationEditorPlugin.ID, 1, statusMessage, exception);
		if(exception != null)
		{
			ConfigurationEditorPlugin.INSTANCE.log(status);
		}
		ErrorDialog.openError(Display.getCurrent().getActiveShell(), null, null, status);
	}

	/**
	 * Shows a dialog to select a file containing a model to slice from the workspace or file system
	 * @return uri of the selected file, <code>null</code> if the dialog was cancelled
	 */
	protected URI showModelSelectionDialog()
	{
		ResourceDialog resourceDialog = new ResourceDialog(Display.getCurrent().getActiveShell(),
			ConfigurationEditorPlugin.INSTANCE.getString("_UI_RunSlicer_SelectModel"), SWT.OPEN) //$NON-NLS-1$
		{
			@Override
			protected boolean processResources()
			{
				return !getURIText().isEmpty();
			}
		};
		resourceDialog.setBlockOnOpen(true);
		if(resourceDialog.open() == Window.CANCEL)
			return null;
		return URI.createURI(resourceDialog.getURIText());
	}

	/**
	 * Shows a dialog to select the output uri for the sliced model from the workspace or file system
	 * @return uri of the selected file, <code>null</code> if the dialog was cancelled
	 */
	protected URI showOutputSelectionDialog(URI contextURI)
	{
		ResourceDialog resourceDialog = new ResourceDialog(Display.getCurrent().getActiveShell(),
			ConfigurationEditorPlugin.INSTANCE.getString("_UI_RunSlicer_SelectOutput"), SWT.SAVE, contextURI) //$NON-NLS-1$
		{
			@Override
			protected boolean processResources()
			{
				if(getURIText().isEmpty())
				{
					uriField.setText(ConfigurationEditorPlugin.getSubstitutedString("_UI_RunSlicer_SlicedDefaultFilename", //$NON-NLS-1$
							context.trimFileExtension().toString(), context.fileExtension()));
					return false;
				}

				return true;
			}
		};
		resourceDialog.setBlockOnOpen(true);
		if(resourceDialog.open() == Window.CANCEL)
			return null;
		return URI.createURI(resourceDialog.getURIText());
	}

	/**
	 * Shows a dialog to select context identifiers from the provided resource.
	 * @param modelResource resource of the model to slice
	 * @return list of selected objects, <code>null</code> if the dialog was cancelled
	 */
	protected List<EObject> showContextIdentifierSelectionDialog(Resource modelResource)
	{
		initializeAdapterFactory();

		// create dialog
		CheckedTreeSelectionDialog dialog = new CheckedTreeSelectionDialog(Display.getCurrent().getActiveShell(),
				new AdapterFactoryLabelProvider(adapterFactory), new AdapterFactoryContentProvider(adapterFactory));

		dialog.setInput(modelResource);
		dialog.setInitialSelection(modelResource.getContents().get(0));
		dialog.setTitle(ConfigurationEditorPlugin.INSTANCE.getString("_UI_RunSlicer_SelectIdentifier")); //$NON-NLS-1$
		dialog.setMessage(ConfigurationEditorPlugin.INSTANCE.getString("_UI_RunSlicer_SelectIdentifier_message")); //$NON-NLS-1$
		dialog.setHelpAvailable(false);
		dialog.setSize(80, 20);
		dialog.setBlockOnOpen(true);

		// open dialog
		if(dialog.open() != Window.OK)
			return null;

		// get the result
		Object result[] = dialog.getResult();
		if(result == null)
			return null;

		// convert array to list and return it
		List<EObject> list = new ArrayList<EObject>(result.length);
		for(Object o : result)
		{
			list.add((EObject)o);
		}
		return list;
	}

	/**
	 * Shows a dialog to select a slicer from the provided list of slicers.
	 * @param slicers array of slicers to show
	 * @return the selected slicer, <code>null</code> if the dialog was cancelled
	 */
	protected ISlicer showSlicerSelectionDialog(ISlicer slicers[])
	{
		// create dialog
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(Display.getCurrent().getActiveShell(),
				new SlicerLabelProvider());

		dialog.setTitle(ConfigurationEditorPlugin.INSTANCE.getString("_UI_RunSlicer_SelectSlicer")); //$NON-NLS-1$
		dialog.setMessage(ConfigurationEditorPlugin.INSTANCE.getString("_UI_RunSlicer_EnterFilter")); //$NON-NLS-1$
		dialog.setEmptySelectionMessage(ConfigurationEditorPlugin.INSTANCE.getString("_UI_Runslicer_FilterNotMatch")); //$NON-NLS-1$
		dialog.setElements(slicers);
		dialog.setMultipleSelection(false);
		dialog.setHelpAvailable(false);
		dialog.setIgnoreCase(true);
		dialog.setSize(80, 20);
		dialog.setBlockOnOpen(true);

		// show dialog
		if(dialog.open() != Window.OK)
			return null;

		// get result
		Object result = dialog.getFirstResult();
		if(result == null || !(result instanceof ISlicer))
			return null;

		return (ISlicer)result;
	}

	/**
	 * {@link LabelProvider} for {@link ISlicer} used by the slicer selection dialog.
	 * @see RunSlicerActionHandler#showSlicerSelectionDialog(ISlicer[])
	 * @author rmueller
	 *
	 */
	protected class SlicerLabelProvider extends LabelProvider
	{
		@Override
		public String getText(Object element)
		{
			if(element instanceof ISlicer)
			{
				ISlicer slicer = (ISlicer)element;
				return slicer.getName();
			}

			return super.getText(element);
		}
	}
}
