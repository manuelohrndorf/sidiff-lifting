package org.sidiff.difference.lifting.settings;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.rulebase.util.RuleBaseUtil;
import org.silift.common.util.emf.Scope;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;
import org.silift.difference.symboliclink.handler.util.SymbolicLinkHandlerUtil;

public abstract class Settings {

	/**
	 * The {@link Scope} of the comparison. (Default: {@link Scope#RESOURCE}.
	 */
	private Scope scope = Scope.RESOURCE;

	/**
	 * The Matcher for calculating correspondences.
	 */
	private IMatcher matcher;

	/**
	 * The Symbolic Link Handler for calculating symbolic links.
	 */
	private ISymbolicLinkHandler symbolicLinkHandler;
	
	/**
	 * List of active Rulebases.
	 */
	private Set<IRuleBase> ruleBases;

	/**
	 * All listeners of this Setting-Object.
	 */
	private ArrayList<ISettingsChangedListener> listeners = new ArrayList<ISettingsChangedListener>();

	/**
	 * Setup the comparison settings.
	 */
	public Settings() {
	}

	/**
	 * Setup the settings.
	 * 
	 * @param documentType
	 *            The document type of the models.
	 */
	public Settings(String documentType) {
		// Default: Use all available rulebases:
		this.ruleBases = RuleBaseUtil.getAvailableRulebases(documentType);

		// Default: Use named element matcher
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry().getConfigurationElementsFor(
				IMatcher.extensionPointID)) {
			try {
				IMatcher matcherExtension = (IMatcher) configurationElement.createExecutableExtension("name");

				if (matcherExtension.getName().equals("org.sidiff.difference.matcher.namedelement.NamedElementMatcher")) {
					this.matcher = matcherExtension;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Setup the settings.
	 * 
	 * @param scope
	 *            {@link Settings#setScope(Scope)}
	 * @param matcher
	 *            {@link Settings#setMatcher(IMatcher)}
	 * @param ruleBases
	 *            {@link Settings#setRuleBases(Set)}
	 */
	public Settings(Scope scope, IMatcher matcher, Set<IRuleBase> ruleBases) {
		super();

		this.matcher = matcher;
		this.scope = scope;
		this.ruleBases = ruleBases;
	}

	/**
	 * @return The {@link Scope} of the comparison.
	 */
	public Scope getScope() {
		return scope;
	}

	/**
	 * Setup the new {@link Scope} of the comparison.
	 * 
	 * @param scope
	 *            The new {@link Scope}.
	 */
	public void setScope(Scope scope) {
		if (this.scope == null || !this.scope.equals(scope)) {
			this.scope = scope;
			this.notifyListeners(SettingsItem.SCOPE);
		}
	}

	/**
	 * @return The Matcher for model comparison.
	 */
	public IMatcher getMatcher() {
		return matcher;
	}

	/**
	 * @param matcher
	 *            The Matcher for model comparison.
	 */
	public void setMatcher(IMatcher matcher) {
		if (this.matcher == null || !this.matcher.getName().equals(matcher.getName())) {
			this.matcher = matcher;
			this.notifyListeners(SettingsItem.MATCHER);
		}
	}

	/**
	 * 
	 * @return The Symbolic Link Handler for symbolic link generation.
	 */
	public ISymbolicLinkHandler getSymbolicLinkHandler() {
		return symbolicLinkHandler;
	}

	/**
	 * 
	 * @param symbolicLinkHandler
	 * 						The Symbolic Link Handler for symbolic link generation.
	 */
	public void setSymbolicLinkHandler(ISymbolicLinkHandler symbolicLinkHandler) {
		if(symbolicLinkHandler == null && this.symbolicLinkHandler != null){
			this.symbolicLinkHandler = null;
			this.notifyListeners(SettingsItem.SYMBOLIC_LINK_HANDLER);
		}else
		if(this.symbolicLinkHandler == null || !this.symbolicLinkHandler.getName().equals(symbolicLinkHandler.getName())){
			this.symbolicLinkHandler = symbolicLinkHandler;
			this.notifyListeners(SettingsItem.SYMBOLIC_LINK_HANDLER);
		}
	}

	/**
	 * 
	 * @return <code>true</code>, if the {@link #symbolicLinkHandler} is set
	 * 			otherwise <code>false</code>.
	 */
	public boolean useSymbolicLinks(){
		if(symbolicLinkHandler != null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * List of active Rulebases.
	 * 
	 * @return The list of all active Rulebases.
	 */
	public Set<IRuleBase> getRuleBases() {
		return ruleBases;
	}

	/**
	 * Sets the list of active Rulebases.
	 * 
	 * @param ruleBases
	 *            The list of all active Rulebases.
	 */
	public void setRuleBases(Set<IRuleBase> ruleBases) {
		if (this.ruleBases == null || this.ruleBases.size() != ruleBases.size()) {
			this.ruleBases = ruleBases;
			this.notifyListeners(SettingsItem.RULEBASES);
		}
	}

	/**
	 * Adds a new listener to this Settings-Object.
	 * 
	 * @param listener
	 *            The listener.
	 */
	public void addSettingsChangedListener(ISettingsChangedListener listener) {
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	/**
	 * Call this function every time when a setting was changed!
	 * 
	 * @param item
	 *            The Enumeration associated with the changed setting.
	 */
	protected void notifyListeners(Enum<?> item) {
		if (listeners != null) {
			for (ISettingsChangedListener listener : listeners) {
				listener.settingsChanged(item);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(scope != null ? "Scope: " + scope + "\n" : "");
		result.append(matcher != null ? "Matcher: " + matcher.getName() + "\n" : "");

		if (ruleBases != null) {
			result.append("Rulesbases: ");

			for (IRuleBase rb : ruleBases) {
				result.append(rb.getName() + ", ");
			}
			if (result.toString().endsWith(",")){
				result.deleteCharAt(result.toString().lastIndexOf(','));
			}
			result.append("\n");
		}

		return result.toString();
	}
}
