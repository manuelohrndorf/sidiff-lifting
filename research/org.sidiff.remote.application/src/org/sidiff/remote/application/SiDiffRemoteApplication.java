package org.sidiff.remote.application;

import java.io.File;
import java.util.Map;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.sidiff.remote.common.Session;


public class SiDiffRemoteApplication {

	private IWorkspace workspace;
	
	private Session session;
	
	private File user_folder;
	
	private File session_folder;
	
	private File session_temp_folder;
	
	private ModelIndexer modelIndexer;
	
	public SiDiffRemoteApplication(IWorkspace workspace, Session session) throws CoreException {
		this.workspace = workspace;
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
	
}
