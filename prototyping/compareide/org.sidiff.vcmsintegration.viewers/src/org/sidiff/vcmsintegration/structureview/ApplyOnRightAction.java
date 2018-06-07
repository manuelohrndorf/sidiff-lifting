package org.sidiff.vcmsintegration.structureview;

import java.util.EventObject;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.vcmsintegration.util.Resources;

/**
 * An action that is used to apply a selected operation invocation. This action
 * does not know about the left or right side of the model. It will
 * 
 * @author Adrian Bingener
 *
 */
public class ApplyOnRightAction extends Action {

	/**
	 * The callback that is being notified when a the action is executed.
	 */
	private Handler callback;

	/**
	 * Creates a new instance of the {@link ApplyOnRightAction}.
	 */
	public ApplyOnRightAction(Handler callback) {
		this.setText("Apply selected operation");
		this.setEnabled(false);
		this.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, Resources.ICON_MERGE_TO_RIGHT));
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
