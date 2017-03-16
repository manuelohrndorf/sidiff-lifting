package org.eclipse.emf.henshin.editing.utils.tests;

import org.eclipse.emf.henshin.editing.utils.HenshinEditingUtils;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.tests.framework.HenshinLoaders;
import org.junit.Before;
import org.junit.Test;

public class GeneralizeRuleTest {

	private Module testCase1;
	
	@Before
	public void setUp() throws Exception {
		testCase1 = HenshinLoaders.loadHenshin("tests/GeneralizeRule/TestCase1/default.henshin");
	}
	
	@Test
	public void testGeneralizeRuleCase1() {
		HenshinEditingUtils.generalizeRule(testCase1);
		// TODO: More tests...
	}
}
