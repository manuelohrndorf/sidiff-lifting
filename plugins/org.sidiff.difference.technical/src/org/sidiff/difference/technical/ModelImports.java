package org.sidiff.difference.technical;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.emf.ecore.EObject;

/**
 * Container for import of model/resource A/B.
 * 
 * @author Manuel Ohrndorf
 */
public class ModelImports {
	
	private PackageRegistryAdapter registryAdapter;

	private ResourceSetAdapter resourceSetAdapter;
	
	public ModelImports(PackageRegistryAdapter registryAdapter, ResourceSetAdapter resourceSetAdapter) {
		assert (registryAdapter != null) : "PackageRegistryAdapter can not be null!";
		
		this.registryAdapter = registryAdapter;
		this.resourceSetAdapter = resourceSetAdapter;
	}
	
	/*
	 * Model A
	 */
	
	public Iterator<EObject> getIteratorImportsModelA() {
		Iterator<EObject> registryAdapterIterator = registryAdapter.getImportsModelA().iterator();
		Iterator<EObject> resourceSetAdapterIterator = null;
		
		if (resourceSetAdapter != null) {
			resourceSetAdapterIterator = resourceSetAdapter.getImportsModelA().iterator();
		}
		
		return new ImportIterator(registryAdapterIterator, resourceSetAdapterIterator);
	}

	public boolean containsImportsModelA(EObject element) {
		if (registryAdapter.getImportsModelA().contains(element)) {
			return true;
		} else {
			if (resourceSetAdapter != null) {
				return  resourceSetAdapter.getImportsModelA().contains(element);
			}
		}
		
		return false;
	}
	
	public void addImportsModelA(Collection<EObject> base) {
		base.addAll(registryAdapter.getImportsModelA());
		
		if (resourceSetAdapter != null) {
			base.addAll(resourceSetAdapter.getImportsModelA());
		}
	}
	
	/*
	 * Model B
	 */
	
	public Iterator<EObject> getIteratorImportsModelB() {
		Iterator<EObject> registryAdapterIterator = registryAdapter.getImportsModelB().iterator();
		Iterator<EObject> resourceSetAdapterIterator = null;
		
		if (resourceSetAdapter != null) {
			resourceSetAdapterIterator = resourceSetAdapter.getImportsModelB().iterator();
		}
		
		return new ImportIterator(registryAdapterIterator, resourceSetAdapterIterator);
	}
	
	public boolean containsImportsModelB(EObject element) {
		if (registryAdapter.getImportsModelB().contains(element)) {
			return true;
		} else {
			if (resourceSetAdapter != null) {
				return  resourceSetAdapter.getImportsModelB().contains(element);
			}
		}
		
		return false;
	}
	
	public void addImportsModelB(Collection<EObject> base) {
		base.addAll(registryAdapter.getImportsModelB());
		
		if (resourceSetAdapter != null) {
			base.addAll(resourceSetAdapter.getImportsModelB());
		}
	}
	
	private class ImportIterator implements Iterator<EObject> {

		private Iterator<EObject> registryAdapterIterator;
		private Iterator<EObject> resourceSetAdapterIterator;
		
		public ImportIterator(Iterator<EObject> registryAdapterIterator, Iterator<EObject> resourceSetAdapterIterator) {
			assert (registryAdapterIterator != null) : "PackageRegistryAdapter iterator can not be null!";
			
			this.registryAdapterIterator = registryAdapterIterator;
			this.resourceSetAdapterIterator = resourceSetAdapterIterator;
		}

		@Override
		public boolean hasNext() {
			if (registryAdapterIterator.hasNext()) {
				return true;
			} else {
				if (resourceSetAdapterIterator != null) {
					return resourceSetAdapterIterator.hasNext();
				}
			}
			return false;
		}

		@Override
		public EObject next() {
			if (registryAdapterIterator.hasNext()) {
				return registryAdapterIterator.next();
			} else {
				if (resourceSetAdapterIterator != null) {
					return resourceSetAdapterIterator.next();
				}
			}
			
			throw new NoSuchElementException("Model import iterator!");
		}
		
	}
}
