package org.sidiff.profileapplicator.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.profileapplicator.ProfileApplicatorService;
import org.sidiff.profileapplicator.services.ProfileApplicator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProfileApplicatorServiceImpl implements ProfileApplicatorService {

	// Global {@see ProfileApplicator} element
	private static ProfileApplicator applicator = null;

	// Temporal variables for creation of {@see ProfileApplicator}

	private static String basePackage = null;
	private static String stereoPackage = null;
	private static List<URI> transformations = new ArrayList<URI>();
	private static List<String> configuredStereoTypes = new ArrayList<String>();
	private static List<String> stereoTypes = new ArrayList<String>();
	private static List<String> baseTypes = new ArrayList<String>();
	private static List<String> baseReferences = new ArrayList<String>();

	// Iterate through all subtypes of baseType?
	private boolean baseTypeInheritance = false;

	public ProfileApplicatorServiceImpl() {

	}

	/*
	 * Initialize the {@see ProfileApplicator} Read the XML configuration file
	 * and define the applicator accordingly
	 * 
	 * @see
	 * org.sidiff.profileapplicator.ProfileApplicatorService#init(java.lang.
	 * Class, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void init(Class<?> service, String pathToConfig,
			String pathToInputFolder, String pathToOutputFolder, int numberOfThreads) {

		if (service == ProfileApplicator.class) {

			// Interpreting the XML configuration file
			applicator = new ProfileApplicator();

			applicator.setConfigPath(pathToConfig);
			applicator.setInputFolderPath(pathToInputFolder);
			applicator.setOutputFolderPath(pathToOutputFolder);
			applicator.setNumberThreads(numberOfThreads);

			LogUtil.log(LogEvent.NOTICE, "Interpreting Configuration File...");

			Document doc = XMLParser.parseStream(IOUtil
					.getInputStream(applicator.getConfigPath()));
			Element docElem = doc.getDocumentElement();
			org.w3c.dom.Node currentNode = null;
			NodeList currentChildNodes = null;

			// retrieve and set configuration parameters			

			currentNode = doc.getElementsByTagName("Profile").item(0);
			applicator.setProfileName((String.valueOf(getAttributeValue("name",
					currentNode))));

			currentNode = doc.getElementsByTagName("BaseTypeInstances").item(0);
			applicator.setBaseTypeInstances((Boolean.valueOf(getAttributeValue(
					"allow", currentNode))));

			currentNode = doc.getElementsByTagName("BaseTypeContext").item(0);
			applicator.setBaseTypeContext((Boolean.valueOf(getAttributeValue(
					"allow", currentNode))));

			currentNode = doc.getElementsByTagName("BaseTypeInheritance").item(
					0);
			baseTypeInheritance = (Boolean.valueOf(getAttributeValue("allow",
					currentNode)));

			currentNode = doc.getElementsByTagName("BasePackage").item(0);
			basePackage = String
					.valueOf(getAttributeValue("nsUri", currentNode));
			applicator.setBasePackage(EPackage.Registry.INSTANCE
					.getEPackage(basePackage));

			currentNode = doc.getElementsByTagName("StereoPackage").item(0);
			stereoPackage = String.valueOf(getAttributeValue("nsUri",
					currentNode));
			applicator.setStereoPackage(EPackage.Registry.INSTANCE
					.getEPackage(stereoPackage));

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
			applicator.setTransformations(transformations);

			// Set all used StereoTypes
			NodeList stereoTypeNodes = doc.getElementsByTagName("StereoType");
			for (int i = 0; i <= stereoTypeNodes.getLength() - 1; i++) {
				Node stereoTypeNode = stereoTypeNodes.item(i);
				String configuredStereoType = String.valueOf(getAttributeValue(
						"name", stereoTypeNode));
				configuredStereoTypes.add(configuredStereoType);
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
					if (configuredStereoTypes.size() == 0
							|| (configuredStereoTypes.indexOf(stereoType
									.getName()) != -1 && stereoType
									.equals((EClass) getClassifier(
											applicator.getStereoPackage(),
											configuredStereoTypes.get(configuredStereoTypes
													.indexOf(stereoType
															.getName())))))) {
						// Get all baseReferences of stereoType
						List<EStructuralFeature> allBaseReferences = EMFMetaAccess
								.getEStructuralFeaturesByRegEx(stereoType,
										"^(base)_\\w+", true);
						for (EStructuralFeature baseReference : allBaseReferences) {

							// Create temporal variables

							String stereoTypeTemp = stereoType.getName();
							String baseReferenceTemp = ((EReference) baseReference)
									.getName();
							String baseTypeTemp = ((EClass) baseReference
									.getEType()).getName();

							// Add stereoType and its corresponding baseType and
							// baseReference without inheritance
							stereoTypes.add(stereoTypeTemp);
							baseReferences.add(baseReferenceTemp);
							baseTypes.add(baseTypeTemp);

							if (baseTypeInheritance) {
								// Adding all possible sub types of base type
								for (Iterator<EObject> it = applicator
										.getBasePackage().eAllContents(); it
										.hasNext();) {
									EObject obj = it.next();

									if (obj instanceof EClass) {

										EClass eSubClass = (EClass) obj;

										for (EClass eSuperClass : eSubClass
												.getEAllSuperTypes()) {

											if (eSuperClass.getName().equals(
													baseTypeTemp)) {

												stereoTypes.add(stereoTypeTemp);
												baseTypes.add(eSubClass
														.getName());
												baseReferences
														.add(baseReferenceTemp);

											}
										}
									}
								}
							}
						}
					}
				}
			}

			applicator.setStereoTypes(stereoTypes);
			applicator.setBaseTypes(baseTypes);
			applicator.setBaseReferences(baseReferences);

			LogUtil.log(LogEvent.NOTICE,
					"Interpreting completed, ProfileApplicator initialized!");
		} else
			LogUtil.log(LogEvent.ERROR, "ProfileApplicatorService not found!");
	}

	public void applyProfile(Class<?> service) {

		assert (service != null) : "Service not found";

		applicator.applyProfile();

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

	/*
	 * Get Attribute value of given attribute name in given node
	 * 
	 * @param attribName Name of the attribute
	 * 
	 * @param node Node where to look for attribute
	 * 
	 * @return value Attribute value
	 */
	public static String getAttributeValue(String attribName,
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
