package org.sidiff.slicer.structural.configuration.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.sidiff.slicer.structural.configuration.ConfigurationFactory;
import org.sidiff.slicer.structural.configuration.ConfigurationPackage;
import org.sidiff.slicer.structural.configuration.Constraint;
import org.sidiff.slicer.structural.configuration.IConstraintInterpreter;
import org.sidiff.slicer.structural.configuration.SlicedEClass;
import org.sidiff.slicer.structural.configuration.SlicedEReference;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;

public class ConfigurationUtil
{
	/**
	 * Returns the constraint interpreter with the given ID from the registry.
	 * @param id the ID of the constraint interpreter
	 * @return constraint interpreter with that ID, or <code>null</code> if non was found
	 */
	public static IConstraintInterpreter getConstraintInterpreterByID(String id) {
		for(IConstraintInterpreter availableConstraintInterpreter : getAllAvailableConstraintInterpreters()){
			if(availableConstraintInterpreter.getID().equals(id)){
				return availableConstraintInterpreter;
			}
		}
		return null;
	}

	/**
	 * Returns all available constraint interpreters from the registry.
	 * @return set of all available constraint interpreters
	 */
	public static Set<IConstraintInterpreter> getAllAvailableConstraintInterpreters() {
		Set<IConstraintInterpreter> availableConstraintInterpreter = new HashSet<IConstraintInterpreter>();
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				IConstraintInterpreter.EXTENSION_POINT_ID)) {
			try {
				availableConstraintInterpreter.add((IConstraintInterpreter) configurationElement.createExecutableExtension("class"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return availableConstraintInterpreter;
	}

	/**
	 * Returns the first {@link SlicedEClass} from the given slicing configuration
	 * that has the given type, or <code>null</code> if the type is not found
	 * @param slicingConfig the slicing configuration
	 * @param type the type to find
	 * @return first SlicedEClass with type, <code>null</code> if not found
	 */
	public static SlicedEClass findSlicedEClass(SlicingConfiguration slicingConfig, EClass type)
	{
		Assert.isNotNull(slicingConfig);
		Assert.isNotNull(type);

		for(SlicedEClass sC : slicingConfig.getSlicedEClasses())
		{
			if(sC.getType() != null && sC.getType().equals(type))
			{
				return sC;
			}
		}

		return null;
	}

	/**
	 * Returns the first {@link SlicedEReference} from the given slicing configuration
	 * that has the given type, or <code>null</code> if the type is not found
	 * @param slicingConfig the slicing configuration
	 * @param type the type to find
	 * @return first SlicedEReference with type, <code>null</code> if not found
	 */
	public static SlicedEReference findSlicedEReference(SlicingConfiguration slicingConfig, EReference type)
	{
		Assert.isNotNull(slicingConfig);
		Assert.isNotNull(type);

		// handle overriding references
		EClass containingClass;
		if(type instanceof OverridingEReference)
		{
			OverridingEReference overridingRef = (OverridingEReference)type;
			containingClass = overridingRef.getActualClass(); // the container is the actual class
			type = overridingRef.getDelegate(); // the type is the delegate of the overriding reference
		}
		else
		{
			containingClass = type.getEContainingClass();
		}

		if(containingClass == null)
			return null;

		SlicedEClass slicedEClass = findSlicedEClass(slicingConfig, containingClass);
		if(slicedEClass == null)
			return null; // containing EClass was not found

		for(SlicedEReference sR : slicedEClass.getSlicedEReferences())
		{
			if(sR.getType() != null && sR.getType().equals(type))
			{
				return sR;
			}
		}

		// EReference was not found
		return null;
	}

	/**
	 * Returns the constraint expression for the first sliced element from the
	 * given slicing configuration with the given type. Only {@link SlicedEClass}
	 * and {@link SlicedEReference} support constraints. Returns an empty string, if
	 * no element with the given type was found, or if the element has no constraint.
	 * @param slicingConfig the slicing configuration
	 * @param type the type
	 * @return constraint expression, or empty string if element or constraint not found
	 */
	public static String getConstraintExpression(SlicingConfiguration slicingConfig, EObject type)
	{
		Assert.isNotNull(slicingConfig);
		Assert.isNotNull(type);

		Constraint constraint = null;

		if(type instanceof EClass)
		{
			SlicedEClass slicedEClass = findSlicedEClass(slicingConfig, (EClass)type);
			if(slicedEClass == null)
				return "";
			constraint = slicedEClass.getConstraint();
		}
		else if(type instanceof EReference)
		{
			SlicedEReference slicedEReference = findSlicedEReference(slicingConfig, (EReference)type);
			if(slicedEReference == null)
				return "";
			constraint = slicedEReference.getConstraint();
		}

		return constraint != null ? (constraint.getExpression() != null ? constraint.getExpression() : "") : "";
	}

	/**
	 * Sets the constraint expression for the first sliced element in the given slicing
	 * configuration with the given type to the given expression using the editing domain
	 * and a {@link SetCommand}. The type must be one of the supported ones ({@link EClass}
	 * or {@link EReference}) and must be in the slicing configuration.
	 * @param editingDomain the editing domain
	 * @param slicingConfig the slicing configuration
	 * @param type the type
	 * @param expression constraint expression
	 */
	public static void setConstraintExpression(EditingDomain editingDomain, SlicingConfiguration slicingConfig, EObject type, String expression)
	{
		Assert.isNotNull(editingDomain);
		Assert.isNotNull(slicingConfig);
		Assert.isNotNull(type);
		Assert.isNotNull(expression);

		// create the constraint
		Object constraint = SetCommand.UNSET_VALUE;
		if(!expression.isEmpty())
		{
			constraint = ConfigurationFactory.eINSTANCE.createConstraint();
			((Constraint)constraint).setExpression(expression);
		}

		if(type instanceof EClass)
		{
			SlicedEClass slicedEClass = findSlicedEClass(slicingConfig, (EClass)type);
			Assert.isTrue(slicedEClass != null, "no SlicedEClass with with type " + type + " was found");
			
			// set the constraint using the editing domain
			editingDomain.getCommandStack().execute(
					SetCommand.create(editingDomain,
							slicedEClass,
							ConfigurationPackage.eINSTANCE.getSlicedEClass_Constraint(),
							constraint));
		}
		else if(type instanceof EReference)
		{
			SlicedEReference slicedEReference = findSlicedEReference(slicingConfig, (EReference)type);
			Assert.isTrue(slicedEReference != null, "no SlicedEReference with with type " + type + " was found");
			
			// set the constraint using the editing domain
			editingDomain.getCommandStack().execute(
					SetCommand.create(editingDomain,
							slicedEReference,
							ConfigurationPackage.eINSTANCE.getSlicedEReference_Constraint(),
							constraint));
		}
		else
		{
			Assert.isTrue(false, "the type " + type + " does not support constraints");
		}
	}

	/**
	 * Creates a new {@link SlicedEClass} with the given type using the {@link ConfigurationFactory}.
	 * @param type the type of the SlicedEClass
	 * @return a new SlicedEClass with that type
	 */
	public static SlicedEClass createSlicedEClass(EClass type)
	{
		Assert.isNotNull(type);

		SlicedEClass slicedClass = ConfigurationFactory.eINSTANCE.createSlicedEClass();
		slicedClass.setType(type);
		return slicedClass;
	}
	
	/**
	 * Creates a new {@link SlicedEClass} with the type of the class containing the given reference 
	 * using the {@link ConfigurationFactory}. For {@link OverridingEReference} this uses
	 * the actual class instead of the containing class.
	 * @param referenceType the type of the reference which containing class' type will be used
	 * @return a new SlicedEClass with the type of the class containing the given reference
	 */
	public static SlicedEClass createSlicedEClass(EReference referenceType)
	{
		Assert.isNotNull(referenceType);

		SlicedEClass slicedClass = ConfigurationFactory.eINSTANCE.createSlicedEClass();
		if(referenceType instanceof OverridingEReference)
		{
			slicedClass.setType(((OverridingEReference)referenceType).getActualClass());
		}
		else
		{
			slicedClass.setType(referenceType.getEContainingClass());
		}
		return slicedClass;
	}

	/**
	 * Creates a new {@link SlicedEReference} with the given type using the {@link ConfigurationFactory}.
	 * If the type is an {@link OverridingEReference}, the delegate of that reference is used.
	 * @param type the type of the SlicedEReference
	 * @return a new SlicedEReference with that type
	 */
	public static SlicedEReference createSlicedEReference(EReference type)
	{
		Assert.isNotNull(type);

		SlicedEReference slicedReference = ConfigurationFactory.eINSTANCE.createSlicedEReference();
		if(type instanceof OverridingEReference)
		{
			slicedReference.setType(((OverridingEReference)type).getDelegate());
		}
		else
		{
			slicedReference.setType(type);
		}
		return slicedReference;
	}

	/**
	 * Returns all sliceable objects ({@link EClass}es and {@link EReference}s) contained in
	 * the specified resource. This also creates an {@link OverridingEReference} for every
	 * EReference in all super types of an EClass.
	 * @param resource the resource
	 * @return set of all sliceable objects
	 */
	public static Set<EObject> getAllSliceableObjects(Resource resource)
	{
		Set<EObject> allObjects = new HashSet<EObject>();

		for(TreeIterator<EObject> it = resource.getAllContents(); it.hasNext(); )
		{
			EObject obj = it.next();

			if(obj instanceof EClass)
			{
				allObjects.add(obj);

				// also add overriding references
				EClass clazz = (EClass)obj;
				for(EReference ref : clazz.getEAllReferences())
				{
					// only add references that are sliceable and not local to the class itself
					if(isSliceable(ref) && !clazz.getEReferences().contains(ref))
					{
						allObjects.add(OverridingEReference.get(ref, clazz));
					}
				}
			}
			else if(obj instanceof EReference)
			{
				if(isSliceable((EReference)obj))
				{
					allObjects.add(obj);
				}
			}
			
			// all other types are ignores
		}

		return allObjects;
	}

	/**
	 * Returns whether the specified {@link EReference} can be sliced.
	 * A reference can be sliced if it is changeable, not derived, and not transient.
	 * @param reference the reference
	 * @return <code>true</code> if the reference is changeable, not derived, and not transient; <code>false</code> otherwise
	 */
	public static boolean isSliceable(EReference reference)
	{
		return reference.isChangeable() && !reference.isDerived() && !reference.isTransient();
	}
}
