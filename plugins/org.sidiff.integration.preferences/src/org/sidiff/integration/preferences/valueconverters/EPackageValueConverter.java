package org.sidiff.integration.preferences.valueconverters;

import org.eclipse.emf.ecore.EPackage;

public class EPackageValueConverter implements IPreferenceValueConverter<EPackage> {

	public static final EPackageValueConverter INSTANCE = new EPackageValueConverter();
	
	@Override
	public String getValue(EPackage object) {
		return object.getNsURI();
	}

	@Override
	public String getLabel(EPackage object) {
		return object.getName() + " (" + object.getNsURI() + ")";
	}
}
