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

package org.atlanmod.appa.kernel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AsyncScheduler implements Scheduler {

    private final BlockingQueue<Runnable> actions;

    private final Thread[] threads;

    public AsyncScheduler(int threadsNumber) {
        actions = new LinkedBlockingQueue<>();
        threads = new Thread[threadsNumber];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Consumer());
        }
    }

    @Override
    public void schedule(Runnable runnable) {
        try {
            actions.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    private class Consumer implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    actions.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
