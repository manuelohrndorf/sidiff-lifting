package org.sidiff.javaast.model.adapter;

import java.util.*;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.sidiff.common.emf.EMFAdapter;
import org.sidiff.javaast.model.*;

/**
 * Adapter providing comfortable access to JClasses.
 * 
 * @author kehrer
 */
public class JClassAdapter extends AdapterImpl {

	private Collection<JField> declaredFields;
	private Collection<JField> allFields;
	private Collection<JField> inheritedFields;
	
	private Collection<JMethod> declaredMethods;
	private Collection<JMethod> allMethods;
	private Collection<JMethod> inheritedMethods;

	@Override
	public boolean isAdapterForType(Object type) {
		return type == JClass.class;
	}
	
	/**
	 * Returns all fields declared in the adapted class.
	 * 
	 * @return A collection which can be empty but not <code>null</code>
	 */
	public Collection<JField> getDeclaredFields() {
		if (declaredFields == null) {
			declaredFields = Collections.unmodifiableCollection(getJClass().getFields());
		}

		return declaredFields;
	}
	
	/**
	 * Returns all fields (declared and inherited) of the adapted class.
	 * 
	 * @return A collection which can be empty but not <code>null</code>
	 */
	public Collection<JField> getAllFields() {
		if (allFields == null) {
			Collection<JField> all_tmp = new ArrayList<JField>();
			all_tmp.addAll(getDeclaredFields());
			all_tmp.addAll(getInheritedFields());
			allFields = Collections.unmodifiableCollection(all_tmp);
		}

		return allFields;
	}

	/**
	 * Returns all fields of the adapted class which are inherited from superclasses.
	 * 
	 * @return A collection which can be empty but not <code>null</code>
	 */
	public Collection<JField> getInheritedFields() {
		if (inheritedFields == null){
			Collection<JField> inh_tmp = new ArrayList<JField>();
			if (getJClass().getSuperClass() != null){				
				JClassAdapter superClassAdapter = EMFAdapter.INSTANCE.adapt(getJClass().getSuperClass(), JClassAdapter.class);		
				inh_tmp.addAll(superClassAdapter.getDeclaredFields());
				inh_tmp.addAll(superClassAdapter.getInheritedFields());				
			}
			inheritedFields = Collections.unmodifiableCollection(inh_tmp);
		}
		
		return inheritedFields;
	}
	
	/**
	 * Returns all methods declared in the adapted class.
	 * 
	 * @return A collection which can be empty but not <code>null</code>
	 */
	public Collection<JMethod> getDeclaredMethods() {
		if (declaredMethods == null) {
			declaredMethods = Collections.unmodifiableCollection(getJClass().getMethods());
		}

		return declaredMethods;
	}

	/**
	 * Returns all methods (declared and inherited) of the adapted class.
	 * 
	 * @return A collection which can be empty but not <code>null</code>
	 */
	public Collection<JMethod> getAllMethods() {
		if (allMethods == null) {
			Collection<JMethod> all_tmp = new ArrayList<JMethod>();
			all_tmp.addAll(getDeclaredMethods());
			all_tmp.addAll(getInheritedMethods());
			allMethods = Collections.unmodifiableCollection(all_tmp);
		}

		return allMethods;
	}

	/**
	 * Returns all methods of the adapted class which are inherited from superclasses.
	 * 
	 * @return A collection which can be empty but not <code>null</code>
	 */
	public Collection<JMethod> getInheritedMethods() {
		if (inheritedMethods == null){
			Collection<JMethod> inh_tmp = new ArrayList<JMethod>();
			if (getJClass().getSuperClass() != null){				
				JClassAdapter superClassAdapter = EMFAdapter.INSTANCE.adapt(getJClass().getSuperClass(), JClassAdapter.class);		
				inh_tmp.addAll(superClassAdapter.getDeclaredMethods());
				inh_tmp.addAll(superClassAdapter.getInheritedMethods());				
			}
			inheritedMethods = Collections.unmodifiableCollection(inh_tmp);
		}
		
		return inheritedMethods;
	}

	private JClass getJClass() {
		return (JClass) getTarget();
	}
}
