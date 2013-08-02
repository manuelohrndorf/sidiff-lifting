package org.sidiff.profileapplicator.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.profileapplicator.impl.ProfileApplicatorThread;

public class ProfileApplicator {

	// Folder for input edit rules
	private String inputFolderPath = null;

	// Path for config file
	private String configPath = null;

	// Folder for output edit rules
	private String outputFolderPath = null;

	/**
	 * @return the numberThreads
	 */
	public int getNumberThreads() {
		return numberThreads;
	}

	/**
	 * @param numberThreads the numberThreads to set
	 */
	public void setNumberThreads(int numberThreads) {
		this.numberThreads = numberThreads;
	}

	// Configuration parameters
	private boolean baseTypeInstances = false;
	private boolean baseTypeContext = false;

	// Profile configuration
	private String profileName = null;
	private EPackage basePackage;
	private EPackage stereoPackage;
	private List<URI> transformations = new ArrayList<URI>();
	private List<String> stereoTypes = new ArrayList<String>();
	private List<String> baseTypes = new ArrayList<String>();
	private List<String> baseReferences = new ArrayList<String>();

	// Number of concurrent threads applying the profile
	private int numberThreads = 4;

	/*
	 * Apply the profile to given input edit rules Configuration has already
	 * taken place in {@see ProfileApplicatorServiceImpl}
	 */
	public void applyProfile() {

		LogUtil.log(LogEvent.NOTICE,
				"Executing profile application for profile " + this.profileName
						+ "...");

		// For debugging purposes
		// Print used stereotypes without duplicates
		HashSet<String> stereoTypesUnique = new HashSet<String>();
		stereoTypesUnique.addAll(this.stereoTypes);
		LogUtil.log(LogEvent.NOTICE, "Using following stereotypes: "
				+ stereoTypesUnique);

		LogUtil.log(
				LogEvent.NOTICE,
				"Using following higher order transformations: "
						+ this.transformations
								.toString()
								.replace(
										"platform:/plugin/org.sidiff.profileapplicator/hots/",
										""));

		if (this.baseTypeInstances)
			LogUtil.log(
					LogEvent.NOTICE,
					"BaseTypeInstances allowed, source edit rules will also be copied untransformed.");

		if (this.baseTypeContext)
			LogUtil.log(LogEvent.NOTICE,
					"BaseTypeContext allowed, instances of baseType allowed as sufficient context");

		LogUtil.log(
				LogEvent.NOTICE,
				"Applying transformations now, this could (and most certainly will) take some time...");

		// Get all input henshin files
		File sourceFolder = new File(this.inputFolderPath);
		ArrayList<File> sourceFiles = new ArrayList<File>(
				Arrays.asList(sourceFolder.listFiles()));

		// Check if input is really a henshin file
		// and create corresponding SET
		Set<File> henshinFiles = new HashSet<File>();
		for (File sourceFile : sourceFiles) {

			if (sourceFile.getName().endsWith(".henshin"))
				henshinFiles.add(sourceFile);
		}

		LogUtil.log(LogEvent.DEBUG, "Creating thread pool...");

		// Create thread pool
		ExecutorService executor = Executors.newFixedThreadPool(numberThreads);

		LogUtil.log(LogEvent.DEBUG, "Creating profiling threads...");

		// Create pool of source files
		for (File henshinFile : henshinFiles) {
			try {
				// Add all files to workpool
				Runnable profileThread = new ProfileApplicatorThread(
						henshinFile, this);
				executor.execute(profileThread);
			} catch (Exception e) {
				// Nothing to be done here
				// Exceptions possible here
				// are about cross reference adapters
			}
		}

		// This will make the executor accept no new threads
		// and finish all existing threads in the queue
		executor.shutdown();

		LogUtil.log(LogEvent.DEBUG,
				"Waiting for profiling threads to finish...");
		// Wait until all threads are finished
		try {
			executor.awaitTermination(12, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LogUtil.log(LogEvent.NOTICE,
				"Applying profile " + this.getProfileName() + " completed!");

	}

	/**
	 * @return the stereoTypes
	 */
	public List<String> getStereoTypes() {
		return stereoTypes;
	}

	/**
	 * @param stereoTypes
	 *            the stereoTypes to set
	 */
	public void setStereoTypes(List<String> stereoTypes) {
		this.stereoTypes = stereoTypes;
	}

	/**
	 * @return the baseTypes
	 */
	public List<String> getBaseTypes() {
		return baseTypes;
	}

	/**
	 * @param baseTypes
	 *            the baseTypes to set
	 */
	public void setBaseTypes(List<String> baseTypes) {
		this.baseTypes = baseTypes;
	}

	/**
	 * @return the baseReferences
	 */
	public List<String> getBaseReferences() {
		return baseReferences;
	}

	/**
	 * @param baseReferences
	 *            the baseReferences to set
	 */
	public void setBaseReferences(List<String> baseReferences) {
		this.baseReferences = baseReferences;
	}

	/**
	 * @param basePackage
	 *            the basePackage to set
	 */
	public void setBasePackage(EPackage basePackage) {
		this.basePackage = basePackage;
	}

	/**
	 * @param stereoPackage
	 *            the stereoPackage to set
	 */
	public void setStereoPackage(EPackage stereoPackage) {
		this.stereoPackage = stereoPackage;
	}

	/**
	 * @return the basePackage
	 */
	public EPackage getBasePackage() {
		return basePackage;
	}

	/**
	 * @return the stereoPackage
	 */
	public EPackage getStereoPackage() {
		return stereoPackage;
	}

	/**
	 * @return the inputFolderPath
	 */
	public String getInputFolderPath() {
		return inputFolderPath;
	}

	/**
	 * @param inputFolderPath
	 *            the inputFolderPath to set
	 */
	public void setInputFolderPath(String inputFolderPath) {
		this.inputFolderPath = inputFolderPath;
	}

	/**
	 * @return the inputConfigPath
	 */
	public String getConfigPath() {
		return configPath;
	}

	/**
	 * @param inputConfigPath
	 *            the inputConfigPath to set
	 */
	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

	/**
	 * @return the ouputFolderPath
	 */
	public String getOutputFolderPath() {
		return outputFolderPath;
	}

	/**
	 * @param ouputFolderPath
	 *            the ouputFolderPath to set
	 */
	public void setOutputFolderPath(String ouputFolderPath) {
		this.outputFolderPath = ouputFolderPath;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName
	 *            the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the baseTypeInstances
	 */
	public boolean isBaseTypeInstances() {
		return baseTypeInstances;
	}

	/**
	 * @param baseTypeInstances
	 *            the baseTypeInstances to set
	 */
	public void setBaseTypeInstances(boolean baseTypeInstances) {
		this.baseTypeInstances = baseTypeInstances;
	}

	/**
	 * @return the baseTypeContext
	 */
	public boolean isBaseTypeContext() {
		return baseTypeContext;
	}

	/**
	 * @param baseTypeContext
	 *            the baseTypeContext to set
	 */
	public void setBaseTypeContext(boolean baseTypeContext) {
		this.baseTypeContext = baseTypeContext;
	}

	/**
	 * @return the transformations
	 */
	public List<URI> getTransformations() {
		return transformations;
	}

	/**
	 * @param transformations
	 *            the transformations to set
	 */
	public void setTransformations(List<URI> transformations) {
		this.transformations = transformations;
	}

}
