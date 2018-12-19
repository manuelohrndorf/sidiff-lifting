package org.sidiff.editrule.rulebase.builder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.sidiff.common.io.IOUtil;
import org.sidiff.editrule.rulebase.builder.internal.Activator;

public class EditRuleBaseClassBuilder {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getFormattedPackageName(String pluginID) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < pluginID.length(); i++) {
			char ch = pluginID.charAt(i);
			if (builder.length() == 0) {
				if (Character.isJavaIdentifierStart(ch))
					builder.append(Character.toLowerCase(ch));
			} else {
				if (Character.isJavaIdentifierPart(ch) || ch == '.')
					builder.append(ch);
			}
		}
		return builder.toString().toLowerCase(Locale.ENGLISH);
	}
	
	private IProject project;
	
	public EditRuleBaseClassBuilder(IProject project) {
		this.project = Objects.requireNonNull(project);
	}
	
	/**
	 * @param name
	 *            The name of the rulebase.
	 * @param buildDate
	 *            The build date of the rulebase.
	 * @param documentType
	 *            All supported document types.
	 * @param attachmentTypes
	 *            All supported attachment types.
	 * @return The code of the rulebase class file.
	 */
	private String getProjectClassCode(String name, Date buildDate,
			Set<String> documentType, Set<String> attachmentTypes) throws IOException, CoreException {

		String template = IOUtil.toString(
			IOUtil.openInputStream(Activator.PLUGIN_ID, "/templates/RuleBaseProject.template"),
			StandardCharsets.UTF_8);
		return String.format(template,
			getFormattedPackageName(project.getName()),
			project.getName(),
			name + " (" + DATE_FORMAT.format(buildDate) + ")",
			documentType.stream().collect(Collectors.joining("\", \"", "\"", "\"")),
			attachmentTypes.stream().collect(Collectors.joining("\", \"", "\"", "\"")));
	}

	public void generateClassFile(String name, Date buildDate,
			Set<String> documentType, Set<String> attachmentTypes, IProgressMonitor monitor) throws IOException, CoreException {
		
		String code = getProjectClassCode(name, buildDate, documentType, attachmentTypes);
		writeClassFile(monitor, code);
	}

	private void writeClassFile(IProgressMonitor monitor, String code) throws CoreException, IOException {
		IFile classFile = getClassFile();
		try (InputStream inputStream = new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8))) {
			if(classFile.exists()) {
				classFile.setContents(inputStream, true, false, monitor);
			} else {
				classFile.create(inputStream, true, monitor);
			}			
		}
		classFile.setCharset(StandardCharsets.UTF_8.name(), monitor);
	}

	private String getRuleBaseClassFilePath() {
		return "/src/"
				+ project.getName().replaceAll(Pattern.quote("."), "/")
				+ "/" + EditRuleBaseBuilder.RULE_BASE_CLASS_FILE;
	}

	private IFile getClassFile() {
		return project.getFile(getRuleBaseClassFilePath());
	}
	
	public void deleteClassFile(IProgressMonitor monitor) throws CoreException {
		IFile classFile = getClassFile();
		if (classFile.exists()) {
			classFile.delete(true, monitor);
		}
	}
}
