package org.sidiff.difference.symmetric.compareview;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.sidiff.difference.symmetric.Change;

public interface ISelectionAdapter {

	/**
	 * Empty iteration.
	 */
	public static Iterator<EObject> EMPTY_EOBJECT_ITERATOR = new Iterator<EObject>() {

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public EObject next() {
			throw new NoSuchElementException();
		}
	};
	
	/**
	 * Empty iteration.
	 */
	public static Iterator<Change> EMPTY_CHANGE_ITERATOR = new Iterator<Change>() {

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Change next() {
			throw new NoSuchElementException();
		}
	};
	
	Iterator<Change> getChanges(ISelection selection);
	
	Iterator<EObject> getHighlightableElements(ISelection selection);
}
