package org.sidiff.integration.editor.highlighting.internal;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.commands.State;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.sidiff.integration.editor.highlighting.EditorHighlighting;
import org.sidiff.integration.editor.highlighting.StyledObject;
import org.sidiff.integration.editor.highlighting.internal.gmf.SelectionControllerDiagram;
import org.sidiff.integration.editor.highlighting.internal.tree.SelectionControllerTreeViewer;

public class SelectionController implements ISelectionListener, ISelectionChangedListener, INullSelectionListener {

	private static SelectionController instance;

	private Collection<StyledObject> selected = new ArrayList<>();
	private boolean enabled;

	private SelectionController() {
		this.enabled = getInitialEnabledState();
	}

	private boolean getInitialEnabledState() {
		State state = PlatformUI.getWorkbench().getService(ICommandService.class)
				.getCommand("org.sidiff.integration.editor.highlighting.commands.Toggle")
				.getState("org.eclipse.ui.commands.toggleState");
		return (Boolean)state.getValue();
	}

	public static SelectionController getInstance() {
		if (instance == null) {
			instance = new SelectionController();
		}
		return instance;
	}

	public Collection<StyledObject> getSelected() {
		synchronized (selected) {
			return new ArrayList<>(selected);
		}
	}

	public void setSelection(Collection<StyledObject> newSelection) {
		synchronized (selected) {
			selected.clear();
			selected.addAll(newSelection);			
		}
		refresh();
	}

	public void setSelection(ISelection selection) {
		setSelection(EditorHighlighting.getInstance().getElements(selection));
	}

	public void appendSelection(Collection<StyledObject> appendedSelection) {
		synchronized (selected) {
			selected.addAll(appendedSelection);
		}
		refresh();
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		setSelection(selection);
	}
	
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		setSelection(event.getSelection());
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		refresh();
	}

	private void refresh() {
		SelectionControllerDiagram.getInstance().refresh();
		SelectionControllerTreeViewer.getInstance().refresh();
	}
}
