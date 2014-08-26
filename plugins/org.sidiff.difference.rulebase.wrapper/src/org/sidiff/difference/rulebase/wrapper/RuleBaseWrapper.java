package org.sidiff.difference.rulebase.wrapper;

import static org.sidiff.difference.rulebase.wrapper.RuleBaseItemInfo.invertActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
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
 * 
 * @author Manuel Ohrndorf
 */
public class RuleBaseWrapper extends Observable {

	/**
	 * Logs the observer notification state. 
	 */
	private boolean notifyChangedStarted;
	
	/**
	 * Internal Ecore notification adapter.
	 */
	private EContentAdapter notifyChangedAdapter;
	
	/**
	 * <code>true</code> if the Rulebase was edited; <code>false</code> otherwise.
	 */
	private boolean dirty;
	
	/**
	 * The path of the *.rulebase file. 
	 */
	private URI rulebaseURI;
	
	/**
	 * The folder to store the Recognition-Rules.
	 */
	private URI recognitionRuleFolder;
	
	/**
	 * The folder of the corresponding Edit-Rules. 
	 */
	private URI editRuleFolder;
	
	/**
	 * The (Ecore model) rulebase root element. 
	 */
	private RuleBase rulebase;
	
	/**
	 * Internal {@link RuleBasePotentialDependencyAnalyzer} 
	 */
	private RuleBasePotentialDependencyAnalyzer ruleBasePotentialDependencyAnalyzer;
	
	/**
	 * List of edited/new (Henshin) Recognition-Rules. Used to delay the storage of the Henshin
	 * files. Call {@link RuleBaseWrapper#saveRuleBase()} to save all.
	 */
	private Set<RecognitionRule> newRecognitionRules;
	
	/**
	 * List of edited (Henshin) Edit-Rules. Used to delay the storage of the Henshin
	 * files. Call {@link RuleBaseWrapper#saveRuleBase()} to save all.
	 */
	private Set<EditRule> changedEditRules;
	
	/**
	 * Initializes a (new or existing) rulebase.
	 * 
	 * @param rulebaseURI
	 *            The path of the rulebase.
	 * @param recognitionRuleFolder
	 *            The folder of the generated Recognition-Rules.
	 * @param editRuleFolder
	 *            The folder of the corresponding Edit-Rules.
	 * @param resolveRules
	 *            Resolve all (Henshin) Edit- and Recognition-Rules.
	 */
	public RuleBaseWrapper(URI rulebaseURI, URI recognitionRuleFolder, URI editRuleFolder, boolean resolveRules) {
		this.rulebaseURI = rulebaseURI;

		if (exists(rulebaseURI)) {
			// Load existing rule base
			URIMappingRegistryImpl.INSTANCE.getURI(rulebaseURI);
			rulebase = (RuleBase) EMFStorage.eLoad(rulebaseURI);
			
			if (resolveRules) {
				EcoreUtil.resolveAll(rulebase);
			}
			
			this.recognitionRuleFolder = URI.createURI(rulebase.getRecognitionRuleFolder());
			this.editRuleFolder = URI.createURI(rulebase.getEditRuleFolder());
		} else {
			// Create new rule base
			assert (recognitionRuleFolder != null) : "Assert not Null: Recognition-Rule folder!"; 
			assert (editRuleFolder != null) : "Assert not Null: Edit-Rule folder!"; 
			
			rulebase = RulebaseFactory.eINSTANCE.createRuleBase();
			rulebase.setRecognitionRuleFolder(recognitionRuleFolder.toString());
			rulebase.setEditRuleFolder(editRuleFolder.toString());
			
			this.recognitionRuleFolder = recognitionRuleFolder;
			this.editRuleFolder = editRuleFolder;
		}

		ruleBasePotentialDependencyAnalyzer = new RuleBasePotentialDependencyAnalyzer(rulebase);
		newRecognitionRules = new HashSet<RecognitionRule>();
		changedEditRules = new HashSet<EditRule>();
		resetDirtyFlag();
	}

	/**
	 * Initializes a (new or existing) rulebase.
	 * 
	 * @param rulebaseURI
	 *            The path of the rulebase.
	 * @param recognitionRuleFolder
	 *            The folder of the generated Recognition-Rules.
	 * @param editRuleFolder
	 *            The folder of the corresponding Edit-Rules.
	 */
	public RuleBaseWrapper(URI rulebaseURI, URI recognitionRuleFolder, URI editRuleFolder) {
		// Load existing rule base
		this(rulebaseURI, recognitionRuleFolder, editRuleFolder, true);
	}
	
	/**
	 * Initializes an existing rulebase.
	 * 
	 * @param rulebaseURI
	 *            The path of the rulebase.
	 * @see RuleBaseWrapper#saveRuleBase()
	 */
	public RuleBaseWrapper(URI rulebaseURI) {
		this(rulebaseURI, null, null);
	}
	
	/**
	 * Checks if the given rulebase file exists.
	 * 
	 * @param rulebaseURI
	 *            The rulebase file to test.
	 * @return <code>true</code> if the rulebase file exists; <code>false</code> otherwise.
	 */
	public static boolean exists(URI rulebaseURI) {
		return EMFStorage.uriToFile(rulebaseURI).exists();
	}

	/**
	 * Saves the rulebase file and all changed Recognition-Rules. Can't be used if the rulebase was
	 * initialized in read-only mode ({@link RuleBaseWrapper#RuleBaseWrapper(URI)}).
	 * 
	 * @throws IOException
	 */
	public void saveRuleBase() throws IOException {
		saveEditModules();
		saveRecognitionModules();
		
		if (exists(rulebaseURI)) {
			EMFStorage.eSave(rulebase);
		}  else {
			EMFStorage.eSaveAs(rulebaseURI, rulebase);
		}
		
		resetDirtyFlag();
	}

	/**
	 * Saves all (Henshin) changed Edit-Rules.
	 */
	private void saveEditModules() {
		
		for (EditRule erRule : changedEditRules) {
			
			if (erRule.getExecuteMainUnit().eResource() != null) {
				// Existing edit rule:
				EMFStorage.eSave(erRule.getExecuteModule());
			} else {
				throw new RuntimeException("Missing Edit-Rule: " + erRule);
			}
		}
		
		changedEditRules.clear();
	}

	/**
	 * Saves all (Henshin) new/changed Recognition-Rules.
	 */
	private void saveRecognitionModules() {
		
		for (RecognitionRule rrRule : newRecognitionRules) {
			
			if (rrRule.getRecognitionMainUnit().eResource() != null) {
				// Existing recognition rule:
				EMFStorage.eSave(rrRule.getRecognitionModule());
			} else {
				// New recognition rule:
				Edit2RecognitionUtil.saveRecognitionRule(
						rrRule.getRecognitionModule(),
						rrRule.getEditRule().getExecuteModule(),
						getRecognitionRuleSaveURI(rrRule.getEditRule().getExecuteModule()));
			}
		}
		
		newRecognitionRules.clear();
	}

	/**
	 * Helper method to get corresponding output save URI for given EditRule. This is necessary if
	 * there is some subfolder structure beneath the recognitionRuleFolder.
	 * 
	 * 
	 * @param editModule
	 *            The corresponding Edit-Rule.
	 * @return The Recognition-Rule save path.
	 */
	private URI getRecognitionRuleSaveURI(Module editModule){
		
		// Replace Edit-Rule with Recognition-Rule to keep folder structure:
		String editRuleFolderString = editRuleFolder.lastSegment();
		String recognitionRuleFolderString = recognitionRuleFolder.lastSegment();
		
		String savePath = EMFStorage.uriToPath(editModule.eResource().getURI());
		
		// Get URI without filename
		savePath = savePath.substring(0, savePath.lastIndexOf(File.separator));
		
		// Replace EditRuleFolder with RecognitionRuleFolder
		savePath = savePath.replace(
				File.separator + editRuleFolderString, File.separator + recognitionRuleFolderString);
		
		return EMFStorage.pathToUri(savePath);
	}

	/**
	 * Log all rulebase (rulebase file, Henshin Edit- / Recognition-Rules) changes as from now.
	 * Notifies the observers and sets the dirty flag for occurring changes.
	 */
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

						// Priority or name of edit module changed:
						if (notification.getNotifier() instanceof Module) {
							Module editModule = (Module) notification.getNotifier();
							changedEditRules.add(findEditRule(editModule));
						}
						
						// Active flag of Main-Unit has changed:
						if (notification.getNotifier() instanceof Unit) {
							Unit mainUnit = (Unit) notification.getNotifier();
							changedEditRules.add(findEditRule(mainUnit.getModule()));
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

	/**
	 * Logs the changes of the (Henshin) Edit- / Recognition-Rules.
	 * 
	 * @param item
	 *            The item to log.
	 */
	private void addNotifyChangedAdapterToItem(RuleBaseItem item) {
		item.getRecognitionRule().getRecognitionModule().eAdapters().add(notifyChangedAdapter);
		item.getEditRule().getExecuteModule().eAdapters().add(notifyChangedAdapter);
	}

	/**
	 * Stop change logging.
	 */
	public void stopObserverNotification() {
		// Unregister notify changed adapter
		rulebase.eAdapters().remove(notifyChangedAdapter);

		for (RuleBaseItem item : getItems()) {
			removeNotifyChangedAdapterFromItem(item);
		}

		notifyChangedStarted = false;
	}

	/**
	 * Stop logging of the (Henshin) Edit- / Recognition-Rules.
	 * 
	 * @param item
	 *            The item to log.
	 */
	private void removeNotifyChangedAdapterFromItem(RuleBaseItem item) {
		item.getRecognitionRule().getRecognitionModule().eAdapters().remove(notifyChangedAdapter);
		item.getEditRule().getExecuteModule().eAdapters().remove(notifyChangedAdapter);
	}

	/**
	 * @return All list containing all items (Item = Edit- / Recognition-Rule) of this rulebase.
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

		// Extract formal parameters of the edit rule
		ParameterExtractor paramExtractor = new ParameterExtractor(item.getEditRule());
		paramExtractor.extractParameters();
		
		// Add notification adapter to Module
		if (notifyChangedStarted) {
			addNotifyChangedAdapterToItem(item);
		}
	}

	/**
	 * Removes an rulebase item.
	 * 
	 * @param item
	 *            The item to remove.
	 * @param removeRecognRuleFile
	 *            <code>true</code> will also delete the corresponding Henshin file; Set to
	 *            <code>false</code> to keep the file.
	 */
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
			
			//Delete parent folder if it is empty now
			File parentFolder =  recognitionRuleFile.getParentFile();			
			if(parentFolder.listFiles() == null || parentFolder.listFiles().length == 0){
				parentFolder.delete();					
			}
		}
	}

	/**
	 * Removes all items from the rulebase.
	 * 
	 * @param removeRecognRuleFiles
	 *            <code>true</code> will also delete the corresponding Henshin files; Set to
	 *            <code>false</code> to keep the files.
	 */
	public void clean(boolean removeRecognRuleFiles) {
		while (!rulebase.getItems().isEmpty()) {
			removeItem(rulebase.getItems().get(0), removeRecognRuleFiles);
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
	 * Generates a new Recognition-Rule and adds a new rulebase item to the rulebase.
	 * 
	 * @param editRule
	 *            The input Edit-Rule.
	 * @throws Edit2RecognitionException
	 */
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

	/**
	 * Inverts the active flag of all rulebase items.
	 */
	public void invertAllItemsActivity() {
		for (RuleBaseItem item : getItems()) {
			invertActivity(item);
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
	 * Needs an Observer!
	 * 
	 * @return The dirty flag which indicated if something of the rulebase or some of the rules
	 *         (Henshin Edit- / Recognition-Rules) have been changed.
	 * @see RuleBaseWrapper#saveRuleBase()
	 * @see RuleBaseWrapper#addObserver(java.util.Observer)
	 * @see RuleBaseWrapper#startObserverNotification()
	 */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * Internally sets the dirty flag.
	 */
	private void setDirtyFlag() {
		dirty = true;
	}

	/**
	 * Internally resets the dirty flag.
	 */
	private void resetDirtyFlag() {
		dirty = false;
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
	 * @return The most characteristic document type of the rulebase.
	 */
	public String getCharacteristicDocumentType() {
		return rulebase.getCharacteristicDocumentType();
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
			documentTypes.add(pkg.getNsURI());
		}
		
		return EMFModelAccessEx.getCharacteristicDocumentType(documentTypes);
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
			documentTypes.add(pkg.getNsURI());
		}
		
		return documentTypes;
	}
	
	/**
	 * @return The target folder where the Recognition-Rules are stored.
	 */
	public URI getRecognitionRuleFolder() {
		return recognitionRuleFolder;
	}
	
	/**
	 * @return The Edit-Rule source folder. (We support substructuring.)
	 */
	public URI getEditRuleFolder() {
		return editRuleFolder;
	}
	
	/**
	 * Searches an Recognition-Rule rulebase wrapper by its corresponding Henshin Recognition-Rule.
	 * 
	 * @param recognitionModule
	 *            The Henshin Recognition-Rule.
	 * @return The corresponding Recognition-Rule rulebase wrapper.
	 */
	public RecognitionRule findRecognitionRule(Module recognitionModule) {
		for (RecognitionRule rr_rule : rulebase.getRecognitionRules()) {
			if (recognitionModule == rr_rule.getRecognitionModule()) {
				return rr_rule;
			}
		}
		return null;
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
			File rbEditRuleFile = EMFStorage.uriToFile(rbEditRuleURI);

			if (editRuleFile.equals(rbEditRuleFile)) {
				return er_rule;
			}
		}
		return null;
	}
}
