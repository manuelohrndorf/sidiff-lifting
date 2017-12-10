package org.sidiff.difference.technical.api.settings;

import java.util.Set;

import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.correspondences.matchingmodel.MatchingModelCorrespondences;
import org.sidiff.difference.symmetric.mergeimports.MergeImports;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.difference.technical.api.util.TechnicalDifferenceUtils;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.api.settings.MatchingSettings;

public class DifferenceSettings extends MatchingSettings {

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
	 * default {@link DifferenceSettings}
	 */
	public DifferenceSettings(){
		super();
		this.techBuilder = TechnicalDifferenceUtils.getGenericTechnicalDifferenceBuilder();
		setCorrespondencesService(new MatchingModelCorrespondences());
	}
	
	public DifferenceSettings(Set<String> documentTypes) {
		super(documentTypes);
		this.techBuilder = TechnicalDifferenceUtils.getDefaultTechnicalDifferenceBuilder(documentTypes);
		setCorrespondencesService(new MatchingModelCorrespondences());
	}
	
	public DifferenceSettings(
			Scope scope, boolean validate, 
			IMatcher matcher, ICandidates candidatesService, ICorrespondences correspondenceService, 
			ITechnicalDifferenceBuilder techBuilder) {
		
		super(scope, validate, matcher, candidatesService, correspondenceService);
		this.techBuilder = techBuilder;
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
		
		// TODO: Implement symbolic links for the symmetric/technical difference!
//		result.append("Use symbolic links: " + useSymbolicLinks() + "\n");

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
}
