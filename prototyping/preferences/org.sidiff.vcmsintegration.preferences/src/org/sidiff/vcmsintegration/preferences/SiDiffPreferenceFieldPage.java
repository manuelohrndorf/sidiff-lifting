package org.sidiff.vcmsintegration.preferences;

import org.eclipse.jface.resource.ImageDescriptor;
import org.sidiff.vcmsintegration.preferences.util.PreferenceUtil;

/**
 * 
 * @author Felix Breitweiser
 *
 */
public abstract class SiDiffPreferenceFieldPage extends PreferenceFieldPage {

	public SiDiffPreferenceFieldPage() {
		super();
	}
	
	public SiDiffPreferenceFieldPage(String title) {
		super(title);
	}
	
	public SiDiffPreferenceFieldPage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected String getQualifier() {
		return PreferenceUtil.getInstance().getPropertyQualifier();
	}

}
