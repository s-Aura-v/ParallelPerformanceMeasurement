package com.performance_measurement.perforamancemeasurement.Classes;

import java.util.concurrent.ThreadLocalRandom;

public class Librarian implements Runnable {
    private Library library;

    //DEBUG
    int writeCount = 0;
    int readCount = 0;

    private int numberOfLibrarians;
    // ReadProbability = A number between 0 and 1.
    private final double readProbability;

    public Librarian(int numberOfLibrarians, double readProbability) {
        this.numberOfLibrarians = numberOfLibrarians;
        this.readProbability = readProbability;

//        //DEBUG
//        this.readProbability = .95;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfLibrarians; i++) {
            runSkipListCalculation();
        }
        System.out.println(Thread.currentThread() + ": Read   " + readCount + "   Write: " + writeCount);
    }

    private void initializeHo() {

    }

    private void runSkipListCalculation() {
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 100);
        if (randomNumber < readProbability * 100) {
            // READ
            readCount++;
        } else {
            // WRITE
            writeCount++;
        }
    }

    private void runJavaUtilityCalculation() {

    }

}
