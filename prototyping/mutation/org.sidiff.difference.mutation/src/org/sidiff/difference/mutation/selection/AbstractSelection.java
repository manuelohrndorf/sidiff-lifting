package org.sidiff.difference.mutation.selection;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;

/**
 * Abstract Selection class for selecting possible candidates
 * Subclasses have to implement when and how to select as well as update the candidates. 
 * This class is used for ranking as well as selection of candidates based on the ranking.
 * 
 * @author dreuling
 * @param <T>
 *
 */
public abstract class AbstractSelection<T> implements ISelection<T>, Comparator<T>{			

	/**
	 * Possible candidates, sorted by the subclass implementation of {@link AbstractSelection#compare(T, T)}.
	 */
	private LinkedList<T> rankedCandidates; 	
	
	/**
	 * Set of candidates which have been selected before.
	 */
	private LinkedList<T> selectedCandidates;	
	
	/**
	 * Number of possible candidates
	 */
	private int numberOfCandidates;
	
	/**
	 * Defines the percentage of possible candidates to use.
	 */
	private int selectionCoveragePercent;
	
	/**
	 * Defines whether candidates can be used more than once
	 */
	private boolean allowDuplicateCandidateSelection;
	
	
	
	/**
	 * Constructor with initialization
	 */
	public AbstractSelection(LinkedList<T> rankedCandidates, LinkedList<T> selectedCandidates, int selectionCoveragePercent, boolean
			allowDuplicateCandidateSelection) {
		super();
		if(rankedCandidates != null){
			this.rankedCandidates = rankedCandidates;
			this.numberOfCandidates = rankedCandidates.size();
		}
		else{
			this.rankedCandidates = new LinkedList<T>();
			this.numberOfCandidates = 0;
		}

		
		if(selectedCandidates != null)
			this.selectedCandidates = selectedCandidates;
		else
			this.selectedCandidates = new LinkedList<T>();
		
		this.selectionCoveragePercent = selectionCoveragePercent;
		this.allowDuplicateCandidateSelection = allowDuplicateCandidateSelection;
	}	
	
	
	/**
	 * Generic method for returning the next candidate to choose. The 
	 * candidates will be ordered before according to {@link AbstractSelection#rankCandidates()}.
	 * @return the next candidate to choose
	 */
	protected T getNextCandidate() {
		rankCandidates();
		return rankedCandidates.getFirst();
	}
	
	/**
	 * Convenient method for selecting the next candidate.
	 * This method implicitly:
	 * <ul>
	 * <li> Ranks all candidates according to the implemented Comparator
	 * <li> Updates the candidate according to the implemented method
	 * <li> Adds the candidate to the list of already used ones
	 * </ul>
	 * @return the candidate selected next
	 */
	public T selectNextCandidate(){
		LogUtil.log(LogEvent.DEBUG, "Selecting candidate [" + (selectedCandidates.size()+1) + "/" + getNumberToBeSelected() + "] from "
				+ getRankedCandidates().size() + " possible candidates...");
		T candidate = getNextCandidate();
		if(candidate == null){
			LogUtil.log(LogEvent.DEBUG, "No possible candidate found.");
			return null;			
		}
		if(allowDuplicateCandidateSelection)
			updateCandidate(candidate);
		else
			rankedCandidates.remove(candidate);
		
		selectedCandidates.add(candidate);
		LogUtil.log(LogEvent.DEBUG, "Selected candidate: " + candidate);
		return candidate;
	}
	
	public LinkedList<T> getRankedCandidates() {
		return rankedCandidates;
	}

	public LinkedList<T> getSelectedCandidates() {
		return selectedCandidates;
	}
	
	/**
	 * Method for initializing the candidates. This
	 * is run most of the time once.
	 * @param candidates
	 */
	public void initializeCandidates(LinkedList<T> candidates){
		this.rankedCandidates = candidates;
		this.numberOfCandidates = rankedCandidates.size();
	}

	/**
	 * Method for ranking all candidates.
	 * It really just sorts the candidates according to comparator
	 * implementation in this class.
	 * 
	 */
	private void rankCandidates(){				
		Collections.sort(rankedCandidates,this);
	}	
	
	/**
	 * Check whether enough candidate elements have been selected already
	 * @return true if the coverage has been reached
	 */
	public boolean selectionCoverageReached(){
		
		// No candidates available
		if(rankedCandidates.size() == 0){
			LogUtil.log(LogEvent.DEBUG, "No candidates left.");
			return true;
		}
		
		//Coverage reached
		if(selectedCandidates.size() >= getNumberToBeSelected()){
			LogUtil.log(LogEvent.DEBUG, "Selection Coverage Reached.");
			return true;
		}
		
		return false;
	}
	
	/**
	 * Number of selections to be made
	 * @return
	 */
	private int getNumberToBeSelected(){		
		return (int) (selectionCoveragePercent *(numberOfCandidates/100.0f));		
	}
	
	/**
	 * Method for updating the given candidate.
	 * @param candidate 
	 */
	protected abstract void updateCandidate(T candidate);
	
	@Override
	public abstract int compare(T o1, T o2);


	@Override
	public void clearCandidates() {
		this.rankedCandidates.clear();	
	}

	@Override
	public void clearResults() {
		this.selectedCandidates.clear();		
	}	
	
	


}
