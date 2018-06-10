package org.sidiff.vcmsintegration.structureview;

import java.util.EventObject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.sidiff.vcmsintegration.util.Resources;

/**
 * An action that is used to apply a selected operation invocation. This action
 * does not know about the left or right side of the model. It will
 * 
 * @author Adrian Bingener
 *
 */
public class ApplyOnLeftAction extends Action {

	/**
	 * The callback that is being notified when a the action is executed.
	 */
	private IActionHandler callback;

	/**
	 * Creates a new instance of the {@link ApplyOnLeftAction}.
	 */
	public ApplyOnLeftAction(IActionHandler callback) {
		Assert.isNotNull(callback);
		this.setText("Apply selected operation");
		this.setEnabled(false);
		this.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, Resources.ICON_MERGE_TO_LEFT));
		this.callback = callback;
	}

	@Override
	public void run() {
		callback.handle(new EventObject(this));
	}
}
