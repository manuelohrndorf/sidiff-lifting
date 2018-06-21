package org.sidiff.vcmsintegration.structureview.actions;

import java.util.EventObject;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.sidiff.vcmsintegration.Activator;

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
	private IActionHandler callback;

	/**
	 * Creates a new instance of the {@link ApplyOnRightAction}.
	 */
	public ApplyOnRightAction(IActionHandler callback) {
		Assert.isNotNull(callback);
		this.setText("Apply selected operation on right");
		this.setEnabled(false);
		this.setImageDescriptor(Activator.getImageDescriptor(Activator.IMAGE_MERGE_TO_RIGHT));
		this.callback = callback;
	}

	@Override
	public void run() {
		callback.handle(new EventObject(this));
	}
}
