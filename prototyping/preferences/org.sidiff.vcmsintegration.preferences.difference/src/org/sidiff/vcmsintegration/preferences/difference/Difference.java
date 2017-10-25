package org.sidiff.vcmsintegration.preferences.difference;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.RadioBoxPreferenceField;
import org.sidiff.vcmsintegration.preferences.fieldeditors.ValueAndLabelProvider;
import org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

/**
 * 
 * Class to create the tab for the difference settings.
 * @author Daniel Roedder
 */
public class Difference implements ISiDiffEnginesPreferenceTab {
	
	/**
	 * List to hold all {@link org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField}
	 */
	private ArrayList<PreferenceField> fieldList;
	
	/**
	 * The {@link IPreferenceStore} to be used
	 */
	IPreferenceStore store;
	
	/**
	 * The {@link org.sidiff.vcmsintegration.preferences.fieldeditors.PreferenceField} for the symbolic link handlers
	 */
	private PreferenceField symbolicLinkHandlers;

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Difference";
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {

	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#getTabContent()
	 */
	@Override
	public Iterable<PreferenceField> getTabContent() {
		fieldList = new ArrayList<PreferenceField>();
		
		
		Set<ISymbolicLinkHandler> handlers = SymbolicLinkHandlerUtil.getAvailableSymbolicLinkHandlers();
		
		symbolicLinkHandlers = RadioBoxPreferenceField.createFromIterable("symbolicLinkHandlers", "Symbolic Link Handlers", handlers, new ValueAndLabelProvider<ISymbolicLinkHandler>() {
			@Override public String[] get(ISymbolicLinkHandler input) {return new String[] {input.getKey(), input.getName()};}
		});
		
		fieldList.add(symbolicLinkHandlers);
		
		for (PreferenceField field : fieldList) {
			field.setPreferenceStore(store);
		}
		
		return fieldList;
	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffEnginesPreferenceTab#setPreferenceStore(org.eclipse.jface.preference.IPreferenceStore)
	 */
	@Override
	public void setPreferenceStore(IPreferenceStore store) {
		this.store = store;

	}

	/**
	 * @see org.sidiff.vcmsintegration.preferences.interfaces.ISiDiffOrderableTab#getStepInPipeline()
	 */
	@Override
	public int getStepInPipeline() {
		return 1;
	}

}
