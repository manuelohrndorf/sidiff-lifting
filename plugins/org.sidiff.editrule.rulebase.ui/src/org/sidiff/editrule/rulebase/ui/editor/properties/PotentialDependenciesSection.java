package org.sidiff.editrule.rulebase.ui.editor.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialAttributeDependency;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.PotentialDependencyKind;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.provider.EditRuleItemProvider;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.internal.EditruleRulebaseUiPlugin;

/**
 * Property section which shows the outgoing {@link PotentialDependency}s
 * of the edit rule(s) selected in the {@link RulebaseEditor}.
 * @author rmueller
 */
public class PotentialDependenciesSection extends AbstractPropertySection {

	private Label selectedRuleLabel;
	private TreeViewer treeViewer;

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);

		if(selection.isEmpty()) {
			selectedRuleLabel.setText("No source rule selected");
			treeViewer.setInput(null);
		} else {
			RulebaseEditor editor = (RulebaseEditor)part;
			RuleBase ruleBase = editor.getRulebaseWrapper().getRuleBase();
			Set<EditRule> selectedRules =
				editor.getSelectedRuleBaseItems().stream()
					.map(RuleBaseItem::getEditRule)
					.collect(Collectors.toSet());

			selectedRuleLabel.setText("Source rule" + (selectedRules.size() > 1 ? "s" : "") + ": "
					+ selectedRules.stream().map(EditRuleItemProvider::getEditRuleName).collect(Collectors.joining(", ")));

			// Get all outgoing dependencies of the source rule/s
			// and group them by target rule, then dependency kind.
			treeViewer.setInput(
				Stream.of(ruleBase.getPotentialNodeDependencies(),
						ruleBase.getPotentialEdgeDependencies(),
						ruleBase.getPotentialAttributeDependencies())
					.flatMap(Collection::stream)
					.filter(dependency -> selectedRules.contains(dependency.getSourceRule()))
					.collect(Collectors.groupingBy(PotentialDependency::getTargetRule,
							Collectors.groupingBy(PotentialDependency::getKind)))
					.entrySet().stream() // copy into wrapper classes
						.sorted(Comparator.comparing(Map.Entry::getKey, Comparator.comparing(EditRuleItemProvider::getEditRuleName)))
						.map(DependencyTargetRuleWrapper::new)
						.collect(Collectors.toList()));
		}
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);

		Composite composite = getWidgetFactory().createFlatFormComposite(parent);

		selectedRuleLabel = getWidgetFactory().createLabel(composite, "", SWT.NONE);
		{
			FormData data = new FormData();
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(100, 0);
			data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
			selectedRuleLabel.setLayoutData(data);
		}

		treeViewer = new TreeViewer(composite, SWT.BORDER | SWT.FILL | SWT.FULL_SELECTION);
		treeViewer.setContentProvider(new DependenciesContentProvider());
		treeViewer.getTree().setHeaderVisible(true);
		treeViewer.getTree().setHeaderBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_BACKGROUND));
		treeViewer.getTree().setHeaderForeground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_FOREGROUND));
		treeViewer.getTree().setLinesVisible(true);
		{
			FormData data = new FormData();
			data.left = new FormAttachment(0, 0);
			data.right = new FormAttachment(100, 0);
			data.top = new FormAttachment(selectedRuleLabel, ITabbedPropertyConstants.VSPACE);
			data.bottom = new FormAttachment(100, ITabbedPropertyConstants.VSPACE);
			data.height = 200;
			treeViewer.getTree().setLayoutData(data);
		}

		TreeViewerColumn dependencyColumn = createColumn(treeViewer, "Potential Dependency", 400);
		dependencyColumn.setLabelProvider(new DependencyColumnLabelProvider());

		TreeViewerColumn detailsColumn = createColumn(treeViewer, "Details", 300);
		detailsColumn.setLabelProvider(new DetailColumnLabelProvider());

		ColumnViewerToolTipSupport.enableFor(treeViewer);
	}

	private static TreeViewerColumn createColumn(TreeViewer treeViewer, String title, int width) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(width);
		column.setResizable(true);
		column.setMoveable(false);
		return viewerColumn;
	}

	static class DependencyTargetRuleWrapper {
		final EditRule targetRule;
		final List<DependencyKindWrapper> kindWrappers;

		public DependencyTargetRuleWrapper(Map.Entry<EditRule,
				? extends Map<PotentialDependencyKind,? extends Collection<? extends PotentialDependency>>> wrapped) {
			this.targetRule = wrapped.getKey();
			this.kindWrappers = Collections.unmodifiableList(
					wrapped.getValue().entrySet().stream()
						.map(entry -> new DependencyKindWrapper(this, entry.getKey(), entry.getValue()))
						.sorted(Comparator.comparing(wrapper -> wrapper.kind.name()))
						.collect(Collectors.toList()));
		}
	}

	static class DependencyKindWrapper {
		final DependencyTargetRuleWrapper parent;
		final PotentialDependencyKind kind;
		final List<PotentialDependency> dependencies;

		public DependencyKindWrapper(DependencyTargetRuleWrapper parent, PotentialDependencyKind kind,
				Collection<? extends PotentialDependency> dependencies) {
			this.parent = parent;
			this.kind = kind;
			this.dependencies = Collections.unmodifiableList(new ArrayList<>(dependencies));
		}
	}

	static class DependenciesContentProvider implements ITreeContentProvider {

		private static final Object[] EMPTY_ARRAY = new Object[0];

		@Override
		public Object[] getElements(Object inputElement) {
			return ((Collection<?>)inputElement).toArray();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof DependencyTargetRuleWrapper) {
				return ((DependencyTargetRuleWrapper)parentElement).kindWrappers.toArray();
			} else if(parentElement instanceof DependencyKindWrapper) {
				return ((DependencyKindWrapper)parentElement).dependencies.toArray();
			}
			return EMPTY_ARRAY;
		}

		@Override
		public Object getParent(Object element) {
			if(element instanceof DependencyKindWrapper) {
				return ((DependencyKindWrapper)element).parent;
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return element instanceof DependencyTargetRuleWrapper
				|| element instanceof DependencyKindWrapper;
		}
	}

	static class DependencyColumnLabelProvider extends ColumnLabelProvider {

		@Override
		public String getText(Object element) {
			if(element instanceof DependencyTargetRuleWrapper) {
				DependencyTargetRuleWrapper wrapper = (DependencyTargetRuleWrapper)element;
				return EditRuleItemProvider.getEditRuleName(wrapper.targetRule);					
			} else if(element instanceof DependencyKindWrapper) {
				DependencyKindWrapper wrapper = (DependencyKindWrapper)element;
				return wrapper.kind.toString();					
			} else if(element instanceof PotentialNodeDependency) {
				PotentialNodeDependency nodeDependency = (PotentialNodeDependency)element;
				return nodeDependency.getSourceNode().toString();
			} else if(element instanceof PotentialEdgeDependency) {
				PotentialEdgeDependency edgeDependency = (PotentialEdgeDependency)element;
				return edgeDependency.getSourceEdge().toString();
			} else if(element instanceof PotentialAttributeDependency) {
				PotentialAttributeDependency attributeDependency = (PotentialAttributeDependency)element;
				return attributeDependency.getSourceAttribute().toString();
			}
			return null;
		}

		@Override
		public String getToolTipText(Object element) {
			if(element instanceof PotentialAttributeDependency) {
				PotentialAttributeDependency attributeDependency = (PotentialAttributeDependency)element;
				return attributeDependency.getSourceNode().toString();
			}
			return null;
		}

		@Override
		public Image getImage(Object element) {
			if(element instanceof PotentialDependency) {
				if(((PotentialDependency)element).isTransient()) {
					return EditruleRulebaseUiPlugin.getImageDescriptor("transient.png").createImage();
				}
			}
			return null;
		}
	}

	static class DetailColumnLabelProvider extends ColumnLabelProvider {

		@Override
		public String getText(Object element) {
			if(element instanceof DependencyTargetRuleWrapper) {
				DependencyTargetRuleWrapper wrapper = (DependencyTargetRuleWrapper)element;
				return wrapper.kindWrappers.stream()
						.map(w -> w.kind)
						.distinct()
						.map(Object::toString)
						.collect(Collectors.joining(", "));
			} else if(element instanceof DependencyKindWrapper) {
				DependencyKindWrapper wrapper = (DependencyKindWrapper)element;
				return wrapper.dependencies.stream()
						.map(DetailColumnLabelProvider::dependencyToTypeString)
						.distinct()
						.collect(Collectors.joining(", "));
			} else if(element instanceof PotentialNodeDependency) {
				PotentialNodeDependency nodeDependency = (PotentialNodeDependency)element;
				return nodeDependency.getTargetNode().toString();
			} else if(element instanceof PotentialEdgeDependency) {
				PotentialEdgeDependency edgeDependency = (PotentialEdgeDependency)element;
				return edgeDependency.getTargetEdge().toString();
			} else if(element instanceof PotentialAttributeDependency) {
				PotentialAttributeDependency attributeDependency = (PotentialAttributeDependency)element;
				return attributeDependency.getTargetAttribute().toString();
			}
			return null;
		}

		@Override
		public String getToolTipText(Object element) {
			if(element instanceof PotentialAttributeDependency) {
				PotentialAttributeDependency attributeDependency = (PotentialAttributeDependency)element;
				return attributeDependency.getTargetNode().toString();
			}
			return null;
		}

		private static String dependencyToTypeString(PotentialDependency dependency) {
			if(dependency instanceof PotentialNodeDependency) {
				return "Node";
			} else if(dependency instanceof PotentialEdgeDependency) {
				return "Edge";
			} else if(dependency instanceof PotentialAttributeDependency) {
				return "Attribute";
			}
			throw new AssertionError();
		}
	}
}
