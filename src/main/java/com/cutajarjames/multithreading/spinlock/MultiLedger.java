package com.cutajarjames.multithreading.spinlock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiLedger implements Runnable {
    final static int TOTAL_ACCOUNTS = 800000;
    final static int MAX_AMOUNT_TO_MOVE = 10;
    final static int INITIAL_MONEY = 100;
    final static int THREADS = 4;

    AtomicInteger[] ledger = new AtomicInteger[TOTAL_ACCOUNTS];
    Lock[] locks = new Lock[TOTAL_ACCOUNTS];
    AtomicInteger totalTrans = new AtomicInteger(0);
    Random random = new Random();

    public MultiLedger() {
        for (int i = 0; i < TOTAL_ACCOUNTS; i++) {
            ledger[i] = new AtomicInteger(INITIAL_MONEY);
            locks[i] = new SpinLock();
        }
    }

    @Override
    public void run() {
        while (true) {
            var accountA = random.nextInt(TOTAL_ACCOUNTS);
            var accountB = random.nextInt(TOTAL_ACCOUNTS);
            while (accountA == accountB) {
                accountB = random.nextInt(TOTAL_ACCOUNTS);
            }
            var amountToMove = random.nextInt(MAX_AMOUNT_TO_MOVE);
            var toLock = new int[]{accountA, accountB};
            Arrays.sort(toLock);
            locks[toLock[0]].lock();
            locks[toLock[1]].lock();

            ledger[accountA].addAndGet(-amountToMove);
            ledger[accountB].addAndGet(amountToMove);

            locks[toLock[1]].unlock();
            locks[toLock[0]].unlock();

            totalTrans.incrementAndGet();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.printf("Total accounts: %s total threads: %s using SpinLocks\n", TOTAL_ACCOUNTS, THREADS);
        var multiLedger = new MultiLedger();
        for (int i = 0; i < THREADS; i++) {
            new Thread(multiLedger).start();
        }
        while (true) {
            TimeUnit.SECONDS.sleep(2);
            Arrays.stream(multiLedger.locks).forEach(Lock::lock);
            var sum = Arrays.stream(multiLedger.ledger).mapToInt(AtomicInteger::get).sum();
            Arrays.stream(multiLedger.locks).forEach(Lock::unlock);
            System.out.println(multiLedger.totalTrans.get() + " " + sum);
        }
    }
}
