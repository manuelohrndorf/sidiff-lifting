package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.tracker.activeocl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ParserException;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclDependencyCollector;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclEvaluator;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclResult;

class ActiveOclEvaluatorImpl implements ActiveOclEvaluator {

	public ActiveOclResult evaluate(EObject context, String oclExp) throws ParserException {
		return new ActiveOclResultImpl(context, oclExp);
	}

	public Object evaluate(EObject context, String oclExp, ActiveOclDependencyCollector dependencyCollector) throws ParserException {
		return new DependencyEvaluator(context, oclExp, dependencyCollector).evaluate();
	}
}
