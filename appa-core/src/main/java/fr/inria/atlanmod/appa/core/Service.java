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

package fr.inria.atlanmod.appa.core;

import fr.inria.atlanmod.appa.datatypes.Id;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface Service extends Runnable {

    /**
     * Returns the unique identification of this {@code Service}.
     */
    @Nonnull
    Id id();

    /**
     * Starts this {@code Service}.
     */
    void start();

    /**
     * Stops this {@code Service}.
     */
    void stop();

    /**
     * Returns the port that this {@code Service} is listening to.
     */
    @Nonnegative
    int port();
}
