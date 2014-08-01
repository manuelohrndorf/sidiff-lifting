package org.sidiff.serge.ocl.constraintapplicator.analyzers;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;
import org.sidiff.serge.ocl.constraintapplicator.exceptions.OCLExpressionPatternNotImplementedExeption;
import org.sidiff.serge.ocl.constraintapplicator.oclevaluators.OperationCallEvaluator;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.OCLExpressionPattern;
import org.sidiff.serge.ocl.constraintapplicator.settings.OCLCASettings;
import org.sidiff.serge.ocl.constraintapplicator.store.Constraint2ModuleStore;

public class OCLConstraintAnalyzer {

	
	private OCLCASettings settings;
	
	public OCLConstraintAnalyzer(OCLCASettings settings) {
		this.settings = settings;
	}
	
	public Constraint2ModuleStore indentifyConstraintPatterns() throws ParserException, OCLExpressionPatternNotImplementedExeption{
		
		EPackage metaModel = settings.getMetaModelEPackage();
		String eAnnotationKeyName = settings.geteAnnotationConstraintKey();
		
		
		// create store instance to hold all modules and all constraints
		Constraint2ModuleStore c2ms = new Constraint2ModuleStore(settings);
		
		// iterate over each EClassifier in the meta-model and find/analyze constraint
		for(EClassifier currentEClassifier: metaModel.getEClassifiers()) {

			EAnnotation eAnnoKey = currentEClassifier.getEAnnotation(eAnnotationKeyName);		
			
			if(eAnnoKey!=null) {
		
				
				System.out.println("\nCurrently considered EClasssifier: " + currentEClassifier.getName());
				String expressionText = eAnnoKey.getDetails().values().iterator().next();
				
				/**********************************************************************
				 * Create an instance of OCL and a OCLHelper for it
				 **********************************************************************/

				// create an OCL instance for Ecore
				OCL<?, EClassifier, ?, ?, ?, ?, ?, ?, ?, Constraint, EClass, EObject> ocl;
				ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);

				// create an OCL helper object
				OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();

				/**********************************************************************************
				 * Use the OCLHelper to parse the expression text into an OCLExpression object
				 **********************************************************************************/

				// set the OCL context classifier
				helper.setContext(currentEClassifier);  
				OCLExpression<EClassifier> oxlxp = helper.createQuery(expressionText);

				
				/**********************************************************************************
				 * Check expression for supported OCLExpressionPatterns and add them to
				 * constraint2modulestore
				 **********************************************************************************/
				OCLExpressionPattern oclxpp = checkForPattern(oxlxp, currentEClassifier);

				// put pattern into constraint2modulestore
				c2ms.addOCLExpressionPattern(oclxpp);


			}
		}
			
		return c2ms;
	}
	
	private OCLExpressionPattern checkForPattern(OCLExpression<EClassifier> oclxp, EClassifier containerEClassifier) throws OCLExpressionPatternNotImplementedExeption {
		
		OCLExpressionPattern foundPattern = null;
		
		if(oclxp instanceof OperationCallExp) {
		
			OperationCallEvaluator ocEval = new OperationCallEvaluator();
			foundPattern = ocEval.evaluate(oclxp, containerEClassifier);
			
		}

		else{
			throw new OCLExpressionPatternNotImplementedExeption(oclxp);
		}
		
		return foundPattern;
		
	}
	
}
