package org.sidiff.remote.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.remote.application.adapters.BrowseRepositoryContentOperationResult;
import org.sidiff.remote.application.adapters.CheckoutRepositoryContentOperationResult;
import org.sidiff.remote.application.exception.AuthenticationException;
import org.sidiff.remote.application.preferences.RemotePreferencesSupplier;
import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ErrorReport;
import org.sidiff.remote.common.ProtocolHandler;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.commands.BrowseRemoteApplicationContentRequest;
import org.sidiff.remote.common.commands.BrowseRemoteApplicationReply;
import org.sidiff.remote.common.commands.BrowseRepositoryContentReply;
import org.sidiff.remote.common.commands.BrowseRepositoryContentRequest;
import org.sidiff.remote.common.commands.CheckoutRepositoryContentReply;
import org.sidiff.remote.common.commands.CheckoutRepositoryContentRequest;
import org.sidiff.remote.common.commands.CheckoutSubModelReply;
import org.sidiff.remote.common.commands.CheckoutSubModelRequest;
import org.sidiff.remote.common.commands.ErrorReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsRequest;
import org.sidiff.remote.common.commands.GetRequestedModelFileReply;
import org.sidiff.remote.common.commands.GetRequestedModelFileRequest;
import org.sidiff.remote.common.commands.GetServerPropertiesReply;
import org.sidiff.remote.common.commands.RequestCommand;
import org.sidiff.remote.common.commands.UpdateSubModelReply;
import org.sidiff.remote.common.commands.UpdateSubModelRequest;
import org.sidiff.remote.common.exceptions.AddRepositoryException;
import org.sidiff.remote.common.exceptions.CheckoutSubModelException;
import org.sidiff.remote.common.exceptions.ListRepositoryContentException;
import org.sidiff.remote.common.exceptions.ProtocolHandlerException;
import org.sidiff.remote.common.exceptions.UpdateSubModelException;
import org.sidiff.remote.common.settings.IRemotePreferencesSupplier;
import org.sidiff.remote.common.settings.RemotePreferences;

/**
 * 
 * @author cpietsch
 *
 */
public class SiDiffRemoteApplicationServer implements IApplication {
	
	private volatile boolean running = true;
	
	private ServerConfiguration config;
	
	private RemotePreferences preferences;
	
	private ServerSocket server;
	
	private Map<String, SiDiffRemoteApplication> client_apps;
	
	private IWorkspace workspace;

	@Override
	public Object start(IApplicationContext context) throws IOException, ProtocolHandlerException {
		
		this.workspace = ResourcesPlugin.getWorkspace();
				
		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		
		String config_path = "";
		if(args.length>0)
			config_path = args[0];
		
		this.config = readConfig(config_path);
		
		this.preferences = IRemotePreferencesSupplier.getAllSuppliers().get(RemotePreferencesSupplier.class.getName()).getRemotePreference();
		
		this.server = new ServerSocket(config.PORT);
		this.client_apps = new HashMap<String, SiDiffRemoteApplication>();	

		LogUtil.log(LogEvent.CONFIG, config.toString());
		
		while(running) {
			LogUtil.log(LogEvent.INFO, "waiting for request");
			Socket client = server.accept();
			ProtocolHandler protocolHandler = new ProtocolHandler(client);
			try {
				LogUtil.log(LogEvent.INFO, "processing request:");
				handleRequest(protocolHandler);
			} catch (ProtocolHandlerException | ListRepositoryContentException | AddRepositoryException | CheckoutSubModelException | UpdateSubModelException | AuthenticationException e) {
				handleException(protocolHandler, e);
			}finally {
				client.close();
			}
		}
		return null;
	}

	@Override
	public void stop() {
		running = false;
	}
	
	private void handleRequest(ProtocolHandler protocolHandler) throws ProtocolHandlerException, ListRepositoryContentException, AddRepositoryException, CheckoutSubModelException, UpdateSubModelException, AuthenticationException {
		RequestCommand command = (RequestCommand) protocolHandler.read();
		LogUtil.log(LogEvent.INFO, command.toString());
		
		if(authenticate(command.getCredentials())) {
		
			SiDiffRemoteApplication app = client_apps.get(command.getCredentials().getUser());
			if(app == null) {
				LogUtil.log(LogEvent.INFO, "initialize remote session");
				app = new SiDiffRemoteApplication(this.workspace, command.getCredentials().getUser());
				client_apps.put(command.getCredentials().getUser(), app);
				
			}

			switch(command.getECommand()) {
			case BROWSE_REPOSITORY_CONTENT_REQUEST:
				BrowseRepositoryContentRequest browseRepositoryContentRequest = (BrowseRepositoryContentRequest) command;
				BrowseRepositoryContentOperationResult browseRepositoryOperationResult = app.browseRepositoryContent(browseRepositoryContentRequest.getRepositoryUrl(), browseRepositoryContentRequest.getRepositoryPort(), browseRepositoryContentRequest.getRepositoryPath(), browseRepositoryContentRequest.getRepositoryUserName(), browseRepositoryContentRequest.getRepositoryPassword());
				BrowseRepositoryContentReply browseRepositoryContentReply = new BrowseRepositoryContentReply(browseRepositoryOperationResult.getProxyObjects());
				protocolHandler.write(browseRepositoryContentReply, null);
				break;
				
			case CHECKOUT_REPOSITORY_CONTENT_REQUEST:
				CheckoutRepositoryContentRequest checkoutRepositoryContentRequest = (CheckoutRepositoryContentRequest) command;
				CheckoutRepositoryContentOperationResult checkoutRepositoryContentOperationResult = app.checkoutRepositoryContent(checkoutRepositoryContentRequest.getRepositoryUrl(), checkoutRepositoryContentRequest.getRepositoryPort(), checkoutRepositoryContentRequest.getRepositoryPath(), checkoutRepositoryContentRequest.getRepositoryUserName(), checkoutRepositoryContentRequest.getRepositoryPassword());
				CheckoutRepositoryContentReply checkoutRepositoryContentReply = new CheckoutRepositoryContentReply();
				protocolHandler.write(checkoutRepositoryContentReply, null);
				break;
				
			case BROWSE_REMOTE_APPLICATION_CONTENT_REQUEST:
				BrowseRemoteApplicationContentRequest browseRemoteApplicationContentRequest = (BrowseRemoteApplicationContentRequest) command;
				List<ProxyObject> proxyObjects = app.browseRemoteApplicationContent(browseRemoteApplicationContentRequest.getRelativeRemoteFilePath(), browseRemoteApplicationContentRequest.getElementID());
				BrowseRemoteApplicationReply browseRemoteApplicationReply = new BrowseRemoteApplicationReply(proxyObjects);
				protocolHandler.write(browseRemoteApplicationReply, null);
				break;
				
			case CHECKOUT_SUB_MODEL_REQUEST:
				CheckoutSubModelRequest checkoutSubModelRequest = (CheckoutSubModelRequest) command;
				File attachment = app.checkoutModel(checkoutSubModelRequest.getRelativeRemoteModelPath(), checkoutSubModelRequest.getRelativeLocalModelPath(), checkoutSubModelRequest.getElementIDs(), checkoutSubModelRequest.getPreferences());
				
				CheckoutSubModelReply checkoutSubModelReply = new CheckoutSubModelReply(attachment);
				protocolHandler.write(checkoutSubModelReply, attachment);
				break;
				
			case GET_REQUESTED_MODEL_FILE_REQUEST:
				GetRequestedModelFileRequest getRequestedModelFileRequest = (GetRequestedModelFileRequest) command;
				List<ProxyObject> proxyObjectsFile = app.getRequestedModelFile(getRequestedModelFileRequest.getRelativeRemoteFilePath());
				GetRequestedModelFileReply getRequestedModelFileReply = new GetRequestedModelFileReply(proxyObjectsFile);
				protocolHandler.write(getRequestedModelFileReply, null);
				break;
				
			case GET_REQUESTED_MODEL_ELEMENTS_REQUEST:
				GetRequestedModelElementsRequest getRequestedModelElementsRequest = (GetRequestedModelElementsRequest) command;
				List<ProxyObject> proxyObjects_ = app.getRequestedModelElements(getRequestedModelElementsRequest.getRelativeRemoteModelPath(), getRequestedModelElementsRequest.getRelativeLocalModelPath());
				GetRequestedModelElementsReply getRequestedModelElementsReply = new GetRequestedModelElementsReply(proxyObjects_);
				protocolHandler.write(getRequestedModelElementsReply, null);
				break;
				
			case GET_SERVER_PROPERTIES_REQUEST:
				GetServerPropertiesReply getServerPropertiesReply = new GetServerPropertiesReply(preferences);
				protocolHandler.write(getServerPropertiesReply, null);
				break;
				
			case UPDATE_SUBMODEL_REQUEST:
				UpdateSubModelRequest updateSubModelRequest = (UpdateSubModelRequest) command;
				File modelSliceZip = app.updateSubModel(updateSubModelRequest.getRelativeRemoteModelPath(), updateSubModelRequest.getRelativeLocalModelPath(), updateSubModelRequest.getElementIDs(), updateSubModelRequest.getPreferences());
				UpdateSubModelReply updateSubModelReply = new UpdateSubModelReply(modelSliceZip);
				protocolHandler.write(updateSubModelReply, modelSliceZip);
				break;
			default:
			}
		}else {
			throw new AuthenticationException();
		}
	}
	
	private void handleException(ProtocolHandler protocolHandler, Exception e) throws ProtocolHandlerException {
		LogUtil.log(LogEvent.ERROR, "An error occured", e);

		ErrorReply errorReply = new ErrorReply(new ErrorReport(e));
		protocolHandler.write(errorReply, null);
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
