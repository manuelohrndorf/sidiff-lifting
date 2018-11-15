package org.sidiff.editrule.generator.serge.core.variantgeneration;

import java.util.Iterator;
import java.util.LinkedHashSet;

import org.eclipse.emf.henshin.model.Node;
import org.sidiff.editrule.generator.serge.core.variantgeneration.SequenceEntry.EntryFlag;

@SuppressWarnings("serial")
public class Sequence extends LinkedHashSet<SequenceEntry>{

	private static InspectionFlag inspectionFlag;
	
	private static Boolean sequenceContainsAbstracts = false;
	
	public enum InspectionFlag {
		uninspected,
		incomplete,
		complete	
	}
	
	public Sequence() {
		inspectionFlag = InspectionFlag.uninspected;
	}
	
	public static Boolean doesSequenceContainAbstracts() {
		return sequenceContainsAbstracts;
	}

	public void setSequenceContainsAbstracts() {
		sequenceContainsAbstracts = true;
	}

	public SequenceEntry addEntry(Node n) {
		
		SequenceEntry se = new SequenceEntry(n, this);
		this.add(se);
		
		return se;
	}
	
	public SequenceEntry getLastEntry() {
		
		SequenceEntry last = null;
		
		Iterator<SequenceEntry> sequenceIt = this.iterator();
		
		while(sequenceIt.hasNext()) {
			
			last = sequenceIt.next();			
		}
		
		
		return last;
	}
	
	public void setEntryFlag(Node node, EntryFlag entryFlag) {
		
		for(SequenceEntry entry: this) {
			if(entry.getKey().equals(node)) {
				entry.setValue(entryFlag);
				break;
			}
		}
	}
	
	public EntryFlag getEntryFlag(Node node) {
		
		for(SequenceEntry entry: this) {
			if(entry.getKey().equals(node)) {
				return (EntryFlag) entry.getValue();
			}
		}
		return EntryFlag.uninspected;
	}
	
	public void setInspectionFlag(InspectionFlag flag) {
		inspectionFlag = flag;
	}
	
	public InspectionFlag getInspectionFlag() {
		return inspectionFlag;
	}
	
	public void print() {

		for(SequenceEntry entry: this) {
			if(entry.getValue() == EntryFlag.isAbstractAndWasReplaced) {
				System.out.print(" " + entry.getKey().getType().getName());
			}
			else {
				System.out.print(" " + "[" + entry.getKey().getType().getName() + "]");
			}
		}
		System.out.println("\n");

	}
	
	public void concatSequence(Sequence furtherSequence) {
		for(SequenceEntry entry: furtherSequence) {
			this.add(entry);
		}
	}
	
	public String getSequenceWithoutInspectionFlag() {
		
		String output = "";

		for(SequenceEntry entry: this) {
			
			output += entry.getKey().getType().getName();
			
			if(!entry.equals(this.getLastEntry())) {
				output += " ";
			}
		}
		
		return output;
	}
	
	@Override
	public String toString() {

		String output = "";

		for(SequenceEntry entry: this) {
			output += entry.getKey().getType().getName() + " ";
		}
		
		output += "(Inspection: " + inspectionFlag.toString() + ")";
		
		return output;
	}
	
}
