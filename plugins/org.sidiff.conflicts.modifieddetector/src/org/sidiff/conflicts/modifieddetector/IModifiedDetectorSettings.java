package org.sidiff.conflicts.modifieddetector;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.matcher.IMatcher;

public interface IModifiedDetectorSettings {

	IMatcher getMatcher();
	Scope getScope();
}
