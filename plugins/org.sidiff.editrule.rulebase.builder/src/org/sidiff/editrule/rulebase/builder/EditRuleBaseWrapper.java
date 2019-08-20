package org.sidiff.editrule.rulebase.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.URIMappingRegistryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;
import org.sidiff.editrule.analysis.classification.IClassificator;
import org.sidiff.editrule.analysis.criticalpairs.IntraRuleBasePotentialConflictAnalyzer;
import org.sidiff.editrule.analysis.criticalpairs.IntraRuleBasePotentialDependencyAnalyzer;
import org.sidiff.editrule.rulebase.Classification;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialConflict;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.RulebaseFactory;
import org.sidiff.editrule.rulebase.builder.internal.EditRuleBaseBuilderPlugin;
import org.sidiff.editrule.rulebase.builder.util.ParameterExtractor;
import org.sidiff.editrule.rulebase.util.EditRuleItemUtil;

/**
 * Encapsulates a RuleBase instance and provides some convenience functions for the RuleBase management.
 * 
 * @author Manuel Ohrndorf, cpietsch
 */
public class EditRuleBaseWrapper {
	
	/**
	 * The path of the *.rulebase file. 
	 */
	private URI rulebaseURI;
	
	/**
	 * The (Ecore model) rulebase root element. 
	 */
	private RuleBase rulebase;
	
	/**
	 * Internal {@link IntraRuleBasePotentialDependencyAnalyzer} 
	 */
	private IntraRuleBasePotentialDependencyAnalyzer ruleBasePotentialDependencyAnalyzer;
	
	/**
	 * Internal {@link IntraRuleBasePotentialConflictAnalyzer} 
	 */
	private IntraRuleBasePotentialConflictAnalyzer ruleBasePotentialConflictAnalyzer;
	
	/**
	 * List of edited (Henshin) Edit-Rules. Used to delay the storage of the Henshin
	 * files. Call {@link EditRuleBaseWrapper#saveRuleBase()} to save all.
	 */
	private Set<EditRule> changedEditRules;
	
	private SiDiffResourceSet resourceSet;

	private IProject project;
	
	/**
	 * Initializes a (new or existing) rulebase.
	 * 
	 * @param rulebaseURI
	 *            The path of the rulebase.
	 * @param resolveRules
	 *            Resolve all (Henshin) Edit-Rules.
	 */
	public EditRuleBaseWrapper(SiDiffResourceSet resourceSet, IProject project, URI rulebaseURI, boolean resolveRules) {
		this.resourceSet = Objects.requireNonNull(resourceSet, "resourceSet is null");
		this.rulebaseURI = Objects.requireNonNull(rulebaseURI, "rulebaseURI is null");
		this.project = Objects.requireNonNull(project, "project is null");

		if (resourceSet.getURIConverter().exists(rulebaseURI, null)) {
			// Load existing rule base
			URIMappingRegistryImpl.INSTANCE.getURI(rulebaseURI);
			try {
				rulebase = resourceSet.loadEObject(rulebaseURI, RuleBase.class);
			} catch(Exception e) {
				EditRuleBaseBuilderPlugin.logError("Rulebase " + rulebaseURI + " is invalid. Creating a new one.", e);
				rulebase = null;

				// delete the invalid file
				try {
					resourceSet.getURIConverter().delete(rulebaseURI, null);
				} catch (IOException e1) {
					throw new RuntimeException("Failed to delete corrupted rulebase file.", e1);
				}
			}
		}

		if(rulebase == null) {
			// Create new rule base
			rulebase = RulebaseFactory.eINSTANCE.createRuleBase();
			Resource resource = resourceSet.createResource(rulebaseURI);
			resource.getContents().add(rulebase);
		}

		if (resolveRules) {
			EcoreUtil.resolveAll(rulebase);
		}

		init();
	}

	/**
	 * Initializes this rulebase wrapper.
	 */
	private void init() {
		ruleBasePotentialDependencyAnalyzer = new IntraRuleBasePotentialDependencyAnalyzer(rulebase);
		ruleBasePotentialConflictAnalyzer = new IntraRuleBasePotentialConflictAnalyzer(rulebase);
		changedEditRules = new HashSet<EditRule>();
	}

	/**
	 * Saves the rulebase file.
	 */
	public void saveRuleBase() {
		resourceSet.saveAllResources();
	}
	
	/**
	 * @param changedEditRule
	 *            A changed edit rule which will be saved.
	 */
	public void addChangedEditRule(EditRule changedEditRule) {
		changedEditRules.add(changedEditRule);
	}

	/**
	 * Creates a new rule base item which includes an edit rule.
	 * 
	 * @param editRule
	 *            The Henshin edit-rule.
	 * 
	 * @return A new rule base item.
	 */
	public RuleBaseItem createItem(EditRule editRule) {
		
		// Create rule base element
		RuleBaseItem item = RulebaseFactory.eINSTANCE.createRuleBaseItem();
		item.setEditRule(editRule);
		
		return item;
	}
	
	/**
	 * Initializes a rule base edit rule wrapper.
	 * 
	 * @return an edit rule wrapper.
	 * @throws NoMainUnitFoundException 
	 */
	public EditRule createEditRule(Module editModule) throws NoMainUnitFoundException {

		// Create edit rule
		EditRule editRule = RulebaseFactory.eINSTANCE.createEditRule();
		editRule.setExecuteModule(editModule);
		editRule.setUseDerivedFeatures(HenshinModuleAnalysis.hasDerivedReferences(editModule));
		
		return editRule;
	}

	/**
	 * @return All list containing all items of this rulebase.
	 */
	public EList<RuleBaseItem> getItems() {
		return rulebase.getItems();
	}

	/**
	 * Adds and analysis a new rulebase item.
	 * 
	 * @param item
	 *            The new rulebase item.
	 */
	public void addItem(RuleBaseItem item) {
		addItem(rulebase.getItems().size(), item);
	}

	/**
	 * Adds and analysis a new rulebase item.
	 * 
	 * @param position
	 *            Index at which the specified element is to be inserted.
	 * @param item
	 *            The new rulebase item.
	 */
	public void addItem(int position, RuleBaseItem item) {
		// Add item to rule base at specified position
		rulebase.getItems().add(position, item);

		// Add document type (if it is not already added).
		addDocumentType(item.getEditRule());
		
		// Find new potential dependencies in the rule base
		ruleBasePotentialDependencyAnalyzer.findDependencies(item.getEditRule());
		
		// Find new potential conflicts in the rule base
		ruleBasePotentialConflictAnalyzer.findConflicts(item.getEditRule());

		// Extract formal parameters of the edit rule
		ParameterExtractor paramExtractor = new ParameterExtractor(item.getEditRule());
		paramExtractor.extractParameters();

		for (IClassificator c : IClassificator.MANAGER.getClassificators(item.getEditRule())) {
			Classification a = RulebaseFactory.eINSTANCE.createClassification();
			a.setName(c.createClassification(item.getEditRule()));
			a.setClassificator(c.getKey());
			rulebase.getItems().get(position).getEditRule().getClassification().add(a);
		}
	}

	/**
	 * Removes an rulebase item.
	 * 
	 * @param item
	 *            The item to remove.
	 */
	public void removeItem(RuleBaseItem item) {

		// Remove potential dependencies of the item:
		for (PotentialDependency potDep : getIncomingDependencies(item.getEditRule())) {
			EcoreUtil.remove(potDep);
		}
		
		// Remove potential conflicts of the item:
		for (PotentialConflict potCon : getConflicts(item.getEditRule())) {
			EcoreUtil.remove(potCon);
		}

		// Remove item from rule base
		EcoreUtil.remove(item);
	}

	/**
	 * Removes all items from the rulebase.
	 */
	public void clean() {
		while (!rulebase.getItems().isEmpty()) {
			removeItem(rulebase.getItems().get(0));
		}
	}

	/**
	 * Collects all incoming potential (Node, Edge, Attribute) dependencies of the given Edit-Rule.
	 * 
	 * @param er
	 *            The Edit-Rule of interest.
	 * @return All incoming potential (Node, Edge, Attribute) dependencies.
	 */
	public List<PotentialDependency> getIncomingDependencies(EditRule er) {
		List<PotentialDependency> potDeps = new ArrayList<PotentialDependency>();
		
		// Nodes
		for (PotentialDependency potDep : rulebase.getPotentialNodeDependencies()) {
			if ((potDep.getSourceRule() == er) || (potDep.getTargetRule() == er)) {
				potDeps.add(potDep);
			}
		}

		// Edges
		for (PotentialDependency potDep : rulebase.getPotentialEdgeDependencies()) {
			if ((potDep.getSourceRule() == er) || (potDep.getTargetRule() == er)) {
				potDeps.add(potDep);
			}
		}
		
		// Attributes
		for (PotentialDependency potDep : rulebase.getPotentialAttributeDependencies()) {
			if ((potDep.getSourceRule() == er) || (potDep.getTargetRule() == er)) {
				potDeps.add(potDep);
			}
		}
		
		return potDeps;
	}

	/**
	 * Collects all potential (Node, Edge, Attribute) conflicts of the given Edit-Rule.
	 * 
	 * @param er
	 *            The Edit-Rule of interest.
	 * @return All potential (Node, Edge, Attribute) conflicts.
	 */
	public List<PotentialConflict> getConflicts(EditRule er) {
		List<PotentialConflict> potCons = new ArrayList<PotentialConflict>();
		
		// Nodes
		for (PotentialConflict potCon : rulebase.getPotentialNodeConflicts()) {
			if ((potCon.getSourceRule() == er) || (potCon.getTargetRule() == er)) {
				potCons.add(potCon);
			}
		}

		// Edges
		for (PotentialConflict potCon : rulebase.getPotentialEdgeConflicts()) {
			if ((potCon.getSourceRule() == er) || (potCon.getTargetRule() == er)) {
				potCons.add(potCon);
			}
		}
		
		// Attributes
		for (PotentialConflict potCon : rulebase.getPotentialAttributeConflicts()) {
			if ((potCon.getSourceRule() == er) || (potCon.getTargetRule() == er)) {
				potCons.add(potCon);
			}
		}
		
		return potCons;
	}
	
	/**
	 * Inverts the active flag of all rulebase items.
	 */
	public void invertAllItemsActivity() {
		for (RuleBaseItem item : getItems()) {
			EditRuleItemUtil.invertActivity(item);
		}
	}
	
	/**
	 * @return The path to the rulebase.
	 */
	public URI getRuleBaseLocation() {
		return rulebaseURI;
	}

	/**
	 * @return The (Ecore model) rulebase root.
	 */
	public RuleBase getRuleBase() {
		return rulebase;
	}

	/**
	 * @return Unique identifier of the rulebase.
	 */
	public String getKey() {
		return rulebase.getKey();
	}

	/**
	 * Sets the unique identifier of the rulebase.
	 * 
	 * @param key
	 *            The rulebase key.
	 */
	public void setKey(String key) {
		rulebase.setKey(key);
	}
	
	/**
	 * @return The human readable name of the rulebase.
	 */
	public String getName() {
		return rulebase.getName();
	}

	/**
	 * Sets the human readable name of the rulebase.
	 * 
	 * @param name
	 *            The rulebase name.
	 */
	public void setName(String name) {
		rulebase.setName(name);
	}

	/**
	 * Analyse editRule for new imports.
	 * 
	 * @param editRule
	 *            the added edit rule.
	 */
	private void addDocumentType(EditRule editRule){
		Set<String> docTypes = findDocumentTypes(editRule.getExecuteModule());
		for (String docType : docTypes) {
			if (!rulebase.getDocumentTypes().contains(docType)){
				rulebase.getDocumentTypes().add(docType);
			}
		}
	}
	
	/**
	 * {@link EMFModelAccessEx#getCharacteristicDocumentType(Set)}
	 * 
	 * @param module
	 *            The module to analyse.
	 * @return The most characteristic document type of the module.
	 */
	public static String findCharacteristicDocumentType(Module module) {
		Set<String> documentTypes = new HashSet<String>();
		
		List<EPackage> imports = module.getImports();		
		for (EPackage eImport : imports) {
			EPackage pkg = eImport;
			while (pkg != null && pkg.getESuperPackage() != null) {
				pkg = pkg.getESuperPackage();
			}
			if(pkg != null) {
				documentTypes.add(pkg.getNsURI());				
			}
		}
		
		return EMFModelAccess.getCharacteristicDocumentType(documentTypes);
	}
	
	/**
	 * Collects all document types of the given module.
	 * 
	 * @param module
	 *            The module to analyze.
	 * @return All document type of the module.
	 */
	public static Set<String> findDocumentTypes(Module module) {
		Set<String> documentTypes = new HashSet<String>();
		
		List<EPackage> imports = module.getImports();		
		for (EPackage eImport : imports) {
			EPackage pkg = eImport;
			while (pkg != null && pkg.getESuperPackage() != null) {
				pkg = pkg.getESuperPackage();
			}
			if(pkg != null) {
				documentTypes.add(pkg.getNsURI());				
			}
		}
		
		return documentTypes;
	}
	
	/**
	 * Searches an Edit-Rule rulebase wrapper by its corresponding Henshin Edit-Rule.
	 * 
	 * @param editModule
	 *            The Henshin Edit-Rule.
	 * @return The corresponding rulebase Edit-Rule wrapper.
	 */
	public EditRule findEditRule(Module editModule) {
		for (EditRule er_rule : rulebase.getEditRules()) {
			if (editModule == er_rule.getExecuteModule()) {
				return er_rule;
			}
		}
		return null;
	}
	
	/**
	 * Searches an Edit-Rule rulebase wrapper by its corresponding Henshin Edit-Rule file.
	 * 
	 * @param editModule
	 *            The Henshin Edit-Rule file.
	 * @return The corresponding rulebase Edit-Rule wrapper.
	 */
	public EditRule findEditRule(String editRulePath) {
		File editRuleFile = new File(editRulePath);
		
		for (EditRule er_rule : rulebase.getEditRules()) {
			URI rbEditRuleURI = EcoreUtil.getURI(er_rule.getExecuteMainUnit()).trimFragment();
			File rbEditRuleFile = EMFStorage.toFile(rbEditRuleURI);
			if (editRuleFile.equals(rbEditRuleFile)) {
				return er_rule;
			}
		}
		return null;
	}

	public void addInverseEditRules(IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, getRuleBase().getItems().size());
		for (RuleBaseItem rule : getRuleBase().getItems()) {
			if (rule.getEditRule().getInverse() == null) {
				for (Annotation a : rule.getEditRule().getExecuteModule().getAnnotations()) {
					if (a.getKey().equals("INVERSE")) {
						String ruleName = a.getValue();
						for (RuleBaseItem invRule : getRuleBase().getItems()) {
							if (invRule.getEditRule().getExecuteModule().getName().equals(ruleName)) {
								rule.getEditRule().setInverse(invRule.getEditRule());
								break;
							}
						}
					}
				}
			}
			progress.worked(1);
		}
	}

	public IProject getProject() {
		return project;
	}
	
	public SiDiffResourceSet getResourceSet() {
		return resourceSet;
	}
}
