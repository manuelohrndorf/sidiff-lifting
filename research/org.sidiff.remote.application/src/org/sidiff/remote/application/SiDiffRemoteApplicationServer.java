package org.sidiff.remote.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.remote.application.adapters.CheckoutOperationResult;
import org.sidiff.remote.application.adapters.ListOperationResult;
import org.sidiff.remote.application.exception.AuthenticationException;
import org.sidiff.remote.application.exception.RepositoryAdapterException;
import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ErrorReport;
import org.sidiff.remote.common.ProtocolHandler;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.commands.AddRepositoryReply;
import org.sidiff.remote.common.commands.AddRepositoryRequest;
import org.sidiff.remote.common.commands.BrowseReply;
import org.sidiff.remote.common.commands.BrowseRequest;
import org.sidiff.remote.common.commands.CheckoutSubModelReply;
import org.sidiff.remote.common.commands.CheckoutSubModelRequest;
import org.sidiff.remote.common.commands.ErrorReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsRequest;
import org.sidiff.remote.common.commands.GetRequestedModelFileReply;
import org.sidiff.remote.common.commands.GetRequestedModelFileRequest;
import org.sidiff.remote.common.commands.ListRepositoryContentReply;
import org.sidiff.remote.common.commands.ListRepositoryContentRequest;
import org.sidiff.remote.common.commands.RequestCommand;
import org.sidiff.remote.common.commands.UpdateSubModelReply;
import org.sidiff.remote.common.commands.UpdateSubModelRequest;
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
	
	@Override
	public Object start(IApplicationContext context) throws IOException {
		
		this.workspace = ResourcesPlugin.getWorkspace();
				
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		
		String config_path = "";
		if(args.length>0)
			config_path = args[0];
		
		this.config = readConfig(config_path);
		
		this.server = new ServerSocket(config.PORT);
		this.client_sessions = new HashMap<String, SiDiffRemoteApplication>();	
		this.protocolHandler = new ProtocolHandler();
		
		LogUtil.log(LogEvent.CONFIG, config.toString());
		
		while(true) {
			LogUtil.log(LogEvent.INFO, "waiting for request");
			Socket client = server.accept();
			try {
				LogUtil.log(LogEvent.INFO, "processing request:");
				handleRequest(client);
			} catch (IOException | ClassNotFoundException | UncoveredChangesException | InvalidModelException | NoCorrespondencesException | NotInitializedException | ExtendedSlicingCriteriaIntersectionException  | AuthenticationException | RepositoryAdapterException | CoreException e) {
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
	
	private void handleRequest(Socket client) throws IOException, ClassNotFoundException, UncoveredChangesException, InvalidModelException, NoCorrespondencesException, NotInitializedException, ExtendedSlicingCriteriaIntersectionException, AuthenticationException, RepositoryAdapterException, CoreException {
		InputStream in = client.getInputStream();
		OutputStream out = client.getOutputStream();
		
		RequestCommand command = (RequestCommand) this.protocolHandler.read(in);
		
		LogUtil.log(LogEvent.INFO, command.toString());
		
		if(authenticate(command.getCredentials())) {
		
			SiDiffRemoteApplication app = client_sessions.get(command.getCredentials().getSessionID());
			if(app == null) {
				LogUtil.log(LogEvent.INFO, "initialize remote session");
				app = new SiDiffRemoteApplication(this.workspace, command.getCredentials().getUser(), command.getCredentials().getSessionID());
				client_sessions.put(command.getCredentials().getSessionID(), app);
				
			}
	
			File attachment = null;
			
			switch(command.getECommand()) {
			case LIST_REPOSITORY_CONTENT_REQUEST:
				ListRepositoryContentRequest listRepositoryContentRequest = (ListRepositoryContentRequest) command;
				ListOperationResult listOperationResult = app.listRepository(listRepositoryContentRequest.getRepositoryUrl(), listRepositoryContentRequest.getRepositoryPort(), listRepositoryContentRequest.getRepositoryPath(), listRepositoryContentRequest.getRepositoryUserName(), listRepositoryContentRequest.getRepositoryPassword());
				ListRepositoryContentReply listRepositoryContentReply = new ListRepositoryContentReply(listOperationResult.getHost(), listOperationResult.getProxyObjects());
				this.protocolHandler.write(out, listRepositoryContentReply, null);
				break;
				
			case ADD_REPOSITORY_REQUEST:
				AddRepositoryRequest addRepositoryRequest = (AddRepositoryRequest) command;
				CheckoutOperationResult checkoutResult = app.addRepository(addRepositoryRequest.getRepositoryUrl(), addRepositoryRequest.getRepositoryPort(), addRepositoryRequest.getRepositoryPath(), addRepositoryRequest.getRepositoryUserName(), addRepositoryRequest.getRepositoryPassword());
				AddRepositoryReply addRepositoryReply = new AddRepositoryReply(checkoutResult.getHost(), checkoutResult.getTargetPath().split("/")[0]);
				this.protocolHandler.write(out, addRepositoryReply, null);
				break;
				
			case BROWSE_REQUEST:
				BrowseRequest browseRequest = (BrowseRequest) command;
				String session_path = browseRequest.getSessionPath();
				String element_id = browseRequest.getElementID();
				List<ProxyObject> proxyObjects = app.browse(session_path, element_id);
				BrowseReply browseReply = new BrowseReply(proxyObjects);
				this.protocolHandler.write(out, browseReply, null);
				break;
				
			case CHECKOUT_SUB_MODEL_REQUEST:
				CheckoutSubModelRequest checkoutSubModelRequest = (CheckoutSubModelRequest) command;
				String localPath = checkoutSubModelRequest.getLocalModelPath();
				String remotePath = checkoutSubModelRequest.getRemoteModelPath();
				Set<String> elementIds = checkoutSubModelRequest.getElementIds();
				attachment = app.checkoutModel(remotePath, localPath, elementIds);
				
				CheckoutSubModelReply checkoutSubModelReply = new CheckoutSubModelReply(attachment);
				this.protocolHandler.write(out, checkoutSubModelReply, attachment);
				break;
				
			case GET_REQUESTED_MODEL_FILE_REQUEST:
				GetRequestedModelFileRequest getRequestedModelFileRequest = (GetRequestedModelFileRequest) command;
				ProxyObject proxyObject = app.getRequestedModelFile(getRequestedModelFileRequest.getSessionPath());
				GetRequestedModelFileReply getRequestedModelFileReply = new GetRequestedModelFileReply(proxyObject);
				this.protocolHandler.write(out, getRequestedModelFileReply, null);
				break;
				
			case GET_REQUESTED_MODEL_ELEMENTS_REQUEST:
				GetRequestedModelElementsRequest getRequestedModelElementsRequest = (GetRequestedModelElementsRequest) command;
				String localPathRME = getRequestedModelElementsRequest.getLocalModelPath();
				List<ProxyObject> proxyObjects_ = app.getRequestedModelElements(localPathRME);
				GetRequestedModelElementsReply getRequestedModelElementsReply = new GetRequestedModelElementsReply(proxyObjects_);
				this.protocolHandler.write(out, getRequestedModelElementsReply, null);
				break;
				
			case UPDATE_SUBMODEL_REQUEST:
				UpdateSubModelRequest updateSubModelRequest = (UpdateSubModelRequest) command;
				String localPathSubModel = updateSubModelRequest.getLocalModelPath();
				Set<String> updatedElementIds = updateSubModelRequest.getElementIds();
				File modelSliceZip = app.updateSubModel(localPathSubModel, updatedElementIds);
				UpdateSubModelReply updateSubModelReply = new UpdateSubModelReply(modelSliceZip);
				this.protocolHandler.write(out, updateSubModelReply, modelSliceZip);
				break;
			default:
			}
		}else {
			throw new AuthenticationException();
		}
	}
	
	private void handleException(Socket client, Exception e) throws IOException {
		LogUtil.log(LogEvent.ERROR, "An error occured", e);
		OutputStream out = client.getOutputStream();
		
		ErrorReply errorReply = new ErrorReply(new ErrorReport(e));
		protocolHandler.write(out, errorReply, null);
	}
	
	
	private ServerConfiguration readConfig(String path) throws IOException {
		Properties properties_cfg = new Properties();
		InputStream in = null;
		if(path == null || path.isEmpty()) {
			in = getClass().getResourceAsStream("/config/default.cfg");
		}else {
			File config_file = new File(path);
			in = new FileInputStream(config_file);
		}
		
		properties_cfg.load(in);
		in.close();
		String url = properties_cfg.getProperty("url");
		int port = Integer.parseInt(properties_cfg.getProperty("port"));
		String user_path = properties_cfg.getProperty("user");
		
		Properties properties_user = new Properties();
		if(user_path.equals("DEFAULT")) {
			in = getClass().getResourceAsStream("/config/default.user");
		}else {
			File user_file = new File(user_path);
			in = new FileInputStream(user_file);
		}
		
		properties_user.load(in);
		in.close();
		
		Map<String, String> users = new HashMap<String, String>();
		for(Object key : properties_user.keySet()) {
			String user_name = (String) key;
			String password = properties_user.getProperty(user_name);
			users.put(user_name, password);
		}
		ServerConfiguration config = new ServerConfiguration(url, port, users);
		return config;
	}
	
	private boolean authenticate(Credentials credentials) {
		if(config.users.containsKey(credentials.getUser())) {
			//TODO use public-key cryptography
			return config.users.get(credentials.getUser()).equals(credentials.getPassword());
		}
		return false;
	}

}
