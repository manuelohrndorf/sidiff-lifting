package org.sidiff.vcmsintegration.structureview;

import java.util.EventObject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.sidiff.vcmsintegration.util.Resources;

/**
 * An action that is used to toggle the direction of the difference that is
 * shown, since applying operation invocations depends on this direaction.
 * 
 * @author Adrian Bingener
 *
 */
public class ToggleDirectionAction extends Action {

	/**
	 * The callback that is being notified when a the action is executed.
	 */
	private IActionHandler callback;

	/**
	 * Creates a new instance of the {@link ToggleDirectionAction}.
	 */
	public ToggleDirectionAction(IActionHandler callback) {
		Assert.isNotNull(callback);
		this.setText("Switch Difference Direction");
		this.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, Resources.ICON_TOGGLE));
		this.callback = callback;
	}

	@Override
	public void run() {
		callback.handle(new EventObject(this));
	}
}
