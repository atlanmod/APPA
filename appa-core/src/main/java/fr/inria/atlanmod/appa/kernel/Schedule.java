/*
 * Created on 2 juil. 07
 *
 */
package fr.inria.atlanmod.appa.kernel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class Schedule   implements Runnable {

    private final static Logger logger = Logger.global;

    private BlockingQueue<Runnable> actions;
    private Thread[] threads;

    public Schedule(int threadsNumber) {
        actions = new LinkedBlockingQueue<Runnable>();
        threads = new Thread[threadsNumber];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Consumer());
        }
    }

    public void schedule(Runnable a) {
        //logger.info("Scheduling " + a);
        try {
            actions.put(a);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void execute(Runnable r) {
        new Thread(r).start();
    }


    public void run() {
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }

    private class Consumer implements Runnable {
        public void run() {
            //ogger.info("Logger running");
            Runnable a;

            while (!Thread.interrupted()) {
                try {
                    a = actions.take();
                    a.run();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }

    }

}
