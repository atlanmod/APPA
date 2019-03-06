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

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.Serializable;

@ParametersAreNonnullByDefault
public interface DHT<K extends Serializable, V extends Serializable> {

    /**
     * Stores an object in this {@code DHT}.
     */
    void put(K key, V value);

    /**
     * Retrieves an object from this {@code DHT}.
     */
    V get(K key);

    /**
     * Removes the value store at this key and the key itself from the DHT.
     */
    void remove(K key);
}
