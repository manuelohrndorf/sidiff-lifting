package org.sidiff.difference.patch.animation.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.ui.IFileEditorInput;
import org.sidiff.difference.patch.animation.GMFAnimation.EditorMatching;

public class AnimationAdapter extends EContentAdapter {
		
	private EditorMatching[] matchings = null;
	private boolean triggerMode = false;
	private List<Notification> notificationQueue = new ArrayList<Notification>();
	
	public AnimationAdapter(EditorMatching[] matchings, boolean triggerMode) {
		super();
		
		this.matchings = matchings;
		this.triggerMode = triggerMode;
	}

	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		
		if(this.triggerMode){
			this.notificationQueue.add(notification);
		} else {
			for(EditorMatching editorMatching : matchings){
				DiagramEditor editor = editorMatching.editor;

				List<IFile> affectedFiles = new ArrayList<IFile>();
				affectedFiles.add(((IFileEditorInput) editor.getEditorInput()).getFile());
				AnimateChangeCommand operation = new AnimateChangeCommand(editor.getEditingDomain(), "Reflect external change", affectedFiles, notification, editorMatching);

				CompositeCommand compositeCommand = new CompositeCommand("Reflect external change");
				compositeCommand.add(operation);

				ICommandProxy command = new ICommandProxy(compositeCommand);

				editor.getDiagramEditDomain().getDiagramCommandStack().execute(command);

			}
		}
	}
	
	public void trigger(){
		for(Notification notification : this.notificationQueue){
			for(EditorMatching editorMatching : matchings){
				DiagramEditor editor = editorMatching.editor;

				List<IFile> affectedFiles = new ArrayList<IFile>();
				affectedFiles.add(((IFileEditorInput) editor.getEditorInput()).getFile());
				AnimateChangeCommand operation = new AnimateChangeCommand(editor.getEditingDomain(), "Reflect external change", affectedFiles, notification, editorMatching);

				CompositeCommand compositeCommand = new CompositeCommand("Reflect external change");
				compositeCommand.add(operation);

				ICommandProxy command = new ICommandProxy(compositeCommand);

				editor.getDiagramEditDomain().getDiagramCommandStack().execute(command);

			}
		}
		this.notificationQueue.clear();
	}
}
