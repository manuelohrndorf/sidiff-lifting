package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.part;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.GMFToolingRuntimePlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * @since 3.2
 */
public class DefaultElementChooserDialog extends Dialog {

	public interface Context {

		public AdapterFactory getAdapterFactory();

		public PreferencesHint getPreferenceHint();

		public String[] getFileExtesions();

		public String getDialogTitle();

		public ITreeContentProvider getTreeContentProvider();

		public boolean allowMultiSelection();
	}

	private final Context myContext;

	private TreeViewer myTreeViewer;

	private List<URI> mySelectedModelElementURIs;

	private View myView;

	private TransactionalEditingDomain myEditingDomain = GMFEditingDomainFactory.INSTANCE.createEditingDomain();

	public DefaultElementChooserDialog(Shell parentShell, View view, Context context) {
		super(parentShell);
		myContext = context;
		setShellStyle(getShellStyle() | SWT.RESIZE);
		myView = view;
	}

	public URI getSelectedModelElementURI() {
		return getSelectedModelElementURIs().size() > 0 ? getSelectedModelElementURIs().get(0) : null;
	}

	public List<URI> getSelectedModelElementURIs() {
		if (mySelectedModelElementURIs == null) {
			mySelectedModelElementURIs = new LinkedList<URI>();
		}
		return mySelectedModelElementURIs;
	}

	public int open() {
		int result = super.open();
		for (Resource resource : myEditingDomain.getResourceSet().getResources()) {
			resource.unload();
		}
		myEditingDomain.dispose();
		return result;
	}

	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		getShell().setText(myContext.getDialogTitle());
		createModelBrowser(composite);
		return composite;
	}

	protected Control createButtonBar(Composite parent) {
		Control buttonBar = super.createButtonBar(parent);
		setOkButtonEnabled(false);
		return buttonBar;
	}

	private void createModelBrowser(Composite composite) {
		myTreeViewer = new TreeViewer(composite, myContext.allowMultiSelection() ? SWT.MULTI : SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.heightHint = 300;
		layoutData.widthHint = 300;
		myTreeViewer.getTree().setLayoutData(layoutData);
		myTreeViewer.setContentProvider(new ModelElementsTreeContentProvider(myContext.getTreeContentProvider(), myContext.getAdapterFactory()));
		myTreeViewer.setLabelProvider(new ModelElementsTreeLabelProvider(myContext.getAdapterFactory()));
		myTreeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
		myTreeViewer.addFilter(new ModelFilesFilter());
		myTreeViewer.addSelectionChangedListener(new OkButtonEnabler(myContext.getPreferenceHint()));
	}

	private void setOkButtonEnabled(boolean enabled) {
		getButton(IDialogConstants.OK_ID).setEnabled(enabled);
	}

	private boolean isValidModelFile(IFile file) {
		String fileExtension = file.getFullPath().getFileExtension();
		for (int i = 0; i < myContext.getFileExtesions().length; i++) {
			String currentExtension = myContext.getFileExtesions()[i];
			if (currentExtension != null && currentExtension.equals(fileExtension)) {
				return true;
			}
		}
		return false; //$NON-NLS-1$
	}

	private class ModelElementsTreeContentProvider implements ITreeContentProvider {

		private final ITreeContentProvider myWorkbenchContentProvider;

		private final AdapterFactoryContentProvider myAdapterFactoryContentProvier;

		private ModelElementsTreeContentProvider(ITreeContentProvider iTreeContentProvider, AdapterFactory adapterFactory) {
			myWorkbenchContentProvider = iTreeContentProvider;
			myAdapterFactoryContentProvier = new AdapterFactoryContentProvider(adapterFactory);
		}

		public Object[] getChildren(Object parentElement) {
			Object[] result = myWorkbenchContentProvider.getChildren(parentElement);
			if (result != null && result.length > 0) {
				return result;
			}
			if (parentElement instanceof IFile) {
				IFile modelFile = (IFile) parentElement;
				IPath resourcePath = modelFile.getFullPath();
				ResourceSet resourceSet = myEditingDomain.getResourceSet();
				try {
					Resource modelResource = resourceSet.getResource(URI.createPlatformResourceURI(resourcePath.toString(), true), true);
					return myAdapterFactoryContentProvier.getChildren(modelResource);
				} catch (WrappedException e) {
					GMFToolingRuntimePlugin.getInstance().getLog().log(new Status(IStatus.ERROR, GMFToolingRuntimePlugin.ID, //
							"Unable to load resource: " + resourcePath.toString(), e)); //$NON-NLS-1$
				}
				return Collections.EMPTY_LIST.toArray();
			}
			return myAdapterFactoryContentProvier.getChildren(parentElement);
		}

		public Object getParent(Object element) {
			Object parent = myWorkbenchContentProvider.getParent(element);
			if (parent != null) {
				return parent;
			}
			if (element instanceof EObject) {
				EObject eObject = (EObject) element;
				if (eObject.eContainer() == null && eObject.eResource().getURI().isFile()) {
					String path = eObject.eResource().getURI().path();
					return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(path));
				}
				return myAdapterFactoryContentProvier.getParent(eObject);
			}
			return null;
		}

		public boolean hasChildren(Object element) {
			if (element instanceof IFile) {
				return isValidModelFile((IFile) element);
			}
			return myWorkbenchContentProvider.hasChildren(element) || myAdapterFactoryContentProvier.hasChildren(element);
		}

		public Object[] getElements(Object inputElement) {
			Object[] elements = myWorkbenchContentProvider.getElements(inputElement);
			return elements;
		}

		public void dispose() {
			myWorkbenchContentProvider.dispose();
			myAdapterFactoryContentProvier.dispose();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			myWorkbenchContentProvider.inputChanged(viewer, oldInput, newInput);
			myAdapterFactoryContentProvier.inputChanged(viewer, oldInput, newInput);
		}

	}

	private class ModelElementsTreeLabelProvider implements ILabelProvider {

		private final WorkbenchLabelProvider myWorkbenchLabelProvider;

		private final AdapterFactoryLabelProvider myAdapterFactoryLabelProvider;

		private ModelElementsTreeLabelProvider(AdapterFactory adapterFactory) {
			myWorkbenchLabelProvider = new WorkbenchLabelProvider();
			myAdapterFactoryLabelProvider = new AdapterFactoryLabelProvider(adapterFactory);
		}

		public Image getImage(Object element) {
			Image result = myWorkbenchLabelProvider.getImage(element);
			return result != null ? result : myAdapterFactoryLabelProvider.getImage(element);
		}

		public String getText(Object element) {
			String result = myWorkbenchLabelProvider.getText(element);
			return result != null && result.length() > 0 ? result : myAdapterFactoryLabelProvider.getText(element);
		}

		public void addListener(ILabelProviderListener listener) {
			myWorkbenchLabelProvider.addListener(listener);
			myAdapterFactoryLabelProvider.addListener(listener);
		}

		public void dispose() {
			myWorkbenchLabelProvider.dispose();
			myAdapterFactoryLabelProvider.dispose();
		}

		public boolean isLabelProperty(Object element, String property) {
			return myWorkbenchLabelProvider.isLabelProperty(element, property) || myAdapterFactoryLabelProvider.isLabelProperty(element, property);
		}

		public void removeListener(ILabelProviderListener listener) {
			myWorkbenchLabelProvider.removeListener(listener);
			myAdapterFactoryLabelProvider.removeListener(listener);
		}

	}

	private class ModelFilesFilter extends ViewerFilter {

		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (element instanceof IContainer) {
				return true;
			}
			if (element instanceof IFile) {
				IFile file = (IFile) element;
				return isValidModelFile(file);
			}
			return true;
		}

	}

	private class OkButtonEnabler implements ISelectionChangedListener {

		private final PreferencesHint myPreferenceHint;

		private OkButtonEnabler(PreferencesHint preferenceHint) {
			myPreferenceHint = preferenceHint;
		}

		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSelection() instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				getSelectedModelElementURIs().clear();
				boolean isAllOk = true;
				for (Object selectedElement : selection.toList()) {
					URI elementURI = getElementURI(selectedElement);
					if (elementURI != null) {
						getSelectedModelElementURIs().add(elementURI);
					} else {
						getSelectedModelElementURIs().clear();
						isAllOk = false;
						break;
					}
				}
				setOkButtonEnabled(isAllOk);
			}
		}

		private URI getElementURI(Object selectedElement) {
			URI result = null;
			if (selectedElement instanceof IWrapperItemProvider) {
				selectedElement = ((IWrapperItemProvider) selectedElement).getValue();
			}
			if (selectedElement instanceof FeatureMap.Entry) {
				selectedElement = ((FeatureMap.Entry) selectedElement).getValue();
			}
			if (selectedElement instanceof EObject) {
				EObject selectedModelElement = (EObject) selectedElement;
				if (ViewService.getInstance().provides(Node.class, new EObjectAdapter(selectedModelElement), myView, null, ViewUtil.APPEND, true, myPreferenceHint)) {
					result = EcoreUtil.getURI(selectedModelElement);
				}
			}
			return result;
		}

	}

}
