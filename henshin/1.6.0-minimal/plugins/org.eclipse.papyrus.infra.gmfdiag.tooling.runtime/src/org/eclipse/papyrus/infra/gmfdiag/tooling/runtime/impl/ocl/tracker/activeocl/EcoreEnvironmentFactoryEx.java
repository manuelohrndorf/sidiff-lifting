package  org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.tracker.activeocl;

import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.EvaluationEnvironment;
import org.eclipse.ocl.EvaluationVisitor;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.internal.evaluation.TracingEvaluationVisitor;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclDependencyCollector;

class EcoreEnvironmentFactoryEx extends EcoreEnvironmentFactory {

	private final ActiveOclDependencyCollector myDependencyCollector;

	public EcoreEnvironmentFactoryEx(org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclDependencyCollector dependencyCollector) {
		myDependencyCollector = dependencyCollector;
	}

	@Override
	public EvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> createEvaluationVisitor(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> env,
			EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> evalEnv, Map<? extends EClass, ? extends Set<? extends EObject>> extentMap) {

		EvaluationVisitorImplEx evaluationVisitorImplEx = new EvaluationVisitorImplEx(env, evalEnv, extentMap, myDependencyCollector);
		EvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> result = evaluationVisitorImplEx;

		if (isEvaluationTracingEnabled()) {
			result = decorateWithTracingSupport(result);
		}
		return result;
	}

	private static <PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> //
	EvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> decorateWithTracingSupport(EvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> base) {
		return new TracingEvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E>(base);
	}
}
