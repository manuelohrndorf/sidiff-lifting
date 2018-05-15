
package org.sidiff.integration.preferences.lifting.valueconverters;

import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.integration.preferences.valueconverters.IPreferenceValueConverter;

/**
 * 
 * @author Robert Müller
 *
 */
public class LiftingRuleBaseValueConverter implements IPreferenceValueConverter<ILiftingRuleBase> {

	@Override
	public String getValue(ILiftingRuleBase value) {
		// TODO: this is not a good value to permanently save
		return value.getName();
	}

	@Override
	public String getLabel(ILiftingRuleBase value) {
		return value.getName();
	}
}
