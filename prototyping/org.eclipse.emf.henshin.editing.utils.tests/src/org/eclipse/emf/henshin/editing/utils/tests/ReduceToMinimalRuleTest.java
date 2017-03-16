package org.eclipse.emf.henshin.editing.utils.tests;

import org.eclipse.emf.henshin.editing.utils.HenshinEditingUtils;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.tests.framework.HenshinLoaders;
import org.junit.Before;
import org.junit.Test;

public class ReduceToMinimalRuleTest {

	private Module testCase1;

	@Before
	public void setUp() throws Exception {
		testCase1 = HenshinLoaders.loadHenshin("tests/ReduceToMinimalRule/default.henshin");
	}

	@Test
	public void testGeneralizeRuleCase1() {
		HenshinEditingUtils.reduceToMinimalRule(testCase1);
		// TODO: More tests...
	}
}
