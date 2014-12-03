package org.sidiff.profileapplicator.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ApplyProfilesWizardPage0 extends WizardPage {

	protected ApplyProfilesWizardPage0(String defaultInputFolderPath) {
		super("Main Page");
		this.defaultInputFolderPath = defaultInputFolderPath;
		setTitle("ProfileApplicator configuration");
		setDescription("");
		
	}
	
	/* Default values */
	private final String defaultInputFolderPath;
	
	/* States */
	private boolean inputFolderValid=false;
	
	/* Components */
	private Text inputFolderPath;
	private Button includeSubfolders;
	private Composite container;

	@Override
	public void createControl(Composite parent) {
		/* Container and layout */
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		/* Rule input folder */
		Label label1 = new Label(container, SWT.NONE);
		label1.setText("Rule input folder");
		inputFolderPath = new Text(container, SWT.BORDER | SWT.SINGLE );
		inputFolderPath.setText(defaultInputFolderPath);
		inputFolderPath.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				inputFolderValid=checkInputFolderPath(inputFolderPath.getText());
				updatePageState();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				inputFolderValid=checkInputFolderPath(inputFolderPath.getText());
				updatePageState();
			}
		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    inputFolderPath.setLayoutData(gd);
	    Button inputPathBrowse=new Button(container, SWT.NONE);
	    inputPathBrowse.setText("Browse...");
	    /* Subfolders */
	    //Empty Label for intend
		Label label2 = new Label(container, SWT.NONE);
		label2.setText("");
	    includeSubfolders = new Button(container, SWT.CHECK);
	    includeSubfolders.setText("Include subfolders");
	    includeSubfolders.setSelection(true);
	    /* Finish */
	    setControl(container);
	    //Required to avoid an error in the system
		inputFolderValid=checkInputFolderPath(inputFolderPath.getText());
	    updatePageState();
	}
	
	private void updatePageState(){
		if (!inputFolderValid){
			setErrorMessage("Rule input folder not valid");
		} else {
			setErrorMessage(null);
		}
		setPageComplete(inputFolderValid);
	}
	
	private boolean checkInputFolderPath(String path){
		return (path != null && !path.isEmpty());
	}
}
