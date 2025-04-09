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
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EvaluationVisitorImpl;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.ecore.impl.PropertyCallExpImpl;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.expressions.PropertyCallExp;

public class EvaluationVisitorImplEx extends EvaluationVisitorImpl {

	private final org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclDependencyCollector myDependencyCollector;

	public EvaluationVisitorImplEx(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> env, //
			EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> evalEnv, Map<? extends EClass, ? extends Set<? extends EObject>> extentMap, //
			org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.impl.ocl.activeocl.ActiveOclDependencyCollector dependencyCollector) {
		super(env, evalEnv, extentMap);
		myDependencyCollector = dependencyCollector;
	}

	@Override
	public Object visitPropertyCallExp(PropertyCallExp<EClassifier, EStructuralFeature> pc) {
		PropertyCallExpImpl pcImpl = (PropertyCallExpImpl) pc;

		final EStructuralFeature feature = pcImpl.getReferredProperty();

		OCLExpression<EClassifier> source = pc.getSource();

		EObject context = (EObject) source.accept(getVisitor());

		myDependencyCollector.registerDependency(context, feature);

		return super.visitPropertyCallExp(pc);
	}
}
