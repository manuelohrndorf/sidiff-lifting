package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ParserException;

public interface ActiveOclEvaluator {

	ActiveOclResult evaluate(EObject context, String oclExp) throws ParserException;

	Object evaluate(EObject context, String oclExp, ActiveOclDependencyCollector dependencyCollector) throws ParserException;
}
