package org.sidiff.integration.selection;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.PlatformUI;
import org.sidiff.integration.Activator;
import org.sidiff.integration.editor.highlighting.EditorHighlighting;
import org.sidiff.integration.properties.PropertySheetPageHelper;

/**
 * The SiLiftCompareSelectionController is a {@link ISelectionProvider} and
 * handles the selection in the compare editor.
 * This class also implements {@link ISelectionChangedListener} to propagate
 * the selection to all registered listeners.
 * @author Robert Müller
 *
 */
public class SiLiftCompareSelectionController implements ISelectionProvider, ISelectionChangedListener {

	private static SiLiftCompareSelectionController instance;

	public static SiLiftCompareSelectionController getInstance() {
		if(instance == null) {
			instance = new SiLiftCompareSelectionController();
		}
		return instance;
	}

	private List<ISelectionChangedListener> listeners;
	private ISelection selection;
	private boolean propagatingSelection;

	public SiLiftCompareSelectionController() {
		this.listeners = new LinkedList<>();
		this.selection = null;
		this.propagatingSelection = false;
		hookUpEditorHighlighting();
		hookUpPropertySheetPage();
	}

	/**
	 * Hooks up the selection controller with the EditorHighlighting-plugin,
	 * allowing the selection to be propagated to the EditorHighlighting.
	 */
	private void hookUpEditorHighlighting() {
		try {
			addSelectionChangedListener(EditorHighlighting.getInstance().getSelectionChangedListener());
		} catch(NoClassDefFoundError e) {
			// The editor highlighting plugin is optional.
			// This exception might be thrown when it is unavailable.
		}
	}

	/**
	 * Hooks up the selection controller with the PropertySheetPageHelper,
	 * allowing the selection to be propagated to the property sheet page.
	 */
	private void hookUpPropertySheetPage() {
		addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				PropertySheetPageHelper.notifiySelectionChanged(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart(),
						event.getSelection());
			}
		});
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public ISelection getSelection() {
		return selection;
	}

	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void setSelection(ISelection selection) {
		this.selection = selection;
		propageSelection();
	}

	protected void propageSelection() {
		for(ISelectionChangedListener listener : listeners) {
			try {
				listener.selectionChanged(new SelectionChangedEvent(this, selection));
			} catch(Exception e) {
				Activator.logWarning("Uncaught exception in ISelectionChangedListener", e);
			}
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if(propagatingSelection) {
			// this function is being called recursively; stop
			return;
		}
		propagatingSelection = true;
		setSelection(event.getSelection());
		propagatingSelection = false;
	}
}
