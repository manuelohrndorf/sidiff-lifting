package org.sidiff.integration.preferences.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.integration.preferences.PreferencesPlugin;

/**
 * Contains utility functions for retrieving pipeline steps.
<<<<<<< HEAD
 * @author Robert M�ller
 *
=======
 * @author rmueller
>>>>>>> refs/heads/oxygen
 */
public class PipelineStepUtil {

	public static final String EXTENSION_POINT_ID = "org.sidiff.integration.preferences.pipelineSteps";
	public static final String EXTENSION_POINT_ATTRIBUTE_ID = "id";
	public static final String EXTENSION_POINT_ATTRIBUTE_TITLE = "title";
	public static final String EXTENSION_POINT_ATTRIBUTE_POSITION = "position";

	private static Map<String, PipelineStep> pipelineSteps;

	/**
	 * Returns all available pipeline steps as a map. The map is ordered according to the pipeline steps' positions.
	 * @return all available pipeline steps (ordered map of id -> PipelineStep)
	 */
	public static Map<String, PipelineStep> getAllAvailablePipelineSteps() {
		if(pipelineSteps == null) {
			// get all pipeline steps and sort them
			List<PipelineStep> list = new ArrayList<PipelineStep>();
			for(IConfigurationElement element : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID)) {
				try {
					String id = element.getAttribute(EXTENSION_POINT_ATTRIBUTE_ID);
					String title = element.getAttribute(EXTENSION_POINT_ATTRIBUTE_TITLE);
					int position = Integer.parseInt(element.getAttribute(EXTENSION_POINT_ATTRIBUTE_POSITION));
					list.add(new PipelineStep(id, title, position));
				} catch(NumberFormatException e) {
					PreferencesPlugin.logWarning("Invalid position for Pipeline Step contributed by "
									+ element.getDeclaringExtension().getContributor().getName(), e);
				}
			}
			list.sort(PipelineStep.COMPARATOR);

			// add the sorted list to an ordered map for faster access
			pipelineSteps = new LinkedHashMap<String, PipelineStep>();
			for(PipelineStep step : list) {
				pipelineSteps.put(step.getId(), step);
			}
		}
		return pipelineSteps;
	}

	/**
	 * Returns the PipelineStep with the given id.
	 * @param id the id
	 * @return the pipeline step with that id, <code>null</code> if none was found
	 */
	public static PipelineStep getPipelineStep(String id) {
		return getAllAvailablePipelineSteps().get(id);
	}

	/**
	 * A PipelineStep is a step in the SiDiff pipeline and has an id, a title, and a position.
	 * The position of a pipeline step determines the order of operations that reference this pipeline step.
	 */
	public static class PipelineStep {
		static final Comparator<PipelineStep> COMPARATOR = Comparator.comparingInt(PipelineStep::getPosition);

		private final String id;
		private final String title;
		private final int position;

		PipelineStep(String id, String title, int position) {
			this.id = id;
			this.title = title;
			this.position = position;
		}

		public String getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}

		public int getPosition() {
			return position;
		}
	}
}
