/*
 * Copyright (c) 2012, Montages AG
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Michael Golubev (Montages) - initial API (#372479)
 */
package org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.update;

import java.util.List;

import org.eclipse.gmf.runtime.notation.View;

/**
 * @since 3.0
 *
 * @deprecated since 3.1
 *             use {@link org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater} API instead
 * 
 *             This class will be removed in Papyrus 5.0, see Bug 541027
 */
@Deprecated
public interface DiagramUpdater {

	public List<? extends UpdaterNodeDescriptor> getSemanticChildren(View view);

	public List<? extends UpdaterLinkDescriptor> getContainedLinks(View view);

	public List<? extends UpdaterLinkDescriptor> getIncomingLinks(View view);

	public List<? extends UpdaterLinkDescriptor> getOutgoingLinks(View view);
}
