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
import org.atlanmod.appa.datatypes.ServiceDescription;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.Future;

@ParametersAreNonnullByDefault
public interface RegistryService extends Service {

    /**
     * Name used to locate this service.
     */
    String NAME = "RegistryService";

    /**
     * Publishes an available service.
     *
     * @param service
     */
    void publish(ServiceDescription service);

    /**
     * Unpublishes a service
     *
     * @param service
     */
    void unpublish(ServiceDescription service);

    /**
     * Locates an available service
     *
     * @param id
     * @return
     */
    Future<ServiceDescription> locate(Id id);
}
