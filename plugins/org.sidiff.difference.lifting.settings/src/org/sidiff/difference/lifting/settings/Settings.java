package org.sidiff.difference.lifting.settings;

import java.util.Set;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.rulebase.util.RuleBaseUtil;
import org.sidiff.difference.settings.settings.MatchingSettings;
import org.sidiff.matcher.IMatcher;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

public abstract class Settings extends MatchingSettings{

	/**
	 * The Symbolic Link Handler for calculating symbolic links.
	 */
	private ISymbolicLinkHandler symbolicLinkHandler;
	
	/**
	 * List of active Rulebases.
	 */
	private Set<IRuleBase> ruleBases;


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
		super(documentType);
		this.ruleBases = RuleBaseUtil.getAvailableRulebases(documentType);
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
		super(scope, matcher);
		this.ruleBases = ruleBases;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(getScope() != null ? "Scope: " + getScope() + "\n" : "");
		result.append(getMatcher() != null ? "Matcher: " + getMatcher().getName() + "\n" : "");

		if (ruleBases != null) {
			result.append("Rulebases: ");

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
