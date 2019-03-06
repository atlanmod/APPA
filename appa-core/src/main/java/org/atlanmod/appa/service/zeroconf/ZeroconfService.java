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

package org.atlanmod.appa.service.zeroconf;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface ZeroconfService {

    /**
     * The name of the service.
     */
    @Nonnull
    String name();

    /**
     * Type service type, used as a DNS-SD entry.
     */
    @Nonnull
    String type();

    /**
     * The service port.
     */
    @Nonnegative
    int port();
}
