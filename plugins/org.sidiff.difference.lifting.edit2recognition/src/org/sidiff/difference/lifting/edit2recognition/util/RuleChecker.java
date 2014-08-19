package org.sidiff.difference.lifting.edit2recognition.util;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

public class RuleChecker {

	/**
	 * Checks if the rules of a module use derived references
	 * @param editModule
	 * @return
	 * 
	 * TODO: has to be adapted for multirules (Henshin >= 0.9)
	 */
	public static boolean checkDerivedReferences(Module editModule){
		for(Unit u : editModule.getUnits()){
			if(u instanceof Rule){
				for(Edge e: ((Rule) u).getLhs().getEdges()){
					if((e.getType().isDerived()))
							return true;
				}
				//if(!((Rule)u).getMultiRules().isEmpty() ) ...
			}
		}
		return false;
	}
	
}
