package org.sidiff.mutation.selection;

import java.util.LinkedList;
import java.util.List;

public interface ISelection<T>{
	
	public void initializeCandidates(LinkedList<T> candidates);
	
	public void clearCandidates();
	
	public void clearResults();
	
	public T selectNextCandidate();
	
	public LinkedList<T> getRankedCandidates();
	
	public List<T> getSelectedCandidates();	
	
	public boolean selectionCoverageReached();

}
