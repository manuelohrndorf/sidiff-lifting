package org.sidiff.difference.lifting.recognitionengine.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.common.statistics.StatisticsUtil;
import org.sidiff.common.statistics.StatisticsUtil.StatisticType;
import org.sidiff.difference.lifting.recognitionengine.IRecognitionEngineStatistics;
import org.sidiff.difference.lifting.recognitionengine.graph.LiftingGraphFactory;
import org.sidiff.difference.lifting.recognitionengine.rules.RecognitionRuleBlueprint;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;
import org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil;
import org.sidiff.matching.model.MatchingModelPackage;

/**
 * Recognition-Engine statistic output helper.
 * 
 * @author Manuel Ohrndorf
 */
public class RecognitionEngineStatistics implements IRecognitionEngineStatistics {

	/**
	 * The CSV-Output-File.
	 */
	private File csvFile; 
	
	/**
	 * An instance of the statistic database.
	 */
	private StatisticsUtil statisticsUtil = StatisticsUtil.createStatisticsUtil();
	
	/**
	 * Map: Marker -> List of IDs.
	 */
	private Map<String, Set<String>> splitTimers = new HashMap<String, Set<String>>();
	
	/**
	 * Global statistic trigger.
	 */
	private boolean STATISTICS = false;
	
	/**
	 * The count of nodes/edges in the working graph will be post calculated in
	 * a simulation. Counting the working graph edges is time consuming and
	 * would lead to an execution time overhead in the real calculation.
	 */
	private boolean MEASURE_GRAPH_SIZE = true;
	
	/**
	 * Print an unavailable value.
	 */
	private static final String UNAVAILABLE_VALUE = "N/A";
	
	/**
	 * The common prefix of all statistic entities in the {@link StatisticsUtil}
	 */
	public static final String COMMON_PREFIX = "RecognitionEngine:";
	
	/**
	 * File name of model A. 
	 */
	public static final String MODEL_A = COMMON_PREFIX + "ModelA";
	
	/**
	 * Count of objects (nodes) in model A.
	 */
	public static final String MODEL_A_OBJECTS = COMMON_PREFIX + "ModelA:ObjectCount";
	
	/**
	 * Count of references (edges) in model A.
	 */
	public static final String MODEL_A_REFERENCES = COMMON_PREFIX + "ModelA:ReferenceCount";
	
	/**
	 * File name of model B.
	 */
	public static final String MODEL_B = COMMON_PREFIX + "ModelB";
	
	/**
	 * Count of objects (nodes) in model B.
	 */
	public static final String MODEL_B_OBJECTS = COMMON_PREFIX + "ModelB:ObjectCount";
	
	/**
	 * Count of references (edges) in model B.
	 */
	public static final String MODEL_B_REFERENCES = COMMON_PREFIX + "ModelB:ReferenceCount";
	
	/**
	 * Time that was consumed to filter unmatchable Recognition-Rules.
	 */
	public static final String RULE_SET_REDUCTION = COMMON_PREFIX + "RuleSetReduction";
	
	/**
	 * Execution time of {@link RecognitionEngine#execute()}
	 */
	public static final String EXECUTION = COMMON_PREFIX + "Execution:CreateGraphs+ExecuteRecognitionRules";
	
	/**
	 * The sum/minimum/maximum/average execution time to match a Recognition-Rule.
	 */
	public static final String MATCH_RR = COMMON_PREFIX + "Matching:RecognitionRule";
	
	/**
	 * The sum/minimum/maximum/average execution time to sorting a Recognition-Rule.
	 */
	public static final String SORT_RR = COMMON_PREFIX + "Sorting:RecognitionRule";
	
	/**
	 * The sum/minimum/maximum/average creation time of minimal/full EGraphs.
	 */
	public static final String CREATE_GRAPH = COMMON_PREFIX + "CreateGraph";
	
	/**
	 * The minimum/maximum/average created working graph size (nodes).
	 */
	public static final String GRAPH_NODES = COMMON_PREFIX + "Graph:Nodes";
	
	/**
	 * The minimum/maximum/average created working graph size (edges).
	 */
	public static final String GRAPH_EDGES = COMMON_PREFIX + "Graph:Edges";
	
	/**
	 * The node count a full (unreduced) working graph.
	 * 
	 * @deprecated
	 */
	public static final String FULL_GRAPH_NODES = COMMON_PREFIX + "(deprecated) FullGraph:Nodes";
	
	/**
	 * The edge count a full (unreduced) working graph.
	 * 
	 * @deprecated
	 */
	public static final String FULL_GRAPH_EDGES = COMMON_PREFIX + "(deprecated) FullGraph:Edges";
	
	/**
	 * Count of Add-Objects in the difference.
	 */
	public static final String DIFFERENCE_ADD_OBJECTS = COMMON_PREFIX + "Difference:AddObject";
	
	/**
	 * Count of Remove-Objects in the difference.
	 */
	public static final String DIFFERENCE_REMOVE_OBJECTS = COMMON_PREFIX + "Difference:RemoveObject";
	
	/**
	 * Count of Add-References in the difference.
	 */
	public static final String DIFFERENCE_ADD_REFERENCES = COMMON_PREFIX + "Difference:AddReference";
	
	/**
	 * Count of Remove-References in the difference.
	 */
	public static final String DIFFERENCE_REMOVE_REFERENCES = COMMON_PREFIX + "Difference:RemoveReference";
	
	/**
	 * Count of Attribute-Value-Changes in the difference.
	 */
	public static final String DIFFERENCE_ATTRIBUTE_VALUE_CHANGES = COMMON_PREFIX + "Difference:AttributeValueChanges";
	
	/**
	 * Count of Correspondences in the difference.
	 */
	public static final String DIFFERENCE_CORRESPONDENCES = COMMON_PREFIX + "Difference:Correspondences";
	
	/**
	 * Count of Semantic-Change-Sets in the difference.
	 */
	public static final String DIFFERENCE_SCS = COMMON_PREFIX + "Difference:SemanticChangeSets";
	
	/**
	 * Count of Low-Level changes in the difference.
	 */
	public static final String DIFFERENCE_LL_CHANGES = COMMON_PREFIX + "Difference:LowLevelChanges";
	
	/**
	 * Count of Low-Level in the difference which are not contained by a Semantic-Change-Sets.
	 */
	public static final String DIFFERENCE_LL_CHANGES_OUT = COMMON_PREFIX + "Difference:LowLevelChangesNotInSCS";
	
	/**
	 * Count of Low-Level in the difference which are contained by a Semantic-Change-Sets.
	 */
	public static final String DIFFERENCE_LL_CHANGES_IN = COMMON_PREFIX + "Difference:LowLevelChangesInSCS";
	
	/**
	 * Count of applied Recognition-Rules after filtering.
	 */
	public static final String RR_APPLIED = COMMON_PREFIX + "RR:Applied";
	
	/**
	 * Count of filtered Recognition-Rules.
	 */
	public static final String RR_FILTERED = COMMON_PREFIX + "RR:Filtered";
	
	/**
	 * Count of Add-Objects in the Recognition-Rules.
	 */
	public static final String RR_ADD_OBJECTS = COMMON_PREFIX + "RR:AddObject";
	
	/**
	 * Count of Remove-Objects in the Recognition-Rules.
	 */
	public static final String RR_REMOVE_OBJECTS = COMMON_PREFIX + "RR:RemoveObject";
	
	/**
	 * Count of Add-References in the Recognition-Rules.
	 */
	public static final String RR_ADD_REFERENCES = COMMON_PREFIX + "RR:AddReference";
	
	/**
	 * Count of Remove-References in the Recognition-Rules.
	 */
	public static final String RR_REMOVE_REFERENCES = COMMON_PREFIX + "RR:RemoveReference";
	
	/**
	 * Count of Attribute-Value-Changes in the Recognition-Rules.
	 */
	public static final String RR_ATTRIBUTE_VALUE_CHANGES = COMMON_PREFIX + "RR:AttributeValueChanges";
	
	/**
	 * Count of Correspondences in the Recognition-Rules.
	 */
	public static final String RR_CORRESPONDENCES = COMMON_PREFIX + "RR:Correspondences";
	
	/** 
	 * Column separator.
	 */
	private static final String COL = ";";
	
	/**
	 * Row separator.
	 */
	private static final String ROW = "\n";
	
	/**
	 * New statistic types: 
	 */
	public enum StatisticTypeExtensions {
		String, 
		Minimum, 
		MinimumInfo, 
		Maximum, 
		MaximumInfo, 
		Average, 
		SplitTime;
	}
	
	/**
	 * Statistic types <-> Primitive types: 
	 */
	private static Map<Enum<?>, String> statisticTypeToPrimitiveTypes = new HashMap<Enum<?>, String>();
    static {
    	statisticTypeToPrimitiveTypes.put(StatisticType.Count, "Integer");
    	statisticTypeToPrimitiveTypes.put(StatisticType.Other, "");
    	statisticTypeToPrimitiveTypes.put(StatisticType.Size, "Integer");
    	statisticTypeToPrimitiveTypes.put(StatisticType.Time, "Decimal");
    	statisticTypeToPrimitiveTypes.put(StatisticTypeExtensions.Average, "Decimal");
    	statisticTypeToPrimitiveTypes.put(StatisticTypeExtensions.Maximum, "Decimal");
    	statisticTypeToPrimitiveTypes.put(StatisticTypeExtensions.MaximumInfo, "String");
    	statisticTypeToPrimitiveTypes.put(StatisticTypeExtensions.Minimum, "Decimal");
    	statisticTypeToPrimitiveTypes.put(StatisticTypeExtensions.MinimumInfo, "String");
    	statisticTypeToPrimitiveTypes.put(StatisticTypeExtensions.String, "String");
    }
	
	/**
	 * All columns of the statistic:
	 */
	private static Object[][] columns = {
		{MODEL_A, StatisticTypeExtensions.String},
		{MODEL_A_OBJECTS, StatisticType.Size},
		{MODEL_A_REFERENCES, StatisticType.Size},
		{MODEL_B, StatisticTypeExtensions.String},
		{MODEL_B_OBJECTS, StatisticType.Size},
		{MODEL_B_REFERENCES, StatisticType.Size},
		{RULE_SET_REDUCTION, StatisticType.Time},
		{EXECUTION, StatisticType.Time},
		{CREATE_GRAPH, StatisticTypeExtensions.SplitTime, StatisticType.Time},						// Join Split-Timer
		{CREATE_GRAPH, StatisticTypeExtensions.SplitTime, StatisticTypeExtensions.Minimum},
		{CREATE_GRAPH, StatisticTypeExtensions.SplitTime, StatisticTypeExtensions.MinimumInfo},
		{CREATE_GRAPH, StatisticTypeExtensions.SplitTime, StatisticTypeExtensions.Maximum},
		{CREATE_GRAPH, StatisticTypeExtensions.SplitTime, StatisticTypeExtensions.MaximumInfo},
		{CREATE_GRAPH, StatisticTypeExtensions.SplitTime, StatisticTypeExtensions.Average},
		{MATCH_RR, StatisticTypeExtensions.SplitTime, StatisticType.Time},							// Join Split-Timer
		{MATCH_RR, StatisticTypeExtensions.SplitTime, StatisticTypeExtensions.Minimum},
		{MATCH_RR, StatisticTypeExtensions.SplitTime, StatisticTypeExtensions.MinimumInfo},
		{MATCH_RR, StatisticTypeExtensions.SplitTime, StatisticTypeExtensions.Maximum},
		{MATCH_RR, StatisticTypeExtensions.SplitTime, StatisticTypeExtensions.MaximumInfo},
		{MATCH_RR, StatisticTypeExtensions.SplitTime, StatisticTypeExtensions.Average},
		{GRAPH_NODES, StatisticTypeExtensions.Minimum},
		{GRAPH_NODES, StatisticTypeExtensions.MinimumInfo},
		{GRAPH_NODES, StatisticTypeExtensions.Maximum},
		{GRAPH_NODES, StatisticTypeExtensions.MaximumInfo},
		{GRAPH_NODES, StatisticTypeExtensions.Average},
		{GRAPH_EDGES, StatisticTypeExtensions.Minimum},
		{GRAPH_EDGES, StatisticTypeExtensions.MinimumInfo},
		{GRAPH_EDGES, StatisticTypeExtensions.Maximum},
		{GRAPH_EDGES, StatisticTypeExtensions.MaximumInfo},
		{GRAPH_EDGES, StatisticTypeExtensions.Average},
		{FULL_GRAPH_NODES, StatisticType.Size},
		{FULL_GRAPH_EDGES, StatisticType.Size},
		{DIFFERENCE_ADD_OBJECTS, StatisticType.Size},
		{DIFFERENCE_REMOVE_OBJECTS, StatisticType.Size},
		{DIFFERENCE_ADD_REFERENCES, StatisticType.Size},
		{DIFFERENCE_REMOVE_REFERENCES, StatisticType.Size},
		{DIFFERENCE_ATTRIBUTE_VALUE_CHANGES, StatisticType.Size},
		{DIFFERENCE_CORRESPONDENCES, StatisticType.Size},
		{DIFFERENCE_LL_CHANGES, StatisticType.Size},
		{DIFFERENCE_LL_CHANGES_IN, StatisticType.Size},
		{DIFFERENCE_LL_CHANGES_OUT, StatisticType.Size},
		{DIFFERENCE_SCS, StatisticType.Size},
		{RR_APPLIED, StatisticType.Size},
		{RR_FILTERED, StatisticType.Size},
		{RR_ADD_OBJECTS, StatisticType.Count},
		{RR_REMOVE_OBJECTS, StatisticType.Count},
		{RR_ADD_REFERENCES, StatisticType.Count},
		{RR_REMOVE_REFERENCES, StatisticType.Count},
		{RR_ATTRIBUTE_VALUE_CHANGES, StatisticType.Count},
		{RR_CORRESPONDENCES, StatisticType.Count}
	}; 
	
	/**
	 * Starts the Recognition-Engine statistic output.
	 * 
	 * @param CSVpath
	 *            The path to write the CSV-Output-File.
	 */
	@Override
	public void enable(String CSVpath) {
		STATISTICS = true;
		
		// Enable StatisticsUtil
		statisticsUtil.setEnabled(true);
		
		// Create CSV-Output-File
		csvFile = new File(CSVpath);
		writeHeader();
	}
	
	/**
	 * Stops and resets the Recognition-Engine statistic output.
	 */
	@Override
	public void disable() {
		// Reset statistics:
		reset();
		
		// Disable Recognition-Engine-Statistics:
		STATISTICS = false;
		
		// Disable StatisticsUtil:
		statisticsUtil.setEnabled(false);
	}
	
	/**
	 * Resets all measured statistics.
	 */
	@Override
	public void reset() {
		if (STATISTICS) {
			// Reset statistics:
			statisticsUtil.reset();
			splitTimers.clear();
		}
	}
	
	/**
	 * Checks if the Recognition-Engine statistic output is enabled.
	 * 
	 * @return <code>true</code> if the Recognition-Engine statistic output is
	 *         enabled; <code>false</code> otherwise.
	 */
	@Override
	public boolean isEnabled() {
		return STATISTICS;
	}
	
	/**
	 * The count of nodes/edges in the working graph will be post calculated in
	 * a simulation. Counting the working graph edges is time consuming and
	 * would lead to an execution time overhead in the real calculation.
	 * 
	 * @return <code>true</code> if measurement is enabled; 
	 *         <code>false</code> otherwise.
	 */
	public boolean isMesureGraphSize() {
		return MEASURE_GRAPH_SIZE;
	}

	/**
	 * The count of nodes/edges in the working graph will be post calculated in
	 * a simulation. Counting the working graph edges is time consuming and
	 * would lead to an execution time overhead in the real calculation.
	 * 
	 * @param mesureGraphSize
	 *            <code>true</code> if measurement should be enabled;
	 *            <code>false</code> otherwise.
	 */
	public void setMesureGraphSize(boolean mesureGraphSize) {
		MEASURE_GRAPH_SIZE = mesureGraphSize;
	}

	/**
	 * Starts a timer.
	 * 
	 * @param marker
	 *            The marker name.
	 */
	protected synchronized void startTimer(String marker) {
		if (STATISTICS) {
			statisticsUtil.start(marker);
		}
	}
	
	/**
	 * Stops a timer.
	 * 
	 * @param marker
	 *            The marker name.
	 */
	protected synchronized void stopTimer(String marker) {
		if (STATISTICS) {
			statisticsUtil.stop(marker);
		}
	}
	
	/**
	 * Returns the measured time (deducting a measured overhead of the statistics).
	 * 
	 * @param marker
	 *            The marker name.
	 * @return The measured time.
	 */
	protected float getTimer(String marker) {
		float time = -1;
		
		if (statisticsUtil.getTimeStatistic().containsKey(marker)) {
			return statisticsUtil.getTime(marker);
		}
		
		return time;
	}
	
	/**
	 * Starts a Split-Timer with a given ID. A Split-Timer is
	 * used to measure parallel execution times.
	 * 
	 * @param marker
	 *            The marker name.
	 * @param id
	 *            The ID of the Split-Timer.
	 */
	protected synchronized void startSplitTimer(String marker, String id) {
		startSplitTimer(marker, id, null);
	}
	
	/**
	 * Starts a Split-Timer with a given ID. A Split-Timer is
	 * used to measure parallel execution times.
	 * 
	 * @param marker
	 *            The marker name.
	 * @param id
	 *            The ID of the Split-Timer.
	 * @param info
	 *            A comment which will be assoziated with the actual split timer.
	 */
	protected synchronized void startSplitTimer(String marker, String id, String info) {
		if (STATISTICS)  {
			
			// Timer:
			Set<String> splitTimerIDs = null;
			
			if (splitTimers.containsKey(marker)) {
				splitTimerIDs = splitTimers.get(marker);
			} else {
				splitTimerIDs = new HashSet<String>();
				splitTimers.put(marker, splitTimerIDs);
			}
			
			if (!splitTimerIDs.contains(id)) {
				splitTimerIDs.add(id);
				
				// Info:
				if (info != null) {
					statisticsUtil.put(marker + "@" + id + "@info", info);
				}
			}
			
			statisticsUtil.start(marker + "@" + id);
		}
	}
	
	/**
	 * Stops a Split-Timer with a given ID.
	 * 
	 * @param marker
	 *            The marker name.
	 * @param id
	 *            The ID of the Split-Timer.
	 */
	protected synchronized void stopSplitTimer(String marker, String id) {
		if (STATISTICS)  {
			statisticsUtil.stop(marker + "@" + id);
		}
	}
	
	/**
	 * Summation of all Split-Timers for the given marker.
	 * 
	 * @param marker
	 *            The marker name.
	 */
	protected double joinSplitTimer(String marker) {
		if (splitTimers.containsKey(marker)) {
			double time = 0;
			
			for (String id : splitTimers.get(marker)) {
				time += statisticsUtil.getTime(marker + "@" + id);
			}
			
			return time;	
		}
		return -1;
	}
	
	/**
	 * Get the measured minimum of all Split-Timers for the given marker.
	 * 
	 * @param marker
	 *            The marker name.
	 * @return The ID of the minimum Split-Timer.
	 */
	protected String getMinimumSplitTimer(String marker) {
		if (splitTimers.containsKey(marker)) {
			double time = Double.MAX_VALUE;
			String minID = "";
			
			for (String id : splitTimers.get(marker)) {
				double nextTime = statisticsUtil.getTime(marker + "@" + id);
				
				if (nextTime < time) {
					time = nextTime;
					minID = id;
				}
			}
			
			return minID;	
		}
		return "";
	}
	
	
	/**
	 * Get the measured maximum of all Split-Timers for the given marker.
	 * 
	 * @param marker
	 *            The marker name.
	 * @return The ID of the maximum Split-Timer.
	 */
	protected String getMaximumSplitTimer(String marker) {
		if (splitTimers.containsKey(marker)) {
			double time = -1;
			String maxID = "";
			
			for (String id : splitTimers.get(marker)) {
				double nextTime = statisticsUtil.getTime(marker + "@" + id);
				
				if (nextTime > time) {
					time = nextTime;
					maxID = id;
				}
			}
			
			return maxID;	
		}
		return "";
	}
	
	/**
	 * Get the calculated average of all Split-Timers for the given marker.
	 * 
	 * @param marker
	 *            The marker name.
	 * @return The calculated average.
	 */
	protected double getAverageSplitTimer(String marker) {
		if (splitTimers.containsKey(marker)) {
			double time = 0;
			int count = 0;
			
			for (String id : splitTimers.get(marker)) {
				time += statisticsUtil.getTime(marker + "@" + id);
				count++;
			}
			
			return (time / count);
		}
		return -1;
	}
	
	/**
	 * Stores the minimum/maximum size measurement.
	 * 
	 * @param marker
	 *            The marker name.
	 * @param size
	 *            The new measured size.
	 * @param info
	 *            An info string which describes the measured size.
	 */
	protected synchronized void noteMinMax(String marker, double size, String info) {
		if (STATISTICS) {
			
			// Min-Max:
			double min = Double.MAX_VALUE;
			double max = -1;
			
			if (statisticsUtil.getOtherStatistic().containsKey(marker + "@min")) {
				min = (Double) statisticsUtil.getObject(marker + "@min");
			}
			
			if (statisticsUtil.getOtherStatistic().containsKey(marker + "@max")) {
				max = (Double) statisticsUtil.getObject(marker + "@max");
			}
			
			if (size < min) {
				statisticsUtil.put(marker + "@min", size);
				
				if (info != null) {
					statisticsUtil.put(marker + "@minInfo", info);
				}
			}
			
			if (size > max) {
				statisticsUtil.put(marker + "@max", size);
				
				if (info != null) {
					statisticsUtil.put(marker + "@maxInfo", info);
				}
			}
		}
	}
	
	/**
	 * Get the measured minimum.
	 * 
	 * @param marker
	 *            The marker name.
	 * @return The measured minimum.
	 */
	protected double getMinimum(String marker) {
		double min = -1;
		
		if (statisticsUtil.getOtherStatistic().containsKey(marker + "@min")) {
			min = (Double) statisticsUtil.getObject(marker + "@min");
		}
		
		return min;
	}
	
	/**
	 * Get the info string of the measured minimum.
	 * 
	 * @param marker
	 *            The marker name.
	 * @return The info string of the measured minimum.
	 */
	protected String getMinimumInfo(String marker) {
		String info = UNAVAILABLE_VALUE;
		
		if (statisticsUtil.getOtherStatistic().containsKey(marker + "@minInfo")) {
			info = (String) statisticsUtil.getObject(marker + "@minInfo");
		}
		
		return info;
	}
	
	/**
	 * Get the measured maximum.
	 * 
	 * @param marker
	 *            The marker name.
	 * @return The measured maximum.
	 */
	protected double getMaximum(String marker) {
		double max = -1;
		
		if (statisticsUtil.getOtherStatistic().containsKey(marker + "@max")) {
			max = (Double) statisticsUtil.getObject(marker + "@max");
		}
		
		return max;	
	}
	
	/**
	 * Get the info string of the measured maximum.
	 * 
	 * @param marker
	 *            The marker name.
	 * @return The info string of the measured maximum.
	 */
	protected String getMaximumInfo(String marker) {
		String info = UNAVAILABLE_VALUE;
		
		if (statisticsUtil.getOtherStatistic().containsKey(marker + "@maxInfo")) {
			info = (String) statisticsUtil.getObject(marker + "@maxInfo");
		}
		
		return info;
	}
	
	/**
	 * Calculates the average of the measurements.
	 * 
	 * @param marker
	 *            The marker name.
	 * @param size
	 *            The new measured size.
	 */
	protected synchronized void noteAverage(String marker, double size) {
		if (STATISTICS) {
			
			// Average:
			double fullSize = 0;
			double counter = 0;
			
			if (statisticsUtil.getOtherStatistic().containsKey(marker + "@average:size")) {
				fullSize = (Double) statisticsUtil.getObject(marker + "@average:size");
			}
			
			if (statisticsUtil.getOtherStatistic().containsKey(marker + "@average:count")) {
				counter = (Double) statisticsUtil.getObject(marker + "@average:count");
			}
			
			fullSize += size;
			counter++;
			
			statisticsUtil.put(marker + "@average:size", fullSize);
			statisticsUtil.put(marker + "@average:count", counter);
		}
	}
	
	/**
	 * Get the calculated average.
	 * 
	 * @param marker
	 *            The marker name.
	 * @return The calculated average.
	 */
	protected double getAverage(String marker) {
		double fullSize = -1;
		double counter = -1;
		
		if (statisticsUtil.getOtherStatistic().containsKey(marker + "@average:size")) {
			fullSize = (Double) statisticsUtil.getObject(marker + "@average:size");
		}
		
		if (statisticsUtil.getOtherStatistic().containsKey(marker + "@average:count")) {
			counter = (Double) statisticsUtil.getObject(marker + "@average:count");
		}
		
		if ((fullSize != -1) && (counter != -1)) {
			return (fullSize / counter);
		} else {
			return -1;
		}
	}
	
	/**
	 * Remember a size measurement.
	 * 
	 * @param marker
	 *            The marker name.
	 */
	protected synchronized void noteSize(String marker, int size) {
		if (STATISTICS) {
			statisticsUtil.putSize(marker, size);
		}
	}
	
	/**
	 * Count the nodes and edges of the used working graphs.
	 * 
	 * @param liftingGraphFactory
	 *            The factory that creates a Henshin graph.
	 * @param recognitionRules
	 *            The all executed Recognition-Rules.
	 * 
	 * @see RecognitionEngineStatistics#MEASURE_GRAPH_SIZE
	 */
	private void analyseEGraphs(LiftingGraphFactory liftingGraphFactory,  
			Map<Rule, RecognitionRuleBlueprint> recognitionRules) {
		
		if (!MEASURE_GRAPH_SIZE) {
			return;
		}
		
		analyseFullEGraph(liftingGraphFactory);
		
		// Simulate graph building: Dedicated working graph
		for (Rule recognitionRule : recognitionRules.keySet()) {
			analyseEGraph(liftingGraphFactory, recognitionRule, recognitionRules.get(recognitionRule));
		}
	}
	
	/**
	 * Count the nodes and edges of the (dedicated) recognition-rule working graph.
	 * 
	 * @param liftingGraphFactory
	 *            The factory that creates a Henshin graph.
	 * @param recognitionRule
	 *            The corresponding Recognition-Rule.
	 */
	private void analyseEGraph(LiftingGraphFactory liftingGraphFactory, 
			Rule recognitionRule, RecognitionRuleBlueprint blueprint) {
		
		if (!STATISTICS) {
			return;
		}
		
		EGraph graph = liftingGraphFactory.createLiftingGraph(recognitionRule, blueprint);
		
		// Nodes:
		noteMinMax(GRAPH_NODES, graph.size(), recognitionRule.getName());
		noteAverage(GRAPH_NODES, graph.size());
		
		//  Edges: Counting the working graph edges is time consuming and will lead to an execution time overhead.
		int edgeCount = getModelReferenceCount(graph.iterator());
		noteMinMax(GRAPH_EDGES, edgeCount, recognitionRule.getName());
		noteAverage(GRAPH_EDGES, edgeCount);
	}
	
	/**
	 * Measure the size a full (unreduced) working graph.
	 * 
	 * @param liftingGraphFactory
	 *            The factory that creates a Henshin graph.
	 *            
	 * @deprecated
	 */
	private void analyseFullEGraph(LiftingGraphFactory liftingGraphFactory) {
		if (!STATISTICS) {
			return;
		}
		
		noteSize(FULL_GRAPH_NODES, -1);
		noteSize(FULL_GRAPH_EDGES, -1);
	}
	
	/**
	 * Analyze the difference.
	 * 
	 * @param difference
	 *            The Symmetric-Difference.
	 */
	private void analyseDifference(SymmetricDifference difference) {
		if (!STATISTICS) {
			return;
		}
		
		// Analyze difference:
		DifferenceAnalysis analysis = new DifferenceAnalysis(difference);
		
		noteSize(DIFFERENCE_ADD_OBJECTS, analysis.getAddObjectCount());
		noteSize(DIFFERENCE_REMOVE_OBJECTS, analysis.getRemoveObjectCount());
		noteSize(DIFFERENCE_ADD_REFERENCES, analysis.getAddReferenceCount());
		noteSize(DIFFERENCE_REMOVE_REFERENCES, analysis.getRemoveReferenceCount());
		noteSize(DIFFERENCE_ATTRIBUTE_VALUE_CHANGES, analysis.getAttributeValueChangeCount());
		noteSize(DIFFERENCE_CORRESPONDENCES, analysis.getCorrespondenceCount());
		noteSize(DIFFERENCE_SCS, difference.getChangeSets().size());
		
		int allChanges = difference.getChanges().size();
		int uncoveredChanges = DifferenceAnalysisUtil.getRemainingChanges(difference).size();
		int coveredChanges = allChanges - uncoveredChanges;
		
		noteSize(DIFFERENCE_LL_CHANGES, allChanges);
		noteSize(DIFFERENCE_LL_CHANGES_OUT, uncoveredChanges);
		noteSize(DIFFERENCE_LL_CHANGES_IN, coveredChanges);
		
		// Analyze models:
		statisticsUtil.put(MODEL_A, difference.getModelA().getURI().lastSegment());
		statisticsUtil.put(MODEL_B, difference.getModelB().getURI().lastSegment());
		
		noteSize(MODEL_A_OBJECTS, getModelObjectCount(difference.getModelA().getAllContents()));
		noteSize(MODEL_A_REFERENCES, getModelReferenceCount(difference.getModelA().getAllContents()));
		
		noteSize(MODEL_B_OBJECTS, getModelObjectCount(difference.getModelB().getAllContents()));
		noteSize(MODEL_B_REFERENCES, getModelReferenceCount(difference.getModelB().getAllContents()));
	}
	
	/**
	 * Analyze the applied Recognition-Rules.
	 * 
	 * @param recognitionRules
	 *            All Recognition-Rules that were executed by the
	 *            Recognition-Engine.
	 * @param filtered
	 *            Recognition-Rules that were not needed.
	 */
	private void analyseRuleSet(Collection<Rule> recognitionRules, Collection<Rule> filtered) {
		if (!STATISTICS) {
			return;
		}
		
		statisticsUtil.putSize(RR_APPLIED, recognitionRules.size() - filtered.size());
		statisticsUtil.putSize(RR_FILTERED, filtered.size());
		
		statisticsUtil.resetCounter(RR_ADD_OBJECTS);
		statisticsUtil.resetCounter(RR_REMOVE_OBJECTS);
		statisticsUtil.resetCounter(RR_ADD_REFERENCES);
		statisticsUtil.resetCounter(RR_REMOVE_REFERENCES);
		statisticsUtil.resetCounter(RR_ATTRIBUTE_VALUE_CHANGES);
		statisticsUtil.resetCounter(RR_CORRESPONDENCES);
		
		SymmetricPackage SYM = SymmetricPackage.eINSTANCE;
	
		MatchingModelPackage MAT = MatchingModelPackage.eINSTANCE;

		for (Rule rr : recognitionRules) {
			if (!filtered.contains(rr)) {
				for (Node node : rr.getLhs().getNodes()) {
					
					// Add-Object:
					if (node.getType() == SYM.getAddObject()) {
						statisticsUtil.count(RR_ADD_OBJECTS);
					}
					
					// Remove-Object:
					else if (node.getType() == SYM.getRemoveObject()) {
						statisticsUtil.count(RR_REMOVE_OBJECTS);
					}
					
					// Add-Reference:
					else if (node.getType() == SYM.getAddReference()) {
						statisticsUtil.count(RR_ADD_REFERENCES);
					}
					
					// Remove-Reference:
					else if (node.getType() == SYM.getRemoveReference()) {
						statisticsUtil.count(RR_REMOVE_REFERENCES);
					}
					
					// Attribute-Value-Change:
					else if (node.getType() == SYM.getAttributeValueChange()) {
						statisticsUtil.count(RR_ATTRIBUTE_VALUE_CHANGES);
					}
					
					// Correspondence:
					else if (node.getType() == MAT.getCorrespondence()) {
						statisticsUtil.count(RR_CORRESPONDENCES);
					}
				}
			}
		}
	}
	
	/**
	 * Finish the statistic after the lifting is done.
	 * 
	 * @param difference
	 *            The lifted difference.
	 * @param recognitionRules
	 *            Set of processed Recognition-Rules.
	 * @param filtered
	 *            All filtered Recognition-Rules.
	 * 
	 */
	protected void finishStatistic(
			SymmetricDifference difference, 
			Map<Rule, RecognitionRuleBlueprint> recognitionRules,
			Collection<Rule> filtered,
			LiftingGraphFactory liftingGraphFactory) {
		
		if (!STATISTICS) {
			return;
		}
		
		LogUtil.log(LogEvent.MESSAGE, "Recognition-Engine-Statistics: Analyse data...");
		
		// Analyse difference and rule set:
		analyseDifference(difference);
		analyseRuleSet(recognitionRules.keySet(), filtered);
		analyseEGraphs(liftingGraphFactory, recognitionRules);
		
		// Save statistics:
		flushStatisticToCSV();
	}
	
	/**
	 * Write CSV Header (names of columns) if necessary.
	 */
	private void writeHeader() {
		
		// Check for existing header:
		if (csvFile.exists()) {
			BufferedReader reader = null;
			
			try {
				reader = new BufferedReader(new FileReader(csvFile));
				String header = reader.readLine();
				
				if (header != null) {
					return;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
		// Write header:
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(csvFile, true));
			
			// Column names:
			for (int i = 0; i < columns.length; i++) {
				String column = (String) columns[i][0];
				Enum<?> typeA = (Enum<?>) columns[i][1];
				Enum<?> typeB = null;
				
				if (columns[i].length > 2) {
					typeB = (Enum<?>) columns[i][2];
				}
				
				writer.write(parseHeaderField(column, typeA, typeB));
				
				if (i < (columns.length - 1)) {
					writer.write(COL);
				}
			}
			
			// Column types:
			writer.write(ROW);
			
			for (int i = 0; i < columns.length; i++) {
				Enum<?> type = (Enum<?>) columns[i][1];
				
				if (columns[i].length > 2) {
					type = (Enum<?>) columns[i][2];
				}
				
				writer.write(statisticTypeToPrimitiveTypes.get(type));
				
				if (i < (columns.length - 1)) {
					writer.write(COL);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Parse a Header field form its corresponding marker.
	 * 
	 * @param Marker
	 *            The marker to parse
	 * @return The header field entry.
	 */
	private String parseHeaderField(String marker, Enum<?> typeA, Enum<?> typeB) {
		String header = marker.substring(COMMON_PREFIX.length(), marker.length());
		
		// Remove camel-case
		String regex = "([a-z])([A-Z]+)";
		String replacement = "$1 $2";
		header = header.replaceAll(regex, replacement);
		
		// Separator
		header = header.replace(":", ": ");
		header = header.replace("+", " + ");
		
		// Type
		header += " :: " + typeA.toString().replaceAll(regex, replacement);
		
		if (typeB != null) {
			header += " :: " + typeB.toString().replaceAll(regex, replacement);
		}
		
		return header;
	}
	
	/**
	 * Flushes the statistic. This is done by writing any statistic entity to a
	 * CSV-File and then reset all statistic entities.
	 */
	protected void flushStatisticToCSV() {
		if (!STATISTICS || (csvFile == null)) {
			return;
		}
		
		BufferedWriter writer = null;
		
		try {
			// Write new row:
			writer = new BufferedWriter(new FileWriter(csvFile, true));
			writer.write(ROW);
			
			for (int i = 0; i < columns.length; i++) {
				String column = (String) columns[i][0];
				
				try {
					if (columns[i][1].equals(StatisticType.Time)) {
						writer.write("" + format(getTimer(column)));
						writer.write(COL);
					}
					
					else if (columns[i][1].equals(StatisticType.Size)) {
						writer.write("" + getStatisticSize(column));
						writer.write(COL);
					}
					
					else if (columns[i][1].equals(StatisticType.Count)) {
						writer.write("" + getStatisticCounter(column));
						writer.write(COL);
					}
					
					else if (columns[i][1].equals(StatisticType.Other)) {
						writer.write("" + getStatisticObject(column));
						writer.write(COL);
					}
					
					else if (columns[i][1].equals(StatisticTypeExtensions.String)) {
						writer.write("" + getStatisticObject(column));
						writer.write(COL);
					}
					
					else if (columns[i][1].equals(StatisticTypeExtensions.Minimum)) {
						writer.write("" + format(getMinimum(column)));
						writer.write(COL);
					}
					
					else if (columns[i][1].equals(StatisticTypeExtensions.MinimumInfo)) {
						writer.write("" + getMinimumInfo(column));
						writer.write(COL);
					}
					
					else if (columns[i][1].equals(StatisticTypeExtensions.Maximum)) {
						writer.write("" + format(getMaximum(column)));
						writer.write(COL);
					}
					
					else if (columns[i][1].equals(StatisticTypeExtensions.MaximumInfo)) {
						writer.write("" + getMaximumInfo(column));
						writer.write(COL);
					}
					
					else if (columns[i][1].equals(StatisticTypeExtensions.Average)) {
						writer.write("" + format(getAverage(column)));
						writer.write(COL);
					}
					
					else if (columns[i][1].equals(StatisticTypeExtensions.SplitTime)) {
						
						String minID = getMinimumSplitTimer(column);
						String maxID = getMaximumSplitTimer(column);
						
						if (columns[i][2].equals(StatisticType.Time)) {
							writer.write("" + format(joinSplitTimer(column)));
							writer.write(COL);
						}
						
						else if (columns[i][2].equals(StatisticTypeExtensions.Minimum)) {
							writer.write("" + format(getTimer(column + "@" + minID)));
							writer.write(COL);
						}
						
						else if (columns[i][2].equals(StatisticTypeExtensions.MinimumInfo)) {
							writer.write("" + getStatisticObject(column + "@" + minID + "@info"));
							writer.write(COL);
						}
						
						else if (columns[i][2].equals(StatisticTypeExtensions.Maximum)) {
							writer.write("" + format(getTimer(column + "@" + maxID)));
							writer.write(COL);
						}
						
						else if (columns[i][2].equals(StatisticTypeExtensions.MaximumInfo)) {
							writer.write("" + getStatisticObject(column + "@" + maxID + "@info"));
							writer.write(COL);
						}
						
						else if (columns[i][2].equals(StatisticTypeExtensions.Average)) {
							writer.write("" + format(getAverageSplitTimer(column)));
							writer.write(COL);
						}
						
					}
				} catch (Exception e) {
					writer.write(UNAVAILABLE_VALUE);
					writer.write(COL);
					e.printStackTrace();
				}
			}
			
			// Reset statistics:
			reset();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String getStatisticObject(String marker) {
		Object obj = statisticsUtil.getObject(marker);
		
		return (obj == null) ? UNAVAILABLE_VALUE : obj.toString();
	}
	
	private String getStatisticCounter(String marker) {
		Object obj = statisticsUtil.getCounter(marker);
		
		return (obj == null) ? UNAVAILABLE_VALUE : obj.toString();
	}
	
	private String getStatisticSize(String marker) {
		Object obj = statisticsUtil.getSize(marker);
		
		return (obj == null) ? UNAVAILABLE_VALUE : obj.toString();
	}
	
	private String format(double number) {
		
		if (number < 0) {
			return UNAVAILABLE_VALUE;
		}
		
		if ((number % 1) == 0) {
			int result = (int) number;
			return result + "";
		} else {
			DecimalFormat f = new DecimalFormat("#0.000"); 
			f.setGroupingUsed(false);
			
			return f.format(number);	
		}
	}
	
	private String format(float number) {
		
		if (number < 0) {
			return UNAVAILABLE_VALUE;
		}
		
		if ((number % 1) == 0) {
			int result = (int) number;
			return result + "";
		} else {
			DecimalFormat f = new DecimalFormat("#0.000"); 
			f.setGroupingUsed(false);
			
			return f.format(number);	
		}
	}
	
	/**
	 * Counts all object of a model.
	 * 
	 * @param it
	 *            An iterator to iterate over all elements of the model.
	 * @return The counted object of the model.
	 */
	public int getModelObjectCount(Iterator<EObject> it) {
		int counter = 0;
		
		while (it.hasNext()) {
			it.next();
			counter++;
		}
		return counter;
	}
	
	/**
	 * Counts all references of a model.
	 * 
	 * @param it
	 *            An iterator to iterate over all elements of the model.
	 * @return The counted references of the model.
	 */
	public int getModelReferenceCount(Iterator<EObject> it) {
		int counter = 0;
		
		while(it.hasNext()) {
			EObject obj = it.next();
			
			for (EReference ref : obj.eClass().getEAllReferences()) {
				if (obj.eGet(ref) != null) {
					if (ref.isMany()) {
						Collection<?> multiRef = (Collection<?>) obj.eGet(ref);
						counter += multiRef.size();
					} else {
						counter++;
					}	
				}
			}
		}
		return counter;
	}
}
