package org.sidiff.javaast.model.adapter;

import org.eclipse.emf.common.notify.Adapter;

/**
 * Adapter factory creating all supported adapters.
 * 
 * @author kehrer
 */
public class JavaModelAdapterFactory extends org.sidiff.javaast.model.util.JavaModelAdapterFactory {

	@Override
	public Adapter createJClassAdapter() {
		return new JClassAdapter();
	}
	
	@Override
	public Adapter createJMethodAdapter() {
		return new JMethodAdapter();
	}
	
	@Override
	public boolean isFactoryForType(Object type) {
		return type == JClassAdapter.class || type == JMethodAdapter.class;
	}
}
