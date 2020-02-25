package org.sidiff.integration.compare.structureview.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.sidiff.integration.compare.DisplayMode;
import org.sidiff.integration.compare.SiLiftCompareConfiguration;
import org.sidiff.integration.compare.internal.CompareIntegrationPlugin;
import org.sidiff.integration.compare.structureview.SiLiftStructureMergeViewer;

/**
 * An action that changes the display mode in the structure merge viewer. This
 * actions is an {@link IMenuCreator} which means, that it creates a drop down
 * menu. This menu provides a list of all {@link DisplayMode}s.
 * 
 * @author Adrian Bingener
 * @author rmueller
 */
public class SwitchDisplayModeAction extends Action implements IMenuCreator {

	/**
	 * The menu that is shown beside the action that this class represents.
	 */
	private Menu menu;

	private SiLiftStructureMergeViewer mergeViewer;
	private SiLiftCompareConfiguration compareConfiguration;

	/**
	 * Creates a new instance of the {@link SwitchDisplayModeAction} with the
	 * given list of {@link DisplayMode}s that are shown in the drop down menu.
	 * 
	 * @param displayModes The {@link DisplayMode}s that are shown in the menu
	 * @param callback The callback that is notified when the
	 *            {@link DisplayMode} changes
	 */
	public SwitchDisplayModeAction(SiLiftStructureMergeViewer mergeViewer,
			SiLiftCompareConfiguration compareConfiguration) {
		super("Refresh", IAction.AS_DROP_DOWN_MENU);
		this.setImageDescriptor(CompareIntegrationPlugin.getImageDescriptor(CompareIntegrationPlugin.IMAGE_REFRESH));
		this.setEnabled(true);
		this.mergeViewer = mergeViewer;
		this.compareConfiguration = compareConfiguration;
		setMenuCreator(this);
	}

	@Override
	public void run() {
		mergeViewer.refresh();
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

	private class DisplayModeSwitchAction extends Action {

		protected final DisplayMode displayMode;

		public DisplayModeSwitchAction(DisplayMode displayMode) {
			super(displayMode.getName(), IAction.AS_RADIO_BUTTON);
			this.displayMode = displayMode;
			this.setToolTipText(displayMode.getName());
			this.setChecked(displayMode == compareConfiguration.getDisplayMode());
		}

		@Override
		public void run() {
			compareConfiguration.setDisplayMode(displayMode);
		}
	}
}
