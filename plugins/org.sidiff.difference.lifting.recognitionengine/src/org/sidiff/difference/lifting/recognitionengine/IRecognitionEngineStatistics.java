package org.sidiff.difference.lifting.recognitionengine;

/**
 * Can be used to test the performance of the recognition engine.
 */
public interface IRecognitionEngineStatistics {

	/**
	 * Checks if the Recognition-Engine statistic output is enabled.
	 * 
	 * @return <code>true</code> if the Recognition-Engine statistic output is
	 *         enabled; <code>false</code> otherwise.
	 */
	public boolean isEnabled();
	
	/**
	 * Starts the Recognition-Engine statistic output.
	 * 
	 * @param CSVpath
	 *            The path to write the CSV-Output-File.
	 */
	public void enable(String CSVpath);
	
	/**
	 * Stops and resets the Recognition-Engine statistic output.
	 */
	public void disable();
	
	/**
	 * Resets all measured statistics.
	 */
	public void reset();
}
