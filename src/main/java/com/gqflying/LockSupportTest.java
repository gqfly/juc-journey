package com.gqflying;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author gqfly
 * @version 1.0
 * @date 2024-05-20
 */
public class LockSupportTest {

    public static void main(String[] args) {

        Thread currentThread = Thread.currentThread();

        new NotifyThread(currentThread).start();

        System.out.println("before park");
        LockSupport.park();
        System.out.println("after park");

    }

    private static class NotifyThread extends Thread {

        private final Thread notifyThread;

        public NotifyThread(Object notifyObj) {
            if (!(notifyObj instanceof Thread)) {
                throw new RuntimeException("type error");
            }
            this.notifyThread = (Thread) notifyObj;
        }

        @Override
        public void run() {

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException ignored) {
            }

            System.out.println("before unpark");
            // 可以唤醒指定的线程
            LockSupport.unpark(notifyThread);
            System.out.println("after unpark");

        }
    }

}
