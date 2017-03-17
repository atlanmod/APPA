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

package fr.inria.atlanmod.appa.service;

import fr.inria.atlanmod.appa.core.DHT;
import fr.inria.atlanmod.appa.core.Service;

import java.io.Serializable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * The interface defining the Distributed Hash Table (DHT) service.
 */
@ParametersAreNonnullByDefault
public interface DHTService<K, V extends Serializable> extends DHT<K, V>, Service {

    /**
     * Service name
     */
    String NAME = "DHTService";
}
