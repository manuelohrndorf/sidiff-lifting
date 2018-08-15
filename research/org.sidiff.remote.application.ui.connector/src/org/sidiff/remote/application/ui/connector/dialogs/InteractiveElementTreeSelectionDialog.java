package org.sidiff.remote.application.ui.connector.dialogs;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.application.connector.exception.InvalidSessionException;
import org.sidiff.remote.application.connector.exception.RemoteApplicationException;
import org.sidiff.remote.application.connector.settings.RepositorySettings;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeModel;
import org.sidiff.remote.application.ui.connector.model.AdaptableTreeNode;
import org.sidiff.remote.application.ui.connector.model.ModelUtil;
import org.sidiff.remote.common.ProxyObject;

public class InteractiveElementTreeSelectionDialog extends ElementTreeSelectionDialog {


	private RepositorySettings settings;
	
	public InteractiveElementTreeSelectionDialog(Shell parent, ILabelProvider labelProvider,
			ITreeContentProvider contentProvider, RepositorySettings settings) {
		super(parent, labelProvider, contentProvider);
		this.settings = settings;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		TreeViewer treeViewer = getTreeViewer();
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if(selection instanceof IStructuredSelection) {
					IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					AdaptableTreeNode selectedAdaptableTreeNode = (AdaptableTreeNode) structuredSelection.getFirstElement();
					if(selectedAdaptableTreeNode == null) {
						selectedAdaptableTreeNode = ((AdaptableTreeModel)treeViewer.getInput()).getRoot();
					}else {
						settings.setRepositoryPath(selectedAdaptableTreeNode.getPath());
					}
					try {
						if(selectedAdaptableTreeNode.getChildren().isEmpty()) {
							List<ProxyObject> proxyObjects = ConnectorFacade.browseRepositoryContent(settings.getRepositoryURL(), settings.getRepositoryPort(), settings.getRepositoryPath(), settings.getUserName(), settings.getPassword());
							selectedAdaptableTreeNode.addAllChildren(ModelUtil.transform(proxyObjects));
							treeViewer.refresh();
						}
						
					} catch (ConnectionException | InvalidSessionException | RemoteApplicationException e) {
						MessageDialog.openError(getShell(), e.getClass().getSimpleName(), e.getMessage());
						e.printStackTrace();
					}
				}
				
			}
		});
		
		return control;
	}

}
