package org.sidiff.patching;

import java.util.Collection;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.patching.report.ReportEntry;

public interface IValidationUnit {

	public Collection<ReportEntry> test(Resource resource);

	public Diagnostic validate(Resource resource);
	public Collection<Diagnostic> getErrors(Diagnostic diagnostic);
}
