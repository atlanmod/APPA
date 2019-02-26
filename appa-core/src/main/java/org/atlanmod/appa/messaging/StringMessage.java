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

import fr.inria.atlanmod.appa.datatypes.RamdomId;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class StringMessage extends AbstractMessage {

    @SuppressWarnings("JavaDoc")
    private static final long serialVersionUID = -5411841115008367992L;

    private final String message;

    public StringMessage(String message) {
        super(new RamdomId());
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message: " + message;
    }
}
