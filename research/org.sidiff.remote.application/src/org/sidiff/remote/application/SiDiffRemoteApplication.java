package org.sidiff.remote.application;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.remote.common.Session;

/**
 * 
 * @author cpietsch
 *
 */
public class SiDiffRemoteApplication {

	private IWorkspace workspace;
	
	private Session session;
	
	private File user_folder;
	
	private File session_folder;
	
	private File session_temp_folder;
	
	private ModelIndexer modelIndexer;
	
	public SiDiffRemoteApplication(IWorkspace workspace, Session session) throws CoreException {
		this.workspace = workspace;
		this.session = session;
		String ws_path = this.workspace.getRoot().getLocation().toOSString();
		String	user_path= ws_path + File.separator + session.getUser();
		String session_path = user_path + File.separator + session.getSessionID();
		String session_temp_path = session_path + File.separator + "temp";
		
		this.user_folder = new File(user_path);
		if(!user_folder.exists()) {
			user_folder.mkdir();
		}
		this.session_folder = new File(session_path);
		if(!session_folder.exists()) {
			session_folder.mkdir();
		}
		this.modelIndexer = new ModelIndexer(session, session_folder);
		
		this.session_temp_folder = new File(session_temp_path);
		if(!session_temp_folder.exists()) {
			session_temp_folder.mkdir();
		}
	}
	
	public void handle(Map<String, Object> args) {
		System.out.println(args.get("Command"));
		System.out.println(this.user_folder.getPath());
		System.out.println(this.session_folder.getPath());
	}
	
	public UUIDResource browseModel(String path) {
		String absolute_path = user_folder + File.separator + path;
		ResourceSet resourceSet = new ResourceSetImpl();
		UUIDResource uuidResource = new UUIDResource(EMFStorage.pathToUri(absolute_path), resourceSet);
		return uuidResource;
	}
	
	public List<File> browseModelFiles() {
		this.modelIndexer.index();
		return this.modelIndexer.getModel_files();
	}
	
	public Session getSession() {
		return session;
	}
}
