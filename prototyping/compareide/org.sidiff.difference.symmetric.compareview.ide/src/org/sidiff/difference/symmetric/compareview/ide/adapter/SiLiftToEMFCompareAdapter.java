package org.sidiff.difference.symmetric.compareview.ide.adapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.emf.compare.CompareFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.RemoveObject;

public class SiLiftToEMFCompareAdapter implements InvocationHandler {
	
	private ICompareInput compareInputAdapter;
	private EObject emfcompareDiff;
	private EObject siliftDiff;
	
	protected SiLiftToEMFCompareAdapter() {
	}
	
	public SiLiftToEMFCompareAdapter(EObject siliftDiff, Comparison emfCompareRootDiff) 
			throws ConversionException {
		
		// SiLift difference model:
		this.siliftDiff = siliftDiff;
		
		// Convert to the EMF-Compare difference model:
		emfcompareDiff = convertSiLiftToEMFCompare(siliftDiff, emfCompareRootDiff);
		
		// Adapted EMF-Compare difference element to the Eclipse-Compare interface:
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		compareInputAdapter = (ICompareInput) adapterFactory.adapt(emfcompareDiff, ICompareInput.class);
	}
	
	public EObject convertSiLiftToEMFCompare(EObject siliftDiff, Comparison emfCompareRootDiff) 
			throws ConversionException {
		
		/*
		 *  TODO: Convert to the EMF-Compare difference model:
		 */
		
		if (siliftDiff instanceof Correspondence) {
			
			// Correspondence -> Match: 
			Match match = CompareFactory.eINSTANCE.createMatch();
			Correspondence correspondence = (Correspondence) siliftDiff;

			match.setLeft(correspondence.getObjA());
			match.setRight(correspondence.getObjB());
			emfCompareRootDiff.getMatches().add(match);
			
			return match;
		}
		
		else if (siliftDiff instanceof AddObject) {
			
			// AddObject -> Match: 
			Match match = CompareFactory.eINSTANCE.createMatch();
			AddObject addObject = (AddObject) siliftDiff;

			match.setRight(addObject.getObj());
			emfCompareRootDiff.getMatches().add(match);
			
			return match;
		}
		
		else if (siliftDiff instanceof RemoveObject) {
			
			// RemoveObject -> Match: 
			Match match = CompareFactory.eINSTANCE.createMatch();
			RemoveObject removeObject = (RemoveObject) siliftDiff;

			match.setRight(removeObject.getObj());
			emfCompareRootDiff.getMatches().add(match);
			
			return match;
		} 
		
//		else if (siliftDiff instanceof AddReference) {
//			
//			// AddReference -> ReferenceChange
//			ReferenceChange referenceChange = CompareFactory.eINSTANCE.createReferenceChange();
//			AddReference addReference = (AddReference) siliftDiff;
//			
//			referenceChange.setReference(addReference.getType());
//			referenceChange.setKind(DifferenceKind.ADD);
//			referenceChange.setSource(DifferenceSource.LEFT);
//		}
		
		else {
			throw new ConversionException();
		}
	}
	
	public EObject createProxy() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		Class<?>[] interfaces = {InternalEObject.class, siliftDiff.getClass().getInterfaces()[0], ICompareInput.class};
		InvocationHandler invocationHandler = this;

		EObject proxy = (EObject) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
		return proxy;
	}
	
	public static void replaceWithProxy(EObject siliftDiff, Comparison emfCompareRootDiff) 
			throws ConversionException {
		
		SiLiftToEMFCompareAdapter invocationHandler = new SiLiftToEMFCompareAdapter(siliftDiff, emfCompareRootDiff);
		EcoreUtil.replace(siliftDiff, invocationHandler.createProxy());
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		
		// Filter ICompareInput interface:
		if (method.getDeclaringClass() != ICompareInput.class) {
			try {
				// Delegation to SiLift SymmetricDifference / Ecore:
				return method.invoke(siliftDiff, args);
			} catch (IllegalArgumentException e) {
				// Try another interface...
			}
		}

		// Delegation to the Eclipse ICompareInput interface / EMF-Compare (AbstractEDiffNode):
		return method.invoke(compareInputAdapter, args);
	}
}
