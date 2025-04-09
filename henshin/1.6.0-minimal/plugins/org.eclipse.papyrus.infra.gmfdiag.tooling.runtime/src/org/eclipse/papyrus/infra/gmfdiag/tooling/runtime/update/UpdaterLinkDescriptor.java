/*
 * Copyright (c) 2006, 2007, 2012 Borland Software Corporation and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Alexander Shatalin (Borland) - original templates
 *    Michael Golubev (Montages) - API extracted to gmf.tooling.runtime (#372479)
 */
package org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.update;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

/**
 * @since 3.0
 *
 * @deprecated since 3.1
 *             use {@link org.eclipse.papyrus.infra.gmfdiag.common.updater.UpdaterLinkDescriptor} API instead
 * 
 *             This class will be removed in Papyrus 5.0, see Bug 541027
 */
@Deprecated
public class UpdaterLinkDescriptor extends UpdaterNodeDescriptor {

	private EObject mySource;

	private EObject myDestination;

	private IAdaptable mySemanticAdapter;

	private UpdaterLinkDescriptor(EObject source, EObject destination, EObject linkElement, int linkVID) {
		super(linkElement, linkVID);
		mySource = source;
		myDestination = destination;
	}

	public UpdaterLinkDescriptor(EObject source, EObject destination, final IElementType elementType, int linkVID) {
		this(source, destination, (EObject) null, linkVID);
		mySemanticAdapter = new IAdaptable() {

			@Override
			public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
				if (IElementType.class.equals(adapter)) {
					return elementType;
				}
				return null;
			}
		};
	}

	public UpdaterLinkDescriptor(EObject source, EObject destination, EObject linkElement, final IElementType elementType, int linkVID) {
		this(source, destination, linkElement, linkVID);
		mySemanticAdapter = new EObjectAdapter(linkElement) {

			@Override
			public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
				if (IElementType.class.equals(adapter)) {
					return elementType;
				}
				return super.getAdapter(adapter);
			}
		};
	}

	public EObject getSource() {
		return mySource;
	}

	public EObject getDestination() {
		return myDestination;
	}

	public IAdaptable getSemanticAdapter() {
		return mySemanticAdapter;
	}

}
