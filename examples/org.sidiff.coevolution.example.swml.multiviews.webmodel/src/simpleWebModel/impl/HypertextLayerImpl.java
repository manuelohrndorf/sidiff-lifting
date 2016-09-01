/**
 */
package simpleWebModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import simpleWebModel.DataLayer;
import simpleWebModel.HypertextLayer;
import simpleWebModel.Page;
import simpleWebModel.SimpleWebModelPackage;
import simpleWebModel.StaticPage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Hypertext Layer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link simpleWebModel.impl.HypertextLayerImpl#getPages <em>Pages</em>}</li>
 *   <li>{@link simpleWebModel.impl.HypertextLayerImpl#getStartPage <em>Start Page</em>}</li>
 *   <li>{@link simpleWebModel.impl.HypertextLayerImpl#getDataLayer <em>Data Layer</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HypertextLayerImpl extends MinimalEObjectImpl.Container implements HypertextLayer {
	/**
	 * The cached value of the '{@link #getPages() <em>Pages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPages()
	 * @generated
	 * @ordered
	 */
	protected EList<Page> pages;

	/**
	 * The cached value of the '{@link #getStartPage() <em>Start Page</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPage()
	 * @generated
	 * @ordered
	 */
	protected StaticPage startPage;

	/**
	 * The cached value of the '{@link #getDataLayer() <em>Data Layer</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataLayer()
	 * @generated
	 * @ordered
	 */
	protected DataLayer dataLayer;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HypertextLayerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimpleWebModelPackage.Literals.HYPERTEXT_LAYER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Page> getPages() {
		if (pages == null) {
			pages = new EObjectContainmentEList<Page>(Page.class, this, SimpleWebModelPackage.HYPERTEXT_LAYER__PAGES);
		}
		return pages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StaticPage getStartPage() {
		if (startPage != null && startPage.eIsProxy()) {
			InternalEObject oldStartPage = (InternalEObject)startPage;
			startPage = (StaticPage)eResolveProxy(oldStartPage);
			if (startPage != oldStartPage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SimpleWebModelPackage.HYPERTEXT_LAYER__START_PAGE, oldStartPage, startPage));
			}
		}
		return startPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StaticPage basicGetStartPage() {
		return startPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartPage(StaticPage newStartPage) {
		StaticPage oldStartPage = startPage;
		startPage = newStartPage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimpleWebModelPackage.HYPERTEXT_LAYER__START_PAGE, oldStartPage, startPage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataLayer getDataLayer() {
		if (dataLayer != null && dataLayer.eIsProxy()) {
			InternalEObject oldDataLayer = (InternalEObject)dataLayer;
			dataLayer = (DataLayer)eResolveProxy(oldDataLayer);
			if (dataLayer != oldDataLayer) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SimpleWebModelPackage.HYPERTEXT_LAYER__DATA_LAYER, oldDataLayer, dataLayer));
			}
		}
		return dataLayer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataLayer basicGetDataLayer() {
		return dataLayer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataLayer(DataLayer newDataLayer) {
		DataLayer oldDataLayer = dataLayer;
		dataLayer = newDataLayer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimpleWebModelPackage.HYPERTEXT_LAYER__DATA_LAYER, oldDataLayer, dataLayer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SimpleWebModelPackage.HYPERTEXT_LAYER__PAGES:
				return ((InternalEList<?>)getPages()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SimpleWebModelPackage.HYPERTEXT_LAYER__PAGES:
				return getPages();
			case SimpleWebModelPackage.HYPERTEXT_LAYER__START_PAGE:
				if (resolve) return getStartPage();
				return basicGetStartPage();
			case SimpleWebModelPackage.HYPERTEXT_LAYER__DATA_LAYER:
				if (resolve) return getDataLayer();
				return basicGetDataLayer();
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
			case SimpleWebModelPackage.HYPERTEXT_LAYER__PAGES:
				getPages().clear();
				getPages().addAll((Collection<? extends Page>)newValue);
				return;
			case SimpleWebModelPackage.HYPERTEXT_LAYER__START_PAGE:
				setStartPage((StaticPage)newValue);
				return;
			case SimpleWebModelPackage.HYPERTEXT_LAYER__DATA_LAYER:
				setDataLayer((DataLayer)newValue);
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
			case SimpleWebModelPackage.HYPERTEXT_LAYER__PAGES:
				getPages().clear();
				return;
			case SimpleWebModelPackage.HYPERTEXT_LAYER__START_PAGE:
				setStartPage((StaticPage)null);
				return;
			case SimpleWebModelPackage.HYPERTEXT_LAYER__DATA_LAYER:
				setDataLayer((DataLayer)null);
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
			case SimpleWebModelPackage.HYPERTEXT_LAYER__PAGES:
				return pages != null && !pages.isEmpty();
			case SimpleWebModelPackage.HYPERTEXT_LAYER__START_PAGE:
				return startPage != null;
			case SimpleWebModelPackage.HYPERTEXT_LAYER__DATA_LAYER:
				return dataLayer != null;
		}
		return super.eIsSet(featureID);
	}

} //HypertextLayerImpl
