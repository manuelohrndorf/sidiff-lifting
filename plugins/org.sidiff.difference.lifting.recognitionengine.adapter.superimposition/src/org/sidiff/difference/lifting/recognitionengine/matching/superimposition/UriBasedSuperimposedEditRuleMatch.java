package org.sidiff.difference.lifting.recognitionengine.matching.superimposition;

import java.util.Set;

import org.eclipse.emf.ecore.*;
import org.sidiff.common.emf.access.Field;
import org.sidiff.common.emf.access.Link;
import org.sidiff.difference.lifting.recognitionengine.matching.UriBasedEditRuleMatch;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.entities.Attribute;
import org.sidiff.entities.Reference;
import org.sidiff.superimposition.SuperimposedElement;

/**
 * @author rmueller
 */
public class UriBasedSuperimposedEditRuleMatch extends UriBasedEditRuleMatch {

	public UriBasedSuperimposedEditRuleMatch(SemanticChangeSet scs) {
		super(scs);
	}

	@Override
	protected void createLink(Set<Link> edgeOccurrences, EObject source, EObject target, EReference eReference) {
		if (source instanceof SuperimposedElement) {
			SuperimposedElement srcElement = (SuperimposedElement)source;
			for (Reference reference : srcElement.getReferences(eReference)) {
				if (reference.getTarget() == target) {
					edgeOccurrences.add(new Link(source, target, eReference));
				}
			}
		} else {
			super.createLink(edgeOccurrences, source, target, eReference);
		}
	}

	@Override
	protected void createAttribute(Set<Field> attributeOccurrences, EObject eObject, EAttribute eAttribute) {
		if(eObject instanceof SuperimposedElement) {
			SuperimposedElement element = (SuperimposedElement)eObject;
			for (Attribute attr : element.getAttributes(eAttribute)) {
				for (String value : attr.getValue()) {
					attributeOccurrences.add(new Field(eObject, eAttribute, value));
				}
			}
		} else {
			super.createAttribute(attributeOccurrences, eObject, eAttribute);
		}
	}
}
