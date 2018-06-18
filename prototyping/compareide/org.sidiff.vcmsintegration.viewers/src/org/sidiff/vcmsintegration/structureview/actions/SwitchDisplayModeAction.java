package org.sidiff.vcmsintegration.structureview.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.sidiff.vcmsintegration.Activator;
import org.sidiff.vcmsintegration.contentprovider.DisplayMode;

/**
 * An action that changes the display mode in the structure merge viewer. This
 * actions is an {@link IMenuCreator} which means, that it creates a drop down
 * menu. This menu provides a list of all {@link DisplayMode}s.
 * 
 * @author Adrian Bingener, Robert Müller
 *
 */
public class SwitchDisplayModeAction extends Action implements IMenuCreator {

	/**
	 * The menu that is shown beside the action that this class represents.
	 */
	private Menu menu;

	/**
	 * The callback that is being notified when a {@link DisplayMode} is
	 * selected or the refresh button is clicked.
	 */
	private DisplayModeCallback callback;

	/**
	 * Creates a new instance of the {@link SwitchDisplayModeAction} with the
	 * given list of {@link DisplayMode}s that are shown in the drop down menu.
	 * 
	 * @param displayModes The {@link DisplayMode}s that are shown in the menu
	 * @param callback The callback that is notified when the
	 *            {@link DisplayMode} changes
	 */
	public SwitchDisplayModeAction(DisplayModeCallback callback) {
		super("Refresh", IAction.AS_DROP_DOWN_MENU);
		this.setImageDescriptor(Activator.getImageDescriptor(Activator.IMAGE_REFRESH));
		this.setEnabled(true);
		this.callback = callback;
		setMenuCreator(this);
	}

	@Override
	public void run() {
		callback.onRefresh();
	}

	@Override
	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
	}

	/**
	 * Each time an item in the drop down menu is selected, the menu is
	 * recreated. Therefore this method is called again to build the menu.
	 */
	@Override
	public Menu getMenu(Control parent) {
		if (menu != null) {
			menu.dispose();
		}
		menu = new Menu(parent);

		// Create a new menu action item for each display modes
		for (final DisplayMode displayMode : DisplayMode.values()) {
			Action action = new DisplayModeSwitchAction(displayMode);
			ActionContributionItem item = new ActionContributionItem(action);
			item.fill(menu, -1);
		}

		return menu;
	}

	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}

	protected class DisplayModeSwitchAction extends Action {

		protected final DisplayMode displayMode;

		public DisplayModeSwitchAction(DisplayMode displayMode) {
			super(displayMode.getName(), IAction.AS_RADIO_BUTTON);
			this.displayMode = displayMode;
			this.setToolTipText(displayMode.getName());
			this.setChecked(displayMode == DisplayMode.getDefault());
		}

		@Override
		public void run() {
			// TODO: updating the checked-state does not work
			this.setChecked(true);
			callback.onDisplayModeChanged(displayMode);
		}
	}

	/**
	 * A callback interface that is used to notify the implementing class when
	 * the {@link DisplayMode} changed or was refreshed.
	 * 
	 * @author Adrian Bingener
	 *
	 */
	public interface DisplayModeCallback {

		/**
		 * Called when the user clicked the refresh button. This should
		 * recalculate and populate the data.
		 */
		void onRefresh();

		/**
		 * Called when the user changed the {@link DisplayMode} selection.
		 * 
		 * @param displayMode The new {@link DisplayMode} that is selected
		 */
		void onDisplayModeChanged(DisplayMode displayMode);
	}
}
