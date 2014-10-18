package org.sidiff.difference.symmetric.compareview.ide.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

public class SiLiftToEMFCompareProxy implements InvocationHandler {
	
	private ICompareInput compareInput;
	private EObject emfcompareDiffElement;
	private EObject siliftDiffElement;
	
	protected SiLiftToEMFCompareProxy() {
	}
	
	public SiLiftToEMFCompareProxy(EObject siliftDiffElement, EObject emfcompareDiffElement) {
		
		// SiLift difference model:
		this.siliftDiffElement = siliftDiffElement;

		// EMF-Compare difference model:
		this.emfcompareDiffElement = emfcompareDiffElement;
		
		// Adapted EMF-Compare difference element to the Eclipse-Compare interface:
		// (AbstractEDiffNode extends AdapterImpl implements ICompareInput)
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		compareInput = (ICompareInput) adapterFactory.adapt(emfcompareDiffElement, ICompareInput.class);
	}
	
	public EObject createProxy() {
		
		// Set up the proxy interface:
		Class<?>[] interfaces = {
				InternalEObject.class,
				siliftDiffElement.getClass().getInterfaces()[0],
				ICompareInput.class
		};
		
		// Pick a class loader with access to all interfaces:
		ClassLoader classLoader = this.getClass().getClassLoader();
		
		// The handler which delegates all operation invocations from the proxy to the objects:
		InvocationHandler invocationHandler = this;

		// Dynamically create a new proxy object: 
		EObject proxy = (EObject) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
		return proxy;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		// Filter ICompareInput interface:
		if (method.getDeclaringClass() != ICompareInput.class) {
			try {
				// Delegation to SiLift SymmetricDifference / Ecore:
				return method.invoke(siliftDiffElement, args);
			} catch (IllegalArgumentException e) {
				// Try another interface...
			}
		}

		// Delegation to the Eclipse ICompareInput interface / EMF-Compare (AbstractEDiffNode):
		return method.invoke(compareInput, args);
	}
	
	public static void replaceWithProxy(EObject siliftDiffElement, EObject emfcompareDiffElement) {
		
		SiLiftToEMFCompareProxy invocationHandler = new SiLiftToEMFCompareProxy(siliftDiffElement, emfcompareDiffElement);
		EObject proxy = invocationHandler.createProxy();
		EcoreUtil.replace(siliftDiffElement, proxy);
	}

	public ICompareInput getCompareInput() {
		return compareInput;
	}

	
	public EObject getEmfcompareDiffElement() {
		return emfcompareDiffElement;
	}

	
	public EObject getSiliftDiffElement() {
		return siliftDiffElement;
	}
}
