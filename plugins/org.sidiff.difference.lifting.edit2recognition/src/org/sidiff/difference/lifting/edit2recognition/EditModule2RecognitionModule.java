package org.sidiff.difference.lifting.edit2recognition;

import static org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.isAmalgamationUnit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.difference.lifting.edit2recognition.exceptions.NoMainUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.NoUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.UnsupportedApplicationConditionException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.UnsupportedTransformationSytemException;
import org.sidiff.difference.lifting.edit2recognition.traces.TransformationPatterns;
import org.sidiff.difference.lifting.edit2recognition.util.Edit2RecognitionUtil;
import org.sidiff.difference.lifting.edit2recognition.util.TransformationConstants;
import org.sidiff.difference.symmetric.SymmetricPackage;

/**
 * Transforms an edit rule transformation system into an recognition transformation system.
 * 
 * @author Manuel Ohrndorf
 */
public class EditModule2RecognitionModule {

	/**
	 * The edit rule transformation system.
	 */
	private Module editModule;
	
	/**
	 * The edit rule main unit.
	 */
	private Unit executeMainUnit;
	
	/**
	 * The recognition rule transformation system.
	 */
	private Module recognitionModule;
	
	/**
	 * The recognition rule main unit.
	 */
	private Unit recognitionMainUnit;
	
	/**
	 * Unit transformation engine
	 */
	private EditUnit2RecognitionUnit transformation;
	
	/**
	 * Remembers all added implicit edges
	 */
	private List<Edge> implicitEdges;

	/** 
	 * "atomic" or "complex" edit operation
	 */
	private boolean atomic;
	
	/**
	 * Transforms an edit rule transformation system into an recognition transformation system.
	 * <p>supports:</p>
	 * <ul>
	 * <li>Sequential unit with single rule</li>
	 * <li>Priority unit with single rule</li>
	 * <li>Amalgamation unit</li>
	 * </ul>
	 * 
	 * @param editModule
	 *            the edit rule transformation system.
	 * @throws NoUnitFoundException 
	 * @throws NoMainUnitFoundException 
	 */
	public EditModule2RecognitionModule(Module editModule) 
			throws NoMainUnitFoundException, NoUnitFoundException {
		
		// Initialize transformation:
		this.editModule = editModule;
		this.executeMainUnit = Edit2RecognitionUtil.findExecuteMainUnit(editModule);
		
		// Complex or atomic edit operation	
		URI editRuleURI = EcoreUtil.getURI(editModule);
		
		if (editRuleURI.toString().contains(TransformationConstants.PATH_SEGMENT_ATOMIC_EDITRULES)) {
			atomic = true;
		} else {
			atomic = false;
		}
	}
	
	/**
	 * Creates all missing opposite edges in the transformation system.
	 * 
	 * @param editModule The edit rule module to process.
	 */
	private void createImplicitEdges(Module editModule) {
		implicitEdges = new ArrayList<Edge>();
		
		for (Rule editRule : HenshinModuleAnalysis.getAllRules(editModule)) {
			createImplicitEdges(editRule.getLhs().getEdges());
			createImplicitEdges(editRule.getRhs().getEdges());
		}
	}
	
	/**
	 * Create the implicit opposite edges in the edit rule which were not defined before.
	 * 
	 * @param edges
	 *            the list of edges which will be searched for undefined opposite edges.
	 */
	private void createImplicitEdges(List<Edge> edges) {
		List<Edge> oppositeEdges = new LinkedList<Edge>();

		for (Edge edge : edges) {
			// Check if edge (EReference type) has opposite
			if (edge.getType().getEOpposite() != null) {
				// Look for existing opposite edge
				boolean oppositeExists = false;

				for (Edge opposite : edge.getTarget().getOutgoing()) {
					if (opposite.getType() == edge.getType().getEOpposite()) {
						oppositeExists = true;
					}
				}

				// If opposite do not exist
				if (!oppositeExists) {
					// Create new edge (unlinked to graph)
					Edge oppositeEdge = HenshinFactory.eINSTANCE.createEdge();
					oppositeEdge.setSource(edge.getTarget());
					oppositeEdge.setTarget(edge.getSource());
					oppositeEdge.setType(edge.getType().getEOpposite());
					oppositeEdges.add(oppositeEdge);
				}
			}
		}

		// Remember edges
		implicitEdges.addAll(oppositeEdges);

		// Link new edges to graph
		edges.addAll(oppositeEdges);
	}

	/**
	 * Delete all added implicit edges
	 */
	public void deleteImplicitEdges() {
		for (Edge edge : implicitEdges) {
			EcoreUtil.remove(edge);
			edge.getSource().getOutgoing().remove(edge);
			edge.getTarget().getIncoming().remove(edge);
		}
	}

	/**
	 * Initializes a new recognition rule module.
	 * 
	 * @return A new recognition rule module.
	 */
	private Module createRecognitionModule() {

		// Initialize recognition transformation system
		Module recognitionModule = HenshinFactory.eINSTANCE.createModule();
		recognitionModule.setName(TransformationConstants.RECOGNITION_MODULE_PREFIX + editModule.getName());

		// Organize recognition rule imports
		recognitionModule.getImports().add(SymmetricPackage.eINSTANCE);
		
		// Copy description
		if (editModule.getDescription() != null) {
			recognitionModule.setDescription(TransformationConstants.RECOGNITION_TS_DESCRIPTION_PREFIX + editModule.getDescription());
		}

		for (EPackage eImport : editModule.getImports()) {
			recognitionModule.getImports().add(eImport);
		}
		
		return recognitionModule;
	}
	
	/**
	 * Transforms an edit rule transformation system into an recognition transformation system.
	 * supports:
	 * <ul>
	 * <li>Sequential unit with single rule
	 * <li>Priority unit with single rule
	 * <li>Amalgamation unit
	 * <ul>
	 * <br>
	 * 
	 * @param editRuleModule
	 *            the edit rule transformation system.
	 * @return
	 * @throws UnsupportedTransformationSytemException
	 * @throws NoUnitFoundException
	 * @throws NoMainUnitFoundException
	 * @throws UnsupportedApplicationConditionException 
	 * @throws NoRecognizableChangesInEditRule 
	 */
	public void transform()

			throws UnsupportedTransformationSytemException,
			NoUnitFoundException, NoMainUnitFoundException, 
			UnsupportedApplicationConditionException {

		/*
		 * Start unit transformation
		 */
		
		// Add implicit edges to all rules in the module
		createImplicitEdges(editModule);
		
		// Supported: AmalgamationUnit := Unit -> Kernel Rule -> Multi-Rules
		if (isAmalgamationUnit(executeMainUnit)) {
			recognitionMainUnit = transformAmalgamationUnit();
		}
		
		// Supported: SequentialUnit with single Rule
		else if (executeMainUnit instanceof SequentialUnit) {
			recognitionMainUnit = transformSequentialUnit();
		}

		// Supported: PriorityUnit with single Rule
		else if (executeMainUnit instanceof PriorityUnit) {
			recognitionMainUnit = transformPriorityUnit();
		}
		
		else {
			throw new UnsupportedTransformationSytemException(editModule);
		}

		// TODO: More Transformation Units
		
		// Initialize recognition rule:
		recognitionModule = createRecognitionModule();
		recognitionModule.getUnits().add(recognitionMainUnit);
	}

	/**
	 * Handles the transformation of the edit rule amalgamation unit to a recognition rule unit.
	 * 
	 * @return The transformed recognition rule unit.
	 * @throws UnsupportedTransformationSytemException
	 * @throws UnsupportedApplicationConditionException
	 * @throws NoRecognizableChangesInEditRule 
	 */
	private Unit transformAmalgamationUnit() 
			throws UnsupportedTransformationSytemException, 
			UnsupportedApplicationConditionException {

		List<Unit> subUnits = executeMainUnit.getSubUnits(false);
		
		if ((subUnits.size() == 1) && (subUnits.get(0) instanceof Rule)) {
			Rule kernelRule = (Rule) subUnits.get(0);
			
			// Print report
			//System.out.println("Amalgamation Unit (" + executeMainUnit.getName() + "): ");
			//System.out.println("  Kernel rule: " + kernelRule.getName());
			//System.out.println("  Multi rules: ");

			for (Rule multiRule : kernelRule.getMultiRules()) {
				//System.out.println("    -> " + multiRule.getName());
			}
			
			// Start rule transformation
			transformation = new EditMulti2RecognitionMulti();
			Unit recognitionMainUnit = ((EditMulti2RecognitionMulti) transformation)
					.transform(executeMainUnit, atomic);
			
			return recognitionMainUnit;
		}  else {
			throw new UnsupportedTransformationSytemException(editModule);
		}
	}
	
	/**
	 * Handles the transformation of the edit rule sequential unit to a recognition rule unit.
	 * 
	 * @return The transformed recognition rule unit.
	 * @throws UnsupportedTransformationSytemException
	 * @throws UnsupportedApplicationConditionException
	 * @throws NoRecognizableChangesInEditRule 
	 */
	private Unit transformSequentialUnit() 
			
			throws UnsupportedTransformationSytemException, 
			UnsupportedApplicationConditionException {
		
		List<Unit> subUnits = ((SequentialUnit) executeMainUnit).getSubUnits();

		if ((subUnits.size() == 1) && (subUnits.get(0) instanceof Rule)) {
			Rule editRule = (Rule) subUnits.get(0);

			// Print report
			//System.out.println("Sequential Unit (" + executeMainUnit.getName() + "): "
			//		+ editRule.getName());
			
			// Start rule transformation
			transformation = new EditRule2RecognitionRule();
			Unit recognitionMainUnit = ((EditRule2RecognitionRule) transformation)
					.transform(editRule, executeMainUnit, atomic);
			
			return recognitionMainUnit;
		} else {
			throw new UnsupportedTransformationSytemException(editModule);
		}
	}

	/**
	 * Handles the transformation of the edit rule priority unit to a recognition rule unit.
	 * 
	 * @return The transformed recognition rule unit.
	 * @throws UnsupportedTransformationSytemException
	 * @throws UnsupportedApplicationConditionException
	 * @throws NoRecognizableChangesInEditRule 
	 */
	private Unit transformPriorityUnit() 
	
			throws UnsupportedTransformationSytemException, 
			UnsupportedApplicationConditionException {
		
		List<Unit> subUnits = ((PriorityUnit) executeMainUnit).getSubUnits();

		if ((subUnits.size() == 1) && (subUnits.get(0) instanceof Rule)) {
			Rule editRule = (Rule) subUnits.get(0);

			// Print report
			//System.out.println("Priority Unit (" + executeMainUnit.getName() + "): "
			//		+ editRule.getName());
			
			// Start rule transformation
			transformation =  new EditRule2RecognitionRule();
			Unit recognitionMainUnit = ((EditRule2RecognitionRule) transformation)
					.transform(editRule, executeMainUnit, atomic);
			
			return recognitionMainUnit;
		} else {
			throw new UnsupportedTransformationSytemException(editModule);
		}
	}

	/**
	 * @return The edit rule transformation system.
	 */
	public Module getEditModule() {
		return editModule;
	}

	/**
	 * @return The edit rule main unit.
	 */
	public Unit getExecuteMainUnit() {
		return executeMainUnit;
	}

	/**
	 * @return The recognition rule transformation system.
	 */
	public Module getRecognitionModule() {
		return recognitionModule;
	}

	/**
	 * @return The recognition rule main unit.
	 */
	public Unit getRecognitionMainUnit() {
		return recognitionMainUnit;
	}

	/**
	 * @return All transformation patterns of the module transformation.
	 */
	public Collection<TransformationPatterns> getPatterns() {
		return transformation.getPatterns();
	}
}
