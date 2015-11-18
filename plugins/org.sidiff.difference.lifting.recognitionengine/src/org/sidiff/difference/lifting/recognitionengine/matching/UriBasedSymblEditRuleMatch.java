package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.common.emf.access.Field;
import org.sidiff.common.emf.access.Link;
import org.sidiff.difference.symmetric.SemanticChangeSet;

/**
 * 
 * @author cpietsch
 *
 */
public class UriBasedSymblEditRuleMatch extends UriBasedEditRuleMatch {	
	
	
	public UriBasedSymblEditRuleMatch(SemanticChangeSet scs) {
		super(scs);
	}
	
	@Override
	protected  void createLink(Set<Link> edgeOccurrences, EObject[] tuple, EReference reference ){
		// FIXME (cpietsch: 02.09.2014) getNodeNeighbors doesn't work with symbolic links 
		// (see also org.sidiff.common.emf.access.Link)
		if (tuple[0] instanceof SymbolicLinkObject) {
			SymbolicLinkObject symbloSrc = (SymbolicLinkObject) tuple[0];
			for (SymbolicLinkReference symblRef : symbloSrc
					.getOutgoings(reference)) {
				if (symblRef.getTarget().equals(tuple[1])) {
					edgeOccurrences.add(new Link(tuple[0], tuple[1], reference));
				}
			}
		}
	}
	
	/**
	 * 
	 * @param attributeOccurrences
	 * @param eObject
	 * @param eAttribute
	 */
	@Override
	protected void createAttribute(Set<Field> attributeOccurrences, EObject eObject, EAttribute eAttribute){
		
		if(eObject instanceof SymbolicLinkObject){
			for(SymbolicLinkAttribute symblA : ((SymbolicLinkObject)eObject).getLinkAttributes()){
				if(symblA.getType().equals(eAttribute)){
					attributeOccurrences.add(new Field(symblA.eContainer(), symblA.getType() , symblA.getValue()));
				}
			}
		}
		
	}
	


}
