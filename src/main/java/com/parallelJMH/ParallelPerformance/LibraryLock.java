package com.parallelJMH.ParallelPerformance;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is an example of Conservative Lock for a read-mostly database.
 * Java's StampedLock is an example of what this code does.
 */
public class LibraryLock {
    final static ReentrantLock lock = new ReentrantLock();
    final static Condition writeable = lock.newCondition();
    final static Condition readable = lock.newCondition();
    static int readers = 0, writers = 0;
    static int waitingReaders = 0;

    static void writeLock() {
        lock.lock();
        try {
            while (readers > 0 || writers > 0) {
                writeable.awaitUninterruptibly();
            }
            writers = 1;
        } finally {
            lock.unlock();
        }
    }

    static void readLock() {
        lock.lock();
        try {
            waitingReaders++;
            while (writers > 0) {
                readable.awaitUninterruptibly();
            }
            waitingReaders--;
            ++readers;
        } finally {
            lock.unlock();
        }
    }

    static void unlockWrite() {
        lock.lock();
        try {
            writers = 0;
            writeable.signal();
            readable.signalAll();
        } finally {
            lock.unlock();
        }
    }

    static void unlockRead() {
        lock.lock();
        try {
            --readers;
            if (readers == 0) {
                writeable.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
