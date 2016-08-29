/**
 */
package simpleWebModel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import simpleWebModel.DynamicPage;
import simpleWebModel.Entity;
import simpleWebModel.SimpleWebModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dynamic Page</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link simpleWebModel.impl.DynamicPageImpl#getShows <em>Shows</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class DynamicPageImpl extends PageImpl implements DynamicPage {
	/**
	 * The cached value of the '{@link #getShows() <em>Shows</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShows()
	 * @generated
	 * @ordered
	 */
	protected Entity shows;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DynamicPageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimpleWebModelPackage.Literals.DYNAMIC_PAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity getShows() {
		if (shows != null && shows.eIsProxy()) {
			InternalEObject oldShows = (InternalEObject)shows;
			shows = (Entity)eResolveProxy(oldShows);
			if (shows != oldShows) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SimpleWebModelPackage.DYNAMIC_PAGE__SHOWS, oldShows, shows));
			}
		}
		return shows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity basicGetShows() {
		return shows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShows(Entity newShows) {
		Entity oldShows = shows;
		shows = newShows;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimpleWebModelPackage.DYNAMIC_PAGE__SHOWS, oldShows, shows));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SimpleWebModelPackage.DYNAMIC_PAGE__SHOWS:
				if (resolve) return getShows();
				return basicGetShows();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SimpleWebModelPackage.DYNAMIC_PAGE__SHOWS:
				setShows((Entity)newValue);
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
			case SimpleWebModelPackage.DYNAMIC_PAGE__SHOWS:
				setShows((Entity)null);
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
			case SimpleWebModelPackage.DYNAMIC_PAGE__SHOWS:
				return shows != null;
		}
		return super.eIsSet(featureID);
	}

} //DynamicPageImpl
