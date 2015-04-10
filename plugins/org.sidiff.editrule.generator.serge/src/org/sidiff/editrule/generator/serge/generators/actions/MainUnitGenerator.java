package org.sidiff.editrule.generator.serge.generators.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.exceptions.EPackageNotFoundException;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.common.henshin.ParameterInfo.ParameterDirection;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.GlobalConstants;
import org.sidiff.editrule.generator.serge.core.InverseTracker;

public class MainUnitGenerator {

	/**
	 * The module to generate a main unit for.
	 */
	private Module module;

	/**
	 * The main unit which is generated.
	 */
	private PriorityUnit prioUnit;

	/**
	 * Constructor.
	 * 
	 * @param module
	 */
	public MainUnitGenerator(Module module) {
		this.module = module;
	}

	public void generate() {

		prioUnit = HenshinFactory.eINSTANCE.createPriorityUnit();
		prioUnit.setActivated(true);
		prioUnit.setName(INamingConventions.MAIN_UNIT);

		List<Rule> rulesUnderModule = HenshinModuleAnalysis.getAllRules(module);
		assert (rulesUnderModule.size() == 1);

		Rule rule = rulesUnderModule.get(0);
		generateUnitParameters(rule);

		// Add rule to unit
		prioUnit.getSubUnits().add(rule);

		// Add unit to module
		module.getUnits().add(prioUnit);

		// kick out unnecessary sub package imports when super package import
		// available
		// and set main meta-model packages as first import
		organizeImports(module);
	}

	/**
	 * Generates the unit parameters and the respective mappings.
	 */
	private void generateUnitParameters(Rule rule) {
		// LHS nodes
		for (Node lhsNode : rule.getLhs().getNodes()) {
			Parameter objectInParam = null;
			objectInParam = rule.getParameter(lhsNode.getName());
			if (objectInParam != null) {
				generateUnitParameter(objectInParam, ParameterDirection.IN);
			}

			for (Attribute attribute : lhsNode.getAttributes()) {
				Parameter valueInParam = null;
				valueInParam = rule.getParameter(attribute.getValue());
				if (valueInParam != null) {
					generateUnitParameter(valueInParam, ParameterDirection.IN);
				}
			}
		}

		// RHS nodes
		for (Node rhsNode : rule.getRhs().getNodes()) {
			if (HenshinRuleAnalysisUtilEx.isCreationNode(rhsNode)) {
				Parameter objectOutParam = null;
				objectOutParam = rule.getParameter(rhsNode.getName());
				if (objectOutParam != null) {
					generateUnitParameter(objectOutParam, ParameterDirection.OUT);
				}
			}

			for (Attribute attribute : rhsNode.getAttributes()) {
				Parameter valueInParam = null;
				valueInParam = rule.getParameter(attribute.getValue());
				if (valueInParam != null) {
					generateUnitParameter(valueInParam, ParameterDirection.IN);
				}
			}
		}
	}

	/**
	 * Generates a unit parameter and the correct mapping.
	 * 
	 * @param ruleParameter
	 * @param direction
	 */
	private void generateUnitParameter(Parameter ruleParameter, ParameterDirection direction) {
		// Parameter
		Parameter unitParameter = HenshinFactory.eINSTANCE.createParameter(ruleParameter.getName());
		if (unitParameter.getName().equals(GlobalConstants.SEL)) {
			unitParameter.setName(GlobalConstants.SELEO);
		}
		unitParameter.setType(ruleParameter.getType());

		// Mapping
		ParameterMapping pm = HenshinFactory.eINSTANCE.createParameterMapping();
		if (direction.equals(ParameterDirection.IN)) {
			pm.setSource(unitParameter);
			pm.setTarget(ruleParameter);
		} else {
			pm.setSource(ruleParameter);
			pm.setTarget(unitParameter);
		}

		// Add to unit
		prioUnit.getParameters().add(unitParameter);
		prioUnit.getParameterMappings().add(pm);
		
		// Add to inverse tracker
		InverseTracker.INSTANCE.overrideParameter(ruleParameter, unitParameter);
	}

	/**
	 * This method removes unnecessary imports of EPackages that are sub
	 * packages of imported super packages. Additionally, this method removes
	 * all EPackage imports whose model elements have not been used. Also the
	 * meta model EPackage will always be set as the first import.
	 * 
	 * @param module
	 */
	private void organizeImports(Module module) {

		// find out which (sub) packages have actually been used (via node type
		// usage)
		List<EPackage> actuallyUsedEPackages = new ArrayList<EPackage>();
		for (Rule rule : HenshinModuleAnalysis.getAllRules(module)) {
			List<Node> allNodesInRule = new ArrayList<Node>();
			allNodesInRule.addAll(rule.getRhs().getNodes());
			allNodesInRule.addAll(rule.getLhs().getNodes());
			for (Node node : allNodesInRule) {
				EPackage usedEPackage = node.getType().getEPackage();
				if (!actuallyUsedEPackages.contains(usedEPackage)) {
					actuallyUsedEPackages.add(usedEPackage);
				}
			}
		}

		// get EPackage of main meta-model
		EPackage mainMetaModel = Configuration.getInstance().EPACKAGESSTACK.firstElement();
		// get sub EPackages of main meta-model
		List<EPackage> subsOfMain = new ArrayList<EPackage>();
		try {
			subsOfMain.addAll(EMFUtil.getAllSubEPackages(mainMetaModel));
		} catch (EPackageNotFoundException e) {
			e.printStackTrace();
		}

		// remove the following EPackages:
		// a) unused EPackages which are not the EPackage of the meta-model
		// b) sub EPackages of the meta-model
		Iterator<EPackage> itImports = module.getImports().iterator();
		while (itImports.hasNext()) {

			EPackage currentEPackage = itImports.next();
			// if currentEPackage is not the meta-model itself....
			if (!mainMetaModel.equals(currentEPackage)) {
				// ..but a sub package of the meta-model
				// ..or actually not used: remove it.
				boolean actuallyUsed = actuallyUsedEPackages.contains(currentEPackage);
				if (subsOfMain.contains(currentEPackage) || !actuallyUsed) {
					itImports.remove();
				}
			}
		}
	}

}
