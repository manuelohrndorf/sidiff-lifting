package org.sidiff.editrule.rulebase.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.editrule.rulebase.view.basic.IBasicRuleBase;

/**
 * Access registered rulebase views.
 */
public class RuleBaseViewLibrary {

	/**
	 * The registration ID for a rulebase views (e.g. edit-rule, recognition-rules, ...).
	 */
	public static final String EXTENSION_POINT_ID_RULEBASE_VIEW_TYPE = "org.sidiff.editrule.rulebase.view.type";
	
	/**
	 * All registered rulebase views (e.g. edit-rule, recognition-rules, ...): Class-Type -> Factory
	 */
	private static Map<Class<? extends IBasicRuleBase>, RuleBaseViewEntry> rulebaseViews;

	/**
	 * @param type
	 *            A Rulebase-Class-Type.
	 * @return The corresponding rulebase factory or <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <R extends IBasicRuleBase> IRuleBaseFactory<R> getRulebaseViewFactory(Class<R> type) {
		
		if (getRulebaseViews().containsKey(type)) {
			return (IRuleBaseFactory<R>) rulebaseViews.get(type).getFactory();
		}
		
		return null;
	}
	
	/**
	 * @param supportedAttachments
	 *            All supported attachments (of the rulebase).
	 * @return All supported view-types
	 */
	public static Set<Class<? extends IBasicRuleBase>> getSupportedViewTypes(Set<String> supportedAttachments) {
		
		Set<Class<? extends IBasicRuleBase>> supportedViewTypes = new HashSet<Class<? extends IBasicRuleBase>>();
		
		for (RuleBaseViewEntry view : getRulebaseViews().values()) {
			if (supportedAttachments.containsAll(view.getRequiredAttachments())) {
				supportedViewTypes.add(view.getViewType());
			}
		}
		
		return supportedViewTypes;
	}
	
	private static Map<Class<? extends IBasicRuleBase>, RuleBaseViewEntry> getRulebaseViews() {
		
		if (rulebaseViews == null) {
			readRegisteredRulebaseViews();
		}
		
		return rulebaseViews;
	}
	
	private static void readRegisteredRulebaseViews() {
		
		// Collect all registered rulebase types:
		rulebaseViews = new HashMap<Class<? extends IBasicRuleBase>, RuleBaseViewEntry>();
		
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID_RULEBASE_VIEW_TYPE)) {
			
			try {
				IRuleBaseFactory<?> rulebaseFactory = null;
				Set<String> requiredAttachments = new HashSet<String>();
				
				// Factory:
				Object rulebaseFactoryExtension = configurationElement.createExecutableExtension("factory");
				
				if (rulebaseFactoryExtension instanceof IRuleBaseFactory<?>) {
					rulebaseFactory = (IRuleBaseFactory<?>) rulebaseFactoryExtension;
				}
				
				// Attachments:
				for (IConfigurationElement requiredAttachment : configurationElement.getChildren("requiredAttachment")) {
					requiredAttachments.add(requiredAttachment.getAttribute("attachment"));
				}
				
				if (rulebaseFactory != null) {
					rulebaseViews.put(
							rulebaseFactory.getRuleBaseViewType(), 
							new RuleBaseViewEntry(
									configurationElement.getAttribute("id"),
									rulebaseFactory, requiredAttachments));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}