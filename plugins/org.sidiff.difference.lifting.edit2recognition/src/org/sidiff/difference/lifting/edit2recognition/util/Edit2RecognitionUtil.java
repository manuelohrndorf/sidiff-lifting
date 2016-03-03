package org.sidiff.difference.lifting.edit2recognition.util;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.henshin.INamingConventions;
import org.sidiff.common.henshin.exceptions.NoMainUnitFoundException;

/**
 * Some convenience functions to handle edit- and recognition-rules. 
 * 
 * @author Manuel Ohrndorf
 */
public class Edit2RecognitionUtil {
	
	/**
	 * Searches the main unit of the edit rule.
	 * 
	 * @param editModule
	 *            The edit rule to search.
	 * @return The main unit of the edit rule.
	 * 
	 * @throws NoMainUnitFoundException
	 * @throws NoUnitFoundException
	 */
	public static Unit findExecuteMainUnit(Module editModule) throws NoMainUnitFoundException {
		
		// Needed at least one transformation unit to perform generation
		if (editModule.getUnits().isEmpty()) {
			throw new NoMainUnitFoundException(editModule);
		}
		
		// Search for unit with name mainUnit
		Unit executeMainUnit = editModule.getUnit(INamingConventions.MAIN_UNIT);
		
		if (executeMainUnit == null) {
			throw new NoMainUnitFoundException(editModule);
		}
		
		return executeMainUnit;
	}
	
	/**
	 * Loads the given edit rule.
	 * 
	 * @param editRuleURI
	 *            The URI of the serialized edit rule.
	 * @return The module containing the edit rule.
	 */
	public static Module loadEditRule(URI editRuleURI) {
		// Load edit transformation system
		return (Module) EMFStorage.eLoad(editRuleURI);
	}
	
	/**
	 * Saves the given recognition rule (using UUIDs).
	 * 
	 * @param recognitionRule
	 *            The recognition rule to save.
	 * @param editModule
	 *            The edit rule (for the save path).
	 * @param tgtDir
	 *            The target directory of the recognition rule.
	 */
	public static void saveRecognitionRule(Module recognitionModule, Module editModule, URI tgtDir) {
		
		// Edit-Rule URI
		URI editRuleURI = EcoreUtil.getURI(editModule);
		
		// Save the recognition transformation system to file
		saveFile(URI.createURI(
				tgtDir.toString() + "/"
						+ TransformationConstants.FILE_PREFIX + editRuleURI.lastSegment()),
				recognitionModule);
	}
	
	/**
	 * Save henshin file by using UUIDs for serialization. UUIDs are necessary when trying to
	 * initialize a henshin diagram.
	 * 
	 * @param filename
	 *            Target filename where to write the henshin file.
	 * @param root
	 *            Root of the data structure. In henshin normally the TransformationSystem.
	 */
	private static void saveFile(URI uri, EObject root) {
		
		Resource resource = new UUIDXmiResourceImpl(uri);
		resource.getContents().add(root);
		try {
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This class represents an XMIResource that uses UUIDs.
	 */
	private static class UUIDXmiResourceImpl extends XMIResourceImpl {
		
		/**
		 * Constructor for UUIDXmiResourceImpl
		 * 
		 * @param uri
		 *            Set the Uniform Resource Identifier
		 */
		public UUIDXmiResourceImpl(URI uri) {
			super(uri);
		}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#useUUIDs()
		 */
		@Override
		protected boolean useUUIDs() {
			return true;
		}
	}
}
