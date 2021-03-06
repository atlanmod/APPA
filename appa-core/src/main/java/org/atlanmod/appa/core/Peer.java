/*
 * Copyright (c) 2016-2017 Atlanmod INRIA LINA Mines Nantes.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 */

package org.atlanmod.appa.core;

import org.atlanmod.appa.datatypes.Id;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetAddress;

@ParametersAreNonnullByDefault
public interface Peer {

    @Nonnull
    Id id();

    long getBasePort();

    @Nonnull
    InetAddress getIp();
}
