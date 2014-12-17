package org.sidiff.profileapplicator.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.profileapplicator.ProfileApplicator;

public class ProfileApplicatorThread extends Thread {

	/**
	 * The sourceFile to use as input.
	 */
	private File sourceFile;

	/**
	 * Counts number of base type processed since the last call of
	 * {@link #getProgessDelta()}. Transformations that were skipped must be
	 * counted too, since this field is used to track progress.
	 */
	private volatile int progressDelta = 0;

	/**
	 * The defined {@link ProfileApplicator} which executed this thread The
	 * global configuration is just needed once and never changed by any of the
	 * threads, hence this solution
	 */
	private ProfileApplicator applicator;

	/**
	 * Set if the ProfileApplicator canceled execution of this thread. The
	 * thread should finish its execution after this field is set.
	 */
	private boolean canceled = false;

	/**
	 * Set if this thread is done working
	 */
	private boolean finished = false;
	
	private Exception occuredException=null;

	/**
	 * 
	 * Initialize profile applicator thread
	 * 
	 * @param sourcefiles
	 *            the source files for this thread
	 * @param applicator
	 *            the profile applicator which executed this thread
	 */
	public ProfileApplicatorThread(File sourceFile, ProfileApplicator applicator) {

		this.sourceFile = sourceFile;
		this.applicator = applicator;

	}

	/**
	 * 
	 * The working method, which applies a profile to a given input file,
	 * whereas the input file is a henshin edit rule. Both are configured
	 * through the constructor call of this thread.
	 * 
	 * @throws Exception
	 * 
	 */
	public void applyProfile() throws Exception {

		// Initialize organizing variables
		boolean stereoTypesUsed = false;

		// Create resourceSet for source
		HenshinResourceSet srcResourceSet = new HenshinResourceSet(applicator
				.getSettings().getInputFolderPath());

		// Create EGraph for source
		EGraph srcGraph = new EGraphImpl();
		String relPath =(new File(applicator.getSettings().getInputFolderPath())).toURI().relativize(sourceFile.toURI()).getPath();
		Module mod = srcResourceSet.getModule(relPath);
		if (mod == null) {
			throw new IOException("Could not load module from " + relPath);
		}
		if (!srcGraph.add(mod)) {
			throw new Exception("Could not add module " + relPath + " to graph");
		}

		LogUtil.log(LogEvent.NOTICE, "Transformating: " + relPath + "...");

		// Iterate over all stereoTypes used in profile
		for (int i = 0; i < applicator.getStereoTypes().size(); i++) {

			StereoType stereoType = applicator.getStereoTypes().get(i);

			// Remember if at least one stereoType has been used
			if (applyStereoType(stereoType))
				stereoTypesUsed = true;
			progressDelta++;
			if (i == applicator.getStereoTypes().size() - 1)
				finished = true;
			if (canceled) {
				break;
			}
		}

		if (applicator.isBaseTypeInstances() || !stereoTypesUsed) {
			//TODO Check if saving works
			//TODO Need check if root has contents?
			File outputFile = getOutputFile(sourceFile, ((Module)srcGraph.getRoots().get(0)).getName());
			srcResourceSet.saveEObject(srcGraph.getRoots().get(0), outputFile.toString());
			LogUtil.log(
					LogEvent.DEBUG,
					"BaseTypeInstances allowed or stereoType not applicable"
							+ ", copied unmodified module to: "
							+ outputFile.toString());
		}

		// Clear memory
		releaseAdapters(srcGraph);
		srcResourceSet = null;
		srcGraph = null;
	}

	/**
	 * Tries to apply a given stereoType to the input file, defined through
	 * Thread constructor. Returns true if stereoType could be applied
	 * successfully.
	 * 
	 * @param stereoType
	 *            stereoType to apply
	 * @return boolean if stereoType could be applied successfully
	 * @throws Exception
	 */
	public boolean applyStereoType(StereoType stereoType) throws Exception {

		// Initialize organizing variables
		boolean stereoTypeUsed = false;

		for (EClass baseType : stereoType.getBaseTypeMap().keySet()) {

			boolean applied = false;

			LogUtil.log(LogEvent.DEBUG, "---------------------------");
			LogUtil.log(LogEvent.DEBUG,
					"Applying Stereotype: "
							+ stereoType.getName()
							+ " --("
							+ stereoType.getBaseTypeMap().get(baseType)
									.getName() + ")--> " + baseType.getName());

			HenshinResourceSet workResourceSet = null;
			EGraph workGraph = null;

			// Create resourceSet as working copy
			workResourceSet = new HenshinResourceSet(applicator.getSettings()
					.getInputFolderPath());

			// Create Module EGraph and its children as working copy
			workGraph = new EGraphImpl();
			String relPath=(new File(applicator.getSettings().getInputFolderPath())).toURI().relativize(sourceFile.toURI()).getPath();
			Module mod = workResourceSet.getModule(relPath);
			if (mod == null) {
				throw new IOException("Could not load module " + relPath);
			}
			if (!workGraph.addTree(mod)) {
				throw new Exception("Could not add module " + relPath);
			}

			try { // TODO Position dieses Trys überdenken
					// Add all important elements for matching
				workGraph.add(applicator.getStereoPackage());

				// Add Stereotype and its Attributes
				workGraph.add(stereoType.getStereoTypeClass());
				for (EStructuralFeature feature : stereoType
						.getStereoTypeClass().getEAllStructuralFeatures()) {
					workGraph.add(feature);
				}
				workGraph.add(baseType);
				workGraph.add(stereoType.getBaseTypeMap().get(baseType));

			} catch (Exception e) {

				// Nothing to do here
				// Just catching exceptions
				// of creating cross references

			}

			// Rename rule/module accordingly to profile
			// Naming scheme defined via editrule generator
			String baseTypeName = "_" + baseType.getName() + "_";
			String stereoTypeName = "_" + stereoType.getName() + "("
					+ baseType.getName() + ")" + "_";

			// Just for performance purposes:
			// Check if modified module already exists in target folder
			Pattern baseTypePattern = Pattern.compile(baseTypeName);
			String moduleName = new String(((Module) workGraph.getRoots()
					.get(0)).getName());
			Matcher m = baseTypePattern.matcher(moduleName);
			moduleName = m.replaceAll(stereoTypeName);

			// Test if result file already exists
			// Could be created by another thread or the previous run
			// -> No Matching/Transformation needed
			File outputFile = getOutputFile(sourceFile, moduleName);
			outputFile.getParentFile().mkdirs();

			boolean alreadyCreated = false;

				if (outputFile.exists()) {
					alreadyCreated = true;
					LogUtil.log(LogEvent.DEBUG, "File already created: "
							+ outputFile.toString());
					if (this.applicator.getSettings()
							.isOverwriteGeneratedTransformations()) {
						LogUtil.log(LogEvent.DEBUG, "Will be overwritten...");

					} else {
						LogUtil.log(LogEvent.DEBUG, "Will be skipped...");
					}

			}

			// If target file has not been created before or file shall be
			// overwritten according to settings
			if (!alreadyCreated
					|| this.applicator.getSettings()
							.isOverwriteGeneratedTransformations()) {

				// Create Henshin Engine
				Engine engine = new EngineImpl();

				// Iterate over all enabled higher order transformations
				for (URI hot : applicator.getTransformations()) {

					// Create unitapplication for transformation
					UnitApplication unitapp = new UnitApplicationImpl(engine);
					// Use current working copy graph
					unitapp.setEGraph(workGraph);

					// Create resourceSet for higher order transformation
					// henshin rule
					HenshinResourceSet hotsResourceSet = new HenshinResourceSet();

					// Get module
					Module module = hotsResourceSet.getModule(hot, false);

					LogUtil.log(
							LogEvent.DEBUG,
							"Executing HOT "
									+ hot.toString()
											.replace(
													"platform:/plugin/org.sidiff.profileapplicator/hots/",
													"") + "...");

					// Set unit to SiLift default
					unitapp.setUnit((Unit) module.getUnit("mainUnit"));

					// setting parameters
					unitapp.setParameterValue("stereoPackage",
							applicator.getStereoPackage());
					unitapp.setParameterValue("stereoType",
							stereoType.getStereoTypeClass());
					unitapp.setParameterValue("stereoTypeName", stereoTypeName);
					unitapp.setParameterValue("baseType", baseType);
					unitapp.setParameterValue("baseTypeName", baseTypeName);
					unitapp.setParameterValue("baseReference", stereoType
							.getBaseTypeMap().get(baseType));

					boolean executed = false;

					// Execute Henshin unit as often as possible
					do {
						executed = unitapp.execute(null);
						// Keep track of successful execution
						if (executed) {
							stereoTypeUsed = true;
							applied = true;

						}
					} while (executed);

					LogUtil.log(LogEvent.DEBUG, "Successfully applied: "
							+ applied);

				}

				// Save created profiled henshin edit rule
				if (applied) {
					// Save module as new file
					workResourceSet.saveEObject(workGraph.getRoots().get(0), outputFile.toString());
					LogUtil.log(LogEvent.DEBUG, "Result saved as: "+outputFile.toString());

				}
			}

			releaseAdapters(workGraph);
			workResourceSet = null;
			workGraph = null;

		}

		return stereoTypeUsed;

	}

	private File getOutputFile(File sourceFile, String moduleName){
		String relTargetFolderPath = (new File(applicator.getSettings().getInputFolderPath())).toURI().relativize((new File(sourceFile.getParent()).toURI())).getPath();
		File targetFolder = new File(applicator.getSettings().getOutputFolderPath(), relTargetFolderPath);
		return new File(targetFolder, moduleName + "_execute.henshin");
	}
	

	/**
	 * Helper method for clearing adapters and memory leaks in EGraph
	 * 
	 * @param graph
	 *            The EGraph to clear of Adapters
	 */
	public void releaseAdapters(EGraph graph) {

		try {
			// Clear memory from unused EObjects
			// If not done, execution time is exponential
			for (EObject roots : graph.getRoots()) {

				graph.removeGraph(roots);
			}

		} catch (Exception e) {

			// Nothing to do here
			// Just catching exceptions
			// of deleting cross references

		}
	}

	public int getProgessDelta() {
		int tmp = progressDelta;
		progressDelta = 0;
		return tmp;
	}

	public void cancel() {
		this.canceled = true;
	}

	public boolean isFinished() {
		return finished;
	}

	@Override
	public void run() {
		try {
			applyProfile();
		} catch (Exception e) {
			occuredException=e;
		}
	}

	public Exception getOccuredException() {
		return occuredException;
	}
}
