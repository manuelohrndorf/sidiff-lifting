package org.sidiff.patching.patch.ui.wizard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffFacade;
import org.sidiff.difference.asymmetric.facade.AsymmetricDiffSettings;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.silift.patching.patch.PatchCreator;
import org.silift.patching.patch.ui.Activator;
import org.sidiff.difference.lifting.facade.LiftingFacade;
import org.sidiff.difference.lifting.facade.LiftingSettings;
import org.sidiff.difference.lifting.ui.util.ValidateDialog;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.util.access.EMFModelAccessEx;
import org.sidiff.difference.util.emf.EMFStorage;
import org.sidiff.patching.util.PatchUtil;
import org.silift.common.exceptions.FileAlreadyExistsException;
import org.silift.common.exceptions.FileNotCreatedException;
import org.silift.common.file.util.FileOperations;
import org.silift.common.file.util.ZipUtil;

public class CreatePatchWizard extends Wizard {
	
	protected static final String PPRE = "Post Processed Recognition Engine (Default)";

	protected CreatePatchPage createPatchPage;
	
	protected IFile fileA = null;
	protected IFile fileB = null;
	
	protected Resource resourceA = null;
	protected Resource resourceB = null;
	
	protected String documentType = null;
	
	protected IProject project = null;
	
	protected String diffSavePath;
	private String separator;
	
	private PatchCreator patchCreator;
	
	
	// UI
	private Button modelBRadio;
	

	public CreatePatchWizard(IFile fileA, IFile fileB) {
		
		separator = System.getProperty("file.separator");
		
		project = fileA.getProject();
		
		this.fileA = fileA;
		this.fileB = fileB;
		
		resourceA = LiftingFacade.loadModel(fileA.getLocation().toOSString());
		resourceB = LiftingFacade.loadModel(fileB.getLocation().toOSString());
		
		documentType = EMFModelAccessEx.getCharacteristicDocumentType(resourceA);
		
		diffSavePath = fileA.getParent().getLocation().toOSString();
	}

	@Override
	public void addPages() {
		createPatchPage = new CreatePatchPage("CreateDifferencePage", "Create a Patch", getImageDescriptor("icon.png"));
		addPage(createPatchPage);
	}
	
	@Override
	public boolean canFinish() {
		return createPatchPage.isPageComplete();
	}


	@Override
	public boolean performFinish() {
		
		
		// In which direction shall we calculate?
		if (modelBRadio.getSelection()){
			// switch direction
			IFile tmp = fileA;
			fileA = fileB;
			fileB = tmp;
		}

		
		resourceA = LiftingFacade.loadModel(fileA.getLocation().toOSString());
		resourceB = LiftingFacade.loadModel(fileB.getLocation().toOSString());
		
		patchCreator = new PatchCreator(resourceA, resourceB);
		
		
//		String fileA_name = fileA.getName();
//		String fileB_name = fileB.getName();
//		IContainer parentA = fileA.getParent();
//		IContainer parentB = fileB.getParent();
//		
//		diffSavePath += separator+"PATCH(origin_"+fileA_name+"_to_"+"modified_"+fileB_name+")";
//		
//		try{
//			FileOperations.createFolder(diffSavePath, false);
//			
//			// create folder structure
//			while (fileA_name.equals(fileB_name)){
//				fileA_name = parentA.getName() + separator + fileA_name;
//				fileB_name = parentB.getName() + separator + fileB_name;
//				parentA = parentA.getParent();
//				parentB = parentB.getParent();
//			}
//			
//			String split = separator.equals("\\")? Pattern.quote("\\"): separator;
//			String[] folderA = fileA_name.split(split);
//			String[] folderB = fileB_name.split(split);
//			String pathA = diffSavePath + separator;
//			String pathB = diffSavePath+separator;
//			
//			for(int i = 0 ; i < folderA.length-1 ; i++){
//				pathA += separator + folderA[i];
//				FileOperations.createFolder(pathA, false);
//			}
//			for(int i = 0 ; i < folderB.length-1 ; i++){
//				pathB += separator + folderB[i];
//				FileOperations.createFolder(pathB, false);
//			}
//			FileOperations.copyFile(fileA.getLocation().toOSString(), pathA + separator + fileA.getName());
//			FileOperations.copyFile(fileB.getLocation().toOSString(), pathB + separator + fileB.getName());
//			FileOperations.createInfoFile(diffSavePath, fileA.getProject().getName());
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		resourceA = LiftingFacade.loadModel(diffSavePath + separator + fileA_name);
//		resourceB = LiftingFacade.loadModel(diffSavePath + separator + fileB_name);
		
		// Start calculation

		LiftingSettings settings = readSettings();
		try{
			Difference fullDiff = AsymmetricDiffFacade.liftMeUp(resourceA, resourceB, new AsymmetricDiffSettings(settings));
			
			patchCreator.setAsymmetricDifference(fullDiff.getAsymmetric());
			patchCreator.setSymmetricDifference(fullDiff.getSymmetric());
			
			patchCreator.serializePatch(fileA.getParent().getLocation());
			
		}catch(InvalidModelException e){
			ValidateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
			return false;
		}
			// Print report
//			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
//			LogUtil.log(LogEvent.NOTICE, "---------------------- Create Patch Bundle -----------------");
//			LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
//			
//			String patchName = LiftingFacade.generateDifferenceFileName(resourceA, resourceB, settings);
//			AsymmetricDiffFacade.serializeDifference(fullDiff, diffSavePath, patchName);
//			
//			
//			//gather necessary editrules
//			String erPath = diffSavePath + separator + "EditRules";
//			FileOperations.createFolder(erPath, false);
//			for(OperationInvocation op : fullDiff.getAsymmetric().getOperationInvocations()){
//				ResourceSet resourceSet = new ResourceSetImpl();
//				URI fileUri = URI.createFileURI(erPath + separator + op.getChangeSet().getEditRName()+".henshin");
//				Resource resource = resourceSet.createResource(fileUri);
//				resource.getContents().add(op.resolveEditRule().getExecuteModule());
//
//				// create option map for saving
//				Map<String,Boolean> options = new HashMap<String, Boolean>();
//				options.put (XMIResource.OPTION_SCHEMA_LOCATION, true);
//				resource.save(options);
//				
//			}
//			
//			// zip all necessary files
//			ZipUtil.zip(diffSavePath, diffSavePath, PatchUtil.PATCH_EXTENSION);
//			FileOperations.removeFolder(diffSavePath);
//			
//			/*
//			 * Done
//			 */
//			LogUtil.log(LogEvent.NOTICE, "done...");
//			
//		}catch(InvalidModelException e){
//			ValidateDialog.openErrorDialog(Activator.PLUGIN_ID, e);
//			return false;
//		} catch (FileNotCreatedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (FileAlreadyExistsException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		// Refresh workspace
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (OperationCanceledException e) {

		}
		return true;
	}
	
	
	public LiftingSettings readSettings() {
		// Move functionality to lifting facade		
		/*
		 * Do lifting settings
		 */

		LiftingSettings liftingSettings = new LiftingSettings();

		liftingSettings.setValidate(createPatchPage.isValidateModels());
		// Used matcher
		liftingSettings.setMatcher(createPatchPage.getSelectedMatchingEngine());
		
		//Used technical difference builder
		liftingSettings.setTechnicalDifferenceBuilder(createPatchPage.getSelectedTechnicalDifferenceBuilder());
				
		// Do lifting..?
		liftingSettings.setDoLifting(true);
		
		// Use Postprocessor..?
		liftingSettings.setPostProcess(true);

		// Used rulebases
		liftingSettings.setUsedRulebases(createPatchPage.getSelectedRulebases());

		return liftingSettings;
	}
	
	
	public class CreatePatchPage extends WizardPage {

		private java.util.List<IMatcher> matchers = null;
		private java.util.List<ITechnicalDifferenceBuilder> tdBuilders = null;
		private java.util.List<RuleBaseEntry> rulebases = null;

		private boolean validateModels = false;
		private String selectedMatchingEngine = null;
		private String selectedTechnicalDifferenceBuilder = null;
		private String selectedRecognitionEngine = null;

		public CreatePatchPage(String pageName, String title,
				ImageDescriptor titleImage) {
			super(pageName, title, titleImage);
		}

		@Override
		public void createControl(Composite parent) {
			createRulebaseList();

			setControl(parent);
			{
				GridLayout grid = new GridLayout(1, false);
				grid.marginWidth = 10;
				parent.setLayout(grid);
			}
			

			Group modelsGroup = new Group(parent, SWT.NONE);
			{
				GridLayout grid = new GridLayout(3, false);
				grid.marginWidth = 10;
				grid.marginHeight = 10;
				modelsGroup.setLayout(grid);

				GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
				modelsGroup.setLayoutData(data);
			}
			modelsGroup.setText("Select source model:");

			final Button modelARadio = new Button(modelsGroup, SWT.RADIO);
			modelARadio.setSelection(true);

			final Label modelALabel = new Label(modelsGroup, SWT.NONE);
			modelALabel.setText("Model A:");

			final Label modelAName = new Label(modelsGroup, SWT.NONE);
			modelAName.setText(EMFStorage.uriToFile(resourceA.getURI())
					.getName());

			final Image arrowUp = getImageDescriptor("arrow_up.png")
					.createImage();
			final Image arrowDown = getImageDescriptor("arrow_down.png")
					.createImage();

			new Label(modelsGroup, SWT.NONE);

			final Label arrowLabel = new Label(modelsGroup, SWT.NONE);
			arrowLabel.setText("Patch direction:");
			final Label arrow = new Label(modelsGroup, SWT.NONE);
			{
				GridData data = new GridData(SWT.CENTER, SWT.CENTER, false,
						true);
				arrow.setLayoutData(data);
			}
			arrow.setImage(arrowDown);

			modelBRadio = new Button(modelsGroup, SWT.RADIO);

			final Label modelBLabel = new Label(modelsGroup, SWT.NONE);
			modelBLabel.setText("Model B:");

			final Label modelBName = new Label(modelsGroup, SWT.NONE);
			modelBName.setText(EMFStorage.uriToFile(resourceB.getURI())
					.getName());

			modelARadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					arrow.setImage(arrowDown);
				}
			});

			modelBRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					arrow.setImage(arrowUp);
				}
			});

			
			// Validate models
			final Button buttonValidateModels = new Button(modelsGroup,
					SWT.CHECK);
			buttonValidateModels.setSelection(validateModels);
			buttonValidateModels.setText("Validate Models");
			buttonValidateModels.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					validateModels = buttonValidateModels.getSelection();
				}
			});
			
			
			Group algorithmsGroup = new Group(parent, SWT.NONE);
			{
				GridLayout grid = new GridLayout(1, false);
				grid.marginWidth = 10;
				grid.marginHeight = 10;
				algorithmsGroup.setLayout(grid);

				GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
				algorithmsGroup.setLayoutData(data);
			}
			algorithmsGroup.setText("Algorithms");

			Label matchingLabel = new Label(algorithmsGroup, SWT.NONE);
			matchingLabel.setText("Matching Engine:");

			final List matcherList = new List(algorithmsGroup, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
			{
				GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
				data.heightHint = 70;
				matcherList.setLayoutData(data);
			}
			matcherList.setItems(getMatcherNames());
			matcherList.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					selectedMatchingEngine = matcherList.getSelection()[0];
					validate();
				}
			});
		
			matcherList.select(0);
			selectedMatchingEngine = matcherList.getItem(0);
			

			
			// Technical Difference Builder
			if(getTechnicalDifferenceBuildersNames().length > 1){
				Label tdbLabel = new Label(algorithmsGroup, SWT.NONE);
				tdbLabel.setText("Technical Difference Builder:");
				
				final List tdbList = new List(algorithmsGroup, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
				{
					GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
					data.heightHint = 70;
					tdbList.setLayoutData(data);
				}
				tdbList.setItems(getTechnicalDifferenceBuildersNames());
				tdbList.addSelectionListener(new SelectionAdapter() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					selectedTechnicalDifferenceBuilder = tdbList.getSelection()[0];
					validate();
				}
				});
				
				tdbList.select(0);
				selectedTechnicalDifferenceBuilder = tdbList.getItem(0);
			
			}else{
				selectedTechnicalDifferenceBuilder = getTechnicalDifferenceBuildersNames()[0];
			}

			/*
			 * Rulebase table
			 */

			// Table viewer composite
			Composite rulebaseComposite = new Composite(parent, SWT.NONE);
			{
				GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
				data.heightHint = 100;
				rulebaseComposite.setLayoutData(data);
			}
			TableColumnLayout tableColumnLayout = new TableColumnLayout();
			rulebaseComposite.setLayout(tableColumnLayout);

			// Rulebase viewer
			int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER;
			final TableViewer rulebaseTableViewer = new TableViewer(rulebaseComposite, style);

			// SWT table
			final Table ruleBaseTable = rulebaseTableViewer.getTable();
			{
				GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
				ruleBaseTable.setLayoutData(data);
			}
			ruleBaseTable.setHeaderVisible(true);
			ruleBaseTable.setLinesVisible(true);

			// ArrayContentProvider -> Table input is a java collection
			rulebaseTableViewer.setContentProvider(ArrayContentProvider.getInstance());

			/*
			 * Active column
			 */

			TableViewerColumn activeColumn = new TableViewerColumn(rulebaseTableViewer, SWT.NONE);
			tableColumnLayout.setColumnData(activeColumn.getColumn(), new ColumnPixelData(25));
			activeColumn.getColumn().setText("");
			activeColumn.getColumn().setAlignment(SWT.CENTER);
			activeColumn.getColumn().setResizable(false);
			activeColumn.getColumn().setToolTipText("Activate/Deactivate rulebase for recognition engine");

			// LabelProvider for activeColumn
			activeColumn.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					cell.setText(((String) cell.getElement()).toString());
				}
			});

			// Table header action - invert selection
			activeColumn.getColumn().addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					for (RuleBaseEntry ruleBase : rulebases) {
						if (ruleBase.activated) {
							ruleBase.activated = false;
						} else {
							ruleBase.activated = true;
						}
					}
					rulebaseTableViewer.refresh();
				}
			});

			// Setup check box for activeColumn
			activeColumn.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					return "";
				}

				@Override
				public Image getImage(Object element) {
					if (((RuleBaseEntry) element).activated) {
						return getImageDescriptor("checked.png").createImage();
					} else {
						return getImageDescriptor("unchecked.png").createImage();
					}
				}
			});

			// Setup editing support for activeColumn
			activeColumn.setEditingSupport(new EditingSupport(rulebaseTableViewer) {

				protected boolean canEdit(Object element) {
					return true;
				}

				protected CellEditor getCellEditor(Object element) {
					return new CheckboxCellEditor(ruleBaseTable);
				}

				protected Object getValue(Object element) {
					return ((RuleBaseEntry) element).activated;
				}

				protected void setValue(Object element, Object value) {
					RuleBaseEntry ruleBase = ((RuleBaseEntry) element);
					ruleBase.activated = ruleBase.activated ? false : true;

					rulebaseTableViewer.refresh(element);
					validate();
				}

			});

			/*
			 * Rulebase name column
			 */

			TableViewerColumn ruleBaseColumn = new TableViewerColumn(rulebaseTableViewer, SWT.NONE);
			tableColumnLayout.setColumnData(ruleBaseColumn.getColumn(), new ColumnWeightData(100));
			ruleBaseColumn.getColumn().setText("Rule Base");
			ruleBaseColumn.getColumn().setToolTipText("Rulebase description name");

			// LabelProvider for activeColumn
			ruleBaseColumn.setLabelProvider(new CellLabelProvider() {
				public void update(ViewerCell cell) {
					cell.setText(((RuleBaseEntry) cell.getElement()).rulebase.getName());
				}
			});

			/*
			 * Set table input
			 */

			rulebaseTableViewer.setInput(rulebases);
			rulebaseTableViewer.refresh();

			validate();
		}

		private void validate() {
			boolean complete = true;

			
			if (selectedMatchingEngine == null) {
				complete = false;
				setErrorMessage("Please select a matching engine");
				setPageComplete(complete);
				return;
			}
			

			if (getSelectedRulebases().isEmpty()) {
				complete = false;
				setErrorMessage("Please select at least one rulebase");
				setPageComplete(complete);
				return;
			}

			setErrorMessage(null);
			setPageComplete(complete);
		}


		private class RuleBaseEntry implements Comparable<RuleBaseEntry>{
			public IRuleBase rulebase;
			public Boolean activated;

			public RuleBaseEntry(IRuleBase rulebase, Boolean activated) {
				super();
				this.rulebase = rulebase;
				this.activated = activated;
			}
			
			@Override
			public int compareTo(RuleBaseEntry o) {
				return this.rulebase.getName().compareTo(o.rulebase.getName());
			}
		}
		
		public boolean isValidateModels(){
			return validateModels;
		}
		
		private String[] getMatcherNames() {
			// Search registered matcher extension points
			matchers = new ArrayList<IMatcher>();
			matchers.addAll(LiftingFacade.getAvailableMatchers(resourceA, resourceB));

			ArrayList<String> matcherNames = new ArrayList<String>();
			for (int i = 0; i < matchers.size(); i++) {
				matcherNames.add(matchers.get(i).getName());
			}
			Collections.sort(matcherNames);
			
			return matcherNames.toArray(new String[matcherNames.size()]);
		}
		
		public IMatcher getSelectedMatchingEngine() {
			for (IMatcher matcher : matchers) {
				if (matcher.getName().equals(selectedMatchingEngine)) {
					return matcher;
				}
			}
			return null;
		}

		private String[] getTechnicalDifferenceBuildersNames() {
			// Search registered matcher extension points
			tdBuilders = new ArrayList<ITechnicalDifferenceBuilder>();
			tdBuilders.addAll(LiftingFacade.getAvailableTechnicalDifferenceBuilders(documentType));

			ArrayList<String> tdbNames = new ArrayList<String>();
			for (int i = 0; i < tdBuilders.size(); i++) {
				tdbNames.add(tdBuilders.get(i).getName());
			}
			Collections.sort(tdbNames);
			String [] result = tdbNames.toArray(new String[tdbNames.size()]);
			return result;
		}
		
		public ITechnicalDifferenceBuilder getSelectedTechnicalDifferenceBuilder(){
			for(ITechnicalDifferenceBuilder tdb : tdBuilders){
				if(tdb.getName().equals(selectedTechnicalDifferenceBuilder)){
					return tdb;
				}
			}
			return null;
		}
		
		public String getSelectedRecognitionEngine() {
			return selectedRecognitionEngine;
		}
		
		private void createRulebaseList() {
			// Search registered rulebase extension points
			Set<IRuleBase> rulebaseInstances = LiftingFacade.getAvailableRulebases(documentType);
			
			// Create rulebase list for table viewer
			rulebases = new LinkedList<RuleBaseEntry>();
			
			for (IRuleBase rulebase : rulebaseInstances) {
				rulebases.add(new RuleBaseEntry(rulebase, true));
			}
			
			Collections.sort(rulebases);
		}
		
		public Set<IRuleBase> getSelectedRulebases() {
			Set<IRuleBase> selectedRuleBases = new HashSet<IRuleBase>();
			for (RuleBaseEntry entry : rulebases) {
				if (entry.activated) {
					selectedRuleBases.add(entry.rulebase);
				}
			}
			return selectedRuleBases;
		}
	}
	

	protected ImageDescriptor getImageDescriptor(String name) {
		return ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
				new Path(String.format("icons/%s", name)), null));
	}
}
