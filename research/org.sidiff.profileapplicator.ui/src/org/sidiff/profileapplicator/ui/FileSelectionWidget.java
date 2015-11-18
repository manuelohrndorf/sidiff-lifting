package org.sidiff.profileapplicator.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetInformation;
import org.sidiff.common.ui.widgets.IWidgetValidation;

public class FileSelectionWidget implements IWidget, IWidgetValidation,
		IWidgetInformation {

	/* Default values */
	private final String name;
	private final String defaultFile;
	private final boolean mustExist;
	private final String[] fileExtensions;

	/* State */
	public static final int MESSAGE_EMPTY = 1;
	public static final int MESSAGE_NONEXISTANT = 2;
	private int state = 0;

	/* Parent */
	private Composite parent;

	/* Components */
	private final FileDialog fileDialog = new FileDialog(Display.getCurrent()
			.getActiveShell(), SWT.SINGLE | SWT.OPEN);
	private Composite composite;
	private Text filePath;
	private Button browse;

	public FileSelectionWidget(String name, String defaultFile,
			boolean mustExist, String[] fileExtensions) {
		super();
		this.name = name;
		this.defaultFile = defaultFile;
		this.mustExist = mustExist;
		this.fileExtensions = fileExtensions;
	}

	@Override
	public String getInformationMessage() {
		return null;
	}

	@Override
	public boolean validate() {
		String path=filePath.getText();
		if (path == null || path.isEmpty()){
			state=MESSAGE_EMPTY;
		} else if (mustExist && !(new java.io.File(path).isFile())){
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
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		filePath = new Text(composite, SWT.BORDER | SWT.SINGLE);
		filePath.setText(defaultFile);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		filePath.setLayoutData(gd);
		browse = new Button(composite, SWT.PUSH);
		browse.setText("Browse...");
		browse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fileDialog.setFilterExtensions(fileExtensions);
				String ret = fileDialog.open();
				if (ret != null)
					filePath.setText(ret);
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

	public void addModifiedListener(ModifyListener listener) {
		filePath.addModifyListener(listener);
	}

	public String getText() {
		return filePath.getText();
	}
}
