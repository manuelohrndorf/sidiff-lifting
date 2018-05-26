package org.sidiff.patching.patch.ui.widgets;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

public class SymbolicLinkHandlerWidget extends AbstractWidget implements IWidgetSelection, IWidgetValidation, ISettingsChangedListener{

	private PatchingSettings settings;
	private SortedMap<String, ISymbolicLinkHandler> symbolicLinkHandlers;
	private boolean symbolicLinkHandlerAvailable;

	private Composite container;
	private Button use_symbolicLinks;
	private List list_symbolicLinkHandlers;

	public SymbolicLinkHandlerWidget() {
		// Search registered symbolic link handler extension
		Set<ISymbolicLinkHandler> symbolicLinkHandlerSet = SymbolicLinkHandlerUtil.getAvailableSymbolicLinkHandlers();
		symbolicLinkHandlerAvailable = !symbolicLinkHandlerSet.isEmpty();
		symbolicLinkHandlers = new TreeMap<String, ISymbolicLinkHandler>();
		for (ISymbolicLinkHandler symbolicLinkHandler : symbolicLinkHandlerSet) {
			symbolicLinkHandlers.put(symbolicLinkHandler.getName(), symbolicLinkHandler);
		}
	}

	@Override
	public boolean validate() {
		return !use_symbolicLinks.getSelection() || list_symbolicLinkHandlers.getSelectionIndex() != -1;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		if (validate()) {
			return ValidationMessage.OK;
		} else {
			return new ValidationMessage(ValidationType.ERROR, "Please select a symbolic link resolver!");
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if(list_symbolicLinkHandlers == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		list_symbolicLinkHandlers.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if(list_symbolicLinkHandlers == null) {
			throw new IllegalStateException("createControl must be called first");
		}
		list_symbolicLinkHandlers.removeSelectionListener(listener);
	}

	@Override
	public Composite createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		{
			GridLayout grid = new GridLayout(1, false);
			grid.marginWidth = 0;
			grid.marginHeight = 0;
			container.setLayout(grid);
		}

		// Symbolic link resolver controls:
		Label slrLabel = new Label(container, SWT.NONE);
		slrLabel.setText("Symbolic Link Resolver:");

		use_symbolicLinks = new Button(container, SWT.CHECK);
		use_symbolicLinks.setText("Use symbolic links");
		use_symbolicLinks.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				list_symbolicLinkHandlers.setEnabled(use_symbolicLinks.getSelection());
				ISymbolicLinkHandler selection = getSelection();
				if(selection != null) {
					settings.setSymbolicLinkHandler(selection);
				}
			}
		});

		list_symbolicLinkHandlers = new List(container, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 70;
			list_symbolicLinkHandlers.setLayoutData(data);
		}
		list_symbolicLinkHandlers.setItems(symbolicLinkHandlers.keySet().toArray(new String[0]));
		list_symbolicLinkHandlers.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				settings.setSymbolicLinkHandler(getSelection());
			}		
		});

		updateSelection();
		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	public ISymbolicLinkHandler getSelection() {
		if (use_symbolicLinks.getSelection() && list_symbolicLinkHandlers.getSelectionIndex() != -1) {
			return symbolicLinkHandlers.get(list_symbolicLinkHandlers.getSelection()[0]);
		} else {
			return null;
		}
	}

	public PatchingSettings getSettings() {
		return settings;
	}

	public void setSettings(PatchingSettings settings) {
		this.settings = settings;
		this.settings.addSettingsChangedListener(this);
		updateSelection();
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if(item == PatchingSettingsItem.SYMBOLIC_LINK_HANDLER) {
			updateSelection();
			getWidgetCallback().requestValidation();
		}
	}

	private void updateSelection() {
		if(use_symbolicLinks == null || list_symbolicLinkHandlers == null) {
			return;
		}

		if(settings.useSymbolicLinks()) {
			int index = list_symbolicLinkHandlers.indexOf(settings.getSymbolicLinkHandler().getName());
			if(index == -1) {
				// default to the first available symbolic link handler
				index = 0;
			}
			use_symbolicLinks.setSelection(true);
			list_symbolicLinkHandlers.setEnabled(true);
			list_symbolicLinkHandlers.setSelection(index);
		} else {
			use_symbolicLinks.setSelection(false);
			list_symbolicLinkHandlers.setEnabled(false);
			list_symbolicLinkHandlers.deselectAll();
		}
	}

	public boolean isSymbolicLinkHandlerAvailable() {
		return symbolicLinkHandlerAvailable;
	}
}