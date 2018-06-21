
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
		// TODO: the name is not a good value to permanently save, as it contains a time stamp
		// as a workaround, the time stamp is remove from the name
		int index = value.getName().lastIndexOf(" (");
		if(index != -1) {
			return value.getName().substring(0, index);
		}
		return value.getName();
	}

	@Override
	public String getLabel(ILiftingRuleBase value) {
		return value.getName();
	}
}
