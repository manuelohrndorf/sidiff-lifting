package org.sidiff.difference.patch.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.matcher.uri.URIFragmentMatcher;
import org.sidiff.difference.matcher.util.MatcherUtil;
import org.sidiff.difference.patch.animation.internal.AnimationAdapter;
import org.sidiff.difference.patch.animation.internal.GridLayouter;
import org.sidiff.difference.symmetric.SymmetricDifference;

public class GMFAnimation {
	
	private static Map<Resource, AnimationAdapter> animatedResources = new HashMap<Resource, AnimationAdapter>();
	
	public static void enableAnimation(Resource changingResource){		
		List<EditorMatching> editorMatchings = new ArrayList<EditorMatching>();

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String[] segments = changingResource.getURI().deresolve(URI.createURI(root.getLocationURI().toString())).toString().split("/", 2);
		URI uri = URI.createPlatformResourceURI(segments[1], true);
		for(IEditorReference editorReference : PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences()){
			IEditorPart editor = editorReference.getEditor(false);
			if(editor instanceof DiagramEditor){
				DiagramEditor diagramEditor = (DiagramEditor) editor;
				TransactionalEditingDomain editingDomain = diagramEditor.getEditingDomain();
				
				GridLayouter layouter = GridLayouter.getInstance();
				layouter.setup(diagramEditor);
				
				for(Resource resource : editingDomain.getResourceSet().getResources()){
					if(resource.getURI().toString().endsWith(".ecore")){
						IMatcher matcher = MatcherUtil.getMatcherByKey(URIFragmentMatcher.KEY, changingResource, resource);
						SymmetricDifference matching = matcher.createMatching(changingResource, resource, 0, false);
						EcoreUtil.resolveAll(matching);
						if(checkMatching(matching)){
							editorMatchings.add(new EditorMatching(matching, diagramEditor));
						}
					}
				}
			}
		}
		AnimationAdapter adapter = new AnimationAdapter(editorMatchings.toArray(new EditorMatching[editorMatchings.size()]));

		changingResource.eAdapters().add(adapter);
		animatedResources.put(changingResource, adapter);
	}
	
	private static boolean checkMatching(SymmetricDifference matching) {
		return matching.getChanges().isEmpty();
	}

	public static void disableAnimation(Resource resource){
		if(animatedResources.containsKey(resource)){
			resource.eAdapters().remove(animatedResources.get(resource));
			animatedResources.remove(resource);
		}
	}
	
	public static class EditorMatching {
		
		public SymmetricDifference matching = null;
		public DiagramEditor editor = null;
		
		public EditorMatching(SymmetricDifference matching, DiagramEditor editor){
			this.matching = matching;
			this.editor = editor;
		}
		
	}

}
