package org.sidiff.patching.api.settings;

import org.sidiff.common.emf.settings.ISettingsItem;

/**
 * Enumerations which are associated with a {@link PatchingSettings patching setting}.
 */
public enum PatchingSettingsItem implements ISettingsItem {

	VALIDATION_MODE,
	RELIABILITY,
	EXEC_MODE,
	PATCH_MODE,
	ARG_MANAGER,
	INTERRUPT_HANDLER,
	TRANSFORMATION_ENGINE,
	MODIFIED_DETECTOR,
	SYMBOLIC_LINK_HANDLER
}
