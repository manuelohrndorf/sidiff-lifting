package org.sidiff.difference.symmetric.compareview.ide.structureviewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffTreeViewer;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;


/*
 * Difference-StructureViewer: Oberer Teil des Compare-View.
 *  - Muss minimal StructuredViewer implementieren für Double-Click Inhaltspropagierung an ContentViewer
 *     - z.B. TreeViewer (allgemein), DiffTreeViewer (Compare API: verwendet IDiffContainer, IDiffElement als Inhalt)
 *  - ExtensionPoint: org.eclipse.compare.structureMergeViewers registriert die Factory für den StructureViewer
 *     - viewer -> extensions: gültige Dateierweiterungen 
 *       UND!!! Knoten-Typen im StructureViewer, welche auf Double-Click einen ContentViewer anzeigen sollen.
 *       Bei Double-Click wird zunächst nach einem passenden StructureViewer gesucht und erst wenn dieser 
 *       gefunden wurde, wird ein passender ContentViewer gesucht und angezeigt.
 *  - Double-Click Knoten müssen ICompareInput Interface implementieren.
 *    - getLeft, getRight, getAncestor liefern ITypedElement -> getType muss den im ExtensionPoint registrierten
 *      Typ liefern: z.B. "ORG.SIDIFF.DIFFERENCE.SYMMETRIC.IMPL.REMOVEREFERENCEIMPL"
 *      
 * Difference-ContentViewer: Unterer Teil des Compare-View.
 *  - ExtensionPoint: org.eclipse.compare.contentMergeViewers registriert die Factory für den ContentViewer.
 *    - viewer -> extensions: (Analog zum StructureViewer) Knoten-Typen (Content-Typen) des Structure-Viewers
 *      die von ICompareInput.getLeft().getType() geliefert werden: z.B. "ORG.SIDIFF.DIFFERENCE.SYMMETRIC.IMPL.REMOVEREFERENCEIMPL"
 *    - Basisklasse z.B. ContentMergeViewer
 */

public class TestStructureViewer extends DiffTreeViewer {

	DiffContainer c;
	
	public TestStructureViewer(Composite parent, CompareConfiguration configuration) {
		super(parent, configuration);
		
		getTree().setLinesVisible(true);
		
		c = new DiffContainer();
		DiffElement a = new DiffElement();
		
		a.setParent(c);
		c.add(a);
		
		setInput(c);
		refresh();
	}
	
	@Override
	protected void inputChanged(Object input, Object oldInput) {
		super.inputChanged(input, oldInput);
		
		if (input != c) {
			setInput(c);
		}
		
		refresh();
	}
	
	public class DiffContainer implements IDiffContainer {

		IDiffContainer parent;
		IDiffElement[] children = new IDiffElement[1];
		
		@Override
		public int getKind() {
			return Differencer.CHANGE;
		}

		@Override
		public IDiffContainer getParent() {
			return parent;
		}

		@Override
		public void setParent(IDiffContainer parent) {
			this.parent = parent;
		}

		@Override
		public String getName() {
			return "Container";
		}

		@Override
		public Image getImage() {
			return null;
		}

		@Override
		public String getType() {
			return "ecore";
		}

		@Override
		public boolean hasChildren() {
			return false;
		}

		@Override
		public IDiffElement[] getChildren() {
			return children;
		}

		@Override
		public void add(IDiffElement child) {
			children[0] = child;
			
		}

		@Override
		public void removeToRoot(IDiffElement child) {
			children[0] = null;
		}
		
	}
	
	public class DiffElement implements IDiffElement, ICompareInput {

		public String toString() {
			return "DiffElement";
		};
		
		IDiffContainer parent;
		String element = "Ich bin ein DiffElement!";
		
		@Override
		public String getName() {
			return element;
		}

		@Override
		public Image getImage() {
			return null;
		}

		@Override
		public String getType() {
			return "ecore";
		}

		@Override
		public int getKind() {
			return Differencer.CHANGE;
		}

		@Override
		public IDiffContainer getParent() {
			return parent;
		}

		@Override
		public void setParent(IDiffContainer parent) {
			this.parent = parent;
		}

		@Override
		public ITypedElement getAncestor() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ITypedElement getLeft() {

			return new ITypedElement() {
				
				@Override
				public String getType() {
					return "ORG.SIDIFF.DIFFERENCE.SYMMETRIC.IMPL.REMOVEREFERENCEIMPL";
//					return "org.eclipse.emf.compare.rcp.ui.eMatch";
				}
				
				@Override
				public String getName() {
					return "ITYPETEST.test";
				}
				
				@Override
				public Image getImage() {
					return null;
				}
			};
		}

		@Override
		public ITypedElement getRight() {
//			return new ITypedElement() {
//				
//				@Override
//				public String getType() {
//					return "ORG.SIDIFF.DIFFERENCE.SYMMETRIC.IMPL.REMOVEREFERENCEIMPL";
////					return "org.eclipse.emf.compare.rcp.ui.eMatch";
//				}
//				
//				@Override
//				public String getName() {
//					return "ITYPETEST.test";
//				}
//				
//				@Override
//				public Image getImage() {
//					return null;
//				}
//			};
			return null;
		}

		@Override
		public void addCompareInputChangeListener(ICompareInputChangeListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public void removeCompareInputChangeListener(ICompareInputChangeListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public void copy(boolean leftToRight) {
			// TODO Auto-generated method stub
		}
	}
}
