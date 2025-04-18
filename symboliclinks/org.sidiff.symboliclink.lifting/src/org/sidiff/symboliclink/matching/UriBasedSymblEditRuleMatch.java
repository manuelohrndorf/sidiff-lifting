package org.sidiff.symboliclink.matching;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.access.Field;
import org.sidiff.common.emf.access.Link;
import org.sidiff.difference.lifting.recognitionengine.matching.UriBasedEditRuleMatch;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.silift.difference.symboliclink.SymbolicLinkAttribute;
import org.silift.difference.symboliclink.SymbolicLinkObject;
import org.silift.difference.symboliclink.SymbolicLinkReference;
import org.silift.difference.symboliclink.util.SymboliclinkUtil;

/**
 * 
 * @author cpietsch
 *
 */
public class UriBasedSymblEditRuleMatch extends UriBasedEditRuleMatch {	

	public UriBasedSymblEditRuleMatch(SemanticChangeSet scs) {
		super(scs, Collections.singleton(SymboliclinkUtil.resolveCharacteristicDocumentType(((SymmetricDifference) scs.eContainer()).getModelA())));
	}

	@Override
	protected void createLink(Set<Link> edgeOccurrences, EObject source, EObject target, EReference reference) {
		// FIXME (cpietsch: 02.09.2014) getNodeNeighbors doesn't work with symbolic links 
		// (see also org.sidiff.common.emf.access.Link)
		if (source instanceof SymbolicLinkObject) {
			SymbolicLinkObject symbloSrc = (SymbolicLinkObject)source;
			for (SymbolicLinkReference symblRef : symbloSrc.getOutgoings(reference)) {
				if (symblRef.getTarget().equals(target)) {
					edgeOccurrences.add(new Link(source, target, reference));
				}
			}
		}
	}

	@Override
	protected void createAttribute(Set<Field> attributeOccurrences, EObject eObject, EAttribute eAttribute) {
		if(eObject instanceof SymbolicLinkObject) {
			for(SymbolicLinkAttribute symblA : ((SymbolicLinkObject)eObject).getLinkAttributes()){
				if(symblA.getType().equals(eAttribute)){
					attributeOccurrences.add(new Field(symblA.eContainer(), symblA.getType(), symblA.getValue()));
				}
			}
		}
	}
}
