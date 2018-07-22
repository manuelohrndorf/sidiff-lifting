package org.sidiff.remote.application.adapter.svn;

import java.io.File;

import org.sidiff.remote.application.adapters.CheckoutOperationResult;
import org.sidiff.remote.application.adapters.IRepositoryAdapter;
import org.sidiff.remote.application.exception.RepositoryAdapterException;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.wc2.SvnCheckout;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnTarget;

/**
 * 
 * @author cpietsch
 *
 */
public class SVNRepositoryAdapter implements IRepositoryAdapter {


	@Override
	public CheckoutOperationResult checkout(String url, int port, String path, String username, char[] password, String target) throws RepositoryAdapterException {
		
		SVNURL svnURL;
		try {
			svnURL = SVNURL.parseURIEncoded(url + ":" + port + "/" + path);
		} catch (SVNException e) {
			throw new RepositoryAdapterException(e);
		}
		
		try {
	
		
		SVNRepository repository = SVNRepositoryFactory.create(svnURL);
		
		ISVNAuthenticationManager authManager =	SVNWCUtil.createDefaultAuthenticationManager(username, password);

		repository.setAuthenticationManager(authManager);
		
		SVNClientManager clientManager = SVNClientManager.newInstance(SVNWCUtil.createDefaultOptions(true));
		clientManager.setAuthenticationManager(authManager);
		
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
		SvnCheckout checkout = operationFactory.createCheckout();
		checkout.setSource(SvnTarget.fromURL(svnURL));
		String[] segments = svnURL.getPath().split("/");
		for(int i = 1; i < segments.length ; i++) {
			target += File.separator + segments[i];
		}
		checkout.setSingleTarget(SvnTarget.fromFile(new File(target)));
		checkout.run();
		
		return new CheckoutOperationResult(url, svnURL.getHost(), svnURL.getPort(), svnURL.getPath(), target, "Checkout file/folder successfully!", true);


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
