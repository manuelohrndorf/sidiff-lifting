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

	@Override
	public EList<Adapter> eAdapters()
	{
		return delegate.eAdapters();
	}

	@Override
	public boolean eDeliver()
	{
		return delegate.eDeliver();
	}

	@Override
	public EList<EAnnotation> getEAnnotations()
	{
		return delegate.getEAnnotations();
	}

	@Override
	public String getName()
	{
		return delegate.getName();
	}

	@Override
	public void eSetDeliver(boolean deliver)
	{
		delegate.eSetDeliver(deliver);
	}

	@Override
	public void eNotify(Notification notification)
	{
		delegate.eNotify(notification);
	}

	@Override
	public void setName(String value)
	{
		delegate.setName(value);
	}

	@Override
	public boolean isContainment()
	{
		return delegate.isContainment();
	}

	@Override
	public EAnnotation getEAnnotation(String source)
	{
		return delegate.getEAnnotation(source);
	}

	@Override
	public boolean isTransient()
	{
		return delegate.isTransient();
	}

	@Override
	public void setContainment(boolean value)
	{
		delegate.setContainment(value);
	}

	@Override
	public boolean isOrdered()
	{
		return delegate.isOrdered();
	}

	@Override
	public void setTransient(boolean value)
	{
		delegate.setTransient(value);
	}

	@Override
	public boolean isContainer()
	{
		return delegate.isContainer();
	}

	@Override
	public void setOrdered(boolean value)
	{
		delegate.setOrdered(value);
	}

	@Override
	public boolean isVolatile()
	{
		return delegate.isVolatile();
	}

	@Override
	public boolean isUnique()
	{
		return delegate.isUnique();
	}

	@Override
	public boolean isResolveProxies()
	{
		return delegate.isResolveProxies();
	}

	@Override
	public void setVolatile(boolean value)
	{
		delegate.setVolatile(value);
	}

	@Override
	public EClass eClass()
	{
		return delegate.eClass();
	}

	@Override
	public void setUnique(boolean value)
	{
		delegate.setUnique(value);
	}

	@Override
	public boolean isChangeable()
	{
		return delegate.isChangeable();
	}

	@Override
	public void setResolveProxies(boolean value)
	{
		delegate.setResolveProxies(value);
	}

	@Override
	public Resource eResource()
	{
		return delegate.eResource();
	}

	@Override
	public int getLowerBound()
	{
		return delegate.getLowerBound();
	}

	@Override
	public void setChangeable(boolean value)
	{
		delegate.setChangeable(value);
	}

	@Override
	public EReference getEOpposite()
	{
		return delegate.getEOpposite();
	}

	@Override
	public void setLowerBound(int value)
	{
		delegate.setLowerBound(value);
	}

	@Override
	public String getDefaultValueLiteral()
	{
		return delegate.getDefaultValueLiteral();
	}

	@Override
	public EObject eContainer()
	{
		return delegate.eContainer();
	}

	@Override
	public void setEOpposite(EReference value)
	{
		delegate.setEOpposite(value);
	}

	@Override
	public int getUpperBound()
	{
		return delegate.getUpperBound();
	}

	@Override
	public EClass getEReferenceType()
	{
		return delegate.getEReferenceType();
	}

	@Override
	public void setDefaultValueLiteral(String value)
	{
		delegate.setDefaultValueLiteral(value);
	}

	@Override
	public EStructuralFeature eContainingFeature()
	{
		return delegate.eContainingFeature();
	}

	@Override
	public Object getDefaultValue()
	{
		return delegate.getDefaultValue();
	}

	@Override
	public void setUpperBound(int value)
	{
		delegate.setUpperBound(value);
	}

	@Override
	public EList<EAttribute> getEKeys()
	{
		return delegate.getEKeys();
	}

	@Override
	public boolean isMany()
	{
		return delegate.isMany();
	}

	@Override
	public EReference eContainmentFeature()
	{
		return delegate.eContainmentFeature();
	}

	@Override
	public boolean isRequired()
	{
		return delegate.isRequired();
	}

	@Override
	public void setDefaultValue(Object value)
	{
		delegate.setDefaultValue(value);
	}

	@Override
	public EClassifier getEType()
	{
		return delegate.getEType();
	}

	@Override
	public boolean isUnsettable()
	{
		return delegate.isUnsettable();
	}

	@Override
	public EList<EObject> eContents()
	{
		return delegate.eContents();
	}

	@Override
	public void setEType(EClassifier value)
	{
		delegate.setEType(value);
	}

	@Override
	public EGenericType getEGenericType()
	{
		return delegate.getEGenericType();
	}

	@Override
	public void setUnsettable(boolean value)
	{
		delegate.setUnsettable(value);
	}

	@Override
	public TreeIterator<EObject> eAllContents()
	{
		return delegate.eAllContents();
	}

	@Override
	public boolean isDerived()
	{
		return delegate.isDerived();
	}

	@Override
	public void setEGenericType(EGenericType value)
	{
		delegate.setEGenericType(value);
	}

	@Override
	public boolean eIsProxy()
	{
		return delegate.eIsProxy();
	}

	@Override
	public void setDerived(boolean value)
	{
		delegate.setDerived(value);
	}

	@Override
	public EClass getEContainingClass()
	{
		return delegate.getEContainingClass();
	}

	@Override
	public EList<EObject> eCrossReferences()
	{
		return delegate.eCrossReferences();
	}

	@Override
	public int getFeatureID()
	{
		return delegate.getFeatureID();
	}

	@Override
	public Class<?> getContainerClass()
	{
		return delegate.getContainerClass();
	}

	@Override
	public Object eGet(EStructuralFeature feature)
	{
		return delegate.eGet(feature);
	}

	@Override
	public Object eGet(EStructuralFeature feature, boolean resolve)
	{
		return delegate.eGet(feature, resolve);
	}

	@Override
	public void eSet(EStructuralFeature feature, Object newValue)
	{
		delegate.eSet(feature, newValue);
	}

	@Override
	public boolean eIsSet(EStructuralFeature feature)
	{
		return delegate.eIsSet(feature);
	}

	@Override
	public void eUnset(EStructuralFeature feature)
	{
		delegate.eUnset(feature);
	}

	@Override
	public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException
	{
		return delegate.eInvoke(operation, arguments);
	}
}
