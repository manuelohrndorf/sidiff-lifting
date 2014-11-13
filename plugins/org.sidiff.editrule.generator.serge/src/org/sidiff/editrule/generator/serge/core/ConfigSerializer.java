package org.sidiff.editrule.generator.serge.core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.sidiff.common.io.IOUtil;
import org.sidiff.common.xml.XMLParser;
import org.sidiff.common.xml.XMLWriter;
import org.sidiff.editrule.generator.serge.settings.SergeSettings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ConfigSerializer {

	private SergeSettings settings = null;
	
	public ConfigSerializer(SergeSettings settings) {
		
		this.settings = settings;
		
	}
	
	public void serialize() throws IOException {
		
		// find out appropriate timestamp if there already exists a config in
		// the output folder.
		String timestamp = new java.text.SimpleDateFormat("YYYYMMdd").format(new Date());
		Path sourceConfig = Paths.get(settings.getConfigPath());
		Path targetConfig = Paths.get(
				settings.getOutputFolderPath()
				+ System.getProperty("file.separator")
				+ "usedConfig"
				+ "_"+ timestamp + ".serge");
		
		if(Files.exists(targetConfig) && !settings.isOverwriteConfigInTargetFolder()) {
			timestamp = new java.text.SimpleDateFormat("YYYYMMdd_hhmmss").format(new Date());
			targetConfig = Paths.get(settings.getOutputFolderPath()
					+ System.getProperty("file.separator")
					+ "usedConfig"
					+ "_"+ timestamp + ".serge");

		}
		
		// If default config creation was used build a serge config xml file
		// based on the defaultconfig template and the given meta-model nsURIs.
		if(settings.isUseDefaultConfig()) {			
			Document doc = XMLParser.parseStream(IOUtil.getInputStream(sourceConfig.toString()));	
			Element docElem = doc.getDocumentElement();
			doc.getElementsByTagName("MainModel").item(0).setTextContent(settings.getMetaModelNsUri());
			String content = doc.toString();			
			Files.write(targetConfig, content.getBytes());
		}
		
		// Otherwise just copy the used refined config.
		else{
			Files.copy(sourceConfig, targetConfig, StandardCopyOption.REPLACE_EXISTING);
		}
	}
}
