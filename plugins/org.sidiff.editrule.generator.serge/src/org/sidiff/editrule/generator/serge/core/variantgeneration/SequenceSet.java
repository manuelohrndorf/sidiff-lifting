package org.sidiff.editrule.generator.serge.core.variantgeneration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * A LinkedHashSet of Sequences. It also contains a map which contains the identities of each entry which 
 * occures unchanged in replicated sequences;
 * 
 * @author mrindt
 *
 */
@SuppressWarnings("serial")
public class SequenceSet extends LinkedHashSet<Sequence>{
	
	private static Map<SequenceEntry, LinkedHashMap<Sequence, SequenceEntry>> entryIdentityMap = new HashMap<SequenceEntry, LinkedHashMap<Sequence, SequenceEntry>>();
	
	public void addToIdentity(SequenceEntry originalEntry, Sequence ocurringInSequence, SequenceEntry ocurringEntryInSequence) {
		
		if(entryIdentityMap.containsKey(originalEntry)) {
			LinkedHashMap<Sequence, SequenceEntry> ocurrences = entryIdentityMap.get(originalEntry);
			ocurrences.put(ocurringInSequence, ocurringEntryInSequence);
		}else {
			LinkedHashMap<Sequence, SequenceEntry> ocurrences = new LinkedHashMap<Sequence, SequenceEntry>();
			ocurrences.put(ocurringInSequence, ocurringEntryInSequence);
			entryIdentityMap.put(originalEntry, ocurrences);
		}		
	}
	
	public Map<SequenceEntry, LinkedHashMap<Sequence, SequenceEntry>> getEntryIdentityMap() {
		return entryIdentityMap;
	}
	
	public void clearIdentityMap() {
		
		entryIdentityMap.clear();
		
	}
	
}
