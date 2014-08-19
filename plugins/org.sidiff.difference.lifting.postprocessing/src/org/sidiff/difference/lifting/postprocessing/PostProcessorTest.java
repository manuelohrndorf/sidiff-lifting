package org.sidiff.difference.lifting.postprocessing;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;

/**
 * Class is used to run a test scenario on the post processor. 
 */
public class PostProcessorTest {
	
	/**
	 * Run the test scenarios.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		boolean nongreedy = test(false);
		boolean greedy = test(true);
		
		System.out.println("\n\n===========================");
		
		if (nongreedy && greedy) {
			System.out.print("\nALL TESTS PASSED!!!");
		} else {
			if (nongreedy) {
				System.out.print("\nNON GREEDY TEST PASSED!!!");
			} else {
				System.err.print("\nNON GREEDY TEST FAILED!!!");
			}
			
			if (greedy) {
				System.out.print("\nGREEDY TEST PASSED!!!");
			} else {
				System.err.print("\nGREEDY TEST FAILED!!!");
			}
		}
	}
	
	/**
	 * Run the test scenario.
	 * 
	 * @param args
	 */
	public static boolean test(boolean testGreedy) {
		
		/*
		 * Create semantic change sets
		 */
		
		// 1
		SemanticChangeSet cs1 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs1.setName("cs1");

		Change c0 = SymmetricFactory.eINSTANCE.createAddObject();
		Change c1 = SymmetricFactory.eINSTANCE.createAddReference();
		Change c2 = SymmetricFactory.eINSTANCE.createAttributeValueChange();
		Change c3 = SymmetricFactory.eINSTANCE.createRemoveObject();
		Change c4 = SymmetricFactory.eINSTANCE.createRemoveReference();
		Change c5 = SymmetricFactory.eINSTANCE.createAddObject();

		cs1.getChanges().add(c0);
		cs1.getChanges().add(c1);
		cs1.getChanges().add(c2);
		cs1.getChanges().add(c3);
		cs1.getChanges().add(c4);
		cs1.getChanges().add(c5);

		// 2
		SemanticChangeSet cs2 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs2.setName("cs2");
		cs2.getChanges().add(c0);

		// 3
		SemanticChangeSet cs3 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs3.setName("cs3");
		cs3.getChanges().add(c0);

		// 4
		SemanticChangeSet cs4 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs4.setName("cs4");

		cs4.getChanges().add(c1);
		cs4.getChanges().add(c2);

		// 5
		SemanticChangeSet cs5 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs5.setName("cs5");

		cs5.getChanges().add(c2);
		cs5.getChanges().add(c3);

		// 6
		SemanticChangeSet cs6 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs6.setName("cs6");
		cs6.getChanges().add(c4);

		// 7
		SemanticChangeSet cs7 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs7.setName("cs7");
		cs7.getChanges().add(c5);

		// 8
		SemanticChangeSet cs8 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs8.setName("cs8");

		Change c6 = SymmetricFactory.eINSTANCE.createAddReference();
		Change c7 = SymmetricFactory.eINSTANCE.createAttributeValueChange();
		Change c8 = SymmetricFactory.eINSTANCE.createRemoveObject();

		cs8.getChanges().add(c6);
		cs8.getChanges().add(c7);
		cs8.getChanges().add(c8);

		// 9
		SemanticChangeSet cs9 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs9.setName("cs9");

		cs9.getChanges().add(c6);
		cs9.getChanges().add(c7);
		cs9.getChanges().add(c8);

		// 10
		SemanticChangeSet cs10 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs10.setName("cs10");

		cs10.getChanges().add(c7);

		// 11
		SemanticChangeSet cs11 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs11.setName("cs11");
		Change c9 = SymmetricFactory.eINSTANCE.createRemoveReference();
		cs11.getChanges().add(c9);

		// 12
		SemanticChangeSet cs12 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs12.setName("cs12");

		Change c10 = SymmetricFactory.eINSTANCE.createAddObject();
		Change c11 = SymmetricFactory.eINSTANCE.createAddReference();
		Change c18 = SymmetricFactory.eINSTANCE.createAddReference(); // -> cs19

		cs12.getChanges().add(c10);
		cs12.getChanges().add(c11);
		cs12.getChanges().add(c18);

		// 13
		SemanticChangeSet cs13 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs13.setName("cs13");

		Change c12 = SymmetricFactory.eINSTANCE.createAttributeValueChange();
		Change c13 = SymmetricFactory.eINSTANCE.createRemoveObject();
		Change c14 = SymmetricFactory.eINSTANCE.createRemoveReference();

		cs13.getChanges().add(c11);
		cs13.getChanges().add(c12);
		cs13.getChanges().add(c13);
		cs13.getChanges().add(c14);

		// 14
		SemanticChangeSet cs14 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs14.setName("cs14");
		cs14.getChanges().add(c12);

		// 15
		SemanticChangeSet cs15 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs15.setName("cs15");
		cs15.getChanges().add(c13);

		// 16
		SemanticChangeSet cs16 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs16.setName("cs16");
		Change c15 = SymmetricFactory.eINSTANCE.createAddObject();

		cs16.getChanges().add(c14);
		cs16.getChanges().add(c15);

		// 17
		SemanticChangeSet cs17 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs17.setName("cs17");

		Change c16 = SymmetricFactory.eINSTANCE.createAddReference();
		Change c17 = SymmetricFactory.eINSTANCE.createAttributeValueChange();

		cs17.getChanges().add(c16);
		cs17.getChanges().add(c17);

		// 18
		SemanticChangeSet cs18 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs18.setName("cs18");
		cs18.getChanges().add(c17);

		// 19
		SemanticChangeSet cs19 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs19.setName("cs19");
		cs19.getChanges().add(c18); // <- cs12
		
		// 20
		SemanticChangeSet cs20 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs20.setName("cs20");

		Change c19 = SymmetricFactory.eINSTANCE.createAddObject();
		Change c20 = SymmetricFactory.eINSTANCE.createAddReference();
		Change c21 = SymmetricFactory.eINSTANCE.createAttributeValueChange();
		Change c22 = SymmetricFactory.eINSTANCE.createRemoveObject();
		Change c23 = SymmetricFactory.eINSTANCE.createRemoveReference();
		Change c24 = SymmetricFactory.eINSTANCE.createAddObject();
		Change c25 = SymmetricFactory.eINSTANCE.createAddReference();
		Change c26 = SymmetricFactory.eINSTANCE.createAttributeValueChange();

		cs20.getChanges().add(c19);
		cs20.getChanges().add(c20);
		cs20.getChanges().add(c21);
		cs20.getChanges().add(c22);
		cs20.getChanges().add(c23);
		cs20.getChanges().add(c24);
		cs20.getChanges().add(c25);
		cs20.getChanges().add(c26);
		
		// 21
		SemanticChangeSet cs21 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs21.setName("cs21");
		
		Change c27 = SymmetricFactory.eINSTANCE.createRemoveObject();
		
		cs21.getChanges().add(c19);
		cs21.getChanges().add(c27);
		
		// 22
		SemanticChangeSet cs22 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs22.setName("cs22");
		
		Change c28 = SymmetricFactory.eINSTANCE.createAddReference();
		
		cs22.getChanges().add(c20);
		cs22.getChanges().add(c28);
		
		// 23
		SemanticChangeSet cs23 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs23.setName("cs23");
		
		Change c29 = SymmetricFactory.eINSTANCE.createAttributeValueChange();
		Change c30 = SymmetricFactory.eINSTANCE.createRemoveObject();
		
		cs23.getChanges().add(c21);
		cs23.getChanges().add(c29);
		cs23.getChanges().add(c30);
		
		// 24
		SemanticChangeSet cs24 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs24.setName("cs24");
		
		cs24.getChanges().add(c30);
		
		// 25
		SemanticChangeSet cs25 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs25.setName("cs25");
		
		Change c31 = SymmetricFactory.eINSTANCE.createAttributeValueChange();
		Change c32 = SymmetricFactory.eINSTANCE.createRemoveObject();
		
		cs25.getChanges().add(c22);
		cs25.getChanges().add(c31);
		cs25.getChanges().add(c32);
		
		// 26
		SemanticChangeSet cs26 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs26.setName("cs26");

		cs26.getChanges().add(c22);
		cs26.getChanges().add(c31);

		// 31
		SemanticChangeSet cs31 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs31.setName("cs31");

		Change c33 = SymmetricFactory.eINSTANCE.createAddObject();
		Change c34 = SymmetricFactory.eINSTANCE.createAddReference();
		Change c35 = SymmetricFactory.eINSTANCE.createAttributeValueChange();
		Change c36 = SymmetricFactory.eINSTANCE.createRemoveObject();
		Change c37 = SymmetricFactory.eINSTANCE.createRemoveReference();
		Change c38 = SymmetricFactory.eINSTANCE.createAddObject();
		Change c39 = SymmetricFactory.eINSTANCE.createAddReference();
		Change c40 = SymmetricFactory.eINSTANCE.createAttributeValueChange();
		Change c41 = SymmetricFactory.eINSTANCE.createRemoveObject();
		Change c42 = SymmetricFactory.eINSTANCE.createRemoveReference();

		cs31.getChanges().add(c33);
		cs31.getChanges().add(c34);
		cs31.getChanges().add(c35);
		cs31.getChanges().add(c36);
		cs31.getChanges().add(c37);
		cs31.getChanges().add(c38);
		cs31.getChanges().add(c39);
		cs31.getChanges().add(c40);
		cs31.getChanges().add(c41);
		cs31.getChanges().add(c42);
		
		// 27
		SemanticChangeSet cs27 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs27.setName("cs27");
		
		Change c43 = SymmetricFactory.eINSTANCE.createAddReference();
		
		cs27.getChanges().add(c33);
		cs27.getChanges().add(c43);
		
		// 28
		SemanticChangeSet cs28 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs28.setName("cs28");
		
		Change c44 = SymmetricFactory.eINSTANCE.createAddReference();
		
		cs28.getChanges().add(c34);
		cs28.getChanges().add(c44);
		
		// 29
		SemanticChangeSet cs29 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs29.setName("cs29");
		
		Change c45 = SymmetricFactory.eINSTANCE.createAddReference();
		
		cs29.getChanges().add(c35);
		cs29.getChanges().add(c45);
		
		// 30
		SemanticChangeSet cs30 = SymmetricFactory.eINSTANCE.createSemanticChangeSet();
		cs30.setName("cs30");
		
		Change c46 = SymmetricFactory.eINSTANCE.createAddReference();
		
		cs30.getChanges().add(c36);
		cs30.getChanges().add(c46);
		

		
		/*
		 *  Difference
		 */
		SymmetricDifference difference = SymmetricFactory.eINSTANCE.createSymmetricDifference();

		difference.getChanges().add(c0);
		difference.getChanges().add(c1);
		difference.getChanges().add(c2);
		difference.getChanges().add(c3);
		difference.getChanges().add(c4);
		difference.getChanges().add(c5);
		difference.getChanges().add(c6);
		difference.getChanges().add(c7);
		difference.getChanges().add(c8);
		difference.getChanges().add(c9);
		difference.getChanges().add(c10);
		difference.getChanges().add(c11);
		difference.getChanges().add(c12);
		difference.getChanges().add(c13);
		difference.getChanges().add(c14);
		difference.getChanges().add(c15);
		difference.getChanges().add(c16);
		difference.getChanges().add(c17);
		difference.getChanges().add(c18);
		
		difference.getChanges().add(c19);
		difference.getChanges().add(c20);
		difference.getChanges().add(c21);
		difference.getChanges().add(c22);
		difference.getChanges().add(c23);
		difference.getChanges().add(c24);
		difference.getChanges().add(c25);
		difference.getChanges().add(c26);
		difference.getChanges().add(c27);	
		difference.getChanges().add(c28);
		difference.getChanges().add(c29);
		difference.getChanges().add(c30);
		difference.getChanges().add(c31);
		difference.getChanges().add(c32);
		
		difference.getChanges().add(c33);
		difference.getChanges().add(c34);
		difference.getChanges().add(c35);
		difference.getChanges().add(c36);
		difference.getChanges().add(c37);
		difference.getChanges().add(c38);
		difference.getChanges().add(c39);
		difference.getChanges().add(c40);
		difference.getChanges().add(c41);
		difference.getChanges().add(c42);
		difference.getChanges().add(c43);
		difference.getChanges().add(c44);
		difference.getChanges().add(c45);
		difference.getChanges().add(c46);
		

		// Ungrouped change
		Change c99 = SymmetricFactory.eINSTANCE.createAddReference();
		difference.getChanges().add(c99);
		
		difference.getChangeSets().add(cs1);
		difference.getChangeSets().add(cs2);
		difference.getChangeSets().add(cs3);
		difference.getChangeSets().add(cs4);
		difference.getChangeSets().add(cs5);
		difference.getChangeSets().add(cs6);
		difference.getChangeSets().add(cs7);
		difference.getChangeSets().add(cs8);
		difference.getChangeSets().add(cs9);
		difference.getChangeSets().add(cs10);
		difference.getChangeSets().add(cs11);
		difference.getChangeSets().add(cs12);
		difference.getChangeSets().add(cs13);
		difference.getChangeSets().add(cs14);
		difference.getChangeSets().add(cs15);
		difference.getChangeSets().add(cs16);
		difference.getChangeSets().add(cs17);
		difference.getChangeSets().add(cs18);
		difference.getChangeSets().add(cs19);
		
		difference.getChangeSets().add(cs20);
		difference.getChangeSets().add(cs21);
		difference.getChangeSets().add(cs22);
		difference.getChangeSets().add(cs23);
		difference.getChangeSets().add(cs24);
		difference.getChangeSets().add(cs25);
		difference.getChangeSets().add(cs26);
		
		difference.getChangeSets().add(cs27);
		difference.getChangeSets().add(cs28);
		difference.getChangeSets().add(cs29);
		difference.getChangeSets().add(cs30);
		difference.getChangeSets().add(cs31);
		
		/*
		 *  Start post processing
		 */

		PostProcessor postProcessor = new PostProcessor(difference);
		
		if (testGreedy) {
			postProcessor.setLimit(0);
		} else {
			postProcessor.setLimit(100000);
		}
		

		// Print before post processing
		System.out.println("Semantic change sets before post processing:");
		System.out.println(printChangeSet(difference.getChangeSets()));
		System.out.println("");

		postProcessor.postProcess();

		// Print after post processing
		System.out.println("\nSemantic change sets after post processing:");
		System.out.println(printChangeSet(difference.getChangeSets()));

		/*
		 * Check the result
		 */
		
		LinkedList<SemanticChangeSet> changeSetsExpectedResults_0 = new LinkedList<SemanticChangeSet>();
		changeSetsExpectedResults_0.add(cs1);
		changeSetsExpectedResults_0.add(cs11);
		changeSetsExpectedResults_0.add(cs12);
		changeSetsExpectedResults_0.add(cs14);
		changeSetsExpectedResults_0.add(cs15);
		changeSetsExpectedResults_0.add(cs16);
		changeSetsExpectedResults_0.add(cs17);
		
		if(testGreedy) {
			changeSetsExpectedResults_0.add(cs20);
			changeSetsExpectedResults_0.add(cs24);
			
			changeSetsExpectedResults_0.add(cs31);
		} else {
			changeSetsExpectedResults_0.add(cs21);
			changeSetsExpectedResults_0.add(cs22);
			changeSetsExpectedResults_0.add(cs23);
			changeSetsExpectedResults_0.add(cs25);
			
			changeSetsExpectedResults_0.add(cs31);
		}

		LinkedList<SemanticChangeSet> changeSetsExpectedResults_1 = new LinkedList<SemanticChangeSet>();
		changeSetsExpectedResults_1.addAll(changeSetsExpectedResults_0);
		changeSetsExpectedResults_1.add(cs8);

		LinkedList<SemanticChangeSet> changeSetsExpectedResults_2 = new LinkedList<SemanticChangeSet>();
		changeSetsExpectedResults_2.addAll(changeSetsExpectedResults_0);
		changeSetsExpectedResults_2.add(cs9);

		if (checkResult(difference.getChangeSets(), changeSetsExpectedResults_1)
				|| checkResult(difference.getChangeSets(), changeSetsExpectedResults_2)) {
			System.out.print("\nTEST PASSED!!!");
			return true;
		} else {
			System.out.print("\nTEST FAILED!!!");
			return false;
		}
	}

	/**
	 * Prints all semantic change sets.
	 * 
	 * @param changeSets the semantic change sets to print.
	 * @return the printed string.
	 */
	public static String printChangeSet(Collection<SemanticChangeSet> changeSets) {
		String changeSetsString = "";
		
		for (SemanticChangeSet changeSet : changeSets) {
			changeSetsString += "\n" + changeSet.getName();
		}
		
		return changeSetsString;
	}

	/**
	 * Check post processing results.
	 * 
	 * @param changeSetsResults the returned results.
	 * @param changeSetsExpectedResults the expected results.
	 * @return true if test is passed; false otherwise.
	 */
	private static boolean checkResult(EList<SemanticChangeSet> changeSetsResults,
			List<SemanticChangeSet> changeSetsExpectedResults) {
		if (changeSetsResults.containsAll(changeSetsExpectedResults)) {
			if (changeSetsResults.size() == changeSetsExpectedResults.size()) {
				return true;
			}
		}
		return false;
	}
}
