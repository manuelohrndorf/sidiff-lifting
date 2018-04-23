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
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.remote.application.exception.UnsupportedProtocolException;
import org.sidiff.remote.common.ProtocolHandler;
import org.sidiff.remote.common.commands.BrowseModelFilesReply;
import org.sidiff.remote.common.commands.BrowseModelFilesRequest;
import org.sidiff.remote.common.commands.BrowseModelReply;
import org.sidiff.remote.common.commands.BrowseModelRequest;
import org.sidiff.remote.common.commands.CheckoutSubModelReply;
import org.sidiff.remote.common.commands.CheckoutSubModelRequest;
import org.sidiff.remote.common.commands.Command;
import org.sidiff.remote.common.commands.GetRequestedModelElementsReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsRequest;
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
	
	private ServerConfiguration config;
	
	private ServerSocket server;
	
	private Map<String, SiDiffRemoteApplication> client_sessions;
	
	private IWorkspace workspace;
	
	private ProtocolHandler protocolHandler;
	
	private Logger log;
	
	@Override
	public Object start(IApplicationContext context) throws IOException {
		
		this.workspace = ResourcesPlugin.getWorkspace();
		
		initializeLogger();
				
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		String arg_port = args[0];
		
		Integer port = arg_port == null || arg_port.isEmpty() ? 2345 : Integer.parseInt(arg_port);
		
		this.config = new ServerConfiguration(port);
		
		this.server = new ServerSocket(config.PORT);
		
		this.client_sessions = new HashMap<String, SiDiffRemoteApplication>();
		
		log.log(Level.INFO, "Sidiff Remote Application Server is starting");
		this.protocolHandler = new ProtocolHandler(this.workspace.getRoot().getLocation().toOSString());
		log.log(Level.INFO, config.toString());
		log.log(Level.INFO, "Localization: " + workspace.getRoot().getLocation().toOSString());
		
		while(true) {
			log.log(Level.INFO, "waiting for request");
			Socket client = server.accept();
			try {
				log.log(Level.INFO, "processing request:");
				handleRequest(client);
			} catch (CoreException | UnsupportedProtocolException | IOException | ClassNotFoundException | UncoveredChangesException | InvalidModelException | NoCorrespondencesException | NotInitializedException | ExtendedSlicingCriteriaIntersectionException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
				handleException(client, e);
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
		
		log.log(Level.INFO, command.toString());
		
		SiDiffRemoteApplication app = client_sessions.get(command.getSession().getSessionID());
		if(app == null) {
			log.log(Level.INFO, "create new remote session");
			app = new SiDiffRemoteApplication(this.workspace, command.getSession());
			client_sessions.put(command.getSession().getSessionID(), app);
			
		}
		app.setSession(command.getSession());

		File attachment = null;
		switch(command.getECommand()) {
		case BROWSE_MODEL_FILES_REQUEST:
			BrowseModelFilesRequest browseModelFilesRequest = (BrowseModelFilesRequest) command;
			String local_model_path = browseModelFilesRequest.getLocalModelPath();
			TreeModel modelFiles = app.browseModelFiles(local_model_path);
			BrowseModelFilesReply browseModelFilesReply = new BrowseModelFilesReply(app.getSession(), modelFiles, null);
			this.protocolHandler.write(out, browseModelFilesReply, null);
			break;
			
		case BROWSE_MODEL_REQUEST:
			BrowseModelRequest browseModelRequest = (BrowseModelRequest) command;
			TreeModel treeModel = app.browseModel(browseModelRequest.getRemoteModelPath());
			BrowseModelReply browseModelReply = new BrowseModelReply(app.getSession(), treeModel);
			this.protocolHandler.write(out, browseModelReply, null);
			break;
			
		case CHECKOUT_SUB_MODEL_REQUEST:
			CheckoutSubModelRequest checkoutSubModelRequest = (CheckoutSubModelRequest) command;
			String localPath = checkoutSubModelRequest.getLocalModelPath();
			String remotePath = checkoutSubModelRequest.getRemoteModelPath();
			List<String> elementIds = checkoutSubModelRequest.getElementIds();
			attachment = app.checkoutModel(remotePath, localPath, elementIds);
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
			
		case GET_REQUESTED_MODEL_ELEMENTS_REQUEST:
			GetRequestedModelElementsRequest getRequestedModelElementsRequest = (GetRequestedModelElementsRequest) command;
			String localPathRME = getRequestedModelElementsRequest.getLocalModelPath();
			TreeModel treeModelRME = app.getRequestedModelElements(localPathRME);
			GetRequestedModelElementsReply getRequestedModelElementsReply = new GetRequestedModelElementsReply(app.getSession(), treeModelRME);
			this.protocolHandler.write(out, getRequestedModelElementsReply, null);
			break;
		default:
		}
	}
	
	private void handleException(Socket client, Exception e) throws IOException {
		this.log.log(Level.SEVERE, "An error occured", e);
	}
	
	private void initializeLogger() throws SecurityException, IOException {
		this.log = Logger.getLogger(this.getClass().getName());
		Handler errorHandler = new FileHandler(this.workspace.getRoot().getLocation().toOSString() + File.separator + "error.log");
		errorHandler.setLevel(Level.SEVERE);
		this.log.addHandler(errorHandler);
	}

}
