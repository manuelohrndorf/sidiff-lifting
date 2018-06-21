package org.sidiff.slicer.structural.configuration.presentation.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditor;
import org.sidiff.slicer.structural.configuration.presentation.ConfigurationEditorPlugin;
import org.sidiff.slicer.structural.configuration.util.ConfigurationUtil;

/**
 * Property section that contains a textbox to edit the constraint for model
 * elements that support constraints.
 * @author rmueller
 *
 */
public class ConstraintPropertySection extends AbstractPropertySection
{
	/**
	 * Text input for the constraint expression
	 */
	private Text expressionText;

	/**
	 * Reference to the configuration editor
	 */
	private ConfigurationEditor confEditor;

	/**
	 * The selected element
	 */
	private EObject inputObject;

	/**
	 * The last value of the constraint expression. Updated when the text gains
	 * focus.
	 */
	private String lastValue = null;

	/**
	 * Flag to indicate that auto completion has already been activated for the textbox
	 * to prevent it from being activated twice.
	 */
	private boolean autoCompletionActivated = false;

	/**
	 * The tabbed property sheet page containing this section.
	 */
	private TabbedPropertySheetPage tabbedPropertySheetPage;

	/**
	 * Listener to update the configuration when the focus is lost and the
	 * constraint changed
	 */
	private final FocusListener focusListener = new FocusListener()
	{
		@Override
		public void focusLost(FocusEvent e)
		{
			updateConstraint();
		}

		@Override
		public void focusGained(FocusEvent e)
		{
			// save the current value when focus is gained
			lastValue = expressionText.getText();
			updateStatusLine();
		}
	};

	/**
	 * Default constructor needed for {@link TabDescriptorProvider}
	 */
	public ConstraintPropertySection()
	{
		super();
	}

	/**
	 * Enables auto completion for the text box.
	 */
	protected void enableAutoCompletion()
	{
		if(autoCompletionActivated)
			return;

		if(confEditor.getConfig().getConstraintInterpreter() == null)
			return;

		autoCompletionActivated = true;

		IContentProposalProvider cpp = new IContentProposalProvider()
		{
			@Override
			public IContentProposal[] getProposals(String contents, int position)
			{
				return confEditor.getConfig().getConstraintInterpreter().getSyntaxHelp(inputObject, contents, position);
			}
		};

		// With the key stroke and the activation chars being null, the proposals are shown every time a key is typed.
		// ContentProposalAdapter needs to be rewritten completely to allow a sequence of characters to activate
		// the display of proposals.
		ContentProposalAdapter adapter = new ContentProposalAdapter(expressionText, new TextContentAdapter(), cpp, null, null);
		adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_INSERT);
	}

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage)
	{
		super.createControls(parent, tabbedPropertySheetPage);
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
		Composite composite = getWidgetFactory().createFlatFormComposite(parent);

		// create input for constraint expression
		expressionText = getWidgetFactory().createText(composite, "", SWT.V_SCROLL | SWT.WRAP | SWT.MULTI); //$NON-NLS-1$
		FormData data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		data.bottom = new FormAttachment(0, 100);
		expressionText.setLayoutData(data);
		expressionText.addFocusListener(focusListener);

		// create label
		CLabel expressionLabel = getWidgetFactory().createCLabel(composite,
				ConfigurationEditorPlugin.INSTANCE.getString("_UI_Property_Expression_label")); //$NON-NLS-1$
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(expressionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(expressionText, 0, SWT.CENTER);
		expressionLabel.setLayoutData(data);
	}

	@Override
	public void setInput(IWorkbenchPart part, ISelection selection)
	{
		super.setInput(part, selection);

		// get reference to configuration editor
		confEditor = (ConfigurationEditor)part;
		enableAutoCompletion();
		
		// the input should always be an EClass or EReference because the section is only shown for those two
		Object input = ((IStructuredSelection)selection).getFirstElement();
		if(input instanceof EObject)
			this.inputObject = (EObject)input;
	}

	@Override
	public void refresh()
	{
		expressionText.removeFocusListener(focusListener);

		// set text for constraint
		expressionText.setText(ConfigurationUtil.getConstraintExpression(confEditor.getConfig(), inputObject));

		expressionText.addFocusListener(focusListener);
	}

	/**
	 * Saves the constraint expression if it changed using the editing domain.
	 */
	protected void updateConstraint()
	{
		String newValue = expressionText.getText();
		
		// check if current value differs from value on focus gain
		if(lastValue != null && !lastValue.equals(newValue))
		{
			// update the constraint
			ConfigurationUtil.setConstraintExpression(confEditor.getEditingDomain(),
					confEditor.getConfig(), inputObject, newValue);
		}
		
		// update last value
		lastValue = newValue;
	}

	/**
	 * Updates the status line to show the ID of the current constraint interpreter
	 * or an error message if no constraint interpreter has been specified.
	 */
	protected void updateStatusLine()
	{
		IStatusLineManager statusLineManager = tabbedPropertySheetPage.getSite().getActionBars().getStatusLineManager();
		if(confEditor.getConfig().getConstraintInterpreter() == null)
		{
			statusLineManager.setErrorMessage(ConfigurationEditorPlugin.getSubstitutedString(
					"_UI_Property_StatusLine_NoConstraintInterpreter")); //$NON-NLS-1$
		}
		else
		{
			statusLineManager.setErrorMessage(null);
			statusLineManager.setMessage(ConfigurationEditorPlugin.getSubstitutedString(
					"_UI_Property_StatusLine_CurrentConstraintInterpreter", //$NON-NLS-1$
					confEditor.getConfig().getConstraintInterpreter().getID()));
		}
	}
}
