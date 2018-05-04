package org.sidiff.remote.application.connector;

import java.io.File;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.sidiff.remote.application.connector.exception.ConnectionException;
import org.sidiff.remote.common.Session;
import org.sidiff.remote.common.commands.AddRepositoryReply;
import org.sidiff.remote.common.commands.AddRepositoryRequest;
import org.sidiff.remote.common.commands.BrowseModelFilesReply;
import org.sidiff.remote.common.commands.BrowseModelFilesRequest;
import org.sidiff.remote.common.commands.BrowseModelReply;
import org.sidiff.remote.common.commands.BrowseModelRequest;
import org.sidiff.remote.common.commands.CheckoutSubModelReply;
import org.sidiff.remote.common.commands.CheckoutSubModelRequest;
import org.sidiff.remote.common.commands.GetRequestedModelElementsReply;
import org.sidiff.remote.common.commands.GetRequestedModelElementsRequest;
import org.sidiff.remote.common.commands.UpdateSubModelReply;
import org.sidiff.remote.common.commands.UpdateSubModelRequest;
import org.sidiff.remote.common.exceptions.InvalidSessionException;
import org.sidiff.remote.common.tree.TreeModel;

/**
 * 
 * @author cpietsch
 *
 */
public class ConnectorFacade {
	
	public static final ConnectionHandler CONNECTION_HANDLER = ConnectionHandler.getInstance();

	public static void addRepository(String repository_url, int repository_prot, String repository_user_name, char[] repository_password) throws ConnectionException, InvalidSessionException {
		AddRepositoryRequest addRepositoryRequest = new AddRepositoryRequest(getSession(), repository_url, repository_prot ,repository_user_name, repository_password);
		AddRepositoryReply addRepositoryReply = (AddRepositoryReply) CONNECTION_HANDLER.handleRequest(addRepositoryRequest, null);
	}
	
	/**
	 * 
	 * @param local_model_path
	 * 			absolute local os-based location path of the model file
	 * @return
	 * @throws ConnectionException
	 * @throws InvalidSessionException 
	 */
	public static TreeModel browseModelFiles(String local_model_path) throws ConnectionException, InvalidSessionException  {
		
		BrowseModelFilesRequest browseModelFilesRequest = new BrowseModelFilesRequest(getSession(), local_model_path);
		BrowseModelFilesReply browseModelFilesReply = (BrowseModelFilesReply) CONNECTION_HANDLER.handleRequest(browseModelFilesRequest, null); 
		
		return browseModelFilesReply.getModelFiles();
	}
	
	/**
	 * 
	 * @param remote_model_path
	 * 				the session based remote model path
	 * @return
	 * @throws ConnectionException
	 * @throws InvalidSessionException 
	 */
	public static TreeModel browseModelElements(String remote_model_path) throws ConnectionException, InvalidSessionException {
		
		BrowseModelRequest browseModelRequest = new BrowseModelRequest(getSession(), remote_model_path);
		BrowseModelReply browseModelReply = (BrowseModelReply) CONNECTION_HANDLER.handleRequest(browseModelRequest, null);
		
		return browseModelReply.getModel();
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
	 */
	public static File checkoutSubModel(String remote_model_path, String target_model_path, Set<String> elementIds) throws ConnectionException, InvalidSessionException {
		
		CheckoutSubModelRequest checkoutCommand = new CheckoutSubModelRequest(getSession(), remote_model_path, target_model_path, elementIds);		
		CheckoutSubModelReply reply = (CheckoutSubModelReply) CONNECTION_HANDLER.handleRequest(checkoutCommand, null);
		File model_file = reply.getAttachment();
		model_file.renameTo(new File(target_model_path)); 
		getSession().addModel(checkoutCommand.getLocalModelPath(), remote_model_path, model_file);
		saveSession();
		
		return model_file;
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
		
		GetRequestedModelElementsRequest getRequestedModelElementsRequest = new GetRequestedModelElementsRequest(getSession(), local_model_path);
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
		
		UpdateSubModelRequest updateSubModelRequest = new UpdateSubModelRequest(getSession(), local_model_path, elementIds);
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
}
