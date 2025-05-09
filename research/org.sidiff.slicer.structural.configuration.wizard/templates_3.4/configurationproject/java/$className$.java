package $packageName$;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;
import org.sidiff.slicer.ISlicer;
import org.sidiff.slicer.ISlicingConfiguration;
import org.sidiff.slicer.configuration.project.runtime.ISlicingConfigurationProject;
import org.sidiff.slicer.structural.StructureBasedSlicer;
import org.sidiff.slicer.structural.configuration.SlicingConfiguration;

public class $className$ implements ISlicingConfigurationProject
{
	public static final String PLUGIN_ID = "$pluginId$";

	private ISlicer slicerInstance;

	@Override
	public ISlicer getSlicer()
	{
		if(slicerInstance == null)
		{
			slicerInstance = new StructureBasedSlicer();
		}
		return slicerInstance;
	}

	@Override
	public String getName()
	{
		return "$projectName$";
	}

	@Override
	public Set<String> getDocumentTypes()
	{
		Set<String> docTypes = new HashSet<>();
		for(ISlicingConfiguration config : getSlicingConfigurations())
		{
			if(config instanceof SlicingConfiguration)
			{
				docTypes.addAll(((SlicingConfiguration)config).getDocumentTypes());
			}
		}
		return docTypes;
	}

	@Override
	public List<ISlicingConfiguration> getSlicingConfigurations()
	{
		List<ISlicingConfiguration> configurations = new LinkedList<>();
		Enumeration<String> entries = Platform.getBundle(PLUGIN_ID).getEntryPaths("/$configurationFolder$");
		while(entries.hasMoreElements())
		{
			String entry = entries.nextElement();
			if(entry.endsWith(".$configurationFileExtension$"))
			{
				try
				{
					URI uri = URI.createPlatformPluginURI("/" + PLUGIN_ID + "/" + entry, true);
					configurations.add(SiDiffResourceSet.create().loadEObject(uri, SlicingConfiguration.class));
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return configurations;
	}
}