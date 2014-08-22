package org.sidiff.editrule.generator.ocl.constraintapplicator.analyzers;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData.EStructuralFeatureExtendedMetaData;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.EcoreEvaluationEnvironment;
import org.eclipse.ocl.ecore.EcoreOCLStandardLibrary;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.editrule.generator.ocl.constraintapplicator.exceptions.OCLExpressionPatternNotImplementedExeption;
import org.sidiff.editrule.generator.ocl.constraintapplicator.oclevaluators.OperationCallEvaluator;
import org.sidiff.editrule.generator.ocl.constraintapplicator.oclpatterns.OCLExpressionPattern;
import org.sidiff.editrule.generator.ocl.constraintapplicator.settings.OCLCASettings;
import org.sidiff.editrule.generator.ocl.constraintapplicator.store.Constraint2ModuleStore;

public class OCLConstraintAnalyzer {

	/**
	 * The settings object for the OCLConstraintApplicator.
	 */
	private OCLCASettings settings;
	
	/**
	 * Constructor.
	 * @param settings
	 */
	public OCLConstraintAnalyzer(OCLCASettings settings) {
		this.settings = settings;
	}
	
	/**
	 * This method iterates though the meta-model, regards each OCLExpression and
	 * tries to find out if one of our defined OCLExpressionPattern matches it.
	 * If so, the corresponding OCLExpressionPattern is added to the Constraint2ModuleStore
	 * for later processing.
	 * @return
	 * @throws ParserException
	 * @throws OCLExpressionPatternNotImplementedExeption
	 */
	public Constraint2ModuleStore indentifyConstraintPatterns() throws ParserException, OCLExpressionPatternNotImplementedExeption{
		
		EPackage metaModel = settings.getMetaModelEPackage();
		String eAnnotationKeyName = settings.geteAnnotationConstraintKey();
		
		// create store instance to hold all modules and all constraints
		Constraint2ModuleStore c2ms = new Constraint2ModuleStore(settings);
		
		// iterate over each EObject in the meta-model and find/analyze constraints.
		// Differentiate between EOperation and EClassifier along the way to find the necessary eContainer as context
		for(EObject currentEObject: EMFUtil.getAllContentAsIterable(metaModel.eResource())) {
			
			String constraintAsString  = null;	
			EClassifier context = null;
			
			if(currentEObject instanceof EClassifier) {
				context = (EClassifier) currentEObject;
				
				constraintAsString = EcoreUtil.getAnnotation(context, eAnnotationKeyName, "documentation");
				
				if(constraintAsString!=null) {
					System.out.println("\nCurrently considered EClasssifier: " + context.getName());
				}
			}
			if(currentEObject instanceof EOperation) {
				EOperation currentEOperation = (EOperation) currentEObject;
				
				if(currentEOperation.eContainer() instanceof EClass) {
					context = (EClass) currentEOperation.eContainer();

					constraintAsString = EcoreUtil.getAnnotation(currentEOperation, eAnnotationKeyName, "documentation");				
					
					if(constraintAsString!=null) {
						System.out.println("\nCurrently considered EOperation: " + currentEOperation.getName()
								+"inside EClass '" + context.getName() + "'");
					}
				}
				else if(currentEOperation.eContainer() instanceof EAnnotation) {
					context = (EClass) currentEOperation.eContainer().eContainer();

					constraintAsString = EcoreUtil.getAnnotation(currentEOperation, eAnnotationKeyName, "documentation");				
					
					if(constraintAsString!=null) {
						System.out.println("\nCurrently considered EOperation: " + currentEOperation.getName()
								+"inside EClass '" + context.getName() + "'");
					}
				}
			}	
			
			if(constraintAsString!=null) {
		
				//strip Annotation text from everything that is not OCL
				//TODO this method might need improvements and is just a quick fix
				String expressionText = stripAnnotationTextFromExplanation(constraintAsString);
				
				//if there could not be found an OCL-Constraint
				//inside the Annotation-Details, skip.
				if(expressionText==null) {
					continue;
				}
				
				
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
				helper.setContext(context);  
				OCLExpression<EClassifier> oxlxp = helper.createQuery(expressionText);

				
				/**********************************************************************************
				 * Check expression for supported OCLExpressionPatterns and add them to
				 * constraint2modulestore
				 **********************************************************************************/
				OCLExpressionPattern oclxpp = checkForPattern(oxlxp, context);

				// put pattern into constraint2modulestore
				c2ms.addOCLExpressionPattern(oclxpp);


			}
		}
			
		return c2ms;
	}
	
	/**
	 * This method takes an OCLExpression and the EClassifier, under which the OCLExpression is stored in the meta-model,
	 * and identifies the outer OCL expression type (e.g. OperationCallExp, IfExp ..). The evaluation of such an
	 * expression is then delegated to the corresponding Evaluator (e.g. OperationCallEvaluator) for further analysis.
	 * If one of our defined OCLExpressionPattern can be found, it will be returned by this method.
	 * @param oclxp
	 * @param containerEClassifier
	 * @return OCLExpressionPattern
	 * @throws OCLExpressionPatternNotImplementedExeption
	 */
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
	
	
	/**
	 * Strip explanation text from constraintAsString text.
	 * Usually explanation is on the first line and the constraint
	 * follows on the second line. If there is only one line, there is no
	 * respective OCL constraint (--> skip then)	
	 * @param string
	 * @return
	 */
	private String stripAnnotationTextFromExplanation(String constraintAsString) {
		
					
		String expressionText = null;				
		String[] splittedText = constraintAsString.split("\n");
		if(splittedText.length>1) {
			for (int i = 1; i < splittedText.length; i++) {
				//If the line starts with 'self', then this is
				//an indicator for an OCL-Constraint.
				//TODO find more indicators according to meta-model
				if(expressionText==null && (
						splittedText[i].startsWith("self")
						|| splittedText[i].startsWith("(self") //TODO remove last braket
						)) {
					expressionText = splittedText[i];
					//concatenate the following lines to the expression
					//if there are any
					if(i<splittedText.length) {
						for(int j=i+1; j<splittedText.length; j++) {
							expressionText += splittedText[j];
						}
					}
				}
			}
		}	
		return expressionText;
	}
	
}
