package org.sidiff.patching.test;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.sidiff.patching.IValidationUnit;
import org.sidiff.patching.report.PatchReport.Status;
import org.sidiff.patching.report.PatchReport.Type;
import org.sidiff.patching.report.ReportEntry;

public class EMFValidationTestUnit implements IValidationUnit {

	@Override
	public Collection<ReportEntry> test(Resource resource) {
		Collection<ReportEntry> entries = new ArrayList<ReportEntry>();
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(resource.getContents().get(0));
		if (diagnostic.getSeverity() == Diagnostic.ERROR || diagnostic.getSeverity() == Diagnostic.WARNING) {
			for (Diagnostic childDiagnostic : diagnostic.getChildren()) {
				switch (childDiagnostic.getSeverity()) {
				case Diagnostic.ERROR:
					entries.add(new ReportEntry(Status.FAILED, Type.VALIDATION, childDiagnostic));
					break;
				case Diagnostic.WARNING:
					entries.add(new ReportEntry(Status.WARNING, Type.VALIDATION, childDiagnostic));
					break;
				}
			}
		} else {
			entries.add(new ReportEntry(Status.PASSED, Type.VALIDATION, "Model is valid"));
		}
		return entries;
	}

}
