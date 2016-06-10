package org.sidiff.editrule.rulebase.builder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

public class EditRuleBaseClassBuilder {

	private String classTemplate;
	
	/**
	 * @param jPackage
	 *            The rulebase package.
	 * @param name
	 *            The name of the rulebase.
	 * @param documentType
	 *            All supported document types.
	 * @param attachmentTypes
	 *            All supported attachment types.
	 * @return The code of the rulebase class file.
	 */
	public String writeProjectClass(
			String jPackage, String name,
			Set<String> documentType, Set<String> attachmentTypes) {

		return String.format(getClassTemplate(), jPackage, name, 
				getAddToSetCode(documentType), getAddToSetCode(attachmentTypes));
	}
	
	/**
	 * @param projectClass
	 *            The code of the rulebase class file.
	 * @param path
	 *            The save path.
	 */
	public void saveProjectClass(String projectClass, File path) {
		Writer out = null;
		
		try {
			if (!path.exists()) {
				path.getParentFile().mkdirs();
				path.createNewFile();
			}
			
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if (out != null) {
			try {
				out.write(projectClass);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private String getClassTemplate() {
		
		if (classTemplate == null) {
			InputStream input = getClass().getResourceAsStream("/templates/RuleBaseProject.template");
			
			BufferedReader in = null;
			
			try {
				in = new BufferedReader(new InputStreamReader(input));
				
				StringBuilder sb = new StringBuilder();
			    String line = in.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = in.readLine();
			    }
			    
			    classTemplate = sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return classTemplate;
	}
	
	private String getAddToSetCode(Set<String> strings) {
		StringBuilder addToSet = new StringBuilder();
		
		for (String string : strings) {
			addToSet.append("types.add(\"");
			addToSet.append(string);
			addToSet.append("\");\n");
		}
		
		return addToSet.toString();
	}
}
