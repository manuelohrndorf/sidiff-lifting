package org.sidiff.remote.application.adapters;

import java.io.File;

import org.sidiff.common.extension.IExtension;
import org.sidiff.remote.application.exception.RepositoryAdapterException;

/**
 * 
 * @author cpietsch
 *
 */
public interface IRepositoryAdapter extends IExtension {

	Description<IRepositoryAdapter> DESCRIPTION = Description.of(IRepositoryAdapter.class,
			"org.sidiff.remote.application.repository_adapter", "repository_adapter", "class");

	RepositoryAdapterManager MANAGER = new RepositoryAdapterManager();

	public CheckoutOperationResult checkout(String url, int port, String path, String username, char[] password, String target) throws RepositoryAdapterException;
	
	public CommitOperationResult commit(String url, int port, String path, File wcFile, String username, char[] password, String message) throws RepositoryAdapterException;
	
	public InfoOperationResult info(File wcFile) throws RepositoryAdapterException;
	
	public ListOperationResult list(String url, int port, String path, String username, char[] password) throws RepositoryAdapterException;
}
