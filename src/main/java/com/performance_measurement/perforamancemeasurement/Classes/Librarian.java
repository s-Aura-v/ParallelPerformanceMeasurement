package com.performance_measurement.perforamancemeasurement.Classes;

import java.util.concurrent.ThreadLocalRandom;

public class Librarian implements Runnable {
    private int numberOfLibrarians;
    private int readProbability;

    public Librarian(int numberOfLibrarians, int readProbability) {
        this.numberOfLibrarians = numberOfLibrarians;
        this.readProbability = readProbability;
    }

    @Override
    public void run() {
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 100) * readProbability;
        if (randomNumber < readProbability) {
            // WRITE

        } else {
            // READ

        }
    }
}
