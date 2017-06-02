package org.sidiff.integration.editor.highlighting.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.sidiff.integration.editor.highlighting.EditorHighlighting;
import org.sidiff.integration.editor.highlighting.internal.gmf.SelectionControllerDiagram;
import org.sidiff.integration.editor.highlighting.internal.tree.SelectionControllerTreeViewer;

public class SelectionController implements ISelectionListener, ISelectionChangedListener, INullSelectionListener {

	private static SelectionController instance;

	private CountDownLatch highlightingProcess;
	
	private List<EObject> selected = new ArrayList<>();

	public static SelectionController getInstance() {
		if (instance == null) {
			instance = new SelectionController();
		}
		return instance;
	}
	
	public List<EObject> getSelected() {
		return selected;
	}
	
	public synchronized void setSelection(List<EObject> selected) {
		
		// Synchronize previous highlighting:
		if (highlightingProcess != null) {
			try {
				highlightingProcess.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Set new selection:
		this.selected = selected;
		
		// Start the highlighting:
		highlightingProcess = new CountDownLatch(1);
		
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				SelectionControllerDiagram.getInstance().setSelection(selected);
				SelectionControllerTreeViewer.getInstance().setSelection(selected);
				highlightingProcess.countDown();
			}
		});
	}
	
	public synchronized void setSelection(ISelection selection) {
		setSelection(EditorHighlighting.getInstance().getElements(selection));
	}
	
	public synchronized void appendSelection(List<EObject> selection) {
		this.selected.addAll(selection);
		setSelection(this.selected);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		setSelection(selection);
	}
	
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		setSelection(event.getSelection());
	}
}
