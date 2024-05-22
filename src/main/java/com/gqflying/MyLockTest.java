package com.gqflying;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author gqfly
 * @version 1.0
 * @date 2024-05-20
 */
public class MyLockTest {

    private static int counter = 0;
    private static final MyLock lock = new MyLock();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                incr();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                incr();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(counter);
    }

    private static void incr() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    private static class MyLock extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        public void lock() {
            acquire(1);
        }

        public boolean tryLock() {
            return tryAcquire(1);
        }

        public void unlock() {
            release(1);
        }

        public boolean isLocked() {
            return isHeldExclusively();
        }

    }
}
