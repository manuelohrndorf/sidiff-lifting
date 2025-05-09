package org.sidiff.integration.compare;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.sidiff.difference.asymmetric.util.AsymmetricAdapterFactory;
import org.sidiff.difference.symmetric.util.SymmetricAdapterFactory;

/**
 * @author rmueller
 */
public class SiLiftCompareAdapterFactory extends ComposedAdapterFactory {

	public SiLiftCompareAdapterFactory() {
		super(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		addAdapterFactory(new ResourceItemProviderAdapterFactory());
		addAdapterFactory(new AsymmetricAdapterFactory());
		addAdapterFactory(new SymmetricAdapterFactory());
		addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
	}
}
