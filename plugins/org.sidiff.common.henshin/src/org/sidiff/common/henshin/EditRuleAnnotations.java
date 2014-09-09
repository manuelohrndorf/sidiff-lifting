package org.sidiff.common.henshin;

import java.util.Iterator;

import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.henshin.model.Module;

public class EditRuleAnnotations {
	
	public static enum ModuleAnnotations {
		priority,
		condition
	}
	
	public static enum Condition {
		pre, 
		post
	}
	
	public static boolean isKnownAnnotation(Annotation annotation) {
		ModuleAnnotations known = null;
			
		try {
			known = ModuleAnnotations.valueOf(annotation.getKey());
		} catch (Exception e) {
			return false;
		}

		if (known != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Condition getCondition(Graph applicationCondition) {
		String value = getAnnotation(applicationCondition, ModuleAnnotations.condition);
		
		if (value != null) {
			return Condition.valueOf(value);
		}
		
		return null;
	}
	
	public static void setCondition(Graph applicationCondition, Condition value) {
		setAnnotation(applicationCondition, ModuleAnnotations.condition, getAnnotationKey(value));
	}
	
	public static void removeCondition(Graph applicationCondition) {
		removeAnnotation(applicationCondition, ModuleAnnotations.condition);
	}
	
	public static void addCondition(Graph applicationCondition, Condition value) {
		createAnnotation(applicationCondition, ModuleAnnotations.priority, getAnnotationKey(value));
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
	
	public static String getAnnotationKey(Enum<?> type) {
		return type.name();
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
