package org.sidiff.patching.ui.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import org.sidiff.patching.report.PatchReport.Type;
import org.sidiff.patching.report.ReportEntry;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.view.filter.ReportViewFilter;

public class ReportView extends ViewPart {
	public static final String ID = "org.sidiff.patching.ui.view.ReportView";
	private Logger logger = Logger.getLogger(ReportView.class.getName());

	private Image PASSED_IMG = Activator.getImageDescriptor("success.png").createImage();
	private Image SKIPPED_IMG = Activator.getImageDescriptor("skipped.png").createImage();
	private Image FAILED_IMG = Activator.getImageDescriptor("error.png").createImage();
	private Image WARNING_IMG = Activator.getImageDescriptor("warning.png").createImage();

	private TableViewer reportViewer;

	private ReportViewFilter reportParameterFilter;
	private Action reportParameterFilterAction;
	private ReportViewFilter reportExecutionFilter;
	private Action reportExecutionFilterAction;
	
	private List<ReportEntry> entries;
	private boolean showPassed = false;
	private boolean showSkipped = true;
	private boolean showFailed = true;
	private boolean showWarning = true;
	private Button passedButton;
	private Button skippedButton;
	private Button failedButton;
	private Button warningButton;
	
	@Override
	public void createPartControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.FILL);
		composite.setLayout(new GridLayout());
		
		Composite editComposite = new Composite(composite, SWT.NONE);
		GridLayout glEditComposite = new GridLayout(4,false);
		editComposite.setLayout(glEditComposite);
		
		passedButton = new Button(editComposite, SWT.CHECK);
		passedButton.setText("PASSED");
		passedButton.setSelection(false);
		passedButton.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        // Handle the selection event
		        showPassed = passedButton.getSelection();
		        update();
		    }
		}); 
		
		skippedButton = new Button(editComposite, SWT.CHECK);
		skippedButton.setText("SKIPPED");
		skippedButton.setSelection(true);
		skippedButton.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        // Handle the selection event
		        showSkipped = skippedButton.getSelection();
		        update();
		    }
		}); 
		
		
		failedButton = new Button(editComposite, SWT.CHECK);
		failedButton.setText("FAILED");
		failedButton.setSelection(true);
		failedButton.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        // Handle the selection event
		        showFailed = failedButton.getSelection();
		        update();
		    }
		}); 
		
		warningButton = new Button(editComposite, SWT.CHECK);
		warningButton.setText("WARNING");
		warningButton.setSelection(true);
		warningButton.addSelectionListener(new SelectionAdapter() {
		    @Override
		    public void widgetSelected(SelectionEvent e) {
		        // Handle the selection event
		        showWarning = warningButton.getSelection();
		        update();
		    }
		}); 
		
		// Result view
		reportViewer = new TableViewer(composite, SWT.SINGLE | SWT.FULL_SELECTION);
		reportViewer.setContentProvider(new ArrayContentProvider());
		reportViewer.getTable().setHeaderVisible(true);
		reportViewer.getTable().setLinesVisible(true);
		reportViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

		createColumns();

		createActions();
		createMenus();

	}

	// This will create the columns for the table
	private void createColumns() {
		String[] titles = { "Status", "Type", "Description" };
		int[] bounds = { 75, 100, 200 };

		// the status
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}

			@Override
			public Image getImage(Object element) {
				switch (((ReportEntry) element).getStatus()) {
				case PASSED: {
					return PASSED_IMG;
				}
				case SKIPPED: {
					return SKIPPED_IMG;
				}
				case FAILED: {
					return FAILED_IMG;
				}
				case WARNING: {
					return WARNING_IMG;
				}
				default:
					return null;
				}
			}
		});

		// test type
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ReportEntry entry = (ReportEntry) element;
				return entry.getType().toString();
			}
		});

		// test description
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ReportEntry entry = (ReportEntry) element;
				return entry.getDescription();
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(reportViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(false);
		return viewerColumn;
	}

	private void createActions() {
		this.reportParameterFilter = new ReportViewFilter(Type.PARAMETER);
		this.reportParameterFilterAction = new Action("Hide successful parameter bindings in Report") {
			@Override
			public void run() {
				updatefilter(reportParameterFilterAction);
			}
		};
		reportParameterFilterAction.setChecked(false);
		
		this.reportExecutionFilter = new ReportViewFilter(Type.EXECUTION);
		this.reportExecutionFilterAction = new Action("Hide successful executions in Report") {
			@Override
			public void run() {
				updatefilter(reportExecutionFilterAction);
			}
		};
		reportExecutionFilterAction.setChecked(false);
	}

	private void updatefilter(Action action) {
		if (action == reportParameterFilterAction) {
			if (action.isChecked()) {
				reportViewer.addFilter(reportParameterFilter);
			} else {
				reportViewer.removeFilter(reportParameterFilter);
			}
		} else if (action == reportExecutionFilterAction) {
			if (action.isChecked()) {
				reportViewer.addFilter(reportExecutionFilter);
			} else {
				reportViewer.removeFilter(reportExecutionFilter);
			}
		}
	}

	private void createMenus() {
		IMenuManager rootMenuManager = getViewSite().getActionBars().getMenuManager();
		rootMenuManager.setRemoveAllWhenShown(true);
		rootMenuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				fillMenu(mgr);
			}
		});
		fillMenu(rootMenuManager);
	}

	private void fillMenu(IMenuManager rootMenuManager) {
		IMenuManager filterSubmenu = new MenuManager("Filters");
		rootMenuManager.add(filterSubmenu);
		filterSubmenu.add(reportParameterFilterAction);
		filterSubmenu.add(reportExecutionFilterAction);
	}

	@Override
	public void setFocus() {

	}
	
	public void setEntries(List<ReportEntry> entries) {
		this.entries = entries;
		update();
	}
	
	private void update(){
		List<ReportEntry> input = new ArrayList<ReportEntry>();
		
		for(ReportEntry re : entries){
			if(re.getStatus().name().equals("PASSED") && showPassed){
				input.add(re);
			}else if(re.getStatus().name().equals("FAILED") && showFailed){
				input.add(re);
			}else if(re.getStatus().name().equals("SKIPPED") && showSkipped){
				input.add(re);
			}else if(re.getStatus().name().equals("WARNING") && showWarning){
				input.add(re);
			}
		}
		reportViewer.setInput(input);
		reportViewer.getTable().getColumns()[2].pack();
		logger.log(Level.INFO, "\n" + input.toString());
	}
}
