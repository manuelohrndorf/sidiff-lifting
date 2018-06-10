package org.sidiff.vcmsintegration.structureview;

import java.util.EventObject;

/**
 * A generic handler interface. That provides a handle method that can pass a
 * {@link EventObject} to provide additional information about the event.
 * 
 * @author Adrian Bingener
 *
 */
public interface IActionHandler {

	/**
	 * Called when the component that this handler is registered for, fired a
	 * certain event.
	 * 
	 * @param event The event that was fired
	 */
	void handle(EventObject event);
}
