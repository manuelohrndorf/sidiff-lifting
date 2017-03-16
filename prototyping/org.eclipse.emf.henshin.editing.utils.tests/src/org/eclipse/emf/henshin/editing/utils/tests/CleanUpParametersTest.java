package org.eclipse.emf.henshin.editing.utils.tests;

import org.eclipse.emf.henshin.editing.utils.HenshinEditingUtils;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.tests.framework.HenshinLoaders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CleanUpParametersTest {

	/**
	 * Remove unnecessary parameters and parameter mapping -> no parameters/mappings.
	 */
	private Module testCase1;
	
	@Before
	public void setUp() throws Exception {
		testCase1 = HenshinLoaders.loadHenshin("tests/CleanUpParameters/TestCase1/default.henshin");
	}
	
	@Test
	public void testCleanUpParametersCase1() {
		HenshinEditingUtils.cleanUpParameters(testCase1);
		
		testCase1.eAllContents().forEachRemaining(element -> {
			if (element instanceof Parameter) {
				Assert.fail("Unexpexted parameter found!");
			}
			
			else if (element instanceof ParameterMapping) {
				Assert.fail("Unexpexted parameter mapping found!");
			}
		});
	}
}
