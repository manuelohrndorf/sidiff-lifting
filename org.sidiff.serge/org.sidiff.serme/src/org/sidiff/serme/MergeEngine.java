package org.sidiff.serme;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;

public class MergeEngine {

	private String henshinFile = null;

	/**
	 * The MergeEngine handles manipulations on the source henshin file
	 * 
	 * @param henshinFile
	 */
	public MergeEngine(String henshinFile) {

		this.henshinFile = henshinFile;

	}

	/**
	 * Starts the @see MergeEngine and invokes all methods
	 * 
	 * @return Module
	 * @throws Exception
	 */
	public void startEngine(HenshinResourceSet resourceSet) throws Exception {

		
		Module sourceTs = (Module) resourceSet.getModule(this.henshinFile,false);
		Module workingTs = EcoreUtil.copy(sourceTs);

		for (Unit tu : workingTs.getUnits()) {
			if (tu instanceof SequentialUnit) {
				tu = mergeSequentialUnit((SequentialUnit) tu);
			}
		}

		Module mergedTs = constructMergedTs(workingTs);
		
		resourceSet.saveEObject(mergedTs, "testrules/" + mergedTs.getName() + "_merged.henshin");

	}

	/**
	 * Merges N Units into one
	 * 
	 * @param Module
	 * @return Module
	 */
	public Module constructMergedTs(Module ts) {

		if (ts.getUnits().size() == 1) {

			ts.setName(ts.getUnits().get(0).getName());
			ts.setDescription(ts.getUnits().get(0).getDescription());
			ts.getUnits().get(0).setName("mainUnit");
		}

		else {
			// TODO
			// Do something in case of more than one Unit
			// within the Module
		}
		return ts;
	}

	/**
	 * Merges the Sequential unit's rules into one and constructs a new
	 * Sequential Unit containing only this rule
	 * 
	 * @param the
	 *            working sequential unit
	 * @return the merged sequential unit
	 * 
	 */
	public SequentialUnit mergeSequentialUnit(SequentialUnit seqUnit) {

		/**
		 * TODO METACLASS Creation copy Module of metaclass setname
		 * setdescription
		 * 
		 * Recursive Stereotype Creation for(...){ createNode(s) createEdge(s)
		 * Rule(MC).createParameters(ST) Rule(MC).deleteParameters(ST-Mapped)
		 * UnitParameterMapping }
		 * 
		 * Copy vs Edit ? URI ? RelativePaths ? Beziehungen zwischen
		 * Rules/Parameters/Mappings (immer transitiv)
		 */

		// Testing:

		SequentialUnit resultUnit = EcoreUtil.copy(seqUnit);

		HenshinRuleAnalysisUtilEx hraue = new HenshinRuleAnalysisUtilEx();
		Module ts = hraue.getContainingTransformationSystem(seqUnit);

		// ResourceSet rs = new ResourceSetImpl();
		// Resource r =
		// rs.getResource(EcoreUtil.getURI(seqUnit.getSubUnits().get(0)),true);
		// EcoreUtil.resolveAll(rs);
		// SequentialUnit workUnit = (SequentialUnit) r.getContents().get(0);

		// Rule resultRule = (Rule) seqUnit.getSubUnits().get(0);
		// Parameter parMc = resultRule.getParameterByName("New");
		// ParameterMapping pmMc =
		// HenshinRuleAnalysisUtilEx.findUnitParamterMapping(seqUnit.getParameterMappings(),
		// parMc);

		System.out.println("SeqUnitName: "
				+ hraue.getContainingTransformationSystem(seqUnit).getName());

		System.out.println("RuleName: " + ts.getUnits().get(0).getName());

		return resultUnit;
	}

	public String getHenshinFilename() {
		return henshinFile;
	}

	public void setHenshinFilename(String henshinFile) {
		this.henshinFile = henshinFile;
	}

}
