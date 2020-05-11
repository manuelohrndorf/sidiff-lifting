package org.sidiff.editrule.rulebase.util;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isAmalgamationUnit;
import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isKernelRule;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.editrule.rulebase.Classification;
import org.sidiff.editrule.rulebase.RuleBaseItem;

public class EditRuleItemUtil {

	private static final Map<String, String> dict;

	static {
		dict = new HashMap<>();
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
		return dict.getOrDefault(input, input);
	}
	
	public static String formatName(RuleBaseItem item) {

		// Remove quotes:
		String displayName = getName(item);

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
			result.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1));

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

	public static String getName(RuleBaseItem item) {
		return item.getEditRule().getExecuteModule().getName();
	}

	public static void setName(RuleBaseItem item, String name) {
		if (!name.equals(getName(item))) {
			item.getEditRule().getExecuteModule().setName(name);
		}
	}

	public static String getDescription(RuleBaseItem item) {
		return item.getEditRule().getExecuteModule().getDescription();
	}

	public static void setDescription(RuleBaseItem item, String name) {
		if (!name.equals(getDescription(item))) {
			item.getEditRule().getExecuteModule().setDescription(name);
		}
	}

	public static String getClassificationName(RuleBaseItem item, String classificator) {
		for (Classification c : item.getEditRule().getClassification()) {
			if (c.getClassificator().equals(classificator)) {
				return c.getName();
			}
		}
		return "Not Classified";
	}

	public static void setClassificationName(RuleBaseItem item, String name, String classificator) {
		for (Classification c : item.getEditRule().getClassification()) {
			if (c.getClassificator().equals(classificator)) {
				c.setName(name);
				break;
			}
		}
	}

	public static String getInverseName(RuleBaseItem item) {
		if (item.getEditRule().getInverse() != null) {
			return item.getEditRule().getInverse().getExecuteModule().getName();
		}
		return "";
	}

	public static void setInverseName(RuleBaseItem item, String name) {
		if (item.getEditRule().getInverse() != null) {
			item.getEditRule().getInverse().getExecuteModule().setName(name);
		}
	}

	public static void invertActivity(RuleBaseItem item) {
		item.setActive(!item.isActive());
	}

	public static EClass getERType(RuleBaseItem item) {
		// Get unit type of execute main unit
		return item.getEditRule().getExecuteMainUnit().eClass();
	}

	public static String getDisplayERType(RuleBaseItem item) {
		// Get unit type of execute main unit
		return getUnitType(item.getEditRule().getExecuteMainUnit());
	}

	public static URI getEditRuleURI(RuleBaseItem item) {
		return EcoreUtil.getURI(item.getEditRule().getExecuteMainUnit());
	}

	/**
	 * Returns the unit type of the given unit.
	 * 
	 * @param unit
	 *            the transformation unit instance.
	 * @return the unit type.
	 */
	public static String getUnitType(Unit unit) {
		if (unit == null) {
			return "null";
		} else if (unit instanceof Rule) {
			if (isKernelRule((Rule)unit)) {
				return "Multi-Rule";
			}
			return "Rule";
		} else if (isAmalgamationUnit(unit)) {
			return "Amalgamation Unit";
		} else if (unit instanceof IndependentUnit) {
			return "Independent";
		} else if (unit instanceof SequentialUnit) {
			return "Sequential";
		} else if (unit instanceof LoopUnit) {
			return "Loop";
		} else if (unit instanceof IteratedUnit) {
			return "Iterated";
		} else if (unit instanceof ConditionalUnit) {
			return "Conditional";
		} else if (unit instanceof PriorityUnit) {
			return "Priority";
		}
		return null;
	}
}
