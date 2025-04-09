package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.tracker.activeocl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.OCL.Helper;
import org.eclipse.ocl.ecore.OCLExpression;

class DependencyEvaluator {

	private final EObject myContext;

	private final org.eclipse.ocl.ecore.OCL myOcl;

	private final OCLExpression myExpression;

	public DependencyEvaluator(EObject context, String oclExp, org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclDependencyCollector dependencyCollector) throws ParserException {
		myContext = context;

		myOcl = org.eclipse.ocl.ecore.OCL.newInstance(new org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.tracker.activeocl.EcoreEnvironmentFactoryEx(dependencyCollector));

		Helper helper = myOcl.createOCLHelper();
		helper.setContext(context.eClass());
		myExpression = helper.createQuery(oclExp);
	}

	public Object evaluate() {
		return myOcl.evaluate(myContext, myExpression);
	}
}
