package org.sidiff.difference.technical.ui.widgets;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.extension.ui.labelprovider.ExtensionLabelProvider;
import org.sidiff.common.settings.ISettingsChangedListener;
import org.sidiff.common.ui.widgets.AbstractListWidget;
import org.sidiff.common.ui.widgets.IWidgetDisposable;
import org.sidiff.common.ui.widgets.IWidgetValidation;
import org.sidiff.common.ui.widgets.IWidgetValidation.ValidationMessage.ValidationType;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.IncrementalMatcher;
import org.sidiff.matching.api.settings.MatchingSettings;
import org.sidiff.matching.api.settings.MatchingSettingsItem;
import org.sidiff.matching.input.InputModels;

public class MatchingEngineWidget extends AbstractListWidget<IMatcher> implements IWidgetValidation, IWidgetDisposable, ISettingsChangedListener {

	protected MatchingSettings settings;
	protected List<IMatcher> matchers;

	public MatchingEngineWidget(InputModels inputModels, MatchingSettings settings) {
		super(IMatcher.class);
		setTitle("Matcher");
		setLabelProvider(new ExtensionLabelProvider());
		this.matchers = IMatcher.MANAGER.getMatchers(inputModels.getResources());
		this.settings = Objects.requireNonNull(settings, "Settings must not be null");
		this.settings.addSettingsChangedListener(this);
		addModificationListener((oldMatchers,newMatchers) -> {
			settings.setMatcher(wrapMatchers(getSelection()));
		});
	}

	protected static IMatcher wrapMatchers(List<IMatcher> selection) {
		switch(selection.size()) {
			case 0: return null;	
			case 1: return selection.get(0);
			default: return new IncrementalMatcher(selection);
		}
	}
	
	@Override
	public List<IMatcher> getSelectableValues() {
		return Collections.unmodifiableList(matchers);
	}

	@Override
	protected void hookInitSelection() {
		if(getSelection().isEmpty()) {
			setSelection(getSettingsMatchers());
		}
	}

	@Override
	public boolean validate() {
		IMatcher matcher = wrapMatchers(getSelection());
		if(matcher != null && settings.getScope().equals(Scope.RESOURCE_SET) && !matcher.isResourceSetCapable()) {
			setValidationMessage(new ValidationMessage(ValidationType.ERROR,
				"Selected matching engine does not support ResourceSet scope, select another matching engine!"));
			return false;
		}
		return super.validate();
	}

	@Override
	public void settingsChanged(Enum<?> item) {
		if (item == MatchingSettingsItem.MATCHER) {
			setSelection(getSettingsMatchers());
		}
	}

	protected java.util.List<IMatcher> getSettingsMatchers() {
		if(settings.getMatcher() == null) {
			return Collections.emptyList();
		} else if(settings.getMatcher() instanceof IncrementalMatcher) {
			return ((IncrementalMatcher)settings.getMatcher()).getMatchers();
		} else {
			return Collections.singletonList(settings.getMatcher());
		}
	}

	public MatchingSettings getSettings() {
		return settings;
	}

	@Override
	public void dispose() {
		settings.removeSettingsChangedListener(this);
	}
}