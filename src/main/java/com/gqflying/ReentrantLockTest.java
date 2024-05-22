package com.gqflying;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gqfly
 * @version 1.0
 * @date 2024-05-20
 */
public class ReentrantLockTest {

    static int threadNum = 10;
    static ReentrantLock lock = new ReentrantLock();
    static volatile int tickets = 6;
    static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException ignored) {
                }
                buyTicket();
            }, "t" + i).start();
        }
        countDownLatch.countDown();

        TimeUnit.SECONDS.sleep(999999999);

    }

    static void buyTicket() {
        lock.lock();
        try {

            if (tickets <= 0) {
                System.out.println(Thread.currentThread().getName() + "购票，但是没票了~");
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException ignored) {
                }
                System.out.println(Thread.currentThread().getName() + "购买了最后第" + tickets-- + "张票");
            }

        } finally {
            lock.unlock();
        }
    }

}
