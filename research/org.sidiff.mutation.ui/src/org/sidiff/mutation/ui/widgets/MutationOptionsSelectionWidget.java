package org.sidiff.mutation.ui.widgets;

import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.sidiff.common.ui.widgets.IWidget;
import org.sidiff.common.ui.widgets.IWidgetSelection;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.mutation.config.MutationConfig;
import org.sidiff.mutation.selection.AbstractSelection;
import org.sidiff.mutation.selection.IntactSelection;
import org.sidiff.mutation.selection.RandomSelection;
import org.sidiff.mutation.selection.context.SimilarityContextSelection;
import org.sidiff.mutation.selection.operator.SimilarityOperatorSelection;

public class MutationOptionsSelectionWidget implements IWidget,
		IWidgetSelection, IWidgetValidation {

	private Composite container;

	private Spinner mutationOrderSpinner;
	private Button validateMutantsCheckBox;
	private Button opIntact;
	private Button opRandom;
	private Button opSimilarity;
	private Button ctxIntact;
	private Button ctxRandom;
	private Button ctxSimilarity;
	private Spinner opCoverageSpinner;
	private Spinner ctxCoverageSpinner;
	private Spinner opRadiusSpinner;
	private Spinner ctxRadiusSpinner;
	private Spinner opMinimumSpinner;
	private Spinner ctxMinimumSpinner;
	private Spinner opMaximumSpinner;
	private Spinner ctxMaximumSpinner;

	private ValidationMessage vm = new ValidationMessage(
			ValidationMessage.ValidationType.OK, null);

	public MutationOptionsSelectionWidget() {
		super();

	}

	public void updateConfiguration(MutationConfig config) {
		if (container == null)
			throw new RuntimeException("Controils not created");
		config.setMutationOrder(mutationOrderSpinner.getSelection());
		config.setValidateMutants(validateMutantsCheckBox.getSelection());
		AbstractSelection<EditRule> aos;
		AbstractSelection<Match> acs;
		int opCoverage = opCoverageSpinner.getSelection();
		int opMin = opMinimumSpinner.getSelection();
		int opMax = opMaximumSpinner.getSelection();
		int opRadius = opRadiusSpinner.getSelection();
		if (opIntact.getSelection()) {
			aos = new IntactSelection<EditRule>(null, null, opCoverage, opMin,
					opMax, false); // TODO DuplicateCandiateSelection?
		} else if (opRandom.getSelection()) {
			aos = new RandomSelection<EditRule>(null, null, opCoverage, opMin,
					opMax, false); // TODO DuplicateCandiatSeelection?
		} else if (opSimilarity.getSelection()) {
			aos = new SimilarityOperatorSelection(null, null, opCoverage,
					opMin, opMax, false, false, opRadius);
			// TODO  DuplicateCandiateSelection, InvertSort?
		} else {
			throw new RuntimeException("No operation selection");
		}
		int ctxCoverage = ctxCoverageSpinner.getSelection();
		int ctxMin = ctxMinimumSpinner.getSelection();
		int ctxMax = ctxMaximumSpinner.getSelection();
		int ctxRadius = ctxRadiusSpinner.getSelection();
		if (ctxIntact.getSelection()) {
			acs = new IntactSelection<Match>(null, null, ctxCoverage, ctxMin,
					ctxMax, false);// TODO DuplicateCandiatSelection?
		} else if (ctxRandom.getSelection()) {
			acs = new RandomSelection<Match>(null, null, ctxCoverage, ctxMin,
					ctxMax, false);// TODO DuplicateCandiatSelection?
		} else if (ctxSimilarity.getSelection()) {
			acs = new SimilarityContextSelection(null, null, ctxCoverage,
					ctxMin, ctxMax, false, false, ctxRadius); 
			// TODO DuplicateCandiatSelection,  InvertSort?
		} else {
			throw new RuntimeException("No context selection");
		}
		config.setAcs(acs);
		config.setAos(aos);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Composite createControl(Composite parent) {

		container = new Composite(parent, SWT.NONE);
		{
			GridLayout layout = new GridLayout(1, false);
			container.setLayout(layout);
		}

		{
			Composite container0 = new Composite(container, SWT.NONE);
			container0.setLayout(new GridLayout(3, false));
			container0.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			Label label = new Label(container0, SWT.NONE);
			label.setText("Mutation order");
			mutationOrderSpinner = new Spinner(container0, SWT.BORDER);
			mutationOrderSpinner.setMaximum(9);
			mutationOrderSpinner.setMinimum(1);
			mutationOrderSpinner.setSelection(1);
			validateMutantsCheckBox = new Button(container0, SWT.CHECK);
			validateMutantsCheckBox.setText("Validate Mutants");
			validateMutantsCheckBox.setSelection(true);
		}
		
		{
			(new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL)).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		}

		{
			Composite container0 = new Composite(container, SWT.NONE);
			{
				GridLayout layout = new GridLayout(3, false);
				container0.setLayout(layout);
				container0.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			}
			
			/*
			 * Header
			 */
			(new Label(container0, SWT.NONE)).setText("");
			(new Label(container0, SWT.NONE)).setText("Operation");
			(new Label(container0, SWT.NONE)).setText("Context");

			/*
			 * Selection
			 */
			Label label = new Label(container0, SWT.NONE);
			label.setText("Selection");
			label.setLayoutData(new GridData(GridData.FILL_VERTICAL));
			Composite opRadioButtonContainer = new Composite(container0,
					SWT.NONE);
			{
				GridLayout layout = new GridLayout(1, false);
				layout.horizontalSpacing = 0;
				opRadioButtonContainer.setLayout(layout);
			}
			opIntact = new Button(opRadioButtonContainer, SWT.RADIO);
			opIntact.setText("Intact");
			opRandom = new Button(opRadioButtonContainer, SWT.RADIO);
			opRandom.setText("Random");
			opSimilarity = new Button(opRadioButtonContainer, SWT.RADIO);
			opSimilarity.setText("Similarity-based");
			opSimilarity.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					boolean selected = ((Button) e.widget).getSelection();
					opRadiusSpinner.setEnabled(selected);
				}
			});

			opIntact.setSelection(false);
			opSimilarity.setSelection(false);
			opRandom.setSelection(true);

			Composite ctxRadioButtonContainer = new Composite(container0,
					SWT.NONE);
			{
				GridLayout layout = new GridLayout(1, false);
				layout.horizontalSpacing = 0;
				ctxRadioButtonContainer.setLayout(layout);
			}
			ctxIntact = new Button(ctxRadioButtonContainer, SWT.RADIO);
			ctxIntact.setText("Intact");
			ctxRandom = new Button(ctxRadioButtonContainer, SWT.RADIO);
			ctxRandom.setText("Random");
			ctxSimilarity = new Button(ctxRadioButtonContainer, SWT.RADIO);
			ctxSimilarity.setText("Similarity-based");
			ctxSimilarity.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					boolean selected = ((Button) e.widget).getSelection();
					ctxRadiusSpinner.setEnabled(selected);
				}
			});
			ctxIntact.setSelection(false);
			ctxSimilarity.setSelection(true);
			ctxRandom.setSelection(false);

			/*
			 * Radius
			 */
			(new Label(container0, SWT.NONE)).setText("Radius");
			opRadiusSpinner = createSpinner(container0, 1, 10, 1);
			ctxRadiusSpinner = createSpinner(container0, 1, 10, 1);
			opRadiusSpinner.setEnabled(opSimilarity.getSelection());
			ctxRadiusSpinner.setEnabled(ctxSimilarity.getSelection());

			/*
			 * Coverage
			 */
			(new Label(container0, SWT.NONE)).setText("Coverage (%)");
			opCoverageSpinner = createSpinner(container0, 0, 100, 100);
			ctxCoverageSpinner = createSpinner(container0, 0, 100, 50);

			/*
			 * Minimum
			 */
			(new Label(container0, SWT.NONE))
					.setText("Minimum Selected Elements");
			opMinimumSpinner = createSpinner(container0, 1, Integer.MAX_VALUE, 1);
			ctxMinimumSpinner = createSpinner(container0, 1, Integer.MAX_VALUE,
					1);

			/*
			 * Maximum
			 */
			(new Label(container0, SWT.NONE))
					.setText("Maximum Selected Elements");
			opMaximumSpinner = createSpinner(container0, 1, Integer.MAX_VALUE,
					10);
			ctxMaximumSpinner = createSpinner(container0, 1, Integer.MAX_VALUE,
					50);
		}

		return container;

	}

	private Spinner createSpinner(Composite container, int min, int max,
			int selected) {
		Spinner spinner = new Spinner(container, SWT.BORDER);
		spinner.setMinimum(min);
		spinner.setMaximum(max);
		spinner.setSelection(selected);
		return spinner;
	}

	@Override
	public boolean validate() {
		if (container == null) {
			vm = new ValidationMessage(ValidationMessage.ValidationType.ERROR,
					"(internal) Controls not created");
			return false;
		}
		try {
			if (Integer.parseInt(opMinimumSpinner.getText()) > Integer
					.parseInt(opMaximumSpinner.getText())) {
				vm = new ValidationMessage(
						ValidationMessage.ValidationType.ERROR,
						"The minumum selection value for operation must be smaller than maximum selection value");
				return false;
			}
		} catch (NumberFormatException e) {
			vm = new ValidationMessage(ValidationMessage.ValidationType.ERROR,
					"Illegal value");
			return false;
		}
		try {
			if (Integer.parseInt(ctxMinimumSpinner.getText()) > Integer
					.parseInt(ctxMaximumSpinner.getText())) {
				vm = new ValidationMessage(
						ValidationMessage.ValidationType.ERROR,
						"The minumum selection value for context must be smaller than maximum selection value");
				return false;
			}
		} catch (NumberFormatException e) {
			vm = new ValidationMessage(ValidationMessage.ValidationType.ERROR,
					"Illegal value");
			return false;
		}
		vm = new ValidationMessage(ValidationMessage.ValidationType.OK, null);
		return true;
	}

	@Override
	public ValidationMessage getValidationMessage() {
		return vm;
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		if (container == null)
			throw new RuntimeException("Create controls first!");
		mutationOrderSpinner.addSelectionListener(listener);
		validateMutantsCheckBox.addSelectionListener(listener);
		opIntact.addSelectionListener(listener);
		opRandom.addSelectionListener(listener);
		opSimilarity.addSelectionListener(listener);
		opCoverageSpinner.addSelectionListener(listener);
		opRadiusSpinner.addSelectionListener(listener);
		opMinimumSpinner.addSelectionListener(listener);
		opMaximumSpinner.addSelectionListener(listener);
		ctxIntact.addSelectionListener(listener);
		ctxRandom.addSelectionListener(listener);
		ctxSimilarity.addSelectionListener(listener);
		ctxCoverageSpinner.addSelectionListener(listener);
		ctxRadiusSpinner.addSelectionListener(listener);
		ctxMinimumSpinner.addSelectionListener(listener);
		ctxMaximumSpinner.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		mutationOrderSpinner.addSelectionListener(listener);
		validateMutantsCheckBox.addSelectionListener(listener);
		opIntact.removeSelectionListener(listener);
		opRandom.removeSelectionListener(listener);
		opSimilarity.removeSelectionListener(listener);
		opCoverageSpinner.removeSelectionListener(listener);
		opRadiusSpinner.removeSelectionListener(listener);
		opMinimumSpinner.removeSelectionListener(listener);
		opMaximumSpinner.removeSelectionListener(listener);
		ctxIntact.removeSelectionListener(listener);
		ctxRandom.removeSelectionListener(listener);
		ctxSimilarity.removeSelectionListener(listener);
		ctxCoverageSpinner.removeSelectionListener(listener);
		ctxRadiusSpinner.removeSelectionListener(listener);
		ctxMinimumSpinner.removeSelectionListener(listener);
		ctxMaximumSpinner.removeSelectionListener(listener);
	}

	@Override
	public Composite getWidget() {
		return null;
	}

	@Override
	public void setLayoutData(Object layoutData) {
		if(container == null) throw new RuntimeException("Controls not created");
		container.setLayoutData(layoutData);
	}

	// public enum MutationMode {
	// INTACT, RANDOM, SIMILARITY
	// }
}
