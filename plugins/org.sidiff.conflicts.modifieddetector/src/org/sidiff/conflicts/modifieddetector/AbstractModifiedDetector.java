package org.sidiff.conflicts.modifieddetector;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.matcher.IMatcher;

public abstract class AbstractModifiedDetector implements IModifiedDetector {

	@Override
	public String getKey() {
		return getClass().getName();
	}

	@Override
	public void init(Resource modelA, Resource modelB, IMatcher matcher, Scope scope) throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isModified(EObject targetObject) {
		// TODO Auto-generated method stub
		return false;
	}
}
