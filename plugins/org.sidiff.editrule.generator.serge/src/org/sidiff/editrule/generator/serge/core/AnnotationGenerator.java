package org.sidiff.editrule.generator.serge.core;

import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.henshin.INamingConventions;

public class AnnotationGenerator {
	private Module module;

	public AnnotationGenerator(Module module) {
		super();
		this.module = module;
	}

	public void generate() {
		applyInverseAnnotations(); //TODO[LM@10.03.2015] Add config option for this
	}

	private void applyInverseAnnotations(){
		Module inverse = InverseTracker.INSTANCE.getInverse(module);
		if (inverse != null) {
			Annotation moduleAnnotation = HenshinFactory.eINSTANCE
					.createAnnotation();
			moduleAnnotation.setKey("INVERSE");
			moduleAnnotation.setValue(inverse.getName());
			module.getAnnotations().add(moduleAnnotation);
		}
		for (Parameter parameter : getMainUnitByName(module).getParameters()){
			Parameter inverseParameter = InverseTracker.INSTANCE.getInverse(parameter);
			if (inverseParameter != null){
				Annotation parameterAnnotation = HenshinFactory.eINSTANCE
						.createAnnotation();
				parameterAnnotation.setKey("INVERSE");
				parameterAnnotation.setValue(inverseParameter.getName());
				parameter.getAnnotations().add(parameterAnnotation);
			}
		}
	}
	
	public static Unit getMainUnitByName(Module module) {
		for (Unit unit : module.getUnits()) {
			if (!(unit instanceof Rule)
					&& unit.getName().equals(INamingConventions.MAIN_UNIT))
				return unit;
		}
		return null;
	}
}
