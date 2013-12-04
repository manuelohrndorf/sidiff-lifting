package org.sidiff.difference.patch.animation.internal;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.difference.patch.animation.GMFAnimation;

public class EnableAnimationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
	    Command command = event.getCommand();
	    boolean oldValue = HandlerUtil.toggleCommandState(command);

	    EcoreEditor editor = (EcoreEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	    ResourceSet resourceSet = editor.getEditingDomain().getResourceSet();
	    
	    if(!oldValue){
	    	for(Resource resource : resourceSet.getResources()){
	    		GMFAnimation.enableAnimation(resource);
	    	}
	    } else {
	    	for(Resource resource : resourceSet.getResources()){
	    		GMFAnimation.disableAnimation(resource);
	    	}
	    }
	    
		return null;
	}

}
