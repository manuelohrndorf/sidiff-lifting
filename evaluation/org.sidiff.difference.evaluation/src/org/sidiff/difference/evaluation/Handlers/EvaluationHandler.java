package org.sidiff.difference.evaluation.Handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.evaluation.engine.AbstractEvaluationEngine;
import org.sidiff.difference.evaluation.utils.EvaluationEngineUtil;
import org.sidiff.difference.symmetric.SymmetricDifference;

/**
 * 
 * @author cpietsch
 *
 */
public class EvaluationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) currentSelection;

			if (selection.getFirstElement() instanceof IFolder) {
				IFolder folder = (IFolder) selection.getFirstElement();
				System.out.println(folder.getName());

				File[] fileArray = (new File(folder.getLocation().toOSString()))
						.listFiles();
				List<List<Difference>> differences_List = new ArrayList<List<Difference>>();
				for (File file : fileArray) {
					if (file.isDirectory()) {
						List<Difference> differences = new ArrayList<Difference>();
						for (File subFile : file.listFiles()) {
							Resource resource = getResourceFromFile(subFile);
							Difference difference = new Difference();
							difference
									.setSymmetric((SymmetricDifference) resource
											.getContents().get(0));
							differences.add(difference);
						}
						differences_List.add(differences);
					}
				}
				AbstractEvaluationEngine engine = EvaluationEngineUtil.getEvaluationEngines("EnsureEvaluation");
				engine.setDifferences(differences_List);
				engine.evaluate();
				System.out.println("finsih");
				try {
					engine.createCSV(folder.getLocation().toOSString()+File.separator+"statistics.csv");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
		}
			
		
		return null;
	}

	/**
	 * loads a given {@link File} as a {@link Resource}
	 * 
	 * @param file
	 * 			{@link File} to load
	 * @return
	 * 			the file as a {@link Resource}
	 */
	public static Resource getResourceFromFile(File file){
		
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put(URI.createFileURI(file.getPath()).fileExtension(), new XMIResourceFactoryImpl());
		
	    ResourceSet resSet = new ResourceSetImpl();
		Resource resource = resSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);

		return resource;
	}
}
