package org.silift.difference.rulereviser;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ReviserHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveMenuSelection(event);
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof IFile) {
			IFile iFile = (IFile) firstElement;
			if(iFile.getFileExtension().equals("henshin")){
				ERReviser reviser = new ERReviser();
				reviser.initModule(iFile.getParent().getLocation().toOSString(), iFile.getName());
				reviser.reviseHenshinModule();
			}
		}
		return null;
	}
}
