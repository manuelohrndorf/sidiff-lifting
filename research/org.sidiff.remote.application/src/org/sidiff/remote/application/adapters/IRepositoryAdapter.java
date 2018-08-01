package org.sidiff.remote.application.adapters;

import org.sidiff.remote.application.exception.RepositoryAdapterException;

/**
 * 
 * @author cpietsch
 *
 */
public interface IRepositoryAdapter {
	
	public static final String EXTENSION_POINT_ID = "org.sidiff.remote.application.repository_adapter";
	
	public static final String ATTRIBUTE_ID = "class";
	
	public BrowseRepositoryContentOperationResult list(String url, int port, String path, String username, char[] password) throws RepositoryAdapterException;
	
	public CheckoutRepositoryContentOperationResult checkout(String url, int port, String path, String username, char[] password, String target) throws RepositoryAdapterException;
	
	public String getKey();
	
	public String getName();

}
