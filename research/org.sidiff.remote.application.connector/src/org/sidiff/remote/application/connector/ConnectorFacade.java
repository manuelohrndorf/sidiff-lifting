package org.sidiff.remote.application.connector;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.application.connector.exception.InvalidSessionException;
import org.sidiff.remote.application.connector.exception.RemoteApplicationException;
import org.sidiff.remote.application.connector.session.Session;
import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.commands.BrowseRemoteApplicationContentRequest;
import org.sidiff.remote.common.commands.BrowseRemoteApplicationReply;
import org.sidiff.remote.common.commands.BrowseRepositoryContentReply;
import org.sidiff.remote.common.commands.BrowseRepositoryContentRequest;
import org.sidiff.remote.common.commands.CheckoutRepositoryContentReply;
import org.sidiff.remote.common.commands.CheckoutRepositoryContentRequest;
import org.sidiff.remote.common.commands.CheckoutSubModelReply;
import org.sidiff.remote.common.commands.CheckoutSubModelRequest;
import org.sidiff.remote.common.commands.Command;
import org.sidiff.remote.common.commands.ErrorReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsRequest;
import org.sidiff.remote.common.commands.GetRequestedModelFileReply;
import org.sidiff.remote.common.commands.GetRequestedModelFileRequest;
import org.sidiff.remote.common.commands.GetServerPropertiesReply;
import org.sidiff.remote.common.commands.GetServerPropertiesRequest;
import org.sidiff.remote.common.commands.ReplyCommand;
import org.sidiff.remote.common.commands.UpdateSubModelReply;
import org.sidiff.remote.common.commands.UpdateSubModelRequest;
import org.sidiff.remote.common.settings.IRemotePreferencesSupplier;
import org.sidiff.remote.common.settings.RemotePreferences;

/**
 * 
 * @author cpietsch
 *
 */
public class ConnectorFacade {
	
	public static final ConnectionHandler CONNECTION_HANDLER = ConnectionHandler.getInstance();

	/**
	 * browses the remote repository content
	 * 
	 * @param repository_url
	 * 			the URL of the repository, i.e. protocol + domain + name
	 * @param repository_port
	 * 			the port of the repository
	 * @param repository_path
	 * 			an absolute path within the repository
	 * @param repository_user_name
	 * 			the user name for accessing the repository content
	 * @param repository_password
	 * 			the password for accessing the repository content
	 * @return the content of the repository location as {@link List}
	 * of {@link ProxyObject}s
	 * 		
	 * @throws ConnectionException
	 * @throws InvalidSessionException
	 * @throws RemoteApplicationException
	 */
	public static List<ProxyObject> browseRepositoryContent(String repository_url, int repository_port, String repository_path, String repository_user_name, char[] repository_password) throws ConnectionException, InvalidSessionException, RemoteApplicationException {
		BrowseRepositoryContentRequest browseRepositoryContentRequest = new BrowseRepositoryContentRequest(getCredentials(), repository_url, repository_port, repository_path, repository_user_name, repository_password);
		
		Command replyCommand = CONNECTION_HANDLER.handleRequest(browseRepositoryContentRequest, null);
		if(replyCommand.getECommand().equals(ECommand.BROWSE_REPOSITORY_CONTENT_REPLY)) {
			BrowseRepositoryContentReply browseRepositoryContentReply = (BrowseRepositoryContentReply) replyCommand;
			return browseRepositoryContentReply.getProxyObjects();
		}else {
			ErrorReply errorReply = (ErrorReply) replyCommand;
			throw new RemoteApplicationException(errorReply.getErrorReport());
		}
	}
	
	/**
	 * checkout to the remote application file system
	 * 
	 * @param repository_url
	 * 			the URL of the repository, i.e. protocol + domain + name
	 * @param repository_port
	 * 			the port of the repository
	 * @param repository_path
	 * 			an absolute path within the repository
	 * @param repository_user_name
	 * 			the user name for accessing the repository content
	 * @param repository_password
	 * 			the password for accessing the repository content
	 * @throws ConnectionException
	 * @throws InvalidSessionException
	 * @throws RemoteApplicationException
	 */
	public static void checkoutRepositoryContent(String repository_url, int repository_port, String repository_path, String repository_user_name, char[] repository_password) throws ConnectionException, InvalidSessionException, RemoteApplicationException {
		CheckoutRepositoryContentRequest checkoutRepositoryContentRequest = new CheckoutRepositoryContentRequest(getCredentials(), repository_url, repository_port, repository_path, repository_user_name, repository_password);
		
		Command replyCommand = CONNECTION_HANDLER.handleRequest(checkoutRepositoryContentRequest, null);
		if(replyCommand.getECommand().equals(ECommand.CHECKOUT_REPOSITORY_CONTENT_REPLY)) {
			CheckoutRepositoryContentReply checkoutRepositoryContentReply = (CheckoutRepositoryContentReply) replyCommand;
			saveSession();
			//TODO return some information about the files
		}else {
			ErrorReply errorReply = (ErrorReply) replyCommand;
			throw new RemoteApplicationException(errorReply.getErrorReport());
		}
	}

	/**
	 * Browses the remote application content. If the requested file is a directory,
	 * its content is returned. If the requested file is a model file and no
	 * elementID is given, the root elements of the model are returned. If an
	 * elementID is given, the elements contained by the identified element are
	 * returned.
	 * 
	 * @param session_path
	 *            a session based path of a requested file, i.e. a path starting
	 *            with the sessionID
	 * @param element_id
	 *            the ID of a requested model element (only used if the requested
	 *            file is a model file)
	 * @return the content of the remote application as {@link List} of
	 *         {@link ProxyObject}s
	 * @throws ConnectionException
	 * @throws RemoteApplicationException
	 */
	public static List<ProxyObject> browseRemoteApplicationContent(String session_path, String element_id) throws ConnectionException, RemoteApplicationException{
		BrowseRemoteApplicationContentRequest browseRemoteApplicationContentRequest = new BrowseRemoteApplicationContentRequest(getCredentials(), session_path, element_id);
		Command replayCommand = (ReplyCommand) CONNECTION_HANDLER.handleRequest(browseRemoteApplicationContentRequest, null);
		if(replayCommand.getECommand().equals(ECommand.BROWSE_REMOTE_APPLICATION_CONTENT_REPLY)) {
			BrowseRemoteApplicationReply browseRemoteApplicationReply = (BrowseRemoteApplicationReply) replayCommand;
			return browseRemoteApplicationReply.getProxyObjects();
		}else {
			ErrorReply errorReply = (ErrorReply) replayCommand;
			throw new RemoteApplicationException(errorReply.getErrorReport());
		}
	}
	
	/**
	 * 
	 * Extracts a submodel containing at least all elements identified by the
	 * elementIDs
	 * 
	 * @param remote_model_path
	 *            a session based path of a requested file, i.e. a path starting
	 *            with the sessionID
	 * @param target_model_path
	 *            absolute local os-based location path of the model file
	 * @param elementIds
	 *            the IDs of the model elements to be checked out.
	 * @return a {@link File} containing a submodel with all requested model
	 *         elements
	 * @throws ConnectionException
	 * @throws InvalidSessionException
	 * @throws RemoteApplicationException
	 */
	public static File checkoutSubModel(String remote_model_path, String target_model_path, Set<String> elementIds, RemotePreferences preferences) throws ConnectionException, InvalidSessionException, RemoteApplicationException {
		
		CheckoutSubModelRequest checkoutCommand = new CheckoutSubModelRequest(getCredentials(), remote_model_path, target_model_path, elementIds, preferences);		
		ReplyCommand replyCommand = (ReplyCommand) CONNECTION_HANDLER.handleRequest(checkoutCommand, null);
		if(replyCommand.getECommand().equals(ECommand.CHECKOUT_SUB_MODEL_REPLY)) {
			CheckoutSubModelReply checkoutSubModelReply = (CheckoutSubModelReply) replyCommand;
			File model_file = checkoutSubModelReply.getAttachment();
			File target_model = new File(target_model_path);
			if(target_model.exists()) {
				target_model.delete();
				getSession().removeModel(checkoutCommand.getLocalModelPath(), remote_model_path);
			}
			model_file.renameTo(target_model); 
			getSession().addModel(checkoutCommand.getLocalModelPath(), remote_model_path, target_model);
			saveSession();
			return target_model;
		}else {
			ErrorReply errorReply = (ErrorReply) replyCommand;
			throw new RemoteApplicationException(errorReply.getErrorReport());
		}
		
		
	}
	
	public static ProxyObject getRequestedModelFile(String remote_model_path) throws ConnectionException, RemoteApplicationException {
		GetRequestedModelFileRequest getRequestedModelFileRequest = new GetRequestedModelFileRequest(getCredentials(), remote_model_path);
		ReplyCommand replyCommand = (ReplyCommand) CONNECTION_HANDLER.handleRequest(getRequestedModelFileRequest, null);
		if(replyCommand.getECommand().equals(ECommand.GET_REQUESTED_MODEL_FILE_REPLY)) {
			GetRequestedModelFileReply getRequestedModelFileReply = (GetRequestedModelFileReply) replyCommand;
			return getRequestedModelFileReply.getProxyObject();
		}else {
			ErrorReply errorReply = (ErrorReply) replyCommand;
			throw new RemoteApplicationException(errorReply.getErrorReport());
		}
	}
	
	/**
	 * 
	 * @param local_model_path
	 * 				absolute local os-based location path of the model file
	 * @return
	 * @throws ConnectionException 
	 * @throws InvalidSessionException 
	 */
	public static List<ProxyObject> getRequestedModelElements(String local_model_path) throws ConnectionException, InvalidSessionException {
		
		GetRequestedModelElementsRequest getRequestedModelElementsRequest = new GetRequestedModelElementsRequest(getCredentials(), local_model_path);
		GetRequestedModelElementsReply getRequestedModelElementsReply = (GetRequestedModelElementsReply) CONNECTION_HANDLER.handleRequest(getRequestedModelElementsRequest, null);
		
		return getRequestedModelElementsReply.getProxyObjects();
	}
	
	
	public static RemotePreferences getRemotePreferences() throws ConnectionException {
		GetServerPropertiesRequest getServerPropertiesRequest = new GetServerPropertiesRequest(getCredentials());
		GetServerPropertiesReply getServerPropertiesReply = (GetServerPropertiesReply) CONNECTION_HANDLER.handleRequest(getServerPropertiesRequest, null);
	
		return getServerPropertiesReply.getRemotePreferences();
	}
	/**
	 * 
	 * @param local_model_path
	 * 				absolute local os-based location path of the model file
	 * @param elementIds
	 * @return
	 * @throws ConnectionException
	 * @throws InvalidSessionException 
	 * @throws CoreException
	 */
	public static File updateSubModel(String local_model_path, Set<String> elementIds) throws ConnectionException, InvalidSessionException {
		
		UpdateSubModelRequest updateSubModelRequest = new UpdateSubModelRequest(getCredentials(), local_model_path, elementIds, IRemotePreferencesSupplier.getDefaultRemotePreferences());
		UpdateSubModelReply updateSubModelReply = (UpdateSubModelReply) CONNECTION_HANDLER.handleRequest(updateSubModelRequest, null);
		File resource_file = updateSubModelReply.getAttachment();
		String path = local_model_path.substring(0, local_model_path.lastIndexOf(File.separator)) + File.separator + updateSubModelReply.getAttachmentName();
		resource_file.renameTo(new File(path));
		return resource_file;
	}
	
	public static Session getSession() throws InvalidSessionException {
	
		return ConnectorPlugin.getInstance().readSession();
	
	}
	
	public static void saveSession() throws InvalidSessionException {

		ConnectorPlugin.getInstance().writeSession();
		
	}
	
	public static Credentials getCredentials() {
		return ConnectorPlugin.getInstance().getCredentials();
	}
}
