/**
 */
package de.imotep.core.behavior.de_imotep_core_behavior.impl;

import de.imotep.core.behavior.de_imotep_core_behavior.De_imotep_core_behaviorPackage;
import de.imotep.core.behavior.de_imotep_core_behavior.MCodeFragment;
import de.imotep.core.behavior.de_imotep_core_behavior.MELanguages;

import de.imotep.core.datamodel.de_imotep_core_datamodel.MAttribute;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>MCode Fragment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MCodeFragmentImpl#getCuse <em>Cuse</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MCodeFragmentImpl#getPuse <em>Puse</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MCodeFragmentImpl#getDuse <em>Duse</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MCodeFragmentImpl#getUsedAttributes <em>Used Attributes</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MCodeFragmentImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link de.imotep.core.behavior.de_imotep_core_behavior.impl.MCodeFragmentImpl#getLanguage <em>Language</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MCodeFragmentImpl extends MBehaviorEntityImpl implements MCodeFragment {
	/**
	 * The cached value of the '{@link #getCuse() <em>Cuse</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCuse()
	 * @generated
	 * @ordered
	 */
	protected EList<MAttribute> cuse;

	/**
	 * The cached value of the '{@link #getPuse() <em>Puse</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPuse()
	 * @generated
	 * @ordered
	 */
	protected EList<MAttribute> puse;

	/**
	 * The cached value of the '{@link #getDuse() <em>Duse</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDuse()
	 * @generated
	 * @ordered
	 */
	protected EList<MAttribute> duse;

	/**
	 * The cached value of the '{@link #getUsedAttributes() <em>Used Attributes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsedAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<MAttribute> usedAttributes;

	/**
	 * The default value of the '{@link #getExpression() <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected String expression = EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final MELanguages LANGUAGE_EDEFAULT = MELanguages.PROMELA;

	/**
	 * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected MELanguages language = LANGUAGE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MCodeFragmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return De_imotep_core_behaviorPackage.Literals.MCODE_FRAGMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAttribute> getCuse() {
		if (cuse == null) {
			cuse = new EObjectResolvingEList<MAttribute>(MAttribute.class, this, De_imotep_core_behaviorPackage.MCODE_FRAGMENT__CUSE);
		}
		return cuse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAttribute> getPuse() {
		if (puse == null) {
			puse = new EObjectResolvingEList<MAttribute>(MAttribute.class, this, De_imotep_core_behaviorPackage.MCODE_FRAGMENT__PUSE);
		}
		return puse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAttribute> getDuse() {
		if (duse == null) {
			duse = new EObjectResolvingEList<MAttribute>(MAttribute.class, this, De_imotep_core_behaviorPackage.MCODE_FRAGMENT__DUSE);
		}
		return duse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MAttribute> getUsedAttributes() {
		if (usedAttributes == null) {
			usedAttributes = new EObjectResolvingEList<MAttribute>(MAttribute.class, this, De_imotep_core_behaviorPackage.MCODE_FRAGMENT__USED_ATTRIBUTES);
		}
		return usedAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpression(String newExpression) {
		String oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MCODE_FRAGMENT__EXPRESSION, oldExpression, expression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MELanguages getLanguage() {
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLanguage(MELanguages newLanguage) {
		MELanguages oldLanguage = language;
		language = newLanguage == null ? LANGUAGE_EDEFAULT : newLanguage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, De_imotep_core_behaviorPackage.MCODE_FRAGMENT__LANGUAGE, oldLanguage, language));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__CUSE:
				return getCuse();
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__PUSE:
				return getPuse();
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__DUSE:
				return getDuse();
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__USED_ATTRIBUTES:
				return getUsedAttributes();
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__EXPRESSION:
				return getExpression();
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__LANGUAGE:
				return getLanguage();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__CUSE:
				getCuse().clear();
				getCuse().addAll((Collection<? extends MAttribute>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__PUSE:
				getPuse().clear();
				getPuse().addAll((Collection<? extends MAttribute>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__DUSE:
				getDuse().clear();
				getDuse().addAll((Collection<? extends MAttribute>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__USED_ATTRIBUTES:
				getUsedAttributes().clear();
				getUsedAttributes().addAll((Collection<? extends MAttribute>)newValue);
				return;
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__EXPRESSION:
				setExpression((String)newValue);
				return;
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__LANGUAGE:
				setLanguage((MELanguages)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__CUSE:
				getCuse().clear();
				return;
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__PUSE:
				getPuse().clear();
				return;
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__DUSE:
				getDuse().clear();
				return;
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__USED_ATTRIBUTES:
				getUsedAttributes().clear();
				return;
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__EXPRESSION:
				setExpression(EXPRESSION_EDEFAULT);
				return;
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__LANGUAGE:
				setLanguage(LANGUAGE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__CUSE:
				return cuse != null && !cuse.isEmpty();
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__PUSE:
				return puse != null && !puse.isEmpty();
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__DUSE:
				return duse != null && !duse.isEmpty();
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__USED_ATTRIBUTES:
				return usedAttributes != null && !usedAttributes.isEmpty();
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__EXPRESSION:
				return EXPRESSION_EDEFAULT == null ? expression != null : !EXPRESSION_EDEFAULT.equals(expression);
			case De_imotep_core_behaviorPackage.MCODE_FRAGMENT__LANGUAGE:
				return language != LANGUAGE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (expression: ");
		result.append(expression);
		result.append(", language: ");
		result.append(language);
		result.append(')');
		return result.toString();
	}

} //MCodeFragmentImpl
