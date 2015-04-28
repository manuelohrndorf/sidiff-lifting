package org.sidiff.difference.symmetric.compareview.ide.structureviewer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IEncodedStreamContentAccessor;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.ide.ui.internal.EMFCompareIDEUIPlugin;
import org.eclipse.emf.compare.rcp.ui.internal.EMFCompareConstants;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.settings.LiftingSettings;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.compareview.ide.proxy.SiLiftToEMFCompareConverter;
import org.sidiff.difference.symmetric.provider.AdapterToolTipLabelProvider;
import org.sidiff.difference.symmetric.provider.SymmetricItemProviderAdapterFactory;
import org.silift.common.util.access.EMFModelAccessEx;
import org.silift.common.util.exceptions.NoCorrespondencesException;

public class SiLiftStructureViewer extends TreeViewer  {

	CompareConfiguration config;
	ComposedAdapterFactory adapterFactory;
	
	// TEST:
	private static String matcherName = "NamedElement";
	
	public SiLiftStructureViewer(Composite parent, CompareConfiguration config) {
		super(parent, SWT.MULTI);
		parent.setLayout(new GridLayout(1, false));
		
		this.config = config;
		
		// Create an adapter factory that yields item providers:
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		
		adapterFactory.addAdapterFactory(new SymmetricItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());

		// Set item and label provider:
		setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				TreeViewer viewer = (TreeViewer) event.getViewer();
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
				Object selectedNode = thisSelection.getFirstElement();
				viewer.setExpandedState(selectedNode, !viewer.getExpandedState(selectedNode));
			}
		});

		// Set tool tip:
		ColumnViewerToolTipSupport.enableFor(this);
		AdapterToolTipLabelProvider toolTip = new AdapterToolTipLabelProvider(adapterFactory);
		toolTip.setDefaultFont(getControl().getFont());
		setLabelProvider(toolTip);
	}

	@Override
	protected void inputChanged(Object input, Object oldInput) {

		if(input != oldInput) {
			SymmetricDifference diff = diff(input);
			
			if (diff != null) {
				setInput(diff);
				refresh();
			}
		}
	}

	private SymmetricDifference diff(Object input) {

		if (input instanceof ICompareInput) {
			ITypedElement left = ((ICompareInput) input).getLeft();
			ITypedElement right = ((ICompareInput) input).getRight();
			
			Resource rightResource = null;
			Resource leftResource = null;
			
			try {
				ResourceSet resourceSet = new ResourceSetImpl();
				
				// Load the left resource from the input stream:
				if (left instanceof IStreamContentAccessor) {
					InputStream leftIn = ((IEncodedStreamContentAccessor) left).getContents();
					leftResource = resourceSet.createResource(URI.createURI(left.getName()));
					leftResource.load(leftIn, null);
				}
				
				// Load the right resource from the input stream:
				if (right instanceof IStreamContentAccessor) {
					InputStream rightIn = ((IEncodedStreamContentAccessor) right).getContents();
					rightResource = resourceSet.createResource(URI.createURI(right.getName()));
					rightResource.load(rightIn, null);
				}
			} catch (CoreException | IOException e) {
				e.printStackTrace();
			}
			
			System.out.println(leftResource);
			System.out.println(rightResource);
			
			try {
				// Run lifting:
				LiftingSettings settings =  buildSettings(rightResource, leftResource);
				SymmetricDifference diff = LiftingFacade.liftMeUp(rightResource, leftResource, settings);
				
				// Convert to the EMF-Compare difference model:
				SiLiftToEMFCompareConverter converter = new SiLiftToEMFCompareConverter(diff);
				config.setProperty(EMFCompareIDEUIPlugin.PLUGIN_ID + ".COMPARE_RESULT", converter.getEmfcompareDiff());
				// NOTE: EMFCompareConstants.COMPARE_RESULT -> Discouraged access -> As in EMF-Compare itself!?
				
				return diff;
			} catch (InvalidModelException | NoCorrespondencesException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	private static LiftingSettings buildSettings(Resource modelA, Resource modelB) {
		String documentType = EMFModelAccessEx.getCharacteristicDocumentType(modelA);
		IMatcher matcher = getMatcher(modelA, modelB);
		
		LiftingSettings settings = new LiftingSettings(documentType);
		settings.setMatcher(matcher);
		
		settings.setUseThreadPool(false);
		
		return settings;
	}
	
	private static IMatcher getMatcher(Resource modelA, Resource modelB) {
		Set<IMatcher> matchers = LiftingFacade.getAvailableMatchers(modelA, modelB);
		
		for (IMatcher matcher : matchers) {
			if (matcher.getName().toLowerCase().contains(matcherName.toLowerCase())) {
				return matcher;
			}
		}
		
		throw new RuntimeException("Matcher not found: " + matcherName);
	}
}
