package org.sidiff.remote.application.ui.connector.wizards;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;
import org.sidiff.remote.application.connector.ConnectorFacade;
import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.application.connector.exception.InvalidSessionException;
import org.sidiff.remote.application.connector.exception.RemoteApplicationException;
import org.sidiff.remote.application.connector.settings.RepositorySettings;
import org.sidiff.remote.application.ui.connector.pages.RepositorySettingsPage;

/**
 * 
 * @author cpietsch
 *
 */
public class AddRepositoryLocationWizard extends Wizard {

	/**
	 * The wizard id
	 */
	public static String ID = "org.sidiff.remote.ui.connector.wizard.add_repository_location";
	
	private RepositorySettings settings;
	
	/**
	 * Holds an exception caught by an inner class
	 */
	private Exception exception;
	
	public AddRepositoryLocationWizard(RepositorySettings settings) {
		this.settings = settings;
	}
	
	// ---------- UI Elements ----------
	
	/**
	 * The new repository location {@link ImageDescriptor}
	 */
//	private static final ImageDescriptor NEW_REPO_IMG = ConnectorUIPlugin.getImageDescriptor("full/wizban/newdmprj_wiz.png");
	
	private RepositorySettingsPage repositorySettingsPage;
	
	// ---------- Wizard ----------
	
	@Override
	public void addPages() {
		repositorySettingsPage = new RepositorySettingsPage("Repsoitory Settings Page",
				"Repository Settings", settings);
		addPage(repositorySettingsPage);
	}

	@Override
	public boolean performFinish() {
		
		try {
			ConnectorFacade.checkoutRepositoryContent(this.settings.getRepositoryURL(), this.settings.getRepositoryPort(), this.settings.getRepositoryPath(), this.settings.getUserName(), this.settings.getPassword());
		} catch (ConnectionException | InvalidSessionException | RemoteApplicationException e) {
			this.exception = e;
		}
		return true;
	}
	
	public Exception getException() {
		return exception;
	}

}
