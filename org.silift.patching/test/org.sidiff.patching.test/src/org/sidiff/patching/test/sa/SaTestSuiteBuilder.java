package org.sidiff.patching.test.sa;

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

public class SaTestSuiteBuilder extends AbstractSuiteBuilder {

	public SaTestSuiteBuilder(File modelFolder) {
		super(modelFolder);		
	}

	@Override
	protected String getFileExtension() {
		return "sa";
	}

	@Override
	protected IMatcher getMatcher(Resource original, Resource modified) {
		IMatcher matcher = MatcherUtil.getMatcherByKey("SaMatcher", original, modified);
		if (matcher == null) {
			LogUtil.log(LogEvent.ERROR, "SaMatcher not found!");
		}
		
		return matcher;
	}

	@Override
	protected AbstractBatchArgumentManager getArgumentManager(Difference patch) {
		 return new GenericCopyArgumentManager(patch);
	}

}
