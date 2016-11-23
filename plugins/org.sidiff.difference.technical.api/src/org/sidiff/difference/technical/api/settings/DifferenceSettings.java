package org.sidiff.difference.technical.api.settings;

import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.MergeImports;
import org.sidiff.difference.technical.util.TechnicalDifferenceBuilderUtil;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

public class DifferenceSettings extends MatchingSettings{

	/**
	 * Enables/disables the internal mergeImports function (more specifically
	 * the EObject location lookup) This lookup is quite costly when comparing
	 * big models and can be disabled here for performance gain however at the
	 * expense of some matching correctness.
	 */
	private boolean mergeImports = true;
	
	/**
	 * Finally clean up merge imports.
	 */
	private boolean unmergeImports = true;
	
	/**
	 * The imported external references.
	 */
	private MergeImports imports = null;
	
	/**
	 * The Technical Difference Builder to use. (
	 * {@link ITechnicalDifferenceBuilder})
	 */
	private ITechnicalDifferenceBuilder techBuilder;
	
	/**
	 * The Symbolic Link Handler for calculating symbolic links.
	 */
	private ISymbolicLinkHandler symbolicLinkHandler;

	
	/**
	 * default {@link DifferenceSettings}
	 */
	public DifferenceSettings(){
		super();
		techBuilder = TechnicalDifferenceBuilderUtil.getGenericTechnicalDifferenceBuilder();
	}
	
	/**
	 * Setup the settings.
	 * 
	 * @param scope
	 *            {@link DifferenceSettings#setScope(Scope)}
	 * @param matcher
	 *            {@link DifferenceSettings#setMatcher(IMatcher)}
	 * @param symbolicLinkHandler
	 * 			  {@link ISymbolicLinkHandler}
	 */
	public DifferenceSettings(Scope scope, boolean validate, IMatcher matcher, ICandidates candidatesService, ICorrespondences correspondenceService, ITechnicalDifferenceBuilder techBuilder, ISymbolicLinkHandler symbolicLinkHandler) {
		super(scope, validate, matcher, candidatesService, correspondenceService);
		this.techBuilder = techBuilder;
		this.symbolicLinkHandler = symbolicLinkHandler;
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
	
	@Override
	public boolean validateSettings() {
		return super.validateSettings()
				&& techBuilder != null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Merge Imports: " + isEnabled_MergeImports() + "\n");
		result.append(techBuilder != null ? "Technical-Difference-Builder: " + techBuilder.getName() + "\n" : "");
		result.append("Use symbolic links: " + useSymbolicLinks() + "\n");

		return result.toString();
	}
	
	// ---------- Getter and Setter Methods----------
	
	/**
	 * Returns whether mergeImports (esp. EObject Localization Lookup) is enabled. (Default: true)
	 * 
	 * @return <code>true</code> if enabled;
	 *         <code>false</code> otherwise.
	 */
	public boolean isEnabled_MergeImports() {
		return mergeImports;
	}

	/**
	 * Returns whether mergeImports (esp. EObject Localization Lookup) is enabled. (Default: true)
	 * 
	 * @param mergeImports
	 */
	public void setMergeImports(boolean mergeImports) {
		this.mergeImports = mergeImports;
	}
	
	/**
	 * Finally clean up merge imports.
	 * 
	 * @return <code>true</code> if enabled; 
	 *         <code>false</code> otherwise.
	 */
	public boolean isEnabled_UnmergeImports() {
		return unmergeImports;
	}

	/**
	 * Finally clean up merge imports.
	 * 
	 * @param unmergeImports
	 *            <code>true</code> if enabled; 
	 *            <code>false</code> otherwise.
	 */
	public void setUnmergeImports(boolean unmergeImports) {
		this.unmergeImports = unmergeImports;
	}

	/**
	 * @return The imported external references.
	 */
	public MergeImports getImports() {
		return imports;
	}

	/**
	 * @param imports
	 *            The imported external references.
	 */
	public void setImports(MergeImports imports) {
		this.imports = imports;
	}

	/**
	 * @return The Technical Difference Builder. (
	 *         {@link ITechnicalDifferenceBuilder})
	 */
	public ITechnicalDifferenceBuilder getTechBuilder() {
		return techBuilder;
	}

	/**
	 * @param techBuilder
	 *            The Technical Difference Builder. (
	 *            {@link ITechnicalDifferenceBuilder})
	 */
	public void setTechBuilder(ITechnicalDifferenceBuilder techBuilder) {
		if (this.techBuilder == null || !this.techBuilder.equals(techBuilder)) {
			this.techBuilder = techBuilder;
			this.notifyListeners(DifferenceSettingsItem.TECH_BUILDER);
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
			this.notifyListeners(DifferenceSettingsItem.SYMBOLIC_LINK_HANDLER);
		}else
		if(this.symbolicLinkHandler == null || !this.symbolicLinkHandler.getName().equals(symbolicLinkHandler.getName())){
			this.symbolicLinkHandler = symbolicLinkHandler;
			this.notifyListeners(DifferenceSettingsItem.SYMBOLIC_LINK_HANDLER);
		}
	}	
}
