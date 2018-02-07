package org.sidiff.slicer.structural.configuration.presentation.properties;


import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditor;

/**
 * Property section that contains the default properties.
 * This is basically an {@link AdvancedPropertySection} which sets a custom PropertySourceProvider
 * to make the properties read-only if one of the imported models is currently showing.
 * @author rmueller
 *
 */
public class DefaultPropertySection extends AdvancedPropertySection
{
	/**
	 * Default constructor needed for {@link TabDescriptorProvider}
	 */
	public DefaultPropertySection()
	{
		super();
	}
	
	@Override
	public void setInput(IWorkbenchPart part, ISelection selection)
	{
		if(part instanceof ConfigurationEditor)
		{
			final ConfigurationEditor confEditor = (ConfigurationEditor)part;
			
			// set a custom property source provider
			page.setPropertySourceProvider(new AdapterFactoryContentProvider(confEditor.getAdapterFactory())
			{
				// create custom property source for making the imported models' properties read only
				@Override
				protected IPropertySource createPropertySource(Object object, IItemPropertySource itemPropertySource)
				{
					return new PropertySource(object, itemPropertySource)
					{
						@Override
						protected IPropertyDescriptor createPropertyDescriptor(IItemPropertyDescriptor itemPropertyDescriptor)
						{
							return new PropertyDescriptor(object, itemPropertyDescriptor)
							{
								@Override
								public CellEditor createPropertyEditor(Composite composite)
								{
									// normal behavior for the configuration tab
									if(confEditor.isConfigurationTabActive())
										return super.createPropertyEditor(composite);
									
									// return null makes it read only
									return null;
								}
							};
						}
					};
				}
			});
		}
		
		super.setInput(part, selection);
	}
}
