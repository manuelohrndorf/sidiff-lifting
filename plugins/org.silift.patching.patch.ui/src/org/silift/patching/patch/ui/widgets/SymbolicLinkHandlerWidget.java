package org.silift.patching.patch.ui.widgets;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.lifting.settings.ISettingsChangedListener;
import org.sidiff.difference.lifting.settings.Settings;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetSelection;
import org.silift.common.util.ui.widgets.IWidgetValidation;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

public class SymbolicLinkHandlerWidget implements IWidget, IWidgetSelection, IWidgetValidation, ISettingsChangedListener{

	private Settings settings;
	private SortedMap<String, ISymbolicLinkHandler> symbolicLinkHandlers;
	private Composite container;
	private Button use_symbolicLinks;
	private List list_symbolicLinkHandlers;
	
	public SymbolicLinkHandlerWidget() {
		super();
		symbolicLinkHandlers = new TreeMap<String, ISymbolicLinkHandler>();

		// Search registered symbolic link handler extension
		Set<ISymbolicLinkHandler> symbolicLinkHandlerSet = SymbolicLinkHandlerUtil.getAvailableSymbolicLinkHandlers();

		for (Iterator<ISymbolicLinkHandler> iterator = symbolicLinkHandlerSet.iterator(); iterator.hasNext();) {
			ISymbolicLinkHandler symbolicLinkHandler = iterator.next();
			symbolicLinkHandlers.put(symbolicLinkHandler.getName(), symbolicLinkHandler);
		}
	}

	@Override
	public boolean validate() {
		if (list_symbolicLinkHandlers.getSelectionIndex() != -1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getValidationMessage() {
		if (validate()) {
			return "";
		} else {
			return "Please select a symbolic link resolver!";
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if (list_symbolicLinkHandlers == null) {
			throw new RuntimeException("Create controls first!");
		}
		list_symbolicLinkHandlers.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		if (list_symbolicLinkHandlers != null) {
			list_symbolicLinkHandlers.removeSelectionListener(listener);
		}
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
		use_symbolicLinks.setText("use symbolic links");
		use_symbolicLinks.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				Button button = (Button) e.widget;
				if(!button.getSelection()){
					list_symbolicLinkHandlers.setEnabled(false);
					list_symbolicLinkHandlers.deselectAll();
					settings.setSymbolicLinkHandler(null);
				}else{
					list_symbolicLinkHandlers.setEnabled(true);
					list_symbolicLinkHandlers.setSelection(0);
					settings.setSymbolicLinkHandler(getSelection());
				}
			}
		});
		
		list_symbolicLinkHandlers = new List(container, SWT.SINGLE | SWT.BORDER
				| SWT.V_SCROLL);
		{
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 70;
			list_symbolicLinkHandlers.setLayoutData(data);
		}
		
		list_symbolicLinkHandlers.setItems(symbolicLinkHandlers.keySet().toArray(new String[0]));
		
		if(list_symbolicLinkHandlers.getItems().length != 0){
			list_symbolicLinkHandlers.select(0);
		}else{
			MessageDialog.openError(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Missing Symbolic Link Resolver", "No symbolic link resolvers are found!");
		}
		
		list_symbolicLinkHandlers.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				(settings).setSymbolicLinkHandler(getSelection());;
			}		
		});
		
		list_symbolicLinkHandlers.deselectAll();
		list_symbolicLinkHandlers.setEnabled(false);
		
		return container;
	}

	@Override
	public Composite getWidget() {
		return container;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		container.setLayoutData(layoutData);
	}
	
	public ISymbolicLinkHandler getSelection(){
		if (validate()) {
			return symbolicLinkHandlers.get(list_symbolicLinkHandlers.getSelection()[0]);
		} else {
			return null;
		}
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public Composite getContainer() {
		return container;
	}

	public void setContainer(Composite container) {
		this.container = container;
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		// TODO Auto-generated method stub
		
	}
}