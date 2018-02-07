package org.sidiff.slicer.structural.configuration.views;

import java.util.Arrays;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditor;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;
import org.sidiff.slicer.structural.configuration.provider.ConfigurationItemProviderAdapterFactory;

/**
 * This view shows a list of alternative elements for the selected element in a tree/list.
 * The tree provides checkboxes that can be used to modify the slicing configuration.
 * The first element of the list is displayed with a bold font and should be the currently selected one.
 * @author rmueller
 *
 */
public class AlternativeElementsView extends ViewPart
{
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.sidiff.slicer.structural.configuration.views.AlternativeElementsView"; //$NON-NLS-1$

	/**
	 * An empty array of EObjects
	 */
	private static final EObject EMPTY_EOBJECT_ARRAY[] = new EObject[0];

	/**
	 * Reference to the currently open {@link ConfigurationEditor}.
	 * Updated when the selection changes.
	 */
	private ConfigurationEditor configurationEditor = null;

	/**
	 * Adapter factory used for providing views of the displayed elements
	 */
	private ComposedAdapterFactory adapterFactory;

	/**
	 * The main viewer that contains the elements and checkboxes
	 */
	private CheckboxTreeViewer treeViewer;
	
	/**
	 * A composite that contains the main viewer ({@link #treeViewer}) and the empty view ({@link #emptyViewContainer}).
	 * Uses the {@link #stackLayout} to either show the viewer or the empty view, if the viewer is empty.
	 */
	private Composite stackComposite;
	
	/**
	 * A composite containing a {@link Text} that shows that the viewer is currently empty.
	 * The composite is needed, because otherwise a Text cannot be aligned vertically.
	 */
	private Composite emptyViewContainer;
	
	/**
	 * The layout for {@link #stackComposite}, used for switching between the two viewers.
	 */
	private StackLayout stackLayout;

	/**
	 * The root element
	 */
	private EObject rootElement = null;
	
	/**
	 * The alternative elements to show
	 */
	private EObject alternativeElements[] = EMPTY_EOBJECT_ARRAY;

	/**
	 * Listener for global selection events.
	 * If the selection originates from a {@link ConfigurationEditor}, the reference {@link #configurationEditor} is updated.
	 * If the selection contains an EObject and the reference to the configuration editor is set, the elements to
	 * display are retrieved from the configuration editor and the layout is refreshed.
	 */
	private final ISelectionListener selectionListener = new ISelectionListener()
	{
		@Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection)
		{
			// ignore all selections that do not contain an EObject
			if(selection.isEmpty())
				return;
			if(!(selection instanceof IStructuredSelection))
				return;
			final Object selectedObject = ((IStructuredSelection)selection).getFirstElement();
			if(!(selectedObject instanceof EObject))
				return;

			// ignore selection of the view itself
			if(part instanceof AlternativeElementsView)
				return;

			setSelectedObject(part, (EObject)selectedObject);
		}
	};
	
	/**
	 * Listener for part lifecycle events. When the part being closed is the currently
	 * referenced {@link #configurationEditor}, the reference is set to <code>null</code>
	 * and the viewer is emptied.
	 */
	private final IPartListener partListener = new IPartListener()
	{
		@Override
		public void partOpened(IWorkbenchPart part)
		{
		}
		
		@Override
		public void partDeactivated(IWorkbenchPart part)
		{
		}
		
		@Override
		public void partClosed(IWorkbenchPart part)
		{
			if(part == configurationEditor)
			{
				configurationEditor = null;
				rootElement = null;
				alternativeElements = EMPTY_EOBJECT_ARRAY;
				updateStackLayout();
			}
		}
		
		@Override
		public void partBroughtToTop(IWorkbenchPart part)
		{
		}
		
		@Override
		public void partActivated(IWorkbenchPart part)
		{
		}
	};

	/**
	 * The constructor.
	 */
	public AlternativeElementsView()
	{
		initializeAdapterFactory();
	}

	/**
	 * Initializes the ComposedAdapterFactory
	 */
	private void initializeAdapterFactory()
	{
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ConfigurationItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent)
	{
		stackComposite = new Composite(parent, SWT.NONE);
		stackLayout = new StackLayout();
		stackComposite.setLayout(stackLayout);

		treeViewer = new CheckboxTreeViewer(stackComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		AlternativeElementsDelegate delegate = new AlternativeElementsDelegate(adapterFactory, treeViewer);
		treeViewer.setContentProvider(delegate);
		treeViewer.setInput("root"); // the value is ignored, this must be non-null to show the elements //$NON-NLS-1$
		treeViewer.setLabelProvider(delegate);
		treeViewer.setCheckStateProvider(delegate);
		treeViewer.addCheckStateListener(delegate);
		getSite().setSelectionProvider(treeViewer);

		// container for the empty text, so it can be aligned vertically
		emptyViewContainer = new Composite(stackComposite, SWT.NONE);
		emptyViewContainer.setLayout(new GridLayout());

		CLabel emptyView = new CLabel(emptyViewContainer, SWT.NONE);
		emptyView.setText(ConfigurationEditorPlugin.INSTANCE.getString("_UI_AltElements_NoAltAvailable")); //$NON-NLS-1$
		emptyView.setLayoutData(new GridData(SWT.BEGINNING, SWT.TOP, true, true));

		// the initial viewer is the empty view
		stackLayout.topControl = emptyViewContainer;

		// register listeners
		getSite().getPage().addSelectionListener(selectionListener);
		getSite().getPage().addPartListener(partListener);
	}
	
	/**
	 * Updates the {@link #stackLayout}. If {@link #alternativeElements} is empty,
	 * the {@link #emptyViewContainer} is shown, else the {@link #treeViewer} is shown.
	 */
	private void updateStackLayout()
	{
		stackLayout.topControl = alternativeElements.length == 0 ? emptyViewContainer : treeViewer.getControl();
		stackComposite.layout();
		treeViewer.refresh();
		treeViewer.expandAll();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus()
	{
		treeViewer.getControl().setFocus();
	}
	
	/**
	 * Refreshes the viewer.
	 */
	public void refresh()
	{
		treeViewer.refresh();
	}

	/**
	 * Updates the displayed alternative elements for the selection of the given object
	 * in the given workbench part.
	 * @param part the part of the selection
	 * @param selectedObject the selected object
	 */
	public void setSelectedObject(IWorkbenchPart part, EObject selectedObject)
	{
		ConfigurationEditor lastConfigurationEditor = configurationEditor;
		EObject lastRootElement = rootElement;
		EObject lastAlternativeElements[] = alternativeElements;

		// update reference to active ConfigurationEditor
		if(part instanceof ConfigurationEditor)
		{
			configurationEditor = (ConfigurationEditor)part;
		}

		// update displayed alternative elements
		if(configurationEditor != null)
		{
			alternativeElements = configurationEditor.getAlternativeElements(selectedObject).toArray(EMPTY_EOBJECT_ARRAY);
			rootElement = alternativeElements.length > 0 ? selectedObject : null;
		}

		// only refresh layout if something changed,
		// or the check state listener does not work correctly
		if(lastConfigurationEditor != configurationEditor || lastRootElement != rootElement
				|| !Arrays.equals(lastAlternativeElements, alternativeElements))
		{
			updateStackLayout();
		}
	}

	@Override
	public void dispose()
	{
		adapterFactory.dispose();

		// clear references
		configurationEditor = null;
		alternativeElements = EMPTY_EOBJECT_ARRAY;

		// unregister listeners
		getSite().getPage().removePartListener(partListener);
		getSite().getPage().removeSelectionListener(selectionListener);

		super.dispose();
	}

	/**
	 * This class implements
	 * <ul>
	 * <li>{@link AdapterFactoryLabelProvider.FontProvider}</li>
	 * <li>{@link ITreeContentProvider}</li>
	 * <li>{@link ICheckStateListener}</li>
	 * <li>{@link ICheckStateProvider}</li>
	 * </ul>
	 * for the {@link CheckboxTreeViewer}.
	 * The first element of the list is bold. Check state events and provider are delegated
	 * to the ConfigurationEditor to modify the actual slicing configuration.
	 * @author rmueller
	 *
	 */
	protected class AlternativeElementsDelegate extends AdapterFactoryLabelProvider.FontProvider
		implements ITreeContentProvider, ICheckStateListener, ICheckStateProvider
	{
		public AlternativeElementsDelegate(AdapterFactory adapterFactory, Viewer viewer)
		{
			super(adapterFactory, viewer);
		}

		@Override
		public Object[] getElements(Object inputElement)
		{
			if(rootElement == null)
				return EMPTY_EOBJECT_ARRAY;
			return new EObject[] { rootElement };
		}

		@Override
		public Object[] getChildren(Object parentElement)
		{
			if(parentElement == rootElement)
				return alternativeElements;
			return EMPTY_EOBJECT_ARRAY;
		}

		@Override
		public Object getParent(Object element)
		{
			if(element == rootElement)
				return null;
			return rootElement;
		}

		@Override
		public boolean hasChildren(Object element)
		{
			return element == rootElement;
		}

		@Override
		public Font getFont(Object object)
		{
			if(rootElement == object)
			{
				FontDescriptor fontDescriptor = FontDescriptor.createFrom(treeViewer.getControl().getFont());
				return fontDescriptor.setStyle(SWT.BOLD).createFont(Display.getCurrent());
			}

			return super.getFont(object);
		}

		@Override
		public void checkStateChanged(CheckStateChangedEvent event)
		{
			if(configurationEditor != null)
			{
				configurationEditor.onAlternativeChecked(event);
			}
		}

		@Override
		public boolean isGrayed(Object element)
		{
			return false;
		}

		@Override
		public boolean isChecked(Object element)
		{
			return element instanceof EObject && configurationEditor != null
					&& configurationEditor.isAlternativeChecked((EObject)element);
		}
	}
}
