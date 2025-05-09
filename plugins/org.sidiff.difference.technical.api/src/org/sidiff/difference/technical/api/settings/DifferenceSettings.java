package org.sidiff.difference.technical.api.settings;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.sidiff.candidates.ICandidates;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.correspondences.ICorrespondences;
import org.sidiff.difference.symmetric.mergeimports.MergeImports;
import org.sidiff.difference.technical.ITechnicalDifferenceBuilder;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matching.api.settings.MatchingSettings;

/**
 * @see DifferenceSettingsItem
 */
public class DifferenceSettings extends MatchingSettings {

	private static final String PLUGIN_ID = "org.sidiff.difference.technical.api";

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
	 * The Technical Difference Builder to use.
	 */
	private ITechnicalDifferenceBuilder techBuilder;

	/**
	 * Default constructor to create mostly empty settings.
	 * Call initDefaults last, after calling the necessary setters.
	 */
	public DifferenceSettings() {
		super();
	}

	/**
	 * @deprecated Use the empty constructor and initDefaults instead.
	 */
	public DifferenceSettings(Scope scope, boolean validate, IMatcher matcher, ICandidates candidatesService,
			ICorrespondences correspondenceService, ITechnicalDifferenceBuilder techBuilder) {
		super(scope, validate, matcher, candidatesService, correspondenceService);
		this.techBuilder = techBuilder;
	}

	@Override
	public void initDefaults(Set<String> documentTypes) {
		super.initDefaults(documentTypes);

		if(techBuilder == null || !techBuilder.canHandle(documentTypes)) {
			setTechBuilder(getDefaultTechnicalDifferenceBuilder(documentTypes));
		}
	}
	
	protected ITechnicalDifferenceBuilder getDefaultTechnicalDifferenceBuilder(Set<String> documentTypes) {
		if(documentTypes.isEmpty()) {
			return ITechnicalDifferenceBuilder.MANAGER.getDefaultExtension().orElseThrow(
					() -> new IllegalStateException("No generic technical difference builder is available"));
		}
		return ITechnicalDifferenceBuilder.MANAGER.getDefaultExtension(documentTypes).orElseThrow(
				() -> new IllegalStateException("No technical difference builder available for the document types " + documentTypes));
		
	}

	@Override
	public void validate(MultiStatus multiStatus) {
		super.validate(multiStatus);

		if(techBuilder == null) {
			multiStatus.add(new Status(IStatus.ERROR, PLUGIN_ID, 0, "Technical Difference Builder is not set.", null));
		}
	}

	@Override
	public String toString() {
		return super.toString() + "\n"
			+ "DifferenceSettings["
			+ "Merge Imports: " + isEnabled_MergeImports() + ", "
			+ "Unmerge Imports: " + isEnabled_UnmergeImports() + ", "
			+ "Imports: " + getImports() + ", "
			+ "Technical-Difference-Builder: " + toString(getTechBuilder()) + "]";
	}

	// ---------- Getter and Setter Methods----------

	/**
	 * Returns whether mergeImports (esp. EObject Localization Lookup) is enabled. (Default: true)
	 * @return <code>true</code> if enabled; <code>false</code> otherwise.
	 * @see DifferenceSettingsItem#MERGE_IMPORTS
	 */
	public boolean isEnabled_MergeImports() {
		return mergeImports;
	}

	/**
	 * Enables/disables mergeImports (esp. EObject Localization Lookup). (Default: true)
	 * @param mergeImports <code>true</code> if enabled; <code>false</code> otherwise.
	 * @see DifferenceSettingsItem#MERGE_IMPORTS
	 */
	public void setMergeImports(boolean mergeImports) {
		if (this.mergeImports != mergeImports) {
			this.mergeImports = mergeImports;
			notifyListeners(DifferenceSettingsItem.MERGE_IMPORTS);
		}
	}

	/**
	 * Finally clean up merge imports.
	 * @return <code>true</code> if enabled; <code>false</code> otherwise.
	 * @see DifferenceSettingsItem#UNMERGE_IMPORTS
	 */
	public boolean isEnabled_UnmergeImports() {
		return unmergeImports;
	}

	/**
	 * Finally clean up merge imports.
	 * @param unmergeImports <code>true</code> if enabled; <code>false</code> otherwise.
	 * @see DifferenceSettingsItem#UNMERGE_IMPORTS
	 */
	public void setUnmergeImports(boolean unmergeImports) {
		if (this.unmergeImports != unmergeImports) {
			this.unmergeImports = unmergeImports;
			notifyListeners(DifferenceSettingsItem.UNMERGE_IMPORTS);
		}
	}

	/**
	 * @return The imported external references.
	 * @see DifferenceSettingsItem#IMPORTS
	 */
	public MergeImports getImports() {
		return imports;
	}

	/**
	 * @param imports The imported external references.
	 * @see DifferenceSettingsItem#IMPORTS
	 */
	public void setImports(MergeImports imports) {
		if(!Objects.equals(this.imports, imports)) {
			this.imports = imports;
			notifyListeners(DifferenceSettingsItem.IMPORTS);
		}
	}

	/**
	 * @return The Technical Difference Builder. ({@link ITechnicalDifferenceBuilder})
	 * @see DifferenceSettingsItem#TECH_BUILDER
	 */
	public ITechnicalDifferenceBuilder getTechBuilder() {
		return techBuilder;
	}

	/**
	 * @param techBuilder The Technical Difference Builder. ({@link ITechnicalDifferenceBuilder})
	 * @see DifferenceSettingsItem#TECH_BUILDER
	 */
	public void setTechBuilder(ITechnicalDifferenceBuilder techBuilder) {
		if (!Objects.equals(this.techBuilder, techBuilder)) {
			this.techBuilder = techBuilder;
			notifyListeners(DifferenceSettingsItem.TECH_BUILDER);
		}
	}


	public static DifferenceSettings defaultSettings() {
		return defaultSettings(Collections.emptySet());
	}

	public static DifferenceSettings defaultSettings(Set<String> documentTypes) {
		DifferenceSettings settings = new DifferenceSettings();
		settings.initDefaults(documentTypes);
		return settings;
	}
}
