package org.sidiff.patching.ui.adapter;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Information about model changes. The model changes that are reported mostly
 * correspond to our definitions of low-level changes with the following
 * abbreviations:
 * <ul>
 * <li>Only non-containment reference changes are reported as reference changes.
 * </li>
 * <li>Containment references changes are not separately reported. Here, only
 * the AddObject and removeObject changes are reported.</li>
 * <li>Each value setting of an attribute is reported, not only
 * AttributeValueChanges</li>
 * </ul>
 * 
 * 
 * @author kehrer
 */
public interface IModelChangeListener {

	public void objectAdded(EObject eObject);

	public void objectRemoved(EObject eObject);

	public void referenceAdded(EReference referenceType, EObject src, EObject tgt);

	public void referenceRemoved(EReference referenceType, EObject src, EObject tgt);

	public void attributeValueSet(EAttribute attribute, EObject object, Object value);

}
