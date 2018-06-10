/**
 * 
 */
package org.sidiff.vcmsintegration.structureview;

import java.util.EventObject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.sidiff.vcmsintegration.util.Resources;

/**
 * Save model to file
 * @author Louis Christ
 *
 */
public class SaveAction extends Action {

	/**
	 * The callback that is being notified when a the action is executed.
	 */
	private IActionHandler callback;
	
	/**
	 * Creates a new instance of the {@link SaveAction}.
	 */
	public SaveAction(IActionHandler callback) {
		Assert.isNotNull(callback);
		this.setText("Save model");
		this.setEnabled(false);
		this.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, Resources.ICON_SAVE));
		this.callback = callback;
	}

	@Override
	public void run() {
		callback.handle(new EventObject(this));
	}
}
