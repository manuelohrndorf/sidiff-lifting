package org.sidiff.difference.rulebase.wrapper;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isAmalgamationUnit;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isKernelRule;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.silift.common.util.emf.EMFStorage;
import org.silift.common.util.file.FileOperations;

public class RuleBaseItemWrapper {
	
	public static String getName(RuleBaseItem item) {
		String value = getSematicChangeSetName(item).getValue();
		value = value.substring(1, value.length() - 1);

		return value;
	}

	public static String formatName(RuleBaseItem item) {
		String internalName = getSematicChangeSetName(item).getValue();
		
		// Remove quotes:
		String displayName = internalName.substring(1, internalName.length() - 1);
		
		// Remove underscore
		displayName = displayName.replace('_', ' ');

		// Remove camel-case
		String regex = "([a-z])([A-Z]+)";
        String replacement = "$1 $2";
        displayName = displayName.replaceAll(regex, replacement);
		
        // Make first letters upper-case
        displayName = capitalizeFirstLetter(displayName);
        
        // Translate special words:
        displayName = translate(displayName);
        
        return displayName;
	}

	private static String capitalizeFirstLetter(String input) {
		StringBuilder result = new StringBuilder(input.length());
		String[] words = input.split("\\s");
		
		for (int i = 0, l = words.length; i < l; ++i) {
			if (i > 0) {
				result.append(" ");
			}
			result.append(Character.toUpperCase(words[i].charAt(0)))
					.append(words[i].substring(1));

		}
		return result.toString();
	}
	
	private static String translate(String input) {
		StringBuilder result = new StringBuilder(input.length());
		String[] words = input.split("\\s");
		
		for (int i = 0, l = words.length; i < l; ++i) {
			if (i > 0) {
				result.append(" ");
			}
			result.append(dictionary(words[i]));

		}
		return result.toString();
	}
	
	private static final Map<String, String> dict;
	static {
		dict = new HashMap<String, String>();
		dict.put("SET", "Set");
		dict.put("UNSET", "Unset");
		dict.put("ADD", "Add");
		dict.put("CREATE", "Create");
		dict.put("DELETE", "Delete");
		dict.put("MOVE", "Move");
		dict.put("REMOVE", "Remove");
		dict.put("CHANGE", "Change");
		dict.put("NOT", "Not");
		dict.put("REFERENCE", "Reference");
		dict.put("MOVEs", "Moves");
		dict.put("CHANGEs", "Changes");
		dict.put("FROM", "From");
		dict.put("TO", "To");
		dict.put("IN", "In");
		dict.put("ATTRIBUTE", "Attribute");
		dict.put("Id", "ID");
		dict.put("TGT", "Target");
		dict.put("SRC", "Source");
	}

	private static String dictionary(String input) {
		// Translate:
		String output = dict.get(input);
		
		if (output == null) {
			return input;
		} else {
			return output;
		}
	}

	public static void setName(RuleBaseItem item, String name) {
		if (!name.equals(getName(item))) {
			getSematicChangeSetName(item).setValue("\"" + name + "\"");
		}
	}

	public static String getDescription(RuleBaseItem item) {
		String value = getSemanticChangeSetDescription(item).getValue();
		value = value.substring(1, value.length() - 1);

		return value;
	}
	
	public static void setDescription(RuleBaseItem item, String name) {
		if (!name.equals(getDescription(item))) {
			getSemanticChangeSetDescription(item).setValue("\"" + name + "\"");
		}
	}

	
	public static int getRefinementLevel(RuleBaseItem item) {
		return Integer.valueOf(getSematicChangeSetRefinementLevel(item).getValue());
	}
	
	public static int getNumberOfACs(RuleBaseItem item) {
		if (getSematicChangeSetNumberOfACs(item) == null){
			// Compatibility
			return 0;
		} else {
			return Integer.valueOf(getSematicChangeSetNumberOfACs(item).getValue());
		}		
	}
	
	public static int getNumberOfParams(RuleBaseItem item) {
		if (getSematicChangeSetNumberOfParams(item) == null){
			// Compatibility
			return 0;
		} else {
			return Integer.valueOf(getSematicChangeSetNumberOfParams(item).getValue());
		}	
	}

	public static boolean isActiv(RuleBaseItem item) {
		return item.isActive();
	}

	public static void setActiv(RuleBaseItem item, boolean value) {
		item.setActive(value);
	}
	
	public static void setValid(RuleBaseItem item) {
		// Set valid flag:
		if (!validate_EditRule(item).getChildren().isEmpty()) {
			item.setValid(false);
		} else {
			item.setValid(true);
		}
	}
	
	public static boolean isValid(RuleBaseItem item, boolean value) {
		return item.isValid();
	}
	
	/**
	 * Validation of the edit-rule.
	 * 
	 * @return The edit-rule validation diagnostic.
	 */
	public static Diagnostic validate_EditRule(RuleBaseItem item) {
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(item.getEditRule());
		
		// Console report:
		if (!diagnostic.getChildren().isEmpty()) {
			// Console report:
			System.out.println("Validation of " + item.getEditRule().getExecuteModule().getName() + ":");
			
			for (Diagnostic childDiagnostic : diagnostic.getChildren()) {
				System.out.println("  " + childDiagnostic);
			}
		}
		
		return diagnostic;
	}

	public static void invertActivity(RuleBaseItem item) {
		if (item.isActive()) {
			item.setActive(false);
		} else {
			item.setActive(true);
		}
	}

	public static int getPriority(RuleBaseItem item) {
		return Integer.valueOf(getSematicChangeSetPriority(item).getValue());
	}

	public static void setPriority(RuleBaseItem item, int priority) {
		getSematicChangeSetPriority(item).setValue("" + priority);
	}

	public static EClass getERType(RuleBaseItem item) {
		// Get unit type of execute main unit
		return item.getEditRule().getExecuteMainUnit().eClass();
	}

	public static String getDisplayERType(RuleBaseItem item) {
		// Get unit type of execute main unit
		return getUnitType(item.getEditRule().getExecuteMainUnit());
	}

	public static EClass getRRType(RuleBaseItem item) {
		// Get unit type of recognition main unit
		return item.getRecognitionRule().getRecognitionMainUnit().eClass();
	}

	public static String getDisplayRRType(RuleBaseItem item) {
		// Get unit type of recognition main unit
		return getUnitType(item.getRecognitionRule().getRecognitionMainUnit());
	}

	public static String getVersion(RuleBaseItem item) {
		return item.getVersion();
	}

	public static void setVersion(RuleBaseItem item, int major, int minor, int revision) {
		String version = major + "." + minor + "." + revision;
		item.setVersion(version);
	}

	public static void increaseVersion(RuleBaseItem item, int position) {
		int[] version = convertStringToVersion(item.getVersion());
		version[position] = version[position] + 1;
		setVersion(item, version[0], version[1], version[2]);
	}

	public static void setEditRuleMD5Hash(EditRule editRule) {
		try {
			String path = EMFStorage.uriToPath(editRule.getExecuteModule().eResource().getURI());
			byte[] md5 = FileOperations.readMD5FileHash(path);
			editRule.setFileHashMD5(md5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkEditRuleMD5Hash(RuleBaseItem item) {
		try {
			String path = EMFStorage.uriToPath(item.getEditRule().getExecuteModule().eResource().getURI());
			byte[] md5File = FileOperations.readMD5FileHash(path);
			byte[] md5Stored = item.getEditRule().getFileHashMD5();
			
			if (md5Stored == null) {
				return false;
			}
			
			if (md5File.length == md5Stored.length) {
				for (int i = 0; i < md5Stored.length; i++) {
					if(md5Stored[i] != md5File[i]) {
						return false;
					}
				}
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static int[] convertStringToVersion(String version) 
			
			throws ArrayIndexOutOfBoundsException, NumberFormatException {
		
		// Returns {major, minor, revision}
		String[] stringPositions = version.split("\\.");
		int[] positions = new int[3];
		positions[0] = Integer.valueOf(stringPositions[0]);
		positions[1] = Integer.valueOf(stringPositions[1]);
		positions[2] = Integer.valueOf(stringPositions[2]);

		return positions;
	}

	public static URI getEditRuleURI(RuleBaseItem item) {
		return EMFStorage.getURI(item.getEditRule().getExecuteMainUnit());
	}

	public static URI getRecognitionRuleURI(RuleBaseItem item) {
		return EMFStorage.getURI(item.getRecognitionRule().getRecognitionMainUnit());
	}

	/**
	 * Returns the unit type of the given unit.
	 * 
	 * @param unit
	 *            the transformation unit instance.
	 * @return the unit type.
	 */
	private static String getUnitType(Unit unit) {
		
		if (unit instanceof Rule) {
			if (isKernelRule((Rule) unit)) {
				return "Amalgamation Rule";
			} else {
				return "Rule";
			}
		}

		else if (isAmalgamationUnit(unit)) {
			return "Amalgamation Unit";
		}
		
		else if (unit instanceof IndependentUnit) {
			return "Independent";
		}

		else if (unit instanceof SequentialUnit) {
			return "Sequential";
		}

		else if (unit instanceof LoopUnit) {
			return "Loop";
		}
		
		else if (unit instanceof IteratedUnit) {
			return "Iterated";
		}

		else if (unit instanceof ConditionalUnit) {
			return "Conditional";
		}

		else if (unit instanceof PriorityUnit) {
			return "Priority";
		}
		return null;
	}

	private static Attribute getSematicChangeSetName(RuleBaseItem item) {

		for (Attribute attribute : getSematicChangeSet(item).getAttributes()) {
			if (attribute.getType() == SymmetricPackage.eINSTANCE.getSemanticChangeSet_Name()) {
				return attribute;
			}
		}

		return null;
	}
	
	private static Attribute getSemanticChangeSetDescription(RuleBaseItem item) {

		for (Attribute attribute : getSematicChangeSet(item).getAttributes()) {
			if (attribute.getType() == SymmetricPackage.eINSTANCE
					.getSemanticChangeSet_Description()) {
				return attribute;
			}
		}

		return null;
	}

	/**
	 * Returns the Henshin semantic change set (SCS) priority attribute.
	 * 
	 * @param recognitionRule
	 *            the rulebase recognition rule.
	 * @return the Henshin SCS priority attribute.
	 */
	private static Attribute getSematicChangeSetPriority(RuleBaseItem item) {

		for (Attribute attribute : getSematicChangeSet(item).getAttributes()) {
			if (attribute.getType() == SymmetricPackage.eINSTANCE.getSemanticChangeSet_Priority()) {
				return attribute;
			}
		}

		return null;
	}

	private static Attribute getSematicChangeSetRefinementLevel(RuleBaseItem item) {

		for (Attribute attribute : getSematicChangeSet(item).getAttributes()) {
			if (attribute.getType() == SymmetricPackage.eINSTANCE
					.getSemanticChangeSet_RefinementLevel()) {
				return attribute;
			}
		}

		return null;
	}
	
	private static Attribute getSematicChangeSetNumberOfACs(RuleBaseItem item) {

		for (Attribute attribute : getSematicChangeSet(item).getAttributes()) {
			if (attribute.getType() == SymmetricPackage.eINSTANCE
					.getSemanticChangeSet_NumberOfACs()) {
				return attribute;
			}
		}

		return null;
	}
	
	private static Attribute getSematicChangeSetNumberOfParams(RuleBaseItem item) {

		for (Attribute attribute : getSematicChangeSet(item).getAttributes()) {
			if (attribute.getType() == SymmetricPackage.eINSTANCE
					.getSemanticChangeSet_NumberOfParams()) {
				return attribute;
			}
		}

		return null;
	}
	
	private static Node getSematicChangeSet(RuleBaseItem item) {
		Module recognitionModule = item.getRecognitionRule().getRecognitionModule();

		for (Rule rule : HenshinModuleAnalysis.getAllRules(recognitionModule)) {
			for (Node node : rule.getRhs().getNodes()) {
				if (node.getType() == SymmetricPackage.eINSTANCE.getSemanticChangeSet()) {
					return node;
				}
			}
		}

		return null;
	}
}
