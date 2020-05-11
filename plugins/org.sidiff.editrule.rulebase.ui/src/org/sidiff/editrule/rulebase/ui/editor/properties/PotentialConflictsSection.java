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

import org.eclipse.jface.viewers.CellLabelProvider;
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
import org.sidiff.editrule.rulebase.PotentialAttributeConflict;
import org.sidiff.editrule.rulebase.PotentialConflict;
import org.sidiff.editrule.rulebase.PotentialConflictKind;
import org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialNodeConflict;
import org.sidiff.editrule.rulebase.RuleBase;
import org.sidiff.editrule.rulebase.RuleBaseItem;
import org.sidiff.editrule.rulebase.provider.EditRuleItemProvider;
import org.sidiff.editrule.rulebase.ui.editor.RulebaseEditor;
import org.sidiff.editrule.rulebase.ui.internal.EditruleRulebaseUiPlugin;

/**
 * Property section which shows the outgoing {@link PotentialConflict}s
 * of the edit rule(s) selected in the {@link RulebaseEditor}.
 * @author rmueller
 */
public class PotentialConflictsSection extends AbstractPropertySection {

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

			// Get all outgoing conflicts of the source rule/s
			// and group them by target rule, then conflict kind.
			treeViewer.setInput(
				Stream.of(ruleBase.getPotentialNodeConflicts(),
						ruleBase.getPotentialEdgeConflicts(),
						ruleBase.getPotentialAttributeConflicts(),
						ruleBase.getPotentialDanglingEdgeConflicts())
					.flatMap(Collection::stream)
					.filter(conflict -> selectedRules.contains(conflict.getSourceRule()))
					.collect(Collectors.groupingBy(PotentialConflict::getTargetRule,
							Collectors.groupingBy(PotentialConflict::getKind)))
					.entrySet().stream() // copy into wrapper classes
						.sorted(Comparator.comparing(Map.Entry::getKey, Comparator.comparing(EditRuleItemProvider::getEditRuleName)))
						.map(ConflictTargetRuleWrapper::new)
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
		treeViewer.setContentProvider(new ConflictsContentProvider());
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

		createColumn(treeViewer, "Potential Conflict", 400, new ConflictsColumnLabelProvider());
		createColumn(treeViewer, "Details", 300, new DetailsColumnLabelProvider());

		ColumnViewerToolTipSupport.enableFor(treeViewer);
	}

	private static TreeViewerColumn createColumn(TreeViewer treeViewer, String title, int width, CellLabelProvider labelProvider) {
		TreeViewerColumn viewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(width);
		column.setResizable(true);
		column.setMoveable(false);
		viewerColumn.setLabelProvider(labelProvider);
		return viewerColumn;
	}

	static class ConflictTargetRuleWrapper {
		final EditRule targetRule;
		final List<ConflictKindWrapper> kindWrappers;

		public ConflictTargetRuleWrapper(Map.Entry<EditRule,
				? extends Map<PotentialConflictKind,? extends Collection<? extends PotentialConflict>>> wrapped) {
			this.targetRule = wrapped.getKey();
			this.kindWrappers = Collections.unmodifiableList(
					wrapped.getValue().entrySet().stream()
						.map(entry -> new ConflictKindWrapper(this, entry.getKey(), entry.getValue()))
						.sorted(Comparator.comparing(wrapper -> wrapper.kind.name()))
						.collect(Collectors.toList()));
		}
	}

	static class ConflictKindWrapper {
		final ConflictTargetRuleWrapper parent;
		final PotentialConflictKind kind;
		final List<PotentialConflict> conflicts;

		public ConflictKindWrapper(ConflictTargetRuleWrapper parent, PotentialConflictKind kind,
				Collection<? extends PotentialConflict> conflicts) {
			this.parent = parent;
			this.kind = kind;
			this.conflicts = Collections.unmodifiableList(new ArrayList<>(conflicts));
		}
	}

	static class ConflictsContentProvider implements ITreeContentProvider {

		private static final Object[] EMPTY_ARRAY = new Object[0];

		@Override
		public Object[] getElements(Object inputElement) {
			return ((Collection<?>)inputElement).toArray();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof ConflictTargetRuleWrapper) {
				return ((ConflictTargetRuleWrapper)parentElement).kindWrappers.toArray();
			} else if(parentElement instanceof ConflictKindWrapper) {
				return ((ConflictKindWrapper)parentElement).conflicts.toArray();
			}
			return EMPTY_ARRAY;
		}

		@Override
		public Object getParent(Object element) {
			if(element instanceof ConflictKindWrapper) {
				return ((ConflictKindWrapper)element).parent;
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return element instanceof ConflictTargetRuleWrapper
				|| element instanceof ConflictKindWrapper;
		}
	}

	static class ConflictsColumnLabelProvider extends ColumnLabelProvider {

		@Override
		public String getText(Object element) {
			if(element instanceof ConflictTargetRuleWrapper) {
				ConflictTargetRuleWrapper wrapper = (ConflictTargetRuleWrapper)element;
				return EditRuleItemProvider.getEditRuleName(wrapper.targetRule);					
			} else if(element instanceof ConflictKindWrapper) {
				ConflictKindWrapper wrapper = (ConflictKindWrapper)element;
				return wrapper.kind.toString();
			} else if(element instanceof PotentialNodeConflict) {
				PotentialNodeConflict nodeConflict = (PotentialNodeConflict)element;
				return nodeConflict.getSourceNode().toString();
			} else if(element instanceof PotentialEdgeConflict) {
				PotentialEdgeConflict edgeConflict = (PotentialEdgeConflict)element;
				return edgeConflict.getSourceEdge().toString();
			} else if(element instanceof PotentialAttributeConflict) {
				PotentialAttributeConflict attributeConflict = (PotentialAttributeConflict)element;
				return attributeConflict.getSourceAttribute().toString();
			} else if(element instanceof PotentialDanglingEdgeConflict) {
				PotentialDanglingEdgeConflict danglingEdgeConflict = (PotentialDanglingEdgeConflict)element;
				return danglingEdgeConflict.getCreationEdge().toString();
			}
			return null;
		}

		@Override
		public String getToolTipText(Object element) {
			if(element instanceof PotentialAttributeConflict) {
				PotentialAttributeConflict attributeConflict = (PotentialAttributeConflict)element;
				return attributeConflict.getSourceNode().toString();
			}
			return null;
		}

		@Override
		public Image getImage(Object element) {
			if(element instanceof PotentialConflict) {
				if(((PotentialConflict)element).isDuplicate()) {
					return EditruleRulebaseUiPlugin.getImageDescriptor("duplicate.png").createImage();
				}
			}
			return null;
		}
	}

	static class DetailsColumnLabelProvider extends ColumnLabelProvider {

		@Override
		public String getText(Object element) {
			if(element instanceof ConflictTargetRuleWrapper) {
				ConflictTargetRuleWrapper wrapper = (ConflictTargetRuleWrapper)element;
				return wrapper.kindWrappers.stream()
						.map(w -> w.kind)
						.distinct()
						.map(Object::toString)
						.collect(Collectors.joining(", "));
			} else if(element instanceof ConflictKindWrapper) {
				ConflictKindWrapper wrapper = (ConflictKindWrapper)element;
				return wrapper.conflicts.stream()
						.map(DetailsColumnLabelProvider::conflictToTypeString)
						.distinct()
						.collect(Collectors.joining(", "));
			} else if(element instanceof PotentialNodeConflict) {
				PotentialNodeConflict nodeConflict = (PotentialNodeConflict)element;
				return nodeConflict.getTargetNode().toString();
			} else if(element instanceof PotentialEdgeConflict) {
				PotentialEdgeConflict edgeConflict = (PotentialEdgeConflict)element;
				return edgeConflict.getTargetEdge().toString();
			} else if(element instanceof PotentialAttributeConflict) {
				PotentialAttributeConflict attributeConflict = (PotentialAttributeConflict)element;
				return attributeConflict.getTargetAttribute().toString();
			} else if(element instanceof PotentialDanglingEdgeConflict) {
				PotentialDanglingEdgeConflict danglingEdgeConflict = (PotentialDanglingEdgeConflict)element;
				return danglingEdgeConflict.getDeletionNode().toString();
			}
			return null;
		}

		@Override
		public String getToolTipText(Object element) {
			if(element instanceof PotentialAttributeConflict) {
				PotentialAttributeConflict attributeConflict = (PotentialAttributeConflict)element;
				return attributeConflict.getTargetNode().toString();
			}
			return null;
		}

		private static String conflictToTypeString(PotentialConflict conflict) {
			if(conflict instanceof PotentialNodeConflict) {
				return "Node";
			} else if(conflict instanceof PotentialEdgeConflict) {
				return "Edge";
			} else if(conflict instanceof PotentialAttributeConflict) {
				return "Attribute";
			} else if(conflict instanceof PotentialDanglingEdgeConflict) {
				return "Dangling-Edge";
			} 
			throw new AssertionError();
		}
	}
}
