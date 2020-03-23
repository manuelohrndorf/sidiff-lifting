package org.sidiff.integration.preferences.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Control;

public class PreferenceFieldUtil {

	public static void requestLayout(Control control) {
		// find the ScrolledComposite because PreferenceDialog does not expose it directly
		for(Control current = control; current != null; current = current.getParent()) {
			if(current instanceof ScrolledComposite) {
				ScrolledComposite scrolled = (ScrolledComposite)current;
				scrolled.setMinSize(scrolled.getContent().computeSize(SWT.DEFAULT, SWT.DEFAULT, true));
				scrolled.requestLayout();
				return;
			}
		}
	}
}
