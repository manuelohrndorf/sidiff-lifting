package org.sidiff.difference.mutation.util;

import java.io.File;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.mutation.Mutation;
import org.silift.common.util.emf.EMFStorage;

public class MutationUtil {
	
	/**
	 * Helper method for getting given resource name
	 * @param model
	 * @return
	 */
	public static String getResourceName(Resource model){
		//Try to resolve absolute path
		String modelFile = model.getURI().toFileString();
		
		//In case of relative uri
		if(modelFile == null)
			modelFile = EMFStorage.uriToPath(model.getURI());
		
		String modelName = modelFile.substring(modelFile.lastIndexOf(File.separator) + 1, modelFile.length());
		return modelName;		
	}
	
	/**
	 * Adapts the given context references according to
	 * the information given by the copier. This is used to redirect
	 * references in a match between copies of models.
	 * @param copier
	 * @param context
	 * @param rule
	 * @return
	 */
	public static void adaptContext(Copier copier, Match context, Rule rule){
		
		LogUtil.log(LogEvent.DEBUG, "Adapting context");		
		for(Node node : rule.getLhs().getNodes()){
			if(copier.containsKey(context.getNodeTarget(node))){
				EObject oldTarget = context.getNodeTarget(node);
				EObject newTarget = copier.get(context.getNodeTarget(node));

				LogUtil.log(LogEvent.DEBUG, "Changing target of Node " + node.getName() + " :" + oldTarget + " -> " + newTarget );
				LogUtil.log(LogEvent.DEBUG, "Changing referenced resource :" + oldTarget.eResource() + " -> " + newTarget.eResource());
				context.setNodeTarget(node, newTarget);
			}			
		}		
	}

	/**
	 * Adapts the context of given mutation to
	 * elements of the input model.
	 * @param mutation
	 */
	public static void adaptToInputContext(Mutation mutation){
		// Invert the copy map to get original elements
		Copier invertedCopier = new Copier();
		for(Map.Entry<EObject, EObject> entry : mutation.getCopier().entrySet()){
			invertedCopier.put(entry.getValue(), entry.getKey());
		}

		// Get Rule for mutation operator
		Rule mutationRule = (Rule) mutation.getUsedOperator().getExecuteModule().getUnits().get(0);

		// Re-Adapt context from mutant to input model
		MutationUtil.adaptContext(invertedCopier, mutation.getUsedContext(), mutationRule);	
	}
	
	/**
	 * Adapts the context of given mutation to elements
	 * transitively referenced in the mutation before.
	 * @param mutation
	 * @param mutationSequence
	 */
	public static void adaptToPreviousMutationContext(Mutation mutation, LinkedList<Mutation> mutationSequence){

		//Start at given mutation
		ListIterator<Mutation> li = mutationSequence.listIterator(mutationSequence.indexOf(mutation));			
		if(li.hasPrevious()){
			//Get previous
			Mutation previousMutation = li.previous();

			// Invert the copy map to get original elements
			Copier invertedCopier = new Copier();
			for(Map.Entry<EObject, EObject> entry : previousMutation.getCopier().entrySet()){
				invertedCopier.put(entry.getValue(), entry.getKey());
			}

			// Get Rule for mutation operator
			Rule mutationRule = (Rule) mutation.getUsedOperator().getExecuteModule().getUnits().get(0);

			// Re-Adapt context from previous mutant
			MutationUtil.adaptContext(invertedCopier, mutation.getUsedContext(), mutationRule);	
		}
	}

	
}
