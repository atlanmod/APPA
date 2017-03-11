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

import fr.inria.atlanmod.appa.base.Message;
import fr.inria.atlanmod.appa.datatypes.RamdomId;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class AbstractMessage implements Message {

    @SuppressWarnings("JavaDoc")
    private static final long serialVersionUID = 3129434818460833862L;

    private final RamdomId id;

    public AbstractMessage(RamdomId id) {
        this.id = id;
    }

    @Nonnull
    public RamdomId getId() {
        return id;
    }

    @Nonnull
    @Override
    public byte[] toByteArray() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream outputStream = new ObjectOutputStream(baos)) {
            outputStream.writeObject(this);
            return baos.toByteArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
