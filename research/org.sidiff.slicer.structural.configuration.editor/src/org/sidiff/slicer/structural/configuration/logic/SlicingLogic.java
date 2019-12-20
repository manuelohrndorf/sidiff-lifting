package org.sidiff.slicer.structural.configuration.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.slicer.structural.configuration.ConfigurationPackage;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.SlicedEReference;
import org.sidiff.slicer.structural.configuration.preferences.PreferenceConstants;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;
import org.sidiff.slicer.structural.configuration.presentation.IConfigurationEditor;
import org.sidiff.slicer.structural.configuration.util.ConfigurationUtil;
import org.sidiff.slicer.structural.configuration.util.OverridingEReference;

/**
 * Generates a slicingconfig and improves user interactions
 * @author mRakowski, mMinutillo,
 * 
 */
public class SlicingLogic extends AbstractSlicingLogic
{
	/**  maps all references to its alternative elements */
	protected Map<EReference, Set<EObject>> alternativesMap = new HashMap<EReference, Set<EObject>>();

	// saves all sliceable Elements for quicker access
	/** set which contains all sliceable Objects as EObjects */
	protected Set<EObject> allSliceableObjects;
	/** set which contains all sliceable Classes as EClasses */
	protected Set<EClass> allSliceableClasses;
	/** set which contains all sliceable References as EReferences */
	protected Set<EReference> allSliceableReferences;
	/** map of all sliceable classes to a set of all references of that class, including OverridingEReferences */
	protected Map<EClass,Set<EReference>> class2ReferenceMap;

	// used for getDecoratedText to show overriding references
	protected Styler overridingRefStyler = new Styler()
	{
		@Override
		public void applyStyles(TextStyle textStyle)
		{
			textStyle.borderStyle = SWT.BORDER_DOT;
			Color refText = createColor(ConfigurationEditorPlugin.getPlugin().getPreferenceStore().getString(PreferenceConstants.TEXT_COLOR_OVERRIDE));
			textStyle.foreground = refText;
		}
	};

	/**
	 * For referenced Classes:
	 * @author mRakowski
	 */
	enum Multiplicity
	{
		/** not referenced */
		NO,
		/** referenced but no lowerbound */
		LOWERBOUND_0,
		/** referenced with lowerbound greater than 0 */
		LOWERBOUND_1;
	}

	/**
	 * @author mMinutillo
	 * @param configurationEditor the Slicing Configuration Editor
	 * @param modelResource the Model Tree
	 */
	public SlicingLogic(IConfigurationEditor configurationEditor, Resource modelResource)
	{
		super(configurationEditor, modelResource);
		initializeSliceableElements();
		initializeAlternativeElements();
	}

	/**
	 * initializes the sets allSliceableObjects, allSliceableClasses and allSliceableReferences
	 * 
	 * @author rMueller
	 */
	protected void initializeSliceableElements()
	{
		this.allSliceableObjects = ConfigurationUtil.getAllSliceableObjects(modelResource);
		this.allSliceableClasses = new HashSet<EClass>();
		this.allSliceableReferences = new HashSet<EReference>();
		this.class2ReferenceMap = new HashMap<EClass,Set<EReference>>();

		for(EObject curr : allSliceableObjects)
		{
			if(curr instanceof EClass)
			{
				allSliceableClasses.add((EClass)curr);
			}
			else if(curr instanceof EReference)
			{
				allSliceableReferences.add((EReference)curr);

				EClass containingClass = getContainingClass((EReference)curr);
				if(class2ReferenceMap.get(containingClass) == null)
				{
					class2ReferenceMap.put(containingClass, new HashSet<EReference>());
				}
				class2ReferenceMap.get(containingClass).add((EReference)curr);
			}
		}
	}

	/**
	  * Initializes the referenceMap variable. Should only get called once in constructor.
	  * @author mMinutillo
	  */
	protected void initializeAlternativeElements()
	{
		for(EReference ref : allSliceableReferences)
		{
			Set<EObject> alternatives = computeAlternativeElements(ref);
			if(alternatives != null)
			{
				alternativesMap.put(ref, alternatives);
			}
		}
	}

	/**
	 * @author mRakowski, rM�ller
	 * 
	 */
	@Override
	public Set<EObject> checkStateChanged(EObject element, boolean checked)
	{
		Set<EObject> result = new HashSet<EObject>();

		// change configuration if Class
		if(element instanceof EClass)
		{
			EClass clazz = (EClass)element;

			// add or remove it depending on the checked-state
			if(checked)
			{
				// add element to the SlicingConfig
				Command addCmd = AddCommand.create(
						configurationEditor.getEditingDomain(),
						configurationEditor.getConfig(),
						ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES,
						ConfigurationUtil.createSlicedEClass(clazz));
				configurationEditor.executeCommand(addCmd);

				// Performance: only change the checked references of element 
				// with alternative EClasses included
				for(EReference ref : getAllReferencesTo(clazz))
				{
					if(isChecked(ref))
					{
						result.add(ref);
						result.add(getContainingClass(ref));
					}
					
				}
				
				//check if clazz is an alternative. If so refresh reference and containing class
				boolean stop = false;
				for(Map.Entry<EReference, Set<EObject>> entry : alternativesMap.entrySet())
				{
					for(EObject obj : entry.getValue())
					{
						if(obj instanceof EClass && EcoreUtil.equals(obj, clazz))
						{
							result.add(entry.getKey());
							result.add(getContainingClass(entry.getKey()));
							stop = true;
							break;
						}
					}
					if(stop)
					{
						break;
					}
				}
				
				result.add(clazz);
			}
			else
			{
				if(confirmRemoveDialog(clazz))
				{
					SlicedEClass sC = ConfigurationUtil.findSlicedEClass(configurationEditor.getConfig(), clazz);
					if(sC != null)
					{
						// remove element from SlicingConfig - if EClass it also removes child EReferences
						Command remCmd = RemoveCommand.create(
								configurationEditor.getEditingDomain(),
								configurationEditor.getConfig(),
								ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES,
								sC);
						configurationEditor.executeCommand(remCmd);
						return null;
					}
				}
				else
				{
					result.add(clazz); // Refresh the element
				}
			}
		}
		// change configuration if the element is an EReference
		else if(element instanceof EReference)
		{
			EReference reference = (EReference)element;
			SlicedEClass parentClass = ConfigurationUtil.createSlicedEClass(reference);

			// add or remove it depending on the checked-state
			if(checked)
			{
				SlicedEReference slicedReference = ConfigurationUtil.createSlicedEReference(reference);

				// if the parent Class isn't checked add it with the selected Reference
				if(!isChecked(parentClass.getType()))
				{
					parentClass.getSlicedEReferences().add(slicedReference);
					Command addParent = AddCommand.create(
							configurationEditor.getEditingDomain(),
							configurationEditor.getConfig(),
							ConfigurationPackage.SLICING_CONFIGURATION__SLICED_ECLASSES,
							parentClass);
					configurationEditor.executeCommand(addParent);
				}
				else
				{
					SlicedEClass sC = ConfigurationUtil.findSlicedEClass(configurationEditor.getConfig(), parentClass.getType());
					if(sC != null)
					{
						Command addCmd = AddCommand.create(
								configurationEditor.getEditingDomain(),
								sC,
								ConfigurationPackage.SLICED_ECLASS__SLICED_EREFERENCES,
								slicedReference);
						configurationEditor.executeCommand(addCmd);
					}
				}

				result.add(reference);
				result.add(reference.getEReferenceType());
				EClass containingClass = getContainingClass(reference);
				result.add(containingClass);
				for(EReference ref : getAllReferencesTo(containingClass))
				{
					if(isChecked(ref))
					{
						result.add(ref);
						result.add(getContainingClass(ref));
					}
				}

				if(hasAlternativeElements(reference))
				{
					boolean alternativeChecked = false;

					for(EObject alt : getAlternativeElements(reference))
					{
						if(isChecked(alt))
						{
							alternativeChecked = true;
							break;
						}
					}

					if(!alternativeChecked)
					{
						configurationEditor.showAlternativeElements(reference);
					}
				}
			}
			else
			{
				if(confirmRemoveDialog(reference))
				{
					SlicedEReference sR = ConfigurationUtil.findSlicedEReference(configurationEditor.getConfig(), reference);

					if(sR != null)
					{
						SlicedEClass sC = ConfigurationUtil.findSlicedEClass(configurationEditor.getConfig(), parentClass.getType());

						Command remCmd = RemoveCommand.create(
								configurationEditor.getEditingDomain(),
								sC,
								ConfigurationPackage.SLICED_ECLASS__SLICED_EREFERENCES,
								sR);
						configurationEditor.executeCommand(remCmd);
						return null;
					}
				}
				else
				{
					result.add(reference);
				}
			}
		}
		else if(element instanceof EPackage)
		{
			result.add(element);
		}

		return result;
	}

	@Override
	public CheckboxState getCheckboxState(Object element)
	{
		if(element instanceof EPackage)
		{
			return CheckboxState.GRAYED;
		}
		else if(isChecked(element))
		{
			return CheckboxState.CHECKED;
		}

		return CheckboxState.NOT_CHECKED;
	}

	/**
	 * @author mRakowski, mMinutillo, rM�ller
	 */
	@Override
	public Color getForegroundColor(Object object)
	{
		if(object instanceof EClass)
		{
			Multiplicity ref = isReferenced((EClass)object);
			if(ref != Multiplicity.NO && !isChecked(object))
			{
				return createColor(ConfigurationEditorPlugin.getPlugin().getPreferenceStore().getString(PreferenceConstants.TEXT_COLOR_REFERENCED));
			}
		}
		return createColor(ConfigurationEditorPlugin.getPlugin().getPreferenceStore().getString(PreferenceConstants.TEXT_COLOR_DEFAULT));
	}

	/**
	 * @author mRakowski, mMinutillo, rM�ller
	 */
	@Override
	public Color getBackgroundColor(Object object)
	{
		if(object instanceof EReference)
		{
			if(isReferenceDangling((EReference)object))
			{
				return createColor(ConfigurationEditorPlugin.getPlugin().getPreferenceStore().getString(PreferenceConstants.BACKGROUND_COLOR_DANGLING_REFERENCE));
			}
		}
		return null;
	}

	/**
	 * @author mRakowski, rM�ller
	 */
	@Override
	public int getFontStyle(Object object)
	{
		if(object instanceof EClass)
		{
			if(!isChecked(object))
			{
				Multiplicity isRef = isReferenced((EClass)object);
				switch(isRef)
				{
					case NO:
						return ConfigurationEditorPlugin.getPlugin().getPreferenceStore().getInt(PreferenceConstants.TEXT_STYLE_DEFAULT);
					case LOWERBOUND_0:
						return ConfigurationEditorPlugin.getPlugin().getPreferenceStore().getInt(PreferenceConstants.TEXT_STYLE_REFERENCED);
					case LOWERBOUND_1:
						return ConfigurationEditorPlugin.getPlugin().getPreferenceStore().getInt(PreferenceConstants.TEXT_STYLE_REFERENCED_MULTI);
				}
			}
		}
		return ConfigurationEditorPlugin.getPlugin().getPreferenceStore().getInt(PreferenceConstants.TEXT_STYLE_DEFAULT);
	}

	/**
	 * @author mMinutillo, mRakowski, rM�ller
	 */
	@Override
	public ImageDescriptor getDecoratedImage(Image baseImage, Object object)
	{
		ImageDescriptor[] over = new ImageDescriptor[4];
		boolean shouldOverlay = false;

		if(object instanceof EObject && !ConfigurationUtil.getConstraintExpression(configurationEditor.getConfig(), (EObject)object).isEmpty())
		{
			// top-left
			over[0] = ConfigurationEditorPlugin.getPlugin().getImageRegistry().getDescriptor(ConfigurationEditorPlugin.IMAGE_CONSTRAINT);
			shouldOverlay = true;
		}
		if(object instanceof EClass)
		{
			for(EReference ref : getAllReferences((EClass)object))
			{
				if(isReferenceDangling(ref))
				{
					// bottom-left
					over[2] = ConfigurationEditorPlugin.getPlugin().getImageRegistry().getDescriptor(ConfigurationEditorPlugin.IMAGE_DANGLING);
					shouldOverlay = true;
					break;
				}
			}
		}
		if(object instanceof EReference && ((EReference)object).getLowerBound() > 0)
		{
			// bottom-right
			over[1] = ConfigurationEditorPlugin.getPlugin().getImageRegistry().getDescriptor(ConfigurationEditorPlugin.IMAGE_MULTIPLICITY);
			shouldOverlay = true;
		}

		// Sets the Decoration, if the array (over) is empty = no decoration will be added
		return shouldOverlay ? new DecorationOverlayIcon(baseImage, over) : null;
	}

	@Override
	public StyledString getDecoratedText(StyledString originalText, Object object)
	{
		if(object instanceof OverridingEReference)
		{
			return originalText.append(" ").append( //$NON-NLS-1$
					ConfigurationEditorPlugin.getSubstitutedString("_UI_SlicingLogic_Override"), overridingRefStyler); //$NON-NLS-1$
		}
		// no decoration / use original text
		return null;
	}
	@Override
	public String getToolTipText(Object object)
	{
		// TODO: return a tooltip for the element (only if it is useful)
		// the tooltip might explain why an element is colored in a certain way
		// etc.

		// no tooltip
		return null;
	}

	/**
	  * Gets all elements that can be included in the slicing
	  * configuration alternative to the given object. The result should <u>not</u>
	  * contain the object itself. The function may return <code>null</code> if no
	  * alternatives are available.
	  * 
	  * @param object the element
	  * @return set of alternatives, <code>null</code> if non are available
	  * @author mMinutillo
	  */
	@Override
	public Set<EObject> getAlternativeElements(EObject object)
	{
		if(object instanceof EReference)
		{
			if(object instanceof OverridingEReference)
			{
				return alternativesMap.get(((OverridingEReference)object).getDelegate());
			}
			return alternativesMap.get(object);
		}
		return null;
	}

	// #########################################################################################
	// Protected Stuff down here
	/**
	 * @author mRakowski
	 * @param elem is the EObject which got unchecked
	 * @return false if the user didn't confirm the removal
	 */
	protected boolean confirmRemoveDialog(EObject elem)
	{
		// check if user opted out
		if(ConfigurationEditorPlugin.getPlugin().getPreferenceStore().getBoolean(PreferenceConstants.DONT_ASK_AGAIN_CONSTRAINT_REMOVAL))
			return true;

		if(elem instanceof EClass && hasSelfOrChildConstraints((EClass) elem))
		{
			MessageDialogWithToggle dialog = MessageDialogWithToggle.openYesNoQuestion(
					null,
					ConfigurationEditorPlugin.getSubstitutedString("_UI_SlicingLogic_ConfirmRemoval"), //$NON-NLS-1$
					ConfigurationEditorPlugin.getSubstitutedString("_UI_SlicingLogic_ConfirmRemoval_Class", EMFUtil.getEObjectID(elem)), //$NON-NLS-1$
					ConfigurationEditorPlugin.getSubstitutedString("_UI_SlicingLogic_DontAskAgain"), //$NON-NLS-1$
					false,
					null,
					null);

			// Save the Toggle in PreferenceStore
			ConfigurationEditorPlugin.getPlugin().getPreferenceStore().setValue(
					PreferenceConstants.DONT_ASK_AGAIN_CONSTRAINT_REMOVAL, dialog.getToggleState());

			return dialog.getReturnCode() == IDialogConstants.YES_ID; // If the 'yes' button is not pressed don't confirm
		}
		else if(elem instanceof EReference && !ConfigurationUtil.getConstraintExpression(configurationEditor.getConfig(), elem).isEmpty())
		{
			MessageDialogWithToggle dialog =  MessageDialogWithToggle.openYesNoQuestion(
					null,
					ConfigurationEditorPlugin.getSubstitutedString("_UI_SlicingLogic_ConfirmRemoval"), //$NON-NLS-1$
					ConfigurationEditorPlugin.getSubstitutedString("_UI_SlicingLogic_ConfirmRemoval_Reference", EMFUtil.getEObjectID(elem)), //$NON-NLS-1$
					ConfigurationEditorPlugin.getSubstitutedString("_UI_SlicingLogic_DontAskAgain"), //$NON-NLS-1$
					false,
					null,
					null);

			// Save the Toggle in PreferenceStore
			ConfigurationEditorPlugin.getPlugin().getPreferenceStore().setValue(
					PreferenceConstants.DONT_ASK_AGAIN_CONSTRAINT_REMOVAL, dialog.getToggleState());

			return dialog.getReturnCode() == IDialogConstants.YES_ID; // If the 'yes' button is not pressed don't confirm
		}

		return true;
	}

	/**
	 * Searches the SlicingConfiguration for the given Object, calls the
	 * methods
	 * <ul>
	 * <li>{@link ConfigurationUtil#findSlicedEClass}</li>
	 * <li>{@link ConfigurationUtil#findSlicedEReference}</li>
	 * </ul>
	 * 
	 * @author mRakowski
	 * @param element the Object to find
	 * @return true if the Object (element) is in the SlicingConfiguration
	 */
	protected boolean isChecked(Object element)
	{
		// check if EClass is in slicing configuration
		if(element instanceof EClass)
		{
			return ConfigurationUtil.findSlicedEClass(configurationEditor.getConfig(), (EClass)element) != null;
		}
		else if(element instanceof EReference) // check if Reference is in the configuration
		{
			return ConfigurationUtil.findSlicedEReference(configurationEditor.getConfig(), (EReference)element) != null;
		}

		return false;
	}

	/**
	 * Gets called by {@link #getForegroundColor} and {@link #getDecoratedImage},
	 * searches through all EReferences from the modelResource to find a checked
	 * one which refers to the EClass (element)
	 * 
	 * @param element the EClass to check
	 * @return {@link Multiplicity#NO} if the EClass is not referenced <br>
	 * {@link Multiplicity#LOWERBOUND_0} if the EClass has a reference and a
	 * LowerBound == 0 <br>
	 * {@link Multiplicity#LOWERBOUND_1} if the EClass has a reference and a
	 * LowerBound &gt;= 1
	 * @author mRakowski
	 */
	protected Multiplicity isReferenced(EClass element)
	{
		Multiplicity m = Multiplicity.NO;

		for(EReference ref : allSliceableReferences)
		{
			if(ref.getEType() != null && ref.getEType().equals(element) && isChecked(ref))
			{
				if(ref.getLowerBound() >= 1)
				{
					// return immediately as this value won't change anymore
					return Multiplicity.LOWERBOUND_1;
				}
				else
				{
					// do not return immediately, because there might be a different reference with lower bound 1
					m = Multiplicity.LOWERBOUND_0;
				}
			}
		}

		return m;
	}

	/**
	  * Computes alternative elements of a reference.
	  * Only gets called in {@link #initializeAlternativeElements}.
	  *
	  * @author mMinutillo
	  * @param object EObject which contains an EReference
	  * @return alternative elements as a Set of EObjects, <code>null</code> if object in parameter does not contain an EReference.
	  */
	protected Set<EObject> computeAlternativeElements(EObject object)
	{
		if(object instanceof EReference)
		{
			EClass refClass = ((EReference)object).getEReferenceType();
			if(refClass == null)
				return null;
			Set<EObject> result = new HashSet<EObject>();

			for(EClass clazz : allSliceableClasses)
			{
				if(refClass.isSuperTypeOf(clazz))
				{
					result.add(clazz);
				}
			}

			return result;
		}

		return null;
	}

	/**
	  * gets all references to a given Class
	  * 
	  * @author mMinutillo
	  * @param elem the EClass to find all references to
	  * @return Set of all references to a class
	  */
	protected Set<EReference> getAllReferencesTo(EClass elem)
	{
		HashSet<EReference> result = new HashSet<EReference>();

		for(EReference reference : allSliceableReferences)
		{
			if(EcoreUtil.equals(reference.getEReferenceType(), elem))
			{
				result.add(reference);
			}
		}

		return result;
	}

	/**
	 * Gets called by {@link #confirmRemoveDialog} only if the User didn't disable it via "Dont Ask me again"
	 * @author mRakowski
	 * @param element the EClass which should be checked if it or its children has Constraints
	 * @return true if the EClass or its children has a Constraint
	 */
	protected boolean hasSelfOrChildConstraints(EClass element)
	{
		if(!ConfigurationUtil.getConstraintExpression(configurationEditor.getConfig(), element).isEmpty())
			return true;

		for(EReference curr : getAllReferences(element))
		{
			if(!ConfigurationUtil.getConstraintExpression(configurationEditor.getConfig(), curr).isEmpty())
				return true;
		}

		return false;
	}
	
	/**
	 * checks if the EReference ref has points to alternative elements
	 * @param ref the Reference to be checked
	 * @return true if ref points to alternative elements
	 */
	protected boolean hasAlternativeElements(EReference ref)
	{
		if(getAlternativeElements(ref) == null)
		{
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param ref the Reference to be checked
	 * @return true if the Reference is dangling
	 */
	protected boolean isReferenceDangling(EReference ref)
	{
		if(isChecked(ref) && !isChecked(ref.getEReferenceType()))
		{
			Set<EObject> alternatives = getAlternativeElements(ref);
			if(alternatives != null)
			{
				for(EObject alt : alternatives)
				{
					if(isChecked(alt))
					{
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns the EClass that contains the EReference.
	 * For OverridingEReference, this is the actual class that contains the reference.
	 * For all other EReferences, this is the default containing EClass.
	 * @param reference the reference
	 * @return class that contains the reference
	 */
	protected EClass getContainingClass(EReference reference)
	{
		if(reference instanceof OverridingEReference)
		{
			return ((OverridingEReference)reference).getActualClass();
		}
		else
		{
			return reference.getEContainingClass();
		}
	}

	/**
	 * Returns all EReferences of the given EClass. This
	 * includes OverridingEReferences.
	 * @param eClass the class
	 * @return set of EReferences of the EClass, never <code>null</code>
	 */
	protected Set<EReference> getAllReferences(EClass eClass)
	{
		Set<EReference> references = class2ReferenceMap.get(eClass);
		if(references == null)
			return Collections.emptySet();
		return references;
	}
}