package org.sidiff.remote.application.adapter.svn;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.wc2.ISvnObjectReceiver;
import org.tmatesoft.svn.core.wc2.SvnCheckout;
import org.tmatesoft.svn.core.wc2.SvnList;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnTarget;

/**
 * 
 * @author cpietsch
 *
 */
public class SVNRepositoryAdapter implements IRepositoryAdapter {


	@Override
	public BrowseRepositoryContentOperationResult list(String url, int port, String path, String username, char[] password)
			throws RepositoryAdapterException {
		
		String domain = url.substring(0, url.lastIndexOf("/"));
		String repo_name = url.substring(url.lastIndexOf("/"));
		SVNURL svnURL;
		try {
			svnURL = SVNURL.parseURIEncoded(domain + ":" + port + repo_name + "/" + path);
		} catch (SVNException | ArrayIndexOutOfBoundsException e) {
			throw new RepositoryAdapterException(e);
		}
		
		try {
			
		// TODO use high-level api instead of directly creating the operations.
		SVNRepository repository = SVNRepositoryFactory.create(svnURL);
			
		ISVNAuthenticationManager authManager =	SVNWCUtil.createDefaultAuthenticationManager(username, password);

		repository.setAuthenticationManager(authManager);
		
		SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true));
		clientManager.setAuthenticationManager(authManager);
	
		
		SvnOperationFactory operationFactory = new SvnOperationFactory();
		operationFactory.setAuthenticationManager(authManager);
		SvnList svnList = operationFactory.createList();
		svnList.setDepth(SVNDepth.IMMEDIATES);
		
		svnList.setRevision(SVNRevision.HEAD);
		svnList.addTarget(SvnTarget.fromURL(svnURL));
		List<ProxyObject> proxyObjects = new ArrayList<ProxyObject>();
		svnList.setReceiver(new ISvnObjectReceiver<SVNDirEntry>() {
		    public void receive(SvnTarget target, SVNDirEntry object) throws SVNException {
		    	String kind = "";
		    	boolean container = true;
		    	if(object.getKind().equals(SVNNodeKind.DIR)) {
		    		kind = "Folder";
		    	}else {
		    		kind = "File";
		    		container = false;
		    	}
		    	String name = object.getName();
		    	if(name.isEmpty()) {
		    		name = svnURL.getPath().substring(svnURL.getPath().lastIndexOf("/")+1);
		    	}
		    	
		       	String	path = svnURL.getPath().replace(url, "");
		    	
		    	ProxyObject proxyObject = new ProxyObject(name, path, kind, new ArrayList<ProxyProperty>(), container);
		    	proxyObjects.add(proxyObject);
		    }
		});
		svnList.run();
		
		BrowseRepositoryContentOperationResult listOperationResult = new BrowseRepositoryContentOperationResult(url, svnURL.getHost(), svnURL.getPort(), svnURL.getPath(), proxyObjects, "test", true);
		
		return listOperationResult;
		
		} catch (SVNException e) {
			throw new RepositoryAdapterException(e);
		}
	}

	@Override
	public CheckoutRepositoryContentOperationResult checkout(String url, int port, String path, String username, char[] password, String target) throws RepositoryAdapterException {
		
		String domain = url.substring(0, url.lastIndexOf("/"));
		String repo_name = url.substring(url.lastIndexOf("/"));
		SVNURL svnURL;
		try {
			svnURL = SVNURL.parseURIEncoded(domain + ":" + port + repo_name + "/" + path);
		} catch (SVNException | ArrayIndexOutOfBoundsException e) {
			throw new RepositoryAdapterException(e);
		}
		
		try {
			
		ISVNAuthenticationManager authManager =	SVNWCUtil.createDefaultAuthenticationManager(username, password);
	
		//use SVNUpdateClient to do the checkout
		
//		SVNUpdateClient updateClient = clientManager.getUpdateClient( );
//		
//	
//		updateClient.setIgnoreExternals( false );
//
//		updateClient.doCheckout( repository.getLocation(), new File(target),
//
//		SVNRevision.create(repository.getLatestRevision()), SVNRevision.create(repository.getLatestRevision()),
//
//		true);
		
		SvnOperationFactory operationFactory = new SvnOperationFactory();
		operationFactory.setAuthenticationManager(authManager);
		SvnCheckout checkout = operationFactory.createCheckout();
		checkout.setSource(SvnTarget.fromURL(svnURL));
		String[] segments = svnURL.getPath().split("/");
		for(int i = 1; i < segments.length ; i++) {
			target += File.separator + segments[i];
		}
		checkout.setSingleTarget(SvnTarget.fromFile(new File(target)));
		checkout.run();
		
		return new CheckoutRepositoryContentOperationResult(url, svnURL.getHost(), svnURL.getPort(), svnURL.getPath(), target, "Checkout file/folder successfully!", true);


		} catch (SVNException e) {
			throw new RepositoryAdapterException(e);
		}
		
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
