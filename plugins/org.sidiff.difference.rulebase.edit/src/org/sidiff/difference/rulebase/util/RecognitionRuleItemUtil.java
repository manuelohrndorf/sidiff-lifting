package org.sidiff.difference.rulebase.util;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.editrule.analysis.annotations.EditRuleAnnotations;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.util.EditRuleItemUtil;

public class RecognitionRuleItemUtil {

	public static int getRefinementLevel(RuleBaseItem item) {
		return Integer.valueOf(getSematicChangeSetRefinementLevel(item).getValue());
	}

	public static int getNumberOfACs(RuleBaseItem item) {
		if (getSematicChangeSetNumberOfACs(item) == null) {
			// Compatibility
			return 0;
		}
		return Integer.valueOf(getSematicChangeSetNumberOfACs(item).getValue());
	}

	public static int getNumberOfParams(RuleBaseItem item) {
		if (getSematicChangeSetNumberOfParams(item) == null) {
			// Compatibility
			return 0;
		}
		return Integer.valueOf(getSematicChangeSetNumberOfParams(item).getValue());
	}

	public static int getPriority(RuleBaseItem item) {
		Integer priority = EditRuleAnnotations.getPriority(item.getEditRule().getExecuteModule());

		if (priority == null) {
			// Return Recognition-Rule (default) value:
			priority = Integer.valueOf(getSematicChangeSetPriority(item).getValue());
		}

		return priority;
	}

	public static void setPriority(RuleBaseItem item, int priority) {
		EditRuleAnnotations.setPriority(item.getEditRule().getExecuteModule(), priority);
	}

	public static EClass getRRType(RuleBaseItem item) {
		// Get unit type of recognition main unit
		return item.getEditRuleAttachment(RecognitionRule.class).getRecognitionMainUnit().eClass();
	}

	public static String getDisplayRRType(RuleBaseItem item) {
		// Get unit type of recognition main unit
		return EditRuleItemUtil.getUnitType(item.getEditRuleAttachment(RecognitionRule.class).getRecognitionMainUnit());
	}

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
			if (attribute.getType() == SymmetricPackage.eINSTANCE.getSemanticChangeSet_RefinementLevel()) {
				return attribute;
			}
		}

		return null;
	}

	private static Attribute getSematicChangeSetNumberOfACs(RuleBaseItem item) {

		for (Attribute attribute : getSematicChangeSet(item).getAttributes()) {
			if (attribute.getType() == SymmetricPackage.eINSTANCE.getSemanticChangeSet_NumberOfACs()) {
				return attribute;
			}
		}

		return null;
	}

	private static Attribute getSematicChangeSetNumberOfParams(RuleBaseItem item) {

		for (Attribute attribute : getSematicChangeSet(item).getAttributes()) {
			if (attribute.getType() == SymmetricPackage.eINSTANCE.getSemanticChangeSet_NumberOfParams()) {
				return attribute;
			}
		}

		return null;
	}

	private static Node getSematicChangeSet(RuleBaseItem item) {
		Module recognitionModule = item.getEditRuleAttachment(RecognitionRule.class).getRecognitionModule();
		if(recognitionModule.eIsProxy()) {
			throw new IllegalArgumentException("Recognition module is a proxy: " + EcoreUtil.getURI(recognitionModule));
		}

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
