package org.sidiff.profileapplicator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.runtime.IProgressMonitor;
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
import org.sidiff.common.xml.XMLResolver;
import org.sidiff.profileapplicator.core.ProfileApplicatorThread;
import org.sidiff.profileapplicator.core.StereoType;
import org.sidiff.profileapplicator.settings.ProfileApplicatorSettings;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ProfileApplicator {

	// Settings
	private ProfileApplicatorSettings settings = null;

	// Progress and execution
	private IProgressMonitor progressMonitor = null;
	private final Lock lock;
	private final Condition condition;

	// Profile configuration
	// TODO DR 08.05.14: Implement as new ProfileApplicatorConfig object
	// instead of parameters

	private String profileName = null;
	private boolean baseTypeInstances = false;
	private EPackage basePackage;
	private EPackage stereoPackage;
	private List<URI> transformations = new ArrayList<URI>();
	private List<StereoType> stereoTypes = new ArrayList<StereoType>();
	private static List<String> configuredStereoTypeNames = new ArrayList<String>();

	// Iterate through all subtypes of baseType?
	private boolean baseTypeInheritance = false;

	public ProfileApplicator(ProfileApplicatorSettings settings,
			IProgressMonitor progressMonitor) throws Exception {

		this.settings = settings;
		this.progressMonitor = progressMonitor;
		this.lock = new ReentrantLock();
		this.condition = this.lock.newCondition();

		ResourceUtil.registerClassLoader(this.getClass().getClassLoader());
		XMLResolver.getInstance().includeMapping(
				IOUtil.getInputStream("ProfileApplicatorConfig.dtdmap.xml"));

		init(settings);
	}

	/*
	 * Apply the profile to given input edit rules Configuration has already
	 * taken place in {@see ProfileApplicatorServiceImpl}
	 */
	public boolean applyProfile() throws Exception {

		final int cleaningWorkUnits = 5;
		final int copyConfigWorkUnits = 1;
		int applicationWorkUnits = 0;
		for (StereoType st1 : stereoTypes) {
			applicationWorkUnits += st1.getBaseTypeMap().size();
		}
		final int workUnits = cleaningWorkUnits + copyConfigWorkUnits + 2
				* applicationWorkUnits;
		if (progressMonitor != null) {
			progressMonitor.beginTask(
					"Executing profile application for profile "
							+ this.profileName, workUnits);
		}
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

		LogUtil.log(LogEvent.NOTICE, "Using " + settings.getNumberOfThreads()
				+ " threads for computation.");

		LogUtil.log(
				LogEvent.NOTICE,
				"Applying transformations now, this could (and most certainly will) take some time...");

		if (settings.isDeleteGeneratedTransformations()) {
			LogUtil.log(LogEvent.NOTICE,
					"Deleting all Files in Output Folder...");
			if (progressMonitor != null) {
				progressMonitor.subTask("Deleting all Files in Output Folder");
			}
			// TODO Implement deleting of all henshin files recursively in
			// subfolders. Can not use FileUtils.cleanDirectory() as config et
			// al shall not be deleted.
		}
		if (progressMonitor != null) {
			progressMonitor.worked(cleaningWorkUnits);
		}

		LogUtil.log(LogEvent.NOTICE, "Copying Config over to OutputFolder...");
		if (progressMonitor != null) {
			progressMonitor.subTask("Copying Config over to OutputFolder");
		}

		File oldConfigFile = new File(this.settings.getConfigPath());
		File newConfigFile = new File(this.settings.getOutputFolderPath()
				+ File.separator
				+ this.settings.getConfigPath().substring(
						this.settings.getConfigPath().lastIndexOf(
								File.separator),
						this.settings.getConfigPath().length()));

		try {

			if (this.settings.isOverwriteConfigInTargetFolder()) {
				Files.copy(oldConfigFile.toPath(), newConfigFile.toPath(),
						StandardCopyOption.COPY_ATTRIBUTES,
						StandardCopyOption.REPLACE_EXISTING);
			} else {
				Files.copy(oldConfigFile.toPath(), newConfigFile.toPath(),
						StandardCopyOption.COPY_ATTRIBUTES);

			}
		} catch (IOException e) {
			throw new Exception("Error copying the the configuration", e);
		}
		if (progressMonitor != null) {
			progressMonitor.worked(copyConfigWorkUnits);
		}

		LogUtil.log(LogEvent.NOTICE,
				"Applying StereoTypes for the first time...");
		if (progressMonitor != null) {
			progressMonitor.subTask("Applying StereoTypes (Round 1)");
		}

		// Execute first time
		if (!applyStereoTypes()) {
			LogUtil.log(LogEvent.NOTICE, "Applying stereotypes did not finish");
			if (progressMonitor != null)
				progressMonitor.done();
			return false;
		}

		// Get resulted files from first run as new input files
		settings.setInputFolderPath(settings.getOutputFolderPath());

		LogUtil.log(LogEvent.NOTICE,
				"Applying StereoTypes for the second time...");
		if (progressMonitor != null) {
			progressMonitor.subTask("Applying StereoTypes (Round 2)");
		}

		// Execute second time -> apply all possible stereotype combinations
		if (!applyStereoTypes()) {
			LogUtil.log(LogEvent.NOTICE, "Applying stereotypes did not finish");
			if (progressMonitor != null)
				progressMonitor.done();
			return false;
		}

		LogUtil.log(LogEvent.NOTICE, "Applying profile " + profileName
				+ " completed!");
		if (progressMonitor != null)
			progressMonitor.done();
		return true;
	}

	private static Set<File> scanDirectoryForHenshinFiles(File inputFolder,
			boolean includeSubfolders) {
		Set<File> henshinFiles = new HashSet<File>();
		for (File f : inputFolder.listFiles()) {
			if (f.isFile()) {
				if (f.getName().endsWith(".henshin"))
					henshinFiles.add(f);
			} else if (includeSubfolders && f.isDirectory()) {
				henshinFiles.addAll(scanDirectoryForHenshinFiles(f,
						includeSubfolders));
			}
		}
		return henshinFiles;
	}

	/**
	 * Real working method, applying all stereoTypes to inputFiles. This is done
	 * twice, for applying all stereoType combinations possible. In the first
	 * run the input files are unmodified, in the second run the input files are
	 * the modified ones of the first round
	 * 
	 * 
	 * @param inputFiles
	 *            Set of files to be used as input files
	 * @throws Exception
	 */
	private boolean applyStereoTypes() throws Exception {

		LogUtil.log(LogEvent.DEBUG,
				"Scanning input folder for .henshin files...");
		// Get all input henshin files
		File sourceFolder = new File(this.settings.getInputFolderPath());
		if (!sourceFolder.isDirectory())
			throw new IOException("Folder "
					+ this.settings.getInputFolderPath()
					+ " does not exist or is not a folder");
		Set<File> henshinFiles = scanDirectoryForHenshinFiles(sourceFolder,
				this.settings.isUseSubfolders());
		LogUtil.log(LogEvent.NOTICE, "Found " + henshinFiles.size()
				+ " .henshin files");

		LogUtil.log(LogEvent.DEBUG, "Creating thread pool...");
		// Create thread pool
		ExecutorService executor = Executors.newFixedThreadPool(this.settings
				.getNumberOfThreads());
		//TODO afterExecute vom Executor nutzen um nach einem Task zu prüfen, ob dieser Erfolgreich war
		Map<ProfileApplicatorThread, Future<?>> threadList = new HashMap<ProfileApplicatorThread, Future<?>>();
		LogUtil.log(LogEvent.DEBUG, "Creating profiling threads...");

		// Create pool of source files
		for (File henshinFile : henshinFiles) {
			// Add all files to workpool
			ProfileApplicatorThread task = new ProfileApplicatorThread(henshinFile, this);
			// Do all the hard work in {@link ProfileApplicatorThread}
			//executor.execute(profileThread);
			threadList.put(task, executor.submit(task));
		}

		// This will make the executor accept no new threads
		// and finish all existing threads in the queue
		executor.shutdown();

		LogUtil.log(LogEvent.DEBUG,
				"Waiting for profiling threads to finish...");
		// Wait until all threads are finished
		boolean finished = false;
		while (true) {
			try {
				lock.lock();
				condition.await(2, TimeUnit.SECONDS);
				/* Update progress */
				if (progressMonitor != null) {
					for (ProfileApplicatorThread paThread : threadList.keySet()) {
						int worked = paThread.getProgessDelta();
						progressMonitor.worked(worked);
					}
				}
				/* Check if user canceled the profile application */
				if (progressMonitor != null && progressMonitor.isCanceled()) {
					/* Cancel all future tasks */
					for (Future<?>future : threadList.values()) {
						future.cancel(false);
					}
					/* Cancel all ProfileApplicator tasks */
					for (ProfileApplicatorThread paThread : threadList.keySet()){
						paThread.cancel();
					}
					//Continue with loop, until all running tasks have finished
				}
				/* Check if profile application application is done */
				if (executor.isTerminated()) {
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		/* Check if no error occured and all tasks are finished */
		finished=true;
		for (ProfileApplicatorThread paThread : threadList.keySet()){
			if (paThread.getOccuredException() != null){
				throw new Exception("Exception in worker for ", paThread.getOccuredException());
			} else if (!paThread.isFinished()){
				finished=false;
				break;
			}
		}
		return finished;
	}

	public ProfileApplicatorSettings getSettings() {
		return settings;
	}

	public void setSettings(ProfileApplicatorSettings settings) {
		this.settings = settings;
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
	 * Read the XML configuration file and define the applicator accordingly
	 * 
	 * @throws Exception
	 * 
	 */
	private void init(ProfileApplicatorSettings settings) throws Exception {

		// Interpreting the XML configuration file
		LogUtil.log(LogEvent.NOTICE, "Interpreting Configuration File...");

		DocumentBuilderFactory dbF = DocumentBuilderFactory.newInstance();
		dbF.setValidating(true);
		DocumentBuilder db = dbF.newDocumentBuilder();
		db.setEntityResolver(new EntityResolver() {
			@Override
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				if (systemId
						.equals("http://pi.informatik.uni-siegen.de/SiDiff/ProfileApplicatorConfig.dtd")) {
					return new InputSource(IOUtil.getInputStream("config.dtd"));
				} else {
					return null;
				}
			}
		});
		final List<SAXParseException> parseErrors = new ArrayList<SAXParseException>();
		db.setErrorHandler(new ErrorHandler() {

			@Override
			public void warning(SAXParseException arg0) throws SAXException {
				System.out.println("Configuration warning: " + arg0.toString());
			}

			@Override
			public void fatalError(SAXParseException arg0) throws SAXException {
				System.out.println("Configuration fatal error: "
						+ arg0.toString());
				parseErrors.add(arg0);
			}

			@Override
			public void error(SAXParseException arg0) throws SAXException {
				System.out.println("Configuration error: " + arg0.toString());
				parseErrors.add(arg0);
			}
		});
		Document doc = db
				.parse(IOUtil.getInputStream(settings.getConfigPath()));
		if (parseErrors.size() > 0) {
			String msg = "";
			for (SAXParseException e : parseErrors) {
				msg += "\r\n" + e.getLineNumber() + "." + e.getColumnNumber()
						+ ": " + e.getMessage();
			}
			throw new Exception("Error(s) in config file:" + msg);
		}

		org.w3c.dom.Node currentNode = null;
		// retrieve and set configuration parameters

		currentNode = doc.getElementsByTagName("Profile").item(0);
		profileName = String.valueOf(getAttributeValue("name", currentNode));

		currentNode = doc.getElementsByTagName("BaseTypeInstances").item(0);
		baseTypeInstances = Boolean.valueOf(getAttributeValue("allow",
				currentNode));

		currentNode = doc.getElementsByTagName("BaseTypeInheritance").item(0);
		baseTypeInheritance = (Boolean.valueOf(getAttributeValue("allow",
				currentNode)));

		currentNode = doc.getElementsByTagName("BasePackage").item(0);
		String basePackageAsString = String.valueOf(getAttributeValue("nsUri",
				currentNode));
		basePackage = EPackage.Registry.INSTANCE
				.getEPackage(basePackageAsString);

		currentNode = doc.getElementsByTagName("StereoPackage").item(0);
		String stereoPackageAsString = String.valueOf(getAttributeValue(
				"nsUri", currentNode));
		stereoPackage = EPackage.Registry.INSTANCE
				.getEPackage(stereoPackageAsString);

		// Set all used Higher Order Transformations
		NodeList transformationNodes = doc
				.getElementsByTagName("Transformation");
		for (int i = 0; i <= transformationNodes.getLength() - 1; i++) {
			Node transformationNode = transformationNodes.item(i);
			String transformation = String.valueOf(getAttributeValue("name",
					transformationNode));
			Boolean apply = Boolean.valueOf(getAttributeValue("apply",
					transformationNode));

			if (apply) {
				URI transformationURI = URI.createPlatformPluginURI(
						"org.sidiff.profileapplicator/hots/" + transformation
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
						EClass baseTypeTemp = (EClass) baseReference.getEType();

						// Add stereoType and its corresponding baseType and
						// baseReference without inheritance
						HashMap<EClass, EReference> baseTypeMapTemp = new HashMap<EClass, EReference>();
						baseTypeMapTemp.put(baseTypeTemp, baseReferenceTemp);
						stereoTypeTemp.setBaseTypeMap(baseTypeMapTemp);

						if (baseTypeInheritance) {
							// Adding all possible sub types of base type
							for (Iterator<EObject> it = basePackage
									.eAllContents(); it.hasNext();) {
								EObject obj = it.next();

								if (obj instanceof EClass) {

									EClass eSubClass = (EClass) obj;

									for (EClass eSuperClass : eSubClass
											.getEAllSuperTypes()) {

										if (eSuperClass.equals(baseTypeTemp)) {

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
