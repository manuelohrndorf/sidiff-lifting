package org.sidiff.patching.test.ft;

import java.io.File;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.matcher.util.MatcherUtil;
import org.sidiff.patching.test.AbstractBatchArgumentManager;
import org.sidiff.patching.test.AbstractSuiteBuilder;
import org.sidiff.patching.test.GenericCopyArgumentManager;

public class FtTestSuiteBuilder extends AbstractSuiteBuilder {

	public FtTestSuiteBuilder(File modelFolder) {
		super(modelFolder);		
	}

	@Override
	protected String getFileExtension() {
		return "faulttree";
	}

	@Override
	protected IMatcher getMatcher(Resource original, Resource modified) {
		IMatcher matcher = MatcherUtil.getMatcherByKey("FtMatcher", original, modified);
		if (matcher == null) {
			LogUtil.log(LogEvent.ERROR, "FtMatcher not found!");
		}
		
		return matcher;
	}

	@Override
	protected AbstractBatchArgumentManager getArgumentManager(Difference patch) {
		 return new GenericCopyArgumentManager(patch);
	}

}
