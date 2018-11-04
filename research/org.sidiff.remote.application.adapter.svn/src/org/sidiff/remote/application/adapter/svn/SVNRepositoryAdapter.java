package org.sidiff.remote.application.adapter.svn;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.sidiff.remote.application.adapters.CheckoutOperationResult;
import org.sidiff.remote.application.adapters.CommitOperationResult;
import org.sidiff.remote.application.adapters.IRepositoryAdapter;
import org.sidiff.remote.application.adapters.InfoOperationResult;
import org.sidiff.remote.application.adapters.ListOperationResult;
import org.sidiff.remote.application.exception.RepositoryAdapterException;
import org.sidiff.remote.common.ProxyObject;
import org.sidiff.remote.common.ProxyProperty;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.admin.SVNEntry;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNCopyClient;
import org.tmatesoft.svn.core.wc.SVNCopySource;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/**
 * 
 * @author cpietsch
 *
 */
public class SVNRepositoryAdapter implements IRepositoryAdapter {

	private SVNRepository repository;

	private void setUpRepository(String url, int port, String path, String username, char[] password) throws RepositoryAdapterException {
		SVNURL svnURL = parseURL(url, port, path);
		try {
			repository = SVNRepositoryFactory.create(svnURL);
		} catch (SVNException e) {
			throw new RepositoryAdapterException(e);
		}
		
		ISVNAuthenticationManager authManager = null;
		if(username == null || password == null) {
			authManager = SVNWCUtil.createDefaultAuthenticationManager();
		}else {
			authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
		}
		repository.setAuthenticationManager(authManager);
	}
	
	private SVNURL parseURL(String url, int port, String path) throws RepositoryAdapterException {
		path = path.replaceAll("\\\\", "/");
		try {
			String domain = url.substring(0, url.lastIndexOf("/"));
			String repo_name = url.substring(url.lastIndexOf("/"));
			SVNURL svnURL = SVNURL.parseURIEncoded(domain + ":" + port + repo_name + path);
			return svnURL;
		} catch (SVNException e) {
			throw new RepositoryAdapterException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ListOperationResult list(String url, int port, String path, String username, char[] password)
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
		
		ListOperationResult listOperationResult = new ListOperationResult(url, port, path, proxyObjects, "Content of " + path);
		
		return listOperationResult;		
	}

	@Override
	public CheckoutOperationResult checkout(String url, int port, String path, String username, char[] password, String target) throws RepositoryAdapterException {
		setUpRepository(url, port, path, username, password);
		SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true));
		SVNCopyClient copyClient = clientManager.getCopyClient();
		SVNCopySource copySource = new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, repository.getLocation());
		try {
			SVNURL dstURL = parseURL(url, port, "").appendPath("branches", true).appendPath(username, true);
			String message = "<init> branch " + username + "/" + path;
			copyClient.doCopy(new SVNCopySource[] {copySource}, dstURL, false, false, false, message, new SVNProperties());
			SVNUpdateClient updateClient = clientManager.getUpdateClient( );
			
			target += dstURL.getPath();
			updateClient.doCheckout(repository.getLocation() , new File(target) , SVNRevision.HEAD , SVNRevision.HEAD , SVNDepth.INFINITY , false);
			return new CheckoutOperationResult(url, port, path, target);		

		} catch (SVNException e) {
			throw new RepositoryAdapterException(e);
		}		
	}
	
	public CommitOperationResult commit(String url, int port, String path, File wcFile, String username, char[] password, String message) throws RepositoryAdapterException {
		setUpRepository(url, port, path, username, password);
		SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true));
		SVNCommitClient commitClient = clientManager.getCommitClient();
		try {
				SVNWCClient wcClient = clientManager.getWCClient();
				wcClient.doAdd(wcFile, true, true, true, SVNDepth.INFINITY ,false, false, true);
				commitClient.doCommit(new File[] {wcFile}, false, message, true, true);
		}catch (SVNException e) {
			throw new RepositoryAdapterException(e);
		}
		
		return new CommitOperationResult(url, port);
		
	}
	
//	public ImportFileOperationResult importFile(String url, int port, String path, File file, String username, char[] password, String message) throws RepositoryAdapterException {
//		setUpRepository(url, port, path, username, password);
//		try {
//			SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true));
//			clientManager.getCommitClient().doImport(file, repository.getLocation(), "<import> " + message, null, false, true, SVNDepth.fromRecurse(true));
//			SVNUpdateClient updateClient = clientManager.getUpdateClient( );
//			updateClient.doCheckout(repository.getLocation() , file , SVNRevision.HEAD , SVNRevision.HEAD , SVNDepth.INFINITY , true);
//			return new ImportFileOperationResult(url, port, true);
//		} catch (SVNException e) {
//			throw new RepositoryAdapterException(e);
//		}
//	}
	
	@Override
	public InfoOperationResult info(File wcFile) throws RepositoryAdapterException {
		SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true));
		SVNWCClient wcClient = clientManager.getWCClient();
		try {
			SVNInfo info = wcClient.doInfo(wcFile, SVNRevision.WORKING);
			SVNURL svnURL = info.getURL();
			String repoName = svnURL.getPath().substring(1, svnURL.getPath().substring(1).indexOf("/")+1);
			String url = svnURL.getProtocol() + "://" + svnURL.getHost() + "/" + repoName;
			int port = svnURL.getPort();
			String path = svnURL.getPath();
			String revision = info.getRevision().getID()+"";
			String author = info.getAuthor();
			return new InfoOperationResult(url, port,  path, revision, author);
		} catch (SVNException e) {
			throw new RepositoryAdapterException(e);
		}
	}
	
//	public InitBranchResult initBranch(String url, int port, String id, String username, char[] password) throws RepositoryAdapterException {
//		setUpRepository(url, port, "/branches", username, password);
//		try {
//			SVNURL svnURL = repository.getLocation().appendPath(id, true);
//			String path = "/branches/"+id;
//			if(repository.checkPath(path, -1) == SVNNodeKind.NONE) {
//				SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true));
//			
//				clientManager.getCommitClient( ).doMkDir( new SVNURL[] { svnURL } , "<init> branch " + id );
//			}
//			return new InitBranchResult(url, port, path, true);
//		} catch (SVNException e) {
//			throw new RepositoryAdapterException(e);
//		}
//	}
	
	@Override
	public String getKey() {
		return this.getClass().getName();
	}

	@Override
	public String getName() {
		return "SVN Repository Adapter";
	}

}
