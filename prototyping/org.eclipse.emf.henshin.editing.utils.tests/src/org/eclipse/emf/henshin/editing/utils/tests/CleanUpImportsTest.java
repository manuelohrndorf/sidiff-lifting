package org.eclipse.emf.henshin.editing.utils.tests;

import org.eclipse.emf.henshin.editing.utils.HenshinEditingUtils;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.tests.framework.HenshinLoaders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CleanUpImportsTest {

	/**
	 * Empty rule -> no imports.
	 */
	private Module testCase1;
	
	/**
	 * Needs only ecore import.
	 */
	private Module testCase2;
	
	@Before
	public void setUp() throws Exception {
		testCase1 = HenshinLoaders.loadHenshin("tests/CleanUpImports/TestCase1/default.henshin");
		testCase2 = HenshinLoaders.loadHenshin("tests/CleanUpImports/TestCase2/default.henshin");
	}
	
	@Test
	public void testCleanUpImportsCase1() {
		HenshinEditingUtils.cleanUpImports(testCase1);
		Assert.assertEquals(0, testCase1.getImports().size());
	}
	
	@Test
	public void testCleanUpImportsCase2() {
		HenshinEditingUtils.cleanUpImports(testCase2);
		Assert.assertEquals(1, testCase1.getImports().size());
	}
}
