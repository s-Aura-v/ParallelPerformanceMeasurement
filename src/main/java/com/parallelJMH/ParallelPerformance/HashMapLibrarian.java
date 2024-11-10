package com.parallelJMH.ParallelPerformance;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class HashMapLibrarian implements Runnable {
    private final Library library;
    private final ConcurrentHashMap<Long, Long> hashMapLibrary;
    private final int numberOfLibrarians;
    // ReadProbability = A number between 0 and 1.
    private final double readProbability;

    //DEBUG
    int writeCount = 0;
    int readCount = 0;

    public HashMapLibrarian(int numberOfLibrarians, double readProbability, Library library, ConcurrentHashMap<Long, Long> hashMapLibrary) {
        this.numberOfLibrarians = numberOfLibrarians;
        this.readProbability = readProbability;
        this.library = library;
        this.hashMapLibrary = hashMapLibrary;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfLibrarians; i++) {
            runJavaUtilityCalculation();
        }
//        System.out.printf("%-40s Read:%4d   Write:%3d%n",
//                Thread.currentThread(), readCount, writeCount);
    }


    private void runJavaUtilityCalculation() {
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 100);
        if (randomNumber < readProbability * 100) {
            if (randomNumber < readProbability * 100) {
                // READ
                long id = ThreadLocalRandom.current().nextInt(0, 10000);
                hashMapLibrary.containsKey(id);
                readCount++;
            } else {
                // WRITE
                long id = ThreadLocalRandom.current().nextInt(0, 10000);
                if (id % 2 == 0) {
                    hashMapLibrary.remove(id);
                } else {
                    hashMapLibrary.put(id, id);
                }
                writeCount++;
            }
        }
    }

    private void debugCode() {
        /*
         * TEST NUMBER 1: SEE IF ONE LIBRARY IS SHARED BETWEEN ALL THREADS
         * RESULT: TRUE;
         * CONCLUSION: A SINGLE LIBRARY IS SHARED BETWEEN ALL THREADS
         */
//        int id = Integer.parseInt(Thread.currentThread().getName().substring(Thread.currentThread().getName().lastIndexOf('-') + 1));
//        if (id % 2 == 0) {
//            library.addBook(1);
//        }
//        System.out.println(library.readBook(1));
    }


}
