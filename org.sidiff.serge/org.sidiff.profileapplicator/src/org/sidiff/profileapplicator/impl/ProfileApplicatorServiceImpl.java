package org.sidiff.profileapplicator.impl;

import java.util.ArrayList;
import java.util.List;

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
	private static List<EPackage> basePackages = new ArrayList<EPackage>();
	private static List<EPackage> stereoPackages = new ArrayList<EPackage>();

	private static List<String> stereoTypes = new ArrayList<String>();
	private static List<String> baseTypes = new ArrayList<String>();
	private static List<String> baseReferences = new ArrayList<String>();

	public ProfileApplicatorServiceImpl() {

	}

	public void init(Class<?> service, String package_loc, String pathToConfig,
			String pathToInputFolder, String pathToOutputFolder) {

		if (service == ProfileApplicator.class) {

			applicator = new ProfileApplicator();

			applicator.setHotsPath(package_loc + "hots/");
			applicator.setConfigPath(pathToConfig);
			applicator.setInputFolderPath(pathToInputFolder);
			applicator.setOutputFolderPath(pathToOutputFolder);
			
		
			LogUtil.log(LogEvent.NOTICE, applicator.getConfigPath());

			LogUtil.log(LogEvent.NOTICE, "Interpreting Configuration File...");

			Document doc = XMLParser.parseStream(IOUtil
					.getInputStream(applicator.getConfigPath()));
			Element docElem = doc.getDocumentElement();
			org.w3c.dom.Node currentNode = null;
			NodeList currentChildNodes = null;

			// retrieve and set configuration parameters

			currentNode = doc.getElementsByTagName("MetaInstances").item(0);
			applicator.setmetaInstances((Boolean.valueOf(Common
					.getAttributeValue("allow", currentNode))));
		
			LogUtil.log(LogEvent.NOTICE, "Adding BasePackages...");


			NodeList basePackageNodes = doc.getElementsByTagName("BasePackage");
			for (int i = 0; i <= basePackageNodes.getLength() - 1; i++) {
				Node basePackageNode = basePackageNodes.item(i);
				String uri = String.valueOf(Common.getAttributeValue("nsUri",
						basePackageNode));
				EPackage basePackage = EPackage.Registry.INSTANCE
						.getEPackage(uri);
				if (!basePackages.contains(basePackage)) {
					basePackages.add(basePackage);	
					LogUtil.log(LogEvent.NOTICE, "Package: " + basePackage);

				}
			}
			applicator.setBasePackages(basePackages);

			LogUtil.log(LogEvent.NOTICE, "Adding StereoPackages...");

			NodeList stereoPackageNodes = doc
					.getElementsByTagName("StereoPackage");
			for (int i = 0; i <= stereoPackageNodes.getLength() - 1; i++) {
				Node stereoPackageNode = stereoPackageNodes.item(i);
				String uri = String.valueOf(Common.getAttributeValue("nsUri",
						stereoPackageNode));
				EPackage stereoPackage = EPackage.Registry.INSTANCE
						.getEPackage(uri);
				if (!stereoPackages.contains(stereoPackage)) {
					stereoPackages.add(stereoPackage);
				}
			}
			applicator.setStereoPackages(stereoPackages);

			LogUtil.log(LogEvent.NOTICE, "Adding stereoType rule parameters...");

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
					stereoTypes.add(stereoType);
					baseTypes.add(baseType);
					baseReferences.add(baseReference);
				}
			}
			applicator.setStereoTypes(stereoTypes);
			applicator.setBaseTypes(baseTypes);
			applicator.setBaseReferences(baseReferences);

			LogUtil.log(LogEvent.NOTICE, "Initialization completed!");
		}// TODO else exception
	}

	public void applyProfile(Class<?> service) {

		assert (service != null) : "Service not found";

		LogUtil.log(LogEvent.NOTICE, "Applying profile to basemodel edit rules...");
		applicator.applyProfile();
		LogUtil.log(LogEvent.NOTICE, "Applying profile completed!");

	}

}
