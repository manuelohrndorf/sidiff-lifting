package org.sidiff.editrule.generator.serge.generators.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.common.emf.metamodel.analysis.EClassifierInfo;
import org.sidiff.common.emf.metamodel.analysis.EClassifierInfoManagement;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.view.NodePair;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;
import org.sidiff.editrule.generator.serge.core.ModuleInternalsApplicator;
import org.sidiff.editrule.generator.types.OperationType;

public class AttachGenerator {

	private static Configuration c = Configuration.getInstance();
	private static EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();

	private final EClassifier eClassifier;

	public AttachGenerator(EClassifier eClassifier) {
		super();
		this.eClassifier = eClassifier;
	}

	public Set<Module> generate() throws OperationTypeNotImplementedException {
		Set<Module> modules = new HashSet<>();
		EClassifierInfo eInf = ECM.getEClassifierInfo(eClassifier);
		if (!eInf.isStereotype())
			return modules;
		HashMap<EClassifier, Set<EReference>> combinations = getCombinations((EClass) eClassifier);
		for (Map.Entry<EClassifier, Set<EReference>> c : combinations.entrySet()) {
			for (EReference s : c.getValue()) {
				boolean useSimpleName = AttachGenerator.c.use_simple_names_for_attach_and_detach_operations && (c.getValue().size() == 1);
				LogUtil.log(LogEvent.NOTICE, "Generating ATTACH: " + GlobalConstants.ATTACH_prefix
						+ eClassifier.getName() + (!useSimpleName ? "(" + s.getName() + ")" : ""));
				modules.add(generate((EClass) eClassifier, c.getKey(), s, useSimpleName));
			}
		}

		return modules;
	}

	private static Module generate(EClass stereotype, EClassifier metaclass, EReference baseRef, boolean useSimpleName)
			throws OperationTypeNotImplementedException {

		// Create Module
		Module module = HenshinFactory.eINSTANCE.createModule();
		String name = GlobalConstants.ATTACH_prefix + stereotype.getName()
				+ (!useSimpleName ? "(" + baseRef.getName() + ")" : "");
		module.setDescription("Attaches a " + stereotype.getName());
		module.setName(name);

		// Add imports for meta model
		module.getImports().addAll(c.EPACKAGESSTACK);

		// Add new rule to Module
		Rule rule = HenshinRuleAnalysisUtilEx.createRule("attach_" + stereotype.getName(),
				"attaches a " + stereotype.getName(), true, module);

		// Add new eClass to RHS
		String newName = ModuleInternalsApplicator.getFreeNodeName(GlobalConstants.NEW, rule);
		Node stereotypeNode = HenshinRuleAnalysisUtilEx.createCreateNode(rule.getRhs(), newName, stereotype);

		// Add necessary attributes to the new eClass node
		ModuleInternalsApplicator.createAttributes(stereotype, stereotypeNode, rule);

		// create mandatories if any
		Node createNode = HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule).get(0);
		EClassifierInfo childInfo = ECM.getEClassifierInfo(stereotype);
		if (childInfo.hasMandatories()) {
			if (c.create_mandatory_children)
				ModuleInternalsApplicator.createMandatoryChildren(rule, childInfo, createNode, OperationType.ATTACH);
			if (c.create_mandatory_neighbours)
				ModuleInternalsApplicator.createMandatoryNeighbours(rule, childInfo, createNode, OperationType.ATTACH);
		}

		// create <<preserve>> rules for metaclass
		NodePair metaclassNodePair = HenshinRuleAnalysisUtilEx.createPreservedNode(rule, GlobalConstants.SEL,
				(EClass) metaclass);
		HenshinRuleAnalysisUtilEx.createCreateEdge(stereotypeNode, metaclassNodePair.getRhsNode(), baseRef);
		return module;
	}

	/**
	 * Finds all valid metaclasses and according names for the reference
	 * 
	 * @param stereotype
	 * @return
	 */
	private HashMap<EClassifier, Set<EReference>> getCombinations(EClass stereotype) {
		HashMap<EClassifier, Set<EReference>> result = new HashMap<>();
		List<EStructuralFeature> featureList = EMFMetaAccess.getEStructuralFeaturesByRegEx(stereotype, "^(base)_\\w+",
				true);
		for (EStructuralFeature extension : featureList) {
			EClassifier metaClass = extension.getEType();
			if (!result.containsKey(metaClass))
				result.put(metaClass, new HashSet<EReference>());
			result.get(metaClass).add((EReference) extension);
			/*
			 * if (findSubtypes) { for (EClassifier subtype :
			 * ECM.getAllSubTypes(metaClass)) {
			 * getCombinations_MetaclassSubtypes(subtype, (EReference)
			 * extension, result); } }
			 */
		}
		return result;
	}

	@SuppressWarnings("unused")
	@Deprecated
	private void getCombinations_MetaclassSubtypes(EClassifier eClassifier, EReference extensionRef,
			HashMap<EClassifier, Set<EReference>> result) {
		if (eClassifier instanceof EClass && !((EClass) eClassifier).isAbstract()) {
			if (!result.containsKey(eClassifier))
				result.put(eClassifier, new HashSet<EReference>());
			result.get(eClassifier).add(extensionRef);
		}
		for (EClassifier subtype : ECM.getAllSubTypes(eClassifier)) {
			getCombinations_MetaclassSubtypes(subtype, extensionRef, result);
		}
	}
}
