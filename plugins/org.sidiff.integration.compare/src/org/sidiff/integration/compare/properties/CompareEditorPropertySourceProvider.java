package org.sidiff.integration.compare.properties;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.sidiff.integration.compare.SiLiftCompareAdapterFactory;
import org.sidiff.patching.operation.OperationInvocationWrapper;
import org.sidiff.patching.ui.view.OperationInvocationWrapperPropertySource;

/**
 * @author rmueller
 */
public class CompareEditorPropertySourceProvider implements IPropertySourceProvider {

	private IPropertySourceProvider delegate;

	public CompareEditorPropertySourceProvider() {
		initDelegate();
	}

	@Override
	public IPropertySource getPropertySource(Object object) {
		if(object instanceof OperationInvocationWrapper) {
			return new OperationInvocationWrapperPropertySource((OperationInvocationWrapper)object);
		}
		return delegate.getPropertySource(object);
	}

	/**
	 * Creates a read-only {@link AdapterFactoryContentProvider} as fallback property source.
	 */
	private void initDelegate() {
		delegate = new AdapterFactoryContentProvider(new SiLiftCompareAdapterFactory()) {
			@Override
			protected IPropertySource createPropertySource(Object object, IItemPropertySource itemPropertySource) {
				return new PropertySource(object, itemPropertySource) {
					@Override
					protected IPropertyDescriptor createPropertyDescriptor(
							IItemPropertyDescriptor itemPropertyDescriptor) {
						return new PropertyDescriptor(object, itemPropertyDescriptor) {
							@Override
							public CellEditor createPropertyEditor(Composite composite) {
								// returning null makes the property read-only
								return null;
							}
						};
					}
				};
			}
		};
	}
}
