package org.sidiff.patching.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

public class DropDownAction extends Action implements IMenuCreator, IPropertyChangeListener {

	private Menu menu;
	private List<IAction> actions;
	
	
	public DropDownAction(String label){
		super(label, IAction.AS_DROP_DOWN_MENU);
		setMenuCreator(this);
		this.actions = new ArrayList<IAction>();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		Action source = (Action) event.getSource();
		source.run();
		
	}
	

	@Override
	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
		for (IAction action : actions) {
			action.removePropertyChangeListener(this);
		}
		actions.clear();
	}

	@Override
	public Menu getMenu(Control parent) {
		if (menu != null)
			menu.dispose();

		menu = new Menu(parent);

		for (IAction action : actions) {
			ActionContributionItem item= new ActionContributionItem(action);
			item.fill(menu, -1);
		}

		return menu;
	}

	@Override
	public Menu getMenu(Menu parent) {
		if (menu != null)
			menu.dispose();

		menu = new Menu(parent);

		for (IAction action : actions) {
			ActionContributionItem item= new ActionContributionItem(action);
			item.fill(menu, -1);
		}

		return menu;
	}
	
	
	public void add(final IAction action) {
		actions.add(action);
		action.addPropertyChangeListener(this);
	}

}
