/******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Svyatoslav Kovalsky (Montages) - initial API and implementation 
 ****************************************************************************/

package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.directedit;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @since 3.2
 */
public class ComboDirectEditManager extends org.eclipse.gmf.runtime.diagram.ui.tools.DirectEditManagerBase {

	/**
	 * constructor
	 * 
	 * @param source
	 *            <code>GraphicalEditPart</code> to support direct edit of. The
	 *            figure of the <code>source</code> edit part must be of type
	 *            <code>WrapLabel</code>.
	 */
	public ComboDirectEditManager(ITextAwareEditPart source) {
		super(source);
	}

	/**
	 * @param source
	 * @param editorType
	 * @param locator
	 */
	public ComboDirectEditManager(GraphicalEditPart source, Class<?> editorType, CellEditorLocator locator) {
		super(source, editorType, locator);
	}

	protected CellEditor doCreateCellEditorOn(Composite composite) {
		ITextAwareEditPart textEditPart = (ITextAwareEditPart) getEditPart();
		IParser parser = textEditPart.getParser();
		IChoiceParser choiceParser;
		if (parser instanceof IChoiceParser) {
			choiceParser = (IChoiceParser) parser;
		} else {
			throw new RuntimeException("IChoiceParser is expected but " + parser //$NON-NLS-1$
					+ " found for EditPart: " + getEditPart() //$NON-NLS-1$
					+ " with model: " + getEditPart().getModel()); //$NON-NLS-1$
		}

		if (getEditPart() instanceof IGraphicalEditPart) {
			EObject parserElement = ((IGraphicalEditPart) getEditPart()).resolveSemanticElement();
			List<String> editChoices = choiceParser.getEditChoices(new EObjectAdapter(parserElement));
			return new ComboCellEditorEx(composite, editChoices.toArray(new String[editChoices.size()]));
		} else {
			throw new RuntimeException("IGraphicalEditPart is expected but " + getEditPart() + " found "); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	protected void createContentAssistant(Control control, Color proposalPopupForegroundColor, Color proposalPopupBackgroundColor, IContentAssistProcessor processor) {
		// There is no sense in content assistant for Combo manager, since both provide list of strings to choose from
	}

	public interface IChoiceParser extends IParser {

		public List<String> getEditChoices(IAdaptable element);
	}
}
