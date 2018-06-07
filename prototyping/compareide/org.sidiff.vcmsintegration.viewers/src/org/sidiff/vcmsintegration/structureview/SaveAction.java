/**
 * 
 */
package org.sidiff.vcmsintegration.structureview;

import java.util.EventObject;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
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
	private Handler callback;
	
	/**
	 * Creates a new instance of the {@link SaveAction}.
	 */
	public SaveAction(Handler callback) {
		this.setText("Save model");
		this.setEnabled(false);
		this.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, Resources.ICON_SAVE));
		this.callback = callback;
	}

	@Override
	public void run() {
		if (callback != null) {
			callback.handle(new EventObject(this));
		} else {
			LogUtil.log(LogEvent.WARNING, "Cannot execute action. Callback is null");
		}
	}
	
}
