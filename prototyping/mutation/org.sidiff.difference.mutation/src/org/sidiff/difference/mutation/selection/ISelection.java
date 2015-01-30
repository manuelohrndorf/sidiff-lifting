package org.sidiff.difference.mutation.selection;

import java.util.LinkedList;



public interface ISelection<T>{
	
	public void initializeCandidates(LinkedList<T> candidates);
	
	public void clearCandidates();
	
	public void clearResults();
	
	public T selectNextCandidate();
	
	public LinkedList<T> getRankedCandidates();
	
	public LinkedList<T> getSelectedCandidates();	
	
	public boolean selectionCoverageReached();

}
