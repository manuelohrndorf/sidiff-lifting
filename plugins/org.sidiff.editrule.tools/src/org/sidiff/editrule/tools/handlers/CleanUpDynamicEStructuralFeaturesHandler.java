package org.sidiff.editrule.tools.handlers;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResource;
import org.eclipse.emf.henshin.model.resource.HenshinResourceFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.modelstorage.EMFStorage;
import org.sidiff.common.emf.modelstorage.SiDiffResourceSet;

public class CleanUpDynamicEStructuralFeaturesHandler extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if(selection instanceof StructuredSelection) {
			IStructuredSelection selected = (IStructuredSelection) selection;
			Set<IFile> files = new HashSet<IFile>();
			for(Iterator<?> iterator = selected.iterator(); iterator.hasNext();) {
				Object obj = iterator.next();
				if(obj instanceof IResource) {
					try {
						files.addAll(findAllHenshinFiles((IResource)obj));
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
		    m.put(HenshinResource.FILE_EXTENSION, new HenshinResourceFactory());
			for(IFile file : files) {
				Module editRule = SiDiffResourceSet.create().loadEObject(EMFStorage.toPlatformURI(file), Module.class);
				if(cleanUpDynamics(editRule)) {
					try {
						editRule.eResource().save(Collections.emptyMap());
						
					} catch (IOException e) {
						MessageDialog.openError(Display.getDefault().getActiveShell(), e.getClass().getSimpleName(), e.getMessage());
						return null;
					}
				}
			}
		}
		
		return null;
	}
	
	public static boolean cleanUpDynamics(Module editRule) {
		Set<Attribute> attributes = new HashSet<Attribute>();
		Set<Edge> edges = new HashSet<Edge>();
		editRule.eAllContents().forEachRemaining(element -> {
			if(element instanceof Attribute) {
				Attribute attribute = (Attribute) element;
				if(EMFUtil.isDynamic(attribute.getType())) {
					attributes.add(attribute);
					System.out.println("Removed dynamic Attribute:\n\n" + attribute.getType().getName() + " in " + EcoreUtil.getURI(editRule).toPlatformString(true));
				}
			}else if(element instanceof Edge) {
				Edge edge = (Edge) element;
				if(EMFUtil.isDynamic(edge.getType())) {
					edges.add(edge);
					System.out.println("Removed dynamic Edge:\n\n" + edge.getType().getName() + " in " + EcoreUtil.getURI(editRule).toPlatformString(true));
				}
			}
		});
		EcoreUtil.deleteAll(edges, false);
	
		return !(edges.isEmpty() && attributes.isEmpty());
	}
	
	public static Set<IFile> findAllHenshinFiles(IResource resource) throws CoreException{
		Set<IFile> files = new HashSet<IFile>();
		if(resource instanceof IFile && resource.getFileExtension().equals(HenshinResource.FILE_EXTENSION)) {
			files.add((IFile)resource);
		}else if(resource instanceof IContainer){
			IContainer container = (IContainer) resource;
			for(IResource child : container.members()) {
				files.addAll(findAllHenshinFiles(child));
			}
		}
		return files;
	}
	

	
}
