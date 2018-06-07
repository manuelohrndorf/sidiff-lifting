package org.sidiff.vcmsintegration.structureview;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.vcmsintegration.contentprovider.DisplayMode;
import org.sidiff.vcmsintegration.util.Resources;

/**
 * An action that changes the display mode in the structure merge viewer. This
 * actions is an {@link IMenuCreator} which means, that it creates a drop down
 * menu. This menu provides a list of all {@link DisplayMode}s.
 * 
 * @author Adrian Bingener
 *
 */
public class SwitchDisplayModeAction extends Action implements IMenuCreator {

	/**
	 * The menu that is shown beside the action that this class represents.
	 */
	private Menu menu;
	/**
	 * The list of display modes that are shown in the menu.
	 */
	private List<DisplayMode> displayModes;
	/**
	 * The mode that is selected in the menu.
	 */
	private DisplayMode displayMode;
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
	 */
	public SwitchDisplayModeAction(DisplayMode[] displayModes) {
		this.displayModes = Arrays.asList(displayModes);
		this.setText("Refresh");
		this.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, Resources.ICON_REFRESH));

		// Use the default display mode
		if (!this.displayModes.isEmpty()) {
			displayMode = DisplayMode.getDefault();
			setMenuCreator(this);
		}
	}

	/**
	 * Creates a new instance of the {@link SwitchDisplayModeAction} with the
	 * given list of {@link DisplayMode}s that are shown in the drop down menu.
	 * 
	 * @param displayModes The {@link DisplayMode}s that are shown in the menu
	 * @param callback The callback that is notified when the
	 *            {@link DisplayMode} changes
	 */
	public SwitchDisplayModeAction(DisplayMode[] displayModes, DisplayModeCallback callback) {
		this(displayModes);
		this.callback = callback;
	}

	@Override
	public void run() {
		if (!displayModes.isEmpty()) {
			callback.onRefresh();
		}
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
	public Menu getMenu(Control parent) {
		if (menu != null) {
			menu.dispose();
		}
		menu = new Menu(parent);

		// Create a new menu action item for each display modes
		for (final DisplayMode displayMode : displayModes) {
			Action action = getActionByDisplayMode(displayMode);
			action.setChecked(displayMode.equals(this.displayMode));

			ActionContributionItem item = new ActionContributionItem(action);
			item.fill(menu, -1);
		}
		return menu;
	}

	@Override
	public Menu getMenu(Menu parent) {
		LogUtil.log(LogEvent.WARNING, "Unsupported operation getMenu(Menu)");
		return null;
	}

	/**
	 * Creates an {@link Action} for each {@link DisplayMode} that should be
	 * shown in the dropdown menu. If this action is clicked, the
	 * {@link DisplayModeCallback} is notified via
	 * {@link DisplayModeCallback#onDisplayModeChanged(DisplayMode)}.
	 * 
	 * @param displayMode The display mode that the {@link Action} is created
	 *            for
	 * @return An {@link Action} for the given display mode
	 */
	private Action getActionByDisplayMode(final DisplayMode displayMode) {
		Action action = new Action(displayMode.getName(), AS_RADIO_BUTTON) {
			public void run() {
				SwitchDisplayModeAction.this.displayMode = displayMode;
				callback.onDisplayModeChanged(displayMode);
			}
		};
		action.setText(displayMode.getName());
		action.setToolTipText(displayMode.getName());
		return action;
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
