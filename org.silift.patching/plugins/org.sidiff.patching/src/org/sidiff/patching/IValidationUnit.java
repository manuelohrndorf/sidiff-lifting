package org.sidiff.patching;

import java.util.Collection;

import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.patching.report.ReportEntry;

public interface IValidationUnit {

	public Collection<ReportEntry> test(Resource resource);

}
