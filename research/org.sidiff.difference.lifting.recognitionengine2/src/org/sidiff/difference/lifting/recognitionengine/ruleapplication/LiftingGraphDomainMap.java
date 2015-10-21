package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricPackage;

/**
 * <ul>
 * <li>
 * Index of change types (Remove-/Add-/Object, Remove-/Add-Reference,
 * Attribute-Value-Change by the type of the changed object) to candidates.
 * </li>
 * <li>
 * Index of model A/B objects to their correspondences.
 * </li>
 * </ul>
 * 
 * @author Manuel Ohrndorf
 */
public class LiftingGraphDomainMap {

	private static final SymmetricPackage SYMMMETRIC_PACKAGE = SymmetricPackage.eINSTANCE;

	private SymmetricDifference difference;

	private Map<EClass, Set<EClass>> subTypes;

	private Map<EClass, Collection<AddObject>> addObjects;

	private Map<EClass, Collection<RemoveObject>> removeObjects;

	private Map<EReference, Collection<AddReference>> addReference;

	private Map<EReference, Collection<RemoveReference>> removeReference;

	private Map<EAttribute, Collection<AttributeValueChange>> attributeValueChange;

	/**
	 * Creates a new {@link LiftingGraphDomainMap}.
	 * 
	 * @param difference
	 *            The difference containing the changes.
	 */
	public LiftingGraphDomainMap(SymmetricDifference difference) {
		this.difference = difference;

		subTypes = new HashMap<EClass, Set<EClass>>();
		addObjects = new HashMap<EClass, Collection<AddObject>>();
		removeObjects = new HashMap<EClass, Collection<RemoveObject>>();
		addReference = new HashMap<EReference, Collection<AddReference>>();
		removeReference = new HashMap<EReference, Collection<RemoveReference>>();
		attributeValueChange = new HashMap<EAttribute, Collection<AttributeValueChange>>();

		createChangeDomainMap(difference);
	}

	/**
	 * Creates the change index.
	 * 
	 * @param difference
	 *            The difference containing the changes.
	 */
	private void createChangeDomainMap(SymmetricDifference difference) {

		Set<EPackage> packages = new HashSet<EPackage>();

		for (Change change : difference.getChanges()) {

			if (change instanceof AddObject) {
				EClass type = ((AddObject) change).getObj().eClass();
				internalGetAddObjectDomain(type).add((AddObject) change);
			}

			else if (change instanceof RemoveObject) {
				EClass type = ((RemoveObject) change).getObj().eClass();
				internalGetRemoveObjectDomain(type).add((RemoveObject) change);

				// RemoveObject pattern needs inheritance map:
				packages.add(type.getEPackage());
			}

			else if (change instanceof AddReference) {
				EReference type = ((AddReference) change).getType();
				internalGetAddReferenceDomain(type).add((AddReference) change);
			}

			else if (change instanceof RemoveReference) {
				EReference type = ((RemoveReference) change).getType();
				internalGetRemoveReferenceDomain(type).add((RemoveReference) change);
			}

			else if (change instanceof AttributeValueChange) {
				EAttribute type = ((AttributeValueChange) change).getType();
				internalGetAttributeValueChangeDomain(type).add((AttributeValueChange) change);
			}
		}

		subTypes = getSubtypeIndex(packages);
	}
	
	/**
	 * @param modelElement
	 *            The element of model A or model B.
	 * @param correspondenceReference
	 *            Model A:
	 *            {@link SymmetricPackage.eINSTANCE.getCorrespondence_ObjA()} /
	 *            Model B:
	 *            {@link SymmetricPackage.eINSTANCE.getCorrespondence_Objb()}.
	 * @return The correspondence of the model A/B object or <code>null</code>.
	 */
	public Correspondence getCorrespondence(EObject modelElement, EReference correspondenceReference) {
		if (SYMMMETRIC_PACKAGE.getCorrespondence_ObjA() == correspondenceReference) {
			return difference.getCorrespondenceOfModelA(modelElement);
		}
		
		else if (SYMMMETRIC_PACKAGE.getCorrespondence_ObjB() == correspondenceReference) {
			return difference.getCorrespondenceOfModelB(modelElement);
		}
		
		return null;
	}

	/**
	 * Get all candidates which are compatible with the given change type. This
	 * returns a fresh and modifiable list.
	 * 
	 * @param changeType
	 *            The type of the change ({@link AddObject},
	 *            {@link RemoveObject}, {@link AddReference},
	 *            {@link RemoveReference}, {@link AttributeValueChange})
	 * @param changeDomainType
	 *            The type of the changed objects ({@link EClass},
	 *            {@link EReference}, {@link EAttribute}).
	 * @return A set of candidates compatible with the change type.
	 */
	public List<EObject> getChangeDomain(EClass changeType, EObject changeDomainType) {

		// AddObject:
		if ((changeType == SYMMMETRIC_PACKAGE.getAddObject())
				&& (changeDomainType instanceof EClass)) {
			return getAddObjectDomain((EClass) changeDomainType);
		}

		// RemoveObject:
		else if ((changeType == SYMMMETRIC_PACKAGE.getRemoveObject())
				&& (changeDomainType instanceof EClass)) {
			return getRemoveObjectDomain((EClass) changeDomainType);
		}

		// AddReference:
		else if ((changeType == SYMMMETRIC_PACKAGE.getAddReference())
				&& (changeDomainType instanceof EReference)) {
			return getAddReferenceDomain((EReference) changeDomainType);
		}

		// RemoveReference:
		else if ((changeType == SYMMMETRIC_PACKAGE.getRemoveReference())
				&& (changeDomainType instanceof EReference)) {
			return getRemoveReferenceDomain((EReference) changeDomainType);
		}

		// AttributeValueChange:
		else if ((changeType == SYMMMETRIC_PACKAGE.getAttributeValueChange())
				&& (changeDomainType instanceof EAttribute)) {
			return getAttributeValueChangeDomain((EAttribute) changeDomainType);
		}

		return null;
	}

	/**
	 * Returns the size of the domain for a change type. The returned number
	 * equals the size of the list returned by
	 * {@link #getAddObjectDomain(EClass)}. This method should be used whenever
	 * the actual objects are not needed.
	 * 
	 * @param changeType
	 *            The type of the change ({@link AddObject},
	 *            {@link RemoveObject}, {@link AddReference},
	 *            {@link RemoveReference}, {@link AttributeValueChange})
	 * @param changeDomainType
	 *            The type of the changed objects ({@link EClass},
	 *            {@link EReference}, {@link EAttribute}).
	 * @return The count of candidates compatible with the change type.
	 */
	public int getChangeDomainSize(EClass changeType, EObject changeDomainType) {

		// AddObject:
		if ((changeType == SYMMMETRIC_PACKAGE.getAddObject())
				&& (changeDomainType instanceof EClass)) {
			return getAddObjectDomainSize((EClass) changeDomainType);
		}

		// RemoveObject:
		else if ((changeType == SYMMMETRIC_PACKAGE.getRemoveObject())
				&& (changeDomainType instanceof EClass)) {
			return getRemoveObjectDomainSize((EClass) changeDomainType);
		}

		// AddReference:
		else if ((changeType == SYMMMETRIC_PACKAGE.getAddReference())
				&& (changeDomainType instanceof EReference)) {
			return getAddReferenceDomainSize((EReference) changeDomainType);
		}

		// RemoveReference:
		else if ((changeType == SYMMMETRIC_PACKAGE.getRemoveReference())
				&& (changeDomainType instanceof EReference)) {
			return getRemoveReferenceDomainSize((EReference) changeDomainType);
		}

		// AttributeValueChange:
		else if ((changeType == SYMMMETRIC_PACKAGE.getAttributeValueChange())
				&& (changeDomainType instanceof EAttribute)) {
			return getAttributeValueChangeDomainSize((EAttribute) changeDomainType);
		}

		return -1;
	}

	/**
	 * Get all candidates which are compatible with the given change type. This
	 * returns a fresh and modifiable list.
	 * 
	 * @param type
	 *            The type of the added objects.
	 * @return A set of candidates compatible with the type.
	 */
	public List<EObject> getAddObjectDomain(EClass type) {
		return new ArrayList<EObject>(addObjects.get(type));
	}

	/**
	 * Returns the size of the domain for a change type. The returned number
	 * equals the size of the list returned by
	 * {@link #getAddObjectDomain(EClass)}. This method should be used whenever
	 * the actual objects are not needed.
	 * 
	 * @param type
	 *            The type of the added objects.
	 * @return The count of candidates compatible with the change type.
	 */
	public int getAddObjectDomainSize(EClass type) {
		return addObjects.get(type).size();
	}

	/**
	 * Get all candidates which are compatible with the given change type. This
	 * returns a fresh and modifiable list.
	 * 
	 * @param type
	 *            The type of the removed objects.
	 * @return A set of candidates compatible with the type.
	 */
	public List<EObject> getRemoveObjectDomain(EClass type) {
		List<EObject> domain = new ArrayList<EObject>(removeObjects.get(type));

		if (subTypes != null) {
			for (EClass subtype : getSubTypes(type)) {
				domain.addAll(removeObjects.get(subtype));
			}
		}
		return domain;
	}

	/**
	 * Returns the size of the domain for a change type. The returned number
	 * equals the size of the list returned by
	 * {@link #getRemoveObjectDomain(EClass)}. This method should be used
	 * whenever the actual objects are not needed.
	 * 
	 * @param type
	 *            The type of the removed objects.
	 * @return The count of candidates compatible with the change type.
	 */
	public int getRemoveObjectDomainSize(EClass type) {
		int domainSize = removeObjects.get(type).size();

		if (subTypes != null) {
			for (EClass subtype : getSubTypes(type)) {
				domainSize += removeObjects.get(subtype).size();
			}
		}
		return domainSize;
	}

	/**
	 * Get all candidates which are compatible with the given change type. This
	 * returns a fresh and modifiable list.
	 * 
	 * @param type
	 *            The type of the added references.
	 * @return A set of candidates compatible with the type.
	 */
	public List<EObject> getAddReferenceDomain(EReference type) {
		return new ArrayList<EObject>(addReference.get(type));
	}

	/**
	 * Returns the size of the domain for a change type. The returned number
	 * equals the size of the list returned by
	 * {@link #getAddReferenceDomain(EClass)}. This method should be used
	 * whenever the actual objects are not needed.
	 * 
	 * @param type
	 *            The type of added references.
	 * @return The count of candidates compatible with the change type.
	 */
	public int getAddReferenceDomainSize(EReference type) {
		return addReference.get(type).size();
	}

	/**
	 * Get all candidates which are compatible with the given change type. This
	 * returns a fresh and modifiable list.
	 * 
	 * @param type
	 *            The type of removed references.
	 * @return A set of candidates compatible with the type.
	 */
	public List<EObject> getRemoveReferenceDomain(EReference type) {
		return new ArrayList<EObject>(removeReference.get(type));
	}

	/**
	 * Returns the size of the domain for a change type. The returned number
	 * equals the size of the list returned by
	 * {@link #getRemoveReferenceDomain(EClass)}. This method should be used
	 * whenever the actual objects are not needed.
	 * 
	 * @param type
	 *            The type of the removed references.
	 * @return The count of candidates compatible with the change type.
	 */
	public int getRemoveReferenceDomainSize(EReference type) {
		return removeReference.get(type).size();
	}

	/**
	 * Get all candidates which are compatible with the given change type. This
	 * returns a fresh and modifiable list.
	 * 
	 * @param type
	 *            The type of the changed attribute.
	 * @return A set of candidates compatible with the change type.
	 */
	public List<EObject> getAttributeValueChangeDomain(EAttribute type) {
		return new ArrayList<EObject>(attributeValueChange.get(type));
	}

	/**
	 * Returns the size of the domain for a change type. The returned number
	 * equals the size of the list returned by
	 * {@link #getAttributeValueChangeDomain(EClass)}. This method should be
	 * used whenever the actual objects are not needed.
	 * 
	 * @param type
	 *            The type of the changed attribute.
	 * @return The count of candidates compatible with the change type.
	 */
	public int getAttributeValueChangeDomainSize(EAttribute type) {
		return attributeValueChange.get(type).size();
	}

	/**
	 * Get the AddObject domain for a given type.
	 */
	protected Collection<AddObject> internalGetAddObjectDomain(EClass type) {
		Collection<AddObject> domain = addObjects.get(type);

		if (domain == null) {
			domain = new ArrayList<AddObject>();
			addObjects.put(type, domain);
		}

		return domain;
	}

	/**
	 * Get the RemoveObject domain for a given type.
	 */
	protected Collection<RemoveObject> internalGetRemoveObjectDomain(EClass type) {
		Collection<RemoveObject> domain = removeObjects.get(type);

		if (domain == null) {
			domain = new ArrayList<RemoveObject>();
			removeObjects.put(type, domain);
		}

		return domain;
	}

	/**
	 * Get the RemoveReference domain for a given type.
	 */
	protected Collection<RemoveReference> internalGetRemoveReferenceDomain(EReference type) {
		Collection<RemoveReference> domain = removeReference.get(type);

		if (domain == null) {
			domain = new ArrayList<RemoveReference>();
			removeReference.put(type, domain);
		}

		return domain;
	}

	/**
	 * Get the AddReference domain for a given type.
	 */
	protected Collection<AddReference> internalGetAddReferenceDomain(EReference type) {
		Collection<AddReference> domain = addReference.get(type);

		if (domain == null) {
			domain = new ArrayList<AddReference>();
			addReference.put(type, domain);
		}

		return domain;
	}

	/**
	 * Get the AttributeValueChange domain for a given type.
	 */
	protected Collection<AttributeValueChange> internalGetAttributeValueChangeDomain(EAttribute type) {
		Collection<AttributeValueChange> domain = attributeValueChange.get(type);

		if (domain == null) {
			domain = new ArrayList<AttributeValueChange>();
			attributeValueChange.put(type, domain);
		}

		return domain;
	}

	/**
	 * Returns all sub-types of the given EClass.
	 * 
	 * @param type
	 *            The super-type.
	 * @return All sub-types of the given EClass.
	 */
	private Set<EClass> getSubTypes(EClass type) {
		return subTypes.get(type);
	}

	/**
	 * Creates a map form each class in the package to its corresponding
	 * sub-types (in the package).
	 * 
	 * @param ePackage
	 *            The package containing the sub- and super-classes.
	 * @return A map EClass -> Set of EClass sup-types.
	 */
	protected Map<EClass, Set<EClass>> getSubtypeIndex(Set<EPackage> ePackages) {

		// Class (A) -> [Sub classes (X, Y, Z)]
		Map<EClass, Set<EClass>> subTypes = new HashMap<EClass, Set<EClass>>();

		// Iterate over all docType packages
		for (EPackage ePackage : ePackages) {

			// Iterate over all classes in the package
			for (Iterator<EObject> i = ePackage.eAllContents(); i.hasNext();) {
				EObject obj = i.next();

				if (obj instanceof EClass) {
					// Next class (A)
					EClass eSubClass = (EClass) obj;

					if (subTypes.get(eSubClass) == null) {
						subTypes.put(eSubClass, new HashSet<EClass>());
					}

					// Lookup the super types (X,Y,Z) of class (A) and add
					// class (A) as sub type to the classes (X, Y, Z)
					for (EClass eSuperClass : eSubClass.getEAllSuperTypes()) {
						Set<EClass> allSubTypes = subTypes.get(eSuperClass);

						if (allSubTypes == null) {
							allSubTypes = new HashSet<EClass>();
							subTypes.put(eSuperClass, allSubTypes);
						}

						allSubTypes.add(eSubClass);
					}
				}
			}
		}

		return subTypes;
	}

	/**
	 * @return The corresponding difference.
	 */
	public SymmetricDifference getDifference() {
		return difference;
	}
}
