package org.sidiff.editrule.rulebase.project.runtime.library;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import org.sidiff.editrule.rulebase.type.extension.IRuleBase;
import org.sidiff.editrule.rulebase.type.extension.RuleBaseTypeLibrary;

public class RuleBaseMetaUtil {

	/**
	 * The name field = Rulebase name
	 */
	public static final String FIELD_NAME = "name:";
	
	/**
	 * The type field = qualified java class name of the rulebase type (extends {@link IRuleBase})
	 */
	public static final String FIELD_TYPE = "type-id:";
	
	/**
	 * Reads a rulebase meta file.
	 * 
	 * @param metaFilePath
	 *            (IN) Meta-File path.
	 * @param name
	 *            (OUT) Rulebase name.
	 * @param types
	 *            (OUT) Rulebase typs.
	 */
	public static void read(String metaFilePath, StringBuilder name, Set<Class<? extends IRuleBase>> types) {
		BufferedReader bufferedReader = null;
		
		try {
			bufferedReader = new BufferedReader(new FileReader(metaFilePath));
		    StringBuilder stringBuilder = new StringBuilder();
		    String line = bufferedReader.readLine();

		    while (line != null) {
		        stringBuilder.append(line);
		        stringBuilder.append(System.lineSeparator());
		        line = bufferedReader.readLine();
		        line = line.trim();
		        
		        // Name:
		        if (line.toLowerCase().startsWith(FIELD_NAME)) {
		        	String name_value = line.substring(FIELD_NAME.length(), line.length());
		        	name = name.append(name_value.trim());
		        }
		        
		        // Type:
		        if (line.toLowerCase().startsWith(FIELD_TYPE)) {
		        	String type_value = line.substring(FIELD_TYPE.length(), line.length());
		        	Class<? extends IRuleBase> type = RuleBaseTypeLibrary.getRulebaseTypeIDs().get(type_value.trim());
		        	types.add(type);
		        }
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
