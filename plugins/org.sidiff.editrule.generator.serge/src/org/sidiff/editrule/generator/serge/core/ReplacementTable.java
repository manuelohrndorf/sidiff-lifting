package org.sidiff.editrule.generator.serge.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.emf.metamodel.analysis.EClassifierInfoManagement;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.serge.configuration.Configuration;
import org.sidiff.editrule.generator.serge.configuration.Configuration.OperationTypeGroup;

public class ReplacementTable {

	private ReplacementTableHeader tableHeader;
	private Set<ReplacementTableRow> tableRows;
	private EClassifierInfoManagement ECM;

	public ReplacementTable(Module module, OperationTypeGroup opTypeGroup) throws OperationTypeNotImplementedException {
		tableHeader = new ReplacementTableHeader(module);
		tableRows = new HashSet<ReplacementTableRow>();
		ECM = EClassifierInfoManagement.getInstance();
		fillRows(tableHeader, opTypeGroup);
	}

	public void printTable() {
		System.out.println("---------------------------------");
		for (int ni = 0; ni < tableHeader.size(); ni++) {
			System.out.print("[" + ni + "]" + tableHeader.get(ni).getType().getName() + " ");
		}
		System.out.println("\n");

		for (ReplacementTableRow row : tableRows) {
			for (int i = 0; i < row.size(); i++) {
				if (row.get(i) == null) {
					System.out.print(" [" + i + "]-");
				} else {
					System.out.print(" [" + i + "]" + row.get(i).getName());
				}
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	public boolean hasReplacements() {
		return !tableHeader.isEmpty();
	}

	public ReplacementTableHeader getHeader() {
		return tableHeader;
	}

	public Set<ReplacementTableRow> getRows() {
		return tableRows;
	}

	private void fillRows(ReplacementTableHeader tableHeader, OperationTypeGroup opTypeGroup) {

		if (tableHeader.isEmpty())
			return;

		Node initialNode = tableHeader.firstElement();
		EClassifier initialNodeType = initialNode.getType();
		Set<EClassifier> replacements = new HashSet<EClassifier>();
		//Vorraussetzung f�r reduced-Pr�fung: Im ReplacementTableHeader sind nur Danglings enthalten.
		if (!Configuration.getInstance().preferAbstractTypesOnDanglings(opTypeGroup)) {
			replacements.addAll(ECM.getAllConcreteEClassifiersForAbstract(initialNodeType));
		}
		if (!Configuration.getInstance().preferSuperTypesOnDanglings(opTypeGroup)) {
			replacements.addAll(ECM.getAllConcreteSubTypes(initialNodeType));
		}

		// create initial rows for first node replacements
		for (EClassifier initRepl : replacements) {
			ReplacementTableRow newRow = new ReplacementTableRow();
			newRow.insertElementAt(initRepl, 0);
			tableRows.add(newRow);
		}

		for (int i = 1; i < tableHeader.size(); i++) {

			Node nextNode = tableHeader.get(i);
			EClassifier nextNodeType = nextNode.getType();
			Set<EClassifier> nextReplacements = new HashSet<EClassifier>();
			//(siehe oben)
			if (!Configuration.getInstance().preferAbstractTypesOnDanglings(opTypeGroup)) {
				nextReplacements.addAll(ECM.getAllConcreteEClassifiersForAbstract(nextNodeType));
			}
			if (!Configuration.getInstance().preferSuperTypesOnDanglings(opTypeGroup)) {
				nextReplacements.addAll(ECM.getAllConcreteSubTypes(nextNodeType));
			}

			Iterator<ReplacementTableRow> it = tableRows.iterator();

			// if only one replacement for this node,
			// just insert it into existing rows at the respective column (i)
			if (nextReplacements.size() == 1) {
				EClassifier singleReplacement = nextReplacements.iterator().next();

				while (it.hasNext()) {
					ReplacementTableRow currentRow = it.next();
					currentRow.ensureCapacity(i);
					currentRow.insertElementAt(singleReplacement, i);
				}

			}
			// if more than one replacements for this node,
			// copy existing row (n times the number of replacements)
			// and insert replacements in the respective column (i) to each copy
			else if (nextReplacements.size() > 1) {

				Set<ReplacementTableRow> newRows = new HashSet<ReplacementTableRow>();

				for (EClassifier nextReplace : nextReplacements) {
					while (it.hasNext()) {
						ReplacementTableRow currentRow = it.next();
						currentRow.ensureCapacity(i);
						currentRow.insertElementAt(nextReplace, i);
						ReplacementTableRow copy = copy(currentRow);
						copy.set(nextReplace, i);
						newRows.add(copy);
					}
				}
				tableRows.addAll(newRows);
			}
		}

	}

	private ReplacementTableRow copy(ReplacementTableRow row) {

		ReplacementTableRow copy = new ReplacementTableRow();

		for (int i = 0; i < row.size(); i++) {
			copy.insertElementAt(row.get(i), i);
		}

		return copy;
	}
}
