package org.sidiff.evaluation.silift.patching.ft;

import java.io.File;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.evaluation.silift.patching.AbstractBatchArgumentManager;
import org.sidiff.evaluation.silift.patching.AbstractSuiteBuilder;
import org.sidiff.evaluation.silift.patching.GenericCopyArgumentManager;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.MatcherUtil;

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
		IMatcher matcher = MatcherUtil.getMatcher("FtMatcher");
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
