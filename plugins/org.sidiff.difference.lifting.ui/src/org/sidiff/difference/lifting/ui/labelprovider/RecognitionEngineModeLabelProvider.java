package org.sidiff.difference.lifting.ui.labelprovider;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.sidiff.difference.lifting.api.settings.RecognitionEngineMode;

public class RecognitionEngineModeLabelProvider extends ColumnLabelProvider {

	@Override
	public String getText(Object element) {
		if(element instanceof RecognitionEngineMode) {
			switch((RecognitionEngineMode)element) {
				case NO_LIFTING: return "No Semantic Lifting (Operation Detection)";
				case LIFTING: return "Semantic Lifting (Operation Detection)";
				case LIFTING_AND_POST_PROCESSING: return "Semantic Lifting and Post Processing (Default)";
			};
		}
		return super.getText(element);
	}
}
