package org.eclipse.emf.henshin.editing.utils.tests;

import org.eclipse.emf.henshin.editing.utils.HenshinEditingUtils;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.tests.framework.HenshinLoaders;
import org.junit.Assert;
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
		
		Rule rule = (Rule) testCase1.getUnits().get(0);
		
		// Nodes:
		Assert.assertEquals(4, rule.getLhs().getNodes().size());
		Assert.assertEquals(5, rule.getRhs().getNodes().size());
		
		// Edges:
		Assert.assertEquals(1, rule.getLhs().getEdges().size());
		Assert.assertEquals(2, rule.getRhs().getEdges().size());
	}
}
