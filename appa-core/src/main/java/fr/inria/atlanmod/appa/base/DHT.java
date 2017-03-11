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

package fr.inria.atlanmod.appa.base;

import java.io.Serializable;
import java.util.concurrent.Future;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface DHT<K, V extends Serializable> {

    /**
     * Stores an object in this {@code DHT}.
     */
    void put(K key, V value);

    /**
     * Retrieves an object from this {@code DHT}.
     */
    Future<V> get(K key);
}
