package org.sidiff.vcmsintegration.preferences.patching.interfaces;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl.EObjectOutputStream.Check;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.conflicts.modifieddetector.util.ModifiedDetectorUtil;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.sidiff.patching.transformation.TransformationEngineUtil;
import org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab;
import org.sidiff.vcmsintegration.preferences.fieldeditors.CheckBoxPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.ValueAndLabelProvider;

/**
 * Abstract class for the domain specific {@link org.sidiff.patching.settings.PatchingSettings}
 * @author Daniel Roedder
 * 
 */
public abstract class AbstractSiDiffDomainPatchingPreferenceTab implements ISiDiffDomainPreferenceTab {

	/**
	 * List to hold all {@link org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField}
	 */
	private ArrayList<PreferenceField> fieldList;
	
	/**
	 * The {@link RadioBoxPreferenceField} for the transformation Engine setting
	 */
	private RadioBoxPreferenceField transformationEngineField;
	
	/**
	 * The {@link RadioBoxPreferenceField} for the modified detector setting
	 */
	private RadioBoxPreferenceField modifiedDetectorField;
	
	/**
	 * The {@link CheckBoxPreferenceField} for the use interactive patching setting
	 */
	private CheckBoxPreferenceField useInteractivePatching;
	
	/**
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Patching";
	}

	/**
	 * Superclass method, not needed here
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();
		
		Set<ITransformationEngine> engines = TransformationEngineUtil.getAvailableTransformationEngines(getDocumentType());
		transformationEngineField = RadioBoxPreferenceField.createFromIterable(getDocumentType().concat("transformationEngine"), "Transformation Engine", engines, new ValueAndLabelProvider<ITransformationEngine>() {
			@Override public String[] get(ITransformationEngine input) {return new String[] {input.getKey(), input.getName()};}
		});
		
		fieldList.add(transformationEngineField);
		
		Set<IModifiedDetector> detectors = ModifiedDetectorUtil.getAvailableModifiedDetectors(getDocumentType());
		modifiedDetectorField = RadioBoxPreferenceField.createFromIterable(getDocumentType().concat("modifiedDetector"), "Modified Detector", detectors, new ValueAndLabelProvider<IModifiedDetector>() {
			@Override public String[] get(IModifiedDetector input) {return new String[] {input.getKey(), input.getName()};}
		});
		
		
		
		fieldList.add(modifiedDetectorField);
		
		
		useInteractivePatching = new CheckBoxPreferenceField("useInteractivePatching", "Use interactive patching");
		fieldList.add(useInteractivePatching);
		
		return fieldList;
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getDocumentType()
	 */
	@Override
	public abstract String getDocumentType();

}
