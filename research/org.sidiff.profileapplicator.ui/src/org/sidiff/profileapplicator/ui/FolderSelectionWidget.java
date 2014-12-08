package org.sidiff.profileapplicator.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.silift.common.util.ui.widgets.IWidget;
import org.silift.common.util.ui.widgets.IWidgetInformation;
import org.silift.common.util.ui.widgets.IWidgetValidation;

public class FolderSelectionWidget implements IWidget, IWidgetValidation,
		IWidgetInformation {

	/* Default values */
	private final String name;
	private final String path;
	private final boolean mustExist;
	

	/* State */
	public static final int MESSAGE_EMPTY = 1;
	public static final int MESSAGE_NONEXISTANT = 2;
	private int state = 0;

	/* Parent */
	private Composite parent;

	/* Components */
	private final DirectoryDialog dirDialog = new DirectoryDialog(Display.getCurrent().getActiveShell());
	private Composite composite;
	private Text folderPath;
	private Button browse;
	
	public FolderSelectionWidget(String name, String defaultPath, boolean mustExist) {
		this.name=name;
		this.path = defaultPath;
		this.mustExist=mustExist;
	}

	@Override
	public String getInformationMessage() {
		return null;
	}

	@Override
	public boolean validate() {
		String path=folderPath.getText();
		if (path == null || path.isEmpty()){
			state=MESSAGE_EMPTY;
		} else if (mustExist && !(new java.io.File(path).isDirectory())){
			state = MESSAGE_NONEXISTANT;
		} else {
			state = 0;
		}
		return (state == 0);
	}

	@Override
	public String getValidationMessage() {
		switch (state) {
		case MESSAGE_EMPTY:
			return name+" must not be empty";
		case MESSAGE_NONEXISTANT:
			return name+" does not exist";
		default:
			return null;
		}
	}

	@Override
	public Composite createControl(Composite parent) {
		this.parent = parent;
		composite = new Composite(parent, SWT.NONE);
		GridLayout layout=new GridLayout(2, false);
		composite.setLayout(layout);
		folderPath = new Text(composite, SWT.BORDER | SWT.SINGLE);
		folderPath.setText(path);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		folderPath.setLayoutData(gd);
		browse=new Button(composite, SWT.PUSH);
		browse.setText("Browse...");
		browse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String ret=dirDialog.open();
				if (ret != null) folderPath.setText(ret);
			}
		});
		return parent;
	}

	@Override
	public Composite getWidget() {
		return parent;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		composite.setLayoutData(layoutData);
	}

	public void addModifiedListener(ModifyListener listener){
		folderPath.addModifyListener(listener);
	}

	public String getText() {
		return folderPath.getText();
	}
}
