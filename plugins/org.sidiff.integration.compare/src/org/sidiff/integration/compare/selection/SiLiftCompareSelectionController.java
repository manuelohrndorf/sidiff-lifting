package org.sidiff.integration.compare.selection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;
import org.sidiff.common.ui.util.PropertySheetUtil;
import org.sidiff.integration.compare.internal.CompareIntegrationPlugin;
import org.sidiff.integration.editor.highlighting.EditorHighlighting;

/**
 * The SiLiftCompareSelectionController is a {@link ISelectionProvider} and
 * handles the selection in the compare editor.
 * This class also implements {@link ISelectionChangedListener} to propagate
 * the selection to all registered listeners.
 * @author rmueller
 */
public class SiLiftCompareSelectionController implements ISelectionProvider, ISelectionChangedListener {

	private static SiLiftCompareSelectionController instance;

	public static SiLiftCompareSelectionController getInstance() {
		if(instance == null) {
			instance = new SiLiftCompareSelectionController();
		}
		return instance;
	}

	private ListenerList<ISelectionChangedListener> listeners = new ListenerList<>();
	private ISelection selection;
	private boolean propagatingSelection;

	public SiLiftCompareSelectionController() {
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
				PropertySheetUtil.notifySelectionChanged(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart(),
						event.getSelection());
			}
		});
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);
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
				StatusManager.getManager().handle(new Status(IStatus.ERROR, CompareIntegrationPlugin.ID,
						"Uncaught exception in ISelectionChangedListener", e));
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
