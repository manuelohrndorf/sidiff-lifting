package org.sidiff.remote.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.emf.modelstorage.UUIDResource;
import org.sidiff.remote.application.exception.UnsupportedProtocolException;
import org.sidiff.remote.common.ProtocolHandler;
import org.sidiff.remote.common.commands.BrowseModelFilesReply;
import org.sidiff.remote.common.commands.BrowseModelReply;
import org.sidiff.remote.common.commands.BrowseModelRequest;
import org.sidiff.remote.common.commands.CheckoutSubModelReply;
import org.sidiff.remote.common.commands.CheckoutSubModelRequest;
import org.sidiff.remote.common.commands.Command;
import org.sidiff.remote.common.tree.TreeModel;
import org.sidiff.slicer.rulebased.exceptions.ExtendedSlicingCriteriaIntersectionException;
import org.sidiff.slicer.rulebased.exceptions.NotInitializedException;
import org.sidiff.slicer.rulebased.exceptions.UncoveredChangesException;

/**
 * 
 * @author cpietsch
 *
 */
public class SiDiffRemoteApplicationServer implements IApplication {
		
	private ServerSocket server;
	
	private Map<String, SiDiffRemoteApplication> client_sessions;
	
	private IWorkspace workspace;
	
	private ProtocolHandler protocolHandler;

	@Override
	public Object start(IApplicationContext context) throws IOException {
		
		System.out.println("/ Sidiff Remote Application is starting");
				
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		String arg_port = args[0];
		
		Integer port = arg_port == null || arg_port.isEmpty() ? 2345 : Integer.parseInt(arg_port);
		
		this.server = new ServerSocket(port);
		
		this.client_sessions = new HashMap<String, SiDiffRemoteApplication>();
		
		this.workspace = ResourcesPlugin.getWorkspace();
		
		this.protocolHandler = new ProtocolHandler(this.workspace.getRoot().getLocation().toOSString());
		System.out.println("port: " + port);
		System.out.println("workspace: " + workspace.getRoot().getLocation().toOSString());
		System.out.println("Sidiff Remote Application is running");
		
		while(true) {
			System.out.println("waiting for request");
			Socket client = server.accept();
			try {
				System.out.println("processing request");
				handleRequest(client);
			} catch (CoreException | UnsupportedProtocolException | IOException | ClassNotFoundException | UncoveredChangesException | InvalidModelException | NoCorrespondencesException | NotInitializedException | ExtendedSlicingCriteriaIntersectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				client.close();
			}
		}
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}
	
	private void handleRequest(Socket client) throws CoreException, UnsupportedProtocolException, IOException, ClassNotFoundException, UncoveredChangesException, InvalidModelException, NoCorrespondencesException, NotInitializedException, ExtendedSlicingCriteriaIntersectionException  {
		InputStream in = client.getInputStream();
		OutputStream out = client.getOutputStream();
		
		Command command = this.protocolHandler.read(in);
		
		SiDiffRemoteApplication app = client_sessions.get(command.getSession().getSessionID());
		if(app == null) {
			app = new SiDiffRemoteApplication(this.workspace, command.getSession());
			client_sessions.put(command.getSession().getSessionID(), app);
			
		}
		app.setSession(command.getSession());
		System.out.println("client session:" + command.getSession().getSessionID());
		File attachment = null;
		switch(command.getECommand()) {
		case BROWSE_MODEL_FILES_REQUEST:
			TreeModel modelFiles = app.browseModelFiles();
			BrowseModelFilesReply browseModelFilesReply = new BrowseModelFilesReply(app.getSession(), modelFiles, null);
			this.protocolHandler.write(out, browseModelFilesReply, null);
			break;
			
		case BROWSE_MODEL_REQUEST:
			BrowseModelRequest browseModelRequest = (BrowseModelRequest) command;
			TreeModel model = app.browseModel(browseModelRequest.getRemoteModelPath());
			BrowseModelReply browseModelReply = new BrowseModelReply(app.getSession(), model, null);
			this.protocolHandler.write(out, browseModelReply, null);
			break;
			
		case CHECKOUT_SUB_MODEL_REQUEST:
			CheckoutSubModelRequest checkoutSubModelRequest = (CheckoutSubModelRequest) command;
			String localPath = checkoutSubModelRequest.getLocalModelPath();
			String remotePath = checkoutSubModelRequest.getRemoteModelPath();
			List<String> elementIds = checkoutSubModelRequest.getElementIds();
			attachment = app.checkoutModel(remotePath, elementIds);
			app.getSession().addModel(localPath, remotePath);
			CheckoutSubModelReply checkoutSubModelReply = new CheckoutSubModelReply(app.getSession(), attachment);
			this.protocolHandler.write(out, checkoutSubModelReply, attachment);
//			String[] output = this.protocolHandler.getContent().toString().split("\\n");
//			String localPath = output[0];
//			String remotePath= output[1];
//			String[] elementIDs = new String[output.length-2];
//			for(int i = 2; i < output.length; i++) {
//				if(i == 0) {
//					elementIDs[i-2] = output[i];
//				}
//			}
//			resource = app.checkoutModel(remotePath, elementIDs);
//			this.protocolHandler.getSession().addModel(localPath, remotePath);
//			this.protocolHandler.write(out, app.getSession(), Command.CHECKOUT, ContentType.FILE, resource);
			break;
		default:
		}
	}

}
