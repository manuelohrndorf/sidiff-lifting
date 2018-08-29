package org.sidiff.slicer.structural.configuration.presentation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.IToolTipProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.services.IDisposable;
import org.sidiff.slicer.structural.configuration.logic.ISlicingLogic;
import org.sidiff.slicer.structural.configuration.logic.ISlicingLogic.CheckboxState;
import org.sidiff.slicer.structural.configuration.logic.SlicingLogic;

/**
 * This class implements
 * <ul>
 * <li>{@link AdapterFactoryLabelProvider.StyledLabelProvider}</li>
 * <li>{@link ICheckStateProvider}</li>
 * <li>{@link ICheckStateListener}</li>
 * <li>{@link ILabelDecorator}</li>
 * <li>{@link IToolTipProvider}</li>
 * </ul>
 * and delegates the implemented functions to the slicing logic.<br>
 * The class also implements {@link IDisposable} to dispose all images that were created by the delegate.<br>
 * Each delegate creates its own slicing logic.
 * @author rmueller
 *
 */
class SlicingLogicDelegate extends AdapterFactoryLabelProvider.StyledLabelProvider
	implements ICheckStateProvider, ICheckStateListener, ILabelDecorator, IDisposable, IToolTipProvider
{

	/**
	 * The configuration editor
	 */
	protected ConfigurationEditor editor;
	
	/**
	 * The slicing logic
	 */
	protected ISlicingLogic slicingLogic;
	
	/**
	 * The model viewer
	 */
	protected CheckboxTreeViewer viewer;

	/**
	 * Collection of images that need to be disposed when the viewer that uses this SlicingLogicDelegate is disposed
	 */
	protected Collection<Image> imagesToDispose;

	/**
	 * Flag for {@link #dispose()} to only dispose the object once
	 */
	private boolean disposed;

	/**
	 * Create a new SlicingLogicDelegate. The delegate creates a new {@link SlicingLogic}.
	 * @param editor the configuration editor
	 * @param adapterFactory adapter factory for model elements
	 * @param viewer model viewer
	 * @param modelResource resource of the model
	 */
	public SlicingLogicDelegate(ConfigurationEditor editor, AdapterFactory adapterFactory, CheckboxTreeViewer viewer, Resource modelResource)
	{
		super(adapterFactory, viewer);
		Assert.isNotNull(editor);
		Assert.isNotNull(adapterFactory);
		Assert.isNotNull(viewer);
		Assert.isNotNull(modelResource);
		this.editor = editor;
		this.viewer = viewer;
		this.imagesToDispose = new LinkedList<Image>();
		this.disposed = false;
		this.slicingLogic = new SlicingLogic(editor, modelResource);
	}

	@Override
	public StyledString getStyledText(Object object)
	{
		StyledString original = super.getStyledText(object);
		StyledString styled = slicingLogic.getDecoratedText(original, object);
		return styled != null ? styled : original;
	}

	@Override
	public Color getForeground(Object object)
	{
		return slicingLogic.getForegroundColor(object);
	}

	@Override
	public Color getBackground(Object object)
	{
		return slicingLogic.getBackgroundColor(object);
	}

	@Override
	public Font getFont(Object object)
	{
		int fontStyle = slicingLogic.getFontStyle(object);

		// create new font if style is not normal
		if(fontStyle != SWT.NORMAL)
		{
			// copy the font descriptor of the default font
			FontDescriptor fontDescriptor = FontDescriptor.createFrom(viewer.getControl().getFont());

			// set style and create font from font descriptor
			return fontDescriptor.setStyle(fontStyle).createFont(Display.getCurrent());
		}

		// default font
		return super.getFont(object);
	}

	@Override
	public void checkStateChanged(CheckStateChangedEvent event)
	{
		Object elem = event.getElement();
		if(elem instanceof EObject)
		{
			Set<EObject> changedElements = slicingLogic.checkStateChanged((EObject)elem, event.getChecked());

			// update layout
			if(changedElements == null)
			{
				viewer.refresh();
			}
			else if(!changedElements.isEmpty())
			{
				viewer.update(changedElements.toArray(), null);
			}

			editor.analyzeImportedResources();
			editor.refreshPropertySheetPages(elem);
			editor.refreshAlternativeElementsView();
		}

		editor.updateProblemIndication(false);
	}

	@Override
	public boolean isGrayed(Object element)
	{
		return slicingLogic.getCheckboxState(element) == CheckboxState.GRAYED;
	}

	@Override
	public boolean isChecked(Object element)
	{
		return slicingLogic.getCheckboxState(element) != CheckboxState.NOT_CHECKED;
	}

	@Override
	public Image decorateImage(Image image, Object element)
	{
		// get descriptor of decorated image from slicing logic
		final ImageDescriptor desc = slicingLogic.getDecoratedImage(image, element);
		if(desc == null)
			return null;

		// create image from image descriptor and add it to the image list
		// so it can be disposed when the viewer is disposed
		final Image i = desc.createImage();
		imagesToDispose.add(i);
		return i;
	}

	@Override
	public String decorateText(String text, Object element)
	{
		// no text decoration; text is decorated with getStyledText
		return null;
	}

	public Set<EObject> getAlternativeElements(EObject element)
	{		
		final Set<EObject> elements = slicingLogic.getAlternativeElements(element);
		return elements == null ? Collections.emptySet() : elements;
	}

	/**
	 * Disposes of this delegate. Disposes all images that were created by {@link #decorateImage(Image, Object)}.
	 * Disposes the slicing logic. Calls through to super.dispose() to dispose of the {@link AdapterFactoryLabelProvider}.
	 */
	@Override
	public void dispose()
	{
		// check if already disposed
		if(disposed)
			return;
		disposed = true;
		
		// dispose all images that were created by this delegate
		for(Image i : imagesToDispose)
		{
			i.dispose();
		}
		imagesToDispose.clear();
		
		// dispose the slicing logic
		slicingLogic.dispose();
		
		// call through
		super.dispose();
	}

	@Override
	public String getToolTipText(Object element)
	{
		return slicingLogic.getToolTipText(element);
	}
}
