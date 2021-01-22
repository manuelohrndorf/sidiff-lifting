package org.sidiff.patching.ui.view;

import java.util.*;
import java.util.List;

import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.ViewPart;
import org.sidiff.patching.report.*;
import org.sidiff.patching.ui.internal.PatchingUiPlugin;
import org.sidiff.patching.ui.view.filter.ReportViewFilter;

public class ReportView extends ViewPart implements IPatchReportListener,IPartListener{

	public static final String ID = "org.sidiff.patching.ui.view.ReportView";

	private PatchReportManager reportManager;

	private final Map<OperationExecutionKind,Image> images = new HashMap<>();
	{
		images.put(OperationExecutionKind.PASSED, PatchingUiPlugin.getImageDescriptor("success.png").createImage());
		images.put(OperationExecutionKind.WARNING, PatchingUiPlugin.getImageDescriptor("warning.png").createImage());
		images.put(OperationExecutionKind.REVERTED, PatchingUiPlugin.getImageDescriptor("skipped.png").createImage());
		images.put(OperationExecutionKind.EXEC_FAILED, PatchingUiPlugin.getImageDescriptor("error.png").createImage());
		images.put(OperationExecutionKind.REVERT_FAILED, PatchingUiPlugin.getImageDescriptor("error.png").createImage());
	}

	private int reportStackEntry;

	private TableViewer reportViewer;

	private DropDownAction reportStackAction;

	private ReportViewFilter reportFilter;
	private Action reportFilterAction;

	private Map<OperationExecutionKind,Button> buttons = new LinkedHashMap<>();

	private boolean disposed = false;

	@Override
	public void createPartControl(Composite parent) {

		//Register part listener (for editor)
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(this);

		Composite composite = new Composite(parent, SWT.FILL);
		composite.setLayout(new GridLayout());

		Composite editComposite = new Composite(composite, SWT.NONE);
		editComposite.setLayout(new GridLayout(3, false));

		for (OperationExecutionKind kind : OperationExecutionKind.values()) {
			Button button = new Button(editComposite, SWT.CHECK);
			button.setText(kind.toString());
			button.setSelection(true);
			button.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> update()));
			buttons.put(kind, button);
		}

		// Result view
		reportViewer = new TableViewer(composite, SWT.SINGLE | SWT.FULL_SELECTION);
		reportViewer.setContentProvider(new ArrayContentProvider());
		reportViewer.getTable().setHeaderVisible(true);
		reportViewer.getTable().setLinesVisible(true);
		reportViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

		getSite().setSelectionProvider(reportViewer);

		// Report Filter
		reportFilter = new ReportViewFilter();

		createColumns();

		createActions();
		createMenus();
		createToolbar();

		//Clear initially
		this.clearView();

	}

	public void setPatchReportManager(PatchReportManager reportManager) {
		this.reportManager = reportManager;
		this.clearView();
		this.initView();
		update();
	}

	@Override
	public void dispose() {
		super.dispose();
		disposed = true;

		if(reportManager != null) {
			reportManager.removePatchReportListener(this);
			reportManager = null;
		}

		for (Image image : images.values()) {
			image.dispose();
		}
	}

	// This will create the columns for the table
	private void createColumns() {
		// the status
		TableViewerColumn col = createTableViewerColumn("Status", 75);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return null;
			}
			@Override
			public Image getImage(Object element) {
				if (element instanceof OperationExecutionEntry) {
					OperationExecutionEntry execEntry = (OperationExecutionEntry) element;
					return images.get(execEntry.getKind());
				}
				return null;
			}
		});

		// test type
		col = createTableViewerColumn("Type", 100);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ReportEntry entry = (ReportEntry) element;
				if (entry instanceof OperationExecutionEntry) {
					OperationExecutionEntry execEntry = (OperationExecutionEntry) entry;
					return execEntry.getKind().toString();
				} else if (entry instanceof ValidationEntry) {
					return "Validation";
				}
				return null;
			}
		});

		// test description
		col = createTableViewerColumn("Description", 200);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				ReportEntry entry = (ReportEntry) element;
				return entry.getDescription();
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(reportViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(false);
		return viewerColumn;
	}

	private void createActions() {
		this.reportStackAction = new DropDownAction("Reports") {
			@Override
			public void run() {
				for(int i = 0; i < reportStackAction.getActions().size(); i++) {
					reportStackAction.getActions().get(i).setChecked(i == reportStackAction.getActions().size()-1);
				}
				update();
			}
		};
		this.reportStackAction.setImageDescriptor(PatchingUiPlugin.getImageDescriptor("empty_report_stack_16x16.gif"));
		this.reportStackAction.setEnabled(false);
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
		rootMenuManager.addMenuListener(this::fillMenu);
		fillMenu(rootMenuManager);
	}

	private void fillMenu(IMenuManager rootMenuManager) {
		IMenuManager filterSubmenu = new MenuManager("Filters");
		rootMenuManager.add(filterSubmenu);
		filterSubmenu.add(reportFilterAction);
	}

	private void createToolbar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		toolbarManager.add(reportStackAction);
	}

	@Override
	public void setFocus() {
	}

	private void update() {
		List<ReportEntry> input = new ArrayList<>();
		if(!reportManager.getReports().isEmpty()) {
			List<ReportEntry> allEntries = reportManager.get(reportStackEntry).getEntries();
			if (allEntries != null) {
				for (ReportEntry reportEntry : allEntries) {
					if (reportEntry instanceof OperationExecutionEntry) {
						OperationExecutionEntry execEntry = (OperationExecutionEntry) reportEntry;
						if (buttons.get(execEntry.getKind()).getSelection()) {
							input.add(execEntry);
						}
					} else {
						input.add(reportEntry);
					}
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

	@Override
	public void pushReport(int i) {
		Action action = new Action("Report " + (i+1), Action.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				reportStackEntry = i;
				update();
			}
		};
		reportStackAction.add(action);
		reportStackAction.getActions().get(reportStackEntry).setChecked(false);

		action.setChecked(true);
		if(!reportStackAction.isEnabled() && reportManager.getReports().size() > 1) {
			reportStackAction.setEnabled(true);
			reportStackAction.setImageDescriptor(PatchingUiPlugin.getImageDescriptor("report_stack_16x16.gif"));
		}
		reportStackEntry = reportManager.getReports().size()-1;
	}

	private void clearView() {
		if(!disposed) {
			reportStackEntry = 0;
			reportStackAction.clearMenu();
			reportStackAction.setEnabled(false);
			reportViewer.getTable().clearAll();
			reportViewer.getTable().setVisible(false);

			this.reportFilterAction.setEnabled(false);
			buttons.values().forEach(button -> button.setEnabled(false));
		}

		if(reportManager != null) {
			reportManager.removePatchReportListener(this);
		}
	}

	private void initView() {
		if(reportManager != null) {
			reportManager.addPatchReportListener(this);
		}

		reportViewer.getTable().setVisible(true);
		this.reportFilterAction.setEnabled(true);
		buttons.values().forEach(button -> button.setEnabled(true));
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		if (part instanceof EditorPart) {
			//Check if at least one editor is still open
			if(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences().length == 0) {
				this.clearView();
			}
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
	}
}
