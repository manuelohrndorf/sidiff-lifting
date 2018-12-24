package org.sidiff.conflicts.modifieddetector;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.extension.AbstractTypedExtension;
import org.sidiff.matcher.IMatcher;

public abstract class AbstractModifiedDetector extends AbstractTypedExtension implements IModifiedDetector {

	@Override
	public void init(Resource modelA, Resource modelB, IMatcher matcher, Scope scope) throws IOException {
	}
}
