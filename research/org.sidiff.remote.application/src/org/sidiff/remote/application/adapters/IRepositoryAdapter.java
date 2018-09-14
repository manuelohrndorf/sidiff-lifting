package org.sidiff.remote.application.adapters;

import java.io.File;

import org.sidiff.remote.application.exception.RepositoryAdapterException;

/**
 * 
 * @author cpietsch
 *
 */
public interface IRepositoryAdapter {
	
	public static final String EXTENSION_POINT_ID = "org.sidiff.remote.application.repository_adapter";
	
	public static final String ATTRIBUTE_ID = "class";
	
	public CheckoutOperationResult checkout(String url, int port, String path, String username, char[] password, String target) throws RepositoryAdapterException;
	
	public InfoOperationResult info(File wcFile) throws RepositoryAdapterException;
	
	public ListOperationResult list(String url, int port, String path, String username, char[] password) throws RepositoryAdapterException;
	
	public ImportFileOperationResult importFile(String url, int port, String path, File file, String username, char[] password, String message) throws RepositoryAdapterException;
	
	public InitBranchResult initBranch(String url, int port, String id, String username, char[] password) throws RepositoryAdapterException;
	
	public String getKey();
	
	public String getName();

}
