package org.sidiff.evaluation.silift.lifting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.evaluation.silift.lifting.engine.AbstractEvaluationEngine;
import org.sidiff.evaluation.silift.lifting.utils.EvaluationEngineUtil;

/**
 * Main class to be executed (within a, at minimum, headless eclipse) for running an evaluation.
 * 
 * @author kehrer, cpietsch
 */
public class EvaluationRun {

	public static void run(String folderPath) {
		File folder = new File(folderPath);

		File[] fileArray = folder.listFiles();

		// Sort fileArray
		Arrays.sort(fileArray, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		List<List<Difference>> differences_List = new ArrayList<List<Difference>>();
		for (File file : fileArray) {
			if (file.isDirectory()) {
				List<Difference> differences = new ArrayList<Difference>();
				for (File subFile : file.listFiles()) {
					Resource resource = getResourceFromFile(subFile);
					Difference difference = new Difference();
					difference.setSymmetric((SymmetricDifference) resource.getContents().get(0));
					differences.add(difference);
				}
				differences_List.add(differences);
			}
		}

		AbstractEvaluationEngine engine_result = EvaluationEngineUtil.getEvaluationEngines("ResultEvaluation");
		engine_result.setDifferences(differences_List);
		engine_result.evaluate();

		AbstractEvaluationEngine engine_distribution = EvaluationEngineUtil.getEvaluationEngines("DistributionEvaluation");
		engine_distribution.setDifferences(differences_List);
		engine_distribution.evaluate();

		System.out.println("finsih");
		try {
			engine_result.createCSV(folder.getAbsolutePath() + File.separator + "result-statistics.csv");
//			engine_distribution.createCSV(folder.getAbsolutePath() + File.separator + "distribution-statistics.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * loads a given {@link File} as a {@link Resource}
	 * 
	 * @param file
	 *            {@link File} to load
	 * @return the file as a {@link Resource}
	 */
	private static Resource getResourceFromFile(File file) {

		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put(URI.createFileURI(file.getPath()).fileExtension(), new XMIResourceFactoryImpl());

		ResourceSet resSet = new ResourceSetImpl();
		Resource resource = resSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);

		return resource;
	}
}
