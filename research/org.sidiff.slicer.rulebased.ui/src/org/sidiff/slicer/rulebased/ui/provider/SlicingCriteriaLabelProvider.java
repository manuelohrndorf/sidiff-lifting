package org.sidiff.slicer.rulebased.ui.provider;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class SlicingCriteriaLabelProvider implements IColorProvider, ILabelProvider {

	private AdapterFactoryLabelProvider adapterFactoryLabelProvider;
	
	private Set<EObject> addElements;
	private Set<EObject> remElements;
	
	public SlicingCriteriaLabelProvider(AdapterFactoryLabelProvider adapterFactoryLabelProvider) {
		super();
		this.adapterFactoryLabelProvider = adapterFactoryLabelProvider;
		this.addElements = new HashSet<EObject>();
		this.remElements = new HashSet<EObject>();
	}

	@Override
	public Color getForeground(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		if(addElements.contains(element)){
			return new Color(Display.getCurrent(), 255, 255, 0);
		}else if(remElements.contains(element)){
			return new Color(Display.getCurrent(), 200, 0, 0);
		}
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		adapterFactoryLabelProvider.addListener(listener);
	}

	@Override
	public void dispose() {
		adapterFactoryLabelProvider.dispose();		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return adapterFactoryLabelProvider.isLabelProperty(element, property);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		adapterFactoryLabelProvider.removeListener(listener);
	}

	@Override
	public Image getImage(Object element) {
		return adapterFactoryLabelProvider.getImage(element);
	}

	@Override
	public String getText(Object element) {
		return adapterFactoryLabelProvider.getText(element);
	}

	public Set<EObject> getAddElements() {
		return addElements;
	}

	public void setAddElements(Set<EObject> addElements) {
		this.addElements = addElements;
	}

	public Set<EObject> getRemElements() {
		return remElements;
	}

	public void setRemElements(Set<EObject> remElements) {
		this.remElements = remElements;
	}

}
