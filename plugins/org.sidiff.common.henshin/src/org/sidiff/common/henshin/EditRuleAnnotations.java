package org.sidiff.common.henshin;

import java.util.Iterator;

import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.henshin.model.Module;

public class EditRuleAnnotations {
	
	public static enum ModuleAnnotations {
		priority,
	}
	
	public static String getAnnotationKey(Enum<?> type) {
		return type.name();
	}
	
	public static Integer getPriority(Module editRule) {
		String value = getAnnotation(editRule, ModuleAnnotations.priority);
		
		if (value != null) {
			return Integer.valueOf(value);
		}
		
		return null;
	}
	
	public static void setPriority(Module editRule, int value) {
		setAnnotation(editRule, ModuleAnnotations.priority, value + "");
	}
	
	public static void removePriority(Module editRule) {
		removeAnnotation(editRule, ModuleAnnotations.priority);
	}
	
	public static void addPriority(Module editRule, int priority) {
		createAnnotation(editRule, ModuleAnnotations.priority, priority + "");
	}
	
	private static void createAnnotation(ModelElement element, Enum<?> type, String value) {
		Annotation annotation = HenshinFactory.eINSTANCE.createAnnotation();
		annotation.setKey(getAnnotationKey(type));
		annotation.setValue(value);
		element.getAnnotations().add(annotation);
	}
	
	private static String getAnnotation(ModelElement element, Enum<?> type) {
		String key = getAnnotationKey(type);

		for (Iterator<Annotation> it = element.getAnnotations().iterator(); it.hasNext();) {
			Annotation annotation = it.next();
			
			if (annotation.getKey().equals(key)) {
				return annotation.getValue();
			}
		}
		
		return null;
	}
	
	public static void removeAnnotation(ModelElement element, Enum<?> type) {
		String key = getAnnotationKey(type);

		for (Iterator<Annotation> it = element.getAnnotations().iterator(); it.hasNext();) {
			Annotation annotation = it.next();
			
			if (annotation.getKey().equals(key)) {
				it.remove();
				break;
			}
		}
	}
	
	private static void setAnnotation(ModelElement element, Enum<?> type, String value) {
		String key = getAnnotationKey(type);
		boolean found = false;

		// Search existing annotation:
		for (Iterator<Annotation> it = element.getAnnotations().iterator(); it.hasNext();) {
			Annotation annotation = it.next();
			
			if (annotation.getKey().equals(key)) {
				found = true;
				annotation.setValue(value);
				break;
			}
		}
		
		// Create a new annotation if necessary:
		if (!found) {
			createAnnotation(element, type, value);
		}
	}
}
