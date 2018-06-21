package org.sidiff.slicer.structural.configuration.presentation.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptor;
import org.eclipse.ui.views.properties.tabbed.ITabDescriptorProvider;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditor;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;
import org.sidiff.slicer.structural.configuration.util.ConfigurationUtil;

/**
 * Provides the tabs for the tabbed property view.
 * A default tab with the default properties is always provided.
 * A second tab for the constraint is provided when a model element
 * is selected which is either an EClass or an EReference and the element
 * is currently included in the slicing configuration.
 * @author rmueller
 *
 */
public class TabDescriptorProvider implements ITabDescriptorProvider
{
	@Override
	public ITabDescriptor[] getTabDescriptors(IWorkbenchPart part, ISelection selection)
	{
		final TabDescriptor defaultTab = new TabDescriptor("default", //$NON-NLS-1$
				ConfigurationEditorPlugin.INSTANCE.getString("_UI_PropertyTab_Default_label"), //$NON-NLS-1$
				DefaultPropertySection.class);
		
		if(shouldShowConstraint(part, selection))
		{
			// default and constraint tab
			return new AbstractTabDescriptor[]
				{
					defaultTab,
					new TabDescriptor("constraint", //$NON-NLS-1$
							ConfigurationEditorPlugin.INSTANCE.getString("_UI_PropertyTab_Constraint_label"), //$NON-NLS-1$
							ConstraintPropertySection.class)
				};
		}
		else
		{
			// default tab
			return new AbstractTabDescriptor[]
				{
					defaultTab
				};
		}
	}

	/**
	 * Returns whether the constraint tab should be shown. The constraint
	 * tab will be show, if a model tab is active in the editor, and
	 * an EClass or EReference is selected, that is in the slicing configuration.
	 * Furthermore, the part must be a {@link ConfigurationEditor} and the
	 * selection must be an {@link IStructuredSelection}.
	 * @param part the workbench part, must be ConfigurationEditor
	 * @param selection the current selection in the part
	 * @return whether the constraint tab should be shown
	 */
	protected boolean shouldShowConstraint(IWorkbenchPart part, ISelection selection)
	{
		if(part instanceof ConfigurationEditor && selection instanceof IStructuredSelection) // check type
		{
			final ConfigurationEditor confEditor = (ConfigurationEditor)part;
			final SlicingConfiguration slicingConfig = confEditor.getConfig();
			
			// only show the constraint tab if the configuration tab is not active
			// and the slicing configuration was loaded successfully
			if(!confEditor.isConfigurationTabActive() && slicingConfig != null)
			{
				Object selectedElement = ((IStructuredSelection)selection).getFirstElement();
				
				// check if the element is currently in the configuration
				// only EClass and EReference support constaints
				return (selectedElement instanceof EClass
							&& ConfigurationUtil.findSlicedEClass(slicingConfig, (EClass)selectedElement) != null)
					|| (selectedElement instanceof EReference
							&& ConfigurationUtil.findSlicedEReference(slicingConfig, (EReference)selectedElement) != null);
			}
		}
		
		return false;
	}

	/**
	 * A custom TabDescriptor which holds a SectionDescriptor.
	 * @author rmueller
	 *
	 */
	class TabDescriptor extends AbstractTabDescriptor
	{
		private String idExt;
		private String label;
		private Class<? extends ISection> sectionClass;
		
		/**
		 * Creates a TabDescriptor.
		 * @param idExt extension for tab id, the id prefix is constant
		 * @param label label for the tab
		 * @param sectionClass class of the section that will be shown; the class must have a public default constructor
		 */
		TabDescriptor(String idExt, String label, Class<? extends ISection> sectionClass)
		{
			this.idExt = idExt;
			this.label = label;
			this.sectionClass = sectionClass;
		}
		
		@Override
		public String getCategory()
		{
			return ""; //$NON-NLS-1$
		}

		@Override
		public String getId()
		{
			return "org.sidiff.slicer.structural.configuration.editor.tabs." + idExt; //$NON-NLS-1$
		}

		@Override
		public String getLabel()
		{
			return label;
		}
		
		@Override
		public List<AbstractSectionDescriptor> getSectionDescriptors()
		{
			List<AbstractSectionDescriptor> sections = new ArrayList<AbstractSectionDescriptor>();
			sections.add(new SectionDescriptor(getId(), sectionClass));
			return sections;
		}
	}
	
	/**
	 * A custom SectionDescriptor that creates the section by instantiating the supplied class.
	 * @author rmueller
	 *
	 */
	class SectionDescriptor extends AbstractSectionDescriptor
	{
		private String tabId;
		private Class<? extends ISection> sectionClass;
		
		/**
		 * Creates a new SectionDescriptor.
		 * @param tabId the id of the tab containing the section
		 * @param sectionClass class of the section that will be shown; the class must have a public default constructor
		 */
		SectionDescriptor(String tabId, Class<? extends ISection> sectionClass)
		{
			this.tabId = tabId;
			this.sectionClass = sectionClass;
		}
		
		@Override
		public String getId()
		{
			return tabId + ".section"; //$NON-NLS-1$
		}

		@Override
		public ISection getSectionClass()
		{
			// instantiate the class
			try
			{
				return sectionClass.newInstance();
			}
			catch(InstantiationException e)
			{
				e.printStackTrace();
				return null;
			}
			catch(IllegalAccessException e)
			{
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public String getTargetTab()
		{
			return tabId;
		}
	}
}
