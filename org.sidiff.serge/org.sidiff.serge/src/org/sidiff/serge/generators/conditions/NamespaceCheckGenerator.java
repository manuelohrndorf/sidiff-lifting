package org.sidiff.serge.generators.conditions;

import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.serge.core.LokalNamespace;

public class NamespaceCheckGenerator extends AbstractConditionGenerator {

	public static Set<EAttribute> globalNamespaces;
	public static Set<LokalNamespace> lokalNamespaces;

	public NamespaceCheckGenerator(Rule editRule) {
		super(editRule);
		// TODO Auto-generated constructor stub
	}

}
