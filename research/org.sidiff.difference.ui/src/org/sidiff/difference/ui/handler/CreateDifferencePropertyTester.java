package org.sidiff.difference.ui.handler;

import org.eclipse.core.expressions.PropertyTester;


public class CreateDifferencePropertyTester extends PropertyTester {


	public CreateDifferencePropertyTester() {

	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		return true;
	}

}
