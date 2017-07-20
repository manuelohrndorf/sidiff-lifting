package org.sidiff.editrule.recorder.filter;

import org.eclipse.emf.ecore.EObject;

public interface IObjectFilter {

	public static final IObjectFilter DUMMY = new IObjectFilter() {

		@Override
		public boolean filter(EObject object) {
			return false;
		}
	};
	
	boolean filter(EObject object);
}
