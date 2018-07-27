package org.sidiff.remote.application.connector;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.application.connector.exception.InvalidSessionException;
import org.sidiff.remote.application.connector.exception.RemoteApplicationException;
import org.sidiff.remote.common.Credentials;
import org.sidiff.remote.common.ECommand;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.commands.AddRepositoryRequest;
import org.sidiff.remote.common.commands.BrowseReply;
import org.sidiff.remote.common.commands.BrowseRequest;
import org.sidiff.remote.common.commands.CheckoutSubModelReply;
import org.sidiff.remote.common.commands.CheckoutSubModelRequest;
import org.sidiff.remote.common.commands.Command;
import org.sidiff.remote.common.commands.ErrorReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsRequest;
import org.sidiff.remote.common.commands.ListRepositoryContentReply;
import org.sidiff.remote.common.commands.ListRepositoryContentRequest;
import org.sidiff.remote.common.commands.ReplyCommand;
import org.sidiff.remote.common.commands.UpdateSubModelReply;
import org.sidiff.remote.common.commands.UpdateSubModelRequest;
import org.sidiff.remote.common.tree.TreeModel;

/**
 * 
 * @author cpietsch
 *
 */
public class ConnectorFacade {
	
	public static final ConnectionHandler CONNECTION_HANDLER = ConnectionHandler.getInstance();

	public static List<ProxyObject> listRepository(String repository_url, int repository_port, String repository_path, String repository_user_name, char[] repository_password) throws ConnectionException, InvalidSessionException, RemoteApplicationException {
		ListRepositoryContentRequest listRepositoryContentRequest = new ListRepositoryContentRequest(getCredentials(), repository_url, repository_port, repository_path, repository_user_name, repository_password);
		
		Command replyCommand = CONNECTION_HANDLER.handleRequest(listRepositoryContentRequest, null);
		if(replyCommand.getECommand().equals(ECommand.LIST_REPOSITORY_CONTENT_REPLY)) {
			ListRepositoryContentReply listRepositoryContentReply = (ListRepositoryContentReply) replyCommand;
			return listRepositoryContentReply.getProxyObjects();
		}else {
			ErrorReply errorReply = (ErrorReply) replyCommand;
			throw new RemoteApplicationException(errorReply.getErrorReport());
		}
	}
	
	public static void addRepository(String repository_url, int repository_port, String repository_path, String repository_user_name, char[] repository_password) throws ConnectionException, InvalidSessionException, RemoteApplicationException {
		AddRepositoryRequest addRepositoryRequest = new AddRepositoryRequest(getCredentials(), repository_url, repository_port, repository_path, repository_user_name, repository_password);
		
		Command replyCommand = CONNECTION_HANDLER.handleRequest(addRepositoryRequest, null);
		if(replyCommand.getECommand().equals(ECommand.ADD_REPOSITORY_REPLY)) {
			getSession().getRepositories().add(repository_url);
			saveSession();
		}else {
			ErrorReply errorReply = (ErrorReply) replyCommand;
			throw new RemoteApplicationException(errorReply.getErrorReport());
		}
	}
	
	public static List<ProxyObject> browse(String session_path, String element_id) throws ConnectionException, RemoteApplicationException{
		BrowseRequest browseRequest = new BrowseRequest(getCredentials(), session_path, element_id);
		Command replayCommand = (ReplyCommand) CONNECTION_HANDLER.handleRequest(browseRequest, null);
		if(replayCommand.getECommand().equals(ECommand.BROWSE_REPLY)) {
			BrowseReply browseReply = (BrowseReply) replayCommand;
			return browseReply.getProxyObjects();
		}else {
			ErrorReply errorReply = (ErrorReply) replayCommand;
			throw new RemoteApplicationException(errorReply.getErrorReport());
		}
	}
	
	/**
	 * 
	 * @param remote_model_path
	 * 				the session based remote model path
	 * @param target_model_path
	 * 				absolute local os-based location path of the model file
	 * @param elementIds
	 * @throws ConnectionException
	 * @throws InvalidSessionException 
	 * @throws RemoteApplicationException 
	 */
	public static File checkoutSubModel(String remote_model_path, String target_model_path, Set<String> elementIds) throws ConnectionException, InvalidSessionException, RemoteApplicationException {
		
		CheckoutSubModelRequest checkoutCommand = new CheckoutSubModelRequest(getCredentials(), remote_model_path, target_model_path, elementIds);		
		ReplyCommand replyCommand = (ReplyCommand) CONNECTION_HANDLER.handleRequest(checkoutCommand, null);
		if(replyCommand.getECommand().equals(ECommand.CHECKOUT_SUB_MODEL_REPLY)) {
			CheckoutSubModelReply checkoutSubModelReply = (CheckoutSubModelReply) replyCommand;
			File model_file = checkoutSubModelReply.getAttachment();
			File target_model = new File(target_model_path);
			model_file.renameTo(target_model); 
			getSession().addModel(checkoutCommand.getLocalModelPath(), remote_model_path, target_model);
			saveSession();
			return target_model;
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
	public static TreeModel getRequestedModelElements(String local_model_path) throws ConnectionException, InvalidSessionException {
		
		GetRequestedModelElementsRequest getRequestedModelElementsRequest = new GetRequestedModelElementsRequest(getCredentials(), local_model_path);
		GetRequestedModelElementsReply getRequestedModelElementsReply = (GetRequestedModelElementsReply) CONNECTION_HANDLER.handleRequest(getRequestedModelElementsRequest, null);
		
		return getRequestedModelElementsReply.getModel();
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
		
		UpdateSubModelRequest updateSubModelRequest = new UpdateSubModelRequest(getCredentials(), local_model_path, elementIds);
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
