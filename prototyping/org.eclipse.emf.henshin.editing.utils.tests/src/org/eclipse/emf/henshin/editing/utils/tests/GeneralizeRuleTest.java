package org.eclipse.emf.henshin.editing.utils.tests;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.editing.utils.HenshinEditingUtils;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.tests.framework.HenshinLoaders;
import org.junit.Assert;
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
		
		// Test rule:
		Rule rule = (Rule) testCase1.getUnits().get(0);
		
		int countETypedElementLHS = 2;
		int countETypedElementRHS = 1;
		int countEClassifierLHS = 2;
		int countEClassifierRHS = 1;
		int countEOperationRHS = 1;
		int countEDataTypeRHS = 1;
		
		// LHS:
		for (Node node : rule.getLhs().getNodes()) {
			
			if (node.getType() == EcorePackage.eINSTANCE.getETypedElement()) {
				--countETypedElementLHS;
			}
			
			else if (node.getType() == EcorePackage.eINSTANCE.getEClassifier()) {
				--countEClassifierLHS;
			}
		}
		
		// RHS:
		for (Node node : rule.getRhs().getNodes()) {
			
			if (node.getType() == EcorePackage.eINSTANCE.getETypedElement()) {
				--countETypedElementRHS;
			}
			
			else if (node.getType() == EcorePackage.eINSTANCE.getEClassifier()) {
				--countEClassifierRHS;
			}
			
			else if (node.getType() == EcorePackage.eINSTANCE.getEOperation()) {
				--countEOperationRHS;
			}
			
			else if (node.getType() == EcorePackage.eINSTANCE.getEDataType()) {
				--countEDataTypeRHS;
			}
		}
		
		Assert.assertEquals(0, countETypedElementLHS);
		Assert.assertEquals(0, countETypedElementRHS);
		Assert.assertEquals(0, countEClassifierLHS);
		Assert.assertEquals(0, countEClassifierRHS);
		Assert.assertEquals(0, countEOperationRHS);
		Assert.assertEquals(0, countEDataTypeRHS);
	}
}
