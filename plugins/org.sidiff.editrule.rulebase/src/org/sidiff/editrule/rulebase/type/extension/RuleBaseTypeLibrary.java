package org.sidiff.editrule.rulebase.type.extension;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * Access registered rulebase types.
 */
public class RuleBaseTypeLibrary {

	/**
	 * The registration ID for a rulebase type (e.g. edit-rule, recognition-rules, ...).
	 */
	public static final String EXTENSION_POINT_ID_RULEBASE_TYPE = "org.sidiff.editrule.rulebase.type";
	
	/**
	 * All registered rulebase types (e.g. edit-rule, recognition-rules, ...): Type-ID -> Class-Type
	 */
	private static Map<String, Class<? extends IRuleBase>> rulebaseTypeIDs;
	
	/**
	 * All registered rulebase types (e.g. edit-rule, recognition-rules, ...): Class-Type -> Factory
	 */
	private static Map<Class<? extends IRuleBase>, IRuleBaseFactory<?>> rulebaseTypes;
	
	/**
	 * All registered rulebase types ((sub)class of {@link IRuleBase}).
	 * 
	 * @return Map: Rulebase-Type-ID -> Rulebase-Class-Type
	 */
	public static Map<String, Class<? extends IRuleBase>> getRulebaseTypeIDs() {
		
		if (rulebaseTypeIDs == null) {
			readRegisteredRulebaseTypes();
		}
		
		return rulebaseTypeIDs;
	}
	
	/**
	 * All registered rulebase types ((sub)class of {@link IRuleBase}).
	 * 
	 * @return Map: Rulebase-Class-Type -> Rulebase-Wrapper-Factory 
	 */
	public static Map<Class<? extends IRuleBase>, IRuleBaseFactory<?>> getRulebaseTypes() {
		
		if (rulebaseTypes == null) {
			readRegisteredRulebaseTypes();
		}
		
		return rulebaseTypes;
	}

	/**
	 * @param type
	 *            A Rulebase-Class-Type.
	 * @return The corresponding rulebase factory or <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <R extends IRuleBase> IRuleBaseFactory<R> getRulebaseType(Class<R> type) {
		
		if (getRulebaseTypes().containsKey(type)) {
			return (IRuleBaseFactory<R>) getRulebaseTypes().get(type);
		}
		
		return null;
	}
	
	private static void readRegisteredRulebaseTypes() {
		
		// Collect all registered rulebase types:
		rulebaseTypes = new HashMap<Class<? extends IRuleBase>, IRuleBaseFactory<?>>();
		
		for (IConfigurationElement configurationElement : Platform.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_POINT_ID_RULEBASE_TYPE)) {
			
			try {
				Object rulebaseFactoryExtension = configurationElement.createExecutableExtension("factory");
				
				if (rulebaseFactoryExtension instanceof IRuleBaseFactory<?>) {
					IRuleBaseFactory<?> rulebaseFactory = (IRuleBaseFactory<?>) rulebaseFactoryExtension;
					rulebaseTypes.put(
							rulebaseFactory.getRuleBaseType(), 
							rulebaseFactory);
					rulebaseTypeIDs.put(
							rulebaseFactory.getRuleBaseType().getName(), 
							rulebaseFactory.getRuleBaseType());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}