package org.sidiff.remote.application.adapter.svn;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.team.svn.core.connector.SVNEntry;
import org.sidiff.remote.application.adapters.BrowseRepositoryContentOperationResult;
import org.sidiff.remote.application.adapters.CheckoutRepositoryContentOperationResult;
import org.sidiff.remote.application.adapters.IRepositoryAdapter;
import org.sidiff.remote.application.exception.RepositoryAdapterException;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.ProxyProperty;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * 
 * @author cpietsch
 *
 */
public class SVNRepositoryAdapter implements IRepositoryAdapter {

	private SVNRepository repository;

	private void setUpRepository(String url, int port, String path, String username, char[] password) throws RepositoryAdapterException {
		String domain = url.substring(0, url.lastIndexOf("/"));
		String repo_name = url.substring(url.lastIndexOf("/"));
		SVNURL svnURL;
		try {
			svnURL = SVNURL.parseURIEncoded(domain + ":" + port + repo_name + path);
			repository = SVNRepositoryFactory.create(svnURL);
		} catch (SVNException | ArrayIndexOutOfBoundsException e) {
			throw new RepositoryAdapterException(e);
		} 
		ISVNAuthenticationManager authManager =	SVNWCUtil.createDefaultAuthenticationManager(username, password);
		repository.setAuthenticationManager(authManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BrowseRepositoryContentOperationResult list(String url, int port, String path, String username, char[] password)
			throws RepositoryAdapterException {	
		
		setUpRepository(url, port, path, username, password);
		
		Collection<SVNDirEntry> entries;
		try {
			entries = repository.getDir(path, -1, null, new ArrayList<SVNEntry>());
		} catch (SVNException e) {
			throw new RepositoryAdapterException(e);
		}
		List<ProxyObject> proxyObjects = new ArrayList<ProxyObject>();
		Iterator<SVNDirEntry> iterator = entries.iterator();
		while (iterator.hasNext()) {
			SVNDirEntry entry = (SVNDirEntry) iterator.next();
			String kind = "";
			boolean container = true;
			if (entry.getKind().equals(SVNNodeKind.DIR)) {
				kind = "Folder";
			} else {
				kind = "File";
				container = false;
			}

			ProxyObject proxyObject = new ProxyObject(entry.getName(), path + "/" + entry.getName(), kind, new ArrayList<ProxyProperty>(), container);
			proxyObjects.add(proxyObject);
		}
		
		BrowseRepositoryContentOperationResult listOperationResult = new BrowseRepositoryContentOperationResult(url, port, path, proxyObjects, "Content of " + path, true);
		
		return listOperationResult;		
	}

	@Override
	public CheckoutRepositoryContentOperationResult checkout(String url, int port, String path, String username, char[] password, String target) throws RepositoryAdapterException {
		setUpRepository(url, port, path, username, password);
		SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true));
		SVNUpdateClient updateClient = clientManager.getUpdateClient( );
		try {
			target += repository.getLocation().getPath();
			updateClient.doCheckout(repository.getLocation() , new File(target) , SVNRevision.HEAD , SVNRevision.HEAD , SVNDepth.INFINITY , false);
		} catch (SVNException e) {
			throw new RepositoryAdapterException(e);
		}
		
		return new CheckoutRepositoryContentOperationResult(url, port, path, target, "Checkout file/folder successful!", true);		
	}
	
	@Override
	public String getKey() {
		return this.getClass().getName();
	}

	@Override
	public String getName() {
		return "SVN Repository Adapter";
	}
}
