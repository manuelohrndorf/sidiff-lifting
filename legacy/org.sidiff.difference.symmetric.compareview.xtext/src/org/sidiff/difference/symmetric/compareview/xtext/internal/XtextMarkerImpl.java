package org.sidiff.difference.symmetric.compareview.xtext.internal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.sidiff.difference.symmetric.compareview.ChangeType;
import org.sidiff.difference.symmetric.compareview.XtextMarker;

public class XtextMarkerImpl implements XtextMarker {
	
	private List<IMarker> textMarker = new ArrayList<IMarker>();

	@Override
	public boolean isXtextObject(EObject eObject) {
		if (eObject != null) {
			INode node = adapt(INode.class, eObject);
			return node != null;
		}
		return false;
	}

	@Override
	public void mark(EObject textEObject, IEditorPart editor, ChangeType type, String markerID) {
		if (!(editor instanceof XtextEditor)){
			// no Xtext editor, nothing to mark
			return;
		}
		
		INode textNode = adapt(INode.class, textEObject);
		if (textNode != null) {
			IResource resource = ((XtextEditor) editor).getResource();
			try {
				IMarker marker = resource
						.createMarker(markerID);
				marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
				switch(type){
				case ADD:
					marker.setAttribute(IMarker.MESSAGE, "added element");
					break;
				case DELETE:
					marker.setAttribute(IMarker.MESSAGE, "deleted element");
					break;
				case CHANGE:
					marker.setAttribute(IMarker.MESSAGE, "modified element");
					break;
				case CORRESPONDENCE:
					marker.setAttribute(IMarker.MESSAGE, "correspondent element");
				case CONTEXT:
					marker.setAttribute(IMarker.MESSAGE, "context element");
					break;
				default: 
					marker.setAttribute(IMarker.MESSAGE, "unsupported difference information");
					break;
				}
				
				marker.setAttribute(IMarker.LINE_NUMBER, textNode.getStartLine());
				
//				if(!type.equals(ChangeType.CONTEXT)){
					marker.setAttribute(IMarker.CHAR_START, textNode.getOffset());
					marker.setAttribute(IMarker.CHAR_END, textNode.getOffset()+ textNode.getLength());
//				}
				

				textMarker.add(marker);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			((XtextEditor) editor).reveal(textNode.getOffset(), textNode.getLength());
		}

	}
	
	public void clear() {
		for (IMarker marker : textMarker) {
			try {
				marker.delete();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		textMarker.clear();	
	}
	
	@SuppressWarnings("unchecked")
	private <T> T adapt(Class<T> clazz, EObject eObject) {
		for(Adapter adapter : eObject.eAdapters()) {
			if (clazz.isInstance(adapter)) {
				T node = (T) adapter;
				return node;
			}
		}
		return null;
	}

	@Override
	public Set<EObject> findXtextObjectInEditor(EObject search, IEditorPart editor, final URI resourceURI) {
		final Set<EObject> result = new HashSet<EObject>();
		if (editor instanceof XtextEditor) {
			final XtextEditor xtextEditor = (XtextEditor) editor;	
			final String selectedObjectFragment = EcoreUtil.getURI(search).fragment();
			
			xtextEditor.getDocument().readOnly(new IUnitOfWork.Void<XtextResource>(){

				@Override
				public void process(XtextResource resource) throws Exception {
					if (resource.getURI().equals(resourceURI)) {
						EObject eObject = resource.getEObject(selectedObjectFragment);

						if (result != null) {
							result.add(eObject);
						}
					}
				}
				
			});
			
		}
		return result;
	}

	@Override
	public EObject[] getContextElement(EObject eObject, Map<EObject, EObject>correspondences) {
		INode textNode = adapt(INode.class, eObject);
		//FIXME 
		INode parent = textNode.getParent();
		
		while(correspondences.get(parent.getSemanticElement())==null){
			parent = parent.getParent();
		}
		
		return new EObject[] {parent.getSemanticElement(), correspondences.get(parent.getSemanticElement())};
		
	}
}
