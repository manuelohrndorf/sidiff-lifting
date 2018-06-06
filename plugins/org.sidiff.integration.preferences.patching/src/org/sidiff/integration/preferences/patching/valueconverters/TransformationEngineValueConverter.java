package org.sidiff.integration.preferences.patching.valueconverters;

import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;
import org.sidiff.patching.transformation.ITransformationEngine;

/**
 * 
 * @author Robert M�ller
 *
 */
public class TransformationEngineValueConverter implements IPreferenceValueConverter<ITransformationEngine> {

	@Override
	public String getValue(ITransformationEngine value) {
		return value.getKey();
	}

	@Override
	public String getLabel(ITransformationEngine value) {
		return value.getName();
	}
}
