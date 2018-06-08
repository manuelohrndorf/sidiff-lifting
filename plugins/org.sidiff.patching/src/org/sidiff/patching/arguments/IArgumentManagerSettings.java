package org.sidiff.patching.arguments;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.matcher.IMatcher;
import org.sidiff.patching.PatchMode;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

public interface IArgumentManagerSettings {

	Scope getScope();
	IMatcher getMatcher();
	IModifiedDetector getModifiedDetector();
	ISymbolicLinkHandler getSymbolicLinkHandler();
	int getMinReliability();
	PatchMode getPatchMode();
}
