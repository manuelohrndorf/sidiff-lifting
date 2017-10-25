package org.sidiff.vcmsintegration.preferences.difference.interfaces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.util.TechnicalDifferenceUtils;
import org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab;
import org.sidiff.vcmsintegration.preferences.fieldeditors.OrderListSelectField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.ValueAndLabelProvider;

/**
 * 
 * Abstract class for the creation of domain specific difference settings tabs.
 * @author Daniel Roedder, cpietsch
 */
public abstract class AbstractSiDiffDomainDifferencePreferenceTab implements ISiDiffDomainPreferenceTab {
	
	/**
	 * List to hold all {@link org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField}
	 */
	private ArrayList<PreferenceField> fieldList;
	
	/**
	 * {@link org.sidiff.vcmsintegration.preferences.fieldeditors.OrderListSelectField} for the {@link ITechnicalDifferenceBuilder}s
	 */
	private OrderListSelectField techDiffBuilderField;

	/**
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Difference";
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.domains.interfaces.ISiDiffDomainPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		
		fieldList = new ArrayList<PreferenceField>();
		
		Set<String> documentTypes = new HashSet<String>();
		documentTypes.add(getDocumentType());
		
		List<ITechnicalDifferenceBuilder> techDiffBuilders = TechnicalDifferenceUtils.getAvailableTechnicalDifferenceBuilders(new HashSet<String>());
		
		techDiffBuilderField = OrderListSelectField.createFromIterable(
				getDocumentType().concat("technicalDifferenceBuilderOrder"), "Technical Difference Builder Order",
				techDiffBuilders, new ValueAndLabelProvider<ITechnicalDifferenceBuilder>() {
					public String[] get(ITechnicalDifferenceBuilder input) {
						return new String[] { input.getKey(), input.getName() };
					}
				});
		
		fieldList.add(techDiffBuilderField);
		
		return fieldList;
	}

	/**
	 * Not implemented interface method.
	 * Subclasses must implement.
	 */
	@Override
	public abstract String getDocumentType();

}
