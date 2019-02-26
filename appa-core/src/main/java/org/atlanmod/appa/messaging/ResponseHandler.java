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

package fr.inria.atlanmod.appa.messaging;

import fr.inria.atlanmod.appa.core.Message;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static java.util.Objects.isNull;

@ParametersAreNonnullByDefault
public class ResponseHandler {

    private byte[] response;

    public synchronized boolean handleResponse(byte[] response) {
        this.response = response;
        notify();
        return true;
    }

    public synchronized Message waitForResponse() {
        if (isNull(response)) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(response))) {
            return (Message) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
