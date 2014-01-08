package org.sidiff.patching.ui.view;

import java.util.ArrayList;
import java.util.List;

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
import org.sidiff.patching.report.IPatchReportListener;
import org.sidiff.patching.report.OperationExecutionEntry;
import org.sidiff.patching.report.OperationExecutionKind;
import org.sidiff.patching.report.PatchReportManager;
import org.sidiff.patching.report.ReportEntry;
import org.sidiff.patching.report.ValidationEntry;
import org.sidiff.patching.ui.Activator;
import org.sidiff.patching.ui.view.filter.ReportViewFilter;

public class ReportView extends ViewPart implements IPatchReportListener {
	
	public static final String ID = "org.sidiff.patching.ui.view.ReportView";

	private PatchReportManager reportManager;

	private Image PASSED_IMG = Activator.getImageDescriptor("success.png").createImage();
	private Image WARNING_IMG = Activator.getImageDescriptor("warning.png").createImage();
	private Image SKIPPED_IMG = Activator.getImageDescriptor("skipped.png").createImage();
	private Image REVERTED_IMG = Activator.getImageDescriptor("skipped.png").createImage();
	private Image EXEC_FAILED_IMG = Activator.getImageDescriptor("error.png").createImage();
	private Image REVERT_FAILED_IMG = Activator.getImageDescriptor("error.png").createImage();

	private TableViewer reportViewer;

	private ReportViewFilter reportFilter;
	private Action reportFilterAction;

	private boolean showPassed = true;
	private boolean showWarning = true;
	private boolean showSkipped = true;
	private boolean showReverted = true;
	private boolean showExecFailed = true;
	private boolean showRevertFailed = true;

	private Button passedButton;
	private Button warningButton;
	private Button skippedButton;
	private Button revertedButton;
	private Button execFailedButton;
	private Button revertFailedButton;

	@Override
	public void createPartControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.FILL);
		composite.setLayout(new GridLayout());

		Composite editComposite = new Composite(composite, SWT.NONE);
		GridLayout glEditComposite = new GridLayout(4, false);
		editComposite.setLayout(glEditComposite);

		passedButton = new Button(editComposite, SWT.CHECK);
		passedButton.setText("PASSED");
		passedButton.setSelection(true);
		passedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Handle the selection event
				showPassed = passedButton.getSelection();
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

		revertedButton = new Button(editComposite, SWT.CHECK);
		revertedButton.setText("REVERTED");
		revertedButton.setSelection(true);
		revertedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Handle the selection event
				showReverted = revertedButton.getSelection();
				update();
			}
		});

		execFailedButton = new Button(editComposite, SWT.CHECK);
		execFailedButton.setText("EXEC_FAILED");
		execFailedButton.setSelection(true);
		execFailedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Handle the selection event
				showExecFailed = execFailedButton.getSelection();
				update();
			}
		});

		revertFailedButton = new Button(editComposite, SWT.CHECK);
		revertFailedButton.setText("REVERT_FAILED");
		revertFailedButton.setSelection(true);
		revertFailedButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Handle the selection event
				showRevertFailed = revertFailedButton.getSelection();
				update();
			}
		});

		// Result view
		reportViewer = new TableViewer(composite, SWT.SINGLE | SWT.FULL_SELECTION);
		reportViewer.setContentProvider(new ArrayContentProvider());
		reportViewer.getTable().setHeaderVisible(true);
		reportViewer.getTable().setLinesVisible(true);
		reportViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

		// Report Filter
		reportFilter = new ReportViewFilter();

		createColumns();

		createActions();
		createMenus();

	}

	public void setPatchReportManager(PatchReportManager reportManager) {
		this.reportManager = reportManager;
		reportManager.addPatchReportListener(this);
	}

	@Override
	public void dispose() {
		super.dispose();
		if (reportManager != null){
			reportManager.removePatchReportListener(this);
		}	
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
				if (element instanceof OperationExecutionEntry) {
					OperationExecutionEntry execEntry = (OperationExecutionEntry) element;
					if (execEntry.getKind() == OperationExecutionKind.PASSED) {
						return PASSED_IMG;
					}
					if (execEntry.getKind() == OperationExecutionKind.EXEC_WARNING) {
						return WARNING_IMG;
					}
					if (execEntry.getKind() == OperationExecutionKind.SKIPPED) {
						return SKIPPED_IMG;
					}
					if (execEntry.getKind() == OperationExecutionKind.REVERTED) {
						return REVERTED_IMG;
					}
					if (execEntry.getKind() == OperationExecutionKind.EXEC_FAILED) {
						return EXEC_FAILED_IMG;
					}
					if (execEntry.getKind() == OperationExecutionKind.REVERT_FAILED) {
						return REVERT_FAILED_IMG;
					}
				}

				return null;
			}
		});

		// test type
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ReportEntry entry = (ReportEntry) element;
//				if (entry instanceof OperationExecutionEntry) {
//					return "Execution";
//				}
//				if (entry instanceof ValidationEntry) {
//					return "Validation";
//				}
				if (entry instanceof OperationExecutionEntry) {
					OperationExecutionEntry execEntry = (OperationExecutionEntry) entry;
					if (execEntry.getKind() == OperationExecutionKind.PASSED) {
						return "PASSED";
					}
					if (execEntry.getKind() == OperationExecutionKind.EXEC_WARNING) {
						return "WARNING";
					}
					if (execEntry.getKind() == OperationExecutionKind.SKIPPED) {
						return "SKIPPED";
					}
					if (execEntry.getKind() == OperationExecutionKind.REVERTED) {
						return "REVERTED";
					}
					if (execEntry.getKind() == OperationExecutionKind.EXEC_FAILED) {
						return "EXEC_FAILED";
					}
					if (execEntry.getKind() == OperationExecutionKind.REVERT_FAILED) {
						return "REVERT_FAILED";
					}
				}
				if (entry instanceof ValidationEntry){
					return "Validation";
				}
				
				
				return null;
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
		this.reportFilterAction = new Action("Hide successful executions in Report") {
			@Override
			public void run() {
				updatefilter(reportFilterAction);
			}
		};
		reportFilterAction.setChecked(false);
	}

	private void updatefilter(Action action) {
		if (action == reportFilterAction) {
			if (action.isChecked()) {
				reportViewer.addFilter(reportFilter);
			} else {
				reportViewer.removeFilter(reportFilter);
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
		filterSubmenu.add(reportFilterAction);
	}

	@Override
	public void setFocus() {

	}

	private void update() {
		List<ReportEntry> allEntries = reportManager.getLastReport().getEntries();
		List<ReportEntry> input = new ArrayList<ReportEntry>();

		if (allEntries != null) {
			for (ReportEntry reportEntry : allEntries) {
				if (reportEntry instanceof OperationExecutionEntry) {
					OperationExecutionEntry execEntry = (OperationExecutionEntry) reportEntry;
					if (execEntry.getKind() == OperationExecutionKind.PASSED && showPassed) {
						input.add(execEntry);
					}
					if (execEntry.getKind() == OperationExecutionKind.EXEC_WARNING && showWarning) {
						input.add(execEntry);
					}
					if (execEntry.getKind() == OperationExecutionKind.SKIPPED && showSkipped) {
						input.add(execEntry);
					}
					if (execEntry.getKind() == OperationExecutionKind.REVERTED && showReverted) {
						input.add(execEntry);
					}
					if (execEntry.getKind() == OperationExecutionKind.EXEC_FAILED && showExecFailed) {
						input.add(execEntry);
					}
					if (execEntry.getKind() == OperationExecutionKind.REVERT_FAILED && showRevertFailed) {
						input.add(execEntry);
					}
				} else {
					input.add(reportEntry);
				}
			}
		}

		reportViewer.setInput(input);
		reportViewer.getTable().getColumns()[2].pack();
	}

	@Override
	public void reportChanged() {
		update();
	}

}
