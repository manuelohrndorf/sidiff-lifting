package org.sidiff.patching.patch.ui.widgets;

import java.util.List;
import java.util.Objects;

import org.sidiff.common.extension.ui.labelprovider.ExtensionLabelProvider;
import org.sidiff.common.ui.widgets.AbstractListWidget;
import org.sidiff.patching.api.settings.PatchingSettings;
import org.sidiff.patching.api.settings.PatchingSettingsItem;
import org.silift.difference.symboliclink.handler.ISymbolicLinkHandler;

public class SymbolicLinkHandlerWidget extends AbstractListWidget<ISymbolicLinkHandler> {

	private PatchingSettings settings;
	private List<ISymbolicLinkHandler> symbolicLinkHandlers;

	public SymbolicLinkHandlerWidget(PatchingSettings settings) {
		super(ISymbolicLinkHandler.class);
		this.settings = Objects.requireNonNull(settings, "settings must not be null");
		setTitle("Symbolic Link Handler");
		setLabelProvider(new ExtensionLabelProvider());
		setLowerUpperBounds(0, 1);
		symbolicLinkHandlers = ISymbolicLinkHandler.MANAGER.getSortedExtensions();
		settings.addSettingsChangedListener(item -> {
			if(item == PatchingSettingsItem.SYMBOLIC_LINK_HANDLER) {
				setSelection(settings.getSymbolicLinkHandler());
			}
		});
		addModificationListener((oldValues, newValues) -> {
			settings.setSymbolicLinkHandler(newValues.stream().findFirst().orElse(null));
		});
	}

	@Override
	protected void hookInitSelection() {
		if(getSelection().isEmpty()) {
			setSelection(settings.getSymbolicLinkHandler());
		}
	}

	public PatchingSettings getSettings() {
		return settings;
	}

	@Override
	public List<ISymbolicLinkHandler> getSelectableValues() {
		return symbolicLinkHandlers;
	}
}