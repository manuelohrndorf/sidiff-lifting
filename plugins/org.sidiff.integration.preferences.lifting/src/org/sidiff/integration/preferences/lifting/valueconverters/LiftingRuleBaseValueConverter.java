
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
		return value.getKey();
	}

	@Override
	public String getLabel(ILiftingRuleBase value) {
		return value.getName();
	}
}
