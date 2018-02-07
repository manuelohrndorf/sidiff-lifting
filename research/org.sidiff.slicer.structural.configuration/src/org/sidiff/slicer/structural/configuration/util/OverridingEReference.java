package org.sidiff.slicer.structural.configuration.util;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * The class OverridingEReference is used to wrap an {@link EReference}
 * and store the {@link EClass} in which the reference is overridden.
 * The class implements EReference and delegates all methods to the delegate.
 * 
 * @author rmueller
 *
 */
public class OverridingEReference extends MinimalEObjectImpl.Container implements EReference
{
	/**
	 * the reference that is overridden
	 */
	protected final EReference delegate;

	/**
	 * the class that contains the overriding reference
	 */
	protected final EClass actualClass;

	/**
	 * Creates a new OverridingEReference for the given reference and class.
	 * @param delegate the reference that is overridden
	 * @param actualClass the class in which the reference is overridden
	 */
	private OverridingEReference(EReference delegate, EClass actualClass)
	{
		this.delegate = delegate;
		this.actualClass = actualClass;
	}

	/**
	 * Map of (EReference,EClass) to OverridingEReference that stores all
	 * created OverridingEReferences.
	 */
	protected static Map<EReference,Map<EClass,OverridingEReference>> overridingReferences;

	/**
	 * Returns an OverridingEReference for the given reference and class.
	 * If an OverridingEReference was already created for this delegate, it is returned.
	 * If not, a new OverridingEReference is created and returned.
	 * @param delegate the reference that is overridden
	 * @param actualClass the class in which the reference is overridden
	 * @return OverridingEReference for the given reference and class
	 */
	public static OverridingEReference get(EReference delegate, EClass actualClass)
	{
		if(overridingReferences == null)
		{
			overridingReferences = new HashMap<EReference,Map<EClass,OverridingEReference>>();
		}
		if(overridingReferences.get(delegate) == null)
		{
			overridingReferences.put(delegate, new HashMap<EClass,OverridingEReference>());
		}
		Map<EClass,OverridingEReference> innerMap = overridingReferences.get(delegate);
		if(innerMap.get(actualClass) == null)
		{
			innerMap.put(actualClass, new OverridingEReference(delegate, actualClass));
		}
		return innerMap.get(actualClass);
	}

	/**
	 * Returns the actual reference to which all other functions are delegated.
	 * @return the overridden reference
	 */
	public EReference getDelegate()
	{
		return delegate;
	}

	/**
	 * Returns the class that contains the overriding reference. This is not the same as {@link #getEContainingClass()}.
	 * @return the class that contains the overriding reference
	 */
	public EClass getActualClass()
	{
		return actualClass;
	}

	@Override
	public String toString()
	{
		StringBuilder result = new StringBuilder(getClass().getName());
	    result.append('@');
	    result.append(Integer.toHexString(hashCode()));
	    result.append(" (delegate: ");
	    result.append(delegate.toString());
	    result.append(", actualClass: ");
	    result.append(actualClass.toString());
	    result.append(")");
	    return result.toString();
	}

	public EList<Adapter> eAdapters()
	{
		return delegate.eAdapters();
	}

	public boolean eDeliver()
	{
		return delegate.eDeliver();
	}

	public EList<EAnnotation> getEAnnotations()
	{
		return delegate.getEAnnotations();
	}

	public String getName()
	{
		return delegate.getName();
	}

	public void eSetDeliver(boolean deliver)
	{
		delegate.eSetDeliver(deliver);
	}

	public void eNotify(Notification notification)
	{
		delegate.eNotify(notification);
	}

	public void setName(String value)
	{
		delegate.setName(value);
	}

	public boolean isContainment()
	{
		return delegate.isContainment();
	}

	public EAnnotation getEAnnotation(String source)
	{
		return delegate.getEAnnotation(source);
	}

	public boolean isTransient()
	{
		return delegate.isTransient();
	}

	public void setContainment(boolean value)
	{
		delegate.setContainment(value);
	}

	public boolean isOrdered()
	{
		return delegate.isOrdered();
	}

	public void setTransient(boolean value)
	{
		delegate.setTransient(value);
	}

	public boolean isContainer()
	{
		return delegate.isContainer();
	}

	public void setOrdered(boolean value)
	{
		delegate.setOrdered(value);
	}

	public boolean isVolatile()
	{
		return delegate.isVolatile();
	}

	public boolean isUnique()
	{
		return delegate.isUnique();
	}

	public boolean isResolveProxies()
	{
		return delegate.isResolveProxies();
	}

	public void setVolatile(boolean value)
	{
		delegate.setVolatile(value);
	}

	public EClass eClass()
	{
		return delegate.eClass();
	}

	public void setUnique(boolean value)
	{
		delegate.setUnique(value);
	}

	public boolean isChangeable()
	{
		return delegate.isChangeable();
	}

	public void setResolveProxies(boolean value)
	{
		delegate.setResolveProxies(value);
	}

	public Resource eResource()
	{
		return delegate.eResource();
	}

	public int getLowerBound()
	{
		return delegate.getLowerBound();
	}

	public void setChangeable(boolean value)
	{
		delegate.setChangeable(value);
	}

	public EReference getEOpposite()
	{
		return delegate.getEOpposite();
	}

	public void setLowerBound(int value)
	{
		delegate.setLowerBound(value);
	}

	public String getDefaultValueLiteral()
	{
		return delegate.getDefaultValueLiteral();
	}

	public EObject eContainer()
	{
		return delegate.eContainer();
	}

	public void setEOpposite(EReference value)
	{
		delegate.setEOpposite(value);
	}

	public int getUpperBound()
	{
		return delegate.getUpperBound();
	}

	public EClass getEReferenceType()
	{
		return delegate.getEReferenceType();
	}

	public void setDefaultValueLiteral(String value)
	{
		delegate.setDefaultValueLiteral(value);
	}

	public EStructuralFeature eContainingFeature()
	{
		return delegate.eContainingFeature();
	}

	public Object getDefaultValue()
	{
		return delegate.getDefaultValue();
	}

	public void setUpperBound(int value)
	{
		delegate.setUpperBound(value);
	}

	public EList<EAttribute> getEKeys()
	{
		return delegate.getEKeys();
	}

	public boolean isMany()
	{
		return delegate.isMany();
	}

	public EReference eContainmentFeature()
	{
		return delegate.eContainmentFeature();
	}

	public boolean isRequired()
	{
		return delegate.isRequired();
	}

	public void setDefaultValue(Object value)
	{
		delegate.setDefaultValue(value);
	}

	public EClassifier getEType()
	{
		return delegate.getEType();
	}

	public boolean isUnsettable()
	{
		return delegate.isUnsettable();
	}

	public EList<EObject> eContents()
	{
		return delegate.eContents();
	}

	public void setEType(EClassifier value)
	{
		delegate.setEType(value);
	}

	public EGenericType getEGenericType()
	{
		return delegate.getEGenericType();
	}

	public void setUnsettable(boolean value)
	{
		delegate.setUnsettable(value);
	}

	public TreeIterator<EObject> eAllContents()
	{
		return delegate.eAllContents();
	}

	public boolean isDerived()
	{
		return delegate.isDerived();
	}

	public void setEGenericType(EGenericType value)
	{
		delegate.setEGenericType(value);
	}

	public boolean eIsProxy()
	{
		return delegate.eIsProxy();
	}

	public void setDerived(boolean value)
	{
		delegate.setDerived(value);
	}

	public EClass getEContainingClass()
	{
		return delegate.getEContainingClass();
	}

	public EList<EObject> eCrossReferences()
	{
		return delegate.eCrossReferences();
	}

	public int getFeatureID()
	{
		return delegate.getFeatureID();
	}

	public Class<?> getContainerClass()
	{
		return delegate.getContainerClass();
	}

	public Object eGet(EStructuralFeature feature)
	{
		return delegate.eGet(feature);
	}

	public Object eGet(EStructuralFeature feature, boolean resolve)
	{
		return delegate.eGet(feature, resolve);
	}

	public void eSet(EStructuralFeature feature, Object newValue)
	{
		delegate.eSet(feature, newValue);
	}

	public boolean eIsSet(EStructuralFeature feature)
	{
		return delegate.eIsSet(feature);
	}

	public void eUnset(EStructuralFeature feature)
	{
		delegate.eUnset(feature);
	}

	public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException
	{
		return delegate.eInvoke(operation, arguments);
	}
}
