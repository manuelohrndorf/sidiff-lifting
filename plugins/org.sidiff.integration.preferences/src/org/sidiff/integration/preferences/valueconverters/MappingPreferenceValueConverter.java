package org.sidiff.integration.preferences.valueconverters;

import java.util.Map;

/**
 * A {@link IPreferenceValueConverter preference value converter} that uses a
 * map to convert preference values to labels. Values are returned unchanged,
 * labels are retrieved using <code>value2label.get(value)</code>.
 * @author rmueller
 */
public class MappingPreferenceValueConverter implements IPreferenceValueConverter<String> {

	private Map<String, String> value2label;

	/**
	 * Creates a new <code>MappingPreferenceValueConverter</code> using the given
	 * value to label mapping. <b>NOTE: The <u>keys</u> of this mapping should be set as the
	 * <u>input values</u> for the corresponding preference field.</b>
	 * @param value2label map of value to label entries
	 */
	public MappingPreferenceValueConverter(Map<String, String> value2label) {
		this.value2label = value2label;
	}

	@Override
	public String getValue(String value) {
		return value;
	}

	@Override
	public String getLabel(String value) {
		return value2label.get(value);
	}
}
