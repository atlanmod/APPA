/*
 *
 *  * Copyright (c) 2013-2017 Atlanmod INRIA LINA Mines Nantes.
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 *
 *
 */
package org.atlanmod.appa.binaries;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

public class EObjectAdapter implements Adapter {

    private final int id;

    public EObjectAdapter(int id) {
        this.id = id;
    }

    @Override
    public void notifyChanged(Notification notification) {

    }

    @Override
    public Notifier getTarget() {
        return null;
    }

    @Override
    public void setTarget(Notifier notifier) {

    }

    @Override
    public boolean isAdapterForType(Object o) {
        return true;
    }


    public int id() {
        return id;
    }
}
