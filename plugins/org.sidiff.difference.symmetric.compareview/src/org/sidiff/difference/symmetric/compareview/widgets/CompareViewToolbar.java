package org.sidiff.difference.symmetric.compareview.widgets;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.services.IEvaluationService;
import org.eclipse.ui.services.IServiceLocator;
import org.sidiff.common.ui.widgets.IToolbarContribution;
import org.sidiff.difference.symmetric.compareview.Activator;
import org.sidiff.difference.symmetric.compareview.internal.handler.CollapseCompareViewHandler;
import org.sidiff.difference.symmetric.compareview.internal.handler.LayoutCompareViewHandler;
import org.sidiff.difference.symmetric.compareview.internal.handler.ToggleHighlightHandler;

public class CompareViewToolbar implements IToolbarContribution {

	public static final String CMD_LAYOUT_COMPARE_VIEW = "org.sidiff.difference.symmetric.compareview.initCompareView";
	public static final String CMD_COLLAPSE_COMPARE_VIEW = "org.sidiff.difference.symmetric.compareview.collapseAll";
	public static final String CMD_TOGGLE_HIGHTLIGHT = "org.sidiff.difference.symmetric.compareview.toggleHightlight";

	@Override
	public void createItems(ToolBar toolBar) {

		// FIXME[MO@29.11.13]: This is yet only a quick and dirty solution!
		// [DR@03.05.14]: This has to be synchronous/consistent to the "new" extension
		final HashMap<String, String> editorFileCombinations = new HashMap<String, String>();

		String ecore =
				"org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorID," +
				"org.eclipse.emf.ecore.presentation.EcoreEditorID," +
				"ecore," +
				"ecorediag";

		editorFileCombinations.put(
				"org.sidiff.difference.symmetric.compareview.ecoreFileCombination",
				ecore);

		String uml2 =
				"org.eclipse.papyrus.infra.core.papyrusEditor," +
				"org.eclipse.uml2.uml.editor.presentation.UMLEditorID," +
				"uml," +
				"di";

		editorFileCombinations.put(
				"org.sidiff.difference.symmetric.compareview.papyrusFileCombination",
				uml2);

		String web =
				"simplewebmodel.diagram.part.SimplewebmodelDiagramEditorID," +
				"simplewebmodel.presentation.SimplewebmodelEditorID," +
				"simplewebmodel," +
				"simplewebmodel_diagram";

		editorFileCombinations.put(
				"org.sidiff.difference.symmetric.compareview.simplewebmodelFileCombination",
				web);
		
		String smwl =
				"-," +
				"org.eclipse.emf.refactor.examples.SimpleWebModelingLanguage," +
				"swml," +
				"simplewebmodel_diagram";

		editorFileCombinations.put(
				"org.sidiff.difference.symmetric.compareview.simplewebmodellanguageFileCombination",
				smwl);
		
		String fm =
				"org.eclipse.featuremodel.diagrameditor.diagrameditor," +
				"de.imotep.featuremodel.variability.metamodel.FeatureModel.presentation.FeatureModelEditorID," +
				"featuremodel," +
				"featurediagram";

		editorFileCombinations.put(
				"org.sidiff.difference.symmetric.compareview.featuremodelFileCombination",
				fm);


		// Arrange Compare View
		ToolItem tool_compareViewLayout = new ToolItem(toolBar, SWT.NONE);
		tool_compareViewLayout.setImage(Activator.getImageDescriptor("compare_view_layout.png").createImage());
		tool_compareViewLayout.setToolTipText("Arrange Compare View");

		tool_compareViewLayout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// FIXME[MO@29.11.13]: This is yet only a quick and dirty solution!
				LayoutCompareViewHandler handler = new LayoutCompareViewHandler();
				try {
					handler.execute(getExecutionEvent(CMD_LAYOUT_COMPARE_VIEW, editorFileCombinations));
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}
			}
		});

		// Collapse All Editors
		ToolItem tool_compareViewCollapse = new ToolItem(toolBar, SWT.NONE);
		tool_compareViewCollapse.setImage(Activator.getImageDescriptor("compare_view_collapse.png").createImage());
		tool_compareViewCollapse.setToolTipText("Collapse All Editors");

		tool_compareViewCollapse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// FIXME[MO@29.11.13]: This is yet only a quick and dirty solution!
				CollapseCompareViewHandler handler = new CollapseCompareViewHandler();
				try {
					handler.execute(getExecutionEvent(CMD_COLLAPSE_COMPARE_VIEW, null));
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}
			}
		});

		// Highlighting Of Changes
		ToolItem tool_selection = new ToolItem(toolBar, SWT.CHECK);
		tool_selection.setSelection(false);
		tool_selection.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_SYNCED));
		tool_selection.setToolTipText("Highlighting Of Changes");
		tool_selection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ToggleHighlightHandler handler = new ToggleHighlightHandler();
				try {
					handler.execute(getExecutionEvent(CMD_COLLAPSE_COMPARE_VIEW, null));
				} catch (ExecutionException e1) {
					e1.printStackTrace();
				}
			}			
		});
	}

	private static ExecutionEvent getExecutionEvent(String commandID, Map<String, String> paramters) {
		IServiceLocator serviceLocator = PlatformUI.getWorkbench();
		ICommandService commandService = (ICommandService) serviceLocator
				.getService(ICommandService.class);
		IEvaluationService evaluationService = (IEvaluationService) serviceLocator
				.getService(IEvaluationService.class);

		if (paramters == null) {
			paramters = new HashMap<String, String>();
		}

		try {
			Command theCommand = commandService.getCommand(commandID);
			ExecutionEvent event = new ExecutionEvent(
					theCommand,
					paramters,
					null,
					evaluationService.getCurrentState());

			return event;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
