package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.GMFToolingRuntimePlugin;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.directedit.ComboDirectEditManager;

public abstract class ChoiceParserBase extends AbstractFeatureParser implements ComboDirectEditManager.IChoiceParser {
	private static final int SINGLE_FEATURE_INDEX = 0;

	public ChoiceParserBase(EStructuralFeature feature) {
		super(new EStructuralFeature[] { feature });
	}

	protected final EStructuralFeature getFeature() {
		return getEditableFeatures()[SINGLE_FEATURE_INDEX];
	}

	public String getEditString(IAdaptable adapter, int flags) {
		EObject element = (EObject) adapter.getAdapter(EObject.class);
		return getEditChoice(element, getEditableValues(element)[SINGLE_FEATURE_INDEX]);
	}

	public IParserEditStatus isValidEditString(IAdaptable adapter, String editString) {
		if (getEditChoices(adapter).contains(editString)) {
			return ParserEditStatus.EDITABLE_STATUS;
		}
		return new ParserEditStatus(GMFToolingRuntimePlugin.ID, IParserEditStatus.UNEDITABLE, editString);
	}

	public ICommand getParseCommand(IAdaptable adapter, String newString, int flags) {
		EObject element = (EObject) adapter.getAdapter(EObject.class);
		return getParseCommand(adapter, new Object[] { findItem(element, newString) }, flags);
	}

	public String getPrintString(IAdaptable adapter, int flags) {
		return getEditString(adapter, flags);
	}

	public List<String> getEditChoices(IAdaptable adapter) {
		EObject element = (EObject) adapter.getAdapter(EObject.class);
		List<String> result = new ArrayList<String>();
		for (Object item : getItems(element)) {
			result.add(getEditChoice(element, item));
		}
		return result;
	}

	private Object findItem(EObject element, String editChoice) {
		for (Object item : getItems(element)) {
			if (editChoice.equals(getEditChoice(element, item))) {
				return item;
			}
		}
		return null;
	}

	protected abstract Collection<Object> getItems(EObject element);

	protected abstract String getEditChoice(EObject element, Object item);
}
