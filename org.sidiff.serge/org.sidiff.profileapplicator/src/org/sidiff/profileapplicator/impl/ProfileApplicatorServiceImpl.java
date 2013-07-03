package org.sidiff.profileapplicator.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.sidiff.common.io.IOUtil;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.profileapplicator.ProfileApplicatorService;
import org.sidiff.profileapplicator.services.ProfileApplicator;
import org.sidiff.profileapplicator.util.Common;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProfileApplicatorServiceImpl implements ProfileApplicatorService {

	private static ProfileApplicator applicator = null;

	private static List<URI> transformations = new ArrayList<URI>();

	private static List<String> stereoTypes = new ArrayList<String>();
	private static List<String> baseTypes = new ArrayList<String>();
	private static List<String> baseReferences = new ArrayList<String>();

	public ProfileApplicatorServiceImpl() {

	}

	public void init(Class<?> service, String pathToConfig,
			String pathToInputFolder, String pathToOutputFolder) {

		if (service == ProfileApplicator.class) {

			applicator = new ProfileApplicator();

			applicator.setConfigPath(pathToConfig);
			applicator.setInputFolderPath(pathToInputFolder);
			applicator.setOutputFolderPath(pathToOutputFolder);

			LogUtil.log(LogEvent.NOTICE, "Interpreting Configuration File...");

			Document doc = XMLParser.parseStream(IOUtil
					.getInputStream(applicator.getConfigPath()));
			Element docElem = doc.getDocumentElement();
			org.w3c.dom.Node currentNode = null;
			NodeList currentChildNodes = null;

			// retrieve and set configuration parameters

			currentNode = doc.getElementsByTagName("DebugOutput").item(0);
			applicator.setDebugOutput((Boolean.valueOf(Common
					.getAttributeValue("on", currentNode))));

			currentNode = doc.getElementsByTagName("Profile").item(0);
			applicator.setProfileName((String.valueOf(Common.getAttributeValue(
					"name", currentNode))));

			currentNode = doc.getElementsByTagName("BaseTypeInstances").item(0);
			applicator.setBaseTypeInstances((Boolean.valueOf(Common
					.getAttributeValue("allow", currentNode))));

			currentNode = doc.getElementsByTagName("BaseTypeContext").item(0);
			applicator.setBaseTypeContext((Boolean.valueOf(Common
					.getAttributeValue("allow", currentNode))));

			currentNode = doc.getElementsByTagName("BasePackage").item(0);
			applicator.setBasePackage(EPackage.Registry.INSTANCE
					.getEPackage(String.valueOf(Common.getAttributeValue(
							"nsUri", currentNode))));

			currentNode = doc.getElementsByTagName("StereoPackage").item(0);
			applicator.setStereoPackage(EPackage.Registry.INSTANCE
					.getEPackage(String.valueOf(Common.getAttributeValue(
							"nsUri", currentNode))));

			NodeList transformationNodes = doc
					.getElementsByTagName("Transformation");
			for (int i = 0; i <= transformationNodes.getLength() - 1; i++) {
				Node transformationNode = transformationNodes.item(i);
				String transformation = String.valueOf(Common
						.getAttributeValue("name", transformationNode));
				Boolean apply = Boolean.valueOf(Common.getAttributeValue(
						"apply", transformationNode));

				if (apply) {
					URI transformationURI = URI.createPlatformPluginURI(
							"org.sidiff.profileapplicator/hots/"
									+ transformation
									+ "_STEREOTYPE_IN_EDITRULE.henshin", false);

					transformations.add(transformationURI);

				}
			}

			applicator.setTransformations(transformations);

			NodeList stereoTypeNodes = doc.getElementsByTagName("StereoType");
			for (int i = 0; i <= stereoTypeNodes.getLength() - 1; i++) {
				Node stereoTypeNode = stereoTypeNodes.item(i);
				String stereoType = String.valueOf(Common.getAttributeValue(
						"name", stereoTypeNode));
				String baseType = String.valueOf(Common.getAttributeValue(
						"baseType", stereoTypeNode));
				String baseReference = String.valueOf(Common.getAttributeValue(
						"baseReference", stereoTypeNode));
				if (!stereoTypes.contains(stereoType)) {

					// Adding non inherited type
					stereoTypes.add(stereoType);
					baseTypes.add(baseType);
					baseReferences.add(baseReference);

					// Adding all possible sub types of base type
					for (Iterator<EObject> it = applicator.getBasePackage()
							.eAllContents(); it.hasNext();) {
						EObject obj = it.next();

						if (obj instanceof EClass) {

							EClass eSubClass = (EClass) obj;

							for (EClass eSuperClass : eSubClass
									.getEAllSuperTypes()) {

								if (eSuperClass.getName().equals(baseType)) {

									stereoTypes.add(stereoType);
									baseTypes.add(eSubClass.getName());
									baseReferences.add(baseReference);

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

}
