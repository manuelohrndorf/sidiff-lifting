package org.sidiff.editrule.analysis.criticalpairs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.view.ActionGraph;
import org.sidiff.editrule.analysis.criticalpairs.util.SubTypeIndex;
import org.sidiff.editrule.rulebase.RulebaseFactory;

/**
 * 
 * @author rmueller
 */
public abstract class AbstractAnalyzer {

	/**
	 * The EMF Rulebase factory instance.
	 */
	protected static final RulebaseFactory rbFactory = RulebaseFactory.eINSTANCE;

	/**
	 * Caches the Henshin rules as {@link ActionGraph}.
	 */
	private Map<Rule, ActionGraph> actionGraphCache = new HashMap<>();

	private Set<EPackage> imports;
	private SubTypeIndex subTypeIndex;

	public AbstractAnalyzer(Set<EPackage> imports) {
		this.imports = new HashSet<>(imports);
		this.subTypeIndex = new SubTypeIndex(imports);
	}

	/**
	 * Transforms the Henshin rule to a {@link ActionGraph}
	 * 
	 * @param rule
	 *            The Henshin rule.
	 * @return The {@link ActionGraph}.
	 */
	protected ActionGraph getActionGraph(Rule rule) {
		return actionGraphCache.computeIfAbsent(rule, r -> new ActionGraph(r, true));
	}

	public Set<EPackage> getImports() {
		return Collections.unmodifiableSet(imports);
	}

	public SubTypeIndex getSubTypeIndex() {
		return subTypeIndex;
	}

	/**
	 * @param documentTypes
	 *            All document types of the rulebase(s).
	 * @return All imports of the Henshin rules (document types) which shall be analyzed.
	 */
	protected static Set<EPackage> getImports(Collection<String> documentTypes) {
		Set<EPackage> docTypePackages = new HashSet<>();

		for (String docType : documentTypes) {
			EPackage docTypePackage = EPackage.Registry.INSTANCE.getEPackage(docType);
			assert (docTypePackage != null) : "Package for docType " + docType
					+ " not found in the global EMF package registry";
			docTypePackages.add(docTypePackage);
		}

		return docTypePackages;
	}

	/**
	 * Checks if the attribute is a literal attribute.
	 * 
	 * @param attribute
	 *            The attribute to test.
	 * @return <code>true</code> if the attribute is a literal;
	 *         <code>false</code> if it is a variable or expression.
	 */
	protected boolean isLiteral(Attribute attribute) {
		String value = attribute.getValue();

		/*
		 *  String
		 */
		if (value.startsWith("\"") && value.endsWith("\"")) {
			return true;
		}

		/*
		 * Value
		 */
		try {
			EAttribute type = attribute.getType();
			if(type != null) {
				// Strings must have been escaped with " "
				EDataType dataType = type.getEAttributeType();
				if(dataType.getInstanceClass() != String.class) {
					EcoreUtil.createFromString(dataType, value);
					return true;					
				}
			}
		} catch(Exception e) {
		}

		return false;
	}

	/**
	 * Tests if at least one of the attributes is a variable or if both
	 * attributes contain the same literal.
	 * 
	 * @param predecessor
	 *            The predecessor attribute, i.e. the target of dependencies.
	 * @param successor
	 *            The successor attribute, i.e. the source of dependencies
	 * @return <code>true</code> if at least one of the attributes is a variable
	 *         or if both attributes contain the same literal;
	 *         <code>false</code> otherwise.
	 */
	protected boolean attributeCaseDifferentiation(Attribute predecessor, Attribute successor) {
		// Test precondition: Values => 'equal literals' or 'at least one is variable' == true

		// Is literal?
		if (isLiteral(predecessor) && isLiteral(successor)) {
			// Literals are equal
			return predecessor.getValue().equals(successor.getValue());
		}
		// Predecessor or successor is variable!
		return true;
	}
}
