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

package fr.inria.atlanmod.appa.service.dht.mock;

import fr.inria.atlanmod.appa.service.DHTFactory;
import fr.inria.atlanmod.appa.service.DHTService;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Implémentation de l'interface qui sert d'AbstractFactory.
 */
@ParametersAreNonnullByDefault
public class MockDHTFactory implements DHTFactory {

    private String registryAddress;

    public MockDHTFactory(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    @Nonnull
    public <K, V extends Serializable> DHTService<K, V> createDHTService() {
        return new MockDHTAdapter<>(registryAddress);
    }
}
