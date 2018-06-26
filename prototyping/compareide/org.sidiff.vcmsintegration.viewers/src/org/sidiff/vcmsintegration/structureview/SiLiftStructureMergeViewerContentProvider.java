package org.sidiff.vcmsintegration.structureview;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.sidiff.common.emf.exceptions.InvalidModelException;
import org.sidiff.common.emf.exceptions.NoCorrespondencesException;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.lifting.api.LiftingFacade;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.patching.operation.OperationInvocationStatus;
import org.sidiff.vcmsintegration.SiLiftCompareConfiguration;
import org.sidiff.vcmsintegration.SiLiftCompareDifferencer;
import org.sidiff.vcmsintegration.SiLiftCompareDifferencer.IDifferenceViewerAdapter;
import org.sidiff.vcmsintegration.util.MessageDialogUtil;

/**
 * This is a special content provider that is used for the {@link TreeViewer} in
 * the SiLift structure view. This structure view shows the difference between
 * the two models. Therefore this provider will receive the two models as input,
 * but must display the difference that is being lifted by the
 * {@link LiftingFacade}. That is what this content provider does. It receives
 * the two models as input, calculates the {@link AsymmetricDifference} and
 * provides methods for the {@link TreeViewer} to show it.
 * 
 * @author Adrian Bingener, Robert Müller
 *
 */
public class SiLiftStructureMergeViewerContentProvider extends AdapterFactoryContentProvider
	implements IPropertyChangeListener, IDifferenceViewerAdapter {

	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	private SiLiftCompareDifferencer differencer;
	private SiLiftCompareConfiguration compareConfiguration;

	/**
	 * Creates a new instance of the
	 * {@link SiLiftStructureMergeViewerContentProvider}. The given display mode
	 * defines which type of difference is displayed when the viewer, that this
	 * content provider provides data for, is initially created.
	 * 
	 * @param displayMode The type of difference that is displayed
	 */
	public SiLiftStructureMergeViewerContentProvider(AdapterFactory adapterFactory,
			SiLiftCompareConfiguration compareConfiguration) {
		super(adapterFactory);
		this.differencer = SiLiftCompareDifferencer.getInstance();
		this.differencer.addDifferenceViewerAdapter(this);
		this.compareConfiguration = compareConfiguration;
		this.compareConfiguration.addPropertyChangeListener(this);
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);

		if(newInput != null) {
			try {
				differencer.loadCompareInput((ICompareInput)newInput);
				differencer.recalculateDifferences();
			} catch (IOException | CoreException | InvalidModelException | NoCorrespondencesException e) {
				MessageDialogUtil.showExceptionDialog(e);
			}
		}
	}

	@Override
	public void dispose() {
		compareConfiguration.removePropertyChangeListener(this);
		super.dispose();
	}

	/**
	 * This method is called when the view is created. The given input element
	 * represents the two compared models, when using e.g.
	 * "Compare with &gt; Each other". This method assumes that the given input
	 * is an {@link ICompareInput}, which provides methods to access the left
	 * and right model. These models are then used to derive the difference
	 * which is then used as input for the {@link TreeViewer}.
	 * 
	 * @param inputElement The input element which should be an
	 *            {@link ICompareInput}
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		switch (compareConfiguration.getDisplayMode()) {
			case SYMMETRIC_DIFFERENCE:
				SymmetricDifference symmetricDifference = differencer.getSymmetricDifference();
				if(symmetricDifference == null) {
					return EMPTY_OBJECT_ARRAY;
				}

				List<Object> elements = new LinkedList<Object>();
				// add ChangeSets
				for(SemanticChangeSet changeSet : symmetricDifference.getChangeSets()) {
					elements.add(changeSet);
				}
				// add all changes, which are not in any ChangeSet
				for(Change change : symmetricDifference.getChanges()) {
					boolean found = false;
					for(SemanticChangeSet changeSet : symmetricDifference.getChangeSets()) {
						if(changeSet.getChanges().contains(change)) {
							found = true;
							break;
						}
					}
					// add change if found
					if(!found) {
						elements.add(change);
					}
				}
				// add matching
				elements.add(symmetricDifference.getMatching());
				return elements.toArray();

			case ASYMMETRIC_DIFFERENCE:
				if(differencer.getPatchEngine() == null) {
					return EMPTY_OBJECT_ARRAY;
				}

				// get a list of all OperationInvocations, which have not
				// been applied
				List<OperationInvocation> operations = differencer.getPatchEngine().getOperationManager()
						.getOperationInvocations(OperationInvocationStatus.INIT);
				// remove OperationInvocations, which are dependencies
				operations = filterOperationInvocationsWithDependencies(operations);
				return operations.toArray();
		}

		return EMPTY_OBJECT_ARRAY;
	}

	/**
	 * Filters the given list, by removing all {@link OperationInvocation}s
	 * which have dependencies. This method returns a new list that contains
	 * only those {@link OperationInvocation}s that don't have any dependencies.
	 * 
	 * @param input A list of {@link OperationInvocation}s that is filtered
	 * @return A new list that only contains {@link OperationInvocation}s that
	 *         don't have any dependencies.
	 * @param input List of all OperationInvocation
	 * @return Filtered list of OperationInvocations
	 */
	private List<OperationInvocation> filterOperationInvocationsWithDependencies(Collection<OperationInvocation> input) {
		LinkedList<OperationInvocation> result = new LinkedList<OperationInvocation>();
		for (OperationInvocation operationInvocation : input) {
			// check if OperationInvocation has any predecessors
			// every OperationInvocation has itself as a predecessor
			if (operationInvocation.getAllSuccessors().size() == 1) {
				result.add(operationInvocation);
			}
		}
		return result;
	}

	/**
	 * Returns all children of the given object (as {@link EObject}, or an empty
	 * list if it has none or the given element is not an {@link EObject}.
	 * 
	 * @param parentElement The element that is searched for children
	 * @return All children of the given element
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		// The child elements of the OperationInvocation should be the changes
		if (parentElement instanceof OperationInvocation) {
			OperationInvocation operation = (OperationInvocation)parentElement;
			LinkedList<Object> result = new LinkedList<Object>();
			result.add(operation.getChangeSet());
			result.addAll(operation.getParameterBindings());
			// Add predecessors of OperationInvocations as children in tree view
			for (OperationInvocation predecessors : operation.getAllPredecessors()) {
				// every OperationInvocation has itself as predecessors
				// add only if OperationInvocation has not been applied yet
				if (predecessors != parentElement && differencer.getPatchEngine().getOperationManager()
						.getStatusWrapper(predecessors).getStatus() != OperationInvocationStatus.PASSED) {
					result.addFirst(predecessors);
				}
			}
			return result.toArray();
		} else if (parentElement instanceof SemanticChangeSet) {
			// show changes in SemanticChangeSet
			return ((SemanticChangeSet)parentElement).getChanges().toArray();
		}
		return super.getChildren(parentElement);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getProperty() == SiLiftCompareConfiguration.DISPLAY_MODE) {
			onRefresh();
		}
	}

	@Override
	public void onRefresh() {
		if(viewer != null) {
			viewer.refresh();
		}
	}
}
