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
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.remote.application.adapters.CheckoutRepositoryContentOperationResult;
import org.sidiff.remote.application.adapters.BrowseRepositoryContentOperationResult;
import org.sidiff.remote.application.exception.AuthenticationException;
import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ErrorReport;
import org.sidiff.remote.common.ProtocolHandler;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.commands.CheckoutRepositoryContentReply;
import org.sidiff.remote.common.commands.CheckoutRepositoryContentRequest;
import org.sidiff.remote.common.commands.BrowseRemoteApplicationReply;
import org.sidiff.remote.common.commands.BrowseRemoteApplicationContentRequest;
import org.sidiff.remote.common.commands.CheckoutSubModelReply;
import org.sidiff.remote.common.commands.CheckoutSubModelRequest;
import org.sidiff.remote.common.commands.ErrorReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsRequest;
import org.sidiff.remote.common.commands.GetRequestedModelFileReply;
import org.sidiff.remote.common.commands.GetRequestedModelFileRequest;
import org.sidiff.remote.common.commands.GetServerPropertiesReply;
import org.sidiff.remote.common.commands.GetServerPropertiesRequest;
import org.sidiff.remote.common.commands.BrowseRepositoryContentReply;
import org.sidiff.remote.common.commands.BrowseRepositoryContentRequest;
import org.sidiff.remote.common.commands.RequestCommand;
import org.sidiff.remote.common.commands.UpdateSubModelReply;
import org.sidiff.remote.common.commands.UpdateSubModelRequest;
import org.sidiff.remote.common.exceptions.AddRepositoryException;
import org.sidiff.remote.common.exceptions.CheckoutSubModelException;
import org.sidiff.remote.common.exceptions.ListRepositoryContentException;
import org.sidiff.remote.common.exceptions.ProtocolHandlerException;
import org.sidiff.remote.common.exceptions.UpdateSubModelException;
import org.sidiff.remote.common.settings.RemotePreferences;

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
			} catch (ProtocolHandlerException | ListRepositoryContentException | AddRepositoryException | CheckoutSubModelException | UpdateSubModelException | AuthenticationException e) {
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
	
	private void handleRequest(Socket client) throws ProtocolHandlerException, ListRepositoryContentException, AddRepositoryException, CheckoutSubModelException, UpdateSubModelException, AuthenticationException {
		InputStream in = null;
		OutputStream out = null;
		RequestCommand command = null;
		try {
			in = client.getInputStream();
			out = client.getOutputStream();
			command = (RequestCommand) this.protocolHandler.read(in);
		} catch (ClassNotFoundException | IOException e) {
			throw new ProtocolHandlerException(e);
		}
		
		
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
			case BROWSE_REPOSITORY_CONTENT_REQUEST:
				BrowseRepositoryContentRequest browseRepositoryContentRequest = (BrowseRepositoryContentRequest) command;
				BrowseRepositoryContentOperationResult browseRepositoryOperationResult = app.browseRepositoryContent(browseRepositoryContentRequest.getRepositoryUrl(), browseRepositoryContentRequest.getRepositoryPort(), browseRepositoryContentRequest.getRepositoryPath(), browseRepositoryContentRequest.getRepositoryUserName(), browseRepositoryContentRequest.getRepositoryPassword());
				BrowseRepositoryContentReply browseRepositoryContentReply = new BrowseRepositoryContentReply(browseRepositoryOperationResult.getHost(), browseRepositoryOperationResult.getProxyObjects());
				try {
					this.protocolHandler.write(out, browseRepositoryContentReply, null);
				} catch (IOException e) {
					throw new ProtocolHandlerException(e);
				}
				break;
				
			case CHECKOUT_REPOSITORY_CONTENT_REQUEST:
				CheckoutRepositoryContentRequest checkoutRepositoryContentRequest = (CheckoutRepositoryContentRequest) command;
				CheckoutRepositoryContentOperationResult checkoutRepositoryContentOperationResult = app.checkoutRepositoryContent(checkoutRepositoryContentRequest.getRepositoryUrl(), checkoutRepositoryContentRequest.getRepositoryPort(), checkoutRepositoryContentRequest.getRepositoryPath(), checkoutRepositoryContentRequest.getRepositoryUserName(), checkoutRepositoryContentRequest.getRepositoryPassword());
				CheckoutRepositoryContentReply checkoutRepositoryContentReply = new CheckoutRepositoryContentReply(checkoutRepositoryContentOperationResult.getHost(), checkoutRepositoryContentOperationResult.getTargetPath().split("/")[0]);
				try {
					this.protocolHandler.write(out, checkoutRepositoryContentReply, null);
				} catch (IOException e) {
					throw new ProtocolHandlerException(e);
				}
				break;
				
			case BROWSE_REMOTE_APPLICATION_CONTENT_REQUEST:
				BrowseRemoteApplicationContentRequest browseRemoteApplicationContentRequest = (BrowseRemoteApplicationContentRequest) command;
				String session_path = browseRemoteApplicationContentRequest.getSessionPath();
				String element_id = browseRemoteApplicationContentRequest.getElementID();
				List<ProxyObject> proxyObjects = app.browseRemoteApplicationContent(session_path, element_id);
				BrowseRemoteApplicationReply browseRemoteApplicationReply = new BrowseRemoteApplicationReply(proxyObjects);
				try {
					this.protocolHandler.write(out, browseRemoteApplicationReply, null);
				} catch (IOException e) {
					throw new ProtocolHandlerException(e);
				}
				break;
				
			case CHECKOUT_SUB_MODEL_REQUEST:
				CheckoutSubModelRequest checkoutSubModelRequest = (CheckoutSubModelRequest) command;
				String localPath = checkoutSubModelRequest.getLocalModelPath();
				String remotePath = checkoutSubModelRequest.getRemoteModelPath();
				Set<String> elementIds = checkoutSubModelRequest.getElementIds();
				attachment = app.checkoutModel(remotePath, localPath, elementIds);
				
				CheckoutSubModelReply checkoutSubModelReply = new CheckoutSubModelReply(attachment);
				try {
					this.protocolHandler.write(out, checkoutSubModelReply, attachment);
				} catch (IOException e) {
					throw new ProtocolHandlerException(e);
				}
				break;
				
			case GET_REQUESTED_MODEL_FILE_REQUEST:
				GetRequestedModelFileRequest getRequestedModelFileRequest = (GetRequestedModelFileRequest) command;
				ProxyObject proxyObject = app.getRequestedModelFile(getRequestedModelFileRequest.getSessionPath());
				GetRequestedModelFileReply getRequestedModelFileReply = new GetRequestedModelFileReply(proxyObject);
				try {
					this.protocolHandler.write(out, getRequestedModelFileReply, null);
				} catch (IOException e) {
					throw new ProtocolHandlerException(e);
				}
				break;
				
			case GET_REQUESTED_MODEL_ELEMENTS_REQUEST:
				GetRequestedModelElementsRequest getRequestedModelElementsRequest = (GetRequestedModelElementsRequest) command;
				String localPathRME = getRequestedModelElementsRequest.getLocalModelPath();
				List<ProxyObject> proxyObjects_ = app.getRequestedModelElements(localPathRME);
				GetRequestedModelElementsReply getRequestedModelElementsReply = new GetRequestedModelElementsReply(proxyObjects_);
				try {
					this.protocolHandler.write(out, getRequestedModelElementsReply, null);
				} catch (IOException e) {
					throw new ProtocolHandlerException(e);
				}
				break;
				
			case GET_SERVER_PROPERTIES_REQUEST:
				GetServerPropertiesRequest getServerPropertiesRequest = (GetServerPropertiesRequest) command;
				RemotePreferences remotePreferences = app.getRemotePreferences();
				GetServerPropertiesReply getServerPropertiesReply = new GetServerPropertiesReply(remotePreferences);
				try {
					this.protocolHandler.write(out, getServerPropertiesReply, null);
				} catch (IOException e) {
					throw new ProtocolHandlerException(e);
				}
				break;
				
			case UPDATE_SUBMODEL_REQUEST:
				UpdateSubModelRequest updateSubModelRequest = (UpdateSubModelRequest) command;
				String localPathSubModel = updateSubModelRequest.getLocalModelPath();
				Set<String> updatedElementIds = updateSubModelRequest.getElementIds();
				File modelSliceZip = app.updateSubModel(localPathSubModel, updatedElementIds);
				UpdateSubModelReply updateSubModelReply = new UpdateSubModelReply(modelSliceZip);
				try {
					this.protocolHandler.write(out, updateSubModelReply, modelSliceZip);
				} catch (IOException e) {
					throw new ProtocolHandlerException(e);
				}
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
