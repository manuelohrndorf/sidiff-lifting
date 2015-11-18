package org.sidiff.patching.test.smg;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMGFileManager {

	private File modelFolder;
	private Pattern filesPattern = Pattern.compile("\\w+\\.\\d+\\.\\d{17}\\..+");
	private Pattern groupPattern = Pattern.compile("\\d+(?=\\.\\d+)");

	public SMGFileManager(File modelFolder) {
		this.modelFolder = modelFolder;
	}

	public Collection<TestFileGroup> getTestFileGroups() {
		Map<String, TestFileGroup> testMap = new TreeMap<String, SMGFileManager.TestFileGroup>();

		for (File file : modelFolder.listFiles()) {
			Matcher matcher = filesPattern.matcher(file.getName());
			Matcher group = groupPattern.matcher(file.getName());

			if (matcher.matches() && group.find()) {
				String id = group.group();
				TestFileGroup testSuite = testMap.get(id);
				if (testSuite == null) {
					testSuite = new TestFileGroup(id);
					testMap.put(id, testSuite);
				}
				if (file.getName().contains("matching")) {
					testSuite.matching = file;
				} else if (file.getName().contains("modified")) {
					testSuite.modified = file;
				} else if (file.getName().contains("original")) {
					testSuite.original = file;
				}
			}
		}

		return testMap.values();
	}

	public class TestFileGroup {
		File original;
		File modified;
		File matching;
		String id;

		public TestFileGroup(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "Test: " + id 
					+ "\n\tOriginal: " + original.getName() 
					+ "\n\tModified: " + modified.getName()
					+ "\n\tMatching: " + matching.getName();
		}

	}

}
