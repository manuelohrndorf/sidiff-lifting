package org.sidiff.integration.preferences.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.sidiff.integration.preferences.PreferencesPlugin;

/**
 * 
 * @author Robert Müller
 *
 */
public class PipelineStepUtil {

	public static final String EXTENSION_POINT_ID = "org.sidiff.integration.preferences.pipelineSteps";
	public static final String EXTENSION_POINT_ATTRIBUTE_ID = "id";
	public static final String EXTENSION_POINT_ATTRIBUTE_TITLE = "title";
	public static final String EXTENSION_POINT_ATTRIBUTE_POSITION = "position";

	private static Map<String, PipelineStep> pipelineSteps;

	public static Map<String, PipelineStep> getAllAvailablePipelineSteps() {
		if(pipelineSteps == null) {
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

			pipelineSteps = new HashMap<String, PipelineStep>();
			for(PipelineStep step : list) {
				pipelineSteps.put(step.getId(), step);
			}
		}
		return pipelineSteps;
	}

	public static PipelineStep getPipelineStep(String id) {
		return getAllAvailablePipelineSteps().get(id);
	}

	public static class PipelineStep {
		static final Comparator<PipelineStep> COMPARATOR = new Comparator<PipelineStep>() {
			public int compare(PipelineStep o1, PipelineStep o2) {
				return o1.getPosition() - o2.getPosition();
			};
		};
		
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
