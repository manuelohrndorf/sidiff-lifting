package org.sidiff.integration.editor.highlighting.internal;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.sidiff.integration.editor.highlighting.EditorHighlighting;
import org.sidiff.integration.editor.highlighting.StyledObject;
import org.sidiff.integration.editor.highlighting.internal.gmf.SelectionControllerDiagram;
import org.sidiff.integration.editor.highlighting.internal.tree.SelectionControllerTreeViewer;

public class SelectionController implements ISelectionListener, ISelectionChangedListener, INullSelectionListener {

	private static SelectionController instance;
	
	private Collection<StyledObject> selected = new ArrayList<>();
	private boolean enabled = true;

	public static SelectionController getInstance() {
		if (instance == null) {
			instance = new SelectionController();
		}
		return instance;
	}
	
	public Collection<StyledObject> getSelected() {
		return selected;
	}
	
	public synchronized void setSelection(Collection<StyledObject> selected) {
		// Set new selection:
		this.selected = selected;
		refresh();
	}
	
	public void setSelection(ISelection selection) {
		setSelection(EditorHighlighting.getInstance().getElements(selection));
	}

	public void appendSelection(Collection<StyledObject> selection) {
		Collection<StyledObject> newSelection = new ArrayList<>(getSelected());
		newSelection.addAll(selection);
		setSelection(newSelection);
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
