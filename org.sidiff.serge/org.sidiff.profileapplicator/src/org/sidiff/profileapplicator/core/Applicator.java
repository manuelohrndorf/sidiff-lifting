package org.sidiff.profileapplicator.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.io.ResourceUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.common.xml.XMLResolver;
import org.sidiff.profileapplicator.settings.ProfileApplicatorSettings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Applicator {

	//TODO DR 05.05.14: Work "directly" on @see{ProfileApplicatorSettings}
	//instead of parsing into new parameters here
	
	// Folder for input edit rules
	private String inputFolderPath = null;

	// Path for config file
	private String configPath = null;

	// Folder for output edit rules
	private String outputFolderPath = null;

	// Configuration parameter
	private boolean baseTypeInstances = false;

	// Profile configuration
	private String profileName = null;
	private EPackage basePackage;
	private EPackage stereoPackage;
	private List<URI> transformations = new ArrayList<URI>();
	private List<StereoType> stereoTypes = new ArrayList<StereoType>();
	private static List<String> configuredStereoTypeNames = new ArrayList<String>();

	// Iterate through all subtypes of baseType?
	private boolean baseTypeInheritance = false;
	
	
	// Number of concurrent threads applying the profile
	// Defaults to 1
	private int numberThreads = 1;

	public Applicator(ProfileApplicatorSettings settings) {
		
		ResourceUtil.registerClassLoader(this.getClass().getClassLoader());
		XMLResolver.getInstance().includeMapping(IOUtil.getInputStream("ProfileApplicatorConfig.dtdmap.xml"));
		
		init(settings);
	}
	
	
	/*
	 * Apply the profile to given input edit rules Configuration has already
	 * taken place in {@see ProfileApplicatorServiceImpl}
	 */
	public void applyProfile() {

		LogUtil.log(LogEvent.NOTICE,
				"Executing profile application for profile " + this.profileName
						+ "...");

		// Get all used stereoTypes
		String stereoTypesUsed = "";
		for (StereoType st : this.stereoTypes) {

			stereoTypesUsed += " " + st.getName();

		}
		// Print used stereotypes
		LogUtil.log(LogEvent.NOTICE, "Using following stereotypes: "
				+ stereoTypesUsed);

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

		LogUtil.log(LogEvent.NOTICE, "Using " + numberThreads
				+ " threads for computation.");

		LogUtil.log(
				LogEvent.NOTICE,
				"Applying transformations now, this could (and most certainly will) take some time...");

		LogUtil.log(
				LogEvent.NOTICE,
				"Applying StereoTypes for the first time...");
		
		// Execute first time
		applyStereoTypes();

		// Get resulted files from first run as new input files
		this.inputFolderPath = this.outputFolderPath;
		
		LogUtil.log(
				LogEvent.NOTICE,
				"Applying StereoTypes for the second time...");
		
		// Execute second time -> apply all possible stereotype combinations
		applyStereoTypes();

		LogUtil.log(LogEvent.NOTICE,
				"Applying profile " + profileName + " completed!");

	}

	/**
	 * Real working method, applying all stereoTypes to inputFiles.
	 * This is done twice, for applying all stereoType combinations possible.
	 * In the first run the input files are unmodified, in the second run the input files are
	 * the modified ones of the first round
	 * 
	 * 
	 * @param inputFiles
	 *            Set of files to be used as input files
	 */
	private void applyStereoTypes() {

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
			// Add all files to workpool
			Runnable profileThread = new ProfileApplicatorThread(henshinFile,
					this);

			// Do all the hard work in {@link ProfileApplicatorThread}
			executor.execute(profileThread);

		}

		// This will make the executor accept no new threads
		// and finish all existing threads in the queue
		executor.shutdown();

		LogUtil.log(LogEvent.DEBUG,
				"Waiting for profiling threads to finish...");
		// Wait until all threads are finished
		try {
			executor.awaitTermination(6, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			LogUtil.log(LogEvent.NOTICE,
					"Applying profile timed out (six hours)!");
		}
	}

	/**
	 * @return the stereoTypes
	 */
	public List<StereoType> getStereoTypes() {
		return stereoTypes;
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
	 * @return the ouputFolderPath
	 */
	public String getOutputFolderPath() {
		return outputFolderPath;
	}

	/**
	 * @return the baseTypeInstances
	 */
	public boolean isBaseTypeInstances() {
		return baseTypeInstances;
	}


	/**
	 * @return the transformations
	 */
	public List<URI> getTransformations() {
		return transformations;
	}

	
	/**
	 * Read the XML configuration file
	 * and define the applicator accordingly
	 * 
	 */
	private void init(ProfileApplicatorSettings settings) {

			// Interpreting the XML configuration file
			//TODO DR 05.05.14: Needs to implement ALL settings, not just the legacy ones
			
			configPath = settings.getConfigPath();
			inputFolderPath = settings.getInputFolderPath();
			outputFolderPath = settings.getOutputFolderPath();
			numberThreads = settings.getNumberOfThreads();

			LogUtil.log(LogEvent.NOTICE, "Interpreting Configuration File...");

			Document doc = XMLParser.parseStream(IOUtil
					.getInputStream(configPath));
			Element docElem = doc.getDocumentElement();
			org.w3c.dom.Node currentNode = null;
			NodeList currentChildNodes = null;

			// retrieve and set configuration parameters

			currentNode = doc.getElementsByTagName("Profile").item(0);
			profileName = String.valueOf(getAttributeValue("name",
					currentNode));

			currentNode = doc.getElementsByTagName("BaseTypeInstances").item(0);
			baseTypeInstances = Boolean.valueOf(getAttributeValue(
					"allow", currentNode));

			currentNode = doc.getElementsByTagName("BaseTypeInheritance").item(
					0);
			baseTypeInheritance = (Boolean.valueOf(getAttributeValue("allow",
					currentNode)));

			currentNode = doc.getElementsByTagName("BasePackage").item(0);
			String basePackageAsString = String
					.valueOf(getAttributeValue("nsUri", currentNode));
			basePackage = EPackage.Registry.INSTANCE
					.getEPackage(basePackageAsString);

			currentNode = doc.getElementsByTagName("StereoPackage").item(0);
			String stereoPackageAsString = String.valueOf(getAttributeValue("nsUri",
					currentNode));
			stereoPackage = EPackage.Registry.INSTANCE
					.getEPackage(stereoPackageAsString);

			// Set all used Higher Order Transformations
			NodeList transformationNodes = doc
					.getElementsByTagName("Transformation");
			for (int i = 0; i <= transformationNodes.getLength() - 1; i++) {
				Node transformationNode = transformationNodes.item(i);
				String transformation = String.valueOf(getAttributeValue(
						"name", transformationNode));
				Boolean apply = Boolean.valueOf(getAttributeValue("apply",
						transformationNode));

				if (apply) {
					URI transformationURI = URI.createPlatformPluginURI(
							"org.sidiff.profileapplicator/hots/"
									+ transformation
									+ "_STEREOTYPE_IN_EDITRULE.henshin", false);

					transformations.add(transformationURI);

				}
			}

			// Set all used StereoTypes
			NodeList stereoTypeNodes = doc.getElementsByTagName("StereoType");
			for (int i = 0; i <= stereoTypeNodes.getLength() - 1; i++) {
				Node stereoTypeNode = stereoTypeNodes.item(i);
				String configuredStereoTypesName = String
						.valueOf(getAttributeValue("name", stereoTypeNode));
				configuredStereoTypeNames.add(configuredStereoTypesName);
			}

			// Get all stereoTypes of current profile
			EList<EClassifier> allStereoTypes = EMFMetaAccess
					.getAllMetaClassesForPackage(stereoPackage);

			// Iterate over all stereoTypes
			for (EClassifier classifier : allStereoTypes) {

				if (classifier instanceof EClass) {
					// Get stereoType Class
					EClass stereoType = (EClass) classifier;					

					// Test if stereotype is contained in configuration
					// or no stereotype is configured at all, then all will be
					// used
					boolean stereoTypeContained = false;
					for (String stereoTypeName : configuredStereoTypeNames) {

						if (stereoType.getName().equals(stereoTypeName)) {
							stereoTypeContained = true;
							break;
						}

					}

					if (configuredStereoTypeNames.size() == 0
							|| stereoTypeContained) {
						// Get all baseReferences of stereoType
						List<EStructuralFeature> allBaseReferences = EMFMetaAccess
								.getEStructuralFeaturesByRegEx(stereoType,
										"^(base)_\\w+", true);
						for (EStructuralFeature baseReference : allBaseReferences) {

							// Create temporal variables
							StereoType stereoTypeTemp = new StereoType(stereoType);
							EReference baseReferenceTemp = (EReference) baseReference;
							EClass baseTypeTemp =(EClass) baseReference.getEType();

							// Add stereoType and its corresponding baseType and
							// baseReference without inheritance
							HashMap<EClass, EReference> baseTypeMapTemp = new HashMap<EClass, EReference>();
							baseTypeMapTemp
									.put(baseTypeTemp, baseReferenceTemp);
							stereoTypeTemp.setBaseTypeMap(baseTypeMapTemp);
							
							if (baseTypeInheritance) {
								// Adding all possible sub types of base type
								for (Iterator<EObject> it = basePackage.eAllContents(); it
										.hasNext();) {
									EObject obj = it.next();

									if (obj instanceof EClass) {

										EClass eSubClass = (EClass) obj;

										for (EClass eSuperClass : eSubClass
												.getEAllSuperTypes()) {

											if (eSuperClass.equals(
													baseTypeTemp)) {

												stereoTypeTemp.addBaseType(
														eSubClass,
														baseReferenceTemp);

											}
										}
									}
								}
							}
							stereoTypes.add(stereoTypeTemp);
						}

					}

				}

			}

			LogUtil.log(LogEvent.NOTICE,
					"Interpreting completed, ProfileApplicator initialized!");
	}


	private EClassifier getClassifier(EPackage epackage, String name) {

		// Call {@link EMFMetaAccess}
		EClassifier result = EMFMetaAccess.getMetaObjectByName(
				epackage.getNsURI(), name);

		// If not already contained in package
		if (result == null) {

			// Iterate through all subpackes and call {@link getClassifier}
			// recursively
			for (EPackage subpackage : epackage.getESubpackages()) {

				result = getClassifier(subpackage, name);

				// Return if type found
				if (result != null)
					break;
			}

		}
		return result;

	}

	/**
	 * Get Attribute value of given attribute name in given node
	 * 
	 * @param attribName
	 *            Name of the attribute
	 * 
	 * @param node
	 *            Node where to look for attribute
	 * 
	 * @return value Attribute value
	 */
	private static String getAttributeValue(String attribName,
			org.w3c.dom.Node node) {

		NamedNodeMap attribs = node.getAttributes();
		for (int i = 0; i < attribs.getLength(); i++) {
			if (attribs.item(i).getNodeName().equals(attribName)) {
				return attribs.item(i).getNodeValue();
			}
		}

		return null;
	}

}
