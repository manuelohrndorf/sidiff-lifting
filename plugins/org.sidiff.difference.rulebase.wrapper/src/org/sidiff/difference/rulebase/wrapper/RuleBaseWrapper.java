package org.sidiff.difference.rulebase.wrapper;

import static org.sidiff.difference.rulebase.wrapper.RuleBaseItemWrapper.increaseVersion;
import static org.sidiff.difference.rulebase.wrapper.RuleBaseItemWrapper.invertActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.URIMappingRegistryImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Module;
import org.sidiff.difference.asymmetric.dependencies.potential.RuleBasePotentialDependencyAnalyzer;
import org.sidiff.difference.asymmetric.paramextraction.ParameterExtractor;
import org.sidiff.difference.lifting.edit2recognition.exceptions.NoMainUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.NoRecognizableChangesInEditRule;
import org.sidiff.difference.lifting.edit2recognition.exceptions.NoUnitFoundException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.UnsupportedApplicationConditionException;
import org.sidiff.difference.lifting.edit2recognition.exceptions.UnsupportedTransformationSytemException;
import org.sidiff.difference.lifting.edit2recognition.util.Edit2RecognitionUtil;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.PotentialDependency;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.RulebaseFactory;
import org.sidiff.difference.rulebase.wrapper.util.Edit2RecognitionException;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.emf.EMFStorage;

/**
 * Encapsulates a RuleBase instance and provides some convenience functions for the RuleBase management.
 */
public class RuleBaseWrapper extends Observable {

	private boolean notifyChangedStarted;
	private EContentAdapter notifyChangedAdapter;
	private boolean dirty;
	private URI rulebaseURI;
	private URI recognitionRuleFolder;
	private URI editRuleFolder;
	private RuleBase rulebase;
	private RuleBasePotentialDependencyAnalyzer ruleBasePotentialDependencyAnalyzer;
	private Set<RecognitionRule> newRecognitionRules;
	
	/**
	 * Initializes a (new or existing) rulebase.
	 * 
	 * @param rulebaseURI
	 *            The path of the rulebase.
	 * @param recognitionRuleFolder
	 *            The folder of the generated Recognition-Rules.
	 * @param resolveRules
	 *            Resolve all (Henshin) Edit- and Recognition-Rules.
	 */
	public RuleBaseWrapper(URI rulebaseURI, URI recognitionRuleFolder, URI editRuleFolder, boolean resolveRules) {
		this.rulebaseURI = rulebaseURI;
		this.recognitionRuleFolder = recognitionRuleFolder;
		this.editRuleFolder = editRuleFolder;
		
		if (exists(rulebaseURI)) {
			// Load existing rule base
			URIMappingRegistryImpl.INSTANCE.getURI(rulebaseURI);
			rulebase = (RuleBase) EMFStorage.eLoad(rulebaseURI);
			
			if (resolveRules) {
				EcoreUtil.resolveAll(rulebase);
			}
		} else {
			// Create new rule base
			rulebase = RulebaseFactory.eINSTANCE.createRuleBase();			
		}
		
		ruleBasePotentialDependencyAnalyzer = new RuleBasePotentialDependencyAnalyzer(rulebase);
		
		newRecognitionRules = new HashSet<RecognitionRule>();
		resetDirtyFlag();
	}

	/**
	 * Initializes a (new or existing) rulebase.
	 * 
	 * @param rulebaseURI
	 *            The path of the rulebase.
	 * @param recognitionRuleFolder
	 *            The folder of the generated Recognition-Rules.
	 */
	public RuleBaseWrapper(URI rulebaseURI, URI recognitionRuleFolder, URI editRuleFolder) {
		// Load existing rule base
		this(rulebaseURI, recognitionRuleFolder, editRuleFolder, true);
	}
	
	public static boolean exists(URI rulebaseURI) {
		return EMFStorage.uriToFile(rulebaseURI).exists();
	}

	public void saveRuleBase() throws IOException {
		saveRecognitionModules(recognitionRuleFolder);
		
		if (exists(rulebaseURI)) {
			EMFStorage.eSave(rulebase);
		}  else {
			EMFStorage.eSaveAs(rulebaseURI, rulebase);
		}
		
		resetDirtyFlag();
	}

	private void saveRecognitionModules(URI recognitionRuleFolder) {
		for (RecognitionRule rr_rule : newRecognitionRules) {
			if (rr_rule.getRecognitionModule().eResource() != null) {
				// Existing recognition rule
				EMFStorage.eSave(rr_rule.getRecognitionModule());
			} else {
				// New recognition rule
				Edit2RecognitionUtil.saveRecognitionRule(
						rr_rule.getRecognitionModule(),
						rr_rule.getEditRule().getExecuteModule(),
						getSaveURI(rr_rule.getEditRule().getExecuteModule()));
			}
		}
	}

	public void startObserverNotification() {
		if (!notifyChangedStarted) {
			// Notify me if data model has changed:
			notifyChangedAdapter = new EContentAdapter() {
				@Override
				public void notifyChanged(Notification notification) {
					super.notifyChanged(notification);

					// Event Filter:
					if (!(notification.getEventType() == Notification.REMOVING_ADAPTER)
							&& !(notification.getEventType() == Notification.RESOLVE)) {

						// Priority or name of recognition Module changed
						if (notification.getNotifier() instanceof Attribute) {
							Attribute attribute = (Attribute) notification.getNotifier();

							Module recognitionModule = attribute.getNode().getGraph().getRule()
									.getModule();
							newRecognitionRules.add(findRecognitionRule(recognitionModule));
						}

						// Set dirty
						setDirtyFlag();

						// Notify observers
						setChanged();
						notifyObservers();
					}
				}
			};

			// Register notify changed adapter
			rulebase.eAdapters().add(notifyChangedAdapter);

			for (RuleBaseItem item : getItems()) {
				addNotifyChangedAdapterToItem(item);
			}

			notifyChangedStarted = true;
		}
	}

	private void addNotifyChangedAdapterToItem(RuleBaseItem item) {
		item.getRecognitionRule().getRecognitionModule().eAdapters().add(notifyChangedAdapter);
		item.getEditRule().getExecuteModule().eAdapters().add(notifyChangedAdapter);
	}

	public void stopObserverNotification() {
		// Unregister notify changed adapter
		rulebase.eAdapters().remove(notifyChangedAdapter);

		for (RuleBaseItem item : getItems()) {
			removeNotifyChangedAdapterFromItem(item);
		}

		notifyChangedStarted = false;
	}

	private void removeNotifyChangedAdapterFromItem(RuleBaseItem item) {
		item.getRecognitionRule().getRecognitionModule().eAdapters().remove(notifyChangedAdapter);
		item.getEditRule().getExecuteModule().eAdapters().remove(notifyChangedAdapter);
	}

	public URI getRuleBaseLocation() {
		return rulebaseURI;
	}

	public RuleBase getRuleBase() {
		return rulebase;
	}
	
	public RecognitionRule findRecognitionRule(Module recognitionModule) {
		for (RecognitionRule rr_rule : rulebase.getRecognitionRules()) {
			if (recognitionModule == rr_rule.getRecognitionModule()) {
				return rr_rule;
			}
		}
		return null;
	}
	
	public EditRule findEditRule(Module editModule) {
		for (EditRule er_rule : rulebase.getEditRules()) {
			if (editModule == er_rule.getExecuteModule()) {
				return er_rule;
			}
		}
		return null;
	}
	
	public EditRule findEditRule(String editRulePath) {
		File editRuleFile = new File(editRulePath);
		
		for (EditRule er_rule : rulebase.getEditRules()) {
			URI rbEditRuleURI = EcoreUtil.getURI(er_rule.getExecuteMainUnit()).trimFragment();
			File rbEditRuleFile = EMFStorage.uriToFile(rbEditRuleURI);

			if (editRuleFile.equals(rbEditRuleFile)) {
				return er_rule;
			}
		}
		return null;
	}

	public EList<RuleBaseItem> getItems() {
		return rulebase.getItems();
	}

	public void addItem(RuleBaseItem item) {
		// Add item to rule base
		rulebase.getItems().add(item);

		// Add document type (if it is not already added).
		addDocumentType(item.getEditRule());

		// Find new potential dependencies in the rule base
		ruleBasePotentialDependencyAnalyzer.findDependencies(item.getEditRule());
	
		// Extract formal parameters of the edit rule
		ParameterExtractor paramExtractor = new ParameterExtractor(item.getEditRule());
		paramExtractor.extractParameters();
	}

	public void addItem(int position, RuleBaseItem item) {
		// Add item to rule base at specified position
		rulebase.getItems().add(position, item);

		// Add document type (if it is not already added).
		addDocumentType(item.getEditRule());
		
		// Find new potential dependencies in the rule base
		ruleBasePotentialDependencyAnalyzer.findDependencies(item.getEditRule());

		// Extract formal parameters of the edit rule
		ParameterExtractor paramExtractor = new ParameterExtractor(item.getEditRule());
		paramExtractor.extractParameters();
		
		// Add notification adapter to Module
		if (notifyChangedStarted) {
			addNotifyChangedAdapterToItem(item);
		}
	}

	public void removeItem(RuleBaseItem item, boolean removeRecognRuleFile) {

		// Remove potential dependencies of the item:
		for (PotentialDependency potDep : getIncomingDependencies(item.getEditRule())) {
			EcoreUtil.remove(potDep);
		}

		// Remove notification adapter from Item
		if (notifyChangedStarted) {
			removeNotifyChangedAdapterFromItem(item);
		}

		// Remove item from rule base
		EcoreUtil.remove(item);

		// Remove from maybe refreshed
		newRecognitionRules.remove(item.getRecognitionRule());

		// Remove recognition rule from file system
		if (removeRecognRuleFile) {
			URI recognitionRuleURI = EcoreUtil.getURI(item.getRecognitionRule().getRecognitionMainUnit()).trimFragment();
			File recognitionRuleFile = EMFStorage.uriToFile(recognitionRuleURI);
			recognitionRuleFile.delete();
		}
	}

	public void clean(boolean removeRecognRuleFile) {
		while (!rulebase.getItems().isEmpty()) {
			removeItem(rulebase.getItems().get(0), removeRecognRuleFile);
		}
	}

	public List<PotentialDependency> getIncomingDependencies(EditRule er) {
		List<PotentialDependency> potDeps = new ArrayList<PotentialDependency>();
		
		// Nodes
		for (PotentialDependency potDep : rulebase.getPotentialNodeDependencies()) {
			if ((potDep.getSourceRule() == er) || (potDep.getTargetRule() == er)) {
				potDeps.add(potDep);
			}
		}

		// Edeges
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

	public void generateItemFromFile(URI editRule) throws Edit2RecognitionException {

		EditWrapper2RecognitionWrapper generator = null;
		
		try {
			// Generate new rule base item from given Module
			generator = new EditWrapper2RecognitionWrapper(editRule);
			RuleBaseItem newItem = generator.transform();

			// Add new refreshed item to rule base
			addItem(newItem);
			
			// Remember new recognition rules
			newRecognitionRules.add(newItem.getRecognitionRule());
		} catch (UnsupportedTransformationSytemException e) {
			// Error: UnsupportedTransformationSytem
			throw new Edit2RecognitionException(e, new Status(IStatus.ERROR, org.sidiff.difference.lifting.edit2recognition.Activator.PLUGIN_ID, e.getMessage() + "\n\n" + EMFStorage.uriToPath(editRule)));
		} catch (NoUnitFoundException e) {
			// Error: NoUnitFound
			throw new Edit2RecognitionException(e, new Status(IStatus.ERROR, org.sidiff.difference.lifting.edit2recognition.Activator.PLUGIN_ID, e.getMessage() + "\n\n" + EMFStorage.uriToPath(editRule)));
		} catch (NoMainUnitFoundException e) {
			// Error: NoMainUnitFoundException
			throw new Edit2RecognitionException(e, new Status(IStatus.ERROR, org.sidiff.difference.lifting.edit2recognition.Activator.PLUGIN_ID, e.getMessage() + "\n\n" + EMFStorage.uriToPath(editRule)));
		} catch (UnsupportedApplicationConditionException e) {
			// Error: UnsupportedApplicationConditionException
			throw new Edit2RecognitionException(e, new Status(IStatus.ERROR, org.sidiff.difference.lifting.edit2recognition.Activator.PLUGIN_ID, e.getMessage() + "\n\n" + EMFStorage.uriToPath(editRule)));
		} catch (NoRecognizableChangesInEditRule e) {
			// Error: NoRecognizableChangesInEditRule
			throw new Edit2RecognitionException(e, new Status(IStatus.ERROR, org.sidiff.difference.lifting.edit2recognition.Activator.PLUGIN_ID, e.getMessage() + "\n\n" + EMFStorage.uriToPath(editRule)));
		}
	}

	public void refreshItem(RuleBaseItem oldItem) throws Edit2RecognitionException {

		EditWrapper2RecognitionWrapper generator = null;
		
		try {
			// Reload edit rule resource:
			EMFStorage.eReload(oldItem.getEditRule().getExecuteModule());
			
			// Regenerate recognition rule:
			generator = new EditWrapper2RecognitionWrapper(oldItem.getEditRule());
			RuleBaseItem newItem = generator.transform();

			// Increase rule base item revision:
			newItem.setVersion(oldItem.getVersion());
			increaseVersion(newItem, 2);

			// Keep SCS name:
			String itemName = RuleBaseItemWrapper.getName(oldItem);
			RuleBaseItemWrapper.setName(newItem, itemName);

			// Keep SCS Priority:
			int itemPriority = RuleBaseItemWrapper.getPriority(oldItem);
			RuleBaseItemWrapper.setPriority(newItem, itemPriority);

			// Replace old rule base item in rulebase:
			int ruleBaseItemIndex = rulebase.getItems().indexOf(oldItem);
			removeItem(oldItem, false);
			addItem(ruleBaseItemIndex, newItem);
			
			// Update recognition rule resource:
			RecognitionRule oldRecognitionRule = oldItem.getRecognitionRule();
			RecognitionRule newRecognitionRule = newItem.getRecognitionRule();
			EMFStorage.eOverwrite(oldRecognitionRule.getRecognitionModule(), newRecognitionRule.getRecognitionModule());

			// Remember refreshed recognition rules:
			newRecognitionRules.remove(oldRecognitionRule);
			newRecognitionRules.add(newRecognitionRule);
			
		} catch (UnsupportedTransformationSytemException e) {
			// Error: UnsupportedTransformationSytem
			throw new Edit2RecognitionException(e, new Status(IStatus.ERROR, org.sidiff.difference.lifting.edit2recognition.Activator.PLUGIN_ID,  e.getMessage() + "\n\n" + oldItem.getEditRule().getExecuteMainUnit().eResource().getURI()));
		} catch (NoUnitFoundException e) {
			// Error: NoUnitFound
			throw new Edit2RecognitionException(e, new Status(IStatus.ERROR, org.sidiff.difference.lifting.edit2recognition.Activator.PLUGIN_ID,  e.getMessage() + "\n\n" + oldItem.getEditRule().getExecuteMainUnit().eResource().getURI()));
		} catch (NoMainUnitFoundException e) {
			// Error: NoMainUnitFoundException
			throw new Edit2RecognitionException(e, new Status(IStatus.ERROR, org.sidiff.difference.lifting.edit2recognition.Activator.PLUGIN_ID,  e.getMessage() + "\n\n" + oldItem.getEditRule().getExecuteMainUnit().eResource().getURI()));
		} catch (UnsupportedApplicationConditionException e) {
			// Error: UnsupportedApplicationConditionException
			throw new Edit2RecognitionException(e, new Status(IStatus.ERROR, org.sidiff.difference.lifting.edit2recognition.Activator.PLUGIN_ID, e.getMessage() + "\n\n" + oldItem.getEditRule().getExecuteMainUnit().eResource().getURI()));
		} catch (NoRecognizableChangesInEditRule e) {
			// Error: NoRecognizableChangesInEditRule
			throw new Edit2RecognitionException(e, new Status(IStatus.ERROR, org.sidiff.difference.lifting.edit2recognition.Activator.PLUGIN_ID, e.getMessage() + "\n\n" + oldItem.getEditRule().getExecuteMainUnit().eResource().getURI()));
		}
	}

	public void refreshItems(Collection<RuleBaseItem> items) throws Edit2RecognitionException {

		// Regenerate items
		for (RuleBaseItem ruleBaseItem : items) {
			refreshItem(ruleBaseItem);
		}
	}

	public void invertAllItemsActivity() {
		for (RuleBaseItem item : getItems()) {
			invertActivity(item);
		}
	}

	public boolean isDirty() {
		return dirty;
	}

	private void setDirtyFlag() {
		dirty = true;
	}

	private void resetDirtyFlag() {
		dirty = false;
	}

	public String getCharacteristicDocumentType() {
		return rulebase.getCharacteristicDocumentType();
	}


	public String getName() {
		return rulebase.getName();
	}

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
	
	public static String findCharacteristicDocumentType(Module system) {
		Set<String> documentTypes = new HashSet<String>();
		
		List<EPackage> imports = system.getImports();		
		for (EPackage eImport : imports) {
			EPackage pkg = eImport;
			while (pkg != null && pkg.getESuperPackage() != null) {
				pkg = pkg.getESuperPackage();
			}
			documentTypes.add(pkg.getNsURI());
		}
		
		return EMFModelAccessEx.getCharacteristicDocumentType(documentTypes);
	}
	
	public static Set<String> findDocumentTypes(Module system) {
		Set<String> documentTypes = new HashSet<String>();
		
		List<EPackage> imports = system.getImports();		
		for (EPackage eImport : imports) {
			EPackage pkg = eImport;
			while (pkg != null && pkg.getESuperPackage() != null) {
				pkg = pkg.getESuperPackage();
			}
			documentTypes.add(pkg.getNsURI());
		}
		
		return documentTypes;
	}
	
	/**
	 * Helper method to get corresponding output save URI
	 * for given EditRule. This is necessary if there is some
	 * subfolder structure beneath the recognitionRuleFolder.
	 * 
	 * 
	 * @param editModule
	 * @return
	 */
	private URI getSaveURI(Module editModule){
		
		//Only change URI if EditRule folder has been defined
		if(editRuleFolder == null){
			return recognitionRuleFolder;
		}
		
		// Replace SOURCE(ER) with BUILD(RR) to
		// keep folder structure
		String editRuleFolderString = editRuleFolder.lastSegment();
		String recognitionRuleFolderString = recognitionRuleFolder.lastSegment();
		
		String savePath = EMFStorage.uriToPath(editModule.eResource().getURI());
		
		//Get URI without filename
		savePath = savePath.substring(0,savePath.lastIndexOf(File.separator));
		//Replace EditRuleFolder with RecognitionRuleFolder
		savePath = savePath.replace(editRuleFolderString, recognitionRuleFolderString);
		
		return EMFStorage.pathToUri(savePath);
		
	}

	public URI getRecognitionRuleFolder() {
		return recognitionRuleFolder;
	}
	
	public URI getEditRuleFolder() {
		return editRuleFolder;
	}
}
