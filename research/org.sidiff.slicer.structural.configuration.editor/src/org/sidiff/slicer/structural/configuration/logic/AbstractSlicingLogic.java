package org.sidiff.slicer.structural.configuration.logic;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.sidiff.slicer.structural.configuration.presentation.IConfigurationEditor;

/**
 * Abstract implementation of {@link ISlicingLogic}.
 * Has two final attributes:
 * <ul>
 * <li>{@link #configurationEditor}: interface to the configuration editor</li>
 * <li>{@link #modelResource}: resource of the model that this slicing logic uses</li>
 * </ul>
 * Contains utility functions to create colors from preference strings and RGB
 * values that are automatically disposed when the slicing logic is disposed:
 * <ul>
 * <li>{@link #createColor(String)}: create a color from a string with the format <code>r,g,b</code> with r, g and b from 0-255</li>
 * <li>{@link #createColor(RGB)}: create a color from an RGB value</li>
 * </ul>
 * @author rmueller
 *
 */
public abstract class AbstractSlicingLogic implements ISlicingLogic
{
	/**
	 * Interface to the configuration editor
	 */
	protected final IConfigurationEditor configurationEditor;

	/**
	 * Resource of the model that the slicing logic works on
	 */
	protected final Resource modelResource;

	/**
	 * List of colors that were create by {@link #createColor(RGB)} and are
	 * to be disposed when the slicing logic is disposed.
	 */
	private List<Color> disposeableColors;

	/**
	 * Classes extending AbstractSlicingLogic must supply a reference to the configuration editor and a model resource
	 * @param configurationEditor interface of the configuration editor, may not be <code>null</code>
	 * @param modelResource resource of the model, may not be <code>null</code>
	 */
	public AbstractSlicingLogic(IConfigurationEditor configurationEditor, Resource modelResource)
	{
		Assert.isNotNull(configurationEditor);
		Assert.isNotNull(modelResource);
		this.configurationEditor = configurationEditor;
		this.modelResource = modelResource;
		this.disposeableColors = new LinkedList<Color>();
	}

	/**
	 * Disposes all colors that were created by {@link #createColor(RGB)}.
	 * Subclasses must call through to super if they override this method.
	 */
	@Override
	public void dispose()
	{
		// dispose all colors
		if(disposeableColors != null)
		{
			for(Color c : disposeableColors)
			{
				c.dispose();
			}
			disposeableColors = null;
		}
	}

	/**
	 * Creates a {@link Color} from the given string. The format of the string must be
	 * <pre>r,g,b</pre> where r, g and b are in the range 0 - 255. This is the default
	 * format of color preferences. If the string could not be parsed, the color
	 * black is returned.
	 * If a color with the same value was created before,
	 * it is returned. If not, a new color is created on the current {@link Device}.
	 * The created color will be disposed when the slicing logic is disposed.
	 * @param colorString a string representing the color, may not be <code>null</code>
	 * @return a color with the specified value, or black, if the string could not be parsed
	 */
	protected final Color createColor(String colorString)
	{
		Assert.isNotNull(colorString);
		
		RGB rgb = StringConverter.asRGB(colorString, null);
		if(rgb == null)
			return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
		return createColor(rgb);
	}

	/**
	 * Creates a {@link Color} from the given {@link RGB}. If a color with the
	 * same value was created before, it is returned. If not, a new color is
	 * created on the current {@link Device}. The created color will be disposed
	 * when the slicing logic is disposed.
	 * @param rgb the value of the color, may not be <code>null</code>
	 * @return a color with the specified rgb value
	 */
	protected final Color createColor(RGB rgb)
	{
		Assert.isNotNull(rgb);
		
		// check if identical color has already been created
		for(Color c : disposeableColors)
		{
			if(c.getRGB().equals(rgb))
			{
				return c;
			}
		}

		// create a new color
		Color c = new Color(Display.getCurrent(), rgb);
		disposeableColors.add(c);
		return c;
	}
}
