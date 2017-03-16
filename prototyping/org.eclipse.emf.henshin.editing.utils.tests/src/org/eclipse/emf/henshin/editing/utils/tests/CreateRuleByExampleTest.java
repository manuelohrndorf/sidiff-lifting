package org.eclipse.emf.henshin.editing.utils.tests;

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.editing.utils.HenshinEditingUtils;
import org.eclipse.emf.henshin.model.Module;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateRuleByExampleTest {

	/**
	 * The historic model version.
	 */
	private Resource resAtestCase1;
	
	/**
	 * The actual model version.
	 */
	private Resource resBtestCase1;
	
	@Before
	public void setUp() throws Exception {
		ResourceSet resourceSet = new ResourceSetImpl();
		
		resAtestCase1 = resourceSet.createResource(URI.createFileURI(
				"tests/CreateRuleByExample/TestCase1/A/A.ecore"));
		resAtestCase1.load(Collections.EMPTY_MAP);
		
		resBtestCase1 = resourceSet.createResource(URI.createFileURI(
				"tests/CreateRuleByExample/TestCase1/B/A.ecore"));
		resBtestCase1.load(Collections.EMPTY_MAP);
	}
	
	@Test
	public void testCreateRuleByExampleCase1() {
		Module module = HenshinEditingUtils.createRuleByExample(resAtestCase1, resBtestCase1);
		
		if (module != null) {
			Assert.assertEquals(module.getUnits().size(), 1);
			// TODO: More tests...
		} else {
			Assert.fail("No module was generated!");
		}
	}
}
