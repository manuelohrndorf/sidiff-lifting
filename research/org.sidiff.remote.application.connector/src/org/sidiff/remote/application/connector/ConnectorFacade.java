package org.sidiff.remote.application.connector;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
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
	 * @param relative_remote_file_path
	 *             path of a requested remote file relative to the current user directory
	 * @param element_id
	 *            the ID of a requested model element (only used if the requested
	 *            file is a model file)
	 * @return the content of the remote application as {@link List} of
	 *         {@link ProxyObject}s
	 * @throws ConnectionException
	 * @throws RemoteApplicationException
	 */
	public static List<ProxyObject> browseRemoteApplicationContent(String relative_remote_file_path, String element_id) throws ConnectionException, RemoteApplicationException{
		BrowseRemoteApplicationContentRequest browseRemoteApplicationContentRequest = new BrowseRemoteApplicationContentRequest(getCredentials(), relative_remote_file_path, element_id);
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
	 * @param relative_remote_model_path
	 *            path of a requested remote model file relative to the current user directory
	 * @param absolute_local_model_path
	 *            absolute local os-based location path of the model file
	 * @param elementIds
	 *            the IDs of the model elements to be checked out.
	 * @return a {@link File} containing a submodel with all requested model
	 *         elements
	 * @throws ConnectionException
	 * @throws InvalidSessionException
	 * @throws RemoteApplicationException
	 */
	public static File checkoutSubModel(String relative_remote_model_path, String absolute_local_model_path, Set<String> elementIds, RemotePreferences preferences) throws ConnectionException, InvalidSessionException, RemoteApplicationException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = workspace.getRoot().getFileForLocation(new Path(absolute_local_model_path));
		String relative_local_model_path = file.getProject().getName() + File.separator + file.getProjectRelativePath().toOSString();
		CheckoutSubModelRequest checkoutCommand = new CheckoutSubModelRequest(getCredentials(), relative_remote_model_path, relative_local_model_path, elementIds, preferences);		
		ReplyCommand replyCommand = (ReplyCommand) CONNECTION_HANDLER.handleRequest(checkoutCommand, null);
		if(replyCommand.getECommand().equals(ECommand.CHECKOUT_SUB_MODEL_REPLY)) {
			CheckoutSubModelReply checkoutSubModelReply = (CheckoutSubModelReply) replyCommand;
			File model_file = checkoutSubModelReply.getAttachment();
			File target_model = new File(absolute_local_model_path);
			if(target_model.exists()) {
				target_model.delete();
			}
			model_file.renameTo(target_model); 
			getSession().addModel(checkoutCommand.getRelativeLocalModelPath(), relative_remote_model_path, target_model);
			saveSession();
			return target_model;
		}else {
			ErrorReply errorReply = (ErrorReply) replyCommand;
			throw new RemoteApplicationException(errorReply.getErrorReport());
		}
		
		
	}
	
	/**
	 * Returns all folder and files which are reachable by any step of the relative
	 * remote model path as hierarchically structured proxy objects
	 * 
	 * @param relative_remote_model_path
	 *            path for the requested remote model file relative to the current
	 *            user directory
	 * @return list of {@link ProxyObject}s representing the folders and files
	 *         reachable by any step of the given path
	 * @throws ConnectionException
	 * @throws RemoteApplicationException
	 */
	public static List<ProxyObject> getRequestedModelFile(String relative_remote_model_path) throws ConnectionException, RemoteApplicationException {
		GetRequestedModelFileRequest getRequestedModelFileRequest = new GetRequestedModelFileRequest(getCredentials(), relative_remote_model_path);
		ReplyCommand replyCommand = (ReplyCommand) CONNECTION_HANDLER.handleRequest(getRequestedModelFileRequest, null);
		if(replyCommand.getECommand().equals(ECommand.GET_REQUESTED_MODEL_FILE_REPLY)) {
			GetRequestedModelFileReply getRequestedModelFileReply = (GetRequestedModelFileReply) replyCommand;
			return getRequestedModelFileReply.getProxyObjects();
		}else {
			ErrorReply errorReply = (ErrorReply) replyCommand;
			throw new RemoteApplicationException(errorReply.getErrorReport());
		}
	}
	
	/**
	 * Returns the requested model elements as hierarchically structured proxy
	 * objects
	 * 
	 * @param relative_remote_model_path
	 *            path for the requested remote model file relative to the current
	 *            user directory
	 * @param absolute_local_model_path
	 *            absolute local os-based location path of the model file
	 * @return list of {@link ProxyObject}s representing the requested model
	 *         elements
	 * @throws ConnectionException
	 * @throws InvalidSessionException
	 */
	public static List<ProxyObject> getRequestedModelElements(String relative_remote_model_path, String absolute_local_model_path) throws ConnectionException, InvalidSessionException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = workspace.getRoot().getFileForLocation(new Path(absolute_local_model_path));
		String relative_local_model_path = file.getProject().getName() + File.separator + file.getProjectRelativePath().toOSString();
		
		GetRequestedModelElementsRequest getRequestedModelElementsRequest = new GetRequestedModelElementsRequest(getCredentials(), relative_remote_model_path, relative_local_model_path);
		GetRequestedModelElementsReply getRequestedModelElementsReply = (GetRequestedModelElementsReply) CONNECTION_HANDLER.handleRequest(getRequestedModelElementsRequest, null);
		
		return getRequestedModelElementsReply.getProxyObjects();
	}
	
	
	public static RemotePreferences getRemotePreferences() throws ConnectionException {
		GetServerPropertiesRequest getServerPropertiesRequest = new GetServerPropertiesRequest(getCredentials());
		GetServerPropertiesReply getServerPropertiesReply = (GetServerPropertiesReply) CONNECTION_HANDLER.handleRequest(getServerPropertiesRequest, null);
	
		return getServerPropertiesReply.getRemotePreferences();
	}
	
	/**
	 * Returns a slicing edit script for updating the local submodel
	 * 
	 * @param relative_remote_model_path
	 *            path of a requested remote model file relative to the current user
	 *            directory
	 * @param absolute_local_model_path
	 *            absolute local os-based location path of the model file
	 * @param elementIds
	 *            the requested model element IDs
	 * @param preferences
	 * @return a slicing edit script for updating the local submodel
	 * 
	 * @throws ConnectionException
	 * @throws InvalidSessionException
	 */
	public static File updateSubModel(String relative_remote_model_path, String absolute_local_model_path, Set<String> elementIds, RemotePreferences preferences) throws ConnectionException, InvalidSessionException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IFile file = workspace.getRoot().getFileForLocation(new Path(absolute_local_model_path));
		String relative_local_model_path = file.getProject().getName() + File.separator + file.getProjectRelativePath().toOSString();
		
		UpdateSubModelRequest updateSubModelRequest = new UpdateSubModelRequest(getCredentials(), relative_remote_model_path, relative_local_model_path, elementIds, preferences);
		UpdateSubModelReply updateSubModelReply = (UpdateSubModelReply) CONNECTION_HANDLER.handleRequest(updateSubModelRequest, null);
		File resource_file = updateSubModelReply.getAttachment();
		
		File target_file = new File(absolute_local_model_path);
		if(target_file.exists()) {
			target_file.delete();
		}
		resource_file.renameTo(target_file);
		return target_file;
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
