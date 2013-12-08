package org.sidiff.patching.reliability.ecore.impl;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.services.ServiceContext;
import org.sidiff.difference.matcher.sidiff.IReliabilityCalculator;
import org.sidiff.quality.reliability.ReliabilityAssignedMatch;
import org.sidiff.quality.reliability.ReliabilityCalculator;

public class EcoreReliabilityCalculator implements IReliabilityCalculator {
	private ReliabilityCalculator rc;

	@Override
	public void setContext(ServiceContext context) {
		this.rc = new ReliabilityCalculator("org.sidiff.patching.ecore.reliabilityconfig.xml", "org.sidiff.patching.ecore.contextconfig.xml");
		context.putHiddenService(rc);
	}

	@Override
	public void startingMatch() {
		rc.startListening();
	}

	@Override
	public void endingMatch() {
		rc.stopListening();
	}

	@Override
	public float getReliability(EObject elementA, EObject elementB) {
		ReliabilityAssignedMatch ram = rc.getReliabilityAssignedMatch(elementA, elementB);
		if (ram != null) {
			return ram.getReliability();
		}
		return 0;
	}

}
