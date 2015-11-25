package org.sidiff.patching.ui.animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.sidiff.common.emf.access.Scope;
import org.sidiff.correspondences.matchingmodel.MatchingModelCorrespondences;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.matcher.IMatcher;
import org.sidiff.matcher.MatcherUtil;
import org.sidiff.patching.ui.animation.internal.AnimationAdapter;
import org.sidiff.patching.ui.animation.internal.GridLayouter;

public class GMFAnimation {
	
	public static int MODE_DEFAULT = 0;
	public static int MODE_TRIGGER = 1;
	
	private static Map<Resource, AnimationAdapter> animatedResources = new HashMap<Resource, AnimationAdapter>();
	private static List<AnimationAdapter> attachedAdapters = new ArrayList<AnimationAdapter>();
	
	public static void enableAnimation(Resource changingResource, boolean createMatching, int mode){	
		animatedResources.clear();
		attachedAdapters.clear();
		
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
						if(createMatching){
							IMatcher usedMatcher = null;
							// Search registered matcher extension points
							Set<IMatcher> matcherSet = MatcherUtil.getAvailableMatchers(Arrays.asList(changingResource, resource));
							if(matcherSet.size() > 0){
								for (Iterator<IMatcher> iterator = matcherSet.iterator(); iterator.hasNext();) {
									IMatcher matcher = iterator.next();
								//Use URI Fragment Matcher if available
									if(matcher.getKey().equals("URIFragmentMatcher")){
										usedMatcher = matcher;
									}
								}
								//else use any
								if (usedMatcher == null){
									usedMatcher = matcherSet.iterator().next();
								}
								//Get one random matcher
								usedMatcher.startMatching(Arrays.asList(changingResource, resource), Scope.RESOURCE);
								MatchingModelCorrespondences correspondences = (MatchingModelCorrespondences) usedMatcher.getCorrespondencesService();
								EcoreUtil.resolveAll(correspondences.getMatching());
								SymmetricDifference difference = SymmetricFactory.eINSTANCE.createSymmetricDifference();
								difference.setMatching(correspondences.getMatching());
								if(checkMatching(difference)){
									editorMatchings.add(new EditorMatching(difference,correspondences, diagramEditor));
								}
							}
						} else {
							editorMatchings.add(new EditorMatching(null, null, diagramEditor));
						}
					}
				}
			}
		}
		AnimationAdapter adapter = new AnimationAdapter(editorMatchings.toArray(new EditorMatching[editorMatchings.size()]), mode == MODE_TRIGGER);
		attachedAdapters.add(adapter);
		
		changingResource.eAdapters().add(adapter);
		animatedResources.put(changingResource, adapter);
	}
	
	public static void trigger(){
		for(AnimationAdapter animationAdapter : attachedAdapters){
			animationAdapter.trigger();
		}
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
		
		public SymmetricDifference difference = null;
		public DiagramEditor editor = null;
		public MatchingModelCorrespondences correspondences = null;
		
		public EditorMatching(SymmetricDifference difference, MatchingModelCorrespondences correspondences, DiagramEditor editor){
			this.difference = difference;
			this.correspondences = correspondences;
			this.editor = editor;
		}
		
	}

}
